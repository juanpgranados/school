package com.challenge.school.service;

import com.challenge.school.entity.Group;
import com.challenge.school.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    public static final String GROUP_NOT_FOUND = "Group not found";
    @Autowired
    GroupRepository groupRepository;

    public List<Group> list(){
        return groupRepository.findAll();
    }
}
