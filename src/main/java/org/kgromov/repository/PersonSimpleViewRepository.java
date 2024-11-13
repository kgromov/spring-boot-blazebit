package org.kgromov.repository;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import org.kgromov.view.PersonSimpleView;

import java.util.List;

public interface PersonSimpleViewRepository extends EntityViewRepository<PersonSimpleView, Long> {
    List<PersonSimpleView> findAll();
}
