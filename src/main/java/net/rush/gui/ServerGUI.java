package net.rush.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import net.rush.Server;

public class ServerGUI extends JComponent {

	private static final long serialVersionUID = 1L;

	public static Logger logger = Logger.getLogger("Minecraft");

	public static void initGui() throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		final ServerGUI servergui = new ServerGUI();
		final JFrame mainFrame = new JFrame("Rush Server v0.0.1-SNAPSHOT");
		
		mainFrame.add(servergui);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {			
				try {
					Server.getServer().stopServer();
					Thread.sleep(100L);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				} finally {
					System.exit(0);
				}
			}
		});

		mainFrame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				servergui.posX = e.getX();
				servergui.posY = e.getY();
			}
		});

		mainFrame.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent evt) {
				mainFrame.setLocation(evt.getXOnScreen() - servergui.posX, evt.getYOnScreen() - servergui.posY);
			}
		});
	}

	private int posX = 0;
	private int posY = 0;

	public ServerGUI() {
		
		setBorder(null);
		setPreferredSize(new Dimension(840, 480));
		setLayout(new BorderLayout());
		
		JTextArea logAndCmd = new JTextArea();

		logger.addHandler(new LogHandler(logAndCmd));

		JScrollPane log = new JScrollPane(logAndCmd, 22, 30);

		log.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		log.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

		log.setBorder(null);

		final JTextField commandField = new JTextField();

		commandField.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				String command = commandField.getText().trim();

				if (command.length() > 0)
					Server.getServer().getCommandManager().execute(Server.getServer().getConsoleSender(), "/" + command);

				commandField.setText("");
			}
		});

		Font font = new Font("Consolas", Font.PLAIN, 15);
		logAndCmd.setFont(font);		
		logAndCmd.setBackground(Color.BLACK);
		logAndCmd.setForeground(Color.LIGHT_GRAY);

		logAndCmd.setEditable(false);
		logAndCmd.setFocusable(false);
		logAndCmd.setLineWrap(true);

		commandField.setBorder(null);
		commandField.setFont(font);
		commandField.setBackground(Color.BLACK);
		commandField.setForeground(Color.LIGHT_GRAY);
		commandField.requestFocus(false);

		add(log, "Center");
		add(commandField, "South");
	}
}
