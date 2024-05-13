package com.example.sisa;

public class chatsmodel {
    String estatename, rnumber, Phone, email, issue;

    public chatsmodel() {
    }

    public chatsmodel(String estatename, String rnumber, String phone, String email, String issue) {
        this.estatename = estatename;
        this.rnumber = rnumber;
        Phone = phone;
        this.email = email;
        this.issue = issue;
    }

    public String getEstatename() {
        return estatename;
    }

    public String getRnumber() {
        return rnumber;
    }

    public String getPhone() {
        return Phone;
    }

    public String getEmail() {
        return email;
    }

    public String getIssue() {
        return issue;
    }
}
