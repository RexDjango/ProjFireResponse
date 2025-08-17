package com.pms.qms.company.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "datacenter")
public class DataCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstname;
    private String fullname;
    private String lastname;
    private String contactno;
    private String lat;
    private String lng;
    private String compaddress;
    private String cityaddress;
    private LocalDateTime datefiled;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "report_status",
            joinColumns = @JoinColumn(name = "datacenter_id"),
            inverseJoinColumns = @JoinColumn(name = "report_id")
    )
    private Collection<ReportStatus> reportStatuses = new LinkedHashSet<>();
    public DataCenter(final Integer id) {
        super();
        this.id = id;
    }
}
