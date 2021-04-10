package Model;

public class AdminOrders
{
    private String Name, Phone, Address, City, State, Date, Time, TotalAmount,Statename,Pincode;

    public AdminOrders() {
    }

    public AdminOrders(String Name, String Phone, String Address, String City, String State, String Date, String Time,String Statename,String Pincode, String TotalAmount) {
        this.Name = Name;
        this.Phone = Phone;
        this.Address = Address;
        this.City = City;
        this.State = State;

        this.Statename = Statename;
        this.Pincode = Pincode;

        this.Date = Date;
        this.Time = Time;
        this.TotalAmount = TotalAmount;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
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




    public String getStatename() {
        return Statename;
    }

    public void setStatename(String Statename) {
        this.Statename = Statename;
    }



    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String Pincode) {
        this.Pincode = Pincode;
    }








    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.TotalAmount = totalAmount;
    }
}

