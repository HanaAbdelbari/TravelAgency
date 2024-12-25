package usermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;
    private final UserDashboardService dashboardService;

    @Autowired
    public UserController(UserService userService, UserDashboardService dashboardService) {
        this.userService = userService;
        this.dashboardService = dashboardService;
    }


    // Register endpoint that accepts all necessary fields
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDTO registrationDTO) {
        User registeredUser = userService.registerUser(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
        return userService.authenticateUser(email, password)
                .map(user -> ResponseEntity.ok("Login successful"))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String newPassword, @RequestParam String token) {
        boolean success = userService.resetPassword(email, newPassword, token);
        if (success) {
            return ResponseEntity.ok("Password reset successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid reset token or email");
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestParam Long userId) {
        User user = userService.getUserProfile(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateUserProfile(@RequestBody User user) {
        User updatedUser = userService.updateUserProfile(user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/dashboard/{userId}")
    public ResponseEntity<UserDashboard> getUserDashboard(@PathVariable Long userId) {
        try {
            UserDashboard dashboard = dashboardService.getUserDashboard(userId);
            return ResponseEntity.ok(dashboard);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
