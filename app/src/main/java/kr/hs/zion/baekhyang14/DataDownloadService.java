package kr.hs.zion.baekhyang14;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class DataDownloadService extends Service {
    private SharedPreferences BoothData;
    private SharedPreferences PerforanceData;
    private SharedPreferences.Editor BoothDataEditor;
    private SharedPreferences.Editor PerformanceDataEditor;
    public DataDownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    @Override
    public void onCreate(){
        super.onCreate();

        DataDownloadTaskNotification.notify(this, 0);
        boothDataTask();
        performanceDataTask();
        stopSelf();
    }

    private void boothDataTask(){
        //Booth Data Saving Task
        Log.d("FindBooth", "NetWorkTadk Started");
        BoothData = getSharedPreferences("booth_data",MODE_PRIVATE);
        AsyncHttpClient AsyncJsonClient = new AsyncHttpClient();
//        SRL.setRefreshing(true);
        AsyncJsonClient.get("http://www.youngbin-han.kr.pe/baekhyang14/booth/booth.json",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        Log.d("FindBooth","Got JSON!");
//                ContentsRoot.removeAllViews();
                        //바이트 배열을 문자열로 변환
                        //Convert Byte Array to String
                        String ConvertedResponse = null;
                        try {
                            ConvertedResponse = new String(bytes, "UTF-8");
                            Log.d("JsonResponse", ConvertedResponse)
                            ;                } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        JSONObject FullObj = null;
                        try {
                            FullObj = new JSONObject(ConvertedResponse); //문자열에서 Json 객체 얻기
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                1층 부스
                        saveBoothData("household",FullObj);
                        saveBoothData("health",FullObj);
                        saveBoothData("pc2",FullObj);
                        saveBoothData("pc1",FullObj);
                        saveBoothData("c110",FullObj);
                        saveBoothData("c110",FullObj);
                        saveBoothData("c111",FullObj);
                        saveBoothData("c112",FullObj);
                        saveBoothData("c113",FullObj);
                        saveBoothData("c114",FullObj);
                        saveBoothData("c115",FullObj);
                        saveBoothData("weeclass",FullObj); //ERROR

//                2층 부스
                        saveBoothData("biolab",FullObj);
                        saveBoothData("c101",FullObj);
                        saveBoothData("c102",FullObj);
                        saveBoothData("c103",FullObj);
                        saveBoothData("c104",FullObj);
                        saveBoothData("zbs",FullObj);
                        saveBoothData("c105",FullObj);
                        saveBoothData("c106",FullObj);
                        saveBoothData("c107",FullObj);
                        saveBoothData("c108",FullObj);
                        saveBoothData("c109",FullObj);

//                3층 부스
                        saveBoothData("chemistry",FullObj);
                        saveBoothData("c210",FullObj);
                        saveBoothData("c211",FullObj);
                        saveBoothData("c212",FullObj);
                        saveBoothData("c213",FullObj);
                        saveBoothData("c214",FullObj);
                        saveBoothData("language",FullObj);

//                4층 부스
                        saveBoothData("physics",FullObj);
                        saveBoothData("c301",FullObj);
                        saveBoothData("c302",FullObj);
                        saveBoothData("c201",FullObj);
                        saveBoothData("c202",FullObj);
                        saveBoothData("c203",FullObj);
                        saveBoothData("c204",FullObj);
                        saveBoothData("c205",FullObj);
                        saveBoothData("c206",FullObj);
                        saveBoothData("c207",FullObj);
                        saveBoothData("c208",FullObj);
                        saveBoothData("c209",FullObj);
                        saveBoothData("c215",FullObj);

//                5층 부스
                        saveBoothData("earth",FullObj);
                        saveBoothData("c303",FullObj);
                        saveBoothData("c304",FullObj);
                        saveBoothData("c305",FullObj);
                        saveBoothData("c306",FullObj);
                        saveBoothData("c307",FullObj);
                        saveBoothData("c308",FullObj);
                        saveBoothData("c305",FullObj);
                        saveBoothData("c310",FullObj);
                        saveBoothData("c311",FullObj);
                        saveBoothData("c312",FullObj);
                        saveBoothData("c313",FullObj);
                        saveBoothData("c214",FullObj);


                        DataDownloadTaskNotification.notify(DataDownloadService.this, 1);
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Log.d("BoothDataTask","Getting JSON Failed");
                        DataDownloadTaskNotification.notify(DataDownloadService.this, 3);
                    }
                });
    }

    private void saveBoothData(String Id, JSONObject Obj){
        BoothDataEditor = BoothData.edit();
        String Title = "Data Update Needed";
        String Member = "Data Update Needed";
        String Location = "Data Update Needed";
        String Desc = "Data Update Needed";
        String Email = "Data Update Needed";
        try {
            JSONObject SingleObj = Obj.getJSONObject(Id);
            //Get Each String
            Title = SingleObj.get("title").toString();
            Member = SingleObj.get("members").toString();
            Location = SingleObj.get("location").toString();
            Desc = SingleObj.get("desc").toString();
            Email = SingleObj.get("email").toString();

            Log.d("SaveBoothData","Saving Booth Data:"+Title+Member+Location+Desc+Email);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Save Data to SharedPreference
        BoothDataEditor.putString(Id+"_title",Title);
        BoothDataEditor.putString(Id+"_members",Member);
        BoothDataEditor.putString(Id+"_location",Location);
        BoothDataEditor.putString(Id+"_desc",Desc);
        BoothDataEditor.putString(Id+"_email",Email);

        BoothDataEditor.commit();
    }

    private void performanceDataTask(){
        DataDownloadTaskNotification.notify(this, 0);
        //Performance Data Saving Task
        PerforanceData = getSharedPreferences("performance_data",MODE_PRIVATE);
        PerformanceDataEditor = PerforanceData.edit();
        AsyncHttpClient AsyncJsonClient = new AsyncHttpClient();
        AsyncJsonClient.get("http://www.youngbin-han.kr.pe/baekhyang14/performance/schedule.json",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                        //바이트 배열을 문자열로 변환
                        //Convert Byte Array to String
                        String ConvertedResponse = null;
                        try {
                            ConvertedResponse = new String(bytes, "UTF-8");
                            Log.d("JsonResponse", ConvertedResponse)
                            ;                } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        JSONObject FullSchedObj = null;
                        JSONArray SchedArray = null;
                        try {
                            FullSchedObj = new JSONObject(ConvertedResponse); //문자열에서 Json 객체 얻기
                            SchedArray = new JSONArray(FullSchedObj.getString("schedule")); //Json 객체에서 schedule 배열 얻기
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int n = 0; n < SchedArray.length(); n++) {
                            // SchedArray 에서 각 요소로 Json 객체 생성
                            JSONObject EachSchedObj = null;
                            try {
                                EachSchedObj = SchedArray.getJSONObject(n);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                Log.d("Getting String Item",EachSchedObj.getString("time")
                                        + EachSchedObj.getString("title") +EachSchedObj.getString("performers"));
                                PerformanceDataEditor.putString(n+"_performers",EachSchedObj.getString("performers"));
                                PerformanceDataEditor.putString(n+"_time",EachSchedObj.getString("time"));
                                PerformanceDataEditor.putString(n+"_desc",EachSchedObj.getString("desc"));
                                PerformanceDataEditor.putString(n+"_email",EachSchedObj.getString("email"));
                                PerformanceDataEditor.putString(n+"_title",EachSchedObj.getString("title"));
                                PerformanceDataEditor.putString(n+"_turn",EachSchedObj.getString("turn"));
                                PerformanceDataEditor.commit();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        PerformanceDataEditor.putInt("length",SchedArray.length());
                        PerformanceDataEditor.commit();
                        DataDownloadTaskNotification.notify(DataDownloadService.this, 2);
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                DataDownloadTaskNotification.notify(DataDownloadService.this, 4);
                    }
                });
    }
}
