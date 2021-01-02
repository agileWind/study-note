# 第十三章 Java新特性

[TOC]

## Java8的新特性

### Lambda表达式

* 举例：`(o1,o2)->Integer.compare(o1,o2);`
* 格式：
  1. `->`:lambda操作符 或者箭头操作符
  2. ->左边:lambda形参列表(即就是接口中的抽象方法的形参列表)
  3. ->右边:lambda体（即就是重写的抽象方法的方法体）
* lambda表达式的使用
  总结：
  左边：lambda形参列表的参数类型可以省略(类型推断)；如果lambda形参列表只有一个参数，其括号()可以省略
  右边：lambda体应该使用一对大括号{}包裹；如果lambda体只有一条执行语句(可能是return语句)，可以省略一对{}和return。(如果大括号省略，则return要省略)
* lambda表达式的本质：作为接口的实例

```java
public class LambdaTest {

    @Test
    public void method(){
        Comparator<Integer> com1=new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1,o2);
            }
        };
        System.out.println(com1.compare(12, 33));

        Comparator<Integer> com2=(o1,o2)->{return Integer.compare(o1,o2);};
        System.out.println(com2.compare(12, 33));

        //lambda表达式的写法
        Comparator<Integer> com3=(o1,o2)->Integer.compare(o1,o2);
        System.out.println(com3.compare(12, 33));

        //方法引用
        Comparator<Integer> com4=Integer::compare;
        System.out.println(com4.compare(12, 33));

        Comparator<Integer> com5= Comparator.comparingInt(o -> o);
        System.out.println(com5.compare(12, 33));
    }

    @Test
    public void method2(){
        //语法格式一：无参，无返回值
        Runnable runnable=()-> System.out.println("hello world");
        runnable.run();

        //语法格式二：需要一个参数，但没有返回值
        Consumer<String> consumer=(String s)->{
            System.out.println(s);
        };
        consumer.accept("hello world");

        //语法格式三：数据类型可以省略，因为可由编译器推断得出，即类型推断
        //类型推断场景：数组创建，泛型，lambda
        Consumer<String> con=(str)->{
            System.out.println(str);
        };
        con.accept("hello world");

        //语法格式四：lambda若只需要一个参数时，参数的小括号可以省略
        Consumer<String> con2=str->{
            System.out.println(str);
        };
        con2.accept("hello world");

        //语法格式五：lambda需要两个或以上的参数，多条执行语句，并且可以有返回值
        Comparator<Integer> com=(o1,o2)->{
            System.out.println(o1);
            System.out.println(o2);
            return o1.compareTo(o2);
        };
        com.compare(12,33);

        //语法格式六：当lambda体只有一条语句时，return与大括号若有，则都可省略
        Comparator<Integer> com2=(o1,o2)->o1.compareTo(o2);
    }
}

interface Consumer<T> {
    void accept(T t);
}
```

### 函数式接口

* 如果一个接口中，只声明了一个抽象方法，则此接口就称为函数式接口
* 可以通过lambda表达式来创建该接口的对象(若lambda表达式抛出一个受检异常，即非运行时异常，那么该异常需要在目标接口的抽象方法上进行声明)
* 可以通过在接口上使用`@FunctionalInterface`注解，检查它是否是一个函数式接口，当然javadoc也会包含一条声明，说明该接口是一个函数式接口
* 在`java.util.function`包下定义了Java8的丰富的函数式接口

* 理解函数式接口
  1. Java不但可以支持OOP(面向对象编程),也支持面向函数编程OOF
  2. 在函数式编程语言中，函数被当做一等公民对待，Lambda表达式的类型是函数，但是在Java8中，Lambda表达式是对象，而不是函数，它们必须依附于一类特别的对象类型，即函数式接口
  3. 也就是说在Java8中，Lambda表达式就是一个函数式接口的实例，这就是Lambda表达式和函数式接口的关系。即只要一个对象是函数式接口的实例，那么该对象就可以用Lambda表达式来表示

|函数式接口|参数类型|返回类型|用途|
|:-----:|:-----:|:-----:|:-----|
|Consumer\<T\><br>消费型接口|T|void|对类型为T的对象应用操作，包含方法：void accept(T t)|
|Supplier\<T\><br>供给型接口|无|T|返回类型为T的对象，包含方法：T get()|
|Function\<T,R\><br>函数型接口|T|R|对类型为T的对象应用操作，并返回结果。结果是R类型的对象。包含方法：R apply(T t)|
|Predicate\<T\><br>断定型接口|T|boolean|确定类型为T的对象是否满足某约束，并返回boolean值。包含方法：boolean test(T t)|

