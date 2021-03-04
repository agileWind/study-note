public class TestLeapYear {
    public static void main(String[] args){
        int year=1994;
        boolean result=year%400==0||(year%4==0&&year%100!=0)? true:false;
        System.out.println(result);
    }
}
