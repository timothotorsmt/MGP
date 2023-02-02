package com.sdm.mgpica;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewDebug;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

// FACEBOOK STUFF
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;
// FACEBOOK


// Written by Tan Sze Ting

public class Losescreen extends Activity implements View.OnClickListener, StateBase {  //Using StateBase class

    //Define buttons
    private Button btn_menu;
    private Button btn_save;

    private TextView score;

    private TextView num1;
    private TextView beforeScore;
    private TextView yourScore;

    // FACEBOOK
    private Button btn_sharescore;

    private CallbackManager callbackManager;
    private LoginManager loginManager;

    private static final String EMAIL = "email";

    private LoginButton btn_fbLogin;

    private ShareDialog share_Dialog;
    private int PICK_IMAGE_REQUEST = 1;

    //Share Dialog shareDialog
    ProfilePictureView profile_pic;
    // FACEBOOK

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.setApplicationId("573356071042478");
        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.isInitialized();

        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.losescreen);
        // FACEBOOK

        if(BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }

        //Typeface myfont;
        //myfont = Typeface.createFromAsset(getAssets(), "fonts/arcadia.otf");

        btn_fbLogin = (LoginButton) findViewById(R.id.fb_login_button);
        btn_fbLogin.setReadPermissions(Arrays.asList(EMAIL));
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));

        btn_sharescore = findViewById(R.id.btn_sharescore);
        btn_sharescore.setOnClickListener(this);

        profile_pic = findViewById(R.id.picture);

        callbackManager = CallbackManager.Factory.create();

        loginManager = LoginManager.getInstance();

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                profile_pic.setProfileId(Profile.getCurrentProfile().getId());

                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                loginResult.getAccessToken().getUserId();
            }
            @Override
            public void onCancel() {System.out.println("Login attempt cancelled.");}

            @Override
            public void onError(FacebookException e) {
                System.out.println("Login attempt failed.");
            }
        });
        // FACEBOOK

        btn_menu = (Button)findViewById(R.id.menuButton);
        btn_menu.setOnClickListener(this); //Set Listener to this button --> Start Button

        btn_save = (Button)findViewById(R.id.saveButton);
        btn_save.setOnClickListener(this); //Set Listener to this button --> Start Button

        score = (TextView)findViewById(R.id.scoreTxt);
        score.setText(String.valueOf(GameSystem.Instance.GetIntFromSave("Score")));

        num1 = (TextView)findViewById(R.id.num1Score);
        num1.setText(GameSystem.Instance.GetStringFromSave("Num1Str"));

        beforeScore = (TextView)findViewById(R.id.beforeScoreNum);
        beforeScore.setText(GameSystem.Instance.GetStringFromSave("Num2Str"));

        yourScore = (TextView)findViewById(R.id.yourScore);
        yourScore.setText(String.valueOf(GameSystem.Instance.GetIntFromSave("Score")));
    }

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent

        if (v == btn_menu)
        {
            Intent intent = new Intent();
            // intent --> to set to another class which another page or screen that we are launching.
            finish();
            intent.setClass(this, Mainmenu.class);
                StateManager.Instance.ChangeState("Mainmenu"); // Default is like a loading page

            startActivity(intent);
        }
        if (v == btn_save)
        {
            if (NameInputDialogFragment.IsShown)
                return;

            NameInputDialogFragment newNameInput = new NameInputDialogFragment();
            newNameInput.show(getFragmentManager(), "NameInput");
        if (v == btn_sharescore) {
            shareScore();
        }
    }

    @Override
    public void Render(Canvas _canvas)
    {
    }

    @Override
    public void OnEnter(SurfaceView _view) {

    }

    @Override
    public void OnExit() {

    }

    @Override
    public void Update(float _dt) {

    }

    @Override
    public String GetName() {
        return "Losescreen";
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // FACEBOOK
    public void shareScore(){
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        if(ShareDialog.canShow(SharePhotoContent.class)) {
            System.out.println("photoShown");
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(image)
                    .setCaption("Thank you for playing MGP2021. Your final score is " + GameSystem.Instance.GetIntFromSave("Score"))
                    .build();

            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();

            share_Dialog.show(content);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    // FACEBOOK
}
