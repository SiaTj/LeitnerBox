package com.siamiri.gui.word;

import com.siamiri.Model.word.Word;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;


public class LvGermanWordCellFactoryCallback implements Callback<ListView<Word>, ListCell<Word>> {
    @Override
    public ListCell<Word> call(ListView<Word> wordCell) {
        return new LvGermanWordCell();
    }

}
