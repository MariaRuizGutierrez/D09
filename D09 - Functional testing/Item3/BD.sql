drop database if exists `acme-rendezvous2.0`;
create database `acme-rendezvous2.0`;

grant select, insert, update, delete 
	on `acme-rendezvous2.0`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `acme-rendezvous2.0`.* to 'acme-manager'@'%';