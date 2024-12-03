package app.interface_adapter.home;

import app.interface_adapter.ViewModel;

/**
 * ViewModel for the Home view.
 * This class manages the state for the Home feature and interacts with the corresponding view.
 * It extends the ViewModel class with HomeState as its state.
 * Further functionality or fields may need to be added as the application develops.
 */
public class HomeViewModel extends ViewModel<HomeState> {
    public HomeViewModel() {
        super("Home");
        setState(new HomeState());
    }
}
