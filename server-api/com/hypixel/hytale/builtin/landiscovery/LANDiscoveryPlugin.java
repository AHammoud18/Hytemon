/*    */ package com.hypixel.hytale.builtin.landiscovery;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.packets.serveraccess.Access;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.modules.singleplayer.SingleplayerRequestAccessEvent;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class LANDiscoveryPlugin
/*    */   extends JavaPlugin {
/*    */   @Nullable
/*    */   private LANDiscoveryThread lanDiscoveryThread;
/*    */   private static LANDiscoveryPlugin instance;
/*    */   
/*    */   public static LANDiscoveryPlugin get() {
/* 18 */     return instance;
/*    */   }
/*    */   
/*    */   public LANDiscoveryPlugin(@Nonnull JavaPluginInit init) {
/* 22 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 27 */     instance = this;
/*    */     
/* 29 */     getCommandRegistry().registerCommand((AbstractCommand)new LANDiscoveryCommand());
/* 30 */     getEventRegistry().registerGlobal(SingleplayerRequestAccessEvent.class, event -> setLANDiscoveryEnabled((event.getAccess() != Access.Private)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start() {
/* 35 */     if (this.lanDiscoveryThread != null) throw new IllegalArgumentException("Listener thread already exists!");
/*    */   
/*    */   }
/*    */   
/*    */   protected void shutdown() {
/* 40 */     if (this.lanDiscoveryThread != null) setLANDiscoveryEnabled(false); 
/*    */   }
/*    */   
/*    */   public void setLANDiscoveryEnabled(boolean enabled) {
/* 44 */     if (!enabled && this.lanDiscoveryThread != null) {
/* 45 */       this.lanDiscoveryThread.interrupt();
/* 46 */       this.lanDiscoveryThread.getSocket().close();
/* 47 */       this.lanDiscoveryThread = null;
/* 48 */     } else if (enabled && this.lanDiscoveryThread == null) {
/* 49 */       this.lanDiscoveryThread = new LANDiscoveryThread();
/* 50 */       this.lanDiscoveryThread.start();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isLANDiscoveryEnabled() {
/* 55 */     return (this.lanDiscoveryThread != null);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public LANDiscoveryThread getLanDiscoveryThread() {
/* 60 */     return this.lanDiscoveryThread;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\landiscovery\LANDiscoveryPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */