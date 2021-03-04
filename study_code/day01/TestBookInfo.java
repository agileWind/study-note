import org.omg.CORBA.PUBLIC_MEMBER;

public class TestBookInfo{
    public static void main(String[] args){
        BookInfo bookInfo=new BookInfo();
        //bookInfo.setAuthor("11");

    }
}

class BookInfo {
    private String bookName;
    private String author;
    private double price;
    private double salesVolume;
    private double inventory;

}

class EmployeeInfo{
    protected String number;
    protected String employeeName;
    protected String identityId;
    protected String phone;
    protected char gender;
    protected double weight;
    protected boolean isDismission;
}