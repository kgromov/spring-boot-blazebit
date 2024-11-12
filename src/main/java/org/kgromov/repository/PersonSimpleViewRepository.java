package org.kgromov.repository;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import org.kgromov.view.PersonSimpleView;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PersonSimpleViewRepository extends EntityViewRepository<PersonSimpleView, Long> {
    List<PersonSimpleView> findAll();
}
