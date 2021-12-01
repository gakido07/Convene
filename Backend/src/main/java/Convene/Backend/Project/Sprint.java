package Convene.Backend.Project;

import java.sql.Date;

public class Sprint {
    private Long id;
    private String name;
    private Long projectId;
    private Boolean status;
    private Date  startDate;
    private Date endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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
