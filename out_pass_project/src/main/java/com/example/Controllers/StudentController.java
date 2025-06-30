package com.example.Controllers;
import com.example.Models.*;
import com.example.Repository.UserRepository;
import com.example.Service.AvlTreeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
        
      public  @Autowired  UserRepository studentdatabase;
    @GetMapping("/testing")
    public String testing(){
           return "Api is Running........";
    }
     

    public @Autowired AvlTreeService avl;
    @PostMapping("/view_current_outpass_status")
    public HashMap<String,String> view_current_outpass_status(@RequestBody HashMap<String, Integer> json){
           System.out.println("studentid="+json.get("studentid"));
            int student_id = json.get("studentid");
            HashMap<String,String> h=new HashMap<>();
            h =avl.view_current_outpass_status(student_id);
            return h;
    }


     
@PostMapping("/Outpass-history")
public List<OutpassHistoryEntry> Outpass_history(@RequestBody HashMap<String, Integer> json) {
    int student_id = json.get("studentid");
    return avl.outpasshistory(student_id); 
}

    }
    

