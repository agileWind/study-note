# MyBatisPlus

[TOC]

## 简介

### 认识MyBatisPlus

MyBatis-Plus（简称 MP）是一个 MyBatis的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。

一、**特性**

1. **无侵入**：只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑
2. **损耗小**：启动即会自动注入基本 CURD，性能基本无损耗，直接面向对象操作
3. **强大的 CRUD 操作**：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
4. **支持 Lambda 形式调用**：通过 Lambda 表达式，方便的编写各类查询条件，无需再担心字段写错
5. **支持主键自动生成**：支持多达 4 种主键策略（内含分布式唯一 ID 生成器 - Sequence），可自由配置，完美解决主键问题
6. **支持 ActiveRecord 模式**：支持 ActiveRecord 形式调用，实体类只需继承 Model 类即可进行强大的 CRUD 操作
7. **支持自定义全局通用操作**：支持全局通用方法注入（ Write once, use anywhere ）
内置代码生成器：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，更有超多自定义配置等您来使用
8. **内置分页插件**：基于 MyBatis 物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同于普通 List 查询
9. **分页插件支持多种数据库**：支持 MySQL、MariaDB、Oracle、DB2、H2、HSQL、SQLite、Postgre、SQLServer 等多种数据库
10. **内置性能分析插件**：可输出 Sql 语句以及其执行时间，建议开发测试时启用该功能，能快速揪出慢查询
11. **内置全局拦截插件**：提供全表 delete 、 update 操作智能分析阻断，也可自定义拦截规则，预防误操作

二、**支持数据库**

任何能使用 mybatis 进行 crud, 并且支持标准 sql 的数据库，都能使用MyBatisPlus

三、**框架结构**

![mybatis-plus-framework](images/mybatis-plus-framework.jpg)

### 代码和文档地址

官方地址：<https://mp.baomidou.com/>

代码发布地址：<https://github.com/baomidou/mybatis-plus>

### 前置知识

* 完整的JavaSE知识
* 数据库知识
* SQL
* Spring
* Mybatis
* Maven

## 集成MyBaitPlus

1. 导入相关依赖

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>

       <groupId>com.suftz.demo</groupId>
       <artifactId>mybatisplus-demo</artifactId>
       <version>1.0-SNAPSHOT</version>

       <dependencies>
           <!--
               需要注意的是，导入了mybatis-plus包后，就不需要再导入mybatis，mybatis-spring的包了
               mybatis-plus会自动将依赖加入进来
           -->
           <dependency>
               <groupId>com.baomidou</groupId>
               <artifactId>mybatis-plus</artifactId>
               <version>2.3</version>
           </dependency>

           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <version>6.0.6</version>
           </dependency>

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
               <exclusions>
                   <exclusion>
                   <groupId>org.slf4j</groupId>
                   <artifactId>slf4j-api</artifactId>
                   </exclusion>
               </exclusions>
           </dependency>
           <dependency>
               <groupId>org.slf4j</groupId>
               <artifactId>slf4j-api</artifactId>
               <version>1.7.30</version>
           </dependency>

           <dependency>
               <groupId>net.sf.ehcache</groupId>
               <artifactId>ehcache-core</artifactId>
               <version>2.6.8</version>
               <exclusions>
                   <exclusion>
                       <groupId>org.slf4j</groupId>
                       <artifactId>slf4j-api</artifactId>
                   </exclusion>
               </exclusions>
           </dependency>

           <!--单元测试JUnit4-->
           <dependency>
               <groupId>junit</groupId>
               <artifactId>junit</artifactId>
               <version>4.12</version>
               <scope>test</scope>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-test</artifactId>
               <version>4.3.10.RELEASE</version>
           </dependency>

           <dependency>
               <groupId>com.alibaba</groupId>
               <artifactId>druid</artifactId>
               <version>1.0.9</version>
           </dependency>

           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-context</artifactId>
               <version>4.3.10.RELEASE</version>
           </dependency>

           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-orm</artifactId>
               <version>4.3.10.RELEASE</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-aspects</artifactId>
               <version>4.3.10.RELEASE</version>
           </dependency>
       </dependencies>
   </project>
   ```

2. 新建日志配置文件log4j2.xml，这个文件放在resources目录下

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <Configuration status="WARN">
       <Appenders>
           <Console name="Console" target="SYSTEM_OUT">
               <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
           </Console>
       </Appenders>
       <Loggers>
           <Root level="INFO">
               <AppenderRef ref="Console"/>
           </Root>
           <logger name="java.sql" level="INFO"></logger>
           <logger name="org.apache.ibatis" level="INFO"></logger>
       </Loggers>
   </Configuration>
   ```

3. 新建数据源配置文件

   ```properties
   prop.driverClass=com.mysql.cj.jdbc.Driver
   prop.url=jdbc:mysql://localhost:3306/mp?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai
   prop.username=root
   prop.password=booksys123
   prop.minEvictableIdleTimeMillis=300000
   prop.validationQuery=SELECT 1
   ```

4. 新建mybatis-config.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
       <settings>
           <!--延时加载-->
   <!--        <setting name="lazyLoadingEnabled" value="true" />-->
   <!--        <setting name="aggressiveLazyLoading" value="false" />-->
           <setting name="jdbcTypeForNull" value="NULL" />
   <!--        该设置会将Java中的驼峰命名方式与数据库字段进行转换识别，比如xxxYyyZzz,在数据库就是xxx_yyy_zzz -->
           <setting name="mapUnderscoreToCamelCase" value="true" />
           <setting name="logImpl" value="LOG4J2"></setting>

       </settings>

       <!--typeAliases:别名处理器：可以为java类型起别名，这样就可以在sql映射文件中的select标签的resultType属性中直接使用别名了，不用写全类名-->
       <!--别名不区分大小写-->
       <typeAliases>
           <typeAlias type="com.suftz.demo.mp.bean.Employee" alias="employee" />

           <!--
               package:为某个包下的所有类批量起别名
               name:指定包名（为当前包以及下面所有的后代包的每一个类都起一个默认别名(类名小写)）
               批量起别名的情况下，使用@Alias注解为某个类型指定新的别名
           -->
           <package name="com.suftz.demo.mp.bean" />

       </typeAliases>

       <typeHandlers>
           <!--指定其他的枚举处理器，这个是会保持枚举的索引值，而不是枚举名，而默认不指定的情况下会保持枚举的名称-->
   <!--        <typeHandler handler="org.apache.ibatis.type.EnumOrdinalTypeHandler" javaType="指定自己自定义的枚举类的全类名"></typeHandler>-->
   <!--        <typeHandler handler="com.suftz.demo.mybatis.typehandler.MyEnumTypehandler" javaType="com.suftz.demo.mybatis.bean.UserStatus"></typeHandler>-->
   <!--也可以在sql映射文件中：
       在给sql传值的时候，可以在引用的表达式里面写处理器：#{userStatus,typeHandler=com.suftz.demo.mybatis.typehandler.MyEnumTypehandler}
       在解析结果的时候，可以定义resultType，然后在处理枚举属性的时候：<result column="user_status" property="userStatus" typeHandler="com.suftz.demo.mybatis.typehandler.MyEnumTypehandler" >
   -->
       </typeHandlers>


       <!--自定义插件
       <plugins>
           <plugin interceptor="com.github.pagehelper.PageInterceptor">

           </plugin>
           <plugin interceptor="com.suftz.demo.mp.interceptors.MyFirstPlugin">
               <property name="username" value="root"></property>
               <property name="password" value="123qwe"></property>
           </plugin>
       </plugins>
       -->

       <!--environments:mybatis可以配置多种环境。default指定当前是哪种环境
           environment:配置一个具体的环境信息,必须要有以下两个子标签。id属性：代表当前环境的唯一标识，可以用来区别不同的数据库，或者不同的生产环境，可以方便快速切换当前环境
               transactionManager:mybatis有两种事务管理器，type取值有JDBC|MANAGED，这两个值其实是别名，可以通过查看Configuration类查看
                       自定义事务管理器，去实现TransactionFactory接口，type指定为全类名即可
               dataSource:数据源，type取值有UNPOOLED|POOLED|JNDI。不使用数据源|使用数据源|使用容器集中部署的数据源引用。这些都是别名
               也可以自定义，去实现DataSourceFactory接口
       -->

       <!--这个value值是给sql映射文件里的select标签的databaseId使用的，可以指定这个语句是给哪个数据库使用
       <databaseIdProvider type="DB_VENDOR">
           <property name="Oracle" value="oracle" />
           <property name="MySQL" value="mysql" />
       </databaseIdProvider>
       -->

       <!--将sql映射文件注册到全局配置文件中，只有注册到全局配置文件后，才能被使用
           resource:引用类路径下的sql映射文件
           url:引用网络路径或者磁盘路径下的sql映射文件
               file:///var/mappers/UserMapper.xml
               http://www.suftz.com/static/mappers/UserMapper.xml
           class:用来注册接口的，因为接口上可以通过注解来写sql，不需要sql映射文件了，但是仍然需要在这里注册才能使用
           一般重要的接口查询sql，还是要写在映射文件上比较好
           package:可以批量注册，但是只能注册接口，sql映射文件想要被注册除非也在放在了这个包下
           可以在资源文件夹目录下，也建相同的包，这样在运行时，这些sql文件就会被放在代码的包下
       -->
       <mappers>
   <!--        <mapper resource="EmployeeMapper.xml" />-->

   <!--        <mapper class="接口上通过注解把sql语句放接口方法上了，在这里注册这个接口即可" />-->
   <!--        <package name="com.suftz.demo.mp.dao" />--><!--这里如果跟上面的mapper重复就会报错-->
       </mappers>
   </configuration>
   ```

5. 新建applicationContext.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xmlns:tx="http://www.springframework.org/schema/tx"
          xmlns:mybatis-spring="http://mybatis.org/schema/mybatis-spring"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd
          http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd
          http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://mybatis.org/schema/mybatis-spring http://mybatis.org/schema/mybatis-spring.xsd">

       <!--
           如果想扫描多个包，可以使用逗号分隔开
           或者写包的上层目录，这样其子包都能被扫描
       -->
       <context:component-scan base-package="com.suftz.demo.mp"></context:component-scan>

       <context:property-placeholder location="classpath:jdbc.properties" />
       <!--配置数据库连接池信息，获取数据源对象-->
       <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
           <property name="driverClassName" value="${prop.driverClass}"></property>
           <property name="url" value="${prop.url}"></property>
           <property name="username" value="${prop.username}"></property>
           <property name="password" value="${prop.password}"></property>
           <property name="minEvictableIdleTimeMillis" value="${prop.minEvictableIdleTimeMillis}"></property>
           <property name="validationQuery" value="${prop.validationQuery}"></property>
       </bean>


       <!--<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">-->
       <!--mybatisplus的配置数据源-->
       <bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean">
           <property name="dataSource" ref="dataSource"></property>
           <property name="typeAliasesPackage"  value="com.suftz.ssmdemo.bean"/><!--配置mapper别名扫描包-->
           <property name="mapperLocations" value="classpath:mapper/*.xml"></property>
           <property name="configLocation" value="classpath:mybatis-config.xml"></property>
       </bean>

       <!--mybatisplus: 配置可以批量执行的sqlSession-->
       <!--<bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">-->
       <bean id="sqlSessionTemplate" class="com.baomidou.mybatisplus.MybatisSqlSessionTemplate">
           <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory"></constructor-arg>
           <constructor-arg name="executorType" value="BATCH"></constructor-arg>
       </bean>

       <!--扫描所有的mapper接口-->
       <mybatis-spring:scan base-package="com.suftz.demo.mp.mapper"></mybatis-spring:scan>

       <!--事务管理器-->
       <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
           <property name="dataSource" ref="dataSource"></property>
       </bean>
       <!--配置通知-->
       <tx:advice id="txadvice">
           <!--配置事务参数-->
           <tx:attributes>
               <tx:method name="add*" propagation="REQUIRED" isolation="REPEATABLE_READ" read-only="false" timeout="4"  />
               <tx:method name="get*"  read-only="true" />
           </tx:attributes>
       </tx:advice>

       <!--配置切入点和切面-->
       <aop:config>
           <!--配置切入点-->
           <aop:pointcut id="pointcut" expression="execution(* com.suftz.demo.mp.service.*.*(..))" />
           <!--配置切面-->
           <aop:advisor advice-ref="txadvice" pointcut-ref="pointcut" />
       </aop:config>

   </beans>
   ```

6. 可以新建一个Employee类；以及对应的接口EmployeeMapper，方法可以先不定义；还有EmployeeService类使用@Service注解，里面的方法暂时也不用去写

7. 编写测试用例：

   ```java
   import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
   import org.junit.Test;
   import org.junit.runner.RunWith;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.test.context.ContextConfiguration;
   import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

   import javax.sql.DataSource;

   @RunWith(SpringJUnit4ClassRunner.class)  //单元测试框架
   @ContextConfiguration(locations = {"classpath:applicationContext.xml"})  //加载配置文件
   public class EmployeeTest {

       @Autowired
       MybatisSqlSessionFactoryBean sqlSessionFactory;

       @Autowired
       DataSource dataSource;

       @Test
       public void test(){
           System.out.println(dataSource);
           System.out.println(sqlSessionFactory);
       }
   }
   ```

## 入门HelloWorld

假如对Employee数据进行CRUD：

* 基于 Mybatis
  1. 需要编写 EmployeeMapper 接口，并手动编写 CRUD 方法
  2. 提供 EmployeeMapper.xml 映射文件，并手动编写每个方法对应的 SQL 语句.
* 基于 MP
  1. 只需要创建 EmployeeMapper 接口, 并继承 BaseMapper 接口.这就是使用 MP
  2. 需要完成的所有操作，甚至不需要创建 SQL 映射文件。

### 准备工作

编写JOPO类，mapper接口，以及测试方法

1. Employee.java

   ```java
   package com.suftz.demo.mp.bean;

   import com.baomidou.mybatisplus.annotations.TableField;
   import com.baomidou.mybatisplus.annotations.TableId;
   import com.baomidou.mybatisplus.annotations.TableName;
   import com.baomidou.mybatisplus.enums.IdType;

   @TableName("tbl_employee")
   public class Employee {
       //@TableId(value = "id",type= IdType.AUTO),如果在全局配置文件中进行了配置则不需要
       /**
        * mybatisplus支持主键自增的数据库插入数据获取主键值
        * mybatis:需要通过useGeneratedKeys 以及 keyProperty 来设置
        * MP：自动将主键值回写到实体类中
        */
       private Integer id;
       private String lastName;
       private String email;

       /**
        * 0表示女，1表示男
        * 也可以使用枚举，然后再自定义枚举数据的解析器，使用拦截器原理
        */
       private Integer gender;

       private Integer age;

        //排除字段的主键，表示该属性在数据库表中没有对应的字段进行映射
       @TableField(exist=false)
       private Double salary;

       public Employee() {
       }

       public Employee(String lastName, String email, Integer gender, Integer age) {
           this.lastName = lastName;
           this.email = email;
           this.gender = gender;
           this.age = age;
       }

       public Employee(Integer id, String lastName, String email, Integer gender, Integer age) {
           this.id = id;
           this.lastName = lastName;
           this.email = email;
           this.gender = gender;
           this.age = age;
       }

       public Integer getId() {
           return id;
       }

       public void setId(Integer id) {
           this.id = id;
       }

       public String getLastName() {
           return lastName;
       }

       public void setLastName(String lastName) {
           this.lastName = lastName;
       }

       public String getEmail() {
           return email;
       }

       public void setEmail(String email) {
           this.email = email;
       }

       public Integer getGender() {
           return gender;
       }

       public void setGender(Integer gender) {
           this.gender = gender;
       }

       public Integer getAge() {
           return age;
       }

       public void setAge(Integer age) {
           this.age = age;
       }

       @Override
       public String toString() {
           return "Employee{" +
                   "id=" + id +
                   ", lastName='" + lastName + '\'' +
                   ", email='" + email + '\'' +
                   ", gender=" + gender +
                   ", age=" + age +
                   '}';
       }
   }
   ```

2. EmployeeMapper.java

   ```java
   package com.suftz.demo.mp.mapper;

   import com.baomidou.mybatisplus.mapper.BaseMapper;
   import com.suftz.demo.mp.bean.Employee;

   public interface EmployeeMapper extends BaseMapper<Employee> {
   }
   ```

### 插入操作

```java
import com.suftz.demo.mp.bean.Employee;
import com.suftz.demo.mp.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  //单元测试框架
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  //加载配置文件
public class EmployeeTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * insert：不会把非空的字段也写入到插入语句中
     */
    @Test
    public void testInsert(){
        //如果数据库编码以及表的编码以及表字段的编码不对应，则会报错 java.sql.SQLException: Incorrect string value: '\xE5\x88\x98\xE5\xA4\x87' ...
        Employee employee=new Employee("关羽","guanyu@suftz.com",1,28);
        Integer result=employeeMapper.insert(employee);
        /**
         * mybatisplus支持主键自增的数据库插入数据获取主键值
         * mybatis:需要通过useGeneratedKeys 以及 keyProperty 来设置
         * MP：自动将主键值回写到实体类中
         */
        System.out.println(employee);//Employee{id=7, lastName='关羽', email='guanyu@suftz.com', gender=1, age=28}
    }

    /**
     * insertAllColumn：所有的字段，不管是否为空，都会写入到插入语句中，只是对应字段的值为空
     * INSERT INTO tbl_employee ( last_name,email,gender,age ) VALUES ( ?,?,?,? )
     */
    @Test
    public void testInsertAllColumn(){
        Employee employee=new Employee();
        employee.setLastName("张飞");
        employee.setAge(27);
        employee.setGender(1);
        Integer result=employeeMapper.insertAllColumn(employee);//数据库email字段值为空，但是sql插入语句中有该字段
    }
}
```

配置文件applicatonContext.xml修改如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:mybatis-spring="http://mybatis.org/schema/mybatis-spring"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://mybatis.org/schema/mybatis-spring http://mybatis.org/schema/mybatis-spring.xsd">

    <!--
        如果想扫描多个包，可以使用逗号分隔开
        或者写包的上层目录，这样其子包都能被扫描
    -->
    <context:component-scan base-package="com.suftz.demo.mp"></context:component-scan>

    <context:property-placeholder location="classpath:jdbc.properties" />
    <!--配置数据库连接池信息，获取数据源对象-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="${prop.driverClass}"></property>
        <property name="url" value="${prop.url}"></property>
        <property name="username" value="${prop.username}"></property>
        <property name="password" value="${prop.password}"></property>
        <property name="minEvictableIdleTimeMillis" value="${prop.minEvictableIdleTimeMillis}"></property>
        <property name="validationQuery" value="${prop.validationQuery}"></property>
    </bean>


    <!--<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">-->
    <!--mybatisplus的配置数据源-->
    <bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"></property>
        <property name="typeAliasesPackage"  value="com.suftz.ssmdemo.bean"/><!--配置mapper别名扫描包-->
        <property name="mapperLocations" value="classpath:mapper/*.xml"></property>
        <property name="configLocation" value="classpath:mybatis-config.xml"></property>
        <property name="globalConfig" ref="globalConfiguration"></property>
    </bean>

    <!--mybatisplus: 配置可以批量执行的sqlSession-->
    <!--<bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">-->
    <bean id="sqlSessionTemplate" class="com.baomidou.mybatisplus.MybatisSqlSessionTemplate">
        <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory"></constructor-arg>
        <constructor-arg name="executorType" value="BATCH"></constructor-arg>
    </bean>

    <!--
        定义MybatisPlus的全局策略配置
        这个配置需要注入到sqlSessionFactory才能起作用
    -->
    <bean id="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
        <!--2.3版本之后，默认是true，作用xxxYyy转成了xxx_yyy -->
        <property name="dbColumnUnderline" value="true"></property>
        <!--AUTO(0, "数据库ID自增"), INPUT(1, "用户输入ID")  主键策略，也可以在bean上通过注解-->
        <property name="idType" value="0"></property>
        <!--全局的表前缀策略配置，即默认bean和表的名称对应关系，加上特定的前缀。这样就不用在每个bean上使用注解告知-->
        <property name="tablePrefix" value="tbl_"></property>
    </bean>

    <!--扫描所有的mapper接口-->
    <mybatis-spring:scan base-package="com.suftz.demo.mp.mapper"></mybatis-spring:scan>

    <!--事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    <!--配置通知-->
    <tx:advice id="txadvice">
        <!--配置事务参数-->
        <tx:attributes>
            <tx:method name="add*" propagation="REQUIRED" isolation="REPEATABLE_READ" read-only="false" timeout="4"  />
            <tx:method name="get"  read-only="true" />
        </tx:attributes>
    </tx:advice>

    <!--配置切入点和切面-->
    <aop:config>
        <!--配置切入点-->
        <aop:pointcut id="pointcut" expression="execution(* com.suftz.demo.mp.service.*.*(..))" />
        <!--配置切面-->
        <aop:advisor advice-ref="txadvice" pointcut-ref="pointcut" />
    </aop:config>

</beans>
```

从上面可以看出，多了一个MybatisPlus的全局策略配置

### 更新操作

```java
import com.suftz.demo.mp.bean.Employee;
import com.suftz.demo.mp.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  //单元测试框架
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  //加载配置文件
public class EmployeeTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * updateById：只对非空的字段(除主键外)进行更新
     */
    @Test
    public void testUpdateById(){
        Employee employee=new Employee();
        employee.setId(5);
        employee.setAge(51);
        employeeMapper.updateById(employee);//UPDATE tbl_employee SET age=? WHERE id=?
    }

    /**
     * updateAllColumnById：会对所有字段(除主键外)进行更新
     */
    @Test
    public void testUpdateAllColumnById(){
        Employee employee=new Employee();
        employee.setId(8);
        employee.setAge(11);
        employeeMapper.updateAllColumnById(employee);//UPDATE tbl_employee SET last_name=?,email=?,gender=?,age=? WHERE id=?
    }
}
```

### 查询操作

```java
import com.baomidou.mybatisplus.plugins.Page;
import com.suftz.demo.mp.bean.Employee;
import com.suftz.demo.mp.mapper.EmployeeMapper;
import org.apache.ibatis.plugin.Intercepts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)  //单元测试框架
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  //加载配置文件
public class EmployeeTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * selectById：通过id来查询一条记录
     */
    @Test
    public void testSelectById(){
        Employee employee=employeeMapper.selectById(1);
        System.out.println(employee);//Employee{id=1, lastName='刘备', email='liubei@suftz.com', gender=1, age=51}
    }

    /**
     * selectOne：根据多个列的条件来查询一条记录
     *
     * 注意：这个方法是查一条记录的。如果当前给定的条件在数据库可以查出多个记录，则会报错
     */
    @Test
    public void testSelectOne(){
        Employee employee=new Employee();
        employee.setId(1);
        employee.setLastName("刘备");
        Employee dbEmployee= employeeMapper.selectOne(employee);
        System.out.println(dbEmployee);//SELECT id AS id,last_name AS lastName,email,gender,age FROM tbl_employee WHERE id=? AND last_name=?
    }

    /**
     * selectBatchIds：通过传入存有多个id的list来查询一条记录
     */
    @Test
    public void testSelectBatchIds(){
        List<Integer> idList=new ArrayList<>();
        idList.add(1);
        idList.add(2);
        idList.add(3);
        List<Employee> list =employeeMapper.selectBatchIds(idList);
        //SELECT id AS id,last_name AS lastName,email,gender,age FROM tbl_employee WHERE id IN ( ? , ? , ? )
        list.forEach(System.out::println);
    }

    /**
     * selectByMap：通过map来封装查询条件，可以查询多个结果
     *
     * 注意：这里的key需要是数据库表的字段名，而不是javaBean的属性名
     */
    @Test
    public void testSelectByMap(){
        Map<String,Object> columnMap=new HashMap();
        columnMap.put("gender",1);
        columnMap.put("last_name","诸葛亮");
        List<Employee> list=employeeMapper.selectByMap(columnMap);
        //SELECT id AS id,last_name AS lastName,email,gender,age FROM tbl_employee WHERE gender = ? AND last_name = ?
        list.forEach(System.out::println);
    }

    /**
     * selectPage：分页查询
     *
     * 注意：本质是使用RowBounds分页，假分页，并没有真正做到分页
     */
    @Test
    public void testSelectPage(){
        List<Employee> list=employeeMapper.selectPage(new Page(1,2),null);
        //SELECT id AS id,last_name AS lastName,email,gender,age FROM tbl_employee    //假分页
        list.forEach(System.out::println);
    }
}
```

### 删除操作

```java
import com.baomidou.mybatisplus.plugins.Page;
import com.suftz.demo.mp.bean.Employee;
import com.suftz.demo.mp.mapper.EmployeeMapper;
import org.apache.ibatis.plugin.Intercepts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)  //单元测试框架
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  //加载配置文件
public class EmployeeTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * deleteById：根据主键id来删除数据
     */
    public void testDeleteById(){
        Integer rows=employeeMapper.deleteById(4);
        System.out.println(rows);
    }

    /**
     * deleteByMap：根据map中的多个条件来组成where条件来删除
     */
    @Test
    public void testDeleteByMap(){
        Map<String,Object> map=new HashMap<>();
        map.put("last_name","张飞");
        map.put("gender",null);//该条件没有被放入where中
        Integer rows=employeeMapper.deleteByMap(map);//DELETE FROM tbl_employee WHERE last_name = ?
        System.out.println(rows);
    }

    /**
     * deleteBatchIds：根据一组主键id，来删除数据
     */
    @Test
    public void testDeleteBatchIds(){
        List<Integer> list=new ArrayList();
        list.add(4);
        list.add(5);
        Integer rows=employeeMapper.deleteBatchIds(list);
        //DELETE FROM tbl_employee WHERE id IN ( ? , ? )
        System.out.println(rows);
    }
}
```

### MyBatisPlus启动注入SQL原理分析

1. 问题: xxxMapper 继承了 BaseMapper<T>, BaseMapper 中提供了通用的 CRUD 方法,
方法来源于 BaseMapper, 有方法就必须有 SQL, 因为 MyBatis 最终还是需要通过
SQL 语句操作数据.
前置知识:MyBatis 源码中比较重要的一些对象， MyBatis 框架的执行流程
Configuration
MappedStatement
MyBatis四大对象
...

2. 通过现象看到本质

* employeeMapper 的本质 org.apache.ibatis.binding.MapperProxy
* MapperProxy 中 sqlSession –>SqlSessionFactory
* SqlSessionFacotry 中 → Configuration→ MappedStatements每一个 mappedStatement 都表示 Mapper 接口中的一个方法与 Mapper 映射文件中的一个 SQL。
MP 在启动就会挨个分析 xxxMapper 中的方法，并且将对应的 SQL 语句处理好，保存到 configuration 对象中的 mappedStatements 中.
* 本质:
  * Configuration： MyBatis 或者 MP 全局配置对象
  * MappedStatement：一个 MappedStatement 对象对应 Mapper 配置文件中的一个select/update/insert/delete 节点，主要描述的是一条 SQL 语句
  * SqlMethod : 枚举对象 ， MP 支持的 SQL 方法
  * TableInfo： 数据库表反射信息 ，可以获取到数据库表相关的信息
  * SqlSource: SQL 语句处理对象
  * MapperBuilderAssistant： 用于缓存、 SQL 参数、查询方剂结果集处理等.通过MapperBuilderAssistant 将每一个 mappedStatement添加到 configuration 中的 mappedstatements 中

//todo,源码分析

### 通过CRUD小结

以上是基本的 CRUD 操作， 如您所见，我们仅仅需要继承一个 BaseMapper 即可实现
大部分单表 CRUD 操作。 BaseMapper 提供了多达 17 个方法给大家使用, 可以极其方
便的实现单一、批量、分页等操作。 极大的减少开发负担， 难道这就是 MP 的强大之处
了吗？
提出需求:
现有一个需求，需要分页查询 tbl_employee 表中，年龄在 18~50 之间性别为男且
姓名为 xx 的所有用户，这时候该如何实现上述需求呢？
MyBatis : 需要在 SQL 映射文件中编写带条件查询的 SQL,并基于 PageHelper 插件完成
分页. 实现以上一个简单的需求，往往需要做很多重复单调的工作。 普通的 Mapper
能够解决这类痛点吗？
MP: 依旧不用编写 SQL 语句, MP 提供了功能强大的条件构造器 EntityWrapper

## 条件构造器EntityWrapper

### EntityWrapper简介

说明:

* 以下出现的第一个入参boolean condition表示该条件是否加入最后生成的sql中，例如：query.like(StringUtils.isNotBlank(name), Entity::getName, name) .eq(age!=null && age >= 0, Entity::getAge, age)
* 以下代码块内的多个方法均为从上往下补全个别boolean类型的入参,默认为true
* 以下出现的泛型Param均为Wrapper的子类实例(均具有AbstractWrapper的所有方法)
* 以下方法在入参中出现的R为泛型,在普通wrapper中是String,在LambdaWrapper中是函数(例:Entity::getId,Entity为实体类,getId为字段id的getMethod)
* 以下方法入参中的R column均表示数据库字段,当R具体类型为String时则为数据库字段名(字段名是数据库关键字的自己用转义符包裹!)!而不是实体类数据字段名!!!,另当R具体类型为SFunction时项目runtime不支持eclipse自家的编译器!!!
* 以下举例均为使用普通wrapper,入参为Map和List的均以json形式表现!
* 使用中如果入参的Map或者List为空,则不会加入最后生成的sql中!!!

### 带条件的CRUD

```java
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.suftz.demo.mp.bean.Employee;
import com.suftz.demo.mp.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)  //单元测试框架
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  //加载配置文件
public class EntityWrapperTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * selectPage：根据其他的复杂条件来进行分页查询
     */
    @Test
    public void testSelectPage(){
        //查询年龄在18—40岁之间，名字为关羽的男性
        List<Employee> list=employeeMapper.selectPage(new Page<Employee>(1,2),new EntityWrapper<Employee>()
                .between("age",18,40)
                .eq("gender",1)
                .eq("last_name","关羽")
        );
        list.forEach(System.out::println);
    }

    @Test
    public void testSelectList(){
        //查询性别为女且年龄不超过20，或者邮箱中带有"e"字符的员工信息
        List<Employee> list=employeeMapper.selectList(new EntityWrapper<Employee>()
            .eq("gender",0).le("age",20)
            .or()
            .like("email","e")
        );
        //SELECT id AS id,last_name AS lastName,email,gender,age FROM tbl_employee WHERE (gender = ? AND age <= ? OR email LIKE ?)
        list.forEach(System.out::println);
    }

    @Test
    public void testSelectList2(){
        //查询性别为女且年龄不超过20，或者邮箱中带有"e"字符的员工信息
        List<Employee> list=employeeMapper.selectList(new EntityWrapper<Employee>()
                .eq("gender",0).le("age",20)
                .orNew()  //注意对比or()，两者产生的sql不一样
                .like("email","e")
        );
        //SELECT id AS id,last_name AS lastName,email,gender,age FROM tbl_employee WHERE (gender = ? AND age <= ?) OR (email LIKE ?)
        list.forEach(System.out::println);//打印结果和上面的or()一样
    }

    @Test
    public void testUpdate(){
        Employee employee=new Employee();
        employee.setLastName("张飞");
        employee.setEmail("zhangfei@suftz.com");
        employee.setGender(1);

        employeeMapper.update(employee,new EntityWrapper<Employee>()
            .eq("last_name","Tom")
            .eq("age",44)
        );
        //UPDATE tbl_employee SET last_name=?, email=?, gender=? WHERE (last_name = ? AND age = ?)
        //注意到，只会修改不为空的字段值
    }

    @Test
    public void testDelete(){
        employeeMapper.delete(new EntityWrapper<Employee>()
        .eq("age",44)
        .eq("last_name","张飞")
        );
        //DELETE FROM tbl_employee WHERE (age = ? AND last_name = ?)
    }

    @Test
    public void testOrderBy(){
        List<Employee> list=employeeMapper.selectList(new EntityWrapper<Employee>()
        .eq("gender",1)
        .between("age",25,50)
        .orderBy("age",true)//第二个参数表示是否升序,按照age升序
        .orderBy("email",false));//按照email降序
        //当然也可以使用.last()来拼接sql，这有sql注入的风险
        list.forEach(System.out::println);
    }

    /**
     * Condition也是继承了Wrapper，使用Condition的静态方法产生了一个对象
     * 条件使用和EntityWrapper一致
     */
    @Test
    public void testCondition(){
        //查询性别为女且年龄不超过20，或者邮箱中带有"e"字符的员工信息
        List<Employee> list=employeeMapper.selectList(Condition.create()
                .eq("gender",0).le("age",20)
                .orNew()
                .like("email","e")
        );
        //SELECT id AS id,last_name AS lastName,email,gender,age FROM tbl_employee WHERE (gender = ? AND age <= ?) OR (email LIKE ?)
        list.forEach(System.out::println);
    }
}
```

## ActiveRecord(活动记录)

Active Record(活动记录)，是一种领域模型模式，特点是一个模型类对应关系型数据库中的一个表，而模型类的一个实例对应表中的一行记录。
ActiveRecord 一直广受动态语言（ PHP 、 Ruby 等）的喜爱，而 Java 作为准静态语言，对于 ActiveRecord 往往只能感叹其优雅，所以 MP 也在 AR 道路上进行了一定的探索

仅仅需要让实体类继承Model类且实现主键指定方法，即可开启

```java
package com.suftz.demo.mp.bean;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
@TableName("tbl_employee")
public class Employee extends Model<Employee> {

    private Integer id;
    private String lastName;
    private String email;

    private Integer gender;

    private Integer age;

    public Employee() {
    }

    @Override
    protected Serializable pkVal() {
       return id;
    }

    public Employee(String lastName, String email, Integer gender, Integer age) {
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.age = age;
    }

    public Employee(Integer id, String lastName, String email, Integer gender, Integer age) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                '}';
    }

}
```

AR基本的CRUD

```java
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.suftz.demo.mp.bean.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)  //单元测试框架
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  //加载配置文件
public class ARTest {

    Employee employee=new Employee();

    @Test
    public void testInsert(){
        Employee employee=new Employee("赵云","zhaoyun@suftz",1,24);
        boolean result=employee.insert();//INSERT INTO tbl_employee ( last_name, email, gender, age ) VALUES ( ?, ?, ?, ? )
        System.out.println(result);//执行插入操作后数据库返回的结果
        System.out.println(employee);//可以带回主键id的值
    }

    @Test
    public void testUpdateById(){
        employee.setId(1);
        employee.setAge(100);
        boolean result=employee.updateById();
        System.out.println(result);
    }

    @Test
    public void testSelectById(){
        employee.setId(1);
        Employee result=employee.selectById();
        System.out.println(result);
    }

    @Test
    public void testSelectAll(){
        List<Employee> list=employee.selectAll();//SELECT id,last_name AS lastName,email,gender,age FROM tbl_employee
        list.forEach(System.out::println);
    }

    @Test
    public void testSelectList(){
        List<Employee> list=employee.selectList(new EntityWrapper<Employee>()
        .eq("last_name","刘备")
        .like("email","liu")
        );
        list.forEach(System.out::println);
    }


    @Test
    public void testSelectCount(){
       int count= employee.selectCount(new EntityWrapper().eq("gender",1));
       //SELECT COUNT(1) FROM tbl_employee WHERE (gender = ?)
        System.out.println(count);
    }

    /**
     * 删除可以执行且无误，则说明删除成功，而不是根据影响的行数，因为有的条件就是找不到对应可删的记录
     */
    @Test
    public void testDeleteById(){
        boolean result=employee.deleteById(11);//直接传主键id
        System.out.println(result);

        employee.setId(5);//不设置注解id的值，下面的删除会抛异常
        result=employee.deleteById();//不使用传参，而是在对象中指定主键id
        System.out.println(result);
    }

    /**
     * 根据更加丰富的条件来执行删除
     */
    @Test
    public void testDelete(){
        boolean result=employee.delete(new EntityWrapper<Employee>().like("last_name","貂"));
    }

    /**
     * 这是假分页
     */
    @Test
    public void testSelectPage(){
        Page<Employee> page=employee.selectPage(new Page(2,3),new EntityWrapper<Employee>().like("email","e"));
        //SELECT id,last_name AS lastName,email,gender,age FROM tbl_employee WHERE (email LIKE ?)
        List<Employee> list=page.getRecords();
        //这个page对象可以获取更多的相关的分页信息

    }
}
```

1. AR 模式提供了一种更加便捷的方式实现 CRUD 操作，其本质还是调用的 Mybatis 对应的方法，类似于语法糖
   > 语法糖是指计算机语言中添加的某种语法，这种语法对原本语言的功能并没有影响.可以更方便开发者使用，可以避免出错的机会，让程序可读性更好.
2. 到此，简单领略了 Mybatis-Plus 的魅力与高效率，值得注意的一点是：提供强大的代码生成器，可以快速生成各类代码，真正的做到了即开即用

## 代码生成器

1. MP 提供了大量的自定义设置，生成的代码完全能够满足各类型的需求
2. MP 的代码生成器 和 Mybatis MBG 代码生成器:
MP 的代码生成器都是基于 java 代码来生成。 MBG 基于 xml 文件进行代码生成
MyBatis 的代码生成器可生成: 实体类、 Mapper 接口、 Mapper 映射文件
MP 的代码生成器可生成: 实体类(可以选择是否支持 AR)、 Mapper 接口、 Mapper 映射
文件、 Service 层、 Controller 层

步骤：

1. 添加依赖：
MP 的代码生成器默认使用的是 Apache 的 Velocity 模板，当然也可以更换为别的模板
技术，例如 freemarker。

2. 编写代码生成器示例代码：

```java
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

public class HelloTest {

    /**
     * 1.全局配置
     * 2.数据源配置
     * 3.策略配置
     * 4.包名策略配置
     * 5.整合配置
     */
    @Test
    public void test(){
        //1.全局配置
        GlobalConfig config=new GlobalConfig();
        config.setActiveRecord(true)//支持AR模式
        .setAuthor("agile")
        .setOutputDir("E:\\idea_project\\mybatisplusdemo3\\src\\main\\java")
        .setFileOverride(true)
        .setIdType(IdType.AUTO)
        .setServiceName("%sService")//设置生成的service接口的名字的首字母是否为I，如IEmployeeService
        .setBaseResultMap(true)
        .setBaseColumnList(true);

        //2.数据源配置
        DataSourceConfig dsConfig=new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL)
        .setDriverName("com.mysql.jdbc.Driver")
        .setUrl("jdbc:mysql://localhost:3306/mp?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai")
        .setUsername("root")
        .setPassword("booksys123");

        //3.策略配置
        StrategyConfig stConfig=new StrategyConfig();
        stConfig.setCapitalMode(true)
        .setDbColumnUnderline(true)//开启驼峰转下划线
        .setNaming(NamingStrategy.underline_to_camel)
        .setTablePrefix("tbl_")
        .setInclude("tbl_employee");//生成的表，即是根据这个表来进行逆向解析生成代码的

        //4.包名策略配置
        PackageConfig pkConfig=new PackageConfig();
        pkConfig.setParent("com.suftz.demo.mp")
        .setMapper("mapper")
        .setService("service")
        .setEntity("bean")
        .setController("controller")
        .setXml("mapper");


        //5.整合配置
        AutoGenerator ag=new AutoGenerator();
        ag.setGlobalConfig(config);
        ag.setDataSource(dsConfig);
        ag.setStrategy(stConfig);
        ag.setPackageInfo(pkConfig);

