package Convene.Backend.AppUser;

import Convene.Backend.Security.Auth.AuthDto;

import java.util.Optional;

public interface AppUserServiceImpl {

    Optional<AppUser> findAppUserByEmail(String email) throws Exception;

    public String registerUser(AppUserDto.SignUpRequest signUpRequest) throws Exception;

    public AppUser findAppUserById(Long id) throws Exception;

    public AuthDto logIn(AppUserDto.LogInRequest logInRequest) throws Exception;

    public AppUserDto getAppUserDto(Long id);

    }
