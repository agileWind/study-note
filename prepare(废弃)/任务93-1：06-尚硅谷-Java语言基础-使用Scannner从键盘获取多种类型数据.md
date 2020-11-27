```java
Scanner scanner=new Scanner(System.in);
System.out.print("请输入您的姓名：");
String username=scanner.next();
System.out.print("请输入您的年龄：");
int age=scanner.nextInt();
System.out.print("请输入您的体重：");
double weight=scanner.nextDouble();
System.out.println("是否已婚：");
boolean isMarried=scanner.nextBoolean();
System.out.println("您的个人信息是：");
System.out.println("姓名："+username);
System.out.println("年龄："+age);
System.out.println("体重："+weight);
System.out.println("是否已婚："+isMarried);
System.out.println(username=="张成扬");
scanner.close();

//Scanner没有提供获取char型数据的方法，只能提供获取一个字符串的方式来存
String gender_str=scanner.next();
char gender_ch=gender_str.charAt(0);
```

- 如果输入的数据类型与要求的类型不匹配会抛出异常(无法提升的变量)，比如要求输入整型数字，却输入了字符串

