# 通用Mapper

[TOC]

## 通用Mapper介绍

先看作者的原话：
> 我个人最早用 MyBatis 时，先是完全手写，然后用上了 MyBatis 代码生成器（简称为 MBG），在使用 MBG 过程中，发现一个很麻烦的问题，如果数据库字段变化很频繁，就需要反复重新生成代码，并且由于 MBG 覆盖生成代码和追加方式生成 XML，导致每次重新生成都需要大量的比对修改。除了这个问题外，还有一个问题，仅仅基础的增删改查等方法，就已经产生了大量的 XML 内容，还没有添加一个自己手写的方法，代码可能就已经几百行了，内容多，看着比较碍事。
> 因为很多人都在使用 MBG，MBG 中定义了很多常用的单表方法，为了解决前面提到的问题，也为了兼容 MBG 的方法避免项目重构太多，在 MBG 的基础上结合了部分 JPA 注解产生了通用 Mapper。通用 Mapper 可以很简单的让你获取基础的单表方法，也很方便扩展通用方法。使用通用 Mapper 可以极大的提高你的工作效率。

通用Mapper和pageHelper是同一个作者，简而言之，因为MyBatis需要自己写sql，而且当POJO的字段进行修改后，相应的sql映射语句都需要发生变化，如果使用MBG,也只是帮助第一次由数据库表逆向解析出Java代码和sql映射文件，之后如果想要做什么事情，还得自己去修改好几个地方

## Spring+mybatis+mapper

此整合是基于mybatis基础中，已整合的spring+mybatis,不再赘述它们的整合过程。下列步骤是需要额外进行的：

1. 导入mapper依赖：

   ```java
   <dependency>
      <groupId>tk.mybatis</groupId>
      <artifactId>mapper</artifactId>
      <version>4.1.5</version>
   </dependency>
   ```

2. 定义数据的增删改查接口，此接口继承自Mapper接口

   ```java
   package com.suftz.mapperdemo.mapper;

   import com.suftz.mapperdemo.bean.User;
   import tk.mybatis.mapper.common.Mapper;

   public interface UserMapper extends Mapper<User> {
        //自定义的查询方法，在这里定义之后，还需要在sql映射文件中去写相应sql语句
        public User getUserAndDept(Integer uid);
   }
   ```

   之所以方法定义为空(只有一个自定义方法)，是因为这个Maper接口已继承了很多其他接口，而这些接口又定义了许多增删改查的方法，所有不用再定义。当然，如果这些方法都不能满足项目需求，可以自己定义，然后需要去设置sql映射文件里的sql相关的标签(这个过程就还是和以前的mybatis一致)。但是，如果是继承自Mapper接口的方法，则不需要再自己去编写sql映射文件的相关语句，这些查询所需要的语句都是mapper自动生成的

3. 修改扫描所有mapper接口的配置。这和之前已整合好的ssm项目，有所不同，原因就是：之前的接口以及接口方法是自己定义的，然后自己还要在sql映射文件中去编写方法所用到的sql语句。而现在如果只是使用Mapper接口提供的方法，则不需要去sql映射文件中写sql语句，直接可以使用

   ```xml
   <!--下面两种方式在使用Mapper接口提供的方法时，会报异常，无法执行，所以需要更换扫描-->
   <!--<mybatis-spring:scan base-package="com.suftz.mapperdemo.mapper" ></mybatis-spring:scan>
   <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
       <property name="basePackage" value="com.suftz.mapperdemo.mapper"></property>
   </bean>
   -->
   <!--使用下列配置-->
   <bean class="tk.mybatis.spring.mapper.MapperScannerConfigurer">
       <property name="basePackage" value="com.suftz.mapperdemo.mapper"></property>
   </bean>
   ```

4. 测试示例：

   ```java
   package com.suftz.mapperdemo.mapper;

   import com.suftz.mapperdemo.bean.User;
   import org.junit.Test;
   import org.junit.runner.RunWith;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.test.context.ContextConfiguration;
   import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
   import tk.mybatis.mapper.entity.Example;

   @RunWith(SpringJUnit4ClassRunner.class)  //单元测试框架
   @ContextConfiguration(locations = {"classpath:beans.xml"})  //加载配置文件
   public class UserMapperTest {

       @Autowired
       UserMapper userMapper;

       @Test
       public void deleteByPrimaryKey() {
       }

       @Test
       public void delete() {
           //该方法实际开发中禁止使用，会删除所有的数据
           //userMapper.delete(new User());一旦没有传where条件就会删除所有的数据
       }

       @Test
       public void insert() {
       }

       @Test
       public void insertSelective() {
       }

       @Test
       public void existsWithPrimaryKey() {
       }

       @Test
       public void selectAll() {
           userMapper.selectAll().forEach(System.out::println);
       }

       @Test
       public void selectByPrimaryKey() {
           /**
           * 这里的前提是User类使用了@Id注解来标识了主键，否则mapper认为全都是主键一部分，即联合注解，
           * 即where 条件里面不止会有uid=xxx,还有其他的属性name=null,age=null等等，这明显是错误的，所以要指定主键对应的属性
           */
           User user =new User();
           user.setUid(101);
           System.out.println(userMapper.selectByPrimaryKey(user));
       }

       @Test
       public void selectCount() {
   //        System.out.println(userMapper.selectCount());
       }

       @Test
       public void select() {
       }

       @Test
       public void selectOne() {
           User user =new User();
           user.setUid(101);
           System.out.println(userMapper.selectOne(user));
       }

       @Test
       public void updateByPrimaryKey() {
       }

       @Test
       public void updateByPrimaryKeySelective() {
           User user=new User();
           user.setUid(113);
           user.setAddress("杭州");
           userMapper.updateByPrimaryKeySelective(user);
       }

       @Test
       public void deleteByExample() {
       }

       @Test
       public void selectByExample() {
           Example example = new Example(User.class);
           Example.Criteria criteria = example.createCriteria();
           Example.Criteria criteria2 = example.createCriteria();
           criteria.andGreaterThan("age", 30).andLessThan("uid", 115);  // age>30 and uid<115
           criteria2.andGreaterThan("uid", 115).andLessThan("age", "30");//  uid>115 and age<30
           example.or(criteria2);


           example.orderBy("uid").asc().orderBy("name").desc();
           example.setDistinct(true);
           example.selectProperties("uid","name","address","age");

           //select * from user where (age>30 and uid<115) or (uid>115 and age<30)
           userMapper.selectByExample(example).forEach(System.out::println);
       }

       @Test
       public void selectCountByExample() {
       }

       @Test
       public void selectOneByExample() {
       }

       @Test
       public void updateByExample() {
       }

       @Test
       public void updateByExampleSelective() {
       }

       @Test
       public void selectByExampleAndRowBounds() {
       }

       @Test
       public void selectByRowBounds() {
       }
       //以上全部都是Mapper提供的方法

       /**
        * 这是在接口中自定义的
        */
       @Test
       public void getUserAndDept(){
           System.out.println(userMapper.getUserAndDept(101));
       }
   }
   ```

## 逆向工程

使用Maven来执行MBG

1. Maven配置文件pom.xml如下：

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>

       <groupId>com.suftz.mapperdemo</groupId>
       <artifactId>mapper-demo</artifactId>
       <version>1.0-SNAPSHOT</version>

       <properties>
           <!--java代码生成位置-->
           <targetJavaProject>${basedir}/src/main/java</targetJavaProject>
           <!--接口放的位置-->
           <targetMapperPackage>com.suftz.mapperdemo.generation.mappers</targetMapperPackage>
           <!--bean放的位置-->
           <targetModelPackage>com.suftz.mapperdemo.generation.bean</targetModelPackage>
           <!--资源目录-->
           <targetResourceProject>${basedir}/src/main/resources</targetResourceProject>
           <!--sql映射文件放的位置-->
           <targetXMLPackage>mappers</targetXMLPackage>
           <!--mapper版本-->
           <mapper.version>4.1.5</mapper.version>
           <!--mysql驱动版本-->
   <!--        <mysql.version>5.</mysql.version>-->
       </properties>

       <build>
           <plugins>
               <plugin>
                   <groupId>org.mybatis.generator</groupId>
                   <artifactId>mybatis-generator-maven-plugin</artifactId>
                   <version>1.3.2</version>
                   <configuration>
                       <configurationFile>${basedir}/src/main/resources/mbg/generatorConfig.xml</configurationFile>
                       <overwrite>true</overwrite>
                       <verbose>true</verbose>
                   </configuration>
                   <dependencies>
                       <dependency>
                           <groupId>mysql</groupId>
                           <artifactId>mysql-connector-java</artifactId>
                           <version>5.1.48</version>
                       </dependency>
                       <dependency>
                           <groupId>tk.mybatis</groupId>
                           <artifactId>mapper</artifactId>
                           <version>${mapper.version}</version>
                       </dependency>
                   </dependencies>
               </plugin>
           </plugins>
       </build>


       <dependencies>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-test</artifactId>
               <version>4.0.0.RELEASE</version>
           </dependency>

           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-context</artifactId>
               <version>4.0.0.RELEASE</version>
           </dependency>
           <dependency>
               <groupId>commons-logging</groupId>
               <artifactId>commons-logging</artifactId>
               <version>1.1.1</version>
           </dependency>

           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <version>5.1.48</version>
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
               <version>4.0.0.RELEASE</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-tx</artifactId>
               <version>4.0.0.RELEASE</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-orm</artifactId>
               <version>4.0.0.RELEASE</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-core</artifactId>
               <version>4.0.6.RELEASE</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-web</artifactId>
               <version>4.0.0.RELEASE</version>
           </dependency>

           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-webmvc</artifactId>
               <version>4.0.0.RELEASE</version>
           </dependency>

   <!--        <dependency>-->
   <!--            <groupId>org.junit.jupiter</groupId>-->
   <!--            <artifactId>junit-jupiter</artifactId>-->
   <!--            <version>RELEASE</version>-->
   <!--            <scope>test</scope>-->
   <!--        </dependency>-->
   <!--        <dependency>-->
   <!--            <groupId>org.testng</groupId>-->
   <!--            <artifactId>testng</artifactId>-->
   <!--            <version>RELEASE</version>-->
   <!--            <scope>compile</scope>-->
   <!--        </dependency>-->

   <!--        <dependency>-->
   <!--            <groupId>org.hibernate</groupId>-->
   <!--            <artifactId>hibernate-validator</artifactId>-->
   <!--            <version>5.1.3.Final</version>-->
   <!--        </dependency>-->
   <!--        <dependency>-->
   <!--            <groupId>javax.validation</groupId>-->
   <!--            <artifactId>validation-api</artifactId>-->
   <!--            <version>1.1.0.Final</version>-->
   <!--        </dependency>-->
   <!--        <dependency>-->
   <!--            <groupId>org.jboss.logging</groupId>-->
   <!--            <artifactId>jboss-logging</artifactId>-->
   <!--            <version>3.1.1.GA</version>-->
   <!--        </dependency>-->
   <!--        <dependency>-->
   <!--            <groupId>org.apache.taglibs</groupId>-->
   <!--            <artifactId>taglibs-standard-spec</artifactId>-->
   <!--            <version>1.2.1</version>-->
   <!--        </dependency>-->
   <!--        <dependency>-->
   <!--            <groupId>org.apache.taglibs</groupId>-->
   <!--            <artifactId>taglibs-standard-impl</artifactId>-->
   <!--            <version>1.2.1</version>-->
   <!--        </dependency>-->

   <!--        <dependency>-->
   <!--            <groupId>com.fasterxml.jackson.core</groupId>-->
   <!--            <artifactId>jackson-databind</artifactId>-->
   <!--            <version>2.5.4</version>-->
   <!--        </dependency>-->

   <!--        <dependency>-->
   <!--            <groupId>commons-fileupload</groupId>-->
   <!--            <artifactId>commons-fileupload</artifactId>-->
   <!--            <version>1.4</version>-->
   <!--        </dependency>-->
   <!--        <dependency>-->
   <!--            <groupId>commons-io</groupId>-->
   <!--            <artifactId>commons-io</artifactId>-->
   <!--            <version>2.2</version>-->
   <!--        </dependency>-->



           <!--MyBatis本身需要的依赖，有这个就能够使用MyBatis了-->
           <dependency>
               <groupId>org.mybatis</groupId>
               <artifactId>mybatis</artifactId>
               <version>3.4.1</version>
           </dependency>
           <dependency>
               <groupId>org.mybatis</groupId>
               <artifactId>mybatis-spring</artifactId>
               <version>1.3.0</version>
           </dependency>
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

           <dependency>
               <groupId>net.sf.ehcache</groupId>
               <artifactId>ehcache-core</artifactId>
               <version>2.6.8</version>
           </dependency>
           <dependency>
               <groupId>org.mybatis</groupId>
               <artifactId>mybatis-ehcache</artifactId>
               <version>1.0.0</version>
           </dependency>

           <!--单元测试JUnit4-->
           <dependency>
               <groupId>junit</groupId>
               <artifactId>junit</artifactId>
               <version>4.12</version>
               <scope>test</scope>
           </dependency>

           <dependency>
               <groupId>tk.mybatis</groupId>
               <artifactId>mapper</artifactId>
               <version>4.1.5</version>
           </dependency>
       </dependencies>

   </project>
   ```

2. 编写generatorConfig.xml配置文件

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE generatorConfiguration
           PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
           "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
   <generatorConfiguration>

       <properties resource="mbg/config.properties" />

       <!--
           targetRuntime="MyBatis3Simple":生成简单版的CRUD
           MyBatis3:豪华版

        -->
       <context id="DB2Tables" targetRuntime="MyBatis3" defaultModelType="flat">
           <property name="beginningDelimiter" value="`" />
           <property name="endingDelimiter" value="`" />
           <!--配置通用Mapper的MBG的创建相关信息-->
           <plugin type="${mapper.plugin}">
               <property name="mappers" value="${mapper.Mapper}" />
           </plugin>
           <!-- jdbcConnection：指定如何连接到目标数据库 -->
           <!--mybatis-mbg+mapper的逆向工程，在maven中使用插件执行，一定要降低jdbc版本，不然url怎么写都是报错(&,&amp;都是错的)-->
           <jdbcConnection driverClass="${jdbc.driverClass}"
                           connectionURL="${jdbc.url}"
                           userId="${jdbc.user}"
                           password="${jdbc.password}">
           </jdbcConnection>

           <!--  -->
           <javaTypeResolver >
               <property name="forceBigDecimals" value="false" />
           </javaTypeResolver>

           <!-- javaModelGenerator：指定javaBean的生成策略
           targetPackage="test.model"：目标包名
           targetProject="\MBGTestProject\src"：目标工程
           -->
           <javaModelGenerator targetPackage="${targetModelPackage}" targetProject="${targetJavaProject}">
               <property name="enableSubPackages" value="true" />
               <property name="trimStrings" value="true" />
           </javaModelGenerator>

           <!-- sqlMapGenerator：sql映射生成策略： -->
           <sqlMapGenerator targetPackage="${targetXMLPackage}"
                            targetProject="${targetResourcesProject}">
               <property name="enableSubPackages" value="true" />
           </sqlMapGenerator>

           <!-- javaClientGenerator:指定mapper接口所在的位置 -->
           <javaClientGenerator type="XMLMAPPER" targetPackage="${targetMapperPackage}"
                                targetProject="${targetJavaProject}">
               <property name="enableSubPackages" value="true" />
           </javaClientGenerator>

           <!-- 指定要逆向分析哪些表：根据表要创建javaBean -->
           <table schema="mybatis" tableName="department" domainObjectName="Department"></table>
           <table schema="mybatis" tableName="user" domainObjectName="User"></table>
       </context>
   </generatorConfiguration>
   ```

3. config.properties

   ```properties
   jdbc.driverClass=com.mysql.jdbc.Driver
   jdbc.url=jdbc:mysql://localhost:3306/mapper?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai
   jdbc.user=root
   jdbc.password=booksys123

   prop.minEvictableIdleTimeMillis=300000
   prop.validationQuery=SELECT 1

   mapper.plugin=tk.mybatis.mapper.generator.MapperPlugin
   mapper.Mapper=tk.mybatis.mapper.common.Mapper
   ```

在 pom.xml 这一级目录的命令行窗口执行 mvn mybatis-generator:generate即可（前提是配置了mvn）。

## 注意

1. 一些注解的使用

```java
package com.suftz.mapperdemo.bean;

