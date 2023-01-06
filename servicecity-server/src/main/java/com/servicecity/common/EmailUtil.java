package com.servicecity.common;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


/**
 * 
 * @author Yedukondalu K       
 *
 */

@Component
public class EmailUtil {

	private static Logger logger = LogManager.getLogger(EmailUtil.class);

	/*@Value("${spring.mail.properties.mail.smtp.from}")
	private String from;
	@Value("${spring.mail.host}")
	String host;
	@Value("${spring.mail.port}")
	String port;
	@Value("${spring.mail.username}")
	String username;
	@Value("${spring.mail.password}")
	String password;*/

	@Autowired
	private JavaMailSender mailSender;

	//Folder folder;
	//Store store;

	//Use for mail sending
	/*public boolean send(
			String to, 
			String[] cc,
			String subject, 
			String text,
			MultipartFile file
			)
	{
		boolean flag=false;
		try {

			Properties props =new Properties();
			props.put("mail.host",host);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", "true");
			props.put("mail.store.protocol", "imaps");

			// Mail session authentified
			Session session = Session.getInstance(props);

			MimeMessage message=mailSender.createMimeMessage();
			message.setRecipients(Message.RecipientType.TO, to);

			MimeMessageHelper helper=new MimeMessageHelper(message, file!=null?true:false);

			helper.setFrom(from);
			helper.setTo(to); 
			if(cc!=null?true:false)
				helper.setCc(cc);
			helper.setSubject(subject);  
			helper.setText(text);
			if(file!=null)
				helper.addAttachment(file.getOriginalFilename(), file);
			mailSender.send(message);

			// Copy message to "Sent Items" folder as read
			store = session.getStore();
			store.connect(host, username, password);

			folder = store.getFolder("Inbox.Sent");
			if (!folder.exists()) {
				folder.create(Folder.HOLDS_MESSAGES);
			}
			folder.open(Folder.READ_WRITE);
			folder.appendMessages(new Message[] {message});
			message.setFlag(FLAGS.Flag.RECENT, true);
			store.close();
			flag=true;
		}catch (Exception e) {
			flag= false;
			e.printStackTrace();
		}
		return flag;
	}*/

	//Use for User Credenditals mail sending
	public boolean sendCreateUser(
			String to, 
			String subject, 
			Map<String, String> userCredentials

			)
	{
		logger.info(">> start of EmailUtil sendCreateUser");
		boolean flag=false;
		try {	
			/*Properties props =new Properties();
			props.put("mail.host",host);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", "true");
			props.put("mail.store.protocol", "smtp");

			// Mail session authentified
			Session session = Session.getInstance(props);*/

			MimeMessage message=mailSender.createMimeMessage();
			//message.setRecipients(Message.RecipientType.TO, to);


			//MimeMessageHelper helper=new MimeMessageHelper(message,true);
			MimeMessageHelper helper=new MimeMessageHelper(message);


			//helper.setFrom(from);
			helper.setTo(to); 
			helper.setSubject(subject); 
			helper.setText("<p>Hello " + userCredentials.get("userName") + "</p>"
					+ "<p>For security reason, you're required to use the following "
					+ "One Time Password to login:</p>"
					+ "<p><b>" + userCredentials.get("password") + "</b></p>"
					+ "<br>"
					+ "<p>Note: this OTP is set to expire in 5 minutes.</p>");

			mailSender.send(message);
			// Copy message to "Sent Items" folder as read
			/*store = session.getStore();
			store.connect(host, username, password);

			folder = store.getFolder("Inbox.Sent");
			if (!folder.exists()) {
				folder.create(Folder.HOLDS_MESSAGES);
			}
			folder.open(Folder.READ_WRITE);
			folder.appendMessages(new Message[] {message});
			message.setFlag(FLAGS.Flag.RECENT, true);

			store.close();*/
			flag=true;
		}catch (Exception e) {
			flag= false;
			e.printStackTrace();
		}
		return flag;
	}


}


