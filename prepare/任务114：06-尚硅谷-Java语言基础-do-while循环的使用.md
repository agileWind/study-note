# 循环语句的四个组成部分

- ①初始化部分

- ②循环条件部分：boolean类型

- ③循环体部分

- ④迭代部分

①

do{

③；

④；

}while(②);

执行过程：①-><span style="color:red">③->④->②</span>-><span style="color:green">③->④->②</span>->......->③->④->②

- do-while循环至少可以执行一次循环体和迭代部分

- 如果循环体可以执行多次的时候，do……while循环和while循环效果是一样的，但是当可能会出现循环条件一开始就为false的情况下，则结果不同，do……while循环会执行一次，而while循环则一次也不执行

- 开发中，for循环和while循环的使用多余do……while循环的使用频率

