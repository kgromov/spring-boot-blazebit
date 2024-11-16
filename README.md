**Spring Boot Project with Blazebit**

Blaze Persistence is a library that lives on top of a JPA provider and tries to solve these and many more problems a developer faces when having complex requirements. It is composed of multiple modules that all depend on the core module which this documentation deals with.

The core module tries to ease the pain of writing dynamic queries by offering a fluent builder API that puts readability first. In addition to that, it also integrates deeply with the JPA provider to provide advanced SQL features that not even the JPA providers offer.    
The deep integration makes it possible to even workaround some known JPA provider bugs.

The entity view module builds on top of the core module and provides a way to define DTOs with mappings to the entity model. The mapping information is used in the query builder to generate projections that perfectly fit the DTO structure along with possible required joins.

The jpa-criteria module is an implementation of the JPA Criteria API based on the query builder of the core module. It offers extensions to the JPA Criteria API that enable the use of some of the concepts and advanced features that are also offered by the core module.   
The main intent of this module is to ease the migration of existing queries or to allow the use of advanced features in existing queries on a case by case basis.

**Dependencies**
It's better to use BOM to import all needed dependencies with proper versions.

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.blazebit</groupId>
            <artifactId>blaze-persistence-bom</artifactId>
            <version>${blazebit.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Then one of core dependencies is needed. E.g. for jakarta:
```xml
        <dependency>
            <groupId>com.blazebit</groupId>
            <artifactId>blaze-persistence-core-api-jakarta</artifactId>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>com.blazebit</groupId>
            <artifactId>blaze-persistence-core-impl-jakarta</artifactId>
            <scope>runtime</scope>
        </dependency>
```

Then one of integration dependencies:
- hibernate (with proper version)
- spring-data;
- eclipselink;
- querydsl;
- quarkus
- etc.

For more details see [Blaze Persistence documentation](https://persistence.blazebit.com/documentation/1.6/core/manual/en_US).

**Features**

*   **Entity Views**: This project uses entity views to simplify complex database queries. Entity views are a feature of Blaze Persistence that allows you to define custom views on top of your entities.
*   **Repository Integration**: The project integrates the repository pattern, which provides a simple and consistent way to interact with the database.
*   **Spring Boot Integration**: This project uses Spring Boot as its application framework.

**Project Structure**

The project is structured into several modules:

*   **model**: This module contains the entity classes that represent the data in the database.
*   **view**: This module contains the entity views that simplify complex database queries.
*   **repository**: This module contains the repository classes that provide a simple and consistent way to interact with the database.
*   **service**: This module contains the service classes that encapsulate business logic.

**Example Use Cases**

This project provides several example use cases that demonstrate the features of Blaze Persistence:

*   **Entity Views**: The project uses entity views to simplify complex database queries.
*   **Repositories based on CriteriaBuilderFactory**: Manual implementation - `CriteriaBuilderRepository` for main methods like:   
`findAll`, `findById`, `existsById`, `count` with some extensions like `findAllToProjection` - to project entity to some view.

Entity view sample:
```java
@EntityView(Cat.class)
public interface CatSimpleView {
    
    @IdMapping
    Long getId();

    String getName();
}

public interface CatSimpleViewRepository extends EntityViewRepository<CatSimpleView, Long> {
    
}
```

In this example `EntityViewRepository` is interface on top of `JpaRepository` for dto projections (dto class is actually entity view).  
So both methods by name and `@Query` will work.
Base implementation - `EntityViewAwareRepositoryImpl`.

`CriteriaBuilderRepository` is custom implementation to use `CriteriaBuilderFactory` for more complex queries that converted to jpql    
or even more complex - to use CTE, aggregation, subqueries, keyset pagination etc.
e.g.:
```java
public abstract class CriteriaBuilderRepository<E, ID> {

    protected final CriteriaBuilderFactory cbf;
    protected final EntityManager em;

    public CriteriaBuilderRepository(CriteriaBuilderFactory cbf, EntityManagerFactory entityManagerFactory) {
        this.cbf = cbf;
        this.em = entityManagerFactory.createEntityManager();
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

    public <T> List<T> findAllToProjection(ObjectBuilder<T> objectBuilder) {
        return cbf.create(em, Tuple.class)
                .from(this.getEntityClass())
                .selectNew(objectBuilder)
                .getResultList();
    }
    protected abstract Class<E> getEntityClass();
}

```
where `ObjectBuilder<T> objectBuilder` is kind of mapping implementation for tuple to dto projection.   
Due to lots of ceremony (and boilerplate) it's better to use EntityViewRepository instead.
