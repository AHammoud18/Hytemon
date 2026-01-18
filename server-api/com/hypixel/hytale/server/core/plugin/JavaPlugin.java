/*    */ package com.hypixel.hytale.server.core.plugin;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetPack;
/*    */ import com.hypixel.hytale.common.plugin.PluginIdentifier;
/*    */ import com.hypixel.hytale.server.core.asset.AssetModule;
/*    */ import java.nio.file.Path;
/*    */ import java.util.logging.Level;
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
/*    */ public abstract class JavaPlugin
/*    */   extends PluginBase
/*    */ {
/*    */   @Nonnull
/*    */   private final Path file;
/*    */   @Nonnull
/*    */   private final PluginClassLoader classLoader;
/*    */   
/*    */   public JavaPlugin(@Nonnull JavaPluginInit init) {
/* 33 */     super(init);
/* 34 */     this.file = init.getFile();
/* 35 */     this.classLoader = init.getClassLoader();
/* 36 */     this.classLoader.setPlugin(this);
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
/*    */   protected void start0() {
/* 49 */     super.start0();
/*    */     
/* 51 */     if (getManifest().includesAssetPack()) {
/* 52 */       AssetModule assetModule = AssetModule.get();
/* 53 */       String id = (new PluginIdentifier(getManifest())).toString();
/* 54 */       AssetPack existing = assetModule.getAssetPack(id);
/* 55 */       if (existing != null) {
/*    */ 
/*    */         
/* 58 */         getLogger().at(Level.WARNING).log("Asset pack %s already exists, skipping embedded pack", id);
/*    */         return;
/*    */       } 
/* 61 */       assetModule.registerPack(id, this.file, getManifest());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PluginClassLoader getClassLoader() {
/* 70 */     return this.classLoader;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public final PluginType getType() {
/* 76 */     return PluginType.PLUGIN;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\JavaPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */