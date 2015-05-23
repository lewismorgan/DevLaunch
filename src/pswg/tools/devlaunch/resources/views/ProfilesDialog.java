package pswg.tools.devlaunch.resources.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pswg.tools.devlaunch.DevLaunch;

import java.io.IOException;

/**
 * Created by Waverunner on 5/23/2015
 */
public class ProfilesDialog extends Stage {

    public ProfilesDialog(Stage parent) {
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(parent);
        this.setTitle("Profiles");
        createViewContent();
    }

    private void createViewContent() {
        try {
            Parent root = FXMLLoader.load(DevLaunch.class.getResource("resources/design/profiles.fxml"));

            this.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showAndWait() {
        super.showAndWait();
    }
}
