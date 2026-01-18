/*     */ package com.google.crypto.tink.util;
/*     */ 
/*     */ import com.google.api.client.http.GenericUrl;
/*     */ import com.google.api.client.http.HttpHeaders;
/*     */ import com.google.api.client.http.HttpRequest;
/*     */ import com.google.api.client.http.HttpResponse;
/*     */ import com.google.api.client.http.HttpTransport;
/*     */ import com.google.api.client.http.javanet.NetHttpTransport;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.concurrent.GuardedBy;
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
/*     */ @Deprecated
/*     */ public class KeysDownloader
/*     */ {
/*  61 */   private static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */ 
/*     */   
/*  64 */   private static final NetHttpTransport DEFAULT_HTTP_TRANSPORT = (new NetHttpTransport.Builder())
/*  65 */     .build();
/*     */   
/*  67 */   private static final Executor DEFAULT_BACKGROUND_EXECUTOR = Executors.newCachedThreadPool();
/*     */ 
/*     */   
/*  70 */   private static final Pattern MAX_AGE_PATTERN = Pattern.compile("\\s*max-age\\s*=\\s*(\\d+)\\s*");
/*     */   
/*     */   private final Executor backgroundExecutor;
/*     */   
/*     */   private final HttpTransport httpTransport;
/*     */   
/*     */   private final Object fetchDataLock;
/*     */   
/*     */   private final Object instanceStateLock;
/*     */   
/*     */   private final String url;
/*     */   @GuardedBy("instanceStateLock")
/*     */   private Runnable pendingRefreshRunnable;
/*     */   @GuardedBy("instanceStateLock")
/*     */   private String cachedData;
/*     */   @GuardedBy("instanceStateLock")
/*     */   private long cachedTimeInMillis;
/*     */   @GuardedBy("instanceStateLock")
/*     */   private long cacheExpirationDurationInMillis;
/*     */   
/*     */   public KeysDownloader(Executor backgroundExecutor, HttpTransport httpTransport, String url) {
/*  91 */     validate(url);
/*  92 */     this.backgroundExecutor = backgroundExecutor;
/*  93 */     this.httpTransport = httpTransport;
/*  94 */     this.instanceStateLock = new Object();
/*  95 */     this.fetchDataLock = new Object();
/*  96 */     this.url = url;
/*  97 */     this.cachedTimeInMillis = Long.MIN_VALUE;
/*  98 */     this.cacheExpirationDurationInMillis = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String download() throws IOException {
/* 107 */     synchronized (this.instanceStateLock) {
/*     */       
/* 109 */       if (hasNonExpiredDataCached()) {
/*     */         
/* 111 */         if (shouldProactivelyRefreshDataInBackground()) {
/* 112 */           refreshInBackground();
/*     */         }
/* 114 */         return this.cachedData;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 120 */     synchronized (this.fetchDataLock) {
/*     */ 
/*     */       
/* 123 */       synchronized (this.instanceStateLock) {
/* 124 */         if (hasNonExpiredDataCached()) {
/* 125 */           return this.cachedData;
/*     */         }
/*     */       } 
/*     */       
/* 129 */       return fetchAndCacheData();
/*     */     } 
/*     */   }
/*     */   
/*     */   public HttpTransport getHttpTransport() {
/* 134 */     return this.httpTransport;
/*     */   }
/*     */   
/*     */   public String getUrl() {
/* 138 */     return this.url;
/*     */   }
/*     */   
/*     */   @GuardedBy("instanceStateLock")
/*     */   private boolean hasNonExpiredDataCached() {
/* 143 */     long currentTimeInMillis = getCurrentTimeInMillis();
/* 144 */     boolean cachedInFuture = (this.cachedTimeInMillis > currentTimeInMillis);
/* 145 */     boolean cacheExpired = (this.cachedTimeInMillis + this.cacheExpirationDurationInMillis <= currentTimeInMillis);
/*     */     
/* 147 */     return (!cacheExpired && !cachedInFuture);
/*     */   }
/*     */ 
/*     */   
/*     */   @GuardedBy("instanceStateLock")
/*     */   private boolean shouldProactivelyRefreshDataInBackground() {
/* 153 */     return (this.cachedTimeInMillis + this.cacheExpirationDurationInMillis / 2L <= getCurrentTimeInMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long getCurrentTimeInMillis() {
/* 162 */     return System.currentTimeMillis();
/*     */   }
/*     */   @GuardedBy("fetchDataLock")
/*     */   @CanIgnoreReturnValue
/*     */   private String fetchAndCacheData() throws IOException {
/*     */     String data;
/* 168 */     long currentTimeInMillis = getCurrentTimeInMillis();
/*     */     
/* 170 */     HttpRequest httpRequest = this.httpTransport.createRequestFactory().buildGetRequest(new GenericUrl(this.url));
/* 171 */     HttpResponse httpResponse = httpRequest.execute();
/* 172 */     if (httpResponse.getStatusCode() != 200) {
/* 173 */       throw new IOException("Unexpected status code = " + httpResponse.getStatusCode());
/*     */     }
/*     */     
/* 176 */     InputStream contentStream = httpResponse.getContent();
/*     */     try {
/* 178 */       InputStreamReader reader = new InputStreamReader(contentStream, UTF_8);
/* 179 */       data = readerToString(reader);
/*     */     } finally {
/* 181 */       contentStream.close();
/*     */     } 
/* 183 */     synchronized (this.instanceStateLock) {
/* 184 */       this.cachedTimeInMillis = currentTimeInMillis;
/* 185 */       this
/* 186 */         .cacheExpirationDurationInMillis = getExpirationDurationInSeconds(httpResponse.getHeaders()) * 1000L;
/* 187 */       this.cachedData = data;
/*     */     } 
/* 189 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String readerToString(Reader reader) throws IOException {
/* 194 */     reader = new BufferedReader(reader);
/* 195 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     int c;
/* 197 */     while ((c = reader.read()) != -1) {
/* 198 */       stringBuilder.append((char)c);
/*     */     }
/* 200 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long getExpirationDurationInSeconds(HttpHeaders httpHeaders) {
/* 211 */     long expirationDurationInSeconds = 0L;
/* 212 */     if (httpHeaders.getCacheControl() != null) {
/* 213 */       for (String arg : httpHeaders.getCacheControl().split(",")) {
/* 214 */         Matcher m = MAX_AGE_PATTERN.matcher(arg);
/* 215 */         if (m.matches()) {
/* 216 */           expirationDurationInSeconds = Long.valueOf(m.group(1)).longValue();
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 221 */     if (httpHeaders.getAge() != null) {
/* 222 */       expirationDurationInSeconds -= httpHeaders.getAge().longValue();
/*     */     }
/* 224 */     return Math.max(0L, expirationDurationInSeconds);
/*     */   }
/*     */ 
/*     */   
/*     */   public void refreshInBackground() {
/* 229 */     Runnable refreshRunnable = newRefreshRunnable();
/* 230 */     synchronized (this.instanceStateLock) {
/* 231 */       if (this.pendingRefreshRunnable != null) {
/*     */         return;
/*     */       }
/* 234 */       this.pendingRefreshRunnable = refreshRunnable;
/*     */     } 
/*     */     try {
/* 237 */       this.backgroundExecutor.execute(refreshRunnable);
/* 238 */     } catch (Throwable e) {
/* 239 */       synchronized (this.instanceStateLock) {
/*     */         
/* 241 */         if (this.pendingRefreshRunnable == refreshRunnable) {
/* 242 */           this.pendingRefreshRunnable = null;
/*     */         }
/*     */       } 
/* 245 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Runnable newRefreshRunnable() {
/* 250 */     return new Runnable()
/*     */       {
/*     */         public void run() {
/* 253 */           synchronized (KeysDownloader.this.fetchDataLock) {
/*     */             try {
/* 255 */               KeysDownloader.this.fetchAndCacheData();
/* 256 */             } catch (IOException iOException) {
/*     */             
/*     */             } finally {
/* 259 */               synchronized (KeysDownloader.this.instanceStateLock) {
/*     */                 
/* 261 */                 if (KeysDownloader.this.pendingRefreshRunnable == this) {
/* 262 */                   KeysDownloader.this.pendingRefreshRunnable = null;
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private static void validate(String url) {
/*     */     try {
/* 273 */       URL tmp = new URL(url);
/* 274 */       if (!tmp.getProtocol().toLowerCase(Locale.US).equals("https")) {
/* 275 */         throw new IllegalArgumentException("url must point to a HTTPS server");
/*     */       }
/* 277 */     } catch (MalformedURLException ex) {
/* 278 */       throw new IllegalArgumentException(ex);
/*     */     } 
/*     */   }
/*     */   public static class Builder { private HttpTransport httpTransport;
/*     */     
/*     */     public Builder() {
/* 284 */       this.httpTransport = (HttpTransport)KeysDownloader.DEFAULT_HTTP_TRANSPORT;
/* 285 */       this.executor = KeysDownloader.DEFAULT_BACKGROUND_EXECUTOR;
/*     */     }
/*     */     private Executor executor; private String url;
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setUrl(String val) {
/* 291 */       this.url = val;
/* 292 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setExecutor(Executor val) {
/* 298 */       this.executor = val;
/* 299 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @CanIgnoreReturnValue
/*     */     public Builder setHttpTransport(HttpTransport httpTransport) {
/* 310 */       this.httpTransport = httpTransport;
/* 311 */       return this;
/*     */     }
/*     */     
/*     */     public KeysDownloader build() {
/* 315 */       if (this.url == null) {
/* 316 */         throw new IllegalArgumentException("must provide a url with {#setUrl}");
/*     */       }
/* 318 */       return new KeysDownloader(this.executor, this.httpTransport, this.url);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tin\\util\KeysDownloader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */