# 第三章 Java数组

[TOC]

## 3.1 数组的概述

* 数组，是多个相同类型数据按一定顺序排列的集合，使用一个名字命名，并通过编号的方式对这些数据进行统一管理。

* 数组的相关概念：
  1. 数组名
  2. 元素
  3. 角标，下标，索引
  4. 数组的长度

* 数组的特点：有序排列

* 数组本身是**引用数据类型**,数组的元素既可以是基本数据类型，也可以是引用数据类型

* 创建数组对象会在内存中开辟**一整块连续的空间**，而数组名中的引用的是这块连续空间的首地址

* 数组的**长度一旦确定，就不能修改**

* 数组的分类
  1. 按照维数：一维数组、二维数组。。。
  2. 按照数组元素的类型：基本数据类型元素的数组、引用数据类型元素的数组

## 3.2 一维数组

### 3.2.1 一维数组的声明和初始化

```java
int num=10;//声明
num=10;//初始化
int id=1001;//声明+初始化

int[] ids;//声明
ids=new int[]{1001,1002,1003,1004};//静态初始化

String[] names=new String[5];//动态初始化

String[] names={"tom","jack"};//类型推断，这也是正确的,但是这种写法如果分开写，就是错误的
```

* 静态初始化：数组的初始化和数组元素的赋值操作同时进行

* 动态初始化：数组的初始化和数组元素的赋值操作分开进行

* 总结：数组一旦初始化完成，其长度就确定了(数组是长度确定的，且不可更改)

* 定义数组时，左边的`[]`位置这两种写法都可以
  1. `int[] i={1,2,3};`
  2. `int j[]={3,4,5};`
   > 也就是说`[]`放在数组变量名的前面都可以，但最好放在变量名前
* 错误写法：//todo

```java
int[] arr1=new int[];
int[5] arr2=new int[5];
int[] arr3=new int[3]{1,2,3};

int[] arr4;
arr4={1,2,3};//以上全部都是错误的写法
```

### 3.2.2 一维数组元素的默认初始化值

> 讨论默认初始化值是在声明和初始化数组的时候采用动态初始化的前提下，静态就没有讨论必要了，静态就直接在定义的时候给元素赋值了

* 数组元素是**整型(byte,short,int,long)**的，数组元素默认初始化值为**0**
* 数组元素是**浮点型(float,double),数组元素默认初始化值为0.0**
* 数组元素是**char型，数组元素默认初始化值为ASCII表中的第一个字符，即char ch=0;'\u0000';'\0'**
* 数组元素是**boolean型，数组元素默认初始化值为false**
* 数组元素是**引用类型**，数组元素默认初始化值为`null`,注意不是NULL，java中的关键字都是小写的

### 3.2.3 一维数组的操作

#### 获取指定位置的元素

* 通过数组的角标(或索引)的方式来调用元素

* 数组的角标从0开始，到数组的长度-1结束
  > java基本上角标都是从0开始，涉及到数据库可能是从1计数

```java
int[] names=new int[3];
names[0]="张三";
names[1]="李四";
names[2]="王五";
//javac可以编译通过，但是java命令就会报错
//编译只是转成字节码文件，但是真正是否越界，只有等执行才知道
names[3]="java";
```

#### 获取一维数组的长度

* 格式：`arr.length`

```java
int[] arr=new int[]{1,2,5};
int len=arr.length;//len为数组arr的长度
```

#### 遍历一维数组

//todo

```java
int[] arr=new int[]{1,2,5};
for(int i=0;i<arr.length;i++){
    System.out.println(arr[i]);
}
```

### 3.2.4 一维数组的内存解析

#### 1、一个数组内存图

```java
public static void main(String[] args) {
    int[] arr = new int[3];
    System.out.println(arr);//[打印地址信息(其实不是地址值，目前简单当作是地址值)：I@5f150435
    
    //数组变量的值确实是存的地址值，但是println不一定就是打印输出地址值
    char[] chArr=new char[]{'a','b','c'};
    System.out.println(chArr);//控制台输出的不是chArr的数组元素首地址值，而是遍历输出每个数组元素的值，结果是:abc
}

```

![数组内存分析图](../images/数组内存图1.jpg)

> 思考：打印arr为什么是[I@5f150435，它是数组的地址吗？
>
> 答：它不是数组的地址。
>
> 问？不是说arr中存储的是数组对象的首地址吗？
>
> 答：arr中存储的是数组的首地址，但是因为数组是引用数据类型，打印arr时，会自动调用arr数组对象的toString()方法，默认该方法实现的是对象类型名@该对象的hashCode()值的十六进制值。
>
> 问？对象的hashCode值是否就是对象内存地址？
>
> 答：不一定，因为这个和不同品牌的JVM产品的具体实现有关。例如：Oracle的OpenJDK中给出了5种实现，其中有一种是直接返回对象的内存地址，但是OpenJDK默认没有选择这种方式。

#### 2、数组下标为什么是0开始

因为第一个元素距离数组首地址间隔0个单元。

#### 3、两个数组内存图

```java
public static void main(String[] args) {
    int[] arr = new int[3];
    int[] arr2 = new int[2];
    System.out.println(arr);
    System.out.println(arr2);
}
```

![两个数组内存图解](../images/数组内存图2.jpg)

#### 4、两个变量指向一个数组

```java
public static void main(String[] args) {
    // 定义数组，存储3个元素
    int[] arr = new int[3];
    //数组索引进行赋值
    arr[0] = 5;
    arr[1] = 6;
    arr[2] = 7;
    //输出3个索引上的元素值
    System.out.println(arr[0]);
    System.out.println(arr[1]);
    System.out.println(arr[2]);
    //定义数组变量arr2，将arr的地址赋值给arr2
    int[] arr2 = arr;
    arr2[1] = 9;
    System.out.println(arr[1]);
}

```

 ![两个变量指向一个数组图解](../images/数组内存图3.jpg)

## 3.3 多维数组

* Java语言里支持多维数组的语法

* 如果说可以一维数组当成线性图形，那么二维数组就像一个表格

* 理解二维数组：可以堪称一个一维数组，只不过数组的元素类型是数组类型，即元素里存的是引用数据类型：数组，其实从底层运行机制来看，并没有多维数组

### 3.3.1 二维数组的声明和初始化

```java
//静态初始化
int[][] arr=new int[][]{{1,2,3},{4,5},{6,7,8}};

//动态初始化
String[][] arr2=new String[3][2];//申请了一个一维数组，有3个元素，然后每个元素里面又是一个数组B，B有2个元素
//或者
String[][] arr3=new String[3][];
```

其他正确的写法

```java
int[] arr4[]=new int[]{{1,2,3},{4,5},{6,7,8}};
int[] arr5[]={{1,2,3},{4,5},{6,7,8}};
//以上写法也可,或者两个[]都放在变量名之后
```

错误的情况

```java
String[][] arr4=new String[][4];
String[][] arr5=new String[][];
String[][] arr6=new int[3][2]{{1,2},{3,4},{5,6}};
//以上写法全部都是错误的
```

### 3.3.2 二维数组元素的默认初始化值

> 讨论默认初始化值是在声明和初始化数组的时候采用动态初始化的前提下，静态就没有讨论必要了，静态就直接在定义的时候给元素赋值了
> 外层元素指的是一维数组的元素，内层元素指的是二维数组的元素（内层元素的默认初始化值和一维数组情况一样）

```java
int[][] arr=new int[4][3];
int[][] arr1=new int[4][];
System.out.println(arr[0]);//地址值
System.out.println(arr[0][0]);//0
System.out.println(arr1[0]);//null
System.out.println(arr1[0][0]);//报错异常，空指针
```

### 3.3.3 二维数组的操作

#### 获取二维数组指定位置的元素

```java
int[][] arr1=new int[][]{{1,2,3},{4,5},{6,7,8}};
String[][] arr2=new String[3][2];
String[][] arr3=new String[3][];
System.out.println(arr1[0][1]);//2
System.out.println(arr2[1][1]);//null,说明一维数组已经开辟了，并且每个元素指向了一个一维数组，只是这个数组没有赋值
System.out.println(arr3[1][0]);//报错异常，空指针，说明一维数组已经开辟了，但是元素并没有指向

arr3[1]=new String[4];//再输出的话，为null
```

#### 获取二维数组的长度

* 格式：`arr.length`

```java
int[][] arr=new int[][]{{1,2,3},{4,5},{6,7,8}};
int len=arr.length;//len为数组arr的长度,值为3
int len1=arr[1].length;//值为2
```

#### 遍历二维数组

//todo

```java
int[][] arr=new int[][]{{1,2,3},{4,5},{6,7,8}};
for(int i=0;i<arr.length;i++){
    for(int j=0;j<arr[i].length;j++){
        System.out.print(arr[i][j]+" ");
    }
    System.out.println();
}
```

### 3.3.4 二维数组的内存解析

#### 1、示例一

```java
int[][] arr = {
    {1},
    {2,2},
    {3,3,3},
    {4,4,4,4},
    {5,5,5,5,5}
};
```

![二维数组内存分析示例1](../images/二维数组内存分析1.png)

#### 2、示例二

```java
//1、声明二维数组，并确定行数和列数
int[][] arr = new int[4][5];

//2、确定元素的值
for (int i = 0; i < arr.length; i++) {
    for (int j = 0; j < arr.length; j++) {
        arr[i][j] = i + 1;
    }
}
```

![二维数组内存分析示例2](../images/二维数组内存分析2.png)

#### 3、示例三

```java
//1、声明一个二维数组，并且确定行数
//因为每一行的列数不同，这里无法直接确定列数
int[][]  arr = new int[5][];

//2、确定每一行的列数
for(int i=0; i<arr.length; i++){
/*
arr[0] 的列数是1
arr[1] 的列数是2
arr[2] 的列数是3
arr[3] 的列数是4
arr[4] 的列数是5
*/
  arr[i] = new int[i+1];
}

//3、确定元素的值
for(int i=0; i<arr.length; i++){
    for(int j=0; j<arr[i].length; j++){
        arr[i][j] = i+1;
    }
}
```

![二维数组内存分析示例3](../images/二维数组内存分析3.png)

## 3.4 数组操作的常见异常

### 数组越界异常

观察一下代码，运行后会出现什么结果。

```java
public static void main(String[] args) {
    int[] arr = {1,2,3};
    System.out.println(arr[3]);
}
```

创建数组，赋值3个元素，数组的索引就是0，1，2，没有3索引，因此我们不能访问数组中不存在的索引，程序运行后，将会抛出 `ArrayIndexOutOfBoundsException`  数组越界异常。在开发中，数组的越界异常是**不能出现**的，一旦出现了，就必须要修改我们编写的代码。

![数组越界异常](../images/数组越界异常.jpg)

### 数组空指针异常

观察一下代码，运行后会出现什么结果。

```java
public static void main(String[] args) {
    //定义数组
    int[][] arr = new int[3][];
        System.out.println(arr[0][0]);//NullPointerException
    }
```

因为此时数组的每一行还未分配具体存储元素的空间，此时`arr[0]为null`，此时访问`arr[0][0]`会抛出`NullPointerException` 空指针异常。

![空指针异常](../images/空指针异常.jpg)

空指针异常在内存图中的表现

![空指针在内存的图解](../images/空指针在内存的图解.png)

## 3.5 数组元素的查找和排序

查找和排序是与数据结构知识相关联的，扩展到单独的章节中

扩展：[数据结构](../../DataStructure/数据结构.md)
