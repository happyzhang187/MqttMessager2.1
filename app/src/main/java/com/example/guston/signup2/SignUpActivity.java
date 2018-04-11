package com.example.guston.signup2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.VectorDrawable;
import android.opengl.Visibility;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.databinding.DataBindingUtil;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guston.signup2.databinding.ActivitySignUpBinding;
import com.hbb20.CountryCodePicker;
import com.sim.scar.SmsContentObserver;
import com.skydoves.colorpickerview.ColorListener;
import com.skydoves.colorpickerview.ColorPickerView;

import java.util.ArrayList;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class SignUpActivity extends AppCompatActivity {

   private  ActivitySignUpBinding mBinding;
    TextView SignUp;
    TextView Login;
    EditText TheEditText;

    CountryCodePicker countryPicker;
    TextView WaitingLine ;
   // TestView Background;
    Button NextButton;
    ProgressBar NextProcess;
    TextView NextText;
    EditText code1;
    EditText code2;
    EditText code3;
    EditText code4;
    EventHandler eventHandler;
    SmsContentObserver smsObserver;
    int codenumber1;
    int codenumber2;
    int codenumber3;
    int codenumber4;

    String mPhone;
    View RevealView;
    EditText passwordEditor;

    ColorPickerView ColorSelector;
    View TheViewToShowResultColor;
    Button ColorSelectorBtn;
   public int TheFinalColor;
   View ShapeSquare;
   View SHapeCircle;
   View ShapeSmooothSquare;
   View ShapeTriangle;
   View ShapeDiamone;
   Button NextShapeBtn;
   Button Nextpassword;
   Animation SelectedAnimation;
   Animation UnselectedAnimation;
    ArrayList<View> ShapeArray;
    View flagView = null;
    Animation BigToSmall;
    EditText accounEditorLogin;
    EditText passwordEditorLogin;
    Button FinalLogin;
    Boolean IfeditAccount = false;
    Boolean IfEditPassword = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SMSSDK.setAskPermisionOnReadContact(true);
        mBinding = DataBindingUtil.setContentView(this , R.layout.activity_sign_up);
      final TestView Background = mBinding.background;
       SignUp = mBinding.signupButton;
       //LoginBtn = mBinding.loginBtn;
       // ObjectAnimator Ddisappear = ObjectAnimator.ofFloat(SignUp , android.transition.Vi);
       TheEditText = mBinding.editsignup;
       countryPicker = mBinding.countryPicker;
       WaitingLine  = mBinding.waitingLine;
       NextButton = mBinding.nextBtn;
       NextProcess  = mBinding.nextProcess;
       NextText = mBinding.nextText;
       code1 = mBinding.codeInputer1;
       code2 = mBinding.codeInputer2;
       code3 = mBinding.codeInputer3;
       code4 = mBinding.codeInputer4;
       Login = mBinding.loginBtn;
       accounEditorLogin = mBinding.loginAccount;
       passwordEditorLogin = mBinding.loginPassword;
       FinalLogin = mBinding.finalLogin;
       // LogicToShowSelectedColor();

       SetNextButtonListener(this);
       AddTextListener();
       Background.setmListener(new AnimatorListenerAdapter() {
           @Override
           public void onAnimationEnd(Animator animation) {
               super.onAnimationEnd(animation);
           }
       });
         SignUp.setOnClickListener(new View.OnClickListener(){


             @Override
             public void onClick(View view) {


                 AnimateSignUp();
                Background.StartAnimate();



             }
         });

       Login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AnimateLogin();
               Background.LoginAnimation();
           }
       });





        // 创建EventHandler对象.验证码结果的回调
      eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {




                   try {


                       if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                           // 这里是验证成功的回调，可以处理验证成功后您自己的逻辑，需要注意的是这里不是主线程
                       }

                       if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                           // 验证成功的回调，处理逻辑
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {

                                   SuccessfulVerified();
                                   //GotoNextActivity();
                               }
                           });



                       }

                   }catch (Exception e) {

                       Log.d("Callback is wrong", "afterEvent: "+ e);
                   }



                }
            }
        };

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);












//the perission to read sms
        if (this.checkSelfPermission(android.Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(SignUpActivity.this , "NOOOOOOOO Permission" ,Toast.LENGTH_LONG).show();
            //申请READ_SMS权限
           // ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS}, 1);
            Log.d("Permission", "onCreate: NOOOOOOO Permission :(((");

            requestPermissions(new String[] {android.Manifest.permission.READ_SMS} , 1);
            return;

        }
        else {

            Toast.makeText(SignUpActivity.this,"YOU have Permisssion!!!Yessss" , Toast.LENGTH_LONG).show();
            Log.d("Permission", "onCreate: Haveeee  Permission :))))");
        }







//auto-fill-smscode-callback


        smsObserver = new SmsContentObserver(
                new Handler(),
                SignUpActivity.this,
                new String[]{"SChat"},
        (String smsCode) -> {
            Log.d("Code", "onCreate:cod0000000000000000000000000 ");

            if ( !smsCode.isEmpty()) {
               //
                // smscodeEt.setText(smsCode);
                Log.d("Code", "onCreate:codeeeeeeeeeeeeeeeeeeeee ");
                Toast.makeText(SignUpActivity.this,"The code is" + smsCode , Toast.LENGTH_LONG).show();

               // codenumber1 = Integer.par
                LogicOfFillNumber(smsCode);
            }
        }
);

smsObserver.registerSMSObserver();







    }
// The animation Of The SignUp Button after User Chose to SignUp , And bind the Show Edit logic Here
    private void AnimateSignUp(){

        SignUp.animate().alpha(0f)
                                        .setDuration(400)
        .setListener(new Animator.AnimatorListener() {
                         @Override
                         public void onAnimationStart(Animator animator) {
                             //Background.StartAnimate();
                         }

                         @Override
                         public void onAnimationEnd(Animator animator) {
                          ShowEdit();
                            // Background.StartAnimate();
                             SignUp.setVisibility(GONE);
                         }

                         @Override
                         public void onAnimationCancel(Animator animator) {

                         }

                         @Override
                         public void onAnimationRepeat(Animator animator) {

                         }
                     }
        )
        .start();




    }
//The Logic to SHOW UP The PhoneNUmber Editor etc after USER chose to Sign Up
    private  void  ShowEdit(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                countryPicker.setVisibility(View.VISIBLE);
                countryPicker.animate().alpha(1f)
                        .setDuration(200)
                        .start();

            }
        }, 200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TheEditText.setVisibility(VISIBLE);
                TheEditText.animate().alpha(1f)
                        .setDuration(300)
                        .start();



            }
        }, 400);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               WaitingLine.setVisibility(VISIBLE);
                WaitingLine.animate().alpha(1f)
                        .setDuration(400)
                        .start();


            }
        }, 600);


    }
//THE Logic to Observe the change of PhoneNumber EditText!
    public void AddTextListener(){
        TheEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
             WaitingLine.setAlpha(0f);
             WaitingLine.setVisibility(GONE);
             NextButton.setAlpha(1f);
             NextButton.setVisibility(View.VISIBLE);
             NextText.setAlpha(1f);
             NextText.setVisibility(View.VISIBLE);

            }
        });

    }

//The animation to Disappear The " Next "  TEXT!And show UP THE ProcessBar!
    public void narrowButton(){
NextText.animate().alpha(0f)
        .setDuration(200)
        .setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ShowProcessBar();
                NextText.setVisibility(GONE);


            }
        })
        .start();





    }

















//The Logic to set Listener of The first Next Button
    public void SetNextButtonListener(final Context context){

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





               phonenumberGroupDisappear();

             //mPhone = TheEditText.getText().toString().trim();

             // SMSSDK.getVerificationCode("86",mPhone);






                Toast.makeText(context ,"The code is coming" , Toast.LENGTH_SHORT).show();

            }
        });



    }

// Make the button disappear when we click the NEXT Button at the first time
    private  void  NarrowTheButton(){

      /*  ValueAnimator narrowAni = ValueAnimator.ofFloat(NextButton.getMeasuredWidth(),NextButton.getMeasuredHeight());
        narrowAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
              float val = (float) valueAnimator.getAnimatedValue();
              NextButton.setWidth((int)val);
              NextButton.requestLayout();

            }
        }
        );
        narrowAni.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                NextButton.animate().alpha(0f)
                        .setDuration(100)
                        .start();
            }
        });
       narrowAni.setDuration(300);
       narrowAni.start();

*/  NextButton.animate().alpha(0f)
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        NextButton.setVisibility(GONE);

                    }
                })
                .start();

    }


// Animate the processBar to show UP AND THE Code EditText To Show UP!
    private void ShowProcessBar(){
        NextProcess.setAlpha(1f);
        NextProcess
                .getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.RiceColor), PorterDuff.Mode.SRC_IN);
        NextProcess.setVisibility(VISIBLE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                CodeAnimation();

            }
        }, 200);






    }

    //All above is to edit sign up button




//The animation logic to disappear all the Countrypicker ,Phonenumber Editor etc ,
    public void  phonenumberGroupDisappear (){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                countryPicker.animate().alpha(0f)
                        .setDuration(300)
                        .start();



            }
        }, 0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TheEditText.animate().alpha(0f)
                        .setDuration(300)
                        .start();




            }
        }, 300);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                narrowButton();
                NarrowTheButton();
                countryPicker.setVisibility(GONE);
                TheEditText.setVisibility(GONE);

               SuccessfulVerified();
             //   ShowProcessBar();

            }
        }, 600);



    }














    //code EditText animation to Show up
    private void CodeAnimation(){
        code2.setVisibility(View.VISIBLE);
        code3.setVisibility(View.VISIBLE);
        code1.setVisibility(View.VISIBLE);
        code4.setVisibility(View.VISIBLE);

        code1.animate().alpha(1f).setDuration(200).start();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                code2.animate().alpha(1f).setDuration(200).start();

            }
        }, 300);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                code3.animate().alpha(1f).setDuration(200).start();

            }
        }, 600);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                code4.animate().alpha(1f).setDuration(200).start();

            }
        }, 900);



    }




// Fill the number logic .right after we recieve the code , We do submit code to verify at the same time
private  void  LogicOfFillNumber (String smsCode){

        char a = smsCode.charAt(0),b = smsCode.charAt(1),c = smsCode.charAt(2),d = smsCode.charAt(3);
        codenumber1 = Integer.parseInt(String.valueOf(a));
    codenumber2 = Integer.parseInt(String.valueOf(b));
    codenumber3 = Integer.parseInt(String.valueOf(c));
    codenumber4 = Integer.parseInt(String.valueOf(d));

    Log.d("The code ", "LogicOfFillNumber: "+codenumber1+"second code " + codenumber2 + "thisrd" + codenumber3 + "and" + codenumber4);
  //  code1.text
    code1.setText(String.valueOf(a) , TextView.BufferType.EDITABLE);

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            code2.setText(String.valueOf(b), TextView.BufferType.EDITABLE);

        }
    }, 200);


    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            code3.setText(String.valueOf(c), TextView.BufferType.EDITABLE);
        }
    }, 400);



    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            code4.setText(String.valueOf(d), TextView.BufferType.EDITABLE);

        }
    }, 600);


    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            try {

                SMSSDK.submitVerificationCode("86", mPhone, smsCode);
                Log.d("Right", "run: "+mPhone + smsCode);

            }catch (Exception e){

                Log.d("Wrong", "run: "+ e);

            }
        }
    }, 1200);





}


//The reveal Animation
private void SuccessfulVerified(){

RevealView = mBinding.reveal;
RevealView.setVisibility(View.VISIBLE);
float maxR = RevealView.getMeasuredHeight() * 1.2f;
float minR = NextProcess.getMeasuredWidth();
int centerX = (int)NextButton.getX();
int centerY = (int)NextProcess.getY();

Animator RevealAni = ViewAnimationUtils.createCircularReveal(RevealView,centerX , centerY , minR , maxR);
RevealAni.setDuration(500);
RevealAni.addListener(new AnimatorListenerAdapter() {
    @Override
    public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        GotoNextActivity();
    }
});
RevealAni.start();



}
private  void GotoNextActivity(){
    new Handler().postDelayed(new Runnable() {

        @Override
        public void run() {

            passwordEditor = mBinding.editPassword;
           // passwordEditor.setVisibility(View.VISIBLE);
          //  passwordEditor.animate().alpha(1f).setDuration(200).start();

            LogicToShowSelectedColor();

            code1.setVisibility(GONE);
            code2.setVisibility(GONE);
            code3.setVisibility(GONE);
            code4.setVisibility(GONE);


        }
    }, 400);

}





private void LogicToShowSelectedColor(){
    ColorSelector = mBinding.colorSelector;
    TheViewToShowResultColor = mBinding.viewToshowtheselectedcolor;

    ColorSelector.setVisibility(VISIBLE);
    TheViewToShowResultColor.setVisibility(VISIBLE);

    GradientDrawable CircleP =(GradientDrawable) TheViewToShowResultColor.getBackground();

    ColorSelector.setColorListener(new ColorListener() {
        @Override
        public void onColorSelected(int color) {
           // TheViewToShowResultColor.setBackgroundColor(color);
            TheFinalColor = color;
            CircleP.setColor(color);

            ShowUpNextBtnAfterChoseColor(color);
        }
    });





}


//The logic of showing up the next button after you chose color
private void ShowUpNextBtnAfterChoseColor( int color){
    ColorSelectorBtn = mBinding.nextBtnColorSelector;
    ColorSelectorBtn.setVisibility(VISIBLE);
    GradientDrawable NextShape = (GradientDrawable) ColorSelectorBtn.getBackground();
    //NextShape.setColor(color);
    NextShape.setStroke(5,color);
    ColorSelectorBtn.setText("Next");
   ColorSelectorBtn.setTextColor(color);
    //ColorSelectorBtn.setBackgroundColor(color);

    ColorSelectorBtn.animate().alpha(1f).setDuration(300).start();
    ColorSelectorBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            GoToChooseShape();
        }
    });



}


private  void  GoToChooseShape(){
    TheViewToShowResultColor.animate().alpha(0f).setDuration(200);

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

           ColorSelector.animate().alpha(0f).setDuration(200).start();
        }
    }, 200);

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

           ColorSelectorBtn.animate().alpha(0f)
                   .setListener(new AnimatorListenerAdapter() {
                       @Override
                       public void onAnimationEnd(Animator animation) {
                           super.onAnimationEnd(animation);
                           TheViewToShowResultColor.setVisibility(GONE);
                           ColorSelector.setVisibility(GONE);
                           ColorSelectorBtn.setVisibility(GONE);
                           ShowUpChooseShapeUI();

                       }
                   })
                   .setDuration(200).start();
        }
    }, 400);

}

