package Convene.Backend.Project.SoftwareProject;

import Convene.Backend.Models.Project;
import Convene.Backend.Project.ProjectType;
import Convene.Backend.Project.SoftwareProject.SoftwareProjectRole.SoftwareProjectRole;
import Convene.Backend.User.AppUser;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@ToString
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
    @ManyToMany(mappedBy = "projects")
    Set<AppUser> teamMembers;
    @OneToMany
    @JoinColumn(
            name = "project_role_id"
    )
    Set<SoftwareProjectRole> projectRoles;

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

}
