package com.example.app_weather.controller;

import com.example.app_weather.dto.ApiResponse;
import com.example.app_weather.dto.UserDTO;
import com.example.app_weather.entity.User;
import com.example.app_weather.repository.UserRepository;
import com.example.app_weather.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_CRUD', 'EMPLOYEE_READ')")
    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok().body(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        Optional<User> byId = userRepository.findById(id);
        return ResponseEntity.status(byId.isEmpty() ?
                HttpStatus.NOT_FOUND : HttpStatus.OK).body(byId.orElse(new User()));
    }


    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.ok().body("User not Found");
        }
        userRepository.delete(user.get());
        return ResponseEntity.ok().body("Deleted");
    }

    @PostMapping("add")
    public ResponseEntity add(@RequestBody UserDTO dto) {
        ApiResponse add = userService.add(dto);
        return ResponseEntity.ok().body(add);
    }

    @PutMapping("/{id}")
    public ResponseEntity edit(@PathVariable Integer id, @RequestBody UserDTO dto) {
        ApiResponse response = userService.edit(id, dto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }


//    @PreAuthorize("hasAuthority('ADD_COMPANY')")
//    @GetMapping("/me")
//    public ResponseEntity getMe() throws ResourceNotFoundException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
//        if (user == null) {
//            throw new ResourceNotFoundException("User topilmadi!");
//        }
//        return ResponseEntity.ok(user);
//    }

    //    @Check(value = "ADD_COMPANY")
//    @PreAuthorize("hasAuthority('ADD_COMPANY')")
//    @GetMapping
//    public ResponseEntity getMe(@CurrentUser User user) {
//        return ResponseEntity.ok(user);
//    }
}
