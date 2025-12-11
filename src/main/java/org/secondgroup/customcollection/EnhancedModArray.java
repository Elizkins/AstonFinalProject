package org.secondgroup.customcollection;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**Our custom collection is a kind of wrap around an array of elements that will retain the functionality of arrays and
 * add some features. Modified list of elements is a set of non-unique elements, which can be added, removed, replaced.
 * The maximum possible number of elements is limited by {@link Integer#MAX_VALUE}. Our list based on array of elements
 * and dynamically resizes. Also, when initialized, it provides first 8 operations {@link #addInTail(Object)} and
 * {@link #getByPosition(int)} time complexity of O(1) Other operations have O(n) time complexity. For our study project
 * we realized here some base operations which should be sufficient for the educational purposes:<br>
 * - adding a new element to the end of an array;<br>
 * - adding a new value to position by index;<br>
 * - adding a set of elements;<br>
 * - remove an element by position (index);<br>
 * - getting the element by position (index);<br>
 * - replacing the element at position (index) with a new element;<br>
 * - can be cast to a string;<br>
 * - check whether the list is empty or not. The list is empty if its {@link #valuesCount} is 0.<br>
 * - check whether it is possible to add more elements. Elements can be added if the {@link #valuesCount} is less than
 * the {@link #elements} length;<br>
 * - check out the list current size (how many elements contains);<br>
 * - all stored elements can be queried as a standard array;<br>
 * - sort, which needs comparator;<br>
 * - binary search, also needs comparator;<br>
 * - iterator, so you can use foreach cycle to iterate on this collection.*/
public class EnhancedModArray<T> implements Iterable<T> {

    private Object[] elements;
    private int valuesCount = 0;
    private static final int DEFAULT_CAPACITY = 10;
/** Initialization can be done as follows:<br>
 * - without specifying any arguments, in this case the number of elements is 10;<br>
 * - specifying the maximum number of array elements as a number;<br>
 * - when specifying various number of arguments or by an array of objects;<br>
 * - when specifying another {@link FixedListOfElements}. */
    public EnhancedModArray() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    public EnhancedModArray(int capacity) {
        this.elements = new Object[capacity];
    }

    @SuppressWarnings("unchecked")
    public EnhancedModArray(T ... elements) {
        Object[] newarr = new Object[elements.length + 8];
        System.arraycopy(elements, 0, newarr, 0, elements.length);
        this.elements = newarr;
        this.valuesCount = elements.length;
    }

    public EnhancedModArray(EnhancedModArray<T> array) {
        Object[] newarr = new Object[array.valuesCount + 8];
        System.arraycopy(array.elements, 0, newarr, 0, array.valuesCount);
        this.elements = newarr;
        this.valuesCount = array.valuesCount;
    }

    public void addInTail(T element) {
        if (valuesCount != elements.length) {
            this.elements[valuesCount] = element;
            valuesCount++;
        } else {
            increaseArray();
            addInTail(element);
        }
    }

    public void addInAnyPlace(T element, int index) {
        if (valuesCount != elements.length) {
            if (index >= 0 && index < this.valuesCount) {
                Object[] newarr = new Object[elements.length];
                System.arraycopy(elements, 0, newarr, 0, index);
                newarr[index] = element;
                System.arraycopy(elements, index, newarr, index + 1, elements.length - index - 1);
                this.elements = newarr;
                valuesCount++;

            } else {
                throw new IllegalArgumentException("wrong index");
            }
        } else {
            increaseArray();
            addInAnyPlace(element, index);
        }
    }

    public void addInTail(T[] elements) {
        if (valuesCount != this.elements.length && elements.length <= (this.elements.length - valuesCount)) {
            for (int i = 0; i < elements.length; i++) {
                this.elements[valuesCount] = elements[i];
                valuesCount++;
            }
        } else {
            increaseArray();
            addInTail(elements);
        }
    }

    public void addInTail(EnhancedModArray<T> modArr) {
        if (this.valuesCount != this.elements.length && modArr.valuesCount <= (this.elements.length
                - this.valuesCount)) {
            for (int i = 0; i < modArr.valuesCount; i++) {
                this.elements[valuesCount] = modArr.elements[i];
                valuesCount++;
            }
        } else {
            increaseArray();
            addInTail(modArr);
        }
    }

    public void addInAnyPlace(T[] elements, int index) {
        if (valuesCount != this.elements.length) {
            if (elements.length <= (this.elements.length - this.valuesCount)) {
                if (index >= 0 && index < this.valuesCount) {
                    Object[] newarr = new Object[this.elements.length];
                    System.arraycopy(this.elements, 0, newarr, 0, index);
                    int tmpValCount = valuesCount;
                    int ind = index;
                    for (int i = 0; i < elements.length; i++) {
                        newarr[ind] = elements[i];
                        valuesCount++;
                        ind++;
                    }
                    System.arraycopy(this.elements, index, newarr, index + elements.length,
                            tmpValCount - index);
                    this.elements = newarr;

                } else {
                    throw new IllegalArgumentException("wrong index");
                }
            } else {
                increaseArray();
                addInAnyPlace(elements, index);
            }
        } else {
            increaseArray();
            addInAnyPlace(elements, index);
        }
    }

    public void addInAnyPlace(EnhancedModArray<T> modArr, int index) {
        if (valuesCount != elements.length) {
            if (modArr.valuesCount <= (elements.length - valuesCount)) {
                if (index >= 0 && index < this.valuesCount) {
                    Object[] newarr = new Object[elements.length];
                    System.arraycopy(elements, 0, newarr, 0, index);
                    int tmpValCount = valuesCount;
                    int ind = index;
                    for (int i = 0; i < modArr.valuesCount; i++) {
                        newarr[ind] = modArr.elements[i];
                        valuesCount++;
                        ind++;
                    }
                    System.arraycopy(elements, index, newarr, index + modArr.valuesCount,
                            tmpValCount - index);
                    this.elements = newarr;
                } else {
                    throw new IllegalArgumentException("wrong index");
                }
            } else {
                increaseArray();
                addInAnyPlace(modArr, index );
            }
        } else {
            increaseArray();
            addInAnyPlace(modArr, index );
        }
    }

    public void removeAtIndex(int index) {
        if (!checkIfEmpty()) {
            if (index >= 0 && index < this.valuesCount) {
                Object[] newarr = new Object[elements.length];
                System.arraycopy(elements, 0, newarr, 0, index);
                System.arraycopy(elements, index + 1, newarr, index, elements.length - index - 1);
                this.elements = newarr;
                valuesCount--;
            } else {
                throw new IllegalArgumentException("wrong index");
            }
        } else {
            System.out.println("empty array");
        }

        if (valuesCount <= elements.length / 2) {
            decreaseArray();
        }
    }

    @SuppressWarnings("unchecked")
    public T getByPosition(int position) {
        if (position >= 0 && position < this.elements.length) {
            return (T) this.elements[position];
        } else {
            throw new IllegalArgumentException("wrong index");
        }
    }

    public void replaceByPosition(T element, int position) {
        if (position >= 0 && position < this.elements.length) {
            Object[] newarr = new Object[elements.length];
            System.arraycopy(elements, 0, newarr, 0, position);
            newarr[position] = element;
            System.arraycopy(elements, position + 1, newarr, position + 1,
                    elements.length - position - 1);
            this.elements = newarr;
        } else {
            throw new IllegalArgumentException("wrong index");
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

    public boolean checkIfEmpty() {
        if (elements.length == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int showLength() {
        return elements.length;
    }

    public int showValuesCount() {
        return this.valuesCount;
    }

    @SuppressWarnings("unchecked")
    public T[] getStandardArray(Class<T> classItself) {
        if (valuesCount <= elements.length) {
            T[] arr = (T[]) Array.newInstance(classItself, valuesCount);
            System.arraycopy(elements, 0, arr, 0, valuesCount);
            return arr;
        } else {
            return null;
        }
    }

    private void increaseArray() {
        Object[] newarr = new Object[elements.length + elements.length/2 + 1];
        System.arraycopy(elements, 0, newarr,0, valuesCount);
        this.elements = newarr;

    }

    private void decreaseArray() {
        Object[] newarr = new Object[elements.length/2];
        System.arraycopy(elements, 0, newarr,0, valuesCount);
        this.elements = newarr;
    }

    @SuppressWarnings("unchecked")
    public void sort(Comparator<T> compar) {
        Arrays.sort((T[]) elements, 0, valuesCount, compar);
    }

    @SuppressWarnings("unchecked")
    public int binarySearch(T value, Comparator<T> compar) {
        if (elements == null || valuesCount == 0) {
            return -1;
        }
        this.sort(compar); //binary search needs sorted array
        int low = 0;
        int high = valuesCount - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = compar.compare(value, (T) elements[mid]);
            if (cmp < 0) {
                high = mid - 1;
            } else if (cmp > 0) {
                low = mid + 1;
            } else {
                return mid; // found element
            }
        }
        return -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new CustIterator();
    }

    private class CustIterator implements Iterator<T> {

        private int currentIndex = -1;
        @Override
        public boolean hasNext() {
            return currentIndex + 1 < valuesCount;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) elements[++currentIndex];
        }

        @Override
        public void remove() {
            removeAtIndex(currentIndex);
        }
    }

    public static <T> EnhancedModArray<T> createCustomCol() {
        return new EnhancedModArray<>();
    }

    public static <T> EnhancedModArray<T> createMyCustomCol(T[] elements) {
        return new EnhancedModArray<>(elements);
    }
}
