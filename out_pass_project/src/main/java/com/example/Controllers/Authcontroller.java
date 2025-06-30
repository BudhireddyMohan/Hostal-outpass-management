package com.example.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import com.example.Models.*;
import com.example.Service.AvlTreeService;

@Controller
public class Authcontroller {

    @Autowired
    AvlTreeService avl;

    
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/login")
    @ResponseBody
    public HashMap<String, String> login(@RequestBody Login login) {

        System.out.println("username = " + login.getusername());
        System.out.println("password = " + login.getpassword());

        HashMap<String, String> response = new HashMap<>();

        // ----- Admin Login ----- //
        if (login.getusername().equals("Admin") && login.getpassword().equals("Admin@123")) {
            response.put("redirectingpage", "AdminDashBoard");
            response.put("Admin", "ok");
            return response;
        }

        try {
            int username = Integer.parseInt(login.getusername());
            String password = login.getpassword();
            Node node = avl.searchById(username);

            if (node == null) {
                response.put("redirectingpage", "/login");
                response.put("action", "userNotFounded");
                return response;
            }

            String storedPassword = node.getstudent_Information().getpassword();
            if (password.equals(storedPassword)) {
                int studentid = node.getstudent_Information().getstudentId();
                String studentNode = Integer.toString(studentid);
                String username1 = node.getstudent_Information().getstudent_Name();
               
                response.put("username", username1);
                response.put("StudentNode", studentNode);
                response.put("redirectingpage", "/student-dashboard");
                return response;
            } else {
                response.put("redirectingpage", "/login");
                response.put("action", "invalidPassword");
                return response;
            }

        } catch (NumberFormatException e) {
            response.put("redirectingpage", "/login");
            response.put("action", "invalidUsername");
            return response;
        }
    }

//========================logout pages======================================================
    @GetMapping("/logout-to-loginpage")
    public String showloginpage(){
        return "Login";
    }
    

  }

