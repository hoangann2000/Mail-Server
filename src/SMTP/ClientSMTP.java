package SMTP;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.Box;

@SuppressWarnings("serial")
public class ClientSMTP extends JFrame implements ActionListener{

	private JPanel LogPanel,activityPanel,loginPanel,mailPanel,sendMailPanel,recvInfoPanel,toUserPanel,
	subjectPanel,mailBodyPanel,actionBtnPanel;
	private JTextField txtEmail,txtPass,txtTo,txtSubject;
	private JLabel lblLog,lblSpace,lblEmailusername,lblPass,lblTo,lblSubj,lblContent;
	private JTextArea logTextArea,textMessageArea;
	private JScrollPane logSCrollPanel,textScrollPane;
	private JButton btnSignIn,btnClear,btnSend,btnExit;
	
	private String email,pass,subject,body;
	private static String to;
	private String [] receiver;
	
	public String host = "smtp.gmail.com";
	public Properties properties;
	public Session session;
	public MimeMessage message;
	public Transport transport;
	public InternetAddress[] addresses;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new ClientSMTP();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientSMTP() {
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		setBounds(100, 100, 875, 465);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		LogPanel = new JPanel();
		getContentPane().add(LogPanel, BorderLayout.EAST);
		LogPanel.setLayout(new BorderLayout(0, 0));
		
		lblLog = new JLabel("                      Logs:                      ");
		lblLog.setFont(new Font(".VnArial", Font.BOLD, 13));
		LogPanel.add(lblLog, BorderLayout.NORTH);
		
		logTextArea = new JTextArea();
		logTextArea.setEditable(false);
		logTextArea.setLineWrap(true);
		logTextArea.setWrapStyleWord(true);
		logTextArea.setBorder(new LineBorder(Color.GRAY, 1, true));
		logSCrollPanel = new JScrollPane(logTextArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		LogPanel.add(logSCrollPanel, BorderLayout.CENTER);
		
		lblSpace = new JLabel("  ");
		LogPanel.add(lblSpace, BorderLayout.WEST);
		
		activityPanel = new JPanel();
		getContentPane().add(activityPanel, BorderLayout.CENTER);
		activityPanel.setLayout(new BorderLayout(0, 0));
		
		loginPanel = new JPanel();
		loginPanel.setBorder(new LineBorder(Color.GRAY, 2, true));
		activityPanel.add(loginPanel, BorderLayout.NORTH);
		loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblEmailusername = new JLabel("Email:  ");
		lblEmailusername.setFont(new Font(".VnArial", Font.BOLD, 13));
		loginPanel.add(lblEmailusername);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font(".VnArial", Font.BOLD, 13));
		loginPanel.add(txtEmail);
		txtEmail.setColumns(15);
		
		lblPass = new JLabel("  Password: ");
		lblPass.setFont(new Font(".VnArial", Font.BOLD, 13));
		loginPanel.add(lblPass);
		
		txtPass = new JTextField();
		txtPass.setFont(new Font(".VnArial", Font.BOLD, 13));
		loginPanel.add(txtPass);
		txtPass.setColumns(15);
		
		btnSignIn = new JButton("Sign in");
		btnSignIn.addActionListener(this);
		btnSignIn.setForeground(SystemColor.control);
		btnSignIn.setBackground(SystemColor.activeCaption);
		btnSignIn.setFont(new Font(".VnArial", Font.BOLD, 13));
		loginPanel.add(btnSignIn);
		
		mailPanel = new JPanel();
		mailPanel.setVisible(false);
		mailPanel.setBorder(new LineBorder(SystemColor.activeCaptionBorder, 1, true));
		activityPanel.add(mailPanel, BorderLayout.CENTER);
		mailPanel.setLayout(new BorderLayout(0, 0));
		
		sendMailPanel = new JPanel();
		mailPanel.add(sendMailPanel, BorderLayout.CENTER);
		sendMailPanel.setLayout(new BorderLayout(0, 0));
		
		recvInfoPanel = new JPanel();
		recvInfoPanel.setBorder(new LineBorder(Color.GRAY, 1, true));
		sendMailPanel.add(recvInfoPanel, BorderLayout.NORTH);
		recvInfoPanel.setLayout(new BorderLayout(0, 0));
		recvInfoPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		toUserPanel = new JPanel();
		recvInfoPanel.add(toUserPanel, BorderLayout.NORTH);
		recvInfoPanel.add(Box.createVerticalStrut(10));
		toUserPanel.setLayout(new BorderLayout(0, 0));
		
		lblTo = new JLabel("To:             ");
		lblTo.setFont(new Font(".VnArial", Font.BOLD, 13));
		toUserPanel.add(lblTo, BorderLayout.WEST);
		
		txtTo = new JTextField();
		toUserPanel.add(txtTo, BorderLayout.CENTER);
		txtTo.setColumns(10);
		
		subjectPanel = new JPanel();
		recvInfoPanel.add(subjectPanel, BorderLayout.SOUTH);
		subjectPanel.setLayout(new BorderLayout(0, 0));
		
		lblSubj = new JLabel("Subject:     ");
		lblSubj.setFont(new Font(".VnArial", Font.BOLD, 13));
		subjectPanel.add(lblSubj, BorderLayout.WEST);
		
		txtSubject = new JTextField();
		txtSubject.setColumns(10);
		subjectPanel.add(txtSubject, BorderLayout.CENTER);
		
		mailBodyPanel = new JPanel();
		sendMailPanel.add(mailBodyPanel, BorderLayout.CENTER);
		mailBodyPanel.setLayout(new BorderLayout(0, 0));
		

		textMessageArea = new JTextArea();
		
		textScrollPane = new JScrollPane(textMessageArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		mailBodyPanel.add(textScrollPane, BorderLayout.CENTER);
		
		lblContent = new JLabel("Content:");
		lblContent.setFont(new Font(".VnArial", Font.BOLD, 13));
		mailBodyPanel.add(lblContent, BorderLayout.NORTH);
		
		
		actionBtnPanel = new JPanel();
		actionBtnPanel.setBorder(new LineBorder(Color.GRAY, 1, true));
		mailPanel.add(actionBtnPanel, BorderLayout.SOUTH);
		actionBtnPanel.setLayout(new BorderLayout(0, 0));
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(this);
		btnClear.setFont(new Font(".VnArial", Font.BOLD, 13));
		actionBtnPanel.add(btnClear, BorderLayout.WEST);
		
		btnSend = new JButton("Send Mail");
		btnSend.addActionListener(this);
		btnSend.setFont(new Font(".VnArial", Font.BOLD, 13));
		actionBtnPanel.add(btnSend, BorderLayout.CENTER);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(this);
		btnExit.setFont(new Font(".VnArial", Font.BOLD, 13));
		btnExit.setAlignmentX(Component.RIGHT_ALIGNMENT);
		actionBtnPanel.add(btnExit, BorderLayout.EAST);
		

		appendLog(logTextArea, "Now Starting.....\nWaiting for login");
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnSignIn) {
			signIn();
		}else if(e.getSource()==btnSend) {
			appendLog(logTextArea, "Sending email....");
			sendMail();
		}else if(e.getSource()==btnClear) {
			clearMail();
		}else if(e.getSource()==btnExit) {
			exit();
		}
		
	}
	
