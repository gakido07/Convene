package Convene.Backend.SoftwareProject;

import Convene.Backend.Models.Project;
import Convene.Backend.SoftwareProject.Issue.CustomIssueStatus.CustomIssueStatus;
import Convene.Backend.SoftwareProject.Issue.Issue;
import Convene.Backend.SoftwareProject.SoftwareProjectRole.SoftwareProjectRole;
import Convene.Backend.SoftwareProject.Sprint.Sprint;
import Convene.Backend.AppUser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "software_project")
public class SoftwareProject extends Project{
    @Id
    @SequenceGenerator(
            name = "project_sequence",
            sequenceName = "project_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "project_sequence"
    )
    @Getter
    private long id;

    @NotNull(message = "Software project name can't be null")
    private String name;

    private Date initiationDate;

    private String description;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    @Getter
    @OneToMany(mappedBy = "softwareProject", cascade = CascadeType.ALL)
    private Set<SoftwareProjectRole> roles;

    @Getter
    @OneToMany(mappedBy = "softwareProject", cascade = CascadeType.ALL)
    private Set<Issue> issues;

    @Getter
    @OneToMany(mappedBy = "softwareProject", cascade = CascadeType.ALL)
    private Set<Sprint> sprints;

    @Getter
    @OneToMany(mappedBy = "softwareProject", cascade = CascadeType.ALL)
    private Set<CustomIssueStatus> customIssueStatuses;

    @Getter @Setter
    @ManyToMany
    @JoinTable(
            name = "project_invites",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<AppUser> teamInvitees;

    @Getter
    @ManyToMany
    @JoinTable(
            name = "project_team",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<AppUser> teamMembers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id",
    referencedColumnName = "id")

    @Getter @Setter
    private AppUser owner;

    public SoftwareProject(SoftwareProjectDto.CreateSoftwareProjectRequest request, AppUser appUser) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.initiationDate = Date.valueOf(LocalDate.now());
        this.projectType = request.getType();
        this.roles = new HashSet<>();
        this.teamMembers = new HashSet<>();
        this.teamInvitees = new HashSet<>();
        this.teamMembers.add(appUser);
        roles.add(new SoftwareProjectRole("ADMIN", this)
        .withAppUsers(
                teamMembers
        ));
        this.owner = appUser;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Date getInitiationDate() {
        return initiationDate;
    }

    @Override
    public void setInitiationDate(Date initiationDate) {
        this.initiationDate = initiationDate;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public void addRole(SoftwareProjectRole role) {
        if(roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
    }

    public void addTeamMember(AppUser teamMember) {
        this.teamMembers.add(teamMember);
    }

    public void addTeamInvitee(AppUser appUser) {
        this.teamInvitees.add(appUser);
    }

    public void removeTeamInvitee(AppUser appUser) {
        this.teamInvitees.remove(appUser);
    }

    public void receiveTeammateInvite(AppUser appUser) {
        this.teamInvitees.remove(appUser);
        this.teamMembers.add(appUser);
    }

}
