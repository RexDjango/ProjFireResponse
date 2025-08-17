package com.pms.qms.company.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleModel {
    private Integer id;
    private String name;
    private String description;
    private Integer submenu;
}
