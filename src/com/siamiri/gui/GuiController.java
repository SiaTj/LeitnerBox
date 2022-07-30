package com.siamiri.gui;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * This class remember the
 * the primary stage of type{@link javafx.stage.Stage}
 * which the login stage will be called from the
 * {@link com.siamiri.main.Main} and the main stage
 * is able to display different scenes one after the other.
 */

public class GuiController {

    //region 0.Constants
    private static final String GUI_APP_MAIN_MENU_PATH   = "/layout/word_list_menu_layout.fxml";
    private static final String GUI_APP_LOGIN_PATH       = "/layout/login_layout.fxml";
    private static final String GUI_APP_ABOUT_LAYOUT     = "/layout/about_layout.fxml";
    private static final String GUI_APP_SHOW_USER_PATH   = "/layout/show_user_layout.fxml";
    private static final String GUI_APP_SAVE_USER_PATH   = "/layout/create_new_user_layout.fxml";
    private static final String GUI_APP_SHOW_LEITNER_BOX = "/layout/leitner_box_main_menu_layout.fxml";
    private static final String GUI_APP_SHOW_IMAGE_PATH  = "/layout/manage_image_layout.fxml";



    private static final String APPLICATION_NAME = "Personal Word List";
    private static final String LOGIN_TITLE      = APPLICATION_NAME + " - Login interface";

    private static final double SCENE_DEF_LOGIN_WIDTH  = 600D;
    private static final double SCENE_DEF_LOGIN_HEIGHT = 425D;
    private static final double SCENE_DEF_LIST_HEIGHT  = 500D;
    private static final double SCENE_DEF_LIST_WIDTH   = 600D;
    private static final double SCENE_DEF_IMAGE_HEIGHT      = 300D;
    private static final double SCENE_DEF_IMAGE_WIDTH       = 400D;
    private static final double SCENE_WIDTH_MAX_WIDTH_IMAGE = 400D;
    private static final double SCENE_WIDTH_MIN_WIDTH_IMAGE  = 400D;
    private static final double SCENE_WIDTH_MAX_HEIGHT_IMAGE = 300D;
    private static final double SCENE_WIDTH_MIN_HEIGHT_IMAGE = 300D;
    private static final double SCENE_DEF_MAX_HEIGHT         = 400D;
    private static final double SCENE_DEF_MIN_HEIGHT         = 500D;
    private static final double SCENE_DEF_MAX_WIDTH          = 800D;
    private static final double SCENE_DEF_MIN_WIDTH          = 600D;
    private static final double SCENE_DEF_SHOW_IMAGE_WIDTH   = 300D;
    private static final double SCENE_DEF_SHOW_IMAGE_HEIGHT  = 330D;

    //endregion 0


    //region 1.Declaration and initialization of attributes
    private static GuiController instance;
    /*
     * show different windows
     * regard the setting popup
     */
    private        Stage         primaryStage;
    private        Stage         userStage;
    private        Stage         imageStage;
    private        Stage         createNewUser;
    private        Stage         about;
    private        Stage         loadImage;
    //endregion 1


    //region 2.Constructor

    /**
     * Standard Constructor
     * and Singleton
     */

    private GuiController() {

    }

    public static synchronized GuiController getInstance() {
        if (instance == null) {
            instance = new GuiController();
        }
        return instance;
    }
    //endregion 2


    //region 3.Setter and Getter Stage
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    //endregion


    //region 4. Main Methods and Functions

    // TODO 1. Create User Login Stage
    //region 4.1 Login GUI
    public void showLogin() {

        try {
            if (!(createNewUser == null)) {
                createNewUser.close();
            }

            Parent root = FXMLLoader.load(getClass().getResource(GUI_APP_LOGIN_PATH));
            primaryStage.setTitle(LOGIN_TITLE);
            primaryStage.setScene(new Scene(root, SCENE_DEF_LOGIN_WIDTH, SCENE_DEF_LOGIN_HEIGHT));

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion 4.1

    // TODO 2. Show Word List Main Menu
    //region 4.2 Show main menu
    public void showMainWordListMenu() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource(GUI_APP_MAIN_MENU_PATH));

            this.primaryStage.setTitle(APPLICATION_NAME);

            this.primaryStage.setScene(new Scene(root, SCENE_DEF_LIST_WIDTH, SCENE_DEF_LIST_HEIGHT));

            this.primaryStage.setMinHeight(SCENE_DEF_MIN_HEIGHT);
            this.primaryStage.setMinWidth(SCENE_DEF_MIN_WIDTH);
            this.primaryStage.setMaxWidth(SCENE_DEF_MAX_WIDTH);
            this.primaryStage.setMaxHeight(SCENE_DEF_MAX_HEIGHT);

            this.primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion 4.2

    /**
     * This method {@link com.siamiri.gui.user.GuiShowUser}
     * and {@link com.siamiri.gui.user.GuiCreateNewUser}
     * are defined here for handling users
     */
    //region 4.3 show Users
    public void ShowUser() {

        try {

            if (this.userStage == null) {

                this.userStage = new Stage();

                this.userStage.initOwner(this.primaryStage);
            }

            if (!this.userStage.isShowing()) {
                Parent root = FXMLLoader.load(getClass().getResource(GUI_APP_SHOW_USER_PATH));

                this.userStage.setTitle(APPLICATION_NAME);

                this.userStage.setScene(new Scene(root, SCENE_DEF_LOGIN_WIDTH, SCENE_DEF_LOGIN_HEIGHT));

                this.userStage.show();

            } else {

                this.userStage.requestFocus();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region 4.4 create Users
    public void createNewUser() {
        try {

            if (this.createNewUser == null) {

                this.createNewUser = new Stage();

                this.createNewUser.initOwner(this.primaryStage);
            }

            if (!this.createNewUser.isShowing()) {
                Parent root = FXMLLoader.load(getClass().getResource(GUI_APP_SAVE_USER_PATH));

                this.createNewUser.setTitle("Create New User");

                this.createNewUser.setScene(new Scene(root, SCENE_DEF_LOGIN_WIDTH, SCENE_DEF_LOGIN_HEIGHT));

                this.createNewUser.setMaxHeight(230D);
                this.createNewUser.setMinHeight(230D);
                this.createNewUser.setMaxWidth(400D);
                this.createNewUser.setMinWidth(400D);

                this.createNewUser.show();

            } else {

                this.createNewUser.requestFocus();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion 4.4

    // region 4.5 Leitner-Box
    // TODO 3. Config Leitner-Box
    public void LeitnerBoxMainMenu() {

        try {

            Parent root = FXMLLoader.load(getClass().getResource(GUI_APP_SHOW_LEITNER_BOX));

            this.primaryStage.setTitle(APPLICATION_NAME);

            this.primaryStage.setScene(new Scene(root, 600D, 800D));

            this.primaryStage.setMaxHeight(SCENE_DEF_LIST_HEIGHT - 50);
            this.primaryStage.setMinHeight(SCENE_DEF_LIST_HEIGHT - 50);
            this.primaryStage.setMaxWidth(600D);
            this.primaryStage.setMinWidth(600D);


            this.primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //endregion 4.5

    //endregion 4


    //region 6. Show About

    public void showAbout() {

        try {

            if (this.about == null) {

                this.about = new Stage();

                this.about.initOwner(this.primaryStage);
            }

            if (!this.about.isShowing()) {
                Parent root = FXMLLoader.load(getClass().getResource(GUI_APP_ABOUT_LAYOUT));

                this.about.setTitle("About");

                this.about.setScene(new Scene(root, SCENE_DEF_LOGIN_WIDTH, SCENE_DEF_LOGIN_HEIGHT));

                this.about.setMaxHeight(170D);
                this.about.setMinHeight(170D);
                this.about.setMaxWidth(300D);
                this.about.setMinWidth(300D);

                this.about.show();

            } else {

                this.about.requestFocus();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //endregion 6
}
