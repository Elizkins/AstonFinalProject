package sort.algorithms;

public class QuickSort {



    public static void quickSort(int[] arr, int low, int high) {

        // Определение базового случая и условия выхода из рекурсии
        if (low < high) {
            int partitionInd = partition(arr, low, high);
            quickSort(arr, low, partitionInd - 1);
            quickSort(arr, partitionInd + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {

        // Худший случай, опорный элемент последний, либо первый.
        // int pivot = arr[high];

        //средний случай, выбор опорного элемента из середины - сократим существенно стек вызовов
        int middle = low + (high - low) / 2;
        int pivot = arr[middle];

        //обмен опоброго элемента с последним
        int tmp = arr[middle];
        arr[middle] = arr[high];
        arr[high] = tmp;

        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

//    Test
//    public static void main(String[] args) {
//        int[] array = {12, 11, 13, 5, 6, 7};
//        quickSort(array, 0, array.length - 1);
//        System.out.println("Отсортированный массив: ");
//        for (int num : array) {
//            System.out.print(num + " ");
//        }
//    }
}
