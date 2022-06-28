package utils;

import utils.method_provider.CustomMethodProvider;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class WebServer extends CustomMethodProvider {

    private final String base;
    private final HashMap<String, String> headers = new HashMap<>();
    private static HttpURLConnection connection;

    public WebServer(String base, String key) {

        this.base = base;

        this.headers.put("Accept", "application/json");
        this.headers.put("User-Agent", "Java client");
        this.headers.put("Content-Type", "application/x-www-form-urlencoded");
        this.headers.put("Authorization", "Bearer " + key);

    }

    public enum Methods {

        GET,
        POST;

        @Override
        public String toString() {
            char[] name = name().toUpperCase().toCharArray();
            return new String(name);
        }
    }

    private String setupUrl(String path) {
        return java.net.URLDecoder.decode(base + path, StandardCharsets.UTF_8);
    }

    private static String urlEncodeUTF8(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    private static String urlEncodeUTF8(HashMap<String, String> map) {
        StringBuilder sb = new StringBuilder();
        map.forEach((k,v) -> {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(k),
                    urlEncodeUTF8(v)
            ));
        });
        return sb.toString();
    }

    private String apiRequest(String path, Methods method, HashMap<String, String> data) {

        try {
            connection = (HttpURLConnection) new URL(setupUrl(path)).openConnection();

            connection.setDoOutput(true);
            connection.setRequestMethod(method.toString());
            headers.forEach((k,v) -> {
                connection.setRequestProperty(k,v);
            });

            if (method.equals(Methods.POST)) {
                try (var wr = new DataOutputStream(connection.getOutputStream())) {

                    wr.write(urlEncodeUTF8(data).getBytes(StandardCharsets.UTF_8));
                }
            }

            StringBuilder content;

            try (var br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            return content.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String get(String path) {
        return apiRequest(path, Methods.GET, new HashMap<>());
    }

    public String post(String path, HashMap<String, String> data) {
        return apiRequest(path, Methods.POST, data);
    }

}