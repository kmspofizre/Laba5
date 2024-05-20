package commands;

public class Register extends Command implements UserRegisterCommand{
    public Register(){
        super("register", "des", false, false);
    }
}
