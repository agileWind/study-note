# 第一章 MySQL基础

[TOC]

## 数据库的相关概念

* 数据库可以实现数据持久化
* 使用完整的结构化的管理和查询

* DB：数据库(database):存储数据的“仓库”。它保存了一系列有组织的数据
* DBMS：数据库管理系统(database management system)，数据库是通过DBMS创建和操作的容器
* SQL：结构化查询语言(Structured Query Language)简称SQL，不是某个特定数据库供应商专有的语言，大多DBMS都支持SQL,简单易学，而且可以进行非常复杂和高级的操作

> * Oracle:比较大，费用高
> * DB2:处理海量数据，并不多的使用
> * SqlServer：只能安装到windows系统上

### 关系型数据库和非关系型数据库

#### 关系型数据库

* 关系模型指的就是二维表格模型，而一个关系型数据库就是由二维表及其之间的联系所组成的一个数据组织

* 关系型数据库设计规则
  遵循ER(entry-relation)模型

* 关系型数据库的常用概念：
  1. 关系：可以理解是一张二维表，每个关系都具有一个关系名，也就是通常说的**表名**
  2. 元组：可以理解是二维表中的一行，在数据库中经常被称为**记录**
  3. 属性：可以理解是二维表中的一列，在数据库中经常被称为**字段**
  4. 域：属性的取值范围，也就是数据库中某一列的取值限制
  5. 关键字：一组可以唯一标识元组的属性，数据库中常称为**主键**，由一个或多个列组成
  6. 关系模式：指对关系的描述。其格式为：关系名(属性1,属性2,...,属性N)

* 关系型数据库的优点：
  1. 容易理解：二维表结构是非常贴近逻辑世界的一个概念，关系模型相对网状、层次等其他模型来说更容易理解
  2. 使用方便：通过的SQL语言使得操作关系型数据库非常方便
  3. 易于维护：丰富的完整性(实体完整性、参照完整性和用户定义的完整性)大大降低了数据冗余和数据不一致的概率

* 关系型数据库的瓶颈
  1. 高并发读写需求
     网站的用户并发性非常高，往往达到每秒上万次读写请求，对于传统关系型数据库来说，硬盘I/O是一个很大的瓶颈(因为关系型数据库的结构数据其实最终还是以一个特殊的文件形式放在硬盘里了，读取和写入就是I/O操作)
  2. 海量数据的高效率读写
     网站每天产生的数据量是巨大的，对于关系型数据库来说，在一张包含海量数据的表中查询，效率是非常低的
  3. 高扩展性和可用性
     在基于web的结构中，数据库是很难进行横向扩展的，当一个应用系统的用户量和访问量与日俱增的时候，数据库没有办法像web server那样简单的添加更多硬件和服务节点来扩展性能和负载能力。而且对于7*24小时服务的网站来说，对数据库系统进行升级和扩展是非常麻烦的事情，往往需要停机维护和数据迁移

* 对于许多网站来说，关系型数据的某些特性并不需要
  1. 事务一致性
     关系型数据库在对数据一致性的维护中有很大的开销，而现在很多web2.0系统对事务的读写一致性都不高
     **数据库事务必须具备ACID特性：Atomic原子性，Consistency一致性，Isolation隔离性，Durability持久性**
  2. 读写实时性
     关系型数据插入一条数据之后立刻查询，肯定可以读出这条数据，但是有些web应用，并不要求这么高的实时性，比如发一条消息之后，过几秒乃至十几秒才看到这条动态是完全可以接受的
  3. 复杂的SQL，多表关联查询
     多个表进行关联查询，以及复杂的数据分析类型的复杂的SQL查询

#### 非关系型数据库

* 即NoSQL，用于指代非关系型的，分布式的，且一般不保证ACID原则的数据存储系统。非关系型数据库可能以键值对存储，且结构不固定，每一个元组可以有不一样的字段，也可以根据需要增加一些自己的键值对，这样就不会局限于固定的结构，减少一些空间和空间的开销。查询数据也不需要像关系型数据那样的SQL提供where来对字段属性值情况的查询，难以体现设计的完整性，NoSQL数据库可能仅仅根据id就能取出相应的value完成查询。所以非关系型数据库只适合存储一些较为简单的数据，对于需要进行较复杂查询的数据，使用SQL的关系型数据库显得更为合适

* 关系型数据库最大的特点就是事务的一致性，传统的关系型数据库读写操作都是基于事务的，但是关系型数据库的另一个特点就是有固定的表结构，扩展性差，改动数据库也很难。非关系型数据库严格上不是一种数据库，应该是一种数据结构化存储方法的集合。

* 非关系型数据库天生就具有多样性，所以这种数据库非常多，大部分是开源的，根据结构化方法和应用场合不同，分为：
  1. 面向高性能并发读写的key-value数据库：这种数据库主要特点就是具有极高的并发读写性能，如Redis,Tokyo Cabinet,Flare
  2. 面向海量数据访问的面向文档数据库：这种数据库的特点就是在海量的数据中快速的查询数据，典型代表就是MongoDB和CouchDB
  3. 面向可扩展性的分布式数据库：这种数据库尝试解决传统数据库存在可扩展性上的缺陷，可以适应数据量的增加以及数据结构的变化

## 认识MySQL

* 优点
  1. 成本低：开发源代码，免费使用
  2. 性能高：执行很快
  3. 简单：很容易安装和使用,体积小

* DBMS分为两类：
  1. 基于共享文件系统的DBMS（比如access）
  2. 基于客户机----服务器的DBMS

* 属于C/S架构，一般安装服务端软件
* 社区版(免费)和企业版(收费)

* 卸载干净已有数据库的方式：
  1. 卸载软件
  2. 删除Program Files软件目录中的文件夹
  3. 删除ProgramData软件目录中的文件夹
  4. 删除注册表中的信息
   >或者选择360等卸载软件，可以帮忙清理卸载后的文件夹和注册表

* MySQL的服务开启和停止
  1. 开启：
     * 方式1：计算机->管理->服务和应用程序->服务->MySQL，然后开启
     * 方式2：通过cmd启动，要**使用管理员身份打开cmd**，然后输入命令`net start mysql0815`,这里的`mysql0815`是服务名称，就是方式一中mysql的服务名
  2. 关闭：
     * 和开启方式对应，关闭可以输入`net start mysql0815`
  >不同的电脑服务名不一定相同
  > mysql开启后虽然能够通过cmd，输入mysql，进入到mysql命令中，但是查看数据库，看不到全部的数据，没有操作权限，需要登录才能获取相应权限

* MySQL服务端的登录和退出
  1. 通过mysql自带的client客户端，输入密码就可进入，但是这种方式有不足，只能允许root用户使用
  2. 通过在cmd里面，输入命令行语句登录(前提是已经开启了MySQL的服务),命令如下：
     `mysql -h localhost -P 3306 -u root -p`，然后就会进入到输入密码的行，也可直接在该行命令后面附加密码：
     `mysql -h localhost -P 3306 -u root -p密码字符串`，这里不能有空格。前面三个的命令参数之间可以和值可以分开也可不分开，`-hlocalhost`   `-P3306`  `-uroot`
     也就是说，格式是：mysql [-h主机名 -P端口号] -u用户名 -p密码  ,如果是本机且端口默认，括号内是可选的
  > 如果cmd里面无法输入mysql命令，请将mysql软件目录下的bin目录，其地址放在系统环境变量Path里面即可

* 简单的查看数据库的操作命令：
  1. `show databases;`  用来查看mysql里面有哪些数据库(schema)
  2. `use 数据库名;`   进入到某个数据库中,之后所有的对表的增删改查默认都是基于它的(除非明确指出其他表的数据库名，即数据库名.表名)。该命令也可用于切换到其他数据库
  3. `show tables`   查看当前数据库中的所有表名
  4. `show tables from 其他的数据库名`   在当前数据库中查看其他数据库的所有表名
  5. `select database();`   查看当前是在哪个数据库里
  6. `select version();`    查看使用的数据库的版本信息
  7. `desc 表名;`    查看表的结构
  8. `create table 表名(列名 列类型,列名 列类型,列名 列类型,...,列名 列类型);`   创建数据库表
   > 数据库命令语句一定要以分号`;`或者`\g`结束
   > 查看数据库的版本信息，可以在不进入mysql命令的时候，在cmd中输入`mysql --verision`或者`mysql -V`查看

* mysql的语法规范
  1. 不区分大小写，建议关键字大写；表名、列名小写
  2. 每条命令最好用分号结尾
  3. 每条命令根据需要，可以进行缩进，或者换行。
  4. 注释：
     * 单行注释：`#注释文字`
     * 单行注释：`-- 注释文字`,这里注释的标识符和内容需要空格
     * 多行注释：`/*注释文字*/`
  5. 字符串和日期使用单引号，虽然双引号也可，但是 mysql里面有个sql mode ，叫做：ANSI_QUOTES 。这个 ANSI_QUOTES开启后会把双引号当作 ``。所以我们还是建议使用单引号来引用字符串

