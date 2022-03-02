package Convene.Backend.SoftwareProject;

import Convene.Backend.AppUser.AppUserServiceImpl;
import Convene.Backend.Email.JavaMailSenderConfig;
import Convene.Backend.Exception.CustomExceptions.SoftwareProjectExceptions;
import Convene.Backend.AppUser.AppUser;
import Convene.Backend.SoftwareProject.Issue.CustomIssueStatus.CustomIssueStatus;
import Convene.Backend.SoftwareProject.Issue.Issue;
import Convene.Backend.SoftwareProject.SoftwareProjectRole.SoftwareProjectRoleRoleServiceImpl;
import com.sun.mail.smtp.SMTPSendFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SoftwareProjectServiceImpl implements SoftwareProjectService {

    private final SoftwareProjectRepository softwareProjectRepository;

    private final AppUserServiceImpl appUserServiceImpl;

    private final SoftwareProjectRoleRoleServiceImpl softwareProjectRoleService;

    private JavaMailSenderConfig mailSender;

    @Autowired
    public SoftwareProjectServiceImpl(SoftwareProjectRepository softwareProjectRepository, AppUserServiceImpl appUserServiceImpl,
                                      SoftwareProjectRoleRoleServiceImpl softwareProjectRoleService, JavaMailSenderConfig mailSender) {
        this.softwareProjectRepository = softwareProjectRepository;
        this.appUserServiceImpl = appUserServiceImpl;
        this.softwareProjectRoleService = softwareProjectRoleService;
        this.mailSender = mailSender;
    }

    @Override
    public void sendTeammateInviteEmail(AppUser appUser, SoftwareProject softwareProject) throws Exception {
        String message = "You have been invited to join Convene project " + softwareProject.getName() +
                " follow the link" + " " ;
        mailSender.sendSimpleMessage(appUser.getEmail(), "Convene Invitation to join" + " " + softwareProject.getName(), message);
    }

    @Override
    public void deleteSoftwareProject(SoftwareProject softwareProject) {
        softwareProjectRepository.delete(softwareProject);
    }

    @Override
    public SoftwareProject saveSoftwareProject(SoftwareProject softwareProject) {
        return softwareProjectRepository.save(softwareProject);
    }

    @Override
    public SoftwareProject findSoftwareProjectById(long id) throws Exception {
        return softwareProjectRepository.findById(id).orElseThrow(SoftwareProjectExceptions.SoftwareProjectNotFound::new);
    }

    @Override
    public SoftwareProjectDto getSoftwareProjectDto(long id) throws SoftwareProjectExceptions.SoftwareProjectNotFound {
        return new SoftwareProjectDto(
                softwareProjectRepository.findById(id).orElseThrow(
                SoftwareProjectExceptions.SoftwareProjectNotFound::new)
        );
    }

    @Override
    public List<SoftwareProject> findSoftwareProjectsByAppUser(AppUser appUser) {
        return softwareProjectRepository.findSoftwareProjectsByTeamMembersContains(appUser).orElse(new ArrayList<>());
    }

    @Override
    public SoftwareProjectDto createProject(SoftwareProjectDto.CreateSoftwareProjectRequest request) throws Exception {
        AppUser appUser = appUserServiceImpl.findAppUserById(request.getCreatorId());
        SoftwareProject newProject = new SoftwareProject(request, appUser);
        log.info("New Project: " + newProject, newProject);
        softwareProjectRepository.save(newProject);
        return new SoftwareProjectDto(newProject);
    }

    @Override
    public Object[] getProjectIssueStatuses(long id) throws Exception {
        SoftwareProject softwareProject = findSoftwareProjectById(id);
        Object[] issueStatuses = Arrays.stream(Issue.IssueStatus.values()).map(issueStatus -> issueStatus.name()).toArray();
        List<CustomIssueStatus> customIssueStatus = new ArrayList<>(softwareProject.getCustomIssueStatuses());
        if(customIssueStatus.size() < 1) {
            return issueStatuses;
        }
        else {
            List<Object> issueStatusList = Arrays.stream(issueStatuses).collect(Collectors.toList());
            customIssueStatus.forEach(status -> {
                issueStatusList.add(status.getIndex(), status.getName());
            });
            return issueStatusList.toArray();
        }
    }

    @Override
    public void sendTeammateInviteEmail(long softwareProjectId, String email) throws Exception {
        SoftwareProject softwareProject = findSoftwareProjectById(softwareProjectId);
        AppUser appUser = appUserServiceImpl.findAppUserByEmail(email);
        try {
            sendTeammateInviteEmail(appUser, softwareProject);
            softwareProject.addTeamInvitee(appUser);
            softwareProjectRepository.save(softwareProject);
        }
        catch (SMTPSendFailedException exception) {
            exception.printStackTrace();
            softwareProject.removeTeamInvitee(appUser);
            softwareProjectRepository.save(softwareProject);
        }
    }

    @Override
    public void addTeammate(long softwareProjectId, String email) throws Exception {
        SoftwareProject softwareProject = findSoftwareProjectById(softwareProjectId);
        AppUser appUser = appUserServiceImpl.findAppUserByEmail(email);
        if(softwareProject.getTeamInvitees().contains(appUser)){
            softwareProject.receiveTeammateInvite(appUser);
        }
    }
}
