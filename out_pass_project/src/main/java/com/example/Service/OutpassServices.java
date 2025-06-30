package com.example.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Repository.*;
import com.example.Models.*;;

@Service
public class OutpassServices {   

 public  @Autowired Outpasses outpasses; 
  public @Autowired UserRepository userRepository;
  public @Autowired AvlTreeService avltreeservice;

    //------------------------------Addingoutpass_in_database_outpasses--------------------------------------------------------
   
    public String Addingoutpass_in_database_outpasses(OutpassHistoryEntry entry) {
       outpasses.save(entry);
        return "ok";
    }


    //--------------------------------Addingoutpass_in_database_Users------------------------------------------------------------
   
    public String Addingoutpass_in_database_Users(String studentId,OutpassHistoryEntry entry){
        System.out.println("studentid="+studentId);
        User user = userRepository.findByUsername(studentId);
        if(user==null){
            return "user not founded";
        }
        List<OutpassHistoryEntry> history=user.getOutpassHistoryEntry();
        if(history==null){
            history=new ArrayList<>();
        }
        history.add(entry);
        user.setOutpassHistoryStack(history);
        userRepository.save(user);
        return "outpass is added to the user database";
        }

        //--------------------------------------------------aproving all the outpass and rejecting all the outpasses-----------
        public void approvealloutpasses(List<OutpassHistoryEntry> a,String status){
           for(OutpassHistoryEntry outpass:a){
            String id=outpass.getStudentId();
            User user = userRepository.findByUsername(id);
            List<OutpassHistoryEntry> history = user.getOutpassHistoryEntry();
            OutpassHistoryEntry latest = history.get(history.size() - 1);
             latest.setstatus(status);
             userRepository.save(user);
             String mongodbid=outpass.getId();
             System.out.println("mongodb id to delect="+mongodbid);
              outpasses.deleteById(mongodbid); 
              
              avltreeservice.status_updating_in_avltree(id,status);
           }
        }
}
