/*    */ package com.hypixel.hytale.server.core.plugin;
/*    */ 
/*    */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class PluginInit
/*    */ {
/*    */   @Nonnull
/*    */   private final PluginManifest pluginManifest;
/*    */   @Nonnull
/*    */   private final Path dataDirectory;
/*    */   
/*    */   public PluginInit(@Nonnull PluginManifest pluginManifest, @Nonnull Path dataDirectory) {
/* 32 */     this.pluginManifest = pluginManifest;
/* 33 */     this.dataDirectory = dataDirectory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PluginManifest getPluginManifest() {
/* 41 */     return this.pluginManifest;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Path getDataDirectory() {
/* 49 */     return this.dataDirectory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isInServerClassPath() {
/* 56 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\PluginInit.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */