package org.kgromov.repository;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import org.kgromov.view.CatsGroupedByOwnerView;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CatsGroupedByOwnerViewRepository  extends EntityViewRepository<CatsGroupedByOwnerView, Long> {
}
