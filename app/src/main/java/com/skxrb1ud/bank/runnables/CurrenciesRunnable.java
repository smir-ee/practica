package com.skxrb1ud.bank.runnables;

import com.skxrb1ud.bank.models.Currency;

public abstract class CurrenciesRunnable implements Runnable {
    @Override
    public void run() {

    }
    public abstract void run(Currency[] currencies);
}
