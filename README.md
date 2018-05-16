# cnam.nsy209.selServices.server
CNAM NSY209 project 2018 - backend side for SEL Services

## Installation
### Pre-requisites
* [PostgreSQL](https://www.postgresql.org/) installed
* [GLASSFISH](https://javaee.github.io/glassfish/download) installed
* [Java 8 SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) installed
* [Eclipse Oxygen](http://www.eclipse.org/downloads/eclipse-packages/) for JavaEE developper or more installed
* Maven pluggin installed
* Glassfish server synchronized in Eclipse

### POSTGRESQL Configurations
* PostgreSQL is running
* open **pgadmin** in your browser (localhost:5050)
* create a database 'yourDataBase' with your postgresql credentials

### GLASSFISH Configurations
* Edit 'settings.xml' file of this repository and change tag path <...home> by your GLASSFISH installation path
* Save file and put it in your ‘.m2’ folder of your maven installation
* Start GLASSFISH
* Open your browser and type url ‘localhost:4848’ to open GLASSFISH Administration tools
* Go to 'Ressources/JDBC/JDBC-CONNECTION-POOL' to create a connection pool named ‘postgresqlpool’:
  * Ressource type : javax.sql.ConnectionPoolDataSource 
  * Datasource Classname : org.postgresql.ds.PGConnectionPoolDataSource)
* In 'Additonnal Properties' tag add 2 properties (User – yourPostgreUserName et Password – yourPostgrePassword)
* Put 'PortNumber' to 5432
* Go to Resources/JDBC/JDBC Resources to create a new resource named ‘jdbc/yourDataBase’
* Select ‘PostgresqlPool’ as his Pool Name

### Process
* Clone this project
* import this project in Eclipse
* Open 'persistence.xml’ file under 'META-INF' folder
* change *<jta-data-source>jdbc/test</jta-data-source>* in *<jta-data-source>jdbc/yourDataBase</jta-data-source>*
* change properties ‘javax.persistence.jdbc...’ according to your configurations above
* Go to 'run Configurations' -> right click on 'Maven Build' -> 'new'
* At the 'Main' tab fill 'Goals' as **clean install**
* Click on 'run'
* make sure GLASSFISH is running
* Go to 'run Configurations' -> right click on 'Maven Build' -> 'new'
* At the 'Main' tab fill 'Goals' as **glassfish:deploy** or **glassfish:redeploy**
* Click on 'run'
* The project is deployed on GLASSFISH
