Fuel consumption service is spring boot application and it is running on 8082 port. Following are the some info about services:
- Java 8
- RestFul API with JSON payload response
- Spring boot and framework
- Mockito framework
- H2 database
- Maven

There is 4 endpoint where user interact with the fuel consumption service.

1) fuel/upload      : This endpoint is using to insert the fuel consumption request. Request should be inserted with the file using multipart/form-data. Request example is in
                    hello.txt. User can insert single record or bulk of record.

2) fuel/total       : This endpoint is calculated the total spend money grouped by month. Input for "/total" is the driver id. You can insert single driver Id (ex: ["id_1"]
                    or multiple driver id. Ex: ["id_1","id_2","3"].

3) fuel/month/{date}      : This endpoint is calculate the list of fuel consumption according provided month. The driver id is the request data and date is PathVariable. User is able
                    to input one more driver id.

4) /consumption : This endpoint is used to calculate the statics of fuel consumption with each month. The input variable single or list of driver Id.


NOTE:
You can open this and test using swagger:
http://localhost:8080/swagger-ui.html