import com.suftz.mapperdemo.typehandlers.DepartmentTypeHandler;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.*;

//@Alias(value = "user")
//@Table(name="table_user")
public class User {
    /**
     * 默认支持，数据库表字段：user_uid；Java类中的属性则为userUid
     * 默认支持，数据库表字段：uid；Java类中的属性则为uid
     * @Column(name="user_uid")使用该方式指定该属性对应数据库表哪个字段
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //作用是插入数据后，回显自增主键或者基于序列的主键值
    //如果是oracle，则
    //@GeneratedValue(strategy= GenerationType.IDENTITY,generator="select SEQ_ID.nextval from dual")
    private Integer uid;
    private String name;
    private String password;
    private Integer age;
    private String email;
    private String address;

    //表示该属性不是数据库表的字段
    @Transient
    private String birthday;

    @Column(name="dept_id")
    @ColumnType(typeHandler = DepartmentTypeHandler.class)
    private Department dept;

    public User() {
    }

    public User(String name, String password, Integer age, String email, String address) {
        this.name = name;
        this.password = password;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public User(String name, String password, Integer age, String email, String address, Department dept) {
        this.name = name;
        this.password = password;
        this.age = age;
        this.email = email;
        this.address = address;
        this.dept = dept;
    }

    public User(Integer uid, String name, String password, Integer age, String email, String address) {
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public User(Integer uid, String name, String password, Integer age, String email, String address, Department dept) {
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.age = age;
        this.email = email;
        this.address = address;
        this.dept = dept;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
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

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", dpt=" + dept +
                '}';
    }
}

```