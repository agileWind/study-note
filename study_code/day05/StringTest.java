public class StringTest {
    public static void main(String[] args){
        String str="Hello";
        changeStr(str);
        System.out.println(str);//输出：Hello
    }

    public static void changeStr(String str){
        str="hi";
    }
}
