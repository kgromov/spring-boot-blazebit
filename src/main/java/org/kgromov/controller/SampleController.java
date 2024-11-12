/*
 * Copyright 2014 - 2024 Blazebit.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kgromov.controller;

import org.kgromov.repository.CatSimpleViewRepository;
import org.kgromov.repository.CatWithOwnerViewRepository;
import org.kgromov.repository.OwnerCatsViewRepository;
import org.kgromov.repository.PersonSimpleViewRepository;
import org.kgromov.view.CatSimpleView;
import org.kgromov.view.CatWithOwnerView;
import org.kgromov.view.OwnerCatsView;
import org.kgromov.view.PersonSimpleView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SampleController {

    private final CatSimpleViewRepository catSimpleViewRepository;
    private final PersonSimpleViewRepository personSimpleViewRepository;
    private final CatWithOwnerViewRepository catWithOwnerViewRepository;
    private final OwnerCatsViewRepository ownerCatsViewRepository;

    public SampleController(CatSimpleViewRepository catSimpleViewRepository,
                            PersonSimpleViewRepository personSimpleViewRepository,
                            CatWithOwnerViewRepository catWithOwnerViewRepository,
                            OwnerCatsViewRepository ownerCatsViewRepository) {
        this.catSimpleViewRepository = catSimpleViewRepository;
        this.personSimpleViewRepository = personSimpleViewRepository;
        this.catWithOwnerViewRepository = catWithOwnerViewRepository;
        this.ownerCatsViewRepository = ownerCatsViewRepository;
    }

    @GetMapping("/cats")
    ResponseEntity<List<CatSimpleView>> getAllCats() {
        return ResponseEntity.ok(catSimpleViewRepository.findAll());
    }

    @GetMapping("/persons")
    Iterable<PersonSimpleView> getAllPersons() {
        return personSimpleViewRepository.findAll();
    }

    @GetMapping("/catsWithOwners")
    Iterable<CatWithOwnerView> getCatsWithOwners() {
        return catWithOwnerViewRepository.findAll();
    }

    @GetMapping("/ownerCats")
    Iterable<OwnerCatsView> getOwnerCats() {
        return ownerCatsViewRepository.findAll();
    }

}