/*     */ package com.nimbusds.jose.util;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ThreadSafe
/*     */ public class DefaultResourceRetriever
/*     */   extends AbstractRestrictedResourceRetriever
/*     */   implements RestrictedResourceRetriever
/*     */ {
/*     */   private boolean disconnectAfterUse;
/*     */   private final SSLSocketFactory sslSocketFactory;
/*     */   private Proxy proxy;
/*     */   
/*     */   public DefaultResourceRetriever() {
/*  77 */     this(0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultResourceRetriever(int connectTimeout, int readTimeout) {
/*  92 */     this(connectTimeout, readTimeout, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultResourceRetriever(int connectTimeout, int readTimeout, int sizeLimit) {
/* 108 */     this(connectTimeout, readTimeout, sizeLimit, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultResourceRetriever(int connectTimeout, int readTimeout, int sizeLimit, boolean disconnectAfterUse) {
/* 136 */     this(connectTimeout, readTimeout, sizeLimit, disconnectAfterUse, (SSLSocketFactory)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultResourceRetriever(int connectTimeout, int readTimeout, int sizeLimit, boolean disconnectAfterUse, SSLSocketFactory sslSocketFactory) {
/* 167 */     super(connectTimeout, readTimeout, sizeLimit);
/* 168 */     this.disconnectAfterUse = disconnectAfterUse;
/* 169 */     this.sslSocketFactory = sslSocketFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean disconnectsAfterUse() {
/* 186 */     return this.disconnectAfterUse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisconnectsAfterUse(boolean disconnectAfterUse) {
/* 208 */     this.disconnectAfterUse = disconnectAfterUse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Proxy getProxy() {
/* 221 */     return this.proxy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProxy(Proxy proxy) {
/* 235 */     this.proxy = proxy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Resource retrieveResource(URL url) throws IOException {
/* 243 */     URLConnection con = null; try {
/*     */       String content;
/* 245 */       if ("file".equals(url.getProtocol())) {
/* 246 */         con = openFileConnection(url);
/*     */       }
/*     */       else {
/*     */         
/* 250 */         con = openConnection(url);
/*     */       } 
/*     */       
/* 253 */       con.setConnectTimeout(getConnectTimeout());
/* 254 */       con.setReadTimeout(getReadTimeout());
/*     */       
/* 256 */       if (con instanceof HttpsURLConnection && this.sslSocketFactory != null) {
/* 257 */         ((HttpsURLConnection)con).setSSLSocketFactory(this.sslSocketFactory);
/*     */       }
/*     */       
/* 260 */       if (con instanceof HttpURLConnection && getHeaders() != null && !getHeaders().isEmpty()) {
/* 261 */         for (Map.Entry<String, List<String>> entry : getHeaders().entrySet()) {
/* 262 */           for (String value : entry.getValue()) {
/* 263 */             con.addRequestProperty(entry.getKey(), value);
/*     */           }
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 269 */       InputStream inputStream = getInputStream(con, getSizeLimit()); 
/* 270 */       try { content = IOUtils.readInputStreamToString(inputStream, StandardCharset.UTF_8);
/* 271 */         if (inputStream != null) inputStream.close();  } catch (Throwable throwable) { if (inputStream != null)
/*     */           try { inputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 273 */        if (con instanceof HttpURLConnection) {
/*     */         
/* 275 */         HttpURLConnection httpCon = (HttpURLConnection)con;
/* 276 */         int statusCode = httpCon.getResponseCode();
/* 277 */         String statusMessage = httpCon.getResponseMessage();
/*     */ 
/*     */         
/* 280 */         if (statusCode > 299 || statusCode < 200) {
/* 281 */           throw new IOException("HTTP " + statusCode + ": " + statusMessage);
/*     */         }
/*     */       } 
/*     */       
/* 285 */       String contentType = (con instanceof HttpURLConnection) ? con.getContentType() : null;
/*     */       
/* 287 */       return new Resource(content, contentType);
/*     */     }
/* 289 */     catch (Exception e) {
/*     */       String content;
/* 291 */       if (content instanceof IOException) {
/* 292 */         throw content;
/*     */       }
/*     */       
/* 295 */       throw new IOException("Couldn't open URL connection: " + content.getMessage(), content);
/*     */     } finally {
/*     */       
/* 298 */       if (this.disconnectAfterUse && con instanceof HttpURLConnection) {
/* 299 */         ((HttpURLConnection)con).disconnect();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected HttpURLConnection openConnection(URL url) throws IOException {
/* 321 */     return openHTTPConnection(url);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpURLConnection openHTTPConnection(URL url) throws IOException {
/* 338 */     if (this.proxy != null) {
/* 339 */       return (HttpURLConnection)url.openConnection(this.proxy);
/*     */     }
/* 341 */     return (HttpURLConnection)url.openConnection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected URLConnection openFileConnection(URL url) throws IOException {
/* 359 */     return url.openConnection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InputStream getInputStream(URLConnection con, int sizeLimit) throws IOException {
/* 366 */     InputStream inputStream = con.getInputStream();
/*     */     
/* 368 */     return (sizeLimit > 0) ? new BoundedInputStream(inputStream, getSizeLimit()) : inputStream;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jos\\util\DefaultResourceRetriever.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */