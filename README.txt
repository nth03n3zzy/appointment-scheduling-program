Title: Super Appointment Scheduler.

Purpose: The purpose of this application is to provide users with a simple appointment scheduler.
    the application converts time zones from system default time and checks them against EST making scheduling
    simple. the application interacts with a database and has the ability to read, write and delete from that database.
    it has support for customers in the US, UK, and Canada. customers and appointments can be added edited and deleted
    in the application. customers and appointments can also be searched, and appointment schedules can be filtered.
    any manipulations to users, contacts, appointment types must be done in the database itself the application limits
    controls to the user in this regard for system integrity and security.

Author: Anthony Collins.

Contact: aco1147@wgu.edu

Version:1.0

date: 21APR23

IDE: IntelliJ IDEA 2022.2.3 (Community Edition)

JDK Version: java version "17.0.5"
JavaFX version: JavaFX-SDK 17.0.2

Directions: program is launched by running the main method. the user may log in to the program with either
    username test, password test, or username admin password admin.
    once logged in the user will be taken to the calender view from here all appointments will be
    displayed. they can be filtered by current week and month, and searched by appointment ID or customer ID.
    all customers can also be viewed and searched by ID and name. to edit a customer or appointment select the
    customer or appointment and click the modify button. make the desired changes and save the changes.
    to add an appointment simply pick one of the appointment views and click the add button. to add a customer
    click the customer view and then click the add button. to delete a customer or appointment select the desired
    object to delete and click the delete button. to logout click the logout button.the reports tab will give the user
    a view of three different reports. one showing the user the quantity of appointments of a selected type scheduled
    for a selected month. another showing the customer a schedule of appointments for a selected contact. and the
    last showing the user a list of appointments for a searched customer.

Description of report: the third report I decided to add IAW A.3.f is a tableview showing the user all scheduled
    appointments for a searched customer. giving the uer easy access to see a specific customers list of appointments.


MYSQL DRIVER: mysql-connector-j-8.0.32

