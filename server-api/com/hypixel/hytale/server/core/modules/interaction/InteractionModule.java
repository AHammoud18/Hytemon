/*     */ package com.hypixel.hytale.server.core.modules.interaction;
/*     */ import com.hypixel.hytale.assetstore.AssetMap;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
/*     */ import com.hypixel.hytale.assetstore.event.RemovedAssetsEvent;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.set.SetCodec;
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentRegistryProxy;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.event.EventRegistry;
/*     */ import com.hypixel.hytale.event.IBaseEvent;
/*     */ import com.hypixel.hytale.event.IEventDispatcher;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.MouseButtonType;
/*     */ import com.hypixel.hytale.protocol.WorldInteraction;
/*     */ import com.hypixel.hytale.protocol.packets.player.MouseInteraction;
/*     */ import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
/*     */ import com.hypixel.hytale.server.core.asset.packet.AssetPacketGenerator;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.CameraManager;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*     */ import com.hypixel.hytale.server.core.event.events.player.PlayerMouseButtonEvent;
/*     */ import com.hypixel.hytale.server.core.event.events.player.PlayerMouseMotionEvent;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.blocktrack.BlockCounter;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.blocktrack.TrackedPlacement;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.components.PlacedByInteractionComponent;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.UnarmedInteractions;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.AddItemInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.ApplyForceInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.BlockConditionInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.ChainingInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.ChangeBlockInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.CooldownConditionInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.FirstClickInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.PlaceBlockInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.UseEntityInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.ChainFlagInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.ChangeActiveSlotInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.ConditionInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.EffectConditionInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.RunRootInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.SelectInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.simple.RemoveEntityInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.simple.SendMessageInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.selector.AOECircleSelector;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.selector.AOECylinderSelector;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.selector.SelectorType;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.ChangeStatWithModifierInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.DestroyConditionInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.DoorInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.ModifyInventoryInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.OpenCustomUIInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.PlacementCountConditionInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.RefillContainerInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.SpawnPrefabInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.DirectionalKnockback;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.ForceKnockback;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat.Knockback;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.system.InteractionSystems;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.state.LaunchPad;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class InteractionModule extends JavaPlugin {
/*     */   @Nonnull
/*  87 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(InteractionModule.class)
/*  88 */     .depends(new Class[] { EntityModule.class
/*  89 */       }).build();
/*     */ 
/*     */   
/*     */   @Nonnull
/*  93 */   public static final EnumCodec<InteractionType> INTERACTION_TYPE_CODEC = new EnumCodec(InteractionType.class);
/*     */   
/*     */   @Nonnull
/*  96 */   public static final SetCodec<InteractionType, EnumSet<InteractionType>> INTERACTION_TYPE_SET_CODEC = new SetCodec((Codec)INTERACTION_TYPE_CODEC, () -> EnumSet.noneOf(InteractionType.class), true);
/*     */ 
/*     */ 
/*     */   
/*     */   private static InteractionModule instance;
/*     */ 
/*     */ 
/*     */   
/*     */   private ComponentType<EntityStore, InteractionManager> interactionManagerComponent;
/*     */ 
/*     */ 
/*     */   
/*     */   private ComponentType<EntityStore, ChainingInteraction.Data> chainingDataComponent;
/*     */ 
/*     */ 
/*     */   
/*     */   private ComponentType<EntityStore, Interactions> interactionsComponentType;
/*     */ 
/*     */ 
/*     */   
/*     */   private ComponentType<ChunkStore, PlacedByInteractionComponent> placedByComponentType;
/*     */ 
/*     */ 
/*     */   
/*     */   private ResourceType<ChunkStore, BlockCounter> blockCounterResourceType;
/*     */ 
/*     */ 
/*     */   
/*     */   private ComponentType<ChunkStore, TrackedPlacement> trackedPlacementComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static InteractionModule get() {
/* 131 */     return instance;
/*     */   }
/*     */   
/*     */   public InteractionModule(@Nonnull JavaPluginInit init) {
/* 135 */     super(init);
/* 136 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/* 141 */     EventRegistry eventRegistry = getEventRegistry();
/* 142 */     ComponentRegistryProxy<EntityStore> entityStoreRegistry = getEntityStoreRegistry();
/*     */     
/* 144 */     getCommandRegistry().registerCommand((AbstractCommand)new InteractionCommand());
/*     */     
/* 146 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(UnarmedInteractions.class, (AssetMap)new DefaultAssetMap())
/* 147 */         .setPath("Item/Unarmed/Interactions"))
/* 148 */         .setCodec((AssetCodec)UnarmedInteractions.CODEC))
/* 149 */         .setKeyFunction(UnarmedInteractions::getId))
/* 150 */         .loadsAfter(new Class[] { RootInteraction.class
/* 151 */           })).setPacketGenerator((AssetPacketGenerator)new UnarmedInteractionsPacketGenerator())
/* 152 */         .build());
/*     */     
/* 154 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(Interaction.class, (AssetMap)new IndexedLookupTableAssetMap(x$0 -> new Interaction[x$0]))
/* 155 */         .setPath("Item/Interactions"))
/* 156 */         .setCodec((AssetCodec)Interaction.CODEC))
/* 157 */         .setKeyFunction(Interaction::getId))
/* 158 */         .setReplaceOnRemove(id -> new SendMessageInteraction(id, "Missing interaction: " + id)))
/* 159 */         .setIsUnknown(Interaction::isUnknown))
/* 160 */         .setPacketGenerator((AssetPacketGenerator)new InteractionPacketGenerator())
/* 161 */         .loadsAfter(new Class[] { EntityStatType.class, EntityEffect.class, Trail.class, ItemPlayerAnimations.class, SoundEvent.class, ParticleSystem.class, ModelAsset.class, HitboxCollisionConfig.class
/*     */           
/* 163 */           })).preLoadAssets(List.of(ChangeActiveSlotInteraction.DEFAULT_INTERACTION)))
/* 164 */         .build());
/*     */     
/* 166 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(RootInteraction.class, (AssetMap)new IndexedLookupTableAssetMap(x$0 -> new RootInteraction[x$0]))
/* 167 */         .setPath("Item/RootInteractions"))
/* 168 */         .setCodec((AssetCodec)RootInteraction.CODEC))
/* 169 */         .setKeyFunction(RootInteraction::getId))
/* 170 */         .setReplaceOnRemove(x$0 -> new RootInteraction(x$0, new String[0])))
/* 171 */         .setPacketGenerator((AssetPacketGenerator)new RootInteractionPacketGenerator())
/* 172 */         .loadsAfter(new Class[] { Interaction.class
/* 173 */           })).loadsBefore(new Class[] { BlockType.class, Item.class
/* 174 */           })).preLoadAssets(List.of(ChangeActiveSlotInteraction.DEFAULT_ROOT)))
/* 175 */         .build());
/*     */     
/* 177 */     this.interactionManagerComponent = getEntityStoreRegistry().registerComponent(InteractionManager.class, () -> {
/*     */           throw new UnsupportedOperationException();
/*     */         });
/* 180 */     this.interactionsComponentType = getEntityStoreRegistry().registerComponent(Interactions.class, "Interactions", Interactions.CODEC);
/* 181 */     this.placedByComponentType = getChunkStoreRegistry().registerComponent(PlacedByInteractionComponent.class, "PlacedByInteraction", PlacedByInteractionComponent.CODEC);
/* 182 */     Interaction.CODEC.register("Simple", SimpleInteraction.class, SimpleInteraction.CODEC);
/* 183 */     Interaction.CODEC.register("PlaceBlock", PlaceBlockInteraction.class, PlaceBlockInteraction.CODEC);
/* 184 */     Interaction.CODEC.register("PlaceFluid", PlaceFluidInteraction.class, PlaceFluidInteraction.CODEC);
/* 185 */     Interaction.CODEC.register("BreakBlock", BreakBlockInteraction.class, BreakBlockInteraction.CODEC);
/* 186 */     Interaction.CODEC.register("PickBlock", PickBlockInteraction.class, PickBlockInteraction.CODEC);
/* 187 */     Interaction.CODEC.register("UseBlock", UseBlockInteraction.class, UseBlockInteraction.CODEC);
/* 188 */     Interaction.CODEC.register("BlockCondition", BlockConditionInteraction.class, BlockConditionInteraction.CODEC);
/* 189 */     Interaction.CODEC.register("ChangeBlock", ChangeBlockInteraction.class, ChangeBlockInteraction.CODEC);
/* 190 */     Interaction.CODEC.register("ChangeState", ChangeStateInteraction.class, ChangeStateInteraction.CODEC);
/* 191 */     Interaction.CODEC.register("UseEntity", UseEntityInteraction.class, UseEntityInteraction.CODEC);
/* 192 */     Interaction.CODEC.register("BuilderTool", BuilderToolInteraction.class, BuilderToolInteraction.CODEC);
/* 193 */     Interaction.CODEC.register("ModifyInventory", ModifyInventoryInteraction.class, ModifyInventoryInteraction.CODEC);
/* 194 */     Interaction.CODEC.register("Charging", ChargingInteraction.class, ChargingInteraction.CODEC);
/* 195 */     Interaction.CODEC.register("DestroyBlock", DestroyBlockInteraction.class, DestroyBlockInteraction.CODEC);
/* 196 */     Interaction.CODEC.register("CycleBlockGroup", CycleBlockGroupInteraction.class, CycleBlockGroupInteraction.CODEC);
/*     */     
/* 198 */     Interaction.CODEC.register("Explode", ExplodeInteraction.class, ExplodeInteraction.CODEC);
/*     */     
/* 200 */     Interaction.CODEC.register("Chaining", ChainingInteraction.class, ChainingInteraction.CODEC);
/* 201 */     Interaction.CODEC.register("ChainFlag", ChainFlagInteraction.class, ChainFlagInteraction.CODEC);
/* 202 */     Interaction.CODEC.register("CancelChain", CancelChainInteraction.class, CancelChainInteraction.CODEC);
/* 203 */     this.chainingDataComponent = getEntityStoreRegistry().registerComponent(ChainingInteraction.Data.class, com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.ChainingInteraction.Data::new);
/*     */     
/* 205 */     Interaction.CODEC.register("Condition", ConditionInteraction.class, ConditionInteraction.CODEC);
/* 206 */     Interaction.CODEC.register("FirstClick", FirstClickInteraction.class, FirstClickInteraction.CODEC);
/* 207 */     Interaction.CODEC.register("Repeat", RepeatInteraction.class, RepeatInteraction.CODEC);
/* 208 */     Interaction.CODEC.register("Parallel", ParallelInteraction.class, ParallelInteraction.CODEC);
/* 209 */     Interaction.CODEC.register("Serial", SerialInteraction.class, SerialInteraction.CODEC);
/* 210 */     Interaction.CODEC.register("ChangeActiveSlot", ChangeActiveSlotInteraction.class, ChangeActiveSlotInteraction.CODEC);
/*     */     
/* 212 */     Interaction.CODEC.register("Selector", SelectInteraction.class, SelectInteraction.CODEC);
/* 213 */     Interaction.CODEC.register("DamageEntity", DamageEntityInteraction.class, DamageEntityInteraction.CODEC);
/* 214 */     Interaction.CODEC.register("LaunchProjectile", LaunchProjectileInteraction.class, LaunchProjectileInteraction.CODEC);
/* 215 */     Interaction.CODEC.register("Wielding", WieldingInteraction.class, WieldingInteraction.CODEC);
/* 216 */     Interaction.CODEC.register("Replace", ReplaceInteraction.class, ReplaceInteraction.CODEC);
/* 217 */     Interaction.CODEC.register("StatsCondition", StatsConditionInteraction.class, StatsConditionInteraction.CODEC);
/* 218 */     Interaction.CODEC.register("StatsConditionWithModifier", StatsConditionWithModifierInteraction.class, StatsConditionWithModifierInteraction.CODEC);
/* 219 */     Interaction.CODEC.register("SpawnPrefab", SpawnPrefabInteraction.class, SpawnPrefabInteraction.CODEC);
/*     */     
/* 221 */     Interaction.CODEC.register("SendMessage", SendMessageInteraction.class, SendMessageInteraction.CODEC);
/* 222 */     Interaction.CODEC.register("EquipItem", EquipItemInteraction.class, EquipItemInteraction.CODEC);
/* 223 */     Interaction.CODEC.register("RefillContainer", RefillContainerInteraction.class, RefillContainerInteraction.CODEC);
/* 224 */     Interaction.CODEC.register("Door", DoorInteraction.class, DoorInteraction.CODEC);
/* 225 */     Interaction.CODEC.register("IncreaseBackpackCapacity", IncreaseBackpackCapacityInteraction.class, IncreaseBackpackCapacityInteraction.CODEC);
/* 226 */     Interaction.CODEC.register("CheckUniqueItemUsage", CheckUniqueItemUsageInteraction.class, CheckUniqueItemUsageInteraction.CODEC);
/*     */     
/* 228 */     Interaction.CODEC.register("LaunchPad", LaunchPadInteraction.class, LaunchPadInteraction.CODEC);
/*     */     
/* 230 */     Interaction.CODEC.register("OpenContainer", OpenContainerInteraction.class, OpenContainerInteraction.CODEC);
/* 231 */     Interaction.CODEC.register("OpenItemStackContainer", OpenItemStackContainerInteraction.class, OpenItemStackContainerInteraction.CODEC);
/* 232 */     Interaction.CODEC.register("DestroyCondition", DestroyConditionInteraction.class, DestroyConditionInteraction.CODEC);
/* 233 */     Interaction.CODEC.register("OpenCustomUI", OpenCustomUIInteraction.class, OpenCustomUIInteraction.CODEC);
/* 234 */     Interaction.CODEC.register("OpenPage", OpenPageInteraction.class, OpenPageInteraction.CODEC);
/*     */     
/* 236 */     Interaction.CODEC.register("ApplyEffect", ApplyEffectInteraction.class, ApplyEffectInteraction.CODEC);
/* 237 */     Interaction.CODEC.register("ClearEntityEffect", ClearEntityEffectInteraction.class, ClearEntityEffectInteraction.CODEC);
/*     */     
/* 239 */     Interaction.CODEC.register("RemoveEntity", RemoveEntityInteraction.class, RemoveEntityInteraction.CODEC);
/* 240 */     Interaction.CODEC.register("EffectCondition", EffectConditionInteraction.class, EffectConditionInteraction.CODEC);
/* 241 */     Interaction.CODEC.register("ApplyForce", ApplyForceInteraction.class, ApplyForceInteraction.CODEC);
/* 242 */     Interaction.CODEC.register("ChangeStat", ChangeStatInteraction.class, ChangeStatInteraction.CODEC);
/* 243 */     Interaction.CODEC.register("ChangeStatWithModifier", ChangeStatWithModifierInteraction.class, ChangeStatWithModifierInteraction.CODEC);
/* 244 */     Interaction.CODEC.register("MovementCondition", MovementConditionInteraction.class, MovementConditionInteraction.CODEC);
/* 245 */     Interaction.CODEC.register("ResetCooldown", ResetCooldownInteraction.class, ResetCooldownInteraction.CODEC);
/* 246 */     Interaction.CODEC.register("TriggerCooldown", TriggerCooldownInteraction.class, TriggerCooldownInteraction.CODEC);
/* 247 */     Interaction.CODEC.register("CooldownCondition", CooldownConditionInteraction.class, CooldownConditionInteraction.CODEC);
/* 248 */     Interaction.CODEC.register("IncrementCooldown", IncrementCooldownInteraction.class, IncrementCooldownInteraction.CODEC);
/*     */     
/* 250 */     Interaction.CODEC.register("AddItem", AddItemInteraction.class, AddItemInteraction.CODEC);
/* 251 */     Interaction.CODEC.register("Interrupt", InterruptInteraction.class, InterruptInteraction.CODEC);
/*     */     
/* 253 */     Interaction.CODEC.register("RunRootInteraction", RunRootInteraction.class, RunRootInteraction.CODEC);
/*     */     
/* 255 */     Interaction.CODEC.register("Camera", CameraInteraction.class, CameraInteraction.CODEC);
/*     */     
/* 257 */     Interaction.CODEC.register("ToggleGlider", ToggleGliderInteraction.class, ToggleGliderInteraction.CODEC);
/*     */     
/* 259 */     OpenCustomUIInteraction.registerBlockEntityCustomPage((PluginBase)this, LaunchPad.LaunchPadSettingsPage.class, "LaunchPad", (playerRef, ref) -> ref.getStore().getArchetype(ref).contains(LaunchPad.getComponentType()) ? new LaunchPad.LaunchPadSettingsPage(playerRef, ref, CustomPageLifetime.CanDismissOrCloseThroughInteraction) : null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 267 */     OpenCustomUIInteraction.PAGE_CODEC.register("ItemRepair", ItemRepairPageSupplier.class, (Codec)ItemRepairPageSupplier.CODEC);
/*     */     
/* 269 */     SelectorType.CODEC.register("Horizontal", HorizontalSelector.class, (Codec)HorizontalSelector.CODEC);
/* 270 */     SelectorType.CODEC.register("Stab", StabSelector.class, (Codec)StabSelector.CODEC);
/* 271 */     SelectorType.CODEC.register("AOECircle", AOECircleSelector.class, (Codec)AOECircleSelector.CODEC);
/* 272 */     SelectorType.CODEC.register("AOECylinder", AOECylinderSelector.class, (Codec)AOECylinderSelector.CODEC);
/* 273 */     SelectorType.CODEC.register("Raycast", RaycastSelector.class, (Codec)RaycastSelector.CODEC);
/*     */     
/* 275 */     Knockback.CODEC.register("Directional", DirectionalKnockback.class, (Codec)DirectionalKnockback.CODEC);
/* 276 */     Knockback.CODEC.register("Point", PointKnockback.class, (Codec)PointKnockback.CODEC);
/* 277 */     Knockback.CODEC.register("Force", ForceKnockback.class, (Codec)ForceKnockback.CODEC);
/*     */     
/* 279 */     eventRegistry.register(LoadedAssetsEvent.class, RootInteraction.class, InteractionModule::handledLoadedRootInteractions);
/* 280 */     eventRegistry.register(LoadedAssetsEvent.class, Interaction.class, InteractionModule::handledLoadedInteractions);
/* 281 */     eventRegistry.register(RemovedAssetsEvent.class, Interaction.class, InteractionModule::handledRemovedInteractions);
/*     */     
/* 283 */     entityStoreRegistry.registerSystem((ISystem)new InteractionSystems.PlayerAddManagerSystem());
/* 284 */     entityStoreRegistry.registerSystem((ISystem)new InteractionSystems.CleanUpSystem());
/* 285 */     entityStoreRegistry.registerSystem((ISystem)new InteractionSystems.TickInteractionManagerSystem());
/* 286 */     entityStoreRegistry.registerSystem((ISystem)new InteractionSystems.TrackerTickSystem());
/* 287 */     entityStoreRegistry.registerSystem((ISystem)new InteractionSystems.EntityTrackerRemove(EntityTrackerSystems.Visible.getComponentType()));
/*     */     
/* 289 */     getCodecRegistry((StringCodecMapCodec)SelectInteraction.EntityMatcher.CODEC).register("Vulnerable", VulnerableMatcher.class, (Codec)VulnerableMatcher.CODEC);
/* 290 */     getCodecRegistry((StringCodecMapCodec)SelectInteraction.EntityMatcher.CODEC).register("Player", PlayerMatcher.class, (Codec)PlayerMatcher.CODEC);
/*     */     
/* 292 */     this.blockCounterResourceType = getChunkStoreRegistry().registerResource(BlockCounter.class, "BlockCounter", BlockCounter.CODEC);
/* 293 */     this.trackedPlacementComponentType = getChunkStoreRegistry().registerComponent(TrackedPlacement.class, "TrackedPlacement", TrackedPlacement.CODEC);
/* 294 */     getChunkStoreRegistry().registerSystem((ISystem)new TrackedPlacement.OnAddRemove());
/* 295 */     getCodecRegistry(Interaction.CODEC).register("PlacementCountCondition", PlacementCountConditionInteraction.class, PlacementCountConditionInteraction.CODEC);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void handledLoadedRootInteractions(@Nonnull LoadedAssetsEvent<String, RootInteraction, ?> event) {
/* 304 */     for (RootInteraction rootInteraction : event.getLoadedAssets().values()) {
/* 305 */       rootInteraction.build();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void handledLoadedInteractions(@Nonnull LoadedAssetsEvent<String, Interaction, ?> event) {
/* 315 */     for (Map.Entry<String, RootInteraction> entry : (Iterable<Map.Entry<String, RootInteraction>>)RootInteraction.getAssetMap().getAssetMap().entrySet()) {
/* 316 */       ((RootInteraction)entry.getValue()).build(event.getLoadedAssets().keySet());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void handledRemovedInteractions(@Nonnull RemovedAssetsEvent<String, Interaction, ?> event) {
/* 326 */     for (Map.Entry<String, RootInteraction> entry : (Iterable<Map.Entry<String, RootInteraction>>)RootInteraction.getAssetMap().getAssetMap().entrySet()) {
/* 327 */       ((RootInteraction)entry.getValue()).build(event.getRemovedAssets());
/*     */     }
/*     */   }
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
/*     */   
/*     */   public void doMouseInteraction(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull MouseInteraction packet, @Nonnull Player playerComponent, @Nonnull PlayerRef playerRefComponent) {
/*     */     Item item;
/*     */     Entity targetEntity;
/* 345 */     if (isDisabled()) {
/*     */       return;
/*     */     }
/*     */     
/* 349 */     byte activeHotbarSlot = playerComponent.getInventory().getActiveHotbarSlot();
/* 350 */     if (activeHotbarSlot != packet.activeSlot) {
/* 351 */       playerComponent.sendMessage(Message.translation("server.modules.interaction.failedGetActiveSlot").param("server", activeHotbarSlot).param("packet", packet.activeSlot));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 361 */     MouseButtonType mouseButtonType = (packet.mouseButton != null) ? packet.mouseButton.mouseButtonType : MouseButtonType.Left;
/*     */     
/* 363 */     Inventory inventory = playerComponent.getInventory();
/* 364 */     ItemStack itemInHand = inventory.getItemInHand();
/* 365 */     ItemStack itemInOffHand = inventory.getUtilityItem();
/* 366 */     Item primaryItem = (itemInHand == null || itemInHand.isEmpty()) ? null : itemInHand.getItem();
/* 367 */     Item secondaryItem = (itemInOffHand == null || itemInOffHand.isEmpty()) ? null : itemInOffHand.getItem();
/*     */ 
/*     */     
/* 370 */     if (mouseButtonType == MouseButtonType.Left) {
/* 371 */       item = primaryItem;
/* 372 */     } else if (mouseButtonType == MouseButtonType.Right && secondaryItem != null) {
/* 373 */       item = secondaryItem;
/*     */     } else {
/* 375 */       item = primaryItem;
/*     */     } 
/*     */     
/* 378 */     WorldInteraction worldInteraction_ = packet.worldInteraction;
/* 379 */     BlockPosition blockPositionPacket = worldInteraction_.blockPosition;
/*     */     
/* 381 */     if (!ref.isValid())
/*     */       return; 
/* 383 */     EntityStore entityComponentStore = (EntityStore)componentAccessor.getExternalData();
/*     */     
/* 385 */     Vector3i targetBlock = (blockPositionPacket == null) ? null : new Vector3i(blockPositionPacket.x, blockPositionPacket.y, blockPositionPacket.z);
/*     */     
/* 387 */     if (worldInteraction_.entityId < 0) {
/* 388 */       targetEntity = null;
/*     */     } else {
/* 390 */       Ref<EntityStore> entityReference = entityComponentStore.getRefFromNetworkId(worldInteraction_.entityId);
/* 391 */       targetEntity = EntityUtils.getEntity(entityReference, componentAccessor);
/*     */     } 
/*     */     
/* 394 */     CameraManager cameraManagerComponent = (CameraManager)componentAccessor.getComponent(ref, CameraManager.getComponentType());
/* 395 */     assert cameraManagerComponent != null;
/*     */     
/* 397 */     if (packet.mouseButton != null) {
/* 398 */       IEventDispatcher<PlayerMouseButtonEvent, PlayerMouseButtonEvent> dispatcher = HytaleServer.get().getEventBus().dispatchFor(PlayerMouseButtonEvent.class);
/* 399 */       if (dispatcher.hasListener()) {
/* 400 */         dispatcher.dispatch((IBaseEvent)new PlayerMouseButtonEvent(ref, playerComponent, playerRefComponent, packet.clientTimestamp, item, targetBlock, targetEntity, packet.screenPoint, packet.mouseButton));
/*     */       }
/* 402 */       cameraManagerComponent.handleMouseButtonState(packet.mouseButton.mouseButtonType, packet.mouseButton.state, targetBlock);
/*     */     } else {
/* 404 */       IEventDispatcher<PlayerMouseMotionEvent, PlayerMouseMotionEvent> dispatcher = HytaleServer.get().getEventBus().dispatchFor(PlayerMouseMotionEvent.class);
/* 405 */       if (dispatcher.hasListener()) {
/* 406 */         dispatcher.dispatch((IBaseEvent)new PlayerMouseMotionEvent(ref, playerComponent, packet.clientTimestamp, item, targetBlock, targetEntity, packet.screenPoint, packet.mouseMotion));
/*     */       }
/*     */     } 
/*     */     
/* 410 */     cameraManagerComponent.setLastScreenPoint(new Vector2d(packet.screenPoint.x, packet.screenPoint.y));
/* 411 */     cameraManagerComponent.setLastBlockPosition(targetBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ComponentType<EntityStore, ChainingInteraction.Data> getChainingDataComponent() {
/* 419 */     return this.chainingDataComponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ComponentType<EntityStore, Interactions> getInteractionsComponentType() {
/* 427 */     return this.interactionsComponentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ComponentType<EntityStore, InteractionManager> getInteractionManagerComponent() {
/* 435 */     return this.interactionManagerComponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ComponentType<ChunkStore, PlacedByInteractionComponent> getPlacedByComponentType() {
/* 443 */     return this.placedByComponentType;
/*     */   }
/*     */   
/*     */   public ResourceType<ChunkStore, BlockCounter> getBlockCounterResourceType() {
/* 447 */     return this.blockCounterResourceType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, TrackedPlacement> getTrackedPlacementComponentType() {
/* 451 */     return this.trackedPlacementComponentType;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\InteractionModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */