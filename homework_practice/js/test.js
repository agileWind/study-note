// var i=1;
// console.log(i);//1
// console.log(typeof(i));//number
// var i="abc";//可以重复定义
// console.log(i);//abc
// console.log(typeof(i));//string
// i=false;//变量可以重新赋值，且不限于数据类型，可以自动类型转换
// console.log(i);//false
// console.log(typeof(i));//boolean


// abc=123;
// console.log(abc);//123

// var num=123;
// var bool=false;
// var str="123";
// var func=function(){
//     console.log("this is a blank function");
// }
// var obj=new func();//this is a blank function
// console.log(typeof(num));//number
// console.log(typeof(bool));//boolean
// console.log(typeof(str));//string
// console.log(typeof(func));//function
// console.log(typeof(obj));//object
// var n=123*"abc";
// console.log(n);//NaN
// console.log(typeof(n));//number

// var a="123";
// var b=123;
// console.log(a==b);
// console.log(a===b);

// if(!0)//true
//     console.log("0在if里判断是false");
// if(!null)//true
//     console.log("null在if里判断是false");
// if(!undefined)//true
//     console.log("undefined在if里判断是false");
// if(!"")//true
//     console.log("空串在if里判断是false");

// console.log(!0);//true
// console.log(!null);//true
// console.log(!undefined);//true
// console.log(!"");//true

// console.log("abc"&&4);//4
// console.log(null&&4);//null
// console.log(4&&undefined);//undefined
// console.log(""&&null);//""


// console.log("abc"||4);     //abc
// console.log(null||4);      //4
// console.log(4||undefined); //4
// console.log(""||null);     //null



// var arr1=[];
// var arr2=[1,"","123",false,null];

// var arr3=new Array();
// arr3.push(1);
// arr3.push("");
// arr3.push("123");
// arr3.push(false);
// arr3.push(null);
// console.log(arr2);
// console.log(arr3);
// console.log(arr3==arr2);//false

// //定义求和函数，传入两个数字类型变量，返回它们的和
// function sum(a, b) {
//     if (typeof (a) == "number" && typeof (b) == "number") {
//         return a + b;
//     }
// }

// //使用
// console.log(typeof (11) === "number");

// function func(a, b, c) {
//     console.log(a);
// }


// function func1(a, b) {
//     return a + b;
// }

// var func2 = function (a, b) {
//     return a + b;
// }

// console.log(func1("abc", null));
// console.log(func2({
//     "name": "1"
// }, 123));

// function func(){
//     console.log("第一个函数");
// }
// function func(){
//     console.log("第二个函数");
// }
// func();//第二个函数


// function func() {
//     console.log(arguments); //[Arguments] { '0': 'abc', '1': 332, '2': 3, '3': 'aa' }
//     for (args in arguments) {
//         console.log(arguments[args]);
//     }
// }
// func("abc", 332, 3, "aa");

var obj = {
    "name": "Tom",
    "age": 20,
    getName: function () {
        console.log(this.name);
    }
};
obj.getName(); //Tom

var obj2 = new Object();
obj2.name = "Jack";
obj2.age = 12;
obj2.getName = function () {
    console.log(this);
}
obj2.getName(); //Jack


function Person(name,age){
    this.name;
    this.age;
    this.hello=function(){
        console.log("hello! I am "+name);
    }
}

var person=new Person("Smith",22);
person.hello();