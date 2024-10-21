package com.example.SecurityApp.сontrollers;

import com.example.SecurityApp.models.Person;
import com.example.SecurityApp.services.PeopleService;
import com.example.SecurityApp.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AppController {

    private final PeopleService peopleService;


    @Autowired
    public AppController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @GetMapping("/users")
    @ResponseBody
    public List<Person> users() {
        return peopleService.findAll();
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/users/{id}")
    public Map<String, Object> deleteUser(@PathVariable int id) {
        try {
            peopleService.deletePerson(id);
            return Map.of("message", "User deleted successfully");
        } catch (PersonNotFoundException e) {
            throw new PersonNotFoundException("User not found");
        } catch (Exception e) {
            return Map.of("error", "An unexpected error occurred", "status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }



}
