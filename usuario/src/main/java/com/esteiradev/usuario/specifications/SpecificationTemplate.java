package com.esteiradev.usuario.specifications;

import com.esteiradev.usuario.model.UserEsteiraModel;
import com.esteiradev.usuario.model.UserModel;
import jakarta.persistence.criteria.Join;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
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

    public static Specification<UserModel> userEsteiraId(final UUID esteiraId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<UserModel, UserEsteiraModel> userProd = root.join("usersEsteiras");
            return cb.equal(userProd.get("esteiraId"), esteiraId);
        };
    }
}
