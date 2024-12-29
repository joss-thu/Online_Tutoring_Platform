package de.thu.thutorium.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * This is a demo controller, strictly for demonstration purposes.
 * TODO: DELETE AFTER EXPERIMENTING WITH THIS CONTROLLER
 * To try out 'register action':
 * 1. Go to postman
 * 2. Register a new user as following:
 *  a. Method: POST
 *  b. URL: http://localhost:8080/auth/register
 *  c. Body->raw->
 *  {
 *     "firstName":"Homer",
 *     "lastName":"Simpson",
 *     "email": "h@homie.com",
 *     "password":"123456",
 *     "role": "TUTOR"
 * }
 * NOTE: Any values can be used for the fields. The role should be either of STUDENT; TUTOR; ADMIN or VERIFIER.
 * 3. SEND and get the jwt token
 * 4. Verify the token as follows:
 *  a. Go to https://jwt.io/#decoded-jwt and paste the token.
 *  b. Verify that the token has at least a role, the id, and other parameters like token expiration time.
 * //-----------------------------------------------------------------
 * To try out 'login action':
 * 1. Go to POSTMAN and repeat the steps mentioned above with the following changes:
 *  a. URL: http://localhost:8080/auth/login
 *  b. Body-> {
 *     "email": "h@homie.com",
 *     "password":"123456"
 * }
 * NOTE: MAke sure you use the same email and password values which you used to register.
 * c. Copy the token
 * d. It should be noted that each role (Student, Tutor etc.) can only access url links which start with their roles- e.g
 * /tutor/xyz, /student/xyz etc.
 * e. To verify the above point:
 *  a. Try to access http://localhost:8080/tutor/message, if you are already loged in with role tutor
 *  b. 403 forbidden error is shown.
 *  c. paste the copied jwt token from the above step to Authorization -> Bearer Token -> token
 *  d. Now the link should give a response
 *   * //-----------------------------------------------------------------
 *  * To try out cumulative roles:
 *  1. A user can have all roles assigned to him; try out the register action, but with another role.
 *  2. Use the login; get the jwt token; try to access the multiple urls detailed in this class.
 *  3. The urls for each links will be accessible if you have the right role. (Do not forget to copy and paste the right
 *  auth token as discussed in the steps above.)
 *
 */
@RestController
@RequestMapping("")
public class DemoController {

    @PostMapping("/tutor/message")
    public ResponseEntity<String> tutorMessage(@RequestBody final String msg) {
        return ResponseEntity.ok("Hello from tutor " + msg);
    }

    @PostMapping("/admin/message")
    public ResponseEntity<String> adminMessage(@RequestBody final String msg) {
        return ResponseEntity.ok("Hello from admin " + msg);
    }

    @PostMapping("/student/message")
    public ResponseEntity<String> studentMessage(@RequestBody final String msg) {
        return ResponseEntity.ok("Hello from student " + msg);
    }

    @PostMapping("/verifier/message")
    public ResponseEntity<String> verifierMessage(@RequestBody final String msg) {
        return ResponseEntity.ok("Hello from verifier " + msg);
    }

}
