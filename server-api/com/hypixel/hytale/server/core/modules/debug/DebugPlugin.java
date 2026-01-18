/*    */ package com.hypixel.hytale.server.core.modules.debug;
/*    */ 
/*    */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.modules.debug.commands.DebugCommand;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DebugPlugin
/*    */   extends JavaPlugin
/*    */ {
/*    */   @Nonnull
/* 23 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(DebugPlugin.class)
/* 24 */     .build();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private static DebugPlugin instance;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static DebugPlugin get() {
/* 39 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DebugPlugin(@Nonnull JavaPluginInit init) {
/* 48 */     super(init);
/* 49 */     instance = this;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 54 */     getCommandRegistry().registerCommand((AbstractCommand)new DebugCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\debug\DebugPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */