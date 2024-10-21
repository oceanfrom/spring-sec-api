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
    private final PasswordEncoder passwordEncoder;
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PasswordEncoder passwordEncoder, PeopleRepository peopleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.peopleRepository = peopleRepository;
    }

    public void addPerson(Person person) {
        String password = passwordEncoder.encode(person.getPassword());
        person.setPassword(password);
        peopleRepository.save(person);
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        return peopleRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not found with id: " + id));
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
