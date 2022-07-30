package com.siamiri.gui.leitnerBox;

import com.siamiri.Model.word.Word;
import com.siamiri.gui.GuiController;
import com.siamiri.gui.login.GuiLoginController;
import com.siamiri.logic.wordCsvFileHanndler.WordListCsvHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.time.LocalDate;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;


/**
 * This class is developed
 * for controlling learning algorithms
 * which is based on Leitner-Box
 * Method. Each word has life time of
 * Three days and after three days it will
 * goes one box back. If the user does not
 * check or read that for seven days, after
 * seven days it will goes to the first box
 * which is box 0.
 *
 * TO finish the whole learning processes
 * each word should reach box number Seven
 *
 * Worth to mention that the app does not
 * check that he user read thw word one time
 * or not
 *
 * ----> This functionality is under construction.
 */

public class GuiLeitnerBoxController implements Initializable {

    // region 0. Defining FXMl
    @FXML
    public AnchorPane tfLastViewedDate;

    @FXML
    private TextField txtShowChosenWord;

    @FXML
    private TextField txtShowAnswer;

    @FXML
    private ImageView imageWordToShow;

    @FXML
    private Label lblLastReviewedDate;

    @FXML
    private Label lblCurrentBox;

    @FXML
    private Button btnCorrect;

    @FXML
    private Button btnFalse;

    @FXML
    private Button btnAnswer;

    //endregion 0


    // region 1. Dec and Attributes
    List<Word> rndWord;

    protected final int iBoxOne     = 0;
    protected final int iBoxTwo     = 1;
    protected final int iBoxThree   = 2;
    protected final int iBoxFour    = 3;
    protected final int iBoxFive    = 4;
    protected final int iBoxSix     = 5;
    protected final int iBoxSeven   = 6;
    // Zero is define for showing German
    private static int   iOrderOfWordShownLanguage = 0;


    private boolean isThereWord             = true;
    private int     iCurrentWord            = -1;
    private int     iBoxMaxNum              = -1;
    private int     iBoxMinNum              = 0;
    private int     iBoxDone                = 7; // Word is fully memorized and does not need to be shown
    private int     iDateOfRecall           = -1; // Date of recall the word
    private int     iMaxDateToGoFirstBox    = 7;
    private int     iMaxDateToGoPreviousBox = 4;
    final String    strDefaultImagePath     = "/com/siamiri/resource/images/imageNull.jpg";

    //endregion 1



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        askRndWordFromUser();
    }

    /**
     *
     * @return the random word from user word list
     * that has been shown list in the main word windows
     */
    public Integer randNumber() {

        int randomNum = ThreadLocalRandom.current().nextInt(0, this.rndWord.size());

        this.iCurrentWord = randomNum;

        return randomNum;
    }

    /*
     *Ask Rnd word from User
     */
    private void askRndWordFromUser() {

        this.btnCorrect.setDisable(true);
        this.btnFalse.setDisable(true);
        this.btnAnswer.setDisable(true);
        this.txtShowAnswer.clear();


        leitnerBoxController(isThereWord);

        // This part the word that will be shown will be randomize

        this.rndWord = WordListCsvHandler.getInstance().readWordFromCsvFileToShowInGui();

        Word shwWord = this.rndWord.get(randNumber());

        lblLastReviewedDate.setText(shwWord.getDate());
        lblCurrentBox.setText(shwWord.getBoxNumber());

        if((Integer.valueOf(shwWord.getBoxNumber()) <= iBoxDone)) {

            this.btnAnswer.setDisable(false);

            try {

                // In this part the word will be shown by Box orders
                if ((Integer.valueOf(shwWord.getBoxNumber()) == iBoxOne)) {

                    setWordToShow(this.rndWord.get(randNumber()));
                    showImage();


                } else if ((Integer.valueOf(shwWord.getBoxNumber()) == iBoxTwo)) {

                    setWordToShow(this.rndWord.get(randNumber()));
                    showImage();


                } else if ((Integer.valueOf(shwWord.getBoxNumber()) == iBoxThree)) {

                    setWordToShow(this.rndWord.get(randNumber()));
                    showImage();


                } else if ((Integer.valueOf(shwWord.getBoxNumber()) == iBoxFour)) {

                    setWordToShow(this.rndWord.get(randNumber()));
                    showImage();


                } else if ((Integer.valueOf(shwWord.getBoxNumber()) == iBoxFive)) {

                    setWordToShow(this.rndWord.get(randNumber()));
                    showImage();


                } else if ((Integer.valueOf(shwWord.getBoxNumber()) == iBoxSix)) {

                    setWordToShow(this.rndWord.get(randNumber()));
                    showImage();


                } else if ((Integer.valueOf(shwWord.getBoxNumber()) == iBoxSeven)) {

                    setWordToShow(this.rndWord.get(randNumber()));
                    showImage();


                } else if ((Integer.valueOf(shwWord.getBoxNumber()) == iBoxDone)) {

                    this.txtShowChosenWord.setText(">NoWordToShowTryAgain<");
                    this.txtShowAnswer.setText(">NoWordToShowTryAgain<");
                    Image imgGetNull    = new Image(strDefaultImagePath);
                    imageWordToShow.setImage(imgGetNull);
                }

            } catch (Exception e) {
                showMsgThereIsNoWordToShow();
                e.printStackTrace();
            }
        }else{
            showMsgThereIsNoWordToShow();
        }
    }



    /**
     *
     * @return      : This method return the current date
     * which help to monitoring learning process
     *
     */
    // region. Time and Leitner-Box controller
    public static String getCurrentTime() {

        LocalDate localDate     = LocalDate.now();
        String    formattedDate = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        return formattedDate;
    }

    /**
     *
     * @param isThereWord       : if there is no word to show and/or the words reach the Max Leitner-Box number
     *                          isThereWord will be false and there will be no word to show
     * @return                  : return this.isThereWord true or false depend on word list
     */
    private boolean leitnerBoxController(boolean isThereWord) {

        this.rndWord = WordListCsvHandler.getInstance().readWordFromCsvFileToShowInGui();

        if(this.rndWord.size() == 0){
            isThereWord = false;
        }

        /*
         * In this part the Number that indicate the Box
         * for the word will define. The IBoxNumber shows
         * the Max number and IBoxMin shows the Min number
         * this Two number for leitner Box should not be more
         * that 7 and the date decrement for dropping word is
         * first 3 days the words will go one Box Back and then
         * after 7 days the words will go to the first Box which
         * is number 0
         */

        try {
            if (isThereWord) {

                // this for loop define the Max and Min Leitner Box Number
                for (int index = 0; index < rndWord.size(); index++) {

                    // Get the Max and Min Leitner Box Number
                    Word getWordBoxMaxMinNum = this.rndWord.get(index);


                    // Get Max leitner Box Num
                    if ((Integer.valueOf(getWordBoxMaxMinNum.getBoxNumber()) > iBoxMaxNum)) {

                        this.iBoxMaxNum = Integer.valueOf(getWordBoxMaxMinNum.getBoxNumber());
                    }

                    // Get Min leitner Box Num
                    if ((iBoxMinNum < (Integer.valueOf(getWordBoxMaxMinNum.getBoxNumber())))) {

                        this.iBoxMinNum = Integer.valueOf(getWordBoxMaxMinNum.getBoxNumber());
                    }

                    // This section we check the Time of word
                    Word wordSetDate = this.rndWord.get(index);

                    if ((this.iBoxMaxNum < this.iBoxDone)) {

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate ldOld = LocalDate.parse(wordSetDate.getDate(),formatter);
                        LocalDate ldNew = LocalDate.parse(getCurrentTime(),formatter);

                        // How many days passed from last word read
                        this.iDateOfRecall = (int) ChronoUnit.DAYS.between(ldOld, ldNew);

                    }
                }

            }else {
                showMsgThereIsNoWordToShow();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.isThereWord;
    }

    //endregion



    private void showMsgThereIsNoWordToShow() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Leitner Box");
        alert.setHeaderText("There is NO word to show, you might memorize all of them :-)");
        alert.setContentText("Please add new word!");

        alert.showAndWait();
    }


    /**
     * @param shwWord : by default German word will be asked from user
     *
     * But User has this option to change it to English
     *
     */
   private void setWordToShow(Word shwWord){

       this.rndWord = WordListCsvHandler.getInstance().readWordFromCsvFileToShowInGui();

       // from the connection that we make in Gui controller we take the user
       // decision for ordering of the word

       if ( this.iOrderOfWordShownLanguage == 0) {
           try {

               txtShowChosenWord.setText(shwWord.getGermanWord());

           } catch (Exception e) {
               System.out.println("User Decision Problem");
               e.printStackTrace();
           }
       } else if (this.iOrderOfWordShownLanguage == 1) {

           try {

               //Word shwWord = this.rndWord.get(randNumber());
               txtShowChosenWord.setText(shwWord.getEnglishWord());

           } catch (Exception e) {
               System.out.println("User Decision Problem");
               e.printStackTrace();
           }
       }
   }


    /**
     * The image would be read from
     * the {@link /src/com/siamiri/resource/images}
     *
     * The function will be word with the Abs path
     * in MacBook this might be different in
     * the other systems
     */

   private void showImage(){

       this.rndWord = WordListCsvHandler.getInstance().readWordFromCsvFileToShowInGui();
       Word wordImage = this.rndWord.get(this.iCurrentWord);

       /*
        * Get the current Image
        * since the image numbering
        * start from ONE we add 1
        * to the Image number which
        * is index of the word list
        */

       String strImgToShowIndex    = String.valueOf(Integer.valueOf(wordImage.getImageNumber()));

       // Default Image
       Image imgGetNull    = new Image(strDefaultImagePath);

       // Path of the Current word Image
       String  strImagePath = "src/com/siamiri/resource/images/" + GuiLoginController.USER_INDEX + "image" + strImgToShowIndex +".png";

       // check if the image is available
       File    imgDir       = new File(strImagePath).getAbsoluteFile();
       boolean exist        = imgDir.exists();

       if(exist) {

           String strPathImageToShow = "/com/siamiri/resource/images/" + GuiLoginController.USER_INDEX + "image" + strImgToShowIndex + ".png";

           Image imgToShow    = new Image( strPathImageToShow);

           imageWordToShow.setImage(imgToShow);

       }else {

           imageWordToShow.setImage(imgGetNull);

       }

   }


    //region 2. Btn/Menu Controller

    @FXML
    public void btnShowAnswer() {

        this.rndWord = WordListCsvHandler.getInstance().readWordFromCsvFileToShowInGui();

        Word shwWord = this.rndWord.get(this.iCurrentWord);

        if((Integer.valueOf(shwWord.getBoxNumber()) <= iBoxDone)) {

            if (this.iOrderOfWordShownLanguage == 0) {

                try {

                    txtShowAnswer.setText(shwWord.getEnglishWord());

                } catch (Exception e) {
                    System.out.println("User Decision Problem");
                    e.printStackTrace();
                }

            } else if (this.iOrderOfWordShownLanguage == 1) {

                try {

                    txtShowAnswer.setText(shwWord.getGermanWord());

                } catch (Exception e) {
                    System.out.println("User Decision Problem");
                    e.printStackTrace();
                }
            }
        }else {
            showMsgThereIsNoWordToShow();
        }

        btnCorrect.setDisable(false);
        btnFalse.setDisable(false);

    }

    @FXML
    public void btnCorrectAnswer() {

        this.rndWord = WordListCsvHandler.getInstance().readWordFromCsvFileToShowInGui();

        Word shownWord = this.rndWord.get(iCurrentWord);

        // Send the Word One box back if three days passed
        if ((iDateOfRecall >= iMaxDateToGoPreviousBox) && (iDateOfRecall <= iMaxDateToGoFirstBox)
                && !(Integer.valueOf(shownWord.getBoxNumber())== 0)) {

            shownWord.setBoxNumber(String.valueOf(Integer.valueOf(shownWord.getBoxNumber()) - 1));
            WordListCsvHandler.getInstance().saveWordToCsvFile(this.rndWord);

            // Send the Word Leitner Box one Box back if seven days passed
        } else if (iDateOfRecall > iMaxDateToGoFirstBox) {

            shownWord.setBoxNumber("0");
            WordListCsvHandler.getInstance().saveWordToCsvFile(this.rndWord);


        }

        shownWord.setDate(getCurrentTime());
        shownWord.setBoxNumber(String.valueOf(Integer.valueOf(shownWord.getBoxNumber()) + 1));
        WordListCsvHandler.getInstance().saveWordToCsvFile(this.rndWord);

        askRndWordFromUser();
    }

    @FXML
    public void btnFalseAnswer() {

        this.rndWord = WordListCsvHandler.getInstance().readWordFromCsvFileToShowInGui();
        Word shwWord = this.rndWord.get(iCurrentWord);
        shwWord.setDate(getCurrentTime());
        shwWord.setBoxNumber("0");
        WordListCsvHandler.getInstance().saveWordToCsvFile(this.rndWord);

        askRndWordFromUser();

    }

    @FXML
    public void returnHome() {
        GuiController.getInstance().showMainWordListMenu();
    }


    @FXML
    public void logout() {
        GuiController.getInstance().showLogin();
    }

    @FXML
    public void closeApp() {
        System.exit(0);
    }

    /*
    * User has this option to change the order
    * of showing word language
     */
    @FXML
    public void changeShownWordOrder() {

        if (this.iOrderOfWordShownLanguage == 0){

            this.iOrderOfWordShownLanguage = 1 ;

        } else if(this.iOrderOfWordShownLanguage == 1){

            this.iOrderOfWordShownLanguage = 0 ;
        }else {
            this.iOrderOfWordShownLanguage = 0 ;
        }
        askRndWordFromUser();
    }

    @FXML
    public void showAbout() {
        GuiController.getInstance().showAbout();
    }

    //endregion


//region 1. Struktur
}
