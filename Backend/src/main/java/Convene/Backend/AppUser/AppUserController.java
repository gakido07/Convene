package Convene.Backend.AppUser;

import Convene.Backend.SoftwareProject.SoftwareProjectDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}")
@Slf4j
public class AppUserController {

    private final AppUserServiceImpl appUserServiceImpl;

    @Autowired
    public AppUserController(AppUserServiceImpl appUserServiceImpl) {
        this.appUserServiceImpl = appUserServiceImpl;
    }

    @GetMapping(path = "/profile")
    public AppUserDto getAppUserDto(@PathVariable(name = "userId") long userId) {
        return appUserServiceImpl.getAppUserDto(userId);
    }

    @GetMapping(path = "/invites")
    public List<SoftwareProjectDto> getUserInvites(@PathVariable(name = "userId") long userId) throws Exception {
        return appUserServiceImpl.getUserInvites(userId);
    }

    @PutMapping(path = "/password-change")
    public AppUserDto.AppUserIdentificationDto changeAppUserPassword(@PathVariable(name = "userId") long userId, @RequestBody AppUserDto.ChangePasswordRequest request) throws  Exception {
        return appUserServiceImpl.changePassword(userId, request);
    }

}
