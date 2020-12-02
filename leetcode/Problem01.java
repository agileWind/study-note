/**
 * 
 * 删除排序数组中的重复项
给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。

不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。

作者：力扣 (LeetCode)
链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x2gy9m/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

解题过程：
第一版：
让从[1,len-1]每个元素中的i,与[0,i-1]之间的每个数进行比较
如果有相同，则移动[i+1,len]之间的所有元素，向前移动一位（即覆盖了前面重复的元素那一位）
此时仍从下标i开始比较，如果比较[0,i-1]之间的元素没有相同的，则i++
继续这样下去

第二版：
没有考虑这个数组是有序数组，那么判断条件不能只是相同，从大小也能判断,而且第i个元素不用与前面的[0,i-1]个元素进行比较
只比较i与i-1这两个位置的元素大小即可
 */

public class Problem01{
    /*
     * 执行用时：243 ms, 在所有 Java 提交中击败了5.04%的用户
     * 内存消耗：40.3 MB, 在所有 Java 提交中击败了77.14%的用户
     */
    public static int removeDuplicates1(int[] nums) {
        int newLen=nums.length;
        for(int i=1;i<newLen;){
            boolean noRepeat=true;
            int temp=nums[i];
            for(int j=0;j<i;j++){
                if(temp==nums[j]){//如果i处的元素在[0,i-1]中已存在
                    for(int k=i+1;k<nums.length;k++){//[i+1,len-1]的元素全部向前移动一位
                        nums[k-1]=nums[k];
                    }
                    noRepeat=false;
                    newLen--;
                    break;
                }
            }
            if(noRepeat){
                i++;
            }
        }
        return newLen;
    }

    /*
     * 执行用时：159 ms, 在所有 Java 提交中击败了5.04%的用户
     * 内存消耗：40 MB, 在所有 Java 提交中击败了96.04%的用户
     */
    public static int removeDuplicates2(int[] nums) {
        int newLen = nums.length;
        for (int i = 1; i < newLen;) {
            if (nums[i] == nums[i-1]) {
                for (int k = i + 1; k < nums.length; k++) {
                    nums[k - 1] = nums[k];
                }
                newLen--;
            }else{
                i++;
            }
        }
        return newLen;
    }

    /*
     * 执行用时：133 ms, 在所有 Java 提交中击败了5.04%的用户
     * 内存消耗：40.4 MB, 在所有 Java 提交中击败了66.77%的用户
     */
    public static int removeDuplicates3(int[] nums) {
        int newLen,oldLen;
        newLen=oldLen = nums.length;
        for (int i = 1; i < newLen; i++) {
            if (nums[i] == nums[i - 1]) {
                int j = i;
                newLen--;
                while (j + 1 != oldLen && nums[j] == nums[j + 1]) {
                    j++;
                    newLen--;
                }
                for (int k = j + 1, h = i; k < oldLen; k++) {
                    nums[h++] = nums[k];
                }
                oldLen = newLen;
            }
        }
        return newLen;
    }

    /**
     *
     * 执行用时：130 ms, 在所有 Java 提交中击败了5.04%的用户
     * 内存消耗：40.2 MB, 在所有 Java 提交中击败了81.95%的用户
     */
    public static int removeDuplicates4(int[] nums) {
        int newLen, oldLen;
        newLen = oldLen = nums.length;
        for (int i = 0; i + 1 < newLen; i++) {
            int j;
            for (j = i + 1; j != oldLen && nums[j] == nums[j - 1]; j++) {
                newLen--;
            }
            for (int m = j, n = i + 1; m < oldLen; m++) {
                nums[n++] = nums[m];
            }
            oldLen = newLen;
        }
        return newLen;
    }

    /**
     * 
    */
    public static int removeDuplicates5(int[] nums){
        if(nums.length==0||nums==null){
            return 0;
        }
        int i=0;
        for(int j=1;j<nums.length;j++){
            if(nums[i]!=nums[j]){
                nums[++i]=nums[j];
            }
        }
        return i+1;
    }

    public static void main(String[] args) {
        int[] nums={1};
        int newLen=removeDuplicates5(nums);
        for(int i=0;i<newLen;i++){
            System.out.print(nums[i]+" ");
        }
    }

}