* MySQL数据库的编码配置
  查看mysql的参数变量设置:`show variables like 'char%';`
  修改my.ini配置文件

  (停止MySQL服务再修改)

```ini
在路径：mysql软件目录下找到my.ini文件

修改内容1：
  找到[mysql]命令，在其下一行添加
    default-character-set=utf8
修改内容2:
  找到[mysqld]命令，在其下一行添加
    character-set-server=utf8
    collation-server=utf8_general_ci
修改完毕后，重启MySQL服务
```

## DQL语言的学习

* DQL:数据查询语言(Data Query Language),主要涉及到的就是对数据的查询操作

### 基本查询

* 语法：

  ```sql
  select 查询列表 from 表名;
  ```

  类似于System.out.println("打印内容")；
  特点：
  1. 查询列表可以是：表中的字段、常量值、表达式、函数
  2. 查询的结果是一个虚拟的表格

  ```sql
  select first_name,last_name from employees;
  select * from employess;
  select 100;
  select "Tom";
  select 100%98;
  select version();
  select `first_name`,`last_name` from `employees`; #这里的符号是用来标识这是字段或者表名等，而不是关键字
  ```

* 起别名
  好处：
  1. 便于理解；
  2. 如果要查询的字段有重名的情况，使用别名可以区分开来

  方式：
  1. 使用AS
  2. 使用空格

  ```sql
  select 100%98 as 结果;
  select last_name AS 姓氏,first_name AS 名字 FROM employees; #使用as
  select last_name 姓氏,first_name 名字 FROM employees;       #使用空格
  SELECT salary AS "out put" FROM employees;#如果别名中有空格，使用双引号将别名括起来
  ```

* 去重
  使用distinct关键字

  ```sql
  select distinct department_id from employees;
  ```

* `+`号的作用
  java中的`+`号：
  1. 运算符，两个操作数都为数值型
  2. 连接符，只要有一个操作数为字符串

  mysql中的+号：
  1. 仅仅只有一个功能：运算符

  ```sql
  select 100+90;    #两个操作数都为数值型，则做加法运算
  select '123'+90;  #只要其中一方为字符型，试图将字符型数值转换成数值型；如果转换成功，则继续做加法运算
  select 'john'+90; #如果转换失败，则将字符型数值转换成0
  select null+10;   #只要其中一方为null，则结果肯定为null
  ```

  在mysql中要实现拼接字符串，可以使用mysql中提供的函数concat(字段a,字段b,...)

  ```sql
  select concat("a","b","c","d");

  select concat(last_name,first_name) as 姓名 from employees;

  #因为commission_pct有的行该字段值为NULL,则此行的值可能为NULL，因为拼接的时候遇到NULL，则结果为NULL
  SELECT concat(first_name,last_name,job_id,commission_pct) as out_put from employees;

  #如果commission_pct字段的值为NULL，则赋给一个默认值0，这里用到了函数IFNULL
  SELECT concat(first_name,last_name,job_id,ifnull(commission_pct,0)) as out_put from employees;
  ```

### 条件查询

* 语法：

  ```sql
  select 查询列表 from 表名 where 筛选条件;
  ```

* 分类：
  1. 按照条件表达式筛选：
     * 简单条件运算符：`>`、`<`、`=`、`!=`、`>=`、`<=`、`<>`

     > 1. sql里判断是否相等使用一个等号`=`
     > 2. 不等于可以使用`!=`或者`<>`

  2. 按照逻辑表达式筛选:
     * 逻辑运算符：`&&`、`||`、`!`、`and`、`or`、`not`
     * 作用：用于连接条件表达式
     * 语法规则：
       1. `&&`和`and`:两个条件都为true,结果为`true`,反之为`false`
       2. `||`和`or`:只要有一个条件为true,结果为`true`，反之为`false`
       3. `!`和`not`:如果连接的条件本身为false,结果为`true`,反之为`false`
  3. 模糊查询
     * `like`、`not like`
     * `between and`
     * `in`、`not in`
     * `is null`、`is not null`

  ```sql
  # 查询部门编号不等于90号的员工名和部门编号
  select last_name,department_id from employees where department_id<>90;

  # 查询工资在10000到20000之间的员工名，工资
  select last_name,salary from employees where salary>=10000 and salary<=20000;

  # 查询部门编号不在90到110之间，或者工资高于15000的员工信息
  select * from employees where not(department_id>=90 and department_id<=110) or salary>15000;
  # 查询员工名中包含字符a的员工信息
  select * from employees where last_name like "%a%";

  # 查询员工中第三个字符为e,第五个字符为a的员工信息
  select * from employees where last_name like "__e_a%";

  # 查询员工名中第二个字符为_的员工名，因为_为通配符，所以需要转义，可以使用\来转义，也可以指定转义符
  select * from employees where last_name like '_$_%' escape '%';# %为转义符


  select * from employees where employee_id between 100 and 120

  # 查询出满足名字为Tom,Jack,Alice其中一个的所有员工信息
  select * from employees where first_name in("Tom","Jack","Alice")

  # is null用来判断字段值为null
  select * from employees where commission_pct is null;# 查询没有奖金的员工信息


  # 安全等于<=>既可以判断null，也可以判断普通类型的数值
  select * from employees where commission_pct <=> null;# 查询没有奖金的员工信息
  select * from employees where salary <=> 12000; #查询工资等于12000的员工信息
  ```

  * like的使用特点：
    1. 一般要和通配符搭配使用
    2. 通配符有：
       * `%`：任意多个字符，包含0个字符
       * `_`：任意单个字符
  * between and的使用特点：
    1. between and可以提高语句的简洁度
    2. 包含临界值
    3. 两个临界值不要随意调换顺序，语义则不同，因为`字段 between num1 and num2` 相当于 `字段>=num1 and 字段<=num2`,调换则表示的语义不同了
  * in的使用特点：
    1. 判断某字段的值是否属于in列表中的某一项
    2. 使用in提高语句简洁度
    3. in列表的值类型必须一致或者兼容
    4. in列表中不支持通配符
  * is null的使用
    1. `!=`,`=`和`<>`不能用于判断null值
    2. `is null`和`is not null`可以判断null值
    3. `is`不能用于判断其他类型的数据值
  * is null与<=>
    1. `is null`：仅仅可以判断null值，可读性较高，建议使用
    2. `<=>`：既可以判断NULL值，又可以判断普通的值，可读性低

* 思考：
  `select * from employees;`和`select * from employees where commission_pct like "%%"`是否执行结果相同？
  两者执行结果不一定相同，前者是查询表里的所有数据，对字段值没有要求；后者如果字段commission_pct的值没有为null，则两者结果相同；如果字段commission_pct的值有为`null`，`like "%%"`不会包含字段值为null的数据

### 排序查询

* 语法

  ```sql
  select 查询列表 from 表名 where 筛选条件 order by 排序字段或表达式 asc|desc;
  ```

* 特点：
  1. asc代表的是升序，可以省略，默认就是升序
  2. desc代表的是降序
  3. order by子句可以支持单个字段、别名、表达式、函数、多个字段
  4. order by子句在查询语句的最后面，除了limit子句

```sql
#1、按单个字段排序

SELECT * FROM employees ORDER BY salary DESC;

#2、添加筛选条件再排序

#案例：查询部门编号>=90的员工信息，并按员工编号降序
SELECT *
FROM employees
WHERE department_id>=90
ORDER BY employee_id DESC;


#3、按表达式排序

#案例：查询员工信息 按年薪降序
SELECT *,salary*12*(1+IFNULL(commission_pct,0))
FROM employees
ORDER BY salary*12*(1+IFNULL(commission_pct,0)) DESC;


#4、按别名排序

#案例：查询员工信息 按年薪升序
SELECT *,salary*12*(1+IFNULL(commission_pct,0)) 年薪
FROM employees
ORDER BY 年薪 ASC;

#5、按函数排序

#案例：查询员工名，并且按名字的长度降序
SELECT LENGTH(last_name),last_name
FROM employees
ORDER BY LENGTH(last_name) DESC;#length()函数可以计算字符串长度

#6、按多个字段排序

#案例：查询员工信息，要求先按工资降序，再按employee_id升序
SELECT *
FROM employees
ORDER BY salary DESC,employee_id ASC;
```

* 注意：
  1. `length(s)`: 一个汉字是算三个字符，一个数字或字母算一个字符。
  2. `char_length(s)`: 不管汉字还是数字或者是字母都算是一个字符。

```sql
select length("Tom");# 返回的是3
select length("张三");# 返回的是6
```

### 常见函数

#### 字符函数

* length(s)

不同的编码显示和文字显示的长度不同，utf8下，英文长度为1，中文长度为3

```sql
select length('jack');# 4
select length('张三hello');  # 11
```

* concat(字符a,字符b,...,字符n)

```sql
select concat(last_name,'_',first_name) 姓名 from employees;
```

* upper(s)、lower(s)
  upper(s):将字符串中的英文全部转换成大写
  lower(s):将字符串中的英文全部转换成小写

```sql
select upper('Tom'); # TOM
select lower('Tom'); # tom
```

