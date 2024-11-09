package app;

import javax.swing.JFrame;

/**
 * The Main class of our application
 */
public class Main {
    /**
     * Builds and runs CA architecture of application
     * @param args unused argument
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();

        // TODO build the application with AppBuilder class
        final JFrame application = appBuilder.addRegisterView()
                .addRegisterUseCase()
                .build(); // Add this if required by your setup

         application.pack();
         application.setVisible(true);

        System.out.println("1. Running Main!");
    }
}
