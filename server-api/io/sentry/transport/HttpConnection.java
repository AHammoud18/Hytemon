/*     */ package io.sentry.transport;
/*     */ 
/*     */ import io.sentry.RequestDetails;
/*     */ import io.sentry.SentryEnvelope;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.SentryOptions;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Proxy;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Map;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.jetbrains.annotations.TestOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class HttpConnection
/*     */ {
/*  30 */   private static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private final Proxy proxy;
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private final RequestDetails requestDetails;
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private final SentryOptions options;
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private final RateLimiter rateLimiter;
/*     */ 
/*     */   
/*     */   public HttpConnection(@NotNull SentryOptions options, @NotNull RequestDetails requestDetails, @NotNull RateLimiter rateLimiter) {
/*  50 */     this(options, requestDetails, AuthenticatorWrapper.getInstance(), rateLimiter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HttpConnection(@NotNull SentryOptions options, @NotNull RequestDetails requestDetails, @NotNull AuthenticatorWrapper authenticatorWrapper, @NotNull RateLimiter rateLimiter) {
/*  58 */     this.requestDetails = requestDetails;
/*  59 */     this.options = options;
/*  60 */     this.rateLimiter = rateLimiter;
/*     */     
/*  62 */     this.proxy = resolveProxy(options.getProxy());
/*     */     
/*  64 */     if (this.proxy != null && options.getProxy() != null) {
/*  65 */       String proxyUser = options.getProxy().getUser();
/*  66 */       String proxyPassword = options.getProxy().getPass();
/*     */       
/*  68 */       if (proxyUser != null && proxyPassword != null)
/*  69 */         authenticatorWrapper.setDefault(new ProxyAuthenticator(proxyUser, proxyPassword)); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Proxy resolveProxy(@Nullable SentryOptions.Proxy optionsProxy) {
/*  75 */     Proxy proxy = null;
/*  76 */     if (optionsProxy != null) {
/*  77 */       String port = optionsProxy.getPort();
/*  78 */       String host = optionsProxy.getHost();
/*  79 */       if (port != null && host != null) {
/*     */         try {
/*     */           Proxy.Type type;
/*  82 */           if (optionsProxy.getType() != null) {
/*  83 */             type = optionsProxy.getType();
/*     */           } else {
/*  85 */             type = Proxy.Type.HTTP;
/*     */           } 
/*  87 */           InetSocketAddress proxyAddr = new InetSocketAddress(host, Integer.parseInt(port));
/*  88 */           proxy = new Proxy(type, proxyAddr);
/*  89 */         } catch (NumberFormatException e) {
/*  90 */           this.options
/*  91 */             .getLogger()
/*  92 */             .log(SentryLevel.ERROR, e, "Failed to parse Sentry Proxy port: " + optionsProxy
/*     */ 
/*     */ 
/*     */               
/*  96 */               .getPort() + ". Proxy is ignored", new Object[0]);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 101 */     return proxy;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   HttpURLConnection open() throws IOException {
/* 106 */     return 
/* 107 */       (this.proxy == null) ? 
/* 108 */       (HttpURLConnection)this.requestDetails.getUrl().openConnection() : 
/* 109 */       (HttpURLConnection)this.requestDetails.getUrl().openConnection(this.proxy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private HttpURLConnection createConnection() throws IOException {
/* 119 */     HttpURLConnection connection = open();
/*     */     
/* 121 */     for (Map.Entry<String, String> header : (Iterable<Map.Entry<String, String>>)this.requestDetails.getHeaders().entrySet()) {
/* 122 */       connection.setRequestProperty(header.getKey(), header.getValue());
/*     */     }
/*     */     
/* 125 */     connection.setRequestMethod("POST");
/* 126 */     connection.setDoOutput(true);
/*     */     
/* 128 */     connection.setRequestProperty("Content-Encoding", "gzip");
/* 129 */     connection.setRequestProperty("Content-Type", "application/x-sentry-envelope");
/* 130 */     connection.setRequestProperty("Accept", "application/json");
/*     */ 
/*     */     
/* 133 */     connection.setRequestProperty("Connection", "close");
/*     */     
/* 135 */     connection.setConnectTimeout(this.options.getConnectionTimeoutMillis());
/* 136 */     connection.setReadTimeout(this.options.getReadTimeoutMillis());
/*     */     
/* 138 */     SSLSocketFactory sslSocketFactory = this.options.getSslSocketFactory();
/*     */     
/* 140 */     if (connection instanceof HttpsURLConnection && sslSocketFactory != null) {
/* 141 */       ((HttpsURLConnection)connection).setSSLSocketFactory(sslSocketFactory);
/*     */     }
/*     */     
/* 144 */     connection.connect();
/* 145 */     return connection;
/*     */   } @NotNull
/*     */   public TransportResult send(@NotNull SentryEnvelope envelope) throws IOException {
/*     */     TransportResult result;
/* 149 */     this.options.getSocketTagger().tagSockets();
/* 150 */     HttpURLConnection connection = createConnection();
/*     */ 
/*     */     
/* 153 */     try { OutputStream outputStream = connection.getOutputStream(); 
/* 154 */       try { GZIPOutputStream gzip = new GZIPOutputStream(outputStream); 
/* 155 */         try { this.options.getSerializer().serialize(envelope, gzip);
/* 156 */           gzip.close(); } catch (Throwable throwable) { try { gzip.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  if (outputStream != null) outputStream.close();  } catch (Throwable throwable) { if (outputStream != null) try { outputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable e)
/* 157 */     { this.options
/* 158 */         .getLogger()
/* 159 */         .log(SentryLevel.ERROR, e, "An exception occurred while submitting the envelope to the Sentry server.", new Object[0]);
/*     */        }
/*     */     
/*     */     finally
/*     */     
/* 164 */     { result = readAndLog(connection);
/* 165 */       this.options.getSocketTagger().untagSockets(); }
/*     */     
/* 167 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private TransportResult readAndLog(@NotNull HttpURLConnection connection) {
/*     */     try {
/* 178 */       int responseCode = connection.getResponseCode();
/*     */       
/* 180 */       updateRetryAfterLimits(connection, responseCode);
/*     */       
/* 182 */       if (!isSuccessfulResponseCode(responseCode)) {
/* 183 */         this.options.getLogger().log(SentryLevel.ERROR, "Request failed, API returned %s", new Object[] { Integer.valueOf(responseCode) });
/*     */         
/* 185 */         if (this.options.isDebug()) {
/* 186 */           String errorMessage = getErrorMessageFromStream(connection);
/*     */ 
/*     */           
/* 189 */           this.options.getLogger().log(SentryLevel.ERROR, "%s", new Object[] { errorMessage });
/*     */         } 
/*     */         
/* 192 */         return TransportResult.error(responseCode);
/*     */       } 
/*     */       
/* 195 */       this.options.getLogger().log(SentryLevel.DEBUG, "Envelope sent successfully.", new Object[0]);
/*     */       
/* 197 */       return TransportResult.success();
/* 198 */     } catch (IOException e) {
/* 199 */       this.options.getLogger().log(SentryLevel.ERROR, e, "Error reading and logging the response stream", new Object[0]);
/*     */     } finally {
/* 201 */       closeAndDisconnect(connection);
/*     */     } 
/* 203 */     return TransportResult.error();
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
/*     */   public void updateRetryAfterLimits(@NotNull HttpURLConnection connection, int responseCode) {
/* 215 */     String retryAfterHeader = connection.getHeaderField("Retry-After");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     String sentryRateLimitHeader = connection.getHeaderField("X-Sentry-Rate-Limits");
/* 224 */     this.rateLimiter.updateRetryAfterLimits(sentryRateLimitHeader, retryAfterHeader, responseCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void closeAndDisconnect(@NotNull HttpURLConnection connection) {
/*     */     try {
/* 234 */       connection.getInputStream().close();
/* 235 */     } catch (IOException iOException) {
/*     */     
/*     */     } finally {
/* 238 */       connection.disconnect();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private String getErrorMessageFromStream(@NotNull HttpURLConnection connection) {
/*     */     
/* 249 */     try { InputStream errorStream = connection.getErrorStream(); 
/* 250 */       try { BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream, UTF_8));
/*     */         
/* 252 */         try { StringBuilder sb = new StringBuilder();
/*     */ 
/*     */           
/* 255 */           boolean first = true; String line;
/* 256 */           while ((line = reader.readLine()) != null) {
/* 257 */             if (!first) {
/* 258 */               sb.append("\n");
/*     */             }
/* 260 */             sb.append(line);
/* 261 */             first = false;
/*     */           } 
/* 263 */           String str1 = sb.toString();
/* 264 */           reader.close(); if (errorStream != null) errorStream.close();  return str1; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) { if (errorStream != null) try { errorStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 265 */     { return "Failed to obtain error message while analyzing send failure."; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSuccessfulResponseCode(int responseCode) {
/* 276 */     return (responseCode == 200);
/*     */   }
/*     */   
/*     */   @TestOnly
/*     */   @Nullable
/*     */   Proxy getProxy() {
/* 282 */     return this.proxy;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\transport\HttpConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */