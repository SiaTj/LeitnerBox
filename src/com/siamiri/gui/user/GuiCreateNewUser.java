package com.siamiri.gui.user;


import com.siamiri.gui.GuiController;
import com.siamiri.Model.user.User;
import com.siamiri.logic.userCsvFileHandler.UserCsvFileHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class is for creating new user
 * each user has his/her unique Index
 * which start from 0 for the first user
 *
 * This index will later for managing
 * Image in {@link com.siamiri.Model.word.Word}
 */
public class GuiCreateNewUser implements Initializable {

    /*
    * The Min length of the user name
    * should be 4, passwort is set to 4
    * as well
     */

    final int ACCEPTABLE_USERNAME_LENGTH    =   4;
    final int ACCEPTABLE_PASSWORT_LENGTH    =   4;
    private static String strValidPasswortLength;


    @FXML
    private TextField   txtNewUsername;

    @FXML
    private TextField   txtNewPasswort;


    private List<User>  users;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.users = UserCsvFileHandler.getInstance().readUsersFromCsvFile();
    }


    // region 1. Getter and Setter User

    // TODO 1. Get User from HUI
    private User getUserFromGui(){

        String  strUsername         =   txtNewUsername.getText();
        String  strUserPassword     =   txtNewPasswort.getText();
        this.strValidPasswortLength =   strUserPassword;

        User userFromGui = null ;

        if ((!strUsername.isEmpty()) &&
                (!strUserPassword.isEmpty())) {

            try {

                userFromGui = new User();

                userFromGui.setUsername(strUsername);

                userFromGui.setUserPw(strUserPassword);

                for (int index = 0; index <= this.users.size(); index++) {

                    if (index == this.users.size()) {

                        userFromGui.setUserIndex(String.valueOf(index));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return userFromGui;
    }


    // TODO 2. Add the User to the CSV file
    @FXML
    public void addUserToCsv() {

        User saveUserToCsv = this.getUserFromGui();

        if(saveUserToCsv != null){

            if((saveUserToCsv.getUsername().length() > ACCEPTABLE_USERNAME_LENGTH) &&
                    (this.strValidPasswortLength.length() > ACCEPTABLE_PASSWORT_LENGTH)) {

                UserCsvFileHandler.getInstance().readUsersFromCsvFile();

                this.users.add(saveUserToCsv);
                UserCsvFileHandler.getInstance().saveUsersToCsvFile(this.users);
                showAddUserSuccessfully();


            }else{
                showMsgAddUserError();
            }

        }else {
            this.showMsgBoxFillEverything();
        }
        //System.exit(0);
        GuiController.getInstance().showLogin();
    }

    //endregion 1

    // TODO 3. The error/success Msg
    // region 2. Error/Success Msg
    private void showAddUserSuccessfully(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("New User");
        alert.setHeaderText("The user has been saved");
        alert.setContentText("to continue please enter OK");

        alert.showAndWait();

    }

    private void showMsgBoxFillEverything() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Saving Error");
        alert.setHeaderText("The Username and Password field should be fill out");
        alert.setContentText("Please fill out all the fields");

        alert.showAndWait();
    }

    private void showMsgAddUserError() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Saving Error");
        alert.setHeaderText("The Username and Password field should be fill out correctly");
        alert.setContentText("Username length should be bigger that 4 and passwort bigger than that 4");

        alert.showAndWait();
    }
    //endregion 2
}
