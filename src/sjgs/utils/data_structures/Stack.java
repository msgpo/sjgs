package sjgs.utils.data_structures;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import sjgs.utils.data_structures.interfaces.StackInterface;

/** @class: A very powerful SinglyLinkedList based data structure. */
public class Stack<E> implements StackInterface<E> {

	private Node<E> top;
	private int size;

	public Stack(final E ...args) { combine(args); }
	public Stack(final Stack<E> args) { combine(args); }

	@Override
	public int size() { return size; }
	@Override
	public E peek() { return !isEmpty() ? top.data : null; }
	@Override
	public E get(final int index){
		final int target = size - index - 1;
		if(isEmpty() || target < 0 || index < 0) throw new NoSuchElementException();
		Node<E> current = top;
		for(int i = 0; i < target; i++) current = current.prev;
		return current.data;
	}

	@Override
	public void push(final E e) { top = new Node<E>(top, e); size++; }

	@Override
	public E pop() {
		if (isEmpty()) return null;
		final E answer = top.data;
		top = --size == 0 ? null : top.prev;
		return answer;
	}

	@Override
	public void remove(final E e) {
		if(isEmpty()) throw new NoSuchElementException();

		try {
			Node<E> current = top;
			int depth = size - 1;

			while(current.data != e) { current = current.prev; --depth; }

			if(get(depth) != null) remove(depth); else throw new NoSuchElementException();
		} catch (final NullPointerException npe) { /* no element to remove? */ }
	}

	@Override
	public E remove(final int index){
		final int target = size - index - 1;
		if(isEmpty() || target < 0 || index < 0) throw new NoSuchElementException();
		final Stack<E> temp = new Stack<E>();
		for(int i = 0; i < target; i++) temp.push(pop());
		final E answer = pop();
		while(!temp.isEmpty()) push(temp.pop());
		return answer;
	}

	@Override
	public Stack<E> clone() {
		final Stack<E> clone = new Stack<E>();
		for(final E e : this) clone.push(e);
		return clone;
	}

	public Stack<Stack<E>> split() { return split(2); }
	public Stack<Stack<E>> split(final int parts) {
		if(size < parts) throw new NoSuchElementException();
		// CREATE STACK OF STACKS
		final Stack<Stack<E>> stacks = new Stack<Stack<E>>();
		for(int i = 0; i < parts; i++) stacks.push(new Stack<E>());

		// FILL A TEMP STACK WITH CURRENT STACKS GOODS
		final Stack<E> temp = new Stack<E>();
		for(int i = 0; i < size; i++) temp.push(get(i));

		// FILL EACH STACK IN STACK OF STACKS WITH EQUAL PARTS
		for(int i = 0; i < parts; i++)
			for(int j = 0; j < size / parts; j++)
				stacks.get(i).push(temp.pop());

		// IF SIZE IS ODD, THIS WILL CATCH THE STRAGLER
		while(!temp.isEmpty()) stacks.peek().push(temp.pop());

		return stacks;
	}

	@Override
	public Iterator<E> iterator() { return new StackIterator(); }
	public class StackIterator implements Iterator<E>, Serializable {
		private Node<E> current;
		private E temp; /* avoid unnecessary mass object creation */
		public StackIterator() { super(); current = top; }
		@Override
		public boolean hasNext() { return current != null; }
		@Override
		public E next() {
			if (!hasNext()) throw new NoSuchElementException();
			temp = current.data;
			current = current.prev;
			return temp;
		}
	}

	@Override
	public String toString() {
		if(isEmpty()) return "STACK IS EMPTY";
		Node<E> temp = top;  final StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(int i = 0; i < size; i++, temp = temp.prev){
			sb.append(temp.data);
			if(i != size - 1) sb.append(", ");
		} return sb.toString() + ")";
	}

	// ----------------- NODE CLASS ------------------------------------------------- //
	private static class Node<E> implements Serializable {
		public final E data;
		public final Node<E> prev;
		public Node(final Node<E> prev, final E data) { this.prev = prev; this.data = data; }
	}
	// -------------- END NODE CLASS ------------------------------------------------- //

}
