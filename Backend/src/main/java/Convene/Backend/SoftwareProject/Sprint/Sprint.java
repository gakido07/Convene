package Convene.Backend.SoftwareProject.Sprint;

import Convene.Backend.SoftwareProject.Issue.Issue;
import Convene.Backend.SoftwareProject.SoftwareProject;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "sprint")
public class Sprint {
    @Id
    @SequenceGenerator(
            name = "sprint_sequence",
            sequenceName = "sprint_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(
            name = "id",
            nullable = false,
            insertable = false,
            updatable = false
    )
    private SoftwareProject softwareProject;
    private Boolean status;

    @OneToMany(mappedBy = "sprint")
    private Set<Issue> issues;
    private Date  startDate;
    private Date endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status, Date startDate, Date endDate) {
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
