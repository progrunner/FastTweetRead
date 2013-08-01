package com.example.fasttweetread;

import java.util.List;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class FastTweetReadActivity extends ListActivity{
	protected final String TAG = getClass().getName();
	private static String consumerKey="d60C4tYGjbZ0peLUuWQRQ";
	private static String consumerSecret="CKnBcQBPb8WAfGKUgbK7S8jz3qp8hKmwfHrewBBT4";
	private String access_url="	https://api.twitter.com/oauth/access_token";
	private String request_url="https://api.twitter.com/oauth/request_token";
	private String authorize_url="https://api.twitter.com/oauth/authorize";
	private static String access_token="1580102774-PEJ1PsQnLwteUl6n0fUiVFPTsUb8pWa2m6gBTTQ";
	private static String access_secret="yApfIQxIk4FTacD7BY2MC7eGvxlALiYyDkP3oPYw";
	Twitter twitter;
	SharedPreferences prefs;
	String[] timeline;
	ListView list;
	List<Status> tl;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.homepage);
		final EditText text = (EditText)findViewById(R.id.text);
		Button button = (Button)findViewById(R.id.button);
		/**
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Intent i = new Intent(getApplicationContext(),RequestToken.class);
		startActivity(i);
		*/
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Editable str = text.getText();
				try {
					search(str);
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				
			}
		});
	};
	
	public static void search(Editable str) throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer(consumerKey,consumerSecret);
		twitter.setOAuthAccessToken(new AccessToken(access_token, access_secret));
		Query query = new Query("#rus");
		try {
			QueryResult result = twitter.search(query);
			for (Status status : result.getTweets()) {
			System.out.println("@" + status.getUser().getScreenName() + ":"
			+ status.getText());
			}
		} catch (TwitterException e) {
			System.err.print("Failed to search tweets: " + e.getMessage());
			//e.printStackTrace();
		}
	}

	/**
	public void onStart(){
		super.onStart();
		Uri uri = getIntent().getData();
		Thread homepage = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					final String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
					final String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
					AccessToken at = new AccessToken(token, secret);
					twitter = new TwitterFactory().getInstance();
					twitter.setOAuthConsumer(token, secret);
					twitter.setOAuthAccessToken(at);
					try {
						timeline = (String[]) twitter.getHomeTimeline().toArray();
					} catch (Exception e) {
						Log.e(TAG, "Error while timeline change");
					}
					setListAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, timeline));
				} catch (Exception e) {
					Log.e(TAG, "Error while loading homepage");
				}
				
			}
			
		});
		homepage.start();
	}
	*/
}
