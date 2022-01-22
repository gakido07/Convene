package Convene.Backend.Security.Auth;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AuthDto {
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String token;


    public AuthDto(String email, String token) {
        this.email = email;
        this.token = token;
    }


    @Data
    @AllArgsConstructor
    @ToString
    public static class AppUserPrivileges {
        private List<Long> adminPrivileges;
        private List<Long> memberPrivileges;
    }
}
