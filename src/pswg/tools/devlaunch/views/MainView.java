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

public class MainView {

	private MainModel model;

	private JFrame frmDevlaunch;
	private JButton btnLaunch;
	private JButton btnProfiles;
	private JButton btnOptions;
	private JComboBox<LauncherProfile> comboBoxProfiles;
	
	public MainView(MainModel model) {
		this.model = model;
		
		initialize();
		addActionCommands();
		setupFromModel();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDevlaunch = new JFrame();
		frmDevlaunch.setTitle("ProjectSWG - DevLaunch");
		frmDevlaunch.setBounds(100, 100, 379, 323);
		frmDevlaunch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelButtons = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelButtons.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		frmDevlaunch.getContentPane().add(panelButtons, BorderLayout.SOUTH);
		
		btnProfiles = new JButton("Profiles");
		panelButtons.add(btnProfiles);
		
		btnOptions = new JButton("Options");
		panelButtons.add(btnOptions);
		
		Component horizontalStrut = Box.createHorizontalStrut(40);
		panelButtons.add(horizontalStrut);
		
		btnLaunch = new JButton("Launch");
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
		
		JLabel lblNewLabel = new JLabel("IMAGE");
		frmDevlaunch.getContentPane().add(lblNewLabel, BorderLayout.CENTER);
	}

	private void addActionCommands() {
		btnLaunch.setActionCommand(LauncherActions.PLAY.name());
		btnOptions.setActionCommand(LauncherActions.OPTIONS.name());
		btnProfiles.setActionCommand(LauncherActions.PROFILES.name());
	}
	
	private void setupFromModel() {
		populateProfileSelections(model.getProfiles());
		comboBoxProfiles.setSelectedIndex(model.getActiveProfile());
	}
	
	private void populateProfileSelections(List<LauncherProfile> profiles) {
		for (int index = 0; index < profiles.size(); index++) {
			comboBoxProfiles.addItem(profiles.get(index));
		}
	}
	
	public void addController(MainController controller) {
		btnLaunch.addActionListener(controller);
		btnProfiles.addActionListener(controller);
		btnOptions.addActionListener(controller);
	}
	
	public void show() {
		frmDevlaunch.setVisible(true);
	}
}
