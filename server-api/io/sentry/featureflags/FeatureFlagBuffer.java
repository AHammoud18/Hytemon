/*     */ package io.sentry.featureflags;
/*     */ 
/*     */ import io.sentry.ISentryLifecycleToken;
/*     */ import io.sentry.ScopeType;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.protocol.FeatureFlag;
/*     */ import io.sentry.protocol.FeatureFlags;
/*     */ import io.sentry.util.AutoClosableReentrantLock;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class FeatureFlagBuffer
/*     */   implements IFeatureFlagBuffer
/*     */ {
/*     */   @NotNull
/*     */   private volatile CopyOnWriteArrayList<FeatureFlagEntry> flags;
/*     */   @NotNull
/*  31 */   private final AutoClosableReentrantLock lock = new AutoClosableReentrantLock();
/*     */   private int maxSize;
/*     */   
/*     */   private FeatureFlagBuffer(int maxSize) {
/*  35 */     this.maxSize = maxSize;
/*  36 */     this.flags = new CopyOnWriteArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   private FeatureFlagBuffer(int maxSize, @NotNull CopyOnWriteArrayList<FeatureFlagEntry> flags) {
/*  41 */     this.maxSize = maxSize;
/*  42 */     this.flags = flags;
/*     */   }
/*     */   
/*     */   private FeatureFlagBuffer(@NotNull FeatureFlagBuffer other) {
/*  46 */     this.maxSize = other.maxSize;
/*  47 */     this.flags = new CopyOnWriteArrayList<>(other.flags);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(@Nullable String flag, @Nullable Boolean result) {
/*  52 */     if (flag == null || result == null) {
/*     */       return;
/*     */     }
/*  55 */     ISentryLifecycleToken ignored = this.lock.acquire(); 
/*  56 */     try { int size = this.flags.size();
/*  57 */       for (int i = 0; i < size; i++) {
/*  58 */         FeatureFlagEntry entry = this.flags.get(i);
/*  59 */         if (entry.flag.equals(flag)) {
/*  60 */           this.flags.remove(i);
/*     */           break;
/*     */         } 
/*     */       } 
/*  64 */       this.flags.add(new FeatureFlagEntry(flag, result.booleanValue(), Long.valueOf(System.nanoTime())));
/*     */       
/*  66 */       if (this.flags.size() > this.maxSize) {
/*  67 */         this.flags.remove(0);
/*     */       }
/*  69 */       if (ignored != null) ignored.close();  }
/*     */     catch (Throwable throwable) { if (ignored != null)
/*     */         try { ignored.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/*  74 */      } @Nullable public FeatureFlags getFeatureFlags() { List<FeatureFlag> featureFlags = new ArrayList<>();
/*  75 */     for (FeatureFlagEntry entry : this.flags) {
/*  76 */       featureFlags.add(entry.toFeatureFlag());
/*     */     }
/*  78 */     return new FeatureFlags(featureFlags); }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public IFeatureFlagBuffer clone() {
/*  83 */     return new FeatureFlagBuffer(this);
/*     */   }
/*     */   @NotNull
/*     */   public static IFeatureFlagBuffer create(@NotNull SentryOptions options) {
/*  87 */     int maxFeatureFlags = options.getMaxFeatureFlags();
/*  88 */     if (maxFeatureFlags > 0) {
/*  89 */       return new FeatureFlagBuffer(maxFeatureFlags);
/*     */     }
/*  91 */     return NoOpFeatureFlagBuffer.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static IFeatureFlagBuffer merged(@NotNull SentryOptions options, @Nullable IFeatureFlagBuffer globalBuffer, @Nullable IFeatureFlagBuffer isolationBuffer, @Nullable IFeatureFlagBuffer currentBuffer) {
/* 100 */     int maxSize = options.getMaxFeatureFlags();
/* 101 */     if (maxSize <= 0) {
/* 102 */       return NoOpFeatureFlagBuffer.getInstance();
/*     */     }
/*     */     
/* 105 */     return merged(maxSize, 
/*     */         
/* 107 */         (globalBuffer instanceof FeatureFlagBuffer) ? (FeatureFlagBuffer)globalBuffer : null, 
/* 108 */         (isolationBuffer instanceof FeatureFlagBuffer) ? (FeatureFlagBuffer)isolationBuffer : null, 
/* 109 */         (currentBuffer instanceof FeatureFlagBuffer) ? (FeatureFlagBuffer)currentBuffer : null);
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
/*     */   @NotNull
/*     */   private static IFeatureFlagBuffer merged(int maxSize, @Nullable FeatureFlagBuffer globalBuffer, @Nullable FeatureFlagBuffer isolationBuffer, @Nullable FeatureFlagBuffer currentBuffer) {
/* 133 */     CopyOnWriteArrayList<FeatureFlagEntry> globalFlags = (globalBuffer == null) ? null : globalBuffer.flags;
/*     */     
/* 135 */     CopyOnWriteArrayList<FeatureFlagEntry> isolationFlags = (isolationBuffer == null) ? null : isolationBuffer.flags;
/*     */     
/* 137 */     CopyOnWriteArrayList<FeatureFlagEntry> currentFlags = (currentBuffer == null) ? null : currentBuffer.flags;
/*     */     
/* 139 */     int globalSize = (globalFlags == null) ? 0 : globalFlags.size();
/* 140 */     int isolationSize = (isolationFlags == null) ? 0 : isolationFlags.size();
/* 141 */     int currentSize = (currentFlags == null) ? 0 : currentFlags.size();
/*     */ 
/*     */     
/* 144 */     if (globalSize == 0 && isolationSize == 0 && currentSize == 0) {
/* 145 */       return NoOpFeatureFlagBuffer.getInstance();
/*     */     }
/*     */     
/* 148 */     int globalIndex = globalSize - 1;
/* 149 */     int isolationIndex = isolationSize - 1;
/* 150 */     int currentIndex = currentSize - 1;
/*     */ 
/*     */ 
/*     */     
/* 154 */     FeatureFlagEntry globalEntry = (globalFlags == null || globalIndex < 0) ? null : globalFlags.get(globalIndex);
/*     */ 
/*     */     
/* 157 */     FeatureFlagEntry isolationEntry = (isolationFlags == null || isolationIndex < 0) ? null : isolationFlags.get(isolationIndex);
/*     */ 
/*     */     
/* 160 */     FeatureFlagEntry currentEntry = (currentFlags == null || currentIndex < 0) ? null : currentFlags.get(currentIndex);
/*     */     
/* 162 */     Map<String, FeatureFlagEntry> uniqueFlags = new LinkedHashMap<>(maxSize);
/*     */ 
/*     */ 
/*     */     
/* 166 */     while (uniqueFlags.size() < maxSize && (globalEntry != null || isolationEntry != null || currentEntry != null)) {
/*     */ 
/*     */       
/* 169 */       FeatureFlagEntry entryToAdd = null;
/* 170 */       ScopeType selectedBuffer = null;
/*     */ 
/*     */       
/* 173 */       if (globalEntry != null && (entryToAdd == null || globalEntry.nanos.longValue() > entryToAdd.nanos.longValue())) {
/* 174 */         entryToAdd = globalEntry;
/* 175 */         selectedBuffer = ScopeType.GLOBAL;
/*     */       } 
/* 177 */       if (isolationEntry != null && (entryToAdd == null || isolationEntry
/* 178 */         .nanos.longValue() > entryToAdd.nanos.longValue())) {
/* 179 */         entryToAdd = isolationEntry;
/* 180 */         selectedBuffer = ScopeType.ISOLATION;
/*     */       } 
/* 182 */       if (currentEntry != null && (entryToAdd == null || currentEntry.nanos.longValue() > entryToAdd.nanos.longValue())) {
/* 183 */         entryToAdd = currentEntry;
/* 184 */         selectedBuffer = ScopeType.CURRENT;
/*     */       } 
/*     */       
/* 187 */       if (entryToAdd != null) {
/*     */         
/* 189 */         if (!uniqueFlags.containsKey(entryToAdd.flag)) {
/* 190 */           uniqueFlags.put(entryToAdd.flag, entryToAdd);
/*     */         }
/*     */ 
/*     */         
/* 194 */         if (ScopeType.CURRENT.equals(selectedBuffer)) {
/* 195 */           currentIndex--;
/*     */           
/* 197 */           currentEntry = (currentFlags != null && currentIndex >= 0) ? currentFlags.get(currentIndex) : null; continue;
/* 198 */         }  if (ScopeType.ISOLATION.equals(selectedBuffer)) {
/* 199 */           isolationIndex--;
/*     */ 
/*     */ 
/*     */           
/* 203 */           isolationEntry = (isolationFlags != null && isolationIndex >= 0) ? isolationFlags.get(isolationIndex) : null; continue;
/* 204 */         }  if (ScopeType.GLOBAL.equals(selectedBuffer)) {
/* 205 */           globalIndex--;
/*     */           
/* 207 */           globalEntry = (globalFlags != null && globalIndex >= 0) ? globalFlags.get(globalIndex) : null;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 217 */     List<FeatureFlagEntry> resultList = new ArrayList<>(uniqueFlags.values());
/* 218 */     Collections.reverse(resultList);
/* 219 */     return new FeatureFlagBuffer(maxSize, new CopyOnWriteArrayList<>(resultList));
/*     */   }
/*     */ 
/*     */   
/*     */   private static class FeatureFlagEntry
/*     */   {
/*     */     @NotNull
/*     */     private final String flag;
/*     */     
/*     */     private final boolean result;
/*     */     @NotNull
/*     */     private final Long nanos;
/*     */     
/*     */     public FeatureFlagEntry(@NotNull String flag, boolean result, @NotNull Long nanos) {
/* 233 */       this.flag = flag;
/* 234 */       this.result = result;
/* 235 */       this.nanos = nanos;
/*     */     }
/*     */     @NotNull
/*     */     public FeatureFlag toFeatureFlag() {
/* 239 */       return new FeatureFlag(this.flag, this.result);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\featureflags\FeatureFlagBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */