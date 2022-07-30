package com.siamiri.gui.word;

import com.siamiri.gui.GuiController;
import com.siamiri.gui.login.GuiLoginController;
import com.siamiri.logic.wordCsvFileHanndler.WordListCsvHandler;
import com.siamiri.Model.word.Word;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * This is almost the brain
 * of the code which most of the
 * handling part of list will be done
 * here.
 */

// TODO 1. Define all the FXML Data

public class GuiWordController implements Initializable {

    //region 0.Declaration and initialization of attributes

    @FXML
    private ListView<Word> lvWords;

    @FXML
    private TextField txtGermanWord;

    @FXML
    private TextField txtEnglishWord;

    @FXML
    private TextField txtNumberOfGermanWords;

    @FXML
    private TextField txtNumberOfUserWord;

    @FXML
    private TextField txtNumberOfEnglishWords;

    @FXML
    private TextField txtLengthOfGermanWords;

    @FXML
    private TextField txtLengthOfEnglishWords;

    @FXML
    private Button btnAddImage;


    //private LvEnglishWordCellFactoryCallback lvEnglishWordCellFactoryCallback;


    private List<Word> wordToShow;

    private List<Word> words;

    private Word currentSelectedWord;

    private Stage   uploadImage;
    private String  strImageNumToUpload = "-1";
    private boolean saveImage           = false;


    //endregion 0

    // TODO 2. Define What will happen at Stage start

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnAddImage.setDisable(true);
        this.refreshListViewWords();

    }

    // region 1. Refresh starting list

    // TODO 3. Refresh the ListView

    private void refreshListViewWords() {

        this.lvWords.setOrientation(Orientation.VERTICAL);

        LvGermanWordCellFactoryCallback lvGermanWordCellFactoryCallback = new LvGermanWordCellFactoryCallback();

        this.words      = WordListCsvHandler.getInstance().readWordFromCsvFile();
        this.wordToShow = WordListCsvHandler.getInstance().readWordFromCsvFileToShowInGui();

        ObservableList<Word> observableGermanWordList = FXCollections.observableList(this.wordToShow);

        this.lvWords.getItems().clear();

        this.lvWords.setItems(observableGermanWordList);

        this.lvWords.setCellFactory(lvGermanWordCellFactoryCallback);

        this.txtNumberOfUserWord.setText(String.valueOf(wordToShow.size()));

        this.lvWords.getSelectionModel().selectedItemProperty().addListener(this::onItemCellClick);
    }

    //endregion 1

    // region 2. receive click on word list

    private void onItemCellClick(ObservableValue<? extends Word> observableList,
                                 Word previousSelectionWord, Word currentSelectedWord) {

        if ((currentSelectedWord != null) &&
                (!currentSelectedWord.equals(previousSelectionWord))) {

            this.setGuiDataFromWord(currentSelectedWord);
            this.currentSelectedWord = currentSelectedWord;
            this.btnAddImage.setDisable(false);
        } else {

            btnAddImage.setDisable(true);
        }

    }

    // endregion


    // region 3. Save and Delete Words

    /**
     * To save the word the user
     * index will be consider as unique
     * Integer for each word as well as
     * the Image number which will becrease
     * by word number
     */
    @FXML
    private void saveWord() {

        Word wordToSave = this.getWordFromGui();

        if (wordToSave != null) {

            if (this.currentSelectedWord != null) {

                /*
                 * Read word index from Main function
                 * getMainWordListIndex which the
                 * function receive index of shown list
                 */

                int iCurrentSelectedWordIndex =
                        getMainWordListIndex(this.wordToShow.indexOf(this.currentSelectedWord));

                // Set the word data to the main list
                // with the gotten Index

                this.words.set(iCurrentSelectedWordIndex, wordToSave);

            } else {

                // When there is no word selected add
                // the word to the list from TextField

                this.words.add(wordToSave);
            }

            //save the word to list
            WordListCsvHandler.getInstance().saveWordToCsvFile(this.words);

            //update listView
            this.refreshListViewWords();

            //Reset der Eingabe
            resetInputAndCurrentSelection();

            // Show error Msg When the field is empty
        } else {

            showMsgBoxFillEverything();

        }
    }

    /**
     * To delete the word from list
     * from {@link WordListCsvHandler} the
     * Index of the image will reduce by 1
     * from the word that has been delete
     * until  the end of the list which help
     * to keep managing the Image counting
     * as well as if the image Exist in list
     * will be deleted
     */

    @FXML
    private void deleteWord() {

        if (this.currentSelectedWord != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Delete");
            alert.setHeaderText("Delete the selected word");
            alert.setContentText(String
                    .format("Are you sure you want to delete -> %s <-?", this.currentSelectedWord.getGermanWord()));

            //show alert
            Optional<ButtonType> result = alert.showAndWait();

            //take the user acceptance
            if (result.get() == ButtonType.OK) {


                //Delete word from the list
                int iSizeBeforeDelete   = this.words.size() - 1;
                int iIndexOfDeletedWord = getMainWordListIndex(this.wordToShow.indexOf(this.currentSelectedWord));
                this.words.remove(getMainWordListIndex(this.wordToShow.indexOf(this.currentSelectedWord)));

                //Add new word to the list
                WordListCsvHandler.getInstance().saveWordToCsvFile(this.words);


                /*
                 * Reorder Images and delete current image
                 * Since the is removed from the list
                 * Then we have to deduce one from  the image
                 * number to reorder the image order to the
                 * default to keep the balance of Image order
                 */

                // get absolute path to handle Images
                File imageFolderAbsolutePath = new File("src/com/siamiri/resource/images").getAbsoluteFile();

                // current word image
                String strImageAbsPathToDelete =
                        imageFolderAbsolutePath + "/" + GuiLoginController.USER_INDEX + "image" + String
                                .valueOf(iIndexOfDeletedWord) + ".png";

                // Delete Image if exist
                if (strImageAbsPathToDelete != null) {
                    try {

                        File file = new File(strImageAbsPathToDelete);
                        Files.deleteIfExists(file.toPath());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("No Image to Delete");
                }

                for (int iIndexOfWord = this.wordToShow.indexOf(this.currentSelectedWord)
                     ; iIndexOfWord < iSizeBeforeDelete; iIndexOfWord++) {
                    System.out.println("hi");

                    this.words = WordListCsvHandler.getInstance().readWordFromCsvFile();

                    // Get the position of deleted word
                    Word wordToReorder = this.words.get(iIndexOfWord);

                    // Replace the Image number of the word with the previous word
                    wordToReorder.setImageNumber(String.valueOf(iIndexOfWord));

                    // save the Image index in the file
                    WordListCsvHandler.getInstance().saveWordToCsvFile(this.words);

                    /*
                     * reorder the images number name if
                     * it is available in the folder
                     */

                    String strImageAbsPathToModify =
                            imageFolderAbsolutePath + "/" + GuiLoginController.USER_INDEX + "image" + String
                                    .valueOf(iIndexOfWord + 1) + ".png";

                    File   imageToModify           = new File(strImageAbsPathToModify);

                    if (imageToModify.exists()) {

                        File modifyAccepted =

                                new File(imageFolderAbsolutePath + "/" + GuiLoginController.USER_INDEX + "image" + String
                                        .valueOf(iIndexOfWord  ) + ".png");

                        imageToModify.renameTo(modifyAccepted);

                    } else {
                        System.out.println("no Image to delete");
                    }
                }

                //Update View
                this.refreshListViewWords();

                //reset field
                this.resetInputAndCurrentSelection();
            }
        }
    }
    // endregion


    //region 4. Menu handling

    //TODO 4. Add Menu Items like CLose/Help/Logout

    @FXML
    private void menuItemClose() {
        System.exit(0);
    }

    @FXML
    private void menuItemLogout() {
        GuiController.getInstance().showLogin();
    }

    @FXML
    private void about() {
        GuiController.getInstance().showAbout();
    }

    //endregion 4

    // TODO 6. Set the Leitner-Box
    @FXML
    private void btnLeitnerBoxWordOrder() {

        if (!(this.wordToShow.size() == 0)) {
            GuiController.getInstance().LeitnerBoxMainMenu();
        } else {
            showMsgThereIsNoWordToTrain();
        }
    }


    // region 5. Handling Word AND Image
    // TODO 4. Handling Word from Gui and Csv File

    /**
     *
     * @param currentSelectedWord       : It it the current selected
     *                                  word which will be modify or
     *                                  used for adding Image
     */
    private void setGuiDataFromWord(Word currentSelectedWord) {

        this.currentSelectedWord = currentSelectedWord;

        this.txtGermanWord.setText(this.currentSelectedWord.getGermanWord());
        this.txtEnglishWord.setText(this.currentSelectedWord.getEnglishWord());


        int iNumGer =
                this.currentSelectedWord.getGermanWord().strip().length() - this.currentSelectedWord.getGermanWord()
                                                                                                    .replaceAll(" ", "")
                                                                                                    .length();
        int iNumEng =
                this.currentSelectedWord.getEnglishWord().strip().length() - this.currentSelectedWord.getEnglishWord()
                                                                                                     .replaceAll(" ", "")
                                                                                                     .length();

        this.txtNumberOfGermanWords.setText(String.valueOf(iNumGer + 1));
        this.txtNumberOfEnglishWords.setText(String.valueOf(iNumEng + 1));
        this.txtLengthOfGermanWords.setText(String.valueOf(this.currentSelectedWord.getGermanWord().strip().length()));
        this.txtLengthOfEnglishWords
                .setText(String.valueOf(this.currentSelectedWord.getEnglishWord().strip().length()));
    }

    // Reset Gui
    private void resetInputAndCurrentSelection() {
        this.txtGermanWord.clear();
        this.txtEnglishWord.clear();

        this.currentSelectedWord = null;
    }

    // Get word from Gui to save it
    private Word getWordFromGui() {

        String strGermanWord  = txtGermanWord.getText();
        String strEnglishWord = txtEnglishWord.getText();

        String strCurrentDate      = getCurrentTime();
        String strCurrentUserIndex = GuiLoginController.USER_INDEX;

        // Since the word is change it will be moved to the first Leitner Box

        String strWordLeitnerBoxNum = "0";

        String strCurrentWordImageIndex;

        /*
         * The image of each word is unique
         * to the index of the word in list
         */

        if (this.currentSelectedWord == null) {

            strCurrentWordImageIndex = String.valueOf(words.size());

        } else {

            strCurrentWordImageIndex =
                    String.valueOf(getMainWordListIndex(this.wordToShow.indexOf(this.currentSelectedWord)));

        }

        Word wordFromGui = null;

        if ((!strGermanWord.strip().isEmpty()) &&
                (!strEnglishWord.strip().isEmpty())) {

            try {

                wordFromGui = new Word();

                // TODO 14. The Collected word data from User

                wordFromGui.setGermanWord(strGermanWord);
                wordFromGui.setEnglishWord(strEnglishWord);
                wordFromGui.setWordUserIdIndex(strCurrentUserIndex);
                wordFromGui.setImageNumber(strCurrentWordImageIndex);
                wordFromGui.setBoxNumber(strWordLeitnerBoxNum);
                wordFromGui.setDate(strCurrentDate);
                if (strGermanWord.equalsIgnoreCase(strEnglishWord)) {
                    showMsgSaveError();
                }

            } catch (Exception e) {

                e.printStackTrace();
                System.out.println("Get word from GUI section");
            }
        }
        return wordFromGui;
    }

    /**
     * This function aim is to get the selected
     * word from the User word List which has
     * Unique value of the User information
     * and return the index of the word from the
     * list
     *
     * @param iCurrentSelectedShownWordIndex    : This is selected word from User list with
     *                                          User unique information
     * @return                                  : return @iGetIndex which is index of the word
     *                                            in the main word list
     */
    private int getMainWordListIndex(int iCurrentSelectedShownWordIndex) {

        Word wordShownIndex = this.wordToShow.get(iCurrentSelectedShownWordIndex);

        int iGetIndex = -1;

        try {
            int     index = 0;
            boolean enl   = false;

            while ((index <= this.words.size()) && (!enl)) {

                Word getMainListIndex = this.words.get(index);

                if ((Integer.valueOf(wordShownIndex.getWordUserIdIndex())
                            .equals(Integer.valueOf(getMainListIndex.getWordUserIdIndex()))) &&
                        (wordShownIndex.getGermanWord().equals(getMainListIndex.getGermanWord())) &&
                        (wordShownIndex.getEnglishWord().equals(getMainListIndex.getEnglishWord()))) {

                    iGetIndex = index;
                    enl       = true;

                } else {
                    index++;
                }
            }

        } catch (Exception e) {

            System.out.println("The is not match index!");
            e.printStackTrace();

        }

        return iGetIndex;
    }

    private String getCurrentTime() {

        LocalDate localDate = LocalDate.now();

        return localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    /**
     * With the help of this methode we save
     * Images at {@link /src/com/siamiri/resource/images}
     * path.
     *
     * The image name is an example:
     * 0Image0
     * The first integer refers to user that in
     * this case is user == 0;
     * and the second Integer refers to Image number
     * which increase by number of words which here is the first word
     */
    @FXML
    public void uploadImage() {


        if (this.currentSelectedWord != null) {

            // Set desire name for saving the image, that is index of the word in the list
            this.strImageNumToUpload = this.currentSelectedWord.getImageNumber();

            final FileChooser fileChooser = new FileChooser();

            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"));

            File file = fileChooser.showOpenDialog(uploadImage);


            //Is the file chosen
            if (file != null) {

                // check if the file is jpg or png format
                String strAcceptedPath = file.getPath();
                String strAccepted     = strAcceptedPath.substring(strAcceptedPath.length() - 3);

                if ((strAccepted.equalsIgnoreCase("png")) || (strAccepted.equalsIgnoreCase("jpg"))) {

                    /*
                     * The Image file path just word with the absolute
                     * Path, there we get the AbsolutePath and save or
                     * upload picture there
                     */

                    File fileAbsolutePath = new File("src/com/siamiri/resource/images").getAbsoluteFile();

                    // define image name with specific User Id and Word number
                    String strPath =
                            fileAbsolutePath + "/" + GuiLoginController.USER_INDEX + "image" + this.strImageNumToUpload + ".png";

                    try {

                        // retrieve image
                        BufferedImage bi            = ImageIO.read(new File(file.getPath()));
                        File          outputfileOne = new File(strPath);

                        //check if the image exists and user want to replace it or not
                        if (outputfileOne.exists()) {

                            showMsgImageExist();

                            if (saveImage) {

                                ImageIO.write(bi, "png", outputfileOne);

                            }
                        } else {

                            ImageIO.write(bi, "png", outputfileOne);

                        }

                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                } else {
                    showMsgWrongImageFormat();
                }
            }
        }
        refreshListViewWords();
        resetInputAndCurrentSelection();
    }
    //endregion


    // TODO 5. Define all the Msg
    private void showMsgBoxFillEverything() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Saving Error");
        alert.setHeaderText("The german and english field should be fill out by words");
        alert.setContentText("Please fill out fields correctly");

        alert.showAndWait();
    }

    private void showMsgSaveError() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Caution");
        alert.setHeaderText("The german and english fields are filled out the same");
        alert.setContentText(":-)");

        alert.showAndWait();
    }

    private void showMsgWrongImageFormat() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Attention!!");
        alert.setHeaderText("You did not choose any Image or the file type not supportet");
        alert.setContentText("Please try again, the accepted format is: \n--> .png <-- --> .jpg <-- ");

        alert.showAndWait();
    }

    private void showMsgImageExist() {

        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Attention!!");
        alert.setHeaderText("The Image exists!");
        alert.setContentText("The image exist, are you sure you want to replace it?");

        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getDialogPane().getButtonTypes().add(cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            this.saveImage = true;
        }

    }

    private void showMsgThereIsNoWordToTrain() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Leitner Box Error");
        alert.setHeaderText("There is no word to show in Leitner Box");
        alert.setContentText("Please add new word");

        alert.showAndWait();

    }


}


