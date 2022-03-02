package Convene.Backend.SoftwareProject;

import Convene.Backend.AppUser.AppUser;
import Convene.Backend.Exception.CustomExceptions.SoftwareProjectExceptions;
import Convene.Backend.SoftwareProject.Issue.Issue;

import java.util.List;

public interface SoftwareProjectService {

    void deleteSoftwareProject(SoftwareProject softwareProject);

    SoftwareProject saveSoftwareProject(SoftwareProject softwareProject);

    public SoftwareProject findSoftwareProjectById(long id) throws Exception;

    public SoftwareProjectDto getSoftwareProjectDto(long id) throws SoftwareProjectExceptions.SoftwareProjectNotFound;

    public SoftwareProjectDto createProject(SoftwareProjectDto.CreateSoftwareProjectRequest request) throws Exception;

    public List<SoftwareProject> findSoftwareProjectsByAppUser(AppUser appUser) throws Exception;

    public Object[] getProjectIssueStatuses(long id) throws Exception;

    public void sendTeammateInviteEmail(long softwareProjectId, String email) throws Exception;

    void addTeammate(long softwareProjectId, String email) throws Exception;

    void sendTeammateInviteEmail(AppUser appUser, SoftwareProject softwareProject) throws Exception;


}
