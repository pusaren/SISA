package com.example.sisa;

public class nonmodel {
    String id,estatename, rnumber,phone,email,issue,role,date;

    public nonmodel() {

    }

    public nonmodel(String id, String estatename, String rnumber, String phone, String email, String issue, String role, String date) {
        this.id = id;
        this.estatename = estatename;
        this.rnumber = rnumber;
        this.phone = phone;
        this.email = email;
        this.issue = issue;
        this.role = role;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getEstatename() {
        return estatename;
    }

    public String getRnumber() {
        return rnumber;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getIssue() {
        return issue;
    }

    public String getRole() {
        return role;
    }

    public String getDate() {
        return date;
    }
}
