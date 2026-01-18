/*    */ package com.hypixel.hytale.builtin.beds;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.beds.interactions.BedInteraction;
/*    */ import com.hypixel.hytale.builtin.beds.sleep.components.PlayerSomnolence;
/*    */ import com.hypixel.hytale.builtin.beds.sleep.components.SleepTracker;
/*    */ import com.hypixel.hytale.builtin.beds.sleep.resources.WorldSomnolence;
/*    */ import com.hypixel.hytale.builtin.beds.sleep.systems.player.EnterBedSystem;
/*    */ import com.hypixel.hytale.builtin.beds.sleep.systems.player.RegisterTrackerSystem;
/*    */ import com.hypixel.hytale.builtin.beds.sleep.systems.player.UpdateSleepPacketSystem;
/*    */ import com.hypixel.hytale.builtin.beds.sleep.systems.player.WakeUpOnDismountSystem;
/*    */ import com.hypixel.hytale.builtin.beds.sleep.systems.world.StartSlumberSystem;
/*    */ import com.hypixel.hytale.builtin.beds.sleep.systems.world.UpdateWorldSlumberSystem;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.system.ISystem;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ public class BedsPlugin extends JavaPlugin {
/*    */   public static BedsPlugin getInstance() {
/* 24 */     return instance;
/*    */   }
/*    */   
/*    */   private static BedsPlugin instance;
/*    */   private ComponentType<EntityStore, PlayerSomnolence> playerSomnolenceComponentType;
/*    */   private ComponentType<EntityStore, SleepTracker> sleepTrackerComponentType;
/*    */   private ResourceType<EntityStore, WorldSomnolence> worldSomnolenceResourceType;
/*    */   
/*    */   public BedsPlugin(JavaPluginInit init) {
/* 33 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 38 */     instance = this;
/*    */     
/* 40 */     this.playerSomnolenceComponentType = getEntityStoreRegistry().registerComponent(PlayerSomnolence.class, PlayerSomnolence::new);
/* 41 */     this.sleepTrackerComponentType = getEntityStoreRegistry().registerComponent(SleepTracker.class, SleepTracker::new);
/*    */     
/* 43 */     this.worldSomnolenceResourceType = getEntityStoreRegistry().registerResource(WorldSomnolence.class, WorldSomnolence::new);
/*    */     
/* 45 */     getEntityStoreRegistry().registerSystem((ISystem)new StartSlumberSystem());
/* 46 */     getEntityStoreRegistry().registerSystem((ISystem)new UpdateSleepPacketSystem());
/* 47 */     getEntityStoreRegistry().registerSystem((ISystem)new WakeUpOnDismountSystem());
/* 48 */     getEntityStoreRegistry().registerSystem((ISystem)new RegisterTrackerSystem());
/* 49 */     getEntityStoreRegistry().registerSystem((ISystem)new UpdateWorldSlumberSystem());
/* 50 */     getEntityStoreRegistry().registerSystem((ISystem)new EnterBedSystem());
/*    */     
/* 52 */     Interaction.CODEC.register("Bed", BedInteraction.class, BedInteraction.CODEC);
/*    */   }
/*    */   
/*    */   public ComponentType<EntityStore, PlayerSomnolence> getPlayerSomnolenceComponentType() {
/* 56 */     return this.playerSomnolenceComponentType;
/*    */   }
/*    */   
/*    */   public ComponentType<EntityStore, SleepTracker> getSleepTrackerComponentType() {
/* 60 */     return this.sleepTrackerComponentType;
/*    */   }
/*    */   
/*    */   public ResourceType<EntityStore, WorldSomnolence> getWorldSomnolenceResourceType() {
/* 64 */     return this.worldSomnolenceResourceType;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\beds\BedsPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */