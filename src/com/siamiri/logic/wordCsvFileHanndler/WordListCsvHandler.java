package com.siamiri.logic.wordCsvFileHanndler;

import com.siamiri.Model.word.Word;
import com.siamiri.gui.login.GuiLoginController;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class is an Singleton
 * Since it itself make its own
 * single instance at first use
 * and otherwise return it
 */

public class WordListCsvHandler {

    //region 0.Constants
    /**
     * The resource package will be load from here as text file or Csv file
     * as User want
     */
    private static final String WORD_TXT_FILE_PATH = "src/com/siamiri/resource/wordsData/WordList.txt";
    private static final String WORD_CSV_FILE_PATH = "src/com/siamiri/resource/wordsData/WordList.csv";

    //endregion 0


    //region 1.Declaration and initialization of attributes
    private static WordListCsvHandler instance;
    //endregion 1

    // region 2 Constructor

    //region 2.1 Normal Constructor
    /**
     * The private standard
     * constructor is initialized here
     * that can be reach out of this class
     */
    public WordListCsvHandler(){
        System.out.println("My word list is gonna start!");
    }
    //endregion 2.1

    //region 2.2 synchronized the constructor
    /**
     * Initialized the call (No different from which class)
     * the only instance that will exist at the entire runtime
     * of the programm and returns it. So that the functions and methods
     * of this class can be used in a synchronized, thread and access-safe manner.
     *
     * @return :instance: {@link WordListCsvHandler} : only instance that will ever exist in the runtime
     */
    public static synchronized WordListCsvHandler getInstance(){
        if (instance == null) {
            instance = new WordListCsvHandler();
        }
        return instance;
    }
    //endregion 2.2

    //endregion 2

    //region 3 Save and load the word in the list

    public void saveWordToCsvFile(List<Word> wordToSave){

        File    wordCsvFile =   new File(WORD_CSV_FILE_PATH);
        File    wordTxtFile =   new File(WORD_TXT_FILE_PATH);

        FileOutputStream    fos = null;
        OutputStreamWriter  osw = null;
        BufferedWriter      out = null;

        FileOutputStream    fosTxt = null;
        OutputStreamWriter  oswTxt = null;
        BufferedWriter      outTxt = null;

        try {

            if(!wordCsvFile.exists()){

                wordCsvFile.createNewFile();

            }

            if(!wordTxtFile.exists()){

                wordTxtFile.createNewFile();

            }

            fos     =   new FileOutputStream(wordCsvFile);
            fosTxt  =   new FileOutputStream(wordTxtFile);

            osw     =   new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            oswTxt  =   new OutputStreamWriter(fosTxt, StandardCharsets.UTF_8);


            out     =   new BufferedWriter(osw);
            outTxt  =   new BufferedWriter(oswTxt);


            for(Word word : wordToSave){

                out.write(word.getAllWordsAsCsvLine() + "\n");
                outTxt.write(word.getAllWordsAsCsvLine() + "\n");

            }
        }catch (Exception e){
            System.err.println(WORD_CSV_FILE_PATH);
            e.printStackTrace();
        }finally {
            if(out != null){
                try{
                    out.flush();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Word> readWordFromCsvFileToShowInGui(){

        List<Word>  wordFromCsvFile =  new ArrayList<>();
        File        wordCsvFile     =  new File(WORD_CSV_FILE_PATH);

        FileInputStream     fis =   null;
        InputStreamReader   isr =   null;
        BufferedReader      in  =   null;

        try{
            if(wordCsvFile.exists()){
                fis = new FileInputStream(wordCsvFile);
                isr = new InputStreamReader(fis,StandardCharsets.UTF_8);
                in  = new BufferedReader(isr);

                boolean eof = false ;

                while(!eof){

                    String strReadWordCsvLine = in.readLine();

                    if(strReadWordCsvLine == null){
                        eof = true ;
                    }else {
                        Word currentWordFromFile = new Word();
                        currentWordFromFile.setAllAttributesAsCsvLine(strReadWordCsvLine);

                        if((Integer.valueOf(GuiLoginController.USER_INDEX)) == (Integer.valueOf(currentWordFromFile.getWordUserIdIndex()))) {

                            wordFromCsvFile.add(currentWordFromFile);

                        }else{

                            //System.out.println("The User index is "+ GuiLoginController.USER_INDEX +
                                    //" This Word with index "+ currentWordFromFile.getWordIndex() + " can not be shown ");
                        }
                    }
                }
            }
        }catch (Exception e){
            System.err.println(WORD_CSV_FILE_PATH);
            e.printStackTrace();
        }finally {
            if(in != null){
                try{
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return wordFromCsvFile;
    }

    public List<Word> readWordFromCsvFile(){

        List<Word>  wordFromCsvFile =  new ArrayList<>();
        File        wordCsvFile     =  new File(WORD_CSV_FILE_PATH);

        FileInputStream     fis =   null;
        InputStreamReader   isr =   null;
        BufferedReader      in  =   null;

        try{
            if(wordCsvFile.exists()){
                fis = new FileInputStream(wordCsvFile);
                isr = new InputStreamReader(fis,StandardCharsets.UTF_8);
                in  = new BufferedReader(isr);

                boolean eof = false ;

                while(!eof){

                    String strReadWordCsvLine = in.readLine();

                    if(strReadWordCsvLine == null){
                        eof = true ;
                    }else {

                        Word currentWordFromFile = new Word();

                        currentWordFromFile.setAllAttributesAsCsvLine(strReadWordCsvLine);

                        wordFromCsvFile.add(currentWordFromFile);
                    }
                }
            }
        }catch (Exception e){
            System.err.println(WORD_CSV_FILE_PATH);
            e.printStackTrace();
        }finally {
            if(in != null){
                try{
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return wordFromCsvFile;
    }
    //endregion 3
}
