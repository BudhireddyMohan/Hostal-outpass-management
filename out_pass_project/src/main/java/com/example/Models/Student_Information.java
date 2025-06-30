package com.example.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student_Information{

    private int studentId;
    @JsonProperty("student_Name")
    private String student_Name;
    private String branch;
    private int year;
     @JsonProperty("hostal_Name")
    private String hostal_Name;
    @JsonProperty("room_num")
    private String room_num;
    private String phonenumber;
    private String username;
    private String password;

    public Student_Information() {}

    public Student_Information(int studentId, String student_Name, String branch,
                               int year, String hostal_Name, String room_num,
                               String phonenumber, String username, String password) {
        this.studentId = studentId;
        this.student_Name = student_Name;
        this.branch = branch;
        this.year = year;
        this.hostal_Name = hostal_Name;
        this.room_num = room_num;
        this.phonenumber = phonenumber;
        this.username = username;
        this.password = password;
    }

   public void setstudentId(int studentId){
    this.studentId=studentId;
   }
   public int getstudentId(){
        return studentId;
   }

   public void setstudent_Name(String student_Name){
       this.student_Name=student_Name;
   }
   public String getstudent_Name(){
          return student_Name;
   }

   public void setbranch(String branch){
   this.branch=branch;
   }
   public String getbranch(){
        return branch;
   }

   public void setyear(int year){
     this.year=year;
   }
   public int getyear(){
       return year;
   }

   public void sethostal_Name(String hostal_Name){
       this.hostal_Name=hostal_Name;
   }
   public String gethostal_Name(){
    return hostal_Name;
   }

   public void setroom_num(String room_num){
    this.room_num=room_num;
   }
   public String getroom_num(){
    return room_num;
   }

   public void setphonenumber(String phonenumber){
        this.phonenumber=phonenumber;
   }
   public String getphonenumber(){
    return phonenumber;
   }

    public String getusername(){
          return username;
    }
    public void setusername(String username){
          this.username=username;
    }

    public void setpassword(String password){
        this.password=password;
    }
    public String getpassword(){
        return password;
    }
}

