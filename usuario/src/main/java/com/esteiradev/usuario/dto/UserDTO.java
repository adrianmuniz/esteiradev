package com.esteiradev.usuario.dto;

import com.esteiradev.usuario.model.RoleModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    public interface UserView {
        public static interface RegistrationPost {}
        public static interface UserPut{}
        public static interface PasswordPut{}
    }


    private UUID userId;

    @NotBlank(groups = UserView.RegistrationPost.class)
    @Size(min = 3, max = 50)
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String name;

    @NotBlank(groups = UserView.RegistrationPost.class)
    @Email(groups = UserView.RegistrationPost.class)
    @JsonView(UserView.RegistrationPost.class)
    private String email;

    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @Size(min = 6, max =20, groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String password;

    @JsonView(UserView.RegistrationPost.class)
    private LocalDateTime dateCreate;

    @JsonView(UserView.UserPut.class)
    private LocalDateTime dateUpdate;

    private Set<RoleModel> roles;
}
