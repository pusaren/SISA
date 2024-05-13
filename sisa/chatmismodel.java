package com.example.sisa;

public class chatmismodel {
    String id,school,department,studentname,regno,phonenumber,unitcode,cat,main,special,sup,retake,date ;

    public chatmismodel() {
    }

    public chatmismodel(String id, String school, String department, String studentname, String regno, String phonenumber, String unitcode, String cat, String main, String special, String sup, String retake, String date) {
        this.id = id;
        this.school = school;
        this.department = department;
        this.studentname = studentname;
        this.regno = regno;
        this.phonenumber = phonenumber;
        this.unitcode = unitcode;
        this.cat = cat;
        this.main = main;
        this.special = special;
        this.sup = sup;
        this.retake = retake;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getSchool() {
        return school;
    }

    public String getDepartment() {
        return department;
    }

    public String getStudentname() {
        return studentname;
    }

    public String getRegno() {
        return regno;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getUnitcode() {
        return unitcode;
    }

    public String getCat() {
        return cat;
    }

    public String getMain() {
        return main;
    }

    public String getSpecial() {
        return special;
    }

    public String getSup() {
        return sup;
    }

    public String getRetake() {
        return retake;
    }

    public String getDate() {
        return date;
    }
}
