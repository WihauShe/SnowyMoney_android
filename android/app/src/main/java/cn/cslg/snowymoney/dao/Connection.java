package cn.cslg.snowymoney.dao;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Connection {

    public static String getUserToken(String userId,String userName){
        String url = "http://api-cn.ronghub.com/user/getToken.json";
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = new FormBody.Builder().add("userId",userId)
                .add("name",userName).build();
        String App_Key = "pvxdm17jpehwr";
        String App_Secret = "qnA0FHVu3Yyn";
        String Nonce = String.valueOf(Math.floor(Math.random() * 1000000));
        String Timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String Signature = ShaUtil.shaEncode(App_Secret + Nonce + Timestamp);
        Request request = new Request.Builder().addHeader("App-Key",App_Key)
                .addHeader("Nonce",Nonce).addHeader("Timestamp",Timestamp)
                .addHeader("Signature",Signature).url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String addUser(String id,String name,String pass,String sex,String age,String school){
        String url = "http://10.0.2.2:8080/register";
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = new FormBody.Builder()
                .add("userId",id)
                .add("userName",name)
                .add("userPass",pass)
                .add("userSex",sex)
                .add("userAge",age)
                .add("userSchool",school).build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String result = response.body().string();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String updateUser(String userId,String field,String value){
        String url = "http://10.0.2.2:8080/updateUser";
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();;
        RequestBody requestBody = new FormBody.Builder().add("userId",userId)
                .add("field",field).add("value",value).build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String result = response.body().string();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUserInfo(String path,String userId){
        String url = "http://10.0.2.2:8080/";
        url += path;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = new FormBody.Builder().add("userId",userId).build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String result = response.body().string();
                System.out.println(result);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String changeBalance(String... params){
        String url = "http://10.0.2.2:8080/changeBalance";
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = new FormBody.Builder()
                .add("userId",params[0])
                .add("action",params[1])
                .add("flag",params[2])
                .add("jobId",params[3]).build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String result = response.body().string();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addCollection(String jid,String uid){
        String url = "http://10.0.2.2:8080/updateCollection";
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = new FormBody.Builder().add("jobId",jid).add("userId",uid).build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCollections(String userId){
        String url = "http://10.0.2.2:8080/getCollections";
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = new FormBody.Builder().add("userId",userId).build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String result = response.body().string();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteCollection(String id){
        String url = "http://10.0.2.2:8080/updateCollection?id=";
        url += id;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url(url).get().build();
        try{
            client.newCall(request).execute();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String reportUser(String ruser,String reason,String path){
        String url = "http://10.0.2.2:8080/addReport";
        File file = new File(path);
        if(file.exists()) {
            Log.i("FILE", "File exist");
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("reportUser",ruser).addFormDataPart("reason",reason)
                .addFormDataPart("img",file.getName(),RequestBody.create(MediaType.parse("image/*"),file))
                .build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String result = response.body().string();
                System.out.println(result);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static String addJob(String[] params){
        String url = "http://10.0.2.2:8080/addJob";
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = new FormBody.Builder()
                .add("category",params[0])
                .add("salary",params[1])
                .add("duration",params[2])
                .add("position",params[3])
                .add("demands",params[4])
                .add("date",params[5])
                .add("flag",params[6])
                .add("publisherName",params[7])
                .add("publisherId",params[8]).build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String result = response.body().string();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJobs(String type){
        String url = "http://10.0.2.2:8080/getJobs?type=";
        url += type;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url(url).get().build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String result = response.body().string();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteJob(String jobId){
        String url = "http://10.0.2.2:8080/deleteJob?jobId=";
        url += jobId;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url(url).get().build();
        try{
            client.newCall(request).execute();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getOrder(String userId,String total){
        String url = "http://10.0.2.2:8080/getOrder";
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = new FormBody.Builder()
                .add("userId",userId)
                .add("total",total)
                .build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendResult(String tradeNo){
        String url = "http://10.0.2.2:8080/paySuccess";
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = new FormBody.Builder()
                .add("tradeNo",tradeNo)
                .build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
