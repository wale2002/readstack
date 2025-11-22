//package com.amigoscode.bookbuddybackend;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class BookBuddyBackendApplication {
//
//    public static void main(String[] args) {
//
//        SpringApplication.run(BookBuddyBackendApplication.class, args);
//    }
//
//}

package com.amigoscode.bookbuddybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;   // ← THIS WAS MISSING BEFORE

@SpringBootApplication
@EnableFeignClients   // ← THIS LINE ACTIVATES YOUR OPENLIBRARYCLIENT
public class BookBuddyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookBuddyBackendApplication.class, args);
    }
}