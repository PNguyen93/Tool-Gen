import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIDB extends JDialog {

	public GUIDB() {
		setTitle("Setup DB");
		setModal(true);
		JButton b = new JButton("Ok");
		JLabel host = new JLabel("Host");
		JLabel namedb = new JLabel("Database");
		JLabel user = new JLabel("Username");
		JLabel pass = new JLabel("Password");
		// add label
		host.setBounds(10, 10, 70, 30);
		namedb.setBounds(10, 50, 70, 30);
		user.setBounds(10, 90, 70, 30);
		pass.setBounds(10, 130, 70, 30);
		JTextField Fhost = new JTextField();
		JTextField Fnamedb = new JTextField();
		JTextField Fuser = new JTextField();
		JTextField Fpass = new JTextField();
		Fhost.setBounds(90, 10, 280, 30);
		Fnamedb.setBounds(90, 50, 280, 30);
		Fuser.setBounds(90, 90, 280, 30);
		Fpass.setBounds(90, 130, 280, 30);
		b.setBounds(90, 180, 100, 30);
		add(b);// adding button on frame
		add(host);// adding button on frame
		add(namedb);// adding button on frame
		add(user);// adding button on frame
		add(pass);// adding button on frame
		add(Fhost);// adding button on frame
		add(Fnamedb);// adding button on frame
		add(Fuser);// adding button on frame
		add(Fpass);// adding button on frame
		setSize(400, 250);
		setLayout(null);
		// handle action listener start
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeBD(Fhost.getText(), Fnamedb.getText(), Fuser.getText(), Fpass.getText());
				//close dialog 
				dispose();
				
			}
		});
		// end
		// load screen
		String workingDir = System.getProperty("user.dir");
		String FILENAME = workingDir + "\\logDB.txt";
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(FILENAME));
			List<String> list = new ArrayList<String>();
			while ((sCurrentLine = br.readLine()) != null) {
				list.add(sCurrentLine);
			}
			Fhost.setText(list.get(0));
			Fnamedb.setText(list.get(1));
			Fuser.setText(list.get(2));
			Fpass.setText(list.get(3));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void writeBD(String host, String namedb, String user, String pass) {
		// TODO Auto-generated method stub
		BufferedWriter bw = null;
		FileWriter fw = null;
		String workingDir = System.getProperty("user.dir");
		String FILENAME = workingDir + "\\logDB.txt";
		System.out.println(FILENAME);
		File f = new File(FILENAME);
		if (f.exists() && !f.isDirectory()) {
			f.delete();
		}
		try {
			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			bw.write(host);
			bw.newLine();
			bw.write(namedb);
			bw.newLine();
			bw.write(user);
			bw.newLine();
			bw.write(pass);
			bw.newLine();
			System.out.println("Done");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}
