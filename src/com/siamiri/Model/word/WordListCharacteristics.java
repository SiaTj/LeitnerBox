package com.siamiri.Model.word;

/**
 * TODO.1
 * TODO.2
 */

public abstract class WordListCharacteristics extends Object {

    //region 0.Constants
    protected static final String   DEF_VALUE_STR               = ">THERE IS NO WORD!<";
    protected static final String   DEF_VALUE_INT               = "-1";
    protected static final String   SPLIT_CHAR_GERMAN_ENGLISH   = ";";
    protected static final String   SPLIT_CHAR_WORDS            = " ";
    protected static final int      SPLIT_INDEX_WORD            = 0;
    protected static final int      SPLIT_INDEX_GERMAN_WORD     = 1;
    protected static final int      SPLIT_INDEX_ENGLISH_WORD    = 2;
    protected static final int      SPLIT_INDEX_IMAGE_NUMBER    = 3;
    protected static final int      SPLIT_INDEX_LEITNER_BOX_NUM = 4;
    protected static final int      SPLIT_INDEX_DATE            = 5;
    //endregion 0


    //region 1.Declaration and initialization of attributes
    protected   String strWordUserIdIndex;
    protected   String strGermanWord;
    protected   String  strEnglishWord;
    protected   String  strImageNumber;
    protected   String  strReadDate;
    protected   String  strLeitnerBoxNum;


    //region 2.Constructor
    /**
     * This is standard constructor that
     * generate the object
     * <p>
     *     This class is the abstract for the
     *     word class. It is also extend the Object
     *     initialized all the needed
     *     attribute for the word sub class
     * </p>
     */
    protected WordListCharacteristics(){
        this.strWordUserIdIndex =   DEF_VALUE_INT;
        this.strGermanWord      =   DEF_VALUE_STR;
        this.strEnglishWord     =   DEF_VALUE_STR;
        this.strImageNumber     =   DEF_VALUE_INT;
        this.strReadDate        =   DEF_VALUE_INT;
        this.strLeitnerBoxNum   =   DEF_VALUE_INT;
    }
    //endregion 2

}
