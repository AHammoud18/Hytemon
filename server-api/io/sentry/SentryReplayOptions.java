/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.SdkVersion;
/*     */ import io.sentry.util.SampleRateUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
/*     */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SentryReplayOptions
/*     */ {
/*     */   public static final String TEXT_VIEW_CLASS_NAME = "android.widget.TextView";
/*     */   public static final String IMAGE_VIEW_CLASS_NAME = "android.widget.ImageView";
/*     */   public static final String WEB_VIEW_CLASS_NAME = "android.webkit.WebView";
/*     */   public static final String VIDEO_VIEW_CLASS_NAME = "android.widget.VideoView";
/*     */   public static final String ANDROIDX_MEDIA_VIEW_CLASS_NAME = "androidx.media3.ui.PlayerView";
/*     */   public static final String EXOPLAYER_CLASS_NAME = "com.google.android.exoplayer2.ui.PlayerView";
/*     */   public static final String EXOPLAYER_STYLED_CLASS_NAME = "com.google.android.exoplayer2.ui.StyledPlayerView";
/*     */   @Internal
/*     */   public static final int MAX_NETWORK_BODY_SIZE = 153600;
/*     */   @Nullable
/*     */   private Double sessionSampleRate;
/*     */   @Nullable
/*     */   private Double onErrorSampleRate;
/*     */   
/*     */   public enum SentryReplayQuality
/*     */   {
/*  38 */     LOW(0.8F, 50000, 10),
/*     */ 
/*     */     
/*  41 */     MEDIUM(1.0F, 75000, 30),
/*     */ 
/*     */     
/*  44 */     HIGH(1.0F, 100000, 50);
/*     */ 
/*     */ 
/*     */     
/*     */     public final float sizeScale;
/*     */ 
/*     */     
/*     */     public final int bitRate;
/*     */ 
/*     */     
/*     */     public final int screenshotQuality;
/*     */ 
/*     */ 
/*     */     
/*     */     SentryReplayQuality(float sizeScale, int bitRate, int screenshotQuality) {
/*  59 */       this.sizeScale = sizeScale;
/*  60 */       this.bitRate = bitRate;
/*  61 */       this.screenshotQuality = screenshotQuality;
/*     */     }
/*     */     @NotNull
/*     */     public String serializedName() {
/*  65 */       return name().toLowerCase(Locale.ROOT);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   private Set<String> maskViewClasses = new CopyOnWriteArraySet<>();
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
/* 105 */   private Set<String> unmaskViewClasses = new CopyOnWriteArraySet<>();
/*     */   
/*     */   @Nullable
/* 108 */   private String maskViewContainerClass = null;
/*     */   
/*     */   @Nullable
/* 111 */   private String unmaskViewContainerClass = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   private SentryReplayQuality quality = SentryReplayQuality.MEDIUM;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   private int frameRate = 1;
/*     */ 
/*     */   
/* 126 */   private long errorReplayDuration = 30000L;
/*     */ 
/*     */   
/* 129 */   private long sessionSegmentDuration = 5000L;
/*     */ 
/*     */   
/* 132 */   private long sessionDuration = 3600000L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean trackConfiguration = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private SdkVersion sdkVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean debug = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Experimental
/*     */   @NotNull
/* 162 */   private ScreenshotStrategyType screenshotStrategy = ScreenshotStrategyType.PIXEL_COPY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/* 169 */   private List<String> networkDetailAllowUrls = Collections.emptyList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/* 175 */   private List<String> networkDetailDenyUrls = Collections.emptyList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean networkCaptureBodies = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/* 186 */   private static final List<String> DEFAULT_HEADERS = Collections.unmodifiableList(Arrays.asList(new String[] { "Content-Type", "Content-Length", "Accept" }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public static List<String> getNetworkDetailsDefaultHeaders() {
/* 195 */     return DEFAULT_HEADERS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/* 202 */   private List<String> networkRequestHeaders = DEFAULT_HEADERS;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/* 208 */   private List<String> networkResponseHeaders = DEFAULT_HEADERS;
/*     */   
/*     */   public SentryReplayOptions(boolean empty, @Nullable SdkVersion sdkVersion) {
/* 211 */     if (!empty) {
/* 212 */       setMaskAllText(true);
/* 213 */       setMaskAllImages(true);
/* 214 */       this.maskViewClasses.add("android.webkit.WebView");
/* 215 */       this.maskViewClasses.add("android.widget.VideoView");
/* 216 */       this.maskViewClasses.add("androidx.media3.ui.PlayerView");
/* 217 */       this.maskViewClasses.add("com.google.android.exoplayer2.ui.PlayerView");
/* 218 */       this.maskViewClasses.add("com.google.android.exoplayer2.ui.StyledPlayerView");
/* 219 */       this.sdkVersion = sdkVersion;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SentryReplayOptions(@Nullable Double sessionSampleRate, @Nullable Double onErrorSampleRate, @Nullable SdkVersion sdkVersion) {
/* 227 */     this(false, sdkVersion);
/* 228 */     this.sessionSampleRate = sessionSampleRate;
/* 229 */     this.onErrorSampleRate = onErrorSampleRate;
/* 230 */     this.sdkVersion = sdkVersion;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Double getOnErrorSampleRate() {
/* 235 */     return this.onErrorSampleRate;
/*     */   }
/*     */   
/*     */   public boolean isSessionReplayEnabled() {
/* 239 */     return (getSessionSampleRate() != null && getSessionSampleRate().doubleValue() > 0.0D);
/*     */   }
/*     */   
/*     */   public void setOnErrorSampleRate(@Nullable Double onErrorSampleRate) {
/* 243 */     if (!SampleRateUtils.isValidSampleRate(onErrorSampleRate)) {
/* 244 */       throw new IllegalArgumentException("The value " + onErrorSampleRate + " is not valid. Use null to disable or values >= 0.0 and <= 1.0.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 249 */     this.onErrorSampleRate = onErrorSampleRate;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Double getSessionSampleRate() {
/* 254 */     return this.sessionSampleRate;
/*     */   }
/*     */   
/*     */   public boolean isSessionReplayForErrorsEnabled() {
/* 258 */     return (getOnErrorSampleRate() != null && getOnErrorSampleRate().doubleValue() > 0.0D);
/*     */   }
/*     */   
/*     */   public void setSessionSampleRate(@Nullable Double sessionSampleRate) {
/* 262 */     if (!SampleRateUtils.isValidSampleRate(sessionSampleRate)) {
/* 263 */       throw new IllegalArgumentException("The value " + sessionSampleRate + " is not valid. Use null to disable or values >= 0.0 and <= 1.0.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 268 */     this.sessionSampleRate = sessionSampleRate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaskAllText(boolean maskAllText) {
/* 278 */     if (maskAllText) {
/* 279 */       addMaskViewClass("android.widget.TextView");
/* 280 */       this.unmaskViewClasses.remove("android.widget.TextView");
/*     */     } else {
/* 282 */       addUnmaskViewClass("android.widget.TextView");
/* 283 */       this.maskViewClasses.remove("android.widget.TextView");
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
/*     */   public void setMaskAllImages(boolean maskAllImages) {
/* 296 */     if (maskAllImages) {
/* 297 */       addMaskViewClass("android.widget.ImageView");
/* 298 */       this.unmaskViewClasses.remove("android.widget.ImageView");
/*     */     } else {
/* 300 */       addUnmaskViewClass("android.widget.ImageView");
/* 301 */       this.maskViewClasses.remove("android.widget.ImageView");
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Set<String> getMaskViewClasses() {
/* 307 */     return this.maskViewClasses;
/*     */   }
/*     */   
/*     */   public void addMaskViewClass(@NotNull String className) {
/* 311 */     this.maskViewClasses.add(className);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Set<String> getUnmaskViewClasses() {
/* 316 */     return this.unmaskViewClasses;
/*     */   }
/*     */   
/*     */   public void addUnmaskViewClass(@NotNull String className) {
/* 320 */     this.unmaskViewClasses.add(className);
/*     */   }
/*     */   @Internal
/*     */   @NotNull
/*     */   public SentryReplayQuality getQuality() {
/* 325 */     return this.quality;
/*     */   }
/*     */   
/*     */   public void setQuality(@NotNull SentryReplayQuality quality) {
/* 329 */     this.quality = quality;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public int getFrameRate() {
/* 334 */     return this.frameRate;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public long getErrorReplayDuration() {
/* 339 */     return this.errorReplayDuration;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public long getSessionSegmentDuration() {
/* 344 */     return this.sessionSegmentDuration;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public long getSessionDuration() {
/* 349 */     return this.sessionDuration;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setMaskViewContainerClass(@NotNull String containerClass) {
/* 354 */     addMaskViewClass(containerClass);
/* 355 */     this.maskViewContainerClass = containerClass;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setUnmaskViewContainerClass(@NotNull String containerClass) {
/* 360 */     this.unmaskViewContainerClass = containerClass;
/*     */   }
/*     */   @Internal
/*     */   @Nullable
/*     */   public String getMaskViewContainerClass() {
/* 365 */     return this.maskViewContainerClass;
/*     */   }
/*     */   @Internal
/*     */   @Nullable
/*     */   public String getUnmaskViewContainerClass() {
/* 370 */     return this.unmaskViewContainerClass;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public boolean isTrackConfiguration() {
/* 375 */     return this.trackConfiguration;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setTrackConfiguration(boolean trackConfiguration) {
/* 380 */     this.trackConfiguration = trackConfiguration;
/*     */   }
/*     */   @Internal
/*     */   @Nullable
/*     */   public SdkVersion getSdkVersion() {
/* 385 */     return this.sdkVersion;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setSdkVersion(@Nullable SdkVersion sdkVersion) {
/* 390 */     this.sdkVersion = sdkVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDebug() {
/* 399 */     return this.debug;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDebug(boolean debug) {
/* 408 */     this.debug = debug;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Experimental
/*     */   @NotNull
/*     */   public ScreenshotStrategyType getScreenshotStrategy() {
/* 418 */     return this.screenshotStrategy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Experimental
/*     */   public void setScreenshotStrategy(@NotNull ScreenshotStrategyType screenshotStrategy) {
/* 428 */     this.screenshotStrategy = screenshotStrategy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<String> getNetworkDetailAllowUrls() {
/* 437 */     return this.networkDetailAllowUrls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNetworkDetailAllowUrls(@NotNull List<String> networkDetailAllowUrls) {
/* 446 */     this
/* 447 */       .networkDetailAllowUrls = Collections.unmodifiableList(new ArrayList<>(networkDetailAllowUrls));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<String> getNetworkDetailDenyUrls() {
/* 456 */     return this.networkDetailDenyUrls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNetworkDetailDenyUrls(@NotNull List<String> networkDetailDenyUrls) {
/* 466 */     this
/* 467 */       .networkDetailDenyUrls = Collections.unmodifiableList(new ArrayList<>(networkDetailDenyUrls));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNetworkCaptureBodies() {
/* 476 */     return this.networkCaptureBodies;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNetworkCaptureBodies(boolean networkCaptureBodies) {
/* 485 */     this.networkCaptureBodies = networkCaptureBodies;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<String> getNetworkRequestHeaders() {
/* 495 */     return this.networkRequestHeaders;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNetworkRequestHeaders(@NotNull List<String> networkRequestHeaders) {
/* 505 */     this.networkRequestHeaders = mergeHeaders(DEFAULT_HEADERS, networkRequestHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<String> getNetworkResponseHeaders() {
/* 515 */     return this.networkResponseHeaders;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNetworkResponseHeaders(@NotNull List<String> networkResponseHeaders) {
/* 525 */     this.networkResponseHeaders = mergeHeaders(DEFAULT_HEADERS, networkResponseHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private static List<String> mergeHeaders(@NotNull List<String> defaultHeaders, @NotNull List<String> additionalHeaders) {
/* 537 */     Set<String> merged = new LinkedHashSet<>();
/* 538 */     merged.addAll(defaultHeaders);
/* 539 */     merged.addAll(additionalHeaders);
/* 540 */     return Collections.unmodifiableList(new ArrayList<>(merged));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryReplayOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */