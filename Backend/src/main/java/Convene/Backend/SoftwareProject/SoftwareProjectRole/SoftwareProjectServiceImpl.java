package Convene.Backend.SoftwareProject.SoftwareProjectRole;

public interface SoftwareProjectServiceImpl {

    SoftwareProjectRole findRoleById(long id);

    void saveRole(SoftwareProjectRole role);
}
