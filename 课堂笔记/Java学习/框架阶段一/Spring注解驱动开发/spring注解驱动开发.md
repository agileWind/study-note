# spring注解驱动开发

[TOC]

## 容器

### 组件添加

给容器中注册组件：

1. 包扫描+组件标注注解（@Controller,@Service,@Repository,@Component）
2. @Bean[导入的第三方包里面的组件]
3. @Import[快速给容器中导入一个组件]
   1. @Import(要导入到容器中的组件)；容器中就会自动注册这个组件，id默认是全类名
   2. ImportSelector：返回需要导入的组件的全类名数组
   3. ImportBeanDefinitionRegistrar：手动注册bean到容器中
4. 使用Spring提供的FactoryBean，即工厂bean
   1. 默认获取到的是工厂bean调用getObject创建的对象
   2. 要获取工厂bean本身，在使用getBean时需要在传参时给id前面加一个&，如`getBean("&personFactoryBean")`

#### @Bean给容器添加组件

```xml
<bean id="person" class="com.suftz.demo.bean">
    <property name="age" value="18"></property>
    <property name="name" value="Tom"></property>
</bean>
```

现在不使用spring配置文件，而使用注解类可以如下编写：

```java
package com.suftz.demo.config;

import com.suftz.demo.bean.Person;
import org.springframework.context.annotation.*;

@Configuration//作为配置类，替代xml配置文件

public class MainConfig {

    @Bean(value = "person")//没有设置id，则会以方法名作为id
    public Person getPerson(){
        return new Person("Tom",123);
    }
}
```

测试方法如下：

```java
import com.suftz.demo.bean.Person;
import com.suftz.demo.config.MainConfig1;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class SpringAnnotationTest {

    @Test
    public void test1(){
        ApplicationContext context=new AnnotationConfigApplicationContext(MainConfig1.class);
        System.out.println(Arrays.toString(context.getBeanNamesForType(Person.class)));//[person]
        System.out.println(context.getBean("person"));//Person{name='Tom', id=123}
    }

}
```

#### @ComponentScan自动组件扫描

以前扫描组件的注解@Controller,@Service,@Repository,@Component需要在spring配置文件中开启组件扫描才能将这些注解上的类进行实例化并放入ioc容器中管理：

```xml
<context:component-scan base-package="com.suftz.demo"/>
```

通过在注解类上添加如下注解也可达到一样的效果

```java
package com.suftz.demo.config;

import com.suftz.demo.bean.Person;
import org.springframework.context.annotation.*;

@Configuration//作为配置类，替代xml配置文件
@ComponentScan(basePackages = {"com.suftz.demo"}
public class MainConfig {

    @Bean(value = "person")//没有设置id，则会以方法名作为id
    public Person getPerson(){
        return new Person("Tom",123);
    }
}
```

1.**包扫描规则**

* @ComponentScan value:指定要扫描的包
* excludeFilters=Filters[]:指定扫描的时候按照什么规则排除那些组件
* includeFilters=Filters[]:执行扫描的时候只需要包含哪些组件
  * FilterType.ANNOTATION:按照注解
  * FilterType.ASSIGNABLE_TYPE:按照给定的类型，不管是本类还是子类，都会被扫描
  * FilterType.ASPECTJ:使用ASPECTJ表达式
  * FilterType.REGEX:使用正则指定
  * FilterType.CUSTOM：使用自定义规则

示例：

```java
package com.suftz.demo.config;

import com.suftz.demo.bean.Person;
import org.springframework.context.annotation.*;

@Configuration//作为配置类，替代xml配置文件
@ComponentScan(basePackages = {"com.suftz.demo"}
,excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class, Service.class}),//service注解和controller注解不会被扫描
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {Person.class,}),
        @ComponentScan.Filter(type=FilterType.CUSTOM,classes={MyFilterType.class})
}, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes={Controller.class})//只扫描controller注解
},useDefaultFilters = true//设置成false自定义规则才会生效。在springmvc时，如果只扫描Controller，这个需要设置为false
)//组件扫描

/**
 * @ComponentScan value:指定要扫描的包
 * excludeFilters=Filters[]:指定扫描的时候按照什么规则排除那些组件
 * includeFilters=Filters[]:执行扫描的时候只需要包含哪些组件
 * FilterType.ANNOTATION:按照注解
 * FilterType.ASSIGNABLE_TYPE:按照给定的类型
 * FilterType.ASPECTJ:使用ASPECTJ表达式
 * FilterType.REGEX:使用正则指定
 * FilterType.CUSTOM：使用自定义规则
 */
public class MainConfig {

    @Bean(value = "person")//没有设置id，则会以方法名作为id
    public Person getPerson(){
        return new Person("Tom",123);
    }
}
```

2.**自定义包扫描过滤规则**

当@ComponentScan.Filter(type=FilterType.CUSTOM,classes={MyFilterType.class})时，需要创建一个自定义规则的类，该类实现了TypeFilter接口

```java
package com.suftz.demo.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class MyFilterType implements TypeFilter {

    /**
     * metadataReader:读取到的当前正在扫描的类的信息
     * metadataReaderFactory:可以获取到其他任何类信息的
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //获取当前类注解的信息
        AnnotationMetadata annotationMetadata=metadataReader.getAnnotationMetadata();
        //获取当前正在扫描的类的类信息
        ClassMetadata classMetadata=metadataReader.getClassMetadata();
        //获取当前类资源(类的路径)
        Resource Resource=metadataReader.getResource();

        /**
         * 根据这些信息可以自己去做选择，使用自己的规则，去决定哪些类需要被实例化到ioc容器中：
         * 返回true,则表示进入ioc容器管理
         * 返回false,则表示不实例化，也不放入ioc容器
         * */
        return true;
    }
}
```

#### @Scope 设置组件作用域

* prototype:多实例的。每次使用的时候才会去创建对象
* singleton:单实例的，默认值；ioc容器启动的时候就会执行该方法,创建对象。当然如果是使用了@Lazy注解进行懒加载，则会在第一次使用的时候创建对象
* request:同一个请求创建一个实例，在web环境也并不常用
* session:同一个session创建一个实例，在web环境也并不常用

```java
package com.suftz.demo.config;

import com.suftz.demo.bean.Person;
import org.springframework.context.annotation.*;


@Configuration//作为配置类，替代xml配置文件
@ComponentScan(basePackages = {"com.suftz.demo"})
public class MainConfig {

    /**
     * prototype:多实例的。每次使用的时候才会去创建对象
     * singleton:单实例的，默认值；ioc容器启动的时候就会执行该方法,创建对象。
       当然如果是使用了@Lazy注解进行懒加载，则会在第一次使用的时候创建对象
     * request:同一个请求创建一个实例，在web环境也并不常用
     * session:同一个session创建一个实例，在web环境也并不常用
     */
    @Scope(value = "prototype")
    @Lazy
    @Bean(value = "person")//没有设置id，则会以方法名作为id
    public Person getPerson(){
        return new Person("Tom",123);
    }

}
```

测试方法如下：

```java
import com.suftz.demo.bean.Person;
import com.suftz.demo.config.MainConfig1;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class SpringAnnotationTest {
    ApplicationContext context=null;


    @Before
    public void init(){
        context=new AnnotationConfigApplicationContext(MainConfig.class);
    }

    @Test
    public void test1(){
        System.out.println(Arrays.toString(context.getBeanNamesForType(Person.class)));//[person]
        System.out.println(context.getBean("person"));//Person{name='Tom', id=123}
    }

    @Test
    public void test2(){
        Person p1=context.getBean("person",Person.class);
        Person p2=context.getBean("person",Person.class);
        System.out.println(p1==p2);
        //@Scope(value = "prototype")则返回false;@Scope(value = "prototype")则返回true
    }
}
```

#### @Lazy

在单实例bean的设置情况下，默认会在ioc容器初始化创建的时候，进行bean的创建，而使用@Lazy注解可以延迟加载bean，等需要在第一次使用的时候才创建该bean

#### @Conditional条件注册

配置类如下：

```java
package com.suftz.demo.config;

import com.suftz.demo.bean.Person;
import com.suftz.demo.condition.LinuxCondition;
import com.suftz.demo.condition.WindowsCondition;
import org.springframework.context.annotation.*;


@Configuration//作为配置类，替代xml配置文件
@ComponentScan(basePackages = {"com.suftz.demo"})
public class MainConfig {

    /**
     * prototype:多实例的。每次使用的时候才会去创建对象
     * singleton:单实例的，默认值；ioc容器启动的时候就会执行该方法,创建对象。当然如果是使用了@Lazy注解进行懒加载，则会在第一次使用的时候创建对象
     * request:同一个请求创建一个实例，在web环境也并不常用
     * session:同一个session创建一个实例，在web环境也并不常用
     */
    //@Scope(value = "prototype")
    @Bean(value = "person")//没有设置id，则会以方法名作为id
    public Person getPerson(){
        return new Person("Tom",123);
    }

    @Bean(value="Bill")
    @Conditional({WindowsCondition.class})//只有满足该条件才能注册该bean实例；这个条件还能放在当前配置类的类上，表明只有满足这个条件，这个配置下的所有bean才能被创建
    public Person getWindowsInfo(){
        return new Person("Bill",62);
    }

    @Bean("Linus")
    @Conditional({LinuxCondition.class})
    public Person getLinuxInfo(){
        return new Person("Linus",50);
    }

}
```

此时需要创建两个条件判断的实现类WindowsCondition和LinuxCondition

```java
package com.suftz.demo.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinuxCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment=context.getEnvironment();
        String osName=environment.getProperty("os.name");
        if(osName.contains("Linux")){
            return true;
        }else{
            return false;
        }
    }
}
```

