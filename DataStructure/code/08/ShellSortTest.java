public class ShellSortTest {
    public static void main(String[] args) {
        int[] arr = { 55, 12, 7, 3, 12, 6, 9, 4, 1, 3, 5 };
        shellSort(arr);
        printShellSort(arr);
    }

    public static void shellSort(int[] arr) {
        for (int step = arr.length / 2; step > 0; step /= 2) {// 增量step
            if(step!=-1){
            for (int i = step; i < arr.length; i++) {// 对每个子序列进行直接插入排序
                int value = arr[i];
                int j;
                for (j = i - step; j >= 0 && arr[j] > value; j -= step) {
                    arr[j + step] = arr[j];
                }
                arr[j + step] = value;
            }}
        }
    }

    public static void printShellSort(int[] arr) {
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}