package sample.Calculations;

import java.util.ArrayList;

/**
 * Wrapper class should represent data about movement of sprite in next period of time
 */
public class DataPackage {
    private ArrayList<Double> tValues;
    private ArrayList<Double> hValues;
    private ArrayList<Double> vValues;
    private ArrayList<Double> mValues;

    public DataPackage(ArrayList<Double> tValues, ArrayList<Double> hValues, ArrayList<Double> vValues, ArrayList<Double> mValues) {
        this.tValues = tValues;
        this.hValues = hValues;
        this.vValues = vValues;
        this.mValues = mValues;
    }

    public ArrayList<Double> gettValues() {
        return tValues;
    }

    public ArrayList<Double> gethValues() {
        return hValues;
    }

    public ArrayList<Double> getvValues() {
        return vValues;
    }

    public ArrayList<Double> getmValues() {
        return mValues;
    }
}
