package ass1;
/**
 * 
 * @param <E>
 */


public interface Bag<E> {
	public int getSize();
	public boolean isEmpty();
	public boolean add (E element);
	public boolean remove(E element);
	public void clear();
	public boolean contains(E element);
	public int getFrequencyOf(E element);
}
