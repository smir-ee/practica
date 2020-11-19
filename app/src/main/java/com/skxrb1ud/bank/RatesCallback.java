package com.skxrb1ud.bank;

import java.util.ArrayList;

public abstract class RatesCallback implements Runnable {
    @Override
    public void run() { }
    public abstract void run(ArrayList<Rate> rates);
}
