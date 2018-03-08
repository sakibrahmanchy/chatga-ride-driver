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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import __Firebase.FirebaseUtility.FirebaseConstant;
import __Firebase.ICallbacklisteners.ICallBackCurrentServerTime;

/**
 * Created by User on 1/21/2018.
 */

public class GetCurrentTimeAndDate extends AsyncTask<String, Void, String> {

    private String path = ("http://139.59.90.128/notification.php");
    private Context context;
    private ICallBackCurrentServerTime callbackListener;
    private int type = -1;

    public GetCurrentTimeAndDate(int type, Context context, ICallBackCurrentServerTime callbackListener) {
        this.type = type;
        this.context = context;
        this.callbackListener = callbackListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String actionType = FirebaseConstant.GET_CURRENT_TIME;

        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(FirebaseConstant.REQUEST_METHOD);
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, FirebaseConstant.UTF_8));

            String data = URLEncoder.encode("actionType", FirebaseConstant.UTF_8) + FirebaseConstant.EQUAL + URLEncoder.encode(actionType, FirebaseConstant.UTF_8) + FirebaseConstant.AMPERSAND;

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
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(responseStrBuilder.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            inputStream.close();
            String timeAndDate = ((JSONObject) jsonObject.get(FirebaseConstant.TIME)).getString(FirebaseConstant.DATE);

            return timeAndDate;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) { }

    @Override
    protected void onPostExecute(String result) {
        Log.d(FirebaseConstant.RESPONSE_FROM_SERVER, result != null ? result : "null");
        if(result == null){
            this.callbackListener.OnResponseServerTime(-1l, this.type);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(FirebaseConstant.TIME_DATE_PATTERN);
        Date date = null;
        try {
            date = sdf.parse(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            Log.d("TIME_AND_DATE", Long.toString(date.getTime()));
            this.callbackListener.OnResponseServerTime(date.getTime(), this.type);
        } else {
            this.callbackListener.OnResponseServerTime(-1l, this.type);
        }
    }
}
