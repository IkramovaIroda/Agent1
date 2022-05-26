package com.example.app_weather.controller;

import com.example.app_weather.config.EmailConfig;
import com.example.app_weather.dto.LoginDTO;
import com.example.app_weather.security.JwtProvider;
import com.example.app_weather.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    final JwtProvider jwtProvider;
    final AuthService authService;
    final EmailConfig emailConfig;

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginDTO loginDTO) {
        //login qiladi Tizimda bor bo'lsa token generate qilishimz kerak

        UserDetails userDetails = authService.loadUserByUsername(loginDTO.getName());
        String token = jwtProvider.generateToken(loginDTO.getName());

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setText("Assalomu aleykum");
//        message.setSubject("Title bu !");
//        message.setSentDate(new Date());
//        message.setTo("irodaikramovaa@gmail.com");
//        message.setFrom("pdp.uz@gmail.com");
//
//        JavaMailSender mailSender = emailConfig.send();
//        mailSender.send(message);

        return ResponseEntity.ok().body(token);
    }




    //400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
