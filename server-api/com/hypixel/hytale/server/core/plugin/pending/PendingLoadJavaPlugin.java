/*    */ package com.hypixel.hytale.server.core.plugin.pending;
/*    */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.core.plugin.PluginBase;
/*    */ import com.hypixel.hytale.server.core.plugin.PluginClassLoader;
/*    */ import com.hypixel.hytale.server.core.plugin.PluginManager;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.nio.file.Path;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class PendingLoadJavaPlugin extends PendingLoadPlugin {
/* 16 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*    */   
/*    */   @Nonnull
/*    */   private final PluginClassLoader urlClassLoader;
/*    */   
/*    */   public PendingLoadJavaPlugin(@Nullable Path path, @Nonnull PluginManifest manifest, @Nonnull PluginClassLoader urlClassLoader) {
/* 22 */     super(path, manifest);
/* 23 */     this.urlClassLoader = urlClassLoader;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public PendingLoadPlugin createSubPendingLoadPlugin(@Nonnull PluginManifest manifest) {
/* 29 */     return new PendingLoadJavaPlugin(getPath(), manifest, this.urlClassLoader);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInServerClassPath() {
/* 34 */     return this.urlClassLoader.isInServerClassPath();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public JavaPlugin load() {
/*    */     try {
/* 41 */       PluginManifest manifest = getManifest();
/*    */       
/* 43 */       Class<?> mainClass = this.urlClassLoader.loadLocalClass(manifest.getMain());
/* 44 */       if (JavaPlugin.class.isAssignableFrom(mainClass)) {
/* 45 */         Constructor<?> constructor = mainClass.getConstructor(new Class[] { JavaPluginInit.class });
/* 46 */         Path dataDirectory = PluginManager.MODS_PATH.resolve(manifest.getGroup() + "_" + manifest.getGroup());
/* 47 */         JavaPluginInit init = new JavaPluginInit(manifest, dataDirectory, getPath(), this.urlClassLoader);
/* 48 */         return (JavaPlugin)constructor.newInstance(new Object[] { init });
/*    */       } 
/* 50 */       throw new ClassCastException(manifest.getMain() + " does not extend JavaPlugin");
/*    */     }
/* 52 */     catch (ClassNotFoundException e) {
/* 53 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to load plugin %s. Failed to find main class!", getPath());
/* 54 */     } catch (NoSuchMethodException e) {
/* 55 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to load plugin %s. Requires default constructor!", getPath());
/* 56 */     } catch (Throwable e) {
/* 57 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(e)).log("Failed to load plugin %s", getPath());
/*    */     } 
/* 59 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 65 */     return "PendingLoadJavaPlugin{" + super.toString() + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\pending\PendingLoadJavaPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */