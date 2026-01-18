/*    */ package io.sentry;
/*    */ @Internal
/*    */ public interface IConnectionStatusProvider extends Closeable { @NotNull
/*    */   ConnectionStatus getConnectionStatus();
/*    */   @Nullable
/*    */   String getConnectionType();
/*    */   boolean addConnectionStatusObserver(@NotNull IConnectionStatusObserver paramIConnectionStatusObserver);
/*    */   void removeConnectionStatusObserver(@NotNull IConnectionStatusObserver paramIConnectionStatusObserver);
/*    */   
/*    */   public static interface IConnectionStatusObserver { void onConnectionStatusChanged(@NotNull IConnectionStatusProvider.ConnectionStatus param1ConnectionStatus); }
/*    */   
/* 12 */   public enum ConnectionStatus { UNKNOWN,
/* 13 */     CONNECTED,
/* 14 */     DISCONNECTED,
/* 15 */     NO_PERMISSION; }
/*    */    }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\IConnectionStatusProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */