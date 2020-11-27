package com.practica.bank.runnables;

import com.practica.bank.models.Currency;

public abstract class CurrenciesRunnable implements Runnable {
    @Override
    public void run() {

    }
    public abstract void run(Currency[] currencies);
}
