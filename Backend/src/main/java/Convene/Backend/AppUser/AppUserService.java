package Convene.Backend.AppUser;

import Convene.Backend.Exception.CustomExceptions.AuthExceptions;
import Convene.Backend.SoftwareProject.SoftwareProjectRepository;
import Convene.Backend.Security.Auth.AuthDto;
import Convene.Backend.Email.EmailVerification.EmailVerification;
import Convene.Backend.Email.EmailVerification.EmailVerificationDto;
import Convene.Backend.Email.EmailVerification.EmailVerificationService;
import Convene.Backend.Security.Auth.Jwt.JwtUtil;
import Convene.Backend.Security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService implements UserDetailsService, AppUserServiceImpl {

    private final static String USER_NOT_FOUND_MSG = "USER NOT FOUND";
    private final  static String  USERNAME_TAKEN = "USERNAME TAKEN";

    private AppUserRepository appUserRepository;

    private EmailVerificationService emailVerificationService;

    private SecurityUtil securityUtil;

    private JwtUtil jwtUtil;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, EmailVerificationService emailVerificationService,
                          SecurityUtil securityUtil, JwtUtil jwtUtil) {
        this.appUserRepository = appUserRepository;
        this.emailVerificationService = emailVerificationService;
        this.securityUtil = securityUtil;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findAppUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG));
    }

    @Override
    public Optional<AppUser> findAppUserByEmail(String email) throws Exception {
        return appUserRepository.findAppUserByEmail(email);
    }

    @Override
    public String registerUser(AppUserDto.SignUpRequest signUpRequest) throws Exception {
        String message = "";
        Boolean userExists = appUserRepository.findAppUserByEmail(signUpRequest.getEmail()).isPresent();

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

    @Override
    public AppUser findAppUserById(Long id) throws Exception {
        return appUserRepository.findById(id).orElseThrow(() -> new AuthExceptions.UserNotFoundException());
    }

    @Override
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

    @Override
    public AppUserDto getAppUserDto(Long id) {
        AppUserDto.AppUserDtoProjection appUserDtoProjection = appUserRepository.findAppUserProjectsById(id).orElseThrow(() -> new AuthExceptions.UserNotFoundException());
        return new AppUserDto(appUserDtoProjection);
    }

}
