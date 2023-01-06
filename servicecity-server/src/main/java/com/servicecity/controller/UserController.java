package com.servicecity.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicecity.common.EmailUtil;
import com.servicecity.model.User;
import com.servicecity.service.GenerateOTPService;
import com.servicecity.service.UserService;
import com.servicecity.util.AppConstants;
import com.servicecity.util.ServiceControllerUtils;
import com.servicecity.util.ServiceResponse;

/**
 * @author Yedukondalu K
 *
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

	private static Logger logger = LogManager.getLogger(UserController.class);

	@Value("${serverURL}")
	private String serverURL;
	
	@Autowired
	private UserService userService;
	@Autowired
	private GenerateOTPService generateOTPService;
	@Autowired
	private EmailUtil mailUtil;	
	@Autowired
	private ServiceControllerUtils scutils;
	
	@PostMapping("/save")
	public ResponseEntity<?> create(@RequestBody User user) {
		ResponseEntity<?> resp= null;
		ServiceResponse restResponse=new ServiceResponse();
		logger.debug(">>accessed url is : "+serverURL+"/api/user/save");
		try {
			//String otp = generateOTPService.generateOTP();
			//System.out.println("-------- OTP  --------");
			//System.out.println("Generate OTP:"+otp);
			//user.setPassword(otp);
			//user.setOtpRequestTime(LocalDateTime.now());
			user.setOtpExpiryTime(user.getOtpRequestTime().plusMinutes(5));
			Map<String, String> userCredentials = new HashMap<String, String>();
			userCredentials.put("userName", user.getEmail());
			userCredentials.put("userName", user.getPassword());
			//userCredentials.put("password", otp);
			User dbObj=userService.create(user);
			if(dbObj!=null) {
				restResponse.addDataObject("User", dbObj);
				restResponse = scutils.prepareMobileResponseSuccessStatus(restResponse," User is saved");
				resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.OK);
				logger.debug(" User is saved: \n '"+dbObj+"' ");
				boolean flag=mailUtil.sendCreateUser(user.getEmail(), "Here's your One Time Password (OTP) - Expire in 5 minutes!", userCredentials);

				if(flag) {
					restResponse.addDataObject("Email", flag);
					restResponse = scutils.prepareMobileResponseSuccessStatus(restResponse,"Successfully Mail Sent");
					resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.OK);
					logger.debug("Successfully Mail Sent '"+flag+"' ");
				}else {
					restResponse = scutils.prepareMobileResponseInvalidData(restResponse, "Mail not send, Please Try Valid Again");
					resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.OK);
					logger.debug(" Mail is not send: \n '"+flag+"', please try again!!");
				}
			}else {
				restResponse = scutils.prepareMobileResponseInvalidData(restResponse, "User is not saved, Please Enter Valid Data");
				resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.NOT_ACCEPTABLE);
				logger.debug("  User is not saved: \n '"+dbObj+"', please try again!!");
			}	
		}catch (Exception e) {
			if(e.toString().contains("E11000")) {
				restResponse = scutils.prepareMobileResponseErrorStatus(restResponse, AppConstants.ERRORCODE, "Login Id used already");
				resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.NOT_ACCEPTABLE);
				logger.debug("User Exceptions are"+resp);
			}else {
				restResponse = scutils.prepareMobileResponseErrorStatus(restResponse, AppConstants.ERRORCODE, e.getMessage());
				resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.INTERNAL_SERVER_ERROR);
				logger.debug("User Exceptions are: "+resp);
			}
			e.printStackTrace();
		}
		return resp;	
	}


	@GetMapping("/allUsers")
	public ResponseEntity<?> getAllUsersInformation(){
		ResponseEntity<?> resp=null;
		ServiceResponse restResponse=new ServiceResponse();
		logger.debug(">>accessed url is : "+serverURL+"/api/user/allUsers");
		try {
			int[] arry = {1, 2, 3 ,2};
			//list.stream().distinct().collect(Collectors.toList());
			//list.toArray();
			List<int[]> integer = Arrays.asList(arry);
			Comparator cmp = Collections.reverseOrder();
			integer.stream().distinct().sorted(cmp).collect(Collectors.toList());
			integer.stream().distinct().sorted().collect(Collectors.toList());
			Collections.sort(integer, cmp);
			
			List<User> list=userService.findAll();
			if(list!=null && !list.isEmpty()) {
				restResponse.addDataObject("User", list);
				restResponse = scutils.prepareMobileResponseSuccessStatus(restResponse,"Successfully Fetched The User Data");
				resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.OK);
				logger.debug("Successfully Fetched The User Data: \n '"+list+"' ");
			}else {
				restResponse.addDataObject("User", list);
				restResponse = scutils.prepareMobileResponseSuccessStatus(restResponse,"empty User Data");
				resp= new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.OK);
				logger.debug("User Data Is Not Available: \n '"+resp+"' ");
			}
		}catch(Exception e) {
			restResponse = scutils.prepareMobileResponseErrorStatus(restResponse, AppConstants.ERRORCODE, e.getMessage());
			resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.INTERNAL_SERVER_ERROR);
			logger.debug("User Exceptions are: "+resp);
			e.printStackTrace();
		}
		return resp;
	}
	
	@DeleteMapping("/delete/id/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		ResponseEntity<?> resp= null;
		ServiceResponse restResponse=new ServiceResponse();
		logger.debug(">>accessed url is : "+serverURL+"/api/user/delete/id/"+id);
		try {
			User dbObj=userService.delete(id);
			if(dbObj!=null) {
				restResponse.addDataObject("User", dbObj);
				restResponse = scutils.prepareMobileResponseSuccessStatus(restResponse," User is deleted");
				resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.OK);
				logger.debug(" User is deleted: \n '"+dbObj+"' ");				
			}else {
				restResponse = scutils.prepareMobileResponseInvalidData(restResponse, "User is not deleted, Please Enter Valid Data");
				resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.NOT_ACCEPTABLE);
				logger.debug("  User is not deleted: \n '"+dbObj+"', please try again!!");
			}	
		}catch (Exception e) {
			if(e.toString().contains("E11000")) {
				restResponse = scutils.prepareMobileResponseErrorStatus(restResponse, AppConstants.ERRORCODE, "Duplicate Data");
				resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.NOT_ACCEPTABLE);
				logger.debug("User Exceptions are"+resp);
			}else {
				restResponse = scutils.prepareMobileResponseErrorStatus(restResponse, AppConstants.ERRORCODE, e.getMessage());
				resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.INTERNAL_SERVER_ERROR);
				logger.debug("User Exceptions are: "+resp);
			}
			e.printStackTrace();
		}
		return resp;	
	}

	@GetMapping("/id/{<id>}")
	public ResponseEntity<?> findUser(@PathVariable("<id>") String id) {
		ResponseEntity<?> resp= null;
		ServiceResponse restResponse=new ServiceResponse();
		logger.info("User findUser API Called !!");
		try {
			User dbObj=userService.findById(id);
			if(dbObj!=null) {
				restResponse.addDataObject("User", dbObj);
				restResponse = scutils.prepareMobileResponseSuccessStatus(restResponse," User is found ");
				resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.OK);
				logger.debug(" User is found: \n '"+dbObj+"' ");				
			}else {
				restResponse = scutils.prepareMobileResponseInvalidData(restResponse, "User is not found, Please Enter Valid Data");
				resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.NOT_ACCEPTABLE);
				logger.debug("  User is not found: \n '"+dbObj+"', please try again!!");
			}	
		}catch (Exception e) {
			if(e.toString().contains("E11000")) {
				restResponse = scutils.prepareMobileResponseErrorStatus(restResponse, AppConstants.ERRORCODE, "Duplicate Data");
				resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.NOT_ACCEPTABLE);
				logger.debug("User Exceptions are"+resp);
			}else {
				restResponse = scutils.prepareMobileResponseErrorStatus(restResponse, AppConstants.ERRORCODE, e.getMessage());
				resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.INTERNAL_SERVER_ERROR);
				logger.debug("User Exceptions are: "+resp);
			}
			e.printStackTrace();
		}
		return resp;	
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<?> findUserByEmail(@PathVariable("email") String email){
		ResponseEntity<?> resp=null;
		ServiceResponse restResponse=new ServiceResponse();
		logger.debug(">>accessed url is : "+serverURL+"/api/user/email/"+email);
		try {
			Optional<User> user = userService.findUserByEmail(email);
			
			if (user.isPresent()) { // Token found in DB
				restResponse.addDataObject("User", user);
				restResponse = scutils.prepareMobileResponseSuccessStatus(restResponse,"Successfully Fetched The User Details");
				resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.OK);
				logger.debug("Successfully Fetched The tenantId: \n '"+user+"' ");
			}else {
				restResponse.addDataObject("User", user);
				restResponse = scutils.prepareMobileResponseSuccessStatus(restResponse,"empty User");
				resp= new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.OK);
				logger.debug("tenantId is Not Available: \n '"+resp+"' ");
			}

		}catch (Exception e) {
			restResponse = scutils.prepareMobileResponseErrorStatus(restResponse, AppConstants.ERRORCODE, e.getMessage());
			resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.INTERNAL_SERVER_ERROR);
			logger.debug("tenantId Exceptions are: "+resp);
			e.printStackTrace();
		}
		return resp;
	}

	@GetMapping("/phoneNumber/{phoneNumber}")
	public ResponseEntity<?> findUserByPhoneNo(@PathVariable("phoneNumber") Long phoneNumber){
		ResponseEntity<?> resp=null;
		ServiceResponse restResponse=new ServiceResponse();
		logger.debug(">>accessed url is : "+serverURL+"/api/user/phoneNumber/"+phoneNumber);
		try {
			Optional<User> user = userService.findUserByPhoneNumber(phoneNumber);
			
			if (user.isPresent()) { // Token found in DB
				restResponse.addDataObject("User", user);
				restResponse = scutils.prepareMobileResponseSuccessStatus(restResponse,"Successfully Fetched The User Details");
				resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.OK);
				logger.debug("Successfully Fetched The tenantId: \n '"+user+"' ");
			}else {
				restResponse.addDataObject("User", user);
				restResponse = scutils.prepareMobileResponseSuccessStatus(restResponse,"empty User");
				resp= new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.OK);
				logger.debug("tenantId is Not Available: \n '"+resp+"' ");
			}

		}catch (Exception e) {
			restResponse = scutils.prepareMobileResponseErrorStatus(restResponse, AppConstants.ERRORCODE, e.getMessage());
			resp=new ResponseEntity<ServiceResponse>(restResponse,HttpStatus.INTERNAL_SERVER_ERROR);
			logger.debug("tenantId Exceptions are: "+resp);
			e.printStackTrace();
		}
		return resp;
	}

}
