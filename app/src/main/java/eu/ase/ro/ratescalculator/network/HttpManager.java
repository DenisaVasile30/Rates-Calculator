package eu.ase.ro.ratescalculator.network;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import javax.net.ssl.HttpsURLConnection;

public class HttpManager implements Callable<String> {

    private URL url;
    private HttpsURLConnection connection;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private final String urlAddress;

    public HttpManager(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    @Override
    public String call() {
        try {
            return getContentFromHttp();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }

    private void closeConnections() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.disconnect();
    }

    @NonNull
    private String getContentFromHttp() throws IOException {
        url = new URL(urlAddress);
        connection = (HttpsURLConnection) url.openConnection();
        inputStream = connection.getInputStream();
        inputStreamReader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder result = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        //result = "{\"Products\":{\"Deposits\":{\"BankName\":{\"ING\":{\"Interest\":{\"MinimumInterest\":\"0.50\",\"AverageInterest\":\"3.8\",\"MaximumInterest\":\"7.5\"},\"ContactNo\":\"0210456323\",\"Email\":\"contact@ing.com\"},\"BCR\":{\"Interest\":{\"MinimumInterest\":\"0.45\",\"AverageInterest\":\"3.48\",\"MaximumInterest\":\"6.88\"},\"ContactNo\":\"0210878245\",\"Email\":\"contact@bcr.com\"},\"BRD\":{\"Interest\":{\"MinimumInterest\":\"0.62\",\"AverageInterest\":\"4.12\",\"MaximumInterest\":\"6.75\"},\"ContactNo\":\"0210878259\",\"Email\":\"contact@brd.com\"}},\"Period\":{\"MinumumPeriod\":\"1 month\",\"AveragePeriod\":\"12 months\",\"MaximumPeriod\":\"60 months\"},\"MinimumAmount\":\"1.000\",\"MaximumAmount\":\"100.000\"},\"Credits\":{\"BankName\":{\"ING\":{\"Interest\":{\"MinimumInterest\":\"8.99\",\"AverageInterest\":\"9.45\",\"MaximumInterest\":\"13.54\"},\"ContactNo\":\"0210456323\",\"Email\":\"contact@ing.com\"},\"BCR\":{\"Interest\":{\"MinimumInterest\":\"9.86\",\"AverageInterest\":\"11.2\",\"MaximumInterest\":\"15.68\"},\"ContactNo\":\"0210878245\",\"Email\":\"contact@bcr.com\"},\"BRD\":{\"Interest\":{\"MinimumInterest\":\"9.54\",\"AverageInterest\":\"11.84\",\"MaximumInterest\":\"15.43\"},\"ContactNo\":\"0210878259\",\"Email\":\"contact@brd.com\"}},\"Period\":{\"MinumumPeriod\":\"12 months\",\"AveragePeriod\":\"36 months\",\"MaximumPeriod\":\"60 months\"},\"MinimumAmount\":\"1.000\",\"MaximumAmount\":\"100.000\"}}}";
        return result.toString();
    }
}
