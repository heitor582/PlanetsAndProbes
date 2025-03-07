create table probe (
  id SERIAL primary key,
  cord_x INTEGER not null,
  cord_y INTEGER not null,
  direction VARCHAR(5) not null,
  name varchar(200) not null,
  planet_id INTEGER not null,
   CONSTRAINT fk_id_planet FOREIGN KEY (planet_id)
   REFERENCES planet(id)
   ON DELETE CASCADE
)