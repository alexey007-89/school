CREATE TABLE persons (
    id SERIAL,
    name TEXT PRIMARY KEY,
    driver_license BOOLEAN,
    car_id INTEGER REFERENCES cars(id)
);

CREATE TABLE cars (
    id SERIAL,
    manufacturer TEXT PRIMARY KEY,
    model TEXT PRIMARY KEY,
    price INTEGER CHECK ( price > 0 )
)