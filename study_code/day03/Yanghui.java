public class Yanghui {
    public static void main(String[] args) {
        printYanghui(10);
    }
    /** 打印杨辉三角
     * 第n行有n个数
     * 每行第一个数和最后一个数是1
     * 从第三行开始，一个位置的数=它上面的数+上面的数的左边数之和,即a[i][j]=a[i-1][j-1]+a[i-1][j]
    */
    public static void printYanghui(int row){
        //声明
        int[][] yanghui=new int[row][];
        //赋值
        for(int i=0;i<row;i++){
            yanghui[i]=new int[i+1];
            for(int j=0;j<i+1;j++){
                if(j==0||j==i){
                    yanghui[i][j]=1;
                }else{
                    yanghui[i][j]=yanghui[i-1][j]+yanghui[i-1][j-1];
                }
            }
        }
        //打印
        for(int i=0;i<yanghui.length;i++){
            for(int j=0;j<yanghui[i].length;j++){
                System.out.print(yanghui[i][j]+"\t");
            }
            System.out.println();
        }
    }

    /**
     * 创建一个长度为6的int型数组，要求数组元素的值都在1-30之间，且是随机赋值，但要求元素值各不相同
     */
    public static int[] createRandomNumArr(){
        int[] randomArr=new int[6];
        for(int i=0;i<6;){
            int oneNumber=(int)(Math.random()*30+1);
            boolean isRepeat=false;
            if(i!=0)
            for(int j=0;j<i;j++){
                if(randomArr[j]==oneNumber){
                    isRepeat=true;
                    break;
                }
            }
            if(!isRepeat){
                System.out.print(oneNumber+" ");
                randomArr[i]=oneNumber;
                i++;
            }
        }
        return randomArr;
    }
}
