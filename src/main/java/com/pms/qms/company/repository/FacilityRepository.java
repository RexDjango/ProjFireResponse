package com.pms.qms.company.repository;

import com.pms.qms.company.entities.Facility;
import com.pms.qms.company.projection.DataCenterPorjection;
import com.pms.qms.company.projection.FacilityProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityRepository extends JpaRepository<Facility,Integer> {

    @Query(value = "select * from facility",nativeQuery = true)
    List<FacilityProjection> findFacility();
}
