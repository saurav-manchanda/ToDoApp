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
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.todoapplication.userservice.model.User;
import com.bridgelabz.todoapplication.userservice.model.UserDTO;
import com.bridgelabz.todoapplication.userservice.service.IUserService;
import com.bridgelabz.todoapplication.utilservice.ResponseDTO;
import com.bridgelabz.todoapplication.utilservice.ToDoException;
import com.bridgelabz.todoapplication.utilservice.TokenGenerator;
import com.bridgelabz.todoapplication.utilservice.ObjectMapper.ObjectMapping;

/**
 * @author Saurav
 *         <p>
 *         Class UserControllerthat has info about what url access triggers it
 *         and what method to run when accessed.
 *         </p>
 */
@RestController
public class UserController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	String REQ_ID = "IN_User";
	String RES_ID = "OUT_User";
	@Autowired
	IUserService userService;
	@Autowired
	ObjectMapping objectMapping;
	@Autowired
	TokenGenerator token;

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
	public ResponseEntity<ResponseDTO> logIn(@RequestBody UserDTO userDto, HttpServletResponse resp)
			throws ToDoException {
		logger.info(REQ_ID);
		logger.info("User login");
		User user = objectMapping.map(userDto, User.class);
		String validToken = userService.tokengenerator(user);
		if (userService.validateUser(user, resp, validToken)) {
			logger.info(RES_ID);
			logger.info("User Successfully logger in");
			return new ResponseEntity(new ResponseDTO("Welcome to the Application.You are successfully logged in. Token generated is: "+validToken, 200), HttpStatus.OK);
		}
		return new ResponseEntity(new ResponseDTO("User Or Password Incorrect. Therefore no token generated. "+ user.getUserName(), 400) ,
				HttpStatus.CONFLICT);
	}

	/**
	 * @param user
	 * @return
	 * @throws ToDoException
	 * @throws MessagingException
	 *             <p>
	 *             This Method to register a new user into the application on
	 *             "/signup" url call"
	 *             </p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> signUp(@RequestBody UserDTO userDto) throws ToDoException, MessagingException {
		logger.info(REQ_ID);
		logger.info("User Registration");
		User user = objectMapping.map(userDto, User.class);
		userService.checkEmail(user);
		String validToken = userService.tokengenerator(user);
		userService.updateUser(user,validToken);
		logger.info(RES_ID);
		logger.info("User Successfully registered");
		return new ResponseEntity(new ResponseDTO("User successfully registered. The token generated is: "+validToken, 200), HttpStatus.OK);
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
	public ResponseEntity<ResponseDTO> forgotPassword(@RequestParam("email") String email)
			throws ToDoException, MessagingException {
		logger.info(REQ_ID);
		logger.info("Forgot Password Entered");
		User user = new User();
		user.setEmail(email);
		if (userService.isEmailPresent(user) == true) {
			String validToken = userService.tokengenerator(user);
			userService.sendMail(user, validToken);
			logger.info(RES_ID);
			logger.info("Email sent with new Password");
			return new ResponseEntity(new ResponseDTO("Email send with a link to set new password ", 200), HttpStatus.OK);
		}
		return new ResponseEntity(new ResponseDTO("Invalid user Name", 400), HttpStatus.CONFLICT);
	}

	/**
	 * @param HttpRequest
	 * @return response entity
	 *         <p>
	 *         This method is written to make account activated after successful
	 *         sign in
	 *         </p>
	 */
	@RequestMapping(value = "/activate/{token}", method = RequestMethod.GET)
	public ResponseEntity<String> activateaccount(HttpServletRequest request,@PathVariable String token) {
		logger.info(REQ_ID);
		logger.info("Activation started");
//		String token = request.getQueryString();
		if (userService.activate(token)) {
			String messege = "Account activated successfully";
			logger.info(RES_ID);
			logger.info("User Account activated.");
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/newpassword", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> changePassword(@RequestParam("password") String password,
			@RequestParam("newPassword") String newPassword, @RequestParam("Token") String token) throws ToDoException {
		logger.info(REQ_ID);
		logger.info("New Password Module entered");
		userService.resetPassword(token, password, newPassword);
		logger.info(RES_ID);
		logger.info("Password changed Successfully");
		return new ResponseEntity(new ResponseDTO("Password is changed successfully.", 200), HttpStatus.OK);
	}
}
