package Markets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchData {

    // Fetch data from target url

    public static String pull(String url) {

        try {
            URL link = new URL(url);
            HttpURLConnection con = (HttpURLConnection) link.openConnection();
            con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder str = new StringBuilder();
            String tmp;

            while ((tmp = br.readLine()) != null) {
                str.append(tmp);
            }

            return str.toString();
        } catch (Exception e) {return null;}

    }


}
