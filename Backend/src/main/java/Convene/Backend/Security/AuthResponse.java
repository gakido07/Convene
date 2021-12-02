package Convene.Backend.Security;

public class AuthResponse {
    private String email;
    private String token;
    private String message;
    private boolean locked;


    public AuthResponse(String email, String token, boolean locked) {
        this.email = email;
        this.token = token;
        this.locked = locked;
        if(token.length() > 0 && !locked){
            this.message = "Authentication Successful";
        }
        if(token.length()> 0 && locked){
            this.message = "Account locked";
        }
        if(token.length() < 1){
            this.message = "Authentication Failed";
        }
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }



}
