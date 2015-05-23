package pswg.tools.devlaunch.controllers;

import javafx.fxml.Initializable;
import pswg.tools.devlaunch.DevLaunch;
import pswg.tools.devlaunch.models.MainModel;
import pswg.tools.devlaunch.resources.LauncherProfile;
import pswg.tools.devlaunch.resources.ProfilesXmlFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfilesController implements Initializable {

	private MainModel model;

	private int profilesCreated;
	private MainController mainController;


	public void createNewProfile(String name) {
		saveAllProfiles();
		
		LauncherProfile profile = DevLaunch.getDefaultLauncherProfile();
		profile.setName((profilesCreated == 0) ? name : name +" "+String.valueOf(profilesCreated));
		model.addProfile(profile);

		//view.updateProfilesList(model.getProfiles());
		
		profilesCreated++;
	}
	
	
	public void saveAllProfiles() {
		for (int i = 0; i < model.getProfiles().size(); i++) {
/*			if (i == selectedProfile) {
				LauncherProfile p = model.getProfile(i);
				p.setName(view.getValueName());
				p.setGameLoc(view.getValueGameDir());
				p.setGameArgs(view.getValueGameArgs());
				p.setServerAddress(view.getValueAddress());
				p.setServerPort(view.getValuePort());
				break;
			}*/
		}

		try {
			ProfilesXmlFactory.save(model.getProfiles());
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}
		
		//mainController.updateProfiles();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
