package Model;

public class Cart
{
    private String Pid, Pname, Price, Quantity, Discount;

    public Cart() {
    }

    public Cart(String Pid, String Pname, String Price, String Quantity, String Discount) {
        this.Pid = Pid;
        this.Pname = Pname;

        this.Price = Price;
        this.Quantity = Quantity;
        this.Discount = Discount;

    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String Pid) {
        this.Pid = Pid;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String Pname) {
        this.Pname = Pname;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String Discount) {
        this.Discount = Discount;
    }





}
