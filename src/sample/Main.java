package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {
    private Logic l = new Logic();
    private XYChart.Series<Double,Double> series = new XYChart.Series<>();
    private ExecutorService executor;

    private void init(Stage primaryStage){
        primaryStage.setTitle("String");

        NumberAxis xAxis = new NumberAxis(-1, 7, 10);
        xAxis.setLabel("x");
        NumberAxis yAxis = new NumberAxis(-1.1, 1.1, 0.05);
        yAxis.setLabel("f(x)");

        setters(xAxis);
        setters(yAxis);

        series.setName("String vibration");

        LineChart linechart = new LineChart<>(xAxis, yAxis);

        linechart.getData().add(series);

        Group root = new Group(linechart);
        primaryStage.setScene(new Scene(root, 600, 500));
    }

    @Override
    public void start(Stage primaryStage) {
        init(primaryStage);



        executor = Executors.newCachedThreadPool(r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
        AddToQueue addToQueue = new AddToQueue();
        executor.execute(addToQueue);
        //-- Prepare Timeline
        prepareTimeline();

        primaryStage.show();
    }

    private void setDataIntoSeries(){
        series.getData().clear();
        for(int i = 0; i<l.x.length; ++i){
            series.getData().add(new XYChart.Data<>(l.x[i], l.f[i]));
//            series.getData().clear();
        }
    }

    private class AddToQueue implements Runnable {
        public void run() {
            try {
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

    private void setters(NumberAxis axis){
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
