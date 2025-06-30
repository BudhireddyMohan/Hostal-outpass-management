package com.example.Models;
import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "Students")
public class User {

    @Id
    private String id;
   private String username;
   private String password;
   @JsonProperty("student_Information")
   private Student_Information student_Information;
   @JsonProperty("OutpassHistoryStack")
    private List<OutpassHistoryEntry> OutpassHistoryStack;


    public User(){};
    public User(String username,String password,Student_Information student_Information,List<OutpassHistoryEntry> OutpassHistoryStack){
        this.username=username;
        this.password=password;
        this.student_Information=student_Information;
        this.OutpassHistoryStack=OutpassHistoryStack;
    }

    //getter setter

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

    public void setstudent_Information(Student_Information student_Information){
        this.student_Information=student_Information;
    }
    public Student_Information getstudent_Information(){
        return student_Information;
    }

    public void setOutpassHistoryStack(List<OutpassHistoryEntry> OutpassHistoryStack){
              this.OutpassHistoryStack=OutpassHistoryStack;
    }
    public List<OutpassHistoryEntry> getOutpassHistoryEntry(){
        return OutpassHistoryStack;
    }
}


