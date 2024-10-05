-- 1. Вывести к каждому самолету класс обслуживания и количество мест этого класса

select aircraft_code, fare_conditions, count(seat_no)
from seats
group by aircraft_code, fare_conditions
order by aircraft_code, fare_conditions;

-- 2. Найти 3 самых вместительных самолета (модель + кол-во мест)

select aircraft_code, count(seat_no) as number_of_seats
from seats
group by aircraft_code
order by number_of_seats desc
limit 3;

-- 3. Найти все рейсы, которые задерживались более 2 часов

select flight_no
from flights
where date_part('hour', actual_departure - scheduled_departure) > 2;

-- 4. Найти последние 10 билетов, купленные в бизнес-классе (fare_conditions = 'Business'),
--    с указанием имени пассажира и контактных данных

select book_date, passenger_name, contact_data
from tickets
         join ticket_flights using (ticket_no)
         join bookings using (book_ref)
where fare_conditions = 'Business'
order by book_date desc
limit 10;

-- 5. Найти все рейсы, у которых нет забронированных мест в бизнес-классе (fare_conditions = 'Business')

select flight_id
from ticket_flights
where flight_id != any (select flight_id from ticket_flights where fare_conditions = 'Business' group by flight_id)
  and fare_conditions != 'Business'
group by flight_id;

-- 6. Получить список аэропортов (airport_name) и городов (city), в которых есть рейсы с задержкой по вылету

select airport_name, city
from airports_data
         right join flights on departure_airport = airport_code
where actual_departure is not null
group by airport_name, city;

-- 7. Получить список аэропортов (airport_name) и количество рейсов, вылетающих из каждого аэропорта,
--    отсортированный по убыванию количества рейсов

select airport_name, count(flight_id) as number_of_flights
from airports_data
         join flights on departure_airport = airport_code
group by departure_airport, airport_name
order by number_of_flights desc;

-- 8. Найти все рейсы, у которых запланированное время прибытия (scheduled_arrival)
--    было изменено и новое время прибытия (actual_arrival) не совпадает с запланированным

select *
from flights
where actual_arrival != scheduled_arrival;

-- 9. Вывести код, модель самолета и места не эконом класса для самолета "Аэробус A321-200" с сортировкой по местам

select aircraft_code, model, seat_no
from aircrafts_data
         join seats using (aircraft_code)
where model ->> 'ru' = 'Аэробус A321-200'
  and fare_conditions != 'Economy'
order by seat_no;

-- 10. Вывести города, в которых больше 1 аэропорта (код аэропорта, аэропорт, город)

select city
from airports_data
group by city
having count(airport_code) > 1;

-- 11. Найти пассажиров, у которых суммарная стоимость бронирований превышает среднюю сумму всех бронирований

select passenger_name
from tickets
         join bookings using (book_ref)
where total_amount < (select avg(total_amount) from bookings);

-- 12. Найти ближайший вылетающий рейс из Екатеринбурга в Москву, на который еще не завершилась регистрация

select *
from flights
where status = 'Scheduled'
  and departure_airport = 'SVX'
  and (arrival_airport = 'SVO' or arrival_airport = 'VKO' or arrival_airport = 'DME')
order by scheduled_departure desc
limit 1;

-- 13. Вывести самый дешевый и дорогой билет и стоимость (в одном результирующем ответе)

select distinct ticket_no, amount
from ticket_flights
where amount = (select min(amount) from ticket_flights)
   or amount = (select max(amount) from ticket_flights)
order by amount;

-- 14. Написать DDL таблицы Customers, должны быть поля id, firstName, LastName, email, phone.
--     Добавить ограничения на поля (constraints)

create table customers
(
    id         integer generated always as identity,
    first_name varchar(30) not null,
    last_name  varchar(30) not null,
    email      varchar(30) not null,
    phone      varchar(30) not null,
    constraint customers_pk primary key (id),
    constraint check_email check (email ~ '[A-Za-z0-9\._%+\-]+@[A-Za-z0-9\.\-]+\.[A-Za-z]{2,}'),
    constraint check_phone check (phone ~ '(?:([+]\d{1,4})[-.\s]?)?(?:[(](\d{1,3})[)][-.\s]?)?(\d{1,4})[-.\s]?(\d{1,4})[-.\s]?(\d{1,9})')
);

-- 15. Написать DDL таблицы Orders, должен быть id, customerId, quantity.
--     Должен быть внешний ключ на таблицу customers + constraints

create table orders
(
    id          integer generated always as identity,
    customer_id integer not null,
    quantity    integer not null,
    constraint orders_pk primary key (id),
    constraint customer_fk foreign key (customer_id) references customers (id)
);

-- 16. Написать 5 insert в эти таблицы

insert into customers (first_name, last_name, email, phone)
values ('f_name1', 'l_name1', 'email1@email.com', '1111');

insert into customers (first_name, last_name, email, phone)
values ('f_name2', 'l_name2', 'email2@email.com', '2222');

insert into customers (first_name, last_name, email, phone)
values ('f_name3', 'l_name3', 'email3@email.com', '3333');

insert into customers (first_name, last_name, email, phone)
values ('f_name4', 'l_name4', 'email4@email.com', '4444');

insert into customers (first_name, last_name, email, phone)
values ('f_name5', 'l_name5', 'email5@email.com', '5555');

insert into orders (customer_id, quantity)
values (1, 3);

insert into orders (customer_id, quantity)
values (2, 6);

insert into orders (customer_id, quantity)
values (3, 9);

insert into orders (customer_id, quantity)
values (4, 12);

insert into orders (customer_id, quantity)
values (5, 15);

-- 17. Удалить таблицы

drop table customers, orders