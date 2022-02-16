package Convene.Backend.SoftwareProject;

import Convene.Backend.AppUser.AppUser;
import Convene.Backend.Exception.CustomExceptions.SoftwareProjectExceptions;
import Convene.Backend.SoftwareProject.Issue.Issue;

import java.util.List;

public interface SoftwareProjectServiceImpl {

    public SoftwareProject findSoftwareProjectById(long id) throws Exception;

    public SoftwareProjectDto getSoftwareProjectDto(long id) throws SoftwareProjectExceptions.SoftwareProjectNotFound;

    public SoftwareProjectDto createProject(SoftwareProjectDto.CreateSoftwareProjectRequest request) throws Exception;

    public List<SoftwareProject> findSoftwareProjectsByAppUser(AppUser appUser) throws Exception;

    public Object[] getProjectIssueStatuses(long id) throws Exception;

    /*
    * TODO
    *  Write logic to add team member with email
    */

    public void addTeammate(String email) throws Exception;

}
