package com.siamiri.gui.word;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowImage implements Initializable {

    @FXML
    private ImageView ivImageView;


    final private static double IMAGE_WIDTH   = 400D;
    final private static double IMAGE_HEIGHT  = 400D;

    public String strImageName  = "No image is yet set to this word";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        matchImageToWord();
    }


    public void matchImageToWord() {

/*

        Image imgGetNull    = new Image("/com/siamiri/resource/images/imageNull.jpg");
        Image imgGet        = new Image("/com/siamiri/resource/images/image%s"+ strImgToShowIndex +".jpg");

        ivImageView.setFitWidth(IMAGE_WIDTH);
        ivImageView.setFitHeight(IMAGE_HEIGHT);
        ivImageView.resize(IMAGE_HEIGHT, IMAGE_WIDTH);

        if(imgGet != null) {

            ivImageView.setImage(imgGet);

        }else {

            ivImageView.setImage(imgGetNull);

        }

    }

 */
    }
}
