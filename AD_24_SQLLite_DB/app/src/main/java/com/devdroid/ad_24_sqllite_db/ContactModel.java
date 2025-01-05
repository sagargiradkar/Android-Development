package com.devdroid.ad_24_sqllite_db;

public class ContactModel {

    int id;
    String name , phone_no;
    public ContactModel(int id, String name, String phone_no) {
        this.id = id;
        this.name = name;
        this.phone_no = phone_no;
    }

    public ContactModel() {

    }
}
