import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.sql.*;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.awt.Color;
public class Login {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	Connection conn = null;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	
	public Login() {
		initialize();
		conn = OracleConnection.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.ORANGE);
		frame.setBounds(100, 100, 850, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(350, 81, 196, 22);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setEchoChar('*');
		txtPassword.setBounds(350, 116, 196, 22);
		frame.getContentPane().add(txtPassword);
		
		JButton btnLogin = new JButton("Login");
		Image img2 = new ImageIcon(this.getClass().getResource("/2.png")).getImage();
		btnLogin.setIcon(new ImageIcon(img2));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String querry="Select * From LOGIN Where User_Name=? and Password=?";
					PreparedStatement pst=conn.prepareStatement(querry);
					pst.setString(1, txtUsername.getText());
					pst.setString(2, txtPassword.getText());
					ResultSet rs=pst.executeQuery();
					int count =0;
					while(rs.next()){
						count=count+1;
					}
					if (count==1){
						JOptionPane.showMessageDialog(null,"UserName and password is correct");
						frame.dispose();
						PlatformInfo p1=new PlatformInfo();
						p1.setVisible(true);
					}
					else if (count >1){
						JOptionPane.showMessageDialog(null,"Duplicate UserName and password");
					}
					else {
						JOptionPane.showMessageDialog(null,"UserName or password is incorrect");
					}
					rs.close();
					pst.close();
				}catch(Exception e){
					JOptionPane.showMessageDialog(null,e);
				}
			}
		});
		btnLogin.setBounds(469, 181, 176, 61);
		frame.getContentPane().add(btnLogin);
		
		JLabel lblUser = new JLabel("UserName");
		lblUser.setBounds(178, 84, 77, 16);
		frame.getContentPane().add(lblUser);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(178, 119, 56, 16);
		frame.getContentPane().add(lblPassword);
		
		JLabel label = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/1.png")).getImage();
		label.setIcon(new ImageIcon(img));
		label.setBounds(12, -37, 134, 288);
		frame.getContentPane().add(label);
	}
}
