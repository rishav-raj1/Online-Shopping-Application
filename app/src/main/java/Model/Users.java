package Model;


public class Users
{
    private String Name, Phone, Password, Image, Address;

    public Users()
    {

    }

    public Users(String Name, String Phone, String Password, String Image, String Address) {
        this.Name = Name;
        this.Phone = Phone;
        this.Password = Password;
        this.Image = Image;
        this.Address = Address;
    }


    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = Image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }
}
