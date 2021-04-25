package sb.rf.generalchat.encryption;

public interface Ecnrypt {
    public String encrypt(String password);
    public boolean check(String password,String hachedPassword);
}
