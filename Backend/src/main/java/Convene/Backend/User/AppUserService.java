package Convene.Backend.User;

import Convene.Backend.Exception.CustomExceptions.AuthExceptions;
import Convene.Backend.SoftwareProject.SoftwareProjectRepository;
import Convene.Backend.Security.AuthResponse;
import Convene.Backend.Security.EmailVerification.EmailVerification;
import Convene.Backend.Security.EmailVerification.EmailVerificationDto;
import Convene.Backend.Security.EmailVerification.EmailVerificationService;
import Convene.Backend.Security.Jwt.JwtUtil;
import Convene.Backend.Security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "USER NOT FOUND";
    private final  static String  USERNAME_TAKEN = "USERNAME TAKEN";

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private SoftwareProjectRepository softwareProjectRepository;

    @Autowired
    EmailVerificationService emailVerificationService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG));
    }

    public String verifyEmail(EmailVerificationDto.EmailVerificationRequest validationRequest) throws Exception {
        String message = "";
        Boolean userExists = appUserRepository.findByEmail(validationRequest.getEmail()).isPresent();
        if (userExists){
            throw new AuthExceptions.UserExistsException();
        }
        else{
            emailVerificationService.saveVerificationRequest(validationRequest.getEmail());
            emailVerificationService.sendVerificationEmail(validationRequest.getEmail());
            message = "Verification code sent";
        }
        return message;
    }

    public String registerUser(AppUserDto.SignUpRequest signUpRequest) throws Exception {
        String message = "";
        Boolean userExists = appUserRepository.findByEmail(signUpRequest.getEmail()).isPresent();
        EmailVerification verification = emailVerificationService.loadVerificationRecord(signUpRequest.getEmail());

        if (userExists.booleanValue()){
            throw new AuthExceptions.UserExistsException();
        }
        if(!verification.getVerified()) {
            throw new AuthExceptions.InvalidVerificationCodeException();
        }

        String encodedPassword = securityUtil.passwordEncoder().encode(signUpRequest.getPassword());
        signUpRequest.setPassword(encodedPassword);
        AppUser newAppUser = new AppUser(signUpRequest);
        appUserRepository.save(newAppUser);
        message = signUpRequest.getEmail() + " Account registered";
        emailVerificationService.deleteVerificationRecord(verification.getEmail());
        return message;
    }

    public AuthResponse logIn(AppUserDto.LogInRequest logInRequest) throws Exception {
        if(!Util.validateEmail(logInRequest.getEmail())){
            throw new Exception("Invalid email");
        }
        UserDetails userDetails = null;
        String token = "";
        Boolean userExists =  appUserRepository.findByEmail(logInRequest.getEmail()).isPresent();
        if (userExists){
            userDetails  = loadUserByUsername(logInRequest.getEmail());
            if(securityUtil.passwordEncoder().matches(userDetails.getPassword(), logInRequest.getPassword())){
                token = jwtUtil.generateToken(userDetails);
            }
        }

        return new AuthResponse(token, !userDetails.isAccountNonLocked());
    }

}
