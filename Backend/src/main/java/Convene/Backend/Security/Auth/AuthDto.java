package Convene.Backend.Security.Auth;

import Convene.Backend.User.AppUser;
import lombok.Getter;
import lombok.Setter;

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
}
