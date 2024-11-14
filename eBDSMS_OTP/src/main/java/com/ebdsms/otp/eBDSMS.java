package com.ebdsms.otp;

import android.annotation.SuppressLint;
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
import java.util.UUID;

public class eBDSMS {

    private String otp;
    private Context context;
    private String API_KEY;
    private String DEVICE_NUMBER;
    private String NUMBER;
    private String MESSAGE;
    private ProgressDialog progressDialog;
    private String userNumber;
    private String userOtp;

    static {
        System.loadLibrary("native-lib");
    }

    static native String getKey();

    static native String getBAS1();
    static native String getBAS2();

    private String APIKEY, BASEURL, BASEURL2;


    public eBDSMS(String MESSAGE, String NUMBER, String DEVICE_NUMBER, String API_KEY, Context context) {
        //this.result = result;
        this.MESSAGE = MESSAGE;
        this.NUMBER = NUMBER;
        this.DEVICE_NUMBER = DEVICE_NUMBER;
        this.API_KEY = API_KEY;
        this.context = context;

        //init progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);

        /* decode data */
        APIKEY = new String(Base64.decode(getKey(), Base64.DEFAULT));
        BASEURL = new String(Base64.decode(getBAS1(), Base64.DEFAULT));
    }

    public eBDSMS(String userOtp, String userNumber, Context context) {
        this.userOtp = userOtp;
        this.userNumber = userNumber;
        this.context = context;

        //init progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);

        /* decode data */
        APIKEY = new String(Base64.decode(getKey(), Base64.DEFAULT));
        BASEURL2 = new String(Base64.decode(getBAS2(), Base64.DEFAULT));
    }

    public interface OTPCallback {
        void onResult(boolean success);
    }

    public static class OTP {

        String LATTER = "0987654321";
        String NUMBER = "1234567890";

        char[] RANDOM = (LATTER+LATTER.toUpperCase()+NUMBER).toCharArray();

        public String OTPString(int length) {
            StringBuilder result = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                result.append(RANDOM[new Random().nextInt(RANDOM.length)]);
            }
            return result.toString();
        }

    }

    public void sendOTP(OTPCallback callback, int length) {
        try {
            progressDialog.show();
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = BASEURL;
            OTP otpGenerator = new OTP();
            otp = otpGenerator.OTPString(length);

//            String otp = UUID.randomUUID().toString().substring(0, 6);

            @SuppressLint("SetTextI18n") StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        progressDialog.dismiss();
                        if (response != null) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String error = jsonResponse.getString("error");
                                String message = jsonResponse.getString("message");

                                if (error.equals("false") && message.equals("Data added successfully.")) {
                                    new SendSmsTask(success -> {
                                        if (success) {
                                            // SMS sent successfully
                                            Toast.makeText(context, "OTP sent successfully.", Toast.LENGTH_SHORT).show();
                                            callback.onResult(true);
                                        } else {
                                            // SMS failed to send
                                            Toast.makeText(context, "Failed to send OTP", Toast.LENGTH_SHORT).show();
                                            callback.onResult(false);
                                        }
                                    }).execute();

                                } else {
                                    Toast.makeText(context, "Failed to send OTP.", Toast.LENGTH_SHORT).show();
                                    callback.onResult(false);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                callback.onResult(false);
                            }
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            callback.onResult(false);
                        }
                    }, error -> {
                progressDialog.dismiss();
                Toast.makeText(context, NUMBER + " number already sent an OTP!", Toast.LENGTH_SHORT).show();
                callback.onResult(false);
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
                    map.put("number", NUMBER);
                    map.put("otp", otp);
                    map.put("time", String.valueOf(System.currentTimeMillis()));
                    return map;
                }
            };

            queue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onResult(false);
        }
    }

    public interface SmsCallback {
        void onResult(boolean success);
    }

    private class SendSmsTask extends AsyncTask<Void, Void, Boolean> {
        private final SmsCallback callback;

        public SendSmsTask(SmsCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String result = "";
            try {
                String msg = MESSAGE + otp;
                String baseUrl = "https://client.ebdsms.com/services/send.php";
                String urlStr = baseUrl + "?key=" + API_KEY +
                        "&number=" + NUMBER +
                        "&message=" + msg +
                        "&devices=" + DEVICE_NUMBER +
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

                // Check if result contains a success indicator (modify based on actual response format)
                return result.contains("success");

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (callback != null) {
                callback.onResult(success);
            }
        }
    }


    public interface otpVerifyCallback {
        void onResult(boolean success);
    }
    public void verifyOTP(otpVerifyCallback callback) {
        try {
            progressDialog.show();

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = BASEURL2;

            // Request a string response from the provided URL.
            @SuppressLint("SetTextI18n") StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        progressDialog.dismiss();
                        if (response != null) {
                            // Parse JSON
                            JSONObject jsonResponse = null;
                            try {
                                jsonResponse = new JSONObject(response);
                                String error = jsonResponse.getString("error");
                                String message = jsonResponse.getString("message");

                                // Get nested "data" JSON object
                                if (error.equals("false") && message.equals("OTP found.")) {
                                    JSONObject data = jsonResponse.getJSONObject("data");
                                    String otp1 = data.getString("otp"); // server otp

                                    if (userOtp.equals(otp1)) {
                                        callback.onResult(true);
                                    } else {
                                        callback.onResult(false);
                                    }

                                } else {
                                    // failed
                                    callback.onResult(false);
                                }
                            } catch (JSONException e) {
                                callback.onResult(false);
                                throw new RuntimeException(e);
                            }
                        } else {
                            callback.onResult(false);
                        }

                    }, error -> {
                progressDialog.dismiss();
                callback.onResult(false);
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
                    map.put("number", userNumber);
                    return map;
                }
            };

            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
            callback.onResult(false);
        }
    }

}

