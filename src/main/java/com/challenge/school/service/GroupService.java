package com.challenge.school.service;

import com.challenge.school.entity.Group;
import com.challenge.school.exception.ApiException;
import com.challenge.school.model.GroupModel;
import com.challenge.school.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    public static final String GROUP_NOT_FOUND = "Group not found";
    @Autowired
    GroupRepository groupRepository;

    public List<GroupModel> list(){
        List<Group> groups = groupRepository.findAll();
        List<GroupModel> groupDtos = new ArrayList<>();
        groups.forEach(g->groupDtos.add(new GroupModel(g.getName(), g.getId())));
        return groupDtos;
    }

    public GroupModel getGroupById(Long groupId) throws ApiException{
        Optional<Group> opGroup = groupRepository.findById(groupId);
        if(opGroup.isPresent()){
            Group group = opGroup.get();
            return new GroupModel(group.getName(),group.getId());
        }else{
            throw new ApiException(HttpStatus.NOT_FOUND, GROUP_NOT_FOUND);
        }
    }

    public GroupModel create(GroupModel groupDto) {
        Group newGroup = new Group();
        newGroup.setName(groupDto.getName());
        Group group = groupRepository.save(newGroup);
        return new GroupModel(group.getName(), group.getId());
    }

    public GroupModel update(GroupModel groupDto, Long groupId) throws ApiException{
        Optional<Group> opGroup = groupRepository.findById(groupId);
        if(opGroup.isPresent()){
            Group group = opGroup.get();
            group.setName(groupDto.getName());
            Group savedGroup = groupRepository.save(group);
            return new GroupModel(savedGroup.getName(), savedGroup.getId());
        }else{
            throw new ApiException(HttpStatus.BAD_REQUEST, GROUP_NOT_FOUND);
        }
    }

    public void delete(Long groupId) {
        Optional<Group> opGroup = groupRepository.findById(groupId);
        if(opGroup.isPresent()){
            groupRepository.delete(opGroup.get());
        }else{
            throw new ApiException(HttpStatus.BAD_REQUEST, GROUP_NOT_FOUND);
        }
    }
}
