public class Test {
    public static void main(String[] args) {
        Integer a=45;
        Integer c=new Integer(45);
        int b=45;
        System.out.println(a==b);
        System.out.println(b==c);
        System.out.println(a==c);
    }
}