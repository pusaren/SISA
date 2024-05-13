package com.example.sisa;

public class chatmisre {
    private String school,department,studentname,registrationnumber,phonenumber,unitcode,exam;

    public chatmisre(String school, String department, String studentname, String registrationnumber, String phonenumber, String unitcode, String exam) {
        this.school = school;
        this.department = department;
        this.studentname = studentname;
        this.registrationnumber = registrationnumber;
        this.phonenumber = phonenumber;
        this.unitcode = unitcode;
        this.exam = exam;
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

    public String getRegistrationnumber() {
        return registrationnumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getUnitcode() {
        return unitcode;
    }

    public String getExam() {
        return exam;
    }
}