        ag.execute();
    }
}
```

这只是一个可用的执行代码生成的示例，并不是完整的配置方案，更详细的配置参数请参考[MyBatisPlus官网](https://mp.baomidou.com/config/generator-config.html)

## 插件扩展

### Mybatis 插件机制简介

1. 插件机制:
Mybatis 通过插件(Interceptor) 可以做到拦截四大对象相关方法的执行,根据需求， 完
成相关数据的动态改变。
Executor
StatementHandler
ParameterHandler
ResultSetHandler
2. 插件原理
四大对象的每个对象在创建时，都会执行 interceptorChain.pluginAll()，会经过每个插
件对象的 plugin()方法，目的是为当前的四大对象创建代理。代理对象就可以拦截到四
大对象相关方法的执行，因为要执行四大对象的方法需要经过代理

### 分页插件

`com.baomidou.mybatisplus.plugins.PaginationInterceptor`

### 执行分析插件

1. com.baomidou.mybatisplus.plugins.SqlExplainInterceptor
2. SQL 执行分析拦截器，只支持 MySQL5.6.3 以上版本
3. 该插件的作用是分析 DELETE UPDATE 语句,防止小白或者恶意进行 DELETE UPDATE 全表操作
4. 只建议在开发环境中使用，不建议在生产环境使用
5. 在插件的底层 通过 SQL 语句分析命令:Explain 分析当前的 SQL 语句，根据结果集中的 Extra 列来断定当前是否全表操作。

### 性能分析插件

1. com.baomidou.mybatisplus.plugins.PerformanceInterceptor
2. 性能分析拦截器，用于输出每条 SQL 语句及其执行时间
3. SQL 性能执行分析,开发环境使用， 超过指定时间，停止运行。有助于发现问题

### 乐观锁插件

1. com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor
2. 如果想实现如下需求: 当要更新一条记录的时候，希望这条记录没有被别人更新
3. 乐观锁的实现原理:
取出记录时，获取当前 version 2
更新时，带上这个 version 2
执行更新时， set version = yourVersion+1 where version = yourVersion
如果 version 不对，就更新失败
4. @Version 用于注解实体字段，必须要有。

### 示例

1. applicationContext.xml需要进行注册插件(当然也可以在传统的mybatis-config.xml里面配置)，配置如下：

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xmlns:tx="http://www.springframework.org/schema/tx"
          xmlns:mybatis-spring="http://mybatis.org/schema/mybatis-spring"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd
          http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd
          http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://mybatis.org/schema/mybatis-spring http://mybatis.org/schema/mybatis-spring.xsd">

       <!--
           如果想扫描多个包，可以使用逗号分隔开
           或者写包的上层目录，这样其子包都能被扫描
       -->
       <context:component-scan base-package="com.suftz.demo.mp"></context:component-scan>

       <context:property-placeholder location="classpath:jdbc.properties" />
       <!--配置数据库连接池信息，获取数据源对象-->
       <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
           <property name="driverClassName" value="${prop.driverClass}"></property>
           <property name="url" value="${prop.url}"></property>
           <property name="username" value="${prop.username}"></property>
           <property name="password" value="${prop.password}"></property>
           <property name="minEvictableIdleTimeMillis" value="${prop.minEvictableIdleTimeMillis}"></property>
           <property name="validationQuery" value="${prop.validationQuery}"></property>
       </bean>


       <!--<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">-->
       <!--mybatisplus的配置数据源-->
       <bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean">
           <property name="dataSource" ref="dataSource"></property>
           <property name="typeAliasesPackage"  value="com.suftz.ssmdemo.bean"/><!--配置mapper别名扫描包-->
           <property name="mapperLocations" value="classpath:com/suftz/demo/mp/mapper/*.xml"></property>
           <property name="configLocation" value="classpath:mybatis-config.xml"></property>
           <property name="globalConfig" ref="globalConfiguration"></property>
           <property name="plugins">
               <list>
                   <!--注册分页插件-->
                   <bean class="com.baomidou.mybatisplus.plugins.PaginationInterceptor" />
                   <!--注册执行分析插件:可以拦截全表删除或者更新的操作，建议开发环境使用-->
                   <bean class="com.baomidou.mybatisplus.plugins.SqlExplainInterceptor">
                       <property name="stopProceed" value="true"></property>
                   </bean>
                   <!--注册性能分析插件，建议开发环境使用-->
                   <bean class="com.baomidou.mybatisplus.plugins.PerformanceInterceptor">
                       <property name="format" value="true"></property>
                       <!--<property name="maxTime" value=""></property>超过最大的执行时间，则会被停止执行，抛异常-->
                   </bean>
                   <!--注册乐观锁插件-->
                   <bean class="com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor" />
               </list>
           </property>
       </bean>

       <!--mybatisplus: 配置可以批量执行的sqlSession-->
       <!--<bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">-->
       <bean id="sqlSessionTemplate" class="com.baomidou.mybatisplus.MybatisSqlSessionTemplate">
           <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory"></constructor-arg>
           <constructor-arg name="executorType" value="BATCH"></constructor-arg>
       </bean>

       <!--
           定义MybatisPlus的全局策略配置
           这个配置需要注入到sqlSessionFactory才能起作用
       -->
       <bean id="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
           <!--2.3版本之后，默认是true，作用xxxYyy转成了xxx_yyy -->
           <property name="dbColumnUnderline" value="true"></property>
           <!--AUTO(0, "数据库ID自增"), INPUT(1, "用户输入ID")  主键策略，也可以在bean上通过注解-->
           <property name="idType" value="0"></property>
           <!--全局的表前缀策略配置，即默认bean和表的名称对应关系，加上特定的前缀。这样就不用在每个bean上使用注解告知-->
           <property name="tablePrefix" value="tbl_"></property>
       </bean>

       <!--扫描所有的mapper接口-->
       <mybatis-spring:scan base-package="com.suftz.demo.mp.mapper"></mybatis-spring:scan>

       <!--事务管理器-->
       <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
           <property name="dataSource" ref="dataSource"></property>
       </bean>
       <!--配置通知-->
       <tx:advice id="txadvice">
           <!--配置事务参数-->
           <tx:attributes>
               <tx:method name="add*" propagation="REQUIRED" isolation="REPEATABLE_READ" read-only="false" timeout="4"  />
               <tx:method name="get"  read-only="true" />
           </tx:attributes>
       </tx:advice>

       <!--配置切入点和切面-->
       <aop:config>
           <!--配置切入点-->
           <aop:pointcut id="pointcut" expression="execution(* com.suftz.demo.mp.service.*.*(..))" />
           <!--配置切面-->
           <aop:advisor advice-ref="txadvice" pointcut-ref="pointcut" />
       </aop:config>

   </beans>
   ```

2. 插件测试方法

   ```java
   import com.baomidou.mybatisplus.mapper.EntityWrapper;
   import com.baomidou.mybatisplus.plugins.Page;
   import com.suftz.demo.mp.bean.Employee;
   import com.suftz.demo.mp.mapper.EmployeeMapper;
   import org.junit.Test;
   import org.junit.runner.RunWith;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.test.context.ContextConfiguration;
   import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

   import java.util.List;

   @RunWith(SpringJUnit4ClassRunner.class)  //单元测试框架
   @ContextConfiguration(locations = {"classpath:applicationContext.xml"})  //加载配置文件
   public class PluginTest {
       @Autowired
       EmployeeMapper employeeMapper;

       /**
        * 测试分页插件：查看sql是否真的有分页
        * 使用分页插件进行真实的分页查询
        */
       @Test
       public void testPage(){
           Page<Employee> page=new Page<>(2,2);
           List<Employee> list=employeeMapper.selectPage(page,null);
           //SELECT id AS id,last_name AS lastName,email,gender,age FROM tbl_employee LIMIT 2,2
           list.forEach(System.out::println);
           System.out.println("总记录数："+page.getTotal());
           System.out.println("当前页码："+page.getCurrent());
           System.out.println("总页码："+page.getPages());
           System.out.println("每页显示条数："+page.getSize());
           System.out.println("是否有上一页："+page.hasPrevious());
           System.out.println("是否有下一页："+page.hasNext());
       }

       /**
        * 测试SQL执行分析插件
        * 执行全表删除，但是分析插件会拦截该操作
        * 原理：是在执行操作之前向数据库发送explain指令，然后获取数据库的解析结果，得知是否是全表删除
        * 建议开发环境上使用
        */
       @Test
       public void testFullTableDelete(){
           employeeMapper.delete(null);
       }

       /**
        * 测试性能分析插件
        */
       @Test
       public void testPerformance(){
           employeeMapper.insert(new Employee("马超","machao@suftz.com","1",23));

       /**
        * 从打印的信息可以看出有执行时间的统计
        * 23:21:13.760 [main] DEBUG com.suftz.demo.mp.mapper.EmployeeMapper.insert - ==>  Preparing: INSERT INTO tbl_employee ( last_name, email, gender, age ) VALUES ( ?, ?, ?, ? )
        * 23:21:13.780 [main] DEBUG com.suftz.demo.mp.mapper.EmployeeMapper.insert - ==> Parameters: 马超(String), machao@suftz.com(String), 1(String), 23(Integer)
        * 23:21:13.783 [main] DEBUG com.suftz.demo.mp.mapper.EmployeeMapper.insert - <==    Updates: 1
        *  Time：0 ms - ID：com.suftz.demo.mp.mapper.EmployeeMapper.insert
        *  Execute SQL：
        *     INSERT
        *     INTO
        *         tbl_employee
        *         ( last_name, email, gender, age )
        *     VALUES
        *         ( ?, ?, ?, ? )
        *
        */
       }

       /**
        * 测试乐观锁插件的使用
        * 1. 首先需要指定一个字段专门用来维护记录的版本的，比如有个字段version，每次执行update操作时就会更新该字段。该字段对应的属性需要加上@Version注解
        * 2. 而每次update之前，则会从已拿到的version作为更新条件，这样如果从该数据被拿到，和被更新，如果中间还有其他用户更新了数据，
        *    则数据库中该记录version就改变了，即只有当数据从拿到手，到去执行更新期间，没有被其他人更新才能执行更新
        */
       @Test
       public void testOptimisticLockerInterceptor(){
           Employee employee=new Employee();
           employee.setId(16);
           employee.setVersion(1);//现在更新的语句中，where操作会带上version来操作
           employee.setLastName("张飞123");
           employeeMapper.updateById(employee);
           //UPDATE tbl_employee SET last_name=?, version=? WHERE id=? and version=?
           //从语句也可看到，where条件中带了version作为update条件，并且update的字段中多了一个version，且值设置为下一个版本，即当前version值+1
       }
   }
   ```

## 自定义全局操作

### AutoSqlInjector

1. 在 Mapper 接口中定义相关的 CRUD 方法
2. 扩展 AutoSqlInjector inject 方法，实现 Mapper 接口中方法要注入的 SQL
3. 在 MP 全局策略中，配置 自定义注入器

示例：

1. 在EmployeeMapper.java中定义方法

   ```java
   package com.suftz.demo.mp.mapper;

   import com.suftz.demo.mp.bean.Employee;
   import com.baomidou.mybatisplus.mapper.BaseMapper;

   public interface EmployeeMapper extends BaseMapper<Employee> {
       //这是自定义的方法，需要设置自动sql注入，或者在sql映射文件中去定义sql
       public int deleteAll();
   }
   ```

2. 编写扩展的AutoSqlInjector

   ```java
   package com.suftz.demo.mp.injector;

   import com.baomidou.mybatisplus.entity.TableInfo;
   import com.baomidou.mybatisplus.mapper.AutoSqlInjector;
   import org.apache.ibatis.builder.MapperBuilderAssistant;
   import org.apache.ibatis.mapping.SqlSource;
   import org.apache.ibatis.session.Configuration;

   public class MySqlInjector extends AutoSqlInjector {
       @Override
       public void inject(Configuration configuration, MapperBuilderAssistant builderAssistant, Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
           //将EmployeeMapper中定义的deleteALL,处理成对应的MappedStatement对象，加入到configuration
           String sql="delete from "+table.getTableName();
           //注入的方法名
           String method="deleteAll";
           //构造sqlSource对象
           SqlSource sqlSource=languageDriver.createSqlSource(configuration,sql,modelClass);
           //加入到configuration
           this.addDeleteMappedStatement(mapperClass,method,sqlSource);
       }
   }
   ```

3. 需要将扩展的组件加入ioc容器管理，并在全局配置中设置

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xmlns:tx="http://www.springframework.org/schema/tx"
          xmlns:mybatis-spring="http://mybatis.org/schema/mybatis-spring"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd
          http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd
          http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://mybatis.org/schema/mybatis-spring http://mybatis.org/schema/mybatis-spring.xsd">

       <!--
           如果想扫描多个包，可以使用逗号分隔开
           或者写包的上层目录，这样其子包都能被扫描
       -->
       <context:component-scan base-package="com.suftz.demo.mp"></context:component-scan>

       <context:property-placeholder location="classpath:jdbc.properties" />
       <!--配置数据库连接池信息，获取数据源对象-->
       <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
           <property name="driverClassName" value="${prop.driverClass}"></property>
           <property name="url" value="${prop.url}"></property>
           <property name="username" value="${prop.username}"></property>
           <property name="password" value="${prop.password}"></property>
           <property name="minEvictableIdleTimeMillis" value="${prop.minEvictableIdleTimeMillis}"></property>
           <property name="validationQuery" value="${prop.validationQuery}"></property>
       </bean>


       <!--<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">-->
       <!--mybatisplus的配置数据源-->
       <bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean">
           <property name="dataSource" ref="dataSource"></property>
           <property name="typeAliasesPackage"  value="com.suftz.ssmdemo.bean"/><!--配置mapper别名扫描包-->
           <property name="mapperLocations" value="classpath:com/suftz/demo/mp/mapper/*.xml"></property>
           <property name="configLocation" value="classpath:mybatis-config.xml"></property>
           <property name="globalConfig" ref="globalConfiguration"></property>
           <property name="plugins">
               <list>
                   <!--注册分页插件-->
                   <bean class="com.baomidou.mybatisplus.plugins.PaginationInterceptor" />
                   <!--注册执行分析插件:可以拦截全表删除或者更新的操作，建议开发环境使用
                   <bean class="com.baomidou.mybatisplus.plugins.SqlExplainInterceptor">
                       <property name="stopProceed" value="true"></property>
                   </bean>
                   -->

                   <!--注册性能分析插件，建议开发环境使用-->
                   <bean class="com.baomidou.mybatisplus.plugins.PerformanceInterceptor">
                       <property name="format" value="true"></property>
   <!--                    <property name="maxTime" value=""></property>超过最大的执行时间，则会被停止执行，抛异常-->
                   </bean>
                   <!--注册乐观锁插件-->
                   <bean class="com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor" />
               </list>
           </property>
       </bean>

       <!--mybatisplus: 配置可以批量执行的sqlSession-->
       <!--<bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">-->
       <bean id="sqlSessionTemplate" class="com.baomidou.mybatisplus.MybatisSqlSessionTemplate">
           <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory"></constructor-arg>
           <constructor-arg name="executorType" value="BATCH"></constructor-arg>
       </bean>

       <!--
           定义MybatisPlus的全局策略配置
           这个配置需要注入到sqlSessionFactory才能起作用
       -->
       <bean id="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
           <!--2.3版本之后，默认是true，作用xxxYyy转成了xxx_yyy -->
           <property name="dbColumnUnderline" value="true"></property>
           <!--AUTO(0, "数据库ID自增"), INPUT(1, "用户输入ID")  主键策略，也可以在bean上通过注解-->
           <property name="idType" value="0"></property>
           <!--全局的表前缀策略配置，即默认bean和表的名称对应关系，加上特定的前缀。这样就不用在每个bean上使用注解告知-->
           <property name="tablePrefix" value="tbl_"></property>

           <!--注入自定义全局操作-->
           <property name="sqlInjector" ref="mySqlInjector"></property>

       </bean>

       <!--定义自定义注入器-->
       <bean id="mySqlInjector" class="com.suftz.demo.mp.injector.MySqlInjector" />

       <!--扫描所有的mapper接口-->
       <mybatis-spring:scan base-package="com.suftz.demo.mp.mapper"></mybatis-spring:scan>

       <!--事务管理器-->
       <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
           <property name="dataSource" ref="dataSource"></property>
       </bean>
       <!--配置通知-->
       <tx:advice id="txadvice">
           <!--配置事务参数-->
           <tx:attributes>
               <tx:method name="add*" propagation="REQUIRED" isolation="REPEATABLE_READ" read-only="false" timeout="4"  />
               <tx:method name="get"  read-only="true" />
           </tx:attributes>
       </tx:advice>

       <!--配置切入点和切面-->
       <aop:config>
           <!--配置切入点-->
           <aop:pointcut id="pointcut" expression="execution(* com.suftz.demo.mp.service.*.*(..))" />
           <!--配置切面-->
           <aop:advisor advice-ref="txadvice" pointcut-ref="pointcut" />
       </aop:config>

   </beans>
   ```

4. 测试方法

```java
import com.suftz.demo.mp.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  //单元测试框架
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  //加载配置文件
public class GlobalTest {
    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 执行自定义的全局操作
     */
    @Test
    public void testDeleteAll(){
        int rows=employeeMapper.deleteAll();//delete from tbl_employee
        System.out.println(rows);
    }

}
```

### 逻辑删除

假删除、逻辑删除: 并不会真正的从数据库中将数据删除掉，而是将当前被删除的这条数据
中的一个逻辑删除字段置为删除状态，如：

```text
tbl_user logic_flag = 1 → -1
```

1. com.baomidou.mybatisplus.mapper.LogicSqlInjector
2. logicDeleteValue 逻辑删除全局值
3. logicNotDeleteValue 逻辑未删除全局值
4. 在 POJO 的逻辑删除字段 添加 @TableLogic 注解
5. 会在 mp 自带查询和更新方法的 sql 后面，追加『逻辑删除字段』 =『LogicNotDeleteValue默认值』 删除方法: deleteById()和其他 delete 方法, 底层 SQL 调用的是 update tbl_xxxset 『逻辑删除字段』 =『logicDeleteValue 默认值』

示例：

1. 在bean中将维护逻辑删除的字段加上@TableLogic注解

2. 在applicationContext.xml中配置逻辑删除

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xmlns:tx="http://www.springframework.org/schema/tx"
          xmlns:mybatis-spring="http://mybatis.org/schema/mybatis-spring"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd
          http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd
          http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://mybatis.org/schema/mybatis-spring http://mybatis.org/schema/mybatis-spring.xsd">

       <!--
           如果想扫描多个包，可以使用逗号分隔开
           或者写包的上层目录，这样其子包都能被扫描
       -->
       <context:component-scan base-package="com.suftz.demo.mp"></context:component-scan>

       <context:property-placeholder location="classpath:jdbc.properties" />
       <!--配置数据库连接池信息，获取数据源对象-->
       <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
           <property name="driverClassName" value="${prop.driverClass}"></property>
           <property name="url" value="${prop.url}"></property>
           <property name="username" value="${prop.username}"></property>
           <property name="password" value="${prop.password}"></property>
           <property name="minEvictableIdleTimeMillis" value="${prop.minEvictableIdleTimeMillis}"></property>
           <property name="validationQuery" value="${prop.validationQuery}"></property>
       </bean>


       <!--<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">-->
       <!--mybatisplus的配置数据源-->
       <bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean">
           <property name="dataSource" ref="dataSource"></property>
           <property name="typeAliasesPackage"  value="com.suftz.ssmdemo.bean"/><!--配置mapper别名扫描包-->
           <property name="mapperLocations" value="classpath:com/suftz/demo/mp/mapper/*.xml"></property>
           <property name="configLocation" value="classpath:mybatis-config.xml"></property>
           <property name="globalConfig" ref="globalConfiguration"></property>
           <property name="plugins">
               <list>
                   <!--注册分页插件-->
                   <bean class="com.baomidou.mybatisplus.plugins.PaginationInterceptor" />
                   <!--注册执行分析插件:可以拦截全表删除或者更新的操作，建议开发环境使用-->
                   <bean class="com.baomidou.mybatisplus.plugins.SqlExplainInterceptor">
                       <property name="stopProceed" value="true"></property>
                   </bean>
                   <!--注册性能分析插件，建议开发环境使用-->
                   <bean class="com.baomidou.mybatisplus.plugins.PerformanceInterceptor">
                       <property name="format" value="true"></property>
   <!--                    <property name="maxTime" value=""></property>超过最大的执行时间，则会被停止执行，抛异常-->
                   </bean>
                   <!--注册乐观锁插件-->
                   <bean class="com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor" />
               </list>
           </property>
       </bean>

       <!--mybatisplus: 配置可以批量执行的sqlSession-->
       <!--<bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">-->
       <bean id="sqlSessionTemplate" class="com.baomidou.mybatisplus.MybatisSqlSessionTemplate">
           <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory"></constructor-arg>
           <constructor-arg name="executorType" value="BATCH"></constructor-arg>
       </bean>

       <!--
           定义MybatisPlus的全局策略配置
           这个配置需要注入到sqlSessionFactory才能起作用
       -->
       <bean id="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
           <!--2.3版本之后，默认是true，作用xxxYyy转成了xxx_yyy -->
           <property name="dbColumnUnderline" value="true"></property>
           <!--AUTO(0, "数据库ID自增"), INPUT(1, "用户输入ID")  主键策略，也可以在bean上通过注解-->
           <property name="idType" value="0"></property>
           <!--全局的表前缀策略配置，即默认bean和表的名称对应关系，加上特定的前缀。这样就不用在每个bean上使用注解告知-->
           <property name="tablePrefix" value="tbl_"></property>

           <!--注入自定义全局操作
           <property name="sqlInjector" ref="mySqlInjector"></property>
           -->

           <!--注入逻辑删除-->
           <property name="sqlInjector" ref="logicSqlInjector" />
           <!--注入逻辑删除全局值-->
           <property name="logicDeleteValue" value="-1"></property>
           <property name="logicNotDeleteValue" value="1"></property>

       </bean>

       <!--定义自定义注入器-->
       <bean id="mySqlInjector" class="com.suftz.demo.mp.injector.MySqlInjector" />

       <!--逻辑删除-->
       <bean id="logicSqlInjector" class="com.baomidou.mybatisplus.mapper.LogicSqlInjector" />

       <!--扫描所有的mapper接口-->
       <mybatis-spring:scan base-package="com.suftz.demo.mp.mapper"></mybatis-spring:scan>

       <!--事务管理器-->
       <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
           <property name="dataSource" ref="dataSource"></property>
       </bean>
       <!--配置通知-->
       <tx:advice id="txadvice">
           <!--配置事务参数-->
           <tx:attributes>
               <tx:method name="add*" propagation="REQUIRED" isolation="REPEATABLE_READ" read-only="false" timeout="4"  />
               <tx:method name="get"  read-only="true" />
           </tx:attributes>
       </tx:advice>

       <!--配置切入点和切面-->
       <aop:config>
           <!--配置切入点-->
           <aop:pointcut id="pointcut" expression="execution(* com.suftz.demo.mp.service.*.*(..))" />
           <!--配置切面-->
           <aop:advisor advice-ref="txadvice" pointcut-ref="pointcut" />
       </aop:config>

   </beans>
   ```

3. 测试方法：

   ```java
   import com.suftz.demo.mp.mapper.EmployeeMapper;
   import org.junit.Test;
   import org.junit.runner.RunWith;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.test.context.ContextConfiguration;
   import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

   @RunWith(SpringJUnit4ClassRunner.class)  //单元测试框架
   @ContextConfiguration(locations = {"classpath:applicationContext.xml"})  //加载配置文件
   public class GlobalTest {
       @Autowired
       EmployeeMapper employeeMapper;

       /**
        * 测试逻辑删除
        * 并没有真正去删除数据，而是去更新数据，将逻辑删除字段的值 ，置为删除状态-1
        */
       @Test
       public void testLogicDelete(){
           int result=employeeMapper.deleteById(14);//UPDATE tbl_employee SET logic_flag=-1 WHERE id=?
           System.out.println(result);
       }
   }
   ```

## 公共字段自动填充

有些字段在插入数据的时候，没有设置值，但是希望能够在插入或者更新的时候对这些字段填充默认值(当这些字段没有设置值的时候)

com.baomidou.mybatisplus.mapper.MetaObjectHandler
insertFill(MetaObject metaObject)
updateFill(MetaObject metaObject)

* metaobject: 元对象. 是 Mybatis 提供的一个用于更加方便，更加优雅的访问对象的属性,给对象的属性设置值 的一个对象. 还会用于包装对象. 支持对 Object 、 Map、 Collection等对象进行包装
* 本质上 metaObject 获取对象的属性值或者是给对象的属性设置值，最终是要通过 Reflector 获取到属性的对应方法的 Invoker, 最终 invoke.

实现步骤：

1. 注解填充字段 @TableField(fill = FieldFill.INSERT) 查看 FieldFill
2. 自定义公共字段填充处理器
3. MP 全局注入 自定义公共字段填充处理器

示例：

1. 需要在bean中将需要进行公共字段自动填充的属性加上@TableField注解

   ```java
   package com.suftz.demo.mp.bean;

   import com.baomidou.mybatisplus.annotations.*;
   import com.baomidou.mybatisplus.enums.FieldFill;
   import com.baomidou.mybatisplus.enums.IdType;
   import com.baomidou.mybatisplus.annotations.TableId;
   import com.baomidou.mybatisplus.enums.IdType;
   import com.baomidou.mybatisplus.activerecord.Model;

   import java.io.Serializable;

   @TableName("tbl_employee")
   public class Employee extends Model<Employee> {

       private static final long serialVersionUID = 1L;

       @TableId(value = "id", type = IdType.AUTO)
       private Integer id;
       private String lastName;
       @TableField(fill = FieldFill.INSERT)
       private String email;
       private String gender;
       private Integer age;

       @Version
       private Integer version;

       @TableLogic
       private Integer logicFlag;

       public Integer getLogicFlag() {
           return logicFlag;
       }

       public void setLogicFlag(Integer logicFlag) {
           this.logicFlag = logicFlag;
       }

       public Integer getVersion() {
           return version;
       }

       public void setVersion(Integer version) {
           this.version = version;
       }

       public Employee() {
       }

       public Employee(String lastName, String email, String gender, Integer age) {
           this.lastName = lastName;
           this.email = email;
           this.gender = gender;
           this.age = age;
       }

       public Integer getId() {
           return id;
       }

       public void setId(Integer id) {
           this.id = id;
       }

       public String getLastName() {
           return lastName;
       }

       public void setLastName(String lastName) {
           this.lastName = lastName;
       }

       public String getEmail() {
           return email;
       }

       public void setEmail(String email) {
           this.email = email;
       }

       public String getGender() {
           return gender;
       }

       public void setGender(String gender) {
           this.gender = gender;
       }

       public Integer getAge() {
           return age;
       }

       public void setAge(Integer age) {
           this.age = age;
       }

       @Override
       protected Serializable pkVal() {
           return this.id;
       }

       @Override
       public String toString() {
           return "Employee{" +
           " id=" + id +
           ", lastName=" + lastName +
           ", email=" + email +
           ", gender=" + gender +
           ", age=" + age +
           "}";
       }
   }
   ```

2. 实现一个自定义的自动填充字段的对象

   ```java
   package com.suftz.demo.mp.metaObjectHandler;

   import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
   import org.apache.ibatis.reflection.MetaObject;

   public class MyMetaObjectHandler extends MetaObjectHandler {

       @Override
       public void insertFill(MetaObject metaObject) {
           Object fieldValue=getFieldValByName("email",metaObject);
           if(fieldValue==null){
               setFieldValByName("email",getFieldValByName("lastName",metaObject).toString()+"@suftz.com",metaObject);
           }
       }

       @Override
       public void updateFill(MetaObject metaObject) {
           Object fieldValue=getFieldValByName("email",metaObject);
           if(fieldValue==null){
               setFieldValByName("email",getFieldValByName("lastName",metaObject).toString()+"@suftz.com",metaObject);
           }
       }
   }
   ```

