package Client.Controller.Pages2;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class TimerController extends VBox implements Initializable {
    public TimerController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainPanel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private AnchorPane timerPanel;

    @FXML
    private Text hoursTimer;

    @FXML
    private Text minuteTimer;

    @FXML
    private Text secondTimer;

    @FXML
    private Button cancelButton;

    @FXML
    private AnchorPane menuPanel;

    @FXML
    private ComboBox<Integer> hoursInput;

    @FXML
    private ComboBox<Integer> minutesInput;

    @FXML
    private ComboBox<Integer> secondsInput;

    @FXML
    private Button buttonStart;

    Map<Integer,String> numberMap;
    Integer currSeconds;
    Thread trd;
    private Integer hasToSeconds(Integer h,Integer m,Integer s){
        Integer hToSeconds=h*3600;
        Integer mToSeconds=m*60;
        Integer total= hToSeconds+mToSeconds+s;
        return total;
    }//
    private LinkedList<Integer> secondsToHms(Integer currSecond){
        Integer hours= currSecond/3600;
        currSecond= currSecond%3600;
        Integer minute= currSecond/60;
        currSecond= currSecond%60;
        Integer second=currSecond;
        LinkedList<Integer> answer=new LinkedList<>();
        answer.add(hours);
        answer.add(minute);
        answer.add(second);
        return answer;

    }//
    public void setOutput(){
        LinkedList<Integer> currHms=secondsToHms(currSeconds);
        hoursTimer.setText(numberMap.get(currHms.get(0)));
        minuteTimer.setText(numberMap.get(currHms.get(1)));
        secondTimer.setText(numberMap.get(currHms.get(2)));
    }//

    @SuppressWarnings("deprecation")
    private void startCountdown(){
        trd=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                while(true){

                        setOutput();
                        Thread.sleep(1000);
                        if (currSeconds==0){
                            System.out.println("Finished");
                            scrollDown();
                            trd.stop();
                        }
                        currSeconds-=1;
                    }
                }catch (InterruptedException e){
                    trd.stop();
                }
            }
        });
        trd.start();
    }

    @FXML
    void start(ActionEvent event) {
        currSeconds= hasToSeconds(hoursInput.getValue(),minutesInput.getValue(),secondsInput.getValue());
        hoursInput.setValue(0);
        minutesInput.setValue(0);
        secondsInput.setValue(0);
        scrollUp();
    }//

    @SuppressWarnings("deprecation")
    @FXML
    void unStart(ActionEvent event) {
        trd.stop();
        scrollDown();
    }

    private void scrollUp(){
        TranslateTransition tr1 =new TranslateTransition();
        tr1.setDuration(Duration.millis(100));
        tr1.setToX(0);
        tr1.setToY(-200);
        tr1.setNode(menuPanel);
        TranslateTransition tr2 =new TranslateTransition();
        tr2.setDuration(Duration.millis(100));
        tr2.setFromX(0);
        tr2.setFromY(200);
        tr2.setToX(0);
        tr2.setToY(0);
        tr2.setNode(timerPanel);
        ParallelTransition pt= new ParallelTransition(tr1,tr2);
        pt.setOnFinished(event -> {
            System.out.println("Start countdown...");
            startCountdown();
        });
        pt.play();
    }
    @SuppressWarnings("deprecation")
    private void scrollDown(){
        TranslateTransition tr1 =new TranslateTransition();
        tr1.setDuration(Duration.millis(100));
        tr1.setToX(0);
        tr1.setToY(200);
        tr1.setNode(timerPanel);
        TranslateTransition tr2 =new TranslateTransition();
        tr2.setDuration(Duration.millis(100));
        tr2.setFromX(0);
        tr2.setFromY(-200);
        tr2.setToX(0);
        tr2.setToY(0);
        tr2.setNode(menuPanel);
        ParallelTransition pt= new ParallelTransition(tr1,tr2);
        pt.play();
    }//

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Integer> hoursList= FXCollections.observableArrayList();
        ObservableList<Integer> minutesAndSecondsList= FXCollections.observableArrayList();
        for (int i=0;i<60;i++){
            if(i<=24)
                hoursList.add(i);
            minutesAndSecondsList.add(i);
        }
        hoursInput.setItems(hoursList);
        hoursInput.setValue(0);
        minutesInput.setItems(minutesAndSecondsList);
        minutesInput.setValue(0);
        secondsInput.setItems(minutesAndSecondsList);
        secondsInput.setValue(0);

        numberMap=new TreeMap<>();
        for (Integer i=0; i<=60;i++){
            if (i>=0 && i<=9){
                numberMap.put(i,"0"+i.toString());
            }
            else
             numberMap.put(i,i.toString());
        }

    }//
}