* substr|substring    sql中的索引都是从1开始的（limit行数从0开始除外）
  1. substr(s,start)：从start开始，截取字符串
  2. substr(s,start,num):从start处开始，截取长度共计为num的子串

```sql
select substr('hello world',7); # world
select substr('hello world',2,4);# ello
select concat()
```

* instr：返回子串第一次出现的索引，如果找不到返回0

```sql
select instr('hello world','llo');# 3
```

* trim:去掉收尾的指定字符，默认是去掉空格

```sql
select length(trim('   hello world   ')); # 11
select trim('a' from 'aaaaaahelloaworldaaaaaa');# helloaworld
select trim('aa' from 'aaaaaaahelloaworldaaaaaaa');# ahelloaworlda
```

* lpad:左填充

```sql
select lpad('heee',3,'*');# hee
select lpad('heee',10,'*');# ******heee
```

* rpad:右填充

```sql
select lpad('heee',3,'*');# hee
select lpad('heee',10,'*');# heee******
```

* replace:替换

```sql
select replace('hihihe','hi','hello'); # hellohellohe
```

#### 数学函数

```sql
# round 四舍五入
select round(1.55);  # 2
select round(1.45);  # 1
select round(-1.55); # -2
select round(-1.45); # -1
```

```sql
# ceil 向上取整:返回大于等于参数的最小整数
select round(1.05);  # 2
select round(1.00);  # 1
select round(-1.55); # -1
```

```sql
# floor 向下取整:返回小于等于参数的最小整数
select round(1.05);  # 1
select round(1.00);  # 1
select round(-1.55); # -2
```

```sql
# truncate 截断:保留浮点数的小数点的位数
select truncate(1.65555,2);  # 1.65
select truncate(1.00,1);  # 1.0
select truncate(1000,1); # 1000
```

```sql
# mod：取余%,正负号看被除数的正负号，原理a%b=a-a/b*b;
select mod(-10,-3);# -1
select mod(10,-3); #  1
```

#### 日期函数

```sql
select now();      # 当前系统日期+时间：2020-12-21 19:42:11，这个值可以直接存到字段类型为timestamp的列中
select curdate();  # 当前系统日期：2020-12-21
select curtime();  # 当前系统时间：19:42:11
select year(now());#获取指定的部分：年，2020
# 也可以通过相应方法获取其他指定部分

# select monthname(now());# September
```

* 格式转换函数：
  * str_to_date(str):将日期格式的字符串转成指定格式的日期

   ```sql
   select str_to_date('9-13-1999','%m-%d-%Y');# 1999-09-13
   ```

  * date_format(date):将日期转换成字符串

   ```sql
   select date_format('2018/6/6','%Y年%m月%d日');# 2018年06月06日
   ```

* 字符转换的缺省代替格式符

|序号|格式符|功能|
|:----|:----|:----|
|1|%Y|四位的年份|
|2|%y|2位的年份|
|3|%m|月份(01,02,...11,12)|
|4|%c|月份(1,2,...,11,12)|
|5|%d|日(01,02,...)|
|6|%H|小时(24小时制)|
|7|%h|小时(12小时制)|
|8|%i|分钟(00,01,...59)|
|9|%s|秒(00,01,...59)|

#### 其他函数

```sql
select version();# 查看数据库的版本
select database();# 查看当前所在的数据库名
select user();# 查看当前登录的账户
```

#### 流程控制函数

* if

```sql
select if(10<5,'大','小');
select if(grade<60,'没及格，嘤嘤嘤','及格了，哈哈哈') from student
```

* case...when...then
  格式：
  case 要判断的字段或表达式
  when 常量1 then 要显示的值1或语句1
  when 常量2 then 要显示的值2或语句2
  ...
  else 要显示的值n或语句n
  end

```sql
select
case nation
when 'China' then '中国'
when 'Japan' then '日本'
when 'Russia' then '俄罗斯'
else nation
end as 国籍
from student
```

格式：
case
when 条件1 then 要显示的值1或语句1
when 条件2 then 要显示的值2或语句2
when 条件3 then 要显示的值2或语句3
else 要显示的值n或语句n
end

```sql
select
case
when grade<60 then 'E'
when grade>=60&&grade<69 then 'D'
when grade>=70&&grade<79 then 'C'
when grade>=80&&grade<89 then 'B'
when grade>=90 then 'A'
else '成绩统计不对'
end as 成绩等级
from student
```

### 分组函数

* 功能：用作统计使用，又称为聚合函数或统计函数或组函数
* 分类：sum(求和),avg(平均值),max(最大值),min(最小值),count(计算个数)

```sql
select
sum(grade) as sum,
avg(grade) as avg,
max(grade) as max,
min(grade) as min,
count(grade) as count
from student
```

* 参数支持哪些类型
  1. sum,avg只支持数字类型，并且忽略null值，avg求平均值不会将null的行计入权重
  2. max,min支持数字和字符串和日期
  3. count它是计数，都支持，但是它不统计值为null
  > 以上函数都是忽略null值的
  > 可以和distinct搭配使用去重操作
  > `select count(*) from 表名`常用来获取表数据的行数，数据的整行字段都为null也会被统计进去
  > `select count(字段) from 表名`,如果字段的值有空值，则改行不会被统计进去
  > `select count(1) from 表名`也能获取表数据的行数
  > MYISAM存储引擎下，count(*)的效率高
  > INNODB存储引擎下，count(*)和count(1)的效率差不多，比count(字段)要快

* 和分组函数一同被查询的字段有限制，因为分组函数只显示一行数据，被一同查询的其他字段就没有意义
  `select avg(grade),uid from student;`
  分组函数函数一同被查询的应该是group by后面的字段
  `select avg(grade),classid from student group by classid;`按照班级来统计每个班级的平均分数

### 分组查询

* 格式：

```sql
select 列,分组函数
from table
where 筛选条件
group by 分组的列表
order by 子句;
```

* where 一定要放在表名后面
* 查询列表必须特殊，要求是分组函数和group by后出现的字段

* 分组后的筛选：按照group by分组后的筛选
  使用having 分组筛选子句

```sql
# 按照降序来排序，每个班级的平均数及格的班级情况
select calssid,avg(grade)
from student
group by classid
having avg(grade)>60
order by avg(grade) desc
```

* 分组查询中的筛选条件分为两类：
  1. 分组前的筛选：数据源是原始表，位置在group by子句的前面，关键字使用where
  2. 分组后的筛选：数据源是分组后的结果集，位置在group by 子句的后面，关键字使用having

* 能放在分组前的条件最好放在分组前
* group by子句支持单个字段分组，多个字段分组(多个字段直接用逗号隔开没有顺序要求，表达式和函数(用得少))
* 排序子句在最后

### 连接查询

* 笛卡尔乘积现象：表1有m行，表2有n行，结果=n*m行
  发生原因：没有有效的连接条件

* 分类：
  1. 按照年代分类:
     * sql92标准：仅仅支持内连接
     * sql99标准：支持内连接+外连接(左外+右外)+交叉连接
  2. 按照功能分类：
     * 内连接：等值连接，非等值连接，自连接
     * 外连接：左外连接，右外连接，全外连接
     * 交叉连接

#### sql92

* 等值连接：A表.字段1的值=B表.字段3值
  1. 多表等值连接的结果为多表的交集部分
  2. n表连接，至少需要n-1个连接条件
  3. 多表的顺序没有要求
  4. 一般需要为表起别名
  5. 可以搭配前面介绍的所有子句使用，比如排序，分组，筛选

* 非等值连接

```sql
select grade,leveltitle
from student,leveltable
where grade between lowest and highest
```

* 自连接

```sql
# 找对员工对应的领导
select e.employee.id,e.last_name,m.employee.id,m.last_name
from employees e,employees m
where e.manager_id=m.employee_id;
```

#### sql99

* 语法：

```sql
select 查询列表
from 表1 别名 [连接类型]
join 表2 别名
on 连接条件
[where 筛选条件]
[group by 分组]
[having 分组后筛选条件]
[order by 排序列表]
```

* 连接类型：内连接inner,外连接(左外left outer,右外 right outer,全外 full outer)，交叉连接cross

* 内连接：

  特点：
  1. 添加排序、分组、筛选
  2. inner可以省略
  3. 筛选条件放在where后面，连接条件放在on后面，提高分离性，便于阅读

```sql

# 等值连接

# 查询员工名，部门名
select last_name,department_name
from employees e
inner join departments d
on e.department_id=d.department_id;

# 查询名字中包含e的员工名和工种名
select last_name,job_title
from employees e
inner join jobs j
on e.job_id=j.job_id
where e.last_name like '%e%';

# 查询部门个数>3的城市名和部门个数
select city,count(*) 部门个数
from departments d
inner join locations loc
on d.location_id=loc.location_id
group by city
having count(*)>3;

# 查询哪个部门的员工个数>3的部门名和员工个数，并按个数降序
select count(*) department_name
from employees e
inner join departments d
on e.department_id=d.department_id
group by department_name
having count(*)>3
order by count(*) desc;

# 查询员工名，部门名，工种名，并按照部门名降序
select last_name,department_name,job_title
from employees
inner join departments d on e.department_id=d.department_id
inner join jobs j on e.job_id=j.job_id
order by department_name desc;


# 非等值连接

# 查询员工的工资级别
select salary,grade_level
from employees e
inner join job_grade g
on e.salary between g.lowest_sal and g.highest_sal;

# 查询工资级别的个数>20的个数，并且按照工资级别级别降序
select count(*),grade_level
from employees e
inner join job_grades g
on e.salary between g.lowest_sal and g.highest_sal
group by grade_level
having count(*)>20
order by grade_level desc;

# 自连接

# 查询员工的名字，以及员工上级的名字
select e.last_name,m.last_name
from employees e
inner join employees m
on e.manager_id=m.employee_id;
```

#### 外连接

* 应用场景：用于查询一个表中有，另一个表汇总没有的记录
* 特点：
  1. 外连接的查询结果为主表中的所有记录
     * 如果从表中有和它匹配的，则显示匹配的值
     * 如果从表中没有和它匹配的，则显示null
     * 外连接查询结果=内连接结果+主表中有而从表没有的记录
  2. 左外连接，left join左边的是主表
     右外连接，right join右边的是主表
  3. 左外和右外交换两个表的顺序，可以实现同样的效果
  4. 全外连接=内连接的结果+表1中有但表2没有的+表2中有但表1没有的

```sql
# 查询哪个部门没有员工
# 左外
select d.*,e.employee_id
from departments d
left outer join employees e
on d.department_id=e.department_id
where e.employee_id is null

# 右外
select d.*,e.employee_id
from employees e
right outer join departments d
on d.department_id =e.department_id
where e.employee_id is null

# 全外


```

* 交叉连接

* 总结连接查询

![外连接1](./images/外连接1.png)

![外连接2](./images/外连接2.png)

### 子查询

* 含义：出现在其他语句内部的select语句，称为子查询或者内查询
        外部的查询语句，称为主查询或者外查询

* 分类：
  1. 按子查询出现的位置：
     * select后面(这里仅仅支持标量子查询)
     * from后面(这里支持表子查询)
     * where或者having后面(这里支持标量子查询、列子查询，行子查询)
     * exists后面(相关子查询,这里可以支持表子查询)
  2. 按结果集的行列数不同：
     * 标量子查询(结果集只有一行一列)
     * 列子查询(结果集只有一列多行)
     * 行子查询(结果集有一行多列)
     * 表子查询(结果集一般为多行多列)

* where或者having后面
  1. 标量子查询(单行子查询)
  2. 列子查询(多行子查询)
  3. 行子查询(多列多行)
  4. 特点：
     * 子查询放在小括号内
     * 子查询一般放在条件的右侧
     * 标量子查询，一般搭配着单行操作符使用：>、<、>=、<=、=、<>
     * 列子查询：一般搭配着多行操作符使用：in、any/some、all
  5. 子查询语句先执行，查出来之后再参与主查询

```sql
# 标量子查询
# 谁的工资比Abel的工资高
select *
from employees
where salary>(
  select salary
  from employees
  where last_name='Abel'
);

# 返回job_id与141号员工相同，salary比143号员工多的员工
USE myemployees;

SELECT last_name,job_id,salary
FROM employees
WHERE
  job_id = (
    SELECT job_id
    FROM employees
    WHERE employee_id = 141
  )
AND salary > (
  SELECT salary
  FROM employees
  WHERE employee_id = 143
);

# 将上面的标量子查询转换成了连接查询
use myemployees;
select e1.last_name,e1.job_id,e1.salary
from employees e1,employees e2,employees e3
where e1.job_id=e2.job_id and e2.employee_id=141
and e1.salary> e3.salary and e3.employee_id=143;

# 查询公司最低工资的员工的信息
select last_name,job_id,salary
from employees
where salary=(
select min(salary)
from employees
);

# 查询最低工资大于50号部门最低工资的部门id和其最低工资
use myemployees;
select department_id,min(salary)
from employees
group by(department_id)
having min(salary)>(
  select min(salary)
  from employees
  where department_id=50
);
```

* 错误使用标量子查询的情况:
  1. 子查询的结果不是一行一列
  2. 子查询的结果为空

* 列子查询(多行子查询)
  1. 返回多行
  2. 使用多行比较操作符

  |操作符|含义|
  |:----|:----|
  |in/not in|等于/不等于列表中的任意一个|
  |any|some|和子查询返回的某一个值比较,较少使用|
  |all|和子查询返回的所有值比较,较少使用|

```sql
# 查询location_id是1400或1700的部门中的所有员工姓名
select last_name
from employees
where department_id in(
  select distince department_id
  from departments
  where location_id in(1400,1700)
);

# 返回其他部门中比job_id为"IT_PROG"部门任一工资低的员工的员工信息
use myemployees;
select last_name,employee_id,job_id,salary
from employees
where salary<any(
  select distinct salary
  from employees
  where job_id='IT_PROG'
)
```

* 注意“任一”和“任意”或者“所有”
* 任一对应any,任意也就是所有的，对应all

* 行子查询

* select后面
  select后面的子查询只能返回一列

```sql
# 查询每个部门的员工个数
select d.*,(select count(*) from employees e where e.department_id=d.department_id) 个数
from departments d;

# 查询员工号=102的部门名
select (
  select department_name
  from departments d
  inner join employees e
  on d.department_id=e.department_id
  where e.employee_id=102
);
```

* from后面
  1. 将子查询结果充当一张表，要求必须起别名

```sql
# 查询每个部门的平均工资和工资等级
select ag_dep.*,g.grade_level
from (
  select avg(salary) ag,department_id
  from employees
  group by department_id
) ag_dep
inner join job_grades g
on ag_dep.ag between lowest_sal and highest_sal;
```

* exists后面(相关子查询)
  1. 语法：exists(完整的查询语句)
  2. 结果：1或0

```sql
select exists(select employee_id from employees where salary=30000);

# 查询有员工的部门名
select department_name
from departments_d
where exists(
  select *
  from employees e
  where d.department_id=e.department_id
);
# 使用in来实现
select department_name
from departments_d
where d.department_id in(
  select department_id
  from employees
);
```

```sql
select employee_id,last_name,salary,e.department_id
from employees e
inner join(
  select avg(salary) ag,department_id
  from employees
  group by department_id
) ag_dep
on e.department_id=ag_dep.department_id;
```

### 分页查询

格式：

```sql
select 查询列表
from 表
[join_type join 表2]
on 连接条件
where 筛选条件
group by 分组字段
having 分组后的筛选
order by 排序的字段
limit offset,size;
```

* offset即行数，从0开始计数，即第一行为0
* size是每次显示的条目个数
* 分页查询一般要配合order by来使用，这样确保分页数据不会重复

查询语句中涉及到的所有的关键字，以及执行先后顺序

```sql
select 查询列表              #7
from 表                     #1
连接类型 join 表2            #2
on 连接条件                  #3
where 筛选条件               #4
group by 分组列表            #5
having 分组后的筛选          #6
order by 排序列表            #8
limit 偏移,条目数;            #9
```

### union联合查询

* union联合，合并：将多条查询语句的结果合并成一个结果

* 语法：
  查询语句1
  union
  查询语句2
  union
  ...

```sql
# 查询部门编号>90或邮箱包含a的员工信息
select * from employees where email '%a%' or department_id>90;
# 或者
select * from employees where email like '%a%'
union
select * from employees where department_id>90;

# 查询中国用户中男性的信息以及外国用户中年男性的用户信息
select id,cname,csex from t_ca where csex='男'
union
select t_id,tName,tGender from t_ua where tGender='male';
```

* 应用场景：
  要查询的结果来自于多个表，且多个表没有直接的连接关系，即没有引用的各种条件。但查询的信息一致，即它们查询列表相同，此时适合使用联合查询
* 联合查询的查询列表必须是表示相同的内容，字段顺序且列表的字段个数也是相同的
* union关键字默认去重，想不去重可以改成union all

## DML语言的学习

* DML:数据操纵语言(Data Manipulation Language)，主要是对表中的数据进行增`insert`、删`delete`、改(更新)`update`的操作
* DML是针对数据的，不涉及数据库的结构的修改

### 插入语句

```sql
# 指定字段来插入数据，没有指定的字段会使用默认值或者null补充
/* 语法格式：
insert into 表名(
   字段1,
   字段2,
   ...
   字段n
  )values(
    字段1对应的值,
    字段2对应的值,
    ...
    字段n对应的值
);
*/

# 插入完整一条数据到表中，值的顺序要和表的列顺序一致
/* 语法格式：
insert into 表名 values(
    字段1对应的值,
    字段2对应的值,
    ...
    字段n对应的值
);
*/

# 批量插入，即插入多行数据
/* 语法格式：
insert into 表名(
   字段1,
   字段2,
   ...
   字段n
  )values(字段1对应的值,字段2对应的值,...字段n对应的值),
  (字段1对应的值,字段2对应的值,...字段n对应的值),
  (字段1对应的值,字段2对应的值,...字段n对应的值),
  ...
  (字段1对应的值,字段2对应的值,...字段n对应的值);
*/
```

