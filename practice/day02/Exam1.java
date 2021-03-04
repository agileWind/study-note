public class Exam1 {
    public static void main(String[] args) {
		// Scanner sc=new Scanner(System.in);
		// String s1="abc";
		// String s3="abc";
		// String s2=sc.next();//输入字符串：abc
		// System.out.println(s1==s2);//false
		// System.out.println(s1==s3);//true
		// System.out.println(s1=="abc");//true
		// System.out.println(s2=="abc");//false
		// System.out.println("s2:"+s2);//s2:abc
		// System.out.println(s2.equals("abc"));//true
		// sc.close();
		int a=-4;
		int b=-a;
		System.out.println(-b);
	}

	public static void getNumber(){
		int num = 1234;
		//2.通过运算操作求出个位，十位，百位，千位
		int ge = (num%10)/1;
		int shi =(num%100)/10;
		int bai = (num%1000)/100;
		int qian = (num%10000)/1000;
        //(num%(10^n))/(10^(n-1))
		System.out.println(num + "这个四位数个位上的数字是：" + ge);
		System.out.println(num + "这个四位数十位上的数字是：" + shi);
		System.out.println(num + "这个四位数百位上的数字是：" + bai);
		System.out.println(num + "这个四位数千位上的数字是：" + qian);
	}
	void AA(){}
}