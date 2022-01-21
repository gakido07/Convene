package Convene.Backend.SoftwareProject.SoftwareProjectRole;

import Convene.Backend.Exception.CustomExceptions.SoftwareProjectRoleExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoftwareProjectRoleService implements SoftwareProjectServiceImpl {

    private SoftwareProjectRoleRepository roleRepository;

    @Autowired
    public SoftwareProjectRoleService(SoftwareProjectRoleRepository softwareProjectRoleRepository) {
        this.roleRepository = softwareProjectRoleRepository;
    }

    @Override
    public SoftwareProjectRole findRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new SoftwareProjectRoleExceptions.SoftwareProjectRoleNotFoundException());
    }

    @Override
    public void saveRole(SoftwareProjectRole role) {
        roleRepository.save(role);
    }
}
