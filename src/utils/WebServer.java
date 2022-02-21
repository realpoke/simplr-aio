package utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import utils.method_provider.CustomMethodProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;

public class WebServer extends CustomMethodProvider {

    private final String BaseAPI;

    public WebServer(String Server) {

        this.BaseAPI = Server + "api/";

    }

    private String apiCall(String API, HashMap<String, String> params) {

        try {
            String pathAPI = BaseAPI + API;
            URL url = new URL(pathAPI);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            params.forEach(con::setRequestProperty);
            con.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            if (con.getResponseCode() <= 199 || con.getResponseCode() >= 300) {

                JsonParser parser = new JsonParser();
                JsonElement json = parser.parse(br.readLine());
                JsonObject jsonObject = json.getAsJsonObject();

                return jsonObject.get("MESSAGE").toString();

            }

            return br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public String saveAccountByEmail(String email) {

        Base64.Encoder encoder = Base64.getEncoder();
        String base64Email = encoder.encodeToString(email.getBytes());
        logger.debug("Encoded email: " + base64Email);

        HashMap<String, String> params = new HashMap<>();
        params.put("email", base64Email);

        return apiCall("updateoot", params);

    }

}