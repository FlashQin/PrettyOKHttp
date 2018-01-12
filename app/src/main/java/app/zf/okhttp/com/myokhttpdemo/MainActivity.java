package app.zf.okhttp.com.myokhttpdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tsy.sdk.myokhttp.MyOkHttp;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MyInterface {
    MyOkHttp myOKHttp = BaseAppcalition.getInstance().getMyOkHttp();
    String url = "10.208......";
    File file;
    private int HTTPSUCC = 1;
    private TextView txtone;
    Map<String, String> mapdata = new HashMap<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtone = (TextView) findViewById(R.id.txt_post);
        txtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JustPost();
            }
        });

    }

    @Override
    public void JustPost() {

        new HttpUtils().DoPost(mapdata, myOKHttp, url, handler, HTTPSUCC, "");
    }

    @Override
    public void JustGet() {
        new HttpUtils().DoGet(myOKHttp, url, handler, HTTPSUCC);
    }

    @Override
    public void JustPostJson() {
        new HttpUtils().UpJsonString(myOKHttp, url, handler, HTTPSUCC, "你的Json数据", "");
    }

    @Override
    public void JustPic() {
        new HttpUtils().DoUpLoadFiles(mapdata, myOKHttp, url, handler, HTTPSUCC, file);
    }

    @Override
    public void JustMorePic() {

    }
}
