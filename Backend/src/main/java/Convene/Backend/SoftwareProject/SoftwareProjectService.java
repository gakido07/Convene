package Convene.Backend.SoftwareProject;

import Convene.Backend.Exception.CustomExceptions.SoftwareProjectExceptions;

public class SoftwareProjectService {

    private SoftwareProjectRepository repository;

    public SoftwareProject loadById(Long id) throws SoftwareProjectExceptions.SoftwareProjectNotFound {
        return repository.findById(id).orElseThrow(
                () -> new SoftwareProjectExceptions.SoftwareProjectNotFound());

    }

    public SoftwareProject createProject() {

        return new SoftwareProject();
    }

}
