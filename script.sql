
CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE Users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL CHECK (role IN ('USER', 'ADMIN')),
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       last_active TIMESTAMP,
                       description TEXT,
                       avatar_url VARCHAR(255)
);

CREATE TABLE SmokingPreferences (
                                    id BIGSERIAL PRIMARY KEY,
                                    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE UserPreferences (
                                 user_id BIGINT NOT NULL,
                                 preference_id BIGINT NOT NULL,
                                 PRIMARY KEY (user_id, preference_id),
                                 FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
                                 FOREIGN KEY (preference_id) REFERENCES SmokingPreferences(id) ON DELETE CASCADE
);

CREATE TABLE Events (
                        id BIGSERIAL PRIMARY KEY,
                        creator_id BIGINT NOT NULL,
                        start_time TIMESTAMP NOT NULL,
                        location VARCHAR(100) NOT NULL,
                        description TEXT,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        max_participants INT NOT NULL,
                        latitude DECIMAL(9,6) NOT NULL,
                        longitude DECIMAL(9,6) NOT NULL,
                        CONSTRAINT valid_latitude CHECK (latitude BETWEEN -90 AND 90),
                        CONSTRAINT valid_longitude CHECK (longitude BETWEEN -180 AND 180),
                        FOREIGN KEY (creator_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE EventParticipants (
                                   event_id BIGINT NOT NULL,
                                   user_id BIGINT NOT NULL,
                                   PRIMARY KEY (event_id, user_id),
                                   FOREIGN KEY (event_id) REFERENCES Events(id) ON DELETE CASCADE,
                                   FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Messages (
                          id BIGSERIAL PRIMARY KEY,
                          sender_id BIGINT NOT NULL,
                          receiver_id BIGINT NOT NULL,
                          event_id BIGINT,
                          text TEXT NOT NULL,
                          sent_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (sender_id) REFERENCES Users(id) ON DELETE CASCADE,
                          FOREIGN KEY (receiver_id) REFERENCES Users(id) ON DELETE CASCADE,
                          FOREIGN KEY (event_id) REFERENCES Events(id) ON DELETE SET NULL
);

CREATE TABLE Friendships (
                             user1_id BIGINT NOT NULL,
                             user2_id BIGINT NOT NULL,
                             status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'ACCEPTED', 'DECLINED')),
                             PRIMARY KEY (user1_id, user2_id),
                             FOREIGN KEY (user1_id) REFERENCES Users(id) ON DELETE CASCADE,
                             FOREIGN KEY (user2_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Notifications (
                               id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT NOT NULL,
                               type VARCHAR(20) NOT NULL CHECK (type IN ('MESSAGE', 'INVITE', 'FRIEND_REQUEST', 'EVENT_CREATED')),
                               text TEXT NOT NULL,
                               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE UserLocations (
                               id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT UNIQUE NOT NULL,
                               latitude DECIMAL(9,6) NOT NULL,
                               longitude DECIMAL(9,6) NOT NULL,
                               is_ready_to_smoke BOOLEAN NOT NULL DEFAULT FALSE,
                               last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               CONSTRAINT valid_latitude CHECK (latitude BETWEEN -90 AND 90),
                               CONSTRAINT valid_longitude CHECK (longitude BETWEEN -180 AND 180),
                               FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);


CREATE INDEX idx_users_username ON Users(username);
CREATE INDEX idx_users_email ON Users(email);
CREATE INDEX idx_userlocations_coordinates ON UserLocations(latitude, longitude);