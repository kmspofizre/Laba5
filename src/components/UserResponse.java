package components;

public class UserResponse extends Response{
    private boolean success;
    private User user;
    public UserResponse(String reponseString, boolean success, User user) {
        super(reponseString);
        this.success = success;
        this.user = user;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
