package org.kgromov.repository;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.JoinType;
import com.blazebit.persistence.ObjectBuilder;
import com.blazebit.persistence.SelectBuilder;
import com.blazebit.persistence.view.EntityViewManager;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Tuple;
import org.kgromov.model.Cat;
import org.kgromov.projections.CatProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class CatCriteriaBuilderRepository {
    private final CriteriaBuilderFactory cbf;
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager em;

    public CatCriteriaBuilderRepository(CriteriaBuilderFactory cbf, EntityManagerFactory entityManagerFactory) {
        this.cbf = cbf;
        this.entityManagerFactory = entityManagerFactory;
        em = entityManagerFactory.createEntityManager();
    }

    public Optional<Cat> findById(Long id) {
        return Optional.ofNullable(
                cbf.create(em, Cat.class, "c")
                        .where("c.id").eq(id)
                        .getSingleResult()
        );
    }

    public List<Cat> findAll() {
        return cbf.create(em, Cat.class).getResultList();
    }

    public boolean existsById(Long id) {
        return cbf.create(em, Cat.class, "c")
                .where("c.id").eq(id)
                .getCountQuery().getSingleResult() > 0;
    }

    public long count() {
        return cbf.create(em, Cat.class).getCountQuery().getSingleResult();
    }

    public String getCatNamesByOwnerId(Long ownerId) {
        return cbf.create(em, String.class)
                .from(Cat.class, "c")
                .join("c.owner", "p", JoinType.INNER)
                .where("p.id").eq(ownerId)
                .groupBy("c.owner.id")
                .select("GROUP_CONCAT(c.name)")
                .getSingleResult();
    }

    public List<CatProjection> findAllToProjection() {
        return cbf.create(em, Tuple.class)
                .from(Cat.class, "c")
                .select("c.id")
                .select("c.name")
                .select("c.age")
                .orderByAsc("c.name")
                .getResultStream()
                .map(t -> new CatProjection(t.get(0, Long.class), t.get(1, String.class), t.get(2, Integer.class)))
                .toList();
    }

    // Alternative framework specific way of explicit mapping from Tuple. So EntityView should be used for dto projection in most cases
    public List<CatProjection> findAllToProjectionWithObjectBuilder() {
        return cbf.create(em, Tuple.class)
                .from(Cat.class, "c")
                .selectNew(new ObjectBuilder<CatProjection>() {
                    @Override
                    public <X extends SelectBuilder<X>> void applySelects(X selectBuilder) {
                        selectBuilder
                                .select("c.id")
                                .select("c.name")
                                .select("c.age");
                    }

                    @Override
                    public CatProjection build(Object[] tuple) {
                        return new CatProjection((Long) tuple[0], (String) tuple[1], (Integer) tuple[2]);
                    }

                    @Override
                    public List<CatProjection> buildList(List<CatProjection> list) {
                        return list;
                    }
                })
                .orderByAsc("c.name")
                .getResultList();
    }
}