* 插入数据需要注意的点：
  1. 日期和字符(串)一样，都需要引号包起来
  2. values中的值的顺序必须与字段的顺序对应
  3. 如果某个字段插入空值，使用null补位
  4. 如果插入完整的一条数据到表中，可以省略字段列表，使用`*`代替,

* 插入数据除了上面这种方式(方式一)，还可以是(方式二):
  `insert into 表名 set 字段名1=值1,字段名2=值2;`

* 两种插入方式的区别：
  1. 方式一支持多行插入，方式二只能插入一行
  2. 方式一支持子查询，方式二不支持
   `insert into 表名1 (字段列表) select 字段列表 from 表名2 where 条件;`

### 修改语句

```sql
# 更新数据
# 语法格式：update 表名 set 字段名1=值1,字段名2=值2 [where 条件1=条件1的值 and 条件2=条件2的值];

# 设置全部学生的成绩为60
update student set grade=60;

# 设置名字为Tom的学生成绩为90
update student set grade=90 where name='Tom';

# 将所有学生的成绩在原来基础上加40
update student set grade=grade+40;
```

* 单表更新和多表更新
  上面的更新操作只是对单表进行操作修改，多表也是可以的，多表修改支持sql92语法和sql99语法

  ```sql
  update boys bo
  right join beauty b
  on bo.id=b.boyfriend_id
  set b.boyfriend_id=2
  where bo.id is NULL
  ```

### 删除语句

```sql
# 删除数据
# 语法格式：delete from 表名 [where 条件表达式]

# 删除表中名称为Tom的记录
delete from student where name='Tom';

# 删除表中所有的记录
delete from student
```

* 单表删除和多表删除
  1. sql92语法：

     ```sql
     delete 表1的别名,表2的别名
     from 表1 别名,表2 别名
     where 连接条件
     and 筛选条件;
     ```

  2. sql99语法：

     ```sql
     delete 表1的别名,表2的别名
     from 表1 别名
     inner|left|right join 表2 别名
     on 连接条件
     where 筛选条件
     ```

* 删除语句`delete`和`truncate`的区别
  1. delete语句是DML语句，truncate通常认为是DDL语句
  2. truncate删除效率要高一点点
  3. truncate删除没有返回值，delete删除有返回值
  4. truncate删除不能回滚，delete删除可以回滚，也就是说如果是在手动提交的事务中，truncate无法回滚(但是还是可以通过其他手段恢复数据)
  5. delete删除表中的数据，表结构还在，delete语句后面还可以跟where子句，指定条件的记录来删除。删除数据后,自增加的主键id会从断点处开始，被删除的数据主键id不能再被使用
  6. truncate语句只能用于删除表中的所有数据，实际上是把表直接drop删除掉，然后再创建一个结构相同的新的空表。主键id会重新计算再次使用。删除数据不能找回，无法进行事务回滚，执行速度比delete快

## DDL语言的学习

* DDL:数据定义语言(Data Define Language),,主要涉及到的操作就是：创建`create`,修改`alter`,删除`drop`
* 数据定义语言可以分别作用于数据库，数据表，以及表的字段
* 这里的操作不涉及数据库里的数据的修改，是在定义(修改)数据库，数据表，以及表的结构

### 操作数据库

```sql
# 创建数据库
# 格式：create database [if not exists] 数据库名;
create database mydb1;
create database mydb2 character set GBK;
create database mydb3 character set GBK collate gbk_chinese_ci;# collate会影响排序

# 查看数据库
show databases;# 列出所有的数据库名称
show create databases mydb2;# 查看刚刚创建的数据库mydb2的创建信息

# 修改数据库
alter database mydb2 character set utf8;

# 删除数据库
# 格式：drop database [if exists] 数据库名;
drop database mydb3;

# 其他操作

# 使用(切换)数据库
use mydb2;

# 查看当前使用的数据库
select database();
```

### 操作数据表

```sql
# 创建数据表
/* 格式：
   create table [if not exists] [数据库名].表名(
     字段名1 数据类型 [属性] [索引] [注释],
     字段名2 数据类型 [属性] [索引] [注释],
     字段名3 数据类型 [属性] [索引] [注释],
     ...
     字段名n 数据类型 [属性] [索引] [注释] #最后一个字段的定义后面不要逗号
   );
*/
create table Employees(
  uid int,
  age int,
  first_name varchar(10),
  last_name varchar(10)  #最后一个字段的定义后面不要逗号
);

# 查看数据表

# 查看定义的数据表创建信息
show create table Employees;

# 查看数据表的列信息
desc Employees;

# 查看当前使用的数据库中的所有数据表
show tables;

# 修改数据表:修改表名；增加列，删除列，修改列名、列类型、列约束

# 修改表名
# 格式：alter table 原表名 rename [to] 新表名;
alter table stu rename student;
alter table stu to student;//两者方式都可，第二种不是标准的，是方言

# 修改字段名(列名)
# 格式：alter table 表名 change 原字段名 新字段名 新数据类型;
alter table student change stu_age age varchar(3);

# 修改字段数据类型
# 格式：alter table 表名 modify 字段名 数据类型;
alter table student modify age int;

# 添加字段
# 格式：alter table 表名 add 新字段名 数据类型 [first|after 字段名2]; # 可选命令指示新字段的位置
alter table student add stu_grade int;

# 删除字段
# 格式：alter table 表名 drop 字段名;
alter table student drop stu_grade;

# 修改字段排列位置
# 格式：alter table 表名 modify 字段名1 数据类型 first|after 字段名2;
alter table student modify stu_id varchar(12) first; # 字段stu_id在表的第一列
alter table student modify stu_name varchar(20) after stu_id; # 字段stu_name在列stu_id的后面挨着

# 删除数据表
# 格式： drop table 数据表名;
drop table student;
```

* 表的复制

 1. 仅仅复制表的结构
 `create table 新表名 like 原表名;`

 2. 复制表的结构+数据
 `create table 新表名 select * from 原表名;`

 3. 复制表的部分数据
 `create table 新表名 select 字段列表 from 原表名 where 筛选条件;`

 4. 复制表的某些字段结构，不要数据

 ```sql
 create table 新表
 select 字段列表
 from 原表名
 where 0;# 或者where 1=2;
 ```

### 常见的数据类型介绍

* 数值型：
  1. 整型
  2. 小数
     * 定点数
     * 浮点数
* 字符型：
  1. 较短的文本
     * char
     * varchar
  2. 较长的文本
     * text
     * blob
* 日期型

#### 整型

* 分类：
  |整数类型|字节|范围|
  |:----|:----|:----|
  |tinyint|1|有符号：$-2^7$ ~$2^7-1$<br>无符号：0 ~$2^8-1$|
  |smallint|2|有符号：$-2^{15}$ ~$2^{15}-1$<br>无符号：0 ~$2^{16}-1$|
  |mediumint|3|有符号：$-2^{23}$ ~$2^{23}-1$<br>无符号：0 ~$2^{24}-1$|
  |int、integer|4|有符号：$-2^{31}$ ~$2^{31}-1$<br>无符号：0 ~$2^{32}-1$|
  |bigint|8|有符号：$-2^{63}$ ~$2^{63}-1$<br>无符号：0 ~$2^{64}-1$|

* 特点：
  1. 如果不设置无符号还是有符号，默认是有符号，如果想设置无符号，需要添加unsigned关键字
  2. 如果插入的数值超出了整型的范围，会报out of range异常，并且插入临界值
  3. 如果不设置长度，会有默认的长度,这里说的长度不是值数字的范围，数字的范围由类型决定，这里的长度指的是显示的长度，如果不够长度，会在左边高位补0，但是仅仅只是设置了显示长度，默认缺位并不会补0，还要定义表的时候使用关键字zerofill

  ```sql
  create table tab_int(
    field1 int(7) zerofill,#该字段显示长度为7，不足则补0
    field2 int(7) zerofill unsigned
  );
  ```

#### 小数

|浮点数类型|字节|范围|
|:----|:----|:----|
|float|4|$\pm1.75*10^{-38}$ ~ $\pm3.40*10^{+38}$|
|double|8|$\pm2.22*10^{-308}$ ~ $\pm1.79*10^{+308}$|
|定点数<br>dec\|decimal(m,d)|m+2|最大取值范围与double相同，给定dec的有效取值范围由m和d决定|

* 分类
  1. 浮点型
   float(M,D)
   double(M,D)
  2. 定点型
   dec(M,D)
   decimal(M,D)

* 特点：
  1. M：整数部位+小数部位
  2. D：小数部位
  3. 如果超过范围，则插入临界值
  4. M和D都可以省略
     浮点数可以自动改变精度
     如果是定点数类型，则M默认是10，D默认是0

#### 字符型

