ALTER TABLE users RENAME COLUMN username TO email;
--;;
ALTER TABLE users ALTER COLUMN email SET NOT NULL;
--;;
ALTER TABLE users ADD COLUMN phone_number varchar(16) NOT NULL;
