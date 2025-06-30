
package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.Models.OutpassHistoryEntry;
import com.example.Models.Student_Information;
import com.example.Repository.UserRepository;
import com.example.Service.Avl_Tree;
import com.example.Models.*;

@SpringBootApplication
public class App {
    public static void main(String[] args) {

       ApplicationContext context =SpringApplication.run(App.class, args);
       UserRepository studentdatabase=context.getBean(UserRepository.class);
       Avl_Tree avl=context.getBean(Avl_Tree.class);


       //---------------------------fetching all the details from database and forming node array---------------//
       ArrayList<User> a=new ArrayList<>(studentdatabase.findAll());
       for(int i=0;i<a.size();i++){
            User user=a.get(i);
             int studentId=user.getstudent_Information().getstudentId();
             System.out.println("adding value=="+studentId);
             Student_Information student_Information=user.getstudent_Information();
             List<OutpassHistoryEntry> history=user.getOutpassHistoryEntry();
              Stack<OutpassHistoryEntry> stack=new Stack<>();
             for(int j=0;j<history.size();j++){
                  stack.add(history.get(j));
             }
              Node temp=new Node(studentId, student_Information);
              temp.history=stack;
              avl.Insert(temp);  
       }

       avl.viewroot();
       

       
    }
}