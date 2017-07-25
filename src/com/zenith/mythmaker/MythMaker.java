package com.zenith.mythmaker;

import com.elixer.core.ElixerGame;
import com.elixer.core.Util.Logger;

/**
 * Created by aweso on 7/20/2017.
 */
public class MythMaker extends ElixerGame{

    public static void main(String[] args) {
        MythMaker maker = new MythMaker();
        maker.start();
    }

    public MythMaker() {
        super("MythMaker");
    }

    @Override
    protected void onPreInit() {
        Logger.println("STARTING PRE-INIT");
    }

    @Override
    protected void onPostInit() {
        Logger.println("STARTING POST-INIT");
    }

    @Override
    protected void onInit() {
        Logger.println("STARTING INIT");
    }

    @Override
    protected void onEnd() {
        Logger.println("STARTING END");
    }
}
