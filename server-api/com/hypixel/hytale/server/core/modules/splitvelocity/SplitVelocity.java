/*    */ package com.hypixel.hytale.server.core.modules.splitvelocity;
/*    */ 
/*    */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*    */ import com.hypixel.hytale.protocol.packets.setup.ClientFeature;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SplitVelocity
/*    */   extends JavaPlugin {
/* 11 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(SplitVelocity.class)
/* 12 */     .build();
/*    */   
/*    */   public static boolean SHOULD_MODIFY_VELOCITY = true;
/*    */   
/*    */   public SplitVelocity(@Nonnull JavaPluginInit init) {
/* 17 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 22 */     getClientFeatureRegistry().register(ClientFeature.SplitVelocity);
/* 23 */     SHOULD_MODIFY_VELOCITY = false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void shutdown() {
/* 28 */     SHOULD_MODIFY_VELOCITY = true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\splitvelocity\SplitVelocity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */