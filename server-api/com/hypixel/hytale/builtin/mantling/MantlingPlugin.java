/*    */ package com.hypixel.hytale.builtin.mantling;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.packets.setup.ClientFeature;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MantlingPlugin
/*    */   extends JavaPlugin {
/*    */   public MantlingPlugin(@Nonnull JavaPluginInit init) {
/* 11 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 16 */     getClientFeatureRegistry().registerClientTag("Allows=Movement");
/* 17 */     getClientFeatureRegistry().register(ClientFeature.Mantling);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mantling\MantlingPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */