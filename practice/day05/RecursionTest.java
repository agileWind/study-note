public class RecursionTest {
    public static int count = 1;

    public static void main(String[] args) {
        //hanoi(5, 'x', 'y', 'z');
        System.out.println(getFibonacciSequence(10));
    }

    /**
     * 汉诺塔问题：n个圆环从大到小依次叠放串在x柱子上，现在需要从x柱子上将圆环移到z柱子上，可以用y柱子作为辅助。
     * 要求：移动过程中，不管是哪个柱子上，小圆环一定不能在大圆环下面
     *
     * 思路：先把上面n-1个圆环移动到y柱子上 然后移动第n个圆环到z柱子上 最后把y柱子上的n-1个圆环移动到z柱子上
     *
     * @param n              表示几个圆环，排序是按照从上到下，从小到大排的，最上面的圆环编号为1，最下面的圆环编号为n
     * @param origination    表示目前待移动的n个圆环所在的柱子名称
     * @param intermediation 表示移动过程中辅助的柱子名称
     * @param destination    表示目前待移动的n个圆环需要移动到的柱子名称
     */
    public static void hanoi(int n, char origination, char intermediation, char destination) {
        if (n == 1) {
            move(origination, 1, destination);
        } else {
            hanoi(n - 1, origination, destination, intermediation);
            move(origination, n, destination);
            hanoi(n - 1, intermediation, origination, destination);
        }
    }

    /**
     * 汉诺塔问题中，提供移动的函数
     *
     * @param origination  表示需要移动的圆环现在所在的柱子名称
     * @param serialNumber 表示待移动的圆环的编号
     * @param destination  表示圆环需要移动到的柱子名称
     */
    public static void move(char origination, int serialNumber, char destination) {
        System.out.printf("第%d步：将编号为%d的圆环从柱子%c移动到柱子%c\n", count++, serialNumber, origination, destination);
    }

    /**
     * 求斐波那契数列的第n项
     * @param n 数列的第n项
     */
    public static int getFibonacciSequence(int n){
        if(n==1||n==2){
            return 1;
        }else if(n>=3){
            return getFibonacciSequence(n-1)+getFibonacciSequence(n-2);
        }else{
            return 0;
        }
    }
}