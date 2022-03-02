package Convene.Backend.Security.Auth;

import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@ToString
public class AuthDto {
    private String email;
    private String accessToken;
    private String refreshToken;


    @Data
    @AllArgsConstructor
    @ToString
    public static class AppUserPrivileges {
        private List<Long> adminPrivileges;
        private List<Long> memberPrivileges;
    }
}
