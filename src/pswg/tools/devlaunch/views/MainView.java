package pswg.tools.devlaunch.views;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import java.awt.Component;

import javax.swing.Box;

import java.awt.FlowLayout;
import java.util.List;

import pswg.tools.devlaunch.controllers.MainController;
import pswg.tools.devlaunch.models.MainModel;
import pswg.tools.devlaunch.resources.LauncherProfile;
import pswg.tools.devlaunch.resources.actions.LauncherActions;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class MainView {
/*
	private JFrame frmDevlaunch;
	private JButton btnLaunch;
	private JButton btnProfiles;
	private JButton btnOptions;
	private JComboBox<LauncherProfile> comboBoxProfiles;
	private JLabel backgroundImage;
	
	public MainView(MainModel model) {
		initialize();
		addActionCommands();
		setupFromModel(model);
	}

	*//**
	 * Initialize the contents of the frame.
	 *//*
	private void initialize() {
		frmDevlaunch = new JFrame();
		frmDevlaunch.setIconImage(Toolkit.getDefaultToolkit().getImage(MainView.class.getResource("/pswg/tools/devlaunch/resources/images/icon.png")));
		frmDevlaunch.setTitle("ProjectSWG - DevLaunch");
		frmDevlaunch.setBounds(100, 100, 650, 390);
		frmDevlaunch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelButtons = new JPanel();
		frmDevlaunch.getContentPane().add(panelButtons, BorderLayout.SOUTH);
		
		btnProfiles = new JButton("Profiles");
		panelButtons.add(btnProfiles);
		
		btnOptions = new JButton("Options");
		btnOptions.setEnabled(false);
		panelButtons.add(btnOptions);
		
		Component horizontalStrut = Box.createHorizontalStrut(40);
		panelButtons.add(horizontalStrut);
		
		btnLaunch = new JButton("LAUNCH");
		panelButtons.add(btnLaunch);
		
		JPanel panelProfiles = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelProfiles.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		frmDevlaunch.getContentPane().add(panelProfiles, BorderLayout.NORTH);
		
		JLabel lblActiveProfile = new JLabel("Profile: ");
		panelProfiles.add(lblActiveProfile);
		
		comboBoxProfiles = new JComboBox<LauncherProfile>();
		comboBoxProfiles.setModel(new DefaultComboBoxModel<LauncherProfile>());
		panelProfiles.add(comboBoxProfiles);
		
		backgroundImage = new JLabel("IMAGE");
		frmDevlaunch.getContentPane().add(backgroundImage, BorderLayout.CENTER);
	}

	private void addActionCommands() {
		btnLaunch.setActionCommand(LauncherActions.PLAY.name());
		btnOptions.setActionCommand(LauncherActions.OPTIONS.name());
		btnProfiles.setActionCommand(LauncherActions.PROFILES.name());
		comboBoxProfiles.setActionCommand(LauncherActions.PROFILE_CHANGED.name());
	}
	
	private void setupFromModel(MainModel model) {
		populateProfileSelections(model.getProfiles());
		comboBoxProfiles.setSelectedIndex(model.getActiveProfile());
		LauncherProfile profile = model.getProfile(model.getActiveProfile());
		setBackground(profile.getBackground());
	}
	
	private void populateProfileSelections(List<LauncherProfile> profiles) {
		for (int index = 0; index < profiles.size(); index++) {
			comboBoxProfiles.addItem(profiles.get(index));
		}
	}
	
	public void updateProfileSelections(List<LauncherProfile> updatedProfiles, int selection) {
		comboBoxProfiles.setModel(new DefaultComboBoxModel<LauncherProfile>());
		populateProfileSelections(updatedProfiles);
		comboBoxProfiles.setSelectedIndex(selection);
	}
	
	public void addController(MainController controller) {
		btnLaunch.addActionListener(controller);
		btnProfiles.addActionListener(controller);
		btnOptions.addActionListener(controller);
		comboBoxProfiles.addActionListener(controller);
	}
	
	public void show() {
		frmDevlaunch.setVisible(true);
	}
	
	public int getActiveProfile() {
		return comboBoxProfiles.getSelectedIndex();
	}
	
	public void setBackground(String imgLocation) {
		if (imgLocation == null || imgLocation.isEmpty()) {
			backgroundImage.setIcon(new ImageIcon(MainView.class.getResource("/pswg/tools/devlaunch/resources/images/default_bg.png")));
		} else {
			backgroundImage.setIcon(new ImageIcon(imgLocation));
		}

	}*/
}