//The logic to show up the shape-select section and the logic after user chose a shape
private  void  ShowUpChooseShapeUI(){
       ShapeSquare = mBinding.squareShape;
       SHapeCircle = mBinding.circleShape;
       ShapeDiamone = mBinding.diamoneShape;
       ShapeSmooothSquare = mBinding.smoothsquareShape;
       ShapeTriangle = mBinding.triangleShape;
       NextShapeBtn = mBinding.nextBtnShapeSelector;

  ShapeArray = new ArrayList<View>();
    ShapeArray.add(ShapeSquare);
    ShapeArray.add(SHapeCircle);
    //ShapeArray.add(ShapeDiamone);
    ShapeArray.add(ShapeSmooothSquare);
    //ShapeArray.add(ShapeTriangle);
    for(View a : ShapeArray)
    {
        a.setVisibility(VISIBLE);
        GradientDrawable b = (GradientDrawable) a.getBackground();
        b.setStroke(10,TheFinalColor);
       // b.setStroke(5,TheFinalColor);


    }

    ShapeTriangle.setVisibility(VISIBLE);
    Drawable shapeB = ShapeTriangle.getBackground();
    shapeB = DrawableCompat.wrap(shapeB);
    DrawableCompat.setTint(shapeB,TheFinalColor);

    ShapeDiamone.setVisibility(VISIBLE);
    Drawable shapeC = ShapeDiamone.getBackground();
    shapeC = DrawableCompat.wrap(shapeC);
    DrawableCompat.setTint(shapeC,TheFinalColor);




      // NextShapeBtn.setVisibility(VISIBLE);
        GradientDrawable btncolorSetter = (GradientDrawable) NextShapeBtn.getBackground();
        btncolorSetter.setStroke(5 , TheFinalColor);
        NextShapeBtn.setText("Next");

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

           ShapeSquare.animate().alpha(1f).setDuration(200).start();
           ShapeSquare.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   NextShapeBtn.setVisibility(VISIBLE);
                   ScaleTheChosenShape(ShapeSquare);

               }
           });
        }
    }, 200);

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            SHapeCircle.animate().alpha(1f).setDuration(200).start();
            SHapeCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NextShapeBtn.setVisibility(VISIBLE);
                    ScaleTheChosenShape(SHapeCircle);

                }
            });


        }
    }, 400);

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            ShapeDiamone.animate().alpha(1f).setDuration(200).start();
            ShapeDiamone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NextShapeBtn.setVisibility(VISIBLE);
                    ScaleTheChosenShape(ShapeDiamone);

                }
            });


        }
    }, 600);

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            ShapeSmooothSquare.animate().alpha(1f).setDuration(200).start();
            ShapeSmooothSquare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NextShapeBtn.setVisibility(VISIBLE);
                    ScaleTheChosenShape(ShapeSmooothSquare);

                }
            });



        }
    }, 800);

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            ShapeTriangle.animate().alpha(1f).setDuration(200).start();
            ShapeTriangle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NextShapeBtn.setVisibility(VISIBLE);
                    ScaleTheChosenShape(ShapeTriangle);

                }
            });

        }
    }, 1000);
   AfterUserChoseAShape();

}

// The logic after User clicke NEXT button
private  void AfterUserChoseAShape(){
    NextShapeBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TheLogicToDisappearShapeAndShowUpPasswordEditor();
        }
    });


}

private void TheLogicToDisappearShapeAndShowUpPasswordEditor(){

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            ShapeSquare.animate().alpha(0f).setDuration(100).start();


        }
    }, 100);

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            SHapeCircle.animate().alpha(0f).setDuration(100).start();



        }
    }, 200);


    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            ShapeDiamone.animate().alpha(0f).setDuration(100).start();



        }
    }, 300);


    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            ShapeSmooothSquare.animate().alpha(0f).setDuration(100).start();



        }
    }, 400);



    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            ShapeTriangle.animate().alpha(0f).setDuration(100).start();




        }
    }, 500);


    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {



            NextShapeBtn.animate().alpha(0f)
                    .setDuration(100)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {

                            ShapeSquare.setVisibility(GONE);
                            SHapeCircle.setVisibility(GONE);
                            ShapeDiamone.setVisibility(GONE);
                            ShapeSmooothSquare.setVisibility(GONE);
                            ShapeTriangle.setVisibility(GONE);
                            NextShapeBtn.setVisibility(GONE);

                            super.onAnimationEnd(animation);
                            ShowUpEditPasswordUI();
                        }
                    })
                    .start();



        }
    }, 600);










}

private void ShowUpEditPasswordUI(){
    passwordEditor = mBinding.editPassword;
    passwordEditor.setVisibility(VISIBLE);
    GradientDrawable passcolor = (GradientDrawable) passwordEditor.getBackground();
    passcolor.setStroke(5,TheFinalColor);
  //  passwordEditor.setHintTextColor(TheFinalColor);
    //passwordEditor.setHighlightColor(TheFinalColor);
    passwordEditor.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
    passwordEditor.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            Nextpassword = mBinding.nextBtnAfterPassword;
            Nextpassword.setVisibility(VISIBLE);
            GradientDrawable btnpasswaord = (GradientDrawable) Nextpassword.getBackground();
            btnpasswaord.setStroke(5,TheFinalColor);


            Nextpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SignUpActivity.this,MainChatActivity.class);
                    startActivity(intent);



                }
            });

        }
    });


}


private void ScaleTheChosenShape(View chosenOne ){
 /*   SelectedAnimation = AnimationUtils.loadAnimation(SignUpActivity.this , R.anim.scale_selected_shape);
   // UnselectedAnimation = AnimationUtils.loadAnimation(SignUpActivity.this , R.anim.scale_unselected_shape);
    BigToSmall = AnimationUtils.loadAnimation(SignUpActivity.this,R.anim.scale_fromthelastone_youchose);
    chosenOne.startAnimation(SelectedAnimation);


    if (flagView!= null){
        flagView.startAnimation(BigToSmall);

    }
    flagView = chosenOne;
  //  chosenOne.startAnimation(SelectedAnimation);

*/

 ValueAnimator animator = ValueAnimator.ofFloat(1f,1.6f);
 animator.setTarget(chosenOne);
 animator.setDuration(200).start();
 animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
     @Override
     public void onAnimationUpdate(ValueAnimator valueAnimator) {
         chosenOne.setScaleX((float)valueAnimator.getAnimatedValue());

         chosenOne.setScaleY((float)valueAnimator.getAnimatedValue());
     }
 });

    if (flagView!= null){

        ValueAnimator animator1 = ValueAnimator.ofFloat(1.6f,1f);
        animator1.setTarget(flagView);
        animator1.setDuration(200).start();
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                flagView.setScaleX((float)valueAnimator.getAnimatedValue());

                flagView.setScaleY((float)valueAnimator.getAnimatedValue());
            }
        });
        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                flagView = chosenOne;

            }
        });

    }
    else {

        flagView = chosenOne;

    }



}






@Override
    protected void onDestroy (){
    super.onDestroy();
    SMSSDK.unregisterEventHandler(eventHandler);
    if (smsObserver != null) {
        smsObserver.unregisterSMSObserver();
        smsObserver = null;
    }
}











public void AnimateLogin(){
       Login.animate().alpha(0f).setDuration(400)
               .setListener(new AnimatorListenerAdapter() {
                   @Override
                   public void onAnimationEnd(Animator animation) {
                       super.onAnimationEnd(animation);
                       Login.setVisibility(GONE);
                       ShowUpLoginEdit();

                   }
               })
               .start();

       SignUp.animate().alpha(0f).setDuration(400)
               .setListener(new AnimatorListenerAdapter() {
                   @Override
                   public void onAnimationEnd(Animator animation) {
                       super.onAnimationEnd(animation);
                       SignUp.setVisibility(GONE);
                   }
               })
               .start();
}

public void ShowUpLoginEdit(){
 /*   accounEditorLogin.setVisibility(VISIBLE);
    passwordEditorLogin.setVisibility(VISIBLE);
*/
   DelayShowUpElement delayhandler = new DelayShowUpElement(accounEditorLogin,passwordEditorLogin);
   delayhandler.ShowUpTheView();

    accounEditorLogin.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
if (IfEditPassword){

    FinalLogin.setVisibility(VISIBLE);

}
            IfeditAccount = true;

        }
    });
    passwordEditorLogin.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {


            if (IfeditAccount){
                FinalLogin.setVisibility(VISIBLE);
            }
            IfEditPassword = true;

        }
    });




}





    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                   // insertDummyContact();
                } else {
                    // Permission Denied
                    Toast.makeText(SignUpActivity.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
