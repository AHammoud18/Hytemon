/*     */ package com.hypixel.hytale.builtin.parkour;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2LongMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
/*     */ import java.util.Collection;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ParkourPlugin extends JavaPlugin {
/*     */   public static ParkourPlugin get() {
/*  25 */     return instance;
/*     */   }
/*     */   
/*     */   protected static ParkourPlugin instance;
/*     */   public static final String PARKOUR_CHECKPOINT_MODEL_ID = "Objective_Location_Marker";
/*  30 */   private final Object2IntMap<UUID> currentCheckpointByPlayerMap = (Object2IntMap<UUID>)new Object2IntOpenHashMap();
/*  31 */   private final Object2LongMap<UUID> startTimeByPlayerMap = (Object2LongMap<UUID>)new Object2LongOpenHashMap();
/*  32 */   private final Int2ObjectMap<UUID> checkpointUUIDMap = (Int2ObjectMap<UUID>)new Int2ObjectOpenHashMap();
/*     */   
/*     */   private ComponentType<EntityStore, ParkourCheckpoint> parkourCheckpointComponentType;
/*     */   private Model parkourCheckpointModel;
/*     */   private int lastIndex;
/*     */   
/*     */   public ParkourPlugin(@Nonnull JavaPluginInit init) {
/*  39 */     super(init);
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, ParkourCheckpoint> getParkourCheckpointComponentType() {
/*  43 */     return this.parkourCheckpointComponentType;
/*     */   }
/*     */   
/*     */   public Model getParkourCheckpointModel() {
/*  47 */     return this.parkourCheckpointModel;
/*     */   }
/*     */   
/*     */   public Object2IntMap<UUID> getCurrentCheckpointByPlayerMap() {
/*  51 */     return this.currentCheckpointByPlayerMap;
/*     */   }
/*     */   
/*     */   public Object2LongMap<UUID> getStartTimeByPlayerMap() {
/*  55 */     return this.startTimeByPlayerMap;
/*     */   }
/*     */   
/*     */   public Int2ObjectMap<UUID> getCheckpointUUIDMap() {
/*  59 */     return this.checkpointUUIDMap;
/*     */   }
/*     */   
/*     */   public int getLastIndex() {
/*  63 */     return this.lastIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  68 */     instance = this;
/*     */     
/*  70 */     this.parkourCheckpointComponentType = getEntityStoreRegistry().registerComponent(ParkourCheckpoint.class, "ParkourCheckpoint", ParkourCheckpoint.CODEC);
/*     */     
/*  72 */     EntityModule entityModule = EntityModule.get();
/*  73 */     ComponentType<EntityStore, Player> playerComponentType = entityModule.getPlayerComponentType();
/*  74 */     ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> playerSpatialComponent = entityModule.getPlayerSpatialResourceType();
/*     */     
/*  76 */     getEntityStoreRegistry().registerSystem((ISystem)new ParkourCheckpointSystems.EnsureNetworkSendable());
/*  77 */     getEntityStoreRegistry().registerSystem((ISystem)new ParkourCheckpointSystems.Init(this.parkourCheckpointComponentType));
/*  78 */     getEntityStoreRegistry().registerSystem((ISystem)new ParkourCheckpointSystems.Ticking(this.parkourCheckpointComponentType, playerComponentType, playerSpatialComponent));
/*     */     
/*  80 */     getCommandRegistry().registerCommand((AbstractCommand)new ParkourCommand());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start() {
/*  85 */     ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset("Objective_Location_Marker");
/*  86 */     if (modelAsset == null) {
/*  87 */       throw new IllegalStateException(String.format("Default parkour checkpoint model '%s' not found", new Object[] { "Objective_Location_Marker" }));
/*     */     }
/*     */     
/*  90 */     this.parkourCheckpointModel = Model.createUnitScaleModel(modelAsset);
/*     */   }
/*     */   
/*     */   public void updateLastIndex(int index) {
/*  94 */     if (index > this.lastIndex) this.lastIndex = index; 
/*     */   }
/*     */   
/*     */   public void updateLastIndex() {
/*  98 */     this.lastIndex = ((Integer)Collections.<Integer>max((Collection<? extends Integer>)this.checkpointUUIDMap.keySet())).intValue();
/*     */   }
/*     */   
/*     */   public void resetPlayer(UUID playerUuid) {
/* 102 */     this.currentCheckpointByPlayerMap.replace(playerUuid, -1);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\parkour\ParkourPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */