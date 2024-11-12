package org.kgromov.view;


import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.Mapping;
import org.kgromov.model.Person;

import java.util.Set;

@EntityView(Person.class)
public interface CatsGroupedByOwnerView extends PersonSimpleView {

 /*   @Mapping("COUNT(kittens)")
    long getKittensCount();*/

    @Mapping("GROUP_CONCAT(kittens.name)")
    Set<String> getKittensNames();
}
