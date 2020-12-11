# 第六章 Java多线程

[TOC]

## 6.1 进程线程概念

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

## 6.2 创建多线程

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
  3. 多线程中其中一个线程出现异常而停止，一般不会影响其他线程和主线程;
* Thread中的常用方法：
  1. `start()`:启动当前线程，并调用当前线程的`run()`
  2. `run()`:通常需要重写Thread类中的此方法，将创建的线程要执行的操作写在此方法中
  3. `currentThread()`:静态方法，返回执行当前代码的线程
  4. `getName()`:获取当前线程的名字
  5. `setName()`:设置当前线程的名字，取名得在线程执行之前(start()执行之前)，这是主线程下完成的
  6. `yield()`:释放当前CPU的执行权
     * 又称线程让步，暂停当前正在执行的线程，把执行机会让给优先级相同或更高的线程
     * 若队列中没有同优先级的线程，忽略此方法，接下来仍是执行该线程
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

* 线程的优先级
  1. MAX_PRIORITY:10
  2. MIN_PRIORITY:1
  3. NORM_PRIORITY:5---->默认优先级
  4. 获取与设置当前线程的优先级
     * getPriority():获取线程的优先级
     * setPriority(int p):设置线程的优先级
  >说明：高优先级的线程要抢占低优先级线程cpu的执行权。但是这只是从概率上讲，高优先级的线程高概率的情况下被执行。并不意味着只当高优先级的线程执行完以后，低优先级的线程才执行

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

### 方式三：实现Callable接口

* 创建线程的方式三：实现Callable接口，这是JDK5.0新增的
* 相比Runnable,Callable功能更强大些
  1. 相比run()方法，可以有返回值
  2. 方法可以抛出异常
  3. 支持泛型的返回值
  4. 需要借助FutureTask类，比如获取返回结果

* Future接口
  1. 可以对具体Runnable、Callable任务的执行结果进行取消、查询是否完成、获取结果等
  2. FutureTask是Future接口的唯一的实现类
  3. FutureTask同时实现了Runnable，Future接口，它既可以作为Runnable被线程执行，又可以作为Future得到Callable的返回值

* 步骤：
  1. 创建一个实现Callable的实现类
  2. 实现call方法，将此线程需要执行的操作声明在call()中
  3. 创建Callable接口实现类的对象
  4. 将此Callable接口实现类的对象作为参数传递到FutureTask构造器中，创建FutureTask对象
  5. 将FutureTask的对象作为参数传递到Thread类的构造器中，创建Thread对象，并调用start()

```java
public class MyThread implements Callable {

    @Override
    public Object call() throws Exception {
        int sum=0;
        for(int i=0;i<101;i++){
            if(i%2==0){
                sum+=i;
                System.out.println(i);
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        MyThread myThread=new MyThread();
        FutureTask futureTask=new FutureTask(myThread);
        new Thread(futureTask).start();
        Object sum= null;
        try {
            //get()返回值即为FutureTask构造器参数Callable实现类重写的call()的返回值
            sum = futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(sum);
    }
}
```

### 方式四：使用线程池

* 在开发中真正使用到的这种方式更多
* 背景：经常创建和销毁、使用量特别大的资源，比如并发情况下的线程，对性能影响很大
* 思路：提前创建好多个线程，放入线程池中，使用时直接获取，使用完毕放回池中。可以避免频繁创建销毁、实现重复利用。
* 好处：
  1. 提高响应速度(减少了创建新线程的时间)
  2. 降低资源消耗(重复利用线程池中线程，不需要每次都创建)
  3. 便于线程管理（有一些设置线程池的参数，便于管理）

```java
public class ThreadPool {
    public static void main(String[] args) {
        ExecutorService executorService= Executors.newFixedThreadPool(10);
        //如果不进行线程管理，不用向下转型
        ThreadPoolExecutor service=(ThreadPoolExecutor) executorService;
        service.setCorePoolSize(100);
        service.setMaximumPoolSize(10);

        //下面直接使用executorService也可
        service.execute(new NumberThread());//适用于Runnable
        service.execute(new NumberThread1());//适用于Runnable
        //executorService.submit();//适用于Callable
        service.shutdown();
    }
}

class NumberThread implements Runnable{
    @Override
    public void run(){
        for(int i=0;i<101;i++){
            if(i%2==0)
            System.out.println(i);
        }
    }
}
class NumberThread1 implements Runnable{
    @Override
    public void run(){
        for(int i=0;i<101;i++){
            if(i%2!=0)
                System.out.println(i);
        }
    }
}
```

