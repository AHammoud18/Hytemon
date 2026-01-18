/*     */ package com.hypixel.hytale.builtin.buildertools.prefabeditor.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSession;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditSessionManager;
/*     */ import com.hypixel.hytale.builtin.buildertools.prefabeditor.PrefabEditingMetadata;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class PrefabEditSelectCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  29 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_SELECT_ERROR_NO_TARGET_FOUND = Message.translation("server.commands.editprefab.select.error.noTargetFound");
/*     */   @Nonnull
/*  31 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_SELECT_ERROR_NO_PREFAB_FOUND = Message.translation("server.commands.editprefab.select.error.noPrefabFound");
/*     */   @Nonnull
/*  33 */   private static final Message MESSAGE_COMMANDS_EDIT_PREFAB_NOT_IN_EDIT_SESSION = Message.translation("server.commands.editprefab.notInEditSession");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  39 */   private final FlagArg nearestArg = withFlagArg("nearest", "server.commands.editprefab.select.nearest.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrefabEditSelectCommand() {
/*  45 */     super("select", "server.commands.editprefab.select.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  50 */     PrefabEditSessionManager prefabEditSessionManager = BuilderToolsPlugin.get().getPrefabEditSessionManager();
/*  51 */     PrefabEditSession prefabEditSession = prefabEditSessionManager.getPrefabEditSession(playerRef.getUuid());
/*     */     
/*  53 */     if (prefabEditSession == null) {
/*  54 */       context.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_NOT_IN_EDIT_SESSION);
/*     */       
/*     */       return;
/*     */     } 
/*  58 */     PrefabEditingMetadata prefabEditingMetadata = null;
/*  59 */     if (((Boolean)this.nearestArg.get(context)).booleanValue()) {
/*  60 */       TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  61 */       assert transformComponent != null;
/*     */       
/*  63 */       Vector3d playerLocation = transformComponent.getPosition().clone();
/*  64 */       playerLocation.setY(0.0D);
/*     */       
/*  66 */       double distance = 2.147483647E9D;
/*     */       
/*  68 */       for (PrefabEditingMetadata value : prefabEditSession.getLoadedPrefabMetadata().values())
/*     */       {
/*     */ 
/*     */         
/*  72 */         Vector3d centerPoint = new Vector3d(((value.getMaxPoint()).x + (value.getMinPoint()).x) / 2.0D, 0.0D, ((value.getMaxPoint()).z + (value.getMinPoint()).z) / 2.0D);
/*     */ 
/*     */         
/*  75 */         double distanceTo = centerPoint.distanceTo(playerLocation);
/*  76 */         if (distance > distanceTo) {
/*  77 */           distance = distanceTo;
/*  78 */           prefabEditingMetadata = value;
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  86 */       Vector3i targetLocation = getTargetLocation(ref, (ComponentAccessor<EntityStore>)store);
/*     */       
/*  88 */       if (targetLocation == null) {
/*  89 */         context.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_SELECT_ERROR_NO_TARGET_FOUND);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  94 */       for (PrefabEditingMetadata value : prefabEditSession.getLoadedPrefabMetadata().values()) {
/*  95 */         boolean isWithinPrefab = value.isLocationWithinPrefabBoundingBox(targetLocation);
/*  96 */         if (isWithinPrefab) {
/*  97 */           prefabEditingMetadata = value;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     if (prefabEditingMetadata == null) {
/* 106 */       context.sendMessage(MESSAGE_COMMANDS_EDIT_PREFAB_SELECT_ERROR_NO_PREFAB_FOUND);
/*     */       
/*     */       return;
/*     */     } 
/* 110 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 111 */     assert playerComponent != null;
/*     */     
/* 113 */     prefabEditSession.setSelectedPrefab(ref, prefabEditingMetadata, (ComponentAccessor)store);
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
/* 128 */     Vector3i targetBlock = TargetUtil.getTargetBlock(ref, 200.0D, componentAccessor);
/* 129 */     if (targetBlock != null) {
/* 130 */       return targetBlock;
/*     */     }
/*     */ 
/*     */     
/* 134 */     Ref<EntityStore> targetEntityRef = TargetUtil.getTargetEntity(ref, componentAccessor);
/* 135 */     if (targetEntityRef == null || !targetEntityRef.isValid()) {
/* 136 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 140 */     TransformComponent entityTransformComponent = (TransformComponent)componentAccessor.getComponent(targetEntityRef, TransformComponent.getComponentType());
/* 141 */     if (entityTransformComponent == null) {
/* 142 */       return null;
/*     */     }
/*     */     
/* 145 */     return entityTransformComponent.getPosition().toVector3i();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\commands\PrefabEditSelectCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */