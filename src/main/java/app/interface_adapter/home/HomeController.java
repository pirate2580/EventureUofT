package app.interface_adapter.home;

import app.use_case.home.HomeInputBoundary;

public class HomeController {
    private final HomeInputBoundary homeUseCaseInteractor;
    public HomeController(HomeInputBoundary homeUseCaseInteractor) {
        this.homeUseCaseInteractor = homeUseCaseInteractor;
    }

}
