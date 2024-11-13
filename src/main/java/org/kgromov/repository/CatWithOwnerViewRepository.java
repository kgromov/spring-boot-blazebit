package org.kgromov.repository;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import org.kgromov.view.CatWithOwnerView;

public interface CatWithOwnerViewRepository extends EntityViewRepository<CatWithOwnerView, Long> {
}
