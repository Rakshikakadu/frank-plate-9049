# frank-plate-9049
<p align="center">
  <img src="https://user-images.githubusercontent.com/105484277/208636032-ae5be720-bb0b-4fad-ad3a-4f5aaf56001e.png" alt="CabWay" />
</p>
  
 #  CabWay(Online Cab Booking System)
This application is a REST API for an Online Cab Booking Management System.Using this web service a customer can book a trip, an admin can assign a driver to a particular trip, and a driver can complete the trip. All these CRUD operations require validation on each step.




### Tech Stacks

- Spring Boot 
- Spring Framework
- Spring Data JPA 
- MySQL 
- Hibernate
- Java
- Layered Architechture
- Swagger UI
- Lombok
- Postman


### Modules
- Login Module
-	Admin Module
-	Customer Module
-	Driver Module
-	Trip Booking Module

### Functionalities
- Common Functionalities :- Login, Logout and registration to the system.
* Admin Functionalities :-
    * Administrator Role of the entire application
    * Only registered admins with valid session token can add/update/delete driver or customer from main database
    * Admin can access the details of different customers, drivers and trip bookings
    * Admin can assign a driver to a particular trip.
* Customer Functionalities :-
    * Registering themselves with application, and logging in to get the valid session token
    * Book a trip.
    * Access his trip history, profile updation, update trip details.
    * Rate a driver.    
* Driver Functionalities :-
    * Driver can login, update his profile.
    * Driver can update their cab details
    * Check trips assigned to him.
    * Can generate a bill.

##   ER_Diagram                                            
![Book Cab ER Diagram](https://user-images.githubusercontent.com/105484277/208635652-24755177-0a30-4601-9eb4-e934d45f2b8d.png)

### Installation & Run
- Before running the API server, you have to update the database configuration inside the application.properties file
- Update the port number, username and password as per your local database configuration
````
    server.port=8087

    spring.datasource.url=jdbc:mysql://localhost:3306/cabwayDB;
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.username=root
    spring.datasource.password=root
    
````
## API Root Endpoint

`https://localhost:8087/`

`http://localhost:8087/swagger-ui.html`


## API Module Endpoints

### Admin Module

* POST /admins :- Register admin

* `PUT /admins :- Update admin details.

* PUT /admins/tripbooking/bill/{cid} :- Generate a bill for a trip.

* PUT /admin/updateTripBooking/{userId} :- Update trip status to completed or cancelled.

* GET /admins/viewCabByType/{carType} :- View cabs of particular type.

* GET /admins/tripbookings/datewise/{date} :- View all trips for a particular day.

* GET /admins/tripbookings/datewise/{cid}/{sdate}/{edate} :- Check all trips for a particular date range.

* GET /admins/driver/{driverId} :- Check a driver with driver id.

* GET /admins/customers :- Check all customers in the database.

* GET /admins/customers/tripbookings :- Check all trips.

* GET /admins/customer/{cid} :- Check a particular customer

* GET /admins/customer/tripbookings/{cid} :- Check all trips of a particular customer.

* GET /admins/countCabsOfType/{carType} :- Count cabs of a particular type.

* GET /admins/bestdrivers :- Check drivers with rating over 4.5.

* DELETE /admins/{aid} :- Delete an admin.

* DELETE /admins/deletetripBooking/{tbid}/{uid} :- Delete trip if status is completed or cancelled.

* DELETE /admins/deleteDriver/{driverId} :- Delete a driver from the system.


### Customer Module

* POST /customer :- Register a customer.

* PUT /updateCustomer :- Update customer details.

* POST /customers/tripBook/{cid} :- Book a trip.

* PUT /customer/updateTripBooking/{userId} :- Update trip booking details.

* PUT /customer/ratedriver :- Rate a driver.

* GET /validateCustomer :- Validate himself.

* GET /customers/{customerId} :- Get his details

* GET /customer/tripbookings/{cid} :- Get trips history.

* DELETE /deleteCustomer/{customerId} :- Delete his account.


### Driver Module

* POST /driver :- Register a driver.

* PUT /updateDriver :- Update his details.

* PUT /drivers/updateCab :- Update cab details.

* PUT /driver/updateTripBooking/{userId} :- Mark trip as completed or cancelled.

* PUT /driver/tripbooking/bill/{customerid} :- Generate bill.

* GET /driver/{driverId} :- Get his details.

* DELETE /deleteDriver/{driverId} :- Delete his account.

### Login Module

* `POST /login :- Login to the system, get uuid.

* DELETE /logout :- Logout of the system.

### Book a Trip
![AdminController](https://user-images.githubusercontent.com/105484277/208647381-d6f6af91-8383-48fb-a767-1a305faf6361.PNG)
![CustomerController](https://user-images.githubusercontent.com/105484277/208647398-c0e92499-12d3-4f45-8b67-ecde97899e1d.PNG)

![Driver module](https://user-images.githubusercontent.com/105484277/208647508-aa602de8-65ae-4e6b-a8b9-2fd99d8a3262.PNG)
![LoginController](https://user-images.githubusercontent.com/105484277/208647531-d6ebed0f-c846-4132-bb7f-27582d6f42f6.PNG)



#### This project is developed by team of 3 Java Back-End Developers during construct week at Masai School
### Contributors

- [@Rakshika_Kadu](https://github.com/Rakshikakadu)
- [@Tarique_Anwar](https://github.com/tarique076)
- [@Dinesh_Shendre](https://github.com/dyshendre)
#### For any feedback, report, suggestions, you can contact with anyone of the team members.
### THANK YOU
