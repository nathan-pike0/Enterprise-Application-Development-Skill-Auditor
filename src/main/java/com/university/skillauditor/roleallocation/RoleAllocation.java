package com.university.skillauditor.roleallocation;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role_allocation")
@Getter
@Setter
public class RoleAllocation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 35)
    @NotBlank(message = "Name is required")
    @Size(max = 35, message = "Name must be 35 characters or less")
    private String name;
}