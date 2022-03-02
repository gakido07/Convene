package Convene.Backend.SoftwareProject.Sprint;

import Convene.Backend.SoftwareProject.Issue.Issue;
import Convene.Backend.SoftwareProject.SoftwareProject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sprint")
@NoArgsConstructor
public class Sprint {
    @Id
    @SequenceGenerator(
            name = "sprint_sequence",
            sequenceName = "sprint_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sprint_sequence"
    )
    private long id;

    @Getter @Setter
    private String name;

    @ManyToOne
    @JoinColumn(
            name = "software_project",
            nullable = false,
            updatable = false
    )
    private SoftwareProject softwareProject;


    @OneToMany(mappedBy = "sprint")
    @Getter
    private Set<Issue> issues;

    @Getter @Setter
    private Date  startDate;

    @Getter @Setter
    private Date endDate;

    public Sprint(String name, Date startDate, Date endDate, SoftwareProject softwareProject) {
        this.name = name;
        this.softwareProject = softwareProject;
        this.issues = new HashSet<>();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setStatus(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void addIssue(Issue issue) {
        this.issues.add(issue);
    }
}
