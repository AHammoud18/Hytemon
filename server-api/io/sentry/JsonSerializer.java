/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.clientreport.ClientReport;
/*     */ import io.sentry.profilemeasurements.ProfileMeasurement;
/*     */ import io.sentry.profilemeasurements.ProfileMeasurementValue;
/*     */ import io.sentry.protocol.App;
/*     */ import io.sentry.protocol.Browser;
/*     */ import io.sentry.protocol.Contexts;
/*     */ import io.sentry.protocol.DebugImage;
/*     */ import io.sentry.protocol.DebugMeta;
/*     */ import io.sentry.protocol.Device;
/*     */ import io.sentry.protocol.Feedback;
/*     */ import io.sentry.protocol.Geo;
/*     */ import io.sentry.protocol.Gpu;
/*     */ import io.sentry.protocol.MeasurementValue;
/*     */ import io.sentry.protocol.Mechanism;
/*     */ import io.sentry.protocol.Message;
/*     */ import io.sentry.protocol.OperatingSystem;
/*     */ import io.sentry.protocol.Request;
/*     */ import io.sentry.protocol.SdkInfo;
/*     */ import io.sentry.protocol.SdkVersion;
/*     */ import io.sentry.protocol.SentryException;
/*     */ import io.sentry.protocol.SentryPackage;
/*     */ import io.sentry.protocol.SentryRuntime;
/*     */ import io.sentry.protocol.SentrySpan;
/*     */ import io.sentry.protocol.SentryStackFrame;
/*     */ import io.sentry.protocol.SentryStackTrace;
/*     */ import io.sentry.protocol.SentryThread;
/*     */ import io.sentry.protocol.SentryTransaction;
/*     */ import io.sentry.protocol.User;
/*     */ import io.sentry.protocol.ViewHierarchy;
/*     */ import io.sentry.protocol.ViewHierarchyNode;
/*     */ import io.sentry.rrweb.RRWebBreadcrumbEvent;
/*     */ import io.sentry.rrweb.RRWebEventType;
/*     */ import io.sentry.rrweb.RRWebInteractionEvent;
/*     */ import io.sentry.rrweb.RRWebInteractionMoveEvent;
/*     */ import io.sentry.rrweb.RRWebMetaEvent;
/*     */ import io.sentry.rrweb.RRWebSpanEvent;
/*     */ import io.sentry.rrweb.RRWebVideoEvent;
/*     */ import io.sentry.util.Objects;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JsonSerializer
/*     */   implements ISerializer
/*     */ {
/*  65 */   private static final Charset UTF_8 = Charset.forName("UTF-8");
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private final SentryOptions options;
/*     */   
/*     */   @NotNull
/*     */   private final Map<Class<?>, JsonDeserializer<?>> deserializersByClass;
/*     */ 
/*     */   
/*     */   public JsonSerializer(@NotNull SentryOptions options) {
/*  76 */     this.options = options;
/*     */     
/*  78 */     this.deserializersByClass = new HashMap<>();
/*  79 */     this.deserializersByClass.put(App.class, new App.Deserializer());
/*  80 */     this.deserializersByClass.put(Breadcrumb.class, new Breadcrumb.Deserializer());
/*  81 */     this.deserializersByClass.put(Browser.class, new Browser.Deserializer());
/*  82 */     this.deserializersByClass.put(Contexts.class, new Contexts.Deserializer());
/*  83 */     this.deserializersByClass.put(DebugImage.class, new DebugImage.Deserializer());
/*  84 */     this.deserializersByClass.put(DebugMeta.class, new DebugMeta.Deserializer());
/*  85 */     this.deserializersByClass.put(Device.class, new Device.Deserializer());
/*  86 */     this.deserializersByClass.put(Device.DeviceOrientation.class, new Device.DeviceOrientation.Deserializer());
/*     */     
/*  88 */     this.deserializersByClass.put(Feedback.class, new Feedback.Deserializer());
/*  89 */     this.deserializersByClass.put(Gpu.class, new Gpu.Deserializer());
/*  90 */     this.deserializersByClass.put(MeasurementValue.class, new MeasurementValue.Deserializer());
/*  91 */     this.deserializersByClass.put(Mechanism.class, new Mechanism.Deserializer());
/*  92 */     this.deserializersByClass.put(Message.class, new Message.Deserializer());
/*  93 */     this.deserializersByClass.put(OperatingSystem.class, new OperatingSystem.Deserializer());
/*  94 */     this.deserializersByClass.put(ProfileChunk.class, new ProfileChunk.Deserializer());
/*  95 */     this.deserializersByClass.put(ProfileContext.class, new ProfileContext.Deserializer());
/*  96 */     this.deserializersByClass.put(ProfilingTraceData.class, new ProfilingTraceData.Deserializer());
/*  97 */     this.deserializersByClass.put(ProfilingTransactionData.class, new ProfilingTransactionData.Deserializer());
/*     */     
/*  99 */     this.deserializersByClass.put(ProfileMeasurement.class, new ProfileMeasurement.Deserializer());
/* 100 */     this.deserializersByClass.put(ProfileMeasurementValue.class, new ProfileMeasurementValue.Deserializer());
/*     */     
/* 102 */     this.deserializersByClass.put(Request.class, new Request.Deserializer());
/* 103 */     this.deserializersByClass.put(ReplayRecording.class, new ReplayRecording.Deserializer());
/* 104 */     this.deserializersByClass.put(RRWebBreadcrumbEvent.class, new RRWebBreadcrumbEvent.Deserializer());
/* 105 */     this.deserializersByClass.put(RRWebEventType.class, new RRWebEventType.Deserializer());
/* 106 */     this.deserializersByClass.put(RRWebInteractionEvent.class, new RRWebInteractionEvent.Deserializer());
/* 107 */     this.deserializersByClass.put(RRWebInteractionMoveEvent.class, new RRWebInteractionMoveEvent.Deserializer());
/*     */     
/* 109 */     this.deserializersByClass.put(RRWebMetaEvent.class, new RRWebMetaEvent.Deserializer());
/* 110 */     this.deserializersByClass.put(RRWebSpanEvent.class, new RRWebSpanEvent.Deserializer());
/* 111 */     this.deserializersByClass.put(RRWebVideoEvent.class, new RRWebVideoEvent.Deserializer());
/* 112 */     this.deserializersByClass.put(SdkInfo.class, new SdkInfo.Deserializer());
/* 113 */     this.deserializersByClass.put(SdkVersion.class, new SdkVersion.Deserializer());
/* 114 */     this.deserializersByClass.put(SentryEnvelopeHeader.class, new SentryEnvelopeHeader.Deserializer());
/* 115 */     this.deserializersByClass.put(SentryEnvelopeItemHeader.class, new SentryEnvelopeItemHeader.Deserializer());
/*     */     
/* 117 */     this.deserializersByClass.put(SentryEvent.class, new SentryEvent.Deserializer());
/* 118 */     this.deserializersByClass.put(SentryException.class, new SentryException.Deserializer());
/* 119 */     this.deserializersByClass.put(SentryItemType.class, new SentryItemType.Deserializer());
/* 120 */     this.deserializersByClass.put(SentryLevel.class, new SentryLevel.Deserializer());
/* 121 */     this.deserializersByClass.put(SentryLockReason.class, new SentryLockReason.Deserializer());
/* 122 */     this.deserializersByClass.put(SentryLogEvents.class, new SentryLogEvents.Deserializer());
/* 123 */     this.deserializersByClass.put(SentryPackage.class, new SentryPackage.Deserializer());
/* 124 */     this.deserializersByClass.put(SentryRuntime.class, new SentryRuntime.Deserializer());
/* 125 */     this.deserializersByClass.put(SentryReplayEvent.class, new SentryReplayEvent.Deserializer());
/* 126 */     this.deserializersByClass.put(SentrySpan.class, new SentrySpan.Deserializer());
/* 127 */     this.deserializersByClass.put(SentryStackFrame.class, new SentryStackFrame.Deserializer());
/* 128 */     this.deserializersByClass.put(SentryStackTrace.class, new SentryStackTrace.Deserializer());
/* 129 */     this.deserializersByClass.put(SentryAppStartProfilingOptions.class, new SentryAppStartProfilingOptions.Deserializer());
/*     */     
/* 131 */     this.deserializersByClass.put(SentryThread.class, new SentryThread.Deserializer());
/* 132 */     this.deserializersByClass.put(SentryTransaction.class, new SentryTransaction.Deserializer());
/* 133 */     this.deserializersByClass.put(Session.class, new Session.Deserializer());
/* 134 */     this.deserializersByClass.put(SpanContext.class, new SpanContext.Deserializer());
/* 135 */     this.deserializersByClass.put(SpanId.class, new SpanId.Deserializer());
/* 136 */     this.deserializersByClass.put(SpanStatus.class, new SpanStatus.Deserializer());
/* 137 */     this.deserializersByClass.put(User.class, new User.Deserializer());
/* 138 */     this.deserializersByClass.put(Geo.class, new Geo.Deserializer());
/* 139 */     this.deserializersByClass.put(UserFeedback.class, new UserFeedback.Deserializer());
/* 140 */     this.deserializersByClass.put(ClientReport.class, new ClientReport.Deserializer());
/* 141 */     this.deserializersByClass.put(ViewHierarchyNode.class, new ViewHierarchyNode.Deserializer());
/* 142 */     this.deserializersByClass.put(ViewHierarchy.class, new ViewHierarchy.Deserializer());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T, R> T deserializeCollection(@NotNull Reader reader, @NotNull Class<T> clazz, @Nullable JsonDeserializer<R> elementDeserializer) {
/*     */     
/* 153 */     try { JsonObjectReader jsonObjectReader = new JsonObjectReader(reader); 
/* 154 */       try { if (Collection.class.isAssignableFrom(clazz))
/* 155 */         { if (elementDeserializer == null)
/*     */           
/* 157 */           { Object object1 = jsonObjectReader.nextObjectOrNull();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 164 */             jsonObjectReader.close(); return (T)object1; }  List<R> list = jsonObjectReader.nextListOrNull(this.options.getLogger(), elementDeserializer); jsonObjectReader.close(); return (T)list; }  Object object = jsonObjectReader.nextObjectOrNull(); jsonObjectReader.close(); return (T)object; } catch (Throwable throwable) { try { jsonObjectReader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable e)
/* 165 */     { this.options.getLogger().log(SentryLevel.ERROR, "Error when deserializing", e);
/* 166 */       return null; }
/*     */   
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public <T> T deserialize(@NotNull Reader reader, @NotNull Class<T> clazz) {
/*     */     
/* 173 */     try { JsonObjectReader jsonObjectReader = new JsonObjectReader(reader); 
/* 174 */       try { JsonDeserializer<?> deserializer = this.deserializersByClass.get(clazz);
/* 175 */         if (deserializer != null)
/* 176 */         { Object object = deserializer.deserialize(jsonObjectReader, this.options.getLogger());
/* 177 */           T t1 = clazz.cast(object);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 183 */           jsonObjectReader.close(); return t1; }  if (isKnownPrimitive(clazz)) { Object object = jsonObjectReader.nextObjectOrNull(); jsonObjectReader.close(); return (T)object; }  T t = null; jsonObjectReader.close(); return t; } catch (Throwable throwable) { try { jsonObjectReader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Exception e)
/* 184 */     { this.options.getLogger().log(SentryLevel.ERROR, "Error when deserializing", e);
/* 185 */       return null; }
/*     */   
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SentryEnvelope deserializeEnvelope(@NotNull InputStream inputStream) {
/* 191 */     Objects.requireNonNull(inputStream, "The InputStream object is required.");
/*     */     try {
/* 193 */       return this.options.getEnvelopeReader().read(inputStream);
/* 194 */     } catch (IOException e) {
/* 195 */       this.options.getLogger().log(SentryLevel.ERROR, "Error deserializing envelope.", e);
/* 196 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void serialize(@NotNull T entity, @NotNull Writer writer) throws IOException {
/* 204 */     Objects.requireNonNull(entity, "The entity is required.");
/* 205 */     Objects.requireNonNull(writer, "The Writer object is required.");
/*     */     
/* 207 */     if (this.options.getLogger().isEnabled(SentryLevel.DEBUG)) {
/* 208 */       String serialized = serializeToString(entity, this.options.isEnablePrettySerializationOutput());
/* 209 */       this.options.getLogger().log(SentryLevel.DEBUG, "Serializing object: %s", new Object[] { serialized });
/*     */     } 
/* 211 */     JsonObjectWriter jsonObjectWriter = new JsonObjectWriter(writer, this.options.getMaxDepth());
/* 212 */     jsonObjectWriter.value(this.options.getLogger(), entity);
/* 213 */     writer.flush();
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
/*     */   public void serialize(@NotNull SentryEnvelope envelope, @NotNull OutputStream outputStream) throws Exception {
/* 226 */     Objects.requireNonNull(envelope, "The SentryEnvelope object is required.");
/* 227 */     Objects.requireNonNull(outputStream, "The Stream object is required.");
/*     */ 
/*     */     
/* 230 */     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
/* 231 */     Writer writer = new BufferedWriter(new OutputStreamWriter(bufferedOutputStream, UTF_8));
/*     */     
/*     */     try {
/* 234 */       envelope
/* 235 */         .getHeader()
/* 236 */         .serialize(new JsonObjectWriter(writer, this.options.getMaxDepth()), this.options.getLogger());
/* 237 */       writer.write("\n");
/*     */       
/* 239 */       for (SentryEnvelopeItem item : envelope.getItems()) {
/*     */         
/*     */         try {
/* 242 */           byte[] data = item.getData();
/*     */           
/* 244 */           item.getHeader()
/* 245 */             .serialize(new JsonObjectWriter(writer, this.options.getMaxDepth()), this.options.getLogger());
/* 246 */           writer.write("\n");
/* 247 */           writer.flush();
/*     */           
/* 249 */           outputStream.write(data);
/*     */           
/* 251 */           writer.write("\n");
/* 252 */         } catch (Exception exception) {
/* 253 */           this.options
/* 254 */             .getLogger()
/* 255 */             .log(SentryLevel.ERROR, "Failed to create envelope item. Dropping it.", exception);
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 259 */       writer.flush();
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String serialize(@NotNull Map<String, Object> data) throws Exception {
/* 265 */     return serializeToString(data, false);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private String serializeToString(Object object, boolean pretty) throws IOException {
/* 271 */     StringWriter stringWriter = new StringWriter();
/* 272 */     JsonObjectWriter jsonObjectWriter = new JsonObjectWriter(stringWriter, this.options.getMaxDepth());
/* 273 */     if (pretty) {
/* 274 */       jsonObjectWriter.setIndent("\t");
/*     */     }
/* 276 */     jsonObjectWriter.value(this.options.getLogger(), object);
/* 277 */     return stringWriter.toString();
/*     */   }
/*     */   
/*     */   private <T> boolean isKnownPrimitive(@NotNull Class<T> clazz) {
/* 281 */     return (clazz.isArray() || Collection.class
/* 282 */       .isAssignableFrom(clazz) || String.class
/* 283 */       .isAssignableFrom(clazz) || Map.class
/* 284 */       .isAssignableFrom(clazz));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\JsonSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */