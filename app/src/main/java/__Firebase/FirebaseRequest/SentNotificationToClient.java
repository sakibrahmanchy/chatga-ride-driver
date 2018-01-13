package __Firebase.FirebaseRequest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.ICallbacklisteners.ICallbackMain;

/**
 * Created by User on 1/5/2018.
 */

public class SentNotificationToClient extends AsyncTask<String, Void, String> {

    private String path = ("http://139.59.90.128/notification.php");
    private Context context;
    private ICallbackMain callbackListener;

    public SentNotificationToClient(Context context, ICallbackMain callbackListener) {
        this.context = context;
        this.callbackListener = callbackListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String actionType = FirebaseConstant.RIDER_TO_CLIENT;
        String riderId = FirebaseConstant.Empty;
        String riderName = FirebaseConstant.Empty;
        String riderPhone = FirebaseConstant.Empty;
        String clientDeviceToken = FirebaseConstant.Empty;

        try {
            riderId = params[0];
            riderName = params[1];
            riderPhone = params[2];
            clientDeviceToken = params[3];
        } catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(FirebaseConstant.REQUEST_METHOD);
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, FirebaseConstant.UTF_8));

            String data = URLEncoder.encode("actionType", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(actionType, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("riderId", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(riderId, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("riderName", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(riderName, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("riderPhone", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(riderPhone, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND +
                    URLEncoder.encode("clientDeviceToken", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(clientDeviceToken, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND;

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, FirebaseConstant.UTF_8));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;

            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            try {
                JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            inputStream.close();
            return responseStrBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {

    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(FirebaseConstant.RESPONSE_FROM_SERVER, result);
        this.callbackListener.OnSentNotificationToClient(true);
    }
}
