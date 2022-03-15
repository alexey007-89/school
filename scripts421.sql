ALTER TABLE student
    ADD CONSTRAINT age_constraint CHECK ( age < 16 ),
    ADD PRIMARY KEY (name),
    ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE faculty
    ADD CONSTRAINT name_color_unique UNIQUE (name,color),
    ALTER COLUMN name SET NOT NULL;

