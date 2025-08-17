package com.pms.qms.company.serviceImpl;

import com.pms.qms.constant.QMSConstant;
import com.pms.qms.constant.SeverityContant;
import com.pms.qms.company.entities.Role;
import com.pms.qms.company.projection.RoleProjection;
import com.pms.qms.company.repository.RoleRepository;
import com.pms.qms.company.service.RoleService;
import com.pms.qms.utils.QMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        // Find role by name using the roleDao
        Role role = roleRepository.findRoleByName(name);
        return role;
    }

    @Override
    public ResponseEntity<?> list() {
        try {
            List<RoleProjection> roles=roleRepository.findRoleProj();
            return new ResponseEntity<>(roles, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
