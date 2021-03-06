package cz.cvut.fit.tjv.social_network.web.model;

public class UserWebModel extends UserDTO{
    private boolean error;

    public UserWebModel() {
    }

    public UserWebModel(boolean error, UserDTO userDto) {
        super(userDto.getName(), userDto.getDateOfBirth(), userDto.getId());
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
