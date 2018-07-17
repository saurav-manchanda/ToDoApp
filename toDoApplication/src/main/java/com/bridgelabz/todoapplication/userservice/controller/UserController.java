/********************************************************************************* *
 * Purpose: To do Login Registration with the help of MONGODB repository. 
 * This is the controller class
 * 
 * @author Saurav Manchanda
 * @version 1.0
 * @since 11/07/2018
 *********************************************************************************/
package com.bridgelabz.todoapplication.userservice.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.todoapplication.userservice.model.User;
import com.bridgelabz.todoapplication.userservice.service.IUserService;
import com.bridgelabz.todoapplication.utilservice.ToDoException;
import com.bridgelabz.todoapplication.utilservice.TokenGenerator;

/**
 * @author Saurav
 *         <p>
 *         Class UserControllerthat has info about what url access triggers it
 *         and what method to run when accessed.
 *         </p>
 */
@RestController
public class UserController {
	@Autowired
	IUserService userService;

	TokenGenerator token = new TokenGenerator();

	/**
	 * @param user
	 * @return ResponseEntity
	 * @throws ToDoException
	 *             <p>
	 *             Method to login into the application on "/login" url call
	 *             </p>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<User> logIn(@RequestParam("email") String email, @RequestParam("password") String password)
			throws ToDoException {
		User user = userService.getUserForLogin(email, password);
		if (userService.validateUser(user) == true) {
			return new ResponseEntity("Welcome to the Application.You are successfully logged in ", HttpStatus.OK);
		}
		return new ResponseEntity("User Or Password Incorrect. Therefore no token generated. " + user.getUserName(),
				HttpStatus.CONFLICT);
	}

	/**
	 * @param user
	 * @return
	 * @throws MessagingException
	 *             <p>
	 *             This Method to register a new user into the application on
	 *             "/signup" url call"
	 *             </p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<User> signUp(@RequestBody User user) throws ToDoException, MessagingException {
		userService.checkEmail(user);
		userService.updateUser(user);
		return new ResponseEntity("User successfully registered " + user.getUserName(), HttpStatus.OK);
	}

	/**
	 * @param user
	 * @return
	 * @throws ToDoException
	 * @throws MessagingException
	 *             <p>
	 *             This Method is to send the existing password to the user by
	 *             forgot password
	 *             </p>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public ResponseEntity<User> forgotPassword(@RequestParam("email") String email)
			throws ToDoException, MessagingException {
		User user = new User();
		user.setEmail(email);
		if (userService.isEmailPresent(user) == true) {
			String validToken = userService.tokengenerator(user);
			userService.sendMail(user, validToken);
			return new ResponseEntity("Email send with a link to set new password ", HttpStatus.OK);
		}
		return new ResponseEntity("Invalid user Name", HttpStatus.CONFLICT);
	}

	/**
	 * @param HttpRequest
	 * @return response entity
	 *         <p>
	 *         This method is written to make account activated after successful
	 *         sign in
	 *         </p>
	 */
	@RequestMapping(value = "/activate", method = RequestMethod.GET)
	public ResponseEntity<String> activateaccount(HttpServletRequest request) {
		String token = request.getQueryString();

		if (userService.activate(token)) {
			String messege = "Account activated successfully";
			return new ResponseEntity<String>(messege, HttpStatus.OK);
		} else {
			String msg = "Account not activated";
			return new ResponseEntity<String>(msg, HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * @param changePassword
	 * @param req
	 * @return
	 *         <p>
	 *         This method is for changing the password.
	 *         </p>
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/newpassword", method = RequestMethod.POST)
	public ResponseEntity<String> changePassword(@RequestParam("password") String password,
			@RequestParam("newPassword") String newPassword,@RequestParam("Token") String token) throws ToDoException {
		userService.resetPassword(token, password, newPassword);
		return new ResponseEntity<String>("Password is changed successfully.", HttpStatus.OK);
	}
}
