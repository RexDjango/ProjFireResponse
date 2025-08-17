package com.pms.qms.company.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reportstatus")
public class ReportStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String remarks;
    private Integer incidentstatusid=0;
    private Integer accountid;
    private LocalDateTime incidentdate;
}
