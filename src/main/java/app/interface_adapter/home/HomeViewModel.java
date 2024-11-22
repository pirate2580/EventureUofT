package app.interface_adapter.home;

import app.interface_adapter.ViewModel;

public class HomeViewModel extends ViewModel<HomeState> {
    public HomeViewModel() {
        super("Home");
        setState(new HomeState());
    }
}
