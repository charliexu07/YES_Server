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

### Brute Force Attack on login information
* Description: On /login page, an attacker can try as many username-password pairs as it wants
* Demo: see test fucnction
* Test function: testBFA()
* Location: N/A 
* Solution: Implement classes that keep track of login failure information using spring security framework. See https://www.baeldung.com/spring-security-block-brute-force-authentication-attempts
* Grading rubrics: completely ignoring the vulnerbility -10; code partialy implemented but fails the test -5; correct -0;

### Improper api design (exposing admin authorities to users)
* Description: On /index page, a user is allowed to see the link for /admin page whhich is a page only authorized to ADMIN
* Demo: 
* Test function: none
* Location: HomeResource.java, Line: 19-21
* Solution: After login, direct users with different roles to corresponding index pages that only list the corresponding authorities of that role.
* Grading rubrics: completely ignoring the vulnerbility -10; code partialy implemented but fails the test -5; correct -0; implement a working listener class and pass the test +5;

### Invalid inputs that cause sql server error
* Description: On /get_product_info_by_id page, an attacker can enter anything rather than a product id which is an integer. This will cause server error.
* Demo: see test fucnction
* Test function: testInvalidSqlInput()
* Location: HomeResource.java, Line: 48-50
* Solution: Implement code that sanitize user input, see the source code comment for sample solution.
* Grading rubrics: completely ignoring the vulnerbility -10; code partialy implemented but fails the test -5; correct -0;

### Unauthorized access of system by anyone
* Description: /admin page can be accessed by an attacker without being authenticated
* Demo: see test fucnction
* Test function: testUnauthenticatedAccess()
* Location: SecurityConfiguration.java, Line: 36 
* Solution: Refine authority assigned to each role, see the source code comment for sample solution.
* Grading rubrics: completely ignoring the vulnerbility -10; code partialy implemented but fails the test -5; correct -0;

### CSRF attack
* Description: On /get_product_info_by_id page, an attacker cause user to carry out a query on product information unintentionally.
* Demo: see test fucnction
* Test function: testCSRF()
* Location: SecurityConfiguration.java, Line: 49
* Solution: Enable csrf protection provided by spring security, see the source code comment for sample solution.
* Grading rubrics: completely ignoring the vulnerbility -10; code partialy implemented but fails the test -5; correct -0;

### Expose of sql source code
* Description: In the HomeSource.java file, the sql quey used by /get_product_info_by_id is exposed.
* Demo:
* Test function: none
* Location: HomeResource.java, Line: 69-71
* Solution: Implement mysql procedure in the database management system and call procedure in source code instead, see the source code comment for sample solution.
* Grading rubrics: completely ignoring the vulnerbility -10; code partialy implemented but fails the test -5; correct -0;

### Sql injection attack
* Description: On /get_product_info_by_id page, an attacker can enter additional sql query element along with a product id. This will expose data in the database.
* Demo: see test fucnction
* Test function: testSQLInjection()
* Location: HomeResource.java, Line: 81
* Solution: Implement code that sanitize user input, see the source code comment for sample solution.
* Grading rubrics: completely ignoring the vulnerbility -10; code partialy implemented but fails the test -5; correct -0;

