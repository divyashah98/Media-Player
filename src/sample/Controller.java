package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    MediaView mv;
    @FXML
    Media media;
    @FXML
    MediaPlayer mp;
    @FXML Slider volumeslider;


    @Override
    public void initialize(URL locate, ResourceBundle resources) {
        String path = new File("src/sample.mp4").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(media);

        mv.setMediaPlayer(mp);
        mp.setAutoPlay(false);

        DoubleProperty width = mv.fitWidthProperty();
        DoubleProperty height = mv.fitHeightProperty();

        width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));

        volumeslider.setValue(mp.getVolume()*100);
        volumeslider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mp.setVolume(volumeslider.getValue()/100);
            }
        });


    }

    public void play() {

        mp.play();

        System.out.println("media play started");
    }

    public void stop() {
        mp.stop();
        System.out.println("media play stoped");
    }

    public void pause() {
        mp.pause();
        System.out.println("media play paused");
    }

    public void reload() {
        mp.seek(mp.getStartTime());
        mp.play();
        System.out.println("media play reloaded");
    }

    public void pickfile() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP4", "*.mp4"));
        File video = fc.showOpenDialog(null);
        String fileName = video.getPath();
        if (video != null) {
            System.out.println("LOG :: SELECTED FILE : " + fileName);
            mp.dispose();
            media = new Media(new File(video.getAbsolutePath()).toURI().toString());
            mp = new MediaPlayer(media);
            mv.setMediaPlayer(mp);
            mp.play();

        }


    }

}
