/*     */ package com.hypixel.hytale.server.core.plugin;
/*     */ 
/*     */ import com.hypixel.hytale.common.plugin.PluginIdentifier;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.server.core.plugin.pages.PluginListPage;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PluginListPageManager
/*     */ {
/*     */   public static PluginListPageManager instance;
/*     */   @Nonnull
/*  27 */   private final List<PluginListPage> activePages = new CopyOnWriteArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PluginListPageManager() {
/*  34 */     instance = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static PluginListPageManager get() {
/*  42 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerPluginListPage(@Nonnull PluginListPage page) {
/*  51 */     if (this.activePages.contains(page))
/*  52 */       return;  this.activePages.add(page);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deregisterPluginListPage(@Nonnull PluginListPage page) {
/*  61 */     if (!this.activePages.contains(page)) {
/*     */       return;
/*     */     }
/*  64 */     this.activePages.remove(page);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyPluginChange(@Nonnull Map<PluginIdentifier, PluginBase> plugins, @Nonnull PluginIdentifier pluginIdentifier) {
/*  74 */     PluginBase plugin = plugins.get(pluginIdentifier);
/*  75 */     this.activePages.forEach(page -> page.handlePluginChangeEvent(pluginIdentifier, 
/*  76 */           (plugin != null && plugin.isEnabled())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SessionSettings
/*     */     implements Component<EntityStore>
/*     */   {
/*     */     public boolean descriptiveOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SessionSettings() {
/*  96 */       this.descriptiveOnly = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SessionSettings(boolean descriptiveOnly) {
/* 105 */       this.descriptiveOnly = descriptiveOnly;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public static ComponentType<EntityStore, SessionSettings> getComponentType() {
/* 113 */       return PluginManager.get().getSessionSettingsComponentType();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Component<EntityStore> clone() {
/* 119 */       return new SessionSettings(this.descriptiveOnly);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\PluginListPageManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */