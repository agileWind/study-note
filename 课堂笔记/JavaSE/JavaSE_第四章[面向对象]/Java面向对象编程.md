# 第四章 Java面向对象编程

[TOC]

## 4.1 认识面向对象

* 面向过程(POP)和面向对象(OOP)的区别
  1. 面向过程(Procedure Oriented Programming)，强调的是功能行为，以函数为最小的单位，考虑怎么做。
  2. 面向对象(Object Oriented Programming)，将功能封装进对象，强调具备了功能的对象，以类/对象为最小单位，考虑谁来做

  > 思考“人把大象装进冰箱”
    1.面向过程，强调的是功能行为，以函数为最小单位，考虑怎么做
        ①把冰箱门打开
        ②抬起大象，塞进冰箱
        ③把冰箱门关闭
    2.面向对象，强调具备了功能的对象，以类/对象为最小单位，考虑谁来做,可以如下考虑

```java
    人{
        打开(冰箱){
            冰箱.开门();
        }

        抬起(大象){
            大象.进入(冰箱);
        }

        关闭(冰箱){
            冰箱.闭合();
        }
    }

    冰箱{
        开门(){

        }

        闭合(){

        }
    }

    大象{
        进入(冰箱){

        }
    }
```

> 可以比喻一个公司在做一件事情，当参与的人并不多时，几个人凑一起解决即可，也不需要特别细致的安排和规划，或者任务分担。但是当事情特别庞大，参与的人达到几百上千，如果不进行安排和划分职责，想必事情进行的一定会比较混乱，效率也低，出问题了也不好追溯问责。
> 同样的，在计算机中，如果需要处理一个事务，当事情并不复杂时，面向过程编程即可，无需面向对象的思维，几个步骤就处理完毕了，但是假如是一个错综复杂，纷繁交错的事务，如果不进行细致的划分，是很难维护代码的，面向对象编程，可以更加清晰明确到底谁解决这个步骤

* 面向对象更符合人类在日常的思维逻辑中采用的思想方法和原则，如：抽象、分类、继承、聚合、多态等

* 面向对象的两个要素：
  1. 类：对一类事物的描述，是抽象的、概念上的定义
  2. 对象：是实际存在的该类事物的每个个体，因而也称为实例(instance)

* 拿人来说，可以理解为：类=抽象概念的人；对象=实实在在的某个人

* 面向对象程序设计的重点是**类的设计**

* 类的设计，其实就是类的**成员**的设计

* 面向对象的三大特征(后面会针对这些特征一一说明)：

  1. 封装(Encapsulation)
  2. 继承(Inheritance)
  3. 多态(Polymorphism)

## 4.2 类的属性和方法

### 4.2.1 Java类及类的成员

* 属性：对应类中的成员变量
* 行为：对应类中的成员方法

* Field=属性=成员变量，Method=(成员)方法=函数

* 创建类的对象=类的实例化=实例化类
* 类及类的成员：属性、方法、构造器、代码块、内部类

### 4.2.2 类和对象的使用

1. 创建类,设计类的成员
2. 创建类的对象
3. 通过`对象.属性`或者`对象.方法()`调用对象的结构

```java
class Person {

    public String name;

    public int age=1;

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

public class PersonTest {
    public static void main(String[] args){
        Person person1=new Person();
        person1.name="Tom";
        person1.age=20;
        person1.language="Chinese";
        System.out.println("姓名:"+person1.name+"；年龄:"+person1.age+"；语言:"+person1.language);
        person1.eat();
        person1.sleep();
        person1.speak();
    }
}

```

* 如果创建了一个类的多个对象，则每个对象都独立的拥有一套类的属性。(非static的)
  表明当修改一个类的对象的属性值时，另一个该类的对象的同名属性，其值并不会受影响

```java
public class PersonTest {
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
        System.out.println("person2.name:"+person2.name);//person2.name:null   说明：未赋值状态，初始化为null；person2与person1互不影响，在堆中不是同一个地址

        Person person3=person1;
        System.out.println("person3.name:"+person3.name);//person3.name:Tom
        /**
         * 因为person1,person3都是引用数据类型Person的实例化对象，栈中这两个变量都记录了指向的地址空间，
         * 也就是说person1指向的是地址空间,当把person1赋值给person3时，也就是说person3也指向了同一个地址空间，
         * 所以person1和person3的属性值是相同的，且修改其中一个，另一个也会受影响
         */


        person1.name="Jack";
        System.out.println("person3.name:"+person3.name);//person3.name:Jack
    }
}
```

* static修饰的变量为静态变量或者类变量，它的值是该类所有的实例对象共享的，值存储到方法区

* static 修饰的变量如果在类中没有初始化，随后在一个实例化对象A中进行了初始化赋值，那么这个类的其他实例化对象的该static属性的值也和对象A中的该属性的值相同，也就是说一个对象改变了static属性的值，那么其他对象的该属性值也会被改变

```java
public static String order="desc";

//使用该变量，不需要实例化，直接:类.静态变量
```

## 4.3 对象的内存解析

Java中的JVM虚拟机在执行Java程序过程中会把它所管理的内存划分为若干个不同的数据区域。各区域都有各自的用途，以及创建和销毁的时间，有的区域随着虚拟机进程的启动而存在，有些区域则是用来用户线程的启动和结束而建立和销毁。Java虚拟机所管理的内存将会包括以下几个运行区域：

![Java内存管理](../images/Java内存管理.png)

这里只介绍里面其中的几个区域和简要用途：

1. **堆(Heap)**,此内存区域的唯一目的就是**存放对象实例**，几乎所有的对象实例都在这里分配的内存。Java虚拟机的规范中描述是：所有的对象实例以及数组都要在堆上分配(含成员变量)
2. **栈(Stack)**，是指虚拟机栈，用于存储局部变量等。局部变量表存放了编译期可知长度的各种基本数据类型(boolean、byte、char、short、int、float、long、double)、对象引用(reference类型，它不等同于对象本身，存的是对象在堆内存的首地址)。方法执行完毕后，自动释放
3. **方法区(Method Area)**,用于存储已被虚拟机加载的**类信息、常量、静态变量、即时编译器编译后的代码**等

```java
Person p1=new Person();
p1.name="Tom";
p1.language="Chinese";
Person p2=new Person();
Person p3=p1;
p3.age=10;
```

以上代码运行时各变量在内存中开辟的空间图解如下：

![对象内存图解](../images/对象内存解析.png)

## 4.4 属性与局部变量

* 相同点：
  1. 定义变量的格式：数据类型 变量名 = 变量值;
  2. 先声明，后使用
  3. 变量都有其对应的作用域

* 不同点：
  1. 在类中声明的位置的不同
     * 属性：直接定义在类的一对`{}`内
     * 局部变量：声明在方法内、方法形参、代码块内、构造器形参、构造器内部的变量
  2. 关于权限修饰符的不同
     * 属性：可以在声明属性时，指明其权限，使用权限修饰符，常用权限修饰符(作用于类的封装)：`private`,`public`,`缺省default(不写)`,`protected`
     * 局部变量：不可以使用权限修饰符
  3. 默认初始化值的情况：
     * 属性：类的属性，根据其类型，都有默认初始化值
        * 整型(byte,short,int,long)：`0`
        * 浮点型(float,double)：`0.0`
        * 字符型(char)：`'\0'`或`'\u0000'`
        * 布尔型(boolean)：`false`
        * 引用数据类型(类，数组，接口)：`null`
     * 局部变量：没有默认初始化值
     说明，在调用局部变量之前，一定要显式赋值（方法形参在方法被调用时就传参赋值了）
  4. 在内存中加载的位置：
     * 属性：加载到堆空间中(非static的属性)
     * 局部变量：加载到栈空间

## 4.5 类的方法

* 方法：描述类应该具有的功能
* 比如：Math类中的random();Arrays类的sort(),binarySearch()...

* 举例

```java
public void eat(){}
public void sleep(int hour){}
public String getName(){}
public String getNation(String nation){}
```

* 方法声明的格式

```java
权限修饰符 (特殊修饰符) 返回值类型 方法名(形参列表){
    方法体
}
```

> 特殊修饰符native,static,final,abstract,synchronized来修饰的方法，暂时不讨论，等有开始了解到这块知识再说明

* 说明：
   1. 权限修饰符：private,public,缺省default(不写),protected--->在封装特性的体现时会用到
   2. 返回值的类型：有返回值 or 没有返回值
      如果有返回值，则必须在方法声明时，指定返回值的类型。同时，方法中也需要使用rentun关键字来返回指定类型的变量或常量: `return 数据`
      如果方法没有返回值，则方法声明时，使用`void`来表示。通常，没有返回值的方法中，不需要使用`return`,但是使用的话，只能`return;`来表示结束此方法
   3. 方法名：属于标识符，遵循标识符的命名规则和规范
   4. 形参列表：方法可以有0个，1个，或者多个形参
      格式：`数据类型1 形参1,数据类型2 形参2,...`

* return关键字的使用
  1. 使用范围：使用在方法体中
  2. 作用：①结束方法；②针对有返回值类型的方法，使用`return 数据`的方式返回所要的数据
  3. 注意点：return关键字后面不可以声明执行语句

* 方法的使用注意：
  1. 方法中可以调用当前类的属性和其他方法
  2. 特殊的：方法A中又调用了方法A：递归方法
  3. 方法中不能定义方法
  4. 类的静态方法中不能直接使用类的其他非静态方法
  5. 类的静态方法中能直接使用类的其他静态方法
  6. 类的非静态方法中能直接调用类的其他静态方法

## 4.6 对象数组

```java
Person[] persons=new Person[5];//这里只是在栈里申请了一个变量，变量指向了5个内存空间在堆中的首地址，但是这五个地址空间里全部是null
persons[0]=new Person();//数组的第一个空间指向了一个对象在内存的首地址
persons[0].name="Jack";
persons[0].age=16;
```

* 引用类型的变量，只可能存储两个值：null或地址值(含变量的类型)

* 对象数组的内存解析,如下图：

![对象数组内存图解](../images/对象数组内存分析.png)

## 4.7 匿名对象

* 理解：创建对象，即实例化一个类时，没有显式的赋给一个变量名，即为匿名对象
* 特征：匿名对象只能调用一次
* 使用，如下代码：

  ```java



  public class Company{
      public static void main(String[] args){
          new Person().hello();//这里匿名对象就只能用一次
          new Person().age=18;//这里匿名对象就只能用一次
          getPerson(new Person());//一般可以将匿名对象整个作为参数传给一个方法，然后在方法里面就可以调用这个匿名对象的各自属性和方法
      }

      public static void getPerson(Person person){
          person.age=20;
          person.language="Chinese";
          person.hello();
          person.speak();
      }
  }
  ```

## 4.8 再谈方法

### 4.8.1 方法的重载

* 方法重载(overload)定义：在同一个类中，允许存在一个以上的同名方法，只要它们的参数个数或者参数类型不同即可
  >两同一不同：①同一个类、相同方法名；②参数列表不同：参数个数不同，参数类型不同

  >子类可以重载父类的方法：子类拥有同名方法，但是参数列表不同，算作方法的重载，因为子类继承了父类，也就是相当于拥有了父类的成员方法，现在又定义了同名不同参的方法，自然就是重载

```java

class Arrays{
    //这两个就是方法重载，只要形参不是位置和类型一一对应且完全一致就可以重载
    public static void sort(int a[],int length){}
    public static void sort(int length,int a[]){}
    //以下也是重载
    public static void sort(int a[]){}
    public static void sort(String a[],int length){}

    //以下是错误的
    public static void sort(int b[],int len){}
    public static int[] sort(int a[],int length){}
    public void sort(int a[],int length){}
    protected static void sort(int a[],int length){}
}
```

