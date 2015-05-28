-- name: all-users
-- Selects all users
SELECT * FROM users;

-- name: user-by-id
-- Selects user by id (primary key)
SELECT * FROM users WHERE id = :id;

-- name: create-user
-- Creates user and returns id
INSERT INTO users (username, nest_api_key)
       VALUES (:username, :nest_api_key)
       RETURNING id;
