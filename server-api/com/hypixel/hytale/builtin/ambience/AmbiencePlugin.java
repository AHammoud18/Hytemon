/*     */ package com.hypixel.hytale.builtin.ambience;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.builtin.ambience.commands.AmbienceCommands;
/*     */ import com.hypixel.hytale.builtin.ambience.components.AmbienceTracker;
/*     */ import com.hypixel.hytale.builtin.ambience.components.AmbientEmitterComponent;
/*     */ import com.hypixel.hytale.builtin.ambience.resources.AmbienceResource;
/*     */ import com.hypixel.hytale.builtin.ambience.systems.AmbientEmitterSystems;
/*     */ import com.hypixel.hytale.builtin.ambience.systems.ForcedMusicSystems;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.Config;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class AmbiencePlugin
/*     */   extends JavaPlugin {
/*     */   public static AmbiencePlugin get() {
/*  30 */     return instance;
/*     */   }
/*     */   
/*     */   private static final String DEFAULT_AMBIENT_EMITTER_MODEL = "NPC_Spawn_Marker";
/*     */   private static AmbiencePlugin instance;
/*     */   private ComponentType<EntityStore, AmbienceTracker> ambienceTrackerComponentType;
/*     */   private ComponentType<EntityStore, AmbientEmitterComponent> ambientEmitterComponentType;
/*     */   private ResourceType<EntityStore, AmbienceResource> ambienceResourceType;
/*  38 */   private final Config<AmbiencePluginConfig> config = withConfig("AmbiencePlugin", AmbiencePluginConfig.CODEC);
/*     */   
/*     */   private Model ambientEmitterModel;
/*     */   
/*     */   public AmbiencePlugin(@Nonnull JavaPluginInit init) {
/*  43 */     super(init);
/*  44 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  49 */     this.ambienceTrackerComponentType = getEntityStoreRegistry().registerComponent(AmbienceTracker.class, AmbienceTracker::new);
/*  50 */     this.ambientEmitterComponentType = getEntityStoreRegistry().registerComponent(AmbientEmitterComponent.class, "AmbientEmitter", AmbientEmitterComponent.CODEC);
/*     */     
/*  52 */     this.ambienceResourceType = getEntityStoreRegistry().registerResource(AmbienceResource.class, AmbienceResource::new);
/*     */     
/*  54 */     getEntityStoreRegistry().registerSystem((ISystem)new AmbientEmitterSystems.EntityAdded());
/*  55 */     getEntityStoreRegistry().registerSystem((ISystem)new AmbientEmitterSystems.EntityRefAdded());
/*  56 */     getEntityStoreRegistry().registerSystem((ISystem)new AmbientEmitterSystems.Ticking());
/*  57 */     getEntityStoreRegistry().registerSystem((ISystem)new ForcedMusicSystems.Tick());
/*  58 */     getEntityStoreRegistry().registerSystem((ISystem)new ForcedMusicSystems.PlayerAdded());
/*     */     
/*  60 */     getCommandRegistry().registerCommand((AbstractCommand)new AmbienceCommands());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start() {
/*  65 */     AmbiencePluginConfig config = (AmbiencePluginConfig)this.config.get();
/*  66 */     String ambientEmitterModelId = config.ambientEmitterModel;
/*  67 */     DefaultAssetMap<String, ModelAsset> modelAssetMap = ModelAsset.getAssetMap();
/*  68 */     ModelAsset modelAsset = (ModelAsset)modelAssetMap.getAsset(ambientEmitterModelId);
/*  69 */     if (modelAsset == null) {
/*  70 */       getLogger().at(Level.SEVERE).log("Ambient emitter model %s does not exist");
/*  71 */       modelAsset = (ModelAsset)modelAssetMap.getAsset("NPC_Spawn_Marker");
/*  72 */       if (modelAsset == null) {
/*  73 */         throw new IllegalStateException(String.format("Default ambient emitter marker '%s' not found", new Object[] { "NPC_Spawn_Marker" }));
/*     */       }
/*     */     } 
/*  76 */     this.ambientEmitterModel = Model.createUnitScaleModel(modelAsset);
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, AmbienceTracker> getAmbienceTrackerComponentType() {
/*  80 */     return this.ambienceTrackerComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, AmbientEmitterComponent> getAmbientEmitterComponentType() {
/*  84 */     return this.ambientEmitterComponentType;
/*     */   }
/*     */   
/*     */   public ResourceType<EntityStore, AmbienceResource> getAmbienceResourceType() {
/*  88 */     return this.ambienceResourceType;
/*     */   }
/*     */   
/*     */   public Model getAmbientEmitterModel() {
/*  92 */     return this.ambientEmitterModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AmbiencePluginConfig
/*     */   {
/*     */     public static final BuilderCodec<AmbiencePluginConfig> CODEC;
/*     */ 
/*     */     
/*     */     static {
/* 103 */       CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(AmbiencePluginConfig.class, AmbiencePluginConfig::new).append(new KeyedCodec("AmbientEmitterModel", (Codec)Codec.STRING), (o, i) -> o.ambientEmitterModel = i, o -> o.ambientEmitterModel).add()).build();
/*     */     }
/* 105 */     private String ambientEmitterModel = "NPC_Spawn_Marker";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\ambience\AmbiencePlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */