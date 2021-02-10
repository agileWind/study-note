# Spring Data

[TOC]

## Spring Data概述

* Spring Data : Spring 的一个子项目。用于简化数据库访问，支持NoSQL 和 关系数据存储。其主要目标是使数据库的访问变得方便快捷。
* SpringData 项目所支持 NoSQL 存储：
  * MongoDB （文档数据库）
  * Neo4j（图形数据库）
  * Redis（键/值存储）
  * Hbase（列族数据库）
* SpringData 项目所支持的关系数据存储技术：
  * JDBC
  * JPA

* JPA Spring Data : 致力于减少数据访问层 (DAO) 的开发量. 开发者唯一要做的，就只是声明持久层的接口，其他都交给 Spring Data JPA 来帮开发者完成！
框架怎么可能代替开发者实现业务逻辑呢？比如：当有一个 `UserDao.findUserById()`  这样一个方法声明，大致应该能判断出这是根据给定条件的 ID 查询出满足条件的 User  对象。Spring Data JPA 做的便是规范方法的名字，根据符合规范的名字来确定方法需要实现什么样的逻辑。

## 第一个程序

使用 Spring Data JPA 进行持久层开发需要的四个步骤：

* 配置 Spring 整合 JPA
* 在 Spring 配置文件中配置 Spring Data，让 Spring 为声明的接口创建代理对象。配置了 `<jpa:repositories>` 后，Spring 初始化容器时将会扫描 base-package  指定的包目录及其子目录，为继承 Repository 或其子接口的接口创建代理对象，并将代理对象注册为 Spring Bean，业务层便可以通过 Spring 自动封装的特性来直接使用该对象。
* 声明持久层的接口，该接口继承  Repository，Repository 是一个标记型接口，它不包含任何方法，如必要，Spring Data 可实现 Repository 其他子接口，其中定义了一些常用的增删改查，以及分页相关的方法。
* 在接口中声明需要的方法。Spring Data 将根据给定的策略（具体策略稍后讲解）来为其生成实现代码。

即只需要在Spring+JPA项目上添加扫描Repository的实现类的标签，另外再编写bean对应的Repository接口，然后在接口中定义需要的方法

1. 导入依赖

     ```xml
     <dependency>
         <groupId>org.springframework.data</groupId>
         <artifactId>spring-data-commons</artifactId>
         <version>2.3.4.RELEASE</version>
     </dependency>

     <dependency>
         <groupId>org.springframework.data</groupId>
         <artifactId>spring-data-jpa</artifactId>
         <version>2.3.4.RELEASE</version>
     </dependency>
     ```

