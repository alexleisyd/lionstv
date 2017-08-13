package au.com.lionslogistics.lionstv.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import au.com.lionslogistics.lionstv.R;
import au.com.lionslogistics.lionstv.service.AuthenticationService;
import au.com.lionslogistics.lionstv.service.UserService;
import au.com.lionslogistics.lionstv.util.Constants;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements AuthenticationService{

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private LoginActivity mContext=this;
    // UI references.
    private TextView mEmailView;
    private EditText mPasswordView;
    private boolean isLoginInProgress=false;
    private Button mEmailSignInButton;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (TextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Determine whether auto login is setup
        SharedPreferences preferences=getSharedPreferences(Constants.AUTH_PREF,MODE_PRIVATE);
        String token=preferences.getString("token","");
        String username=preferences.getString("username","");
        String password=preferences.getString("password","");
        if (!token.equals("")){
            mEmailView.setText(username);
            mPasswordView.setText(password);
            attemptLogin();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (isLoginInProgress) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            doSignIn();
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }


    @Override
    public void onAuthenticationSuccessful() {
        resetView();
        isLoginInProgress=false;
        SharedPreferences.Editor editor= getSharedPreferences(Constants.AUTH_PREF,MODE_PRIVATE).edit();
        editor.putString("token",UserService.getInstance().getToken());
        editor.putString("username",mEmailView.getText().toString());
        editor.putString("password",mPasswordView.getText().toString());
        editor.apply();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void onAuthenticationFailed() {
        resetView();
        AlertDialog.Builder builder=new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.Theme_Leanback));
        String msg=getString(R.string.auth_error);
        builder.setMessage(msg);
        builder.setTitle(R.string.error);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    @Override
    public void onNetworkError() {
        resetView();
        AlertDialog.Builder builder=new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.Theme_Leanback));
        String msg=getString(R.string.network_error);
        builder.setMessage(msg);
        builder.setTitle(R.string.error);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void resetView(){
        mProgressDialog.dismiss();
        isLoginInProgress=false;
        mEmailSignInButton.setText(R.string.action_sign_in);
        mEmailView.setEnabled(true);
        mPasswordView.setEnabled(true);
    }

    private void doSignIn(){
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setTitle(R.string.prompt_sign_in_progress);
        mProgressDialog.setMessage(getString(R.string.prompt_sing_in_progress_long));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        mEmailSignInButton.setText(R.string.prompt_sign_in_progress);
        mEmailView.setEnabled(false);
        mPasswordView.setEnabled(false);
        isLoginInProgress=true;
        UserService.getInstance().bindActivity(mContext).authenticate(mEmailView.getText().toString(),mPasswordView.getText().toString());
    }
}

