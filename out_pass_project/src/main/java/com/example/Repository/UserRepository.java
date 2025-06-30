package com.example.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.Models.*;

@Repository
public interface UserRepository extends MongoRepository<User,String>{
    User findByUsername(String username);
   // User findBystudent_InformationStudentId(int studentId);

    
}
