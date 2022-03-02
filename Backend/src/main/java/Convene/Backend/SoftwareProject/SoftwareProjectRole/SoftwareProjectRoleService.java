package Convene.Backend.SoftwareProject.SoftwareProjectRole;

import Convene.Backend.AppUser.AppUser;

public interface SoftwareProjectRoleService {

    SoftwareProjectRole findRoleById(long id);

    void saveRole(SoftwareProjectRole role);
}
