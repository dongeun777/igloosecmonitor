package igloosec.monitor;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class HttpRequest {
    public boolean doGetHttps(String email, String company) {
        String urlString = "https://igloocld.com/userRegister?" +
                "email=" + email;
        if (company != null || company.equals("") == false) {
            try {
                company = URLEncoder.encode(company, "UTF-8");
            } catch (UnsupportedEncodingException ue) {
                ue.printStackTrace();
                return false;
            }
            urlString += "&company=" + company;
        }

        HttpsURLConnection httpsConn = null;
        try {
            // Get HTTPS URL connection
            URL url = new URL(urlString);
            httpsConn = (HttpsURLConnection) url.openConnection();

            // Set Hostname verification
            httpsConn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    // Ignore host name verification. It always returns true.
                    return true;
                }
            });

            // Input setting
            httpsConn.setDoInput(true);
            // Output setting
            // httpsConn.setDoOutput(true);
            // Caches setting
            httpsConn.setUseCaches(false);
            // Read Timeout Setting
            httpsConn.setReadTimeout(10000);
            // Connection Timeout setting
            httpsConn.setConnectTimeout(10000);
            // Method Setting(GET/POST)
            httpsConn.setRequestMethod("GET");
            // Header Setting
            //httpsConn.setRequestProperty("HeaderKey", "HeaderValue");
            int responseCode = httpsConn.getResponseCode();

            // Connect to host
            httpsConn.connect();
            httpsConn.setInstanceFollowRedirects(true);
            // Print response from host
            if (responseCode == HttpsURLConnection.HTTP_OK) { // 정상 호출 200
                return true;
            } else { // 에러 발생
                System.out.println("responseCode : " + responseCode);
                System.out.println("responseMessage : " + httpsConn.getResponseMessage());
                return false;
            }
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (httpsConn != null) {
                    httpsConn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

    }

    public boolean doGetHttp(String email, String company) {
        String urlString = "http://localhost:8080/userRegister?" +
                "email=" + email;
        if (company != null || company.equals("") == false) {
            try {
                company = URLEncoder.encode(company, "UTF-8");
            } catch (UnsupportedEncodingException ue) {
                ue.printStackTrace();
                return false;
            }
            urlString += "&company=" + company;
        }

        BufferedReader in = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET"); // optional default is GET

            int responseCode = con.getResponseCode();
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            return true;
        } catch(Exception e) {
            try {
                if (in != null) {
                    in.close();
                }
            } catch(IOException ie) {
                ie.printStackTrace();
            }
        }

        return true;
    }


    // HTTP POST request
    public String doPostHttp(String ip, String partitionName) throws Exception {

        URL url = new URL("http://" + ip + ":8983/solr/indexer.json");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST"); // HTTP POST 메소드 설정
        con.setDoOutput(true); // POST 파라미터 전달을 위한 설정
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String param    = "wt=json&type=TARGET&action=DISK&partition=true";

        if(partitionName != null) {
            param += "&partition_add=true";
            param += "&partition_pri=1";
            param += "&partition_limit=512";
            param += "&partition_name=" + partitionName;
        }

        OutputStream out_stream = con.getOutputStream();

        out_stream.write( param.getBytes("UTF-8") );
        out_stream.flush();
        out_stream.close();

        InputStream is      = null;
        BufferedReader in   = null;
        String data         = "";

        is  = con.getInputStream();
        in  = new BufferedReader(new InputStreamReader(is), 8 * 1024);

        String line = null;
        StringBuffer buff   = new StringBuffer();

        while ( ( line = in.readLine() ) != null ) {
            buff.append(line + "\n");
        }
        data    = buff.toString().trim();

        return data;
    }
}

