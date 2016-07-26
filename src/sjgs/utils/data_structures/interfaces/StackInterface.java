package sjgs.utils.data_structures.interfaces;

import static sjgs.utils.Utils.nextInt;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import sjgs.utils.Utils;
import sjgs.utils.data_structures.Stack;

public interface StackInterface<E> extends Iterable<E>, Serializable {

	public abstract int size();
	public abstract void push(E e);
	public abstract E pop();
	public abstract E peek();
	public abstract E get(int index);
	public abstract void remove(E e);
	public abstract E remove(int index);
	@Override
	public abstract String toString();

	default E first() { return getFirst(); }
	default E getFirst(){ return !isEmpty() ? get(0) : null; }
	default E getLast() { return peek(); }
	default boolean contains(final E e){ for(final E i : this) if (i == e) return true; return false; }

	default E removeFirst() { return remove(0); }

	default void clear(){ while(!isEmpty()) pop(); }

	default void writeToFile(final String path) {
		try { final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(path)));  oos.writeObject(this); oos.close();
		} catch (final Exception e) { e.printStackTrace(); System.exit(1); }
	}

	default void combine(final Stack<E> stack) { for(final E e : stack) push(e); }
	default void combine(final Stack<E> ...args) { for(final Stack<E> s : args) combine(s); }
	default void combine(final E[] arr) { for (final E element : arr)
		push(element); }
	default void combine(final E[] ...args) { for(final E[] a : args) combine(a); }

	default E[] toArray(){
		final E[] arr = (E[])new Object[size()];
		for(int i = 0; i < size(); i++) arr[i] = get(i);
		return arr;
	}

	default void reverse(){
		final Stack<E> temp = new Stack<E>();
		while(!isEmpty()) temp.push(removeFirst());
		while(!temp.isEmpty()) push(temp.pop());
	}

	default void shuffle() {
		final Stack<E> temp = new Stack<E>();
		while(!isEmpty()) temp.push(pop());
		while(!temp.isEmpty()) push(temp.remove(nextInt(temp.size())));
	}

	default void print() { if(isEmpty()) { Utils.print("STACK IS EMPTY"); return; } for(final E e : this) Utils.print(e); }
	default boolean isEmpty() { return size() == 0; }
	default void insert(final E e) { push(e); }
	default void add(final E e) { push(e); }
	default E top() { return peek(); }

}
