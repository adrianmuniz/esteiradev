package com.esteiradev.usuario.specifications;

import com.esteiradev.usuario.model.UserModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "name", spec = Like.class)
    })
    public interface UserSpec extends Specification<UserModel> {}

    public static Specification<UserModel> userIdEquals(UUID userId) {
        return (Root<UserModel> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get("userId"), userId);
    }
}
