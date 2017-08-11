import com.elixer.core.ElixerGame;
import com.elixer.core.Util.Logger;

/**
 * Created by aweso on 7/27/2017.
 */
public class Main extends ElixerGame {

    public static void main(String[] args) {
        Main game = new Main();
        game.start();
    }

    public Main() {
        super("test");
    }

    @Override
    protected void onPreInit() {
        Logger.println("TEST");
    }

    @Override
    protected void onPostInit() {

    }

    @Override
    protected void onInit() {

    }

    @Override
    protected void onEnd() {

    }
}
