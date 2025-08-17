package com.pms.qms.company.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataCenterModel {
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
    private List<ReportStatusModel> reportStatusModels;
}
