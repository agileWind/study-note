public class QuickSortTest {
    public static void main(String[] args) {
        int[] a = { 49, 38, 65, 97, 76, 13, 27, 49 };
        quickSort(a, 0, a.length - 1);
        printArrByQuickSort(a);
    }

    /**
     * 快速排序算法
     * 思路：
     * 1.这里用到了两个标记指针，分别在序列的首位和末尾，然后选一个元素作为枢纽(选第一个元素)
     * 2.向前移动标记j，直到遇到a[j]<temp时停止，并将该a[j]值赋给a[i++]
     * 3.现在向后移动标记i，直到遇到a[i]>temp时停止，并将该a[i]的值赋给a[j++]
     * 4.重复第二步
     * 直到遇到i==j时停止移动标记指针，并将temp赋给a[i]
     * 现在整个序列以枢纽为分割，被分成两个子序列，将这两个序列再次执行此算法步骤
     */
    public static void quickSort(int[] a, int left, int right) {
        int i = left, j = right;
        if (i < j) {
            int temp = a[i];
            while (i < j) {
                while (i < j && a[j] >= temp) {//想要从大到小的话，这里改成a[j] <= temp
                    --j;
                }
                if (i < j) {//这里必须判断i，j大小，因为有可能中途i和j相遇
                    a[i] = a[j];
                    ++i;
                }
                while (i < j && a[i] <= temp) {//想要从大到小的话，这里改成a[i] >= temp
                    ++i;
                }
                if (i < j) {//这里必须判断i，j大小，因为有可能中途i和j相遇
                    a[j] = a[i];
                    --j;
                }
            }
            a[i] = temp;
            quickSort(a, left, i - 1);
            quickSort(a, i + 1, right);
        }
    }

    /**
     * 打印数组经过快速排序后结果
     */
    public static void printArrByQuickSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }
}