```java
package com.suftz.demo.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WindowsCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        //能获取到ioc使用的beanFactory
        ConfigurableListableBeanFactory beanFactory=context.getBeanFactory();

        //获取类加载器
        ClassLoader classLoader=context.getClassLoader();

        //获取bean定义的注册类,可以移除一个bean的定义，获取添加，判断是否存在一个bean的定义
        BeanDefinitionRegistry beanDefinitionRegistry=context.getRegistry();

        //获取当前环境信息
        Environment environment=context.getEnvironment();
        String osName=environment.getProperty("os.name");
        if(osName.contains("Windows")){
            return true;
        }else{
            return false;
        }
    }
}
```

在windows下，进行测试结果如下：

```java
import com.suftz.demo.bean.Person;
import com.suftz.demo.config.MainConfig1;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class SpringAnnotationTest {
    ApplicationContext context=null;

    @Before
    public void init(){
        context=new AnnotationConfigApplicationContext(MainConfig1.class);
    }

    @Test
    public void testConditional(){
        System.out.println(Arrays.toString(context.getBeanNamesForType(Person.class)));//[person, Bill]
    }
}
```

#### @Import快速导入一个组件

```java
package com.suftz.demo.config;

import com.suftz.demo.bean.Person;
import com.suftz.demo.condition.LinuxCondition;
import com.suftz.demo.condition.MyImportBeanDefinitionRegistrar;
import com.suftz.demo.condition.MyImportSelector;
import com.suftz.demo.condition.WindowsCondition;
import org.springframework.context.annotation.*;


@Configuration//作为配置类，替代xml配置文件
@ComponentScan(basePackages = {"com.suftz.demo"})
@Import({com.suftz.demo.bean.Color.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})//快速导入组件
public class MainConfig1 {

    /**
     * prototype:多实例的。每次使用的时候才会去创建对象
     * singleton:单实例的，默认值；ioc容器启动的时候就会执行该方法,创建对象。当然如果是使用了@Lazy注解进行懒加载，则会在第一次使用的时候创建对象
     * request:同一个请求创建一个实例，在web环境也并不常用
     * session:同一个session创建一个实例，在web环境也并不常用
     */
    //@Scope(value = "prototype")
    @Bean(value = "person")//没有设置id，则会以方法名作为id
    public Person getPerson(){
        return new Person("Tom",123);
    }

    @Bean(value="Bill")
    @Conditional({WindowsCondition.class})//只有满足该条件才能注册该bean实例；这个条件还能放在当前配置类的类上，表明只有满足这个条件，这个配置下的所有bean才能被创建
    public Person getWindowsInfo(){
        return new Person("Bill",62);
    }

    @Bean("Linus")
    @Conditional({LinuxCondition.class})
    public Person getLinuxInfo(){
        return new Person("Linus",50);
    }

}
```

`@Import({com.suftz.demo.bean.Color.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})`

这里可以有三种方式来导入组件：

1. 直接写需要导入的类的全类名
2. 实现一个ImportSerlector接口，可以在里面对类进行过滤或者有选择性的导入组件

   ```java
   package com.suftz.demo.condition;

   import org.springframework.context.annotation.ImportSelector;
   import org.springframework.core.type.AnnotationMetadata;

   public class MyImportSelector implements ImportSelector {
       @Override
       public String[] selectImports(AnnotationMetadata importingClassMetadata) {
           return new String[]{"com.suftz.demo.bean.Color","com.suftz.demo.bean.Person"};
           //方法不能返回null，会报异常的，至少要返回一个空数组
       }
   }
   ```

3. 实现一个ImportBeanDefinitionRegistrar接口，可以在里面根据已有的bean定义，来决定导入bean定义

   ```java
   package com.suftz.demo.condition;

   import com.suftz.demo.bean.Color;
   import org.springframework.beans.factory.support.BeanDefinitionRegistry;
   import org.springframework.beans.factory.support.RootBeanDefinition;
   import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
   import org.springframework.core.type.AnnotationMetadata;

   public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

       /**
        * AnnotationMetadata:当前bean的注解信息
        * BeanDefinitionRegistry：bean的注册类，可以调用该类的方法手动注册一个bean定义
        *
        * */
       @Override
       public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
           RootBeanDefinition rootBeanDefinition =new RootBeanDefinition(Color.class);
           registry.registerBeanDefinition("newcolor",rootBeanDefinition);
       }
   }
   ```

#### @FactoryBean

也可以通过工厂bean的方式来让工厂产生一个bean，该方式在整合其他框架时经常使用

配置文件如下：

```java
package com.suftz.demo.config;

import com.suftz.demo.bean.Person;
import com.suftz.demo.bean.PersonFactoryBean;
import com.suftz.demo.condition.LinuxCondition;
import com.suftz.demo.condition.MyImportBeanDefinitionRegistrar;
import com.suftz.demo.condition.MyImportSelector;
import com.suftz.demo.condition.WindowsCondition;
import org.springframework.context.annotation.*;


@Configuration//作为配置类，替代xml配置文件
@ComponentScan(basePackages = {"com.suftz.demo"})
@Import({com.suftz.demo.bean.Color.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})//快速导入组件
public class MainConfig {

    /**
     * prototype:多实例的。每次使用的时候才会去创建对象
     * singleton:单实例的，默认值；ioc容器启动的时候就会执行该方法,创建对象。当然如果是使用了@Lazy注解进行懒加载，则会在第一次使用的时候创建对象
     * request:同一个请求创建一个实例，在web环境也并不常用
     * session:同一个session创建一个实例，在web环境也并不常用
     */
    //@Scope(value = "prototype")
    @Bean(value = "person")//没有设置id，则会以方法名作为id
    public Person getPerson(){
        return new Person("Tom",123);
    }

    @Bean(value="Bill")
    @Conditional({WindowsCondition.class})//只有满足该条件才能注册该bean实例；这个条件还能放在当前配置类的类上，表明只有满足这个条件，这个配置下的所有bean才能被创建
    public Person getWindowsInfo(){
        return new Person("Bill",62);
    }

    @Bean("Linus")
    @Conditional({LinuxCondition.class})
    public Person getLinuxInfo(){
        return new Person("Linus",50);
    }

    @Bean("personFactoryBean")
    public PersonFactoryBean getPersonFactoryBean(){
        return new PersonFactoryBean();
    }
}
```

PersonFactoryBean类如下：

```java
package com.suftz.demo.bean;

import org.springframework.beans.factory.FactoryBean;

public class PersonFactoryBean  implements FactoryBean<Person>{
    /**
     * 返回使用工厂创建的bean
     * */
    @Override
    public Person getObject() throws Exception {
        return new Person("Jack",23);
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

    /**
     * 创建的该bean是否是单例
     * */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
```

编写的测试方法如下：

```java
import com.suftz.demo.bean.Person;
import com.suftz.demo.config.MainConfig1;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class SpringAnnotationTest {
    ApplicationContext context=null;


    @Before
    public void init(){
        context=new AnnotationConfigApplicationContext(MainConfig1.class);
    }

    @Test
    public void testFactoryBean(){
        Object obj1=context.getBean("personFactoryBean");//person对象
        Object obj2=context.getBean("personFactoryBean");//person对象
        System.out.println(obj1==obj2);//上面定义的方法isSingleton表明是否以单例形式创建bean
    }
}
```

如果需要获取工厂本身的bean，可以编写如下：

```java
@Test
public void testFactoryBean(){
    Object obj1=context.getBean("personFactoryBean");
    Object obj2=context.getBean("personFactoryBean");
    System.out.println(obj1==obj2);

    //获取工厂本身的bean，即PersonFactoryBean实例，需要在id前面加上一个&符号即可
    Object obj3=context.getBean("&personFactoryBean");
    System.out.println(obj3);//com.suftz.demo.bean.PersonFactoryBean@b7a896
}
```

### 生命周期

1. 指定初始化和销毁方法
   通过在@Bean设置initMethod属性和destroyMethod属性来分别设置初始化方法和销毁方法
2. 通过在bean类上实现InitializingBean，DisposableBean这两个接口，来定义初始化逻辑和销毁逻辑
3. 可以使用JSR250的两个注解，标注在方法上，标识初始化方法和销毁方法
   * @PostConstruct：在bean创建完成并且属性赋值完成，来执行初始化方法
   * @PreDestroy：在容器销毁bean之前通知进行清理工作
4. BeanPostProcessor接口，bean的后置处理器
   在bean初始化前后进行一些处理工作：
    postProcessBeforeInitialization：在初始化之前工作
    postProcessAfterInitialization：在初始化之后工作

#### 指明初始化方法和销毁方法

通过在spring配置文件中的bean标签上，有属性init-method,destroy-method，分别指定了创建bean时调用的方法，bean销毁的时候调用的方法

```xml
<bean id="person" class="com.suftz.demo.bean" init-method="init" destroy-method="destroy">
    <property name="age" value="18"></property>
    <property name="name" value="Tom"></property>
</bean>
```

Person类如下：

```java
package com.suftz.demo.bean;

public class Person {

    private String name;
    private Integer id;

    public Person() {
    }

    public Person(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public void init(){
        System.out.println("init method");
    }

    public void destroy(){
        System.out.println("destroy method");
    }
}
```

在@Bean上也能设置：

```java
//@Scope(value = "prototype")
@Bean(value = "person",initMethod = "init",destroyMethod = "destroy")//没有设置id，则会以方法名作为id
public Person getPerson(){
  return new Person("Tom",123);
}
```

测试方法如下：

```java
@Test
public void testLifeCycle(){
    Person p1=context.getBean("person",Person.class);
    System.out.println(p1);
    ((AnnotationConfigApplicationContext) context).close();
}
```

* 初始化：对象创建完成，并赋值，此时调用初始化方法
* 销毁：
  1. 单实例：容器在关闭的时候调用bean的销毁方法；
  2. 多实例：容器不会管理这个bean，容器不会调用销毁方法，只会帮忙创建

#### InitializingBean和DisposableBean

通过在定义bean对应的类时，实现InitializingBean和DisposableBean这两个接口，并实现对应的初始化方法和销毁方法

```java
package com.suftz.demo.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class User implements InitializingBean, DisposableBean {

    private String name;
    private Integer age;

    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    /**
     * 销毁时会调用该方法
     *
     * */
    @Override
    public void destroy() throws Exception {
        System.out.println(" user destroy");
    }

    /**
     * 初始化之后会调用此方法
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("user afterPropertiesSet");
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

测试方法如下：

```java
@Test
public void testInitializingBeanAndDisposableBean(){
    User user=context.getBean("user",User.class);
    System.out.println(user);
    ((AnnotationConfigApplicationContext) context).close();
}
```

#### @PostConstruct和@PreDestroy

* @PostConstruct：在bean创建完成并且属性赋值完成，来执行初始化方法
* @PreDestroy：在容器销毁bean之前通知进行清理工作

```java
package com.suftz.demo.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Customer {

    private String name;
    private Integer age;

    public Customer() {
    }

    public Customer(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    /**
     * 对象创建并赋值之后调用
     */
    @PostConstruct
    public void init(){
        System.out.println("customer init");
    }

    /**
     *  容器移除对象之前
     */
    @PreDestroy
    public void destroy(){
        System.out.println("customer destroy");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

测试方法如下：

```java
@Test
public void testPostConstruct_PreDestroy(){
    Customer customer=context.getBean("customer", Customer.class);
    System.out.println(customer);
    ((AnnotationConfigApplicationContext) context).close();

    /**
    user afterPropertiesSet
    customer init
    Customer{name='null', age=null}
    customer destroy
    user destroy
    */
}
```

#### BeanPostProcessor后置处理器

postProcessBeforeInitialization：在初始化之前工作
postProcessAfterInitialization：在初始化之后工作

1. 自定义一个BeanPostProcessor后置处理器，需要实现其接口

```java
package com.suftz.demo.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        System.out.println("进入到postProcessBeforeInitialization方法");
        System.out.println(bean);
        System.out.println(beanName);
        System.out.println("离开postProcessBeforeInitialization方法");

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("进入到postProcessAfterInitialization");
        System.out.println(bean);
        System.out.println(beanName);
        System.out.println("离开postProcessAfterInitialization");
        return bean;
    }
}
```

通过这两个方法，可以在bean创建前后，对bean进行处理，包装，或者赋值等等
需要注意的是，这个自定义的后置处理器需要加上`@Component`注解才能使用
另外，这个后置处理器会作用在任何一个bean的初始化前后，包括spring的内置组件的初始化

1.**BeanPostProcessor后置处理器**

![BeanPostProcessor原理](images/BeanPostProcessor原理.png)
![BeanPostProcessor原理2](images/BeanPostProcessor原理2.png)
![BeanPostProcessor原理3](images/BeanPostProcessor原理3.png)
从上可以看出，调用后置处理器是在bean已被创建，且已赋值之后发生的

* 这个BeanPostProcessor后置处理器在Spring底层其他地方也有许多使用的地方：
  bean赋值，注入其他组件，@Autowired,生命周期注解功能，@Async......等实现原理都是通过实现了BeanPostProcessor后置处理器接口来完成

### 组件注入

### 组件赋值

#### @Value

```java
package com.suftz.demo.bean;

import org.springframework.beans.factory.annotation.Value;

public class Person {

    /**
     * 使用@Value赋值：
     * 1. 基本数值
     * 2. 可以写SpEL：#{}
     * 3. 可以写${}：取出配置文件中的值（在运行环境变量里面的值）
     */
    @Value("Tom")
    private String name;

    @Value("#{20-2}")
    private Integer id;


    public Person() {
    }

    public Person(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public void init(){
        System.out.println("init method");
    }

    public void destroy(){
        System.out.println("destroy method");
    }
}
```

#### @PropertySource加载外部配置文件

需要在配置类中使用注解，并在注解的属性中指定配置文件的位置

```java
package com.suftz.demo.config;

import com.suftz.demo.bean.Person;
import org.springframework.context.annotation.*;

@PropertySource(value={"classpath:/person.properties","classpath:/user.properties"})
@Configuration
@ComponentScan(basePackages = {"com.suftz.demo"})
public class MainConfig2 {
    @Scope(value = "prototype")
    @Bean(value = "person",initMethod = "init",destroyMethod = "destroy")//没有设置id，则会以方法名作为id
    public Person getPerson(){
        return new Person("Tom",123);
    }
}
```

#### @Autowired,Qualifier,@Primary

@Autowired：自动注入

1. 默认优先按照类型去容器中找对应的组件：applicationContext.getBean(BookDao.class);
2. 如果找到多个相同类型的组件，再将属性的名称作为组件的id去容器中查找,如果被注入的地方属性定义是"BookDao bookDao"：applicationContext.getBean("bookDao")。
3. @Qualifier("bookDao")：使用@Qualifier指定需要装配的组件的id，而不是使用属性名。默认不指定的时候装配是使用属性名作为id
4. 自动装配默认一定要将属性赋值好，找不到则会报错；可以使用`@Autowired(required=false)`
5. @Primary：让Spring进行自动装配的时候，默认使用首选的bean;也可以继续使用@Qualifier指定需要装配的bean的名字

#### @Resource,@Inject

Spring还支持使用@Resource（JSR250）和@Inject（JSR330）[java规范的注解]
@Resource:可以和@Autowired一样实现自动装配功能，默认是按照组件名称进行装配的；也可以指定id的名字就可以按照id指定
@Inject：需要导入javax.inject的包，和Autowired的功能一样。没有required=false的功能

> @Autowired是Spring定义的；@Resource,@Inject都是java规范
> 对这些注入的注解的支持，是因为Spring的AutowiredAnnotationBeanPostProcessor这个类的处理

#### @Autowired自动装配的位置

@Autowired可以使用的位置：构造器，参数，方法，属性

```java
@Component
public class Boss{

    private Car car;

    @Autowired
    public Boss(Car car){
        this.car=car;
    }

    /**
     * 标注在方法，Spring容器创建当前对象，就会调用方法，完成赋值
     * 方法使用的参数，自定义类型的值从ioc容器中获取
     */
    @Autowired
    public void setCar(Car car){
        this.car=car;
    }

    //或者使用在参数上
    public Boss(@Autowired Car car){
        this.car=car;
    }

    public void setCar(@Autowired Car car){
        this.car=car;
    }
}
```

#### Aware注入Spring底层组件&原理

* 自定义的组件想要使用Spring容器底层的一些组件(ApplicationContext，BeanFactory，XXX)
* 都是使用了后置处理器的机制来完成的
* 自定义组件实现xxxAware，在创建对象的时候，会调用接口规定的方法注入相关组件，把Spring底层一些组件注入到自定义的Bean中
* xxxAware的功能使用由xxxProcessor来实现，提供功能：
  ApplicationContextAware----->ApplicationContextAwareProcessor

```java
package com.suftz.demo.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

@Component
public class Employee implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {
    private ApplicationContext applicationContext;
    private String beanName;

    /**
     * 获取spring中的applicationContext对象
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取当前被实例化的bean的名字
     */
    @Override
    public void setBeanName(String name) {
        this.beanName=name;
    }

    public String getBeanName() {
        return beanName;
    }

    /**
     * 获取字符串解析器，它可以把本身具有占位符的字符串进行解析
     */
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        String resolveStringValue=resolver.resolveStringValue("你好${os.name}");
        System.out.println("解析后的字符串为："+resolveStringValue);
    }

}
```

测试如下：

```java
@Test
public void testAware(){
    Employee employee=context.getBean("employee",Employee.class);
    System.out.println(employee);

    System.out.println(context == employee.getApplicationContext());//true
}
```

#### @Profile

Spring为开发者提供可以根据当前环境，动态激活和切换一系列组件的功能

开发环境，测试环境，生产环境，可能有不同的组件使用，或者使用不同的组件，或者禁用一些组件
数据源的切换

@Profile:指定组件在哪个环境的情况下才能被注册到容器中，不指定则在任何环境下都能注册这个组件

1. 加了环境标识的bean，只有这个环境被激活的时候才能注册到容器中。默认是default环境
2. 写在配置类上，只有是指定的环境的时候，整个配置类里面的所有配置才能开始生效
3. 没有标注环境标识的bean，在任何环境下都会加载

使用示例：
在配置类中配置多个数据源，根据不同的数据源切换

1. 配置类如下：

   ```java
   package com.suftz.demo.config;

   import com.alibaba.druid.pool.DruidDataSource;
   import com.suftz.demo.bean.Person;
   import org.springframework.context.annotation.*;

   import javax.sql.DataSource;


   //@PropertySource(value={"classpath:/db.properties"})//也可以通过配置来读取数据库的连接
   //@Profile("dev")//这里配置，则环境为此设置时该配置才能生效
   @Configuration
   @ComponentScan(basePackages = {"com.suftz.demo"})
   public class MainConfig2 {
       @Scope(value = "prototype")
       @Bean(value = "person",initMethod = "init",destroyMethod = "destroy")//没有设置id，则会以方法名作为id
       public Person getPerson(){
           return new Person("Tom",123);
       }
       /**
       * 开发环境数据源
       */
       @Profile("dev")
       @Bean("devDataSource")
       public DataSource dataSourceDev(){
           DruidDataSource dataSource=new DruidDataSource();
           dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
           dataSource.setUrl("jdbc:mysql://localhost:3306/spring1?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai");
           dataSource.setUsername("root");
           dataSource.setPassword("booksys123");
           dataSource.setMinEvictableIdleTimeMillis(300000);
           dataSource.setValidationQuery("SELECT 1");
           return dataSource;
       }

       /**
        * 测试环境数据源
        */
       @Profile("test")
       @Bean("testDataSource")
       public DataSource dataSourceTest(){
           DruidDataSource dataSource=new DruidDataSource();
           dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
           dataSource.setUrl("jdbc:mysql://localhost:3306/spring2?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai");
           dataSource.setUsername("root");
           dataSource.setPassword("booksys123");
           dataSource.setMinEvictableIdleTimeMillis(300000);
           dataSource.setValidationQuery("SELECT 1");
           return dataSource;
       }

       /**
        * 生产环境数据源
        */
       @Profile("prod")
       @Bean("prodDataSource")
       public DataSource dataSourceProd(){
           DruidDataSource dataSource=new DruidDataSource();
           dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
           dataSource.setUrl("jdbc:mysql://localhost:3306/spring3?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai");
           dataSource.setUsername("root");
           dataSource.setPassword("booksys123");
           dataSource.setMinEvictableIdleTimeMillis(300000);
           dataSource.setValidationQuery("SELECT 1");
           return dataSource;
       }
   }
   ```

2. 测试方法如下：

   ```java
   import com.suftz.demo.config.MainConfig;
   import com.suftz.demo.config.MainConfig2;
   import org.junit.Test;
   import org.springframework.context.annotation.AnnotationConfigApplicationContext;

   import javax.sql.DataSource;

   public class ProfileTest {

       @Test
       public void testGetDataSource() {
           AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(MainConfig2.class);
           String[] nameForType = context.getBeanNamesForType(DataSource.class);
           for (String s : nameForType) {
               System.out.println(s);
           }
       }

       @Test
       public void testProfile(){
           AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext();
           applicationContext.getEnvironment().setActiveProfiles("test","dev");

           applicationContext.register(MainConfig2.class);
           applicationContext.refresh();
           String[] nameForType = applicationContext.getBeanNamesForType(DataSource.class);
           for (String s : nameForType) {
               System.out.println(s);
           }
           //devDataSource
           //testDataSource
       }
   }
   ```

如何设置当前系统环境是属于哪个(两种方式)：

* 使用命令行动态参数：在虚拟机参数位置加载如下：
   `-Dspring.profiles.active=test`
   `-Dspring.profiles.active=dev`
   `-Dspring.profiles.active=test`

* 在容器创建的时候，设置需要激活的环境
  1. 使用无参的构造器，创建一个applicationContext对象
  2. 然后设置需要激活的环境
  3. 注册主配置类
  4. 启动刷新容器

  ```java
  AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext();
  applicationContext.getEnvironment().setActiveProfiles("test","dev");
  applicationContext.register(MainConfig.class);
  applicationContext.refresh();
  ```

### AOP

#### AOP注解开发

AOP:动态代理
指在程序运行期间动态地将某段代码切入到指定方法指定位置进行运行的编程方式

通知方法：

1. 前置通知(@Before)：在目标方法运行之前运行
2. 后置通知(@After)：在目标方法运行结束之后运行（无论方法正常结束还是异常结束）
3. 返回通知(@AfterReturning)：在目标方法正常返回之后运行
4. 异常通知(@AfterThrowing)：在目标方法出现异常以后运行
5. 环绕通知(@Around)：动态代理，手动推进目标方法运行（被该注解的方法体内需要执行`proceedingJoinPoint.proceed();`）

* 需要将切面类的目标方法使用通知注解来标识何时运行
* 将切面类和业务逻辑类(目标方法所在类)都加入到ioc容器中
* 需要在切面类上添加注解@Aspect来表明这个类不是一个普通的类，而是一个切面类

在之前spring配置文件中可以使用配置的方式来开启基于注解的切面功能，然后再使用@Aspect注解告诉ioc容器这是一个切面类

也可以通过完全配置的方式来设置切面类

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
    <bean id="userDaoProxy" class="com.suftz.aop.UserDaoProxy"/>

    <aop:config>
        <!--切入点-->
        <aop:pointcut id="p1" expression="execution(* com.suftz.dao.*.add(..))"/>
        <!--配置切面-->
        <aop:aspect ref="userDaoProxy" order="1">
            <aop:before method="before" pointcut-ref="p1"/>
            <aop:after-returning method="afterReturning" pointcut-ref="p1"/><!--这两个的执行，在正确的情况下，是按照前后顺序相反执行的-->
            <aop:after method="after" pointcut-ref="p1"/><!--这两个的执行，在正确的情况下，是按照前后顺序相反执行的-->
            <aop:after-throwing method="afterThrowing" pointcut-ref="p1"/>
            <aop:around method="around" pointcut-ref="p1"/>
        </aop:aspect>
    </aop:config>

</beans>
```

那么完全基于注解开发，需要使用@EnableAspectJAutoProxy

使用注解开启AOP扫描：

```java
package com.suftz.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAOP {
}
```

UserService.java如下：

```java
package com.suftz.demo.service;

import com.suftz.demo.bean.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public boolean login(User user){
        return false;
    }
}
```

增强的通知类UserServiceProxy.java如下：

```java
package com.suftz.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class UserServiceProxy {

    @Pointcut("execution(public boolean com.suftz.demo.service.UserService.login(..))")
    public void pointCut(){}

    /**
     * 在目标方法之前切入，切入点表达式
     */
    @Before("pointCut()")
    //@Before("execution(public boolean com.suftz.demo.service.UserService.login(..))")
    public void loginStart(JoinPoint joinPoint){
        System.out.println("方法："+joinPoint.getSignature().getName());
        System.out.println("执行登录之前");
        System.out.println(Arrays.toString(joinPoint.getArgs()));
    }

    @After("com.suftz.demo.aop.UserServiceProxy.pointCut()")
    public void loginEnd(JoinPoint joinPoint){
        System.out.println("方法："+joinPoint.getSignature().getName());
        System.out.println(Arrays.toString(joinPoint.getArgs()));
        System.out.println("执行登录结束");
    }

    @AfterReturning(value="pointCut()",returning = "result")//指定接收返回值的形参变量名
    public void loginReturn(JoinPoint joinPoint,Object result){
        System.out.println("登录执行正常，登录验证的结果:"+result);
    }

    @AfterThrowing(value="pointCut()",throwing = "exception")
    public void loginException(JoinPoint joinPoint,Exception exception){
        System.out.println("执行登录出现异常，异常信息:"+exception.getMessage());
    }

    //JoinPoint如果要使用，则必须放在参数列表的第一位
}
```

测试类如下：

```java
import com.suftz.demo.bean.User;
import com.suftz.demo.config.MainConfig;
import com.suftz.demo.config.MainConfig3;
import com.suftz.demo.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AOPTest {

    AnnotationConfigApplicationContext context=null;

    @Before
    public void init(){
        context=new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
    }

    @Test
    public void testUserServiceLoginMethod(){
        UserService userService=context.getBean("userService",UserService.class);
        System.out.println(userService.getClass().getName());//代理对象：com.suftz.demo.service.UserService$$EnhancerBySpringCGLIB$$263d79f6
        userService.login(new User("Tom",20,"aaasdf"));
        /**
         * 方法：login
         * 执行登录之前
         * [User{name='Tom', age=20}]
         * 方法：login
         * [User{name='Tom', age=20}]
         * 执行登录结束
         * 登录执行正常，登录验证的结果:false
         */
    }
}
```

#### AOP注解原理

```java
package com.suftz.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP：【动态代理】
 * 指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式；
 *
 * 1、导入aop模块；Spring AOP：(spring-aspects)
 * 2、定义一个业务逻辑类（MathCalculator）；在业务逻辑运行的时候将日志进行打印（方法之前、方法运行结束、方法出现异常，xxx）
 * 3、定义一个日志切面类（LogAspects）：切面类里面的方法需要动态感知MathCalculator.div运行到哪里然后执行；
 *  通知方法：
 *  前置通知(@Before)：logStart：在目标方法(div)运行之前运行
 *  后置通知(@After)：logEnd：在目标方法(div)运行结束之后运行（无论方法正常结束还是异常结束）
 *  返回通知(@AfterReturning)：logReturn：在目标方法(div)正常返回之后运行
 *  异常通知(@AfterThrowing)：logException：在目标方法(div)出现异常以后运行
 *  环绕通知(@Around)：动态代理，手动推进目标方法运行（joinPoint.procced()）
 * 4、给切面类的目标方法标注何时何地运行（通知注解）；
 * 5、将切面类和业务逻辑类（目标方法所在类）都加入到容器中;
 * 6、必须告诉Spring哪个类是切面类(给切面类上加一个注解：@Aspect)
 * [7]、给配置类中加 @EnableAspectJAutoProxy 【开启基于注解的aop模式】
 *    在Spring中很多的 @EnableXXX;
 *
 * 三步：
 *   1）、将业务逻辑组件和切面类都加入到容器中；告诉Spring哪个是切面类（@Aspect）
 *   2）、在切面类上的每一个通知方法上标注通知注解，告诉Spring何时何地运行（切入点表达式）
 *  3）、开启基于注解的aop模式；@EnableAspectJAutoProxy
 *
 * AOP原理：【看给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么？】
 *     @EnableAspectJAutoProxy；
 * 1、@EnableAspectJAutoProxy是什么？
 *     @Import(AspectJAutoProxyRegistrar.class)：给容器中导入AspectJAutoProxyRegistrar
 *       利用AspectJAutoProxyRegistrar自定义给容器中注册bean；BeanDefinetion
 *       internalAutoProxyCreator=AnnotationAwareAspectJAutoProxyCreator
 *
 *     给容器中注册一个AnnotationAwareAspectJAutoProxyCreator；
 *
 * 2、 AnnotationAwareAspectJAutoProxyCreator：
 *     AnnotationAwareAspectJAutoProxyCreator
 *       ->AspectJAwareAdvisorAutoProxyCreator
 *         ->AbstractAdvisorAutoProxyCreator
 *           ->AbstractAutoProxyCreator
 *               implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *             关注后置处理器（在bean初始化完成前后做事情）、自动装配BeanFactory
 *
 * AbstractAutoProxyCreator.setBeanFactory()
 * AbstractAutoProxyCreator.有后置处理器的逻辑；
 *
 * AbstractAdvisorAutoProxyCreator.setBeanFactory()-》initBeanFactory()
 *
 * AnnotationAwareAspectJAutoProxyCreator.initBeanFactory()
 *
 *
 * 流程：
 *     1）、传入配置类，创建ioc容器
 *     2）、注册配置类，调用refresh（）刷新容器；
 *     3）、registerBeanPostProcessors(beanFactory);注册bean的后置处理器来方便拦截bean的创建；
 *       1）、先获取ioc容器已经定义了的需要创建对象的所有BeanPostProcessor
 *       2）、给容器中加别的BeanPostProcessor
 *       3）、优先注册实现了PriorityOrdered接口的BeanPostProcessor；
 *       4）、再给容器中注册实现了Ordered接口的BeanPostProcessor；
 *       5）、注册没实现优先级接口的BeanPostProcessor；
 *          6）、注册BeanPostProcessor，实际上就是创建BeanPostProcessor对象，保存在容器中；
 *             创建internalAutoProxyCreator的BeanPostProcessor【AnnotationAwareAspectJAutoProxyCreator】
 *             1）、创建Bean的实例
 *             2）、populateBean；给bean的各种属性赋值
 *             3）、initializeBean：初始化bean；
 *                   1）、invokeAwareMethods()：处理Aware接口的方法回调
 *                   2）、applyBeanPostProcessorsBeforeInitialization()：应用后置处理器的postProcessBeforeInitialization（）
 *                   3）、invokeInitMethods()；执行自定义的初始化方法
 *                   4）、applyBeanPostProcessorsAfterInitialization()；执行后置处理器的postProcessAfterInitialization（）；
 *             4）、BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)创建成功；--》aspectJAdvisorsBuilder
 *          7）、把BeanPostProcessor注册到BeanFactory中；
 *             beanFactory.addBeanPostProcessor(postProcessor);
 * =======以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程========
 *
 *          AnnotationAwareAspectJAutoProxyCreator => InstantiationAwareBeanPostProcessor
 *       4）、finishBeanFactoryInitialization(beanFactory);完成BeanFactory初始化工作；创建剩下的单实例bean
 *          1）、遍历获取容器中所有的Bean，依次创建对象getBean(beanName);
 *             getBean->doGetBean()->getSingleton()->
 *          2）、创建bean
 *             【AnnotationAwareAspectJAutoProxyCreator在所有bean创建之前会有一个拦截，InstantiationAwareBeanPostProcessor，会调用postProcessBeforeInstantiation()】
 *             1）、先从缓存中获取当前bean，如果能获取到，说明bean是之前被创建过的，直接使用，否则再创建；
 *                只要创建好的Bean都会被缓存起来
 *             2）、createBean（）;创建bean；
 *                AnnotationAwareAspectJAutoProxyCreator 会在任何bean创建之前先尝试返回bean的实例
 *                【BeanPostProcessor是在Bean对象创建完成初始化前后调用的】
 *                【InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象的】
 *                1）、resolveBeforeInstantiation(beanName, mbdToUse);解析BeforeInstantiation
 *                   希望后置处理器在此能返回一个代理对象；如果能返回代理对象就使用，如果不能就继续
 *                   1）、后置处理器先尝试返回对象；
 *                      bean = applyBeanPostProcessorsBeforeInstantiation（）：
 *                         拿到所有后置处理器，如果是InstantiationAwareBeanPostProcessor;
 *                         就执行postProcessBeforeInstantiation
 *                      if (bean != null) {
bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
}
 *
 *                2）、doCreateBean(beanName, mbdToUse, args);真正的去创建一个bean实例；和3.6流程一样；
 *                3）、
 *
 *
 * AnnotationAwareAspectJAutoProxyCreator【InstantiationAwareBeanPostProcessor】   的作用：
 * 1）、每一个bean创建之前，调用postProcessBeforeInstantiation()；
 *       关心MathCalculator和LogAspect的创建
 *       1）、判断当前bean是否在advisedBeans中（保存了所有需要增强bean）
 *       2）、判断当前bean是否是基础类型的Advice、Pointcut、Advisor、AopInfrastructureBean，
 *          或者是否是切面（@Aspect）
 *       3）、是否需要跳过
 *          1）、获取候选的增强器（切面里面的通知方法）【List<Advisor> candidateAdvisors】
 *             每一个封装的通知方法的增强器是 InstantiationModelAwarePointcutAdvisor；
 *             判断每一个增强器是否是 AspectJPointcutAdvisor 类型的；返回true
 *          2）、永远返回false
 *
 * 2）、创建对象
 * postProcessAfterInitialization；
 *       return wrapIfNecessary(bean, beanName, cacheKey);//包装如果需要的情况下
 *       1）、获取当前bean的所有增强器（通知方法）  Object[]  specificInterceptors
 *          1、找到候选的所有的增强器（找哪些通知方法是需要切入当前bean方法的）
 *          2、获取到能在bean使用的增强器。
 *          3、给增强器排序
 *       2）、保存当前bean在advisedBeans中；
 *       3）、如果当前bean需要增强，创建当前bean的代理对象；
 *          1）、获取所有增强器（通知方法）
 *          2）、保存到proxyFactory
 *          3）、创建代理对象：Spring自动决定
 *             JdkDynamicAopProxy(config);jdk动态代理；
 *             ObjenesisCglibAopProxy(config);cglib的动态代理；
 *       4）、给容器中返回当前组件使用cglib增强了的代理对象；
 *       5）、以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程；
 *
 *
 *    3）、目标方法执行   ；
 *       容器中保存了组件的代理对象（cglib增强后的对象），这个对象里面保存了详细信息（比如增强器，目标对象，xxx）；
 *       1）、CglibAopProxy.intercept();拦截目标方法的执行
 *       2）、根据ProxyFactory对象获取将要执行的目标方法拦截器链；
 *          List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 *          1）、List<Object> interceptorList保存所有拦截器 5
 *             一个默认的ExposeInvocationInterceptor 和 4个增强器；
 *          2）、遍历所有的增强器，将其转为Interceptor；
 *             registry.getInterceptors(advisor);
 *          3）、将增强器转为List<MethodInterceptor>；
 *             如果是MethodInterceptor，直接加入到集合中
 *             如果不是，使用AdvisorAdapter将增强器转为MethodInterceptor；
 *             转换完成返回MethodInterceptor数组；
 *
 *       3）、如果没有拦截器链，直接执行目标方法;
 *          拦截器链（每一个通知方法又被包装为方法拦截器，利用MethodInterceptor机制）
 *       4）、如果有拦截器链，把需要执行的目标对象，目标方法，
 *          拦截器链等信息传入创建一个 CglibMethodInvocation 对象，
 *          并调用 Object retVal =  mi.proceed();
 *       5）、拦截器链的触发过程;
 *          1)、如果没有拦截器执行执行目标方法，或者拦截器的索引和拦截器数组-1大小一样（指定到了最后一个拦截器）执行目标方法；
 *          2)、链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回以后再来执行；
 *             拦截器链的机制，保证通知方法与目标方法的执行顺序；
 *
 *    总结：
 *       1）、  @EnableAspectJAutoProxy 开启AOP功能
 *       2）、 @EnableAspectJAutoProxy 会给容器中注册一个组件 AnnotationAwareAspectJAutoProxyCreator
 *       3）、AnnotationAwareAspectJAutoProxyCreator是一个后置处理器；
 *       4）、容器的创建流程：
 *          1）、registerBeanPostProcessors（）注册后置处理器；创建AnnotationAwareAspectJAutoProxyCreator对象
 *          2）、finishBeanFactoryInitialization（）初始化剩下的单实例bean
 *             1）、创建业务逻辑组件和切面组件
 *             2）、AnnotationAwareAspectJAutoProxyCreator拦截组件的创建过程
 *             3）、组件创建完之后，判断组件是否需要增强
 *                是：切面的通知方法，包装成增强器（Advisor）;给业务逻辑组件创建一个代理对象（cglib）；
 *       5）、执行目标方法：
 *          1）、代理对象执行目标方法
 *          2）、CglibAopProxy.intercept()；
 *             1）、得到目标方法的拦截器链（增强器包装成拦截器MethodInterceptor）
 *             2）、利用拦截器的链式机制，依次进入每一个拦截器进行执行；
 *             3）、效果：
 *                正常执行：前置通知-》目标方法-》后置通知-》返回通知
 *                出现异常：前置通知-》目标方法-》后置通知-》异常通知
 *
 *
 *
 */
@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAOP {
}
```

源码解析图示：//todo

![aop原理1](images/aop原理1.png)

### 声明式事务

需要在配置类中创建数据源，开启事务管理器，在需要进行事务管理的方法或者类上使用@Transactional注解标识当前方法或类是一个事务方法(类)

```java
package com.suftz.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement//事务管理
@Configuration
@ComponentScan(basePackages = {"com.suftz.demo"})
public class TxConfig {

    @Bean("dataSource")
    public DataSource dataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/spring1?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("booksys123");
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setValidationQuery("SELECT 1");
        return dataSource;
    }

    @Bean("jdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) throws Exception{
        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
        //JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource());//直接调用dataSource()也可以从ioc容器注入，而不是去new一个数据源
        return jdbcTemplate;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

}
```

UserService.java中的事务方法如下：

```java
package com.suftz.demo.service;

import com.suftz.demo.bean.User;
import com.suftz.demo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public boolean login(User user){
        return false;
    }

    @Transactional
    public int addUser(User user){
        int result=userDao.addUser(user);
        int i=10/0;
        return result;
    }
}
```

#### @EnableTransactionManagement原理

```java
package com.suftz.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
/**
* 原理：
* 1）、@EnableTransactionManagement
*         利用TransactionManagementConfigurationSelector给容器中会导入组件
 *         导入两个组件
 *         AutoProxyRegistrar
 *         ProxyTransactionManagementConfiguration
 * 2）、AutoProxyRegistrar：
 *         给容器中注册一个 InfrastructureAdvisorAutoProxyCreator 组件；
 *         InfrastructureAdvisorAutoProxyCreator：？
 *         利用后置处理器机制在对象创建以后，包装对象，返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用；
 *
 * 3）、ProxyTransactionManagementConfiguration 做了什么？
 *             1、给容器中注册事务增强器；
 *                 1）、事务增强器要用事务注解的信息，AnnotationTransactionAttributeSource解析事务注解
 *                 2）、事务拦截器：
 *                     TransactionInterceptor；保存了事务属性信息，事务管理器；
 *                     他是一个 MethodInterceptor；
 *                     在目标方法执行的时候；
 *                         执行拦截器链；
 *                         事务拦截器：
 *                             1）、先获取事务相关的属性
 *                             2）、再获取PlatformTransactionManager，如果事先没有添加指定任何transactionmanger
 *                                 最终会从容器中按照类型获取一个PlatformTransactionManager；
 *                             3）、执行目标方法
 *                                 如果异常，获取到事务管理器，利用事务管理回滚操作；
 *                                 如果正常，利用事务管理器，提交事务
 *
 */


@EnableTransactionManagement//事务管理
@Configuration
@ComponentScan(basePackages = {"com.suftz.demo"})
public class TxConfig {

    @Bean("dataSource")
    public DataSource dataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/spring1?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("booksys123");
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setValidationQuery("SELECT 1");
        return dataSource;
    }

    @Bean("jdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) throws Exception{
        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
        //JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource());//直接调用dataSource()也可以从ioc容器注入，而不是去new一个数据源
        return jdbcTemplate;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

}
```

## 扩展原理

### BeanFactoryPostProcessor

* BeanPostProcessor：bean后置处理器，bean创建对象初始化前后进行拦截工作
* BeanFactoryPostProcessor：BeanFactory的后置处理器
  在BeanFactory标准初始化之后调用，所有的bean定义已经保存加载到BeanFactory，但是还没有任何bean被创建和加载到ioc容器

* BeanFactoryPostProcessor原理:
  1. ioc容器创建对象
  2. invokeBeanFactoryPostProcessors(beanFactory);
     如何找到所有的BeanFactoryPostProcessor并执行他们的方法：

     1. 直接在BeanFactory中找到所有类型是BeanFactoryPostProcessor的组件，并执行他们的方法
     2. 在初始化创建其他组件前面执行

```java
package com.suftz.demo.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor...");
        int count=beanFactory.getBeanDefinitionCount();
        String[] names=beanFactory.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("bean定义总数："+count);
    }
}
```

//源码解析todo

### BeanDefinitionRegistryPostProcessor

`public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor`

* 对标准BeanFactoryPostProcessor SPI的扩展，允许在进行常规BeanFactoryPostProcessor检测之前注册其他Bean定义。特别是，BeanDefinitionRegistryPostProcessor可以注册其他Bean定义，这些定义又定义了BeanFactoryPostProcessor实例。

该接口定义了一个方法:
`void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException;`

* 标准初始化后，修改应用程序上下文的内部bean定义注册表。 所有常规bean定义都将被加载，但尚未实例化任何bean。 这允许在下一个后处理阶段开始之前添加更多的bean定义。
* 参数为：注册表–应用程序上下文使用的bean定义注册表
* 该方法早于BeanFactoryPostPorcessor执行，利用该接口的实例，可以给容器再额外添加一些组件

```java
package com.suftz.demo.config;

