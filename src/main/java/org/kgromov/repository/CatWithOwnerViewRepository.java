package org.kgromov.repository;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import org.kgromov.view.CatWithOwnerView;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CatWithOwnerViewRepository extends EntityViewRepository<CatWithOwnerView, Long> {
}
