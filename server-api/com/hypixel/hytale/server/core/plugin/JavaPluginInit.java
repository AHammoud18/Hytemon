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
/*    */ 
/*    */ public class JavaPluginInit
/*    */   extends PluginInit
/*    */ {
/*    */   @Nonnull
/*    */   private final Path file;
/*    */   @Nonnull
/*    */   private final PluginClassLoader classLoader;
/*    */   
/*    */   public JavaPluginInit(@Nonnull PluginManifest pluginManifest, @Nonnull Path dataDirectory, @Nonnull Path file, @Nonnull PluginClassLoader classLoader) {
/* 34 */     super(pluginManifest, dataDirectory);
/* 35 */     this.file = file;
/* 36 */     this.classLoader = classLoader;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Path getFile() {
/* 44 */     return this.file;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PluginClassLoader getClassLoader() {
/* 52 */     return this.classLoader;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInServerClassPath() {
/* 57 */     return this.classLoader.isInServerClassPath();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\JavaPluginInit.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */