package Convene.Backend.SoftwareProject.SoftwareProjectRole;

public interface SoftwareProjectServiceImpl {

    public SoftwareProjectRole findRoleById(Long id);

    public void saveRole(SoftwareProjectRole role);
}
