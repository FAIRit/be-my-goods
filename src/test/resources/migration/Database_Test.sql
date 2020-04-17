CREATE TABLE users (
    id  BIGSERIAL NOT NULL,
    country VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE avatars (
     id  SERIAL NOT NULL,
     data OID,
     filetype VARCHAR(255),
     user_id INT8,
     PRIMARY KEY (id),
     FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE albums (
    album_id  BIGSERIAL NOT NULL,
    artist VARCHAR(255) NOT NULL,
    musicbrainz_id VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    user_id INT8,
    wiki TEXT,
    PRIMARY KEY (album_id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE tracks (
    id       BIGSERIAL    NOT NULL,
    title    VARCHAR(255) NOT NULL,
    album_id INT8,
    PRIMARY KEY (id),
    FOREIGN KEY (album_id) REFERENCES albums (album_id)
);

CREATE TABLE tags (
    tag_id BIGSERIAL NOT NULL,
    name VARCHAR(255),
    user_id INT8,
    PRIMARY KEY (tag_id),
    FOREIGN KEY (user_id) REFERENCES users
);

CREATE TABLE albums_tags (
     album_id INT8 NOT NULL,
     tag_id INT8 NOT NULL,
     PRIMARY KEY (album_id, tag_id),
     FOREIGN KEY (tag_id) REFERENCES tags,
     FOREIGN KEY (album_id) REFERENCES albums
);




