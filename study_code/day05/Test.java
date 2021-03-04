import java.util.Scanner;

public class Test {
    {
        System.out.println("Hello Wolrd1");
    }
    static {
        System.out.println("Hello world");
    }
    public static void main(String[] args) {
        char[] ch1=new char[]{'a','b','c'};
        char[] ch2=new char[]{'a','b','c'};
        System.out.println(ch1==ch2);
        int n=1;
        Integer nn=new Integer(n);//基本转包装
        int m=nn.intValue();//包装转基本

        String str=nn.toString();//包装转String
        Integer dd=new Integer(str);
        
        
        int n3=Integer.parseInt(str);//string转基本，NumberFormatException
        String str2=String.valueOf(111);//基本转string;111+""

        boolean b=true;
        System.out.println();
        // System.out.print("请输入年月日，返回这天是打鱼还是晒网(格式：年 月 日)：");
        // Scanner sc = new Scanner(System.in);
        // int year = sc.nextInt();
        // int month = sc.nextInt();
        // int day = sc.nextInt();
        // int days = 0;
        // for (int i = 1990; i < year; i++) {
        //     days += year % 400 == 0 || (year % 4 == 0 && year % 100 != 0) ? 366 : 365;
        // }
        // days += findDateLocate(year, month, day);
        // if (days % 5 == 1 || days % 5 == 2 || days % 5 == 3) {
        //     System.out.println("今天打鱼");
        // }
        // if (days % 5 == 4 || days % 5 == 0) {
        //     System.out.println("今天晒网");
        // }
        // sc.close();
    }

    /**
     * 返回一个日期是当年的第几天，如2020年1月1日，则第1天；2020年12月31日，则第366天
     * @param year  年份
     * @param month 月份
     * @param day   日
     */
    public static int findDateLocate(int year, int month, int day) {
        int days = day;
        int[] monthValues = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        for (int i = 0; i < month - 1; i++) {
            days += monthValues[i];
            if (i == 1 && (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))) {
                days++;
            }
        }
        // System.out.println("当年的第"+days+"天");
        return days;
    }
}