	private void signIn(){
		email = txtEmail.getText().toString();
		pass = txtPass.getText().toString();
		if(email.isEmpty() || pass.isEmpty()) {
			popUpMessage("Please fill in email and pass your account infomation to login");
		}else {
			if(email.contains("@")) {
				email = email.split("@")[0];
			}
			properties = new Properties();
			properties.put("mail.smtp.starttls.enable","true");
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.user",email);
			properties.put("mail.smtp.password", pass);
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.auth","true");
			
			session = Session.getInstance(properties);
			try {
				transport = session.getTransport("smtp");
			
				transport.connect(host,email,pass);
				if(transport.isConnected()) {
					mailPanel.setVisible(true);
					txtEmail.setEnabled(false);
					txtPass.setVisible(false);
					lblPass.setVisible(false);
					btnSignIn.setVisible(false);
					appendLog(logTextArea, "Login success!!");
					appendLog(logTextArea, "---------------------");
					appendLog(logTextArea, "Welcome back "+email);
					appendLog(logTextArea, "---------------------");
				}else {
					appendLog(logTextArea, "Login Failed, Please try again or check your infomation and internet connection");
					popUpMessage("Login Failed, Please try again or check your infomation and internet connection");
				}
				
			} catch (NoSuchProviderException e) {
				popUpMessage("Something went wrong. Please contact your admin to know more infomation");
				//e.printStackTrace();
			} catch (MessagingException e) {
				appendLog(logTextArea, "Login Failed. Username and Password not accepted.Please try again");
				popUpMessage("Username and Password not accepted");
				//e.printStackTrace();
			}
		}
		
	}
	
	private void sendMail() {
		subject = txtSubject.getText().toString();
		to = txtTo.getText().toString();
		body =textMessageArea.getText().toString();
		if(to.isEmpty() || subject.isEmpty() || body.isEmpty()) {
			appendLog(logTextArea, "Sent Email Failed...\nPlease fill in all the fields (subject - to whom - body content)");
			popUpMessage("Please fill in all the fields");
		}else {
			if(to.contains(";")) {
				receiver = to.split(";");
			}else {
				String[] recei = {to};
				receiver = recei;
			}
			
			message = new MimeMessage(session);
			
			try {
				
				message.setFrom(new InternetAddress(email));
				addresses = new InternetAddress[receiver.length];
				
				for(int i=0;i<receiver.length;i++) {
					addresses[i] = new InternetAddress(receiver[i]);
				}
				
				for(int i=0;i<addresses.length;i++) {
					message.addRecipient(Message.RecipientType.TO,addresses[i]);
				}
				
				message.setSubject(subject);
				message.setText(body);
				
				transport.sendMessage(message, message.getAllRecipients());

				appendLog(logTextArea, "Email sent successfully");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
	}
	
	private void clearMail() {
		textMessageArea.setText("");
		appendLog(logTextArea, "Cleaning message area....");
	}
	
	private void exit() {
		try {
			transport.close();
		} catch (MessagingException e) {
			//e.printStackTrace();
		}
		dispose();
		
	}

	private void popUpMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg,"System",JOptionPane.WARNING_MESSAGE);
	}
	

	
	
	public void appendLog(JTextArea log,String message) {
		log.append("\n"+message+".");
	}

}
