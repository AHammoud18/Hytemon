/*    */ package com.hypixel.hytale.server.core.modules.time;
/*    */ 
/*    */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*    */ import com.hypixel.hytale.component.ComponentRegistryProxy;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.modules.time.commands.TimeCommand;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimeModule
/*    */   extends JavaPlugin
/*    */ {
/*    */   @Nonnull
/* 21 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(TimeModule.class)
/* 22 */     .build();
/*    */ 
/*    */   
/*    */   private static TimeModule instance;
/*    */   
/*    */   private ResourceType<EntityStore, WorldTimeResource> worldTimeResourceType;
/*    */   
/*    */   private ResourceType<EntityStore, TimeResource> timeResourceType;
/*    */ 
/*    */   
/*    */   public static TimeModule get() {
/* 33 */     return instance;
/*    */   }
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
/*    */   public TimeModule(@Nonnull JavaPluginInit init) {
/* 52 */     super(init);
/* 53 */     instance = this;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 58 */     ComponentRegistryProxy<EntityStore> entityStoreRegistry = getEntityStoreRegistry();
/*    */     
/* 60 */     getCommandRegistry().registerCommand((AbstractCommand)new TimeCommand());
/*    */     
/* 62 */     this.worldTimeResourceType = entityStoreRegistry.registerResource(WorldTimeResource.class, WorldTimeResource::new);
/* 63 */     entityStoreRegistry.registerSystem((ISystem)new WorldTimeSystems.Init(this.worldTimeResourceType));
/* 64 */     entityStoreRegistry.registerSystem((ISystem)new WorldTimeSystems.Ticking(this.worldTimeResourceType));
/*    */ 
/*    */     
/* 67 */     this.timeResourceType = entityStoreRegistry.registerResource(TimeResource.class, "Time", TimeResource.CODEC);
/* 68 */     entityStoreRegistry.registerSystem((ISystem)new TimeSystem(this.timeResourceType));
/*    */     
/* 70 */     entityStoreRegistry.registerSystem((ISystem)new TimePacketSystem(this.worldTimeResourceType));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ResourceType<EntityStore, WorldTimeResource> getWorldTimeResourceType() {
/* 78 */     return this.worldTimeResourceType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ResourceType<EntityStore, TimeResource> getTimeResourceType() {
/* 86 */     return this.timeResourceType;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\time\TimeModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */