/*     */ package com.hypixel.hytale.server.core.modules.entity.player;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolsSetSoundSet;
/*     */ import com.hypixel.hytale.protocol.packets.inventory.SetActiveSlot;
/*     */ import com.hypixel.hytale.protocol.packets.player.SetBlockPlacementOverride;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.SpawnConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.particle.config.WorldParticle;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerWorldData;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.pages.RespawnPage;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
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
/*     */ public class PlayerAddedSystem
/*     */   extends RefSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*  97 */   private static final Message MESSAGE_SERVER_GENERAL_KILLED_BY_UNKNOWN = Message.translation("server.general.killedByUnknown");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 103 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.AFTER, PlayerSystems.PlayerSpawnedSystem.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerAddedSystem(@Nonnull ComponentType<EntityStore, MovementManager> movementManagerComponentType) {
/* 117 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)Player.getComponentType(), (Query)PlayerRef.getComponentType(), (Query)movementManagerComponentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 123 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 129 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 134 */     World world = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/*     */     
/* 136 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/* 137 */     assert playerComponent != null;
/*     */     
/* 139 */     PlayerRef playerRefComponent = (PlayerRef)commandBuffer.getComponent(ref, PlayerRef.getComponentType());
/* 140 */     assert playerRefComponent != null;
/*     */     
/* 142 */     MovementManager movementManagerComponent = (MovementManager)commandBuffer.getComponent(ref, MovementManager.getComponentType());
/* 143 */     assert movementManagerComponent != null;
/*     */     
/* 145 */     if (commandBuffer.getComponent(ref, DisplayNameComponent.getComponentType()) == null) {
/* 146 */       Message displayName = Message.raw(playerRefComponent.getUsername());
/* 147 */       commandBuffer.putComponent(ref, DisplayNameComponent.getComponentType(), (Component)new DisplayNameComponent(displayName));
/*     */     } 
/*     */     
/* 150 */     playerComponent.setLastSpawnTimeNanos(System.nanoTime());
/*     */     
/* 152 */     PacketHandler playerConnection = playerRefComponent.getPacketHandler();
/*     */     
/* 154 */     Objects.requireNonNull(world, "world");
/* 155 */     Objects.requireNonNull(playerComponent.getPlayerConfigData(), "data");
/*     */     
/* 157 */     PlayerWorldData perWorldData = playerComponent.getPlayerConfigData().getPerWorldData(world.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     Player.initGameMode(ref, (ComponentAccessor)commandBuffer);
/*     */     
/* 164 */     playerConnection.writeNoCache((Packet)new BuilderToolsSetSoundSet(world.getGameplayConfig().getCreativePlaySoundSetIndex()));
/*     */     
/* 166 */     playerComponent.sendInventory();
/* 167 */     Inventory playerInventory = playerComponent.getInventory();
/* 168 */     playerConnection.writeNoCache((Packet)new SetActiveSlot(-1, playerInventory.getActiveHotbarSlot()));
/* 169 */     playerConnection.writeNoCache((Packet)new SetActiveSlot(-5, playerInventory.getActiveUtilitySlot()));
/* 170 */     playerConnection.writeNoCache((Packet)new SetActiveSlot(-8, playerInventory.getActiveToolsSlot()));
/*     */     
/* 172 */     if (playerInventory.containsBrokenItem()) {
/* 173 */       playerComponent.sendMessage(Message.translation("server.general.repair.itemBrokenOnRespawn").color("#ff5555"));
/*     */     }
/*     */     
/* 176 */     playerConnection.writeNoCache((Packet)new SetBlockPlacementOverride(playerComponent.isOverrideBlockPlacementRestrictions()));
/*     */ 
/*     */     
/* 179 */     DeathComponent deathComponent = (DeathComponent)commandBuffer.getComponent(ref, DeathComponent.getComponentType());
/* 180 */     if (deathComponent != null) {
/* 181 */       Message pendingDeathMessage = deathComponent.getDeathMessage();
/* 182 */       if (pendingDeathMessage == null) {
/* 183 */         ((HytaleLogger.Api)Entity.LOGGER.at(Level.SEVERE).withCause(new Throwable()))
/* 184 */           .log("Player wasn't alive but didn't have a pending death message?");
/* 185 */         pendingDeathMessage = MESSAGE_SERVER_GENERAL_KILLED_BY_UNKNOWN;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 192 */       RespawnPage respawnPage = new RespawnPage(playerRefComponent, pendingDeathMessage, deathComponent.displayDataOnDeathScreen(), deathComponent.getDeathItemLoss());
/*     */       
/* 194 */       playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)respawnPage);
/*     */     } 
/*     */     
/* 197 */     TransformComponent transform = (TransformComponent)commandBuffer.getComponent(ref, TransformComponent.getComponentType());
/* 198 */     GameplayConfig gameplayConfig = world.getGameplayConfig();
/* 199 */     SpawnConfig spawnConfig = gameplayConfig.getSpawnConfig();
/*     */     
/* 201 */     if (transform != null) {
/* 202 */       Vector3d position = transform.getPosition();
/* 203 */       SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)commandBuffer.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 204 */       ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 205 */       playerSpatialResource.getSpatialStructure().collect(position, 75.0D, (List)results);
/*     */ 
/*     */       
/* 208 */       results.add(ref);
/*     */       
/* 210 */       if (playerComponent.isFirstSpawn()) {
/* 211 */         WorldParticle[] firstSpawnParticles = spawnConfig.getFirstSpawnParticles();
/* 212 */         if (firstSpawnParticles == null) firstSpawnParticles = spawnConfig.getSpawnParticles(); 
/* 213 */         if (firstSpawnParticles != null) {
/* 214 */           ParticleUtil.spawnParticleEffects(firstSpawnParticles, position, null, (List)results, (ComponentAccessor)commandBuffer);
/*     */         }
/*     */       } else {
/* 217 */         WorldParticle[] spawnParticles = spawnConfig.getSpawnParticles();
/* 218 */         if (spawnParticles != null) {
/* 219 */           ParticleUtil.spawnParticleEffects(spawnParticles, position, null, (List)results, (ComponentAccessor)commandBuffer);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 224 */     playerConnection.tryFlush();
/*     */     
/* 226 */     perWorldData.setFirstSpawn(false);
/*     */   }
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerSystems$PlayerAddedSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */