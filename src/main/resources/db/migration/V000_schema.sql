CREATE TABLE albums (
    id  BIGSERIAL NOT NULL,
    artist VARCHAR(255) NOT NULL,
    musicbrainz_id VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    release_date DATE,
    user_id INT8,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE albums_tags (
    album_id INT8 NOT NULL,
    tags_id INT8 NOT NULL,
    PRIMARY KEY (album_id, tags_id),
    FOREIGN KEY (tags_id) REFERENCES album_tags,
    FOREIGN KEY (album_id) REFERENCES albums
);

CREATE TABLE album_tags (
    id INT8 NOT NULL,
    name VARCHAR(255),
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

CREATE TABLE tracks (
    id  BIGSERIAL NOT NULL,
    title VARCHAR(255) NOT NULL,
    album_id INT8,
    PRIMARY KEY (id),
    FOREIGN KEY (album_id) REFERENCES albums(id)
);

CREATE TABLE users (
    id  BIGSERIAL NOT NULL,
    country VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users_tags (
    user_id INT8 NOT NULL,
    tags_id INT8 NOT NULL,
    PRIMARY KEY (user_id, tags_id),
    FOREIGN KEY (tags_id) REFERENCES users,
    FOREIGN KEY (user_id) REFERENCES album_tags
);