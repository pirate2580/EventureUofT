package app;

import app.view.LoginView;
import app.view.RegisterView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.JFrame;

/**
 * The Main class of our application
 */
// TODO: Fix label for sign-up and log-in buttons so its on the left
@SpringBootApplication(scanBasePackages = {"app", "app.config"})
public class Main {
    /**
     * Builds and runs CA architecture of application
     * @param args unused argument
     */
    public static void main(String[] args) {

        // Check if running in headless mode
        if (java.awt.GraphicsEnvironment.isHeadless()) {
            System.out.println("Headless environment detected. GUI will not be initialized.");
            return;
        }

        // start the Spring Boot application context
        var context = SpringApplication.run(Main.class, args);

        // get the AppBuilder bean from the Spring context
        AppBuilder appBuilder = context.getBean(AppBuilder.class);

        // create the RegisterView and LoginView
        RegisterView registerView = appBuilder.addRegisterView(); // Add the RegisterView to the app
        appBuilder.addRegisterUseCase(); // Add the use case logic for RegisterView
        LoginView loginView = appBuilder.addLoginView(); // Add the LoginView to the app

        // set parent panels for navigation
        registerView.setParentPanel(appBuilder.getCardPanel());
        loginView.setParentPanel(appBuilder.getCardPanel());

        // build the application
        final JFrame application = appBuilder.build();

        // set up the application window
        if (application != null) {
            application.pack();
            application.setVisible(true);
        } else {
            System.out.println("Application is running in headless mode. No GUI will be displayed.");
        }

        System.out.println("1. Running Main!"); // testing
    }
}
