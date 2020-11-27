package com.practica.bank.runnables;

import com.practica.bank.models.Bankomat;

public abstract class BankomatsRunnable implements Runnable {
    @Override
    public void run() {

    }
    public abstract void run(Bankomat[] bankomats);
}