## 6.3 线程的生命周期

* 想要实现多线程，必须在主线程中创建新的线程对象，Java语言使用Thread类以及子类的对象来表示线程，在它的完整生命周期中通常经历了五种状态(Thread.State类定义了线程的几种状态)：
  * **新建**：当一个Thread类或者子类的对象被声明并创建时，新生的线程对象处于新建状态
  * **就绪**；处于新建状态的线程被start()后，讲进入队列等待CPU时间片，此时它已具备了运行的条件，只是没分配到cpu资源
  * **运行**：当就绪的线程被调度并获取CPU资源时，便进入运行状态，run()方法定义了线程的操作和功能
  * **阻塞**：在特殊的某种情况下，被认为挂起或执行输入输出等操作，让处CPU并临时中止自己的执行，进入阻塞状态
  * **死亡**：线程完成了它的全部工资或线程被提前强制中止或出现异常导致结束
  ![线程生命周期图示](../images/线程生命周期.png)

## 6.4 线程同步

* 多个线程在执行时，有需要共享数据的可能，当它们同时对一个共享数据进行操作时，可能存在同时获取唯一资源，比如买票程序的多个线程窗口售票，同一个编号的票被多个窗口售出
* 归根结底的原因是：当某个线程操作过程中，尚未操作完成时，其他线程参与进来

### Java 同步机制

#### 方式一：同步代码块

```java
synchronized(同步监视器){
    //需要同步的代码
}
```

* 操作共享数据的代码，即为需要被同步的代码
* 共享数据：多个线程共同操作的变量
* 同步监视器，俗称为锁。任何一个类的对象，都可以充当锁
  要求：**多个线程必须要共用同一把锁**

```java
public class SaleWindow implements Runnable {
    private int ticket = 100;
    private final Object obj = new Object();

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);//为了更明显的看到交替售票的过程，进来先阻塞
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj) {//也可以使用当前对象this作为锁
                if (ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + "，售出票号为：" + ticket);
                    ticket--;
                    Thread.yield();//这里也是为了更明显的看到交替售票，执行一次售票后交出线程执行权
                } else
                    return;
            }
        }
    }

    public static void main(String[] args) {
        SaleWindow sale = new SaleWindow();
        Thread th1 = new Thread(sale);//这几个线程都是共用的同一个sale对象，所以这几个线程共享sale对象中的实例属性
        Thread th2 = new Thread(sale);
        Thread th3 = new Thread(sale);
        th1.setName("窗口1");
        th2.setName("窗口2");
        th3.setName("窗口3");
        th1.start();
        th2.start();
        th3.start();
    }
}
```

* 同步的方式，解决了线程的安全问题。但是操作同步代码时，只有一个线程参与，其他线程等待，相当于一个单线程的过程，效率低

* 继承Thread的多线程使用同步代码块解决同步问题：

```java
public class SaleWindow extends Thread {
    private static int ticket = 100;
    private static final Object obj = new Object();

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj) {//使用SaleWindow.class也可以作为锁
                if (ticket > 0) {
                    System.out.println(getName() + "，售出票号为：" + ticket);
                    ticket--;
                    Thread.yield();
                } else
                    return;
            }
        }
    }

    public static void main(String[] args) {
        SaleWindow th1= new SaleWindow();//以下每个线程都实例化了一次子类，为了使它们共享数据，将需要共享的数据属性设置为static
        SaleWindow th2= new SaleWindow();
        SaleWindow th3= new SaleWindow();
        th1.setName("窗口1");
        th2.setName("窗口2");
        th3.setName("窗口3");
        th1.start();
        th2.start();
        th3.start();
    }
}
```

* 被同步的代码里面，不能包含多了，也不能包含少了，只把需要对共享数据进行读取、操作的一些代码放在同步代码里

>如果run方法按照下面这样写，则会让一个窗口售完所有票后其他窗口才能售票

```java
public void run() {
    synchronized (obj) {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + "，售出票号为：" + ticket);
                ticket--;
                Thread.yield();
            } else
                return;
        }
    }
}
```

#### 方式二：同步方法

* 将需要同步的方法用synchronized来修饰，此方法就是一个同步方法了

