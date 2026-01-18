/*     */ package com.hypixel.hytale.builtin.teleport.commands.teleport.variant;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.teleport.components.TeleportHistory;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.Coord;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeFloat;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*     */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TeleportToCoordinatesCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  34 */   private static final Message MESSAGE_COMMANDS_TELEPORT_TELEPORTED_TO_COORDINATES_WITH_LOOK = Message.translation("server.commands.teleport.teleportedToCoordinatesWithLook");
/*     */   @Nonnull
/*  36 */   private static final Message MESSAGE_COMMANDS_TELEPORT_TELEPORTED_TO_COORDINATES = Message.translation("server.commands.teleport.teleportedToCoordinates");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  42 */   private final RequiredArg<Coord> xArg = withRequiredArg("x", "server.commands.teleport.x.desc", (ArgumentType)ArgTypes.RELATIVE_DOUBLE_COORD);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  48 */   private final RequiredArg<Coord> yArg = withRequiredArg("y", "server.commands.teleport.y.desc", (ArgumentType)ArgTypes.RELATIVE_DOUBLE_COORD);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  54 */   private final RequiredArg<Coord> zArg = withRequiredArg("z", "server.commands.teleport.z.desc", (ArgumentType)ArgTypes.RELATIVE_DOUBLE_COORD);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  60 */   private final OptionalArg<RelativeFloat> yawArg = withOptionalArg("yaw", "server.commands.teleport.yaw.desc", (ArgumentType)ArgTypes.RELATIVE_FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  66 */   private final OptionalArg<RelativeFloat> pitchArg = withOptionalArg("pitch", "server.commands.teleport.pitch.desc", (ArgumentType)ArgTypes.RELATIVE_FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  72 */   private final OptionalArg<RelativeFloat> rollArg = withOptionalArg("roll", "server.commands.teleport.roll.desc", (ArgumentType)ArgTypes.RELATIVE_FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TeleportToCoordinatesCommand() {
/*  78 */     super("server.commands.teleport.toCoordinates.desc");
/*  79 */     requirePermission(HytalePermissions.fromCommand("teleport.self"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  86 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*  87 */     assert transformComponent != null;
/*     */     
/*  89 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/*  90 */     assert headRotationComponent != null;
/*     */     
/*  92 */     Vector3d previousPos = transformComponent.getPosition().clone();
/*  93 */     Vector3f previousHeadRotation = headRotationComponent.getRotation().clone();
/*  94 */     Vector3f previousBodyRotation = transformComponent.getRotation().clone();
/*     */ 
/*     */     
/*  97 */     Coord relX = (Coord)this.xArg.get(context);
/*  98 */     Coord relY = (Coord)this.yArg.get(context);
/*  99 */     Coord relZ = (Coord)this.zArg.get(context);
/*     */     
/* 101 */     double x = relX.resolveXZ(previousPos.getX());
/* 102 */     double z = relZ.resolveXZ(previousPos.getZ());
/* 103 */     double y = relY.resolveYAtWorldCoords(previousPos.getY(), world, x, z);
/*     */ 
/*     */ 
/*     */     
/* 107 */     float yaw = this.yawArg.provided(context) ? (((RelativeFloat)this.yawArg.get(context)).resolve(previousHeadRotation.getYaw() * 57.295776F) * 0.017453292F) : Float.NaN;
/*     */ 
/*     */     
/* 110 */     float pitch = this.pitchArg.provided(context) ? (((RelativeFloat)this.pitchArg.get(context)).resolve(previousHeadRotation.getPitch() * 57.295776F) * 0.017453292F) : Float.NaN;
/*     */ 
/*     */     
/* 113 */     float roll = this.rollArg.provided(context) ? (((RelativeFloat)this.rollArg.get(context)).resolve(previousHeadRotation.getRoll() * 57.295776F) * 0.017453292F) : Float.NaN;
/*     */ 
/*     */     
/* 116 */     Teleport teleport = (new Teleport(new Vector3d(x, y, z), new Vector3f(previousBodyRotation.getPitch(), yaw, previousBodyRotation.getRoll()))).withHeadRotation(new Vector3f(pitch, yaw, roll));
/* 117 */     store.addComponent(ref, Teleport.getComponentType(), (Component)teleport);
/*     */     
/* 119 */     boolean hasRotation = (this.yawArg.provided(context) || this.pitchArg.provided(context) || this.rollArg.provided(context));
/* 120 */     if (hasRotation) {
/*     */       
/* 122 */       float displayYaw = Float.isNaN(yaw) ? (previousHeadRotation.getYaw() * 57.295776F) : (yaw * 57.295776F);
/* 123 */       float displayPitch = Float.isNaN(pitch) ? (previousHeadRotation.getPitch() * 57.295776F) : (pitch * 57.295776F);
/* 124 */       float displayRoll = Float.isNaN(roll) ? (previousHeadRotation.getRoll() * 57.295776F) : (roll * 57.295776F);
/* 125 */       context.sendMessage(MESSAGE_COMMANDS_TELEPORT_TELEPORTED_TO_COORDINATES_WITH_LOOK
/* 126 */           .param("x", x)
/* 127 */           .param("y", y)
/* 128 */           .param("z", z)
/* 129 */           .param("yaw", displayYaw)
/* 130 */           .param("pitch", displayPitch)
/* 131 */           .param("roll", displayRoll));
/*     */     } else {
/* 133 */       context.sendMessage(MESSAGE_COMMANDS_TELEPORT_TELEPORTED_TO_COORDINATES
/* 134 */           .param("x", x)
/* 135 */           .param("y", y)
/* 136 */           .param("z", z));
/*     */     } 
/* 138 */     ((TeleportHistory)store.ensureAndGetComponent(ref, TeleportHistory.getComponentType())).append(world, previousPos, previousHeadRotation, 
/* 139 */         String.format("Teleport to (%s, %s, %s)", new Object[] { Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) }));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\teleport\variant\TeleportToCoordinatesCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */