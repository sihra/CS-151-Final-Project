import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class LoginView extends JPanel{
	private MainScreen parent;
	private JTextField username;
	private JTextField password;
	private JButton loginB;
	private LoginController controller;
	private boolean isValid = false;
	public LoginView()
	{
		SpringLayout lytMgr = new SpringLayout();
		JPanel textAreas = new JPanel();
		JLabel usernameL = new JLabel("Username");
		username = new JTextField(20);
		JLabel passwordL = new JLabel("Password");
		password = new JTextField(20);
		setLayout(lytMgr);
		loginB = new JButton("Login");
		loginB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if ("admin".equals(username.getText())&&"admin".equals(password.getText()))
				{
					isValid = true;
					if (parent!=null)
					{
						parent.setLoggedIn(true);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "The password you entered was invalid,\n please re-enter a valid password to continue");
				}
				
			}
		});
		setPreferredSize(new Dimension(450, 125));
		lytMgr.putConstraint(SpringLayout.HORIZONTAL_CENTER, username, 0, SpringLayout.HORIZONTAL_CENTER, this);
		lytMgr.putConstraint(SpringLayout.SOUTH, username, 0, SpringLayout.VERTICAL_CENTER, this);
		lytMgr.putConstraint(SpringLayout.EAST, usernameL, -5, SpringLayout.WEST, username);
		lytMgr.putConstraint(SpringLayout.NORTH, usernameL, 0, SpringLayout.NORTH, username);
		

		lytMgr.putConstraint(SpringLayout.HORIZONTAL_CENTER, password, 0, SpringLayout.HORIZONTAL_CENTER, this);
		lytMgr.putConstraint(SpringLayout.NORTH, password, 5, SpringLayout.VERTICAL_CENTER, this);
		lytMgr.putConstraint(SpringLayout.EAST, passwordL, -5, SpringLayout.WEST, password);
		lytMgr.putConstraint(SpringLayout.NORTH, passwordL, 0, SpringLayout.NORTH, password);
		
		lytMgr.putConstraint(SpringLayout.NORTH, loginB, 5, SpringLayout.SOUTH, password);
		lytMgr.putConstraint(SpringLayout.EAST, loginB, 0, SpringLayout.EAST, password);
		add(usernameL);
		add(username);
		add(passwordL);
		add(password);
		add(loginB);
	}
	public void setParent(MainScreen _parent)
	{
		parent = _parent;
	}
	public static void main(String[] args)
	{
		JFrame d = new JFrame();
		d.getContentPane().add(new LoginView());
		d.pack();
		d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		d.setVisible(true);
	}

}
