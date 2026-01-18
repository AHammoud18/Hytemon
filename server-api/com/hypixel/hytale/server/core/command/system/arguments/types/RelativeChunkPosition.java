/*    */ package com.hypixel.hytale.server.core.command.system.arguments.types;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector2i;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RelativeChunkPosition
/*    */ {
/*    */   @Nonnull
/* 21 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*    */   @Nonnull
/* 23 */   private static final Message MESSAGE_COMMANDS_ERRORS_WORLD_UNPSPECIFIED = Message.translation("server.commands.errors.worldUnspecified");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final IntCoord x;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final IntCoord z;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RelativeChunkPosition(@Nonnull IntCoord x, @Nonnull IntCoord z) {
/* 42 */     this.x = x;
/* 43 */     this.z = z;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector2i getChunkPosition(@Nonnull CommandContext context, ComponentAccessor<EntityStore> componentAccessor) {
/* 55 */     Ref<EntityStore> playerRef = context.senderAsPlayerRef();
/*    */ 
/*    */     
/* 58 */     if (playerRef == null) {
/* 59 */       throw new GeneralCommandException(MESSAGE_COMMANDS_ERRORS_WORLD_UNPSPECIFIED);
/*    */     }
/*    */ 
/*    */     
/* 63 */     if (!playerRef.isValid()) {
/* 64 */       throw new GeneralCommandException(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */     }
/*    */ 
/*    */     
/* 68 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(playerRef, TransformComponent.getComponentType());
/* 69 */     assert transformComponent != null;
/* 70 */     return getChunkPosition(transformComponent.getPosition());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector2i getChunkPosition(@Nonnull Vector3d base) {
/* 81 */     int relX = this.x.isNotBase() ? this.x.getValue() : (this.x.resolveXZ(MathUtil.floor(base.x)) >> 5);
/* 82 */     int relZ = this.z.isNotBase() ? this.z.getValue() : (this.z.resolveXZ(MathUtil.floor(base.z)) >> 5);
/* 83 */     return new Vector2i(relX, relZ);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isRelative() {
/* 90 */     return (this.x.isRelative() || this.z.isRelative());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\types\RelativeChunkPosition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */