package com.siamiri.gui.login;

import com.siamiri.Model.user.User;
import com.siamiri.logic.userCsvFileHandler.UserCsvFileHandler;
import com.siamiri.gui.GuiController;
import de.rhistel.Encrypter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is for controlling username and password
 * entered by user. create new user and ect.. . After
 * the user log in, user will enter main application scene
 *
 * The User data Will be save {@link UserCsvFileHandler}
 * As Text and Csv format. Each User could has it is own index
 * from {@link User} therefore different person with the same
 * Username and Passwort can Sign Up.
 */

 //TODO 1. Define all needed variable


public class GuiLoginController implements Initializable {


    //region 0.Constants
    private static  final int DEF_VALUE_LOGIN_ATTEMPTS   =   1;
    private static  final int MAX_LOGIN_ATTEMPTS         =   3;
    //endregion 0


    //region 1.Declaration and initialization of FXML file

    @FXML
    private TextField       txtUsername ;

    @FXML
    private PasswordField   txtPasswordField;

    @FXML
    private Label           lblLoginInfo;

    //endregion 1

    // region 2. class needed variable define

    private int     iLoginAttempts;
    public static   String    USER_INDEX = "0";

    // endregion 2.

    /**
     * It will be initialized a controller after a root element
     * has been completely processed .
     *
     * @param location The location is used to resolve relative path for thr root object
     *                 or {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null}
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.iLoginAttempts = DEF_VALUE_LOGIN_ATTEMPTS;
    }


    //region 2. Login

     //TODO 2. Define the login
     //TODO 3. Encrypt the user data
     //TODO 5. Check and proof the CsVEncrypted Data
     //TODO 6. Check the login Error Massage


    @FXML
    private void login() {

        try {

            /* this Stage is to check If the Text field is
            * empty or not if it is Empty the user will be
            * asked to fill out the field
             */

            if ((!(txtPasswordField.getText().isEmpty()) && !(txtUsername.getText().isEmpty()))) {

                // Check the Max attempt for Log in

                if (this.iLoginAttempts < MAX_LOGIN_ATTEMPTS) {

                    String strLoginUserName     = this.txtUsername.getText();
                    String strLoginHashedUserPw = this.txtPasswordField.getText();

                    // Get data from Text field and Set it to CsvUser file

                    if ((!strLoginUserName.isEmpty()) && (!strLoginHashedUserPw.isEmpty())) {

                        // Encrypt the user password
                        strLoginHashedUserPw = Encrypter.getInstance().encryptToSha256HashHexString(strLoginHashedUserPw);

                        boolean isValidUser = false;

                        // At this step we will make unique user index
                        /**
                         * Since in this part I am using
                         * the Public static String to save
                         * User Index , I am not sure it is
                         * correct way ot not
                         */
                        for (User user : UserCsvFileHandler.getInstance().readUsersFromCsvFile()) {

                            if ((user.getUsername().equals(strLoginUserName)) &&
                                    (user.getUserPw().equals(strLoginHashedUserPw))) {

                                this.USER_INDEX = user.getUserIndex();

                                isValidUser     = true;

                            }
                        }
                        if (isValidUser) {

                            //TODO 9. Login successfully, Open Main Menu

                            GuiController.getInstance().showMainWordListMenu();

                        } else {
                            this.txtUsername.clear();
                            this.txtPasswordField.clear();

                            this.lblLoginInfo.setTextFill(Paint.valueOf("red"));
                            this.lblLoginInfo.setText("Username or Passwort is false");

                            iLoginAttempts++;
                        }

                    }

                } else if (iLoginAttempts >= MAX_LOGIN_ATTEMPTS) {
                    System.exit(0);
                }
            }else {
                msgFillOutUsernameAndPasswortField();
            }

            }catch(Exception e){
                e.printStackTrace();
            }

    }

    @FXML
    public void showAllUsername() {
        GuiController.getInstance().ShowUser();
    }

    //TODO 4. Define Sign up
    @FXML
    public void signup(){
        GuiController.getInstance().createNewUser();
    }

    @FXML
    public void exitApplicationLogin() {
        System.exit(0);
    }

    public void showAbout() {
        GuiController.getInstance().showAbout();
    }

    // TODO 7. Login error
    private void msgFillOutUsernameAndPasswortField() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Login error");
        alert.setHeaderText("Username and Passwort field should fill out completely");
        alert.setContentText("Please try again");

        alert.showAndWait();
    }
}
