package com.pms.qms.company.repository;

import com.pms.qms.company.entities.Role;
import com.pms.qms.company.projection.RoleProjection;
import com.pms.qms.company.projection.RoleWithUserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String roleUser);

    Role findRoleByName(String name);

//    @Query(value = "EXEC sp_RoleWithUserProjection :id",nativeQuery = true)
//    List<RoleWithUserProjection> findByUserid(@Param("id") Integer id);

    @Query(value = "select * from role where usertype=:id",nativeQuery = true)
    List<Role> findUsertype(@Param("id") int id);

    @Query(value = "select id,name,description from role order by id",nativeQuery = true)
    List<RoleProjection> findRoleProj();
}
