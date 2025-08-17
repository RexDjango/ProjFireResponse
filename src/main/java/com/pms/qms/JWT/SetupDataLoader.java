package com.pms.qms.JWT;

import com.pms.qms.company.entities.IncidentStatus;
import com.pms.qms.company.entities.Role;
import com.pms.qms.company.repository.RoleRepository;
import com.pms.qms.company.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private IncidentStatusRepository incidentStatusRepository;
    // API
    @Override


    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        //<=======USERTYPE========>//
        createRoleIfNotFound("ADMIN","Admin",0,1); //1
        createRoleIfNotFound("USER","User",0,1); //2
        //<=======SETTINGS========>//
        createRoleIfNotFound("Setting","Settings",0,0); //4
        // USERS
        createRoleIfNotFound("Setting_Users","User Setting",4,0); //5

        //  INCIDENT REPORT STATUS
        createIncidentStatusIfNotFound("Reported","Initial fire report received by authorities or safety team.");
        createIncidentStatusIfNotFound("Verified","Fire confirmed after inspection or visual confirmation.");
        createIncidentStatusIfNotFound("False Alarm","Fire unconfirmed after inspection or visual confirmation.");
        createIncidentStatusIfNotFound("Alert Raised","Notifications sent to responders and key personnel.");
        createIncidentStatusIfNotFound("Evacuation Ongoing","Evacuation procedures activated and in progress.");
        createIncidentStatusIfNotFound("Response Initiated","Firefighters or emergency responders dispatched.");
        createIncidentStatusIfNotFound("Under Control","Fire is contained and no longer spreading.");
        createIncidentStatusIfNotFound("Extinguished","Fire fully put out. Hotspots are being monitored.");
        createIncidentStatusIfNotFound("Damage Assessed","Post-fire assessment ongoing or completed.");
        createIncidentStatusIfNotFound("Cause Under Investigation","Origin and cause of fire being determined.");
        createIncidentStatusIfNotFound("Cleared / Safe to Return","Area declared safe for reentry. Operations can resume.");
        createIncidentStatusIfNotFound("Closed / Incident Resolved","Final status after all actions completed and documented.");
        alreadySetup = true;
    }

    @Transactional
    public Role createRoleIfNotFound(final String name, final String description, final Integer submenuid, final Integer usertype) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setDescription(description);
            role.setSubmenu(submenuid);
            role.setUsertype(usertype);
            role = roleRepository.save(role);
        }

        return role;
    }

    @Transactional
    public IncidentStatus createIncidentStatusIfNotFound(final String name,final String description) {
        IncidentStatus incidentStatus = incidentStatusRepository.findByName(name);
        if (incidentStatus == null) {
            incidentStatus = new IncidentStatus();
            incidentStatus.setName(name);
            incidentStatus.setDescription(description);
            incidentStatus = incidentStatusRepository.save(incidentStatus);
        }

        return incidentStatus;
    }

}
