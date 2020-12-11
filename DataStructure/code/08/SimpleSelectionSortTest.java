public class SimpleSelectionSortTest {
    public static void main(String[] args) {
        int[] arr = { 49, 38, 65, 97, 76, 13, 27, 49 };
        simpleSelectionSort(arr);
        printSimpleSelectionSort(arr);
    }
    /**
     * 简单选择排序
     * 思路：
     * 选择类排序的主要动作是“选择”,简单选择排序采用最简单的选择方式，
     * 从头至尾顺序扫描序列，找出最小的关键字(把最小关键字的数组下标index放在一个临时变量中)，
     * 然后最小的元素和第一个元素交换(整个扫描过程结束后再交换，所以一趟选择排序，只交换了一次元素)，
     * 接着从剩下的关键字中继续这种选择和交换，最终使序列有序
     */
    public static void simpleSelectionSort(int arr[]) {
        for (int i = 0; i < arr.length; i++) {
            int k = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[k] > arr[j]) {
                    k = j;
                }
            }
            if (i != k) {
                int temp = arr[i];
                arr[i] = arr[k];
                arr[k] = temp;
            }
        }
    }

    /**
     * 打印数组经过快速排序后结果
     */
    public static void printSimpleSelectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
