package Convene.Backend.Security.Auth;

import Convene.Backend.Exception.CustomExceptions.AuthExceptions;
import Convene.Backend.Security.EmailVerification.EmailVerification;
import Convene.Backend.Security.EmailVerification.EmailVerificationDto;
import Convene.Backend.Security.EmailVerification.EmailVerificationService;
import Convene.Backend.Security.SecurityUtil;
import Convene.Backend.User.AppUserDto;
import Convene.Backend.User.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private EmailVerificationService verificationService;

    @Autowired
    private SecurityUtil securityUtil;

    @PostMapping(path = "/sign-up/email-verification")
    public ResponseEntity<String> registerUser(@RequestBody EmailVerificationDto.EmailVerificationRequest verificationRequest) throws Exception {
        String result = appUserService.verifyEmail(verificationRequest);
        return new ResponseEntity<>(
                result.toString(),
                HttpStatus.resolve(200)
        );
    }

    @PostMapping(path = "/sign-up/email-verification/verify-code")
    public ResponseEntity<EmailVerification> verifyEmail(@RequestBody EmailVerificationDto.CodeValidationRequest request) throws Exception {
       EmailVerification verificationDto = verificationService.verifyCode(request);

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
