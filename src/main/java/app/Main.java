package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.JFrame;

/**
 * The Main class of our application
 */
@SpringBootApplication(scanBasePackages = {"app", "entity.User"})
public class Main {
    /**
     * Builds and runs CA architecture of application
     * @param args unused argument
     */
    public static void main(String[] args) {
        // start the Spring Boot application context
        var context = SpringApplication.run(Main.class, args);

        // get the AppBuilder bean from the Spring context
        AppBuilder appBuilder = context.getBean(AppBuilder.class);

        // build the application using AppBuilder
        final JFrame application = appBuilder.addRegisterView()
                .addRegisterUseCase()
                .build();

        // set up the application window
        application.pack();
        application.setVisible(true);

        System.out.println("1. Running Main!"); // testing
    }
}
