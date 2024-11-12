package org.kgromov.view;

import com.blazebit.persistence.view.EntityView;
import org.kgromov.model.Person;

import java.util.Set;

@EntityView(Person.class)
public interface OwnerCatsView extends PersonSimpleView{
    Set<CatSimpleView> getKittens();
}
