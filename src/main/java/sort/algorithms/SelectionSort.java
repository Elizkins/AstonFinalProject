package sort.algorithms;


import java.util.Arrays;

public class SelectionSort {
    public static void SelSort(int[] someArr) {
        for(int i = 0; i < someArr.length; i++){
            int minIndex = i;
            for(int j =  i + 1; j < someArr.length; j++) {
                if (someArr[j] < someArr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = someArr[minIndex];
            someArr[minIndex] = someArr[i];
            someArr[i] = temp;
        }

    }
//    Test
//    public static void main(String[] args) {
//        int[] arr1 = new int[] {3, 5, 2, 8, 5, 2, 0};
//        SelectionSort.SelSort(arr1);
//        System.out.println(Arrays.toString(arr1));
//
//    }
}
