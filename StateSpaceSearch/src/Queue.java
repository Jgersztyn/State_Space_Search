import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * A representation of a logical queue data structure based on the Java LinkedList
 * @author Jason Gersztyn
 *
 */

public class Queue<E> {

	//we need to base the queue off a LinkedList
	private LinkedList<E> list = new LinkedList<E>();

	/**
	 * generic constructor
	 */
	public Queue() {

	}

	/**
	 * place this item at the tail of the queue
	 * @param item
	 */
	public void enqueue(E item) {
		if(item == null) {
			throw new NullPointerException("Do not add a null item to this queue.");
		}
		list.addLast(item);
	}

	/**
	 * remove and return the item at the head of the queue
	 * @return
	 */
	public E dequeue() {
		if(list.isEmpty()) {
			throw new NoSuchElementException("There is nothing inside this queue.");
		}
		else {
			return list.removeFirst();
		}
	}

	/**
	 * get the first item in the queue (a.k.a. the head) but do not remove it
	 * @return the item at the head of the queue
	 */
	private E getHead() {
		if(list.isEmpty()) {
			throw new NoSuchElementException("There is nothing inside this queue.");
		}
		return list.get(0);
	}

	/**
	 * checks to see if there are elements inside of the queue. An empty queue has nothing at the head.
	 * @return true if the queue is empty
	 */
	public boolean isEmpty() {
		if(list.size() == 0) {
			return true; //there are no elements inside of this queue
		}
		else { return false; }
	}

	/**
	 * main method
	 * @param args
	 */
	public static void main(String args[]) {
		Queue<Object> que = new Queue<Object>();

		for(int i = 1; i <= 100; i++) {
			que.enqueue(i);
			System.out.println(i); //check the value being added
		}

		if (que.isEmpty()) {
			System.out.println("Add something next time");
		}
		else { System.out.println("Good job"); }

		Object obj = que.getHead();
		System.out.println(obj); //this should print out the first item we added to the queue

	}
}
