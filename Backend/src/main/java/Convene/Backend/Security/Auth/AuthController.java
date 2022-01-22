package Convene.Backend.Security.Auth;

import Convene.Backend.Exception.CustomExceptions.AuthExceptions;
import Convene.Backend.Email.EmailVerification.EmailVerification;
import Convene.Backend.Email.EmailVerification.EmailVerificationDto;
import Convene.Backend.Email.EmailVerification.EmailVerificationService;
import Convene.Backend.Security.SecurityUtil;
import Convene.Backend.AppUser.AppUserDto;
import Convene.Backend.AppUser.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private AppUserService appUserService;

    private EmailVerificationService emailVerificationService;

    private SecurityUtil securityUtil;


    @Autowired
    public AuthController(AppUserService appUserService, EmailVerificationService emailVerificationService, SecurityUtil securityUtil) {
        this.appUserService = appUserService;
        this.emailVerificationService = emailVerificationService;
        this.securityUtil = securityUtil;
    }


    @PostMapping(path = "/sign-up/email-verification")
    public ResponseEntity<String> requestVerificationEmail(@RequestBody EmailVerificationDto.EmailVerificationRequest verificationRequest) throws Exception {
        String result = emailVerificationService.verifyEmail(verificationRequest);
        return new ResponseEntity<>(
                result.toString(),
                HttpStatus.resolve(200)
        );
    }

    @PostMapping(path = "/sign-up/email-verification/verify-code")
    public ResponseEntity<EmailVerification> verifyCode(@RequestBody EmailVerificationDto.CodeValidationRequest request) throws Exception {
       EmailVerification verificationDto = emailVerificationService.verifyCode(request);

        return new ResponseEntity<>(
               verificationDto,
                HttpStatus.ACCEPTED
        );
    }

    @PostMapping(path = "/sign-up/form")
    public ResponseEntity<String> signUpUser(@RequestBody AppUserDto.SignUpRequest request) throws Exception {
        String message = appUserService.registerUser(request);

        return new ResponseEntity<>(
                message,
                HttpStatus.ACCEPTED
        );
    }

    @PostMapping(path = "/sign-in")
    public ResponseEntity signIn(@RequestBody AppUserDto.LogInRequest request, HttpServletResponse response) throws Exception {
        AuthDto authDto = appUserService.logIn(request);

        if(authDto.getToken().length() < 1) {
            throw new AuthExceptions.InvalidAuthCredentialsException();
        }

        response.setHeader("Authorization", authDto.getToken());

        return ResponseEntity.accepted().build();
    }
}
