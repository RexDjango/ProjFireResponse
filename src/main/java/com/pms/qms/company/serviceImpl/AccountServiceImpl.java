package com.pms.qms.company.serviceImpl;

import com.pms.qms.JWT.JwtUtil;
import com.pms.qms.company.Model.RoleModel;
import com.pms.qms.company.Model.UserModel;
import com.pms.qms.constant.QMSConstant;
import com.pms.qms.constant.SeverityContant;
import com.pms.qms.company.entities.Account;
import com.pms.qms.company.entities.Role;
import com.pms.qms.company.projection.AccountByRoleProjection;
import com.pms.qms.company.projection.AccountProjection;
import com.pms.qms.company.projection.AccountWithEmpIDProjection;
import com.pms.qms.company.repository.AccountRepository;
import com.pms.qms.company.repository.RoleRepository;
import com.pms.qms.company.service.AccountService;
import com.pms.qms.utils.QMSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    boolean allowsave=false;

    @Override
    public Account findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(UserModel userModel) {
        Account user = new Account();

        user.setEmail(userModel.getEmail());
        user.setContactNumber(userModel.getContactNumber());
        user.setName(userModel.getName());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(user);
    }

    @Override
    public ResponseEntity<?> listAccount() {
        try {
            List<UserModel> userModels=new ArrayList<>();
            List<AccountWithEmpIDProjection> accounts=userRepository.getAccountAllId();
            if(null!=accounts){
                accounts.forEach(acc->{
                    UserModel userModel=mapEntityToModel(acc);
                    userModels.add(userModel);
                });
                return new ResponseEntity<>(userModels,HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> listRole() {
        try {
            List<RoleModel> roleModels=new ArrayList<>();
            List<Role> role=roleRepository.findAll();
            if(!role.isEmpty()){
                role.stream().forEach(rl->{
                    RoleModel roleModel=mapEntityToModelRole(rl);
                    roleModels.add(roleModel);
                });
                return new ResponseEntity<>(roleModels,HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> listPermissions() {
        return null;
    }

    @Override
    public ResponseEntity<?> getAccount(Integer id) {
        try {
//            AccountWithEmpIDProjection account=userRepository.getAccountId(id);
//            if(null!=account){
//                return new ResponseEntity<>(account,HttpStatus.OK);
//            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?>  changepass(Map<String, String> requestmap) {
        try {
            if(validateChangePass(requestmap,true)){
                Account account=userRepository.getById(Integer.valueOf(requestmap.get("id")));
                if(account==null || !passwordEncoder.matches(requestmap.get("currentpassword"),account.getPassword())){
                    return QMSUtils.getResponseEntity("Invalid current password.",SeverityContant.error,HttpStatus.BAD_REQUEST);
                }
                else if(passwordEncoder.matches(requestmap.get("currentpassword"),account.getPassword())){
                    String encodenewpassword=passwordEncoder.encode(requestmap.get("newpassword"));
                    account.setPassword(encodenewpassword);
                    userRepository.save(account);
                    return QMSUtils.getResponseEntity("Password successfully change.",SeverityContant.success,HttpStatus.OK);
                }
            }
            else{
                return QMSUtils.getResponseEntity(QMSConstant.INVALID_DATA,SeverityContant.error,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAccountDeptList(Integer deptid) {
        try {
            List<AccountProjection> accountProjections=userRepository.accountByDept(deptid);
            return new ResponseEntity<>(accountProjections,HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> reguser(UserModel userModel) {
        try {
            Account user = new Account();
            user.setEmail(userModel.getEmail());
            user.setContactNumber(userModel.getContactNumber());
            user.setName(userModel.getName());
            user.setPassword(passwordEncoder.encode(userModel.getPassword()));
            user.setEnabled(userModel.getEnabled());
            List<Role> roles=new ArrayList<>();
            userModel.getRoles().stream().forEach(rls->{
                Role role = roleRepository.findByName(rls.getName());
                if(role == null){
                    role = checkRoleExist();
                }
                roles.add(role);
            });
            user.setRoles(roles);
            userRepository.save(user);
            return QMSUtils.getResponseEntity("New user successfully saved.",SeverityContant.success,HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateAccount(UserModel userModel) {
        try {
            if(validateSignUp1(userModel,true)){
//                Account account=userRepository.getOne(userModel.getId());
//                if(account!=null){
//                    Role deleteusertype=roleRepository.findByName(userModel.getDeleteusertype());
//                    account.getRoles().remove(deleteusertype);
//                    Account account4=userRepository.save(account);
//
//                    Account account1=getAccountFromMap1(userModel,account4,true);
//                    Account account2=userRepository.save(account1);
//                    AccountWithEmpIDProjection accountWithEmpIDProjection=userRepository.getAccountId(account2.getId());
//                    if(null!=accountWithEmpIDProjection){
//                        return new ResponseEntity<>(mapEntityToModel(accountWithEmpIDProjection),HttpStatus.OK);
//                    }
//                }else{
//                    return QMSUtils.getResponseEntity("Account id doesn't exist.",SeverityContant.warning,HttpStatus.BAD_REQUEST);
//                }
            }
            else{
                return QMSUtils.getResponseEntity(QMSConstant.INVALID_DATA,SeverityContant.error,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getDeptAccount(Integer id) {
        try {
            List<Account> account=userRepository.getDeptID(id);
            if(null!=account){
                List<UserModel> userModels=new ArrayList<>();
                account.stream().forEach(acc -> {
                    UserModel userModel=mapEntityToModel1(acc);
                    userModels.add(userModel);
                });
                return new ResponseEntity<>(userModels,HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
//            authorities.addAll(role.getPrivileges()
//                    .stream()
//                    .map(p -> new SimpleGrantedAuthority(p.getName()))
//                    .collect(Collectors.toList()));
        }
        return authorities;
    }

    private Collection<? extends GrantedAuthority> getAuthorities1(String roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roles));
//        for (Role role: roles) {
//            log.info("role.getName() {}",role.getName());
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
////            authorities.addAll(role.getPrivileges()
////                    .stream()
////                    .map(p -> new SimpleGrantedAuthority(p.getName()))
////                    .collect(Collectors.toList()));
//        }
        return authorities;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
              Account accounts=userRepository.findByEmailId(requestMap.get("email"));

            if(accounts!=null){
                if(!accounts.getRoles().isEmpty()){
                    Authentication auth = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"),getAuthorities(accounts.getRoles()))
                    );
                    if (auth.isAuthenticated()) {
                        return new ResponseEntity<String>("{\"token\":\"" +
                                jwtUtil.generateToken(accounts) + "\"}", HttpStatus.OK);
                    } else if(accounts.getEnabled()==true) {
                        return QMSUtils.getResponseEntity("Wait for admin approval.",SeverityContant.warning,HttpStatus.BAD_REQUEST);
                    }else{
                        return QMSUtils.getResponseEntity("Invalid username or password.",SeverityContant.warning,HttpStatus.UNAUTHORIZED);
                    }
                }
                return QMSUtils.getResponseEntity("Please contact your system administrator for your account role access",SeverityContant.warning,HttpStatus.UNAUTHORIZED);
            }
            return QMSUtils.getResponseEntity("Invalid username or password.",SeverityContant.warning,HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":\""+"Invalid username or password."+"\",\"severity\":\""+"error"+"\"}", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> signup(Map<String, String> requestMap) {
        try {
            if(validateSignUp(requestMap,false)){
                Account account=userRepository.findByEmailId(requestMap.get("email"));
                if(account==null){
                    Account account1=userRepository.save(mapModelToEntityAccount(requestMap));
                    return new ResponseEntity<>(mapEntityToModel1(account1),HttpStatus.CREATED);
                }
                return QMSUtils.getResponseEntity("Account email already exists.", SeverityContant.warning, HttpStatus.BAD_REQUEST);
            }
            return QMSUtils.getResponseEntity("Please fill all the required fields.", SeverityContant.warning, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAccountByRole(Integer roleid) {
        try {
//            List<AccountByRoleProjection> account=userRepository.findAccountByRole(roleid);
//            List<UserModel> userModels=new ArrayList<>();
//            if(null!=account){
//                account.stream().forEach(acc->{
//                    UserModel userModel=mapEntityToModel3(acc);
//                    userModels.add(userModel);
//                });
//                return new ResponseEntity<>(userModels,HttpStatus.OK);
//            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<?> Usertype() {
        try {
            List<RoleModel> roleModels=new ArrayList<>();
            List<Role> role=roleRepository.findUsertype(1);
            if(!role.isEmpty()){
                role.stream().forEach(rl->{
                    RoleModel roleModel=mapEntityToModelRole(rl);
                    roleModels.add(roleModel);
                });
                return new ResponseEntity<>(roleModels,HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getModuleList(Integer accountid) {
        try {
//            if(!model.getRolesdelete().isEmpty()){
//                Optional<Account> account=userRepository.findById(userid);
//                if(account.isPresent()){
//                    Collection<Role> roles=new LinkedHashSet<>();
//
//                    model.getRolesdelete().stream().forEach(role->{
//                        Role role1=roleRepository.findByName(role);
//                        roles.add(role1);
//                    });
//                    account.get().getRoles().removeAll(roles);
//                    userRepository.save(account.get());
//                    return QMSUtils.getResponseEntity("Data successfully deleted",SeverityContant.success,HttpStatus.OK);
//                }
//            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> saveModuleList(Integer userid, UserModel model) {
        try {
            if(!model.getRolesupdate().isEmpty()){
                Optional<Account> account=userRepository.findById(userid);
                if(account.isPresent()){
                    Collection<Role> roles=new LinkedHashSet<>();

                    model.getRolesupdate().stream().forEach(uprole->{
                        allowsave=true;
                        account.get().getRoles().stream().forEach(myrole->{
                            if(Objects.equals(myrole.getName(), uprole)){
                                allowsave=false;
                            }
                        });
                        if(allowsave){
                            Role role1=roleRepository.findByName(uprole);
                            roles.add(role1);
                        }
                    });
                    account.get().getRoles().addAll(roles);
                    userRepository.save(account.get());
                    return QMSUtils.getResponseEntity("Data successfully updated",SeverityContant.success,HttpStatus.OK);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> deleteModule(Integer userid, UserModel model) {
        try {
            if(!model.getRolesdelete().isEmpty()){
                Optional<Account> account=userRepository.findById(userid);
                if(account.isPresent()){
                    Collection<Role> roles=new LinkedHashSet<>();

                    model.getRolesdelete().stream().forEach(role->{
                        Role role1=roleRepository.findByName(role);
                        roles.add(role1);
                    });
                    account.get().getRoles().removeAll(roles);
                    userRepository.save(account.get());
                    return QMSUtils.getResponseEntity("Data successfully deleted",SeverityContant.success,HttpStatus.OK);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private UserModel mapEntityToModel3(AccountByRoleProjection acc) {
        UserModel userModel=new UserModel();
        userModel.setName(acc.getName());
        userModel.setId(acc.getId());
        userModel.setEmail(acc.getEmail());
        return userModel;
    }

    private Account mapModelToEntityAccount(Map<String, String> requestMap) {
        Account account=new Account();
        account.setName(requestMap.get("fullName"));
        account.setEmail(requestMap.get("email"));
        account.setPassword(passwordEncoder.encode(requestMap.get("password")));
        account.setEnabled(false);
        return account;
    }

    private boolean validateChangePass(Map<String, String> requestmap, boolean isValid) {
        if(requestmap.get("newpassword")!=null && requestmap.get("id")!=null){
            return true;
        }
        return false;
    }

    private boolean validateSignUp1(UserModel userModel, boolean isValid) {
        if(!userModel.getName().isEmpty() && !userModel.getEmail().isEmpty()){
            if(userModel.getId()>0 && isValid){
                return true;
            }else{
                return true;
            }
        }
        return false;
    }

    private boolean validateSignUp(Map<String, String> requestMap, boolean isValid) {
        if(requestMap.containsKey("fullName") && !requestMap.get("fullName").isEmpty() &&
                requestMap.containsKey("email") && !requestMap.get("email").isEmpty()){
            if(requestMap.containsKey("id") && !requestMap.get("id").isEmpty() && requestMap.containsKey("password") && isValid){
                return true;
            } else if (requestMap.containsKey("password") && !requestMap.get("password").isEmpty() && !isValid) {
                return true;
            }
        }
        return false;
    }

    private Account getAccountFromMap1(UserModel userModel,Account account1, boolean isUpdate) {
        Account account=new Account();
        if(isUpdate==true && userModel.getId()>0){
            account.setId(userModel.getId());
        }
        if(!userModel.getPassword().isEmpty()){
            account.setPassword(passwordEncoder.encode(userModel.getPassword()));
        }else{
            account.setPassword(account1.getPassword());
        }
        account.setName(userModel.getName());
        account.setEmail(userModel.getEmail());
        account.setContactNumber(userModel.getContactNumber());
        account.setRoles(account1.getRoles());
        account.setEnabled(userModel.getEnabled());
        Collection<Role> roles=new LinkedHashSet<>();
        Role role1=roleRepository.findByName(userModel.getUsertypename());
        roles.add(role1);
        account.getRoles().addAll(roles);
        return account;
    }

    private Account getAccountFromMap(Map<String, String> requestMap,Account account1, boolean isUpdate) {
        Account account=new Account();
        if(isUpdate==true && requestMap.get("id")!="0"){
            account.setId(Integer.valueOf(requestMap.get("id")));
        }
        if(!requestMap.get("password").isEmpty()){
            account.setPassword(passwordEncoder.encode(requestMap.get("password")));
        }else{
            account.setPassword(account1.getPassword());
        }
        account.setName(requestMap.get("name"));
        account.setEmail(requestMap.get("email"));
        account.setContactNumber(requestMap.get("contactNumber"));
        return account;
    }

    private Role mapModelToEntityRole(Role user) {
        Role role=new Role();
        role.setId(user.getId());
        role.setName(user.getName());
        return role;
    }

    private boolean validateAccount(UserModel userModel, boolean isValid) {
        if(userModel.getName()!=""){
            if(userModel.getId()!=0 && isValid){
                return true;
            } else if (!isValid) {
                return true;
            }
        }
        return false;
    }

    private RoleModel mapEntityToModelRole(Role rl) {
        RoleModel roleModel=new RoleModel();
        roleModel.setId(rl.getId());
        roleModel.setName(rl.getName());
        roleModel.setDescription(rl.getDescription());
        roleModel.setSubmenu(rl.getSubmenu());
        return roleModel;
    }

    private UserModel mapEntityToModel(AccountWithEmpIDProjection acc) {
        UserModel userModel=new UserModel();
        userModel.setId(acc.getId());
        if(acc.getEid()!=null){
            userModel.setEid(acc.getEid());
        }else{
            userModel.setEid("");
        }
        if(acc.getName()!=null){
            userModel.setName(acc.getName());
        }else{
            userModel.setName("");
        }
        if(acc.getEmail()!=null){
            userModel.setEmail(acc.getEmail());
        }else{
            userModel.setEmail("");
        }
        if(acc.getContact()!=null){
            userModel.setContactNumber(acc.getContact());
        }else{
            userModel.setContactNumber("");
        }
        if(acc.getEmployeeid()!=null){
            userModel.setEmployeeid(acc.getEmployeeid());
        }else{
            userModel.setEmployeeid("");
        }
        if(acc.getEname()!=null){
            userModel.setEname(acc.getEname());
        }else{
            userModel.setEname("");
        }
        if(acc.getEnabled()!=null){
            userModel.setEnabled(acc.getEnabled());
        }else{
            userModel.setEnabled(false);
        }
        userModel.setPassword("");
        return userModel;
    }

    private UserModel mapEntityToModel2(Account acc) {
        UserModel userModel=new UserModel();
        userModel.setId(acc.getId());
        userModel.setName(acc.getName());
        userModel.setEmail(acc.getEmail());
        userModel.setContactNumber("");
        if(acc.getContactNumber()!=null){
            userModel.setContactNumber(acc.getContactNumber());
        }
//        if(acc.getDepartment()!=null){
//            userModel.setFkdepartment(acc.getDepartment().getId());
//        }else{
//            userModel.setFkdepartment(0);
//        }
        List<Role> roles=new ArrayList<>();

        userModel.setRoles(null);
        userModel.setPassword(acc.getPassword());
        return userModel;
    }

    private UserModel mapEntityToModel1(Account acc) {
        UserModel userModel=new UserModel();
        userModel.setId(acc.getId());
        userModel.setName(acc.getName());
        userModel.setEmail(acc.getEmail());
        userModel.setContactNumber("");
        if(acc.getContactNumber()!=null){
            userModel.setContactNumber(acc.getContactNumber());
        }
//        if(acc.getDepartment()!=null){
//            userModel.setFkdepartment(acc.getDepartment().getId());
//        }else{
//            userModel.setFkdepartment(0);
//        }
//        List<Role> roles=new ArrayList<>();
//
//        userModel.setRoles(acc.getRoles());
//        userModel.setPassword(acc.getPassword());
        return userModel;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }
}
