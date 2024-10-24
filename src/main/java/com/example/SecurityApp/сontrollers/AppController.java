package com.example.SecurityApp.сontrollers;

import com.example.SecurityApp.dto.PersonDTO;
import com.example.SecurityApp.models.Person;
import com.example.SecurityApp.services.PeopleService;
import com.example.SecurityApp.util.PersonNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AppController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @Autowired
    public AppController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @GetMapping("/users")
    @ResponseBody
    public List<PersonDTO> users() {
        return peopleService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/users/{id}")
    public Map<String, Object> updateUser(@PathVariable int id,
                                          @RequestBody @Valid PersonDTO personDTO,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Map.of("message", "Validation failed", "status", HttpStatus.BAD_REQUEST.value());
        }

        try {
            Person existingPerson = peopleService.findById(id)
                    .orElseThrow(() -> new PersonNotFoundException("User not found"));

            Person updatedPerson = convertToPerson(personDTO);
            existingPerson.setUsername(updatedPerson.getUsername());
            existingPerson.setPassword(updatedPerson.getPassword());
            existingPerson.setRole(updatedPerson.getRole());

            peopleService.updatePerson(existingPerson);

            return Map.of("message", "User updated successfully");
        } catch (PersonNotFoundException e) {
            throw new PersonNotFoundException("User not found");
        }
    }

    private PersonDTO convertToDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }
}
