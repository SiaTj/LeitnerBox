package com.siamiri.logic.database;

/**
 * This class is used to provide all
 * required SQL keywords encapsulated
 * and centrally.
 *
 * Created by sMirifar
 */

public abstract class ASqlKeyWords {

    //region 0. Data type
    protected static final String DATA_TYPE_INTEGER           = " INTEGER ";

    protected static final int DATA_TYPE_BOOLEAN_FALSE = 0;
    protected static final int DATA_TYPE_BOOLEAN_TRUE  = 1;
    //endregion

    //region 1. table actions
    protected static final String SELECT_INC_BLANK      = "SELECT ";
    protected static final String FROM_INC_BLANKS       = " FROM ";
    protected static final String SELECT_ALL_DATA_FROM  = SELECT_INC_BLANK + "*" + FROM_INC_BLANKS;
    protected static final String INSERT_TBL      = "INSERT INTO ";
    protected static final String UPDATE_TBL      = "UPDATE ";
    protected static final String DELETE_FROM_TBL = "DELETE" + FROM_INC_BLANKS;

    //endregion

    //region operators
    protected static final String SET_OPERATOR                     = " SET ";
    protected static final String VALUES_OPERATOR                  = " VALUES ";
    protected static final String EQUALS_OPERATOR                  = " = ";
    //endregion

    //region conditions
    protected static final String WHERE_CONDITION = " WHERE ";

    //endregion

    //region chars
    protected static final String CHAR_COMMA                   = ", ";
    protected static final String CHAR_COL_BACK_TICK           = "`";
    protected static final String CHAR_VALUE_BACK_TICK         = "'";
    protected static final String CHAR_SEMICOLON               = ";";
    protected static final String CHAR_OPEN_BRACKET            = "(";
    protected static final String CHAR_CLOSE_BRACKET           = ")";
    protected static final String CHAR_CLOSE_BRACKET_SEMICOLON = CHAR_CLOSE_BRACKET + CHAR_SEMICOLON;

    //endregion
}
