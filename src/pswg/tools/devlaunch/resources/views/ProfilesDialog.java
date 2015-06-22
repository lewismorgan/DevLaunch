package pswg.tools.devlaunch.resources.views;

import javafx.stage.Stage;

/**
 * Created by Waverunner on 5/23/2015
 */
public class ProfilesDialog extends ApplicationDialog {

    public ProfilesDialog(Stage parent) {
        super(parent, "Profiles");
    }

    @Override
    protected String getDesignPath() {
        return "resources/design/profiles.fxml";
    }
}
