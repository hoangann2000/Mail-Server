package GuiMain;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class PopMail {
	private static String USER_NAME = "exo.troll.10"; 
    private static String PASSWORD = "Ann191997508";
    
	public static void main(String[] args) throws NoSuchProviderException,Exception {
		// TODO Auto-generated method stub
		
		Properties props = new Properties();
		props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.pop3.socketFactory.fallback", "false");
		props.put("mail.pop3.socketFactory.port", "995");
		props.put("mail.pop3.port", "995");
		props.put("mail.pop3.host", "pop.gmail.com");
		props.put("mail.pop3.user", USER_NAME);
		props.put("mail.store.protocol", "pop3");
		
		Session session = Session.getDefaultInstance(props);
		
		Store store = session.getStore("pop3");
		store.connect("pop.gmail.com",USER_NAME,PASSWORD);
		
		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);
		
		Message[] message = inbox.getMessages();
		for(Message msg:message) {
			msg.writeTo(System.out);
		}
		
		inbox.close(false);
		store.close();
	}

}