```java
public class LambdaTest{
    @Test
    public void test(){
        //老写法
        havefun(100, new Consumer<Double>() {
            @Override
            public void accept(Double aDouble) {
                System.out.println("买东西花费："+aDouble+"元");
            }
        });
        //lambda表达式写法
        havefun(200,money-> System.out.println("买东西花费："+money+"元"));
    }
    public void havefun(double money,Consumer<Double> consumer){
        consumer.accept(money);
    }
}

interface Consumer<T> {
    void accept(T t);
}
```

```java
public class LambdaTest{
    @Test
    public void test2(){
        List<String> list = Arrays.asList("张三","李三","王三","刘四","赵三","陈五");
        //老方法：传递匿名子类的对象
        List<String> filterStrs=filterString(list,new Predicate<String>(){
            @Override
            public boolean test(String s){
                return s.contains("三");
            }
        });
        System.out.println(filterStrs);

        //lambda表达式
        List<String> filterStrs2=filterString(list,s->s.contains("三"));
        System.out.println(filterStrs2);
    }

    public List<String> filterString(List<String> list, Predicate<String> pre){
        ArrayList<String> filterList=new ArrayList<>();
        for(String s:list){
            if(pre.test(s)){
                filterList.add(s);
            }
        }
        return filterList;
    }
}
```

### 方法引用

* 使用情景：当要传递给lambda体的操作，已经有实现的方法时，可以使用方法引用
* 方法引用，本质上就是lambda表达式，而lambda表达式作为函数式接口的实例，所以，方法引用，也就是函数式接口的实例
* 使用格式：
  1. 实例对象名::实例方法
  2. 类名::静态方法
  3. 类名::实例方法

* **方法引用使用的要求：要求接口中的抽象方法的形参列表和返回值类型与方法引用的方法的形参列表和返回值类型一致**（前两种格式）

* 实例对象名::实例方法

```java
public class FunctionalInterfaceTest {
    //实例对象名::实例方法
    @Test
    public void test3(){
        Consumer<String> con1=str-> System.out.println(str);
        con1.accept("hello world");

        PrintStream ps=System.out;
        Consumer<String> con2=ps::println;
        con2.accept("hello world");
    }
}
```

```java
public class FunctionalInterfaceTest {

    //类::静态方法
    @Test
    public void test(){
        Comparator<Integer> com1=(t1,t2)->Integer.compare(t1,t2);
        System.out.println(com1.compare(12, 33));

        Comparator<Integer> com2=Integer::compare;
        System.out.println(com2.compare(12, 33));
    }
}
```

* 类名::静态方法

```java
public class FunctionalInterfaceTest {
    //类::静态方法
    @Test
    public void test2(){
        Function<Double,Long> func=new Function<Double,Long>(){
            @Override
            public Long apply(Double d){
                return Math.round(d);
            }
        };
        System.out.println(func.apply(4.0));

        Function<Double,Long> function=Math::round;
        System.out.println(function.apply(12.0));
    }

    //类::静态方法
    @Test
    public void test(){
        Comparator<Integer> com1=(t1,t2)->Integer.compare(t1,t2);
        System.out.println(com1.compare(12, 33));

        Comparator<Integer> com2=Integer::compare;
        System.out.println(com2.compare(12, 33));
    }
}
```

* 类::实例方法

```java
public class FunctionalInterfaceTest {

    //情况三  类::实例方法
    //Comparator中的int compare(T t1,T t2)
    //String中的int t1.compareTo(t2)
    @Test
    public void test4(){
        Comparator<String> com1=(s1,s2)->s1.compareTo(s2);
        System.out.println(com1.compare("abc","abd"));

        Comparator<String> com2=String::compareTo;
        System.out.println(com2.compare("abd", "abc"));
    }

    //BiPredicate中的boolean test(T t1,Tt2)
    //String中的boolean t1,equals(t2)
    @Test
    public void test5(){
        BiPredicate<String,String> pre1=(s1,s2)->s1.equals(s2);
        System.out.println(pre1.test("abc", "abc"));

        BiPredicate<String,String> pre2=String::equals;
        System.out.println(pre2.test("abc", "abd"));
    }

    //Function中的R apply(T t)
    //Employee中的String getName()
    @Test
    public void test6(){
        Employee employee=new Employee("Tom",22);
        Function<Employee,String> func1=e->e.getName();
        System.out.println(func1.apply(employee));

        Function<Employee,String> func2=Employee::getName;
        System.out.println(func2.apply(employee));
    }
}

class Employee{
    String name;
    int age;

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, int age) {
        this.name=name;
        this.age=age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return age == employee.age &&
                name.equals(employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
```

### 构造器引用

* 构造器引用和方法引用类似，函数式接口的抽象方法的形参列表和构造器的形参列表一致，抽象方法的返回值类型即为构造器所属的类型

```java
public class FunctionalInterfaceTest {

    //Supplier中的R get()
    @Test
    public void test7(){
        Supplier<Employee> sup=new Supplier<Employee>(){
            @Override
            public Employee get(){
                return new Employee();
            }
        };

        Supplier<Employee> sup1=()->new Employee();

        Supplier<Employee> sup2=Employee::new;
    }
    //Function中的R apply(T t)
    @Test
    public void test8(){
        Function<String,Employee> func1=name->new Employee(name);
        Employee employee=func1.apply("Tom");
        System.out.println(employee);

        Function<String,Employee> func2=Employee::new;
        Employee employee1=func2.apply("Tiny");
        System.out.println(employee1);
    }
    //Function中的R apply(T t,U u)
    @Test
    public void test9(){
        BiFunction<String,Integer,Employee> func1=(name, age)->new Employee(name,age);
        Employee employee=func1.apply("Tom",20);
        System.out.println(employee);

        BiFunction<String,Integer,Employee> func2=Employee::new;
        Employee employee1=func2.apply("Tiny",22);
        System.out.println(employee1);
    }
}
```

### 数组引用

```java
public class FunctionalInterfaceTest {

    @Test
    public void test11(){
        Function<Integer,String[]> func1=length->new String[length];
        String[] arr1=func1.apply(5);

        Function<Integer,String[]> func2=String[]::new;
        String[] arr2=func2.apply(10);
    }
}
```

### Stream API

* Stream API把真正的函数式编程风格引入到Java中
* Stream是处理集合的关键抽象概念，可以指定对集合进行的操作，可以执行非常复杂的查找，过滤和映射数据等操作。使用Stream API对集合进行操作，就类似于使用SQL执行的数据库查询。
* Stream可以使用并行执行操作

* 什么是Stream
  1. 是数据渠道，用于操作数据源(集合、数组)所生成的元素序列。
  2. Stream不会改变源对象，而是返回一个持有结果的新Stream
  3. Stream操作是延迟执行的，这就意味着它是等到需要结果的时候才执行

* Stream的操作三个步骤
  1. 创建Stream：一个数据源(如集合，数组)，获取一个流
  2. 中间操作：一个中间操作链，对数据源的数据进行处理。**但是中间操作是延迟执行的，遇到终止操作才开始执行中间操作并终止**，产生结果
  3. 终止操作：一旦执行终止操作，**就开始执行中间操作链**，并产生结果。之后，不会再被使用

* 创建Stream的方式

```java
public class StreamTest {
    //创建Stream方式一：通过集合
    @Test
    public void test() {
        List<Employee> list=StreamTest.getEmployees();
        Stream<Employee> stream=list.stream();//顺序流

        //并行流
        Stream<Employee> parallelStream=list.parallelStream();

    }
    //创建Stream方式二：通过数组
    @Test
    public void test1(){
        int[] arr=new int[]{1,2,3,4,5};
        IntStream steam= Arrays.stream(arr);

        Employee e1=new Employee("Tom",11);
        Employee e2=new Employee("Tony",21);
        Employee[] arr1=new Employee[]{e1,e2};
        Stream<Employee> stream1=Arrays.stream(arr1);
    }

    //创建Stream方式三：通过Stream的of()
    @Test
    public void test2(){
        Stream<Integer> stream=Stream.of(1,2,3,4,5,6);
    }
    //创建Stream方式四：创建无限流
    @Test
    public void test3(){
        //迭代  public static<T> Stream<T> iterate(find T seed,final UnaryOperator<T> f)
        Stream.iterate(0,t->t+2).limit(10).forEach(System.out::println);//不加limit则会无限执行下去，这里只获取10个：给一个数字t，返回t+2;然后再次迭代

        //生成  public static<T> Stream<T> generate(Supplier<T> s)
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }

    public static List<Employee> getEmployees(){
        List<Employee> list=new ArrayList<>();
        list.add(new Employee("张飞",34));
        list.add(new Employee("马超",24));
        list.add(new Employee("关羽",25));
        list.add(new Employee("刘备",54));
        list.add(new Employee("赵云",22));
        list.add(new Employee("黄忠",64));
        list.add(new Employee("周瑜",37));
        list.add(new Employee("诸葛亮",30));
        return list;
    }
}
```

#### Stream的中间操作

* 多个中间操作可以连接起来形成一个流水线，除非流水线上触发终止操作，否则中间操作不会执行任何的处理，而在终止操作时一次性全部处理，称为“惰性处理”

* 筛选和切片

|方法|描述|
|:----|:----|
|filter(Predicate p)|接收Lambda,从流中排除某些元素|
|distinct()|筛选，通过流所生成元素的hashCode()和equals()去除重复元素|
|limit(long maxSize)|截断流，使其元素不超过给定数量|
|skip(long n)|跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回衣蛾空流，与limit(n)互补|

```java
public class StreamTest {
    @Test
    public void test4(){
        List<Employee> list=StreamTest.getEmployees();
        Stream<Employee> stream = list.stream();
        stream.filter(e->e.getAge()>25).forEach(System.out::println);//找出员工中年龄大于25的

        //stream.limit(3).forEach(System.out::println);//这里stream已经在上面被终止操作关闭了

        list.stream().limit(3).forEach(System.out::println);//截断流，元素个数不超过前3个

        list.stream().skip(3).forEach(System.out::println);//跳过元素，扔掉前面的3个元素

        list.stream().distinct().forEach(System.out::println);//按照hashCode()和equals()去重
    }
}
```

* 映射

|方法|描述|
|:----|:----|
|map(Function f)|接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素|
|mapToDouble(ToDoubleFunction f)|接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的DoubleStream|
|mapToInt(ToIntFunction f)|接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的IntStream|
|mapToLong(ToLongFunction f)|接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的LongStream|
|flatMap(Function f)|接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流|

```java
public class StreamTest {

    @Test
    public void test5() {
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd", "ee");
        list.stream().map(str -> str.toUpperCase()).forEach(System.out::println);

        List<Employee> employees = StreamTest.getEmployees();
        Stream<Integer> agesStream = employees.stream().map(Employee::getAge);
        agesStream.filter(age -> age > 26).forEach(System.out::println);

        //employees.stream().map(employee->employee.getAge()>28).forEach(System.out::println);这里不能合在一起写，不然只会打印boolean值

        Stream<Stream<Character>> streamStream = list.stream().map(StreamTest::fromStrToStream);
        streamStream.forEach(s -> {
                    s.forEach(System.out::println);
        });

        Stream<Character> characterStream=list.stream().flatMap(StreamTest::fromStrToStream);
        characterStream.forEach(System.out::println);
    }

    public static Stream<Character> fromStrToStream(String str) {
        ArrayList<Character> list = new ArrayList<>();
        for (Character c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();
    }
}
```

* 排序

|方法|描述|
|:----|:----|
|sorted()|产生一个新流，其中按照自然顺序排序|
|sorted(Comparator com)|产生一个新流，其中按比较器顺序排序|

```java
public class StreamTest {

    @Test
    public void test6(){
        List<Integer> list=Arrays.asList(12,32,43,1,55,0,233);
        list.stream().sorted().forEach(System.out::println);//要实现Comparable

        List<Employee> employeeList=StreamTest.getEmployees();
        //employeeList.stream().sorted().forEach(System.out::println);//这里会报错，没有实现Comparable

        employeeList.stream().sorted((e1,e2)->Integer.compare(e1.getAge(),e2.getAge())).forEach(System.out::println);//员工按照年龄从小到大，定制排序
    }
}
```

#### Stream的终止操作

* 匹配和查找

|方法|描述|
|:----|:----|
|allMatch(Predicate p)|检查是否匹配所有元素|
|anyMatch(Predicate p)|检查是否至少匹配一个元素|
|noneMatch(Predicate p)|检查是否没有匹配的元素|
|findFirst()|返回第一个元素|
|findAny|返回当前流中的任意元素|
|count()|返回流中元素的总个数|
|max(Comparator c)|返回流中的最大值|
|min(Comparator c)|返回流中最小值|
|forEach(Consumer c)|内部迭代(使用Collection接口需要用户去做迭代，称为外部迭代)|

