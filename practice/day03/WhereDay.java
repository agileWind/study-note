import java.util.Scanner;

public class WhereDay {
    public static void main(String[] args) {
        System.out.print("请输入年月日，返回这天是今年的第几天(格式：年 月 日)：");
        Scanner sc=new Scanner(System.in);
        int year=sc.nextInt();
        int month=sc.nextInt();
        int day=sc.nextInt();
        System.out.println(_findDateLocate(year,month,day));
        sc.close();
    }

    //这个更简洁
    public static int _findDateLocate(int year, int month, int day) {
        int days=day;
        switch(month){
            case 12:
                days+=30;
            case 11:
                days+=31;
            case 10:
                days+=30;
            case 9:
                days+=31;
            case 8:
                days+=31;
            case 7:
                days+=30;
            case 6:
                days+=31;
            case 5:
                days+=30;
            case 4:
                days+=31;
            case 3:
                days+=year % 400 == 0 || (year % 4 == 0 && year % 100 != 0) ? 29 : 28;
            case 2:
                days+=31;
        }
        return days;
    }

    public static int findDateLocate(int year, int month, int day) {
        int countDay = 0;
        for (int i = 1; i < month; i++) {
            countDay += getCountMonthDays(year, i);
        }
        return countDay + day;
    }

    public static int getCountMonthDays(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0) ? 29 : 28;
            default:
                return 0;
        }
    }
}
