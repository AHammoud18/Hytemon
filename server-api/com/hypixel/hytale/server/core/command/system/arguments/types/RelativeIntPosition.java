/*     */ package com.hypixel.hytale.server.core.command.system.arguments.types;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RelativeIntPosition
/*     */ {
/*     */   @Nonnull
/*  22 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*     */   @Nonnull
/*  24 */   private static final Message MESSAGE_COMMANDS_ERRORS_RELATIVE_POSITION_ARG = Message.translation("server.commands.errors.relativePositionArg");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final IntCoord x;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final IntCoord y;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final IntCoord z;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RelativeIntPosition(@Nonnull IntCoord x, @Nonnull IntCoord y, @Nonnull IntCoord z) {
/*  52 */     this.x = x;
/*  53 */     this.y = y;
/*  54 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getBlockPosition(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  66 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  67 */     assert transformComponent != null;
/*     */     
/*  69 */     Vector3d base = transformComponent.getPosition();
/*     */     
/*  71 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*  72 */     return getBlockPosition(base, world.getChunkStore());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getBlockPosition(@Nonnull CommandContext context, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  84 */     boolean relative = isRelative();
/*     */ 
/*     */     
/*  87 */     Ref<EntityStore> playerRef = context.isPlayer() ? context.senderAsPlayerRef() : null;
/*  88 */     if (playerRef != null) {
/*  89 */       if (!playerRef.isValid()) {
/*  90 */         throw new GeneralCommandException(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*     */       }
/*     */       
/*  93 */       return getBlockPosition(playerRef, componentAccessor);
/*     */     } 
/*  95 */     if (relative) {
/*  96 */       throw new GeneralCommandException(MESSAGE_COMMANDS_ERRORS_RELATIVE_POSITION_ARG);
/*     */     }
/*  98 */     Vector3d base = Vector3d.ZERO;
/*     */ 
/*     */ 
/*     */     
/* 102 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/* 103 */     return getBlockPosition(base, world.getChunkStore());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getBlockPosition(@Nonnull Vector3d base, @Nonnull ChunkStore chunkStore) {
/* 115 */     int relX = this.x.resolveXZ(MathUtil.floor(base.x));
/* 116 */     int relZ = this.z.resolveXZ(MathUtil.floor(base.z));
/* 117 */     int relY = this.y.resolveYAtWorldCoords(MathUtil.floor(base.y), chunkStore, relX, relZ);
/* 118 */     return new Vector3i(relX, relY, relZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRelative() {
/* 127 */     return (this.x.isRelative() || this.y.isRelative() || this.z.isRelative());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\types\RelativeIntPosition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */