- String是引用型数据类型

- String类型的变量被赋值时，使用的是一对双引号””

- String类型的数据可有0个或者1个以上(包括1个)char类型的字符，即“123中”

- char类型的数据不能有0个字符，char类型变量有且仅有一个字符

```java
char ch='';//编译不通过，会报错
String s="";//编译通过
```

- String可以与8种基本数据类型的变量进行连接运算：+

```java
int number=1001;
String s="学号：";
String info=s+number;//info为"学号：1001"
String info1=number+s;//info1为"1001学号为："
```

- 多个不同的数据类型变量进行+运算，只要其中一个变量时String类型，结果则为String类型

```java
System.out.println('1'+'2');//控制台并不会输出12，而是99，因为这两个都是char类型，不是String类型，不会连接运算
```

```text
String str=4;//报错
String str2=3.5f+"";//str2="3.5";

short s=5;
s=s-2;//报错，因为2是int型常量，右边结果为int型，s却为short型，需要强制转换

byte b=3;
b=b+4;//抛出异常
b=(byte)(b+4);//编译通过

float d=.314F;//编译通过，小数点前面为空则表示为0

byte b=5;
short s=3;
short t=s+b;//抛出异常，s+b为int型变量，需要强制转换
```

- boolean类型转换成String类型，则为"true"或者”false”

