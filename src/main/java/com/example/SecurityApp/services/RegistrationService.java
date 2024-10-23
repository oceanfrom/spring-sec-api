package com.example.SecurityApp.services;

import com.example.SecurityApp.models.Person;
import com.example.SecurityApp.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final PeopleRepository peopleRepository;

    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder, PeopleRepository peopleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.peopleRepository = peopleRepository;
    }

    @Transactional
    public void register(Person person) {
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);

        if (peopleRepository.findByUsername(person.getUsername()).isPresent()) {
            throw new RuntimeException("User with this username already exists");
        }
        peopleRepository.save(person);
    }

}
