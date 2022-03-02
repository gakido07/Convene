package Convene.Backend.AppUser;

import Convene.Backend.Security.Auth.AuthDto;
import Convene.Backend.SoftwareProject.SoftwareProjectDto;

import java.util.List;
import java.util.Optional;

public interface AppUserService {

    AppUser findAppUserByEmail(String email) throws Exception;

    Boolean doesUserExist(String email) throws Exception;

    void deleteAppUser(long id) throws Exception;

    AppUser saveAppUser(AppUser appUser) throws Exception;

    public String registerUser(AppUserDto.SignUpRequest signUpRequest) throws Exception;

    public AppUser findAppUserById(long id) throws Exception;

    public AuthDto logIn(AppUserDto.LogInRequest logInRequest) throws Exception;

    public AppUserDto getAppUserDto(long id);

    public List<SoftwareProjectDto> getUserInvites(long id) throws Exception;

    public AppUserDto.AppUserIdentificationDto changePassword(long userId, AppUserDto.ChangePasswordRequest request) throws Exception;
}
