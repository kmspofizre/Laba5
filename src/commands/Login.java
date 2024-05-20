package commands;

public class Login extends Command implements UserRegisterCommand{
    public Login(){
        super("login", "des", false, false);
    }
}
