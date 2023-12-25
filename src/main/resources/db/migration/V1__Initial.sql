CREATE TABLE IF NOT EXISTS t_user (
                                      id SERIAL PRIMARY KEY,
                                      username VARCHAR(255),
                                      password VARCHAR(255),
                                      phone_number VARCHAR(255)
);
