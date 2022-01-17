package Convene.Backend.User;

import Convene.Backend.Exception.CustomExceptions.AuthExceptions;
import Convene.Backend.SoftwareProject.SoftwareProjectRepository;
import Convene.Backend.Security.Auth.AuthDto;
import Convene.Backend.Security.EmailVerification.EmailVerification;
import Convene.Backend.Security.EmailVerification.EmailVerificationDto;
import Convene.Backend.Security.EmailVerification.EmailVerificationService;
import Convene.Backend.Security.Auth.Jwt.JwtUtil;
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
        return appUserRepository.findAppUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG));
    }

    public String verifyEmail(EmailVerificationDto.EmailVerificationRequest validationRequest) throws Exception {
        String message = "";
        Boolean userExists = appUserRepository.findAppUserByEmail(validationRequest.getEmail()).isPresent();
        if (userExists){
            throw new AuthExceptions.UserExistsException();
        }
        try {
            emailVerificationService.saveVerificationRequest(validationRequest.getEmail());
            emailVerificationService.sendVerificationEmail(validationRequest.getEmail());

            message = "Verification code sent";

        } catch (Exception e) {
            e.printStackTrace();
            //TODO Catch Error while sending error
        }

        return message;
    }

    public String registerUser(AppUserDto.SignUpRequest signUpRequest) throws Exception {
        String message = "";
        Boolean userExists = appUserRepository.findAppUserByEmail(signUpRequest.getEmail()).isPresent();

        //TODO Throw error when verification record not found
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

    public AuthDto logIn(AppUserDto.LogInRequest logInRequest) throws Exception {
        if(!Util.validateEmail(logInRequest.getEmail())){
            throw new Exception("Invalid email");
        }

        UserDetails userDetails = null;
        String token = "";
        Boolean userExists =  appUserRepository.findAppUserByEmail(logInRequest.getEmail()).isPresent();
        if (!userExists){
            throw new AuthExceptions.UserNotFoundException();
        }
        userDetails  = loadUserByUsername(logInRequest.getEmail());

        if(!userDetails.isAccountNonLocked()) {
            throw new AuthExceptions.AccountLockedException();
        }

        if(securityUtil.passwordEncoder().matches(logInRequest.getPassword(), userDetails.getPassword())){
            token = jwtUtil.generateToken(userDetails);
        }

        return new AuthDto(userDetails.getUsername(), token);
    }

    public AppUserDto getUserSoftwareProjects(Long id) {
        AppUserDto.AppUserDtoProjection appUserDtoProjection = appUserRepository.findAppUserProjectsById(id).orElseThrow(() -> new AuthExceptions.UserNotFoundException());
        return new AppUserDto(appUserDtoProjection);
    }

}
