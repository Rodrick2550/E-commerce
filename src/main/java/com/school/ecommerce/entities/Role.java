package com.school.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.school.ecommerce.entities.pivots.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150, unique = true)
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserRole> userRoles;
}
