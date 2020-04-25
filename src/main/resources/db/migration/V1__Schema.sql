CREATE TABLE application_user (
    user_id  BIGSERIAL NOT NULL,
    country VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE avatar (
     avatar_id  SERIAL NOT NULL,
     data OID,
     filetype VARCHAR(255),
     user_id  INT8,
     PRIMARY KEY (avatar_id),
     FOREIGN KEY (user_id) REFERENCES application_user (user_id)
);

CREATE TABLE album (
    album_id  BIGSERIAL NOT NULL,
    artist VARCHAR(255) NOT NULL,
    musicbrainz_id VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    user_id INT8,
    wiki TEXT,
    PRIMARY KEY (album_id),
    FOREIGN KEY (user_id) REFERENCES application_user(user_id)
);

CREATE TABLE track (
    track_id BIGSERIAL NOT NULL,
    title    VARCHAR(255) NOT NULL,
    album_id INT8,
    PRIMARY KEY (track_id),
    FOREIGN KEY (album_id) REFERENCES album (album_id)
);

CREATE TABLE tag (
    tag_id BIGSERIAL NOT NULL,
    name VARCHAR(255),
    user_id INT8,
    PRIMARY KEY (tag_id),
    FOREIGN KEY (user_id) REFERENCES application_user
);

CREATE TABLE album_tag (
     album_id INT8 NOT NULL,
     tag_id INT8 NOT NULL,
     PRIMARY KEY (album_id, tag_id),
     FOREIGN KEY (tag_id) REFERENCES tag,
     FOREIGN KEY (album_id) REFERENCES album
);




