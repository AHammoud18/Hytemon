/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.clientreport.ClientReport;
/*     */ import io.sentry.exception.SentryEnvelopeException;
/*     */ import io.sentry.protocol.SentryTransaction;
/*     */ import io.sentry.protocol.profiling.SentryProfile;
/*     */ import io.sentry.util.FileUtils;
/*     */ import io.sentry.util.JsonSerializationUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.Base64;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class SentryEnvelopeItem
/*     */ {
/*     */   private static final long MAX_PROFILE_CHUNK_SIZE = 52428800L;
/*  42 */   private static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */   
/*     */   private final SentryEnvelopeItemHeader header;
/*     */   @Nullable
/*     */   private final Callable<byte[]> dataFactory;
/*     */   @Nullable
/*     */   private byte[] data;
/*     */   
/*     */   SentryEnvelopeItem(@NotNull SentryEnvelopeItemHeader header, byte[] data) {
/*  51 */     this.header = (SentryEnvelopeItemHeader)Objects.requireNonNull(header, "SentryEnvelopeItemHeader is required.");
/*  52 */     this.data = data;
/*  53 */     this.dataFactory = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SentryEnvelopeItem(@NotNull SentryEnvelopeItemHeader header, @Nullable Callable<byte[]> dataFactory) {
/*  59 */     this.header = (SentryEnvelopeItemHeader)Objects.requireNonNull(header, "SentryEnvelopeItemHeader is required.");
/*  60 */     this.dataFactory = (Callable<byte[]>)Objects.requireNonNull(dataFactory, "DataFactory is required.");
/*  61 */     this.data = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public byte[] getData() throws Exception {
/*  69 */     if (this.data == null && this.dataFactory != null) {
/*  70 */       this.data = this.dataFactory.call();
/*     */     }
/*  72 */     return this.data;
/*     */   }
/*     */   @NotNull
/*     */   public SentryEnvelopeItemHeader getHeader() {
/*  76 */     return this.header;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public static SentryEnvelopeItem fromSession(@NotNull ISerializer serializer, @NotNull Session session) throws IOException {
/*  81 */     Objects.requireNonNull(serializer, "ISerializer is required.");
/*  82 */     Objects.requireNonNull(session, "Session is required.");
/*     */     
/*  84 */     CachedItem cachedItem = new CachedItem(() -> { ByteArrayOutputStream stream = new ByteArrayOutputStream(); 
/*     */           try { Writer writer = new BufferedWriter(new OutputStreamWriter(stream, UTF_8)); 
/*     */             try { serializer.serialize(session, writer); byte[] arrayOfByte = stream.toByteArray(); writer.close(); stream.close(); return arrayOfByte; }
/*  87 */             catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) { try { stream.close(); } catch (Throwable throwable1)
/*     */             { throwable.addSuppressed(throwable1); }
/*     */ 
/*     */             
/*     */             throw throwable; }
/*     */         
/*     */         });
/*  94 */     SentryEnvelopeItemHeader itemHeader = new SentryEnvelopeItemHeader(SentryItemType.Session, () -> Integer.valueOf((cachedItem.getBytes()).length), "application/json", null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     return new SentryEnvelopeItem(itemHeader, () -> cachedItem.getBytes());
/*     */   }
/*     */   
/*     */   @Nullable
/* 104 */   public SentryEvent getEvent(@NotNull ISerializer serializer) throws Exception { if (this.header == null || this.header.getType() != SentryItemType.Event) {
/* 105 */       return null;
/*     */     }
/*     */     
/* 108 */     Reader eventReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(getData()), UTF_8)); 
/* 109 */     try { SentryEvent sentryEvent = serializer.<SentryEvent>deserialize(eventReader, SentryEvent.class);
/* 110 */       eventReader.close(); return sentryEvent; }
/*     */     catch (Throwable throwable) { try { eventReader.close(); }
/*     */       catch (Throwable throwable1)
/*     */       { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 115 */      } @NotNull public static SentryEnvelopeItem fromEvent(@NotNull ISerializer serializer, @NotNull SentryBaseEvent event) { Objects.requireNonNull(serializer, "ISerializer is required.");
/* 116 */     Objects.requireNonNull(event, "SentryEvent is required.");
/*     */     
/* 118 */     CachedItem cachedItem = new CachedItem(() -> { ByteArrayOutputStream stream = new ByteArrayOutputStream(); 
/*     */           try { Writer writer = new BufferedWriter(new OutputStreamWriter(stream, UTF_8)); 
/*     */             try { serializer.serialize(event, writer); byte[] arrayOfByte = stream.toByteArray(); writer.close(); stream.close(); return arrayOfByte; }
/* 121 */             catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) { try { stream.close(); } catch (Throwable throwable1)
/*     */             { throwable.addSuppressed(throwable1); }
/*     */ 
/*     */ 
/*     */             
/*     */             throw throwable; }
/*     */         
/*     */         });
/*     */     
/* 130 */     SentryEnvelopeItemHeader itemHeader = new SentryEnvelopeItemHeader(SentryItemType.resolve(event), () -> Integer.valueOf((cachedItem.getBytes()).length), "application/json", null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     return new SentryEnvelopeItem(itemHeader, () -> cachedItem.getBytes()); }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SentryTransaction getTransaction(@NotNull ISerializer serializer) throws Exception {
/* 142 */     if (this.header == null || this.header.getType() != SentryItemType.Transaction) {
/* 143 */       return null;
/*     */     }
/*     */     
/* 146 */     Reader eventReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(getData()), UTF_8)); 
/* 147 */     try { SentryTransaction sentryTransaction = serializer.<SentryTransaction>deserialize(eventReader, SentryTransaction.class);
/* 148 */       eventReader.close(); return sentryTransaction; }
/*     */     catch (Throwable throwable) { try { eventReader.close(); }
/*     */       catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 152 */      } @Nullable public SentryLogEvents getLogs(@NotNull ISerializer serializer) throws Exception { if (this.header == null || this.header.getType() != SentryItemType.Log) {
/* 153 */       return null;
/*     */     }
/*     */     
/* 156 */     Reader eventReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(getData()), UTF_8)); 
/* 157 */     try { SentryLogEvents sentryLogEvents = serializer.<SentryLogEvents>deserialize(eventReader, SentryLogEvents.class);
/* 158 */       eventReader.close(); return sentryLogEvents; }
/*     */     catch (Throwable throwable) { try { eventReader.close(); }
/*     */       catch (Throwable throwable1)
/*     */       { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 163 */      } public static SentryEnvelopeItem fromUserFeedback(@NotNull ISerializer serializer, @NotNull UserFeedback userFeedback) { Objects.requireNonNull(serializer, "ISerializer is required.");
/* 164 */     Objects.requireNonNull(userFeedback, "UserFeedback is required.");
/*     */     
/* 166 */     CachedItem cachedItem = new CachedItem(() -> { ByteArrayOutputStream stream = new ByteArrayOutputStream(); 
/*     */           try { Writer writer = new BufferedWriter(new OutputStreamWriter(stream, UTF_8)); 
/*     */             try { serializer.serialize(userFeedback, writer); byte[] arrayOfByte = stream.toByteArray(); writer.close(); stream.close(); return arrayOfByte; }
/* 169 */             catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) { try { stream.close(); } catch (Throwable throwable1)
/*     */             { throwable.addSuppressed(throwable1); }
/*     */ 
/*     */             
/*     */             throw throwable; }
/*     */         
/*     */         });
/* 176 */     SentryEnvelopeItemHeader itemHeader = new SentryEnvelopeItemHeader(SentryItemType.UserFeedback, () -> Integer.valueOf((cachedItem.getBytes()).length), "application/json", null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     return new SentryEnvelopeItem(itemHeader, () -> cachedItem.getBytes()); }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SentryEnvelopeItem fromCheckIn(@NotNull ISerializer serializer, @NotNull CheckIn checkIn) {
/* 190 */     Objects.requireNonNull(serializer, "ISerializer is required.");
/* 191 */     Objects.requireNonNull(checkIn, "CheckIn is required.");
/*     */     
/* 193 */     CachedItem cachedItem = new CachedItem(() -> { ByteArrayOutputStream stream = new ByteArrayOutputStream(); 
/*     */           try { Writer writer = new BufferedWriter(new OutputStreamWriter(stream, UTF_8)); 
/*     */             try { serializer.serialize(checkIn, writer); byte[] arrayOfByte = stream.toByteArray(); writer.close(); stream.close(); return arrayOfByte; }
/* 196 */             catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) { try { stream.close(); } catch (Throwable throwable1)
/*     */             { throwable.addSuppressed(throwable1); }
/*     */ 
/*     */             
/*     */             throw throwable; }
/*     */         
/*     */         });
/* 203 */     SentryEnvelopeItemHeader itemHeader = new SentryEnvelopeItemHeader(SentryItemType.CheckIn, () -> Integer.valueOf((cachedItem.getBytes()).length), "application/json", null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     return new SentryEnvelopeItem(itemHeader, () -> cachedItem.getBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SentryEnvelopeItem fromAttachment(@NotNull ISerializer serializer, @NotNull ILogger logger, @NotNull Attachment attachment, long maxAttachmentSize) {
/* 218 */     CachedItem cachedItem = new CachedItem(() -> {
/*     */           if (attachment.getBytes() != null) {
/*     */             byte[] data = attachment.getBytes();
/*     */ 
/*     */             
/*     */             ensureAttachmentSizeLimit(data.length, maxAttachmentSize, attachment.getFilename());
/*     */ 
/*     */             
/*     */             return data;
/*     */           } 
/*     */ 
/*     */           
/*     */           if (attachment.getSerializable() != null) {
/*     */             JsonSerializable serializable = attachment.getSerializable();
/*     */ 
/*     */             
/*     */             byte[] data = JsonSerializationUtils.bytesFrom(serializer, logger, serializable);
/*     */             
/*     */             if (data != null) {
/*     */               ensureAttachmentSizeLimit(data.length, maxAttachmentSize, attachment.getFilename());
/*     */               
/*     */               return data;
/*     */             } 
/*     */           } else {
/*     */             if (attachment.getPathname() != null) {
/*     */               return FileUtils.readBytesFromFile(attachment.getPathname(), maxAttachmentSize);
/*     */             }
/*     */             
/*     */             if (attachment.getByteProvider() != null) {
/*     */               byte[] data = attachment.getByteProvider().call();
/*     */               
/*     */               if (data != null) {
/*     */                 ensureAttachmentSizeLimit(data.length, maxAttachmentSize, attachment.getFilename());
/*     */                 
/*     */                 return data;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/*     */           throw new SentryEnvelopeException(String.format("Couldn't attach the attachment %s.\nPlease check that either bytes, serializable, path or provider is set.", new Object[] { attachment.getFilename() }));
/*     */         });
/*     */     
/* 260 */     SentryEnvelopeItemHeader itemHeader = new SentryEnvelopeItemHeader(SentryItemType.Attachment, () -> Integer.valueOf((cachedItem.getBytes()).length), attachment.getContentType(), attachment.getFilename(), attachment.getAttachmentType());
/*     */ 
/*     */ 
/*     */     
/* 264 */     return new SentryEnvelopeItem(itemHeader, () -> cachedItem.getBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void ensureAttachmentSizeLimit(long size, long maxAttachmentSize, @NotNull String filename) throws SentryEnvelopeException {
/* 270 */     if (size > maxAttachmentSize) {
/* 271 */       throw new SentryEnvelopeException(
/* 272 */           String.format("Dropping attachment with filename '%s', because the size of the passed bytes with %d bytes is bigger than the maximum allowed attachment size of %d bytes.", new Object[] {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 277 */               filename, Long.valueOf(size), Long.valueOf(maxAttachmentSize)
/*     */             }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static SentryEnvelopeItem fromProfileChunk(@NotNull ProfileChunk profileChunk, @NotNull ISerializer serializer) throws SentryEnvelopeException {
/* 285 */     return fromProfileChunk(profileChunk, serializer, NoOpProfileConverter.getInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static SentryEnvelopeItem fromProfileChunk(@NotNull ProfileChunk profileChunk, @NotNull ISerializer serializer, @NotNull IProfileConverter profileConverter) throws SentryEnvelopeException {
/* 294 */     File traceFile = profileChunk.getTraceFile();
/*     */     
/* 296 */     CachedItem cachedItem = new CachedItem(() -> {
/*     */           if (!traceFile.exists()) {
/*     */             throw new SentryEnvelopeException(String.format("Dropping profile chunk, because the file '%s' doesn't exists", new Object[] { traceFile.getName() }));
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           if ("java".equals(profileChunk.getPlatform())) {
/*     */             if (!NoOpProfileConverter.getInstance().equals(profileConverter)) {
/*     */               try {
/*     */                 SentryProfile profile = profileConverter.convertFromFile(traceFile.getAbsolutePath());
/*     */ 
/*     */ 
/*     */                 
/*     */                 profileChunk.setSentryProfile(profile);
/* 312 */               } catch (Exception e) {
/*     */                 throw new SentryEnvelopeException("Profile conversion failed", e);
/*     */               } 
/*     */             } else {
/*     */               throw new SentryEnvelopeException("No ProfileConverter available, dropping chunk.");
/*     */             } 
/*     */           } else {
/*     */             byte[] traceFileBytes = FileUtils.readBytesFromFile(traceFile.getPath(), 52428800L);
/*     */ 
/*     */             
/*     */             String base64Trace = Base64.encodeToString(traceFileBytes, 3);
/*     */ 
/*     */             
/*     */             if (base64Trace.isEmpty()) {
/*     */               throw new SentryEnvelopeException("Profiling trace file is empty");
/*     */             }
/*     */ 
/*     */             
/*     */             profileChunk.setSampledProfile(base64Trace);
/*     */           } 
/*     */ 
/*     */           
/*     */           try {
/*     */             ByteArrayOutputStream stream = new ByteArrayOutputStream();
/* 336 */           } catch (IOException e) {
/*     */             throw new SentryEnvelopeException(String.format("Failed to serialize profile chunk\n%s", new Object[] { e.getMessage() }));
/*     */           } finally {
/*     */             traceFile.delete();
/*     */           } 
/*     */         });
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
/* 352 */     SentryEnvelopeItemHeader itemHeader = new SentryEnvelopeItemHeader(SentryItemType.ProfileChunk, () -> Integer.valueOf((cachedItem.getBytes()).length), "application-json", traceFile.getName(), null, profileChunk.getPlatform(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 357 */     return new SentryEnvelopeItem(itemHeader, () -> cachedItem.getBytes());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static SentryEnvelopeItem fromProfilingTrace(@NotNull ProfilingTraceData profilingTraceData, long maxTraceFileSize, @NotNull ISerializer serializer) throws SentryEnvelopeException {
/* 366 */     File traceFile = profilingTraceData.getTraceFile();
/*     */     
/* 368 */     CachedItem cachedItem = new CachedItem(() -> {
/*     */           if (!traceFile.exists()) {
/*     */             throw new SentryEnvelopeException(String.format("Dropping profiling trace data, because the file '%s' doesn't exists", new Object[] { traceFile.getName() }));
/*     */           }
/*     */ 
/*     */           
/*     */           byte[] traceFileBytes = FileUtils.readBytesFromFile(traceFile.getPath(), maxTraceFileSize);
/*     */ 
/*     */           
/*     */           String base64Trace = Base64.encodeToString(traceFileBytes, 3);
/*     */ 
/*     */           
/*     */           if (base64Trace.isEmpty()) {
/*     */             throw new SentryEnvelopeException("Profiling trace file is empty");
/*     */           }
/*     */ 
/*     */           
/*     */           profilingTraceData.setSampledProfile(base64Trace);
/*     */ 
/*     */           
/*     */           profilingTraceData.readDeviceCpuFrequencies();
/*     */ 
/*     */           
/*     */           try {
/*     */             ByteArrayOutputStream stream = new ByteArrayOutputStream();
/* 393 */           } catch (IOException e) {
/*     */             throw new SentryEnvelopeException(String.format("Failed to serialize profiling trace data\n%s", new Object[] { e.getMessage() }));
/*     */           } finally {
/*     */             traceFile.delete();
/*     */           } 
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 407 */     SentryEnvelopeItemHeader itemHeader = new SentryEnvelopeItemHeader(SentryItemType.Profile, () -> Integer.valueOf((cachedItem.getBytes()).length), "application-json", traceFile.getName());
/*     */ 
/*     */ 
/*     */     
/* 411 */     return new SentryEnvelopeItem(itemHeader, () -> cachedItem.getBytes());
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static SentryEnvelopeItem fromClientReport(@NotNull ISerializer serializer, @NotNull ClientReport clientReport) throws IOException {
/* 417 */     Objects.requireNonNull(serializer, "ISerializer is required.");
/* 418 */     Objects.requireNonNull(clientReport, "ClientReport is required.");
/*     */     
/* 420 */     CachedItem cachedItem = new CachedItem(() -> { ByteArrayOutputStream stream = new ByteArrayOutputStream(); 
/*     */           try { Writer writer = new BufferedWriter(new OutputStreamWriter(stream, UTF_8)); 
/*     */             try { serializer.serialize(clientReport, writer); byte[] arrayOfByte = stream.toByteArray(); writer.close(); stream.close(); return arrayOfByte; }
/* 423 */             catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) { try { stream.close(); } catch (Throwable throwable1)
/*     */             { throwable.addSuppressed(throwable1); }
/*     */ 
/*     */ 
/*     */             
/*     */             throw throwable; }
/*     */         
/*     */         });
/*     */     
/* 432 */     SentryEnvelopeItemHeader itemHeader = new SentryEnvelopeItemHeader(SentryItemType.resolve(clientReport), () -> Integer.valueOf((cachedItem.getBytes()).length), "application/json", null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 439 */     return new SentryEnvelopeItem(itemHeader, () -> cachedItem.getBytes());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ClientReport getClientReport(@NotNull ISerializer serializer) throws Exception {
/* 444 */     if (this.header == null || this.header.getType() != SentryItemType.ClientReport) {
/* 445 */       return null;
/*     */     }
/*     */     
/* 448 */     Reader eventReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(getData()), UTF_8)); try {
/* 449 */       ClientReport clientReport = serializer.<ClientReport>deserialize(eventReader, ClientReport.class);
/* 450 */       eventReader.close();
/*     */       return clientReport;
/*     */     } catch (Throwable throwable) {
/*     */       try {
/*     */         eventReader.close();
/*     */       } catch (Throwable throwable1) {
/*     */         throwable.addSuppressed(throwable1);
/*     */       } 
/*     */       throw throwable;
/*     */     }  } public static SentryEnvelopeItem fromReplay(@NotNull ISerializer serializer, @NotNull ILogger logger, @NotNull SentryReplayEvent replayEvent, @Nullable ReplayRecording replayRecording, boolean cleanupReplayFolder) {
/* 460 */     File replayVideo = replayEvent.getVideoFile();
/*     */     
/* 462 */     CachedItem cachedItem = new CachedItem(() -> {
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
/*     */           try {
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
/*     */             ByteArrayOutputStream stream = new ByteArrayOutputStream();
/* 496 */           } catch (Throwable t) {
/*     */             logger.log(SentryLevel.ERROR, "Could not serialize replay recording", t);
/*     */             
/*     */             return null;
/*     */           } finally {
/*     */             if (replayVideo != null) {
/*     */               if (cleanupReplayFolder) {
/*     */                 FileUtils.deleteRecursively(replayVideo.getParentFile());
/*     */               } else {
/*     */                 replayVideo.delete();
/*     */               } 
/*     */             }
/*     */           } 
/*     */         });
/* 510 */     SentryEnvelopeItemHeader itemHeader = new SentryEnvelopeItemHeader(SentryItemType.ReplayVideo, () -> Integer.valueOf((cachedItem.getBytes()).length), null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 516 */     return new SentryEnvelopeItem(itemHeader, () -> cachedItem.getBytes());
/*     */   }
/*     */ 
/*     */   
/*     */   public static SentryEnvelopeItem fromLogs(@NotNull ISerializer serializer, @NotNull SentryLogEvents logEvents) {
/* 521 */     Objects.requireNonNull(serializer, "ISerializer is required.");
/* 522 */     Objects.requireNonNull(logEvents, "SentryLogEvents is required.");
/*     */     
/* 524 */     CachedItem cachedItem = new CachedItem(() -> { ByteArrayOutputStream stream = new ByteArrayOutputStream(); 
/*     */           try { Writer writer = new BufferedWriter(new OutputStreamWriter(stream, UTF_8)); 
/*     */             try { serializer.serialize(logEvents, writer); byte[] arrayOfByte = stream.toByteArray(); writer.close(); stream.close(); return arrayOfByte; }
/* 527 */             catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) { try { stream.close(); } catch (Throwable throwable1)
/*     */             { throwable.addSuppressed(throwable1); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             throw throwable; }
/*     */         
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 542 */     SentryEnvelopeItemHeader itemHeader = new SentryEnvelopeItemHeader(SentryItemType.Log, () -> Integer.valueOf((cachedItem.getBytes()).length), "application/vnd.sentry.items.log+json", null, null, null, Integer.valueOf(logEvents.getItems().size()));
/*     */ 
/*     */ 
/*     */     
/* 546 */     return new SentryEnvelopeItem(itemHeader, () -> cachedItem.getBytes());
/*     */   }
/*     */   private static class CachedItem { @Nullable
/*     */     private byte[] bytes;
/*     */     @Nullable
/*     */     private final Callable<byte[]> dataFactory;
/*     */     
/*     */     public CachedItem(@Nullable Callable<byte[]> dataFactory) {
/* 554 */       this.dataFactory = dataFactory;
/*     */     }
/*     */     @NotNull
/*     */     public byte[] getBytes() throws Exception {
/* 558 */       if (this.bytes == null && this.dataFactory != null) {
/* 559 */         this.bytes = this.dataFactory.call();
/*     */       }
/* 561 */       return orEmptyArray(this.bytes);
/*     */     }
/*     */     @NotNull
/*     */     private static byte[] orEmptyArray(@Nullable byte[] bytes) {
/* 565 */       return (bytes != null) ? bytes : new byte[0];
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] serializeToMsgpack(@NotNull Map<String, byte[]> map) throws IOException {
/* 572 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */     
/*     */     try {
/* 575 */       baos.write((byte)(0x80 | map.size()));
/*     */ 
/*     */       
/* 578 */       for (Map.Entry<String, byte[]> entry : map.entrySet()) {
/*     */         
/* 580 */         byte[] keyBytes = ((String)entry.getKey()).getBytes(UTF_8);
/* 581 */         int keyLength = keyBytes.length;
/*     */         
/* 583 */         baos.write(-39);
/* 584 */         baos.write((byte)keyLength);
/* 585 */         baos.write(keyBytes);
/*     */ 
/*     */         
/* 588 */         byte[] valueBytes = entry.getValue();
/* 589 */         int valueLength = valueBytes.length;
/*     */         
/* 591 */         baos.write(-58);
/* 592 */         baos.write(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(valueLength).array());
/* 593 */         baos.write(valueBytes);
/*     */       } 
/*     */       
/* 596 */       byte[] arrayOfByte = baos.toByteArray();
/* 597 */       baos.close();
/*     */       return arrayOfByte;
/*     */     } catch (Throwable throwable) {
/*     */       try {
/*     */         baos.close();
/*     */       } catch (Throwable throwable1) {
/*     */         throwable.addSuppressed(throwable1);
/*     */       } 
/*     */       throw throwable;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryEnvelopeItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */