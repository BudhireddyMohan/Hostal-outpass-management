package com.example.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.Models.OutpassHistoryEntry;

@Repository
public interface Outpasses extends MongoRepository<OutpassHistoryEntry, String> {
    // You can add custom query methods here if needed.


}

