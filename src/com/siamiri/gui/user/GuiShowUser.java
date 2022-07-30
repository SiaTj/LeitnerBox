package com.siamiri.gui.user;


import com.siamiri.Model.user.User;
import com.siamiri.logic.userCsvFileHandler.UserCsvFileHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This section has been develop
 * in a case that user forget his
 * username and/or passwort
 * to help his memory to remind it
 */

public class GuiShowUser implements Initializable {

    @FXML
    private ListView<User> lvUser;

    private LvUserCellFactoryCallback lvUserCellFactoryCallback;

    private List<User> users;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.showListViewUser();
    }

    // TODO 1.Show the first four String of the User
    private void showListViewUser(){

        this.lvUser.setOrientation(Orientation.VERTICAL);

        this.lvUserCellFactoryCallback = new LvUserCellFactoryCallback();

        this.users = UserCsvFileHandler.getInstance().readUsersFromCsvFile();

        ObservableList<User> observableUserList = FXCollections.observableList(this.users);

        this.lvUser.getItems().clear();
        this.lvUser.setItems(observableUserList);

        this.lvUser.setCellFactory(this.lvUserCellFactoryCallback);



    }
}

