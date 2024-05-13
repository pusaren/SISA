package com.example.sisa;
public class marksmodel {
    String id,school,department,studentname,regno,phonenumber,unitcode, selectedRadioButton,date;

    public marksmodel(String id, String school, String department, String studentname, String regno, String phonenumber, String unitcode, String selectedRadioButton, String date) {
        this.id = id;
        this.school = school;
        this.department = department;
        this.studentname = studentname;
        this.regno = regno;
        this.phonenumber = phonenumber;
        this.unitcode = unitcode;
        this.selectedRadioButton = selectedRadioButton;
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

    public String getSelectedRadioButton() {
        return selectedRadioButton;
    }

    public String getDate() {
        return date;
    }
}
