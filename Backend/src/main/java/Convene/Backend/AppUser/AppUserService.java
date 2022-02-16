package Convene.Backend.AppUser;

import Convene.Backend.Exception.CustomExceptions.AuthExceptions;
import Convene.Backend.Security.Auth.AuthDto;
import Convene.Backend.Email.EmailVerification.EmailVerification;
import Convene.Backend.Email.EmailVerification.EmailVerificationService;
import Convene.Backend.Security.Auth.Jwt.JwtUtil;
import Convene.Backend.Security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AppUserService implements UserDetailsService, AppUserServiceImpl {

    private final static String USER_NOT_FOUND_MSG = "USER NOT FOUND";
    private final  static String  USERNAME_TAKEN = "USERNAME TAKEN";

    private final AppUserRepository appUserRepository;

    private final EmailVerificationService emailVerificationService;

    private final SecurityUtil securityUtil;

    private final JwtUtil jwtUtil;

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
        boolean userExists = appUserRepository.findAppUserByEmail(signUpRequest.getEmail()).isPresent();

        EmailVerification verification = emailVerificationService.loadVerificationRecord(signUpRequest.getEmail());

        if (userExists){
            throw new AuthExceptions.UserExistsException();
        }
        if(!verification.isVerified()) {
            throw new AuthExceptions.InvalidVerificationCodeException();
        }
        String encodedPassword = securityUtil.passwordEncoder().encode(signUpRequest.getPassword());
        signUpRequest.setPassword(encodedPassword);
        AppUser newAppUser = new AppUser(signUpRequest);
        appUserRepository.save(newAppUser);
        emailVerificationService.deleteVerificationRecord(verification.getEmail());
        return signUpRequest.getEmail() + " Account registered";
    }

    @Override
    public AppUser findAppUserById(long id) throws Exception {
        return appUserRepository.findById(id).orElseThrow(AuthExceptions.UserNotFoundException::new);
    }

    @Override
    public AuthDto logIn(AppUserDto.LogInRequest logInRequest) throws Exception {

        AppUser appUser = null;
        String token = "";
        boolean userExists =  appUserRepository.findAppUserByEmail(logInRequest.getEmail()).isPresent();
        if (!userExists){
            throw new AuthExceptions.UserNotFoundException();
        }
        appUser  = findAppUserByEmail(logInRequest.getEmail()).orElseThrow(AuthExceptions.UserNotFoundException::new);

        if(!appUser.isAccountNonLocked()) {
            throw new AuthExceptions.AccountLockedException();
        }

        if(securityUtil.passwordEncoder().matches(logInRequest.getPassword(), appUser.getPassword())){
            token = jwtUtil.generateToken(appUser);
        }

        return new AuthDto(appUser.getUsername(), token);
    }

    @Override
    public AppUserDto getAppUserDto(long id) {
        AppUserDto.AppUserDtoProjection appUserDtoProjection = appUserRepository.findAppUserProjectsById(id).orElseThrow(AuthExceptions.UserNotFoundException::new);
        return new AppUserDto(appUserDtoProjection);
    }

    @Override
    public AppUser changePassword(String currentPassword, String newPassword) {
        return null;
    }
}
