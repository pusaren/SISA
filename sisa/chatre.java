package com.example.sisa;

public class chatre {
    private String estatename,Rnumber,Phone,email,issue;

    public chatre(String estatename, String rnumber, String phone, String email, String issue) {
        this.estatename = estatename;
        this.Rnumber = rnumber;
        this.Phone = phone;
        this.email = email;
        this.issue = issue;
    }

    public String getEstatename() {
        return estatename;
    }

    public String getRnumber() {
        return Rnumber;
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
