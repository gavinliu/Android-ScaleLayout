package cn.gavinliu.android_scalelayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
//        switch (name) {
//            case "FrameLayout":
//                return new ScaleFrameLayout(context, attrs);
//            case "LinearLayout":
//                return new ScaleLinearLayout(context, attrs);
//            case "RelativeLayout":
//                return new ScaleRelativeLayout(context, attrs);
//        }
        return super.onCreateView(name, context, attrs);
    }
}
