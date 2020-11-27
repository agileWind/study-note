/**
 * 直接插入排序算法：从小到大
 */
public class DirectInsertSortTest {
    public static void main(String[] args) {
        int[] a = { 49, 38, 65, 97, 76, 13, 27, 49 };
        directInsertSort(a);
        printArrByDirectInsertSort(a);
    }

    /**
     * 直接插入排序算法
     * 思路：第一个数默认是有序的，然后第二个数与第一个数进行比较，第二个数比第一个数小的话，
     * 那就把第一个数移动到后面，然后第二个数放在第一个数原先的位置；如果第二个数比第一个数大的话，第二个数位置不需要移动
     * 以此类推，第三个数，就需要和前两个已经排好顺序的数字进行对比，来查找出第三个数字应该插入的位置...
     */
    public static void directInsertSort(int a[]) {
        int j, temp;
        for (int i = 1; i < a.length; i++) {
            temp = a[i];
            j = i - 1;
            while (j >= 0 && temp < a[j]) {// 如果需要从大到小，该行修改为temp>a[j]
                a[j + 1] = a[j];
                --j;
            }
            a[j + 1] = temp;//找到了插入的位置，将temp中暂存的待排关键字插入
        }
    }

    /**
     * 打印数组经过直接插入排序后结果
     */
    public static void printArrByDirectInsertSort(int a[]) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }
}
