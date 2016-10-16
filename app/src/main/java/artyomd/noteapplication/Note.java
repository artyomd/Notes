package artyomd.noteapplication;

/**
 * Created by artyomd on 3/20/16.
 */
public class Note {
    String s;
    Boolean state;

    Note() {
        s = "";
        state = true;
    }

    Note(String s) {
        this.s = s;
        state = true;
    }

    public void setString(String s) {
        this.s = s;
    }

    public void setState(Boolean bool) {
        this.state = bool;
    }

    public boolean getState(){
        return this.state;
    }

    public String getString(){
        return this.s;
    }
}
