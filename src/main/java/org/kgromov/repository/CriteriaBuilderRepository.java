package org.kgromov.repository;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.ObjectBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Tuple;

import java.util.List;
import java.util.Optional;

public abstract class CriteriaBuilderRepository<E, ID> {

    protected final CriteriaBuilderFactory cbf;
    protected final EntityManager em;

    public CriteriaBuilderRepository(CriteriaBuilderFactory cbf, EntityManagerFactory entityManagerFactory) {
        this.cbf = cbf;
        this.em = entityManagerFactory.createEntityManager();
    }

    public Optional<E> findById(ID id) {
        return Optional.ofNullable(
                cbf.create(em, this.getEntityClass(), "e")
                        .where("e.id").eq(id)
                        .getSingleResult()
        );
    }

    public List<E> findAll() {
        return cbf.create(em, this.getEntityClass()).getResultList();
    }

    public boolean existsById(ID id) {
        return cbf.create(em, this.getEntityClass(), "e")
                .where("e.id").eq(id)
                .getCountQuery()
                .getSingleResult() > 0;
    }

    public long count() {
        return cbf.create(em, this.getEntityClass()).getCountQuery().getSingleResult();
    }

    public <T> List<T> findAllToProjection(ObjectBuilder<T> objectBuilder) {
        return cbf.create(em, Tuple.class)
                .from(this.getEntityClass())
                .selectNew(objectBuilder)
                .getResultList();
    }
    protected abstract Class<E> getEntityClass();
}
