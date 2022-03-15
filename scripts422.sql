CREATE TABLE persons (
    id SERIAL PRIMARY KEY,
    name TEXT,
    driver_license BOOLEAN,
    car_id INTEGER REFERENCES cars(id)
);

CREATE TABLE cars (
    id SERIAL PRIMARY KEY,
    manufacturer TEXT ,
    model TEXT,
    price INTEGER CHECK ( price > 0 )
)