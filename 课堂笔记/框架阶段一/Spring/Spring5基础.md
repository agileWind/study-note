# Spring5基础

[TOC]

## Spring5框架概述

* Spring是轻量级的开源的JavaEE框架
* Spring可以解决企业应用开发的复杂性
* Spring有两个核心部分：IOC和Aop
  IOC：控制反转，把创建对象过程交给Spring进行管理(反射)
  Aop:面向切面,不修改源代码进行功能增强（动态代理）

  >轻量级：不需要引入很多jar包，且也不依赖其他的jar包，自己是一个独立的框架
  >开源：开放源代码且免费
  >框架：使得开放便捷，解决企业应用开发的复杂性

### Spring的特点

1. **方便解耦，简化开发**
   通过Spring提供的IoC容器，我们可以将对象之间的依赖关系交由Spring进行控制，避免硬编码所造成的过度程序耦合。有了Spring，用户不必再为单实例模式类、属性文件解析等这些很底层的需求编写代码，可以更专注于上层的应用。
2. **AOP编程的支持**
   通过Spring提供的AOP功能，方便进行面向切面的编程，许多不容易用传统OOP实现的功能可以通过AOP轻松应付。
3. **声明式事务的支持**
   在Spring中，我们可以从单调烦闷的事务管理代码中解脱出来，通过声明式方式灵活地进行事务的管理，提高开发效率和质量。
4. **方便程序的测试**
   可以用非容器依赖的编程方式进行几乎所有的测试工作，在Spring里，测试不再是昂贵的操作，而是随手可做的事情。例如：Spring对Junit4支持，可以通过注解方便的测试Spring程序。
5. **方便集成各种优秀框架**
   Spring不排斥各种优秀的开源框架，相反，Spring可以降低各种框架的使用难度，Spring提供了对各种优秀框架（如Struts,Hibernate、Hessian、Quartz）等的直接支持。
6. **降低Java EE API的使用难度**
   Spring对很多难用的Java EE API（如JDBC，JavaMail，远程调用等）提供了一个薄薄的封装层，通过Spring的简易封装，这些Java EE API的使用难度大为降低。
7. **Java 源码是经典学习范例**
   Spring的源码设计精妙、结构清晰、匠心独运，处处体现着大师对Java设计模式灵活运用以及对Java技术的高深造诣。Spring框架源码无疑是Java技术的最佳实践范例。如果想在短时间内迅速提高自己的Java技术水平和应用开发水平，学习和研究Spring源码将会使你收到意想不到的效果

### 第一个Spring程序

