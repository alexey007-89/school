select *
from student
where age >= 8 and age <= 10;

select name from student;

select * from student where lower (name) like '%b%';


select * from student where age < student.id;

select * from student order by age;
