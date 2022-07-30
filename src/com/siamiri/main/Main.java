package com.siamiri.main;

import com.siamiri.gui.GuiController;
import javafx.application.Application;

import javafx.stage.Stage;

/**
 * Start the application
 * Work done by @Smiri
 */
public class Main extends Application {

    /**
     * In the first stage the login stage
     * will be loaded to Sign in, Sign up and
     * and observe the users which will be
     * called from GuiController
     *
     * @param primaryStage {@link GuiController}
     * @throws Exception
     */

    @Override
    public void start(Stage primaryStage) throws Exception{

       GuiController.getInstance().setPrimaryStage(primaryStage);
       GuiController.getInstance().showLogin();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
