package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HowToUseController {

    @FXML
    private Button closeButton;

    @FXML
    public void closeButtonClicked(ActionEvent actionEvent) throws Exception {
        Stage s=(Stage)closeButton.getScene().getWindow();
        s.close();
    }

    public void translateButtonClicked(ActionEvent actionEvent) {
    }
}
