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
import java.util.HashSet;
import java.util.Set;

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
                        JSONArray FirstF = null;
                        JSONArray SecondF = null;
                        JSONArray ThirdF = null;
                        JSONArray FourthF = null;
                        JSONArray FifthF = null;
                        try {
                            FullObj = new JSONObject(ConvertedResponse); //문자열에서 Json 객체 얻기
                            FirstF = FullObj.getJSONArray("first_floor");
                            SecondF = FullObj.getJSONArray("second_floor");
                            ThirdF = FullObj.getJSONArray("third_floor");
                            FourthF = FullObj.getJSONArray("fourth_floor");
                            FifthF = FullObj.getJSONArray("fifth_floor");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("FirstCount",String.valueOf(FirstF.length()));


                        //                1층 부스

                        for(int n=0; n < FirstF.length(); n++){
                            try {
                                String CODE = FirstF.getString(n);
                                saveBoothData(CODE, FullObj);
                                saveBoothFloorData("first",CODE,n);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        BoothDataEditor.putInt("first_count",FirstF.length());
                        BoothDataEditor.commit();

//                2층 부스

                        for(int n=0; n < SecondF.length(); n++){
                            try {
                                String CODE = SecondF.getString(n);
                                saveBoothData(CODE,FullObj);
                                saveBoothFloorData("second",CODE,n);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        BoothDataEditor.putInt("second_count",SecondF.length());
                        BoothDataEditor.commit();
//                3층 부스

                        for(int n=0; n < ThirdF.length(); n++){
                            try {
                                String CODE = ThirdF.getString(n);
                                saveBoothData(CODE,FullObj);
                                saveBoothFloorData("third",CODE,n);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        BoothDataEditor.putInt("third_count",ThirdF.length());
                        BoothDataEditor.commit();
//                4층 부스

                        for(int n=0; n < FourthF.length(); n++){
                            try {
                                String CODE = FourthF.getString(n);
                                saveBoothData(CODE,FullObj);
                                saveBoothFloorData("fourth",CODE,n);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        BoothDataEditor.putInt("fourth_count",FourthF.length());
                        BoothDataEditor.commit();
//                5층 부스

                        for(int n=0; n < FifthF.length(); n++){
                            try {
                                String CODE = FifthF.getString(n);
                                saveBoothData(CODE,FullObj);
                                saveBoothFloorData("fifth",CODE,n);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        BoothDataEditor.putInt("fifth_count",FifthF.length());
                        BoothDataEditor.commit();
                        DataDownloadTaskNotification.notify(DataDownloadService.this, 1);

                    }


                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Log.d("BoothDataTask","Getting JSON Failed");
                        DataDownloadTaskNotification.notify(DataDownloadService.this, 3);
                    }
                });
    }

    private void saveBoothFloorData(String floor, String code, int num){
        BoothDataEditor = BoothData.edit();
        BoothDataEditor.putString(floor+num,code);
        BoothDataEditor.commit();
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
