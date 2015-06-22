package pswg.tools.devlaunch.resources.views;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pswg.tools.devlaunch.DevLaunch;

import java.io.IOException;
import java.net.URL;

/**
 * @author Waverunner on 6/21/2015
 */
public abstract class ApplicationDialog extends Stage {
    private Initializable controller;

    public ApplicationDialog(Stage parent, String title) {
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(parent);
        this.setTitle(title);
        this.setResizable(false);
        createViewContent();

	    initialize();
    }

    private void createViewContent() {
        try {
            URL fxml = DevLaunch.class.getResource(getDesignPath());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(fxml);

            Parent root = fxmlLoader.load();
            controller = fxmlLoader.getController();
            this.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract String getDesignPath();

	protected void initialize() {
	}

	@SuppressWarnings("unchecked")
    public <T extends Initializable> T getController() {
        return (T) controller;
    }
}
