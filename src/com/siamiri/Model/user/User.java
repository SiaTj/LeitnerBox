package com.siamiri.Model.user;

import com.siamiri.Model.word.WordListCharacteristics;
import de.rhistel.Encrypter;

public class User extends WordListCharacteristics {

    //region 0. Constant
    private static final String DEF_VALUE_STR           = ">NO DATA IS SET YET<";
    private static final String SPLIT_CHAR              = ";";

    private static final int SPLIT_INDEX_USER_INDEX      = 0;
    private static final int SPLIT_INDEX_USERNAME        = 1;
    private static final int SPLIT_INDEX_USER_PW         = 2;

    //endregion

    //region 1. Delc and Init
    private     String  strUserPw;
    private     String  strName;
    private     String  strUserIndex;
    //endregion

    //region 2. Construction

    public User() {
        super();
        this.strUserIndex   = DEF_VALUE_STR;
        this.strName        = DEF_VALUE_STR;
        this.strUserPw      = DEF_VALUE_STR;

    }

    /**
     * @param strUserIndex  :       {@link String}      : User Index To manage Word list
     * @param strName       :       {@link String}      : Username
     * @param strUserPw     :       {@link String}      : User Password
     */
    public User(String strUserIndex, String strName, String strUserPw) {
        this.strUserIndex   =   strUserIndex;
        this.strName        =   strName;
        this.strUserPw      =   strUserPw;
    }

    //endregion

    //region 3. Getter / Setter
    public String getUserIndex(){
        return strUserIndex;
    }

    public void setUserIndex(String strUserIndex){
        this.strUserIndex = strUserIndex;
    }
    public String getUsername() {
        return strName;
    }

    public void setUsername(String strName) {
        this.strName = strName;
    }

    public String getUserPw() {
        return strUserPw;
    }

    public void setUserPw(String strUserPw) throws Exception {
        strUserPw = Encrypter.getInstance().encryptToSha256HashHexString(strUserPw);
        this.strUserPw = strUserPw;
    }

    //endregion 3.getter and setter

    // get User attributes as string
    public String getUserAttributesAsCsvLine() {

        //Decl and Init
        String strAllAttributesAsCsvLine = DEF_VALUE_STR;

        //Make Csv file string
        strAllAttributesAsCsvLine   =   this.strUserIndex + SPLIT_CHAR;
        strAllAttributesAsCsvLine   +=  this.strName + SPLIT_CHAR;
        strAllAttributesAsCsvLine   +=  this.strUserPw;

        return strAllAttributesAsCsvLine;
    }



    // set user data as String tube
    public void setUserAttributesFromCsvLine(String strCsvLine) {
        String[] strAllAttributes = strCsvLine.split(SPLIT_CHAR);

        this.strUserIndex   = strAllAttributes[SPLIT_INDEX_USER_INDEX];
        this.strName        = strAllAttributes[SPLIT_INDEX_USERNAME];
        this.strUserPw      = strAllAttributes[SPLIT_INDEX_USER_PW];
    }
    //endregion

}
