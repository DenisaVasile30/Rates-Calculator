package eu.ase.ro.ratescalculator.asyncTask;

public interface Callback<R> {
    void runResultOnUiThread(R result);
}
