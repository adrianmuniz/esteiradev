package com.esteiradev.esteira.specifications;

import com.esteiradev.esteira.model.EsteiraModel;
import com.esteiradev.esteira.model.EsteiraUserModel;
import jakarta.persistence.criteria.Join;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class SpecificationTemplate {


    @And({
            @Spec(path = "esteiraId", spec = Equal.class),
            @Spec(path = "titulo", spec = Equal.class)
    })
    public interface EsteiraSpec extends Specification<EsteiraModel> {}

    public static Specification<EsteiraModel> esteiraUsersId(final UUID userId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<EsteiraModel, EsteiraUserModel> esteiraProd = root.join("esteiraUsers");
            return cb.equal(esteiraProd.get("userId"), userId);
        };
    }
}
