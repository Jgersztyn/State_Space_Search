import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * The portion of the state space machine which controls all of the logic. There are methods
 * in this class which allow for the board to be manipulated, such as moving tiles up and
 * down. It also provides ways to check for valid states and if the puzzle has been completed.
 * @author Jason Gersztyn
 *
 */


public class State {

	private int[] state; //an array which consists of the 16 tiles
	private int indexVal; //the index of this specific tile
	private Stack<Direction> moves; //track all moves have been made inside of the puzzle
	private static final int numTiles = 16; //the number of tiles
	private static final int[] goal = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}; //the completion state

	/**
	 * non-default constructor which creates a brand new state
	 * @param stateVal
	 * @param index
	 * @param moves
	 */
	public State(int indexVal, int[] items, Stack<Direction> moves) { 

		this.indexVal = indexVal;
		this.moves = new Stack<Direction>();
		while(indexVal < numTiles) {
			indexVal++;
			state = new int[indexVal];
		}
		for(int i = 0; i < items.length; i++) {
			state[i] = items[i];
		}
	}
	
	/**
	 * a special constructor which creates a new state based on an already existing one
	 * @param st the existing state to create a new state from
	 */
	public State(State st) { 
		indexVal = st.getIndex();
		int[] temp = st.getStateArray();
		state = new int[16];
		for(int i = 0; i < 16; i++){
			state[i] = temp[i];
		}
		moves = new Stack<Direction>();
		moves.addAll(st.getMoves());
	}

	/**
	 * get the current state of the puzzle array
	 * @return the current puzzle array
	 */
	public int[] getStateArray() {
		return state;
	}

	/**
	 * get the value contained inside of the specific index of an array
	 * @param idx the index of the array to check
	 * @return the value inside of this index
	 */
	public int getIndex() {
		int index = 0;
		while(state[index] != 0) {
			index++;
		}
		return index;
	}

	/**
	 * Get the stack of moves made thus far. That is, if the moves made thus far are down, left, down the stack will contain
	 * those values in a LIFO order
	 * @return the stack of current moves
	 */
	public Stack<Direction> getMoves() {
		return moves;
	}

	/**
	 * performs a left swap
	 * @param val the value being swapped
	 */
	public void left() {
		swap(indexVal, indexVal - 1);
		indexVal -= 1;
		moves.push(Direction.LEFT);
	}

	/**
	 * performs a right swap
	 * @param val the value being swapped
	 */
	public void right() {
		swap(indexVal, indexVal + 1);
		indexVal += 1;
		moves.push(Direction.RIGHT);
	}

	/**
	 * performs a up swap
	 * @param val the value being swapped
	 */
	public void up() {
		swap(indexVal, indexVal - 4);
		indexVal -= 4;
		moves.push(Direction.UP);
	}

	/**
	 * performs a down swap
	 * @param val the value being swapped
	 */
	public void down() {
		swap(indexVal, indexVal + 4);
		indexVal += 4;
		moves.push(Direction.DOWN);
	}
	
	/**
	 * check for all possible moves
	 * @return the arraylist containing all moves thus far
	 */
	public ArrayList<Direction> legalMoves() {

		//a structure to hold all moves found to be legal
		ArrayList<Direction> allLegalMoves = new ArrayList<>();

		if(indexVal % 4 != 0) { //left move
			if(moves.size() == 0) { //the stack is empty
				allLegalMoves.add(Direction.LEFT);
			}
			else if(moves.size() != 0 && moves.peek() != Direction.RIGHT) {
				allLegalMoves.add(Direction.LEFT);
			}
		}

		if(indexVal % 4 != 3) { //right move
			if(moves.size() == 0) { //the stack is empty
				allLegalMoves.add(Direction.RIGHT);
			}
			else if(moves.size() != 0 && moves.peek() != Direction.LEFT) {
				allLegalMoves.add(Direction.RIGHT);
			}
		}

		if(indexVal > 3) { //up move
			if(moves.size() == 0) { //the stack is empty
				allLegalMoves.add(Direction.UP);
			}
			else if(moves.size() != 0 && moves.peek() != Direction.DOWN) {
				allLegalMoves.add(Direction.UP);
			}
		}

		if(indexVal < 12) { //down move
			if(moves.size() == 0) { //the stack is empty
				allLegalMoves.add(Direction.DOWN);
			}
			else if(moves.size() != 0 && moves.peek() != Direction.UP) {
				allLegalMoves.add(Direction.DOWN);
			}
		}

		return allLegalMoves;
	}

	/**
	 * swaps two different tiles with each other
	 * @param old
	 * @param now
	 */
	private void swap(int old, int now) {
		int temp = state[old];
		state[old] = state[now];
		state[now] = temp;
	}

	/**
	 * checks the current state to see if the puzzle is solved
	 * @param checkGoal the array to check against the goal state
	 * @return true is puzzle is solved
	 */
	public boolean isGoal() {
		return Arrays.equals(state, goal);
	}

	/**
	 * print out all moves made thus far
	 */
	public void printMoves() {
		int size = moves.size();
		for(int i = 0; i < size; i++) {
			System.out.println(moves.pop());
		}
	}

	/**
	 * main method
	 * @param args
	 */
	public static void main(String args[]) {
		int[] arr = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}; //default state
		//int[] arr = {4,2,6,1,15,7,12,0,13,9,8,10,14,3,11,5};
		State state = new State(0, arr, new Stack<Direction>());
		if(state.isGoal()) {
			System.out.println("Great job!");
		}

		state.printMoves();
		if(state.isGoal() == false) {
			System.out.println("not there yet...");
		}
	}
}
