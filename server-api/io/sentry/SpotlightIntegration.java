/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.util.IntegrationUtils;
/*     */ import io.sentry.util.Platform;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URI;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.jetbrains.annotations.TestOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class SpotlightIntegration
/*     */   implements Integration, SentryOptions.BeforeEnvelopeCallback, Closeable
/*     */ {
/*     */   @Nullable
/*     */   private SentryOptions options;
/*     */   @NotNull
/*  26 */   private ILogger logger = NoOpLogger.getInstance(); @NotNull
/*  27 */   private ISentryExecutorService executorService = NoOpSentryExecutorService.getInstance();
/*     */ 
/*     */   
/*     */   public void register(@NotNull IScopes scopes, @NotNull SentryOptions options) {
/*  31 */     this.options = options;
/*  32 */     this.logger = options.getLogger();
/*     */     
/*  34 */     if (options.getBeforeEnvelopeCallback() == null && options.isEnableSpotlight()) {
/*  35 */       this.executorService = new SentryExecutorService(options);
/*  36 */       options.setBeforeEnvelopeCallback(this);
/*  37 */       this.logger.log(SentryLevel.DEBUG, "SpotlightIntegration enabled.", new Object[0]);
/*  38 */       IntegrationUtils.addIntegrationToSdkVersion("Spotlight");
/*     */     } else {
/*  40 */       this.logger.log(SentryLevel.DEBUG, "SpotlightIntegration is not enabled. BeforeEnvelopeCallback is already set or spotlight is not enabled.", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(@NotNull SentryEnvelope envelope, @Nullable Hint hint) {
/*     */     try {
/*  51 */       this.executorService.submit(() -> sendEnvelope(envelope));
/*  52 */     } catch (RejectedExecutionException e) {
/*  53 */       this.logger.log(SentryLevel.WARNING, "Spotlight envelope submission rejected.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendEnvelope(@NotNull SentryEnvelope envelope) {
/*     */     try {
/*  59 */       if (this.options == null) {
/*  60 */         throw new IllegalArgumentException("SentryOptions are required to send envelopes.");
/*     */       }
/*  62 */       String spotlightConnectionUrl = getSpotlightConnectionUrl();
/*     */       
/*  64 */       HttpURLConnection connection = createConnection(spotlightConnectionUrl); 
/*  65 */       try { OutputStream outputStream = connection.getOutputStream(); 
/*  66 */         try { GZIPOutputStream gzip = new GZIPOutputStream(outputStream); 
/*  67 */           try { this.options.getSerializer().serialize(envelope, gzip);
/*  68 */             gzip.close(); } catch (Throwable throwable) { try { gzip.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  if (outputStream != null) outputStream.close();  } catch (Throwable throwable) { if (outputStream != null) try { outputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable e)
/*  69 */       { this.logger.log(SentryLevel.ERROR, "An exception occurred while submitting the envelope to the Sentry server.", e); }
/*     */       finally
/*     */       
/*  72 */       { int responseCode = connection.getResponseCode();
/*  73 */         this.logger.log(SentryLevel.DEBUG, "Envelope sent to spotlight: %d", new Object[] { Integer.valueOf(responseCode) });
/*  74 */         closeAndDisconnect(connection); }
/*     */     
/*  76 */     } catch (Exception e) {
/*  77 */       this.logger.log(SentryLevel.ERROR, "An exception occurred while creating the connection to spotlight.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @TestOnly
/*     */   public String getSpotlightConnectionUrl() {
/*  83 */     if (this.options != null && this.options.getSpotlightConnectionUrl() != null) {
/*  84 */       return this.options.getSpotlightConnectionUrl();
/*     */     }
/*  86 */     if (Platform.isAndroid())
/*     */     {
/*     */       
/*  89 */       return "http://10.0.2.2:8969/stream";
/*     */     }
/*  91 */     return "http://localhost:8969/stream";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private HttpURLConnection createConnection(@NotNull String url) throws Exception {
/*  98 */     HttpURLConnection connection = (HttpURLConnection)URI.create(url).toURL().openConnection();
/*     */     
/* 100 */     connection.setReadTimeout(1000);
/* 101 */     connection.setConnectTimeout(1000);
/* 102 */     connection.setRequestMethod("POST");
/* 103 */     connection.setDoOutput(true);
/*     */     
/* 105 */     connection.setRequestProperty("Content-Encoding", "gzip");
/* 106 */     connection.setRequestProperty("Content-Type", "application/x-sentry-envelope");
/* 107 */     connection.setRequestProperty("Accept", "application/json");
/*     */ 
/*     */     
/* 110 */     connection.setRequestProperty("Connection", "close");
/*     */     
/* 112 */     connection.connect();
/* 113 */     return connection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void closeAndDisconnect(@NotNull HttpURLConnection connection) {
/*     */     try {
/* 123 */       connection.getInputStream().close();
/* 124 */     } catch (IOException iOException) {
/*     */     
/*     */     } finally {
/* 127 */       connection.disconnect();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 133 */     this.executorService.close(0L);
/* 134 */     if (this.options != null && this.options.getBeforeEnvelopeCallback() == this)
/* 135 */       this.options.setBeforeEnvelopeCallback(null); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SpotlightIntegration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */