package com.example.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.Models.Node;
import com.example.Models.OutpassHistoryEntry;

@Service
public class AvlTreeService {

 public @Autowired Avl_Tree avl;

//---------------------------searching by id--------------------------------------------------//
    public Node searchById(int userid){
       Node curr=avl.getroot();
           System.out.println("curr="+curr.getstudentId());
       Stack<Node> stack=new Stack<>();
       while(!stack.isEmpty() || curr!=null){
        while(curr!=null){
            stack.add(curr);
            curr=curr.left;
        }
        curr=stack.pop();
        int id=curr.getstudent_Information().getstudentId();
        if(id==userid){
            return curr;
        }
        curr=curr.right;
       }   
        return null;
    }
//----------------------------------Addingoutpass_in_avl_tree-------------------------//

public String Addingoutpass_in_avl_tree(String s,OutpassHistoryEntry entry){
          int userid=Integer.parseInt(s);
          Node curr=searchById(userid);
          curr.history.push(entry);
           return "added sussfully";
}

//-----------------------------------public String view_current_outpass_status-----------------------------


public HashMap<String,String> view_current_outpass_status(int studentid){
    System.out.println("studentid in view_current_outpass_status="+studentid);
     Node curr=searchById(studentid);
      OutpassHistoryEntry e  = curr.history.peek();
      HashMap<String,String> h=new HashMap<>();
      h.put("outpassdate",e.getoutpass_date());
      h.put("outpasstime",e.getoutpass_time());
      h.put("reason",e.getreason());
      h.put("status",e.getstatus());
      return h;
}


public ArrayList<OutpassHistoryEntry> outpasshistory(int studentid) {
    Node curr = searchById(studentid);
    if (curr == null || curr.history == null) {
        return new ArrayList<>(); // Return empty list
    }
    // Convert Stack to List
    return new ArrayList<>(curr.history);
}

//----------------------------outpass status updating in tree----------------------------------------------
public void status_updating_in_avltree(String id,String status){
    Node curr=searchById(Integer.parseInt(id));
   OutpassHistoryEntry history = curr.getoutpassHistoryEntries().peek();
         history.setstatus(status);
         System.out.println("outpass status updating in tree"+history.getstatus());
}


//------------------------------------------search id user name-----------------------------------

public Node searchByName(String username){
Node curr=avl.getroot();
           System.out.println("curr="+curr.getstudentId());
       Stack<Node> stack=new Stack<>();
       while(!stack.isEmpty() || curr!=null){
        while(curr!=null){
            stack.add(curr);
            curr=curr.left;
        }
        curr=stack.pop();
        String name=curr.getstudent_Information().getstudent_Name();
        if(name.equals(username)){
            return curr;
        }
        curr=curr.right;
       }   
        return null;

}
}
