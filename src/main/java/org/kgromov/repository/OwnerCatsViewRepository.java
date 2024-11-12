package org.kgromov.repository;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import org.kgromov.view.OwnerCatsView;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface OwnerCatsViewRepository extends EntityViewRepository<OwnerCatsView, Long> {
}