|字符类型|写法|M的意思|特点|空间的耗费|效率|
|:----|:----|:----|:----|:----|:----|
|char|char(M)|最大的字符数，可以省略，默认为1|固定长度的字符|比较耗费|高|
|varchar|varchar(M)|最大的字符数，不可以省略|可变长度的字符|比较节省|低|

#### 日期型

|日期和时间类型|字节|最小值|最大值|
|:----|:----|:----|:----|
|date|4|1000-01-01|9999-12-31|
|datetime|8|1000-01-01 00:00:00|9999-12-31 23:59:59|
|timestamp|4|19700101080001|2038年的某个时刻|
|time|3|-838:59:59|838:59:59|
|year|1|1901|2155|

* timestamp支持的时间范围较小
* timestamp和实际时区有关，更能反映实际的日期，而datetime则只能反映出插入时的当地时区
* timestamp的属性受Mysql版本和SQLMode的影响很大

### 常见约束

* 含义：一种限制，用于限制表中的数据，为了保证表中的数据的准确和可靠性
* 分类：六大约束
  1. not null:非空，用于保证该字段的值不能为空
  2. default:默认，用于保证该字段有默认值
  3. primary key:主键，用于保证该字段的值具有唯一性，并且非空
  4. unique:唯一，用于保证该字段的值具有唯一性，可以为空
  5. check：检查约束(mysql中不支持),比如字段年龄，性别的取值限制的检查
  6. foreign key：外键，用于限制两个表的关系，用于保证该字段的值必须来自于主表的关联列的值。在从表添加外键约束，用于引用主表中某列的值

* 添加约束的时机：
  1. 创建表时
  2. 修改表时

* 约束的添加分类：
  1. 列级约束：六大约束语法上都支持，但外键约束没有效果
  2. 表级约束：除了非空、默认，其他的都支持

* 添加列级约束：
  1. 语法：直接在字段名和类型后面追加约束类型即可
  2. 只支持：默认，非空，主键，唯一

* 添加表级约束：
  1. 语法：在定义所有定义字段的最下面
  2. 格式：`[constraint 约束名] 约束类型(字段名)`
  3. `constraint 约束名` 可以省略

* 通用写法：
  1. 默认约束，非空约束一定要写在列级约束上
  2. 外键约束一定要写在表级约束上
  3. 主键约束，唯一约束可以写在列级约束或者表级约束上都可；一般写在列级约束上

```sql
# 列级约束
use students;
create table stuinfo(
  id int primary key,
  stuName varchar(20) not null,
  gender char(1) check(gender='男' or gender='女'),
  seat int unique,
  age int default 18,
  majorId int references major(id)
);

create table major(
  id int primary key,
  majorName varchar(20)
);

# 表级约束
create table stuinfo(
  id int,
  stuName varchar(20),
  gender char(1),
  seat int,
  age int,
  major int,
  constraint pk primary key(id),
  constraint uq unique(seat),
  constraint ck check(gender='男' or gender='女'),
  constraint fk_stuinfo_major foreign key(majorid) references major(id)
);
```

* 主键和唯一的区别

  |约束类型|保证唯一性|是否允许为空|一个表中可以有多少个|是否允许组合|
  |:----|:----|:----|:----|:----|
  |主键|√|×|至多有1个|√，但不推荐|
  |唯一|√|√|可以有多个|√，但不推荐|
  >组合主键的意思就是多个字段组成一个主键约束，比如`primary key(id,stuname)`，组合主键仍是算成一个主键
  >组合唯一约束意思就是多个字段组成一个唯一约束，比如`unique(seat,seat2)`，组合唯一约束也仍算成一个约束，而非两个

* 外键使用的特点：
  1. 要求在从表设置外键关系
  2. 从表的外键列的类型和主表的关联列的类型要求一致或兼容，名称无要求
  3. 主表的关联列必须是一个key(一般是主键或唯一)
  4. 插入数据时，先插入主表，再插入从表
  5. 删除数据时，先删除从表，再删除主表

* 字段上的列级约束可以是多个，多个约束使用空格隔开即可

* 修改表时添加约束
  1. 添加列级约束
     `alter table 表名 modify column 字段名 字段类型 新约束;`
  2. 添加表级约束
     `alter table 表名 add [constraint 约束名] 约束类型(字段名) [外键的引用];`

  ```sql
  # 添加非空约束
  alter table stuinfo modify column stuname varchar(20) is not null;

  # 添加默认约束
  alter table stuinfo modify column age int default 18;

  # 添加主键
  alter table stuinfo modify column id int primary key;# 列级约束方式添加
  alter table stuinfo add primary key(id);# 表级约束方式添加
  ```

* 修改表时删除约束

  ```sql
  # 删除非空约束
  alter table stuinfo modify column stuname varchar(20) null;

  # 删除默认约束
  alter table stuinfo modify column age int;

  # 删除主键
  alter table sduinfo drop primary key;

  # 删除唯一
  alter table stuinfo drop index seat;

  # 删除外键
  alter table stuinfo drop foreign key fk_stuinfo_major;
  ```

### 标识列

* 标识列，即自增长列
* 创建表时设置标识列
* 设置自增长列后，插入数据时，不用赋值，即可自动写入值

```sql
create table tab_identity(
  id int primary key auto_increment,
  name varchar(20)
);
```

* 可以查看自增长的初始数字和增长量，但是mysql中无法修改初始值，可以修改增长量

  ```sql
  show variables like '%auto_increment%';
  set auto_increment_increment=3;# 设置增长量
  ```

  查询结果如下：
  |Variable_name|Value|
  |:----|:----|
  |auto_increment_increment|1|
  |auto_increment_offset|1|

* 想要修改起始增长值可以手动在插入数据是给自增长量字段设置值a，然后后面的数据插入时即使不赋值，增长的起始值是从a开始的

* 标识列的使用特点：
  1. 标识列必须和主键搭配吗？不一定，但要求是一个key(主键，外键，唯一三者之一即可)
  2. 一个表中可以有多少标识列？一个表中至多只能有一个标识列
  3. 标识列的类型？只能是数字类型，包括整型和浮点型
  4. 标识列可以通过设置参数改变增长量

* 修改表时设置标识列

  `alter table tab_identity modify column id int primary key auto_increment;`

* 修改表时删除标识列

  `alter table tab_identity modify column id int primary key;`

## TCL语言的学习

* TCL:事务控制语言(Transaction Control Language)
* 事务：一个或一组sql语句组成一个执行单元，这个执行单元要么全部执行，要么全部不执行
* 场合举例：转账，一个账户向另一个账户转钱，这涉及两条sql更新记录的语句，当一个sql执行成功，则会出现一个账户已经转账成功，即数值减少，此时如果另一个sql没有执行成功，则另一个账户的钱并不会增加，这就是问题。需要将他们作为一个事务来提交，要么全部执行，要么全部不执行(如果做到的呢，一个事务如果中间出现问题，则会进行事务回滚，像没有执行过一样)

* 存储引擎
  1. 在mysql中的数据用各种不同的技术存储在文件(或内存)中。
  2. 通过`show engines;`来查看mysql支持的存储引擎
  3. 在mysql中用的最多的存储引擎有`innodb`，`myisam`，`memory`等，其中`innodb`支持事务，而`myisam`，`memory`不支持事务

### 事务和事务的处理

* 事务的ACID属性
  1. 原子性(Atomicity)
     原子性是指事务是一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生
  2. 一致性(Consistency)
     事务必须使数据库从一个一致性状态变换到另外一个一致性状态
  3. 隔离性(Isolation)
     事务的隔离性是指一个事务的执行不能被其他事务干扰(这个具体会不会干扰取决于对数据库设置的隔离级别)，即一个事务内部的操作及使用的数据对并发的其他事务是隔离的，并发执行的各个事务之间不能互相干扰
  4. 持久性(Durability)
     持久性是指一个事务一旦被提交，它对数据库中数据的改变就是永久性的，接下来的其他操作和数据库故障不应该对其任何影响

* 事务的创建
  1. 隐式事务：事务没有明显的开启和结束的标记，比如insert、update、delete、select语句，即不包括DDL语句
  2. 显式事务：必须先设置自动提交功能为禁用

```sql
# 以下三种任一可开启事务
set autocommit=0;
start transaction;
begin;

# 多个语句
...

# 以下两种任一可事务提交
commit;
commit work;

# 以下两种任一可事务的回滚
rollback;
rollback work;
```

* 数据库同时运行多个事务，当这些事务访问数据库中相同的数据时，如果没有采取必要的隔离机制，就会导致各种并发问题：
  1. 脏读：对于两个事务T1，T2，T1读取了已经被T2更新但还没有被提交的字段，之后若T2回滚，T1读取的内容就是临时且无效的
  2. 不可重复读：对于两个事务T1，T2，T1读取了一个字段，然后T2更新了该字段，之后T1再读取同一个字段，值就不同了
  3. 幻读：对于两个事务T1，T2，T1从一个表中读取了一个字段，然后T2在该表中插入了一些新的行，之后如果T1再次读取同一个表，就会多出几行