* 判断是否是重载：
  跟方法的权限修饰符、返回值类型、形参变量名、方法体都没有关系
  > 判断的核心就是，假如该方法被调用的时候根据方法名或者加上参数，不会产生歧义，能够明确是在使用哪个方法

```java
class Arrays{
    public static void showNum(int a,int b){}
    public static void showNum(double a,double b){}
    //如果第一个函数没有被注释掉，调用的时候Arrays.showNum(1,2)会调用第一个函数
    //但是如果第一个函数被注释了，则会自动变量提升参数类型成了double类型，则会调用第二个函数
}
```

* 在通过对象调用方法时，如果确定某一个指定的方法：
  方法名---->参数列表

### 4.8.2 可变个数的形参

* jdk5.0新增的特性
* 格式：`数据类型 ... 变量名`
* 当调用可变个数形参的方法时，传入的参数个数可以是:0个、1个、2个...
* 可变个数形参的方法与本类中的方法名相同、形参不同的方法，会构成方法重载
* 可变个数形参的方法与本类中的方法名相同、形参类型也相同的数组，不会构成方法重载，编译器会认为是同名方法，编译无法通过
  >也就是说，其实可变个数的形参在方法中仍然是当作数组来使用

```java
public static void show(int a,int b){}
public static void show(int a,int ... nums){}//构成重载
public static void show(int ... nums){}
//show(1,2);会报错，不知道是使用的第二个还是第三个

public static void show(int[] nums){}//编译无法通过，会显示已定义了相同的方法，这个不算成方法重载
public static void show(int a,int[] nums){}//编译无法通过，会显示已定义了相同的方法，这个不算成方法重载
```

* 可变个数形参在方法的形参中，必须声明在参数列表的末尾
* 方法的形参列表中只能由一个可变个数的形参

  ```java
  public static void show(int a,int ... nums){}//正确
  public static void show(int ... nums){}//正确
  public static void show(int ... nums,int a){}//错误,可变个数的形参必须在末尾
  public static void show(int ... nums1,int ... nums2){}//错误，可变个数的形参必须在末尾且只能有一个
  ```

* 问题：

```java
class Arrays{
    //public static void sort(int a,int length){}
    public static void sort(int length,int ... a){}
    public static void sort(int ... a){}//但是这两个函数定义的时候可以共存
    public static void main(String[] args){
        Arrays.sort(1,2)//会报错，无法编译，所以第三个函数要删去，才没有歧义。但是取消第一个函数的注释后，就不会报错，准确匹配调用第一个函数
        Arrays.sort(new int[]{1,2});//编译通过
        Arrays.sort(1,new int[]{2,3});//编译通过
    }
}
```

* 形参是可变参数时，实参也可传一个数组

```java
public class Test{
    public static void main(){
        int[] arr={1,4,2,7,5};
        sort(arr);
    }
    public static void sort(int ... arr){

    }
}
```

### 4.8.3 递归方法

* 递归方法：一个方法体内调用(直接或间接)了它本身
* 方法递归包含了一种隐式的循环，它会重复执行某段代码，但这种重复执行无须循环控制
* 递归一定要向已知方向递归，否则这种递归就变成了无穷递归，类似于死循环
* 递归的函数内部，一定要有能够使得函数停下来的条件

* 注意事项：
  * 递归一定要有条件限定，保证递归能够停止下来，否则会发生栈内存溢出。
  * 在递归中虽然有限定条件，但是递归次数不能太多。否则也会发生栈内存溢出。
* 思考：
  > 已知有一个数列，$f(0)=1;f(1)=4;f(n+2)=2*f(n+1)+f(n)$,其中n是自然数,求$f(10)$的值

  ```java
  public static void main(String[] args){
      System.out.println(f(10));
  }
  public static int f(int n){
      if(n==0){
          return 1;
      }else if(n==1){
          return 4;
      }else{
          return 2*f(n-1)+f(n-2);
      }
  }
  ```

* 汉诺塔问题：
  描述：n个圆环从大到小依次叠放串在x柱子上，现在需要从x柱子上将圆环移到z柱子上，可以用y柱子作为辅助。要求：移动过程中，**不管是哪个柱子上，小圆环一定不能在大圆环下面**

![汉诺塔问题](../images/汉诺塔问题.png)

```java
public class RecursionTest {
    public static int count = 1;

    public static void main(String[] args) {
        hanoi(5, 'x', 'y', 'z');
    }

    /**
     * 汉诺塔问题：n个圆环从大到小依次叠放串在x柱子上，现在需要从x柱子上将圆环移到z柱子上，可以用y柱子作为辅助。
     * 要求：移动过程中，不管是哪个柱子上，小圆环一定不能在大圆环下面
     *
     * 思路：先把上面n-1个圆环移动到y柱子上 然后移动第n个圆环到z柱子上 最后把y柱子上的n-1个圆环移动到z柱子上
     *
     * @param n              表示需要移动的圆环个数。另外圆环是编号的，排序是按照从上到下，从小到大排的，最上面的圆环编号为1，最下面的圆环编号为n
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
}
```

 >move被执行的次数为$2^n-1$

* 求第n项斐波那契数列
  描述：数列为 1,1,2,3,5,8,13,21,34,55...f(n)=f(n-1)+f(n-2)
  所以可以看出它的通项公式：
![斐波那契数列通项公式](../images/斐波那契数列通项公式.png)
代码实现如下：

```java
public class RecursionTest {

    public static void main(String[] args) {
        System.out.println(getFibonacciSequence(10));
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
```

## 4.9 值传递机制

* **Java方法的形参的传递机制：值传递**

* 形参：方法定义时，声明的小括号内的参数
  实参：方法调用时，实际传递给形参的数据

* 值传递机制：
  1. 如果参数是基本数据类型，此时实参赋给形参的是实参真实存储的数据值
  2. 如果参数是引用数据类型，此时实参赋给形参的是实参存储数据的地址值

### 4.9.1 基本数据类型做形参

基本数据类型做形参时，传递实参如下代码的结果：

```java
public static void main(String[] args){
    int m=1;
    int n=2;
    System.out.printf("m=%d;n=%d",m,n);//m=1;n=2
    swap(m,n);
    System.out.printf("m=%d;n=%d",m,n);//m=1;n=2,并没有改变m,n的值
}
public void swap(int m,int n){
    int temp=m;
    m=n;
    n=temp;
}
```

图解如下：
![基本数据类型变量当形参](../images/基本数据类型变量当形参.png)

### 4.9.2 引用型数据类型变量做形参

引用型数据类型变量做形参时，传递实参如下代码的结果：

```java
public static void main(String[] args){
    int[] a=new int[]{6,7,8,9};
    int m=1,n=2;
    System.out.printf("a[m]=%d;a[n]=%d",a[m],a[n]);//a[m]=7;a[n]=8
    swap(a,m,n);
    System.out.printf("a[m]=%d;a[n]=%d",a[m],a[n]);//a[m]=8;a[n]=7,数组的值发生了变化
}
public static void swap(int a[],int m,int n){
    int temp=a[m];
    a[m]=a[n];
    a[n]=temp;
}
```

图解如下：
![引用数据类型变量当形参](../images/引用数据类型变量当形参.png)

* 引用数据类型包括数组和类(当然也有接口),所以当以类作为形参变量，实例化对象作为实参的时候，传递的也是值，也就是对象在堆中的首地址值，当方法内对实参数据进行了改变，此时该对象的数据真正发生了改变，因为这就是在原本对象的数据上进行的修改，如下代码示例可知：

```java
public ValueTransferTest{
    public static void main(String[] args){
        Number number=new Number();
        number.m=1;
        number.n=2;
        System.out.printf("number.m=%d;number.n=%d",a[m],a[n]);//number.m=1;number.n=2
        swap(number);
        System.out.printf("number.m=%d;number.n=%d",a[m],a[n]);//number.m=2;number.n=1,对象number的成员属性值发生了变化
    }

    public static void swap(Number number){
        int temp=Number.m;
        number.m=number.n;
        number.n=temp;
    }
}
class Number{
    int m;
    int n;
}
```

* 思考题：

  ```java
  public class Test{
      public static void main(String[] args){
          int a=10;
          int b=10;
          method(a,b);//需要在method方法被调用之后，仅打印出a=100,b=200,请写出method方法的代码
          System.out.println("a="+a);
          System.out.println("b="+b);
      }
      //代码编写处
      public static void method(int a,int b){

      }
  }
  ```

  分析与解答：乍一看，好像是在考察形参传值的知识，其实从这个知识点考虑，无论如何都是无法在method方法中改变main方法中变量a,b的值，只能从其他方法中去思考解决办法：要么在method方法中按照要求输出，然后中止程序的执行；要么就是重写(override)类PrintStream中的println方法，按照要求输出

  ```java
  public static void method(int a,int b){
      a=a*10;
      b=b*20;
      System.out.println("a="+a);
      System.out.println("b="+b);
      System.exit(0);
  }
  ```

  ```java
  public static void method(int a,int b){
      PrintStream ps=new PrintStream(System.out){
          @Override
          public void println(String s){
              if("a=10".equals(s)){
                  s="a=100";
              }else if("b=10".equals(s)){
                  s="b=200";
              }
              super.println(s);
          }
      };
      System.setOut(ps);
  }
  ```

* 陷阱

```java
/*
陷阱：在方法中，形参 = 新new对象，那么就和实参无关了
*/
class Test{
    public static void change(MyData my){
        my = new MyData();//形参指向了新对象，变量指向堆中的地址已不再是之前的地址
        my.num *= 2;
    }

    public static void main(String[] args){
        MyData m = new MyData();
        m.num = 1;

        change(m);//调用完之后，m对象的num属性值仍然为1
    }
}

class MyData{
    int num;
}
```

* String类型的特殊性

```java
public class StringTest {
    public static void main(String[] args){
        String str="Hello";
        changeStr(str);
        System.out.println(str);//输出：Hello
    }

    public static void changeStr(String str){
        str="hi";
    }
}
```

根据上面的知识点，引用数据类型的变量存的是地址值，那么传参时应该会改变地址空间里的值，这里是特殊情况，字符串常量放在了常量池中，当形参赋值新值时，会指向另一个常量池中的地址，所以main方法中的字符串变量的地址指向没有改变，不受影响。

## 4.10 封装

* 封装的由来
  当我们创建一个类的对象以后，我们可以通过`对象.属性`的方式，对对象的属性进行赋值，赋值操作只受到了数据类型和数据范围的限制，除此之外，没有其他制约条件。但是，在实际问题中，我们往往需要给属性赋值时加入额外的限制条件。这个条件就能在属性声明时体现，我们只能通过方法进行限制条件的添加(set方法),同时需要避免用户再使用`对象.属性`方式对属性进行赋值，可以通过在属性上添加权限修饰符private

* 封装性的体现（之一）
  将类的属性私有化(private)，然后提供公共方法(public)来获取(getXxx)和设置(setXxx)属性
  > 拓展：封装性的其他体现：1.不对外暴露的私有方法；2.单例模式；...

* 封装性的思想：把该隐藏的隐藏，该暴露的暴露出来

* 封装性的体现，需要权限修饰符来配合：
  1. java规定的4钟权限(从小到大)：private、缺省(default)、protected、public
  2. 4种权限可以用来修饰类以及类的内部结构：属性、方法、构造器、内部类
  3. 具体的，4种权限都可以修饰类的内部结构：属性、方法、构造器、内部类
     修饰类的只能是：缺省、public
* 权限修饰符的权限范围表
  |  修饰符   | 类内部 | 同一个包 | 不同包的子类 | 同一个工程 |
  | :-------: | :----: | :------: | :----------: | :--------: |
  |  public   |   √    |    √     |      √       |     √      |
  | protected |   √    |    √     |      √       |            |
  |  (缺省)   |   √    |    √     |              |            |
  |  private  |   √    |          |              |            |

* 局部变量没有权限修饰符的说法

## 4.11 构造器

* 构造器的特征：
  * 它具有与类相同的名称
  * 它不声明返回值的类型。(与声明为void不同)
  * 不能被`static`,`final`,`synchronized`,`abstract`,`native`修饰
  * 不能使用`return 数据`语句返回值
  * 一旦加上了返回值类型，那么这就不是构造器了
* 构造器(构造方法、constructor)的使用
  * 创建对象,同时可能会进行对象初始化(对象的成员属性)
* 说明：
  * 如果没有显式的定义类的构造器，则系统默认提供一个空参的构造器,其权限修饰符和类的权限修饰符一致
  * 定义构造器的格式：`权限修饰符 类名(形参列表){}`
  * 一个类中定义多个构造器，相互构成重载
  * 一旦显式定义了类的构造器之后，系统就不再提供默认的空参构造器
  * 一个类中，至少会有一个构造器
  * 多个构造器可以互相调用，但是调用必须在另一个构造器方法的第一行；另外，构造器不能在成员方法中被调用

```java
class Person{
    private String name;
    private int age;

    public Person(){

    }
    public Person(String name,int age) {
        this();
        this.age=age;
        this.name=name;
        this();//此处错误，调用其他构造器必须在本构造器内的第一行
    }
    public String getName(){
        this();//此处错误,成员方法无法调用构造器
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age=age;
    }
}
```

* 属性赋值的先后顺序
  ①默认初始化
  ②显式初始化
  ③构造器中赋值
  ④通过`对象.属性`或`对象.setXxx`赋值
  操作的先后顺序：①->②->③>④

## 4.12 JavaBean

* JavaBean是一种Java语言写成的可重用组件
* JavaBean是指符合如下标准的Java类：
  * 类是公共的
  * 有一个无参的公共的构造器(因为往往使用这个JavaBean的方式是用反射，常调用无参构造器)
  * 有属性，且有对应的set、get方法
  * 如果属性是boolean类型abc，则方法对应为setAbc,isAbc

## 4.13 this的使用

```java
class Person{
    private String name;
    private int age;

    public Person(){

    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age=age;
    }
}
```

* 使用形式：
  `this.成员属性`
  `this.成员方法`
  `this()或者this(参数列表)`：调用当前正在创建的对象的其他构造器

* this使用位置
  * this在实例初始化相关的非静态代码块和构造器中：表示正在创建的那个实例对象，即正在new谁，this就代表谁
  * this在非静态实例方法中：表示调用该方法的对象，即谁在调用，this就代表谁。
  * this不能出现在静态代码块和静态方法中，此时静态变量的setXxx方法内可以使用`类名.私有静态属性`来使用
* this关键字的使用：
  * this可以用来修饰：属性、方法、构造器
  * this修饰属性和方法，this理解为当前对象
* 在类的方法中，我们可以使用`this.属性`或`this.方法`的方式，调用当前对象属性或方法，但是通常情况下，我们都选择省略this。特殊情况下，如果方法的形参和类的属性同名时，就必须显式使用`this.变量`的方式,表明此变量是属性，而非形参
* 在类的构造器中，同样的，调用当前正在创建的对象属性或者方法，我们可以省略this，但是如果构造器的形参与属性名相同，此时必须显式使用`this.变量`的方式，表明此变量是属性，而非形参
* this调用构造器
  * 在类的构造器中，可以显式使用`this(参数列表)`方式，调用本类中指定的其他构造器
  * 构造器中不能通过`this(参数列表)`方式调用自己
  * 如果一个类中有n个构造器，则最多有n-1个构造器中使用了`this(参数列表)`,也就是说不能构造器之间互相调用，形成了死循环
  * 规定：`this(参数列表)`必须写在当前构造器的首行
  * 构造器内部，最多只能调用一个其他的构造器

## 4.14 import与package

### package

* 为了更好的实现项目中类的管理，提供包的概念
* 使用package声明类或接口所属的包，声明在源文件的首行
* 包名，属于标识符，遵循标识符的命名规则、规范(xxx.yyy.zzz)
* 每`.`一次，就代表一层文件目录。
* 同一个包下，不能命名同名的接口、类。
* 不同的包下，可以命名同名的接口、类。
* 当类有了包名后，使用这个类需要带全名称，即`包名.类名`

### import

* 在源文件中显式的使用import结构导入指定包下的类、接口
* 声明位置在包的声明和类的声明之间
* 如果需要导入多个结构，则并列写出即可
* 可以使用`xxx.*`的方式，表示可以导入xxx包下的所有结构
* 如果使用的类或者接口是java.lang包下定义的，则可以省略import
* 如果使用的类或接口是本包下定义的，则可以省略import结构
* `xxx.*`只是导入xxx包当前文件夹下的所有结构，但是如果想使用的是xxx包下的子包，那么需要再导入子包，
  >所谓的同一个包，就是指包名一致，完全一致，而不是说包之间是父子关系

* `import static`：导入指定类或接口中的静态结构：属性或方法

  ```java
  import static java.lang.Math.*;
      public class Test {
      public static void main(String[] args) {
          double pi=PI;//import static已经把Math里所有的成员方法或者属性都导入进来了
      }
  }
  ```

![包的使用](../images/包的使用.png "包的使用")
从图上所示，上面的三个类`PackageTest`,`Test`,`Person`之间是无法在各自的类中去使用其他类的，必须要使用import导入，因为它们的包名不同
PackageTest包名：`com.agile.learn`
Person包名：`com.agile.study`
Test包名：`com.agile.study.aa`

## 4.15 继承

* 为什么要继承？
  多个类中存在相同属性和行为时，将这些内容抽取到单独一个类中，那么多个类无需再定义这些属性和行为，只要继承那个类即可
  那么此处的多个类称为子类(派生类),单独的这个类称为父类(基类或超类)
* 继承的好处：
  * 减少了代码的冗余，提高了代码的复用性
  * 使得功能的扩展
  * 为之后多态性的使用，提供了前提

* 继承性的格式：`class A extends B{}`
  A:派生类、subclass
  B:父类、超类、基类、superclass

* 继承性的体现：
  * 一旦子类A继承父类B之后，子类A中就获取了父类B中声明的所有的属性和方法。
  * 特别的，父类中声明为private的属性或者方法，子类继承父类之后，仍然认为是获取了父类中的私有结构，只是因为封装性的影响，使得子类不能直接调用父类的私有属性(可以使用setXX,getXxx)
  * 子类继承父类之后，还可以声明自己特有的属性或者方法，实现功能的扩展

* Java中继承的规定：
  * 一个子类只能有一个父类
  * 一个父类可以派生出多个子类
  * 子父类是相对的概念，一个子类也可以被其他类继承
  * 子类直接继承的父类，称为：直接父类。间接继承的父类称为：间接父类
  * 子类继承父类之后，就获取了直接父类以及所有间接父类中声明的属性和方法
  * **子类无法继承父类的构造器**；但是如果子类声明了构造器，就一定会调用父类的构造器
  * 当子类有构造器时，则父类一定也要有构造器(没有则无法编译，报错)
  * 当子类构造器没有自己手写super调用父类构造器，则会隐式调用父类的无参构造器
* 如果没有显式的声明一个类的父类，则此类继承于`java.lang.Object`
  所有的类都直接或间接继承于`java.lang.Object`类
  意味着，所有的java类都具有`java.lang.Object`类的属性和方法

## 4.16 方法的重写

* 方法的重写是在子类继承父类之后，可以对父类中同名同参数的方法，进行覆盖操作
* 应用：重写之后，当创建了子类对象后，通过子类对象调用父类中的同名同参数方法时，实际执行的时子类重写父类方法后的方法
* 重写的规定：
    方法的声明：`权限修饰符 返回值类型 方法名(形参列表) throws 异常的类型{方法体}`
    约定俗称：子类中的叫重写的方法，父类中的叫做被重写的方法
    1. 子类重写的方法的方法名和形参列表与父类被重写的方法的方法名和形参列表要相同
    2. 子类重写的方法的权限修饰符不小于父类被重写的方法的权限修饰符
    3. 子类不能重写父类中声明为private权限的方法
    4. 返回值类型：
       * 父类被重写的方法的返回值类型是void,则子类重写的方法的返回值类型只能是void
       * 父类被重写的方法的返回值类型是A类型，则子类重写的方法的返回值类型可以是A类或者A类的子类
       * 父类被重写的方法的返回值类型是基本数据类型(比如double),则子类重写的方法的返回值类型必须是相同的
    5. 子类重写的方法抛出的异常类型不小于父类被重写的方法抛出的异常类型(也就是说，被重写的方法抛出的异常类应该是父类异常，重写的方法抛出异常的类应该是相同或者是它的子类)
    6. 子类和父类中的同名同参数的方法，要么都声明为非static的(考虑重写)，要么都声明为static的(这不是重写,父类的static方法不可重写,但父子类可以同时存在同名同参的方法，且不构成重写)
    7. 如果父类自己只定义了一个有参的构造器（父类写了有参构造器后系统不会再给加上无参构造器)，那么子类必须写一个有参的构造器，因为子类如果不写构造器则系统默认会给子类加上一个无参构造器，此时子类无参构造器又会去调用父类的无参构造器，而现在父类没有无参构造器，则会报错。而且这个子类的有参构造器方法体第一行必须使用super来调用父类有参构造器，因为如果不写，那么会隐式加上调用父类无参构造器，而父类无参构造器不存在(父类写了有参构造器后系统不会再给加上无参构造器)，所以报错
父类：

```java
public class Person {
    public String name;
    public int age;

    public void hello(){
        System.out.println("hello");
        walk();
    }
    private void walk(){//这是私有的
        System.out.println("walk person");
    }
}
```

子类：

```java
class Student extends Person{
        String id;
        public void study(){
            System.out.println("study");
        }

        public void walk(){
            System.out.println("student walk");
        }

}

public class StudentTest{
    public static void main(String[] args) {
        Person person=new Student();
        person.hello();//打印hello，walk person而不是student walk
    }
}
```

## 4.17 super关键字

* super的理解：父类的
* super的使用位置：构造器，非静态方法，非静态代码块
* 格式：
  `super.成员属性`
  `super.成员方法`
  `super()或super(参数列表)`
* super可以用来调用：属性、方法、构造器
* 使用super来调用父类的属性或者方法，因为方法会被子类重写，但是想调用父类的方法就可以使用`super.方法()`
* 类的属性没有覆盖(重写)的说法，**子类可以定义与父类完全相同的属性，此时子类就有了两个同名的属性**，想要指定调用父类的同名属性，就必须使用
* `super.`查找的是直接父类或者间接父类的成员
* `super.属性`
* 在子类的方法或构造器中，通过使用super关键字调用父类中声明的属性或者方法，但是通常情况下(当没有重写方法或者属性不重名)，可以省略

* super调用构造器
  * 格式：`super(参数列表);`,调用父类中声明的指定构造器
  * **super调用构造器时调用的是直接父类**，多层继承的情况下不会继续查找
  * `super(参数列表);`只能写在子类构造器中，且必须是首行
  * 也就是说，在子类的一个构造器中，`this(参数列表)`与`super(参数列表);`至多只能出现一个
  * 在子类构造器的首行，没有显式的声明`this(参数列表)`和`super(参数列表);`则默认调用的是父类中空参的构造器：super()
  * 在类的多个构造器中，至少有一个构造器调用了父类的构造器`super(参数列表)`
父类

```java
public class Person {
    public String name="person";
    public int age;
    public Person(){
        age=1;
    }
    public void hello(){
        System.out.println("hello");
    }
    public void walk(){
        System.out.println("person walk");
    }
}
```

子类

```java
class Student extends Person{
        String id;
        public String name="student";

        public Student(){
            id="1001";
        }
        public void study(){
            System.out.println("study");
        }
        public void walk(){
            System.out.println("student walk");
        }

}

public class StudentTest{
    public static void main(String[] args) {
        Person student=new Student();
        System.out.println(student.age);//1,子类并没有对age进行初始化为1，说明子类的创建的过程中，曾经调用了父类的构造器
    }
}
```

> 也可在子类创建的那一行代码打断点，调式进入到调用构造器的过程中，就可以看到子类在创建对象时，在调用自己的构造器，然后又因为自己的构造器隐式的调用了`super()`,进入了父类的构造器，又因为所有的类都是Object类的子类，此时刚刚的父类又进入到了Object类中

### 子类对象实例化的过程

1. 从结果上来看：
   * 子类继承父类之后，就获取了父类中声明的属性或者方法
   * 创建子类的对象，在堆空间中，就会加载所有父类中声明的成员
2. 从过程上来看
   * 当我们通过子类的构造器创建子类对象时，我们一定会直接或间接的调用其父类的构造器，进而调用父类的构造器......直到调用了java.lang.Object类中空参构造器为止

> 虽然创建子类对象时调用了多个类的构造器，但是整个过程中只创建了一个对象

## 4.18 多态

1. 多态性，可以理解为一个事物的多种形态
2. 对象的多态性：父类的引用指向子类的对象（子类的对象赋给父类的引用）

   ```java
   //对象的多态性：父类的引用指向子类的对象
   Person person=new Student();
   //多态的使用：当调用子父类同名同参的方法时，实际执行的是子类重写父类的方法--虚拟方法调用
   person.walk();//student walk
   ```

3. 多态的使用：虚拟方法调用
   有了对象的多态性之后，在编译期，只能调用父类中声明的方法，但是在运行期，实际执行的是子类重写父类的方法。
   总结：编译，看左边；运行，看右边

4. 多态性使用的前提：
   * 类的继承关系
   * 方法的重写

5. 应用场景：
   * 父类类型作为方法形式参数，子类对象为实参。
   * 数组元素类型声明为父类类型，实际存储的是子类对象
   * 方法的返回值类型声明为父类的类型，实际返回值是子类对象

   ```java
   public void daData(Connection conn){//conn可能是mysql，oracle，mssql...
        conn.method();//子类都重写过父类的某个函数，然后这里运行时执行子类各自的实现
   }
   ```

6. 对象的多态性，只适用于方法，不适用于属性，即子类和父类都有同名属性时，使用多态性创建对象后，对象调用同名属性时，还是调用的是父类的属性，而不是子类

```java
   Person person=new Student();
   System.out.println(person.name);//person
```

### 虚拟方法调用

* 虚方法：可以被子类重写
* 正常的方法调用
  没有继承关系；或者虽然有继承，但是子类调用的方法是子类自己新定义的,特有的
* 虚拟方法调用(多态情况)
  子类中定义了与父类同名同参数的方法，在多态的情况下，将此时父类的方法称为虚拟方法，父类根据赋给它的不同子类对象，动态调用属于子类的该方法。**这样的方法调用在编译期是无法确定的**
* 编译时类型和运行时类型

  ```java
  Person p=new Student();
  p.getInfo();//调用的是Student类中的getInfo()，但是Person类中也必须有该方法
  ````

  编译时引用类变量p为Person类型，而方法的调用是在运行时确定的，所有调用的是Student类的getInfo()方法-------动态绑定

* 多态是运行时行为

### 向下转型

* 动态特性使得父类的引用指向了子类的对象，而且引用调用的方法要实现多态性的话，就需要调用子类重写过的父类方法，而且只能调用父类已有的方法或者子类重写过，而不能调用子类特有的属性和方法
* 向下转型，使用强制类型转换符，才能调用子类特有的属性和方法

  ```java
  Person p=new Student();
  p.walk();//student walk
  Student stu=(Student)p;
  stu.study();//study
  ```

* 向下转型是需要判断的，不然可能会出现异常，如果又有一个子类继承了Person，而且Person类型的变量引用指向了这个类的对象，当这个引用强制转换成Student类型则会出现问题

* 引用行数据类型的变量进行强转，只能是它们有继承关系，将父类引用强转成子类引用

  ```java
  class Worker extends Person{
      String job;
      public void doWork(){
          System.out.println("dowork");
      }
  }

  public class WorkerTest{
      public static void main(String[] args){
          Person p=new Worker();
          p.doWork();//dowork
          Student stu=(Student)p;//报错异常ClassCastException

          if(p instanceof Student){//false
              Student stu=(Student)p;//此行不会执行
          }
      }
  }
  ```

```java
public class DogTest {
    public static void main(String[] args) {
        Animal dog=new Dog();
        ThaidiDog thaidiDog=(ThaidiDog)dog;//编译通过，但是运行不通过，这两段代码相当于把Dog类型的对象转换成了ThaidiDog类型，这是不允许的
        //运行时类型的对象应该小于声明的变量类型
    }
}

class Animal{

}

class Dog extends Animal{

}

class ThaidiDog extends Dog{

}
```

* 无论是向上转型还是向下转型，对象的运行时类型，从头到尾没有改变过，这里说的转型指的是在编译时，所以无论怎么转型，运行时类型都没变化
* 如果`a instanceof A`返回`true`,`a instanceof B`也返回`true`,那么A和B一定存在着继承关系(可能是直接继承，有可能间接继承)

## 4.19 Object类

### 认识java.lang.Object类

* Object类是所有Java类的根父类
* 如果在类的声明中未使用extends关键字指明其父类，则默认父类为java.lang.Object类
* Object类中的功能(属性、方法)就具有通用性
* Object类只声明了一个空参的构造器

```java
Object obj1=new int[5];//可以编译，因为Object是所有引用数据类型的父类，当然可以接收int[]数组
int[] obj2=(int[])obj1;//但是使用前需要转换数据类型
obj2[0]=1;

Object[] obj3=new String[5];
Object[] obj4=new int[5];//错误，编译不通过，int是基本数据类型，不是Object的子类
```

### Object类中的方法

* `protected Object clone()`

  * 因为该方法修饰符是protected，子类想拥有该方法，让其子类实例化对象被克隆，需要重写该方法，放大其权限范围
  * 重写了Object的clone()后，该类还需要实现Cloneable接口才能使用该clone()方法

    ```java
    Person p=new Person();
    p.age=12;
    Person p2=(Person)p.clone();//因为clone返回的类型是Object，这里需要强转
    p2.age=18;
    System.out.println(p.age);//12
    System.out.println(p2.age);//18
    //说明clone是在内存中重新开辟了一个空间来存对象的信息
    ```

* `protected void finalize()`
    垃圾回收器gc在内存中回收某对象之前就会让对象去执行该方法

    ```java
    class Student extends Person {

        @Override
        protected void finalize() {
            System.out.println("student被回收");
        }
    }

    public class StudentTest {
        public static void main(String[] args) {
            Student p = new Student();
            p = null;//引用类型变量设置为null，暗示垃圾回收机制可以回收该对象
            System.gc();//通知系统进行垃圾回收，会有一些效果，但是系统是否进行垃圾回收依然不确定
            //运行后控制台会输出：student被回收
        }
    }
    ```

* `class<?> getClass()`
    用来获取该变量的(运行时)数据类型，反射功能会用到

* `int hashCode()`得学习到集合时再了解
* `void notify()`,`void notifyAll()`,`void wait()`,`wait(long timeout)`,`wait(long timeout,int nanos)`这些得学习多线程之后再了解

* hashCode 的常规协定：
      ①如果两个对象的hash值是不同的，那么这两个对象一定不相等；
      ②如果两个对象的hash值是相同的，那么这两个对象不一定相等。
      >因为在集合Set和Map要保证存入的元素唯一，互不相同，需要判断两个对象是否相等，而这用到了equals和hashcode
      >Set区别对象是不是唯一的标准是，两个对象hashcode是不是一样，再判定两个对象是否equals;

* Map 是先根据Key值的hashcode分配和获取对象保存数组下标的，然后再根据equals区分唯一值

* `equals()`
     问：`==`和`equals()`区别
     ==，是一个运算符
     1. 可以使用在基本数据类型变量和引用数据类型变量中
     2. 如果比较的是基本数据类型变量，比较两个变量保存得数据是否相等(数据类型不一定要相同)

```java
int i=10;
int j=10;
double d=10.0;
System.out.println(i==j);//true
System.out.println(i==j);//true

boolean b=true;
System.out.println(b==b);//无法编译通过

char c=10;
System.out.println(i==c);//true

Person p1=new Person("Tom",18);
Person p2=new Person("Tom",18);
System.out.println(p1==p2);//false,因为这两个变量存的是对象在堆中的首地址值

String s1=new String("abc");
String s2=new String("abc");
System.out.println(s1==s2);//false,同理

String s3="abcd";
String s4="abcd";
System.out.println(s3==s4);//true,这个是比较特殊的，这种写法，虽然也是存的地址值，但不是堆中的，而是方法区中的某地址，因为"abcd"这是字符串常量，常量放在了常量池中
```

* `equals`
    1. 是一个方法，不是运算符
    2. 只能适用于引用数据类型
    3. `Object`类中`equals()`的定义：

    ```java
    public boolean equals(Object obj){
        return (this == obj);
    }
    ```

> 可以看出，Object类中定义的equals()和==的作用是相同的，比较两个对象的地址值是否相同，即两个引用是否指向同一个地址空间

* 像`String`、`Date`、`File`、一些基本数据类型的包装类都重写了Object类中的`equals()`方法，比较的不是两个引用地址是否相同，而是比较对象的“实体内容”是否相同

```java
Person p1=new Person("Tom",20);
Person p2=new Person("Tom",20);
System.out.println(p1.equals(p2));//false,这里与运用==的效果是相同的

String s1=new String("abc");
String s2=new String("abc");
System.out.println(s1.equals(s2));//true,因为String类重写了equals方法，比较的是对象的内容
```

所以也可以在自己定义的类中重写`equals()`,如下代码：

```java
class Person{
    public int age;
    public String name;
    //....

    @Override
    public boolean equals(Object){
        if(this==obj){
            return true;
        }
        if(obj instanceof Person){
            Person person=(Person)obj;
            return this.age==person.age&&this.name.equals(person.name);
        }
        return false;
    }
}
```

> 一般不用在自己的自定义类中亲自重写`equals()`，IDE里面可以生成，就像setXxx,getXxx那样

* 重写equals要求：
  * 如果重写equals，那么一定要一起重写hashCode()方法
  * 如果两个对象调用equals返回true，那么要求这两个对象的hashCode值一定是相等的
  * 如果两个对象的hashCode值不同的，那么要求这个两个对象调用equals方法一定是false
  * 如果两个对象的hashCode值相同的，那么这个两个对象调用equals可能是true，也可能是false
* 重写equals方法的原则：
  * 对称性：如果`x.equals(y)`返回的是`true`,那么`y.equals(x)`也应该返回的是`true`
  * 自反性：`x.equals(x)`必须返回的是`true`
  * 传递性：如果`x.equals(y)`返回的是`true`，且`y.equals(z)`返回的是`true`,那么`x.equals(z)`也应该返回的是`true`
  * 一致性：如果`x.equals(y)`返回的是`true`，只要x和y内容一直不变，那么不管重复多少次`x.equals(y)`，返回的都是`true`
  * 任何情况下，`x.equals(null)`,总是返回`false`
  * `x.equals(和x不同类型的对象)`总是返回`false`
* 重写`hashCode`示例如下：

```java
class Person {
    public String name;
    public int age;

    Person(String name,int age){
        this.name=name;
        this.age=age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;//如果地址相同
        if (o == null || getClass() != o.getClass()) return false;//如果对象为空，或者不是同一个数据类型
        Person person = (Person) o;//转换后比较成员属性
        return age == person.age &&
                name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
    /*
    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }

    public static int hashCode(Object a[]) {
        if (a == null)
            return 0;

        int result = 1;

        for (Object element : a)
            result = 31 * result + (element == null ? 0 : element.hashCode());//核心代码

        return result;
    }
    */
}
```

`toString()`
    1. 当输出一个对象的引用时，实际上就是调用当前对象的`toString()`

```java
Person person=new Person();
System.out.println(person);//输出的时类名和hashcode拼起来的字符串
System.out.println(person.toString());//两个输出相同
```

* Object类中toString()的定义

```java
public String toString(){
    return getClass().getName()+"@"+Integer.toHexString(hashCode());
}
```

1. 像Sting,Date,File,包装类都重写了Object类中的toString()方法,使得在调用对象的toString()时，返回的是“实体内容”信息

 > `toString()`可以使用IDE生成，一般情况下不手写

## 4.20 单元测试

### Junit单元测试

* junit测试类必须是public类
* 测试方法本身必须是public修饰的
* 测试方法返回值类型是void
* 测试方法是无参的
* 测试方法不能是静态的

```java
public class JunitTest{
    @Test
    public void methodTest(){

    }
}
```

* `@Before`:在每个测试方法@Test执行之前都要执行
* `@After`:在每个测试方法@Test执行之前都要执行
* `@BeforeClass`:方法得使用static修饰，在类初始化(类加载)时运行，在所有的@Before和@Test之前运行，且只运行一次
* `@AfterClass`:方法得使用static修饰，在所有的@Test和@After之后运行，且只运行一次

## 4.21 包装类的使用

* 针对8种基本数据类型定义相应的引用类型---包装类(封装类)
* 可以使用包装类对原本的基本数据类型的数据功能扩展，进行更多便利的操作

| 基本数据类型 |   包装类    |
| :----------: | :---------: |
|    `byte`    |   `Byte`    |
|   `short`    |   `Short`   |
|    `int`     |  `Integer`  |
|    `long`    |   `Long`    |
|   `float`    |   `Float`   |
|   `double`   |  `Double`   |
|  `boolean`   |  `Boolean`  |
|    `char`    | `Character` |
> 其中上面前6种表示数字的包装类，它们都有一个父类`Number`

### 基本数据类型和包装类，String互相转换

1. 基本转包装
   `包装类名 变量名=new 包装类名(参数)`
   这里传参的值可以是基本数据类型的变量，也可以是常量;也可以是字符串，但字面一定要符合该类型

   ```java
   Float f=new Float(1.2);
   Float f2=new Float(1.2f);
   Float f3=new Float("1.2f");//以上都正确
   Integer n=new Integer(1);
   Integer n2=new Integer("1");
   Integer n3=new Integer("1.2");//这里字符串的字面上一定要是符合int型的数字

    Boolean b=new Boolean("true");//传"true"或者"TRUE","TRue"不区分大小的情况下，一直就是true.反之其他的都是false,比如"true121"
   ```

2. 包装转基本
   `包装型变量.xxxValue()`,这里的`xxx`指的是包装类对应的基本数据类型,如下：

   ```java
   int n=20;
   Integer in=new Integer(n);
   n=in.intValue();

   Boolean boolObj=new Boolean("true");
   boolean bool=boolObj.booleanValue();
   ```

3. 基本转Sting
   `String.valueOf(参数)`，这里的参数必须得是基本数据类型的变量或者常量，同时变量也可以是char型数组

   ```java
   String s=String.valueOf(1.1f);//s="1.1"
   String s2=String.valueOf(true);//s2="true"

   char[] ch={'a','b','c'};
   String s3=String.valueOf(ch);//s3="abc"
   ```

4. String转基本
   `要转的基本类型对应的包装类.parseXxx(str)`,如下举例：

   ```java
   String str="12.3";//str="12.3f";也可
   float f=Float.parseFloat(str);//f=12.3

   String s2="true12";//主要传的值不是"true"不区分大小写的值，就一定返回false;s2为"TRue"则会转为true
   boolean b=Boolean.parseBoolean(s2);//b=false
   ```

5. 包装转String
   `包装类.toString()`即可转成String类型

6. String转包装
   `包装类名 变量名=new 包装类名(str)`

### 自动装箱与自动拆箱

* JDK5.0新特性

```java
public static void main(String[] args){
    int num=10;
    Integer in=num;//这里直接将基本数据类型转成了包装类，自动装箱
    //...
    method(20);//编译通过，不会出错，自动装箱


    //自动拆箱
    int num2=in;
}
public static void method(Object obj){
    //...
}
```

* 代码分析，如下：

  ```java
  Integer i=new Integer(1);
  Integer j=new Integer(1);
  System.out.println(i==j);//false
  //引用数据类型，变量存的是堆中的地址，这两次new都会在内存开辟新空间

  Integer m=1;
  Integer n=1;
  System.out.println(m==n);//true，这种写法，有点类似String s="abc"; Integer类中有个缓存数组来放[-128-127]之间的数字，所以地址会相同

  Integer x=128;
  Integer y=128;
  System.out.println(x==y);//false，超出了Integer类中缓存的数组范围，所以还是得new，在堆中开辟地址空间
  //Character类型也有缓存0-127
  //Boolean类型也有两个缓存

  Integer a1=1000;
  int a2=1000;
  Integer a3=new Integer(1000);
  System.out.println(a1==a2);//true，按照基本数据类型来比较
  System.out.println(a2==a3);//true，按照基本数据类型来比较
  System.out.println(a1==a3);//false，比较地址空间
  ```

## 4.22 static关键字

* static:静态的
* static可以用来修饰：属性，方法，代码块，内部类
* 使用static修饰属性：静态变量(类变量)
  * 属性，按是否使用static修饰，分为：静态变量和非静态变量(实例变量)
    实例变量，创建多个对象，每个对象都独立的拥有一套类中的非静态属性，当修改一个对象中的非静态变量属性时，不会导致其他对象的同名属性值被修改
    静态变量，创建多个对象，多个对象共享共享一个静态变量。当通过某一个对象修改静态变量时，会导致其他对象调用此静态变量时，是刚刚修改过了的值
  * 静态变量随着类的加载而加载。可以通过`类.静态变量`的方式调用
  * 静态变量的加载要早于对象的创建
  * 由于类只会加载一次，则静态变量在内存中也只会存一份，在方法区的静态域中
* 使用static修饰方法：
  * 随着类的加载而加载，可以通过`类.静态方法`的方式进行调用
  * 静态方法内不能调用非静态的成员属性和成员方法
  * 静态方法中，只能调用静态的方法或属性
  * 非静态方法中，既可以调用非静态的方法和属性，也可以调用静态的方法和属性
  * 静态的方法内，不能使用this关键字，super关键字
  * 关于静态属性和静态方法的使用，可以从生命周期角度区理解：静态的成员是在类的加载时就有的，但是实例成员都还不存在，无法调用

### 单例设计模式

* 设计模式是在大量的实践中总结和理论化之后代码结构、编程风格、以及解决问题的思考方法
* 常见的设计模式(23种)
  1. 创建型模式(5种)：工厂方法模式、抽象工厂模式、单例模式、建造者模式、原型模式
  2. 结构型模式(7种)：适配器模式、装饰器模式、代理模式、外观模式、桥接模式、组合模式、享元模式
  3. 行为型模式(11种)：策略模式、模板方法模式、观察者模式、迭代器模式、责任链模式、命令模式、备忘录模式、中介者模式、解释器模式

单例设计模式：就是采取一定的方法保证在整个软件系统中，对某个类只能存在一个对象实例

* 饿汉式:还没使用前就已经创建了，要使用的时候就之间拿来用

```java
public class BankTest {
    public static void main(String[] args) {
        Bank b1=Bank.getInstance();
        Bank b2=Bank.getInstance();
        System.out.println(b1==b2);//true
    }
}
class Bank{
    private Bank(){

    }
    private static Bank instance=new Bank();
    public static Bank getInstance(){
        return instance;
    }
}
```

* 懒汉式：在函数被调用的时候才创建该对象

```java
public class BankTest {
    public static void main(String[] args) {
        Bank b1=Bank.getInstance();
        Bank b2=Bank.getInstance();
        System.out.println(b1==b2);//true
    }
}
class Bank{
    private Bank(){

    }
    private static Bank instance=null;
    public static Bank getInstance(){
        if(instance == null)//此行现在的写法是线程不安全的
            instance=new Bank();
        return instance;
    }
}
```

* 饿汉式和懒汉式的区分：
  懒汉式：
  好处：延迟对象的创建；
  饿汉式：
  坏处：对象加载时间过长；好处：是线程安全的

* 单例模式的优点：
  由于单例模式只生成一个实例，减少了系统性能开销，当一个对象的产生需要比较多的资源时，如读取配置、产生其他依赖对象时，则可通过在应用启动时直接产生一个单例对象，然后永久驻留内存的方式来解决
  > 举例：java.lang.Runtime类就是单例模式(饿汉式)创建的

* 单例模式的应用场景
  1. 网站的计数器：确保同步
  2. 应用程序的日志应用：由于共享日志文件一直处于打开状态，只有一个实例区操作，否则内容不好追加
  3. 数据库连接池：因为数据库连接是一种数据库资源
  4. 读取配置文件的类：没有必要每次使用配置文件数据，都生成一个对象去读取

## 4.23 代码块

* 代码块，又称初始化块
* 代码块的作用：用来初始化类、对象
* 代码块如果有修饰的话，只能使用static
* 分类：静态代码块、非静态代码块
* 静态代码块
  * **随着类的加载而执行，而且只执行一次**
  * 初始化类的信息
  * 一个类中有多个静态代码块，则按照声明的先后顺序执行
  * 静态代码块的执行先于非静态代码块
* 类初始化方法体`<clinit>`的组成部分：
  （1）静态类成员变量的显式赋值语句
  （2）静态代码块中的语句
* 非静态代码块
  * **随着对象的创建而执行，每创建一个对象，就执行一次非静态代码块**
  * 可以在创建对象时，对对象的(非静态的实例属性)属性等进行初始化
  * 一个类中有多个非静态代码块，则按照声明的先后顺序执行
* 实例初始化方法的方法体，由四部分构成(以下在编译时会组装成一个init方法)：
  （1）super()或super(实参列表) 这里选择哪个，看原来构造器首行是哪句，没写，默认就是super()
  （2）非静态实例变量的显示赋值语句
  （3）非静态代码块
  （4）对应构造器中的代码
特别说明：其中（2）和（3）是按顺序合并的，（1）一定在最前面（4）一定在最后面
* 非静态代码块可以调用静态的成员，也可调用非静态的成员
* 静态代码块不能调用非静态的成员，只能调用静态的成员

```java
class Person{
    {
        //非静态代码块
    }
    static{
        //静态代码块
    }
}
```

* 示例1

```java
class Root{
    static{
        System.out.println("Root的静态初始化块");
    }
    {
        System.out.println("Root的普通初始化块");
    }
    public Root(){
        System.out.println("Root的无参构造器");
    }
}
class Mid extends Root{
    static{
        System.out.println("Mid的静态初始化块");
    }
    {
        System.out.println("Mid的普通初始化块");
    }
    public Mid(){
        System.out.println("Mid的无参构造器");
    }
    public Mid(String msg){
        System.out.println("Mid的带参构造器，参数值："+msg);
    }
}
class Leaf extends Mid{
    static{
        System.out.println("Leaf的静态初始化块");
    }
    {
        System.out.println("Leaf的普通初始化块");
    }
    public Leaf(){
        super("JavaSE");
        System.out.println("Leaf的构造器");
    }
}
public class LeafTest{
    public static void main(String[] args){
        new Leaf();
        System.out.println("****************");
        new Leaf();
    }
}
```

结果输出为：

```text
Root的静态初始化块
Mid的静态初始化块
Leaf的静态初始化块
Root的普通初始化块
Root的无参构造器
Mid的普通初始化块
Mid的带参构造器，参数值：JavaSE
Leaf的普通初始化块
Leaf的构造器
****************
Root的普通初始化块
Root的无参构造器
Mid的普通初始化块
Mid的带参构造器，参数值：JavaSE
Leaf的普通初始化块
Leaf的构造器
```

>可以通过debug的方式调试代码的执行步骤，也能看出它们的执行顺序

![代码块执行](../images/代码块.png)
从上面的图可以看出，编译器将静态属性的显式初始化赋值和静态代码块放在一起，组成了clinit方法来执行

* 示例2

```java
class Father{
    static{
        System.out.println("111");
    }
    {
        System.out.println("222");
    }
    public Father(){
        System.out.println("333");
    }
}
public class Son extends Father{
    static{
        System.out.println("444");
    }
    {
        System.out.println("555");
    }
    public Son(){
        System.out.println("666");
    }
    public static void main(String[] args){
        System.out.println("777");
        System.out.println("****");
        new Son();
        System.out.println("****");
        new Son();
    }
}
```

结果输出为：

```text
111
444
777
****
222
333
555
666
****
222
333
555
666
```

* 属性可以赋值的位置
  ①默认初始化
  ②显式初始化/⑤在代码块中赋值:这里取决于两者出现的先后顺序
  ③构造器中初始化
  ④有了对象后，可以通过`对象.属性`或`对象.setXxx()`进行赋值

* 示例3

```java
public class OrderTest {
    public static void main(String[] args) {
        Order order=new Order();
        System.out.println(order.orderId);

    }
}

class Order{
    String orderId="33";
    {
        orderId="44";
    }
    //String orderId="33";在这里显式初始化成员变量则输出的是33
    //String orderId;//仅仅只是声明变量，那么声明无论在代码块的上下位置，输出都是44
}
```

* 示例4

```java
public class Test{
    public static void main(String[] args){
        Son s1 = new Son();
        System.out.println("-----------------------------");
        Son s2 = new Son("子类");
    }
}
class Father{
    private int a = getNumber();
    private String info;
    {
        System.out.println("Father(1)");
    }
    Father(){
        System.out.println("Father()无参构造");
    }
    Father(String info){
        this.info = info;
        System.out.println("Father(info)有参构造");
    }
    private int b = getNumber();
    {
        System.out.println("Father(2)");
    }

    public int getNumber(){
        System.out.println("Father:getNumber()");
        return 1;
    }
}
class Son extends Father{
    private int a = getNumber();
    {
        System.out.println("Son(1)");
    }
    private int b = getNumber();
    {
        System.out.println("Son(2)");
    }
    public Son(){
        System.out.println("Son()：无参构造");
    }
    public Son(String info){
        super(info);
        System.out.println("Son(info)：有参构造");
    }
    public int getNumber(){
        System.out.println("Son:getNumber()");
        return 1;
    }
}
```

输出：

```text
Son:getNumber()
Father(1)
Son:getNumber()
Father(2)
Father()无参构造
Son:getNumber()
Son(1)
Son:getNumber()
Son(2)
Son()：无参构造
-----------------------------
Son:getNumber()
Father(1)
Son:getNumber()
Father(2)
Father(info)有参构造
Son:getNumber()
Son(1)
Son:getNumber()
Son(2)
Son(info)：有参构造
```

>这里第一行是执行了父类中的属性显式赋值，调用的是子类的`getNumber`，因为子类对该方法进行了重写，当前的this指向的是正在创建的对象，即Son，当然就调用子类中的`getNumber`

* 代码块的作用：
  1. 静态代码块：给(类变量)静态属性初始化赋值，也同时可能会进行读取配置文件等操作
  2. 非静态代码块：给实例属性初始化赋值，也同时可能会进行读取配置文件等操作

## 4.24 final关键字

* final:最终的
* final 可以用来修饰的结构：类、方法、变量
* final修饰类
   1. 此类不能被其他类所继承，比如`public final class String`,System类，StringBuffer类
* final 用来修饰方法：表示此方法不能被子类重写，比如：Object类中的getClass()
* final用来修饰变量：此时的“变量”称为是一个常量
  * final修饰属性，可以考虑的赋值位置：显式初始化、代码块中初始化、构造器

    ```java
    public class FinalTest{
        final int WIDTH=0;
        final int LEFT;
        final int RIGHT;
        {
            LEFT=1;
        }
        public FinalTest(){
            RIGHT=2;
        }
        public FinalTest(int n){
            RIGHT=n;
        }

    }
    ```

  * final修饰局部变量：final修饰形参后，表明形参是一个常量，当调用此方法传递实参后，就不能再重新赋值修改

    ```java
    public class FinalTest{
        public method1(final int n){//n不可修改值
            final int num=10;//方法体内声明并初始化一个final修饰的变量
        }
    }
    ```

* static final 用来修饰属性：全局常量
* final修饰一个引用型变量，此时该变量指向的地址空间不可修改，但是指向的对象，其对象的属性可以修改
  
  ```java
  public class Something{
      public static void main(String[] args){
          Other o=new Other();
          new Something().addOne(o);
      }
      public void addOne(final Other o){
          //o=new Other();不能再次修改o的指向
          o.i++;//o.i=1;
      }
  }
  class Other{
      public int i;
  }
  ```

## 4.25 抽象类和抽象方法

### abstract关键字的使用

* abstract：抽象的
* abstract可以用来修饰的结构：类、方法
* abstract修饰类：抽象类
  * 此类不能实例化
  * 抽象类一定要有构造器，便于子类实例化时调用
  * 开发中，都会提供抽象类的子类，让子类对象实例化
* abstract修饰方法：抽象方法
  * 抽象方法只有方法的声明，没有方法体
  * 包含抽象方法的类，一定是抽象类；反之，抽象类不一定有抽象方法
  * 若子类重写了父类(包括间接父类)中的所有的抽象方法，此子类方可实例化；若子类没有重写父类(包括间接父类)中的所有的抽象方法，则此子类也是一个抽象类，需要使用abstract修饰
* 抽象类是用于一个父类它本身不需要写太多东西，并且用abstract修饰其声明的方法，让子类去实现
* 抽象类也可以是子类，其父类也可以是一个父类，所有的类默认继承`java.lang.Object`
* abstract不能用来修饰：属性、构造器等
* abstract不能用来修饰私有方法、静态方法、final修饰的方法(这种方法不能被重写)、final修饰的类(这种类不能被继承)

```java
abstract class Person{
    String name;
    int age;
    public Person(){

    }
    public Person(String name,int age){
        this.name=name;
        this.age=age;
    }
    //这不是抽象方法
    public void eat(){

    }
    //抽象方法
    public abstract void eat();
}
```

* 创建抽象类的匿名子类以及匿名子类的匿名对象

```java
abstract class Person{
    public void hello(){};
}
public class Test{
    public static void main(String[] args){
        //创建了抽象类的匿名子类，但是匿名子类的对象不是匿名的
        Person person=new Person(){
            @Override
            public void hello(){
                System.out.println("子类hello");
            }
        };
        method(person);//输出：子类hello

        //创建了抽象类的匿名子类，且匿名子类的对象也是匿名的
        method(new Person(){
            @Override
            public void hello(){
                System.out.println("子类hello");
            }
        });//输出：子类hello
    }
    public static method(Person person){
        person.hello();
    }
}
```

>匿名子类的父类不一定要是抽象类，其他的满足要求的父类都可

### 模板方法设计模式

* 抽象类体现的就是一种模板模式的设计，抽象类作为子类的通用模板，子类在抽象类的基础上进行扩展、改造，但子类总体上会保留抽象类的行为方式
* 解决的问题：
  * 当功能内部一部分实现是确定的，一部分实现是不确定的，这时可以把不确定的部分暴露出去，让子类去实现
  * 换句话说，在软件开发中实现一个算法时，整体步骤很固定、通用，这些步骤已经在父类中写好了，但是某些部分易变，可以把易变部分抽象出来，供不同子类实现，这就是一种模板模式

* 示例1：

```java
public class TemplateTest{
    public static void main(String[] args){
        SubTemplate t=new SubTemplate();
        t.spendTime();//打印所花的时间
    }
}
//计算某段代码花费的时间
abstract class Template{
    public void spendTime(){
        long start = System.currentTimeMillis();
        code();
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为："+(end-start));
    }
    public abstract void code();
}
class SubTemplate extends Template{
    @Override
    public void code(){
        //子类code函数具体的业务
    }
}
```

* 示例2：

```java
public class TemplateMethodTest{
    public static void main(String[] args){
        BankTemplateMethod btm=new DrawMoney();
        btm.process();
        BankTemplateMethod btm2=new ManageMoney();
        btm2.process();
    }
}
abstract class BankTemplateMethod{
    public void takeNumber(){
        System.out.println("取号排队");
    }
    public abstract void transact();//办理具体的业务//钩子方法
    public void evaluate(){
        System.out.println("反馈评分");
    }
    public final void process(){
        takeNumber();
        transact();//像个钩子，具体执行时，挂哪个子类，就执行哪个子类的实现代码
        evaluate();
    }
}
```

* 模板方法设计模式是编程中经常用得到的模式，各框架、类库中都有他的身影，比如：
  1. 数据库访问的封装
  2. Junit单元测试
  3. JavaWeb的Servlet中关于doGet/doPost方法调用
  4. Hibernate中模板程序
  5. Spring中JDBCTemplate、HibernateTemplate等

## 4.26 接口

* 有时必须从几个类中派生出一个子类，继承它们所有的属性和方法。但是Java不支持多重继承。有了接口，就可以得到多重继承的效果
* 有时必须从几个类中抽取一些共同的行为特征，但是它们不是像子父类那像的关系，仅仅是具有相同的行为特征而已，比如：鼠标，键盘，打印机，扫描仪，手机，移动硬盘..等都支持USB连接。现在如果把USB连接功能抽象出一个类出来，然后让其他电子配件来继承自它，明显不合适，这些电子配件只是具有USB功能，而不是USB的衍生子类
* 接口就是规范，定义的是一组规则，体现了现实实际中“如果要....则必须能”的规范思想，**继承是一个“是不是”的关系，而接口实现则是“能不能”的关系**
* **接口的本质是契约，标准，规范**，就像法律，制定好后要都遵守。

![接口举例](../images/接口的生活举例.png)

* 接口的使用：
  1. 接口使用`interface`来定义
  2. Java中，接口和类是并列的两个结构
  3. 如何定义接口，定义接口中的成员
     * JDK7以及以前，只能定义全局常量和抽象方法
       * 全局常量：`public static final`格式，但是书写时可以省略，仍认为时全局常量
       * 抽象方法：`public abstract`格式
     * JDK8,除了定义全局常量和抽象方法之外，还可以定义静态方法、默认方法
  4. 接口中不能定义构造器！！也就是说接口不可实例化
  5. Java开发中，接口通过让类去实现(implements)的方式来使用
  6. 如果实现类覆盖(重写，但严格好像没有这个说法)接口的所有抽象方法，则该类可以实例化
  7. 如果实现类没有覆盖接口中所有的抽象方法，则此实现类仍为一个抽象类
  8. Java类可以实现多个接口，弥补了Java单继承的局限性
     格式：`class AA extends BB implements CC,DD,EE`

>为什么要新增静态方法，和默认方法
>
> 1. 静态方法是为接口提供工具方法，通过`类名.static方法`无法调用，只能通过实现类的对象来调用，这一点和子类调用父类的静态方法不同
> 2. 如果接口需要新增方法，那么所有得实现类都需要去实现该新增方法，这工作量可能很大，默认方法是子类可选的实现方法，即可以不重写

* 接口的方法只能用public修饰，属性只能用public static final修饰
* 实现类调用接口中的默认方法（接口的默认方法，外部无法调用）
  `接口名.super.方法`

* 如果实现类继承了父类又实现了接口，且父类和接口中有同名的成员属性，则需要显式写明是调用的哪个
  调用父类的属性：`super.成员属性`
  调用接口的属性：`接口名.成员属性`   //接口只有静态属性

```java
interface Flyable{
    //全局常量
    public static final int MAX_SPEED=7900;
    int MIN_SPEED=1;

    //抽象方法
    public abstract void fly();
    void stop();//也是抽象方法，省略了public abstract
}
public class InterfaceTest{
    public static void main(String[] args){
        System.out.println(Flyable.MAX_SPEED);
        Flyable.MIN_SPEED=2;//此处这样写会报错，无法编译，此成员属性默认是全局常量
    }
}

abstract class Plane implements Flyable{
    @Override
    public void fly(){
        System.out.println("fly");
    }
    //因为没有实现stop这个抽象方法，所有该类只能仍是一个抽象类，类修饰符abstract必须加上，除非实现了所有的抽象方法
}
```

* 类和类之间是继承关系，类和接口之间是实现功能的关系，接口和接口是继承关系，而且可以多继承
  当一个接口继承了多个接口后，类实现该接口时，就需要把该接口所继承的所有接口功能都要实现(当此类是抽象类时不用全部实现，但只要不是抽象类，就必须全部实现)

  ```java
  interface AA{
      void method1();
  }
  interface BB{
      void method2();
  }
  interface CC extends AA,BB{

  }
  class DD implements CC{
      @Override
      public void method1(){
          //方法内容
      }

      @Override
      public void method2(){
          //方法内容
      }
  }
  ```

* **接口的具体使用，体现了多态性**
* 接口，实际上可以看作是一种规范
* 开发中，面向接口编程

>抽象类和接口有哪些异同？

* 创建接口的匿名实现类以及匿名实现类的匿名对象
  >与抽象类实现匿名子类以及匿名子类的匿名对象相似

### 接口的应用

* 代理模式
  代理模式是Java开发中使用较多的一种设计模式。代理设计就是为其他对象提供一种代理以控制对这个对象的访问
  就好比，租客找房，但是不是直接找房东，而是去找房屋中介

```java
public class NetWorkTest{
    public static void main(String[] args){
        Server server=new Server();
        ProxyServer proxyServer=new ProxyServer(server);
        proxyServer.browse();
    }
}
interface NetWork{
    public void browse();
}
//被代理类
class Server implements NetWork{
    @Override
    public void browse(){
        System.out.println("真实的服务器访问网络");
    }
}
//代理类
class ProxyServer implements NetWork{
    private NetWork work;
    public ProxyServer(NetWork work){//这里传入的实际上是被代理类,也说明了接口的实现在使用上也体现了多态性
        this.work=work;
    }
    public void check(){
        System.out.println("联网之前的检查工作");
    }
    @Override
    public void browse(){
        check();
        work.browse();//由于传入的实际上是被代理类，所以执行的也应是被代理类的方法
    }
}
```

> 以上是一个静态代理的实例，专门为一个业务来写了一个代理类

### 接口的应用：代理模式

* 应用场景：
  1. 安全代理：屏蔽对真实角色的直接访问
  2. 远程代理：通过代理类处理远程方法调用(RMI)
  3. 延迟加载：先加载轻量级的代理对象，真正需要再加载真实对象

* 分类：
  1. 静态代理（静态定义代理类）
  2. 动态代理（动态生成代理类）
     JDK自带的动态代理，需要反射等知识

### Comparable接口的使用

* 像String、包装类等实现了Comparable接口，重写了compareTo(obj)方法，给出了比较两个对象大小的方式
* 像String、包装类重写了compareTo()方法以后，进行了从小到达的排列
* 重写compareTo(obj)的规则：
  1. 如果当前对象this大于形参对象obj，则返回正整数
  2. 如果当前对象this小于形参对象obj，则返回负整数
  3. 如果当前对象this等于形参对象obj，则返回零
* 也称自然排序

```java
public class CompareTest {
    public static void main(String[] args) {
        Goods[] arr = new Goods[4];
        arr[0] = new Goods("联想笔记本", 4999.90);
        arr[1] = new Goods("华为手机", 5199.99);
        arr[2] = new Goods("小米手机", 2199.99);
        arr[3] = new Goods("戴尔电脑", 4359.90);
        System.out.println(MyArrays.toString(arr));
        MyArrays.sort(arr);
        System.out.println(MyArrays.toString(arr));

        //直接用java.util.Arrays也可
        //System.out.println(Arrays.toString(arr));
        //Arrays.sort(arr);
        //System.out.println(Arrays.toString(arr));
    }
}

//排序工具类
class MyArrays {
    public static void sort(Object[] objects) {//冒泡排序
        for (int i = objects.length; i > 0; i--) {
            boolean isOrder = true;
            for (int j = 1; j < i; j++) {
                Comparable comparable1 = (Comparable) objects[j - 1];
                if (comparable1.compareTo(objects[j]) > 0) {
                    Object temp = objects[j - 1];
                    objects[j - 1] = objects[j];
                    objects[j] = temp;
                    isOrder = false;
                }
            }
            if (isOrder) {
                return;
            }
        }
    }

    public static String toString(Object[] arr) {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            stringBuilder.append(arr[i].toString());
            if (i != arr.length - 1) {
                stringBuilder.append(", ");
            }
        }
        return String.valueOf(stringBuilder.append("]"));
    }
}

//商品类
class Goods implements Comparable {
    public String name;
    public double price;

    public Goods(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public int compareTo(Object o) {
        Goods otherGoods = (Goods) o;
        return Double.compare(this.price, otherGoods.price);
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

```

### Comparator接口的使用

* 当元素的类型没有实现java.lang.Comparable接口而不方便修改代码，或者实现了Comparable接口的排序规则不适合当前的操作，那么可以考虑使用Comparator的对象进行排序，对多个对象进行整体排序
* 重写`compare(Object o1,Object o2)`方法，比较o1与o2的大小，如果方法返回正整数,则表示o1大于o2；如果返回0,表示相等；如果返回负整数，表示o1小于o2
* 可以将Comparator传递给排序方法中，比如`sort(Object[] arr,Comparator cmp)`
* 还可以使用Comparator来控制某些数据结构的顺序，比如给集合类的对象提供排序

```java
public class ComparatorTest {
    public static void main(String[] args) {
        Goods[] arr = new Goods[4];
        arr[0] = new Goods("联想笔记本", 4999.90);
        arr[1] = new Goods("华为手机", 5199.99);
        arr[2] = new Goods("小米手机", 2199.99);
        arr[3] = new Goods("戴尔电脑", 4359.90);
        //comparable自然排序
        //System.out.println(MyArrays.toString(arr));
        //MyArrays.sort(arr);
        //System.out.println(MyArrays.toString(arr));
        //直接用java.util.Arrays也可
        //System.out.println(Arrays.toString(arr));
        //Arrays.sort(arr);
        //System.out.println(Arrays.toString(arr));

        //comparator定制排序
        Comparator comparator=new GoodsComparator();
        System.out.println(MyArrays.toString(arr));
        MyArrays.sort(arr,comparator);
        System.out.println(MyArrays.toString(arr));
        //直接用java.util.Arrays也可
        //System.out.println(Arrays.toString(arr));
        //Arrays.sort(arr,comparator);
        //System.out.println(Arrays.toString(arr));
    }
}

//排序工具类
class MyArrays {
    public static void sort(Object[] objects) {//冒泡排序
        for (int i = objects.length; i > 0; i--) {
            boolean isOrder = true;
            for (int j = 1; j < i; j++) {
                Comparable comparable1 = (Comparable) objects[j - 1];
                if (comparable1.compareTo(objects[j]) > 0) {
                    Object temp = objects[j - 1];
                    objects[j - 1] = objects[j];
                    objects[j] = temp;
                    isOrder = false;
                }
            }
            if (isOrder) {
                return;
            }
        }
    }

    public static void sort(Object[] objects, Comparator comparator) {//冒泡排序
        for (int i = objects.length; i > 0; i--) {
            boolean isOrder = true;
            for (int j = 1; j < i; j++) {
                Comparable comparable1 = (Comparable) objects[j - 1];
                if (comparator.compare(objects[j - 1], objects[j]) > 0) {
                    Object temp = objects[j - 1];
                    objects[j - 1] = objects[j];
                    objects[j] = temp;
                    isOrder = false;
                }
            }
            if (isOrder) {
                return;
            }
        }
    }

    public static String toString(Object[] arr) {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            stringBuilder.append(arr[i].toString());
            if (i != arr.length - 1) {
                stringBuilder.append(", ");
            }
        }
        return String.valueOf(stringBuilder.append("]"));
    }
}

//商品类
class Goods implements Comparable {
    public String name;
    public double price;

    public Goods(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public int compareTo(Object o) {
        Goods otherGoods = (Goods) o;
        return Double.compare(this.price, otherGoods.price);
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

//定制的商品价格比较类，从大到小
class GoodsComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof Goods && o2 instanceof Goods) {
            Goods goods1 = (Goods) o1;
            Goods goods2 = (Goods) o2;
            return -Double.compare(goods1.price, goods2.price);
        } else {
            throw new RuntimeException("数据类型不一致");
        }
    }
}
```

## 4.27 内部类

* Java中允许将一个类A声明在另一个类B中，则类A就是内部类，类B称为外部类
* 内部类的分类：成员内部类(静态、非静态)；局部内部类(方法内、代码块内、构造器内)
* 成员内部类：
  * 作为外部类的成员：可以调用外部类的结构;可以被static修饰;可以被4种权限修饰
  * 作为一个类，可以定义属性、方法、构造器等；可以被继承；可以被final修饰；可以被abstract修饰
* 对于静态成员内部类，通过`外部类.内部类`方式来使用；非静态的成员内部类，需要外部类进行实例化后，以对象使用属性的方式访问内部类，内部类实例化
* 在内部类中，只能直接使用外部类的静态成员
* 非静态内部类里面不能声明静态成员
* 局部内部类没有静态的，也无法被权限修饰符修饰
* 局部内部类只有final ，abstract这两个词修饰
* 局部内部类无法在外部类的其他成员中使用

```java
public class InnerClassTest {
    public static void main(String[] args) {
        //静态的成员内部类
        Person.Dog dog = new Person.Dog();
        dog.show();

        //非静态的成员内部类
        Person p = new Person();
        Person.Bird bird = p.new Bird("3");

        Comparable comparable=p.getCompareable();
        comparable.compareTo();//在外面使用局部内部类的方法：让局部内部类继承一个父类或者实现一个接口，然后局部内部类重写需要调用的方法
    }
}

class Person {
    static String name = "2";
    int age;

    public void eat() {
        System.out.println("person ,eat");
    }

    public class Bird {
        String name = "1";

        public Bird(String name) {
            System.out.println(Person.name);
        }

        public void sing() {
            System.out.println("bird");
            Person.this.eat();//调用外部类的非静态属性
            eat();
            System.out.println(age);
        }

        public void display(String name) {
            System.out.println(name);//方法形参
            System.out.println(this.name);//内部类的属性
            System.out.println(Person.this.name);//外部类的属性
        }
    }

    static class Dog {
        String name;
        int age;

        public void show() {
            System.out.println(name + "is a dog");
            //eat();静态内部类可以直接调用外部类中的静态方法，但是不能调用外部类中的非静态方法
        }
    }

    static {//静态代码块
        //局部内部类
        /*static */
        class B {//局部内部类没有静态的

        }
    }

    public void method(){
        /*static*/ class A{//局部内部类

        }
    }

    public Comparable getCompareable() {
        int i=1;
        //局部内部类
        return new Comparable() {
            @Override
            public void compareTo() {
                //i=2;//报错,在局部内部类中使用方法体的局部变量，则变量自动变成了final
                System.out.println(i);//局部内部类中只能使用外部类方法体中的final局部变量
                System.out.println("在方法里新建了匿名内部类(它是一个接口的实现)的匿名对象");
            }
        };
    }

}

interface Comparable {
    public abstract void compareTo();
}
```

```java
public class Test{
    public static void main(String[] args) {
        //静态内部类的静态成员直接使用
        System.out.println(Outer.Inner2.age);
        Outer.Inner2.innerMethod2();

        //静态内部类的非静态成员需要静态内部类实例化后使用
        Outer.Inner2 inner2=new Outer.Inner2();
        System.out.println(inner2.name);
        inner2.innerMethod();

        //非静态内部类必须实例化后才能使用非静态成员
        //非静态内部类里面不能声明静态成员
        Outer outer=new Outer();
        Outer.Inner inner=outer.new Inner();
        System.out.println(inner.name);
        inner.innerMethod();

    }
}

class Outer{
    String name;
    static int age;
    class  Inner{
        String name;
        //static int age;//错误，非静态内部类不能有静态成员属性

        public void innerMethod(){
            System.out.println(name);
            System.out.println(Outer.age);
            //外部类的非静态成员无法直接使用，必须实例化外部类后使用
            Outer outer=new Outer();
            System.out.println(outer.name);
        }
        //public static void innerMethod2(){}//错误，非静态内部类不能有静态成员方法
    }
    public static void outMethod1(){
        //new Outer.Inner();//外部类的静态方法不能使用非静态内部类
        Outer outer=new Outer();
        Outer.Inner inner= outer.new Inner();//正确
        inner.innerMethod();

        Inner2.innerMethod2();//直接使用静态内部类的静态方法
        System.out.println(Inner2.age);//直接使用静态内部类的静态属性
        Inner2 inner2=new Inner2();
        inner2.innerMethod();//静态内部类的非静态方法使用之前，静态内部类需要先实例化
        System.out.println(inner2.name);//静态内部类的非静态属性使用之前，静态内部类需要先实例化

    }

    public void outMethod2(){
        Inner inner=new Inner();//正确，因为外部类的非静态方法可以使用非静态内部类
        inner.innerMethod();

        //外部类的非静态方法调用静态内部类是允许的
        //静态内部类的静态成员直接调用：内部类名.静态成员
        //静态内部类的非静态成员需要内部类实例化调用：new 静态内部类名().非静态成员
    }

    static class Inner2{
        String name;
        static int age;
        public void innerMethod(){
            System.out.println(name);
            System.out.println(Outer.age);
            //外部类的非静态成员无法直接使用，必须实例化外部类后使用
            Outer outer=new Outer();
            System.out.println(outer.name);
        }
        public static void innerMethod2(){

        }
    }
}
```

### 匿名类

* 匿名(内部)类需要在声明之时就实例化，不然之后就无法实例化了，因为它没有名字
  格式：

  ```java
  new 父类|接口(参数列表){
      @Override
      //子类重写父类|实现接口的方法
  }
  ```

### 修饰符一起使用问题

|           | 外部类 | 成员变量 | 代码块 | 构造器 | 方法 | 局部变量 |
| --------- | ------ | -------- | ------ | ------ | ---- | -------- |
| public    | √      | √        | ×      | √      | √    | ×        |
| protected | ×      | √        | ×      | √      | √    | ×        |
| private   | ×      | √        | ×      | √      | √    | ×        |
| static    | ×      | √        | √      | ×      | √    | ×        |
| final     | √      | √        | ×      | ×      | √    | √        |
| abstract  | √      | ×        | ×      | ×      | √    | ×        |
| native    | ×      | ×        | ×      | ×      | √    | ×        |

不能和abstract一起使用的修饰符？

（1）abstract和**final**不能一起修饰**方法和类**

（2）abstract和**static**不能一起修饰**方法**

（3）abstract和**native**不能一起修饰**方法**

（4）abstract和**private**不能一起修饰**方法**

static和final一起使用：

（1）修饰方法：可以，因为都不能被重写

（2）修饰成员变量：可以，表示静态常量

（3）修饰局部变量：不可以，static不能修饰局部变量

（4）修饰代码块：不可以，final不能修改代码块

（5）修饰内部类：可以一起修饰成员内部类，不能一起修饰局部内部类

## 4.28 枚举

### 4.28.1 概述

某些类型的对象是有限的几个，这样的例子举不胜举：

* 星期：Monday(星期一)......Sunday(星期天)
* 性别：Man(男)、Woman(女)
* 月份：January(1月)......December(12月)
* 季节：Spring(春节)......Winter(冬天)
* 支付方式：Cash（现金）、WeChatPay（微信）、Alipay(支付宝)、BankCard(银行卡)、CreditCard(信用卡)
* 员工工作状态：Busy（忙）、Free（闲）、Vocation（休假）
* 订单状态：Nonpayment（未付款）、Paid（已付款）、Fulfilled（已配货）、Delivered（已发货）、Checked（已确认收货）、Return（退货）、Exchange（换货）、Cancel（取消）

枚举类型本质上也是一种类，只不过是这个类的对象是固定的几个，而不能随意让用户创建。

在JDK1.5之前，需要程序员自己通过特殊的方式来定义枚举类型。

在JDK1.5之后，Java支持enum关键字来快速的定义枚举类型。

### 4.28.2 JDK1.5之前

在JDK1.5之前如何声明枚举类呢？

* 构造器加private私有化
* 本类内部创建一组常量对象，并添加public static修饰符，对外暴露这些常量对象

示例代码：

```java
public class TestEnum {
    public static void main(String[] args) {
        Season spring = Season.SPRING;
        System.out.println(spring);
    }
}
class Season{
    public static final Season SPRING = new Season();
    public static final Season SUMMER = new Season();
    public static final Season AUTUMN = new Season();
    public static final Season WINTER = new Season();

    private Season(){
    }

    public String toString(){
        if(this == SPRING){
            return "春";
        }else if(this == SUMMER){
            return "夏";
        }else if(this == AUTUMN){
            return "秋";
        }else{
            return "冬";
        }
    }
}
```

### 4.28.3 JDK1.5之后

语法格式：

```java
【修饰符】 enum 枚举类名{
    常量对象列表
}

【修饰符】 enum 枚举类名{
    常量对象列表;

    其他成员列表;
}
```

* 此时枚举类默认继承了java.lang.Enum

示例代码：

```java
public class TestEnum {
    public static void main(String[] args) {
        Season spring = Season.SPRING;
        System.out.println(spring);
    }
}
enum Season{
    SPRING,SUMMER,AUTUMN,WINTER
}
```

示例代码：

```java
public class TestEnum {
    public static void main(String[] args) {
        Season spring = Season.SPRING;
        System.out.println(spring);
    }
}
enum Season{
    SPRING("春"),SUMMER("夏"),AUTUMN("秋"),WINTER("冬");
    private final String description;

    private Season(String description){
        this.description = description;
    }

    public String toString(){//需要手动编写，无法使用Generate toString()...
        return description;
    }
}
```

枚举类的要求和特点：

* 枚举类的常量对象列表必须在枚举类的首行，因为是常量，所以建议大写。
* 如果常量对象列表后面没有其他代码，那么“；”可以省略，否则不可以省略“；”。
* 编译器给枚举类默认提供的是private的无参构造，如果枚举类需要的是无参构造，就不需要声明，写常量对象列表时也不用加参数，
* 如果枚举类需要的是有参构造，需要手动定义private的有参构造，调用有参构造的方法就是在常量对象名后面加(实参列表)就可以。
* 枚举类默认继承的是java.lang.Enum类，因此不能再继承其他的类型。
* JDK1.5之后switch，提供支持枚举类型，case后面可以写枚举常量名。
* 枚举类型如有其它属性，建议（**不是必须**）这些属性也声明为final的，因为常量对象在逻辑意义上应该不可变。

* 用enum声明的枚举类型，不能用extends其他类型，因为它有一个默认的直接父类java.lang.Enum

### 4.28.4 枚举类型常用方法

```java
1.toString(): 默认返回的是常量名（对象名），可以继续手动重写该方法！
2.name():返回的是常量名（对象名） 【很少使用】
3.ordinal():返回常量的次序号，默认从0开始
4.values():返回该枚举类的所有的常量对象，返回类型是当前枚举的数组类型，是一个静态方法
5.valueOf(String name)：根据枚举常量对象名称获取枚举对象
```

### 4.28.5枚举类型实现接口

```java
public class EnumTest {
    public static void main(String[] args) {
        Season[] seasons=Season.values();
        for(int i=0;i<seasons.length;i++){
            seasons[i].show();
        }
    }
}

enum Season implements Info{
    SPRING("春天","出暖花开"){
        @Override
        public void show(){
            System.out.println("春天踏青");
        }
    },
    SUMMER("夏天","夏日炎炎"){
        @Override
        public void show(){
            System.out.println("夏天去冲浪");
        }
    },AUTUMN("秋天","秋高气爽"){
        @Override
        public void show(){
            System.out.println("秋天收粮食");
        }
    },
    WINTER("冬天","寒风凌冽"){
        @Override
        public void show(){
            System.out.println("冬天滑雪");
        }
    };

    String name;
    String desc;
    private Season(String name, String desc){
        this.name=name;
        this.desc=desc;
    }
}

interface Info{
    void show();
}
```

示例代码：

```java
public class TestEnum {
    public static void main(String[] args) {
        Season[] values = Season.values();
        for (int i = 0; i < values.length; i++) {
        switch(values[i]){
            case SPRING:
                System.out.println(values[i]+":春暖花开，万物复苏");
                break;
            case SUMMER:
                System.out.println(values[i]+":百花争艳，郁郁葱葱");
                break;
            case AUTUMN:
                System.out.println(values[i]+":菊桂飘香，百树凋零");
                break;
            case WINTER:
                System.out.println(values[i]+":梅花独开，大地一色");
                break;
            }
        }
    }
}
enum Season{
    SPRING,SUMMER,AUTUMN,WINTER
}
```

## 4.29 注解

* Java内置的三个注解：
  1. `@SuppressWarnings`
   使用位置：在类、方法、变量等上面都可以，凡是需要抑制警告的地方都可以使用
   作用：抑制警告（不一定报错）
  2. `@Override`
   使用位置：子类重写父类的成员方法时
   作用：表示该方法为重写的方法
  3. `@Deprecated`：
  使用位置：在类、方法、变量等上面都可以
  作用：表示该类、方法、变量已经废弃，有更好的选择

* 元注解
  1. @Target：标识该注解能够修饰的结构（注解的程序元素）
  2. @Retention：标记该注解的生命周期
    RetenionPolicy.SOUCE:在源代码中可见，执行编译后就
    RetenionPolicy.CLASS:默认行为，在执行时不会被加载到虚拟机中
    RetenionPolicy.RUNTIME:在运行时该注解也会被加载
  3. @Documented：表示所修饰的注解在被javadoc解析时能够识别保留到文档中，默认不保留
  4. @Inherited：指明如果父类有该注解，子类将继承该注解

* 类的注解可以通过反射来获取到注解信息
* JDK8.0的新特性：
  1. 支持重复注解
     多了个元注解@Repeatable,成员值为支持多注解的自定义注解
  2. 类型注解
     ElementType.TYPE_PARAMETER,表示该注解能够写在类型变量的声明语句中(如，泛型声明)
     ElementType.TYPE_USE,表示该注解能写在使用类型的任何语句中