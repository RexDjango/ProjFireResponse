package com.pms.qms.company.controllers;

import com.pms.qms.company.Model.UserModel;
import com.pms.qms.company.entities.Facility;
import com.pms.qms.company.repository.FacilityRepository;
import com.pms.qms.constant.QMSConstant;
import com.pms.qms.constant.SeverityContant;
import com.pms.qms.company.entities.Account;
import com.pms.qms.company.projection.AccountAuthProjection;
import com.pms.qms.company.repository.AccountRepository;
import com.pms.qms.company.service.AccountService;
import com.pms.qms.utils.QMSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AccountService userService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private FacilityRepository facilityRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody Map<String,String> requestMap){
        try {
            return userService.signup(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/reguser")
    public ResponseEntity<?> signUp(@RequestBody UserModel userModel){
        try {
            return userService.reguser(userModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/listbydept/{deptid}")
    public ResponseEntity<?> getAccountDeptList(@PathVariable("deptid") Integer deptid){
        try {
            return userService.getAccountDeptList(deptid);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasAnyAuthority('Setting_Users')")
    @GetMapping("/list")
    public ResponseEntity<?> getAccountList(){
        try {
            return userService.listAccount();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasAnyAuthority('Setting_Approval')")
    @GetMapping("/listbyrole/{roleid}")
    public ResponseEntity<?> getAccountByRole(@PathVariable Integer roleid){
        try {
            return userService.getAccountByRole(roleid);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasAnyAuthority('Setting_Users')")
    @PostMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestBody UserModel model){
        try {
            return userService.updateAccount(model);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR','CHIEF_NURSE','SUPERVISOR','STAFF')")
    @PostMapping("/changepass")
    public ResponseEntity<?> changepass(@RequestBody Map<String,String> requestmap){
        try {
            return userService.changepass(requestmap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getdept/{id}")
    public ResponseEntity<?> getDeptAccount(@PathVariable Integer id){
        try {
            return userService.getDeptAccount(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAccount(@PathVariable Integer id){
        try {
            return userService.getAccount(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasAnyAuthority('Setting_Users','Setting_Approval')")
    @GetMapping("/role")
    public ResponseEntity<?> getRoleList(){
        try {
            return userService.listRole();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasAnyAuthority('Setting_Users')")
    @GetMapping("/usertype")
    public ResponseEntity<?> getUsertypeList(){
        try {
            return userService.Usertype();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasAnyAuthority('Setting_Users')")
    @GetMapping("/module/{accountid}")
    public ResponseEntity<?> getModuleList(@PathVariable(name = "accountid") Integer accountid){
        try {
            return userService.getModuleList(accountid);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasAnyAuthority('CHAT')")
    @PostMapping("/savemodule/{userid}")
    public ResponseEntity<?> saveModuleList(@PathVariable("userid") Integer userid,@RequestBody UserModel model){
        try {
            return userService.saveModuleList(userid,model);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("hasAnyAuthority('Setting_Users')")
    @PostMapping("/deletemodule/{userid}")
    public ResponseEntity<?> deleteModule(@PathVariable("userid") Integer userid,@RequestBody UserModel model){
        try {
            return userService.deleteModule(userid,model);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/permissions")
    public ResponseEntity<?> getPermissionList(){
        try {
            return userService.listPermissions();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/auth/userinfo")
    public ResponseEntity<?> getUserInfo(Principal puser){
        if(puser.getName()!=null){
            Account user=(Account) userDetailsService.loadUserByUsername(puser.getName());
            List<AccountAuthProjection> accountAuthProjections=accountRepository.findByAccountUserName(puser.getName());
            Account account=accountRepository.getById(user.getId());
            UserModel userModel=new UserModel();
            userModel.setAuthroles(accountAuthProjections);
            userModel.setId(user.getId());
            userModel.setName(user.getName());
            userModel.setEmail(user.getEmail());
            Facility facility=facilityRepository.getById(account.getFacilityid());
            userModel.setFacility(facility.getName());
            userModel.setFacilitylat(facility.getLat());
            userModel.setFacilitylng(facility.getLng());
            return ResponseEntity.ok(userModel);
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
