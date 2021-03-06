import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ParticlesApp extends Application {

    private Emitter emitter = new FireEmitter();

    private List<Particle> particles = new ArrayList<>();

    private GraphicsContext g;

    private void onUpdate(){
        g.setGlobalAlpha(1.0);
        g.setGlobalBlendMode(BlendMode.SRC_OVER);
        g.setFill(Color.BLACK);
        g.fillRect(0,0,600,600);

        particles.addAll(emitter.emit(300, 300));
        for(Iterator<Particle> it = particles.iterator(); it.hasNext();){
            Particle p = it.next();
            p.update();

            if(!p.isAlive()) {
                it.remove();
                continue;
            }

            p.render(g);
        }
    }

    private Parent createContent(){
        Pane root = new Pane();
        root.setPrefSize(600, 600);

        Canvas canvas = new Canvas(600, 600);
        g = canvas.getGraphicsContext2D();


        root.getChildren().add(canvas);
        return root;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                onUpdate();
            }
        };
        timer.start();
    }

    public static void main(String[] args){
        System.setProperty("quantum.multithreaded", "false");
        Application.launch(ParticlesApp.class, args);
    }
}
