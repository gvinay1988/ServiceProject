package com.servicecity.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * @author Yedukondalu K
 *
 */

public class User implements UserDetails, Serializable{
	
	//Default Generate primary key in mongoDB
    private String _id;

	//User Details
	private Long phoneNumber;
	private String userId;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private List<String> role;
	private String tenantId;
	private Address address;
	private String description;
	private String accessType;
	private boolean status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createdDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime otpRequestTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime otpExpiryTime;
	private String email;
	private String resetToken;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime resetTokenExpiryTime;
	
	//@Transient
    private Set<GrantedAuthority> grantedAuthorities;
	//@Transient
    public boolean isAccountNonExpired;
	//@Transient
	public boolean isAccountNonLocked;
	//@Transient
	public boolean isCredentialsNonExpired;
	//@Transient
	public boolean isEnabled;
     
    public User() {

    }
    
	/*public boolean isOTPRequired() {
		if(this.getPassword() == null) {
			return false;
		}
		LocalDateTime currentTimeInMillis =  LocalDateTime.now();
		LocalDateTime otpRequestedTimeInMillis = this.getOtpRequestTime().plusMinutes(5);
		//long otpRequestedTimeInMillis = Date.parse(this.getOtpRequestTime().toString()); //Date.UTC(this.getOtpRequestTime().getYear(), this.getOtpRequestTime().getMonthValue(), this.getOtpRequestTime().getDayOfMonth(), this.getOtpRequestTime().getHour(), this.getOtpRequestTime().getMinute(), this.getOtpRequestTime().getSecond());
		if(currentTimeInMillis.isBefore(otpRequestedTimeInMillis)) {
			return false;
		}
		return true;
	}*/
    
    public User(String userId,
			String password,
			Set<GrantedAuthority> grantedAuthorities,
			String accessType,
			Address address,
			String description,
			String userName,
			String tenantId,
			String email,
			Long phoneNumber,
			List<String> roles,
			boolean status,
			boolean isAccountNonExpired,
			boolean isAccountNonLocked,
			boolean isCredentialsNonExpired,
			boolean isEnabled) {
		this.userId = userId;
		this.password = password;
		this.grantedAuthorities = grantedAuthorities;
		this.accessType = accessType;
		this.address = address;
		this.description = description;
		this.userName = userName;
		this.tenantId = tenantId;
		this.email = email;
		this.phoneNumber =  phoneNumber;
		this.role = roles;
		this.status = status;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isEnabled = isEnabled;
	}

	public User(String userId, String password, List<String> role) {
		super();
		this.userId = userId;
		this.password = password;
		this.role = role;
	}
    

	
	/**
	 * @return the _id
	 */
	public String get_id() {
		return _id;
	}

	/**
	 * @param _id the _id to set
	 */
	public void set_id(String _id) {
		this._id = _id;
	}

	/**
	 * @return the phoneNumber
	 */
	public Long getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the role
	 */
	public List<String> getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(List<String> role) {
		this.role = role;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenatId the tenatId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the accessType
	 */
	public String getAccessType() {
		return accessType;
	}

	/**
	 * @param accessType the accessType to set
	 */
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	/**
	 * @return the status
	 */
	public boolean getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}


	/**
	 * @return the otpRequestTime
	 */
	public LocalDateTime getOtpRequestTime() {
		return otpRequestTime;
	}

	/**
	 * @param otpRequierdTime the otpRequierdTime to set
	 */
	public void setOtpRequestTime(LocalDateTime otpRequestTime) {
		this.otpRequestTime = otpRequestTime;
	}

	
	public LocalDateTime getOtpExpiryTime() {
		return otpExpiryTime;
	}

	public void setOtpExpiryTime(LocalDateTime otpExpiryTime) {
		this.otpExpiryTime = otpExpiryTime;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the resetToken
	 */
	public String getResetToken() {
		return resetToken;
	}

	/**
	 * @param resetToken the resetToken to set
	 */
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	/**
	 * @return the resetTokenExpiryTime
	 */
	public LocalDateTime getResetTokenExpiryTime() {
		return resetTokenExpiryTime;
	}

	/**
	 * @param resetTokenExpiryTime the resetTokenExpiryTime to set
	 */
	public void setResetTokenExpiryTime(LocalDateTime resetTokenExpiryTime) {
		this.resetTokenExpiryTime = resetTokenExpiryTime;
	}
	
	/**
	 * @return the grantedAuthorities
	 */
	public Set<GrantedAuthority> getGrantedAuthorities() {
		return grantedAuthorities;
	}

	/**
	 * @param grantedAuthorities the grantedAuthorities to set
	 */
	public void setGrantedAuthorities(Set<GrantedAuthority> grantedAuthorities) {
		this.grantedAuthorities = grantedAuthorities;
	}

	/**
	 * @return the isAccountNonExpired
	 */
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	/**
	 * @param isAccountNonExpired the isAccountNonExpired to set
	 */
	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	/**
	 * @return the isAccountNonLocked
	 */
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	/**
	 * @param isAccountNonLocked the isAccountNonLocked to set
	 */
	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	/**
	 * @return the isCredentialsNonExpired
	 */
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	/**
	 * @param isCredentialsNonExpired the isCredentialsNonExpired to set
	 */
	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	
	//Address Details
	public static class Address{
		private String lineOne;
		private String lineTwo;
		private String city;
		private String state;
		private String country;
		private Integer pincode;
		/**
		 * @return the lineOne
		 */
		public String getLineOne() {
			return lineOne;
		}
		/**
		 * @param lineOne the lineOne to set
		 */
		public void setLineOne(String lineOne) {
			this.lineOne = lineOne;
		}
		/**
		 * @return the lineTwo
		 */
		public String getLineTwo() {
			return lineTwo;
		}
		/**
		 * @param lineTwo the lineTwo to set
		 */
		public void setLineTwo(String lineTwo) {
			this.lineTwo = lineTwo;
		}
		/**
		 * @return the city
		 */
		public String getCity() {
			return city;
		}
		/**
		 * @param city the city to set
		 */
		public void setCity(String city) {
			this.city = city;
		}
		/**
		 * @return the state
		 */
		public String getState() {
			return state;
		}
		/**
		 * @param state the state to set
		 */
		public void setState(String state) {
			this.state = state;
		}
		
		/**
		 * @return the country
		 */
		public String getCountry() {
			return country;
		}
		/**
		 * @param country the country to set
		 */
		public void setCountry(String country) {
			this.country = country;
		}
		/**
		 * @return the pincode
		 */
		public Integer getPincode() {
			return pincode;
		}
		/**
		 * @param pincode the pincode to set
		 */
		public void setPincode(Integer pincode) {
			this.pincode = pincode;
		}
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getUsername() {
		return email;
	}


}
