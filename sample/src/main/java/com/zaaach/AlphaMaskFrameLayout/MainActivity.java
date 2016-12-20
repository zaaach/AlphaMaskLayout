package com.zaaach.AlphaMaskFrameLayout;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.zaaach.alphamasklayout.AlphaMaskLayout;

public class MainActivity extends AppCompatActivity {
    private AlphaMaskLayout maskLayout;
    private ImageView moreBtn;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moreBtn = (ImageView) findViewById(R.id.more);
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View content = LayoutInflater.from(MainActivity.this).inflate(R.layout.popup, null);
                mPopupWindow = new PopupWindow(content, 300, 400);
                mPopupWindow.setFocusable(true);
                mPopupWindow.setOutsideTouchable(true);
                mPopupWindow.setBackgroundDrawable(new ColorDrawable());
                mPopupWindow.showAsDropDown(moreBtn);
                mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        maskLayout.hideMask();
                    }
                });
                maskLayout.showMask();
            }
        });

        maskLayout = (AlphaMaskLayout) findViewById(R.id.mask_layout);
        //you can also do like this:
//        maskLayout.setAlphaFrom(0);
//        maskLayout.setAlphaTo(127);
//        maskLayout.setDuration(600);

        maskLayout.setOnAlphaFinishedListener(new AlphaMaskLayout.OnAlphaFinishedListener() {
            @Override
            public void onShowFinished() {
                Toast.makeText(MainActivity.this, "show finished", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onHideFinished() {
                Toast.makeText(MainActivity.this, "hide finished", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
