/*     */ package com.hypixel.hytale.builtin.portals;
/*     */ import com.hypixel.hytale.builtin.instances.removal.RemovalCondition;
/*     */ import com.hypixel.hytale.builtin.portals.commands.FragmentCommands;
/*     */ import com.hypixel.hytale.builtin.portals.commands.player.LeaveCommand;
/*     */ import com.hypixel.hytale.builtin.portals.commands.utils.CursedHeldItemCommand;
/*     */ import com.hypixel.hytale.builtin.portals.components.PortalDevice;
/*     */ import com.hypixel.hytale.builtin.portals.components.voidevent.VoidEvent;
/*     */ import com.hypixel.hytale.builtin.portals.components.voidevent.VoidSpawner;
/*     */ import com.hypixel.hytale.builtin.portals.integrations.PortalGameplayConfig;
/*     */ import com.hypixel.hytale.builtin.portals.integrations.PortalMarkerProvider;
/*     */ import com.hypixel.hytale.builtin.portals.integrations.PortalRemovalCondition;
/*     */ import com.hypixel.hytale.builtin.portals.interactions.EnterPortalInteraction;
/*     */ import com.hypixel.hytale.builtin.portals.interactions.ReturnPortalInteraction;
/*     */ import com.hypixel.hytale.builtin.portals.resources.PortalWorld;
/*     */ import com.hypixel.hytale.builtin.portals.systems.CloseWorldWhenBreakingDeviceSystems;
/*     */ import com.hypixel.hytale.builtin.portals.systems.PortalInvalidDestinationSystem;
/*     */ import com.hypixel.hytale.builtin.portals.systems.PortalTrackerSystems;
/*     */ import com.hypixel.hytale.builtin.portals.systems.curse.CurseItemDropsSystem;
/*     */ import com.hypixel.hytale.builtin.portals.systems.curse.DeleteCursedItemsOnSpawnSystem;
/*     */ import com.hypixel.hytale.builtin.portals.systems.curse.DiedInPortalSystem;
/*     */ import com.hypixel.hytale.builtin.portals.systems.voidevent.VoidEventRefSystem;
/*     */ import com.hypixel.hytale.builtin.portals.systems.voidevent.VoidEventStagesSystem;
/*     */ import com.hypixel.hytale.builtin.portals.systems.voidevent.VoidSpawnerSystems;
/*     */ import com.hypixel.hytale.builtin.portals.ui.PortalDevicePageSupplier;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.lookup.StringCodecMapCodec;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.events.AddWorldEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.events.RemoveWorldEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class PortalsPlugin extends JavaPlugin {
/*     */   private static PortalsPlugin instance;
/*     */   private ResourceType<EntityStore, PortalWorld> portalResourceType;
/*     */   private ComponentType<ChunkStore, PortalDevice> portalDeviceComponentType;
/*     */   
/*     */   public static PortalsPlugin getInstance() {
/*  53 */     return instance;
/*     */   }
/*     */   private ComponentType<EntityStore, VoidEvent> voidEventComponentType; private ComponentType<EntityStore, VoidSpawner> voidPortalComponentType; public static final int MAX_CONCURRENT_FRAGMENTS = 4;
/*     */   public PortalsPlugin(@Nonnull JavaPluginInit init) {
/*  57 */     super(init);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  62 */     instance = this;
/*     */     
/*  64 */     this.portalResourceType = getEntityStoreRegistry().registerResource(PortalWorld.class, PortalWorld::new);
/*     */     
/*  66 */     this.portalDeviceComponentType = getChunkStoreRegistry().registerComponent(PortalDevice.class, "Portal", PortalDevice.CODEC);
/*  67 */     this.voidEventComponentType = getEntityStoreRegistry().registerComponent(VoidEvent.class, VoidEvent::new);
/*  68 */     this.voidPortalComponentType = getEntityStoreRegistry().registerComponent(VoidSpawner.class, VoidSpawner::new);
/*     */     
/*  70 */     getCodecRegistry((StringCodecMapCodec)OpenCustomUIInteraction.PAGE_CODEC).register("PortalDevice", PortalDevicePageSupplier.class, (Codec)PortalDevicePageSupplier.CODEC);
/*     */     
/*  72 */     getCodecRegistry(Interaction.CODEC)
/*  73 */       .register("Portal", EnterPortalInteraction.class, EnterPortalInteraction.CODEC)
/*  74 */       .register("PortalReturn", ReturnPortalInteraction.class, ReturnPortalInteraction.CODEC);
/*     */ 
/*     */     
/*  77 */     getEventRegistry().registerGlobal(RemoveWorldEvent.class, this::turnOffPortalWhenWorldRemoved);
/*  78 */     getEventRegistry().registerGlobal(AddWorldEvent.class, event -> event.getWorld().getWorldMapManager().addMarkerProvider("portals", (WorldMapManager.MarkerProvider)PortalMarkerProvider.INSTANCE));
/*     */     
/*  80 */     getChunkStoreRegistry().registerSystem((ISystem)new PortalInvalidDestinationSystem());
/*  81 */     getChunkStoreRegistry().registerSystem((ISystem)new CloseWorldWhenBreakingDeviceSystems.ComponentRemoved());
/*  82 */     getChunkStoreRegistry().registerSystem((ISystem)new CloseWorldWhenBreakingDeviceSystems.EntityRemoved());
/*     */     
/*  84 */     getEntityStoreRegistry().registerSystem((ISystem)new PortalTrackerSystems.TrackerSystem());
/*  85 */     getEntityStoreRegistry().registerSystem((ISystem)new PortalTrackerSystems.UiTickingSystem());
/*  86 */     getEntityStoreRegistry().registerSystem((ISystem)new DiedInPortalSystem());
/*  87 */     getEntityStoreRegistry().registerSystem((ISystem)new CurseItemDropsSystem());
/*  88 */     getEntityStoreRegistry().registerSystem((ISystem)new DeleteCursedItemsOnSpawnSystem());
/*     */     
/*  90 */     getEntityStoreRegistry().registerSystem((ISystem)new VoidEventRefSystem());
/*  91 */     getEntityStoreRegistry().registerSystem((ISystem)new VoidInvasionPortalsSpawnSystem());
/*  92 */     getEntityStoreRegistry().registerSystem((ISystem)new VoidSpawnerSystems.Instantiate());
/*  93 */     getEntityStoreRegistry().registerSystem((ISystem)new StartVoidEventInFragmentSystem());
/*  94 */     getEntityStoreRegistry().registerSystem((ISystem)new VoidEventStagesSystem());
/*     */     
/*  96 */     getCommandRegistry().registerCommand((AbstractCommand)new LeaveCommand());
/*  97 */     getCommandRegistry().registerCommand((AbstractCommand)new CursedHeldItemCommand());
/*  98 */     getCommandRegistry().registerCommand((AbstractCommand)new VoidEventCommands());
/*  99 */     getCommandRegistry().registerCommand((AbstractCommand)new FragmentCommands());
/*     */     
/* 101 */     getCodecRegistry((StringCodecMapCodec)RemovalCondition.CODEC)
/* 102 */       .register("Portal", PortalRemovalCondition.class, (Codec)PortalRemovalCondition.CODEC);
/*     */     
/* 104 */     getCodecRegistry(GameplayConfig.PLUGIN_CODEC)
/* 105 */       .register(PortalGameplayConfig.class, "Portal", (Codec)PortalGameplayConfig.CODEC);
/*     */   }
/*     */   
/*     */   private void turnOffPortalWhenWorldRemoved(RemoveWorldEvent event) {
/* 109 */     Collection<World> worlds = Universe.get().getWorlds().values();
/* 110 */     for (World world : worlds) {
/* 111 */       if (world == event.getWorld())
/* 112 */         continue;  world.execute(() -> PortalInvalidDestinationSystem.turnOffPortalsInWorld(world, event.getWorld()));
/*     */     } 
/*     */   }
/*     */   
/*     */   public int countActiveFragments() {
/* 117 */     Map<String, World> worlds = Universe.get().getWorlds();
/* 118 */     int count = 0;
/* 119 */     for (World world : worlds.values()) {
/* 120 */       PortalGameplayConfig portalConfig = (PortalGameplayConfig)world.getGameplayConfig().getPluginConfig().get(PortalGameplayConfig.class);
/* 121 */       if (portalConfig != null) {
/* 122 */         count++;
/*     */       }
/*     */     } 
/* 125 */     return count;
/*     */   }
/*     */   
/*     */   public ResourceType<EntityStore, PortalWorld> getPortalResourceType() {
/* 129 */     return this.portalResourceType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, PortalDevice> getPortalDeviceComponentType() {
/* 133 */     return this.portalDeviceComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, VoidEvent> getVoidEventComponentType() {
/* 137 */     return this.voidEventComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, VoidSpawner> getVoidPortalComponentType() {
/* 141 */     return this.voidPortalComponentType;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\PortalsPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */