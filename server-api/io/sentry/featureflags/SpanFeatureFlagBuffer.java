/*    */ package io.sentry.featureflags;
/*    */ 
/*    */ import io.sentry.ISentryLifecycleToken;
/*    */ import io.sentry.protocol.FeatureFlag;
/*    */ import io.sentry.protocol.FeatureFlags;
/*    */ import io.sentry.util.AutoClosableReentrantLock;
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class SpanFeatureFlagBuffer
/*    */   implements IFeatureFlagBuffer
/*    */ {
/*    */   private static final int MAX_SIZE = 10;
/*    */   @Nullable
/* 30 */   private Map<String, Boolean> flags = null; @NotNull
/* 31 */   private final AutoClosableReentrantLock lock = new AutoClosableReentrantLock();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(@Nullable String flag, @Nullable Boolean result) {
/* 37 */     if (flag == null || result == null) {
/*    */       return;
/*    */     }
/* 40 */     ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 41 */     try { if (this.flags == null) {
/* 42 */         this.flags = new LinkedHashMap<>(10);
/*    */       }
/*    */       
/* 45 */       if (this.flags.size() < 10 || this.flags.containsKey(flag)) {
/* 46 */         this.flags.put(flag, result);
/*    */       }
/* 48 */       if (ignored != null) ignored.close();  }
/*    */     catch (Throwable throwable) { if (ignored != null)
/*    */         try { ignored.close(); }
/*    */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */           throw throwable; }
/* 53 */      } @Nullable public FeatureFlags getFeatureFlags() { ISentryLifecycleToken ignored = this.lock.acquire(); try {
/* 54 */       if (this.flags == null || this.flags.isEmpty())
/* 55 */       { FeatureFlags featureFlags2 = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 63 */         if (ignored != null) ignored.close();  return featureFlags2; }  List<FeatureFlag> featureFlags = new ArrayList<>(this.flags.size()); for (Map.Entry<String, Boolean> entry : this.flags.entrySet()) featureFlags.add(new FeatureFlag(entry.getKey(), ((Boolean)entry.getValue()).booleanValue()));  FeatureFlags featureFlags1 = new FeatureFlags(featureFlags); if (ignored != null) ignored.close(); 
/*    */       return featureFlags1;
/*    */     } catch (Throwable throwable) {
/*    */       if (ignored != null)
/*    */         try {
/*    */           ignored.close();
/*    */         } catch (Throwable throwable1) {
/*    */           throwable.addSuppressed(throwable1);
/*    */         }  
/*    */       throw throwable;
/* 73 */     }  } @NotNull public IFeatureFlagBuffer clone() { return create(); }
/*    */   
/*    */   @NotNull
/*    */   public static IFeatureFlagBuffer create() {
/* 77 */     return new SpanFeatureFlagBuffer();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\featureflags\SpanFeatureFlagBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */