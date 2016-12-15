package com.zaaach.AlphaMaskFrameLayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zaaach.alphamasklayout.AlphaMaskLayout;

public class MainActivity extends AppCompatActivity {
    private AlphaMaskLayout maskLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maskLayout.showMask();
            }
        });

        findViewById(R.id.btn_reverse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maskLayout.hideMask();
            }
        });


        maskLayout = (AlphaMaskLayout) findViewById(R.id.mask_layout);
        //you can also do like this:
        maskLayout.setAlphaFrom(0);
        maskLayout.setAlphaTo(180);
        maskLayout.setDuration(1200);

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
