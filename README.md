# Car rental 

The task of the final project is to develop a web application that supports the functionality according to the task
variant.

There is a list of Cars in the system, for which it is necessary to implement:
- choice by brand;
- choice according to quality class;
- sort by rental price;
- sort by name.

## Customer
The customer registers in the system, chooses a car and makes a rental order. An unregistered customer cannot place an order. In the order data the client indicates passport data, option with driver / without driver, lease term. The system generates an Invoice, which the client pays. 

## Manager
The manager reviews the order and may reject it, giving a reason. The manager also registers the return of the car, in case of car damage he issues an invoice for repairs through the system.

## Administrator
The system administrator has the rights:
- adding, deleting cars, editing car information;
- blocking / unblocking- the user;
- registration of managers in the system.

## Components used for the project:
- Java 8
- Maven
- Git
- JavaEE: Servlet, JSP, JSTL, JavaMail
- Server/Servlet container: Tomcat 10
- Database: MySQL
- Logger: Log4J2
- Tests: JUnit 5, Mockito
