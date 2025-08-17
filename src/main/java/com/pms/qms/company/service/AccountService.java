package com.pms.qms.company.service;

import com.pms.qms.company.Model.UserModel;
import com.pms.qms.company.entities.Account;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AccountService {

    Account findUserByEmail(String email);

    void saveUser(UserModel userModel);

    ResponseEntity<?> listAccount();

    ResponseEntity<?> listRole();

    ResponseEntity<?> listPermissions();

    ResponseEntity<?> getAccount(Integer id);

    ResponseEntity<?> updateAccount(UserModel userModel);

    ResponseEntity<?> getDeptAccount(Integer id);

    ResponseEntity<String> login(Map<String, String> requestMap);

    ResponseEntity<?> signup(Map<String, String> requestMap);

    ResponseEntity<?> getAccountByRole(Integer roleid);

    ResponseEntity<?> Usertype();

    ResponseEntity<?> getModuleList(Integer accountid);

    ResponseEntity<?> saveModuleList(Integer userid,UserModel model);

    ResponseEntity<?> deleteModule(Integer userid, UserModel model);

    ResponseEntity<?> changepass(Map<String, String> requestmap);

    ResponseEntity<?> getAccountDeptList(Integer deptid);

    ResponseEntity<?> reguser(UserModel userModel);
}
