import java.util.Scanner;

public class FlowControl {
    public static void main(String[] args) {
        //Exam0();
        // Exam1();
        // Exam2();
        // Exam3();
         //Exam4();
        // Exam5();
        // Exam6();
        // Exam7();
        // Exam8();
        // Exam9();
        // Exam10();
         Exam11();
        // Exam12();
        //Exam111();
    }
    public static void Exam111(){
        for (int i = 1; i <=9 ; i++) {
            for (int k = 1; k <=9-i ; k++) {
                System.out.print("\t");
            }
            for (int j = 1; j <=i ; j++) {
                System.out.print(i+"*"+j+"="+i*j+"\t");
            }
            System.out.println();
        }
    }
    public static void Exam0(){
        Scanner sc=new Scanner(System.in);
        System.out.print("请输入一个整数(以0结束)：");
        int num=sc.nextInt();
        int positiveNumber=0;
        int negativeNumber=0;
        for(;num!=0;num=sc.nextInt()){
            if(num>0)
                positiveNumber++;
            else
                negativeNumber++;
            System.out.print("请输入一个整数(以0结束)：");
        }
        sc.close();
        System.out.println("正数个数："+positiveNumber);
        System.out.println("负数个数："+negativeNumber);
    }

    // 第1题：求1-100的和
    public static void Exam1() {
        // int maxNum = 100;
        // System.out.println((maxNum + 1) * maxNum / 2);

        int sum=0;
        for(int i=1;i<101;i++){
            sum+=i;
        }
        System.out.println(sum);
    }

    // 第2题：打印1-100的偶数
    public static void Exam2() {
        String printStr = "";
        int maxNum = 100;
        for (int i = 1; i <= maxNum; i++) {
            printStr += i % 2 == 0 ? i + "\t" : "";// 每行中的数字间隔一个制表符
            if (i % 10 == 0) {// 每五个就换行
                printStr += "\n";
            }
        }
        System.out.println(printStr);
    }

    // 随机生成一个100以内的正整数，猜数字游戏
    public static void Exam4() {
        // 100以内正整数：[1，100)
        Scanner sc = new Scanner(System.in);
        int randomNum = (int) (Math.random() * 99 + 1);
        int count=0;
        while (true) {
            System.out.print("请输入数字：");
            int inputNum = sc.nextInt();
            count++;
            if (inputNum > randomNum) {
                System.out.println("猜大了！");
            } else if (inputNum < randomNum) {
                System.out.println("猜小了！");
            } else {
                System.out.println("猜对了！您猜了"+count+"次");
                sc.close();
                break;
            }
        }
    }

    public static void Exam8(){
        for(int i=1;i<=100;i++){
            if(i%3==0||i%5==0||i%7==0){
                continue;
            }else{
                System.out.print(i+" ");
            }
        }
    }

    // 5.输出所有的水仙花数，所谓水仙花数是指一个3位数，其各个位上数字立方和等于其本身
    public static void Exam5() {
        String resultStr = "所有水仙花数：";
        for (int i = 100; i < 1000; i++) {
            int bai = i / 100;
            int shi = (i % 100) / 10;
            int ge = i % 10;
            if (i == bai * bai * bai + shi * shi * shi + ge * ge * ge) {
                resultStr += i + " ";
            }
        }
        System.out.println(resultStr);
    }

    /**
     * 6.从1循环到150并在每行打印一个值， 另外在每个3的倍数行上打印出“foo”,在每个5的倍数行上打印“biz”,
     * 在每个7的倍数行上打印输出“baz”。
     */
    public static void Exam6() {
        String resultStr = "";
        for (int i = 1; i < 151; i++) {
            resultStr += i;
            if (i % 3 == 0) {
                resultStr += "\tfoo";
            }
            if (i % 5 == 0) {
                resultStr += "\tbiz";
            }
            if (i % 7 == 0) {
                resultStr += "\tbaz";
            }
            resultStr += "\n";
        }
        System.out.println(resultStr);
    }

