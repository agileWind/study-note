# 第二章 如何运行一个Java应用程序

[TOC]

## 2.1 步骤

* 编辑：写代码
  结果：保存为`.java`的源文件

* 编译
  工具：javac.exe编译工具
  作用：把.java源文件的代码编译为JVM能够认识的字节码文件
  结果：.class的字节码文件
  格式：`javac 源文件名.java`
  该命令可以在cmd的命令行窗口运行，或者IDE中运行

* 运行
  工具：java.exe
  格式：`java 主类名`
  > 主类是指main方法所在的类（如果有多个类，这个主类还可以用public来修饰，但是一旦用public修饰，则该类须有main方法，且文件名需要一致）
  >如果只有一个类，且没有使用public修饰，那文件名和这个主类名不需要保持一致

## 2.2 格式要求

面向对象，所以离不开“类”

```java
class 类{
    方法{
        语句;
    }
}
```

* `class`是小写的

* 主函数main的格式是`public static void main(String[] args)`

## 2.3 可能出现的问题

### 乱码

* 原因：源代码的保存格式和编译格式不统一

* 解决：
  1. javac -coding "charsetName" 在执行编译命令的时候指定编码
  2. 编辑的时候保存的格式为与系统编译的编码格式相同
  3. 修改cmd命令窗的编译方式，较难
  > ANSI是说使用操作系统默认的编码方式，中文操作系统默认编码是GBK

### 文件名类名导致找不到类

* 源代码文件中只有一个class,源代码文件名和类名不需要一致
* 源代码文件中有多个class（多个类）,当有public修饰一个类时，这个类的名字与源代码文件名必须一致
* 当执行`java className`来执行java程序时，要求该类有main方法

### 包名导致找不到类

* javac -d 文件夹路径 源代码名.java，这个命令可以指定编译后的文件放在哪里，javac -d . ChairMan.java表示放在与ChairMan.java相同的父目录下，当然这也不是绝对的正确说法，仅仅只是在没有指定包名的情况下，如果有包名，则会在这个源代码文件夹下建立包名文件夹，然后把class文件放在包名文件夹下

  ```bash
  #如果包名a.b,，两组命令对应为
  javac -d . ChairMan.java
  java a.b.ChairMan

  #如果包名为a.b,想把class放在源代码文件下相同位置，则
  javac -d ../../ ChairMan.java
  java -cp ../../ a.b.ChairMan
  ```

* 有些IDE工具有自己设定的源代码文件夹结构和预设的编译后class文件放置的文件夹地址，如果将这些源代码文件拿来在命令行执行，则会有报错的可能性；另外，IDE一般是使用utf-8来保存源文件，所以在控制台输出的时候中文会乱码

* 在有包名a.b的情况下，直接执行javac ChairMan.java也会在当前源代码的相同父文件夹下创建class文件，但是执行java ChairMan会报错，执行java a.b.ChairMan也报错，必须执行java -cp ../../ a.b.ChairMan才可成功。
