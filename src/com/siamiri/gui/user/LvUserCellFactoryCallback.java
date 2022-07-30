package com.siamiri.gui.user;

import com.siamiri.Model.user.User;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/*
 * Callback class to
 * write user name in
 * ListView
 */

public class LvUserCellFactoryCallback implements Callback<ListView<User>, ListCell<User>> {
    @Override
    public ListCell<User> call(ListView<User> wordCell) {
        return new LvUserCell();
    }

}
