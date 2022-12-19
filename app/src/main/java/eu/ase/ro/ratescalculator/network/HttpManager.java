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

        return result.toString();
    }
}
