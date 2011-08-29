package il.co.gadiworks.tutorial;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OurViewClient extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView v, String url) {
		v.loadUrl(url);
		return true;
	}
}
