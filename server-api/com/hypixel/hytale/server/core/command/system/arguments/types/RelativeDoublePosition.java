/*     */ package com.hypixel.hytale.server.core.command.system.arguments.types;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RelativeDoublePosition
/*     */ {
/*     */   @Nonnull
/*  19 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*     */   @Nonnull
/*  21 */   private static final Message MESSAGE_COMMANDS_ERRORS_RELATIVE_POSITION_ARG = Message.translation("server.commands.errors.relativePositionArg");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Coord x;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Coord y;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Coord z;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RelativeDoublePosition(@Nonnull Coord x, @Nonnull Coord y, @Nonnull Coord z) {
/*  46 */     this.x = x;
/*  47 */     this.y = y;
/*  48 */     this.z = z;
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
/*     */   public Vector3d getRelativePosition(@Nonnull Vector3d base, @Nonnull World world) {
/*  60 */     double relX = this.x.resolveXZ(base.x);
/*  61 */     double relZ = this.z.resolveXZ(base.z);
/*  62 */     double relY = this.y.resolveYAtWorldCoords(base.y, world, relX, relZ);
/*  63 */     return new Vector3d(relX, relY, relZ);
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
/*     */   @Nonnull
/*     */   public Vector3d getRelativePosition(@Nonnull CommandContext context, @Nonnull World world, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*     */     Vector3d basePosition;
/*  78 */     boolean relative = isRelative();
/*  79 */     if (relative && !context.isPlayer()) {
/*  80 */       throw new GeneralCommandException(MESSAGE_COMMANDS_ERRORS_RELATIVE_POSITION_ARG);
/*     */     }
/*     */ 
/*     */     
/*  84 */     if (relative) {
/*  85 */       Ref<EntityStore> senderPlayerRef = context.senderAsPlayerRef();
/*  86 */       if (senderPlayerRef == null || !senderPlayerRef.isValid()) {
/*  87 */         throw new GeneralCommandException(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*     */       }
/*     */       
/*  90 */       TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(senderPlayerRef, TransformComponent.getComponentType());
/*  91 */       assert transformComponent != null;
/*     */       
/*  93 */       basePosition = transformComponent.getPosition();
/*     */     } else {
/*  95 */       basePosition = Vector3d.ZERO;
/*     */     } 
/*  97 */     return getRelativePosition(basePosition, world);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRelative() {
/* 104 */     return (this.x.isRelative() || this.y.isRelative() || this.z.isRelative());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\arguments\types\RelativeDoublePosition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */