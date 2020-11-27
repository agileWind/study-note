class Person {

    public String name;

    public int age;

    public String language;

    public void eat(){
        System.out.println("正在吃饭...");
    }

    public void sleep(){
        System.out.println("正在睡觉...");
    }

    public void speak(){
        System.out.println("会讲的语言是:"+this.language);
    }

    public void hello(){
        System.out.println("hello");
    }
}

public class PersonArrTest{
    public static void main(String[] args){
        // Person[] persons=new Person[5];//这里只是在栈里申请了一个变量，变量指向了5个内存空间在堆中的首地址，但是这五个地址空间里全部是null
        // persons[0]=new Person();//数组的第一个空间指向了一个对象在内存的首地址
        // persons[0].name="Jack";
        // persons[0].age=16;
        //Arrays.sort(1,2,3);
    }
}

class PersonTest {
    public static void main(String[] args){
        Person person1=new Person();
        person1.name="Tom";
        person1.age=20;
        person1.language="Chinese";
        System.out.println("姓名:"+person1.name+"；年龄:"+person1.age+"；语言:"+person1.language);
        person1.eat();
        person1.sleep();
        person1.speak();

        Person person2=new Person();
        System.out.println("person2.name:"+person2.name);
        Person person3=person1;
        System.out.println("person3.name:"+person3.name);

        person1.name="Jack";
        System.out.println("person3.name:"+person3.name);
        
    }
}


class Arrays{
    //这两个就是方法重载，只要形参不是位置和类型一一对应且完全一致就可以重载
    // public static void sort(int a,int length){
    //     System.out.println("ddddd");
    // }
    public static void sort(int length,int ... a){}
    //以下也是重载
    public static void sort(int ... a){}
    public static void sort(String a[],int length){}


    //以下是错误的
    // public static void sort(int b[],int len){}
    // public static int[] sort(int a[],int length){}
    // public void sort(int a[],int length){}
    // protected static void sort(int a[],int length){}
}