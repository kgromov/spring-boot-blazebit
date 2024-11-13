package org.kgromov.repository;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import org.kgromov.view.CatsGroupedByOwnerView;

public interface CatsGroupedByOwnerViewRepository  extends EntityViewRepository<CatsGroupedByOwnerView, Long> {
}
