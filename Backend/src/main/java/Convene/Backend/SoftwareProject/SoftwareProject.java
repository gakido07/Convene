package Convene.Backend.SoftwareProject;

import Convene.Backend.Models.Project;
import Convene.Backend.SoftwareProject.SoftwareProjectRole.SoftwareProjectRole;
import Convene.Backend.SoftwareProject.Sprint.Sprint;
import Convene.Backend.User.AppUser;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
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
    private Long id;

    private String name;

    private Date initiationDate;

    private String description;

    private ProjectType projectType;
    @OneToMany(mappedBy = "softwareProject")
    private Set<SoftwareProjectRole> roles;

    @OneToMany(mappedBy = "softwareProject")
    private Set<Sprint> sprints;

    @ManyToMany
    @JoinTable(
            name = "project_team",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    Set<AppUser> teamMembers;


    //Constructor
    public SoftwareProject(SoftwareProjectDto.CreateSoftwareProjectRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.projectType = request.getType();
        this.teamMembers.add(request.getUser());
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


    public Set<AppUser> getTeamMembers() {
        return teamMembers;
    }

    public void addTeamMember(AppUser teamMember) {
        this.teamMembers.add(teamMember);
    }
}
