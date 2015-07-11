-- name: all-users
-- Selects all users
SELECT * FROM users;

-- name: user-by-id
-- Selects user by id (primary key)
SELECT * FROM users WHERE id = :id;

-- name: create-user
-- Creates user and returns id
INSERT INTO users (email, nest_api_key, phone_number)
       VALUES (:email, :nest_api_key, :phone_number)
       RETURNING id;
