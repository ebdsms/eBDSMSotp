package com.rbmjltd.ebdsms;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class eBDSMS {
    private String apiKey;
    private String number;
    private String message;
    private String otp;
    private String device;
    private String extra;


    public eBDSMS(String apiKey, String number, String message, String otp, String device, String extra) {
        this.apiKey = apiKey;
        this.number = number;
        this.message = message;
        this.otp = otp;
        this.device = device;
        this.extra = extra;

    }

    public void sendSms(Context context) {
        new SendSmsTask().execute();
    }

    private class SendSmsTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            try {
                String baseUrl = "https://client.ebdsms.com/services/send.php";
                String urlStr = baseUrl + "?key=" + apiKey +
                        "&number=" + number +
                        "&message=" + message +
                        "&devices=" + device +
                        "&type=sms&prioritize=0";

                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                os.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    result += inputLine;
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // Handle the result (e.g., show a Toast or log the result)
        }
    }
}

