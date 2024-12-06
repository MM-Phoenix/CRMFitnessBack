create table "user"
(
    id         bigserial primary key,
    email      varchar(30)  not null,
    password   varchar(100) not null,
    role       varchar(10)  not null,
    first_name varchar(30)  not null,
    last_name  varchar(30)  not null,
    constraint unique_user_email unique (email)
);

create table training
(
    id   bigserial primary key,
    name varchar(30) not null,
    type varchar(30) not null,
    constraint unique_training_email unique (name)
);

create table schedule
(
    date_from   bigint primary key,
    date_to     bigint not null,
    trainer_id  bigint not null,
    training_id bigint not null,
    constraint fk_schedule_trainer_id foreign key (trainer_id) references "user" (id),
    constraint fk_schedule_training_id foreign key (training_id) references training (id)
);

create table schedule_registration
(
    user_id     bigint,
    schedule_id bigint,
    primary key (user_id, schedule_id),
    constraint fk_schedule_registration_user_id foreign key (user_id) references "user" (id),
    constraint fk_schedule_registration_schedule_id foreign key (schedule_id) references schedule (date_from)
);

create table subscription
(
    type  varchar(30) primary key,
    count int not null
);

create table user_subscription
(
    user_id         bigint primary key,
    subscription_id varchar(30) not null,
    remaining_count int,
    constraint fk_user_subscription_user_id foreign key (user_id) references "user" (id),
    constraint fk_user_subscription_subscription_id foreign key (subscription_id) references subscription (type)
);
