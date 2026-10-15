package com.springboot.helpdesk.controller;

import com.springboot.helpdesk.dto.request.ManagerDto;
import com.springboot.helpdesk.dto.response.JobTitleExecutiveCountDto;
import com.springboot.helpdesk.enums.JobTitle;
import com.springboot.helpdesk.model.Manager;
import com.springboot.helpdesk.service.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/api/manager/add")
    public void add(@Valid @RequestBody ManagerDto managerDto){

        managerService.add(managerDto);
    }

    /*
   JobTitle            |   No of Executives
FIRST_LINE_SUPPORT             2
SECOND_LINE_SUPPORT            3

for(){ //100   [N+1 problem]
  --> DB (3)  : 100
}

GroupBy
-------
FIRST_LINE_SUPPORT    count(id)

   Name of Executive  |  No of Tickets   |   No of CLOSED Tickets  |


   * */
    @GetMapping("/api/manager/jobtitle/stat/executive/count")
    public List<JobTitleExecutiveCountDto> getExecutiveStatWithJobTitleAndCount(){
        return managerService.getExecutiveStatWithJobTitleAndCount();
    }

    @PutMapping("/api/manager/executive/assign/jobtitle/{executiveId}")
    public void updateJobTitle(@PathVariable Long executiveId,
                               @RequestParam("jobTitle") JobTitle jobTitle){
        managerService.updateJobTitle(executiveId, jobTitle);
    }

}
