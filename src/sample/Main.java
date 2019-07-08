package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main extends Application {
    Logic l = new Logic();
    XYChart.Series series = new XYChart.Series();
    private ExecutorService executor;

    public void init(Stage primaryStage){
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("String");

        NumberAxis xAxis = new NumberAxis(-1, 7, 10);
        xAxis.setLabel("x");
        NumberAxis yAxis = new NumberAxis(-1.1, 1.1, 0.05);
        yAxis.setLabel("f(x)");

        setters(xAxis);
        setters(yAxis);

        series.setName("String vibration");

        LineChart linechart = new LineChart(xAxis, yAxis);
        //------------------
        setDataIntoSeries();
        //------------------
        linechart.getData().add(series);

        Group root = new Group(linechart);
        primaryStage.setScene(new Scene(root, 600, 500));
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        init(primaryStage);
        primaryStage.show();


        executor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });
        AddToQueue addToQueue = new AddToQueue();
        executor.execute(addToQueue);
        //-- Prepare Timeline
        prepareTimeline();
    }

    public void setDataIntoSeries(){
        series.getData().clear();
        for(int i = 0; i<l.x.length; ++i){
            System.out.println(l.x.length);
            series.getData().add(new XYChart.Data(l.x[i], l.f[i]));
        }
    }

    private class AddToQueue implements Runnable {
        public void run() {
            try {
                // add a item of data to queue
                l.move();
                Thread.sleep(100);
                executor.execute(this);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void prepareTimeline() {
        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                setDataIntoSeries();
            }
        }.start();
    }

    public void setters(NumberAxis axis){
        axis.setForceZeroInRange(false);
        axis.setAutoRanging(false);
        axis.setTickLabelsVisible(false);
        axis.setTickMarkVisible(false);
        axis.setMinorTickVisible(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
