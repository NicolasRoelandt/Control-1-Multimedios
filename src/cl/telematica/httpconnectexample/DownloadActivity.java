package cl.telematica.httpconnectexample;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import cl.telematica.httpconnectexample.asynctask.DownloadManager;
import cl.telematica.httpconnectexample.interfaces.DownloadListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadActivity extends Activity implements DownloadListener {
	
	private ProgressBar progressBar;
	private TextView text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		text = (TextView) findViewById(R.id.textView1);
		
		new DownloadManager(this, 10000, 15000, "GET")
					.execute(getString(R.string.page_url));
	}

	@Override
	public void onRequestStart() {
		if(progressBar.getVisibility() == View.GONE)
			progressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void onRequestComplete(String data) {
		if(progressBar.getVisibility() == View.VISIBLE)
			progressBar.setVisibility(View.GONE);
		try{
		JSONArray array = new JSONArray(data);
		int arraySize = array.length();
		for(int i =0; i<arraySize;i++)
		{
		   JSONObject o = array.getJSONObject(i);
		   String value = o.getString("title");
		   text.setText(value);
		}
		 //  View view = (View) findViewById(R.layout.activity_download); //the layout you set in `setContentView()`
//		   LinearLayout picLL = new LinearLayout(CurrentActivity.this);
//		   picLL.layout(0, 0, 100, 0);
//		   picLL.setOrientation(LinearLayout.HORIZONTAL);
//		   ((ViewGroup) view).addView(picLL);
//		   NetworkImageView myImage;
//		   myImage = setImageURL(o.getString("image"));
		   
		} catch (Exception e) {} ;
		
	}

	@Override
	public void onError(String error, int code) {
		if(progressBar.getVisibility() == View.VISIBLE)
			progressBar.setVisibility(View.GONE);
		text.setText("No funciono");
	}	

}
