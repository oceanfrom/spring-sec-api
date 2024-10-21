package com.example.SecurityApp.Controllers;

import com.example.SecurityApp.dto.AuthenticationDTO;
import com.example.SecurityApp.dto.PersonDTO;
import com.example.SecurityApp.models.Person;
import com.example.SecurityApp.security.JWTUtil;
import com.example.SecurityApp.services.PeopleService;
import com.example.SecurityApp.services.RegistrationService;
import com.example.SecurityApp.util.InvalidLoginException;
import com.example.SecurityApp.util.PersonAlreadyExistsException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final PeopleService peopleService;

    @Autowired
    public AuthController(RegistrationService registrationService, JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager, PeopleService peopleService) {
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.peopleService = peopleService;
    }


    @PostMapping("/register")
    public Map<String, String> performRegistration(@RequestBody @Valid PersonDTO personDTO,
                                                   BindingResult bindingResult) {

        Person person = convertToPersonDTO(personDTO);
        if (bindingResult.hasErrors()) {
            return Map.of("message", "Error");
        }

        if (peopleService.personExists(person.getUsername())) {
            throw new PersonAlreadyExistsException("User already exists with username: " + person.getUsername());
        }

        registrationService.register(person);

        return Map.of("message", "User registered successfully");
    }


    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(), authenticationDTO.getPassword());
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            throw new InvalidLoginException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return Map.of("jwt-token", token);
    }


    public Person convertToPersonDTO(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

}
