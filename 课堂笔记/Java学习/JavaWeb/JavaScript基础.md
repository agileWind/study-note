# JavaScript基础

[TOC]
> 该篇是JavaScript最基本的语法介绍，没有涉及深入的JavaScript知识，且不全面、不完善：只有简单的语法入门知识，简单的DOM操作API介绍，简单的BOM操作API介绍。没有诸如原型链，闭包等知识

## 认识JavaScript

### JavaScript介绍

* JavaScript ( JS ) 是一种具有函数优先的轻量级，解释型或即时编译型的编程语言。虽然它是作为开发Web 页面的脚本语言而出名的，但是它也被用到了很多非浏览器环境中，例如 Node.js、 Apache CouchDB 和 Adobe Acrobat。JavaScript 是一种基于原型编程、多范式的动态脚本语言，并且支持面向对象、命令式和声明式（如函数式编程）风格。
  >JavaScript本身是一种面向对象的编程语言，只是它运行在浏览器环境，则添加了DOM,BOM操作的API；而当作为服务器端语言(node.js)运行时，则添加了针对网络，io，多线程等开发的API

* JavaScript 的标准是 ECMAScript 。截至 2012 年，所有的现代浏览器都完整的支持  ECMAScript 5.1，旧版本的浏览器至少支持 ECMAScript 3 标准。2015年6月17日，ECMA国际组织发布了 ECMAScript 的第六版，该版本正式名称为 ECMAScript 2015，但通常被称为 ECMAScript 6 或者 ES6。

### Web中的JavaScript

* javascript 有三部分构成，ECMAScript，DOM(Document Object Model)和BOM(Browser Object Model)，根据宿主（浏览器）的不同，具体的表现形式也不尽相同，ie和其他的浏览器风格迥异。

1. DOM 是 W3C 的标准；(所有浏览器公共遵守的标准)
2. BOM 是 各个浏览器厂商根据 DOM
在各自浏览器上的实现;(表现为不同浏览器定义有差别,实现方式不同)
3. **window 是 BOM 对象，而非 js 对象；**
4. DOM（文档对象模型）是 HTML 和 XML 的应用程序接口(API)
5. BOM 主要处理浏览器窗口和框架，不过通常浏览器特定的 JavaScript 扩展都被看做 BOM 的一部分。这些扩展包括：
   弹出新的浏览器窗口 移动、关闭浏览器窗口以及调整窗口大小 提供 Web 浏览器详细信息的定位对象 提供用户屏幕分辨率详细信息的屏幕对象 对 cookie 的支持 IE 扩展了 BOM，加入了 ActiveXObject 类，可以通过 JavaScript 实例化 ActiveX 对象

* javacsript是通过访问BOM（Browser Object Model）对象来访问、控制、修改客户端(浏览器)，由于BOM的window包含了document，window对象的属性和方法是直接可以使用而且被感知的，因此可以直接使用window对象的document属性，通过document属性就可以访问、检索、修改XHTML文档内容与结构。因为document对象又是DOM（Document Object Model）模型的根节点。可以说，BOM包含了DOM(对象)，浏览器提供出来给予访问的是BOM对象，从BOM对象再访问到DOM对象，从而js可以操作浏览器以及浏览器读取到的文档。其中
DOM包含：document

* Window对象包含属性：document、location、navigator、screen、history、frames Document根节点包含子节点：forms、location、anchors、images、links
  **从window.document已然可以看出，DOM的最根本的对象是BOM的window对象的子对象。**

> 区别：DOM描述了处理网页内容的方法和接口，BOM描述了与浏览器进行交互的方法和接口

### Web中JavaScript与HTML结合的使用方式

