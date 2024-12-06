INSERT INTO "user" (email, password, role, first_name, last_name)
VALUES ('owner@gmail.com', '$2a$10$luP.0totUUZm0AlCi4SZA.eFMinCPjoPDpKAHRsrlzEkkhlWoV0DO', 'OWNER', 'hello', 'there');

INSERT INTO subscription (type, count)
VALUES ('BASIC', 5),
       ('INTERMEDIATE', 10),
       ('PRO', 20);

INSERT INTO training (name, type)
VALUES ('Pulling up', 'STRENGTH'),
       ('Push up', 'STRENGTH'),
       ('Run', 'FAT_BURNING'),
       ('Jump', 'FAT_BURNING'),
       ('Stretching', 'RESTORATIVE'),
       ('Sleep', 'RESTORATIVE');
