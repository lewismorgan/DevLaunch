package pswg.tools.devlaunch.models;

import java.util.ArrayList;
import java.util.List;

import pswg.tools.devlaunch.resources.LauncherProfile;

public class ProfilesModel {

	private List<LauncherProfile> profiles;
	
	public ProfilesModel() {
		profiles = new ArrayList<LauncherProfile>();
	}

	public ProfilesModel(List<LauncherProfile> profiles) {
		this.profiles = profiles;
	}
	
	public List<LauncherProfile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<LauncherProfile> profiles) {
		this.profiles = profiles;
	}
	
}
