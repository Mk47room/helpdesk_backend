package com.springboot.helpdesk.service;

import com.springboot.helpdesk.dto.request.ManagerDto;
import com.springboot.helpdesk.dto.response.JobTitleExecutiveCountDto;
import com.springboot.helpdesk.enums.JobTitle;
import com.springboot.helpdesk.enums.Role;
import com.springboot.helpdesk.exception.ResourceNotFoundException;
import com.springboot.helpdesk.mapper.ManagerMapper;
import com.springboot.helpdesk.model.Executive;
import com.springboot.helpdesk.model.Manager;
import com.springboot.helpdesk.model.User;
import com.springboot.helpdesk.repository.ExecutiveRepository;
import com.springboot.helpdesk.repository.ManagerRepository;
import com.springboot.helpdesk.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;
    private final ExecutiveRepository executiveRepository;
    private final PasswordEncoder passwordEncoder;

    public void add(@Valid ManagerDto dto) {
        //Set User first and add to db
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.MANAGER);
        user = userRepository.save(user);//this user has id

        // Step 2: Map Dto to Entity
        Manager manager = ManagerMapper.mapDtoToManager(dto);

        //attach user
        manager.setUser(user);

        //save manager
        managerRepository.save(manager);
    }

    public List<JobTitleExecutiveCountDto> getExecutiveStatWithJobTitleAndCount() {
        return executiveRepository.getExecutiveStatWithJobTitleAndCount();
    }

    public void updateJobTitle(Long executiveId, JobTitle jobTitle) {
        //step 1: validate id
        Executive executive = executiveRepository.findById(executiveId)
                .orElseThrow(()-> new ResourceNotFoundException("Executive not found"));

        //step 2: update job title
        executive.setJobTitle(jobTitle);

        //step 3: save in db
        executiveRepository.save(executive);

    }
}
