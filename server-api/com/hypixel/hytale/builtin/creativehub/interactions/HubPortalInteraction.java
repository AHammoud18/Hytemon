/*     */ package com.hypixel.hytale.builtin.creativehub.interactions;
/*     */ import com.hypixel.hytale.builtin.creativehub.CreativeHubPlugin;
/*     */ import com.hypixel.hytale.builtin.creativehub.config.CreativeHubEntityConfig;
/*     */ import com.hypixel.hytale.builtin.creativehub.config.CreativeHubWorldConfig;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerWorldData;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.teleport.PendingTeleport;
/*     */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*     */ import com.hypixel.hytale.server.core.universe.world.spawn.ISpawnProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*     */ 
/*     */ public class HubPortalInteraction extends SimpleInstantInteraction {
/*  46 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<HubPortalInteraction> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String worldName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String worldGenType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String instanceTemplate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  75 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(HubPortalInteraction.class, HubPortalInteraction::new, SimpleInstantInteraction.CODEC).documentation("Teleports the **Player** to a permanent world, creating it if required.")).appendInherited(new KeyedCodec("WorldName", (Codec)Codec.STRING), (o, i) -> o.worldName = i, o -> o.worldName, (o, p) -> o.worldName = p.worldName).documentation("The name of the permanent world to teleport to.").addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("WorldGenType", (Codec)Codec.STRING), (o, i) -> o.worldGenType = i, o -> o.worldGenType, (o, p) -> o.worldGenType = p.worldGenType).documentation("The world generator type to use when creating the world (e.g., 'Flat', 'Hytale'). Mutually exclusive with InstanceTemplate.").add()).appendInherited(new KeyedCodec("InstanceTemplate", (Codec)Codec.STRING), (o, i) -> o.instanceTemplate = i, o -> o.instanceTemplate, (o, p) -> o.instanceTemplate = p.instanceTemplate).documentation("Instance asset to use as template for creating the permanent world. Mutually exclusive with WorldGenType.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNullDecl
/*     */   public WaitForDataFrom getWaitForDataFrom() {
/*  85 */     return WaitForDataFrom.Server;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*  90 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*  91 */     Ref<EntityStore> ref = context.getEntity();
/*     */     
/*  93 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*  94 */     if (playerComponent == null || playerComponent.isWaitingForClientReady()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  99 */     Archetype<EntityStore> archetype = commandBuffer.getArchetype(ref);
/* 100 */     if (archetype.contains(Teleport.getComponentType()) || archetype.contains(PendingTeleport.getComponentType())) {
/*     */       return;
/*     */     }
/*     */     
/* 104 */     World currentWorld = ((EntityStore)commandBuffer.getExternalData()).getWorld();
/* 105 */     Universe universe = Universe.get();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     World targetWorld = universe.getWorld(this.worldName);
/*     */     
/* 113 */     if (targetWorld != null) {
/*     */       
/* 115 */       teleportToLoadedWorld(ref, (ComponentAccessor<EntityStore>)commandBuffer, targetWorld, playerComponent);
/*     */     } else {
/*     */       CompletableFuture<World> worldFuture;
/*     */       
/* 119 */       if (this.instanceTemplate != null) {
/* 120 */         worldFuture = CreativeHubPlugin.get().spawnPermanentWorldFromTemplate(this.instanceTemplate, this.worldName);
/* 121 */       } else if (universe.isWorldLoadable(this.worldName)) {
/* 122 */         worldFuture = universe.loadWorld(this.worldName);
/*     */       } else {
/* 124 */         worldFuture = universe.addWorld(this.worldName, this.worldGenType, null);
/* 125 */         worldFuture.thenAccept(world -> {
/*     */               if (world.getWorldConfig().getDisplayName() == null) {
/*     */                 world.getWorldConfig().setDisplayName(WorldConfig.formatDisplayName(this.worldName));
/*     */               }
/*     */             });
/*     */       } 
/* 131 */       teleportToLoadingWorld(ref, (ComponentAccessor<EntityStore>)commandBuffer, worldFuture, currentWorld, playerComponent);
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
/*     */   private void teleportToLoadedWorld(@Nonnull Ref<EntityStore> playerRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull World targetWorld, @Nonnull Player playerComponent) {
/*     */     Transform spawnPoint;
/* 144 */     Map<String, PlayerWorldData> perWorldData = playerComponent.getPlayerConfigData().getPerWorldData();
/* 145 */     PlayerWorldData worldData = perWorldData.get(targetWorld.getName());
/*     */ 
/*     */     
/* 148 */     if (worldData != null && worldData.getLastPosition() != null) {
/* 149 */       spawnPoint = worldData.getLastPosition();
/*     */     } else {
/* 151 */       UUIDComponent uuidComponent = (UUIDComponent)componentAccessor.getComponent(playerRef, UUIDComponent.getComponentType());
/* 152 */       assert uuidComponent != null;
/* 153 */       ISpawnProvider spawnProvider = targetWorld.getWorldConfig().getSpawnProvider();
/* 154 */       spawnPoint = (spawnProvider != null) ? spawnProvider.getSpawnPoint(targetWorld, uuidComponent.getUuid()) : new Transform();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 159 */     componentAccessor.addComponent(playerRef, Teleport.getComponentType(), (Component)new Teleport(targetWorld, spawnPoint));
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
/*     */   private void teleportToLoadingWorld(@Nonnull Ref<EntityStore> playerRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull CompletableFuture<World> worldFuture, @Nonnull World originalWorld, @Nonnull Player playerComponent) {
/* 172 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(playerRef, TransformComponent.getComponentType());
/* 173 */     assert transformComponent != null;
/* 174 */     Transform originalPosition = transformComponent.getTransform().clone();
/*     */     
/* 176 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(playerRef, PlayerRef.getComponentType());
/* 177 */     assert playerRefComponent != null;
/*     */     
/* 179 */     Map<String, PlayerWorldData> perWorldData = playerComponent.getPlayerConfigData().getPerWorldData();
/*     */     
/* 181 */     UUIDComponent uuidComponent = (UUIDComponent)componentAccessor.getComponent(playerRef, UUIDComponent.getComponentType());
/* 182 */     assert uuidComponent != null;
/* 183 */     UUID playerUUID = uuidComponent.getUuid();
/*     */     
/* 185 */     CreativeHubEntityConfig hubEntityConfig = (CreativeHubEntityConfig)componentAccessor.getComponent(playerRef, CreativeHubEntityConfig.getComponentType());
/*     */     
/* 187 */     Objects.requireNonNull(playerRefComponent); originalWorld.execute(playerRefComponent::removeFromStore);
/*     */     
/* 189 */     worldFuture
/* 190 */       .orTimeout(1L, TimeUnit.MINUTES)
/* 191 */       .thenCompose(world -> {
/*     */           PlayerWorldData worldData = (PlayerWorldData)perWorldData.get(world.getName());
/*     */           
/*     */           if (worldData != null && worldData.getLastPosition() != null) {
/*     */             return world.addPlayer(playerRefComponent, worldData.getLastPosition(), Boolean.TRUE, Boolean.FALSE);
/*     */           }
/*     */           ISpawnProvider spawnProvider = world.getWorldConfig().getSpawnProvider();
/*     */           Transform spawnPoint = (spawnProvider != null) ? spawnProvider.getSpawnPoint(world, playerUUID) : null;
/*     */           return world.addPlayer(playerRefComponent, spawnPoint, Boolean.TRUE, Boolean.FALSE);
/* 200 */         }).whenComplete((ret, ex) -> {
/*     */           if (ex != null)
/*     */             ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(ex)).log("Failed to teleport %s to permanent world", playerRefComponent.getUsername()); 
/*     */           if (ret != null)
/*     */             return; 
/*     */           if (originalWorld.isAlive()) {
/*     */             originalWorld.addPlayer(playerRefComponent, originalPosition, Boolean.TRUE, Boolean.FALSE);
/*     */             return;
/*     */           } 
/*     */           if (hubEntityConfig != null && hubEntityConfig.getParentHubWorldUuid() != null) {
/*     */             World parentWorld = Universe.get().getWorld(hubEntityConfig.getParentHubWorldUuid());
/*     */             if (parentWorld != null) {
/*     */               CreativeHubWorldConfig parentHubConfig = CreativeHubWorldConfig.get(parentWorld.getWorldConfig());
/*     */               if (parentHubConfig != null && parentHubConfig.getStartupInstance() != null) {
/*     */                 World hubInstance = CreativeHubPlugin.get().getOrSpawnHubInstance(parentWorld, parentHubConfig, new Transform());
/*     */                 hubInstance.addPlayer(playerRefComponent, null, Boolean.TRUE, Boolean.FALSE);
/*     */                 return;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           World defaultWorld = Universe.get().getDefaultWorld();
/*     */           if (defaultWorld != null) {
/*     */             defaultWorld.addPlayer(playerRefComponent, null, Boolean.TRUE, Boolean.FALSE);
/*     */             return;
/*     */           } 
/*     */           LOGGER.at(Level.SEVERE).log("No fallback world available for %s, disconnecting", playerRefComponent.getUsername());
/*     */           playerRefComponent.getPacketHandler().disconnect("Failed to teleport - no world available");
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\creativehub\interactions\HubPortalInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */