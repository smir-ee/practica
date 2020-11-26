package com.skxrb1ud.bank;

import java.util.ArrayList;

public abstract class BanksCallback implements Runnable {
    @Override
    public void run() { }
    public abstract void run(ArrayList<Banks> banks);
}
