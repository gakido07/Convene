package Convene.Backend.SoftwareProject;

import Convene.Backend.Exception.CustomExceptions.SoftwareProjectExceptions;

public interface SoftwareProjectServiceImpl {

    public SoftwareProject findSoftwareProjectById(Long id) throws SoftwareProjectExceptions.SoftwareProjectNotFound;


    public SoftwareProjectDto createProject(SoftwareProjectDto.CreateSoftwareProjectRequest request, Long id) throws Exception;

}
