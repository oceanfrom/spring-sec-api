package com.example.SecurityApp.services;

import com.example.SecurityApp.models.Person;
import com.example.SecurityApp.repositories.PeopleRepository;
import com.example.SecurityApp.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Optional<Person> findByUsername(String username) {
        return peopleRepository.findByUsername(username);
    }

    public void deletePerson(int id) {
        if (!peopleRepository.existsById(id)) {
            throw new PersonNotFoundException("User not found");
        }
        peopleRepository.deleteById(id);
    }

    public boolean personExists(String username) {
        return findByUsername(username).isPresent();
    }
}
