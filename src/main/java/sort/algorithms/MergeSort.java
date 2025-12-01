package sort.algorithms;

public class MergeSort {

    public static void mergeSort(int[] arr, int left, int right) {

        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {

        int length1 = mid - left + 1;
        int length2 = right - mid;
        int[] leftSubArray = new int[length1];
        int[] rightSubArray = new int[length2];
        System.arraycopy(arr, left, leftSubArray, 0, length1);
        System.arraycopy(arr, mid + 1, rightSubArray, 0, length2);

        int i = 0, j = 0;
        int k = left;
        while (i < length1 && j < length2) {
            if (leftSubArray[i] <= rightSubArray[j]) {
                arr[k] = leftSubArray[i];
                i++;
            } else {
                arr[k] = rightSubArray[j];
                j++;
            }
            k++;
        }

        while (i < length1) {
            arr[k] = leftSubArray[i];
            i++;
            k++;
        }

        while (j < length2) {
            arr[k] = rightSubArray[j];
            j++;
            k++;
        }
    }

//    Test
//    public static void main(String[] args) {
//        int[] array = {12, 11, 13, 5, 6, 7};
//        mergeSort(array, 0, array.length - 1);
//        System.out.println("Отсортированный массив: ");
//        for (int num : array) {
//            System.out.print(num + " ");
//        }
//    }

}
