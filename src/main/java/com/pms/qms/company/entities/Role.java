package com.pms.qms.company.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="role")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, unique=true)
    private String name;
    private String description;
    private Integer submenu=0;
    private Integer usertype=0;
    public Role(final String name) {
        super();
        this.name = name;
    }

}