import com.suftz.demo.bean.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        RootBeanDefinition rootBeanDefinition =new RootBeanDefinition(User.class);
        registry.registerBeanDefinition("user",rootBeanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
```

原理：

1. ioc创建对象
2. refresh()-》invokeBeanFactoryPostProcessors(beanFactory);
3. 从容器中获取到所有的BeanDefinitionRegistryPostProcessor组件。
    1. 依次触发所有的postProcessBeanDefinitionRegistry()方法
    2. 再来触发postProcessBeanFactory()方法BeanFactoryPostProcessor；
4. 再来从容器中找到BeanFactoryPostProcessor组件；然后依次触发postProcessBeanFactory()方法

//todo源码分析

### ApplicationListener

```java
package com.suftz.demo.config;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {

    //当容器中发布此事件以后，方法触发
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("receice a event:"+event);

/**
* console print :
* receice a event:org.springframework.context.event.ContextRefreshedEvent[source=org.springframework.context.annotation.AnnotationConfigApplicationContext@1089e5f:
 * startup date [Mon Feb 08 14:56:33 CST 2021]; root of context hierarchy]
 * receice a event:MyTest$1[source=myself pubish a test event]
* receice a event:org.springframework.context.event.ContextClosedEvent[source=org.springframework.context.annotation.AnnotationConfigApplicationContext@1089e5f:
 * startup date [Mon Feb 08 14:56:33 CST 2021]; root of context hierarchy]
*
*/
    }
}

/**
 * ApplicationListener：监听容器中发布的事件。事件驱动模型开发；
         * 	  public interface ApplicationListener<E extends ApplicationEvent>
 * 		监听 ApplicationEvent 及其下面的子事件；
         *
         * 	 步骤：
         * 		1）、写一个监听器（ApplicationListener实现类）来监听某个事件（ApplicationEvent及其子类）
         * 			@EventListener;
 * 			原理：使用EventListenerMethodProcessor处理器来解析方法上的@EventListener；
        *
        * 		2）、把监听器加入到容器；
        * 		3）、只要容器中有相关事件的发布，我们就能监听到这个事件；
        * 				ContextRefreshedEvent：容器刷新完成（所有bean都完全创建）会发布这个事件；
        * 				ContextClosedEvent：关闭容器会发布这个事件；
        * 		4）、发布一个事件：
        * 				applicationContext.publishEvent()；
        *
        *  原理：
        *  	ContextRefreshedEvent、IOCTest_Ext$1[source=我发布的时间]、ContextClosedEvent；
        *  1）、ContextRefreshedEvent事件：
        *  	1）、容器创建对象：refresh()；
        *  	2）、finishRefresh();容器刷新完成会发布ContextRefreshedEvent事件
        *  2）、自己发布事件；
        *  3）、容器关闭会发布ContextClosedEvent；
        *
        *  【事件发布流程】：
        *  	3）、publishEvent(new ContextRefreshedEvent(this));
        *  			1）、获取事件的多播器（派发器）：getApplicationEventMulticaster()
        *  			2）、multicastEvent派发事件：
        *  			3）、获取到所有的ApplicationListener；
        *  				for (final ApplicationListener<?> listener : getApplicationListeners(event, type)) {
        *  				1）、如果有Executor，可以支持使用Executor进行异步派发；
        *  					Executor executor = getTaskExecutor();
        *  				2）、否则，同步的方式直接执行listener方法；invokeListener(listener, event);
        *  				 拿到listener回调onApplicationEvent方法；
        *
        *  【事件多播器（派发器）】
        *  	1）、容器创建对象：refresh();
        *  	2）、initApplicationEventMulticaster();初始化ApplicationEventMulticaster；
        *  		1）、先去容器中找有没有id=“applicationEventMulticaster”的组件；
        *  		2）、如果没有this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        *  			并且加入到容器中，我们就可以在其他组件要派发事件，自动注入这个applicationEventMulticaster；
        *
        *  【容器中有哪些监听器】
        *  	1）、容器创建对象：refresh();
        *  	2）、registerListeners();
        *  		从容器中拿到所有的监听器，把他们注册到applicationEventMulticaster中；
        *  		String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
        *  		//将listener注册到ApplicationEventMulticaster中
        *  		getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
        *
        *   SmartInitializingSingleton 原理：->afterSingletonsInstantiated();
        *   		1）、ioc容器创建对象并refresh()；
        *   		2）、finishBeanFactoryInitialization(beanFactory);初始化剩下的单实例bean；
        *   			1）、先创建所有的单实例bean；getBean();
        *   			2）、获取所有创建好的单实例bean，判断是否是SmartInitializingSingleton类型的；
        *   				如果是就调用afterSingletonsInstantiated();
        */
```

在测试方法中发布一个自定义的事件

```java
import com.suftz.demo.bean.User;
import com.suftz.demo.config.MainConfig4;
import com.suftz.demo.config.TxConfig;
import com.suftz.demo.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class MyTest {

    AnnotationConfigApplicationContext context=null;

    @Before
    public void init(){
        context=new AnnotationConfigApplicationContext(MainConfig4.class);
    }

    @Test
    public void test1(){
        //发布事件
        context.publishEvent(new ApplicationEvent(new String("myself pubish a test event")) {
        });
        context.close();
    }

}
```

使用@EventListener注解使得普通的类的方法也能监听事件

```java
package com.suftz.demo.service;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @EventListener(classes = {ApplicationEvent.class})
    public void listen(ApplicationEvent event){
        System.out.println("userService监听到的事件："+event);
    }
}
```

//todo源码解析

### Spring容器创建

```text
Spring容器的refresh()【创建刷新】;
1、prepareRefresh()刷新前的预处理;
	1）、initPropertySources()初始化一些属性设置;子类自定义个性化的属性设置方法；
	2）、getEnvironment().validateRequiredProperties();检验属性的合法等
	3）、earlyApplicationEvents= new LinkedHashSet<ApplicationEvent>();保存容器中的一些早期的事件；
2、obtainFreshBeanFactory();获取BeanFactory；
	1）、refreshBeanFactory();刷新【创建】BeanFactory；
			创建了一个this.beanFactory = new DefaultListableBeanFactory();
			设置id；
	2）、getBeanFactory();返回刚才GenericApplicationContext创建的BeanFactory对象；
	3）、将创建的BeanFactory【DefaultListableBeanFactory】返回；
3、prepareBeanFactory(beanFactory);BeanFactory的预准备工作（BeanFactory进行一些设置）；
	1）、设置BeanFactory的类加载器、支持表达式解析器...
	2）、添加部分BeanPostProcessor【ApplicationContextAwareProcessor】
	3）、设置忽略的自动装配的接口EnvironmentAware、EmbeddedValueResolverAware、xxx；
	4）、注册可以解析的自动装配；我们能直接在任何组件中自动注入：
			BeanFactory、ResourceLoader、ApplicationEventPublisher、ApplicationContext
	5）、添加BeanPostProcessor【ApplicationListenerDetector】
	6）、添加编译时的AspectJ；
	7）、给BeanFactory中注册一些能用的组件；
		environment【ConfigurableEnvironment】、
		systemProperties【Map<String, Object>】、
		systemEnvironment【Map<String, Object>】
4、postProcessBeanFactory(beanFactory);BeanFactory准备工作完成后进行的后置处理工作；
	1）、子类通过重写这个方法来在BeanFactory创建并预准备完成以后做进一步的设置
======================以上是BeanFactory的创建及预准备工作==================================
5、invokeBeanFactoryPostProcessors(beanFactory);执行BeanFactoryPostProcessor的方法；
	BeanFactoryPostProcessor：BeanFactory的后置处理器。在BeanFactory标准初始化之后执行的；
	两个接口：BeanFactoryPostProcessor、BeanDefinitionRegistryPostProcessor
	1）、执行BeanFactoryPostProcessor的方法；
		先执行BeanDefinitionRegistryPostProcessor
		1）、获取所有的BeanDefinitionRegistryPostProcessor；
		2）、看先执行实现了PriorityOrdered优先级接口的BeanDefinitionRegistryPostProcessor、
			postProcessor.postProcessBeanDefinitionRegistry(registry)
		3）、在执行实现了Ordered顺序接口的BeanDefinitionRegistryPostProcessor；
			postProcessor.postProcessBeanDefinitionRegistry(registry)
		4）、最后执行没有实现任何优先级或者是顺序接口的BeanDefinitionRegistryPostProcessors；
			postProcessor.postProcessBeanDefinitionRegistry(registry)
			
		
		再执行BeanFactoryPostProcessor的方法
		1）、获取所有的BeanFactoryPostProcessor
		2）、看先执行实现了PriorityOrdered优先级接口的BeanFactoryPostProcessor、
			postProcessor.postProcessBeanFactory()
		3）、在执行实现了Ordered顺序接口的BeanFactoryPostProcessor；
			postProcessor.postProcessBeanFactory()
		4）、最后执行没有实现任何优先级或者是顺序接口的BeanFactoryPostProcessor；
			postProcessor.postProcessBeanFactory()
6、registerBeanPostProcessors(beanFactory);注册BeanPostProcessor（Bean的后置处理器）【 intercept bean creation】
		不同接口类型的BeanPostProcessor；在Bean创建前后的执行时机是不一样的
		BeanPostProcessor、
		DestructionAwareBeanPostProcessor、
		InstantiationAwareBeanPostProcessor、
		SmartInstantiationAwareBeanPostProcessor、
		MergedBeanDefinitionPostProcessor【internalPostProcessors】、
		
		1）、获取所有的 BeanPostProcessor;后置处理器都默认可以通过PriorityOrdered、Ordered接口来执行优先级
		2）、先注册PriorityOrdered优先级接口的BeanPostProcessor；
			把每一个BeanPostProcessor；添加到BeanFactory中
			beanFactory.addBeanPostProcessor(postProcessor);
		3）、再注册Ordered接口的
		4）、最后注册没有实现任何优先级接口的
		5）、最终注册MergedBeanDefinitionPostProcessor；
		6）、注册一个ApplicationListenerDetector；来在Bean创建完成后检查是否是ApplicationListener，如果是
			applicationContext.addApplicationListener((ApplicationListener<?>) bean);
7、initMessageSource();初始化MessageSource组件（做国际化功能；消息绑定，消息解析）；
		1）、获取BeanFactory
		2）、看容器中是否有id为messageSource的，类型是MessageSource的组件
			如果有赋值给messageSource，如果没有自己创建一个DelegatingMessageSource；
				MessageSource：取出国际化配置文件中的某个key的值；能按照区域信息获取；
		3）、把创建好的MessageSource注册在容器中，以后获取国际化配置文件的值的时候，可以自动注入MessageSource；
			beanFactory.registerSingleton(MESSAGE_SOURCE_BEAN_NAME, this.messageSource);	
			MessageSource.getMessage(String code, Object[] args, String defaultMessage, Locale locale);
8、initApplicationEventMulticaster();初始化事件派发器；
		1）、获取BeanFactory
		2）、从BeanFactory中获取applicationEventMulticaster的ApplicationEventMulticaster；
		3）、如果上一步没有配置；创建一个SimpleApplicationEventMulticaster
		4）、将创建的ApplicationEventMulticaster添加到BeanFactory中，以后其他组件直接自动注入
9、onRefresh();留给子容器（子类）
		1、子类重写这个方法，在容器刷新的时候可以自定义逻辑；
10、registerListeners();给容器中将所有项目里面的ApplicationListener注册进来；
		1、从容器中拿到所有的ApplicationListener
		2、将每个监听器添加到事件派发器中；
			getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
		3、派发之前步骤产生的事件；
11、finishBeanFactoryInitialization(beanFactory);初始化所有剩下的单实例bean；
	1、beanFactory.preInstantiateSingletons();初始化后剩下的单实例bean
		1）、获取容器中的所有Bean，依次进行初始化和创建对象
		2）、获取Bean的定义信息；RootBeanDefinition
		3）、Bean不是抽象的，是单实例的，是懒加载；
			1）、判断是否是FactoryBean；是否是实现FactoryBean接口的Bean；
			2）、不是工厂Bean。利用getBean(beanName);创建对象
				0、getBean(beanName)； ioc.getBean();
				1、doGetBean(name, null, null, false);
				2、先获取缓存中保存的单实例Bean。如果能获取到说明这个Bean之前被创建过（所有创建过的单实例Bean都会被缓存起来）
					从private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(256);获取的
				3、缓存中获取不到，开始Bean的创建对象流程；
				4、标记当前bean已经被创建
				5、获取Bean的定义信息；
				6、【获取当前Bean依赖的其他Bean;如果有按照getBean()把依赖的Bean先创建出来；】
				7、启动单实例Bean的创建流程；
					1）、createBean(beanName, mbd, args);
					2）、Object bean = resolveBeforeInstantiation(beanName, mbdToUse);让BeanPostProcessor先拦截返回代理对象；
						【InstantiationAwareBeanPostProcessor】：提前执行；
						先触发：postProcessBeforeInstantiation()；
						如果有返回值：触发postProcessAfterInitialization()；
					3）、如果前面的InstantiationAwareBeanPostProcessor没有返回代理对象；调用4）
					4）、Object beanInstance = doCreateBean(beanName, mbdToUse, args);创建Bean
						 1）、【创建Bean实例】；createBeanInstance(beanName, mbd, args);
						 	利用工厂方法或者对象的构造器创建出Bean实例；
						 2）、applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
						 	调用MergedBeanDefinitionPostProcessor的postProcessMergedBeanDefinition(mbd, beanType, beanName);
						 3）、【Bean属性赋值】populateBean(beanName, mbd, instanceWrapper);
						 	赋值之前：
						 	1）、拿到InstantiationAwareBeanPostProcessor后置处理器；
						 		postProcessAfterInstantiation()；
						 	2）、拿到InstantiationAwareBeanPostProcessor后置处理器；
						 		postProcessPropertyValues()；
						 	=====赋值之前：===
						 	3）、应用Bean属性的值；为属性利用setter方法等进行赋值；
						 		applyPropertyValues(beanName, mbd, bw, pvs);
						 4）、【Bean初始化】initializeBean(beanName, exposedObject, mbd);
						 	1）、【执行Aware接口方法】invokeAwareMethods(beanName, bean);执行xxxAware接口的方法
						 		BeanNameAware\BeanClassLoaderAware\BeanFactoryAware
						 	2）、【执行后置处理器初始化之前】applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
						 		BeanPostProcessor.postProcessBeforeInitialization（）;
						 	3）、【执行初始化方法】invokeInitMethods(beanName, wrappedBean, mbd);
						 		1）、是否是InitializingBean接口的实现；执行接口规定的初始化；
						 		2）、是否自定义初始化方法；
						 	4）、【执行后置处理器初始化之后】applyBeanPostProcessorsAfterInitialization
						 		BeanPostProcessor.postProcessAfterInitialization()；
						 5）、注册Bean的销毁方法；
					5）、将创建的Bean添加到缓存中singletonObjects；
				ioc容器就是这些Map；很多的Map里面保存了单实例Bean，环境信息。。。。；
		所有Bean都利用getBean创建完成以后；
			检查所有的Bean是否是SmartInitializingSingleton接口的；如果是；就执行afterSingletonsInstantiated()；
