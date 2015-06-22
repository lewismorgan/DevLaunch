package pswg.tools.devlaunch.resources;

public class LauncherProfile implements Cloneable {

	private String name;
	private String gameArgs;
	private String gameLoc;
	private String serverAddress;
	private String serverPort;
	private String background;
	private boolean console;
	
	public LauncherProfile() {}
	public LauncherProfile(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGameArgs() {
		return gameArgs;
	}
	public void setGameArgs(String gameArgs) {
		this.gameArgs = gameArgs;
	}
	public String getGameLoc() {
		return gameLoc;
	}
	public void setGameLoc(String gameLoc) {
		this.gameLoc = gameLoc;
	}
	public String getServerAddress() {
		return serverAddress;
	}
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	public String getServerPort() {
		return serverPort;
	}
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public boolean isConsoleEnabled() {
		return console;
	}

	public void setConsole(boolean console) {
		this.console = console;
	}

	@Override
	public String toString() { return getName(); }

	@Override
	public LauncherProfile clone() {
		LauncherProfile copy = null;
		try {
			copy = (LauncherProfile) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			DialogUtils.showExceptionDialog(e);
		}
		if (copy == null)
			return null;

		copy.name = name;
		copy.gameArgs = gameArgs;
		copy.serverAddress = serverAddress;
		copy.serverPort = serverPort;
		copy.background = background;
		copy.console = console;
		return copy;
	}
}
