package com.bookstore.dao;

import com.bookstore.model.Address;

import java.sql.*;

public class AddressDAO {
    private Connection conn;

    public AddressDAO(Connection conn) {
        this.conn = conn;
    }

    public Address getAddressByUserEmail(String email) {
        Address address = null;
        try {
            String sql = "SELECT * FROM address WHERE user_email = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                address = new Address();
                address.setName(rs.getString("name"));
                address.setUserEmail(rs.getString("user_email"));
                address.setDoorNo(rs.getString("door_number"));
                address.setAddressLine1(rs.getString("address_line1"));
                address.setAddressLine2(rs.getString("address_line2"));
                address.setCity(rs.getString("city"));
                address.setState(rs.getString("state"));
                address.setPincode(rs.getString("pincode"));
                address.setCountry(rs.getString("country"));
                address.setPhoneNumber(rs.getString("phone_number"));
                address.setNearbyLocation(rs.getString("nearby_location"));
                address.setPlaceType(rs.getString("place_type"));
            }

            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return address;
    }
}
