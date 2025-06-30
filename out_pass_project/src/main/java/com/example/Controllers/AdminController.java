package com.example.Controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;


import com.example.Models.*;
import com.example.Repository.Outpasses;
import com.example.Repository.UserRepository;
import com.example.Service.AvlTreeService;
import com.example.Service.Avl_Tree;
import com.example.Service.OutpassServices;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5500") 
public class AdminController {
 @Autowired AvlTreeService avl;
 public @Autowired UserRepository studentdatabase;
 public @Autowired Avl_Tree avltree;
  @Autowired Outpasses outpasses;
   @Autowired UserRepository userrep;
   @Autowired OutpassServices outpasssseServices;


    @GetMapping("/add-student-page")
    public String loadAddStudentPage() {
        return "AddStudent"; // no .html extension needed
    }


    @GetMapping("managastudentspgae")
    public String managastudentspgae(){
        return "ManageStudent";
    }

    @GetMapping("/viewtreepage")
    public String viewtreepage(){
        return "AvlTreeView";
    }
   //---------------------------------adding student to database and avl tree---------------------------
    
    
  @ResponseBody
    @PostMapping("/addstudent")
    public String Addstudent(@RequestBody User user) {
        studentdatabase.save(user);
      int studentId = user.getstudent_Information().getstudentId();

Student_Information student_Information = user.getstudent_Information();
List<OutpassHistoryEntry> history = user. getOutpassHistoryEntry(); 

Stack<OutpassHistoryEntry> stack = new Stack<>();
Node temp = new Node(studentId, student_Information);
temp.history = stack;

avltree.Insert(temp);  // Insert into AVL tree
 System.out.println(studentId+"==studentId added to the avl tree");
        return "inserted ok";
    }

    //----------------------------------Displaying the outpass details in queue-------------------------

 
 
 @GetMapping("/PendingOutpassesRequests")
public String loadPendingOutpassesRequests(Model model) {
        Queue<OutpassHistoryEntry> outpassQueue = new LinkedList<>(outpasses.findAll());
         
        
        List<HashMap<String, String>> pendingRequests = new ArrayList<>();

        while (!outpassQueue.isEmpty()) {
            OutpassHistoryEntry u = outpassQueue.poll();
               System.out.println(u.getId());
            if (!"pending".equalsIgnoreCase(u.getstatus())) continue;
            
           // Node curr = avl.searchById(u.getStudentId());
            Node curr = avl.searchById(Integer.parseInt(u.getStudentId()));

            if (curr == null) continue;

            HashMap<String, String> entry = new HashMap<>();
            entry.put("mongodbid",u.getId());
            entry.put("studentname", curr.getstudent_Information().getstudent_Name());
            entry.put("studentid", String.valueOf(curr.getstudent_Information().getstudentId()));
            entry.put("branch", curr.getstudent_Information().getbranch());
            entry.put("outpassdate", u.getoutpass_date());
            entry.put("outpasstime", u.getoutpass_time());
            entry.put("reason", u.getreason());

            pendingRequests.add(entry);
        }

        model.addAttribute("pendingOutpasses", pendingRequests);
        return "PendingOutpassesRequests"; // Thymeleaf will load PendingOutpassesRequests.html
    }

 //----------------------------------Accepting and rejecting the out passes by warden-------------------------
 


@PostMapping("/accept-reject-outpasses")
@ResponseBody
public String acceptReject(@RequestBody HashMap<String, String> s) {
    String id = s.get("studentid");
    String status = s.get("status");
    String mongodbid=s.get("mongodbid");
    System.out.println("mongodbid="+mongodbid);
    User user = userrep.findByUsername(id);
    if (user == null) {
        return "User not found";
    }

    List<OutpassHistoryEntry> history = user.getOutpassHistoryEntry();
    if (history != null && !history.isEmpty()) {
        OutpassHistoryEntry latest = history.get(history.size() - 1);
        latest.setstatus(status); // update status

        user.setOutpassHistoryStack(history); // update user doc
        userrep.save(user); // save user

      //--------------delecting from outpass database---------------------------------  
        outpasses.deleteById(mongodbid); 
        //-----------------status updating in avl-tree-------------------------------
        avl.status_updating_in_avltree(id,status);
        return "Status updated";
    }

    return "No outpass history found";
}
//-------------------------------/Approve-Reject-all-passes-------------------------------------------
@PostMapping("/Approve-all-passes")
@ResponseBody // Add this!
public String approveAll(@RequestBody HashMap<String, String> request) {
    String status = request.get("status");
    List<OutpassHistoryEntry> a = outpasses.findAll();
    outpasssseServices.approvealloutpasses(a, status);
    return "All outpasses updated successfully.";
}

//----------------------------------------------
@GetMapping("/Number-of-PendingOutpassesRequests")
public String PendingOutpassesRequests(Model model) {
    int approvedToday = 0;

    Queue<OutpassHistoryEntry> outpassQueue = new LinkedList<>(outpasses.findAll());
    int pendingCount = outpassQueue.size();

    System.out.println("pendingCount == " + pendingCount);

    // Initialize empty list to avoid null errors in Thymeleaf
    List<HashMap<String, String>> pendingRequests = new ArrayList<>();

    model.addAttribute("pendingOutpasses", pendingRequests); // still needed even if empty
    model.addAttribute("pendingCount", pendingCount);

    return "PendingOutpassesRequests";
}

public boolean isNumeric(String str) {
    if (str == null || str.trim().isEmpty()) return false;
    try {
        Integer.parseInt(str); // or Double.parseDouble for decimals
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}

@PostMapping("/searchstudentdetails")
@ResponseBody
public HashMap<String, String> searchstudentdetails(@RequestBody HashMap<String, String> studentData) {
    String student = studentData.get("id");
    HashMap<String ,String> s = new HashMap<>();
    Node curr;

    if (isNumeric(student)) {
        int studentId = Integer.parseInt(student);
        curr = avl.searchById(studentId);
    } else {
        curr = avl.searchByName(student);
    }

    if (curr != null) {
        s.put("studentid", String.valueOf(curr.student_Information.getstudentId()));
        s.put("studentname", curr.student_Information.getstudent_Name());
        s.put("phonenumber", curr.student_Information.getphonenumber());
        s.put("branch", curr.student_Information.getbranch());
        s.put("hostalname", curr.student_Information.gethostal_Name());
        s.put("password", curr.student_Information.getpassword());
        s.put("roomnumber", curr.student_Information.getroom_num());
        s.put("year", String.valueOf(curr.student_Information.getyear()));
    }

    return s;
}


//@CrossOrigin(origins = "http://127.0.0.1:5500") 
@GetMapping("/treeview")
@ResponseBody
public Node treeview(){
    System.out.println(" avltree.getroot()=="+ avltree.getroot());
   return  avltree.getroot();
}

}


 