```java
public class SaleWindow1 implements Runnable {
    private int ticket = 100;
    private final Object obj = new Object();

    @Override
    public void run() {
        while (true) {
            saleTicket();
        }
    }

    private synchronized void saleTicket() {//同步监视器是this

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (ticket > 0) {
            System.out.println(Thread.currentThread().getName() + "，售出票号为：" + ticket);
            ticket--;
            Thread.yield();
        } else
            return;

    }

    public static void main(String[] args) {
        SaleWindow1 sale = new SaleWindow1();
        Thread th1 = new Thread(sale);
        Thread th2 = new Thread(sale);
        Thread th3 = new Thread(sale);
        th1.setName("窗口1");
        th2.setName("窗口2");
        th3.setName("窗口3");
        th1.start();
        th2.start();
        th3.start();
    }
}
```

* 继承Thread的多线程使用同步方法解决同步问题：

```java
public class SaleWindow extends Thread {
    private static int ticket = 100;
    private static final Object obj = new Object();

    @Override
    public void run() {
        while (true) {
            saleTicket();
        }
    }

    /**
     * 仅仅只是synchronized修饰，作为同步方法，但是每个线程锁却是各自的实例对象，锁不同，无法同步
     * 需要再使用static将方法作为类静态成员方法，此时锁是SaleWindow.class,保证了每个线程的锁都一致
     */
    private static synchronized void saleTicket(){//注意称为静态的同步方法后，方法体内无法调用非静态方法了
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (obj) {
            if (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + "，售出票号为：" + ticket);
                ticket--;
                Thread.yield();
            } else
                return;
        }
    }

    public static void main(String[] args) {
        SaleWindow1 th1= new SaleWindow1();
        SaleWindow1 th2= new SaleWindow1();
        SaleWindow1 th3= new SaleWindow1();
        th1.setName("窗口1");
        th2.setName("窗口2");
        th3.setName("窗口3");
        th1.start();
        th2.start();
        th3.start();
    }
}
```

* 线程安全的单例模式(懒汉式)

```java
class Bank{
    private Bank(){

    }
    private static Bank instance=null;
    public static Bank getInstance(){
        if(instance==null){
            synchronized (Bank.class){
                if(instance==null){
                    instance=new Bank();
                }
            }
        }
        return instance;
    }
}
```

#### 方式三：Lock锁

* Lock锁是JDK5.0新增的
* Lock是一个接口，一般使用它的子类`RenntrantLock`

```java
public class SaleWindow implements Runnable {
    private int ticket = 100;
    private ReentrantLock lock=new ReentrantLock();//如果是继承Thread的方式这里要是类变量

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                lock.lock();
                if (ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + "，售出票号为：" + ticket);
                    ticket--;
                    Thread.yield();
                } else
                    return;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        SaleWindow sale = new SaleWindow();
        Thread th1 = new Thread(sale);
        Thread th2 = new Thread(sale);
        Thread th3 = new Thread(sale);
        th1.setName("窗口1");
        th2.setName("窗口2");
        th3.setName("窗口3");
        th1.start();
        th2.start();
        th3.start();
    }
}
```

* synchronized与Lock的异同：
  相同：二者都可以解决线程安全问题
  不同：synchronized机制在执行完同步代码以后，自动释放同步监视器
        Lock需要手动的启动同步(unlock()),同步结束也需要手动的实现(unlock())
  * Lock是显式锁(手动开启和关闭锁)，synchronized是隐式锁，出了作用域自动释放
  * Lock只有代码块锁，synchronized有代码块锁和方法锁
  * 使用Lock锁，JVM将花费较少的时间来调度线程，性能更好。并且具有更好的扩展性
  > 优先使用顺序：
  Lock--->同步代码块(已经)进入了方法体，分配了相应资源)--->同步方法(在方法体之外)

* 有两个人，共同向一个账号里面存钱，两个人分别存了三次，代码实现如下：

```java
public class AccountTest {
    public static void main(String[] args) {
        Account account=new Account(0);
        Customer c1=new Customer(account);
        Customer c2=new Customer(account);//两个用户共用一个账号
        c1.setName("甲");
        c2.setName("乙");
        c1.start();
        c2.start();
    }
}

class Account{
    private double balance;

    public Account(double balance){
        this.balance=balance;
    }
    /**
     * 这里的同步方法没有设置为static的，却能保持同步
     * 因为虽然有两个用户线程在存钱，但是它们用的是同一个账号对象，都调用账号的存钱方法时，this指向的是同一个对象,即同一个锁
     * 两个线程在执行同步方法时用到了同一个锁，即能保证线程同步
     */
    public synchronized void deposit(double amt){
        if(amt>0){
            balance+=amt;
            System.out.println(Thread.currentThread().getName()+",存钱成功，账号余额："+balance);

        }
    }
}

class Customer extends Thread{
    private Account account;

    public Customer(Account account) {
        this.account = account;
    }

    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            account.deposit(1000);
            //Thread.yield();
        }
    }
}
```

