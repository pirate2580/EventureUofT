package app.interface_adapter.modify_user;

public class ModifyUserState {
    private String usernameState;

    private String getUsernameState() {return usernameState;}

    private void setUsernameState(String usernameState) { this.usernameState = usernameState;}
}