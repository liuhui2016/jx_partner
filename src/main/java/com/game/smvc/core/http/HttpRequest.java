package com.game.smvc.core.http;

import com.game.smvc.util.ByteOutputStream;
import com.game.smvc.util.FileUtil;
import java.io.File;
import java.io.PrintStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpRequest {
	public static final int CON_TIME_OUT_MS = 50000;
	public static final int SO_TIME_OUT_MS = 20000;
	public static final int MAX_CONNECTIONS_PER_HOST = 20;
	public static final int MAX_TOTAL_CONNECTIONS = 200;
	private int conTimeOutMs;
	private int soTimeOutMs;
	public CookieStore cookieStore = null;
	private final Logger log = LogManager.getLogger(HttpRequest.class);
	private DefaultHttpClient httpClient;

	public HttpRequest() {
		this(20, 200, 50000, 20000, null, null);
	}

	public HttpRequest(HttpHost proxy) {
		this(20, 200, 50000, 20000, null, proxy);
	}

	public HttpRequest(int maxConnectionsPerHost, int maxTotalConnections,
			int conTimeOutMs, int soTimeOutMs, List<RouteCfg> routeCfgList,
			HttpHost proxy) {
		this.conTimeOutMs = conTimeOutMs;
		this.soTimeOutMs = soTimeOutMs;

		SchemeRegistry supportedSchemes = new SchemeRegistry();
		supportedSchemes.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));
		supportedSchemes.register(new Scheme("https", 443, SSLSocketFactory
				.getSocketFactory()));
		ThreadSafeClientConnManager connectionManager = new ThreadSafeClientConnManager(
				supportedSchemes);

		HttpParams httpParams = new SyncBasicHttpParams();
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);

		httpParams.setParameter("http.connection.timeout",
				Integer.valueOf(conTimeOutMs));
		httpParams.setParameter("http.socket.timeout",
				Integer.valueOf(soTimeOutMs));

		HttpClientParams.setCookiePolicy(httpParams, "compatibility");

		httpParams
				.setParameter("http.useragent",
						"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
		httpParams.setParameter("http.protocol.content-charset", "UTF-8");

		HttpProtocolParams.setUseExpectContinue(httpParams, false);

		connectionManager.setDefaultMaxPerRoute(maxConnectionsPerHost);
		connectionManager.setMaxTotal(maxTotalConnections);
		if (routeCfgList != null) {
			for (RouteCfg routeCfg : routeCfgList) {
				HttpHost localhost = new HttpHost(routeCfg.getHost(),
						routeCfg.getPort());
				connectionManager.setMaxForRoute(new HttpRoute(localhost),
						routeCfg.getMaxConnetions());
			}
		}
		this.httpClient = new DefaultHttpClient(connectionManager, httpParams);
		if (proxy != null) {
			this.httpClient.getParams().setParameter(
					"http.route.default-proxy", proxy);
		}
	}

	public String simpleHttpGet(String url, String queryString)
			throws Exception {
		String responseData = null;
		if ((queryString != null) && (!queryString.equals(""))) {
			url = url + "?" + queryString;
		}
		this.log.info("HttpRequest simpleHttpGet [1] url = " + url);

		HttpGet httpGet = new HttpGet(url);
		httpGet.getParams().setParameter("http.socket.timeout",
				Integer.valueOf(this.conTimeOutMs));

		HttpResponse response = this.httpClient.execute(httpGet);
		this.log.info("HttpRequest simpleHttpGet [2] StatusLine : "
				+ response.getStatusLine());
		responseData = EntityUtils.toString(response.getEntity());
		httpGet.abort();
		this.log.info("HttpRequest simpleHttpGet [3] Response = "
				+ responseData.toString());

		return responseData.toString();
	}

	public String httpGZIPGet(String url, String queryString) throws Exception {
		return httpGZIPGet(url, queryString, "utf-8");
	}

	public String httpGet(String url, String queryString) throws Exception {
		return httpGet(url, queryString, "utf-8");
	}

	public ByteOutputStream httpGetEntity(String url, String queryString)
			throws Exception {
		return httpGetEntity(url, queryString, "utf-8");
	}

	public String httpGZIPGet(String url, String queryString, String charSet)
			throws Exception {
		StringBuilder responseData = new StringBuilder();
		if ((queryString != null) && (!queryString.equals(""))) {
			url = url + "?" + queryString;
		}
		this.log.info("HttpRequest httpGet [1] url = " + url);

		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpGet.getParams().setParameter("http.socket.timeout",
				Integer.valueOf(this.conTimeOutMs));

		HttpResponse response = this.httpClient.execute(httpGet);
		this.log.info("HttpRequest httpGet [2] StatusLine : "
				+ response.getStatusLine());
		Header contentType = response.getFirstHeader("Content-Type");
		System.out.println(contentType.getValue());
		try {
			byte[] b = new byte[2048];
			GZIPInputStream gzin = new GZIPInputStream(response.getEntity()
					.getContent());
			int length = 0;
			while ((length = gzin.read(b)) != -1) {
				responseData.append(new String(b, 0, length, charSet));
			}
			gzin.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpGet.abort();
		}
		this.log.info("HttpRequest httpGet [3] Response = "
				+ responseData.toString());

		return responseData.toString();
	}

	public String httpGet(String url, String queryString, String charSet)
			throws Exception {
		String responseData = null;
		if ((queryString != null) && (!queryString.equals(""))) {
			url = url + "?" + queryString;
		}
		this.log.info("HttpRequest httpGet [1] url = " + url);

		HttpGet httpGet = new HttpGet(url);
		httpGet.getParams().setParameter("http.socket.timeout",
				Integer.valueOf(this.conTimeOutMs));

		HttpResponse response = this.httpClient.execute(httpGet);
		this.log.info("HttpRequest httpGet [2] StatusLine : "
				+ response.getStatusLine());
		try {
			responseData = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpGet.abort();
		}
		this.log.info("HttpRequest httpGet [3] Response = " + responseData);

		return responseData;
	}

	public ByteOutputStream httpGetEntity(String url, String queryString,
			String charSet) throws Exception {
		ByteOutputStream entity = null;
		if ((queryString != null) && (!queryString.equals(""))) {
			url = url + "?" + queryString;
		}
		this.log.info("HttpRequest httpGet [1] url = " + url);

		HttpGet httpGet = new HttpGet(url);
		httpGet.getParams().setParameter("http.socket.timeout",
				Integer.valueOf(this.conTimeOutMs));

		HttpResponse response = this.httpClient.execute(httpGet);
		this.log.info("HttpRequest httpGet [2] StatusLine : "
				+ response.getStatusLine());
		this.cookieStore = this.httpClient.getCookieStore();
		try {
			entity = FileUtil.getByteOutputStream(response.getEntity()
					.getContent());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpGet.abort();
		}
		this.log.info("HttpRequest httpGet [3] Response = " + entity);

		return entity;
	}

	public String httpGZIPPost(String url, String queryString) throws Exception {
		return httpGZIPPost(url, queryString, "utf-8");
	}

	public String httpPost(String url, String queryString, boolean cookie)
			throws Exception {
		return httpPost(url, queryString, "utf-8", cookie);
	}

	public String httpGZIPPost(String url, String queryString, String charSet)
			throws Exception {
		StringBuilder responseData = new StringBuilder();
		URI tmpUri = new URI(url);
		URI uri = URIUtils.createURI(tmpUri.getScheme(), tmpUri.getHost(),
				tmpUri.getPort(), tmpUri.getPath(), queryString, null);
		this.log.info("HttpRequest httpPost [1] url = " + uri.toURL());

		HttpPost httpPost = new HttpPost(uri);
		httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpPost.getParams().setParameter("http.socket.timeout",
				Integer.valueOf(this.conTimeOutMs));
		if ((queryString != null) && (!queryString.equals(""))) {
			StringEntity reqEntity = new StringEntity(queryString);

			httpPost.setEntity(reqEntity);
		}
		HttpResponse response = this.httpClient.execute(httpPost);
		this.log.info("HttpRequest httpPost [2] StatusLine = "
				+ response.getStatusLine());
		try {
			byte[] b = new byte[2048];
			GZIPInputStream gzin = new GZIPInputStream(response.getEntity()
					.getContent());
			int length = 0;
			while ((length = gzin.read(b)) != -1) {
				responseData.append(new String(b, 0, length, charSet));
			}
			gzin.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpPost.abort();
		}
		this.log.info("HttpRequest httpPost [3] Response = "
				+ responseData.toString());
		return responseData.toString();
	}

	public String httpPost(String url, String queryString, String charSet,
			boolean saveCookie) throws Exception {
		String responseData = null;
		URI tmpUri = new URI(url);
		URI uri = URIUtils.createURI(tmpUri.getScheme(), tmpUri.getHost(),
				tmpUri.getPort(), tmpUri.getPath(), queryString, null);
		this.log.info("HttpRequest httpPost [1] url = " + uri.toURL());

		HttpPost httpPost = new HttpPost(uri);
		if (this.cookieStore != null) {
			List<Cookie> cookies = this.cookieStore.getCookies();
			StringBuffer cookieHeader = new StringBuffer();
			for (int i = 1; i < cookies.size(); i++) {
				Cookie cookie = (Cookie) cookies.get(i);
				cookieHeader.append(cookie.getName()).append("=")
						.append(cookie.getValue()).append(";");
			}
			httpPost.setHeader("Cookie", cookieHeader.toString());
		}
		httpPost.getParams().setParameter("http.socket.timeout",
				Integer.valueOf(this.conTimeOutMs));
		if ((queryString != null) && (!queryString.equals(""))) {
			StringEntity reqEntity = new StringEntity(queryString);

			httpPost.setEntity(reqEntity);
		}
		HttpResponse response = this.httpClient.execute(httpPost);
		if (saveCookie) {
			this.cookieStore = this.httpClient.getCookieStore();
		}
		this.log.info("HttpRequest httpPost [2] StatusLine = "
				+ response.getStatusLine());
		try {
			responseData = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpPost.abort();
		}
		this.log.info("HttpRequest httpPost [3] Response = " + responseData);
		return responseData;
	}

	public String httpPostWithFile(String url, String queryString,
			List<NameValuePair> files) throws Exception {
		StringBuilder responseData = new StringBuilder();

		URI tmpUri = new URI(url);
		URI uri = URIUtils.createURI(tmpUri.getScheme(), tmpUri.getHost(),
				tmpUri.getPort(), tmpUri.getPath(), queryString, null);
		this.log.info("HttpRequest httpPostWithFile [1]  uri = " + uri.toURL());
		MultipartEntity mpEntity = new MultipartEntity();
		HttpPost httpPost = new HttpPost(uri);
		httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");

		List<NameValuePair> queryParamList = HttpStrOperate
				.getQueryParamsList(queryString);
		for (NameValuePair queryParam : queryParamList) {
			StringBody stringBody = new StringBody(queryParam.getValue(),
					Charset.forName("UTF-8"));
			FormBodyPart fbp = new FormBodyPart(queryParam.getName(),
					stringBody);
			mpEntity.addPart(fbp);
		}
		for (NameValuePair param : files) {
			String filePath = param.getValue();
			File targetFile = new File(filePath);
			this.log.info("---------- File Path = " + filePath
					+ "\n---------------- MIME Types = "
					+ HttpRequestUtil.getContentType(targetFile));
			FileBody fileBody = new FileBody(targetFile,
					HttpRequestUtil.getContentType(targetFile), "UTF-8");
			FormBodyPart fbp = new FormBodyPart(param.getName(), fileBody);
			mpEntity.addPart(fbp);
		}
		httpPost.setEntity(mpEntity);
		HttpResponse response = this.httpClient.execute(httpPost);
		this.log.info("HttpRequest httpPostWithFile [2] StatusLine = "
				+ response.getStatusLine());
		try {
			byte[] b = new byte[2048];
			GZIPInputStream gzin = new GZIPInputStream(response.getEntity()
					.getContent());
			int length = 0;
			while ((length = gzin.read(b)) != -1) {
				responseData.append(new String(b, 0, length));
			}
			gzin.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpPost.abort();
		}
		this.log.info("HttpRequest httpPostWithFile [3] Response = "
				+ responseData.toString());
		return responseData.toString();
	}

	public void shutdownConnection() {
		try {
			this.httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getConTimeOutMs() {
		return this.conTimeOutMs;
	}

	public void setConTimeOutMs(int conTimeOutMs) {
		this.conTimeOutMs = conTimeOutMs;
	}

	public int getSoTimeOutMs() {
		return this.soTimeOutMs;
	}

	public void setSoTimeOutMs(int soTimeOutMs) {
		this.soTimeOutMs = soTimeOutMs;
	}

	public HttpClient getHttpClient() {
		return this.httpClient;
	}

	public void setHttpClient(DefaultHttpClient httpClient) {
		this.httpClient = httpClient;
	}
}