12、finishRefresh();完成BeanFactory的初始化创建工作；IOC容器就创建完成；
		1）、initLifecycleProcessor();初始化和生命周期有关的后置处理器；LifecycleProcessor
			默认从容器中找是否有lifecycleProcessor的组件【LifecycleProcessor】；如果没有new DefaultLifecycleProcessor();
			加入到容器；
			
			写一个LifecycleProcessor的实现类，可以在BeanFactory
				void onRefresh();
				void onClose();	
		2）、	getLifecycleProcessor().onRefresh();
			拿到前面定义的生命周期处理器（BeanFactory）；回调onRefresh()；
		3）、publishEvent(new ContextRefreshedEvent(this));发布容器刷新完成事件；
		4）、liveBeansView.registerApplicationContext(this);
	
	======总结===========
	1）、Spring容器在启动的时候，先会保存所有注册进来的Bean的定义信息；
		1）、xml注册bean；<bean>
		2）、注解注册Bean；@Service、@Component、@Bean、xxx
	2）、Spring容器会合适的时机创建这些Bean
		1）、用到这个bean的时候；利用getBean创建bean；创建好以后保存在容器中；
		2）、统一创建剩下所有的bean的时候；finishBeanFactoryInitialization()；
	3）、后置处理器；BeanPostProcessor
		1）、每一个bean创建完成，都会使用各种后置处理器进行处理；来增强bean的功能；
			AutowiredAnnotationBeanPostProcessor:处理自动注入
			AnnotationAwareAspectJAutoProxyCreator:来做AOP功能；
			xxx....
			增强的功能注解：
			AsyncAnnotationBeanPostProcessor
			....
	4）、事件驱动模型；
		ApplicationListener；事件监听；
		ApplicationEventMulticaster；事件派发：
```

## WEB

### servleet3.0完全注解开发

从servelt3.0之后(Tomcat7及以上才支持)可以进行注解开发，通过注解来注册三大组件：Servlet,Filter,Listener
同时也支持完全通过配置类，而不是web.xml配置文件的方式，进行web开发，原理是：servlet3.0之后tomcat容器在启动时，会在编译文件的根路径下寻找`META-INF/services/javax.servlet.ServletContainerInitializer`这个文件，这个文件里面需要填写配置类的全类名即可
然后再将所有的配置都在配置类中完成

配置类编写如下：

```java
package com.suftz.demo.servlet;

import com.suftz.demo.filter.MyFilter;
import com.suftz.demo.listener.MyListener;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;

//容器启动的时候会将这个注解指定的类型的子类传递过来，在方法的Set中可以获取到
@HandlesTypes(value = {HelloServlet.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {
    @Override
    /**
     * 应用启动的时候，会运行onStartup方法：
     * Set<Class<?>> 感兴趣的所有子类型
     * ServletContext 代表当前web应用的ServletContext
     *   使用ServletContext注册web组件(Servlet,Filter,Listener)
     *   使用编码的方式，在项目启动的时候给ServletContext里面添加组件
     *
     * 注意：只能在项目启动的时候使用ServletContext添加组件，其他时机再添加是无效的
     */
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println("onStartup");
        ServletRegistration.Dynamic servlet=ctx.addServlet("HelloServlet",new HelloServlet());
        servlet.addMapping("/hello");

        ctx.addListener(MyListener.class);


        FilterRegistration.Dynamic filter=ctx.addFilter("myFilter",MyFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");

        //上述方式，不需要再到相应类中添加对应注解
    }
}
```

定义的Servlet,Filter,Listener可以通过注解来添加到tomcat容器中
Servlet注解：@WebServlet("/hello")
Filter注解：@WebFilter("/*")
Listener注解：@WebListener

### SpringMVC完全注解开发

SpringMVC的完全注解开发也是和Servlet3.0的原理一样，在SpringMVC的包中，就有一个META-INF/services/javax.servlet.ServletContainerInitializer文件，其中指定了一个配置类，即org.springframework.web.SpringServletContainerInitializer，而该类也实现了onStartUp方法，但是通过注解传入了一个接口@HandlesTypes(WebApplicationInitializer.class)，而这个接口有是三个抽象类：

* org.springframework.web.context.AbstractContextLoaderInitializer
* org.springframework.web.servlet.support.AbstractDispatcherServletInitializer
* org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

所以只需要实现AbstractAnnotationConfigDispatcherServletInitializer接口的方法，进行注册即可

```java
package com.suftz.demo.servlet;

import com.suftz.demo.config.AppConfig;
import com.suftz.demo.config.RootConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//web容器启动的时候创建对象；调用方法来初始化容器以及前端控制器
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 获取根容器的配置类：Spring的配置文件，父容器
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    /**
     * 获取web容器的配置类：SpringMVC配置文件，子容器
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    /**
     * 获取DispatcherServlet的映射信息
     *
     *   "/"  :拦截所有请求，包括静态资源,但是不包括*.jsp
     *  "/*" :拦截所有请求,连*.jsp页面都拦截.jsp页面时tomcat的jsp引擎解析的，不属于mvc的工作范畴
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
```

RootConfig.java配置如下：

```java
package com.suftz.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

@ComponentScan(value="com.suftz.demo",excludeFilters = {
        @ComponentScan.Filter(type= FilterType.ANNOTATION,classes={Controller.class})
})
public class RootConfig {
}
```

AppConfig.java配置如下：

```java
package com.suftz.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@ComponentScan(value="com.suftz.demo",includeFilters = {
        @ComponentScan.Filter(type= FilterType.ANNOTATION,classes={Controller.class})
},useDefaultFilters = false)
@EnableWebMvc//<mvc:annotation-driven ></mvc:annotation-driven>
public class AppConfig
        //implements WebMvcConfigurer  //如果继承这个接口，则接口所有的方法都需要重写
 extends WebMvcConfigurerAdapter {//这个父类实现了上面的接口，只不过方法都是空的，继承该父类后，可以根据需要来重写方法

    /**
     *     配置视图解析器：如何把handler方法返回值解析为实际的物理视图
     *     <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
     *         <property name="prefix" value="/WEB-INF/views/"></property>
     *         <property name="suffix" value=".jsp"></property>
     *     </bean>
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        //默认所有的页面都从WEB-INF下查找，，即jsp("/WEB-INF/", ".jsp");
        //registry.jsp();

        registry.jsp("/WEB-INF/views/",".jsp");
    }

    /**
     * 处理静态资源
     * <mvc:default-servlet-handler></mvc:default-servlet-handler>
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
        configurer.enable();
    }

    /**
     * 添加拦截器
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyFirstInterceptor()).addPathPatterns("/**");
    }
}
```

### 异步请求

#### Servlet3.0的异步请求

```java
package com.suftz.demo.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/helloasync",asyncSupported = true)
public class HelloAsyncServlet extends HelloServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("主线程开启："+Thread.currentThread().getName());
        AsyncContext asyncContext=req.startAsync();
        System.out.println(asyncContext);
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                try {
                    sayHello();
                    ServletResponse response = asyncContext.getResponse();
                    response.getWriter().write("hello async...");
                    asyncContext.complete();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        System.out.println("主线程结束:"+Thread.currentThread().getName());
    }

    public void sayHello() throws InterruptedException {
        System.out.println("执行业务的线程:"+Thread.currentThread().getName());
        Thread.sleep(3000);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
```

控制台打印如下：

```text
主线程开启：http-nio-8080-exec-84
org.apache.catalina.core.AsyncContextImpl@553f36
主线程结束:http-nio-8080-exec-84
执行业务的线程:http-nio-8080-exec-85
```

#### SpringMVC异步请求

```java
package com.suftz.demo.handler;

import com.suftz.demo.bean.User;
import com.suftz.demo.service.DeferredResultQueue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * 控制返回Callable：
 * Spring异步处理，将Callable提交到TaskExecutor使用一个隔离的线程进行执行
 * DispatcherServlet和所有的Filter退出web容器的线程，但是response保持打开状态
 * Callable返回结果，SpringMVC将请求重新派发给容器，恢复之前的处理
 * 根据Callable返回的结果，SpringMVC继续进行视图渲染流程（springmvc重新进行接收请求和视图渲染）
 *
 * 在springmvc自定义拦截器中,打印如下：
 * preHandle
 * 主线程开启：http-nio-8080-exec-50
 * 主线程结束：http-nio-8080-exec-50
 * 业务处理线程：MvcAsync1
 * preHandle
 * postHandle
 * afterCompletion
 *
 * 以上说明了Callable线程处理结果完毕之后，又重新进行了完整的mvc请求过程
 * 但是这两次请求，是相同的url，相同的request,只不过第二次请求时，不用再去执行请求处理方法了，直接以Callable线程结果作为处理结果
 */
@Controller
public class AsyncHandler {

    @ResponseBody
    @RequestMapping("/asynchandler")
    public Callable<String> test(){
        System.out.println("主线程开启："+Thread.currentThread().getName());
        Callable<String> callable=new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("业务处理线程："+Thread.currentThread().getName());
                Thread.sleep(3000);
                return "this is a async handler";
            }
        };
        System.out.println("主线程结束："+Thread.currentThread().getName());
        return callable;
    }


    /**
     * 在地址栏上打开两个标签页，分别地址是下面两个请求，先访问createUser,然后立马去访问create
     * 可以看到createUser页面虽然先打开，但是一直在等待，
     * 而访问create则立马出了结果，而此页面出结果后，createUser页面也随即出结果
     * 说明两个业务处理有消息传递
     *
     */

    @ResponseBody
    @RequestMapping("/createUser")
    public DeferredResult<Object> createUser(){
        DeferredResult<Object> deferredResult=new DeferredResult<>(8000L,"create user fail...");
        DeferredResultQueue.save(deferredResult);
        return deferredResult;
    }

    @ResponseBody
    @RequestMapping("/create")
    public String create(){
        User user=new User("关羽",30,"123qwe");
        DeferredResult<Object> deferredResult= DeferredResultQueue.get();
        deferredResult.setResult(user.toString());
        return user.toString();
    }
}
```