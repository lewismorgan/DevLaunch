package pswg.tools.devlaunch.models;

import java.util.ArrayList;
import java.util.List;

import pswg.tools.devlaunch.DevLaunch;
import pswg.tools.devlaunch.resources.LauncherProfile;

public class MainModel {
	private int activeProfile;
	private List<LauncherProfile> profiles;
	
	public MainModel() {
		profiles = new ArrayList<LauncherProfile>();
		LauncherProfile profile = DevLaunch.getDefaultLauncherProfile();
		profiles.add(profile);
		
		setActiveProfile(0);
	}

	public int getActiveProfile() {
		return activeProfile;
	}

	public void setActiveProfile(int activeProfile) {
		this.activeProfile = activeProfile;
	}
	
	public List<LauncherProfile> getProfiles() {
		return profiles;
	}
	
	public void setProfiles(List<LauncherProfile> profiles) {
		this.profiles = profiles;
	}
	
	public void addProfile(LauncherProfile profile) {
		profiles.add(profile);
	}
}