2. applicationContext.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xmlns:tx="http://www.springframework.org/schema/tx"
          xmlns:jpa="http://www.springframework.org/schema/data/jpa"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd
          http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd
          http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
          http://www.springframework.org/schema/data/jpa http://www.springframework.org/schema/data/jpa/spring-jpa-1.3.xsd">

       <!--
           如果想扫描多个包，可以使用逗号分隔开
           或者写包的上层目录，这样其子包都能被扫描
       -->
       <context:component-scan base-package="com.suftz.demo.springdata"/>
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

       <!--配置EntityManagerFactory-->
       <bean id="entityManagerFactory"
             class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
           <property name="dataSource" ref="dataSource"></property>
           <property name="jpaVendorAdapter">
               <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"></bean>
           </property>
           <property name="packagesToScan" value="com.suftz.demo.springdata.bean"></property>
           <property name="jpaProperties">
               <props>
                   <!-- 二级缓存相关 -->

   <!--                <prop key="hibernate.cache.use_query_cache">true</prop>-->
   <!--                <prop key="hibernate.cache.use_second_level_cache">true</prop>-->
   <!--                <prop key="hibernate.generate_statistics">true</prop>-->
   <!--                <prop key="hibernate.cache.region.factory_class">org.hibernate.cache.ehcache.internal.SingletonEhcacheRegionFactory</prop>-->
   <!--                <prop key="javax.persistence.sharedCache.mode">ENABLE_SELECTIVE</prop>-->

                   <!-- 生成的数据表的列的映射策略 -->
                   <prop key="hibernate.ejb.naming_strategy">org.hibernate.cfg.ImprovedNamingStrategy</prop>
                   <!-- hibernate 基本属性 -->
                   <prop key="hibernate.dialect">org.hibernate.dialect.MySQL5InnoDBDialect</prop>
                   <prop key="hibernate.show_sql">true</prop>
                   <prop key="hibernate.format_sql">true</prop>
                   <prop key="hibernate.hbm2ddl.auto">update</prop>
               </props>
           </property>
       </bean>

       <!--配置SpringData,加入jpa的命名空间-->
       <!--base-package:扫描Repository Bean所在的package-->
       <jpa:repositories base-package="com.suftz.demo.springdata.bean" entity-manager-factory-ref="entityManagerFactory"></jpa:repositories>


       <!--事务管理器-->
       <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
           <property name="entityManagerFactory" ref="entityManagerFactory"></property>
       </bean>
       <!--配置通知-->
       <tx:advice id="txadvice">
           <!--配置事务参数-->
           <tx:attributes>
               <tx:method name="add*" propagation="REQUIRED" isolation="REPEATABLE_READ" read-only="false" timeout="4"  />
               <tx:method name="save*" propagation="REQUIRED" isolation="REPEATABLE_READ" read-only="false" timeout="4"  />
               <tx:method name="get"  read-only="true" />
           </tx:attributes>
       </tx:advice>

       <!--配置切入点和切面-->
       <aop:config>
           <!--配置切入点-->
           <aop:pointcut id="pointcut" expression="execution(* com.suftz.demo.springdata.service.*.*(..))" />
           <!--配置切面-->
           <aop:advisor advice-ref="txadvice" pointcut-ref="pointcut" />
       </aop:config>

   </beans>
   ```

3. 编写User类对应的UserRepository接口

   ```java
   package com.suftz.demo.springdata.bean;


   import org.springframework.data.repository.Repository;

   public interface UserRepository extends Repository<User,Integer> {
       User getByName(String name);
   }
   ```

4. 测试方法

   ```java
   import com.suftz.demo.springdata.bean.User;
   import com.suftz.demo.springdata.bean.UserRepository;
   import com.suftz.demo.springdata.service.UserService;
   import org.junit.Test;
   import org.junit.runner.RunWith;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.test.context.ContextConfiguration;
   import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

   @RunWith(SpringJUnit4ClassRunner.class)  //单元测试框架
   @ContextConfiguration(locations = {"classpath:applicationContext.xml"})  //加载配置文件
   public class UserServiceTest {
       @Autowired
       UserService userService;

       @Autowired
       UserRepository userRepository;

       @Test
       public void testSavePersion(){
           User user=new User("刘备","益州",45,"liubei@suftz.com");
           User user2=new User("关羽","运城",30,"guanyu@suftz.com");
           System.out.println(userService.getClass().getName());//代理对象：com.suftz.demo.shj.service.UserService$$EnhancerBySpringCGLIB$$9524a5b2
           userService.savePerson(user,user2);
       }

       @Test
       public void testSpringData(){
           System.out.println(userRepository.getByName("关羽"));
       }
   }
   ```

## Repository接口

* Repository 接口是 Spring Data 的一个核心接口，它不提供任何方法，开发者需要在自己定义的接口中声明需要的方法 
    `public interface Repository<T, ID extends Serializable> { }`
* Spring Data可以让我们只定义接口，只要遵循 Spring Data的规范，就无需写实现类。
* 与继承 Repository 等价的一种方式，就是在持久层接口上使用 @RepositoryDefinition 注解，并为其指定 domainClass 和 idClass 属性。如下两种方式是完全等价的

* 基础的 Repository 提供了最基本的数据访问功能，其几个子接口则扩展了一些功能。它们的继承关系如下：
  * Repository： 仅仅是一个标识，表明任何继承它的均为仓库接口类
  * CrudRepository： 继承 Repository，实现了一组 CRUD 相关的方法
  * PagingAndSortingRepository： 继承 CrudRepository，实现了一组分页排序相关的方法
  * JpaRepository： 继承 PagingAndSortingRepository，实现一组 JPA 规范相关的方法
  * 自定义的 XxxxRepository 需要继承 JpaRepository，这样的 XxxxRepository 接口就具备了通用的数据访问控制层的能力。
  * JpaSpecificationExecutor： 不属于Repository体系，实现一组 JPA Criteria 查询相关的方法

## SpringData方法定义规范

* 简单条件查询: 查询某一个实体类或者集合 
* **按照 Spring Data 的规范，查询方法以 find | read | get 开头**，涉及条件查询时，条件的属性用条件关键字连接，要注意的是：条件属性以首字母大写。 
* 例如：定义一个 Entity 实体类

  ```java
  class User{
      private String firstName;
      private String lastName;
  }
  ```

使用And条件连接时，应这样写： 
`findByLastNameAndFirstName(String lastName,String firstName);`
条件的属性名称与个数要与参数的位置与个数一一对应

### 支持的关键字

直接在接口中定义查询方法，如果是符合规范的，可以不用写实现，目前支持的关键字写法如下

![接口方法支持的关键字](images/接口方法支持的关键字.png)
![接口方法支持的关键字2](images/接口方法支持的关键字2.png)

### 查询方法解析流程

* 假如创建如下的查询：findByUserDepUuid()，框架在解析该方法时，首先剔除 findBy，然后对剩下的属性进行解析，假设查询实体为Doc
  * 先判断 userDepUuid （根据 POJO 规范，首字母变为小写）是否为查询实体的一个属性，如果是，则表示根据该属性进行查询；如果没有该属性，继续第二步；
  * 从右往左截取第一个大写字母开头的字符串(此处为Uuid)，然后检查剩下的字符串是否为查询实体的一个属性，如果是，则表示根据该属性进行查询；如果没有该属性，则重复第二步，继续从右往左截取；最后假设 user 为查询实体的一个属性；
  * 接着处理剩下部分（DepUuid），先判断 user 所对应的类型是否有depUuid属性，如果有，则表示该方法最终是根据 “ Doc.user.depUuid” 的取值进行查询；否则继续按照步骤 2 的规则从右往左截取，最终表示根据 “Doc.user.dep.uuid” 的值进行查询。
  * 可能会存在一种特殊情况，比如 Doc包含一个 user 的属性，也有一个 userDep 属性，此时会存在混淆。可以明确在属性之间加上 "_" 以显式表达意图，比如 "findByUser_DepUuid()" 或者 "findByUserDep_uuid()"
* 特殊的参数： 还可以直接在方法的参数上加入分页或排序的参数，比如：
  `Page<UserModel> findByName(String name, Pageable pageable);`
  `List<UserModel> findByName(String name, Sort sort);`

### 使用 @Query 注解

* 这种查询可以声明在 Repository 方法中，摆脱像命名查询那样的约束，将查询直接在相应的接口方法中声明，结构更为清晰，这是 Spring data 的特有实现。

```java
@Query("select c from Customer c where c.customerId=?1")
```

**索引参数和命名参数**
索引参数如下所示，索引值从1开始，查询中 `?X` 个数需要与方法定义的参数个数相一致，并且顺序也要一致
命名参数（推荐使用这种方式）：可以定义好参数名，赋值时采用`@Param("参数名")`，而不用管顺序。

如果是 @Query 中有 LIKE 关键字，后面的参数需要前面或者后面加 %，这样在传递参数值的时候就可以不加 %：

```java
@Query("select o from UserModel o where o.name like ?1%")
public List<UserModel> findByUuidOrAge(String name);

@Query("select o from UserModel o where o.name like %?1")
public List<UserModel> findByUuidOrAge(String name);

@Query("select o from UserModel o where o.name like %?1%")
public List<UserModel> findByUuidOrAge(String name);
```

还可以使用@Query来指定本地查询，只要设置nativeQuery为true，比如：

```java
@Query(value="select * from tbl_user where name like %?1" ,nativeQuery=true)
public List<UserModel> findByUuidOrAge(String name);
```

## @Modifying注解和事务

@Query 与 @Modifying 这两个 annotation一起声明，可定义个性化更新操作，例如只涉及某些字段更新时最为常用，示例如下： 

```java
@Modifying
@Query("update Customer c set c.customerName=?1")
int updateCustomerName(String cn);
```

注意：

* 方法的返回值应该是 int，表示更新语句所影响的行数
* 在调用的地方必须加事务，没有事务不能正常执行

**事务**
Spring Data 提供了默认的事务处理方式，即所有的查询均声明为只读事务。
对于自定义的方法，如需改变 Spring Data 提供的事务默认方式，可以在方法上注解 @Transactional 声明
进行多个 Repository 操作时，也应该使它们在同一个事务中处理，按照分层架构的思想，这部分属于业务逻辑层，因此，需要在 Service 层实现对多个 Repository 的调用，并在相应的方法上声明事务。

## CrudRepository 接口

CrudRepository 接口提供了最基本的对实体类的添删改查操作 

```java
T save(T entity);//保存单个实体

