package Convene.Backend.SoftwareProject;

import Convene.Backend.AppUser.AppUser;
import Convene.Backend.Exception.CustomExceptions.SoftwareProjectExceptions;

import java.util.List;

public interface SoftwareProjectServiceImpl {
    public SoftwareProject findSoftwareProjectById(Long id) throws Exception;

    public SoftwareProjectDto getSoftwareProjectDto(Long id) throws SoftwareProjectExceptions.SoftwareProjectNotFound;

    public SoftwareProjectDto createProject(SoftwareProjectDto.CreateSoftwareProjectRequest request) throws Exception;

    public List<SoftwareProject> findSoftwareProjectsByAppUser(AppUser appUser) throws Exception;

    }
