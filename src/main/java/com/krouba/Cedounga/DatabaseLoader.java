package com.krouba.Cedounga;

import com.krouba.Cedounga.User;
import com.krouba.Cedounga.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DatabaseLoader {
    private UserRepository userRepository;

    public DatabaseLoader(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Bean
    public CommandLineRunner initialiseDatabase(){
        return args -> {
            User user1 = new User("Jack", " Black", "Jackblack@gmail.com", "password123", "ADMIN");
            User user2 = new User("Denis", " Cool", "DenisCool@gmail.com", "password12", "ADMIN");
            User user3 = new User("Love", " Fool", "LoveFool@gmail.com", "password", "USER");
            User user4 = new User("Alex", " Green", "AlexGreen@gmail.com", "password20", "USER");

            userRepository.saveAll(List.of(user1, user2, user3, user4));

            System.out.println("sample database initializer");

        };
    }

}
