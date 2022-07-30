package com.siamiri.logic.userCsvFileHandler;

import com.siamiri.Model.user.User;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
*
*
* TODO 1
* TODO 2
*/

public class UserCsvFileHandler {

    //region 0. constant
    /**
     * The final address and variable will be define here
     * as well
     */
    private static final String USERS_CSV_FILE_PATH  = "src/com/siamiri/resource/usersData/users.csv";
    private static final String USERS_TEXT_FILE_PATH  = "src/com/siamiri/resource/usersData/users.txt";

    //endregion 0

    //region 1. Decl. and Init Attribute
    private static UserCsvFileHandler instance;
    //endregion 1

    //region 2. Constructor

    public UserCsvFileHandler() {
        System.out.println("User File Handling started.....!");
    }


    public static synchronized UserCsvFileHandler getInstance() {
        if (instance == null) {
            instance = new UserCsvFileHandler();
        }
        return instance;
    }
    //endregion 2


    //region 3. Read and Save User data to CSV file

    public void saveUsersToCsvFile(List<User> userToSave) {

        File userCsvFile = new File(USERS_CSV_FILE_PATH);
        File userTxtFile = new File(USERS_TEXT_FILE_PATH);


        FileOutputStream fos = null;
        FileOutputStream fosTxt = null;

        OutputStreamWriter osw = null;
        OutputStreamWriter oswTxt = null;


        BufferedWriter out = null;
        BufferedWriter outTxt = null;


        try {

            if (!userCsvFile.exists()) {

                userCsvFile.createNewFile();
            }

            if (!userTxtFile.exists()) {

                userTxtFile.createNewFile();
            }

            fos = new FileOutputStream(userCsvFile);
            fosTxt = new FileOutputStream(userTxtFile);


            osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            oswTxt = new OutputStreamWriter(fosTxt, StandardCharsets.UTF_8);


            out = new BufferedWriter(osw);
            outTxt = new BufferedWriter(oswTxt);


            for (User u : userToSave) {

                out.write(u.getUserAttributesAsCsvLine() + "\n");
                outTxt.write(u.getUserAttributesAsCsvLine() + "\n");

            }


        } catch (Exception e) {
            System.err.println(USERS_CSV_FILE_PATH);
            e.printStackTrace();
        } finally {

            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<User> readUsersFromCsvFile() {

        List<User> usersFromCsvFile = new ArrayList<>();

        File userCsvFile = new File(USERS_CSV_FILE_PATH);

        FileInputStream fis = null;

        InputStreamReader isr = null;

        BufferedReader in = null;

        try {
            if (userCsvFile.exists()) {

                fis = new FileInputStream(userCsvFile);

                isr = new InputStreamReader(fis, StandardCharsets.UTF_8);

                in = new BufferedReader(isr);

                boolean eof = false;

                while (!eof) {

                    String strReadCsvLine = in.readLine();

                    if (strReadCsvLine == null) {
                        eof = true;
                    } else {

                        User currentCurrentFromFile = new User();

                        currentCurrentFromFile.setUserAttributesFromCsvLine(strReadCsvLine);

                        usersFromCsvFile.add(currentCurrentFromFile);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println(USERS_CSV_FILE_PATH);
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return usersFromCsvFile;
    }

    //endregion
}
