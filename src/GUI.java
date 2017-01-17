import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.*;

public class GUI extends JFrame implements WindowListener {

	public GUI() {
		super("Tool create model");
		JButton b = new JButton("Generate");
		JButton db = new JButton("Set DB");
		JLabel input = new JLabel("Input");
		JLabel output = new JLabel("Output");
		input.setBounds(10, 10, 40, 40);
		output.setBounds(10, 60, 40, 40);
		JTextField fieldinput = new JTextField();
		JTextField fieldoutput = new JTextField();
		fieldinput.setBounds(60, 10, 300, 30);
		fieldoutput.setBounds(60, 60, 300, 30);
		b.setBounds(60, 100, 100, 30);
		db.setBounds(190, 100, 100, 30);
		add(b);// adding button on frame
		add(db);// adding button on frame
		add(input);// adding button on frame
		add(output);// adding button on frame
		add(fieldinput);// adding button on frame
		add(fieldoutput);// adding button on frame
		setSize(400, 200);
		setLayout(null);
		setVisible(true);
		//action listener start
		fieldoutput.addMouseListener(new MouseAdapter(){ //chosen Directory
            @Override
            public void mouseClicked(MouseEvent e){
            	//file chosen start
        		JFileChooser chooser = new JFileChooser();
        		chooser.setCurrentDirectory(new java.io.File("."));
        		chooser.setDialogTitle("Select file output");
        		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        		chooser.setAcceptAllFileFilterUsed(false);
        		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        			fieldoutput.setText(chooser.getSelectedFile().toString());
        			
        		} else {
        			fieldoutput.setText("");
        		}
        		//file chosen end.
            }
        });
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LowCase lc = new LowCase();
					try {
						lc.Generate(fieldinput.getText(),fieldoutput.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Throwable e1) {
					}
			}
		});
		db.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIDB db = new GUIDB();
				db.setVisible(true);
			}
		});
		//action listener end
		this.addWindowListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
