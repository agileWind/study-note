public class HomeWorkTest{
	public static void main(String[] args){
		/*
		float x =1.1f;
		double y=1.1;
		int a=1;
		long b=1L;
		char c='a';
		double a1=x+y*a/x+b/y+c;
		*/

		/*
		int a=8,b=33;
		int result1=a>>>b;
		int result2=(a>>>b)|1;
		System.out.println("result1="+result1+";result2="+result2);
		*/
		//result1=4;result2=5
		//8占四个bit位，每右移4次，回到原来的数字；32=4*8+1，相当于右移了1次，即result1=4

		/*
		int a=0x12;
		int b=a<<1;
		System.out.println(b);
		*/
		//36

		/*
		int i=2;
		i=i++;//todo
		int j=i++;
		System.out.println(i+","+j);
		*/
		//3,2

		/*
		int i=2;
		int j=i++;
		i=j;
		System.out.println(j);
		byte i=127;
		i=i+2;
		*/
		//i=-127;

		/*
		double s=(short)10/10.2*2;
		*/
		//要区分(short)(10/10.2*2)
		//short s1=(short)(10/10.2*2);

		/*
		int a=5;
		System.out.println((a%2==1)? (a+1)/2:a/2);
		*/
		//3

		/*
		char ch='\u0041';
		System.out.println(ch);
		*/
		/*
		byte a=100;
		byte b=(byte)(a+32);
		if(a<b){
			b++;
		}else{
			b--;
		}
		System.out.println(b);
		*/
		//-124
	}
}