package com.lzp.attributeanimation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class meau_activity extends AppCompatActivity implements View.OnClickListener{


    private int[] res = {
            R.id.image_a,
            R.id.image_b,
            R.id.image_c,
            R.id.image_d,
            R.id.image_e,
            R.id.image_f,
            R.id.image_g,
            R.id.image_h
    };

    private List<ImageView> imageViewList = new ArrayList<ImageView>() ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meau_activity);

        for (int i = 0;i<res.length;i++){

            ImageView iv = (ImageView) findViewById(res[i]);

            iv.setOnClickListener(this);
            imageViewList.add(iv);
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.image_a:
                //展开动画
                if (!isOpen){
                    openAnimation();
                }else{
                    closeAnimation();
                }

                break;
            default: //
                Toast.makeText(meau_activity.this,"click()"+v.getId(),Toast.LENGTH_LONG).show();
                break;
        }
    }


    private boolean isOpen = false;


    /**
     * interpolators [in'tə:pəuleitə]
     * 插值器:  线性运动  或者 加速度运动 抛物线 或者 自定义曲线等
     * 定义一个 动画 变化值 的加速度
     *
     *
     * 加速变化: accelerate ək'seləreɪt
     *
     * Decelerate diː'seləreɪt 减速变化
     *
     *开始/结束 慢  中间 块
     *  Accelerate/Decelerate
     *
     *  anticipate æn'tɪsɪpeɪt 抢占
     *  OverShoot əʊvə'ʃuːt  超越 过头的 -> 数值在结束的时候 偏离出去一点 再收回来
     *
     *  Anticipate/Overshoot
     *
     *  Bounce baʊns 跳跃  -> 回弹  自由落体等
     *
     */
    //展开动画
    private void openAnimation(){

         //点击  一次展开

        //忽略第一张
        for (int i = 1;i<res.length;i++){
            ObjectAnimator a =   ObjectAnimator.ofFloat(imageViewList.get(i),
                    "translationY",
                    0f,
                    i*180F  //假设一张图片 移动60px 2张 120px ...
                    );
                    a.setDuration(500);
                    a.setInterpolator(new BounceInterpolator()); //自由落体效果
                    a.setStartDelay(i*200);
                    a.start();

        }
        isOpen = true;
    }
    //关闭
    private void closeAnimation() {
        for (int i = 1;i<res.length;i++){
          ObjectAnimator a =  ObjectAnimator.ofFloat(imageViewList.get(i),
                    "translationY",
                    i*80F,
                    0f//假设一张图片 移动60px 2张 120px ...
            );
            a  .setDuration(500)
                    .setStartDelay(i*200);
            a.start();




        }
        isOpen = false;
    }

}
