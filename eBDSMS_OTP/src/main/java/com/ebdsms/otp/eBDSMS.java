package com.ebdsms.otp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class eBDSMS {

    private ProgressDialog progressDialog;

    private String apiKey;
    private String number;
    private String message;
    private String device;
    private String extra;
    private String others;
    private Context context;
    private String result;

    static {
        System.loadLibrary("native-lib");
    }

    static native String getKey();
    static native String getBAS1();
    private String APIKEY, BASEURL;


    public eBDSMS(String apiKey, String number, String message, String device, String extra, String others, Context context, String result) {
        this.apiKey = apiKey;
        this.number = number;
        this.message = message;
        this.device = device;
        this.extra = extra;
        this.others = others;
        this.context = context;
        this.result = result;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        /* decode data */
        APIKEY = new String(Base64.decode(getKey(), Base64.DEFAULT));
        BASEURL = new String(Base64.decode(getBAS1(), Base64.DEFAULT));
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


    public static class OTP {

        String LATTER = "0987654321";
        String NUMBER = "1234567890";

        char[] RANDOM = (LATTER+LATTER.toUpperCase()+NUMBER).toCharArray();

        public String OTPString(int lenght) {
            StringBuilder result = new StringBuilder(lenght);
            for (int i = 0; i < lenght; i++) {
                result.append(RANDOM[new Random().nextInt(RANDOM.length)]);
            }
            return result.toString();
        }

    }


    public String SendOTP(String number, String messages, int length) {
        final String[] test = {null};
        try {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = BASEURL;
            OTP otp = new OTP();
            String SendOTP = otp.OTPString(length);


            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        if (response != null) {
                            progressDialog.dismiss();
                            JSONObject jsonResponse = null;
                            try {
                                jsonResponse = new JSONObject(response);
                                String error = jsonResponse.getString("error");
                                String message = jsonResponse.getString("message");

                                if (error.equals("false") && message.equals("Data added successfully.")) {

                                    String MESSAGE = messages+" Your OTP is: "+SendOTP;
                                    eBDSMS sms = new eBDSMS(apiKey, device, number, MESSAGE, null, null, context, "");
                                    sms.sendSms(context);
                                    test[0] = "true";
                                    Toast.makeText(context, "OTP send successfully.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Failed to send otp.", Toast.LENGTH_SHORT).show();
                                    test[0] = "false";
                                }
                            } catch (JSONException e) {
                                test[0] = "false";
                                throw new RuntimeException(e);
                            }

                        } else {
                            progressDialog.dismiss();
                            test[0] = "false";
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }, error -> {
                test[0] = "false";
                try {
                    Toast.makeText(context, number + " number already send an otp!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, number + " number already send an otp!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("api-key", APIKEY);
                    return map;
                }

                @NonNull
                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("number", number);
                    map.put("otp", SendOTP);
                    map.put("time", String.valueOf(System.currentTimeMillis()));
                    return map;
                }
            };

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
            test[0] = "false";
        }

        return test[0];
    }



}

