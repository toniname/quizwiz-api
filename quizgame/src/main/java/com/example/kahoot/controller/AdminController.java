package com.example.kahoot.controller;


import com.example.kahoot.model.Quiz;
import com.example.kahoot.model.Role;
import com.example.kahoot.model.User;
import com.example.kahoot.service.QuizService;
import com.example.kahoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private QuizService quizService;

    @Autowired
    private UserService userService; // Добавлено поле UserService


    // Проверка роли администратора
    private void checkAdminAccess(User user) {
        if (user.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
    }

    @PostMapping("/create-quiz")
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz, @RequestParam Long userId) {

        User user = userService.getUserById(userId); // Метод для получения пользователя
        checkAdminAccess(user);
        Quiz createdQuiz = quizService.createQuiz(quiz);
        return ResponseEntity.ok(createdQuiz);
    }
}

