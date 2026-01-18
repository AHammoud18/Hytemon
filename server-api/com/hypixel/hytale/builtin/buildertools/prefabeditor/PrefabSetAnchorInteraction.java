/*     */ package com.hypixel.hytale.builtin.buildertools.prefabeditor;
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class PrefabSetAnchorInteraction extends SimpleInstantInteraction {
/*     */   @Nonnull
/*  22 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_NOT_IN_EDIT_SESSION = Message.translation("server.commands.editprefab.notInEditSession");
/*     */   @Nonnull
/*  24 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_ANCHOR_ERROR_NO_ANCHOR_FOUND = Message.translation("server.commands.editprefab.anchor.error.noAnchorFound");
/*     */   @Nonnull
/*  26 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_SELECT_ERROR_NO_PREFAB_FOUND = Message.translation("server.commands.editprefab.select.error.noPrefabFound");
/*     */   @Nonnull
/*  28 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_ANCHOR_SUCCESS = Message.translation("server.commands.editprefab.anchor.success");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  34 */   public static final BuilderCodec<PrefabSetAnchorInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PrefabSetAnchorInteraction.class, PrefabSetAnchorInteraction::new, SimpleInstantInteraction.CODEC)
/*  35 */     .documentation("Sets the prefab anchor."))
/*  36 */     .build();
/*     */ 
/*     */   
/*     */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*  40 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*  41 */     assert commandBuffer != null;
/*     */     
/*  43 */     Ref<EntityStore> ref = context.getEntity();
/*     */ 
/*     */     
/*  46 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*  47 */     if (playerComponent == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  52 */     if (type != InteractionType.Primary && type != InteractionType.Secondary) {
/*     */       return;
/*     */     }
/*     */     
/*  56 */     UUIDComponent uuidComponent = (UUIDComponent)commandBuffer.getComponent(ref, UUIDComponent.getComponentType());
/*  57 */     assert uuidComponent != null;
/*     */     
/*  59 */     PlayerRef playerRefComponent = (PlayerRef)commandBuffer.getComponent(ref, PlayerRef.getComponentType());
/*  60 */     assert playerRefComponent != null;
/*     */     
/*  62 */     UUID playerUuid = uuidComponent.getUuid();
/*     */     
/*  64 */     PrefabEditSessionManager prefabEditSessionManager = BuilderToolsPlugin.get().getPrefabEditSessionManager();
/*  65 */     PrefabEditSession prefabEditSession = prefabEditSessionManager.getPrefabEditSession(playerUuid);
/*     */     
/*  67 */     if (prefabEditSession == null) {
/*  68 */       playerRefComponent.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_NOT_IN_EDIT_SESSION);
/*     */       
/*     */       return;
/*     */     } 
/*  72 */     PrefabEditingMetadata prefabEditingMetadata = null;
/*     */ 
/*     */     
/*  75 */     BlockPosition targetBlock = context.getTargetBlock();
/*     */     
/*  77 */     if (targetBlock == null) {
/*  78 */       playerRefComponent.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_ANCHOR_ERROR_NO_ANCHOR_FOUND);
/*     */       
/*     */       return;
/*     */     } 
/*  82 */     Vector3i targetBlockPos = new Vector3i(targetBlock.x, targetBlock.y, targetBlock.z);
/*     */ 
/*     */     
/*  85 */     for (PrefabEditingMetadata value : prefabEditSession.getLoadedPrefabMetadata().values()) {
/*  86 */       boolean isWithinPrefab = value.isLocationWithinPrefabBoundingBox(targetBlockPos);
/*  87 */       if (isWithinPrefab) {
/*  88 */         prefabEditingMetadata = value;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  93 */     if (prefabEditingMetadata == null) {
/*  94 */       playerRefComponent.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_SELECT_ERROR_NO_PREFAB_FOUND);
/*     */       
/*     */       return;
/*     */     } 
/*  98 */     prefabEditSession.setSelectedPrefab(ref, prefabEditingMetadata, (ComponentAccessor<EntityStore>)commandBuffer);
/*  99 */     prefabEditingMetadata.setAnchorPoint(targetBlockPos, ((EntityStore)commandBuffer.getExternalData()).getWorld());
/* 100 */     prefabEditingMetadata.sendAnchorHighlightingPacket(playerRefComponent.getPacketHandler());
/*     */     
/* 102 */     playerRefComponent.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_ANCHOR_SUCCESS);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\PrefabSetAnchorInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */