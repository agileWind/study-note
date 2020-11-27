import java.util.Scanner;

/*
回形数,提示：递归
先使用递归来给二维数组赋值，然后再打印
1  2  3  4  5
16 17 18 19 6
15 24 25 20 7
14 23 22 21 8
13 12 11 10 9

*/
public class Pattern {
    static int[][] numArr;
    static int col;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入回形数的行数：");
        col = sc.nextInt();
        numArr = new int[col][col];
        numArr[0][0] = 1;
        visit(1, 0, 1);
        print();
        sc.close();
    }
    /**
     * @param startNum 上一个打印的值
     * @param m 下一个需要打印值的位置的横坐标
     * @param n 下一个需要打印值的位置的纵坐标
     */
    public static void visit(int startNum, int m, int n) {
        // 向右
        for (; n < col && numArr[m][n] == 0; n++) {
            numArr[m][n] = ++startNum;
        }
        if (startNum == col * col) {
            return;
        }
        n--;
        m++;

        // 向下
        for (; m < col && numArr[m][n] == 0; m++) {
            numArr[m][n] = ++startNum;
        }
        if (startNum == col * col) {
            return;
        }
        m--;
        n--;

        // 向左
        for (; n >= 0 && numArr[m][n] == 0; n--) {
            numArr[m][n] = ++startNum;
        }
        if (startNum == col * col) {
            return;
        }
        n++;
        m--;

        // 向上
        for (; m > 0 && numArr[m][n] == 0; m--) {
            numArr[m][n] = ++startNum;
        }
        if (startNum == col * col) {
            return;
        }
        m++;

        visit(startNum, m, n + 1);
    }

    public static void print() {
        for (int i = 0; i < numArr.length; i++) {
            for (int j = 0; j < numArr[i].length; j++) {
                System.out.print(numArr[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
