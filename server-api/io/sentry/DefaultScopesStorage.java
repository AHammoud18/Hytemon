/*    */ package io.sentry;
/*    */ 
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ @Internal
/*    */ public final class DefaultScopesStorage implements IScopesStorage {
/*    */   @NotNull
/* 10 */   private static final ThreadLocal<IScopes> currentScopes = new ThreadLocal<>();
/*    */ 
/*    */   
/*    */   public void init() {}
/*    */ 
/*    */   
/*    */   public ISentryLifecycleToken set(@Nullable IScopes scopes) {
/* 17 */     IScopes oldScopes = get();
/* 18 */     currentScopes.set(scopes);
/* 19 */     return new DefaultScopesLifecycleToken(oldScopes);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public IScopes get() {
/* 24 */     return currentScopes.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 29 */     currentScopes.remove();
/*    */   }
/*    */   
/*    */   static final class DefaultScopesLifecycleToken implements ISentryLifecycleToken {
/*    */     @Nullable
/*    */     private final IScopes oldValue;
/*    */     
/*    */     DefaultScopesLifecycleToken(@Nullable IScopes scopes) {
/* 37 */       this.oldValue = scopes;
/*    */     }
/*    */ 
/*    */     
/*    */     public void close() {
/* 42 */       DefaultScopesStorage.currentScopes.set(this.oldValue);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\DefaultScopesStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */