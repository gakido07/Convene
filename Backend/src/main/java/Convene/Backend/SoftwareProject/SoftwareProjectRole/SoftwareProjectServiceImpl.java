package Convene.Backend.SoftwareProject.SoftwareProjectRole;

public interface SoftwareProjectServiceImpl {

    SoftwareProjectRole findRoleById(Long id);

    void saveRole(SoftwareProjectRole role);
}
