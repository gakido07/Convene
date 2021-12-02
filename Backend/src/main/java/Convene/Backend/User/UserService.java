package Convene.Backend.User;

import Convene.Backend.Security.AuthResponse;
import Convene.Backend.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

public class UserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG = "USER NOT FOUND";
    private final  static String  USERNAME_TAKEN = "USERNAME TAKEN";

    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG));
    }


    public String registerUser(AppUser.SignUpRequest signUpRequest) throws Exception {
        Boolean userExists =  userRepository.findByEmail(signUpRequest.getEmail()).isPresent();
        Boolean isEmailValid = Util.validateEmail(signUpRequest.getEmail());
        if(!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())){
            throw new Exception("Passwords must match");
        }
        if(!isEmailValid){
            throw new Exception("invalid email pattern");
        }
        String encodedPassword = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(signUpRequest.getPassword());
        AppUser newAppUser = new AppUser(signUpRequest);
        userRepository.save(newAppUser);

        return "User " + newAppUser.getUsername() + " registered";
    }


    public AuthResponse logIn(AppUser.LogInRequest logInRequest) throws Exception {
        if(!Util.validateEmail(logInRequest.getEmail())){
            throw new Exception("Invalid email");
        }
        UserDetails userDetails = null;
        String token = "";
        Boolean userExists =  userRepository.findByEmail(logInRequest.getEmail()).isPresent();
        if (userExists){
            userDetails  = loadUserByUsername(logInRequest.getEmail());
            if(PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(userDetails.getPassword(), logInRequest.getPassword())){
                token = jwtUtil.generateToken(userDetails);
            }

        }
        return new AuthResponse(userDetails.getUsername(), token, !userDetails.isAccountNonLocked());
    }

}
