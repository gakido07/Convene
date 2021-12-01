package Convene.Backend.Models;

import Convene.Backend.Project.ProjectType;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;

@EqualsAndHashCode
@ToString
public abstract class Project {
    private Long id;
    private Long teamId;
    private String name;
    private Date initiationDate;
    private String description;
    private Long leadId;
    private ProjectType projectType;

    public Long getTeamId() {
        return teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInitiationDate() {
        return initiationDate;
    }

    public void setInitiationDate(Date initiationDate) {
        this.initiationDate = initiationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLeadId() {
        return leadId;
    }

    public void setLeadId(Long leadId) {
        this.leadId = leadId;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }
}