* 数据库事务的隔离性：数据库系统必须具有隔离并发运行各个事务的能力，使它们不会相互影响，避免各种并发问题

* 数据库提供的4种事务隔离级别：

|隔离级别|描述|
|:----|:----|
|<pre>read uncommitted<br>(读未提交数据)</pre>|允许事务读取未被其他事务提交的更改、脏读、不可重复读和幻读的问题都会出现|
|<pre>read commited<br>(读已提交数据)</pre>|只允许事务读取已经被其他事务提交的变更，可以避免脏读，但不可重复读和幻读问题仍可能出现|
|<pre>repeatable read<br>(可重复读)</pre>|确保事务可以多次从一个字段中读取相同的值，在这个事务持续期间，禁止其他事务对这个字段进行更新，可以避免脏读和不可重复读，但幻读的问题仍然存在|
|<pre>serializable<br>(串行化)</pre>|确保事务可以从一个表中读取相同的行，在这个事务持续期间，禁止其他事务对该表执行插入，更新和删除操作，所有并发问题都可以避免，但性能十分低下|

* Oracle支持的2种事务隔离级别：`read commited`,`serializable`,Oracle默认的事务隔离级别为`read commited`

* MySQL支持4种事务隔离级别。MySQL默认的事务隔离级别为`repeatable read`

### 事务隔离级别设置

在MySQL数据库中查看当前事务的隔离级别：

```sql
select @@global.transaction_isolation;# 查看全局的隔离级别
select @@session.transaction_isolation;# 查看当前会话的隔离级别
select @@transaction_isolation;#查看当前使用的隔离级别

#mysql5.7.20之前方式，mysql8版本后已废除：
select @@tx_isolation;
select @@session.tx_isolation;
select @@global.tx_isolation;
```

在MySQL数据库中设置事务的隔离级别：

方式一：

|语法|作用域|
|:----|:----|
|<pre>set global transaction isolation level 隔离级别名称;</pre>|全局有效|
|<pre>set session transaction isolation level 隔离级别名称;</pre>|当前会话有效，即当前数据库连接有效|
|<pre>set transaction isolation level 隔离级别名称;</pre>|只对下一个单个事务有效。 无法通过select语句查看设置的事务隔离级别|

```sql
#示例：
set session transaction isolation level Read committed; # 对当前会话（连接）有效的事务隔离级别
```

方式二：直接设置系统变量

|语法|作用域|
|:----|:----|
|<pre>SET GLOBAL transaction_isolation = value</pre>|Global                                        |
|<pre>SET @@GLOBAL.transaction_isolation= value</pre>|Global                                        |
|<pre>SET SESSION transaction_isolation= value</pre>|Session                                       |
|<pre>SET @@SESSION.transaction_isolation= value</pre>|Session                                       |
|<pre>SET transaction_isolation= value</pre>|Session                                       |
|<pre>SET @@transaction_isolation= value</pre>|Next transaction only，无法通过select语句查看 |

```mysql
#示例：
SET transaction_isolation='repeatable-read'; -- 当前会话有效，注意：value使用的是字符串，并且单词间有横线-
#5.7.20版本之前方式：
SET tx_isolation='repeatable-read';
```

**注意：设置隔离级别必须在开启事务之前。**

> *"transaction_isolation was added in MySQL 5.7.20 as an alias for tx_isolation, which is now deprecated and is removed in MySQL 8.0. Applications should be adjusted to use transaction_isolation in preference to tx_isolation."*
>
>[设置事务隔离级别](https://dev.mysql.com/doc/refman/5.7/en/set-transaction.html)

* 回滚
  1. 设置保存点，在回滚的时候可以指定回到哪个保存点

```sql
set autocommit=0;
start transaction;
delete from account where id=25;
savepoint a;
delete from account where id=28;
rollback to a;
```

## 视图

* 视图：MySQL从5.0.1开始提供视图功能。一种虚拟存在的表，行和列的数据来自定义视图的查询中使用的表，并且是在使用视图时动态生成的，只保存了sql逻辑，不保存查询结果
* 应用场景：
  1. 多个地方用到了同样的查询结果
  2. 该查询结果使用sql语句较复杂

```sql
create view my_view
as
select stuname,majorname
from students s
inner join major m
on s.majorid=m.majorid
where s.majorid=1;
```

* 视图本质是保存了sql的逻辑，没有保存查询结果，不是真正的表，但是在使用的时候完全可以像表一样去操作(一般视图只进行查询)

* 视图的好处：
  1. 重用sql语句
  2. 简化复杂的sql操作，不必知道它的查询细节
  3. 保护数据，提高安全性

### 视图的修改

* 方式一：

  ```sql
  create or replace view 视图名  # 意思就是创建，如果存在则覆盖取代旧视图
  as
  查询语句;
  ```

* 方式二：

  ```sql
  alter view 视图名
  as
  查询语句;
  ```

### 删除视图

`drop view 视图1名,视图2名,...;`

### 查看视图的结构

`desc 视图名;`

### 视图的更新

* 插入数据
  `insert into 视图1(字段1,字段2,字段3) values(字段1值,字段2值,字段3值);`

* 修改数据
  `update 视图1 set 字段1=字段1值,字段2=字段2值 where 筛选条件;`

* 删除数据
  `delete from 视图1 where 筛选条件;`

* 视图的各种操作也会影响到原表的数据，会直接将原表数据进行修改
* 一般不会在视图上进行更新操作，也不太允许有这种操作
* 并不是所有的视图都可进行更新，取决于视图的定义，有些视图是无法进行更新操作，只能查看数据：
  1. 包含这些关键字的sql语句：分组函数、distinct、group by、having、union、union all
  2. 常量视图
  3. select中包含子查询
  4. join
  5. from一个不能更新的视图
  6. where子句的子查询引用了from子句中的表

```sql
# 视图来源的查询有分组
create or replace view myview1
as
select max(salary),department_id
from employees
group by department_id;

# 常量视图
create or replace view myview2
as
select 'Tom' name;

# select中包含子查询
create or replace view myview3
as
select (select max(salary) from employees) 最高工资;

# 视图来源中有join：这种视图可以修改但是不能插入
create or replace view myview4
as
select last_name,department_name
from employees e
join departments d
on e.department_id=d.department_id;

# from一个不能更新的视图：即视图的来源自前面的这些不能更新的视图
create or replace view myview5
as
select * from myview1; # 这里from来源是不能更新的视图

# where子句的子查询引用了from子句中的表
create or replace view myview
as
select last_name,email,salary
from employees
where employee_id in(
  select manager_id
  from employees
  where manager_id is not null
);
```

## 变量

* 系统变量：
  1. 全局变量
  2. 会话变量
* 自定义变量：
  1. 用户变量
  2. 局部变量

### 系统变量

* 变量由系统提供，不是用户定义，属于服务器层面
* 使用的语法：
  1. 查看所有的系统变量：`show global|session variables;`
  2. 查看满足条件的部分系统变量：`show global|session variables like '%char%';`
  3. 查看指定的某个系统变量的值：`select @@global|session.系统变量名;`
  4. 为某个系统变量赋值(两种方式)：
     * `set global|session 系统变量名=值;`
     * `set @@global|session.系统变量名=值;`

* 全局变量的作用域：
  服务器每次启动将为所有的全局变量赋初始值，针对于所有的会话(连接)有效，但不能跨重启服务器,即服务器重启后就会恢复默认值

  ```sql
  # 查看所有的全局变量
  show global variables;

  # 查看部分的全局变量
  show global variables like '%char%';

  # 查看指定的全局变量的值
  select @@global.autocommit;
  select @@tx_isolation;

  # 为某个指定的全局变量赋值
  set @@global.autocommit=0;
  ```

* 会话变量的作用域：
  仅仅针对于当前会话(连接)有效

  ```sql
  # 查看所有的会话变量
  show variables;
  show session variables;

  # 查看部分的会话变量
  show variables like '%char%';
  show session variables like '%char%';

  # 查看指定的某个会话变量
  select @@tx_isolation;
  select @@session.tx_isolation;

  # 为某个会话变量赋值
  set @@session.tx_isolation='read-uncommitted';
  set @@tx_isolation='read-uncommitted';
  set session tx_isolation='read-committed';
  ```

### 自定义变量

* 变量是用户自定义的，不是系统预定义的
* 使用步骤：声明->赋值->使用(查看、比较、运算等)

* 用户变量作用域：
  针对于当前会话(连接)有效，同于会话变量的作用域

```sql
# 赋值的操作符：  =或:=

# 声明并初始化
set @用户变量名=值;
set @用户变量名:=值;
select @用户变量名:=值;

# 赋值(更新用户变量的值)
  # 方式一:通过set或select
  set @用户变量名=值;
  set @用户变量名:=值;
  select @用户变量名:=值;

  # 方式二:通过select into
  select 表名A.字段 into @用户自定义的变量名 from 表名A;


# 举例
# 声明并初始化
set @name='Tom';
set @name=100;
set @count=1;

# 赋值
select count(*) into @count from employees;

# 查看
select @count;
```

* 局部变量的作用域：仅仅在定义它的begin end中有效
* 局部变量必须定义在begin end中的第一句话
* 局部变量需要限定类型

```sql
# 声明
declare 变量名 类型;
declare 变量名 类型 default 值;

# 赋值(更新局部变量的值)
  # 方式一:通过set或select
  set 局部变量名=值;
  set 局部变量名:=值;
  select @局部变量名:=值;

  # 方式二:通过select into
  select 表名A.字段 into @局部变量名 from 表名A;

# 使用
select 局部变量名;
```

## 存储过程和函数

* 存储过程和函数：类似于java中的方法
* 好处：
  1. 提高代码的复用性
  2. 简化操作

### 存储过程

* 含义：一组预先编译好的SQL语句的集合，理解成批处理语句
* 好处：
  1. 提高代码的重用性
  2. 简化操作
  3. 减少了编译次数并且减少了和数据库服务器的连接次数，提高了效率

* 创建语法：

  ```sql
  create procedure 存储过程名(参数列表)
  begin
    存储过程体(一组合法的SQL语句)
  end
  ```

* 注意：
  1. 参数列表包含三部分
     参数模式 参数名 参数类型
     举例：
     `in stuname varchar(20)`
  2. 参数模式:
     * `in`：该参数可以作为输入，也就是该参数需要调用方传入值
     * `out`：该参数可以作为输出，也就是该参数可以作为返回值
     * `inout`：该参数既可以作为输入又可以作为输出，也就是该参数既需要传入值，又可以返回值

  3. 如果存储过程体仅仅只有一句话，begin end可以省略
  4. 存储过程体中的每条SQL语句的结尾要求必须加分号
  5. 存储过程的结尾可以使用delimiter重新设置
     语法：
     `delimiter 结束标记`
     案例：
     `delimiter $`

* 调用语法
  `call 存储过程名(实参列表);`

* 空参的存储过程创建和调用

  ```sql
  # 定义
  delimiter $
  create procedure mypro1()
  begin
    insert into stu(username,password)
    values('tom','0000'),('tom','0000'),('tom','0000'),('tom','0000');
  end $

  # 调用
  call mypro1()$
  ```

* 创建带in模式参数的存储过程

  ```sql
  # 定义
  delimiter $
  create procedure mypro2(in employeeId varchar(20))
  begin
    select e2.*
    from employees e1
    inner join employees e2
    on e1.managerId=e2.employeeId;
    where e1.employeeId=employeeId;
  end $

  # 调用
  call mypro2('12345')$

  #创建存储过程实现：用户是否登录成功
  create procedure mypro3(in username varchar(20),in password varchar(20))
  begin
    declare result int default '';# 声明并初始化变量
    select count(*) int result # 变量赋值
    from admin
    where admin.username=username
    and admin.password=password;
    select if(result>0,'成功','失败');# 使用变量
  end $

  # 调用
  call mypro3('tom','1234')$
  ```

* 创建带inout模式的存储过程

  ```sql
  # 传入a和b两个值，最终a和b都翻倍并返回
  delimiter $
  create procedure mypro4(inout a int,inout b int)
  begin
    set a=a*2;
    set b=b*2;
  end $

  # 调用
  set @m=10$
  set @n=20$
  call mypro4(@m,@n)$
  select @m,@n$
  ```

* 删除存储过程
  语法：`drop procedure 存储过程名;`
  举例：`drop procedure mypro4;`

* 查看存储过程的信息
  只能通过命令：`show create procedure mypro3;`

### 函数

* 含义：一组预先编译好的SQL语句的集合，理解成批处理语句
* 优点：
  1. 提高代码的重用性
  2. 简化操作
  3. 减少编译次数并且减少了和数据库服务器的连接次数，提高了效率

* 区别：
  * 存储过程：可以有0个返回，也可以返回多个返回，适合做批量插入，批量更新
  * 函数：有且仅有1个返回，适合做处理数据后返回一个结果

* 创建语法：

  ```sql
  create function 函数名(参数列表) returns 返回类型
  begin
    函数体
  end
  ```

* 注意：
  1. 参数列表包含两部分：参数名，参数类型
  2. 函数体：肯定会有return语句，如果没有会报错；如果return语句没有放在函数体的最后也不抱错，但不建议
  3. 函数体中仅有一句话，则可以省略begin end
  4. 使用delimiter语句设置结束标记

* 调用语法
  `select 函数名(参数列表);`

* 示例：

```sql
# 返回公司的员工个数
delimiter $
create function myfun1() returns int
begin
  declare c int default 0;
  select count(*) into c
  from employees;
  return c;
end $

# 调用
select myfunc1()$

# 根据员工名，返回他的工资
create function myfunc2(empName varchar(20)) returns double
begin
  set @sal=0;
  select salary into @sal
  from employees
  where last_name=empName;
  return @sal;
end $

# 调用
select myfunc2('Tom')$


# 创建函数，实现传入两个float,返回两者之和
# 创建
create function float_sum(num1 float,num2 float) returns float
begin
  declare sum float default 0;
  set sum=num1+num2;
  return sum;
end $

# 调用
select float_sum(1,2)$
```

* 查看函数
  `show create functon 函数名;`

* 删除函数
  `drop function 函数名;`

## 流程控制结构

* 顺序结构：程序从上往下依次执行
* 分支结构：程序从两条或多条路径中选择一条去执行
* 循环结构：程序在满足一定条件的基础上，重复执行一段代码

### 分支结构

#### if函数

* 功能：实现简单的双分支，优点像java的三元运算符
* 语法：`if(表达式1,表达式2,表达式3);`
* 执行顺序：如果表达式1成立，则if函数返回表达式2的值，否则返回表达式3的值
* 应用：任何地方

#### case结构

* 情况一：类似于java中的switch语句，一般用于实现等值判断
  语法：

  ```sql
  case 变量|表达式|字段
  when 要判断的值a then 返回的值1[或返回的语句1];
  when 要判断的值b then 返回的值2[或返回的语句2];
  ...
  else 要返回的值n[或返回的语句n];
  end [case];
  ```

* 情况二：类似于java中的多重if语句，一般用于实现区间判断
  语法：

  ```sql
  case
  when 要判断的条件a then 返回的值1[或语句1];
  when 要判断的条件b then 返回的值2[或语句2];
  ...
  else 要返回的值n[或语句n];
  end [case];
  ```

  > 上面方括号的使用必须放在begin end中作为独立语句使用；不带方括号直接返回值则是可以使用在任何位置

* 特点：
  1. 可以作为表达式，嵌套在其他语句中使用，可以放在任何地方，begin end中或begin end的外面
  2. 可以作为独立的语句去使用，只能放在begin end中

```sql
delimiter $
create procedure test_case(in score int)
begin
  case
  when score>=90 and score<=100 then select 'A';
  when score>=80 then select 'B';
  when score>=60 then select 'C';
  else select 'D';
  end case;
end $

call test_case(64)$
```

#### if结构

* 功能：实现多重分支
* 语法：

  ```sql
  if 条件1 then 语句;
  elseif 条件2 then 语句2;
  ...
  [else 语句n;] # 可选
  end if;
  ```

* 应用在begin end中

```sql
create function test_if(score int) returns char
begin
  if score>=90 and score<=100 then return 'A';
  elseif score>=80 then return 'B';
  elseif socre>-70 then return 'C';
  else return 'D';
  end if;
```

### 循环结构

* 循环结构在`begin end`中使用
* 分类：`while`、`loop`、`repeat`
* 循环控制：
  1. `iterate`类似java中的`continue`,结束本次循环继续下一次
  2. `leave`类似于java中的`break`,结束当前所在的循环

* while
  语法：

  ```sql
  [标签:] while 循环条件 do
    循环体;
  end while [标签];
  ```

* loop
  语法：

  ```sql
  [标签:] loop
  循环体;
  end loop [标签];
  ```

* repeat
  语法：

  ```sql
  [标签:] repeat
    循环体;
    until 结束循环的条件
    end repeat [标签];
  ```

```sql
delimiter $
# while的使用：连续向表中插入100条数据
create procedure pro_while(in insertCount int)
begin
  declare i int default 1;
  while i<insertCount do
    insert into admin(username,password) values(concat('tom',i),'444'));
    set i=i+1;
  end while;
end $

call pro_while(100)$
```

```sql
# while的使用：插入20条数据后跳出循环
delimiter $
create procedure test_while1(in insertCount int)
begin
  declare i int default 1;
  a:while i<insertCount do
      insert into admin(username,password) values(concat('tom',i),'0000');
      if i>=20 then leave a;
      end if;
      set i=i+1;
  end while a;
end $

call test_while1(100)$
```

```sql
# while的使用：当i为偶数时插入数据
delimiter $
create procedure test_while1(in insertCount int)
begin
  declare i int default 0;
  a:while i<=insertCount do
      set i=i+1;
      if mod(i,2)!=0 then ITERATE a;
      end if;
      insert into admin(username,password) values(concat('tom',i),'0000');
      if i>=20 then leave a;
      end if;
  end while a;
end $

call test_while1(100)$
```