### 线程的死锁

* 死锁：
  1. 不同的线程分别占用对方需要的同步资源(一次只能被一个线程使用)不放弃，都再等待对方放弃自己需要的同步资源，就形成了线程的死锁
  2. 出现死锁后，不会出现异常，不会出现提示，只是所有的线程都处于阻塞状态，无法继续
* 解决方法：
  1. 专门的算法、原则
  2. 尽量减少同步资源的定义
  3. 尽量避免嵌套同步
   > StringBuilder不是线程安全的，无法同步访问，如果现在有两个StringBuilder的变量，多个线程同时又需要这两个变量，那就可能会出现死锁

## 6.5 线程通信

线程通信的三个方法：
`wait()`:一旦执行此方法，当前线程就进入阻塞状态，并释放同步监视器
`notify()`:一旦执行此方法，就会唤醒被wait的一个线程，如果有多个线程被wait，就唤醒优先级高的线程
`notifyAll()`:一旦执行此方法，就会唤醒所有被wait的线程

* wait(),notify(),notifyAll()这三个方法必须使用在同步代码块或同步方法中
* wait(),notify(),notifyAll()这三个方法的调用者必须是同步监视器，否则报IllegalMonitorStateException异常
* wait(),notify(),notifyAll()这三个方法是定义在java.lang.Object类中

* 例子：使用两个线程打印交替打印数字1-100

```java
public class PrintNumber implements Runnable {
    private int number = 1;

    @Override
    public void run() {
        while (true) {
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            synchronized (this) {
                notify();
                if (number < 101) {
                    System.out.println(Thread.currentThread().getName() + ",打印数字：" + number);
                    number++;
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else
                    return;
            }
        }
    }

    public static void main(String[] args) {
        PrintNumber printNumber = new PrintNumber();
        Thread th1 = new Thread(printNumber);
        Thread th2 = new Thread(printNumber);
        th1.setName("打印机甲");
        th2.setName("打印机乙");
        th1.start();
        th2.start();
    }
}
```

* `sleep()`和`wait()`的异同
  * 相同点：一旦执行方法，都可以使得当前的线程进入阻塞状态
  * 不同点：
    1. 两个方法声明的位置不同：Thread类中声明sleep(),Object类中声明wait()
    2. 调用的要求不同，sleep()可以在任何需要的场景下调用,wait()必须在同步代码块或同步方法中
    3. 关于是否释放同步监视器：如果两个方法都使用同步代码块或同步方法中，sleep()不会释放锁，wait()会释放同步监视器
    > sleep()、yield()是让出线程，但是如果其他线程会使用当前的线程的资源，该资源不会被释放
* 生产者/消费者问题

1. 问题描述：
   生产者将产品交给店员，而消费者从店员处取走产品，要求：
   1. 店员一次最多只能持有固定数量的产品(20)，如果生产者试图生产更多的产品，店员会让生产者停下
   2. 如果店中有空位放产品会通知生产者继续生产
   3. 如果店中没有产品了，店员会告诉消费者等一下，如果店中有产品了再通知消费者来取走产品
2. 代码实现：

```java
public class Test {
    public static void main(String[] args) {
        Clerk clerk=new Clerk();
        Producer producer=new Producer(clerk);
        Customer customer1=new Customer(clerk);
        Customer customer2=new Customer(clerk);
        producer.setName("生产者");
        customer1.setName("消费者A");
        customer2.setName("消费者B");
        producer.start();
        customer1.start();
        customer2.start();
    }
}
//店员，相等于消费者生产者共有的资源
class Clerk{
    private int productCount=0;

    public synchronized void produceProduct(){
        if(productCount<20){
            productCount++;
            System.out.println(Thread.currentThread().getName()+",生产第"+productCount+"个产品");
            notify();
        }else{
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public synchronized void consumeProduct(){
        if(productCount>0){
            System.out.println(Thread.currentThread().getName()+",消费第"+productCount+"个产品");
            productCount--;
            notify();
        }else{
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
//生产者
class Producer extends Thread{
    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run(){
        System.out.println("生产者开始生产产品...");
        while(true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.produceProduct();
        }
    }
}
//消费者
class Customer extends Thread{
    private Clerk clerk;

    public Customer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName()+"开始消费产品...");
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.consumeProduct();
        }
    }
}
```