* 第一种方式：
  直接在HTML的页面上使用`<script>`标签，然后就可以将JavaScript代码写在标签内部，如下：

  ```html
  <!DOCTYPE html>
  <html lang="zh-CN">
      <head>
          <meta charset="UTF-8">
          <title>Title</title>
          <!--位置一-->
          <script type="text/javascript">
              console.log("Hello World");
              alert("Hello World");
          </script>
      </head>
      <body>
      </body>
      <!--位置二-->
      <script type="text/javascript">
          console.log("Hello World");
          alert("Hello World");
      </script>
  </html>
  ```

  特点：
  1. 这个标签可以放在HTML页面的任何位置(只要语法是正确的)，但是不同位置加载的时机不同，一般有两种位置：
     * 放在`<head>`标签里面，此时`<script>`标签里的js代码会在页面加载之前执行
     * 放在`<body>`标签外面，或者说是放在所有页面元素之下，此时`<script>`标签里的js代码会在页面加载之后执行
     > 这两个方式是有影响的，并不是随便哪个都可以，如果js代码里面有用到dom元素，此时将js代码放在head标签里面是不正确的，js代码执行会报错，因为此时页面元素都还没被解析和加载
  2. `<script>`标签的属性`type="text/javascript"`可以省略不写，此时js代码也会被正确执行，但是如果写上，一定要写正确，type的属性值如果书写错误，标签内部的js代码可能不会被执行(不同浏览器可能处理不一致)

* 第二种方式：
  将JavaScript代码放在一个`文件名.js`文件中，然后在HTML页面中引用这个js文件，如下：

  ```html
  <!DOCTYPE html>
  <html lang="zh-CN">
      <head>
          <meta charset="UTF-8">
          <title>Title</title>
          <!--位置一-->
          <script type="text/javascript" src="test.js"></script>
      </head>
      <body>
      <div id="testdiv"></div>
      </body>
      <!--位置二-->
      <script type="text/javascript" src="test.js"></script>
  </html>
  ```

  test.js文件里面的代码如下：

  ```javascript
  alert(document.getElementById("testdiv"));//使用弹出框输出dom元素中id为testdiv的元素对象
  ```

  特点：
  1. 通过`<script>`标签引用js文件与直接把js代码写在这里`<script>`标签内部的效果一致
  2. 多处引用同一个js文件，文档可能只会被浏览器向服务器端请求一次该资源(不一定，可以设置浏览器缓存)，但是这个js文件里的代码会被执行多次，即n处引用该js文件，则会执行n次这个js文件里面的js代码
  3. js文件的引用也可以放在HTML页面的任何位置，同样的是，引用的位置不同，也会使得js的执行时间不同
  4. 同样的是，`<script>`标签里的type属性也需要注意，要么省略type属性，要么将type属性的值书写正确

> 在同一个`<script>`标签中，以上两种方式只能任选一个，如果同时使用，它会去加载引用js文件，并执行js文件里的代码，而不会去执行`<script>`标签里面直接书写的js代码

```html
<script type="text/javascript" src="test.js">
    alert("我是script标签里面写的代码");//此处的代码不会被执行
</script>
```

## JavaScript基础语法

### JavaScript注释方式

* JavaScript语言里面的注释方法有三种：

  1. 第一种是多行注释`/**/`,一般js文件开头，介绍作者，函数等信息

     ```javascript
     /*
      * author:xxx
      * date:YYYY-MM-dd
      */
     function func(){

     }
     ```

  2. 第二种注释方法是最常见的`//`,在程序间随处可见,只能注释单行

     ```javascript
     function func(){
         alert(document.getElementById("testdiv"));//使用弹出框输出dom元素中id为testdiv的元素对象
     }
     ```

  3. 第三种注释，只能注释单行，不是很常见，会和html内的注释混淆，不推荐使用，第三种方法后面是不要加`-->`,这是和html注释不一样的地方。

     ```javascript
     <!-这是一行注释，只能注释单行
     ```

### JavaScript的变量和数据类型

* JavaScript的变量类型：
  1. 数值类型：number
  2. 字符串类型：string
  3. 布尔类型：boolean
  4. 对象类型：object
  5. 函数类型：function

* JavaScript里的特殊值：
  1. `undefined`：未定义，所有js变量未赋予初始值的时候，默认值都是`undefined`
  2. `null`：空值
  3. `NaN`：全称是not a number,即非数字，非数值

> 区别undefined和null
> `console.log(null==undefined)//true`
  `console.log(null===undefined)//false`
  观察可以发现：null和undefined 两者相等，但是当两者做全等比较时，两者又不等。
  一、原因：
  1.null： object类型，代表“空值”，代表一个空对象指针，
  2.undefined： undefined类型
  二、那到底什么时候是null,什么时候是undefined呢？
   null表示"没有对象"，即该处不应该有值。典型用法是：
（1） 作为函数的参数，表示该函数的参数不是对象。
（2） 作为对象原型链的终点。
   undefined表示"缺少值"，就是此处应该有一个值，但是还没有定义。典型用法是：
（1）变量被声明了，但没有赋值时，就等于undefined。 例如，
（2）调用函数时，应该提供的参数没有提供，该参数等于undefined。
（3）对象没有赋值的属性，该属性的值为undefined。
（4）函数没有返回值时或者return后面什么也没有，返回undefined。

### 变量定义与初始化

```javascript
var i=1;
console.log(i);//1
console.log(typeof(i));//number
var i="abc";//可以重复定义
console.log(i);//abc
console.log(typeof(i));//string
i=false;//变量可以重新赋值，且不限于数据类型，可以自动类型转换
console.log(i);//false
console.log(typeof(i));//boolean


abc=123;//定义变量不用写var
console.log(abc);//123

var num=123;
var bool=false;
var str="123";
var func=function(){
    console.log("this is a blank function");
}
var obj=new func();//this is a blank function
console.log(typeof(num));//number
console.log(typeof(bool));//boolean
console.log(typeof(str));//string
console.log(typeof(func));//function
console.log(typeof(obj));//object
var n=123*"abc";
console.log(n);//NaN
console.log(typeof(n));//number
```

* 从上面的定义变量的实例中可以看出，JavaScript对变量的定义和使用是十分灵活的：
  1. js中的变量定义是弱类型的，并不需要声明变量是哪种数据类型，直接使用关键字`var`即可
  2. 可以定义同名的变量，其实这样就相当于是覆盖了之前的变量，且这样写不会像Java那样报错
  3. 定义一个变量后，可以重新赋值，且可以赋不同数据类型的值，而不需要类型转换，js会自动转换数据类型
  4. 定义变量时也可以省略关键字`var`，这样就是一个全局变量了
  5. 虽然一个变量可以放任意数据类型的变量，但是这不代表当前变量存放的值就没有数据类型了，使用js内置提供的函数`typeof()`可以判断传入的参数变量的值是哪种数据类型

### JavaScript的运算符

#### 关系运算符

* 区分`==`和`===`
  1. 等于==:是简单的做字面值的比较
  2. 全等于===:除了做字面值的比较之外，还会比较两个变量的数据类型

```javascript
var a="123";
var b=123;
console.log(a==b); //true
console.log(a===b);//false
```

#### 逻辑运算符

* 逻辑运算符：
  1. 且运算：`&&`
  2. 或运算：`||`
  3. 非：`!`

* 逻辑运算的规则和Java，C的运算规则一致
* **在JavaScript语言中，所有的变量，都可以作为一个boolean类型的变量去使用**
* `0`、`null`、`undefined`、`""`(空串) ,这些都认为是`false`

  ```javascript
  if(!0)//true
      console.log("0在if里判断是false");
  if(!null)//true
      console.log("null在if里判断是false");
  if(!undefined)//true
      console.log("undefined在if里判断是false");
  if(!"")//true
      console.log("空串在if里判断是false");

  console.log(!0);//true
  console.log(!null);//true
  console.log(!undefined);//true
  console.log(!"");//true
  ```

