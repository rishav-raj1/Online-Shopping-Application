package Model;

public class Products
{
    private String Pname, Description, Price, Image, Category, Pid, Date, Time;

    public Products()
    {

    }

    public Products(String Pname, String Description, String Price, String Image, String Category, String Pid, String Date, String Time) {
        this.Pname = Pname;
        this.Description = Description;
        this.Price = Price;
        this.Image = Image;
        this.Category = Category;

        this.Pid = Pid;
        this.Date = Date;
        this.Time = Time;
    }


    public String getPname() {
        return Pname;
    }

    public void setPname(String Pname) {
        this.Pname = Pname;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String Pid) {
        this.Pid = Pid;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }





}