1. 导入依赖的jar包
   可以去Spring官网上去下载最少的依赖包，作为初学者，应该去下载最小的jar包，而不是随便找个别人已经弄好的lib，其中有些功能是初学者接触不到的，应该在学习时慢慢了解，添加依赖。或者使用maven来添加依赖

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>

       <groupId>com.suftz.springdemo</groupId>
       <artifactId>deom</artifactId>
       <version>1.0-SNAPSHOT</version>
   <dependencies>
       <dependency>
           <groupId>org.springframework</groupId>
           <artifactId>spring-context</artifactId>
           <version>5.2.6.RELEASE</version>
       </dependency>
       <dependency>
           <groupId>commons-logging</groupId>
           <artifactId>commons-logging</artifactId>
           <version>1.1.1</version>
       </dependency>
       <dependency>
           <groupId>junit</groupId>
           <artifactId>junit</artifactId>
           <version>4.12</version>
           <scope>test</scope>
       </dependency>

   </dependencies>

   </project>
   ```

2. 编写一个JavaBean用于测试，即User类

   ```java
   package com.suftz.bean;

   public class User {
       private String name;
       private int age;
       private String email;

       public User() {
       }

       public User(String name, int age, String email) {
           this.name = name;
           this.age = age;
           this.email = email;
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

       public String getEmail() {
           return email;
       }

       public void setEmail(String email) {
           this.email = email;
       }

       @Override
       public String toString() {
           return "User{" +
                   "name='" + name + '\'' +
                   ", age=" + age +
                   ", email='" + email + '\'' +
                   '}';
       }
   }
   ```

3. 在src下或者资源目录(maven)编写bean配置文件beans.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:p="http://www.springframework.org/schema/p"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
       <bean id="user" class="com.suftz.bean.User" />
   </beans>
   ```

4. 编写测试代码

   ```java
   import com.suftz.bean.User;
   import org.junit.Test;
   import org.springframework.context.ApplicationContext;
   import org.springframework.context.support.ClassPathXmlApplicationContext;

   public class SpringTest1 {
       @Test
       public void test1(){
           ApplicationContext act=new ClassPathXmlApplicationContext("beans.xml");
           User user=act.getBean("user", User.class);
           System.out.println(user);//User{name='null', age=0, email='null'}
           user.setEmail("sys@qq.com");
           user.setName("Tom");
           user.setAge(20);
           System.out.println(user);//User{name='Tom', age=20, email='sys@qq.com'}
       }
   }
   ```

### 模拟实现IOC容器

* Spring的IoC核心就是控制反转，将对实现对象的操作控制器交出来，由IoC容器来管理，从配置文件中获取配置信息，Java对XML文档提供了完美的支持，dom4j功能强大，而下面我就用JDOM这一开源项目，利用它可以纯Java技术实现对XML文档的解析、生成、序列化来模拟实现IoC容器。

 一、传统方式编写代码

1. 定义接口

   ```java
   package com.decipher.car;

   public interface Car {
       public String getBrand();
       public void run();

   }
   ```

2. 接下来实现Car接口

   ```java
   package com.decipher.carImplementation;
   import com.decipher.car.Car;
   public class BMWCar implements Car{
       private String MyBrand="宝马";
       public String getBrand(){
           return MyBrand;
       }
       public void run(){
           System.out.println(MyBrand+" is runing");
       }
   }
   ```

3. 新建一个Human类

   ```java
   package com.decipher.human;
   import com.decipher.car.Car;
   public class Human {
       private Car car;

       public Car getCar() {
           return car;
       }

       public void setCar(Car car) {
           this.car = car;
       }
       public void myCarRun(){
           car.run();
       }
   }
   ```

4. 最后编写测试类

   ```java
   package com.decipher.human;

   import com.decipher.car.Car;
   import com.decipher.carImplementation.BMWCar;
   import com.decipher.human.Human;

   public class HumenTest {
       public static void main(String[] args) throws Exception {
           Human human=new Human();
           Car car=new BMWCar();
           human.setCar(car);
           human.myCarRun();
       }
   }
   ```

二.JDOM模拟IoC容器反转控制

在编程之前要导入jdom.jar包到项目工程目录中。

1. 新建BeanFactory

   ```java
   package com.decipher.spring;

   public interface BeanFactory {
       public Object getBean(String id);
   }
   ```

2. 实现BeanFactory接口

   ```java
   package com.decipher.spring;

   import java.lang.reflect.Method;
   import java.util.HashMap;
   import java.util.List;
   import java.util.Map;

   import org.jdom.Document;
   import org.jdom.Element;
   import org.jdom.input.SAXBuilder;

   public class ClassPathXmlApplicationContext implements BeanFactory{
       //储存各个实例的键值对
       private Map<String,Object> beans=new HashMap<String,Object>();
       //构造方法
       public ClassPathXmlApplicationContext() throws Exception{
           //读取XML文档
           SAXBuilder sb=new SAXBuilder();
           //构造文档对象DOC
           Document doc=sb.build(this.getClass().getClassLoader().getResource("beans.xml"));
           //获取XML文档根元素
           Element root=doc.getRootElement();
           //获取根元素下所有的子元素
           List list=root.getChildren("bean");
           //遍历所有的Bean元素
           for(int i=0;i<list.size();i++){
               //取得第i个Bean元素
               Element element=(Element)list.get(i);
               //获取第i个Bean元素的id属性值，并将其存入到字符串变量id中
               String id=element.getAttributeValue("id");
               //获取第i个Bean元素的class属性值，并将其存入到字符串变量clazz中
               String clazz=element.getAttributeValue("class");
               //使用反射生成类的对象，相当于生成类对象，且存储在Map中
               Object o=Class.forName(clazz).newInstance();
               System.out.println(id);
               System.out.println(clazz);
               beans.put(id,o);//将id和对象o存入Map中
               //对第i个bean元素下的每个property子元素进行遍历
               for(Element propertyElement:(List<Element>)element.getChildren("property")){
                   //获取property元素的name属性值
                   String name=propertyElement.getAttributeValue("name");
                   //获取property元素的bean属性值
                   String beanInstance=propertyElement.getAttributeValue("bean");
                   //取得被注入对象的实例
                   Object beanObject=beans.get(beanInstance);
                   //获取setter方法的方法名,形式为setXxx
                   String methodName="set"+name.substring(0, 1).toUpperCase()+name.substring(1);
                   System.out.println("method name= "+methodName);
                   //使用反射取得指定名称，指定参数类型的setXxx方法
                   Method m=o.getClass().getMethod(methodName, beanObject.getClass().getInterfaces()[0]);
                   //调用对象o的setXxx方法
                   m.invoke(o,beanObject);
               }
           }
       }
           public Object getBean(String id){
               return beans.get(id);
           }
   }
   ```

3. 配置beans.xml文件

   ```xml
   <beans>
       <bean id="baomacar" class="com.decipher.carImplementation.BMWCar">
       </bean>
       <bean id="human" class="com.decipher.human.Human">
           <property name="car" bean="baomacar"></property>
       </bean>
   </beans>
   ```

4. 编写测试类HumanTest

   ```java
   package com.decipher.human;

   import com.decipher.spring.ClassPathXmlApplicationContext;
   import com.decipher.car.Car;
   import com.decipher.carImplementation.BMWCar;
   import com.decipher.human.Human;

   public class HumanTest {
       public static void main(String[] args) throws Exception {
           BeanFactory ctx=new ClassPathXmlApplicationContext();
           Human human=(Human)ctx.getBean("human");
           human.myCarRun();
       }
   }
   ```

上面模拟实现IOC容器的方式就是使用了XML解析，获取了待创建对象的各种信息，然后使用反射根据这些信息来创建对象，这里需要完善的是，如果使用泛型，获取对象后不需要类型转换，另外赋值也没有考虑普通赋值等情况

## IOC容器

* 控制反转（Inversion of Control，缩写为IoC），是面向对象编程中的一种设计原则，可以用来减低计算机代码之间的耦合度。其中最常见的方式叫做依赖注入（Dependency Injection，简称DI），还有一种方式叫“依赖查找”（Dependency Lookup）。通过控制反转，对象在被创建的时候，由一个调控系统内所有对象的外界实体将其所依赖的对象的引用传递给它。也可以说，依赖被注入到对象中。

* 什么是IOC
  1. 控制反转，把对象创建和对象之间的调用过程，交给Spring进行管理
  2. 使用IOC目的，为了耦合度降低
  3. 第一个spring案例就是IOC实现

* 传统的MVC在开发时，比如现在有一个UserDao类，还有一个UserService类，在UserService中需要使用UserDao对象，则直接在UserService中使用`new UserDaoImpl()`的方式获取UserDao实例，这种方式耦合度太高

* 解决传统MVC创建对象的耦合度高的问题：
  1. 工厂模式：不在UserService类中直接创建UserDaoImpl对象，而是提供一个工厂，该工厂的作用就是创建所需的实例，比如工厂提供了一个方法getDao，返回UserDao实例
  2. 使用Spring来管理java类

### IOC底层原理

* 通过xml解析，工厂模式，反射等
* IOC过程：
  1. xml配置文件，配置创建的对象
  2. 根据配置文件中的class可以获取对象创建所需的类的全类名
  3. 通过反射来创建对象
  4. 通过配置文件配置的对象属性值，使用反射调用SetXxx为对象的属性赋值(也可能是调用有参构造器直接赋值)

### IOC接口(BeanFactory)

* IOC思想基于IOC容器完成，IOC容器底层就是对象工厂
* Spring提供IOC容器实现两种方式(两个接口)：
  1. BeanFactory：IOC容器基本实现，是Srping内部的使用接口，不提供开发人员进行使用
     加载配置文件时候不会创建对象，在获取对象(使用)采取创建对象
  2. ApplicationContext：BeanFactory接口的子接口，提供更多更强大的功能，一般由开发人员进行使用
     加载配置文件时候就会把配置文件对象进行创建
     > 一般使用第二种方式，当tomcat项目启动时，则会去创建Spring管理的相关对象
        //todo,测试发现第一行都会调用对象，自己百度beanFactory和ApplicationContext创建对象的不同

      ```java
      ApplicationContext act=new ClassPathXmlApplicationContext("beans.xml");//此时就创建了对象
      User user=act.getBean("user", User.class);//此时只是从bean容器获取对象

      BeanFactory context=new ClassPathXmlApplicationContext("beans.xml");//此时没有创建对象
      User user2=act.getBean("user", User.class);//此时才开始创建对象
      ```

  3. ApplicationContext接口有两个实现类
     1. FileSystemXmlApplicationContext，此时配置bean的文件是相对磁盘路径而言的
     2. ClassPathXmlApplicationContext，此时配置bean的文件是相对项目的src而言的
     ![ApplicationContext继承结构](images/ApplicationContext继承结构.png)

### IOC操作Bean管理(基于XML)

* 什么是Bean管理
  指的是两个操作：
  1. Spring创建对象
  2. Spring注入属性

#### 创建对象

* 配置及解释：

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:p="http://www.springframework.org/schema/p"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
      <bean id="user" class="com.suftz.bean.User" />
  </beans>
  ```

  * id属性：并不是指创建对象的名字，而是给该类创建对象取个别名，因为context.getBean("id属性")，传递的就是id属性值，也就是唯一标识该bean
  * class属性：类的全路径，即全类名(包类路径)
  * ~~name属性：和id属性一样的功能，但是它的值可以是特殊字符，主要是为struts1来使用的，早已废弃~~
  * 创建对象的时候，默认是使用反射执行无参构造器来完成对象创建，所以Spring管理的对象所在的类需要定义无参构造器

#### 注入属性

* DI:依赖注入，就是注入属性
* 反射提供了三种方式注入属性：
  1. 调用有参构造器，构造器里面会初始化属性
  2. 调用无参构造器，创建对象，然后获取Field对象，设置属性
  3. 调用无参构造器，创建对象，然后调用属性的setXxx方法，来完成注入(一般选择此方式)

#### 无参构造对象

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
      <bean id="user" class="com.suftz.bean.User" >
          <!--
              name:类里面属性名称
              value:向属性注入的值
              这里注入的方式是调用属性的setXXX方法，所以没有此方法的属性无法注入,报异常
          -->
          <property name="name" value="Smith"></property>
          <property name="age" value="33"></property>
          <property name="email" value="sys@suftz.com"></property>
      </bean>
  </beans>
  ```

  此时测试用例中，获取对象后，打印对象则会输出配置中的信息

  ```java
  import com.suftz.bean.User;
  import org.junit.Test;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;

  public class SpringTest1 {
      @Test
      public void test1(){
          ApplicationContext act=new ClassPathXmlApplicationContext("beans.xml");
          User user=act.getBean("user", User.class);
          System.out.println(user);//User{name='Smith', age=33, email='sys@suftz.com'}
          user.setEmail("sys@qq.com");
          user.setName("Tom");
          user.setAge(20);
          System.out.println(user);//User{name='Tom', age=20, email='sys@qq.com'}
      }
  }
  ```

  可以通过在User类的无参构造器和相应的setXxx方法中，加上控制台输出语句，可以看到，确实调用了无参构造器和setXxx等方法，当然也可以通过debug断点，在无参构造器和相应的setXxx方法中添加断点，可以看到确实程序执行时会进入，调用这些方法

#### 有参构造注入属性

* 配置示例：

  ```java
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

      <bean id="user2" class="com.suftz.bean.User" >
          <!--
              name:有参构造器的形参列表中参数的名字
              value:形参对应的实参
              这里注入的方式是调用有参构造器，所以这里的参数列表和属性数量和名称要在有参构造器中能对应上,可以缺少
          -->
          <constructor-arg name="name" value="Smith"></constructor-arg>
          <constructor-arg name="age" value="33"></constructor-arg>
          <constructor-arg name="email" value="sys@suftz.com"></constructor-arg>
      </bean>
  </beans>
  ```

* 由于此方式需要调用类的有参构造器，所以创建的对象所在的类需要定义有参构造器

* 当然也可以同索引值来使用有参构造：
    `<constructor-arg index="1" value=""></constructor-arg>`表示有参构造器的第一个参数，一般不这样使用

#### p名称空间注入

* 使用p名称空间注入方式，可以简化基于XML配置方式
* 步骤：
  1. 在bean配置文件的头部添加声明：
      `xmlns:p="http://www.springframework.org/schema/p"`
  2. 配置如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="user" class="com.suftz.bean.User" p:name="Jack" p:age="12" p:email="sky@suftz.com"></bean>
    </bean>
</beans>
```

#### 注入空值和特殊符号

* 查看示例：

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:p="http://www.springframework.org/schema/p"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
      <bean id="user" class="com.suftz.bean.User" >
          <!--
              name:类里面属性名称
              value:向属性注入的值
              这里注入的方式是调用属性的setXXX方法，所以没有此方法的属性无法注入,报异常
          -->
          <property name="name" value="Smith"></property>
          <property name="age" value="33"></property>
          <property name="email">
              <null></null>
          </property>
          <property name="address">
              <!---值为： <华中> -->
              <value><![CDATA[<华中>]]></value>
          </property>
      </bean>
  </beans>
  ```

* 可以看出，注入空值是使用标签`<null>`,而特殊字符是使用`<![CDATA[这里写值]]>`
* 特殊字符还可以通过转义字符来实现，比如`<`为`&lt;` `>`为`&gt;`

#### 注入外部bean

* 在bean配置文件中在给属性赋值时不使用value，而是使用ref，属性值为其他bean的id值,指向bean容器中的另一个bean

* 示例：

1. 创建UserDao接口

   ```java
   package com.suftz.dao;

   public interface UserDao {
      void add();
   }
   ```

2. 创建UserDaoImpl类，实现UserDao接口方法

   ```java
   package com.suftz.dao;

   public class UserDaoImpl implements UserDao{
       @Override
       public void add() {
           System.out.println("userDao add...");
       }
   }
   ```

3. 创建UserService接口

   ```java
   package com.suftz.service;

   import com.suftz.dao.UserDao;

   public interface UserService {
       void add();
   }
   ```

4. 创建UserServiceImpl类，实现UserService接口方法

   ```java
   package com.suftz.service;

   import com.suftz.dao.UserDao;

   public class UserServiceImpl implements UserService{
       private UserDao userDao;

       public void setUserDao(UserDao userDao) {
           this.userDao = userDao;
       }

       @Override
       public void add() {
           userDao.add();
           System.out.println("userService add...");
       }
   }
   ```

5. 配置文件如下：

   ```java
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:p="http://www.springframework.org/schema/p"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
       <bean id="userService" class="com.suftz.service.UserServiceImpl" >
           <property name="userDao" ref="userDao"></property>
       </bean>
       <bean id="userDao" class="com.suftz.dao.UserDaoImpl">

       </bean>
   </beans>
   ```

6. 编写测试示例：

   ```java
   import com.suftz.bean.User;
   import com.suftz.service.UserService;
   import org.junit.Test;
   import org.springframework.context.ApplicationContext;
   import org.springframework.context.support.ClassPathXmlApplicationContext;

   public class SpringTest1 {

       @Test
       public void test2(){
           ApplicationContext act=new ClassPathXmlApplicationContext("beans2.xml");
           UserService userService=act.getBean("userService", UserService.class);
           userService.add();
           //打印：
           //userDao add...
           //userService add...
       }
   }
   ```

#### 注入属性：内部bean和级联赋值

* 一对多关系：部门和员工，一个部门有多个员工，一个员工属于一个部门

* 示例
  1. 编写一个Department类

     ```java
     package com.suftz.bean;

     public class Department {
         private String deptName;

         public Department() {
         }

         public Department(String deptName) {
             this.deptName = deptName;
         }

         public String getDeptName() {
             return deptName;
         }

         public void setDeptName(String deptName) {
             this.deptName = deptName;
         }

         @Override
         public String toString() {
             return "Department{" +
                     "deptName='" + deptName + '\'' +
                     '}';
         }
     }
     ```

  2. 编写一个Employee类

     ```java
     package com.suftz.bean;

     public class Employee {
         private String empName;
         private Department dept;

         public String getEmpName() {
             return empName;
         }

         public void setEmpName(String empName) {
             this.empName = empName;
         }

         public Department getDept() {
             return dept;
         }

         public void setDept(Department dept) {
             this.dept = dept;
         }

         public Employee() {
         }

         public Employee(String empName) {
             this.empName = empName;
         }

         public Employee(String empName, Department dept) {
             this.empName = empName;
             this.dept = dept;
         }

         @Override
         public String toString() {
             return "Employee{" +
                     "empName='" + empName + '\'' +
                     ", dept=" + dept +
                     '}';
         }
     }
     ```

  3. 编写配置文件

     ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:p="http://www.springframework.org/schema/p"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
         <!--这是使用内部bean的方式来配置-->
     <!--    <bean id="employee" class="com.suftz.bean.Employee" >-->
     <!--        <property name="empName" value="雷军"></property>-->
     <!--        <property name="dept">-->
     <!--            <bean id="dept" class="com.suftz.bean.Department">-->
     <!--                <property name="deptName" value="研发部"></property>-->
     <!--            </bean>-->
     <!--        </property>-->
     <!--    </bean>-->

         <!--这是使用外部bean的方式复制-->
     <!--    <bean id="employee" class="com.suftz.bean.Employee" >-->
     <!--        <property name="empName" value="雷军"></property>-->
     <!--        <property name="dept" ref="dept">-->
     <!--        </property>-->
     <!--    </bean>-->
     <!--    <bean id="dept" class="com.suftz.bean.Department">-->
     <!--        <property name="deptName" value="研发部"></property>-->
     <!--    </bean>-->

     <!--    可以级联赋值，但是此时dept属性，一定要有getDept方法-->
         <bean id="employee" class="com.suftz.bean.Employee" >
             <property name="empName" value="雷军"></property>
             <property name="dept" ref="dept">
             </property>
             <property name="dept.deptName" value="研发二部"></property>
         </bean>
         <bean id="dept" class="com.suftz.bean.Department">
             <property name="deptName" value="研发一部"></property>
         </bean>

     </beans>
     ```

  4. 编写测试方法

    ```java
    import com.suftz.bean.Employee;
    import com.suftz.bean.User;
    import com.suftz.service.UserService;
    import org.junit.Test;
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.support.ClassPathXmlApplicationContext;

    public class SpringTest1 {

        @Test
        public void test3(){
            ApplicationContext act=new ClassPathXmlApplicationContext("beans3.xml");
            Employee employee=act.getBean("employee",Employee.class);
            System.out.println(employee);//Employee{empName='雷军', dept=Department{deptName='研发二部'}}
        }
    }
    ```

#### 注入集合类型属性

* 注入数组类型属性
* 注入List集合类型属性
* 注入Map集合类型属性

* 举例：
  1. 编写一个学生Student类

     ```java
     package com.suftz.bean;



     import java.util.Arrays;
     import java.util.List;
     import java.util.Map;
     import java.util.Set;

     public class Student {
         private String[] courses;
         private List<String> list;
         private Set<String> set;
         private Map<String,String> map;

         public Set<String> getSet() {
             return set;
         }

         public void setSet(Set<String> set) {
             this.set = set;
         }

         public String[] getCourses() {
             return courses;
         }

         public void setCourses(String[] courses) {
             this.courses = courses;
         }

         public List<String> getList() {
             return list;
         }

         public void setList(List<String> list) {
             this.list = list;
         }

         public Map<String, String> getMap() {
             return map;
         }

         public void setMap(Map<String, String> map) {
             this.map = map;
         }

         @Override
         public String toString() {
             return "Student{" +
                     "courses=" + Arrays.toString(courses) +
                     ", list=" + list +
                     ", set=" + set +
                     ", map=" + map +
                     '}';
         }
     }
     ```

     为了方便测试，类的属性有Map,List，Set,还有数组类型

  2. 编写bean的配置文件

      ```xml
      <?xml version="1.0" encoding="UTF-8"?>
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:p="http://www.springframework.org/schema/p"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
          <bean id="stu" class="com.suftz.bean.Student">
              <property name="courses">
                  <array>
                      <value>语文</value>
                      <value>数学</value>
                      <value>英语</value>
                  </array>
              </property>
              <property name="list">
                  <list>
                      <value>张三</value>
                      <value>李四</value>
                      <value>王五</value>
                  </list>
              </property>
              <property name="set">
                  <set>
                      <value>Java</value>
                      <value>php</value>
                      <value>C++</value>
                  </set>
              </property>
              <property name="map">
                  <map>
                      <entry key="key1" value="value1"></entry>
                      <entry key="key2" value="value2"></entry>
                      <entry key="key3" value="value3"></entry>
                  </map>
              </property>
          </bean>

      </beans>
      ```

  3. 编写测试方法

     ```java

     import com.suftz.bean.Student;
     import org.junit.Test;
     import org.springframework.context.ApplicationContext;
     import org.springframework.context.support.ClassPathXmlApplicationContext;

     public class SpringTest1 {
         @Test
         public void test4(){
             ApplicationContext act=new ClassPathXmlApplicationContext("beans4.xml");
             Student stu=act.getBean("stu", Student.class);
             System.out.println(stu);
             //Student{courses=[语文, 数学, 英语], list=[张三, 李四, 王五], set=[Java, php, C++], map={key1=value1, key2=value2, key3=value3}}
         }
     }
     ```

* 在集合里面设置对象类型的值，即集合的元素也是引用型数据

* 测试示例：
  1. 新建Course类

       ```java
       package com.suftz.bean;

       public class Course {
           private String courseName;

           public Course() {
           }

           public Course(String courseName) {
               this.courseName = courseName;
           }

           public String getCourseName() {
               return courseName;
           }

           public void setCourseName(String courseName) {
               this.courseName = courseName;
           }

           @Override
           public String toString() {
               return "Course{" +
                       "courseName='" + courseName + '\'' +
                       '}';
           }
       }
       ```

  2. 修改Student类，添加属性数据类型为`List<Course>`

     ```java
     package com.suftz.bean;



     import java.util.Arrays;
     import java.util.List;
     import java.util.Map;
     import java.util.Set;

     public class Student {
         private String[] courses;
         private List<String> list;
         private Set<String> set;
         private Map<String,String> map;

         private List<Course> courseLisst;

         public List<Course> getCourseLisst() {
             return courseLisst;
         }

         public void setCourseLisst(List<Course> courseLisst) {
             this.courseLisst = courseLisst;
         }

         public Set<String> getSet() {
             return set;
         }

         public void setSet(Set<String> set) {
             this.set = set;
         }

         public String[] getCourses() {
             return courses;
         }

         public void setCourses(String[] courses) {
             this.courses = courses;
         }

         public List<String> getList() {
             return list;
         }

         public void setList(List<String> list) {
             this.list = list;
         }

         public Map<String, String> getMap() {
             return map;
         }

         public void setMap(Map<String, String> map) {
             this.map = map;
         }

         @Override
         public String toString() {
             return "Student{" +
                     "courses=" + Arrays.toString(courses) +
                     ", list=" + list +
                     ", set=" + set +
                     ", map=" + map +
                     ", courseLisst=" + courseLisst +
                     '}';
         }
     }
     ```

  3. 修改配置文件

       ```xml
       <?xml version="1.0" encoding="UTF-8"?>
       <beans xmlns="http://www.springframework.org/schema/beans"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xmlns:p="http://www.springframework.org/schema/p"
              xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
           <bean id="stu" class="com.suftz.bean.Student">
               <property name="courses">
                   <array>
                       <value>语文</value>
                       <value>数学</value>
                       <value>英语</value>
                   </array>
               </property>
               <property name="list">
                   <list>
                       <value>张三</value>
                       <value>李四</value>
                       <value>王五</value>
                   </list>
               </property>
               <property name="set">
                   <set>
                       <value>Java</value>
                       <value>php</value>
                       <value>C++</value>
                   </set>
               </property>
               <property name="map">
                   <map>
                       <entry key="key1" value="value1"></entry>
                       <entry key="key2" value="value2"></entry>
                       <entry key="key3" value="value3"></entry>
                   </map>
               </property>
               <property name="courseLisst">
                   <list>
                       <ref bean="course1"/>
                       <ref bean="course2"/>
                       <ref bean="course3"/>
                   </list>
               </property>
           </bean>
           <bean id="course1" class="com.suftz.bean.Course">
               <property name="courseName" value="大学数学"></property>
           </bean>
           <bean id="course2" class="com.suftz.bean.Course">
               <property name="courseName" value="大学英语"></property>
           </bean>
           <bean id="course3" class="com.suftz.bean.Course">
               <property name="courseName" value="大学体育"></property>
           </bean>
       </beans>
       ```

  4. 编写测试用例：

     ```java
     import com.suftz.bean.Student;
     import org.junit.Test;
     import org.springframework.context.ApplicationContext;
     import org.springframework.context.support.ClassPathXmlApplicationContext;

     public class SpringTest1 {

         @Test
         public void test4(){
             ApplicationContext act=new ClassPathXmlApplicationContext("beans4.xml");
             Student stu=act.getBean("stu", Student.class);
             System.out.println(stu);
             //Student{courses=[语文, 数学, 英语], list=[张三, 李四, 王五], set=[Java, php, C++], map={key1=value1, key2=value2, key3=value3},
             // courseLisst=[Course{courseName='大学数学'}, Course{courseName='大学英语'}, Course{courseName='大学体育'}]}
         }
     }
     ```

* 把集合注入部分提取出来
  1. 配置文件中的名称命名空间需要添加util
  2. 将集合元素使用util标签定义在公共部分，然后直接引用即可

* 修改bean配置文件如下即可：

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:p="http://www.springframework.org/schema/p"
         xmlns:util="http://www.springframework.org/schema/util"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd">
      <bean id="stu" class="com.suftz.bean.Student">
          <property name="courses" ref="courses" />
          <property name="list" ref="list" />
          <property name="set" ref="set" />
          <property name="map" ref="map" />
          <property name="courseLisst" ref="courseList" />
      </bean>

      <bean id="course1" class="com.suftz.bean.Course">
          <property name="courseName" value="大学数学"></property>
      </bean>
      <bean id="course2" class="com.suftz.bean.Course">
          <property name="courseName" value="大学英语"></property>
      </bean>
      <bean id="course3" class="com.suftz.bean.Course">
          <property name="courseName" value="大学体育"></property>
      </bean>
      <util:list id="courses">
          <value>语文</value>
          <value>数学</value>
          <value>英语</value>
      </util:list>
      <util:map id="map">
          <entry key="key1" value="value1"></entry>
          <entry key="key2" value="value2"></entry>
          <entry key="key3" value="value3"></entry>
      </util:map>
      <util:set id="set">
          <value>Java</value>
          <value>php</value>
          <value>C++</value>
      </util:set>
      <util:list id="list">
          <value>张三</value>
          <value>李四</value>
          <value>王五</value>
      </util:list>
      <util:list id="courseList">
          <ref bean="course1"/>
          <ref bean="course2"/>
          <ref bean="course3"/>
      </util:list>
  </beans>
  ```

  > 可以看到，数组放在util:list里面了

### Bean管理

* Spring有两种类型bean,一种是普通bean,另外一种是工厂bean(FactoryBean)
  1. 普通bean：在配置文件中定义bean类型就是返回类型（上面所创建的都是普通bean）
  2. 工厂bean：在配置文件定义bean类型可以和返回类型不一样

#### 工厂bean

* 获取工厂bean的步骤：
  1. 创建类：让这个类作为bean，实现接口FactoryBean
  2. 实现接口里面的方法，在实现的方法中定义返回的bean类型

* 示例：
  1. 编写MyBean类

     ```java
     package com.suftz.factorybean;


     import com.suftz.bean.Student;
     import com.suftz.bean.User;
     import org.springframework.beans.factory.FactoryBean;

     public class MyBean  implements FactoryBean<User> {
         @Override
         public User getObject() throws Exception {
             return new User("tom",20,"sys@suftz.com");
         }

         @Override
         public Class<?> getObjectType() {
             return null;
         }
     }
     ```

  2. 编写bean配置文件

     ```java
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:p="http://www.springframework.org/schema/p"
            xmlns:util="http://www.springframework.org/schema/util"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd">
         <bean id="myBean" class="com.suftz.factorybean.MyBean">
         </bean>
     </beans>
     ```

  3. 测试用例

    ```java
    import com.suftz.bean.User;
    import com.suftz.service.UserService;
    import org.junit.Test;
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.support.ClassPathXmlApplicationContext;

    public class SpringTest1 {
        @Test
        public void test5(){
            ApplicationContext act=new ClassPathXmlApplicationContext("beans5.xml");
            User user=act.getBean("myBean", User.class);
            System.out.println(user);
        }
    }
    ```

#### bean的作用域

* 在Spring里面，设置创建Bean实例是单实例还是多实例
* 即每次从Spring的IOC容器中取出bean对象，是相同的内存地址，则是单例；取出的bean对象在内存中是不同的位置，则是多实例
* 默认情况下，Spring是单实例的
* 通过使用`<bean>`标签的scope属性设置单实例还是多实例
* 在不写该属性时，默认就是scope="singleton"
* scope="prototype"则是多实例

* singleton和prototype区别
  1. singleton是单例，prototype多实例
  2. 设置scope值是singleton的时候，加载spring配置文件时就会创建单例对象
  3. 设置scope值是prototype的时候，不是在加载配置文件时候创建对象，而是在调用getBean方法获取对象时才创建对象

> scope取值还有request,session，就是在创建对象后，会放在指定的域对象中保存//todo真的吗

#### bean的生命周期

* 生命周期：从对象创建到对象销毁的过程
* bean生命周期
  1. 通过构造器创建bean实例(无参构造器)
  2. 为bean的属性设置值和对其他的bean引用(调用set方法)
  3. (把bean实例传递给bean后置处理器，执行postProcessBeforeInitialization方法)
  4. 调用bean的初始化方法(需要进行配置)
  5. (把bean实例传递给bean后置处理器，执行postProcessAfterInitialization方法)
  6. bean可以使用了（对象获取到了）
  7. 当容器关闭时，调用bean的销毁的方法(需要进行配置销毁的方法)

* 如果没有编写BeanPostProcessor接口实现且没有在bean配置文件中配置后置处理器的类，则不会有把bean实例传递给bean后置处理器的过程

* 演示示例：

  1. 实现BeanPostProcessor接口的实例：

     ```java
     package com.suftz.bean;

     import org.springframework.beans.BeansException;
     import org.springframework.beans.factory.config.BeanPostProcessor;

     public class MyBeanPosst implements BeanPostProcessor {
         @Override
         public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
             System.out.println("执行了beforeInit");
             return bean;
         }

         @Override
         public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
             System.out.println("执行了afterInit");
             return bean;
         }
     }
     ```

  2. 创建User类

     ```java
     package com.suftz.bean;

     public class User {
         private String name;
         private int age;
         private String email;
         private String address;

         public User() {
             System.out.println("第一步：调用了无参构造");
         }

         public User(String name, int age, String email) {
             System.out.println("调用有参构造");
             this.name = name;
             this.age = age;
             this.email = email;
         }

         public String getName() {
             return name;
         }

         public void setName(String name) {
             System.out.println("第二步：调用了set方法");
             this.name = name;
         }

         public int getAge() {
             return age;
         }

         public void setAge(int age) {
             this.age = age;
         }

         public String getEmail() {
             return email;
         }

         public void setEmail(String email) {
             this.email = email;
         }

         public String getAddress() {
             return address;
         }

         public void setAddress(String address) {
             this.address = address;
         }

         @Override
         public String toString() {
             return "User{" +
                     "name='" + name + '\'' +
                     ", age=" + age +
                     ", email='" + email + '\'' +
                     ", address='" + address + '\'' +
                     '}';
         }

         public void init(){
             System.out.println("第三步：执行了init初始化方法");
         }
         public void destory(){
             System.out.println("第五步：执行了destory销毁方法");
         }
     }
     ```

  3. 编写bean配置文件

     ```java
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:p="http://www.springframework.org/schema/p"
            xmlns:util="http://www.springframework.org/schema/util"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd">
         <bean id="user" class="com.suftz.bean.User" init-method="init" destroy-method="destory">
             <property name="name" value="Tom"></property>
         </bean>
         <bean id="myBeanPost" class="com.suftz.bean.MyBeanPost"></bean>
     </beans>
     ```

  4. 编写测试方法

     ```java
     import com.suftz.bean.User;
     import com.suftz.service.UserService;
     import org.junit.Test;
     import org.springframework.context.ApplicationContext;
     import org.springframework.context.support.ClassPathXmlApplicationContext;


     public class SpringTest1 {

         @Test
         public void test6(){
             ApplicationContext act=new ClassPathXmlApplicationContext("beans6.xml");
             User user=act.getBean("user", User.class);
             System.out.println("第四步：使用user对象");

             //手动销毁这个bean，用于测试整个生命周期
             ((ClassPathXmlApplicationContext)act).close();
         }
     }
     ```

  5. 控制台打印如下：

      ```text
      第一步：调用了无参构造
      第二步：调用了set方法
      执行了beforeInit
      第三步：执行了init初始化方法
      执行了afterInit
      第四步：使用user对象
      第五步：执行了destory销毁方法
      ```

#### Bean管理XML方式(自动装配)

* 根据指定装配规则(属性名或者属性类型)，Spring自动将匹配的属性值进行注入
* 使用`<bean>`标签里的autowire属性来设置装配规则：
  1. byName:根据属性名称注入，注入值使用的bean的id值和当前对象的该属性要一致
     比如当前类的属性有dept,而在spring管理的其他bean中，就有id为dept的bean
  2. byType:根据属性类型注入,当bean中有多个相同类型的bean对象时，该方式会引起冲突

> 基于xml来进行自动装配是很少用的，一般是使用注解来自动装配

#### Bean管理XML方式(引入外部属性文件)

* 以配置数据库连接池作为示例：
  1. 需要mysql数据库jdbc包和druid的包，可以去官网下载，也可以使用maven来引用：

     ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <project xmlns="http://maven.apache.org/POM/4.0.0"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
         <modelVersion>4.0.0</modelVersion>

         <groupId>com.suftz.springdemo</groupId>
         <artifactId>deom</artifactId>
         <version>1.0-SNAPSHOT</version>
     <dependencies>
         <!--spring框架最少依赖包-->
         <dependency>
             <groupId>org.springframework</groupId>
             <artifactId>spring-context</artifactId>
             <version>5.2.6.RELEASE</version>
         </dependency>
         <!--日志框架-->
         <dependency>
             <groupId>commons-logging</groupId>
             <artifactId>commons-logging</artifactId>
             <version>1.1.1</version>
         </dependency>
         <!--单元测试-->
         <dependency>
             <groupId>junit</groupId>
             <artifactId>junit</artifactId>
             <version>4.12</version>
             <scope>test</scope>
         </dependency>
         <!--mysql的jdbc-->
         <dependency>
             <groupId>mysql</groupId>
             <artifactId>mysql-connector-java</artifactId>
             <version>6.0.6</version>
         </dependency>
         <!--阿里druid数据库连接池-->
         <dependency>
             <groupId>com.alibaba</groupId>
             <artifactId>druid</artifactId>
             <version>1.0.9</version>
         </dependency>
     </dependencies>

     </project>
     ```

  2. 编写数据库连接池的文件jdbc.properties

     ```properties
     prop.driverClass=com.mysql.cj.jdbc.Driver
     prop.url=jdbc:mysql://localhost:3306/book?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai
     prop.username=root
     prop.password=booksys123
     ```

  3. 修改UserDao.java

     ```java
     package com.suftz.dao;

     public interface UserDao {

        void add();
        void printDataSource();
     }
     ```

  4. 修改UserDaoImpl.java:这个类用于测试是否能通过druid连接池获取Connection对象

     ```java
     package com.suftz.dao;

     import javax.sql.DataSource;
     import java.sql.Connection;
     import java.sql.SQLException;

     public class UserDaoImpl implements UserDao{
         private DataSource dataSource;


         public void setDataSource(DataSource dataSource) {
             this.dataSource = dataSource;
         }

         @Override
         public void add() {
             System.out.println("userDao add...");
         }
         @Override
         public void printDataSource(){
             Connection conn=null;
             System.out.println(dataSource);
             try {
                 conn=dataSource.getConnection();
                 System.out.println(conn);

             } catch (SQLException e) {
                 e.printStackTrace();
             }finally {
                 if(conn!=null) {
                     try {
                         conn.close();
                     } catch (SQLException e) {
                         e.printStackTrace();
                     }
                 }
             }
         }
     }
     ```

  5. 配置bean管理的文件，这里需要使用context标签，在xml文件头部需要声明命名空间

     ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:p="http://www.springframework.org/schema/p"
           xmlns:util="http://www.springframework.org/schema/util"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
           http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd
           http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

        <bean id="userDao" class="com.suftz.dao.UserDaoImpl">
            <property name="dataSource" ref="dataSource"></property>
        </bean>
        <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
            <property name="driverClassName" value="${prop.driverClass}"></property>
            <property name="url" value="${prop.url}"></property>
            <property name="username" value="${prop.username}"></property>
            <property name="password" value="${prop.password}"></property>

        </bean>
        <context:property-placeholder location="classpath:jdbc.properties" />

     </beans>
     ```

  6. 编写测试用例：

     ```java
     import com.suftz.bean.Employee;
     import com.suftz.bean.Student;
     import com.suftz.bean.User;
     import com.suftz.dao.UserDao;
     import com.suftz.dao.UserDaoImpl;
     import com.suftz.service.UserService;
     import org.junit.Test;
     import org.springframework.context.ApplicationContext;
     import org.springframework.context.support.ClassPathXmlApplicationContext;

     public class SpringTest1 {

         @Test
         public void test7(){
             ApplicationContext act=new ClassPathXmlApplicationContext("beans7.xml");
             UserDao userDao=act.getBean("userDao", UserDao.class);
             userDao.printDataSource();
         }
     }
     ```

  7. 打印结果如下：

     ```text
     {
         CreateTime:"2021-01-22 16:52:57",
         ActiveCount:0,
         PoolingCount:0,
         CreateCount:0,
         DestroyCount:0,
         CloseCount:0,
         ConnectCount:0,
         Connections:[
         ]
     }
     一月 22, 2021 4:52:57 下午 com.alibaba.druid.support.logging.JakartaCommonsLoggingImpl error
     严重: testWhileIdle is true, validationQuery not set
     一月 22, 2021 4:52:57 下午 com.alibaba.druid.support.logging.JakartaCommonsLoggingImpl info
     信息: {dataSource-1} inited
     com.mysql.cj.jdbc.ConnectionImpl@38c59b
     ```

### IOC操作Bean管理(基于注解)

* 什么是注解
  1. 注解是代码特殊标记，格式：`@注解名称(属性名称1=属性值1,属性名称2=属性值2,...)`
  2. 注解的位置：类，方法，属性上面
  3. 使用注解的目的：简化xml配置

* Spring针对Bean管理中创建对象提供注解

  |注解|含义|
  |:----|:----|
  |@Component|最普通的组件，可以被注入到spring容器进行管理|
  |@Repository|作用于持久层|
  |@Service|作用于业务逻辑层|
  |@Controller|作用于表现层（spring-mvc的注解）|

  上面四个注解的功能是一样的，都可以用来创建bean实例(也就是说，它们在使用上可以相互替换)

#### 注解管理bean步骤

1. 注解功能需要引入spring-aop包，如果没有则需要添加，maven配置spring-context会自动加上这个包
2. 开启组件扫描

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:p="http://www.springframework.org/schema/p"
          xmlns:util="http://www.springframework.org/schema/util"
          xmlns:context="http://www.springframework.org/schema/context"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

       <!--
           如果想扫描多个包，可以使用逗号分隔开,比如 com.suftz.bean,com.suftz.dao
           或者写包的上层目录，这样其子包都能被扫描,比如 com.suftz
       -->
       <context:component-scan base-package="com.suftz" />
   </beans>
   ```

3. 编写UserServiceImpl类

   ```java
   package com.suftz.service;

   import com.suftz.dao.UserDao;
   import org.springframework.stereotype.Component;

   //等同于bean id="userService" class="..."
   //value不写的话，默认值是类名称，首字母小写
   @Service(value="userService")
   public class UserServiceImpl implements UserService{

       private UserDao userDao;

       public void setUserDao(UserDao userDao) {
           this.userDao = userDao;
       }

       @Override
       public void add() {
           System.out.println("userService add...");
       }
   }
   ```

   这里的@Service也可以替换成其他三个注解，功能一样

4. 编写测试用例：

   ```java
   import com.suftz.service.UserService;
   import org.junit.Test;
   import org.springframework.context.ApplicationContext;
   import org.springframework.context.support.ClassPathXmlApplicationContext;

   public class SpringTest1 {

       @Test
       public void test8(){
           ApplicationContext act=new ClassPathXmlApplicationContext("beans8.xml");
           UserService userService=act.getBean("userService", UserService.class);
           System.out.println(userService);//com.suftz.service.UserServiceImpl@1548a08
           userService.add();//userService add...
       }
   }
   ```

#### 基于注解方式实现属性注入

* 属性注入有关的注解：
  1. @Autowired：根据属性类型进行自动装配
  2. @Qualifier：根据属性名称进行注入
  3. @Resource：可以根据类型注入，也可以根据名称注入
  4. @Value：注入普通类型属性

* 步骤：
  1. 第一步先要把属性相关的对象创建，也就是说作为属性的数据类型所在的类，需要使用创建对象的注解
  2. **在属性上使用注解实现属性注入，该属性不需要setXxx方法**

* @Autowired注解可以单独使用，它是根据属性的类型来自动装配，但是如果有被Spring容器管理的多个相同类型的bean对象，就会出问题，不确定找哪一个对象注入
* 而@Qualifier注解结合@Autowired注解使用可以确定一个bean对象，因为如果仅仅根据属性名称，一个接口可能有多个实现类，spring并不知道要找哪一个实现类，而@Qualifier指明出了bean对象中id值

* 测试用例如下：
  1. UserDao接口如下：

     ```java
     package com.suftz.dao;

     public interface UserDao {
       void add();
     }
     ```

  2. UserDaoImpl类如下

     ```java
     package com.suftz.dao;

     import org.springframework.stereotype.Component;


     @Component(value="userDao")
     public class UserDaoImpl implements UserDao{

         @Override
         public void add() {
             System.out.println("userDao add...");
         }
     }
     ```

  3. UserService接口如下：

     ```java
     package com.suftz.service;

     public interface UserService {
        void add();
     }
     ```

  4. UserServiceImpl类如下：

     ```java
     package com.suftz.service;

     import com.suftz.dao.UserDao;
     import org.springframework.beans.factory.annotation.Autowired;
     import org.springframework.beans.factory.annotation.Qualifier;
     import org.springframework.stereotype.Component;

     @Component(value="userService")
     public class UserServiceImpl implements UserService{

         @Autowired
         @Qualifier(value = "userDao")
         private UserDao userDao;

         public void setUserDao(UserDao userDao) {
             this.userDao = userDao;
         }

         @Override
         public void add() {
             userDao.add();
             System.out.println("userService add...");
         }
     }
     ```

  5. 配置文件如下：

     ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:p="http://www.springframework.org/schema/p"
            xmlns:util="http://www.springframework.org/schema/util"
            xmlns:context="http://www.springframework.org/schema/context"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

         <!--
             如果想扫描多个包，可以使用逗号分隔开
             或者写包的上层目录，这样其子包都能被扫描
         -->
         <context:component-scan base-package="com.suftz" />
     </beans>
     ```

  6. 测试方法如下：

     ```java
     import com.suftz.service.UserService;
     import com.suftz.service.UserServiceImpl;
     import org.junit.Test;
     import org.springframework.context.ApplicationContext;
     import org.springframework.context.support.ClassPathXmlApplicationContext;

     public class SpringTest1 {

         @Test
         public void test(){
             ApplicationContext act=new ClassPathXmlApplicationContext("beans.xml");
             UserService userService=act.getBean("userService", UserService.class);
             System.out.println(userService);
             userService.add();
         }
     }
     ```

* @Resource注解
  1. 该注解是javax.annotation.Resource，并不是spring提供的
  2. 既能根据数据类型注入，也可以根据变量名称注入

* 示例写法：

  ```java
  package com.suftz.service;

  import com.suftz.dao.UserDao;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.beans.factory.annotation.Qualifier;
  import org.springframework.stereotype.Component;

  import javax.annotation.Resource;

  @Component(value="userService")
  public class UserServiceImpl implements UserService{

  //    @Autowired
  //    private UserDao userDao;

  //    @Autowired
  //    @Qualifier(value = "userDao")
  //    private UserDao userDao;

  //    @Resource
  //    private UserDao userDao;

      @Resource(name="userDao")
      private UserDao userDao;

      public void setUserDao(UserDao userDao) {
          this.userDao = userDao;
      }

      @Override
      public void add() {
          userDao.add();
          System.out.println("userService add...");
      }
  }
  ```

* @Value用于普通类型的数据注入
* @Value使用示例：

  ```java
  package com.suftz.bean;

  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.stereotype.Component;

  @Component(value="user")
  public class User {
      @Value(value = "Tom")
      private String name;
      @Value(value = "22")
      private int age;
      @Value(value = "skysha@suftz.com")
      private String email;
      @Value(value = "长安城")
      private String address;

      public User() {

      }

      @Override
      public String toString() {
          return "User{" +
                  "name='" + name + '\'' +
                  ", age=" + age +
                  ", email='" + email + '\'' +
                  ", address='" + address + '\'' +
                  '}';
      }
  }
  ```

  ```java
  import com.suftz.bean.User;
  import org.junit.Test;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;

  public class SpringTest1 {

      @Test
      public void test(){
          ApplicationContext act=new ClassPathXmlApplicationContext("beans.xml");
          User user=act.getBean("user", User.class);
          System.out.println(user);//User{name='Tom', age=22, email='skysha@suftz.com', address='长安城'}
      }
  }
  ```

#### 完全使用注解开发

* 经过上面的注解开发，仍不能离开配置文件，其中组件扫描就是使用的配置文件
* 然后ClassPathXmlApplicationContext类构造器接收一个配置文件的路径地址作为参数

* 完全注解开发步骤：
  1. 创建配置类，来替代xml配置文件
  2. 编写测试类的时候使用AnnotationConfigApplicationContext类来获取context实例，该类需要传递配置类的运行时对象到构造器中

* 实例步骤：
  1. 创建一个类作为配置类，用于配置扫描组件等信息

      ```java
      package com.suftz.config;

      import org.springframework.context.annotation.ComponentScan;
      import org.springframework.context.annotation.Configuration;

      @Configuration                               //作为配置类，替代xml配置文件
      @ComponentScan(basePackages = {"com.suftz"})
      public class SpringConfig {
      }
      ```

  2. 编写测试用例的时候，需要使用AnnotationConfigApplicationContext类，如下：

     ```java

     import com.suftz.bean.User;
     import com.suftz.config.SpringConfig;
     import org.junit.Test;
     import org.springframework.context.ApplicationContext;
     import org.springframework.context.annotation.AnnotationConfigApplicationContext;

     public class SpringTest1 {
         @Test
         public void test10(){
             ApplicationContext act=new AnnotationConfigApplicationContext(SpringConfig.class);
             User user=act.getBean("user",User.class);
             System.out.println(user);
         }
     }
     ```

> 一般完全注解开发是基于SpringBoot，而不是基于Spring

## AOP

### AOP概念

* 什么是AOP?
  AOP为Aspect Oriented Programming的缩写，意为：面向切面编程，通过预编译方式和运行期间动态代理实现程序功能的统一维护的一种技术。AOP是OOP的延续，是软件开发中的一个热点，也是Spring框架中的一个重要内容，是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率
* 通俗描述：不通过修改源代码方式，在主干功能里面添加新功能
* 比如：有一个登陆模块的功能：用户前端输入用户名和密码，后端获取之后，与数据库表进行比对，用户名和密码正确则登陆成功，否则登陆失败。但是现在需求变了，需要在登录的模块逻辑中加一个权限判断，能不能通过不修改整个登录模块功能，不修改登录的源代码前提下，实现权限登录，即权限判断后再登录，在Spring中通过配置文件即可实现

### AOP底层原理

* AOP底层使用动态代理
* 有两种情况动态代理
  * 第一种：有接口情况，使用JDK动态代理
  * 第二种：没有接口情况，使用CGLIB动态代理

* 有接口的动态代理
  创建一个实现类，此类就是代理类，它也继承了接口，然后在方法中对原实现方法进行增强，参考静态代理//todo

* 没有接口的动态代理//todo
  创建一个新的类，新类是被代理类的子类，子类重写父类的方法，通过super方式还能直接使用父类的方法，并增强新类的功能
  CGLIB动态代理的做法是：创建当前类子类的代理对象

* 有接口的动态代理实现

    //todo，源码和示例，基于java提供的Proxy

* 没有接口的动态代理实现

//todo,源码和示例，基于cglib实现的
//比较这两种代理方式，cglib的源码
//cglib是基于ASM的框架，很强大

### AOP术语

* 以下列User类举例：

  ```java
  class User{
      add()
      update()
      select()
      delete()
  }
  ```

* 连接点：类里面哪些方法可以被增强，这些方法称为连接点,比如上面四个方法都可以称为连接点
* 切入点：实际被真正增强的方法，称为切入点，比如我们选择增强add方法
* 通知(增强)：实际增强的逻辑部分称为通知(增强)，比如在add方法前后对数据进行打印输出
  通知的类型：
  1. 前置通知:方法之前执行的
  2. 后置通知:方法执行之后执行的，该通知不一定能执行(可能报错)
  3. 环绕通知:方法前后都有会去执行(方法执行之后未必能执行)
  4. 异常通知:方法执行出错时去执行
  5. 最终通知:无论方法是否出错都会执行，有点类似异常处理的finally
* 切面：是一个动作
  **把通知应用(加到)到切入点**的过程，比如把权限功能加入到登录模块的过程就是切面

### AOP操作

* Spring框架一般都是基于AspectJ实现AOP操作
  AspectJ不是Spring组成部分，独立AOP框架，一般把AspectJ和Spring框架一起使用，进行AOP操作
* 基于AspectJ实现AOP操作
  1. 基于XML配置文件实现
  2. 基于注解方式实现(推荐)

* 在项目模块中引入AOP相关依赖，通过maven引入依赖如下：

  ```xml
  <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjrt</artifactId>
      <version>1.8.9</version>
  </dependency>
  <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjtools</artifactId>
      <version>1.8.9</version>
  </dependency>
  <dependency>
  <groupId>org.aspectj</groupId>
  <artifactId>aspectjweaver</artifactId>
  <version>1.7.4</version>
  </dependency>
  ```

#### 切入点表达式

* 切入点表达式作用：知道对哪个类里面的哪个方法进行增强
* 语法结构：
  `execution([权限修饰符][返回类型][类全路径][方法名称]([参数列表]))`
  1. 比如对com.suftz.dao.UserDao中的add方法进行增强：
     `execution(* com.suftz.dao.UserDao.add(..))`
     上面第一个`*`表示权限修饰符，即任意的修饰符，并且返回类型给省略了，`..`表示参数列表
  2. 对com.suftz.dao.UserDao类里面的所有方法进行增强：
     `execution(* com.suftz.dao.UserDao.*(..))`
  3. 对com.suftz.dao包里面的所有类，类里面所有方法进行增强:
     `execution(* com.suftz.dao.*.*(..))`

### AspectJ注解

1. 创建类，在类里面定义方法
2. 创建增强类
3. 进行通知的配置
   1. 在Spring配置文件中，开启注解扫描
   2. 使用注解创建被代理类和代理类对象
   3. 在增强类上面添加注解@Aspect
       代理对象上面需要添加@Aspect注解标记
   4. 在Spring配置文件中开启生成代理对象
      这里配置文件命名空间需要添加aop
      `<aop:aspectj-autoproxy></aop:aspectj-autoproxy>`或者在配置类中加注解`@EnableAspectJAutoProxy`
4. 配置不同类型的通知
   1. 在增强类的里面，在作为通知的方法上面添加通知类型注解，使用切入点表达式
   2. 把相同切入点抽取
   3. 有多个增强类对同一个方法进行增强，设置增强类优先级
      在增强类上面添加@Order(数字类型值)，数字类型值越小优先级越高

* 完整示例：

1. 创建UserDaoImpl类，它继承自UserDao接口。并使用注解标记

   ```java
   package com.suftz.dao;

   import org.springframework.stereotype.Component;

   @Repository(value="userDao")
   public class UserDaoImpl implements UserDao{

       @Override
       public void add() {
           //int i=10/0;
           System.out.println("userDao add...");
       }
   }
   ```

2. 创建增强类，同时使用注解标识相关信息。同时添加了切入点表达式，如果多个通知作用于同一个切入点，则可以抽取公共切入点，使用@Pointcut标记

   ```java
   package com.suftz.aop;

   import org.aspectj.lang.ProceedingJoinPoint;
   import org.aspectj.lang.annotation.*;
   import org.springframework.core.annotation.Order;
   import org.springframework.stereotype.Component;

   @Component
   @Aspect//标识该类为增强类，提供切入方法
   @Order(1)//如果被增强类有多个增强的类，则需要设置优先级，数字越小优先级越高
   public class UserDaoProxy {
       //抽取的公共切入点
       @Pointcut(value="execution(* com.suftz.dao.*.add(..))")
       public void pointAdd(){

       }
       //@Before(value = "execution(* com.suftz.dao.*.add(..))")
       @Before(value = "pointAdd()")
       public void before(){
           System.out.println("userDaoProxy.....前置通知...");
       }

       @After(value="pointAdd()")
       public void after(){
           System.out.println("userDaoProxy.....最终通知...");
       }

       @AfterReturning(value = "pointAdd()")
       public void afterReturning(){
           System.out.println("userDaoProxy.....后置(返回)通知...");
       }
       @AfterThrowing(value="pointAdd()")
       public void afterThrowing(){
           System.out.println("userDaoProxy.....异常通知...");
       }
       @Around(value = "pointAdd()")
       public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
           System.out.println("userDaoProxy.....环绕之前....");
           //执行被增强的方法
           proceedingJoinPoint.proceed();
           System.out.println("userDaoProxy.....环绕之后....");
       }
   }
   ```

3. 创建配置文件

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:p="http://www.springframework.org/schema/p"
          xmlns:util="http://www.springframework.org/schema/util"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd
          http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd
          http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

       <!--
           如果想扫描多个包，可以使用逗号分隔开
           或者写包的上层目录，这样其子包都能被扫描
       -->
       <context:component-scan base-package="com.suftz" />
       <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
   </beans>
   ```

4. 创建测试方法：

   ```java
   import com.suftz.dao.UserDao;
   import org.junit.Test;
   import org.springframework.context.ApplicationContext;
   import org.springframework.context.support.ClassPathXmlApplicationContext;

   public class SpringTest2 {
       @Test
       public void test1(){
           ApplicationContext context=new ClassPathXmlApplicationContext("day02/beans.xml");
           UserDao userDao=context.getBean("userDao", UserDao.class);
           userDao.add();
       }
   }
   ```

5. 打印结果：
   在没有异常的情况下，控制台打印：

   ```text
   userDaoProxy.....环绕之前...
   userDaoProxy.....前置通知...
   userDao add...
   userDaoProxy.....环绕之后...
   userDaoProxy.....最终通知...
   userDaoProxy.....后置(返回)通知...
   ```

   在UserDaoImpl的方法中写一个运行时异常，比如除0，则打印结果：

   ```text
   userDaoProxy.....环绕之前...
   userDaoProxy.....前置通知...
   userDaoProxy.....最终通知...
   userDaoProxy.....异常通知...
   ```

### AspectJ配置文件

* 使用配置文件来进行AspectJ操作比较少

* 步骤：
  1. 创建两个类，增强类和被增强类，以及相应方法
  2. 在spring配置文件中创建两个类对象，即配置两个bean
  3. 在spring配置文件中配置切入点

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:p="http://www.springframework.org/schema/p"
         xmlns:util="http://www.springframework.org/schema/util"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd
         http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd
         http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

      <!--
          如果想扫描多个包，可以使用逗号分隔开
          或者写包的上层目录，这样其子包都能被扫描
      -->
  <!--    <context:component-scan base-package="com.suftz"/>-->
  <!--    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>-->
  <!--    -->
      <bean id="userDao" class="com.suftz.dao.UserDaoImpl"/>
      <bean id="userDaoProxy" class="com.suftz.aop.UserDaoProxy"/>

      <aop:config>
          <!--切入点-->
          <aop:pointcut id="p1" expression="execution(* com.suftz.dao.*.add(..))"/>
          <!--配置切面-->
          <aop:aspect ref="userDaoProxy" order="1">
              <aop:before method="before" pointcut-ref="p1"/>
              <aop:after method="after" pointcut-ref="p1"/><!--这两个的执行，在正确的情况下，是按照前后顺序相反执行的-->
              <aop:after-returning method="afterReturning" pointcut-ref="p1"/><!--这两个的执行，在正确的情况下，是按照前后顺序相反执行的-->
              <aop:after-throwing method="afterThrowing" pointcut-ref="p1"/>
              <aop:around method="around" pointcut-ref="p1"/>
          </aop:aspect>
      </aop:config>

  </beans>
  ```

  执行的结果与使用注解是相似的，但是唯一不同的是，把after最终通知与after-returning返回通知互换位置，它们的打印结果也是互换的
  如果在UserDaoImpl类中发生异常，比如上面的运行时异常，也会触发异常通知

## JdbcTemplate

### JdbcTemplate概念

* 什么是JdbcTemplate
  Spring框架对JDBC进行封装，使用JdbcTemplate方便实现对数据库操作
* 准备工作
  1. 引入相关jar包
     jdbc数据库jar包，druid数据库连接池jar包
     spring-jdbc,spring-tx,spring-orm(整合其他数据库框架使用)
     可以去相关网站上去下载对应版本的jar包，也可以直接使用maven来导入：

     ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <project xmlns="http://maven.apache.org/POM/4.0.0"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
         <modelVersion>4.0.0</modelVersion>

         <groupId>com.suftz.springdemo</groupId>
         <artifactId>deom</artifactId>
         <version>1.0-SNAPSHOT</version>
         <dependencies>
             <dependency>
                 <groupId>org.springframework</groupId>
                 <artifactId>spring-context</artifactId>
                 <version>5.2.6.RELEASE</version>
             </dependency>
             <dependency>
                 <groupId>commons-logging</groupId>
                 <artifactId>commons-logging</artifactId>
                 <version>1.1.1</version>
             </dependency>
             <dependency>
                 <groupId>junit</groupId>
                 <artifactId>junit</artifactId>
                 <version>4.12</version>
                 <scope>test</scope>
             </dependency>
             <dependency>
                 <groupId>mysql</groupId>
                 <artifactId>mysql-connector-java</artifactId>
                 <version>6.0.6</version>
             </dependency>
             <dependency>
                 <groupId>com.alibaba</groupId>
                 <artifactId>druid</artifactId>
                 <version>1.0.9</version>
             </dependency>


             <dependency>
                 <groupId>org.aspectj</groupId>
                 <artifactId>aspectjrt</artifactId>
                 <version>1.8.9</version>
             </dependency>
             <dependency>
                 <groupId>org.aspectj</groupId>
                 <artifactId>aspectjtools</artifactId>
                 <version>1.8.9</version>
             </dependency>
             <dependency>
                 <groupId>org.aspectj</groupId>
                 <artifactId>aspectjweaver</artifactId>
                 <version>1.7.4</version>
             </dependency>

             <dependency>
                 <groupId>org.springframework</groupId>
                 <artifactId>spring-jdbc</artifactId>
                 <version>5.2.6.RELEASE</version>
             </dependency>
             <dependency>
             <groupId>org.springframework</groupId>
             <artifactId>spring-tx</artifactId>
                 <version>5.2.6.RELEASE</version>
             </dependency>
             <dependency>
             <groupId>org.springframework</groupId>
             <artifactId>spring-orm</artifactId>
                 <version>5.2.6.RELEASE</version>
             </dependency>
         </dependencies>

     </project>
     ```

  2. 在spring配置文件中配置数据库连接池
  3. 配置JdbcTemplate对象，注入DataSource

     ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:p="http://www.springframework.org/schema/p"
            xmlns:util="http://www.springframework.org/schema/util"
            xmlns:context="http://www.springframework.org/schema/context"
            xmlns:aop="http://www.springframework.org/schema/aop"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd
            http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd
            http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

         <!--
             如果想扫描多个包，可以使用逗号分隔开
             或者写包的上层目录，这样其子包都能被扫描
         -->
         <context:component-scan base-package="com.suftz"/>
         <aop:aspectj-autoproxy></aop:aspectj-autoproxy>

         <!--配置数据库连接池信息，获取数据源对象-->
         <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
             <property name="driverClassName" value="${prop.driverClass}"></property>
             <property name="url" value="${prop.url}"></property>
             <property name="username" value="${prop.username}"></property>
             <property name="password" value="${prop.password}"></property>
         </bean>
         <!--将jdbcTemplate放入bean容器中管理，并将druid数据库连接池对象注入到spring的JdbcTemplate对象中-->
         <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
             <property name="dataSource" ref="dataSource"></property>
         </bean>
     </beans>
     ```

  4. 创建service类，创建dao类，在dao注入jdbcTemplate对象(要开启组件扫描，把这些类都放在bean容器中管理，注入属性也是通过注解完成)
  5. 在service类中编写业务代码，包括的数据库操作有增删改查等

* 完整代码如下：

1. User类

   ```java
   package com.suftz.bean;

   import org.springframework.stereotype.Component;

   @Component(value="user")
   public class User {
       private Integer uid;
       private String uname;
       private Integer age;
       private String password;
       private String email;
       private String address;

       public User() {
       }

       public User(String uname, Integer age, String password, String email, String address) {
           this.uname = uname;
           this.age = age;
           this.password = password;
           this.email = email;
           this.address = address;
       }

       public Integer getUid() {
           return uid;
       }

       public void setUid(Integer uid) {
           this.uid = uid;
       }

       public String getUname() {
           return uname;
       }

       public void setUname(String uname) {
           this.uname = uname;
       }

       public Integer getAge() {
           return age;
       }

       public void setAge(Integer age) {
           this.age = age;
       }

       public String getPassword() {
           return password;
       }

       public void setPassword(String password) {
           this.password = password;
       }

       public String getEmail() {
           return email;
       }

       public void setEmail(String email) {
           this.email = email;
       }

       public String getAddress() {
           return address;
       }

       public void setAddress(String address) {
           this.address = address;
       }

       @Override
       public String toString() {
           return "User{" +
                   "uid='" + uid + '\'' +
                   ", uname='" + uname + '\'' +
                   ", age=" + age +
                   ", password='" + password + '\'' +
                   ", email='" + email + '\'' +
                   ", address='" + address + '\'' +
                   '}';
       }
   }
   ```

2. UserDao接口

   ```java
   package com.suftz.dao;

   import com.suftz.bean.User;

   import java.util.List;

   public interface UserDao {

      int addUser(User user);
      int deleteUserByUid(Integer uid);
      int updateUser(User user);
      User getUserByUid(Integer uid);
      List<User> queryAll();
      int[] batchUpdate(List<Object[]> list);
   }
   ```

3. UserDaoImpl类

   ```java
   package com.suftz.dao;

   import com.suftz.bean.User;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.dao.EmptyResultDataAccessException;
   import org.springframework.jdbc.core.BeanPropertyRowMapper;
   import org.springframework.jdbc.core.JdbcTemplate;
   import org.springframework.stereotype.Repository;

   import java.util.ArrayList;
   import java.util.List;

   @Repository(value="userDao")
   public class UserDaoImpl implements UserDao{

       @Autowired
       private JdbcTemplate jdbcTemplate;

       @Override
       public int addUser(User user) {
   //        第一个参数是：sql语句
   //        第二个参数是：可变参数，设置sql中的值
           String sql="insert into user(uname,password,email,address,age) values(?,?,?,?,?)";
           int insertRows=jdbcTemplate.update(sql,user.getUname(),user.getPassword(),user.getEmail(),user.getAddress(),user.getAge());
           return insertRows;
       }

       @Override
       public int deleteUserByUid(Integer uid) {
           String sql="delete from user where uid=?";
           int deleteRows=jdbcTemplate.update(sql,uid);
           return deleteRows;
       }

       @Override
       public int updateUser(User user) {
           String sql="update user set uname=?,email=?,address=?,age=? where uid=?";
           int updateRows=jdbcTemplate.update(sql,user.getUname(),user.getEmail(),user.getAddress(),user.getAge(),user.getUid());
           return updateRows;
       }

       @Override
       public User getUserByUid(Integer uid) {
           String sql="select uid,uname,email,address,age from user where uid=?";
           User user=null;
           try{
               //如果查询的是空值则会直接报错，这个体验很差
               user=jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),uid);
           }catch(EmptyResultDataAccessException e){
           }
           return user;
       }

       @Override
       public List<User> queryAll() {
           String sql="select * from user";
           List<User> userList = new ArrayList<>();
           try {
               userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
           }catch(EmptyResultDataAccessException e){
           }
           return userList;
       }

       @Override
       public int[] batchUpdate(List<Object[]> list) {
           String sql="update user set age=?,address=? where uid=?";
           return jdbcTemplate.batchUpdate(sql,list);
       }
   }
   ```

4. UserService接口

   ```java
   package com.suftz.service;

   import com.suftz.bean.User;

   import java.util.List;

   public interface UserService {
       boolean addUser(User user);
       boolean deleteUserByUid(Integer uid);
       boolean updateUser(User user);
       User getUserByUid(Integer uid);
       List<User> getAllUsers();
       int[] batchUpdate(List<Object[]> list);
   }
   ```

5. UserServiceImpl接口

   ```java
   package com.suftz.service;

   import com.suftz.bean.User;
   import com.suftz.dao.UserDao;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;

   import java.util.List;

   @Service(value="userService")
   public class UserServiceImpl implements UserService{

       @Autowired
       private UserDao userDao;

       @Override
       public boolean addUser(User user) {

           int insertRow=userDao.addUser(user);
           if(insertRow==1){
               System.out.println("插入一条数据成功："+user.toString());
               return true;
           }
           return false;
       }

       @Override
       public boolean deleteUserByUid(Integer uid) {
           int deleteRows=userDao.deleteUserByUid(uid);
           if(deleteRows==1){
               System.out.println("删除一条数据成功，uid="+uid);
               return true;
           }
           return false;
       }

       @Override
       public boolean updateUser(User user) {
           int updateRows=userDao.updateUser(user);
           if(updateRows==1){
               System.out.println("更新一条数据成功:"+user.toString());
               return true;
           }
           return false;
       }

       @Override
       public User getUserByUid(Integer uid) {
           return userDao.getUserByUid(uid);
       }

       @Override
       public List<User> getAllUsers() {
           return userDao.queryAll();
       }

       @Override
       public int[] batchUpdate(List<Object[]> list) {
           return userDao.batchUpdate(list);
       }
   }
   ```

6. jdbc.properties配置数据库连接池

   ```properties
   prop.driverClass=com.mysql.cj.jdbc.Driver
   prop.url=jdbc:mysql://localhost:3306/spring?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai
   prop.username=root
   prop.password=booksys123
   prop.minEvictableIdleTimeMillis=300000
   prop.validationQuery=SELECT 1
   ```

7. spring管理bean的配置文件

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:p="http://www.springframework.org/schema/p"
          xmlns:util="http://www.springframework.org/schema/util"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd
          http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd
          http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

       <!--
           如果想扫描多个包，可以使用逗号分隔开
           或者写包的上层目录，这样其子包都能被扫描
       -->
       <context:component-scan base-package="com.suftz"/>
       <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
       <context:property-placeholder location="classpath:day01/jdbc.properties" />
       <!--配置数据库连接池信息，获取数据源对象-->
       <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
           <property name="driverClassName" value="${prop.driverClass}"></property>
           <property name="url" value="${prop.url}"></property>
           <property name="username" value="${prop.username}"></property>
           <property name="password" value="${prop.password}"></property>
           <property name="minEvictableIdleTimeMillis" value="${prop.minEvictableIdleTimeMillis}"></property>
           <property name="validationQuery" value="${prop.validationQuery}"></property>
       </bean>
       <!--将jdbcTemplate放入bean容器中管理，并将druid数据库连接池对象注入到spring的JdbcTemplate对象中-->
       <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
           <property name="dataSource" ref="dataSource"></property>
       </bean>
   </beans>
   ```

8. 编写测试方法

   ```java
   import com.suftz.bean.User;
   import com.suftz.service.UserService;
   import org.junit.Before;
   import org.junit.Test;
   import org.springframework.context.ApplicationContext;
   import org.springframework.context.support.ClassPathXmlApplicationContext;

   import java.util.ArrayList;
   import java.util.List;

   public class SpringTest2 {

       UserService userService;

       @Before
       public void init(){
           ApplicationContext context=new ClassPathXmlApplicationContext("day02/beans2.xml");
           userService=context.getBean("userService",UserService.class);
       }

       @Test
       public void testInsert(){
           User user=new User("刘备",null,"123sdfs","sky@suftz.com",null);
           userService.addUser(user);
           user=new User("诸葛亮",28,"123ssdfdfs","skyzhuge@suftz.com","古隆中");
           userService.addUser(user);
       }

       @Test
       public void testUpdate(){
           User user=new User("刘备",45,"123sdfs","sky@suftz.com","益州");
           user.setUid(1);
           userService.updateUser(user);
       }

       @Test
       public void testQuery(){
           Integer uid=1;
           User user=userService.getUserByUid(uid);
           System.out.println(user);
           uid=12344;
           user=userService.getUserByUid(uid);
           if(user!=null){
               System.out.println(user);
           }else{
               System.out.println("uid:"+uid+",该数据未找到");
           }
       }

       @Test
       public void testQueryAll(){
           System.out.println(userService.getAllUsers());
       }

       @Test
       public void testBatchUpdate(){
           List<Object[]> list=new ArrayList();
           list.add(new Object[]{60,"益州",1});
           list.add(new Object[]{60,"益州",2});
           userService.batchUpdate(list);
       }

       @Test
       public void testDelete(){
           userService.deleteUserByUid(1);
           userService.deleteUserByUid(12344);
       }
   }
   ```

* 这里面需要注意的几个方法：

  1. 查询返回某个数值
  `public <T> T queryForObject(String sql, Class<T> requiredType, @Nullable Object... args)`

  2. 查询返回某个封装的对象
  `public <T> T queryForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)`
  RowMapper<T>是接口，返回不同数据类型的数据，使用这个接口的实现类完成数据封装

  3. 查询返回集合
  `public List<T> query(String sql, RowMapper<T> rowMapper, @Nullable Object... args)`

  4. 批量操作(批量添加、更新、删除)：
  `public int[] batchUpdate(String sql,List<Object[]> batchArgs)`

## 事务

### 事务概念

* 什么是事务
  事务是数据库操作最基本单元，逻辑上一组操作，要么都成功，如果有一个失败则所有操作都失败
* 典型场景：银行转账
* 事务的四个特性(ACID)：
  1. 原子性；要么都成功，要么都失败，不可分割
  2. 一致性：指系统从一个正确的状态,迁移到另一个正确的状态。根据具体业务场景正确状态含义不同，比如银行转账，不论两个人怎么互相转账，他们两个人的账目总数是不变的(只考虑他们互相转账，不是存取钱)
  3. 隔离性：多事务操作同一个数据时，不会产生影响
  4. 持久性：事务提交后，在数据库物理层面，真正发生了改变

### Spring事务管理

* 事务添加到JavaEE三层结构里面Service层(业务逻辑层)
* 在Spring进行事务管理操作
  编程式事务管理和声明式事务管理
  * 编程式事务管理：通过把多个数据库操作使用同一个连接，并开启手动提交，然后try-catch，出现异常则回滚
  * 声明式事务管理(使用)：通过配置的方式来完成

* 声明式事务管理：
  1. 基于注解方式(使用)
  2. 基于XML配置文件方式

* 在Spring进行声明式事务管理，底层使用AOP原理

* Spring事务管理API
  提供了一个接口，代表事务管理器，这个接口针对不同的框架提供了不同的实现类
  ![事务管理接口实现类](images/事务管理接口实现类.png)

#### Spring事务管理(注解方式)

* 步骤：
  1. 在配置文件里创建事务管理器，并将数据库连接池数据源注入到事务管理器对象属性中

     ```xml
     <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
     </bean>
     ```

  2. 在Spring配置文件中，开启事务注解
     注意；在spring配置文件头部引入名称空间tx

     ```xml
     <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
     ```

  3. 在需要开启事务处理的类或者方法上添加事务注解(一般加到类上面)
     * @Transactional,这个注解添加到类上面，也可以添加到方法上面
     * 如果把这个注解添加到类上面，这个类里面所有的方法都添加事务
     * 如果把这个注解添加方法上面，只是为这个方法添加事务

##### 注解@Transactional配置事务参数

* 在这个注解里面可以配置事务相关参数
* 可以通过查看@Transactional注解的源码查看他的参数有哪些
* 参数列表

  |参数名称|描述|
  |:----|:----|
  |propagation|事务传播行为|
  |isolation|事务隔离级别|
  |timeout|超时时间|
  |readOnly|是否只读|
  |rollbackFor|回滚|
  |noRollbackFor|不回滚|

* 事务传播行为
  解释：
  事务行为：指的是会对数据产生影响的行为，比如增删改，而查不会对数据产生影响
  事务传播：如果多个方法都添加了事务，那么这些方法相互调用时，事务方法应该怎样进行
  Spring中有七种事务传播行为：//todo，更加详细了解
  ![7种事务传播行为](images/7种事务传播行为.png)
  其中REQUIRED和REQUIRED_NEW这两个传播行为的设置参数较多，默认是REQUIRED
  `@Transactional(propagation=Propagation.REQUIRED)`

* 事务隔离级别
  1. 事务具有隔离性，多事务操作之间不会产生影响，不考虑隔离性会产生问题
  2. 读问题：脏读、不可重复读、幻读(虚读)
     **脏读**：一个未提交事务读取到另一个未提交事务的数据
     **不可重复读**：一个未提交事务读取到另一个提交事务修改数据（这是现象，不是问题）
     **幻读**：一个未提交事务读取到另一个事务提交增删数据（这也是现象，不是问题）
  3. 解决：通过设置隔离级别

     ||脏读|不可重复读|幻读|
     |:----:|:----|:----|:----|
     |**READ UNCOMMITTED<br>读未提交**|有|有|有|
     |**READ COMMITTED<br>读已提交**|无|有|有|
     |**REPEATABLE READ<br>可重复读**|无|无|有|
     |**SERIALIZABLE<br>串行化**|无|无|无|
     `@Transactional(propagation= Propagation.REQUIRES_NEW,isolation = Isolation.REPEATABLE_READ)`
* timeout:超时时间
  事务需要在一定时间内进行提交，不能一直占用连接资源而不释放，设置超时时间，如果不提交则进行回滚
  默认值是-1，设置时间以秒单位进行计算
* readOnly:是否只读
  读：查询操作；写：添加修改删除操作
  readOnly默认是false,表示增删改查操作都可以
  设置readOnly为true,就只能查询

* rollbackFor:回滚
  设置出现哪些异常进行事务回滚
* noRollbackFor:不回滚
  设置出现哪些异常不进行事务回滚

* 配置参数语法举例：

  `@Transactional(propagation= Propagation.REQUIRES_NEW,isolation = Isolation.REPEATABLE_READ,timeout = 2,readOnly = false,rollbackFor = Exception.class,noRollbackFor = RuntimeException.class)`

* 完整示例：

1. 创建账户Account类

   ```java
   package com.suftz.bean;

   import org.springframework.stereotype.Component;

   @Component
   public class Account {
       private Integer uid;
       private String uname;
       private double money;

       public Account() {
       }

       public Account(String uname, double money) {
           this.uname = uname;
           this.money = money;
       }

       public Integer getUid() {
           return uid;
       }

       public void setUid(Integer uid) {
           this.uid = uid;
       }

       public String getUname() {
           return uname;
       }

       public void setUname(String uname) {
           this.uname = uname;
       }

       public double getMoney() {
           return money;
       }

       public void setMoney(double money) {
           this.money = money;
       }

       @Override
       public String toString() {
           return "Account{" +
                   "uid=" + uid +
                   ", uname='" + uname + '\'' +
                   ", money=" + money +
                   '}';
       }
   }
   ```

2. 创建AccountDao.java

   ```java
   package com.suftz.dao;

   public interface AccountDao {
       void addMoney(Integer uid,double money);
       void reduceMoney(Integer uid,double money);
   }
   ```

3. 创建AccountDaoImpl.java

   ```java
   package com.suftz.dao;

   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.jdbc.core.JdbcTemplate;
   import org.springframework.stereotype.Repository;

   @Repository(value="accountDao")
   public class AccountDaoImpl implements AccountDao{
       @Autowired
       JdbcTemplate jdbcTemplate;

       @Override
       public void addMoney(Integer uid,double money) {
           String sql="update account set money=money+? where uid=?";
           jdbcTemplate.update(sql,money,uid);
       }

       @Override
       public void reduceMoney(Integer uid,double money) {
           String sql="update account set money=money-? where uid=?";
           jdbcTemplate.update(sql,money,uid);
       }
   }
   ```

4. 创建AccountService.java

   ```java
   package com.suftz.service;

   public interface AccountService {
       void transferAccount(Integer uid1,Integer uid2,double money);
   }
   ```

5. 创建AccountServiceImpl.java

   ```java
   package com.suftz.service;

   import com.suftz.dao.AccountDao;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   import org.springframework.transaction.annotation.Isolation;
   import org.springframework.transaction.annotation.Propagation;
   import org.springframework.transaction.annotation.Transactional;

   @Transactional(propagation= Propagation.REQUIRES_NEW,isolation = Isolation.REPEATABLE_READ,timeout = 2,
           readOnly = false)
   @Service(value = "accountService")
   public class AccountServiceImpl implements AccountService {

       @Autowired
       AccountDao accountDao;

       @Override
       public void transferAccount(Integer uid1, Integer uid2, double money) {
           accountDao.reduceMoney(uid1,money);
           //int i=10/0;//运行时异常
           accountDao.addMoney(uid2,money);
       }
   }
   ```

6. spring配置文件bean.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:p="http://www.springframework.org/schema/p"
          xmlns:util="http://www.springframework.org/schema/util"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xmlns:tx="http://www.springframework.org/schema/tx"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd
          http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd
          http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd
          http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">

       <!--
           如果想扫描多个包，可以使用逗号分隔开
           或者写包的上层目录，这样其子包都能被扫描
       -->
       <context:component-scan base-package="com.suftz"/>
       <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
       <context:property-placeholder location="classpath:day01/jdbc.properties" />
       <!--配置数据库连接池信息，获取数据源对象-->
       <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
           <property name="driverClassName" value="${prop.driverClass}"></property>
           <property name="url" value="${prop.url}"></property>
           <property name="username" value="${prop.username}"></property>
           <property name="password" value="${prop.password}"></property>
           <property name="minEvictableIdleTimeMillis" value="${prop.minEvictableIdleTimeMillis}"></property>
           <property name="validationQuery" value="${prop.validationQuery}"></property>
       </bean>
       <!--将jdbcTemplate放入bean容器中管理，并将druid数据库连接池对象注入到spring的JdbcTemplate对象中-->
       <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
           <property name="dataSource" ref="dataSource"></property>
       </bean>

       <!--事务管理器-->
       <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
           <property name="dataSource" ref="dataSource"></property>
       </bean>
       <!--开启事务注解-->
       <tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
   </beans>
   ```

