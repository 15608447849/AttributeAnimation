package com.lzp.attributeanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.image_view);
    }


    public void image_view(View v){
        Toast.makeText(this,"image_view",Toast.LENGTH_LONG).show();
    }

    public void move(View v){

        //平移
        TranslateAnimation move = new TranslateAnimation(0,200,0,0);//  x 平移200
        move.setDuration(1000);
        move.setFillAfter(true);
        iv.startAnimation(move);
        /**
         * 局限性 :只是 图片 做了动画 而响应事件还停在原地没动
         */
    }



    public void move_attribute(View v){

        //操控的控件imageview
        //操控的属性translationX -> 平移 X轴  translationY -> 平移Y轴
        //可变数组  动画变化范围 (0F,200F)
        ObjectAnimator.ofFloat(iv,"rotation",0F,360F)
                .setDuration(1000)
                .start();

        ObjectAnimator.ofFloat(iv,"translationY",0F,200F)
                .setDuration(1000)
                .start();
        ObjectAnimator.ofFloat(iv,"translationX",0F,200F)
                .setDuration(1000)
                .start();
        //调用 start 是一个 异步过程

        /**
         * 常见属性
         *   X , Y  -> 最终到达的一个绝对值
         *
         *   translationX translationY ->  偏移量
         *      rotation 旋转  0f-360f
         *      rotationX /rotationY
         *      scaleX /scaleY
         *      alpha 透明度
         */
    }


    public void move_attribute2(View v){

        Toast.makeText(this,"property Value Holder ",Toast.LENGTH_LONG).show();

        //属性,  属性变化值
        PropertyValuesHolder p1 =
                PropertyValuesHolder.ofFloat("rotation",0,360);

        PropertyValuesHolder p2=
                PropertyValuesHolder.ofFloat("translationX",0,360);

        PropertyValuesHolder p3 =
                PropertyValuesHolder.ofFloat("translationY",0,360);

        //操控对象, property数组
        ObjectAnimator.ofPropertyValuesHolder(iv,p1,p2,p3).setDuration(1000).start();
        //同时作用三个属性

    }


    public void animation_set(View v){
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator a1 =   ObjectAnimator.ofFloat(iv,"rotation",0F,360F);
        ObjectAnimator a2 = ObjectAnimator.ofFloat(iv,"translationY",0F,200F);
        ObjectAnimator a3 = ObjectAnimator.ofFloat(iv,"translationX",0F,200F);
//        set.playTogether(a1,a2,a3); //同时
//        set.playSequentially(a1,a2,a3);//有序


        //属性控制
        set.play(a2).with(a3);// with  同时,支持,随着
        set.play(a1).before(a2); // 在after 在..之后   before在...之前
        set.setDuration(1000);
        set.start();
    }


    public void animation_listen(View v){


        ObjectAnimator animator =
                ObjectAnimator.ofFloat(v,"alpha",0F,1F);
        animator.setDuration(1000);


       /* animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                        //开始
                Toast.makeText(MainActivity.this,"动画属性事件监听onAnimationStart ",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                        //结束
                Toast.makeText(MainActivity.this,"动画属性事件监听onAnimationEnd ",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                    //取消
                Toast.makeText(MainActivity.this,"动画属性事件监听onAnimationCancel ",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                    //重复
                Toast.makeText(MainActivity.this,"动画属性事件监听onAnimationRepeat",Toast.LENGTH_LONG).show();

            }
        });
*/
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Toast.makeText(MainActivity.this,"动画属性事件监听onAnimationEnd ",Toast.LENGTH_LONG).show();
            }
        });

        animator.start();
    }


    public void test(View view){
        startActivity(new Intent(MainActivity.this,meau_activity.class));
    }

    /**
     * valueAnimator:
     *  数值发生器
     *    int float point等
     *    如何产生每一步的具体实现动画效果
     *    都是通过valueAnimator
     *
     *    例: 0- 100 的一个平移的动画
     *    value 也是 在 0-100 递增
     *    有了这些value 就可以作用属性 产生动画效果
     *    valueAnimator 怎么产生值?
     *          根据动画 已经进行的时间 与 持续的总时间 的一个比值
     *         产生一个 0-1 的时间因子 ,有了 时间因子经过变化
     *        就可以根据 startValue endValue 来生成 中间的 相应值,
     *        同时通过插值器 interpolation, 进一步控制 每一个 时间因子 的产生值的变化速度
     *      如.使用线性插值器 生成数值的时候 就是一个 线性变化( 只要时间相同 增量相同 )
     *         如 加速度插值器 就是一个 二次曲线增长率
     *
     *      由于 valueAnimator 不相应任何一个动画 也不控制任何一个属性 所以并没有 ObjectAnimator使用的广泛
     */
    public void valueAnimator(View v){
        Toast.makeText(MainActivity.this,"valueAnimator ",Toast.LENGTH_LONG).show();

       final Button button = (Button) v;

        //objectAnimator 继承 valueAnimator 二次封装

        //产生一个 0 - 100 的int 变化
        ValueAnimator animator = ValueAnimator.ofInt(0,100);
        animator.setDuration(5*1000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        // valueanimator 不能作用任何一个属性
        //使用动画监听事件
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //可以获取 每一步 所产生的值

               Integer value =(Integer)animation.getAnimatedValue();//每一个时间因子所产生的一个value

                //实现一个计时器 动画效果  -> 点击之后 希望 button上面的数字 从0-100 不断变化
                button.setText("- ,-"+value);
            }
        });
        animator.start();
    }


    public void valueAnimator_object(View v){

        //参数 : typeEvaluator求值程序 [i'væljueitə]   , object[]
    ValueAnimator animator = ValueAnimator.ofObject(new TypeEvaluator() {
        //通过重写evaluate 方法 可以返回各种各样的值
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            //参数:fraction 分数['frækʃ(ə)n]   -> 时间因子  0-1 变化
            //startValue 开始值
            //endValue  结束值
            return null;
        }
    });


        //可以指定泛型
       ValueAnimator animator2 = ValueAnimator.ofObject(new TypeEvaluator<PointF>() { //坐标

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {

            return null;
        }
    });






    }
}
