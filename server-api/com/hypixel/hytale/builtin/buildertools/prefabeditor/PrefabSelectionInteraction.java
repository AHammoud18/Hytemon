/*     */ package com.hypixel.hytale.builtin.buildertools.prefabeditor;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class PrefabSelectionInteraction
/*     */   extends SimpleInstantInteraction
/*     */ {
/*     */   @Nonnull
/*  30 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_SELECT_ERROR_NO_TARGET_FOUND = Message.translation("server.commands.editprefab.select.error.noTargetFound");
/*     */   @Nonnull
/*  32 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_SELECT_ERROR_NO_PREFAB_FOUND = Message.translation("server.commands.editprefab.select.error.noPrefabFound");
/*     */   @Nonnull
/*  34 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_NOT_IN_EDIT_SESSION = Message.translation("server.commands.editprefab.notInEditSession");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float ENTITY_TARGET_RADIUS = 50.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  46 */   public static final BuilderCodec<PrefabSelectionInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PrefabSelectionInteraction.class, PrefabSelectionInteraction::new, SimpleInstantInteraction.CODEC)
/*  47 */     .documentation("Interaction that handles the selection functionally for the prefab selection tool."))
/*  48 */     .build();
/*     */ 
/*     */   
/*     */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*  52 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*  53 */     assert commandBuffer != null;
/*     */     
/*  55 */     Ref<EntityStore> ref = context.getEntity();
/*     */ 
/*     */     
/*  58 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*  59 */     if (playerComponent == null) {
/*     */       return;
/*     */     }
/*  62 */     if (type != InteractionType.Primary && type != InteractionType.Secondary) {
/*     */       return;
/*     */     }
/*     */     
/*  66 */     UUIDComponent uuidComponent = (UUIDComponent)commandBuffer.getComponent(ref, UUIDComponent.getComponentType());
/*  67 */     assert uuidComponent != null;
/*  68 */     UUID uuid = uuidComponent.getUuid();
/*     */     
/*  70 */     PrefabEditSessionManager prefabEditSessionManager = BuilderToolsPlugin.get().getPrefabEditSessionManager();
/*  71 */     PrefabEditSession prefabEditSession = prefabEditSessionManager.getPrefabEditSession(uuid);
/*     */     
/*  73 */     if (prefabEditSession == null) {
/*  74 */       playerComponent.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_NOT_IN_EDIT_SESSION);
/*     */       
/*     */       return;
/*     */     } 
/*  78 */     TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(ref, TransformComponent.getComponentType());
/*  79 */     assert transformComponent != null;
/*  80 */     Vector3d playerPosition = transformComponent.getPosition();
/*     */     
/*  82 */     PrefabEditingMetadata prefabEditingMetadata = null;
/*  83 */     if (type == InteractionType.Secondary) {
/*  84 */       Vector3d playerLocation = playerPosition.clone();
/*  85 */       playerLocation.setY(0.0D);
/*     */       
/*  87 */       double distance = 2.147483647E9D;
/*     */       
/*  89 */       for (PrefabEditingMetadata value : prefabEditSession.getLoadedPrefabMetadata().values())
/*     */       {
/*     */ 
/*     */         
/*  93 */         Vector3d centerPoint = new Vector3d(((value.getMaxPoint()).x + (value.getMinPoint()).x) / 2.0D, 0.0D, ((value.getMaxPoint()).z + (value.getMinPoint()).z) / 2.0D);
/*     */ 
/*     */         
/*  96 */         double distanceTo = centerPoint.distanceTo(playerLocation);
/*  97 */         if (distance > distanceTo) {
/*  98 */           distance = distanceTo;
/*  99 */           prefabEditingMetadata = value;
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 107 */       Vector3i targetLocation = getTargetLocation(ref, (ComponentAccessor<EntityStore>)commandBuffer);
/*     */       
/* 109 */       if (targetLocation == null) {
/* 110 */         playerComponent.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_SELECT_ERROR_NO_TARGET_FOUND);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 115 */       for (PrefabEditingMetadata value : prefabEditSession.getLoadedPrefabMetadata().values()) {
/* 116 */         boolean isWithinPrefab = value.isLocationWithinPrefabBoundingBox(targetLocation);
/* 117 */         if (isWithinPrefab) {
/* 118 */           prefabEditingMetadata = value;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     if (prefabEditingMetadata == null) {
/* 127 */       playerComponent.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_SELECT_ERROR_NO_PREFAB_FOUND);
/*     */       
/*     */       return;
/*     */     } 
/* 131 */     prefabEditSession.setSelectedPrefab(ref, prefabEditingMetadata, (ComponentAccessor<EntityStore>)commandBuffer);
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
/*     */   @Nullable
/*     */   private static Vector3i getTargetLocation(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 146 */     Vector3i targetBlock = TargetUtil.getTargetBlock(ref, 200.0D, componentAccessor);
/* 147 */     if (targetBlock != null) {
/* 148 */       return targetBlock;
/*     */     }
/*     */ 
/*     */     
/* 152 */     Ref<EntityStore> targetEntityRef = TargetUtil.getTargetEntity(ref, 50.0F, componentAccessor);
/* 153 */     if (targetEntityRef == null || !targetEntityRef.isValid()) {
/* 154 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 158 */     TransformComponent entityTransformComponent = (TransformComponent)componentAccessor.getComponent(targetEntityRef, TransformComponent.getComponentType());
/* 159 */     if (entityTransformComponent == null) {
/* 160 */       return null;
/*     */     }
/*     */     
/* 163 */     return entityTransformComponent.getPosition().toVector3i();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\PrefabSelectionInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */