package dm.otus.l3_collections;

import java.util.*;

import static java.lang.Integer.max;

@SuppressWarnings("WeakerAccess")
public class MyArrayList<E> implements List<E> {

    private Object[] elementData;
    private static final int DEFAULT_CAPACITY = 10;
    private int size;


    public MyArrayList() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not implemented");
//        return false;
    }

    public Iterator<E> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<E> {
        int cursor;
        int lastElementIndex = -1;

        @Override
        public boolean hasNext() {
            return cursor < size();
        }

        @Override
        public E next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            return MyArrayList.this.get(lastElementIndex = cursor++);
        }

        @Override
        public void remove() {
            if (lastElementIndex < 0) {
                throw new IllegalStateException();
            }
            MyArrayList.this.remove(lastElementIndex);
            cursor = lastElementIndex;
            lastElementIndex = -1;
        }
    }

    private class ListIter extends Iter implements ListIterator<E>{

        ListIter(int index) {
            super();
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            return MyArrayList.this.get(lastElementIndex = --cursor);
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor-1;
        }

        @Override
        public void set(E e) {
            MyArrayList.this.set(lastElementIndex, e);
        }

        @Override
        public void add(E e) {
            MyArrayList.this.add(cursor++, e);
            lastElementIndex = -1;
        }
    }

    public Object[] toArray() {
        return Arrays.copyOfRange(elementData, 0,size);
    }

    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not implemented");
//        return null;
    }

    public boolean add(E e) {
        ensureGrow(1);
        elementData[size++] = e;
        return true;
    }

    @SuppressWarnings("SameParameterValue")
    private void ensureGrow(int quantity) {
        final int needCapacity = size()+quantity;
        if(needCapacity > elementData.length) {
            final int nextStepCapacity = elementData.length*2;
            final int toCapacity = max(needCapacity, nextStepCapacity);
            growTo(toCapacity);
        }
    }

    private void growTo(int toCapacity) {
        elementData = Arrays.copyOf(elementData, toCapacity);
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not implemented");
//        return false;
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not implemented");
//        return false;
    }

    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not implemented");
//        return false;
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not implemented");
//        return false;
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not implemented");
//        return false;
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not implemented");
//        return false;
    }

    public void clear() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public E get(int index) {
        rangeCheck(index);
        //noinspection unchecked
        return (E)elementData[index];
    }

    private void rangeCheck(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(String.format("Then index=%d is not lower than size %d", index, size));
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException(String.format("The index=%d is less than zero", index));
        }
    }

    public E set(int index, E element) {
        rangeCheck(index);
        elementData[index] = element;
        return element;
    }

    public void add(int index, E element) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public E remove(int index) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public ListIterator<E> listIterator() {
        return new ListIter(0);
    }

    public ListIterator<E> listIterator(int index) {
        return new ListIter(index);
    }

    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
