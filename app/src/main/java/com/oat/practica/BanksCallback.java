package com.oat.practica;

import java.util.ArrayList;

public abstract class BanksCallback implements Runnable {
    @Override
    public void run() { }
    public abstract void run(ArrayList<Bank> banks);
}
