package com.example.YESserver;

import com.example.YESserver.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.*;
import java.util.Optional;

@RestController // This means that this class is a Controller

// @RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)

public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return ("<h1>Welcome</h1>");
    }

    @GetMapping(path="/user")
    public String user(Principal principal) {
        return ("<h1>Welcome User " + principal.getName() + "</h1>");
    }

    @GetMapping(path="/admin")
    public String admin(Principal principal) {
        return ("<h1>Welcome Admin " + principal.getName() + "</h1>");
    }

    @GetMapping(path="/instructor")
    public String instructor(Principal principal) {
        return ("<h1>Welcome Instructor " + principal.getName() + "</h1>");
    }

    @GetMapping(path="/student")
    public String student(Principal principal) {
        return ("<h1>Welcome Student " + principal.getName() + "</h1>");
    }









    // The following mappings are for the catalog


    @GetMapping(path="/catalog")
    public String catalog() throws SQLException {
        // This returns a JSON or XML with the users
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();
        try {
            ResultSet rs = stmt.executeQuery("Select * from courses");

            return viewTable(rs, "Course Catalog");

        }
        catch (Exception e) {
            return "Error";
        }
    }

    @GetMapping(path="/catalog/{course_category}")
    public String catalog_with_category(@PathVariable("course_category") String category) throws SQLException {
        // This returns a JSON or XML with the users
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();

        try {
            ResultSet rs = stmt.executeQuery("Select * from courses where course_category = " + category);

            return viewTable(rs, "Course Catalog for " + category + " courses");

        }
        catch (Exception e) {
            return "Error";
        }
    }

    @GetMapping(path="/catalog/space_available")
    public String catalog_space_available() throws SQLException {
        // This returns a JSON or XML with the users
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();
        try {
            ResultSet rs = stmt.executeQuery("Select * from courses where not total_enrolled = class_capacity");

            return viewTable(rs, "Courses that have available spaces");

        }
        catch (Exception e) {
            return "Error";
        }
    }

    @GetMapping(path="/catalog/waitlist_available")
    public String catalog_waitlist_available() throws SQLException {
        // This returns a JSON or XML with the users
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();
        try {
            ResultSet rs = stmt.executeQuery("Select * from courses where total_enrolled = class_capacity " +
                    "and total_enrolled > 0 and not total_on_waitlist = waitlist_capacity");

            return viewTable(rs, "Courses that have available waitlist spaces");

        }
        catch (Exception e) {
            return "Error";
        }
    }

    @GetMapping(path="/catalog/closed_courses")
    public String catalog_closed_courses() throws SQLException {
        // This returns a JSON or XML with the users
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();
        try {
            ResultSet rs = stmt.executeQuery("Select * from courses where total_on_waitlist = waitlist_capacity " +
                    "and not total_on_waitlist = 0");

            return viewTable(rs, "Courses that are closed");

        }
        catch (Exception e) {
            return "Error";
        }
    }

    public String viewTable(ResultSet rs, String title) throws SQLException {
        StringBuilder result = new StringBuilder();

        result.append("<table border=\"1\" \nalign=\"left\"> \n<caption>").append(title).append("</caption>");
        ResultSetMetaData rsmd = rs.getMetaData();

        int columnsNumber = rsmd.getColumnCount();

        result.append("<tr>");
        for (int i = 1; i <= columnsNumber; i++) {
            result.append("<th>").append(rsmd.getColumnName(i)).append("</th>");
        }
        result.append("</tr>");


        while (rs.next()) {
            result.append("<tr>");
            for (int i = 1; i <= columnsNumber; i++) {
                result.append("<td>").append(rs.getString(i)).append("</td>");
            }
            result.append("</tr>");
        }

        result.append("</table>");

        return result.toString();
    }










    // The following mappings are for the schedule


    @GetMapping(path="/schedule")
    public String schedule(Principal principal) throws SQLException {
        String user = principal.getName();

        // This returns a JSON or XML with the users
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();

        try {
            ResultSet rs = stmt.executeQuery("Select schedules.course_id, schedules.course_status from courses " +
                    "inner join schedules where schedules.course_status in('enrolled', 'waitlisted', 'instructing') " +
                    "and schedules.course_id = courses.course_id and schedules.user_id = \"" + user + "\"");

            return viewTable(rs, "Your Enrolled or Waitlisted Courses");

        }
        catch (Exception e) {
            return "Error";
        }
    }

    @GetMapping(path="/cart")
    public String cart(Principal principal) throws SQLException {
        String user = principal.getName();

        // This returns a JSON or XML with the users
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();

        try {
            ResultSet rs = stmt.executeQuery("Select schedules.course_id, schedules.course_status from courses " +
                    "inner join schedules where schedules.course_status = 'cart' " +
                    "and schedules.course_id = courses.course_id and schedules.user_id = \"" + user + "\"");

            return viewTable(rs, "Your Courses in Cart");

        }
        catch (Exception e) {
            return "Error";
        }
    }

    @GetMapping(path="/watching")
    public String watching(Principal principal) throws SQLException {
        String user = principal.getName();

        // This returns a JSON or XML with the users
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();

        try {
            ResultSet rs = stmt.executeQuery("Select schedules.course_id, schedules.course_status from courses " +
                    "inner join schedules where schedules.course_status = 'watching' " +
                    "and schedules.course_id = courses.course_id and schedules.user_id = \"" + user + "\"");

            return viewTable(rs, "Courses You are Watching");

        }
        catch (Exception e) {
            return "Error";
        }
    }













    // The following mappings are for the admin

    // example: http://localhost:8080/admin/add_course?course_id="MATH2500"&course_hour=3&course_category="Math"&description="Multivariable Calculus and Linear Algebra"&class_capacity=40&waitlist_capacity=12
    @PostMapping(path="/admin/add_course")
    public String add_course(@RequestParam("course_id") String course_id,
                             @RequestParam("course_hour") int course_hour,
                             @RequestParam("course_category") String course_category,
                             @RequestParam("description") String description,
                             @RequestParam("class_capacity") int class_capacity,
                             @RequestParam("waitlist_capacity") int waitlist_capacity) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();



        try {
            stmt.executeUpdate("INSERT INTO courses VALUES (" + course_id + ", " + course_hour + ", " +
                    course_category + ", " + description + ", 0, " + class_capacity + ", 0, " + waitlist_capacity + ")");

            return "Successfully added the course";

        }
        catch (Exception e) {
            return "Error";
        }
    }

    // example: http://localhost:8080/admin/remove_course?course_id="MATH2300"
    @PostMapping(path="/admin/remove_course")
    public String remove_course(@RequestParam("course_id") String course_id) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();

        try {
            stmt.executeUpdate("DELETE FROM courses WHERE course_id = " + course_id);

            return "Successfully added the course";

        }
        catch (Exception e) {
            return "Error";
        }
    }

    // example: http://localhost:8080/admin/display_user
    @GetMapping(path="/admin/display_user")
    public String display_user() throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();

        try {
            ResultSet rs = stmt.executeQuery("Select user_id, name, user_role, email from users limit 50");

            return viewTable(rs, "All users (up to first 50)");

        }
        catch (Exception e) {
            return "Error";
        }
    }

    // example: http://localhost:8080/admin/display_user/{user_id}
    @GetMapping(path="/admin/display_user/{user_id}")
    public String display_user(@PathVariable("user_id") String user_id) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();

        try {
            ResultSet rs = stmt.executeQuery("Select user_id, name, user_role, email from users where user_id = \"" + user_id + "\"");

            return viewTable(rs, user_id + " Info");

        }
        catch (Exception e) {
            return "Error";
        }
    }

    // example: http://localhost:8080/admin/add_user?username=Marina_Wang_01&password=marina1&role="student"&name="Marina_Wang"&email="marina.wang@vanderbilt.edu"
    @PostMapping(path="/admin/add_user") // Map ONLY POST Requests
    public String addNewUser (@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("role") String role,
                              @RequestParam("name") String name,
                              @RequestParam("email") String email) throws SQLException {

        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();

        try {
            User n = new User();
            n.setUserName(username);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            n.setPassword(passwordEncoder.encode(password));
            n.setActive(true);
            if (role.equals("\"student\"") || role.equals("\"instructor\"")) {
                n.setRoles("ROLE_USER");
            }
            else if (role.equals("\"admin\"")) {
                n.setRoles("ROLE_ADMIN");
            }
            else {
                return "Please enter a correct role type. It should be one of admin, student, or instructor. You entered " + role;
            }
            userRepository.save(n);


            stmt.executeUpdate("INSERT INTO users VALUES (\"" + username + "\", " + name + ", \"" +
                    password + "\", " + role + ", " + email + ")");

            return "Successfully added the user";

        }
        catch (Exception e) {
            return "Error";
        }
    }












    // The following mappings are for the instructor


    // example: http://localhost:8080/instructor/add_student_to_course?user_id="Acar_Ary_01"&course_id="MATH2300"
    @PostMapping(path="/instructor/add_student_to_course")
    public String add_student_to_course(@RequestParam("user_id") String user_id,
                                        @RequestParam("course_id") String course_id) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();

        try {

            stmt.executeUpdate("DELETE FROM schedules WHERE user_id = " + user_id + " and course_id = " + course_id);

            stmt.executeUpdate("INSERT INTO schedules VALUES (" + user_id + ", " + course_id + ", 'enrolled')");

            return "Successfully added the course";

        }
        catch (Exception e) {
            return "Error";
        }
    }

    // example: http://localhost:8080/instructor/remove_student_from_course?user_id="Charlie_Xu_01"&course_id="MATH2810"
    @PostMapping(path="/instructor/remove_student_from_course")
    public String remove_student_from_course(@RequestParam("user_id") String user_id,
                                             @RequestParam("course_id") String course_id) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();

        try {

            stmt.executeUpdate("DELETE FROM schedules WHERE user_id = " + user_id + " and course_id = " + course_id + " and course_status = \"enrolled\"");

            return "Successfully removed from the course";

        }
        catch (Exception e) {
            return "Error";
        }
    }












    // The following mappings are for the student

    // example: http://localhost:8080/student/enroll_course?user_id="Frank_Tian_01"&course_id="MATH2810"&course_status="enrolled"
    @PostMapping(path="/student/enroll_course")
    public String add_course(@RequestParam("user_id") String user_id,
                             @RequestParam("course_id") String course_id,
                             @RequestParam("course_status") String course_status) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();

        try {
            stmt.executeUpdate("INSERT INTO schedules VALUES (" + user_id + ", " + course_id + ", " + course_status + ")");

            return "Successfully added the course";

        }
        catch (Exception e) {
            return "Error";
        }
    }

















    @GetMapping(path="/all")
    public Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

































    // Vulnerabilities API


    // SQL Injection Vulnerability
    //
    // When type in
    // "http://localhost:8080/sql_injection_test?user_id="Charlie_Xu_01";+drop+table+if+exists+users"
    // in the browser, the user table will be maliciously deleted

    // Solutions:
    // 1.
    // Remove "?allowMultiQueries=true" from the connection so that only one query can be executed at a time
    // 2.
    // Use executeQuery instead of execute so that no updates can be made to the tables

    @PostMapping("/sql_injection_test")
    public String SQLInjectionTest(@RequestParam("user_id") String user_id) throws SQLException {
//        // Incorrect version
//        Connection con = DriverManager.getConnection(
//                "jdbc:mysql://localhost:3306/yes_database?allowMultiQueries=true","root","");

        // Correct version
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");

        Statement stmt = con.createStatement();
        String insertEmp1 = "SELECT name FROM users WHERE user_id = " + user_id;
        try {
            stmt.execute(insertEmp1);
            return ("Success");
        }
        catch (Exception e) {
            return null;
        }
    }

    // XSS Vulnerability
    @GetMapping("/xss_test")
    public String XSSTest(@RequestParam("name") String name) {
        // Allows for javascript code to be entered as a value for the url key "value"
        // leading to security vulnerabilities
        return ("<p>Hello. </p>" + "Sincerely, " + name);
    }

    // CSRF Vulnerability
    @PostMapping("/admin/get_id_by_name")
    public String CSRFTest(@RequestParam("userName") String userName) throws SQLException {

        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/yes_database","root","");
        Statement stmt = con.createStatement();
        String query = "SELECT * FROM user WHERE username = '" + userName + "'";

        try {
            stmt.execute(query);
            return ("Success");
        }
        catch (Exception e) {
            return null;
        }
    }

    // Password Encoding Vulnerability
    @GetMapping("/password_encoder_test")
    public String PasswordEncoderTest() {

        User n = new User();
        n.setUserName("Marina_Wang_01");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        n.setPassword(passwordEncoder.encode("marina1"));
        n.setActive(true);
        n.setRoles("ROLE_USER");
        userRepository.save(n);

        Optional<User> myUser = userRepository.findByUserName("Marina_Wang_01");
        if (myUser.isPresent()) {
            if (myUser.get().getPassword().equals(passwordEncoder.encode("marina1"))) {
                return "Success";
            }
            else {
                return "Failure";
            }
        }
        else {
            return "User not Present";
        }
    }
}
