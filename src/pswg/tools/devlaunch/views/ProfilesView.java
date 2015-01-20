package pswg.tools.devlaunch.views;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.Component;

import javax.swing.Box;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;

import pswg.tools.devlaunch.controllers.ProfilesController;
import pswg.tools.devlaunch.models.MainModel;
import pswg.tools.devlaunch.resources.LauncherProfile;
import pswg.tools.devlaunch.resources.actions.ProfileActions;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;

public class ProfilesView extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel panelButtons;
	private JList<LauncherProfile> listProfiles;
	private JTextField tfName;
	private JTextField tfGameDir;
	private JTextField tfLaunchArgs;
	private JTextField tfAddress;
	private JTextField tfPort;
	private JTextField tfBg;
	private JButton btnBrowseBg;
	private JButton btnBrowseGame;
	private JButton btnApply;
	private JButton btnCancel;
	private JButton btnNewProfile;
	private JButton btnDeleteProfile;
	private Component horizontalStrut;
	
	private boolean enabledEditing;
	
	public ProfilesView(MainModel model) {
		setResizable(false);
		this.enabledEditing = false;
		initialize();
		addActionCommands();
		setupFromModel(model);
	}
	
	public String getValueName() {
		return tfName.getText();
	}
	
	public String getValueGameDir() {
		return tfGameDir.getText();
	}
	
	public String getValueGameArgs() {
		return tfLaunchArgs.getText();
	}
	
	public String getValueAddress() {
		return tfAddress.getText();
	}
	
	public String getValuePort() {
		return tfPort.getText();
	}
	
	public void addController(ProfilesController controller) {
		addWindowListener(controller);
		// Buttons
		btnNewProfile.addActionListener(controller);
		btnDeleteProfile.addActionListener(controller);
		btnApply.addActionListener(controller);
		btnCancel.addActionListener(controller);
		btnBrowseGame.addActionListener(controller);
		//btnBrowseBg.addActionListener(controller);
		
		// Misc
		listProfiles.addListSelectionListener(controller);
		
	}

	public void showProfileSettings(LauncherProfile profile) {
		tfName.setText(profile.getName());
		tfGameDir.setText(profile.getGameLoc());
		tfLaunchArgs.setText(profile.getGameArgs());
		tfAddress.setText(profile.getServerAddress());
		tfPort.setText(profile.getServerPort());
		tfBg.setText("");
		
		if (!enabledEditing)
			enableForEditing();
	}
	
	public void updateProfilesList(List<LauncherProfile> profiles) {
		listProfiles.setModel(new DefaultListModel<LauncherProfile>()); // clear existing list
		populateProfilesList(profiles);
		listProfiles.setSelectedIndex(profiles.size() - 1);
	}
	
	private void enableForEditing() {
		tfName.setEnabled(true);
		tfGameDir.setEnabled(true);
		tfLaunchArgs.setEnabled(true);
		tfAddress.setEnabled(true);
		tfPort.setEnabled(true);
		//tfBg.setEnabled(true);
		btnBrowseGame.setEnabled(true);
		//btnBrowseBg.setEnabled(true);
		
		enabledEditing = true;
	}
	
	private void setupFromModel(MainModel model) {
		populateProfilesList(model.getProfiles());
	}
	
	private void populateProfilesList(List<LauncherProfile> profiles) {
		for (LauncherProfile profile : profiles) {
			((DefaultListModel<LauncherProfile>) listProfiles.getModel()).addElement(profile);
		}
	}
	
	private void addActionCommands() {
		btnNewProfile.setActionCommand(ProfileActions.NEW.name());
		btnDeleteProfile.setActionCommand(ProfileActions.DELETE.name());
		btnApply.setActionCommand(ProfileActions.SAVE.name());
		btnCancel.setActionCommand(ProfileActions.CLOSE.name());
		btnBrowseGame.setActionCommand(ProfileActions.BROWSE_GAME.name());
		btnBrowseBg.setActionCommand(ProfileActions.BROWSE_BG.name());
	}
	
	/**
	 * Create the frame.
	 */
	private void initialize() {
		setTitle("Manage Profiles");
		setType(Type.UTILITY);
		setBounds(100, 100, 580, 388);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panelButtons = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelButtons.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panelButtons, BorderLayout.SOUTH);
		
		btnDeleteProfile = new JButton("Delete Profile");
		btnDeleteProfile.setEnabled(false);
		panelButtons.add(btnDeleteProfile);
		
		btnNewProfile = new JButton("New Profile");
		panelButtons.add(btnNewProfile);
		
		horizontalStrut = Box.createHorizontalStrut(70);
		panelButtons.add(horizontalStrut);
		
		btnCancel = new JButton("Cancel Changes");
		panelButtons.add(btnCancel);
		
		btnApply = new JButton("Apply Changes");
		panelButtons.add(btnApply);
		
		JScrollPane scrollPaneProfiles = new JScrollPane();
		contentPane.add(scrollPaneProfiles, BorderLayout.WEST);
		
		listProfiles = new JList<LauncherProfile>();
		listProfiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listProfiles.setModel(new DefaultListModel<LauncherProfile>());
		scrollPaneProfiles.setViewportView(listProfiles);
		
		JLabel lblProfiles = new JLabel("Profiles");
		contentPane.add(lblProfiles, BorderLayout.NORTH);
		
		JPanel panelSettings = new JPanel();
		contentPane.add(panelSettings, BorderLayout.CENTER);
		
		JLabel lblName = new JLabel("Name");
		
		tfName = new JTextField();
		tfName.setEnabled(false);
		tfName.setColumns(10);
		
		JLabel lblGameDir = new JLabel("Game Dir.");
		
		tfGameDir = new JTextField();
		tfGameDir.setEditable(false);
		tfGameDir.setColumns(10);
		
		btnBrowseGame = new JButton("Browse");
		btnBrowseGame.setEnabled(false);
		
		JLabel lblLaunchArgs = new JLabel("Launch Args");
		
		tfLaunchArgs = new JTextField();
		tfLaunchArgs.setEnabled(false);
		tfLaunchArgs.setColumns(10);
		
		JLabel lblServerAddress = new JLabel("Server Address");
		
		tfAddress = new JTextField();
		tfAddress.setEnabled(false);
		tfAddress.setColumns(10);
		
		JLabel lblServerPort = new JLabel("Server Port");
		
		tfPort = new JTextField();
		tfPort.setEnabled(false);
		tfPort.setColumns(10);
		
		JLabel lblLauncherBackground = new JLabel("Background");
		
		tfBg = new JTextField();
		tfBg.setEnabled(false);
		tfBg.setColumns(10);
		
		btnBrowseBg = new JButton("Browse");
		btnBrowseBg.setEnabled(false);
		GroupLayout gl_panelSettings = new GroupLayout(panelSettings);
		gl_panelSettings.setHorizontalGroup(
			gl_panelSettings.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSettings.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelSettings.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelSettings.createSequentialGroup()
							.addComponent(lblGameDir)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfGameDir, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBrowseGame))
						.addGroup(gl_panelSettings.createSequentialGroup()
							.addComponent(lblLaunchArgs)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfLaunchArgs, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))
						.addGroup(gl_panelSettings.createSequentialGroup()
							.addComponent(lblName)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfName, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
						.addGroup(gl_panelSettings.createSequentialGroup()
							.addComponent(lblServerAddress)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tfAddress, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
						.addGroup(gl_panelSettings.createSequentialGroup()
							.addComponent(lblServerPort)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfPort, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
						.addGroup(gl_panelSettings.createSequentialGroup()
							.addGap(1)
							.addComponent(lblLauncherBackground)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfBg, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnBrowseBg, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panelSettings.setVerticalGroup(
			gl_panelSettings.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSettings.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelSettings.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName)
						.addComponent(tfName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panelSettings.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblGameDir)
						.addComponent(tfGameDir, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowseGame))
					.addGap(18)
					.addGroup(gl_panelSettings.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLaunchArgs)
						.addComponent(tfLaunchArgs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panelSettings.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblServerAddress)
						.addComponent(tfAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panelSettings.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblServerPort)
						.addComponent(tfPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panelSettings.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLauncherBackground)
						.addComponent(tfBg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowseBg))
					.addContainerGap(70, Short.MAX_VALUE))
		);
		panelSettings.setLayout(gl_panelSettings);
	}
}