3. 在配置文件中注册自定义的填充字段对象

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xmlns:tx="http://www.springframework.org/schema/tx"
          xmlns:mybatis-spring="http://mybatis.org/schema/mybatis-spring"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd
          http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd
          http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://mybatis.org/schema/mybatis-spring http://mybatis.org/schema/mybatis-spring.xsd">

       <!--
           如果想扫描多个包，可以使用逗号分隔开
           或者写包的上层目录，这样其子包都能被扫描
       -->
       <context:component-scan base-package="com.suftz.demo.mp"></context:component-scan>

       <context:property-placeholder location="classpath:jdbc.properties" />
       <!--配置数据库连接池信息，获取数据源对象-->
       <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
           <property name="driverClassName" value="${prop.driverClass}"></property>
           <property name="url" value="${prop.url}"></property>
           <property name="username" value="${prop.username}"></property>
           <property name="password" value="${prop.password}"></property>
           <property name="minEvictableIdleTimeMillis" value="${prop.minEvictableIdleTimeMillis}"></property>
           <property name="validationQuery" value="${prop.validationQuery}"></property>
       </bean>


       <!--<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">-->
       <!--mybatisplus的配置数据源-->
       <bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean">
           <property name="dataSource" ref="dataSource"></property>
           <property name="typeAliasesPackage"  value="com.suftz.ssmdemo.bean"/><!--配置mapper别名扫描包-->
           <property name="mapperLocations" value="classpath:com/suftz/demo/mp/mapper/*.xml"></property>
           <property name="configLocation" value="classpath:mybatis-config.xml"></property>
           <property name="globalConfig" ref="globalConfiguration"></property>
           <property name="plugins">
               <list>
                   <!--注册分页插件-->
                   <bean class="com.baomidou.mybatisplus.plugins.PaginationInterceptor" />
                   <!--注册执行分析插件:可以拦截全表删除或者更新的操作，建议开发环境使用-->
                   <bean class="com.baomidou.mybatisplus.plugins.SqlExplainInterceptor">
                       <property name="stopProceed" value="true"></property>
                   </bean>
                   <!--注册性能分析插件，建议开发环境使用-->
                   <bean class="com.baomidou.mybatisplus.plugins.PerformanceInterceptor">
                       <property name="format" value="true"></property>
   <!--                    <property name="maxTime" value=""></property>超过最大的执行时间，则会被停止执行，抛异常-->
                   </bean>
                   <!--注册乐观锁插件-->
                   <bean class="com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor" />
               </list>
           </property>
       </bean>

       <!--mybatisplus: 配置可以批量执行的sqlSession-->
       <!--<bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">-->
       <bean id="sqlSessionTemplate" class="com.baomidou.mybatisplus.MybatisSqlSessionTemplate">
           <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory"></constructor-arg>
           <constructor-arg name="executorType" value="BATCH"></constructor-arg>
       </bean>

       <!--
           定义MybatisPlus的全局策略配置
           这个配置需要注入到sqlSessionFactory才能起作用
       -->
       <bean id="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
           <!--2.3版本之后，默认是true，作用xxxYyy转成了xxx_yyy -->
           <property name="dbColumnUnderline" value="true"></property>
           <!--AUTO(0, "数据库ID自增"), INPUT(1, "用户输入ID")  主键策略，也可以在bean上通过注解-->
           <property name="idType" value="0"></property>
           <!--全局的表前缀策略配置，即默认bean和表的名称对应关系，加上特定的前缀。这样就不用在每个bean上使用注解告知-->
           <property name="tablePrefix" value="tbl_"></property>

           <!--注入自定义全局操作
           <property name="sqlInjector" ref="mySqlInjector"></property>
           -->

           <!--注入逻辑删除-->
           <property name="sqlInjector" ref="logicSqlInjector" />
           <!--注入逻辑删除全局值-->
           <property name="logicDeleteValue" value="-1"></property>
           <property name="logicNotDeleteValue" value="1"></property>

           <!--注入公共字段填充处理器-->
           <property name="metaObjectHandler" ref="myMetaObjectHandler"></property>
       </bean>

       <!--定义自定义注入器-->
       <bean id="mySqlInjector" class="com.suftz.demo.mp.injector.MySqlInjector" />

       <!--逻辑删除-->
       <bean id="logicSqlInjector" class="com.baomidou.mybatisplus.mapper.LogicSqlInjector" />

       <!--公共字段填充处理器-->
       <bean id="myMetaObjectHandler" class="com.suftz.demo.mp.metaObjectHandler.MyMetaObjectHandler" />

       <!--扫描所有的mapper接口-->
       <mybatis-spring:scan base-package="com.suftz.demo.mp.mapper"></mybatis-spring:scan>

       <!--事务管理器-->
       <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
           <property name="dataSource" ref="dataSource"></property>
       </bean>
       <!--配置通知-->
       <tx:advice id="txadvice">
           <!--配置事务参数-->
           <tx:attributes>
               <tx:method name="add*" propagation="REQUIRED" isolation="REPEATABLE_READ" read-only="false" timeout="4"  />
               <tx:method name="get"  read-only="true" />
           </tx:attributes>
       </tx:advice>

       <!--配置切入点和切面-->
       <aop:config>
           <!--配置切入点-->
           <aop:pointcut id="pointcut" expression="execution(* com.suftz.demo.mp.service.*.*(..))" />
           <!--配置切面-->
           <aop:advisor advice-ref="txadvice" pointcut-ref="pointcut" />
       </aop:config>

   </beans>
   ```

4. 测试方法

   ```java
   import com.suftz.demo.mp.bean.Employee;
   import com.suftz.demo.mp.mapper.EmployeeMapper;
   import org.junit.Test;
   import org.junit.runner.RunWith;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.test.context.ContextConfiguration;
   import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

   @RunWith(SpringJUnit4ClassRunner.class)  //单元测试框架
   @ContextConfiguration(locations = {"classpath:applicationContext.xml"})  //加载配置文件
   public class GlobalTest {
       @Autowired
       EmployeeMapper employeeMapper;

       /**
        * 测试公共字段填充
        */
       @Test
       public void testMetaObjectHandler(){
           Employee employee=new Employee();
           employee.setLastName("赵云");
           employee.setAge(23);
           employee.setGender("1");
           employeeMapper.insert(employee);
           //INSERT INTO tbl_employee ( last_name,email, gender, age ) VALUES ( ?,?, ?, ? )
           //sql会添加email字段，并设置为默认值
           System.out.println(employee);

           //如果待插入的记录有对默认字段设置值，则不会再进行公共字段填充

       }
   }
   ```

## Oracle主键Sequence

MySQL: 支持主键自增。 IdType.Auto
Oracle: 序列(Sequence)

> 序列的常用操作：
>
> 1. 创建序列
>    `create sequence seq_user;`
> 2. 查询序列的下一个值（该操作会使得序列加一）
>    `select seq_user.nextval from dual;`
> 3. 查询序列的当前值
>    `select seq_user.currval from dual;`

1. 实体类配置主键 Sequence @KeySequence(value=”序列名”， clazz=xxx.class 主键属性类型)

   ```java
   @KeySequence(value="seq_user",clazz=Integer.class)
   public class User{
       @TableId(type=IdType.INPUT)
       private Integer id;
   }
   ```

2. 全局 MP 主键生成策略为 IdType.INPUT（也可以只在类上进行注解配置，如上）

3. 全局 MP 中配置 Oracle 主键 Sequence
   `com.baomidou.mybatisplus.incrementer.OracleKeyGenerator`

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xmlns:tx="http://www.springframework.org/schema/tx"
          xmlns:mybatis-spring="http://mybatis.org/schema/mybatis-spring"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd
          http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd
          http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://mybatis.org/schema/mybatis-spring http://mybatis.org/schema/mybatis-spring.xsd">

       <!--
           如果想扫描多个包，可以使用逗号分隔开
           或者写包的上层目录，这样其子包都能被扫描
       -->
       <context:component-scan base-package="com.suftz.demo.mp"></context:component-scan>

       <context:property-placeholder location="classpath:jdbc.properties" />
       <!--配置数据库连接池信息，获取数据源对象-->
       <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
           <property name="driverClassName" value="${prop.driverClass}"></property>
           <property name="url" value="${prop.url}"></property>
           <property name="username" value="${prop.username}"></property>
           <property name="password" value="${prop.password}"></property>
           <property name="minEvictableIdleTimeMillis" value="${prop.minEvictableIdleTimeMillis}"></property>
           <property name="validationQuery" value="${prop.validationQuery}"></property>
       </bean>


       <!--<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">-->
       <!--mybatisplus的配置数据源-->
       <bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean">
           <property name="dataSource" ref="dataSource"></property>
           <property name="typeAliasesPackage"  value="com.suftz.ssmdemo.bean"/><!--配置mapper别名扫描包-->
           <property name="mapperLocations" value="classpath:com/suftz/demo/mp/mapper/*.xml"></property>
           <property name="configLocation" value="classpath:mybatis-config.xml"></property>
           <property name="globalConfig" ref="globalConfiguration"></property>
           <property name="plugins">
               <list>
                   <!--注册分页插件-->
                   <bean class="com.baomidou.mybatisplus.plugins.PaginationInterceptor" />
                   <!--注册执行分析插件:可以拦截全表删除或者更新的操作，建议开发环境使用-->
                   <bean class="com.baomidou.mybatisplus.plugins.SqlExplainInterceptor">
                       <property name="stopProceed" value="true"></property>
                   </bean>
                   <!--注册性能分析插件，建议开发环境使用-->
                   <bean class="com.baomidou.mybatisplus.plugins.PerformanceInterceptor">
                       <property name="format" value="true"></property>
   <!--                    <property name="maxTime" value=""></property>超过最大的执行时间，则会被停止执行，抛异常-->
                   </bean>
                   <!--注册乐观锁插件-->
                   <bean class="com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor" />
               </list>
           </property>
       </bean>

       <!--mybatisplus: 配置可以批量执行的sqlSession-->
       <!--<bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">-->
       <bean id="sqlSessionTemplate" class="com.baomidou.mybatisplus.MybatisSqlSessionTemplate">
           <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory"></constructor-arg>
           <constructor-arg name="executorType" value="BATCH"></constructor-arg>
       </bean>

       <!--
           定义MybatisPlus的全局策略配置
           这个配置需要注入到sqlSessionFactory才能起作用
       -->
       <bean id="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
           <!--2.3版本之后，默认是true，作用xxxYyy转成了xxx_yyy -->
           <property name="dbColumnUnderline" value="true"></property>
           <!--AUTO(0, "数据库ID自增"), INPUT(1, "用户输入ID")  主键策略，也可以在bean上通过注解-->
           <property name="idType" value="0"></property>
           <!--全局的表前缀策略配置，即默认bean和表的名称对应关系，加上特定的前缀。这样就不用在每个bean上使用注解告知-->
           <property name="tablePrefix" value="tbl_"></property>

           <!--注入自定义全局操作
           <property name="sqlInjector" ref="mySqlInjector"></property>
           -->

           <!--注入逻辑删除-->
           <property name="sqlInjector" ref="logicSqlInjector" />
           <!--注入逻辑删除全局值-->
           <property name="logicDeleteValue" value="-1"></property>
           <property name="logicNotDeleteValue" value="1"></property>

           <!--注入公共字段填充处理器-->
           <property name="metaObjectHandler" ref="myMetaObjectHandler"></property>

           <!--注册oracle主键生成器-->
           <property name="keyGenerator" ref="oracleKeyGenerator" />
       </bean>

       <!--定义自定义注入器-->
       <bean id="mySqlInjector" class="com.suftz.demo.mp.injector.MySqlInjector" />

       <!--逻辑删除-->
       <bean id="logicSqlInjector" class="com.baomidou.mybatisplus.mapper.LogicSqlInjector" />

       <!--公共字段填充处理器-->
       <bean id="myMetaObjectHandler" class="com.suftz.demo.mp.metaObjectHandler.MyMetaObjectHandler" />

       <!--oracle注解生成器-->
       <bean id="oracleKeyGenerator" class="com.baomidou.mybatisplus.incrementer.OracleKeyGenerator" />

       <!--扫描所有的mapper接口-->
       <mybatis-spring:scan base-package="com.suftz.demo.mp.mapper"></mybatis-spring:scan>

       <!--事务管理器-->
       <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
           <property name="dataSource" ref="dataSource"></property>
       </bean>
       <!--配置通知-->
       <tx:advice id="txadvice">
           <!--配置事务参数-->
           <tx:attributes>
               <tx:method name="add*" propagation="REQUIRED" isolation="REPEATABLE_READ" read-only="false" timeout="4"  />
               <tx:method name="get"  read-only="true" />
           </tx:attributes>
       </tx:advice>

       <!--配置切入点和切面-->
       <aop:config>
           <!--配置切入点-->
           <aop:pointcut id="pointcut" expression="execution(* com.suftz.demo.mp.service.*.*(..))" />
           <!--配置切面-->
           <aop:advisor advice-ref="txadvice" pointcut-ref="pointcut" />
       </aop:config>

   </beans>
   ```

4. 可以将@keySequence 定义在父类中， 可实现多个子类对应的多个表公用一个 Sequence

## Idea 快速开发插件

MybatisX 辅助 idea 快速开发插件，为效率而生.
可以实现 java 与 xml 跳转，根据 Mapper 接口中的方法自动生成 xml 结构.
官方安装： File -> Settings -> Plugins -> Browse Repositories.. 输入 mybatisx 安装下载
Jar 安装： File -> Settings -> Plugins -> Install plugin from disk.. 选中 mybatisx..jar 安装
