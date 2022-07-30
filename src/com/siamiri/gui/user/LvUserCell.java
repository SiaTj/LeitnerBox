package com.siamiri.gui.user;

import com.siamiri.Model.user.User;
import com.siamiri.Model.word.Word;
import javafx.scene.control.ListCell;

/**
 * This class represented a cell that
 * exist in the {@link Word}
 * which will be carried out and activated the
 * Contents
 */

public class LvUserCell extends ListCell<User> {
    final int beginIndex    =   0;
    final int endIndex      =   4;
    @Override
    protected void updateItem(User userToShowInCell, boolean isCellEmpty) {
        super.updateItem(userToShowInCell, isCellEmpty);

        if((isCellEmpty) || (userToShowInCell == null)){
            setText(null);
            setGraphic(null);
        }else {
            setText(String.format("%s******",
                    userToShowInCell.getUsername().substring(beginIndex,endIndex)));
        }
    }
}
