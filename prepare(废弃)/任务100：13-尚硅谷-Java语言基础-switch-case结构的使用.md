# 格式

```java
switch(表达式){
	case 常量1:
		执行语句1;
		break;//不写的话就会执行下面所有的语句，且不检查,直到遇到break才会停止；如果可能，也可以执行default里面的语句
	case 常量2:
		执行语句2;
		break;
    default:
		执行语句n;
		break;//写不写已经无所谓了，在这里
}
```

- 根据switch表达式中的值，一次匹配各个case中的常量。一旦匹配成功，则进入相应case结构中，调用其执行语句；当执行语句后，仍然继续向下执行，直到遇到break关键字或此switch-case结构末尾才结束。

- break可以在switch-case结构中使用，跳出switch-case结构。

- switch结构中的表达式，只能是如下6中：byte、short、int、枚举类型(JDK5.0新增)、char、String类型(JDK7.0新增)

       也就是说没有long、boolean类型，double、float等类型

- case位置只能写常量，不能写变量，更不能写表达式，也不能写boolean 类型

- default是可选的，而且位置没有要求，不一定必须放在最后，放在switch里面任何位置都可以，没有先后之分

