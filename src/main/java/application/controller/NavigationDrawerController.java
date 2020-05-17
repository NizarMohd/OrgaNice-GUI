package application.controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class NavigationDrawerController implements Initializable {

    public static final String SIDE_PANEL = "SidePanel.fxml";
    @FXML
    private  AnchorPane anchorPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;


    private static JFXDrawer currentDrawer;
    private static HamburgerBackArrowBasicTransition currentTransition;

    public static JFXDrawer getDrawer() {
        return currentDrawer;
    }

    public static HamburgerBackArrowBasicTransition getTransition() {
        return currentTransition;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            VBox vBox = FXMLLoader.load(Objects.requireNonNull(SidePanelController.class.getClassLoader().getResource(SIDE_PANEL)));
            drawer.setSidePane(vBox);
            currentDrawer = drawer;
        } catch (IOException e) {
            PopUpMessageController.createExceptionMessage(e);
        }
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            transition.setRate(transition.getRate() * -1);
            currentTransition = transition;
            transition.play();
            if (drawer.isOpened()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });
    }
}