```java
public class StreamTest {

    @Test
    public void test8(){
        List<Employee> employees = StreamTest.getEmployees();
        boolean allMatch=employees.stream().allMatch(e->e.getAge()>26);//判断是否所有的员工的年龄都大于26

        boolean anyMatch=employees.stream().anyMatch(e->e.getAge()>40);//判断是否至少有一个员工的年龄大于40

        boolean noneMatch=employees.stream().noneMatch(e->"张飞".equals(e.getName()));//判断是否员工里没有“张飞”,有张飞则是返回false,没有张飞则是返回true

        Optional<Employee> employee=employees.stream().findFirst();//返回员工集合中的第一个元素

        Optional<Employee> employee1=employees.stream().findAny();//返回任一个员工
        Optional<Employee> employee2=employees.parallelStream().findAny();//返回任一个员工

        long count=employees.stream().filter(e->e.getAge()>30).count();//员工年龄大于30的总数

        System.out.println(employees.stream().max((e1,e2)->Integer.compare(e1.getAge(),e2.getAge())));//返回员工中年龄最大的一位,返回的对象是Optional<Employee>
        System.out.println(employees.stream().map(e -> e.getAge()).max(Integer::compareTo));//返回员工年龄最大的那位的年龄，返回的是Optional<Integer>

        System.out.println(employees.stream().min((e1,e2)->Integer.compare(e1.getAge(),e2.getAge())));//返回员工中年龄最小的一位,返回的对象是Optional<Employee>

        employees.stream().forEach(System.out::println);//遍历每一个元素，对每个元素执行同一个操作
    }

    public static List<Employee> getEmployees() {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("张飞", 34));
        list.add(new Employee("马超", 24));
        list.add(new Employee("关羽", 25));
        list.add(new Employee("刘备", 54));
        list.add(new Employee("赵云", 22));
        list.add(new Employee("黄忠", 64));
        list.add(new Employee("周瑜", 37));
        list.add(new Employee("诸葛亮", 30));
        return list;
    }
}
```

* 规约

|方法|描述|
|:----|:----|
|reduce(T iden,BinaryOperator b)|可以将流中元素反复结合起来，得到一个值。返回T|
|reduce(BinaryOperator b)|可以将流中元素反复结合起来，得到一个值。返回Optional\<T\>|

> map和reduce的连接通常称为map-reduce模式，因Google用它来进行网络搜索而出名

```java
public class StreamTest {

    @Test
    public void test9(){
        List<Employee> employees=StreamTest.getEmployees();
       // int sum0=employees.stream().reduce(0,(e1,e2)->Integer.sum(e1.getAge(),e2.getAge()));这里这样写是错误的，归约是拿stream里的每个元素进行操作，如果不是这样直接去操作，但是返回的值类型却必须还是stream元素类型
       //于是会报错，说int无法转换成Employee类型

        int sum=employees.stream().map(e->e.getAge()).reduce(0,Integer::sum);//计算出所有员工的年龄总和，第一个参数用来被参与计算

        Optional<Integer> sum1=employees.stream().map(Employee::getAge).reduce(Integer::sum);//计算出所有员工的年龄总和
    }
}
```

* 收集

|方法|描述|
|:----|:----|
|collect(Collector c)|将流转换成其他形式。接收一个Colllector接口的实现，用于给Stream中元素做汇总的方法|

Collector接口中的方法的实现决定了如何对流执行收集的操作（如收集到List,Set,Map）
Collectors实用类提供了很多静态方法，可以方便地创建常用的收集器实例

```java
public class StreamTest {

    @Test
    public void test11(){
        List<Employee> employees=StreamTest.getEmployees();
        List<Employee> collect = employees.stream().filter(e -> e.getAge() > 30).collect(Collectors.toList());
        System.out.println(collect);

        Set<Employee> collect2 = employees.stream().filter(e -> e.getAge() > 30).collect(Collectors.toSet());
        System.out.println(collect2);
    }
}
```

> 可以通过查看源码中的Collectors了解到它提供了哪些方法来返回收集器的实例

### Optional类

```java
public class OptionalTest {
    @Test
    public void test(){
        Human p=new Human();
        p=null;
        Optional<Human> optionalHuman0=Optional.ofNullable(p);//可以存空的对象
        //Optional<Human> optionalHuman=Optional.of(p);//存不进去，会报错，必须存非空的
        System.out.println(optionalHuman0.orElse(new Human("Tom", 20)));//输出的是刚刚创建的对象。如果取值发现为空则使用传入的

        Human h=new Human("tom",11);
        Optional<Integer> age=Optional.ofNullable(h).map(Human::getAge);
    }
}

class Human{
    String name;
    int age;

    public Human() {
    }

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

## Java9的新特性

## Java10的新特性

## Java11的新特性
