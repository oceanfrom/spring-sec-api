package com.example.SecurityApp.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonErrorResponse {
    private String message;
    private Long timestamp;
}
