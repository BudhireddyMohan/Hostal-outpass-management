package com.example.Controllers;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import com.example.Models.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Service.AvlTreeService;
import com.example.Service.OutpassServices;
import com.example.Repository.*;

import org.springframework.ui.Model;


@Controller
public class Outpasscontroller {
    public @Autowired Outpasses outpasses;
    public @Autowired UserRepository userrep;

         @GetMapping("/student-dashboard")
    public String showDashboard() {
        return "studentdashboard";
    }

     @GetMapping("/AdminDashBoard")
    public String showAdminPage(Model model) {
         int pendingCount = 0;
         int  approvedToday=0;
         Queue<OutpassHistoryEntry> queue = new LinkedList<>(outpasses.findAll());
          pendingCount=queue.size();//pending-----------------------
      LocalDate today = LocalDate.now();
           List<User> users = userrep.findAll();
    for (User user : users) {
        List<OutpassHistoryEntry> history = user.getOutpassHistoryEntry();
        if (history != null) {
            for (OutpassHistoryEntry h : history) {
                
                if (today.toString().equals(h.getoutpass_date()) && "accepted".equalsIgnoreCase(h.getstatus()) ) {
                    approvedToday=approvedToday+1;
                    System.out.println(h.getoutpass_date());
                }
            }
        }
    }
           model.addAttribute("pendingCount", pendingCount);
           model.addAttribute("approvedToday", approvedToday);
        return "AdminDashBoard";
    }


    //-------------------------submiting out pass--------------------------------------------------------------//
    @Autowired OutpassServices addoutpass;
    @Autowired AvlTreeService avl;
    @PostMapping("/submit-outpass")
    public String submitOutpassRequest(
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("reason") String reason,
           @RequestParam("studentId") String studentId,
            Model model) {
           String status="pending";
           String s=studentId;
            System.out.println("submit-outpass-webpagestudentid="+s);
        OutpassHistoryEntry entry=new OutpassHistoryEntry(s,date,time,reason,status);
        ArrayList<OutpassHistoryEntry> b=new ArrayList<>();
        b.add(entry);
       String a= addoutpass.Addingoutpass_in_database_outpasses(entry);
       //------------------------out pass added sucessfully to database outpasses-----------------------------------------
       System.out.println("out pass added sucessfully to database outpasses"+a);
       a=addoutpass.Addingoutpass_in_database_Users(s,entry);
       //------------------------out pass added sucessfully to database user----------------------------------------
         System.out.println("out pass added sucessfully to database to users"+a);
       a=avl.Addingoutpass_in_avl_tree(s,entry);
       //------------------------Addingoutpass_in_avl_tree"---------------------------------------
       System.out.println("Addingoutpass_in_avl_tree"+a);
        model.addAttribute("message", "Outpass request submitted successfully!");
         model.addAttribute("currentOutpass", entry);
        return "studentdashboard";
    }
}
