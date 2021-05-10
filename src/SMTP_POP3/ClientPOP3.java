package SMTP_POP3;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class ClientPOP3 extends JFrame implements ActionListener{
	
	private Properties properties;
	private Session session;
	private Store store;
	private Folder folder;
	private Message[] messages;
	
	private JPanel panel,logPanel;
	private JScrollPane scrollPane;
	private JTextArea logTextArea;
	private JScrollPane logScrollPane;
	private JPanel activityPanel;
	private JPanel signInPanel;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JLabel lblPass;
	private JTextField txtPass;
	private JButton btnSignIn;
	private JPanel mainPanel;
	private JPanel listMailPanel;
	private JLabel lbllMailList;
	private JScrollPane mailListScrollPane;
	private JPanel actionPanel;
	private JButton btnClearView;
	private JButton btnExit;
	private JPanel readPanel;
	private JLabel lblSpace_1;
	private JLabel lblSpace_2;
	private JScrollPane readScrollPane;
	private JTextArea readMessageArea;
	private JButton btnRefresh;
	
	private String email,pass;
	private JPanel listPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new ClientPOP3();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientPOP3() {
		super("VU_NQH_18IT118_18IT2_NagaKi_LTM::Client POP3");
		setTitle("HOANG LE THIEN AN - 18IT3 - POP3");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		setBounds(100, 100, 858, 473);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		panel = new JPanel();
		scrollPane = new JScrollPane(panel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.setLayout(new BorderLayout(0, 0));
		
		logPanel = new JPanel();
		logPanel.setBorder(new LineBorder(Color.GRAY, 2, true));
		panel.add(logPanel, BorderLayout.EAST);
		logPanel.setLayout(new BorderLayout(0, 0));
		
		logTextArea = new JTextArea();
		logTextArea.setBackground(SystemColor.activeCaption);
		logTextArea.setColumns(20);
		logTextArea.setEditable(false);
		
		logScrollPane = new JScrollPane(logTextArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		logPanel.add(logScrollPane, BorderLayout.CENTER);
		
		appendLog(logTextArea, "Now Starting.....\nWaiting for login");
		
		btnExit = new JButton("Exit");
		btnExit.setIcon(new ImageIcon("D:\\Downloads\\exit_closethesession_close_6317.png"));
		logScrollPane.setColumnHeaderView(btnExit);
		btnExit.addActionListener(this);
		btnExit.setFont(new Font(".VnArial", Font.BOLD, 13));
		
		activityPanel = new JPanel();
		panel.add(activityPanel, BorderLayout.CENTER);
		activityPanel.setLayout(new BorderLayout(0, 0));
		
		signInPanel = new JPanel();
		signInPanel.setBackground(Color.YELLOW);
		signInPanel.setBorder(new LineBorder(Color.GRAY, 1, true));
		activityPanel.add(signInPanel, BorderLayout.NORTH);
		signInPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 15));
		signInPanel.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font(".VnArial", Font.BOLD, 13));
		signInPanel.add(txtEmail);
		txtEmail.setColumns(15);
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					signIn();
				}
			}
		});
		
		lblPass = new JLabel("Password");
		lblPass.setFont(new Font("Tahoma", Font.BOLD, 15));
		signInPanel.add(lblPass);
		
		txtPass = new JTextField();
		txtPass.setFont(new Font(".VnArial", Font.BOLD, 13));
		signInPanel.add(txtPass);
		txtPass.setColumns(15);
		txtPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					signIn();
				}
			}
		});
		
		btnSignIn = new JButton("Login");
		btnSignIn.addActionListener(this);
		btnSignIn.setFont(new Font("Tahoma", Font.BOLD, 15));
		signInPanel.add(btnSignIn);
		
		mainPanel = new JPanel();
		mainPanel.setVisible(false);
		activityPanel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		listMailPanel = new JPanel();
		listMailPanel.setBorder(new LineBorder(Color.GRAY, 1, true));
		mainPanel.add(listMailPanel, BorderLayout.WEST);
		listMailPanel.setLayout(new BorderLayout(0, 0));
		
		lbllMailList = new JLabel("List Mail");
		lbllMailList.setIcon(new ImageIcon("D:\\Downloads\\iconfinder-document03-1622833_121957.png"));
		lbllMailList.setFont(new Font("Tahoma", Font.BOLD, 15));
		listMailPanel.add(lbllMailList, BorderLayout.NORTH);
		listPanel = new JPanel();
		mailListScrollPane = new JScrollPane(listPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		listMailPanel.add(mailListScrollPane, BorderLayout.CENTER);
		
		
		actionPanel = new JPanel();
		mainPanel.add(actionPanel, BorderLayout.SOUTH);
		actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnClearView = new JButton("Clear View");
		btnClearView.addActionListener(this);
		
		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(this);
		btnRefresh.setFont(new Font(".VnArial", Font.BOLD, 13));
		actionPanel.add(btnRefresh);
		btnClearView.setFont(new Font(".VnArial", Font.BOLD, 13));
		actionPanel.add(btnClearView);
		
		readPanel = new JPanel();
		mainPanel.add(readPanel, BorderLayout.CENTER);
		readPanel.setLayout(new BorderLayout(0, 0));
		
		lblSpace_1 = new JLabel("  ");
		readPanel.add(lblSpace_1, BorderLayout.WEST);
		
		lblSpace_2 = new JLabel("  ");
		readPanel.add(lblSpace_2, BorderLayout.EAST);
		
		readMessageArea = new JTextArea();
		readMessageArea.setEditable(false);
		readScrollPane = new JScrollPane(readMessageArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		readPanel.add(readScrollPane, BorderLayout.CENTER);
		
	
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnRefresh) {
			refresh();
		}else if(e.getSource()==btnClearView) {
			clearView();
		}else if(e.getSource()==btnSignIn) {
			signIn();
		}else if(e.getSource()==btnExit) {
			exit();
		}
		
	}

	private void refresh() {
		readMessageArea.setText("");
		listPanel.removeAll();
		listPanel.revalidate();
		listPanel.repaint();
		emailList();
	}

	private void clearView() {
		readMessageArea.setText("");
		
	}

	private void signIn() {
		email = txtEmail.getText().toString();
		pass = txtPass.getText().toString();
		if(email.isEmpty() || pass.isEmpty()) {
			popUpMessage("Please fill in email and pass your account infomation to login");
		}else {
			if(email.contains("@")) {
				email = email.split("@")[0];
			}
		
			properties = new Properties();
			properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.pop3.socketFactory.fallback", "false");
			properties.put("mail.pop3.socketFactory.port", "995");
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.host", "pop.gmail.com");
			properties.put("mail.pop3.user", email);
			properties.put("mail.store.protocol", "pop3");
			
			session = Session.getDefaultInstance(properties);
			
			try {
				
				store = session.getStore("pop3");
				store.connect("pop.gmail.com",email,pass);
				if(store.isConnected()) {
					mainPanel.setVisible(true);
					btnSignIn.setVisible(false);
					lblPass.setVisible(false);
					txtPass.setVisible(false);
					txtEmail.setEnabled(false);
					appendLog(logTextArea, "Login success!!");
					appendLog(logTextArea, "---------------------");
					appendLog(logTextArea, "Welcome back "+email);
					appendLog(logTextArea, "---------------------");
					emailList();
				}else {
					appendLog(logTextArea, "Login Failed, Please try again or check your infomation and internet connection");
					popUpMessage("Login Failed, Please try again or check your infomation and internet connection");
				}
				
			} catch (NoSuchProviderException e) {
				popUpMessage("Something went wrong. Please contact your admin to know more infomation");
				e.printStackTrace();
			} catch (MessagingException e) {
				appendLog(logTextArea, "Login Failed. Username and Password not accepted.Please try again");
				popUpMessage("Username and Password not accepted");
				e.printStackTrace();
			}
			
		}
	}

	private void exit() {
		if(store.isConnected()) {
			try {
				folder.close(false);
				store.close();
				dispose();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}else {
			dispose();
		}
		
	}
	
	public void emailList() {
		appendLog(logTextArea, "Retrieving email list");
		try {
			folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			
			messages = folder.getMessages();
			//System.out.println(messages);
			for(Message msg : messages) {
				JButton mail = new JButton(msg.getMessageNumber()+": "+checkStringTooLong(msg.getSubject()));
				mail.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						readMessageArea.setText("");
						try {
							appendLog(readMessageArea, msg.getFrom().toString());
							appendLog(readMessageArea, "----------------------");
							appendLog(readMessageArea, msg.getContent().toString());
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (MessagingException e1) {
							e1.printStackTrace();
						}
						
					}
				});
				listPanel.add(mail);
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
	private String checkStringTooLong(String msg) {
		int index = 20;
		if(msg.length()>=index) {
			msg = msg.substring(0,index-1)+"...";
		}
		return msg; 
	}
	
	private void popUpMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg,"System",JOptionPane.WARNING_MESSAGE);
	}
	
	public void appendLog(JTextArea log,String message) {
		log.append("\n"+message+".");
	}

}
