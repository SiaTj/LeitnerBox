package com.siamiri.gui.word;

import com.siamiri.Model.word.Word;
import javafx.scene.control.ListCell;

/**
 * This class represented a cell that
 * exist in the {@link Word}
 * which will be carried out and activated the
 * Contents
 */

public class LvGermanWordCell extends ListCell<Word> {

    final int LENGTH_OF_SHOWN_GERMAN_WORD = 60;


    @Override
    protected void updateItem(Word wordToShowInCell, boolean isCellEmpty) {
        super.updateItem(wordToShowInCell, isCellEmpty);

        if((isCellEmpty) || (wordToShowInCell == null)){
            setText(null);
            setGraphic(null);
        }else {
            if((wordToShowInCell.getGermanWord().length() <= LENGTH_OF_SHOWN_GERMAN_WORD)) {

                    setText(String.format("%s\t\t\t\t\t\t%s\t\t\t\t\t\t\t%s\t\t\t\t\t\t%s",
                            wordToShowInCell.getGermanWord(),wordToShowInCell.getEnglishWord(),
                            wordToShowInCell.getBoxNumber(),wordToShowInCell.getDate()));
            }
        }
    }
}

