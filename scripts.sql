select *
from student
where age >= 8 and age <= 10;

select name
from student;

select *
from student
where name like '%a%';


select *
from student
where age < student.id;

select *
from student
order by age;
