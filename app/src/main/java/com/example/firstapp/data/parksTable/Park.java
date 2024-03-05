package com.example.firstapp.data.parksTable;

public class Park
{
public String Street;//رقم الشارع
public String City;// اسم المدينة
public int Number;//عدد المواقف

    public Park(String street, String city, int number) {
        Street = street;
        City = city;
        Number = number;
    }

    public String getStreet() {
        return Street;
    }

    public String getCity() {
        return City;
    }

    public int getNumber() {
        return Number;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setNumber(int number) {
        Number = number;
    }

    @Override
    public String toString() {
        return "Park{" +
                "Street='" + Street + '\'' +
                ", City='" + City + '\'' +
                ", Number=" + Number +
                '}';
    }
}
