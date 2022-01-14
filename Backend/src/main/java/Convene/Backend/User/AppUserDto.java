package Convene.Backend.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
public class AppUserDto {
    String email;
    Set<Long> projects;

    @Data
    public static class SignUpRequest {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String confirmPassword;
        private Boolean joinProject;
    }

    @AllArgsConstructor
    @Data
    public class LogInRequest {
        private String email;
        private String password;
    }
}