Iterable<T> save(Iterable<? extends T> entities);//保存集合

T findOne(ID id);//根据id查找实体

boolean exists(ID id);//根据id判断实体是否存在

Iterable<T> findAll();//查询所有实体,不用或慎用!

long count();//查询实体数量

void delete(ID id);//根据Id删除实体

void delete(T entity);//删除一个实体

void delete(Iterable<? extends T> entities);//删除一个实体的集合

void deleteAll();//删除所有实体,不用或慎用! 
```

## PagingAndSortingRepository接口

该接口提供了分页与排序功能
Iterable<T> findAll(Sort sort); //排序
Page<T> findAll(Pageable pageable); //分页查询（含排序功能）

## JpaRepository接口

该接口提供了JPA的相关功能
```java
List<T> findAll(); //查找所有实体

List<T> findAll(Sort sort); //排序、查找所有实体

List<T> save(Iterable<? extends T> entities);//保存集合

void flush();//执行缓存与数据库同步

T saveAndFlush(T entity);//强制执行持久化

void deleteInBatch(Iterable<T> entities);//删除一个实体集合
```

### JpaSpecificationExecutor接口

不属于Repository体系，实现一组 JPA Criteria 查询相关的方法 
Specification：封装  JPA Criteria 查询条件。通常使用匿名内部类的方式来创建该接口的对象

```java
//允许基于JPA标准API执行Specification的接口
public interface JpaSpecificationExecutor<T> {

    Optional<T> findOne(@Nullable Specification<T> spec);

    List<T> findAll(@Nullable Specification<T> spec);

    Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable);

    List<T> findAll(@Nullable Specification<T> spec, Sort sort);

    long count(@Nullable Specification<T> spec);
}
```

## 自定义Repository方法

### 为某一个 Repository 上添加自定义方法

步骤：

* 定义一个接口: 声明要添加的, 并自实现的方法
* 提供该接口的实现类: 类名需在要声明的 Repository 后添加 Impl, 并实现方法
* 声明 Repository 接口, 并继承声明的接口
* 使用.
* 注意: 默认情况下, Spring Data 会在 base-package 中查找 "接口名Impl" 作为实现类. 也可以通过　repository-impl-postfix　声明后缀.

```java
public interface EmployeeDao{
    void method();
}

public class EmployeeRepositoryImpl implements EmployeeDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void method(){
        //具体实现
    }
}

public interface EmployeeRepository extends JpaRepository<Employee,Integer>,EmployeeDao{

}
```

### 为所有的 Repository 都添加自实现的方法

步骤：

1. 声明一个接口, 在该接口中声明需要自定义的方法, 且该接口需要继承 Spring Data 的 Repository.
2. 提供 上面 所声明的接口的实现类. 且继承 `SimpleJpaRepository`, 并提供方法的实现
3. 定义 `JpaRepositoryFactoryBean` 的实现类, 使其生成 1) 定义的接口实现类的对象
4. 修改 `<jpa:repositories />` 节点的 `factory-class` 属性指向 3步骤 的全类名
5. 注意: 全局的扩展实现类不要用 Imp 作为后缀名, 或为全局扩展接口添加 `@NoRepositoryBean` 注解告知  Spring Data,让 Spring Data 不把其作为 Repository
