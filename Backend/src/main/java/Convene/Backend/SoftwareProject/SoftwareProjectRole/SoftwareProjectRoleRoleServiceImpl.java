package Convene.Backend.SoftwareProject.SoftwareProjectRole;

import Convene.Backend.Exception.CustomExceptions.SoftwareProjectRoleExceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SoftwareProjectRoleRoleServiceImpl implements SoftwareProjectRoleService {

    private final SoftwareProjectRoleRepository roleRepository;

    @Autowired
    public SoftwareProjectRoleRoleServiceImpl(SoftwareProjectRoleRepository softwareProjectRoleRepository) {
        this.roleRepository = softwareProjectRoleRepository;
    }

    @Override
    public SoftwareProjectRole findRoleById(long id) {
        return roleRepository.findById(id).orElseThrow(SoftwareProjectRoleExceptions.SoftwareProjectRoleNotFoundException::new);
    }

    @Override
    public void saveRole(SoftwareProjectRole role) {
        roleRepository.save(role);
    }
}
