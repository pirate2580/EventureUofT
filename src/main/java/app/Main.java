package app;

import app.view.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.JFrame;

/**
 * The Main class of our application.
 */
@SpringBootApplication(scanBasePackages = {"app", "app.config"})
public class Main {

    /**
     * Builds and runs CA architecture of the application.
     *
     * @param args unused argument
     */
    public static void main(String[] args) {

        // check if running in headless mode
        if (java.awt.GraphicsEnvironment.isHeadless()) {
            System.out.println("Headless environment detected. GUI will not be initialized.");
            return;
        }

        // start the Spring Boot application context
        var context = SpringApplication.run(Main.class, args);

        // get AppBuilder bean from the Spring context
        AppBuilder appBuilder = context.getBean(AppBuilder.class);

        // TODO: ORDER OF BUILD MATTERS, APPLICATION MAY BREAK IF ORDER NOT FOLLOWED
        final JFrame application = appBuilder
                .addRegisterView()
                .addLoginView()
                .addCreateEventView()
                .addHomeView()
                .addViewRSVPView()
                .addViewRSVPUseCase()
                .addFilterEventView()
                .addViewEventView()
                .addRegisterUseCase()
                .addLoginUseCase()
                .addCreateEventUseCase()
                .addFilterEventUseCase()
                .addViewEventUseCase()
                .addHomeUseCase()
                .build();

        application.pack();
        application.setVisible(true);
    }
}
