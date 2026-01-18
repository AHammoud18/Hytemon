/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.AutoClosableReentrantLock;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ @Internal
/*    */ public final class SentrySpanStorage
/*    */ {
/*    */   @Nullable
/*    */   private static volatile SentrySpanStorage INSTANCE;
/*    */   @NotNull
/* 20 */   private static final AutoClosableReentrantLock staticLock = new AutoClosableReentrantLock();
/*    */   
/*    */   @NotNull
/*    */   public static SentrySpanStorage getInstance() {
/* 24 */     if (INSTANCE == null) {
/* 25 */       ISentryLifecycleToken ignored = staticLock.acquire(); 
/* 26 */       try { if (INSTANCE == null) {
/* 27 */           INSTANCE = new SentrySpanStorage();
/*    */         }
/* 29 */         if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*    */           try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*    */     
/* 32 */     }  return INSTANCE;
/*    */   }
/*    */   @NotNull
/* 35 */   private final Map<String, ISpan> spans = new ConcurrentHashMap<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public void store(@NotNull String spanId, @NotNull ISpan span) {
/* 40 */     this.spans.put(spanId, span);
/*    */   }
/*    */   @Nullable
/*    */   public ISpan get(@Nullable String spanId) {
/* 44 */     return this.spans.get(spanId);
/*    */   }
/*    */   @Nullable
/*    */   public ISpan removeAndGet(@Nullable String spanId) {
/* 48 */     return this.spans.remove(spanId);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentrySpanStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */