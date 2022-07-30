package com.siamiri.Model.word;

public class Word extends WordListCharacteristics {

    //region 0.Constructor
    public Word(){
    }

    public Word(String strWordUserIdIndex,String strGermanWord, String strEnglishWord, String strImageNumber,
                String strLeitnerBoxNum, String strReadDate){
        super();

        this.strWordUserIdIndex =   strWordUserIdIndex;
        this.strGermanWord      =   strGermanWord;
        this.strEnglishWord     =   strEnglishWord;
        this.strImageNumber     =   strImageNumber;
        this.strLeitnerBoxNum   =   strLeitnerBoxNum;
        this.strReadDate        =   strReadDate;
    }
    //endregion 0

    //region 1.Setter and Getter

    public String getGermanWord() {
        return this.strGermanWord;
    }

    public void setGermanWord(String strGermanWord) {
        this.strGermanWord = strGermanWord;
    }

    public String getEnglishWord() {
        return this.strEnglishWord;
    }

    public void setEnglishWord(String strEnglishWord) {
        this.strEnglishWord = strEnglishWord;
    }

    public String getWordUserIdIndex(){
        return this.strWordUserIdIndex;
    }

    public void setWordUserIdIndex(String strWordIndex){

        this.strWordUserIdIndex = strWordIndex;

    }

    public void setImageNumber(String strImageNumber){

        this.strImageNumber = strImageNumber;

    }

    public String getImageNumber(){
        return this.strImageNumber;
    }

    public void setBoxNumber(String strLeitnerBoxNum){

        this.strLeitnerBoxNum = strLeitnerBoxNum;

    }

    public String getBoxNumber(){
        return this.strLeitnerBoxNum;
    }

    public void setDate(String strReadDate){

        this.strReadDate = strReadDate;

    }

    public String getDate(){
        return this.strReadDate;
    }

    //endregion 1


    //region 2.Methods and Functions

    public String getAllWordsAsCsvLine() {

        String strAllWordsAsCsvFile = DEF_VALUE_STR;

        strAllWordsAsCsvFile =  this.strWordUserIdIndex +   SPLIT_CHAR_GERMAN_ENGLISH;
        strAllWordsAsCsvFile += this.strGermanWord      +   SPLIT_CHAR_GERMAN_ENGLISH;
        strAllWordsAsCsvFile += this.strEnglishWord     +   SPLIT_CHAR_GERMAN_ENGLISH;
        strAllWordsAsCsvFile += this.strImageNumber     +   SPLIT_CHAR_GERMAN_ENGLISH;
        strAllWordsAsCsvFile += this.strLeitnerBoxNum   +   SPLIT_CHAR_GERMAN_ENGLISH;
        strAllWordsAsCsvFile += this.strReadDate;

        return strAllWordsAsCsvFile;
    }

    public void setAllAttributesAsCsvLine(String strCsvLine) {
        String[] strAllAttributes = strCsvLine.split(SPLIT_CHAR_GERMAN_ENGLISH);

        this.strWordUserIdIndex =   strAllAttributes[SPLIT_INDEX_WORD];
        this.strGermanWord      =   strAllAttributes[SPLIT_INDEX_GERMAN_WORD];
        this.strEnglishWord     =   strAllAttributes[SPLIT_INDEX_ENGLISH_WORD];
        this.strImageNumber     =   strAllAttributes[SPLIT_INDEX_IMAGE_NUMBER];
        this.strLeitnerBoxNum   =   strAllAttributes[SPLIT_INDEX_LEITNER_BOX_NUM];
        this.strReadDate        =   strAllAttributes[SPLIT_INDEX_DATE];
    }
    //endregion 2
}
