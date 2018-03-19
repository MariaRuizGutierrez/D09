drop database if exists `acme-rendezvous`;
create database `acme-rendezvous`;

grant select, insert, update, delete 
	on `acme-rendezvous`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `acme-rendezvous`.* to 'acme-manager'@'%';