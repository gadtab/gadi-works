package il.co.gadiworks.androidtwitter;

import java.sql.Date;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final static String TAG = "Twitter";
	
	// consumer key
	public final static String TWITTER_OAUTH_KEY = "squDavNrviFJfmtT37FkTw";
	// consumer secret
	public final static String TWITTER_OAUTH_SECRET = "Ryn5yGrBGgX9ZPHsW13cn2xPUutDDoOzgcm35hrcvM";
	
//	private final String CALLBACK_URL = "http://otweet.com/authenticated";
	private final String CALLBACK_URL = "twitter-client:///";
	
	private final String REQUEST_TOKEN_ENDPOINT_URL = "http://api.twitter.com/oauth/request_token";
	private final String ACCESS_TOKEN_ENDPOINT_URL = "http://api.twitter.com/oauth/access_token";
	private final String AUTHORIZATION_WEBSITE_URL = "http://api.twitter.com/oauth/authorize";
	
	private CommonsHttpOAuthConsumer httpOAuthConsumer;
	private OAuthProvider httpOAuthProvider;
	private Twitter myTweet;
	
	private Button btnTweet;
	private EditText etTweet;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.i(TAG, "Twitter has started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnTweet = (Button) findViewById(R.id.btnTweet);
        etTweet = (EditText) findViewById(R.id.etTwit);
        
        doOauth();
    }

    /**
    * Opens the browser using signpost jar with application specific 
    * consumerKey and consumerSecret.
    */
	private void doOauth() {
		Log.d(TAG, "doOauth has started");
		try {
			httpOAuthConsumer = new CommonsHttpOAuthConsumer(TWITTER_OAUTH_KEY, TWITTER_OAUTH_SECRET);
			Log.d(TAG, "httpOAuthConsumer = " + httpOAuthConsumer);
			httpOAuthProvider = new DefaultOAuthProvider(REQUEST_TOKEN_ENDPOINT_URL, 
														 ACCESS_TOKEN_ENDPOINT_URL, 
														 AUTHORIZATION_WEBSITE_URL);
			Log.d(TAG, "httpOAuthProvider = " + httpOAuthProvider);
			String authUrl = httpOAuthProvider.retrieveRequestToken(httpOAuthConsumer, CALLBACK_URL);
			Log.d(TAG, "authUrl = " + authUrl);
			
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
		}
		catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			Log.e(TAG, "There has been an exception = " + e);
		}
	}

	/**
	* After use authorizes this is the function where we get back callback with
	* user specific token and secret token. You might want to store this token
	* for future use. 
	*/
	@Override
	protected void onNewIntent(Intent intent) {
		Log.i(TAG, "onNewIntent has started");
		super.onNewIntent(intent);
		
		Uri uri = intent.getData();
		Log.d(TAG, "uri = " + uri);
		
		if (uri != null && uri.toString().startsWith(CALLBACK_URL)) {
			String verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);
			Log.d(TAG, "verifier = " + verifier);
			
			try {
				// this will populate token and token_secret in consumer
				httpOAuthProvider.retrieveAccessToken(httpOAuthConsumer, verifier);
				
				// TODO: Should store token and token_secret in the applications setting!!!!
				
				final AccessToken accessToken = new AccessToken(httpOAuthConsumer.getToken(), httpOAuthConsumer.getTokenSecret());
				Log.d(TAG, "accessToken = " + accessToken);
				
				btnTweet.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// initialize Twitter4J
						myTweet = new TwitterFactory().getInstance();
						myTweet.setOAuthConsumer(TWITTER_OAUTH_KEY, TWITTER_OAUTH_SECRET);
						myTweet.setOAuthAccessToken(accessToken);
						Log.d(TAG, "myTweet = " + myTweet);
						
						// create a tweet
						Date date = new Date(System.currentTimeMillis());
						Log.d(TAG, "date = " + date);
						String tweet = null;
						if (etTweet.getText() != null && !"".equals(etTweet.getText().toString())) {
							tweet = date + ": " + etTweet.getText().toString();
							Log.d(TAG, "tweet = " + tweet);
							
							// send the tweet
							try {
								myTweet.updateStatus(tweet);
								
								// feedback for the user...
								Toast.makeText(getApplicationContext(), "Tweet sent: " + tweet, Toast.LENGTH_SHORT).show();
							} catch (TwitterException e) {
								Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
								Log.e(TAG, "There has been an exception = " + e);
							}
//							btnLogin.setVisibility(Button.GONE);
						}
						else {
							Toast.makeText(getApplicationContext(), "Please enter a message", Toast.LENGTH_SHORT).show();
							Log.d(TAG, "User didn't put a message!");
						}
					}
				});
			}
			catch (Exception e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
				Log.e(TAG, "There has been an exception = " + e);
			}
		}
	}
}