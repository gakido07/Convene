package Convene.Backend.AppUser;

import Convene.Backend.Exception.CustomExceptions.AppUserExceptions;
import Convene.Backend.Exception.CustomExceptions.AuthExceptions;
import Convene.Backend.Security.Auth.AuthDto;
import Convene.Backend.Email.EmailVerification.EmailVerification;
import Convene.Backend.Email.EmailVerification.EmailVerificationServiceImpl;
import Convene.Backend.Security.SecurityUtil;
import Convene.Backend.SoftwareProject.SoftwareProjectDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AppUserServiceImpl implements UserDetailsService, AppUserService {

    private final AppUserRepository appUserRepository;

    private final EmailVerificationServiceImpl emailVerificationServiceImpl;

    private final SecurityUtil securityUtil;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, EmailVerificationServiceImpl emailVerificationServiceImpl,
                              SecurityUtil securityUtil) {
        this.appUserRepository = appUserRepository;
        this.emailVerificationServiceImpl = emailVerificationServiceImpl;
        this.securityUtil = securityUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findAppUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User "+ email +" Not Found"));
    }

    @Override
    public AppUser findAppUserByEmail(String email) throws Exception {
        return appUserRepository.findAppUserByEmail(email).orElseThrow(AuthExceptions.UserNotFoundException::new);
    }

    @Override
    public Boolean doesUserExist(String email) throws Exception {
        return appUserRepository.findAppUserByEmail(email).isPresent();
    }

    @Override
    public void deleteAppUser(long id) throws Exception {
        appUserRepository.deleteAppUserById(id);
    }

    @Override
    public AppUser saveAppUser(AppUser appUser) throws Exception {
        appUserRepository.save(appUser);
        return appUser;
    }

    @Override
    public AppUser findAppUserById(long id) throws Exception {
        return appUserRepository.findById(id).orElseThrow(AuthExceptions.UserNotFoundException::new);
    }

    @Override
    public String registerUser(AppUserDto.SignUpRequest signUpRequest) throws Exception {
        boolean userExists = appUserRepository.findAppUserByEmail(signUpRequest.getEmail()).isPresent();
        EmailVerification verification = emailVerificationServiceImpl.findEmailVerificationRecord(signUpRequest.getEmail());

        if (userExists){
            throw new AuthExceptions.UserExistsException();
        }
        if(!verification.isVerified()) {
            throw new AuthExceptions.InvalidVerificationCodeException();
        }
        String encodedPassword = securityUtil.passwordEncoder().encode(signUpRequest.getPassword());
        signUpRequest.setPassword(encodedPassword);
        AppUser newAppUser = new AppUser(signUpRequest);
        saveAppUser(newAppUser);
        emailVerificationServiceImpl.deleteEmailVerification(newAppUser.getEmail());
        log.info("New User signed up with email " + newAppUser.getEmail());
        return signUpRequest.getEmail() + " Account registered";
    }

    @Override
    public AuthDto logIn(AppUserDto.LogInRequest logInRequest) throws Exception {
        String token = "";
        AppUser appUser  = findAppUserByEmail(logInRequest.getEmail());
        if(!appUser.isAccountNonLocked()) {
            throw new AuthExceptions.AccountLockedException();
        }
        if(securityUtil.passwordEncoder().matches(logInRequest.getPassword(), appUser.getPassword())){
            token = securityUtil.generateToken(appUser);
        }
        return new AuthDto(appUser.getUsername(), token, "");
    }

    @Override
    public AppUserDto getAppUserDto(long id) {
        AppUserDto.AppUserDtoProjection appUserDtoProjection = appUserRepository.findAppUserProjectsById(id).orElseThrow(AuthExceptions.UserNotFoundException::new);
        return new AppUserDto(appUserDtoProjection);
    }

    @Override
    public List<SoftwareProjectDto> getUserInvites(long id) throws Exception {
        return findAppUserById(id).getInvites().stream().map(SoftwareProjectDto::new).collect(Collectors.toList());
    }

    @Override
    public AppUserDto.AppUserIdentificationDto changePassword(long userId, AppUserDto.ChangePasswordRequest request) throws Exception {
        AppUser appUser = findAppUserById(userId);
        if(!securityUtil.passwordEncoder().matches(request.getCurrentPassword(), appUser.getPassword())) {
            throw new AppUserExceptions.PasswordChangeException();
        }
        appUser.setPassword(securityUtil.passwordEncoder().encode(request.getCurrentPassword()));
        return new AppUserDto.AppUserIdentificationDto(saveAppUser(appUser));
    }
}
