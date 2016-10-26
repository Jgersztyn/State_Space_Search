import java.util.ArrayList;
import java.util.Stack;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * This class performs the actions required in a state space search and contains a 
 * graphical user interface for people to interact with. The specified puzzle will be
 * solved if at all possible, then a solution will be displayed.
 * @author Jason Gersztyn
 *
 */


public class PuzzleSolver implements KeyListener, ActionListener {

	private State state; //variable for a new state
	private State stateCopy = null; //variable to hold a state we pull from the queue
	private State newState = null; //variable to hold the state copy, after having made a valid move
	private State goalState = null; //the completed state
	private Stack<Direction> stack; //stack to store all directions
	private Queue<State> queue; //a queue to store our states
	private boolean finished = false; //checks to see if the solution has been solved

	//the basic structure for the GUI
	private JFrame frame;
	private JButton tile;
	private JTextArea output;
	private final int width = 4;
	private final int height = 4;

	//arrays to run the program with; we only use one at a time
	private static final int[] arrayOne = {4, 1, 2, 3, 5, 6, 10, 7, 8, 0, 9, 11, 12, 13, 14, 15};
	private static final int[] arrayTwo = {1, 2, 6, 3, 4, 0, 10, 7, 8, 5, 9, 11, 12, 13, 14, 15};
	private static final int[] arrayThree = {4, 1, 2, 3, 8, 0, 5, 7, 9, 10, 6, 11, 12, 13, 14, 15};
	private static final int[] arrayFour = {1, 2, 6, 3, 4, 5, 0, 7, 12, 8, 10, 11, 13, 14, 9, 15};

	/**
	 * constructor for solver
	 */
	public PuzzleSolver() {
		state = new State(0, arrayFour, stack);
		queue = new Queue<>();
		queue.enqueue(state); //put the newly created state into the queue
	}

	/**
	 * execute a state space search to solve the fifteen puzzle
	 */
	private void solve() {

		while(!finished) {
			stateCopy = queue.dequeue(); //pull a state from the queue
			
			ArrayList<Direction> moves = stateCopy.legalMoves(); //check for all possible legal moves
			for(Direction dir : moves) { //having stored the legal moves in the list, iterate over them
				newState = new State(stateCopy);
				switch(dir) { //check to see which legal moves are in the list, then execute them
				case RIGHT:
					newState.right();
					if(newState.isGoal()) {
						goalState = newState;
						finished = true;
					}
					queue.enqueue(newState);
					break;
				case LEFT:
					newState.left();
					if(newState.isGoal()) {
						goalState = newState;
						finished = true;
					}
					queue.enqueue(newState);
					break;
				case DOWN:
					newState.down();
					if(newState.isGoal()) {
						goalState = newState;
						finished = true;
					}
					queue.enqueue(newState);
					break;
				case UP:
					newState.up();
					if(newState.isGoal()) {
						goalState = newState;
						finished = true;
					}
					queue.enqueue(newState);
					break;
				}
				if(goalState != null) { //if goal has been reached, break out of the for each loop
					System.out.print("MOVES LIST : ");
					for(Direction ddir: goalState.getMoves()) {
						System.out.print(ddir + " | "); //success!
					}
					break; //break out of the while loop
				}
			}
		}
	}

	/**
	 * main method to run the program from
	 * @param args
	 */
	public static void main(String args[]) {
		PuzzleSolver puzzle = new PuzzleSolver();
		puzzle.setUp();
	}

	/**
	 * Construct a GUI for the state space search
	 * @param height tiles for grid layout
	 * @param width tiles for grid layout
	 */
	public void setUp() {
		//the main frame of the GUI
		frame = new JFrame("Jason's Fifteen Puzzle!");
		frame.setSize(500,280);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		//the panel on the bottom which holds the text area and solve button
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		textPanel.setSize(500,80);
		//the text area where text can be typed

		//the text area where the output text is displayed
		output = new JTextArea("");
		output.setSize(400,80);
		output.setEditable(false);
		output.setLineWrap(true);
		//the button which solves the puzzle input
		JButton solveButton = new JButton("Solve");
		//solveButton.setSize(100,80);
		solveButton.addActionListener(this);
		//add the button and text areas to the panel
		textPanel.add(solveButton);
		textPanel.add(output);
		
		//create an empty panel in the middle
		JPanel midPane = new JPanel();
		midPane.setSize(500,40);
		//create a label for this panel
		JLabel label = new JLabel("The solution to solve this puzzle will be displayed below");
		label.setSize(300,20);
		midPane.add(label);

		//create another panel to hold the 16 tiles of the puzzle
		JPanel tilePanel = new JPanel();
		tilePanel.setLayout(new GridLayout(height, width));
		tilePanel.setSize(500,160);
		tilePanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		//create many JButtons for the panel of tiles
		for(int i = 0; i < state.getStateArray().length; i++) {
			JButton tile = new JButton(); 
			tile.setSize(100,40);
			//get the array of values within our state
			int[] arr = state.getStateArray();
			if(arr[i] == 0) {
				tile.setText("");
			}
			else {
				//set each of these values to a corresponding tile
				tile.setText("" + arr[i]);
			}
			tile.setBackground(Color.white);
			tile.setEnabled(false);
			//button.addActionListener(this);
			tilePanel.add(tile);
		}

		//put the panels into the frame and pack it away
		frame.add(textPanel, BorderLayout.SOUTH);
		frame.add(midPane, BorderLayout.CENTER);
		frame.add(tilePanel, BorderLayout.NORTH);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * displays the results of the state space search in the GUIs output window
	 */
	private void displayResults() {

		ArrayList<String> items = new ArrayList<>();
		if(finished) { //only display the results if the puzzle has been finished
			Stack<Direction> moves = goalState.getMoves();
			for(Direction d : moves) {
				items.add("" + d); //allows us to convert the enums into Strings
			}
		}
		output.setText("Follow these steps: ");
		for(String s : items) {
			output.append(s + " -> ");
		}
	}

	/**
	 * replace the values displayed in the 16 tiles with the values of a solved puzzle
	 */
	private void replaceTiles() {
		for(int i = 0; i < 16; i++) {
			int[] arr = goalState.getStateArray();
			tile.setText("" + arr[i]);
		}
	}

	/**
	 * records an action and performs as operation based on it
	 * in this case, it solves our puzzle
	 * @param e the action being performed
	 */
	public void actionPerformed(ActionEvent e) {
		String str = ((JButton) e.getSource()).getText();
		if(str == "Solve") {
			solve();
			displayResults();
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
}
