package java.interface_adapter;

/**
 * Model for the View Manager. Its state is the name of the View which
 * is currently active. An initial state of "" is used.
 * TODO: Naoroj: This is copied from lab-5, I don't fully understand its purpose
 */
public class ViewManagerModel extends ViewModel<String> {

    public ViewManagerModel() {
        super("view manager");
        this.setState("");
    }

}