package com.bookstore.model;

public class Address {
    private String name;
    private String userEmail; // <--- This line is new!
    private String doorNo;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String pincode;
    private String country;
    private String phoneNumber;
    private String nearbyLocation; // <--- You mentioned these in AddressServlet, add if in DB
    private String placeType;      // <--- You mentioned these in AddressServlet, add if in DB

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUserEmail() { // <--- New Getter
        return userEmail;
    }
    public void setUserEmail(String userEmail) { // <--- New Setter
        this.userEmail = userEmail;
    }

    public String getDoorNo() { return doorNo; }
    public void setDoorNo(String doorNo) { this.doorNo = doorNo; }

    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }

    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    // If 'nearby_location' and 'place_type' are in your 'address' table,
    // you should also add them here with their getters and setters:
    public String getNearbyLocation() { return nearbyLocation; }
    public void setNearbyLocation(String nearbyLocation) { this.nearbyLocation = nearbyLocation; }

    public String getPlaceType() { return placeType; }
    public void setPlaceType(String placeType) { this.placeType = placeType; }
}