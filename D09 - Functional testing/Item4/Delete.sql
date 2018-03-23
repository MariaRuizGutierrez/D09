start transaction;
use `Acme-Rendezvous2.0`;
revoke all privileges on `Acme-Rendezvous2,0`.* from 'acme-user'@'%';
revoke all privileges on `Acme-Rendezvous2.0`.* from 'acme-manager'@'%';
drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';
drop database `Acme-Rendezvous2.0`;
commit;