    // 7、输入两个正整数m和n，求其最大公约数和最小公倍数
    /*
     * 辗转相除法,求最大公约数 377,319 58=377%319 319%58=29 58%29=0 最大公约数是29
     * 最小公倍数：由于两个数的乘积等于这两个数的最大公约数与最小公倍数的积
     */
    public static void Exam7() {
        System.out.print("请输入两个正整数(格式：m n)：");
        Scanner sc = new Scanner(System.in);
        int num1 = sc.nextInt();
        int num2 = sc.nextInt();
        sc.close();
        int greatestCommonDivisor = getGreatestCommonDivisor(num1, num2);
        System.out.println(num1 + "和" + num2 + "的最大公约数为:" + greatestCommonDivisor);
        System.out.println("最小公倍数为：" + num1 * num2 / greatestCommonDivisor);
    }

    public static int getGreatestCommonDivisor(int num1, int num2) {
        if (num1 == num2) {
            return num1;
        }
        if (num1 < num2) {
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }
        int remainder;
        do {
            remainder = num1 % num2;
            num1 = num2;
            num2 = remainder;
        } while (num2 != 0);
        return num1;
    }

    /*
     * 9.找到1000以内所有完数。ps:一个数如果恰好等于它的因子之和，这个数就称为"完数"。（因子：除去这个数本身的约数）
     */
    public static void Exam9() {
        for (int i = 2; i <= 1000; i++) {
            int sum = 1;
            for (int j = i - 1; j > 1; j--) {
                if (i % j == 0) {
                    sum += j;
                }
            }
            if (sum == i) {
                System.out.print(i + " ");
            }
        }
    }

    /**
     * 九九乘法表
     *
     */
    public static void Exam10() {
        String resultStr = "";
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j <= i; j++) {
                resultStr += j + "*" + i + "=" + j * i + "\t";
                if (j == i) {
                    resultStr += "\n";
                }
            }
        }
        System.out.println(resultStr);

        resultStr = "";
        for (int i = 1; i < 10; i++) {
            for (int k = 9; k > i; k--) {
                resultStr += "\t";
            }
            for (int j = 1; j <= i; j++) {
                resultStr += i + "*" + j + "=" + i * j + "\t";
                if (j == i) {
                    resultStr += "\n";
                }
            }
        }
        System.out.println(resultStr);
    }

    /**
     * 打印菱形
     *
     */
    public static void Exam11() {
        String resultStr = "";
        for (int i = 1; i <= 5; i++) {
            for (int k = 5; k > i; k--) {
                resultStr += "  ";
            }
            for (int j = 1; j < 2 * i; j++) {
                resultStr += "* ";
            }
            resultStr += "\n";
        }
        for (int i = 4; i >= 1; i--) {
            for (int k = 5; k > i; k--) {
                resultStr += "  ";
            }
            for (int j = 1; j < 2 * i; j++) {
                resultStr += "* ";
            }
            resultStr += "\n";
        }
        // 以上是棱形

        // 以下是空棱形
        resultStr += "\n";
        for (int i = 1; i <= 5; i++) {
            for (int k = 5; k > i; k--) {
                resultStr += "  ";
            }
            for (int j = 1; j < 2 * i; j++) {
                resultStr += j == 1 || j == 2 * i - 1 ? "* " : "  ";// 修改此处
            }
            resultStr += "\n";
        }
        for (int i = 4; i >= 1; i--) {
            for (int k = 5; k > i; k--) {
                resultStr += "  ";
            }
            for (int j = 1; j < 2 * i; j++) {
                resultStr += j == 1 || j == 2 * i - 1 ? "* " : "  ";// 修改此处
            }
            resultStr += "\n";
        }

        System.out.println(resultStr);
    }

    /**
     * 找出1-100之间所有的素数（质数:只能被自己和1整除的数字）
     *
     */
    public static void Exam12() {
        String resultStr = "";
        for (int i = 2; i <= 100; i++) {
            boolean isPrimeNumber = true;
            for (int j = 2; j <= i; j++) {
                if (i % j == 0 && i != j) {
                    isPrimeNumber = false;
                    break;
                }
            }
            if (isPrimeNumber) {
                resultStr += i + " ";
            }
        }
        System.out.println("[1,100]以内的所有质数：" + resultStr);
    }
}
