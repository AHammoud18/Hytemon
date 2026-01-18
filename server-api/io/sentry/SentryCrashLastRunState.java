/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.AutoClosableReentrantLock;
/*    */ import java.io.File;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import org.jetbrains.annotations.TestOnly;
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class SentryCrashLastRunState
/*    */ {
/* 14 */   private static final SentryCrashLastRunState INSTANCE = new SentryCrashLastRunState();
/*    */   private boolean readCrashedLastRun;
/*    */   @Nullable
/*    */   private Boolean crashedLastRun;
/*    */   @NotNull
/* 19 */   private final AutoClosableReentrantLock crashedLastRunLock = new AutoClosableReentrantLock();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SentryCrashLastRunState getInstance() {
/* 25 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Boolean isCrashedLastRun(@Nullable String cacheDirPath, boolean deleteFile) {
/* 30 */     ISentryLifecycleToken ignored = this.crashedLastRunLock.acquire(); 
/* 31 */     try { if (this.readCrashedLastRun)
/* 32 */       { Boolean bool = this.crashedLastRun;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 62 */         if (ignored != null) ignored.close();  return bool; }  if (cacheDirPath == null) { Boolean bool = null; if (ignored != null) ignored.close();  return bool; }  this.readCrashedLastRun = true; File javaMarker = new File(cacheDirPath, "last_crash"); File nativeMarker = new File(cacheDirPath, ".sentry-native/last_crash"); boolean exists = false; try { if (javaMarker.exists()) { exists = true; javaMarker.delete(); } else if (nativeMarker.exists()) { exists = true; if (deleteFile) nativeMarker.delete();  }  } catch (Throwable throwable) {} this.crashedLastRun = Boolean.valueOf(exists); if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*    */         try { ignored.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 64 */      return this.crashedLastRun;
/*    */   }
/*    */   
/*    */   public void setCrashedLastRun(boolean crashedLastRun) {
/* 68 */     ISentryLifecycleToken ignored = this.crashedLastRunLock.acquire(); 
/* 69 */     try { if (!this.readCrashedLastRun) {
/* 70 */         this.crashedLastRun = Boolean.valueOf(crashedLastRun);
/*    */         
/* 72 */         this.readCrashedLastRun = true;
/*    */       } 
/* 74 */       if (ignored != null) ignored.close();  }
/*    */     catch (Throwable throwable) { if (ignored != null)
/*    */         try { ignored.close(); }
/*    */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */           throw throwable; }
/* 79 */      } @TestOnly public void reset() { ISentryLifecycleToken ignored = this.crashedLastRunLock.acquire(); try {
/* 80 */       this.readCrashedLastRun = false;
/* 81 */       this.crashedLastRun = null;
/* 82 */       if (ignored != null) ignored.close(); 
/*    */     } catch (Throwable throwable) {
/*    */       if (ignored != null)
/*    */         try {
/*    */           ignored.close();
/*    */         } catch (Throwable throwable1) {
/*    */           throwable.addSuppressed(throwable1);
/*    */         }  
/*    */       throw throwable;
/*    */     }  }
/*    */ 
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryCrashLastRunState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */