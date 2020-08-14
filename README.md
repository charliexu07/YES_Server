# YES_Server
A course registration server based on spring boot

## Instruction
* Clone/download this project to your local machine
* Open project with Intellij
* Setup and connect to your Mamp/Wamp server
* Run the sql file src/main/resources/schema.sql and src/main/resources/data.sql to initialize database (those two files will be automatically executed by IntelliJ)
* Configure your connection to the database server in src/main/resources/application.properties. In src/main/java/com/example/YESserver/HomeResource.java and src/test/java/com/example/YESserver/YesServerApplicationTests.java the localhost port number should be changed to your selected port.
* To run the test, go to src/test/java/com/example/YESserver/YesServerApplicationTests.java and run the test file
* To run the server: run src/main/java/com/example/YESserver/YesServerApplication.java
* Open your browser and enter request: http://localhost:8080, this address will allow you to access different resources in the api


## API design
The API is a web server that simulates the course registration system of Vanderbilt University (The YES system). The user can log into the system as either an admin, an instructor, 
or a user. A detailed description of all functionalities is attached below



### Login and Credential Information: 



### /
* Description: homepage
* Accept Get Request
* Roles permitted: permit all
* Vulnerabilities: none

### /login
* Description: allow user to login accounts, redirect to /
* Accept Get Request
* Roles permitted: permit all
* Vulnerabilities: Brute Force Attack on login information

### /user
* Description: user’s homepage
* Accept Get Request
* Roles permitted: admin/user
* Vulnerabilities: none

### /admin
* Description: administrator’s homepage
* Accept Get Request
* Roles permitted: admin
* Vulnerabilities: none

### /instructor
* Description: instructor’s homepage
* Accept Get Request
* Roles permitted: admin/user
* Vulnerabilities: none

### /student
* Description: student’s homepage
* Accept Get Request
* Roles permitted: admin/user
* Vulnerabilities: none



### Catalog: 



### /catalog
* Description: Display available courses (first 50)
* Accept Get Request
* Roles permitted: admin/user
* Vulnerabilities: none

### /catalog/{course_category}
* Description: Display available courses in given department
* Accept Get Request
* Roles permitted: admin/user
* Vulnerabilities: SQL Injection on given course_category

### /catalog/space_available
* Description: Display courses that have spaces available
* Accept Get Request
* Roles permitted: admin/user
* Vulnerabilities: none

### /catalog/waitlist_available
* Description: Display courses that have waitlist spaces available
* Accept Get Request
* Roles permitted: admin/user
* Vulnerabilities: none

### /catalog/closed_courses
* Description: Display courses that are closed
* Accept Get Request
* Roles permitted: admin/user
* Vulnerabilities: none



### Schedule: 



### /catalog/schedule
* Description: Display the schedule of the currently logged in user
* Accept Get Request
* Roles permitted: admin/user
* Vulnerabilities: none

### /catalog/cart
* Description: Display the cart of the currently logged in user
* Accept Get Request
* Roles permitted: admin/user
* Vulnerabilities: none

### /catalog/watching
* Description: Display the courses that are closed but are being watched by the currently logged in student
* Accept Get Request
* Roles permitted: admin/user
* Vulnerabilities: none

### /catalog/cart
* Description: Display the cart of the currently logged in user
* Accept Get Request
* Roles permitted: admin/user
* Vulnerabilities: none



### Admin: 



### /admin/add_course
* Description: Add a user to a course with give status
* Accept Post Request
* Roles permitted: admin
* Vulnerabilities: none

### /admin/remove_course
* Description: Remove a user from a course
* Accept Post Request
* Roles permitted: admin
* Vulnerabilities: none

### /admin/display_user
* Description: Display information of all users (first 50)
* Accept Get Request
* Roles permitted: admin
* Vulnerabilities: none

### /admin/display_user/{user_id}
* Description: Display information of the user with given user_id
* Accept Get Request
* Roles permitted: admin
* Vulnerabilities: SQL Injection

### /admin/add_user
* Description: Add information of a new user to the database
* Accept Post Request
* Roles permitted: admin
* Vulnerabilities: none



### Instructor: 



### /instructor/add_student_to_course
* Description: Add a student to a course that the currently logged in instructor is teaching
* Accept Post Request
* Roles permitted: admin/user
* Vulnerabilities: none

### /instructor/remove_student_from_course
* Description: Remove a student from a course that the currently logged in instructor is teaching
* Accept Post Request
* Roles permitted: admin/user
* Vulnerabilities: none



### Student: 



### /student/enroll_course
* Description: Let the currently logged in student enroll into a class with given status (enrolled, waitlist, watching)
* Accept Post Request
* Roles permitted: admin/user
* Vulnerabilities: none





 
## Spring security vulnerability design: 

### Unauthorized access of system by anyone
* Description: /admin page can be accessed by an attacker without being authenticated
* Demo: see test fucnction
* Test function: testUser(), testWrongUser()
* Solution: Refine authority assigned to each role
* Location: SecurityConfiguration.java, Line 70-78
* Grading rubrics: completely ignoring the vulnerbility -10; code partialy implemented but fails the test -5; correct -0;

### XSS attack
* Description: An attacker inject a malicious piece of script into the url
* Demo: see test fucnction
* Test function: testXSS()
* Solution: Ensure the script can be caught
* Location: MainController.java, Line 582-587
* Grading rubrics: completely ignoring the vulnerbility -10; code partialy implemented but fails the test -5; correct -0;

### CSRF attack
* Description: An attacker cause user to carry out a query on product information unintentionally.
* Demo: see test fucnction
* Test function: testCSRF()
* Solution: Enable csrf protection provided by spring security
* Location: MainController.java, Line 590-605
* Grading rubrics: completely ignoring the vulnerbility -10; code partialy implemented but fails the test -5; correct -0;

### Sql injection attack
* Description: An attacker can enter additional sql query element along with a product id. This will expose data in the database.
* Demo: see test fucnction
* Test function: testSQLInjection1() and testSQLInjection2()
* Solution: Ensure that only one query can be executed at a time when connecting tp database
* Location: MainController.java, Line 561-579
* Grading rubrics: completely ignoring the vulnerbility -10; code partialy implemented but fails the test -5; correct -0;

### Password Encoder Vulnerability
* Description: If the password of the user is stored in plain text, the attacker might be able to hack into the system and obtain the password
* Demo: see test function
* Test function: testPasswordEncoder()
* Solution: Use BCryptPasswordEncoder to encode the password before storing user information into the database. Also, during the login process, the entered password will also be encripted to match the password stored in database.
* Location: MainController.java, Line 610-632
* Grading rubrics: completely ignoring the vulnerbility -10; code partialy implemented but fails the test -5; correct -0;
