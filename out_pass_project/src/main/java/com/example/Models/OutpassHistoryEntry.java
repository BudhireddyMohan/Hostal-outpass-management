package com.example.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document("Outpasses")
public class OutpassHistoryEntry {
       @Id
    private String id;  // ✅ This is the unique MongoDB document ID (ObjectId)

    private String StudentId; // ✅ Student's college roll number
    @JsonProperty("outpass_date")
    private String outpass_date;
    @JsonProperty("outpass_time")
    private String outpass_time;
    private String reason;
    private String status;

    public OutpassHistoryEntry(){};
    public OutpassHistoryEntry(String StudentId,String out_date,String out_time,String reason,String status){
        this.StudentId=StudentId;
        this.outpass_date=out_date;
        this.outpass_time=out_time;
        this.reason=reason;
        this.status=status;
    }
    
    //setter getter method

 public void setId(String id){
    this.id = id;
}
public String getId(){
    return id;
}

public void setStudentId(String studentId){
     this.StudentId=studentId;
}
public String getStudentId(){
    return StudentId;
}

    public void setoutpass_date(String outpass_date){
         this.outpass_date=outpass_date;
    }
    public String getoutpass_date(){
        return outpass_date;
    }

    public void setoutpass_time(String outpass_time){
         this.outpass_time=outpass_time;
    }
    public String getoutpass_time(){
        return outpass_time;
    }

    public void setreason(String reason){
        this.reason=reason;
    }
    public String getreason(){
        return reason;
    }

    public void setstatus(String status){
 this.status=status;
    }
    public String getstatus(){
        return status;
    }



}
