import com.google.gson.Gson;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        callApiViaHttpClient("sahil", "");
    }

    public static void callApiViaHttpClient(String sname, String fname) throws IOException, InterruptedException {
        LoveCalculator loveCalculator = new LoveCalculator(sname, fname);
        Gson gson = new Gson();
        //String jsonString = gson.toJson(loveCalculator); //to convert obj to json String which we can pass in body

        HttpRequest get_request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://love-calculator.p.rapidapi.com/getPercentage?sname=%s&fname=%s", sname, fname)))
                .header("X-RapidAPI-Key", "SIGN-UP-FOR-KEY")
                .header("X-RapidAPI-Host", "love-calculator.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        while (true) {
            HttpResponse<String> get_response = HttpClient.newHttpClient().send(get_request, HttpResponse.BodyHandlers.ofString());
            System.out.println(get_response.body());

            // org.json.JSONObject
            JSONObject obj = new JSONObject(get_response.body());
            if (obj.getString("message").equals("You are not subscribed to this API.")){
                break;
            }

            // to convert json from response to LoveCalculator object (field names must be same as json keys)
            loveCalculator = gson.fromJson(get_response.body(), LoveCalculator.class);
            if (Integer.parseInt(loveCalculator.getPercentage()) > 60){
                System.out.println(loveCalculator.getResult());
                break;
            }
            TimeUnit.MILLISECONDS.sleep(1000); //or Thread.sleep(1000) before sending request again
        }

        //POST
        /*
        HttpRequest post_request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://love-calculator.p.rapidapi.com/getPercentage?sname=%s&fname=%s", sname, fname)))
                .header("X-RapidAPI-Key", "SIGN-UP-FOR-KEY")
                .header("X-RapidAPI-Host", "love-calculator.p.rapidapi.com")
                .POST(HttpRequest.BodyPublishers.ofString("{'name':'abc'}"))
                .build();
        HttpResponse<String> post_response = HttpClient.newHttpClient().send(post_request, HttpResponse.BodyHandlers.ofString());
        System.out.println(post_response.body());

         */
    }

    public static void callApiViaAsyncHttpClient() throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepareGet("https://love-calculator.p.rapidapi.com/getPercentage?sname=Alice&fname=John")
                .setHeader("X-RapidAPI-Key", "SIGN-UP-FOR-KEY")
                .setHeader("X-RapidAPI-Host", "love-calculator.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }

    @Deprecated
    public static void callApiViaOkHttpClient(){
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("https://love-calculator.p.rapidapi.com/getPercentage?sname=Alice&fname=John")
//                .get()
//                .addHeader("X-RapidAPI-Key", "SIGN-UP-FOR-KEY")
//                .addHeader("X-RapidAPI-Host", "love-calculator.p.rapidapi.com")
//                .build();
//
//        Response response = client.newCall(request).execute();
    }

}
