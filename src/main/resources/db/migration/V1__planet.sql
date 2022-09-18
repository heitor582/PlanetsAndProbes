create table planet (
    id SERIAL primary key,
    max_x INTEGER not null,
    max_y INTEGER not null,
    name varchar(200) not null
)