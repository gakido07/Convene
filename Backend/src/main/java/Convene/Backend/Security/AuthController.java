package Convene.Backend.Security;

import Convene.Backend.Project.SoftwareProject.SoftwareProject;
import Convene.Backend.User.AppUser;
import Convene.Backend.User.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private SecurityUtil securityUtil;

    @PostMapping(path = "/signUp")
    public ResponseEntity<String> registerUser(@RequestBody AppUser.SignUpRequest signUpRequest) throws Exception {
        String result = appUserService.registerUser(signUpRequest);
        return new ResponseEntity<>(
                result.toString(),
                HttpStatus.resolve(200)
        );
    }
}
