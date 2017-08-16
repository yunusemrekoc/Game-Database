import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

import javax.swing.*;
import java.awt.Color;

public class CategoryInfo extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CategoryInfo frame = new CategoryInfo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	Connection conn = null;
	public CategoryInfo() {
		conn = OracleConnection.dbConnector();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 850, 450);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Click to see categories");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String querry="Select * From category";
					PreparedStatement pst=conn.prepareStatement(querry);
					ResultSet rs=pst.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    
					
				} catch(Exception a){
					JOptionPane.showMessageDialog(null,a);
				}
			}
		});
		btnNewButton.setBounds(44, 25, 182, 37);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Next");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductsInfo p1=new ProductsInfo();
				p1.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(591, 25, 97, 37);
		contentPane.add(btnNewButton_1);
		
		JScrollPane table2 = new JScrollPane();
		table2.setBounds(43, 75, 645, 221);
		contentPane.add(table2);
		
		table = new JTable();
		table2.setViewportView(table);
	}
}
