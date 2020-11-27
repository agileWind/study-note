public class OOPTest {
    public static void main(String[] args){
        Circle circle=new Circle();
        circle.radius=2;
        System.out.println("面积："+circle.getArea());

        Employee employee=new Employee();
        employee.name="Tom";
        MyDate myDate=new MyDate();
        employee.myDate=myDate;
        myDate.year=1998;
        myDate.month=10;
        myDate.day=21;
        employee.showPersonInfo();

        Employee employee2=new Employee();
        MyDate myDate2=new MyDate();
        employee2.myDate=myDate2;
        employee2.name="Jack";
        myDate2.year=1992;
        myDate2.month=9;
        myDate2.day=12;
        employee2.showPersonInfo();

        BankAccount bankAccount=new BankAccount();
        bankAccount.balance=19990000;
        System.out.println("银行余额："+bankAccount.balance);
    }
}

class MyDate{
    public int year;
    public int month;
    public int day;
}

class Employee{
    public String name;
    public MyDate myDate;

    public void showPersonInfo(){
        System.out.printf("姓名：%s\n生日：%d年%d月%d日\n",name,myDate.year,myDate.month,myDate.day);
    }
}

class Circle{
    public double radius;

    public double getArea(){
        return Math.PI*radius*radius;
    }
}

class BankAccount{
   final static double RETE_OF_INTEREST=0.035;
   public double balance;
}