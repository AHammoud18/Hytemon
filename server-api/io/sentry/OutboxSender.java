/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.hints.Flushable;
/*     */ import io.sentry.hints.Resettable;
/*     */ import io.sentry.hints.Retryable;
/*     */ import io.sentry.hints.SubmissionResult;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.protocol.SentryTransaction;
/*     */ import io.sentry.util.CollectionUtils;
/*     */ import io.sentry.util.HintUtils;
/*     */ import io.sentry.util.LogUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.util.SampleRateUtils;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.nio.charset.Charset;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class OutboxSender
/*     */   extends DirectoryProcessor
/*     */   implements IEnvelopeSender
/*     */ {
/*  37 */   private static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */   
/*     */   @NotNull
/*     */   private final IScopes scopes;
/*     */   
/*     */   @NotNull
/*     */   private final IEnvelopeReader envelopeReader;
/*     */   
/*     */   @NotNull
/*     */   private final ISerializer serializer;
/*     */   @NotNull
/*     */   private final ILogger logger;
/*     */   
/*     */   public OutboxSender(@NotNull IScopes scopes, @NotNull IEnvelopeReader envelopeReader, @NotNull ISerializer serializer, @NotNull ILogger logger, long flushTimeoutMillis, int maxQueueSize) {
/*  51 */     super(scopes, logger, flushTimeoutMillis, maxQueueSize);
/*  52 */     this.scopes = (IScopes)Objects.requireNonNull(scopes, "Scopes are required.");
/*  53 */     this.envelopeReader = (IEnvelopeReader)Objects.requireNonNull(envelopeReader, "Envelope reader is required.");
/*  54 */     this.serializer = (ISerializer)Objects.requireNonNull(serializer, "Serializer is required.");
/*  55 */     this.logger = (ILogger)Objects.requireNonNull(logger, "Logger is required.");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processFile(@NotNull File file, @NotNull Hint hint) {
/*  60 */     Objects.requireNonNull(file, "File is required.");
/*     */     
/*  62 */     if (!isRelevantFileName(file.getName())) {
/*  63 */       this.logger.log(SentryLevel.DEBUG, "File '%s' should be ignored.", new Object[] { file.getAbsolutePath() });
/*     */       return;
/*     */     } 
/*     */     
/*  67 */     try { InputStream stream = new BufferedInputStream(new FileInputStream(file)); 
/*  68 */       try { SentryEnvelope envelope = this.envelopeReader.read(stream);
/*  69 */         if (envelope == null) {
/*  70 */           this.logger.log(SentryLevel.ERROR, "Stream from path %s resulted in a null envelope.", new Object[] { file
/*     */ 
/*     */                 
/*  73 */                 .getAbsolutePath() });
/*     */         } else {
/*  75 */           processEnvelope(envelope, hint);
/*  76 */           this.logger.log(SentryLevel.DEBUG, "File '%s' is done.", new Object[] { file.getAbsolutePath() });
/*     */         } 
/*  78 */         stream.close(); } catch (Throwable throwable) { try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/*  79 */     { this.logger.log(SentryLevel.ERROR, "Error processing envelope.", e); }
/*     */     finally
/*  81 */     { HintUtils.runIfHasTypeLogIfNot(hint, Retryable.class, this.logger, retryable -> {
/*     */ 
/*     */             
/*     */             if (!retryable.isRetry()) {
/*     */               
/*     */               try {
/*     */                 
/*     */                 if (!file.delete()) {
/*     */                   this.logger.log(SentryLevel.ERROR, "Failed to delete: %s", new Object[] { file.getAbsolutePath() });
/*     */                 }
/*  91 */               } catch (RuntimeException e) {
/*     */                 this.logger.log(SentryLevel.ERROR, e, "Failed to delete: %s", new Object[] { file.getAbsolutePath() });
/*     */               } 
/*     */             }
/*     */           }); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isRelevantFileName(@Nullable String fileName) {
/* 102 */     return (fileName != null && 
/* 103 */       !fileName.startsWith("session") && 
/* 104 */       !fileName.startsWith("previous_session") && 
/* 105 */       !fileName.startsWith("startup_crash"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processEnvelopeFile(@NotNull String path, @NotNull Hint hint) {
/* 111 */     Objects.requireNonNull(path, "Path is required.");
/*     */     
/* 113 */     processFile(new File(path), hint);
/*     */   }
/*     */ 
/*     */   
/*     */   private void processEnvelope(@NotNull SentryEnvelope envelope, @NotNull Hint hint) throws IOException {
/* 118 */     this.logger.log(SentryLevel.DEBUG, "Processing Envelope with %d item(s)", new Object[] {
/*     */ 
/*     */           
/* 121 */           Integer.valueOf(CollectionUtils.size(envelope.getItems())) });
/* 122 */     int currentItem = 0;
/*     */     
/* 124 */     for (SentryEnvelopeItem item : envelope.getItems()) {
/* 125 */       currentItem++;
/*     */       
/* 127 */       if (item.getHeader() == null) {
/* 128 */         this.logger.log(SentryLevel.ERROR, "Item %d has no header", new Object[] { Integer.valueOf(currentItem) });
/*     */         continue;
/*     */       } 
/* 131 */       if (SentryItemType.Event.equals(item.getHeader().getType())) {
/*     */ 
/*     */         
/* 134 */         try { Reader eventReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(item.getData()), UTF_8)); 
/* 135 */           try { SentryEvent event = this.serializer.<SentryEvent>deserialize(eventReader, SentryEvent.class);
/* 136 */             if (event == null)
/* 137 */             { logEnvelopeItemNull(item, currentItem); }
/*     */             else
/* 139 */             { if (event.getSdk() != null) {
/* 140 */                 HintUtils.setIsFromHybridSdk(hint, event.getSdk().getName());
/*     */               }
/* 142 */               if (envelope.getHeader().getEventId() != null && 
/* 143 */                 !envelope.getHeader().getEventId().equals(event.getEventId()))
/* 144 */               { logUnexpectedEventId(envelope, event.getEventId(), currentItem);
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
/* 155 */                 eventReader.close(); continue; }  this.scopes.captureEvent(event, hint); logItemCaptured(currentItem); if (!waitFlush(hint)) { logTimeout(event.getEventId()); eventReader.close(); break; }  }  eventReader.close(); } catch (Throwable throwable) { try { eventReader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/* 156 */         { this.logger.log(SentryLevel.ERROR, "Item failed to process.", e); }
/*     */       
/* 158 */       } else if (SentryItemType.Transaction.equals(item.getHeader().getType())) {
/*     */ 
/*     */         
/* 161 */         try { Reader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(item.getData()), UTF_8));
/*     */ 
/*     */           
/* 164 */           try { SentryTransaction transaction = this.serializer.<SentryTransaction>deserialize(reader, SentryTransaction.class);
/* 165 */             if (transaction == null)
/* 166 */             { logEnvelopeItemNull(item, currentItem); }
/*     */             else
/* 168 */             { if (envelope.getHeader().getEventId() != null && 
/* 169 */                 !envelope.getHeader().getEventId().equals(transaction.getEventId()))
/* 170 */               { logUnexpectedEventId(envelope, transaction.getEventId(), currentItem);
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
/* 192 */                 reader.close(); continue; }  TraceContext traceContext = envelope.getHeader().getTraceContext(); if (transaction.getContexts().getTrace() != null) transaction.getContexts().getTrace().setSamplingDecision(extractSamplingDecision(traceContext));  this.scopes.captureTransaction(transaction, traceContext, hint); logItemCaptured(currentItem); if (!waitFlush(hint)) { logTimeout(transaction.getEventId()); reader.close(); break; }  }  reader.close(); } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/* 193 */         { this.logger.log(SentryLevel.ERROR, "Item failed to process.", e); }
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 199 */         SentryEnvelope newEnvelope = new SentryEnvelope(envelope.getHeader().getEventId(), envelope.getHeader().getSdkVersion(), item);
/* 200 */         this.scopes.captureEnvelope(newEnvelope, hint);
/* 201 */         this.logger.log(SentryLevel.DEBUG, "%s item %d is being captured.", new Object[] { item
/*     */ 
/*     */               
/* 204 */               .getHeader().getType().getItemType(), 
/* 205 */               Integer.valueOf(currentItem) });
/*     */         
/* 207 */         if (!waitFlush(hint)) {
/* 208 */           this.logger.log(SentryLevel.WARNING, "Timed out waiting for item type submission: %s", new Object[] { item
/*     */ 
/*     */                 
/* 211 */                 .getHeader().getType().getItemType() });
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 216 */       Object sentrySdkHint = HintUtils.getSentrySdkHint(hint);
/* 217 */       if (sentrySdkHint instanceof SubmissionResult && 
/* 218 */         !((SubmissionResult)sentrySdkHint).isSuccess()) {
/*     */ 
/*     */         
/* 221 */         this.logger.log(SentryLevel.WARNING, "Envelope had a failed capture at item %d. No more items will be sent.", new Object[] {
/*     */ 
/*     */               
/* 224 */               Integer.valueOf(currentItem)
/*     */             });
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 230 */       HintUtils.runIfHasType(hint, Resettable.class, resettable -> resettable.reset());
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private TracesSamplingDecision extractSamplingDecision(@Nullable TraceContext traceContext) {
/* 236 */     if (traceContext != null) {
/* 237 */       String sampleRateString = traceContext.getSampleRate();
/* 238 */       if (sampleRateString != null) {
/*     */         try {
/* 240 */           Double sampleRate = Double.valueOf(Double.parseDouble(sampleRateString));
/* 241 */           if (!SampleRateUtils.isValidTracesSampleRate(sampleRate, false)) {
/* 242 */             this.logger.log(SentryLevel.ERROR, "Invalid sample rate parsed from TraceContext: %s", new Object[] { sampleRateString });
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 247 */             String sampleRandString = traceContext.getSampleRand();
/* 248 */             if (sampleRandString != null) {
/* 249 */               Double sampleRand = Double.valueOf(Double.parseDouble(sampleRandString));
/* 250 */               if (SampleRateUtils.isValidTracesSampleRate(sampleRand, false)) {
/* 251 */                 return new TracesSamplingDecision(Boolean.valueOf(true), sampleRate, sampleRand);
/*     */               }
/*     */             } 
/*     */             
/* 255 */             return SampleRateUtils.backfilledSampleRand(new TracesSamplingDecision(
/* 256 */                   Boolean.valueOf(true), sampleRate));
/*     */           } 
/* 258 */         } catch (Exception e) {
/* 259 */           this.logger.log(SentryLevel.ERROR, "Unable to parse sample rate from TraceContext: %s", new Object[] { sampleRateString });
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 267 */     return new TracesSamplingDecision(Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */   private void logEnvelopeItemNull(@NotNull SentryEnvelopeItem item, int itemIndex) {
/* 271 */     this.logger.log(SentryLevel.ERROR, "Item %d of type %s returned null by the parser.", new Object[] {
/*     */ 
/*     */           
/* 274 */           Integer.valueOf(itemIndex), item
/* 275 */           .getHeader().getType()
/*     */         });
/*     */   }
/*     */   
/*     */   private void logUnexpectedEventId(@NotNull SentryEnvelope envelope, @Nullable SentryId eventId, int itemIndex) {
/* 280 */     this.logger.log(SentryLevel.ERROR, "Item %d of has a different event id (%s) to the envelope header (%s)", new Object[] {
/*     */ 
/*     */           
/* 283 */           Integer.valueOf(itemIndex), envelope
/* 284 */           .getHeader().getEventId(), eventId
/*     */         });
/*     */   }
/*     */   
/*     */   private void logItemCaptured(int itemIndex) {
/* 289 */     this.logger.log(SentryLevel.DEBUG, "Item %d is being captured.", new Object[] { Integer.valueOf(itemIndex) });
/*     */   }
/*     */   
/*     */   private void logTimeout(@Nullable SentryId eventId) {
/* 293 */     this.logger.log(SentryLevel.WARNING, "Timed out waiting for event id submission: %s", new Object[] { eventId });
/*     */   }
/*     */   
/*     */   private boolean waitFlush(@NotNull Hint hint) {
/* 297 */     Object sentrySdkHint = HintUtils.getSentrySdkHint(hint);
/* 298 */     if (sentrySdkHint instanceof Flushable) {
/* 299 */       return ((Flushable)sentrySdkHint).waitFlush();
/*     */     }
/* 301 */     LogUtils.logNotInstanceOf(Flushable.class, sentrySdkHint, this.logger);
/*     */     
/* 303 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\OutboxSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */