package sjgs.utils.data_structures.interfaces;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public interface ListInterface<E> extends Iterable<E>, Serializable {

	public abstract int size();
	public abstract void add(E e);
	public abstract void remove(E e);
	public abstract E remove(int index);
	public abstract E get(int index);

	default void clear() { while(!isEmpty()) removeFirst(); }
	default boolean contains(final E e){ for(final E i : this) if (i == e) return true; return false; }
	default E getFirst(){ return !isEmpty() ? get(0) : null; }
	default E removeFirst() { return !isEmpty() ? remove(0) : null; }
	default boolean isEmpty() { return size() == 0; }

	default E[] toArray(){
		final E[] arr = (E[])new Object[size()];
		for(int i = 0; i < size(); i++) arr[i] = get(i);
		return arr;
	}

	default void insert(final E e) { add(e); }

	default E first() { return get(0); }

	default void writeToFile(final String path) {
		try { final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(path)));  oos.writeObject(this); oos.close();
		} catch (final Exception e) { e.printStackTrace(); System.exit(1); }
	}

}
