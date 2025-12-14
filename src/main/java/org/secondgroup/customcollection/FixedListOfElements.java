package org.secondgroup.customcollection;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;

/**Our custom collection is a kind of wrap around an array of elements that will retain the functionality of arrays and
 * add some features. Fixed list of elements is a set of non-unique elements, which can be added, removed, replaced, but
 * the maximum possible number of elements is known in advance. Our list based on array of elements with fixed length.
 * It provides time complexity O(1) in {@link #getByPosition(int)} and {@link #addInTail(Object)} operations. Other
 * operations have O(n) time complexity. For our study project we realized here some base operations which should be
 * sufficient for the educational purposes:<br>
 * - adding a new element to the end of an array;<br>
 * - adding a new value to position by index;<br>
 * - adding a set of elements;<br>
 * - remove an element by position (index);<br>
 * - getting the element by position (index);<br>
 * - replacing the element at position (index) with a new element;<br>
 * - can be cast to a string;<br>
 * - check whether the list is empty or not. The list is empty if its {@link #entriesCount} is 0.<br>
 * - check whether it is possible to add more elements. Elements can be added if the {@link #entriesCount} is less than
 * the {@link #elements} length;<br>
 * - check out the list current size (how many elements contains);<br>
 * - all stored elements can be queried as a standard array;<br>
 * - sort, which needs comparator;<br>
 * - binary search, also needs comparator;<br>
 * - iterator, so you can use foreach cycle to iterate on this collection.*/
public class FixedListOfElements<T> implements Iterable<T> {

    private Object[] elements;
    private int entriesCount = 0;
    private static final int DEFAULT_CAPACITY = 10;
    public static final int MAX_CAPACITY = 10_000;
/** Initialization can be done as follows:<br>
 * - without specifying any arguments, in this case the number of elements is 10;<br>
 * - specifying the maximum number of array elements as a number;<br>
 * - when specifying various number of arguments or by an array of objects;<br>
 * - when specifying another {@link FixedListOfElements}, in this case all values of the specified list are copied, and the
 * maximum size of the current list becomes equal to the current size of the copied list */
    public FixedListOfElements() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    public FixedListOfElements(int capacity) {
        this.elements = new Object[capacity];
    }

    @SuppressWarnings("unchecked")
    public FixedListOfElements(T ... elements) {
        Object[] newarr = new Object[elements.length + 8];
        System.arraycopy(elements, 0, newarr, 0, elements.length);
        this.elements = newarr;
        this.entriesCount = elements.length;
    }

    public FixedListOfElements(FixedListOfElements<T> fixArr) {
        this.elements = Arrays.copyOf(fixArr.elements, fixArr.elements.length);
        this.entriesCount = fixArr.entriesCount;
    }

    public void changeCapacity(int capacity){
        if(capacity >= entriesCount){
            Object[] newarr = new Object[capacity];
            System.arraycopy(elements, 0, newarr, 0, entriesCount);
            this.elements = newarr;
        }
        else{
            throw new IllegalArgumentException("capacity can't be less than current entries count");
        }
    }

    @SuppressWarnings("unchecked")
    public T getByPosition(int position) {
        if (position >= 0 && position < entriesCount) {
            return (T) this.elements[position];
        } else {
            throw new IllegalArgumentException("wrong index value");
        }
    }

    public void replaceByPosition(T value, int position) {
        if (position >= 0 && position < entriesCount) {
            Object[] newarr = new Object[elements.length];
            System.arraycopy(elements, 0, newarr, 0, position);
            newarr[position] = value;
            System.arraycopy(elements, position + 1, newarr, position + 1,
                    elements.length - position - 1);
            this.elements = newarr;

        } else {
            throw new IllegalArgumentException("wrong index value");
        }
    }

    public boolean isEmpty() {
        return elements.length == 0;
    }

    public int showLength() {
        return elements.length;
    }

    public int showValuesCount() {
        return this.entriesCount;
    }

    public int showAvailableCount(){
        return elements.length - entriesCount;
    }

    public boolean checkIfAbleToAdd() {
        return this.elements.length > this.entriesCount;
    }

    @SuppressWarnings("unchecked")
    public T[] getStandardArray(Class<T> classItself) {
        if (entriesCount <= elements.length) {
            T[] arr = (T[]) Array.newInstance(classItself, entriesCount);
            System.arraycopy(elements, 0, arr, 0, entriesCount);
            return arr;
        } else {
            throw new IllegalArgumentException("incorrect argument entriesCount <= elements.length");
        }
    }

    public void clear(){
        this.elements = new Object[elements.length];
        this.entriesCount = 0;
    }

    public void addInTail(T value) {
        if (entriesCount != elements.length) {
            this.elements[entriesCount] = value;
            entriesCount++;
        }
    }

    public void addInTail(T[] values) {
        if (entriesCount != elements.length && values.length <= (elements.length - entriesCount)) {
            for (T value : values) {
                elements[entriesCount] = value;
                entriesCount++;
            }
        }
    }

