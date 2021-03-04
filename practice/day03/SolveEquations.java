import java.util.Scanner;

public class SolveEquations{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入ax^2+bx+c=0方程中的a,b,c");
        System.out.println("请输入a:");
        double a=sc.nextDouble();
        System.out.println("请输入b:");
        double b=sc.nextDouble();
        System.out.println("请输入c:");
        double c=sc.nextDouble();
        System.out.printf("方程%fx^2+%fbx+%f=0的解是：",a,b,c);
        calc(a,b,c);
    }

    public static void calc(double a,double b,double c){
        if(a!=0){
            //分三种情况
            if(b*b-4*a*c>0){
                //两个解
                System.out.print("x1="+((-b)+Math.sqrt(b*b-4*a*c))/(2*a));
                System.out.print("x2="+((-b)-Math.sqrt(b*b-4*a*c))/(2*a));
            }else if(b*b-4*a*c==0){
                //一个解
                System.out.print("x1=x2="+-(b/(2*a)));
            }else{
                //无解
                System.out.println("方程无解");
            }
        }else if(a==0&&b!=0){
            //一个解
            System.out.println("x="+-(c/b));
        }else if(a==0&&b==0&&c==0){
            //无穷任意解
            System.out.println("有无穷任意解");
        }else{
            //无解
            System.out.println("无解");
        }
    }

}

/*
Math.random可以生成[0,1)范围的数

问：要生成[1,6]之间的整数，怎么写
(int)(Math.random*6+1)
*/