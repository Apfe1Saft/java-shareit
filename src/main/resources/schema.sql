create table if not exists items
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    film_name varchar,
    description varchar,
    available boolean,
    owner_id integer,
    request_id integer
);
create table if not exists users
(
    id integer PRIMARY KEY AUTO_INCREMENT,
    name varchar,
    email varchar,
    FOREIGN KEY (id) REFERENCES items(owner_id)
);
create table if not exists itemrequests
(
    id integer PRIMARY KEY AUTO_INCREMENT,
    description varchar,
    requestor_id integer,
    created data,
    FOREIGN KEY (id) REFERENCES items(request_id)
);