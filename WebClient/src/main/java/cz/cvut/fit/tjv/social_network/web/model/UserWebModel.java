package cz.cvut.fit.tjv.social_network.web.model;

public class UserWebModel extends UserDTO{
    private boolean usernameError;

    public UserWebModel() {
    }

    public UserWebModel(boolean usernameError, UserDTO userDto) {
        super(userDto.getName(), userDto.getDateOfBirth(), userDto.getId());
        this.usernameError = usernameError;
    }

    public boolean isUsernameError() {
        return usernameError;
    }

    public void setUsernameError(boolean usernameError) {
        this.usernameError = usernameError;
    }
}