7. 编写测试用例

   ```java
   import com.suftz.service.AccountService;
   import org.junit.Test;
   import org.springframework.context.ApplicationContext;
   import org.springframework.context.support.ClassPathXmlApplicationContext;

   import java.util.ArrayList;
   import java.util.List;

   public class SpringTest2 {

       @Test
       public void testAccountTransfer(){
           ApplicationContext context=new ClassPathXmlApplicationContext("day02/beans.xml");
           AccountService accountService=context.getBean("accountService", AccountService.class);
           //可以通过在AccountServiceImpl类的transferAccount方法中产生运行时异常测试
           accountService.transferAccount(1001,1002,100);
           //数据库表原数据：
           //1001,刘备,12000
           //1002,诸葛亮,9999
       }
   }
   ```

#### Spring事务管理(XML方式)

* 在spring配置文件中进行配置
  1. 配置事务管理器
  2. 配置通知
  3. 配置切入点和切面

* 步骤：
  1.修改spring配置文件即可（要删除上面设置的开启事务注解）

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:p="http://www.springframework.org/schema/p"
         xmlns:util="http://www.springframework.org/schema/util"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xmlns:tx="http://www.springframework.org/schema/tx"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd
         http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd
         http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd
         http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">

      <!--
          如果想扫描多个包，可以使用逗号分隔开
          或者写包的上层目录，这样其子包都能被扫描
      -->
      <context:component-scan base-package="com.suftz"/>
      <context:property-placeholder location="classpath:day01/jdbc.properties" />
      <!--配置数据库连接池信息，获取数据源对象-->
      <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
          <property name="driverClassName" value="${prop.driverClass}"></property>
          <property name="url" value="${prop.url}"></property>
          <property name="username" value="${prop.username}"></property>
          <property name="password" value="${prop.password}"></property>
          <property name="minEvictableIdleTimeMillis" value="${prop.minEvictableIdleTimeMillis}"></property>
          <property name="validationQuery" value="${prop.validationQuery}"></property>
      </bean>
      <!--将jdbcTemplate放入bean容器中管理，并将druid数据库连接池对象注入到spring的JdbcTemplate对象中-->
      <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
          <property name="dataSource" ref="dataSource"></property>
      </bean>

      <!--事务管理器-->
      <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
          <property name="dataSource" ref="dataSource"></property>
      </bean>

      <!--配置通知-->
      <tx:advice id="txadvice">
          <!--配置事务参数-->
          <tx:attributes>
              <tx:method name="transferAccount" propagation="REQUIRED" isolation="REPEATABLE_READ" read-only="false" timeout="2"  />
              <!-- <tx:method name="add*" />-->
          </tx:attributes>
      </tx:advice>

      <!--配置切入点和切面-->
      <aop:config>
          <!--配置切入点-->
          <aop:pointcut id="pointcut" expression="execution(* com.suftz.service.AccountService.*(..))" />
          <!--配置切面-->
          <aop:advisor advice-ref="txadvice" pointcut-ref="pointcut" />
      </aop:config>

  </beans>
  ```

#### Spring事务管理(完全注解方式)

* 完全注解开发需要在上面基于注解开发的基础上进行调整
* 在配置类中：
  1. 创建数据库连接池对象
  2. 创建事务管理器对象
  3. 创建JdbcTemplate对象
  4. 开启事务
  5. 当然还要开启组件扫描
  6. 当然还要设置此类为配置类

* 完整示例(其他的类和注解配置不用修改，只需要创建配置类，然后编写测试方法即可)：
  1. 配置类如下：

     ```java
     package com.suftz.config;

     import com.alibaba.druid.pool.DruidDataSource;
     import org.springframework.context.annotation.Bean;
     import org.springframework.context.annotation.ComponentScan;
     import org.springframework.context.annotation.Configuration;
     import org.springframework.context.annotation.EnableAspectJAutoProxy;
     import org.springframework.jdbc.core.JdbcTemplate;
     import org.springframework.jdbc.datasource.DataSourceTransactionManager;
     import org.springframework.transaction.annotation.EnableTransactionManagement;

     import javax.sql.DataSource;

     @Configuration//作为配置类，替代xml配置文件
     @ComponentScan(basePackages = {"com.suftz"})//组件扫描
     //@EnableAspectJAutoProxy(proxyTargetClass = true)//开启aop
     @EnableTransactionManagement//开启事务
     public class SpringConfig {

        //创建数据库连接池:也会被放入ioc容器中管理
        @Bean
        public DruidDataSource getDruidDataSource(){
            DruidDataSource dataSource=new DruidDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/spring?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai");
            dataSource.setUsername("root");
            dataSource.setPassword("booksys123");
            dataSource.setMinEvictableIdleTimeMillis(300000);
            dataSource.setValidationQuery("SELECT 1");
            return dataSource;
        }

        //创建JdbcTemplate对象:也会被放入ioc容器中管理
        @Bean
        public JdbcTemplate getJdbcTemplate(DataSource dataSource){//参数说明：因为在ioc容器中已经创建了数据库连接池的对象，这里从ioc容器中取得
            JdbcTemplate jdbcTemplate=new JdbcTemplate();
            jdbcTemplate.setDataSource(dataSource);
            return  jdbcTemplate;
        }

        //创建事务管理器对象
        @Bean
        public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource){
            DataSourceTransactionManager transactionManager=new DataSourceTransactionManager();
            transactionManager.setDataSource(dataSource);
            return transactionManager;
        }
     }
     ```

  2. 测试方法如下：

     ```java
     import com.suftz.config.SpringConfig;
     import com.suftz.service.AccountService;
     import org.junit.Test;
     import org.springframework.context.ApplicationContext;
     import org.springframework.context.annotation.AnnotationConfigApplicationContext;
     import org.springframework.context.support.ClassPathXmlApplicationContext;

     import java.util.ArrayList;
     import java.util.List;

     public class SpringTest2 {
         @Test
         public void testAccountTransfer(){
             ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);
             AccountService accountService=context.getBean("accountService", AccountService.class);
             accountService.transferAccount(1001,1002,100);
         }
     }
     ```

## Spring5新特性

* Spring5框架代码基于Java8,运行时兼容JDK9，许多不建议使用的类和方法在代码库中删除
* Spring5框架自带了通用的日志封装
  * Spring5已经移除了Log4jConfigListener,官方建议使用Log4j2

### 整合日志框架

* 整合日志框架log4j2
* 步骤：
  1. 引入jar包，可以通过官网上下载或者使用maven来引用：

     ```xml
     <!--日志框架-->
     <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>2.11.2</version>
     </dependency>
     <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-api</artifactId>
        <version>2.11.2</version>
     </dependency>
     <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-slf4j-impl</artifactId>
        <version>2.11.2</version>
     </dependency>
     <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.30</version>
     </dependency>
     ```

  2. 创建log4j2.xml配置文件(名字不能修改,在项目根目录，如果是maven则在resources目录下)

     ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <!--日志级别以及优先级排序: OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL -->
     <!--Configuration后面的status用于设置log4j2自身内部的信息输出，可以不设置，当设置成trace时，可以看到log4j2内部各种详细输出-->
     <configuration status="INFO">
         <!--先定义所有的appender-->
         <appenders>
             <!--输出日志信息到控制台-->
             <console name="Console" target="SYSTEM_OUT">
                 <!--控制日志输出的格式-->
                 <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
             </console>
         </appenders>
         <!--然后定义logger，只有定义了logger并引入的appender，appender才会生效-->
         <!--root：用于指定项目的根日志，如果没有单独指定Logger，则会使用root作为默认的日志输出-->
         <loggers>
             <root level="info">
                 <appender-ref ref="Console"/>
             </root>
         </loggers>
     </configuration>
     ```

  3. 还可以在类中手动输出日志

     ```java
     import org.slf4j.Logger;
     import org.slf4j.LoggerFactory;

     public class LogTest {
         private static final Logger log= LoggerFactory.getLogger(LogTest.class);

         public static void main(String[] args) {
             log.info("hello log4j2,I am {}","Tom");
             log.info("this is a test log info");
             log.warn("this is a test warn log");
             log.error("this is a test error log");
         }
     }
     ```

* 还可以通过修改日志配置文件log4j2.xml指定日志信息输出到本地文件中，以便查询//todo

### Nullable注解和函数式注册对象

#### @Nullable注解

* @Nullable注解可以使用在方法上面，属性上面，参数上面，表示方法返回可以为空，属性值可以为空，参数值可以为空
* 使用在方法上面，方法返回值可以为空

  ```java
  @Nullable
  String getName();
  ```

* 使用在方法参数里面，方法参数可以为空

  `public <T> void registerBean(Class<T> beanClass, @Nullable String name, @Nullable Supplier<T> supplier,BeanDefinitionCustomizer... customizers)`

* 使用在属性上面，类的属性可以为空

  ```java
  import java.util.Date;
  public class HelloWorld{
      @Nullable
      public Date date;

  }
  ```

#### 函数式风格

```java
import com.suftz.bean.User;
import org.junit.Test;
import org.springframework.context.support.GenericApplicationContext;

public class SpringTest2 {
    @Test
    public void test(){
        //创建GenericApplicationContext对象
        GenericApplicationContext context=new GenericApplicationContext();
        //调用context的方法对象注册
        context.refresh();
        context.registerBean("user",User.class,()->new User());
        //获取在spring注册的对象
        User user=context.getBean("user",User.class);
        System.out.println(user);
    }
}
```

### 整合JUnit5单元测试框架

#### 整合JUnit4

* 步骤

1. 引入Spring相关针对测试的依赖spring-test包，或者使用maven导入依赖：

   ```xml
   <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>5.2.6.RELEASE</version>
   </dependency>
   ```

2. 编写测试类

   ```java
   import com.suftz.service.AccountService;
   import org.junit.Test;
   import org.junit.runner.RunWith;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.test.context.ContextConfiguration;
   import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

   @RunWith(SpringJUnit4ClassRunner.class)  //单元测试框架
   @ContextConfiguration(locations = {"classpath:././day02/beans3.xml"})  //加载配置文件
   public class JTest {
       @Autowired
       private AccountService accountService;

       @Test
       public void testTransferAccount(){
           accountService.transferAccount(1001,1002,100);
       }
   }
   ```

#### 整合JUnit5

* JUnit5与JUnit4相比，有新的特性
* Spring5整合JUnit5步骤

1. 引入JUnit5的jar包

   ```xml
   <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter</artifactId>
      <version>RELEASE</version>
      <scope>test</scope>
   </dependency>
   ```

2. 编写测试类

   ```java
   import com.suftz.service.AccountService;
   import org.junit.jupiter.api.Test;
   import org.junit.jupiter.api.extension.ExtendWith;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.test.context.ContextConfiguration;
   import org.springframework.test.context.junit.jupiter.SpringExtension;
   import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

   //@ExtendWith(SpringExtension.class)  //单元测试框架
   //@ContextConfiguration(locations = {"classpath:././day02/beans3.xml"})  //加载配置文件

   //上面两个注解可以使用下面一个注解代替
   @SpringJUnitConfig(locations = {"classpath:././day02/beans3.xml"})
   public class JTest5 {
       @Autowired
       private AccountService accountService;

       @Test
       public void testTransferAccount(){
           accountService.transferAccount(1001,1002,100);
       }
   }
   ```

### Webflux

基本具备知识：springMVC,SpringBoot,Maven,Java8新特性：lamada表达式和streamAPI
//学完这些后再完善//todo
#### webflux响应式编程


##### webflux响应式编程(Java实现)

##### webflux响应式编程(Reactor)

#### Webflux执行流程和核心API

#### Webflux注解编程模型

#### Webflux函数式编程模型

##### Webflux函数式编程模型(Handler)

##### Webflux函数式编程模型(Router和服务器)

##### Webflux函数式编程模型(WebClient)
