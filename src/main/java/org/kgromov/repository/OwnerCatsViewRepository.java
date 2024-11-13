package org.kgromov.repository;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import org.kgromov.view.OwnerCatsView;

public interface OwnerCatsViewRepository extends EntityViewRepository<OwnerCatsView, Long> {
}
