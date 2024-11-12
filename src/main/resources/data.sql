INSERT INTO person (id, name)
VALUES
    (1, 'Emily Chen'),
    (2, 'Liam Patel'),
    (3, 'Ava Lee'),
    (4, 'Noah Kim'),
    (5, 'Sophia Rodriguez'),
    (6, 'Ethan Hall'),
    (7, 'Mia Garcia'),
    (8, 'Logan Brooks'),
    (9, 'Isabella Martin'),
    (10, 'Alexander White');


INSERT INTO cat (id, name, age, father_id, mother_id, owner_id)
VALUES
    (1, 'Whiskers', 3, NULL, NULL, 1),
    (2, 'Mittens', 2, NULL, NULL, 3),
    (3, 'Fluffy', 1, NULL, NULL, 5),
    (4, 'Snowball', 4, 1, 2, 2),
    (5, 'Patches', 2, 3, 1, 9),
    (6, 'Simba', 3, 2, 3, 6),
    (7, 'Luna', 1, 4, 5, 6),
    (8, 'Ginger', 4, 6, 4, 4),
    (9, 'Salem', 2, 5, 7, 10),
    (10, 'Tiger', 3, 8, 6, 7);


INSERT INTO cat_kittens (cat_id, kittens_id)
VALUES
    (1, 4),  -- Whiskers is the mother of Snowball
    (1, 5),  -- Whiskers is the mother of Patches
    (2, 4),  -- Mittens is the mother of Snowball
    (3, 6),  -- Fluffy is the mother of Simba
    (3, 7),  -- Fluffy is the mother of Luna
    (4, 8),  -- Snowball is the mother of Ginger
    (5, 9),  -- Patches is the mother of Salem
    (6, 10), -- Simba is the mother of Tiger
    (7, 8),  -- Luna is the mother of Ginger
    (8, 10); -- Ginger is the mother of Tiger