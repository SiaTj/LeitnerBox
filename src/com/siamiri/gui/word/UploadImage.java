package com.siamiri.gui.word;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;


public class UploadImage implements Initializable {

    String strLastFourDigits = ">ThereIsNoPath<";
    Stage kiri ;
    Stage tokhmi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            chooseImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void chooseImage() throws IOException {

        final FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));

        File file = fileChooser.showOpenDialog(kiri);


        // File outputfile = new File("image.jpg");
        // ImageIO.write(file, "jpg", outputfile);

        String strImgPath = file.getPath();

        if (file != null) {

            strLastFourDigits = strImgPath.substring(strImgPath.length() - 3);
            String strPath = "/resource/images";

            //Image image = new Image(file.toURI().toString());

            ImageView imageView = new ImageView();
            ImageIO.read(new File(strImgPath));
            File file1 = new File(strPath);

            try {
                ImageIO.write(ImageIO.read(new File(strImgPath)), "jpg", file1);
            } catch (IOException ex) {
                Logger.getLogger( UploadImage.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
