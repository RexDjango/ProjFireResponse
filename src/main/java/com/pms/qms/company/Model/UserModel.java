package com.pms.qms.company.Model;

import com.pms.qms.company.projection.AccountAuthProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Integer id;
    private String name;
    private String loginname;
    private String contactNumber;
    private String email;
    private String status;
    private String password;
    private String ename;
    private String role;
    private String eid;
    private String employeeid;
    private Boolean enabled;
    private List<RoleModel> roles;
    private Collection<AccountAuthProjection> authroles;
    private List<String> rolesupdate;
    private List<String> rolesdelete;
    private String Usertypename;
    private String Deleteusertype;
    private String facility;
    private String facilitylat;
    private String facilitylng;
}
