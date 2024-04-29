package com.example.firstapp.data.parksTable;

public class Park
{
public String street;//رقم الشارع
public String city;// اسم المدينة
public int number;//عدد المواقف
    public double lat;//هذا خط العرض gps
    public double lng;//خط الطول
    public String userId;
    public String parkId;




    public Park() {

    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
                "Street='" + street + '\'' +
                ", City='" + city + '\'' +
                ", Number=" + number +
                ", lat=" + lat +
                ", lng=" + lng +
                ", userId='" + userId + '\'' +
                ", parkId='" + parkId + '\'' +
                '}';
    }
}
