package com.esteiradev.esteira.specifications;

import com.esteiradev.esteira.model.EsteiraModel;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {


    @And({
            @Spec(path = "esteiraId", spec = Equal.class),
            @Spec(path = "titulo", spec = Equal.class)
    })
    public interface EsteiraSpec extends Specification<EsteiraModel> {}

}
