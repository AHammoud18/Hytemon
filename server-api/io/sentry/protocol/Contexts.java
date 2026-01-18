/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.ISentryLifecycleToken;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.ProfileContext;
/*     */ import io.sentry.SpanContext;
/*     */ import io.sentry.util.AutoClosableReentrantLock;
/*     */ import io.sentry.util.HintUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public class Contexts
/*     */   implements JsonSerializable
/*     */ {
/*     */   private static final long serialVersionUID = 252445813254943011L;
/*     */   public static final String REPLAY_ID = "replay_id";
/*     */   @NotNull
/*  32 */   private final ConcurrentHashMap<String, Object> internalStorage = new ConcurrentHashMap<>();
/*     */ 
/*     */   
/*     */   @NotNull
/*  36 */   protected final AutoClosableReentrantLock responseLock = new AutoClosableReentrantLock();
/*     */   
/*     */   public Contexts() {}
/*     */   
/*     */   public Contexts(@NotNull Contexts contexts) {
/*  41 */     for (Map.Entry<String, Object> entry : contexts.entrySet()) {
/*  42 */       if (entry != null) {
/*  43 */         Object value = entry.getValue();
/*  44 */         if ("app".equals(entry.getKey()) && value instanceof App) {
/*  45 */           setApp(new App((App)value)); continue;
/*  46 */         }  if ("browser".equals(entry.getKey()) && value instanceof Browser) {
/*  47 */           setBrowser(new Browser((Browser)value)); continue;
/*  48 */         }  if ("device".equals(entry.getKey()) && value instanceof Device) {
/*  49 */           setDevice(new Device((Device)value)); continue;
/*  50 */         }  if ("os".equals(entry.getKey()) && value instanceof OperatingSystem) {
/*     */           
/*  52 */           setOperatingSystem(new OperatingSystem((OperatingSystem)value)); continue;
/*  53 */         }  if ("runtime".equals(entry.getKey()) && value instanceof SentryRuntime) {
/*  54 */           setRuntime(new SentryRuntime((SentryRuntime)value)); continue;
/*  55 */         }  if ("feedback".equals(entry.getKey()) && value instanceof Feedback) {
/*  56 */           setFeedback(new Feedback((Feedback)value)); continue;
/*  57 */         }  if ("gpu".equals(entry.getKey()) && value instanceof Gpu) {
/*  58 */           setGpu(new Gpu((Gpu)value)); continue;
/*  59 */         }  if ("trace".equals(entry.getKey()) && value instanceof SpanContext) {
/*  60 */           setTrace(new SpanContext((SpanContext)value)); continue;
/*  61 */         }  if ("profile".equals(entry.getKey()) && value instanceof ProfileContext) {
/*  62 */           setProfile(new ProfileContext((ProfileContext)value)); continue;
/*  63 */         }  if ("response".equals(entry.getKey()) && value instanceof Response) {
/*  64 */           setResponse(new Response((Response)value)); continue;
/*  65 */         }  if ("spring".equals(entry.getKey()) && value instanceof Spring) {
/*  66 */           setSpring(new Spring((Spring)value)); continue;
/*     */         } 
/*  68 */         put(entry.getKey(), value);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private <T> T toContextType(@NotNull String key, @NotNull Class<T> clazz) {
/*  75 */     Object item = get(key);
/*  76 */     return clazz.isInstance(item) ? clazz.cast(item) : null;
/*     */   }
/*     */   @Nullable
/*     */   public SpanContext getTrace() {
/*  80 */     return toContextType("trace", SpanContext.class);
/*     */   }
/*     */   
/*     */   public void setTrace(@NotNull SpanContext traceContext) {
/*  84 */     Objects.requireNonNull(traceContext, "traceContext is required");
/*  85 */     put("trace", traceContext);
/*     */   }
/*     */   @Nullable
/*     */   public ProfileContext getProfile() {
/*  89 */     return toContextType("profile", ProfileContext.class);
/*     */   }
/*     */   
/*     */   public void setProfile(@Nullable ProfileContext profileContext) {
/*  93 */     Objects.requireNonNull(profileContext, "profileContext is required");
/*  94 */     put("profile", profileContext);
/*     */   }
/*     */   @Nullable
/*     */   public App getApp() {
/*  98 */     return toContextType("app", App.class);
/*     */   }
/*     */   
/*     */   public void setApp(@NotNull App app) {
/* 102 */     put("app", app);
/*     */   }
/*     */   @Nullable
/*     */   public Browser getBrowser() {
/* 106 */     return toContextType("browser", Browser.class);
/*     */   }
/*     */   
/*     */   public void setBrowser(@NotNull Browser browser) {
/* 110 */     put("browser", browser);
/*     */   }
/*     */   @Nullable
/*     */   public Device getDevice() {
/* 114 */     return toContextType("device", Device.class);
/*     */   }
/*     */   
/*     */   public void setDevice(@NotNull Device device) {
/* 118 */     put("device", device);
/*     */   }
/*     */   @Nullable
/*     */   public OperatingSystem getOperatingSystem() {
/* 122 */     return toContextType("os", OperatingSystem.class);
/*     */   }
/*     */   
/*     */   public void setOperatingSystem(@NotNull OperatingSystem operatingSystem) {
/* 126 */     put("os", operatingSystem);
/*     */   }
/*     */   @Nullable
/*     */   public SentryRuntime getRuntime() {
/* 130 */     return toContextType("runtime", SentryRuntime.class);
/*     */   }
/*     */   
/*     */   public void setRuntime(@NotNull SentryRuntime runtime) {
/* 134 */     put("runtime", runtime);
/*     */   }
/*     */   @Nullable
/*     */   public Feedback getFeedback() {
/* 138 */     return toContextType("feedback", Feedback.class);
/*     */   }
/*     */   
/*     */   public void setFeedback(@NotNull Feedback feedback) {
/* 142 */     put("feedback", feedback);
/*     */   }
/*     */   @Nullable
/*     */   public Gpu getGpu() {
/* 146 */     return toContextType("gpu", Gpu.class);
/*     */   }
/*     */   
/*     */   public void setGpu(@NotNull Gpu gpu) {
/* 150 */     put("gpu", gpu);
/*     */   }
/*     */   @Nullable
/*     */   public Response getResponse() {
/* 154 */     return toContextType("response", Response.class);
/*     */   }
/*     */   
/*     */   public void withResponse(HintUtils.SentryConsumer<Response> callback) {
/* 158 */     ISentryLifecycleToken ignored = this.responseLock.acquire(); 
/* 159 */     try { Response response = getResponse();
/* 160 */       if (response != null) {
/* 161 */         callback.accept(response);
/*     */       } else {
/* 163 */         Response newResponse = new Response();
/* 164 */         setResponse(newResponse);
/* 165 */         callback.accept(newResponse);
/*     */       } 
/* 167 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 171 */      } public void setResponse(@NotNull Response response) { ISentryLifecycleToken ignored = this.responseLock.acquire(); 
/* 172 */     try { put("response", response);
/* 173 */       if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 177 */      } @Nullable public Spring getSpring() { return toContextType("spring", Spring.class); }
/*     */ 
/*     */   
/*     */   public void setSpring(@NotNull Spring spring) {
/* 181 */     put("spring", spring);
/*     */   }
/*     */   @Nullable
/*     */   public FeatureFlags getFeatureFlags() {
/* 185 */     return toContextType("flags", FeatureFlags.class);
/*     */   }
/*     */   
/*     */   public void setFeatureFlags(@NotNull FeatureFlags featureFlags) {
/* 189 */     put("flags", featureFlags);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 194 */     return this.internalStorage.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 199 */     return size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 203 */     return this.internalStorage.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean containsKey(@Nullable Object key) {
/* 207 */     if (key == null) {
/* 208 */       return false;
/*     */     }
/* 210 */     return this.internalStorage.containsKey(key);
/*     */   }
/*     */   @Nullable
/*     */   public Object get(@Nullable Object key) {
/* 214 */     if (key == null) {
/* 215 */       return null;
/*     */     }
/* 217 */     return this.internalStorage.get(key);
/*     */   }
/*     */   @Nullable
/*     */   public Object put(@Nullable String key, @Nullable Object value) {
/* 221 */     if (key == null) {
/* 222 */       return null;
/*     */     }
/* 224 */     if (value == null) {
/* 225 */       return this.internalStorage.remove(key);
/*     */     }
/* 227 */     return this.internalStorage.put(key, value);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Object set(@Nullable String key, @Nullable Object value) {
/* 232 */     return put(key, value);
/*     */   }
/*     */   @Nullable
/*     */   public Object remove(@Nullable Object key) {
/* 236 */     if (key == null) {
/* 237 */       return null;
/*     */     }
/* 239 */     return this.internalStorage.remove(key);
/*     */   }
/*     */   @NotNull
/*     */   public Enumeration<String> keys() {
/* 243 */     return this.internalStorage.keys();
/*     */   }
/*     */   @NotNull
/*     */   public Set<Map.Entry<String, Object>> entrySet() {
/* 247 */     return this.internalStorage.entrySet();
/*     */   }
/*     */   
/*     */   public void putAll(@Nullable Map<? extends String, ? extends Object> m) {
/* 251 */     if (m == null) {
/*     */       return;
/*     */     }
/*     */     
/* 255 */     Map<String, Object> tmpMap = new HashMap<>();
/*     */     
/* 257 */     for (Map.Entry<? extends String, ?> entry : m.entrySet()) {
/* 258 */       if (entry.getKey() != null && entry.getValue() != null) {
/* 259 */         tmpMap.put(entry.getKey(), entry.getValue());
/*     */       }
/*     */     } 
/*     */     
/* 263 */     this.internalStorage.putAll(tmpMap);
/*     */   }
/*     */   
/*     */   public void putAll(@Nullable Contexts contexts) {
/* 267 */     if (contexts == null) {
/*     */       return;
/*     */     }
/* 270 */     this.internalStorage.putAll(contexts.internalStorage);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object obj) {
/* 275 */     if (obj != null && obj instanceof Contexts) {
/* 276 */       Contexts otherContexts = (Contexts)obj;
/* 277 */       return this.internalStorage.equals(otherContexts.internalStorage);
/*     */     } 
/*     */     
/* 280 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 285 */     return this.internalStorage.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 293 */     writer.beginObject();
/*     */     
/* 295 */     List<String> sortedKeys = Collections.list(keys());
/* 296 */     Collections.sort(sortedKeys);
/* 297 */     for (String key : sortedKeys) {
/* 298 */       Object value = get(key);
/* 299 */       if (value != null) {
/* 300 */         writer.name(key).value(logger, value);
/*     */       }
/*     */     } 
/* 303 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<Contexts>
/*     */   {
/*     */     @NotNull
/*     */     public Contexts deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 311 */       Contexts contexts = new Contexts();
/* 312 */       reader.beginObject();
/* 313 */       while (reader.peek() == JsonToken.NAME) {
/* 314 */         String nextName = reader.nextName();
/* 315 */         switch (nextName) {
/*     */           case "app":
/* 317 */             contexts.setApp((new App.Deserializer()).deserialize(reader, logger));
/*     */             continue;
/*     */           case "browser":
/* 320 */             contexts.setBrowser((new Browser.Deserializer()).deserialize(reader, logger));
/*     */             continue;
/*     */           case "device":
/* 323 */             contexts.setDevice((new Device.Deserializer()).deserialize(reader, logger));
/*     */             continue;
/*     */           case "gpu":
/* 326 */             contexts.setGpu((new Gpu.Deserializer()).deserialize(reader, logger));
/*     */             continue;
/*     */           case "os":
/* 329 */             contexts.setOperatingSystem((new OperatingSystem.Deserializer())
/* 330 */                 .deserialize(reader, logger));
/*     */             continue;
/*     */           case "runtime":
/* 333 */             contexts.setRuntime((new SentryRuntime.Deserializer()).deserialize(reader, logger));
/*     */             continue;
/*     */           case "feedback":
/* 336 */             contexts.setFeedback((new Feedback.Deserializer()).deserialize(reader, logger));
/*     */             continue;
/*     */           case "trace":
/* 339 */             contexts.setTrace((new SpanContext.Deserializer()).deserialize(reader, logger));
/*     */             continue;
/*     */           case "profile":
/* 342 */             contexts.setProfile((new ProfileContext.Deserializer()).deserialize(reader, logger));
/*     */             continue;
/*     */           case "response":
/* 345 */             contexts.setResponse((new Response.Deserializer()).deserialize(reader, logger));
/*     */             continue;
/*     */           case "spring":
/* 348 */             contexts.setSpring((new Spring.Deserializer()).deserialize(reader, logger));
/*     */             continue;
/*     */           case "flags":
/* 351 */             contexts.setFeatureFlags((new FeatureFlags.Deserializer()).deserialize(reader, logger));
/*     */             continue;
/*     */         } 
/* 354 */         Object object = reader.nextObjectOrNull();
/* 355 */         if (object != null) {
/* 356 */           contexts.put(nextName, object);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 361 */       reader.endObject();
/* 362 */       return contexts;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\Contexts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */