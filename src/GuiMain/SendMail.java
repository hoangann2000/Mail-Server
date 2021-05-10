package GuiMain;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	
	// full email address dslayer0010@gmail.com
	//                   *user_name*@gmail.com  
	private static String USER_NAME = "genshin1125"; 
    private static String PASSWORD = "KimiNoNamaeWa";
    private static String RECEIVER1 = "dslayer0010@gmail.com";
    private static String RECEIVER2 = "nmthang.18it2@vku.udn.vn";
    private static String RECEIVER3 = "pttung.18it2@vku.udn.vn";
    private static String RECEIVER4 = "pttrung.18it2@vku.udn.vn";
    private static String RECEIVER5 = "natriet.18it2@vku.udn.vn";
    private static String RECEIVER6 = "lvtruyen.18it2@vku.udn.vn";
    private static String RECEIVER7 = "tnhuy.18it3@vku.udn.vn";
			
	public static void main(String[] args) {
		String from =USER_NAME;
		String pass = PASSWORD;
		String[] to = {RECEIVER2}; // list of receiver
		String subject = "Java mail SMTP example";
		String body="Welcome to java mail!!\n"
				+ "Hello Thang, chuc thang ngu ngon na =)))) <3 <3 <3\n";
		
		sendMailFromGmail(from,pass,to,subject,body);
		
	}

	private static void sendMailFromGmail(String from, String pass, String[] to, String subject, String body) {
		// TODO Auto-generated method stub
		Properties properties = new Properties();
		String host = "smtp.gmail.com";
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.user",from);
		properties.put("mail.smtp.password", pass);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth","true");
		
		Session session = Session.getInstance(properties);
		MimeMessage msg = new MimeMessage(session);
		
		try {
			
			Transport transport = session.getTransport("smtp");
			transport.connect(host,from,pass);
			
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];
			
			for(int i=0;i<to.length;i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}
			
			for(int i=0;i<toAddress.length;i++) {
				msg.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}
			
			msg.setSubject(subject);
			msg.setText(body);
			
			
			if(transport.isConnected()){
				System.out.println("Successfully loged in account: "+USER_NAME+"@gmail.com");
				transport.sendMessage(msg,msg.getAllRecipients());
			}else {
				System.out.println("Loggin error, please check your username nad password");
			}
			
			transport.close();
			
		} catch (AddressException ae) {
            ae.printStackTrace();
        }catch (MessagingException me) {
            me.printStackTrace();
        }
	}
}
