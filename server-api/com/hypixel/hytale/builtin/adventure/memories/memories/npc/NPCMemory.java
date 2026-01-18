/*     */ package com.hypixel.hytale.builtin.adventure.memories.memories.npc;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.memories.MemoriesGameplayConfig;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.MemoriesPlugin;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.component.PlayerMemories;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.memories.Memory;
/*     */ import com.hypixel.hytale.builtin.instances.config.InstanceDiscoveryConfig;
/*     */ import com.hypixel.hytale.builtin.instances.config.InstanceWorldConfig;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.item.PickupItemComponent;
/*     */ import com.hypixel.hytale.server.core.modules.i18n.I18nModule;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*     */ import com.hypixel.hytale.server.core.util.NotificationUtil;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ChunkGenerator;
/*     */ import com.hypixel.hytale.server.worldgen.chunk.ZoneBiomeResult;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NPCMemory
/*     */   extends Memory
/*     */ {
/*     */   @Nonnull
/*     */   public static final String ID = "NPC";
/*     */   @Nonnull
/*     */   public static final BuilderCodec<NPCMemory> CODEC;
/*     */   private String npcRole;
/*     */   private boolean isMemoriesNameOverridden;
/*     */   private long capturedTimestamp;
/*     */   private String foundLocationZoneNameKey;
/*     */   private String foundLocationGeneralNameKey;
/*     */   private String memoryTitleKey;
/*     */   
/*     */   static {
/*  87 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(NPCMemory.class, NPCMemory::new).append(new KeyedCodec("NPCRole", (Codec)Codec.STRING), (npcMemory, s) -> npcMemory.npcRole = s, npcMemory -> npcMemory.npcRole).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("TranslationKey", (Codec)Codec.STRING), (npcMemory, s) -> npcMemory.memoryTitleKey = s, npcMemory -> npcMemory.memoryTitleKey).add()).append(new KeyedCodec("IsMemoriesNameOverridden", (Codec)Codec.BOOLEAN), (npcMemory, aBoolean) -> npcMemory.isMemoriesNameOverridden = aBoolean.booleanValue(), npcMemory -> Boolean.valueOf(npcMemory.isMemoriesNameOverridden)).add()).append(new KeyedCodec("CapturedTimestamp", (Codec)Codec.LONG), (npcMemory, aDouble) -> npcMemory.capturedTimestamp = aDouble.longValue(), npcMemory -> Long.valueOf(npcMemory.capturedTimestamp)).add()).append(new KeyedCodec("FoundLocationZoneNameKey", (Codec)Codec.STRING), (npcMemory, s) -> npcMemory.foundLocationZoneNameKey = s, npcMemory -> npcMemory.foundLocationZoneNameKey).add()).append(new KeyedCodec("FoundLocationNameKey", (Codec)Codec.STRING), (npcMemory, s) -> npcMemory.foundLocationGeneralNameKey = s, npcMemory -> npcMemory.foundLocationGeneralNameKey).add()).afterDecode(NPCMemory::processConfig)).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private NPCMemory() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NPCMemory(@Nonnull String npcRole, @Nonnull String nameTranslationKey, boolean isMemoriesNameOverridden) {
/* 100 */     this.npcRole = npcRole;
/* 101 */     this.memoryTitleKey = nameTranslationKey;
/* 102 */     this.isMemoriesNameOverridden = isMemoriesNameOverridden;
/* 103 */     processConfig();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 108 */     return this.npcRole;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getTitle() {
/* 114 */     return this.memoryTitleKey;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Message getTooltipText() {
/* 120 */     return Message.translation("server.memories.general.discovered.tooltipText");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getIconPath() {
/* 126 */     return "UI/Custom/Pages/Memories/npcs/" + this.npcRole + ".png";
/*     */   }
/*     */   
/*     */   public void processConfig() {
/* 130 */     if (this.isMemoriesNameOverridden) {
/*     */ 
/*     */       
/* 133 */       this.memoryTitleKey = "server.npcRoles." + this.npcRole + ".name";
/*     */ 
/*     */       
/* 136 */       if (I18nModule.get().getMessage("en-US", this.memoryTitleKey) == null) {
/* 137 */         this.memoryTitleKey = "server.memories.names." + this.npcRole;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 142 */     if (this.memoryTitleKey == null || this.memoryTitleKey.isEmpty()) {
/* 143 */       this.memoryTitleKey = "server.npcRoles." + this.npcRole + ".name";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Message getUndiscoveredTooltipText() {
/* 150 */     return Message.translation("server.memories.general.undiscovered.tooltipText");
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getNpcRole() {
/* 155 */     return this.npcRole;
/*     */   }
/*     */   
/*     */   public long getCapturedTimestamp() {
/* 159 */     return this.capturedTimestamp;
/*     */   }
/*     */   
/*     */   public String getFoundLocationZoneNameKey() {
/* 163 */     return this.foundLocationZoneNameKey;
/*     */   }
/*     */   
/*     */   public Message getLocationMessage() {
/* 167 */     if (this.foundLocationGeneralNameKey != null) {
/* 168 */       return Message.translation(this.foundLocationGeneralNameKey);
/*     */     }
/* 170 */     if (this.foundLocationZoneNameKey != null) {
/* 171 */       return Message.translation("server.map.region." + this.foundLocationZoneNameKey);
/*     */     }
/* 173 */     return Message.raw("???");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 178 */     if (o == null || getClass() != o.getClass()) return false; 
/* 179 */     if (!super.equals(o)) return false;
/*     */     
/* 181 */     NPCMemory npcMemory = (NPCMemory)o;
/* 182 */     return (this.isMemoriesNameOverridden == npcMemory.isMemoriesNameOverridden && Objects.equals(this.npcRole, npcMemory.npcRole));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 187 */     int result = super.hashCode();
/* 188 */     result = 31 * result + Objects.hashCode(this.npcRole);
/* 189 */     result = 31 * result + Boolean.hashCode(this.isMemoriesNameOverridden);
/* 190 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 195 */     return "NPCMemory{npcRole='" + this.npcRole + "', isMemoriesNameOverride=" + this.isMemoriesNameOverridden + "', capturedTimestamp=" + this.capturedTimestamp + "', foundLocationZoneNameKey='" + this.foundLocationZoneNameKey + "}";
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
/*     */   public static class GatherMemoriesSystem
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/* 212 */     public static final Query<EntityStore> QUERY = (Query<EntityStore>)Query.and(new Query[] {
/* 213 */           (Query)TransformComponent.getComponentType(), 
/* 214 */           (Query)Player.getComponentType(), 
/* 215 */           (Query)PlayerMemories.getComponentType()
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double radius;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public GatherMemoriesSystem(double radius) {
/* 229 */       this.radius = radius;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 234 */       Player playerComponent = (Player)archetypeChunk.getComponent(index, Player.getComponentType());
/* 235 */       assert playerComponent != null;
/* 236 */       if (playerComponent.getGameMode() != GameMode.Adventure)
/*     */         return; 
/* 238 */       TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/* 239 */       assert transformComponent != null;
/* 240 */       Vector3d position = transformComponent.getPosition();
/*     */       
/* 242 */       SpatialResource<Ref<EntityStore>, EntityStore> npcSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(NPCPlugin.get().getNpcSpatialResource());
/* 243 */       ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 244 */       npcSpatialResource.getSpatialStructure().collect(position, this.radius, (List)results);
/* 245 */       if (results.isEmpty())
/*     */         return; 
/* 247 */       PlayerRef playerRefComponent = (PlayerRef)archetypeChunk.getComponent(index, PlayerRef.getComponentType());
/* 248 */       assert playerRefComponent != null;
/*     */       
/* 250 */       MemoriesPlugin memoriesPlugin = MemoriesPlugin.get();
/* 251 */       PlayerMemories playerMemoriesComponent = (PlayerMemories)archetypeChunk.getComponent(index, PlayerMemories.getComponentType());
/* 252 */       assert playerMemoriesComponent != null;
/*     */       
/* 254 */       NPCMemory temp = new NPCMemory();
/*     */       
/* 256 */       World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/* 257 */       String foundLocationZoneNameKey = findLocationZoneName(world, position);
/*     */       
/* 259 */       for (ObjectListIterator<Ref<EntityStore>> objectListIterator = results.iterator(); objectListIterator.hasNext(); ) { Ref<EntityStore> npcRef = objectListIterator.next();
/* 260 */         NPCEntity npcComponent = (NPCEntity)commandBuffer.getComponent(npcRef, NPCEntity.getComponentType());
/* 261 */         if (npcComponent == null)
/*     */           continue; 
/* 263 */         Role role = npcComponent.getRole();
/* 264 */         assert role != null;
/* 265 */         if (!role.isMemory()) {
/*     */           continue;
/*     */         }
/*     */         
/* 269 */         temp.isMemoriesNameOverridden = role.isMemoriesNameOverriden();
/* 270 */         temp.npcRole = temp.isMemoriesNameOverridden ? role.getMemoriesNameOverride() : npcComponent.getRoleName();
/* 271 */         temp.memoryTitleKey = role.getNameTranslationKey();
/* 272 */         temp.capturedTimestamp = System.currentTimeMillis();
/* 273 */         temp.foundLocationGeneralNameKey = foundLocationZoneNameKey;
/*     */         
/* 275 */         if (memoriesPlugin.hasRecordedMemory(temp)) {
/*     */           continue;
/*     */         }
/* 278 */         temp.processConfig();
/*     */ 
/*     */ 
/*     */         
/* 282 */         if (playerMemoriesComponent.recordMemory(temp)) {
/* 283 */           NotificationUtil.sendNotification(playerRefComponent.getPacketHandler(), Message.translation("server.memories.general.collected").param("memoryTitle", Message.translation(temp.getTitle())), null, "NotificationIcons/MemoriesIcon.png");
/*     */           
/* 285 */           temp = new NPCMemory();
/*     */           
/* 287 */           TransformComponent npcTransformComponent = (TransformComponent)commandBuffer.getComponent(npcRef, TransformComponent.getComponentType());
/* 288 */           assert npcTransformComponent != null;
/*     */           
/* 290 */           MemoriesGameplayConfig memoriesGameplayConfig = MemoriesGameplayConfig.get(((EntityStore)store.getExternalData()).getWorld().getGameplayConfig());
/* 291 */           if (memoriesGameplayConfig != null) {
/* 292 */             ItemStack memoryItemStack = new ItemStack(memoriesGameplayConfig.getMemoriesCatchItemId());
/* 293 */             Vector3d memoryItemHolderPosition = npcTransformComponent.getPosition().clone();
/*     */             
/* 295 */             BoundingBox boundingBox = (BoundingBox)commandBuffer.getComponent(npcRef, BoundingBox.getComponentType());
/* 296 */             if (boundingBox != null) {
/* 297 */               memoryItemHolderPosition.y += boundingBox.getBoundingBox().middleY();
/*     */             }
/*     */             
/* 300 */             Holder<EntityStore> memoryItemHolder = ItemComponent.generatePickedUpItem(memoryItemStack, memoryItemHolderPosition, (ComponentAccessor)commandBuffer, playerRefComponent.getReference());
/* 301 */             float memoryCatchItemLifetimeS = 0.62F;
/* 302 */             ((PickupItemComponent)memoryItemHolder.getComponent(PickupItemComponent.getComponentType())).setInitialLifeTime(memoryCatchItemLifetimeS);
/* 303 */             commandBuffer.addEntity(memoryItemHolder, AddReason.SPAWN);
/*     */           } 
/*     */         }  }
/*     */     
/*     */     }
/*     */     
/*     */     private static String findLocationZoneName(World world, Vector3d position) {
/* 310 */       IWorldGen worldGen = world.getChunkStore().getGenerator();
/* 311 */       if (worldGen instanceof ChunkGenerator) { ChunkGenerator generator = (ChunkGenerator)worldGen;
/* 312 */         int seed = (int)world.getWorldConfig().getSeed();
/* 313 */         ZoneBiomeResult result = generator.getZoneBiomeResultAt(seed, MathUtil.floor(position.x), MathUtil.floor(position.z));
/* 314 */         return "server.map.region." + result.getZoneResult().getZone().name(); }
/*     */ 
/*     */       
/* 317 */       InstanceWorldConfig instanceConfig = (InstanceWorldConfig)world.getWorldConfig().getPluginConfig().get(InstanceWorldConfig.class);
/* 318 */       if (instanceConfig != null) {
/* 319 */         InstanceDiscoveryConfig discovery = instanceConfig.getDiscovery();
/* 320 */         if (discovery != null && discovery.getTitleKey() != null) {
/* 321 */           return discovery.getTitleKey();
/*     */         }
/*     */       } 
/*     */       
/* 325 */       return "???";
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 331 */       return QUERY;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\memories\memories\npc\NPCMemory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */