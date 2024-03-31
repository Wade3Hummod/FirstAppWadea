package com.example.firstapp.data.parksTable;

public class Park
{
public String Street;//رقم الشارع
public String City;// اسم المدينة
public int Number;//عدد المواقف
    public double lat;//هذا خط العرض gps
    public double lng;//خط الطول
    public String userId;
    public String parkId;




    public Park() {

    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }

    @Override
    public String toString() {
        return "Park{" +
                "Street='" + Street + '\'' +
                ", City='" + City + '\'' +
                ", Number=" + Number +
                ", lat=" + lat +
                ", lng=" + lng +
                ", userId='" + userId + '\'' +
                ", parkId='" + parkId + '\'' +
                '}';
    }
}
