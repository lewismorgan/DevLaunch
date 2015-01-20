package pswg.tools.devlaunch.models;

import java.util.ArrayList;
import java.util.List;

import pswg.tools.devlaunch.resources.LauncherProfile;

public class MainModel {
	private int activeProfile;
	private List<LauncherProfile> profiles;
	
	public MainModel() {
		profiles = new ArrayList<LauncherProfile>();
		setActiveProfile(0);
	}

	public MainModel(List<LauncherProfile> profiles) {
		this.profiles = profiles;
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
	
	public void replaceProfiles(List<LauncherProfile> profiles) {
		profiles.clear();
		profiles.addAll(profiles);
	}
	
	public void addProfile(LauncherProfile profile) {
		profiles.add(profile);
	}
	
	public void removeProfile(LauncherProfile profile) {
		profiles.remove(profile);
	}
	
	public void removeProfile(int index) {
		profiles.remove(index);
	}
	
	public LauncherProfile getProfile(int index) {
		return profiles.get(index);
	}
}
