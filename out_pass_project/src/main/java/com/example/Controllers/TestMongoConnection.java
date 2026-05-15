package com.example.Controllers;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;

public class TestMongoConnection {
    public static void main(String[] args) {
        String uri = "mongodb+srv://Mohanbudhireddy:Mohan2004@cluster0.urlwavv.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            mongoClient.listDatabaseNames().forEach(System.out::println);
            System.out.println("✅ Connected successfully!");
        } catch (Exception e) {
            System.out.println("❌ Connection failed:");
            e.printStackTrace();
        }
    }
}
