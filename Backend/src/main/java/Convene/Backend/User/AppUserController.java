package Convene.Backend.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/{userId}")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping(path = "/projects")
    public AppUserDto getAppUserDto(@PathVariable String userId) {
        return appUserService.getUserSoftwareProjects(Long.valueOf(userId));
    }
}
