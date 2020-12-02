# 第六章 Java多线程

[TOC]

## 进程线程概念

* 程序(program)：为完成特定任务、用某种语言编写的一组指令的集合。即指一段静态的代码
* 进程(process)：是程序执行的一次执行过程，或是正在运行的一个程序。是一个动态的过程，有它自身的产生、存在和消亡的过程，即生命周期
* 程序是静态的，进程是动态的，进程作为资源分配的单位，系统在运行时会为每个进程分配不同的内存区域
* 线程(thread):进程可细分为线程，是一个程序内部的一条执行路径。
  1. 若一个进程同一时间并行执行多个线程，就是支持多线程的
  2. 线程作为调度和执行的单位，**每个线程拥有独立的运行栈和程序计数器**，线程切换的开销小
  3. 一个进程中的多个线程共享相同的内存单元/内存地址空间,它们从同一个堆中分配对象，可以访问相同的变量和对象。这就使得线程间通信更简便高效。但多个线程操作共享的系统资源可能会带来安全隐患(同步问题)
  > 一个Java应用程序java.exe，至少有三个线程：main()主线程，gc()垃圾回收线程，异常处理线程。如果发生异常，也可能影响主线程的运行
* 单核CPU和多核CPU
  * 单核CPU，其实是一种假的多线程，因为在一个时间单元里，它只能执行多个任务中的一个，只不过它们通过轮转(或其他的约定)来分时执行各个任务，但是由于CPU的运行效率很高，执行时间很短，所以感觉不到任务被暂停
  * 多核CPU，即处理器有多个执行单元，那么在多个时间内有不同的任务分配到了不同的核内执行

* 并行与并发
  * 并行：多个CPU同时执行多个任务。比如：多个人同时做不同的事情
  * 并发：一个CPU(采用时间片轮转)同时执行多个任务。

* 多线程的应用场景
  1. 程序需要同时执行多个任务
  2. 程序需要实现一些需要等待的任务
  3. 需要一些后台运行的程序

## 创建多线程

### 方式一：继承Thread类

* 继承Thread类
  1. 创建一个继承于Thread类的子类
  2. 重写Thread类的run()
  3. 创建Thread类的子类对象
  4. 通过此对象调用start()

```java
public class MyThread extends Thread{
    @Override
    public void run(){
        for(int i=0;i<101;i++){
            if(i%2==0)
                System.out.println(i);
        }
    }

    public static void main(String[] args) {
        MyThread myThread=new MyThread();//此行仍是主线程在完成
        myThread.start();//该行在多线程中进行，也就是说下面的代码不会等此行执行完毕，就开始执行了

        //下面的代码仍是在主线程中进行的
        //myThread.run();直接调用run方法则是在主线程中执行的，就不是多线程了，因为start方法才会启动线程
        System.out.println("hello");

        //myThread.start();想多开一个线程来执行该任务，但是对象仍是已经启动线程的对象，是不允许的，会报错IllegalThreadStateException

        MyThread myThread2=new MyThread();//重新new一个对象，然后再调用start方法是可以的
        myThread2.start();
    }
}
```

* `start()`的作用：
  1. 启动当前线程
  2. 调用当前线程的`run()`,也即被子类重写的`run()`方法

* 注意的问题：
  1. 直接调用run方法则是在主线程中执行的，就不是多线程了，因为start方法才会启动线程
  2. 想开多个线程，就要多创建几个对象，想多开一个线程来执行该任务，但是对象仍是已经启动线程的对象，是不允许的，会报错IllegalThreadStateException

* 测试Thread中的常用方法：
  1. `start()`:启动当前线程，并调用当前线程的`run()`
  2. `run()`:通常需要重写Thread类中的此方法，将创建的线程要执行的操作写在此方法中
  3. `currentThread()`:静态方法，返回执行当前代码的线程
  4. `getName()`:获取当前线程的名字
  5. `setName()`:设置当前线程的名字，取名得在线程执行之前(start()执行之前)，这是主线程下完成的
  6. `yield()`:释放当前CPU的执行权
     * 又称线程让步，暂停当前正在执行的线程，把执行机会让给优先级相同或更高的线程
     * 若队列中没有同优先级的线程，忽略此方法，接下来仍执行该线程
  7. `join()`:在线程a中调用线程b的join(),此时线程a就进入阻塞状态，直到线程b完全执行完以后，线程a才结束阻塞状态
  8. `stop()`:强制线程生命期结束，已过时的方法(不建议使用)
  9. `sleep(long millis)`:单位是毫秒,让当前线程"睡眠"指定的毫秒时间内，当前线程是阻塞的状态，等时间过去后再重写排队
  10. `isAlive`:判断当前线程是否存活

> 父类的Thread的run()没有throws抛异常，子类重写也不能有throws，那么重写的方法体内有需要进行异常处理只能选择try-catch方式

  ```java
  public MyThreadTest{
      public static void main(String[] args){
          MyThread myThread=new MyThread();
          myThread.setName("线程一");
          myThread.start();

          //给主线程命名
          Thread.currentThread().setName("主线程");
      }

  }
  ```

### 方式二：实现Runnable接口

* 实现步骤：
  1. 创建一个实现了Runnable接口的类
  2. 实现类去实现Runnable中的抽象方法：run()
  3. 创建实现类的对象
  4. 将此对象作为参数传递到Thread类的构造器中，创建Thread类的对象
  5. 通过Thread类的对象调用start(),然后会调用线程中的run方法
    > run方法的重写在实现类里面，然后实现类的对象传给了Thread类，Thread在执行run()的时候会先判断当前是否有传入实现类，如果有则执行实现类里的run(),从原代码中可以了解具体的执行流程

```java
class MThread implements Runnable {

    @Override
    public void run() {
      //多线程里面执行的代码
    }
}

public class MThreadTest {
    public static void main(String[] args) {
        MThread mThread = new MThread();
        Thread thread = new Thread(mThread);//这个对象才是线程
        thread.setName("线程1");
        thread.start();

        Thread thread2=new Thread(mThread);//新开线程必须重写创建对象，但是实现类mThread不用重新创建
        thread2.start();
    }
}
```

* 比较两种实现多线程的方式
  * 区别：
    1. 多线程是一个类具有的功能，而不应该是一个子父类的关系，使用实现Runnable接口比较合理
    2. 如果要创建多个线程，且这几个线程之间有需要共享的数据，实现Runnable接口的实现类只需要创建一次，然后放入到多个Thread类的对象中使用，就能够做到共享数据，即使实现类的属性是非静态的属性。而第一种继承Thread类的方式，只有子类里面的成员属性为类变量时才能共享数据
    3. 使用实现Runnable接口后，实现类可以继承其他的更合理的父类
    4. 开发中更适合用实现Runnable接口的方式
  * 联系：
    1. 都实现了Runnable接口
    2. 都需要重写run(),将线程要执行的逻辑写在run()方法体内
