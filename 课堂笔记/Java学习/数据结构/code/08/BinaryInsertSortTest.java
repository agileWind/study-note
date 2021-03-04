public class BinaryInsertSortTest {
    public static void main(String[] args) {
        int[] arr = { 49, 38, 65, 97, 76, 13, 27, 49 };
        binaryInsertSort(arr);
        printBinaryInsertSort(arr);
    }

    public static void binaryInsertSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int low = 0;
            int high = i - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                if (arr[mid] > arr[i]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            int j = i - 1;// [high+1,i-1]之间的元素全部后移
            int temp = arr[i];
            while (j > high) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[high + 1] = temp;
        }
    }

    public static void printBinaryInsertSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}