package org.kgromov.repository;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.JoinType;
import com.blazebit.persistence.ObjectBuilder;
import com.blazebit.persistence.SelectBuilder;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Tuple;
import org.kgromov.model.Cat;
import org.kgromov.projections.CatProjection;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CatCriteriaBuilderRepository extends CriteriaBuilderRepository<Cat, Long> {
    public CatCriteriaBuilderRepository(CriteriaBuilderFactory cbf, EntityManagerFactory entityManagerFactory) {
        super(cbf, entityManagerFactory);
    }

    @Override
    protected Class<Cat> getEntityClass() {
        return Cat.class;
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
        return this.findAllToProjection(new ObjectBuilder<>() {
            @Override
            public <X extends SelectBuilder<X>> void applySelects(X selectBuilder) {
                selectBuilder
                        .select("id")
                        .select("name")
                        .select("age");
            }

            @Override
            public CatProjection build(Object[] tuple) {
                return new CatProjection((Long) tuple[0], (String) tuple[1], (Integer) tuple[2]);
            }

            @Override
            public List<CatProjection> buildList(List<CatProjection> list) {
                return list;
            }
        });
    }
}
