# creditCardManager
Basic Login, Card Creation and Search using SpringBoot, JPA, Thymeleaf and H2.

This is a test application which uses SpringBoot along with JPA and Thymeleaf. This application has an embedded H2 database which is loaded with dummy data at startup. To run this application, simply use the command mvn spring-boot:run and then access the url http://localhost:8080/login through the browser.

A default admin user is created at startup having the credentials admin/admin

There are 4 main screens:

1) Login Screen

2) User Registration Screen

    username (text 3-10chars)    
    password (text 3-20chars)
    
3) Card Registration

    cardNumber (digits 12 -22)    
    clientName (text 2-35)    
    expiryDate (text YY/MM)
    
4)Search

Some very basic test scenarios were made available.
      


