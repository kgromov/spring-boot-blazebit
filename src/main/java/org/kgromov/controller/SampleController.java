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

import org.kgromov.model.Cat;
import org.kgromov.projections.CatProjection;
import org.kgromov.repository.*;
import org.kgromov.view.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class SampleController {

    private final CatSimpleViewRepository catSimpleViewRepository;
    private final PersonSimpleViewRepository personSimpleViewRepository;
    private final CatWithOwnerViewRepository catWithOwnerViewRepository;
    private final OwnerCatsViewRepository ownerCatsViewRepository;
    private final CatsGroupedByOwnerViewRepository catsGroupedByOwnerViewRepository;
    private final CatCriteriaBuilderRepository catCriteriaBuilderRepository;

    public SampleController(CatSimpleViewRepository catSimpleViewRepository,
                            PersonSimpleViewRepository personSimpleViewRepository,
                            CatWithOwnerViewRepository catWithOwnerViewRepository,
                            OwnerCatsViewRepository ownerCatsViewRepository,
                            CatsGroupedByOwnerViewRepository catsGroupedByOwnerViewRepository,
                            CatCriteriaBuilderRepository catCriteriaBuilderRepository) {
        this.catSimpleViewRepository = catSimpleViewRepository;
        this.personSimpleViewRepository = personSimpleViewRepository;
        this.catWithOwnerViewRepository = catWithOwnerViewRepository;
        this.ownerCatsViewRepository = ownerCatsViewRepository;
        this.catsGroupedByOwnerViewRepository = catsGroupedByOwnerViewRepository;
        this.catCriteriaBuilderRepository = catCriteriaBuilderRepository;
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

    @GetMapping("/catsGroupedByOwner")
    Iterable<CatsGroupedByOwnerView> getCatsGroupedByOwner() {
        return catsGroupedByOwnerViewRepository.findAll();
    }

    @GetMapping("/criteria")
   void testCriteriaBasedRepository() {
        var cats = catCriteriaBuilderRepository.findAll();
        var catById = catCriteriaBuilderRepository.findById(1L);
        var existsById = catCriteriaBuilderRepository.existsById(1L);
        var count = catCriteriaBuilderRepository.count();
        var catNamesByOwnerId = catCriteriaBuilderRepository.getCatNamesByOwnerId(6L);
        List<CatProjection> catsToProjection = catCriteriaBuilderRepository.findAllToProjection();
    }
}