* 且运算&&：
  1. 当两个都为真(true)时，返回第二个表达式的值
  2. 当其中有为假(false)时，返回第一个为假的表达式的值

  ```javascript
  console.log("abc"&&4);    //4
  console.log(null&&4);     //null
  console.log(4&&undefined);//undefined
  console.log(""&&null);    //""
  ```

* 或运算||:
  1. 当两个都为假(false)时，返回第二个为假的表达式的值
  2. 当其中有为真(true)时，返回第一个为真的表达式的值

  ```javascript
  console.log("abc"||4);     //abc
  console.log(null||4);      //4
  console.log(4||undefined); //4
  console.log(""||null);     //null
  ```

### JavaScript的数组

```javascript
var arr1=[];
var arr2=[1,"","123",false,null];

var arr3=new Array();
arr3.push(1);
arr3.push("");
arr3.push("123");
arr3.push(false);
arr3.push(null);
console.log(arr2);
console.log(arr3);
console.log(arr3==arr2);//false

var arr4=new Array(123,"abc",null);
```

* 使用`[]`定义的数组和使用`Array`对象定义的都是数组，且都能使用数组的API

* 数组属性：
  1. `length`

* 数组常用方法：

| 方法           | 描述                                                 |
| :------------- | :--------------------------------------------------- |
| concat()       | 连接两个或更多的数组，并返回结果。                   |
| copyWithin()   | 从数组的指定位置拷贝元素到数组的另一个指定位置中。   |
| entries()      | 返回数组的可迭代对象。                               |
| every()        | 检测数值元素的每个元素是否都符合条件。               |
| fill()         | 使用一个固定值来填充数组。                           |
| **filter()**   | 检测数值元素，并返回符合条件所有元素的数组。         |
| **find()**     | 返回符合传入测试（函数）条件的数组元素。             |
| findIndex()    | 返回符合传入测试（函数）条件的数组元素索引。         |
| **forEach()**  | 数组每个元素都执行一次回调函数。                     |
| from()         | 通过给定的对象中创建一个数组。                       |
| **includes()** | 判断一个数组是否包含一个指定的值。                   |
| **indexOf()**  | 搜索数组中的元素，并返回它所在的位置。               |
| isArray()      | 判断对象是否为数组。                                 |
| join()         | 把数组的所有元素放入一个字符串。                     |
| keys()         | 返回数组的可迭代对象，包含原始数组的键(key)。        |
| lastIndexOf()  | 搜索数组中的元素，并返回它最后出现的位置。           |
| **map()**      | 通过指定函数处理数组的每个元素，并返回处理后的数组。 |
| **pop()**      | 删除数组的最后一个元素并返回删除的元素。             |
| **push()**     | 向数组的末尾添加一个或更多元素，并返回新的长度。     |
| reduce()       | 将数组元素计算为一个值（从左到右）。                 |
| reduceRight()  | 将数组元素计算为一个值（从右到左）。                 |
| reverse()      | 反转数组的元素顺序。                                 |
| shift()        | 删除并返回数组的第一个元素。                         |
| **slice()**    | 选取数组的一部分，并返回一个新数组。                 |
| some()         | 检测数组元素中是否有元素符合指定条件。               |
| sort()         | 对数组的元素进行排序。                               |
| splice()       | 从数组中添加或删除元素。                             |
| toString()     | 把数组转换为字符串，并返回结果。                     |
| unshift()      | 向数组的开头添加一个或更多元素，并返回新的长度。     |
| valueOf()      | 返回数组对象的原始值。                               |

> 从上面提供的API可以看出，它们十分类似Java中StreamAPI，提供来对集合进行处理筛选，而JavaScript提供这些API来方便对数组元素进行筛选

### JavaScript的函数

* 定义有参函数

  ```javascript
  function func(a, b, c) {
      console.log(a);
  }
  ```

* 定义无参函数

  ```javascript
  function func(){
      console.log("无参函数");
  }
  ```

* 有返回值的函数:直接在函数体内使用return语句

  ```javascript
  //定义求和函数，传入两个数字类型变量，返回它们的和
  function sum(a, b) {
      if (typeof (a) == "number" && typeof (b) == "number") {
          return a + b;
      }
  }

  //使用
  console.log(sum(1.2, 3));
  ```

* 定义一个函数有两种方式

  ```javascript
  //第一种定义函数的方式
  function func1(a, b) {
      return a + b;
  }

  //第二种定义函数的方式
  var func2 = function (a, b) {
      return a + b;
  }

  //使用
  console.log(func1("abc", null));
  console.log(func2({
      "name": "1"
  }, 123));
  ```

  > 从上面可以看出，JavaScript中的运算符`+`有着与Java相同的作用，即**字符串连接符功能**

* JavaScript函数不能重载

  ```javascript
  function func(){
      console.log("第一个函数");
  }
  function func(){
      console.log("第二个函数");
  }
  func();//第二个函数
  ```

  在一个域中，不能有同名函数；如果有同名函数，则会覆盖上一个函数的定义

* 隐形参数

  虽然函数没有定义形参，但是实参传递进来时，不管形参是否定义，都可以通过`arguments`来获取传入的所有参数

  ```javascript
  function func() {
      console.log(arguments); //[Arguments] { '0': 'abc', '1': 332, '2': 3, '3': 'aa' }
      for (args in arguments) {
          console.log(arguments[args]);
      }
  }
  func("abc", 332, 3, "aa");
  ```

### JavaScript的对象

* 通过 JavaScript，能够定义并创建自己的对象，创建新对象有三种不同的方法：
  1. 使用 Object 定义并创建对象的实例
  2. 使用`{}`来定义并创建对象的实例
  3. 使用函数来定义对象，然后创建新的对象实例

```javascript
//使用{}来定义并创建对象的实例
var obj = {
    "name": "Tom",
    "age": 20,
    getName: function () {
        console.log(this.name);
    }
};
obj.getName(); //Tom

//使用 Object 定义并创建对象的实例
var obj2 = new Object();
obj2.name = "Jack";
obj2.age = 12;
obj2.getName = function () {
    console.log(this.name);
}
obj2.getName(); //Jack
```

>从上面的this可以看出，this的指向是调用当前函数的对象，即谁调用该函数体，则this指向谁

* 使用函数来定义对象，然后创建新的对象实例
  这种方式定义对象是最常用的方式，这种方式创建的对象，需要通过`new`关键字创建其实例

  ```javascript
  function Person(name,age){
      this.name;
      this.age;
      this.hello=function(){
          console.log("hello! I am "+name);
      }
  }

  var person=new Person("Smith",22);
  person.hello();
  ```

> JavaScript创建对象的方式还有很多，比如构造函数模式(上面例子就是)，工厂模式，原型模式，混合模式，而且每种模式都有优缺点，这里不再展开
> 一般来说，混合模式(构造函数模式+原型模式)使用最广泛，构造函数模式用于定义实例属性，原型模式用于定义方法和共享的属性
>JavaScript高级教程中会涉及到动态原型模式，寄生构造函数模式，稳妥构造函数模式，以及它们的优缺点

### JavaScript的事件

#### js事件绑定的几种方式

* 在JavaScript中，有三种常用的绑定事件的方法：
  1. 在DOM元素中直接绑定；
  2. 在JavaScript代码中绑定；
  3. 绑定事件监听函数。

* 在DOM元素中直接绑定
  这里的DOM元素，可以理解为HTML标签。JavaScript支持在标签中直接绑定事件，语法为：
  `<div onXXX="JavaScript Code"></div>`
  其中：
  onXXX 为事件名称。例如，鼠标单击事件 onclick ，鼠标双击事件 ondouble，鼠标移入事件 onmouseover，鼠标移出事件 onmouseout 等。JavaScript Code 为处理事件的JavaScript代码，一般是函数，函数也分为两种情况：
  1. 原生函数
     `<input onclick="alert('原生函数')" type="button" value="点击弹出警告框">`
  2. 自定义函数
     `<input onclick="func(this)" type="button" value="点击按钮">`
     然后在js代码里定义一个函数func

* 在JavaScript代码中绑定
  在JavaScript代码中绑定事件可以使JavaScript代码与HTML标签分离，文档结构清晰，便于管理和开发。
  在JavaScript代码中绑定事件的语法为：

  ```javascript
  elementObject.onXXX=function(){
    // 事件处理代码
  }
  ```

  其中：
  elementObject 为DOM对象，即DOM元素。
  onXXX 为事件名称。

* 绑定事件监听函数
  绑定事件的另一种方法是用 addEventListener() 或 attachEvent() 来绑定事件监听函数。
  1. addEventListener()函数语法：
     `elementObject.addEventListener(eventName,handle,useCapture);`

     参数说明:
     elementObject：DOM对象（即DOM元素）。
     eventName：事件名称。注意，这里的事件名称没有“ on ”，如鼠标单击事件 click ，鼠标双击事件 doubleclick ，鼠标移入事件 mouseover，鼠标移出事件 mouseout 等。
     handle：事件句柄函数，即用来处理事件的函数。
     useCapture：Boolean类型，是否使用捕获，一般用false 。这里涉及到JavaScript事件流的概念，后续章节将会详细讲解。

     ```javascript
     var submitBtn=document.getElementById("submitBtn");
     submitBtn.addEventListener("click",function(){
        //提交数据到后端
     });
     ```

  2. attachEvent()函数语法：
     `elementObject.attachEvent(eventName,handle);`
     参数说明:
     elementObject：DOM对象（即DOM元素）。
     eventName：事件名称。注意，与addEventListener()不同，这里的事件名称有“ on ”，如鼠标单击事件 onclick ，鼠标双击事件 ondoubleclick ，鼠标移入事件 onmouseover，鼠标移出事件 onmouseout 等。
     handle：事件句柄函数，即用来处理事件的函数。

  > 注意：事件句柄函数是指“ 函数名 ”，不能带小括号。
  >addEventListener()是标准的绑定事件监听函数的方法，是W3C所支持的，Chrome、FireFox、Opera、Safari、IE9.0及其以上版本都支持该函数；但是，IE8.0及其以下版本不支持该方法，它使用attachEvent()来绑定事件监听函数。所以，这种绑定事件的方法必须要处理浏览器兼容问题。

> 事件函数可以有一个参数，event对象，从这个对象可以获取到事件的具体信息

#### js常见的事件

* onload加载完成事件：页面(元素)加载完成之后，常用于做页面js代码初始化事件
* onclick单击事件：常用于按钮的点击响应事件
* onblur失去焦点事件：常用于输入框失去焦点后验证其输入内容是否合法
* onchange内容发送变化事件：常用于下拉列表和输入框内容发生改变后操作
* onsubmit表单提交事件：常用于表单提交前，验证所有表单项是否合法

### document对象

* DOM全称是Document Object Model，即文档对象模型
* 就是把文档中的标签，属性以及文本转换成对象来管理

* 通过可编程的对象模型，JavaScript 获得了足够的能力来创建动态的 HTML。
  1. JavaScript 能够改变页面中的所有 HTML 元素
  2. JavaScript 能够改变页面中的所有 HTML 属性
  3. JavaScript 能够改变页面中的所有 CSS 样式
  4. JavaScript 能够对页面中的所有事件做出反应

  ![html文档树](./images/htmltree.gif)

### window对象

* todo

### JavaScript正则表达式

* todo

### jQuery的使用

* todo
