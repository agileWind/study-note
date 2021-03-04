public class TwoDimensionalArrayTest {
    /*
        练习1：用二维数组存储如下数据，并遍历显示
        a
        b,b,b
        c,c,c,c,c
        d,d,d,d,d,d
        练习2：用二维数组存储如下数据，并遍历显示
        1,2,3,4,5
        6,7,8,9,10
        11,12,13,14,15
        16,17,18,19,20
    */
    public static void main(String[] args){
        test1();
        //test2();
    }

    public static void test1(){
        char[][] chArr=new char[4][];
        for(int i=0;i<chArr.length;i++){
            chArr[i]=new char[i+2];
            for(int j=0;j<chArr[i].length;j++){
                chArr[i][j]=(char)(97+i);
            }
        }
        for(int i=0;i<chArr.length;i++){
            for(int j=0;j<chArr[i].length;j++){
                System.out.print(chArr[i][j]);
            }
            System.out.println();
        }
    }

    public static void test2(){
        
    }
}
