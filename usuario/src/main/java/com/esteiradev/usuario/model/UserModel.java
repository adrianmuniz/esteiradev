package com.esteiradev.usuario.model;

import com.esteiradev.usuario.enums.UserStatus;
import com.esteiradev.usuario.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_USERS")
public class UserModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateCreate;

    @Column(nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime dateUpdate;

    @Column(nullable = false, length = 255)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(    name = "TB_USERS_ROLES",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleModel> roles = new HashSet<>();
}