    public void addInTail(FixedListOfElements<T> fixarr) {
        if (this.entriesCount != this.elements.length && fixarr.entriesCount <= (this.elements.length - this.entriesCount)) {
            for (int i = 0; i < fixarr.entriesCount; i++) {
            this.elements[entriesCount] = fixarr.elements[i];
            entriesCount++;
            }
        }
    }

    public void addInAnyPlace(T value, int index) {
        if (entriesCount != elements.length) {
            if (index >= 0 && index < this.entriesCount) {
                Object[] newarr = new Object[elements.length];
                System.arraycopy(elements, 0, newarr, 0, index);
                newarr[index] = value;
                System.arraycopy(elements, index, newarr, index + 1, elements.length - index - 1);
                this.elements = newarr;
                entriesCount++;

            } else {
                throw new IllegalArgumentException("wrong index value");
            }
        }
    }

    public void addInAnyPlace(T[] values, int index) {
        if (entriesCount != elements.length) {
            if (index >= 0 && index < this.entriesCount && values.length <= (elements.length - entriesCount)) {
                Object[] newarr = new Object[elements.length];
                System.arraycopy(elements, 0, newarr, 0, index);
                int tmpValCount = entriesCount;
                int ind = index;
                for (int i = 0; i < values.length; i++) {
                    newarr[ind] = values[i];
                    entriesCount++;
                    ind++;
                }
                System.arraycopy(elements, index, newarr, index + values.length, tmpValCount - index);
                this.elements = newarr;

            } else {
                throw new IllegalArgumentException("wrong index value or too long array trying to add");
            }
        }
    }

    public void addInAnyPlace(FixedListOfElements<T> values, int index) {
        if (entriesCount != elements.length) {
            if (index >= 0 && index < this.entriesCount && values.entriesCount <= (elements.length - entriesCount)) {
                Object[] newarr = new Object[elements.length];
                System.arraycopy(elements, 0, newarr, 0, index);
                int tmpValCount = entriesCount;
                int ind = index;
                for (int i = 0; i < values.entriesCount; i++) {
                    newarr[ind] = values.elements[i];
                    entriesCount++;
                    ind++;
                }
                System.arraycopy(elements, index, newarr, index + values.entriesCount, tmpValCount - index);
                this.elements = newarr;

            } else {
                throw new IllegalArgumentException("wrong index value");
            }
        }
    }

    public void removeAtIndex(int index) {
        if (index >= 0 && index < this.entriesCount) {
            Object[] newarr = new Object[elements.length];
            System.arraycopy(elements, 0, newarr, 0, index);
            System.arraycopy(elements, index + 1, newarr, index, elements.length - index - 1);
            this.elements = newarr;
            entriesCount--;
        } else {
            throw new IllegalArgumentException("wrong index value");
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

    @SuppressWarnings("unchecked")
    public void sort(Comparator<T> compar) {
        Arrays.sort((T[]) elements, 0, entriesCount, compar);
    }

    @SuppressWarnings("unchecked")
    public int binarySearch(T value, Comparator<T> compar) {
        if (elements == null || entriesCount == 0) {
            return -1;
        }
        this.sort(compar); //binary search needs sorted array
        int low = 0;
        int high = entriesCount - 1;

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
            return currentIndex + 1 < entriesCount;
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

    public static <T> FixedListOfElements<T> createCustomCol() {
        return new FixedListOfElements<>();
    }

    public static <T> FixedListOfElements<T> createMyCustomCol(T[] elements) {
        return new FixedListOfElements<>(elements);
    }

    public int countOccurrencesParallel(T element, int threadCount) {
        int elementCount = 0;

        if (threadCount <= 1) {
            return countOccurrencesSequential(element);
        }

        if(threadCount >= Runtime.getRuntime().availableProcessors() - 1)
            threadCount = Runtime.getRuntime().availableProcessors() - 1;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        int chunkSize = Math.max(1, entriesCount / threadCount);

        List<Future<Integer>> futures = new ArrayList<>();
        for (int thread = 0; thread < threadCount; thread++) {
            int startIndex = thread * chunkSize;
            if (startIndex >= entriesCount) {
                break;
            }
            int endIndex = (thread == threadCount - 1) ? entriesCount : Math.min((thread + 1) * chunkSize, entriesCount);
            Callable<Integer> task = getTask(element, startIndex, endIndex);
            futures.add(executorService.submit(task));
        }

        for (Future<Integer> future : futures) {
            try {
                elementCount += future.get();
            } catch (ExecutionException | InterruptedException e) {
                throw new IllegalStateException("Ошибка потока: " + e.getMessage());
            }
        }

        executorService.shutdown();

        return elementCount;
    }

    public int countOccurrencesSequential(T element) {
        int n = 0;
        for (int i = 0; i < entriesCount; i++) {
            if (Objects.equals(elements[i], element)) {
                n++;
            }
        }
        return n;
    }

    private Callable<Integer> getTask(T element, int startIndex, int endIndex) {
        return () -> {
            int n = 0;
            for (int j = startIndex; j < endIndex; j++) {
                if (Objects.equals(elements[j], element)) {
                    n++;
                }
            }
            return n;
        };
    }
}
