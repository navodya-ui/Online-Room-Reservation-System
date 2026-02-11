CREATE DATABASE oceanview_reservation;

USE oceanview_reservation;

CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL DEFAULT 'STAFF',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reservations (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  reservation_no VARCHAR(30) NOT NULL UNIQUE,
  guest_name VARCHAR(100) NOT NULL,
  address VARCHAR(255) NOT NULL,
  contact VARCHAR(20) NOT NULL,
  room_type VARCHAR(30) NOT NULL,
  check_in DATE NOT NULL,
  check_out DATE NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE room_rates (
  room_type VARCHAR(30) PRIMARY KEY,
  rate_per_night DECIMAL(10,2) NOT NULL CHECK (rate_per_night > 0)
);

CREATE TABLE bills (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  reservation_id BIGINT NOT NULL UNIQUE,
  nights INT NOT NULL CHECK (nights > 0),
  total_amount DECIMAL(10,2) NOT NULL CHECK (total_amount >= 0),
  generated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_bills_reservation
    FOREIGN KEY (reservation_id) REFERENCES reservations(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

INSERT INTO room_rates (room_type, rate_per_night) VALUES
('Single', 8000.00),
('Double', 12000.00),
('Deluxe', 18000.00),
('Suite', 25000.00);

INSERT INTO users (username, password_hash, role) VALUES
('admin', 'TEMP_HASH_admin123', 'ADMIN'),
('staff1', 'TEMP_HASH_staff123', 'STAFF');

SHOW TABLES;

SELECT * FROM room_rates;
select * from users;
SELECT id, username, role, created_at FROM users;

CREATE INDEX idx_reservation_no ON reservations(reservation_no);

DESCRIBE users;

UPDATE users SET password_hash =
'$2a$10$2n7v4XxB6KzqA7qvI3XvUe7B7m5W8q3v9fJbKJp0h8Yk4uZ8m0Y8e'
WHERE username='admin';

UPDATE users SET password_hash =
'$2a$10$Hq6o9cY5nqzv1rHk0l5eT.2m7oR9nYcO0xJ2wV9h1Vq6u8pK3c3dG'
WHERE username='staff1';

SELECT username, password_hash FROM users;

DELETE FROM users WHERE username IN ('admin', 'staff1');

SELECT username, password_hash FROM users WHERE username IN ('admin', 'staff1');