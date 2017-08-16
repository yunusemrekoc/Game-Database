import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.sql.*;

import javax.swing.*;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;

public class Search extends JFrame {

	private JPanel contentPane;
	private JComboBox comboPlat;
	private JComboBox comboCat;
	private JComboBox comboGame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search frame = new Search();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection conn = null;

	public void fillComboBox() {
		try {
			String querry = "Select * from Platforms";
			PreparedStatement pst = conn.prepareStatement(querry);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				comboPlat.addItem(rs.getString("PLAT_NAME"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void fillComboBox2() {
		try {
			String querry = "Select * from Category";
			PreparedStatement pst = conn.prepareStatement(querry);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				comboCat.addItem(rs.getString("CAT_NAME"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void fillComboBox3() {
		try {
			String querry = "Select Distinct TYPE_NAME from GameType";
			PreparedStatement pst = conn.prepareStatement(querry);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				comboGame.addItem(rs.getString("TYPE_NAME"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	/**
	 * Create the frame.
	 */

	private JTable table;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JTable table_1;

	public Search() {
		conn = OracleConnection.dbConnector();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 878, 579);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		comboPlat = new JComboBox();
		comboPlat.setBounds(124, 94, 166, 22);
		contentPane.add(comboPlat);

		comboCat = new JComboBox();
		comboCat.setBounds(124, 129, 166, 22);
		contentPane.add(comboCat);

		comboGame = new JComboBox();
		comboGame.setBounds(124, 164, 166, 22);
		contentPane.add(comboGame);

		JLabel lblNewLabel = new JLabel("Platforms");
		lblNewLabel.setBounds(0, 97, 73, 16);
		contentPane.add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(332, 97, 367, 189);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try {
					String querry = "Select * from (SELECT Products.Product_ID,Products.Product_Name FROM Products Inner Join  Platforms ON Platforms.Plat_ID=Products.Plat_ID and Platforms.Plat_NAME=? inner join category on category.cat_id=products.cat_id and category.cat_name=? inner join GameType on GameType.product_id=products.product_id and products.plat_id=GameType.plat_id and GameType.Type_Name=?) where Product_ID=?";
					PreparedStatement pst = conn.prepareStatement(querry);
					pst.setString(1, comboPlat.getSelectedItem().toString());
					pst.setString(2, comboCat.getSelectedItem().toString());
					pst.setString(3, comboGame.getSelectedItem().toString());
					pst.setString(4,textField.getText());
					
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		textField.setBounds(332, 45, 231, 34);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnBuy = new JButton("Buy");
		btnBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String querry="Select User_name,User_Acc From Users inner join payment on Users.user_id=payment.user_id";
					PreparedStatement pst=conn.prepareStatement(querry);
					ResultSet rs=pst.executeQuery();
					table_1.setModel(DbUtils.resultSetToTableModel(rs));
                   
                    
					
				} catch(Exception a){
					JOptionPane.showMessageDialog(null,a);
				}
				JOptionPane.showMessageDialog(null," DO YOU WANT TO CONTINUE TO BUY ?");
				JOptionPane.showMessageDialog(null," YOU SUCCESSFULLY BUY THIS GAME...");
				JOptionPane.showMessageDialog(null," GAME FEE ÝS TAKEN FROM YOUR ACCOUNT...");
				System.exit(0);
			}
		});
		btnBuy.setBounds(180, 336, 110, 36);
		contentPane.add(btnBuy);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String querry = "SELECT Products.Product_ID,Products.Product_Name FROM Products Inner Join  Platforms ON Platforms.Plat_ID=Products.Plat_ID and Platforms.Plat_NAME=? inner join category on category.cat_id=products.cat_id and category.cat_name=? inner join GameType on GameType.product_id=products.product_id and products.plat_id=GameType.plat_id and GameType.Type_Name=?";
					PreparedStatement pst = conn.prepareStatement(querry);
					pst.setString(1, comboPlat.getSelectedItem().toString());
					pst.setString(2, comboCat.getSelectedItem().toString());
					pst.setString(3, comboGame.getSelectedItem().toString());
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnSearch.setBounds(180, 253, 110, 34);
		contentPane.add(btnSearch);

		JLabel lblNewLabel_1 = new JLabel("Categories");
		lblNewLabel_1.setBounds(0, 132, 85, 16);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Game Type");
		lblNewLabel_2.setBounds(0, 167, 73, 16);
		contentPane.add(lblNewLabel_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(332, 336, 367, 158);
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		fillComboBox();
		fillComboBox2();
		fillComboBox3();
	}
}
