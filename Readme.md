# Flight Reservation System
---
## Steps to deploy Application
1. Clone this repository named flight
```shell
git clone https://github.com/shubhamkalsait/Flight-reservation.git
```

2. Install Mysql Server and create database
```shell
apt update -y
apt install mysql-server -y
mysql_secure_installation
mysql -h rds-endpoint -u root -p
>> create user linux identified by "Redhat";
>> grant all privileges on flightdb.* to linux;
>> flush privileges;
>> create database flightdb;
>> exit
```

3. Deploy Backend
```shell
cd Flight-reservation
cd FlightReservationSystem
apt install openjdk-17-jdk maven -y
export DATASOURCE_URL="jdbc:mysql://rds-endpoint:3306/flightdb"
export DATASOURCE_USER="linux"
export DATASOURCE_PASSWORD="Redhat"
mvn clean package
java -jar target/flight*.jar
```

4. Deploy Frontend (open new tab)
```shell
cd Flight-reservation
cd frontend
apt install nodejs npm -y
export VITE_API_URL=http://external-ip(load_balancer):8080
npm install
npm run build
apt install apache2 -y
cp dist/* /var/www/html/
systemctl start apache2
```

