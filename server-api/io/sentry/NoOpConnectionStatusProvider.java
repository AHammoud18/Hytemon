/*    */ package io.sentry;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ @Internal
/*    */ public final class NoOpConnectionStatusProvider implements IConnectionStatusProvider {
/*    */   @NotNull
/*    */   public IConnectionStatusProvider.ConnectionStatus getConnectionStatus() {
/* 12 */     return IConnectionStatusProvider.ConnectionStatus.UNKNOWN;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getConnectionType() {
/* 17 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean addConnectionStatusObserver(@NotNull IConnectionStatusProvider.IConnectionStatusObserver observer) {
/* 22 */     return false;
/*    */   }
/*    */   
/*    */   public void removeConnectionStatusObserver(@NotNull IConnectionStatusProvider.IConnectionStatusObserver observer) {}
/*    */   
/*    */   public void close() throws IOException {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpConnectionStatusProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */