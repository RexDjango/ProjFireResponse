package com.pms.qms.company.repository;

import com.pms.qms.company.entities.Account;
import com.pms.qms.company.projection.AccountAuthProjection;
import com.pms.qms.company.projection.AccountProjection;
import com.pms.qms.company.projection.AccountWithEmpIDProjection;
import com.pms.qms.company.projection.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

    @Query(value = "select * from account where email=:email",nativeQuery = true)
    Account findByEmailId(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update account u set u.enabled=:enabled where u.id=:id")
    Integer updateStatus(@Param("enabled") String enabled, @Param("id") Integer id);

    @Query(nativeQuery = true, value = "select u.email from account u where role='admin'")
    List<String> getAllAdmin();

    Account findByEmail(String email);


    @Query(nativeQuery = true, value = "select u.email from account u where u.email=:email")
    Account findUserByEmail(@Param("email") String email);

    @Query(value = "select * from account order by id desc",nativeQuery = true)
    List<Account> findAccount();

    @Query(value = "select * from account where id=:id order by name",nativeQuery = true)
    List<Account> getDeptID(@Param("id") Integer id);

    @Query(value = "select r.id,a.email,r.name as rolename,r.usertype from account a inner join users_roles ur on ur.user_id=a.id inner join role r on r.id=ur.role_id where a.email=:email",nativeQuery = true)
    List<AccountAuthProjection> findByAccountUserName(@Param("email") String userName);

    @Query(value = "delete from users_roles where user_id=:userid and role_id=:moduleid",nativeQuery = true)
    void deletebyUserIdAndSubId(@Param("moduleid") Integer moduleid,@Param("userid") Integer userid);

    @Query(value = "insert into users_roles(user_id,role_id) values (:userid,:roleid)",nativeQuery = true)
    void saveUserRole(@Param("userid") int userid,@Param("roleid") int roleid);

    @Query(value = "select id,email,name from account where depid=:deptid order by name",nativeQuery = true)
    List<AccountProjection> accountByDept(@Param("deptid") Integer deptid);

    @Query(value = "select * from account order by name",nativeQuery = true)
    List<AccountWithEmpIDProjection> getAccountAllId();
}
