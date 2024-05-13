package com.example.sisa;

import android.view.View;
import android.widget.Button;
import android.telephony.SmsManager;

public class codre {
    private String school,department,studentname,registrationnumber,phonenumber,unitcode,exam;
    private  Button approve;

    public codre(String school, String department, String studentname, String registrationnumber, String phonenumber, String unitcode, String exam) {
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
    public void setButtonClickListener(View.OnClickListener listener) {
        if (approve != null) {
            approve.setOnClickListener(listener);
        }
    }

    // Other getter methods for retrieving data...

    public void setApprove(Button approve) {
        Button approveButton = approve.findViewById(R.id.approve);
        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change button text
                approveButton.setText("Approved");
                String phoneNumber = "0729639619||0717791984"; // Replace with the desired phone number
                String message = "Your marks have been filled visit the portal to check if the missing marks have been approved."; // Replace with your desired message
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            }
        });
    }
}
