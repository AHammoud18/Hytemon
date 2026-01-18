/*    */ package io.sentry;
/*    */ 
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class NoOpScopesStorage implements IScopesStorage {
/*  6 */   private static final NoOpScopesStorage instance = new NoOpScopesStorage();
/*    */ 
/*    */ 
/*    */   
/*    */   public static NoOpScopesStorage getInstance() {
/* 11 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {}
/*    */ 
/*    */   
/*    */   public ISentryLifecycleToken set(@Nullable IScopes scopes) {
/* 19 */     return NoOpScopesLifecycleToken.getInstance();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public IScopes get() {
/* 24 */     return NoOpScopes.getInstance();
/*    */   }
/*    */   
/*    */   public void close() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpScopesStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */