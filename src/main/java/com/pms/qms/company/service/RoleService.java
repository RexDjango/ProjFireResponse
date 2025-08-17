package com.pms.qms.company.service;

import com.pms.qms.company.entities.Role;
import org.springframework.http.ResponseEntity;

public interface RoleService {
    Role findByName(String user);

    ResponseEntity<?> list();
}
