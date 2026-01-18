/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.App;
/*     */ import io.sentry.protocol.Browser;
/*     */ import io.sentry.protocol.Contexts;
/*     */ import io.sentry.protocol.Device;
/*     */ import io.sentry.protocol.FeatureFlags;
/*     */ import io.sentry.protocol.Gpu;
/*     */ import io.sentry.protocol.OperatingSystem;
/*     */ import io.sentry.protocol.Response;
/*     */ import io.sentry.protocol.SentryRuntime;
/*     */ import io.sentry.protocol.Spring;
/*     */ import io.sentry.util.HintUtils;
/*     */ import java.io.IOException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class CombinedContextsView
/*     */   extends Contexts
/*     */ {
/*     */   private static final long serialVersionUID = 3585992094653318439L;
/*     */   @NotNull
/*     */   private final Contexts globalContexts;
/*     */   @NotNull
/*     */   private final Contexts isolationContexts;
/*     */   @NotNull
/*     */   private final Contexts currentContexts;
/*     */   @NotNull
/*     */   private final ScopeType defaultScopeType;
/*     */   
/*     */   public CombinedContextsView(@NotNull Contexts globalContexts, @NotNull Contexts isolationContexts, @NotNull Contexts currentContexts, @NotNull ScopeType defaultScopeType) {
/*  36 */     this.globalContexts = globalContexts;
/*  37 */     this.isolationContexts = isolationContexts;
/*  38 */     this.currentContexts = currentContexts;
/*  39 */     this.defaultScopeType = defaultScopeType;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SpanContext getTrace() {
/*  44 */     SpanContext current = this.currentContexts.getTrace();
/*  45 */     if (current != null) {
/*  46 */       return current;
/*     */     }
/*  48 */     SpanContext isolation = this.isolationContexts.getTrace();
/*  49 */     if (isolation != null) {
/*  50 */       return isolation;
/*     */     }
/*  52 */     return this.globalContexts.getTrace();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTrace(@NotNull SpanContext traceContext) {
/*  57 */     getDefaultContexts().setTrace(traceContext);
/*     */   }
/*     */   @NotNull
/*     */   private Contexts getDefaultContexts() {
/*  61 */     switch (this.defaultScopeType) {
/*     */       case CURRENT:
/*  63 */         return this.currentContexts;
/*     */       case ISOLATION:
/*  65 */         return this.isolationContexts;
/*     */       case GLOBAL:
/*  67 */         return this.globalContexts;
/*     */     } 
/*  69 */     return this.currentContexts;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public App getApp() {
/*  75 */     App current = this.currentContexts.getApp();
/*  76 */     if (current != null) {
/*  77 */       return current;
/*     */     }
/*  79 */     App isolation = this.isolationContexts.getApp();
/*  80 */     if (isolation != null) {
/*  81 */       return isolation;
/*     */     }
/*  83 */     return this.globalContexts.getApp();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setApp(@NotNull App app) {
/*  88 */     getDefaultContexts().setApp(app);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Browser getBrowser() {
/*  93 */     Browser current = this.currentContexts.getBrowser();
/*  94 */     if (current != null) {
/*  95 */       return current;
/*     */     }
/*  97 */     Browser isolation = this.isolationContexts.getBrowser();
/*  98 */     if (isolation != null) {
/*  99 */       return isolation;
/*     */     }
/* 101 */     return this.globalContexts.getBrowser();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBrowser(@NotNull Browser browser) {
/* 106 */     getDefaultContexts().setBrowser(browser);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Device getDevice() {
/* 111 */     Device current = this.currentContexts.getDevice();
/* 112 */     if (current != null) {
/* 113 */       return current;
/*     */     }
/* 115 */     Device isolation = this.isolationContexts.getDevice();
/* 116 */     if (isolation != null) {
/* 117 */       return isolation;
/*     */     }
/* 119 */     return this.globalContexts.getDevice();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDevice(@NotNull Device device) {
/* 124 */     getDefaultContexts().setDevice(device);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public OperatingSystem getOperatingSystem() {
/* 129 */     OperatingSystem current = this.currentContexts.getOperatingSystem();
/* 130 */     if (current != null) {
/* 131 */       return current;
/*     */     }
/* 133 */     OperatingSystem isolation = this.isolationContexts.getOperatingSystem();
/* 134 */     if (isolation != null) {
/* 135 */       return isolation;
/*     */     }
/* 137 */     return this.globalContexts.getOperatingSystem();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOperatingSystem(@NotNull OperatingSystem operatingSystem) {
/* 142 */     getDefaultContexts().setOperatingSystem(operatingSystem);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SentryRuntime getRuntime() {
/* 147 */     SentryRuntime current = this.currentContexts.getRuntime();
/* 148 */     if (current != null) {
/* 149 */       return current;
/*     */     }
/* 151 */     SentryRuntime isolation = this.isolationContexts.getRuntime();
/* 152 */     if (isolation != null) {
/* 153 */       return isolation;
/*     */     }
/* 155 */     return this.globalContexts.getRuntime();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRuntime(@NotNull SentryRuntime runtime) {
/* 160 */     getDefaultContexts().setRuntime(runtime);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Gpu getGpu() {
/* 165 */     Gpu current = this.currentContexts.getGpu();
/* 166 */     if (current != null) {
/* 167 */       return current;
/*     */     }
/* 169 */     Gpu isolation = this.isolationContexts.getGpu();
/* 170 */     if (isolation != null) {
/* 171 */       return isolation;
/*     */     }
/* 173 */     return this.globalContexts.getGpu();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGpu(@NotNull Gpu gpu) {
/* 178 */     getDefaultContexts().setGpu(gpu);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Response getResponse() {
/* 183 */     Response current = this.currentContexts.getResponse();
/* 184 */     if (current != null) {
/* 185 */       return current;
/*     */     }
/* 187 */     Response isolation = this.isolationContexts.getResponse();
/* 188 */     if (isolation != null) {
/* 189 */       return isolation;
/*     */     }
/* 191 */     return this.globalContexts.getResponse();
/*     */   }
/*     */ 
/*     */   
/*     */   public void withResponse(HintUtils.SentryConsumer<Response> callback) {
/* 196 */     if (this.currentContexts.getResponse() != null) {
/* 197 */       this.currentContexts.withResponse(callback);
/* 198 */     } else if (this.isolationContexts.getResponse() != null) {
/* 199 */       this.isolationContexts.withResponse(callback);
/* 200 */     } else if (this.globalContexts.getResponse() != null) {
/* 201 */       this.globalContexts.withResponse(callback);
/*     */     } else {
/* 203 */       getDefaultContexts().withResponse(callback);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResponse(@NotNull Response response) {
/* 209 */     getDefaultContexts().setResponse(response);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Spring getSpring() {
/* 214 */     Spring current = this.currentContexts.getSpring();
/* 215 */     if (current != null) {
/* 216 */       return current;
/*     */     }
/* 218 */     Spring isolation = this.isolationContexts.getSpring();
/* 219 */     if (isolation != null) {
/* 220 */       return isolation;
/*     */     }
/* 222 */     return this.globalContexts.getSpring();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSpring(@NotNull Spring spring) {
/* 227 */     getDefaultContexts().setSpring(spring);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public FeatureFlags getFeatureFlags() {
/* 233 */     FeatureFlags current = this.currentContexts.getFeatureFlags();
/* 234 */     if (current != null) {
/* 235 */       return current;
/*     */     }
/* 237 */     FeatureFlags isolation = this.isolationContexts.getFeatureFlags();
/* 238 */     if (isolation != null) {
/* 239 */       return isolation;
/*     */     }
/* 241 */     return this.globalContexts.getFeatureFlags();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void setFeatureFlags(@NotNull FeatureFlags spring) {
/* 248 */     getDefaultContexts().setFeatureFlags(spring);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 253 */     return mergeContexts().size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 258 */     return size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 263 */     return (this.globalContexts.isEmpty() && this.isolationContexts.isEmpty() && this.currentContexts.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(@Nullable Object key) {
/* 268 */     return (this.globalContexts.containsKey(key) || this.isolationContexts
/* 269 */       .containsKey(key) || this.currentContexts
/* 270 */       .containsKey(key));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Object get(@Nullable Object key) {
/* 275 */     Object current = this.currentContexts.get(key);
/* 276 */     if (current != null) {
/* 277 */       return current;
/*     */     }
/* 279 */     Object isolation = this.isolationContexts.get(key);
/* 280 */     if (isolation != null) {
/* 281 */       return isolation;
/*     */     }
/* 283 */     return this.globalContexts.get(key);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Object put(@Nullable String key, @Nullable Object value) {
/* 288 */     return getDefaultContexts().put(key, value);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Object remove(@Nullable Object key) {
/* 293 */     return getDefaultContexts().remove(key);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Enumeration<String> keys() {
/* 298 */     return mergeContexts().keys();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Set<Map.Entry<String, Object>> entrySet() {
/* 303 */     return mergeContexts().entrySet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 309 */     mergeContexts().serialize(writer, logger);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Object set(@Nullable String key, @Nullable Object value) {
/* 314 */     return put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(@Nullable Map<? extends String, ?> m) {
/* 319 */     getDefaultContexts().putAll(m);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(@Nullable Contexts contexts) {
/* 324 */     getDefaultContexts().putAll(contexts);
/*     */   }
/*     */   @NotNull
/*     */   private Contexts mergeContexts() {
/* 328 */     Contexts allContexts = new Contexts();
/* 329 */     allContexts.putAll(this.globalContexts);
/* 330 */     allContexts.putAll(this.isolationContexts);
/* 331 */     allContexts.putAll(this.currentContexts);
/* 332 */     return allContexts;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\CombinedContextsView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */