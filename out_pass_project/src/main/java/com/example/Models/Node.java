package com.example.Models;

import java.util.*;

public class Node {
    public int studentId;
    public Student_Information student_Information;
    public Stack<OutpassHistoryEntry> history;
    public Node left;
    public Node right;
    public int height;

    
    public Node(int id, Student_Information student_Information) {
        this.studentId = id;
        this.student_Information = student_Information;
        this.history = new Stack<>();
        this.left = null;
        this.right = null;
        this.height = 1;  // default height in AVL
    }

      public void setstudentId(int studentId){
        this.studentId=studentId;
      }
      public int getstudentId(){
        return studentId;
      }

      public void setstudent_Information(Student_Information student_Information){
        this.student_Information=student_Information;
      }
      public Student_Information getstudent_Information(){
        return student_Information;
      }

    public void setaddOutpassHistoryEntry(OutpassHistoryEntry outpassHistoryEntry) {
    this.history.push(outpassHistoryEntry);
     }

     public Stack<OutpassHistoryEntry> getoutpassHistoryEntries(){
        return this.history;
    }

}

