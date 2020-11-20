package com.skxrb1ud.bank.runnables;

import com.skxrb1ud.bank.models.Bankomat;

public abstract class BankomatsRunnable implements Runnable {
    @Override
    public void run() {

    }
    public abstract void run(Bankomat[] bankomats);
}
