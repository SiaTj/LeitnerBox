package com.siamiri.logic.database;


import com.siamiri.Model.word.Word;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.List;

/**
 * Thread-safe access to the database
 */
public class DbManger {

    //region 0. Constants
    private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";

    private static final String DB_LOCAL_SERVER_IP_ADDRESS = "192.168.64.2";
    private static final String DB_LOCAL_NAME              = "/WordList";

    private static final String DB_LOCAL_CONNECTION_URL =
            "jdbc:mariadb://" + DB_LOCAL_SERVER_IP_ADDRESS + DB_LOCAL_NAME;

    private static final String DB_LOCAL_USER_NAME = "root";
    private static final String DB_LOCAL_USER_PW   = "";
    //endregion

    //region 1. Decl. and Init Attribute
    private static DbManger instance;
    /**
     * Access to the database tab
     */
    private        DaoWords daoWord;
    //endregion

    //region 2. singleton

    private DbManger() {
        this.daoWord = new DaoWords();
    }

    public static synchronized DbManger getInstance() {
        if (instance == null) {
            instance = new DbManger();
        }

        return instance;
    }
    //endregion

    //region 4. Database Connection

    /**
     * Returns a generated database connection
     * with read as well as write permissions
     * or null if something went wrong
     *
     * @return rwDbConnection : {@link Connection} : connection to data bank with rw
     */
    private Connection getRwDbConnection() throws Exception {

        Connection rwDbConnection = null;

        try {
            // register the  JDBC driver
            Class.forName(JDBC_DRIVER);

            // open a connection
            rwDbConnection = DriverManager.getConnection(DB_LOCAL_CONNECTION_URL, DB_LOCAL_USER_NAME, DB_LOCAL_USER_PW);

        } catch (SQLNonTransientConnectionException sqlNoConnectionEx) {
            throw new Exception("NO connection to data bank=> check xamp or mysql service expires");
        } catch (ClassNotFoundException classNotFoundEx) {
            throw new Exception("JDBC driver could not be loaded => mariadb-connector maybe not added");
        }

        return rwDbConnection;
    }


    /**
     * Check if the data bakn is online
     *
     * @return isOnline : boolean : true : Data bank is online : false not
     */
    public boolean isDatabaseOnline() {
        boolean isOnline = true;
        try {
            this.getRwDbConnection().close();
        } catch (Exception e) {
            isOnline = false;
        }
        return isOnline;
    }
    //endregion


    //region 5. CRUD -Operations Word

    /**
     * Inserting a single {@link Word}s in to the Datenbank
     *
     * @param wordToInsert : {@link Word} : to add to the data bank
     */
    public void insertWordIntoDbTbl(Word wordToInsert) {

        // make new connection
        try {
            if (this.isDatabaseOnline()) {
                this.daoWord.insertDataRecordIntoDbTbl(this.getRwDbConnection(), wordToInsert);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


    }

    /**
     * more than one {@link Word}s in Datenbank
     *
     * @param wordToInsert : {@link List} - {@link Word} : Word to add to the data bank list
     */
    public void insertWordsIntoDbTbl(List<Word> wordToInsert) {

        try {
            if (this.isDatabaseOnline()) {
                this.daoWord.insertDataRecordsIntoDbTbl(this.getRwDbConnection(), wordToInsert);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }



    /**
     * List all data as word list
     *
     * @return allWordsFromDb : {@link List} - {@link Word}: all words from data bank
     */
    public List<Word> getAllWordsFromDb() {

        //make nnew connection

        List<Word> allWordFromDb = new ArrayList<>();

        try {
            if (this.isDatabaseOnline()) {
                allWordFromDb = this.daoWord.getAllDataRecordsFromDbTbl(this.getRwDbConnection());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return allWordFromDb;
    }

    /**
     * Reads a specified note from the database.
     * @param iId : int : Id os the user that should be read
     * @return specificWordFromDbById : {@link Word} : Read out word or an empty object
     * the note should not be found.
     */

    public Word getWordById(int iId) {

        // new connection
        Word specificWordFromDbById = new Word();

        try {
            if (this.isDatabaseOnline()) {
                specificWordFromDbById = this.daoWord.getSpecificDataRecordFromDbTblById(this.getRwDbConnection(),iId);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return specificWordFromDbById;
    }


    /**
     * update the {@link Word} in data bank.
     *
     * @param wordToUpdate : {@link Word} : word that should be uptaed
     */
    public void updateWordInDbTbl(Word wordToUpdate) {

        // new connection
        try {
            if (this.isDatabaseOnline()) {
                this.daoWord.updateDataRecordIntoDbTbl(this.getRwDbConnection(), wordToUpdate);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * update the give words
     *
     * @param wordToUpdate : {@link List} - {@link Word} : words that shoud be change in the data bank
     */
    public void updateWordsInDbTbl(List<Word> wordToUpdate) {
        //Neue Verbindung erstellen
        try {
            if (this.isDatabaseOnline()) {
                this.daoWord.updateDataRecordsIntoDbTbl(this.getRwDbConnection(), wordToUpdate);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * delete {@link Word} from data bank
     *
     * @param iId : int : Id of user {@link Word} that want to be deleted
     */
    public void deleteWordInDbTblById(int iId) {

        //new connection
        try {
            if (this.isDatabaseOnline()) {
                this.daoWord.deleteDataRecordInDbTblById(this.getRwDbConnection(), iId);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    //endregion
}
