package com.bright.bright.settings;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bright.bright.R;
import com.bright.bright.checkout.Checkout;
import com.bright.bright.home.HomeScreen;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by bryanjordan on 12/2/18.
 */

public class settings_facebookLogin_fragment extends Fragment {

    View facebookCardLayout;
    CallbackManager callbackManager = CallbackManager.Factory.create();
    LoginButton loginButton;
    AccessToken accessToken;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        facebookCardLayout = inflater.inflate(R.layout.athome_facebook_fragment, container, false);

        loginButton = (LoginButton) facebookCardLayout.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile"));
        loginButton.setFragment(this);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d("TAG", "Success login");
                final String user_id = loginResult.getAccessToken().getUserId();
                accessToken = AccessToken.getCurrentAccessToken();

                        GraphRequest request = GraphRequest.newGraphPathRequest(
                        accessToken,
                        "/100024403962374/picture",
                        new GraphRequest.Callback() {
                            @Override
                            public void onCompleted(GraphResponse response) {
                                new ChangeImage().execute(user_id);
                            }
                        });
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        return  facebookCardLayout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class ChangeImage extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... params){
            Log.d("TAG", "PARAMS: " + params[0]);
            Bitmap mIcon1 = null;
            try {
                Log.d("TAG", "https://graph.facebook.com/" + params[0] + "/picture?type=small");

                HttpURLConnection.setFollowRedirects(true);
                URL img_value = new URL("https://graph.facebook.com/" + params[0] + "/picture?type=small");
                mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
            }
            catch (IOException e){
                Log.d("TAG", "LOL");
            }
            return mIcon1;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            ImageButton userPic = (ImageButton) getActivity().findViewById(R.id.settingsToolbar);
            userPic.setImageBitmap(Bitmap.createScaledBitmap(result, 150, 150, false));
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            result.compress(Bitmap.CompressFormat.PNG, 100, bs);

            Intent openHome = new Intent(getActivity(), HomeScreen.class);
            openHome.putExtra("byteArray", bs.toByteArray());
            startActivity(openHome);
            ((Activity) getActivity()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}

    }

}
