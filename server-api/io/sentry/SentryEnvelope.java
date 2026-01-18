/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.exception.SentryEnvelopeException;
/*     */ import io.sentry.protocol.SdkVersion;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.util.Objects;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class SentryEnvelope {
/*     */   @NotNull
/*     */   private final SentryEnvelopeHeader header;
/*     */   @NotNull
/*     */   private final Iterable<SentryEnvelopeItem> items;
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public Iterable<SentryEnvelopeItem> getItems() {
/*  23 */     return this.items;
/*     */   }
/*     */   @Internal
/*     */   @NotNull
/*     */   public SentryEnvelopeHeader getHeader() {
/*  28 */     return this.header;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public SentryEnvelope(@NotNull SentryEnvelopeHeader header, @NotNull Iterable<SentryEnvelopeItem> items) {
/*  35 */     this.header = (SentryEnvelopeHeader)Objects.requireNonNull(header, "SentryEnvelopeHeader is required.");
/*  36 */     this.items = (Iterable<SentryEnvelopeItem>)Objects.requireNonNull(items, "SentryEnvelope items are required.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public SentryEnvelope(@Nullable SentryId eventId, @Nullable SdkVersion sdkVersion, @NotNull Iterable<SentryEnvelopeItem> items) {
/*  44 */     this.header = new SentryEnvelopeHeader(eventId, sdkVersion);
/*  45 */     this.items = (Iterable<SentryEnvelopeItem>)Objects.requireNonNull(items, "SentryEnvelope items are required.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public SentryEnvelope(@Nullable SentryId eventId, @Nullable SdkVersion sdkVersion, @NotNull SentryEnvelopeItem item) {
/*  53 */     Objects.requireNonNull(item, "SentryEnvelopeItem is required.");
/*     */     
/*  55 */     this.header = new SentryEnvelopeHeader(eventId, sdkVersion);
/*  56 */     List<SentryEnvelopeItem> items = new ArrayList<>(1);
/*  57 */     items.add(item);
/*  58 */     this.items = items;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public static SentryEnvelope from(@NotNull ISerializer serializer, @NotNull Session session, @Nullable SdkVersion sdkVersion) throws IOException {
/*  67 */     Objects.requireNonNull(serializer, "Serializer is required.");
/*  68 */     Objects.requireNonNull(session, "session is required.");
/*     */     
/*  70 */     return new SentryEnvelope(null, sdkVersion, 
/*  71 */         SentryEnvelopeItem.fromSession(serializer, session));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public static SentryEnvelope from(@NotNull ISerializer serializer, @NotNull SentryBaseEvent event, @Nullable SdkVersion sdkVersion) throws IOException {
/*  80 */     Objects.requireNonNull(serializer, "Serializer is required.");
/*  81 */     Objects.requireNonNull(event, "item is required.");
/*     */     
/*  83 */     return new SentryEnvelope(event
/*  84 */         .getEventId(), sdkVersion, SentryEnvelopeItem.fromEvent(serializer, event));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public static SentryEnvelope from(@NotNull ISerializer serializer, @NotNull ProfilingTraceData profilingTraceData, long maxTraceFileSize, @Nullable SdkVersion sdkVersion) throws SentryEnvelopeException {
/*  94 */     Objects.requireNonNull(serializer, "Serializer is required.");
/*  95 */     Objects.requireNonNull(profilingTraceData, "Profiling trace data is required.");
/*     */     
/*  97 */     return new SentryEnvelope(new SentryId(profilingTraceData
/*  98 */           .getProfileId()), sdkVersion, 
/*     */         
/* 100 */         SentryEnvelopeItem.fromProfilingTrace(profilingTraceData, maxTraceFileSize, serializer));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryEnvelope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */