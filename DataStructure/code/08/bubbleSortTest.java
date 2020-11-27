public class bubbleSortTest {
    public static void main(String[] args) {
        int[] a = { 49, 38, 65, 97, 76, 13, 27, 49 };
        bubbleSort(a);
        printArrByBubbleSort(a);
    }

    /**
     * 冒泡排序算法
     * 思路：外层的循环次数和数组长度相同，多少个关键字就是要循环多少次，也就是执行多少趟次的冒泡排序
     * 第一趟，i=length;内层循环，都是从j=[1,i-1],然后依次拿a[j]和a[j-1]进行比较和交换:a[1]和a[0]....a[length-1]和a[length-2]。第一趟后最大的数沉到了a[length-1]
     * 第二趟（上一趟找到的最大数字不再参与排序了），i=length-1；内层循环：a[1]和a[0]...a[i-1]和a[i-2]。。。
     * 注意：isOrder可以获取到，某次的冒泡排序，没有进行数字交换，也就是说，这趟的序列已经是从小到大排序了，那外层循环也不需要继续进行了
     */
    public static void bubbleSort(int[] a) {
        int temp;
        for (int i = a.length; i > 0; i--) {
            boolean isOrder = true;
            for (int j = 1; j < i; j++) {
                if (a[j - 1] > a[j]) {//如果需要从大到小，这里改成a[j - 1] < a[j]
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                    isOrder = false;
                }
            }
            if (isOrder) {
                return;
            }
        }
    }

    /**
     * 打印数组经过冒泡排序后结果
     */
    public static void printArrByBubbleSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }
}
