package app.zf.okhttp.com.myokhttpdemo;

/**
 * ( )Created by ${Ethan_Zeng} on 2018/1/12.
 */

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.DownloadResponseHandler;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * ( NetWorkRequst)Created by ${Ethan_Zeng} on 2017/11/7.
 */

public class HttpUtils {
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public HttpUtils() {
    }

    /***
     *
     * @param params
     * @param okttp  post提交带SSID
     * @param url
     * @param handler
     * @param HTTPREQUST
     */
    public void DoPost(Map<String, String> params, MyOkHttp okttp, String url, final Handler handler, final int HTTPREQUST, String Token) {



        okttp.post().url(url).addHeader("utoken",Token).params(params).enqueue(new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                Map<String,Object> mapdata=new HashMap<String, Object>();

               // mapdata=new GetNetResult().GetJosn(response.toString());
                Message message = Message.obtain();
                message.what = HTTPREQUST;
                message.obj = response;
                synchronized (handler) {
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

                Message message = new Message();
                message.what = HTTPREQUST;
                message.obj = error_msg;
                handler.sendMessage(message);

            }
        });


    }

    /***
     *
     * @param okttp  get  requst
     * @param url
     * @param handler
     * @param HTTPREQUST
     */
    public void DoGet(MyOkHttp okttp, String url, final Handler handler, final int HTTPREQUST) {
        okttp.get().url(url).enqueue(new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                super.onSuccess(statusCode, response);
                Message message = Message.obtain();
                message.what = HTTPREQUST;
                message.obj = response;
                synchronized (handler) {
                    handler.sendMessage(message);
                }

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message message = new Message();
                message.what = HTTPREQUST;
                message.obj = error_msg;
                handler.sendMessage(message);
            }
        });

    }

    /***
     *
     * @param okttp    post 上传JSON数据带SSIN"token"
     * @param url
     * @param handler
     * @param HTTPREQUST
     * @param json
     * @param token
     */

    public void UpJsonString( MyOkHttp okttp, String url, final Handler handler, final int HTTPREQUST,String json,String token){
        okttp.post().url(url).addHeader("utoken",token).jsonParams(json).enqueue(new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                Map<String,Object> mapdata=new HashMap<String, Object>();

                //mapdata=new GetNetResult().GetJosn(response.toString());
                Message message = Message.obtain();
                message.what = HTTPREQUST;
                message.obj = response;
                synchronized (handler) {
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

                Message message = new Message();
                message.what = HTTPREQUST;
                message.obj = error_msg;
                handler.sendMessage(message);

            }
        });

    }

    /***
     *
     * @param params
     * @param okttp  上传文件
     * @param url
     * @param handler
     * @param HTTPREQUST
     * @param file
     */
    public void DoUpLoadFiles(Map<String, String> params, MyOkHttp okttp, String url, final Handler handler, final int HTTPREQUST, File file) {
        okttp.upload().url(url).params(params).addFile("file", file).enqueue(new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                super.onSuccess(statusCode, response);
                Message message = Message.obtain();
                message.what = HTTPREQUST;
                message.obj = response;
                synchronized (handler) {
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onProgress(long currentBytes, long totalBytes) {
                super.onProgress(currentBytes, totalBytes);
                Message message = Message.obtain();
                message.what = HTTPREQUST;
                message.obj = totalBytes;
                synchronized (handler) {
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                Message message = new Message();
                message.what = HTTPREQUST;
                message.obj = error_msg;
                handler.sendMessage(message);
            }
        });
    }

    /***
     * downloadFiles
     * @param okttp   下载文件
     * @param url
     * @param handler
     * @param HTTPREQUST
     * @param Filepath
     * @param context
     */
    public void DoDownLoadFiles(MyOkHttp okttp, String url, final Handler handler, final int HTTPREQUST, String Filepath, Context context) {
        okttp.download().url(url).filePath(Filepath).tag(context).enqueue(new DownloadResponseHandler() {
            @Override
            public void onStart(long totalBytes) {
                super.onStart(totalBytes);
            }

            @Override
            public void onFinish(File downloadFile) {
                Message message = Message.obtain();
                message.what = HTTPREQUST;
                message.obj = "ok";
                synchronized (handler) {
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onProgress(long currentBytes, long totalBytes) {
                Message message = Message.obtain();
                message.what = HTTPREQUST;
                message.obj = totalBytes;
                synchronized (handler) {
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(String error_msg) {

            }
        });
    }

    /***
     *@param builder   上传多文件   包括图片
     * @param mImgUrls
     * @param client   这个必须先new 一个对象
     * @param url
     * @param handler
     * @param HTTPREQUST
     */
    public void DoPostMroePicture(MultipartBody.Builder builder, ArrayList<String> mImgUrls, String picname, OkHttpClient client, String url, final Handler handler, final int HTTPREQUST) {
        //MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for (int i = 0; i <mImgUrls.size() ; i++) {
            File f=new File(mImgUrls.get(i));
            if (f!=null) {
                builder.addFormDataPart(picname, f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
            }
        }
        MultipartBody requestBody = builder.build();


        Request request = new Request.Builder()
                .url(url)//地址
                .post(requestBody)//添加请求体

                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
                Message message = Message.obtain();
                message.what = HTTPREQUST;
                message.obj = e.getLocalizedMessage();
                synchronized (handler) {
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("上传照片成功：response = " + response.body().string());
                Message message = Message.obtain();
                message.what = HTTPREQUST;
                message.obj = response.body().string();
                synchronized (handler) {
                    handler.sendMessage(message);
                }
            }
        });



    }


}

