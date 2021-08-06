# Store-Procedure-Spring-Boot
Spring boot Application for Oracle Store Procedure 


#How To Install Oracle Database in Docker


This article provides information about how to install oracle database in docker and oracle usage in docker.

For detailed information about Docker before installation, please refer to my article “What is Docker? Docker Installation and Usage“.
Install Oracle Database in Docker

To install oracle in the docker environment, first you need to register at “hub.docker.com”.

After the registration operation, the required oracle database must be found with search and the required usage agreement must be accepted.

After the required registration and contract operations are done, log in to the user account from the command interpreter as follows.
docker login

Download Oracle Database Docker Image

After login, download the image.
docker pull store/oracle/database-enterprise:12.2.0.1

The Oracle database also has a slim version that does not include tools such as APEX.
docker pull store/oracle/database-enterprise:12.2.0.1-slim

At least 20GB of free space is required to install oracle in the Docker environment.
Create Oracle Database Container From Docker Image

Create the container using the downloaded image.
docker run -d -p 1521:1521 --name oracle store/oracle/database-enterprise:12.2.0.1


It is important for later use to give the appropriate name with the -name parameter.

To use the Oracle APEX tool, the APEX port must also be allowed.
docker run -d -p 8080:8080 -p 1521:1521 --name oracle store/oracle/database-enterprise:12.2.0.1

	
docker run -d -p 8080:8080 -p 1521:1521 --name oracle store/oracle/database-enterprise:12.2.0.1

Oracle Usage in Docker Environment

After the installation is complete, you can connect to the Oracle SQL Plus tool to create users and grant the necessary permissions as follows.
	
docker exec -it oracle bash -c "source /home/oracle/.bashrc; sqlplus /nolog"

Connect to Oracle as sysdba in Docker
	
connect sys as sysdba;

Create Oracle User in Docker
	
create user oracledba identified by admin;

Grant Permission to User in Docker


GRANT ALL PRIVILEGES TO YOUR_USER_NAME;

NOTE: You can grant permissions for certain privileges ​​such as SELECT, INSERT, UPDATE, CREATE TABLE instead of ALL PRIVILEGES.
Connect To SQL Developer in Docker
Username: oracledba
Password: admin
Hostname: localhost
Port: 1521
Service name: ORCLCDB.localdomain
url:- jdbc:oracle:thin:@localhost:1521/ORCLCDB.localdomain

You can execute the following query in SQL Plus to find the Oracle service name value.
	
select value from v$parameter where name='service_names';

Start and Stop Oracle in Docker

Oracle restarts at the start of each operating system using the container name that you provided with the -name parameter during installation.
docker start oracle
	
docker stop oracle

NOTE: We assumed that we set the -name parameter as oracle.

After Oracle installation and user creation process, Oracle database can be managed through Oracle SQL Developer tool according to user permission.

