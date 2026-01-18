/*     */ package com.hypixel.hytale.builtin.teleport.commands.teleport;
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
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*     */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.NotificationUtil;
/*     */ import java.util.Collection;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class TeleportAllCommand extends CommandBase {
/*     */   @Nonnull
/*  32 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*     */   @Nonnull
/*  34 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_OR_ARG = Message.translation("server.commands.errors.playerOrArg");
/*     */   @Nonnull
/*  36 */   private static final Message MESSAGE_COMMANDS_TELEPORT_TELEPORTED_WITH_LOOK_NOTIFICATION = Message.translation("server.commands.teleport.teleportedWithLookNotification");
/*     */   @Nonnull
/*  38 */   private static final Message MESSAGE_COMMANDS_TELEPORT_TELEPORTED_TO_COORDINATES_NOTIFICATION = Message.translation("server.commands.teleport.teleportedToCoordinatesNotification");
/*     */   @Nonnull
/*  40 */   private static final Message MESSAGE_COMMANDS_TELEPORT_TELEPORT_EVERYONE_WITH_LOOK = Message.translation("server.commands.teleport.teleportEveryoneWithLook");
/*     */   @Nonnull
/*  42 */   private static final Message MESSAGE_COMMANDS_TELEPORT_TELEPORT_EVERYONE = Message.translation("server.commands.teleport.teleportEveryone");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  48 */   private final RequiredArg<Coord> xArg = withRequiredArg("x", "server.commands.teleport.x.desc", (ArgumentType)ArgTypes.RELATIVE_DOUBLE_COORD);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  54 */   private final RequiredArg<Coord> yArg = withRequiredArg("y", "server.commands.teleport.y.desc", (ArgumentType)ArgTypes.RELATIVE_DOUBLE_COORD);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  60 */   private final RequiredArg<Coord> zArg = withRequiredArg("z", "server.commands.teleport.z.desc", (ArgumentType)ArgTypes.RELATIVE_DOUBLE_COORD);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  66 */   private final OptionalArg<RelativeFloat> yawArg = withOptionalArg("yaw", "server.commands.teleport.yaw.desc", (ArgumentType)ArgTypes.RELATIVE_FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  72 */   private final OptionalArg<RelativeFloat> pitchArg = withOptionalArg("pitch", "server.commands.teleport.pitch.desc", (ArgumentType)ArgTypes.RELATIVE_FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  78 */   private final OptionalArg<RelativeFloat> rollArg = withOptionalArg("roll", "server.commands.teleport.roll.desc", (ArgumentType)ArgTypes.RELATIVE_FLOAT);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  84 */   private final OptionalArg<World> worldArg = withOptionalArg("world", "server.commands.worldthread.arg.desc", (ArgumentType)ArgTypes.WORLD);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TeleportAllCommand() {
/*  90 */     super("all", "server.commands.tpall.desc");
/*  91 */     setPermissionGroup(null);
/*  92 */     requirePermission(HytalePermissions.fromCommand("teleport.all"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void executeSync(@Nonnull CommandContext context) {
/*     */     World targetWorld;
/* 102 */     Coord relX = (Coord)this.xArg.get(context);
/* 103 */     Coord relY = (Coord)this.yArg.get(context);
/* 104 */     Coord relZ = (Coord)this.zArg.get(context);
/*     */ 
/*     */     
/* 107 */     if (this.worldArg.provided(context)) {
/* 108 */       targetWorld = (World)this.worldArg.get(context);
/* 109 */     } else if (context.isPlayer()) {
/* 110 */       Ref<EntityStore> senderRef = context.senderAsPlayerRef();
/* 111 */       if (senderRef == null || !senderRef.isValid()) {
/* 112 */         context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*     */         return;
/*     */       } 
/* 115 */       targetWorld = ((EntityStore)senderRef.getStore().getExternalData()).getWorld();
/*     */     } else {
/* 117 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_OR_ARG.param("option", "world"));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 122 */     targetWorld.execute(() -> {
/*     */           Store<EntityStore> store = targetWorld.getEntityStore().getStore();
/*     */           
/*     */           double baseX = 0.0D;
/*     */           
/*     */           double baseY = 0.0D;
/*     */           
/*     */           double baseZ = 0.0D;
/*     */           
/*     */           if (context.isPlayer()) {
/*     */             Ref<EntityStore> senderRef = context.senderAsPlayerRef();
/*     */             
/*     */             if (senderRef != null && senderRef.isValid()) {
/*     */               Store<EntityStore> senderStore = senderRef.getStore();
/*     */               
/*     */               World senderWorld = ((EntityStore)senderStore.getExternalData()).getWorld();
/*     */               if (senderWorld == targetWorld) {
/*     */                 TransformComponent transformComponent = (TransformComponent)senderStore.getComponent(senderRef, TransformComponent.getComponentType());
/*     */                 if (transformComponent != null) {
/*     */                   Vector3d pos = transformComponent.getPosition();
/*     */                   baseX = pos.getX();
/*     */                   baseY = pos.getY();
/*     */                   baseZ = pos.getZ();
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           double x = relX.resolveXZ(baseX);
/*     */           double z = relZ.resolveXZ(baseZ);
/*     */           double y = relY.resolveYAtWorldCoords(baseY, targetWorld, x, z);
/* 152 */           boolean hasRotation = (this.yawArg.provided(context) || this.pitchArg.provided(context) || this.rollArg.provided(context));
/*     */           Collection<PlayerRef> playersToTeleport = targetWorld.getPlayerRefs();
/*     */           for (PlayerRef playerRef : playersToTeleport) {
/*     */             Ref<EntityStore> ref = playerRef.getReference();
/*     */             if (ref == null || !ref.isValid())
/*     */               continue; 
/*     */             TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/*     */             HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/*     */             if (transformComponent == null || headRotationComponent == null)
/*     */               continue; 
/*     */             Vector3d previousPos = transformComponent.getPosition().clone();
/*     */             Vector3f previousHeadRotation = headRotationComponent.getRotation().clone();
/*     */             Vector3f previousBodyRotation = transformComponent.getRotation().clone();
/*     */             float yaw = this.yawArg.provided(context) ? (((RelativeFloat)this.yawArg.get(context)).resolve(previousHeadRotation.getYaw() * 57.295776F) * 0.017453292F) : Float.NaN;
/*     */             float pitch = this.pitchArg.provided(context) ? (((RelativeFloat)this.pitchArg.get(context)).resolve(previousHeadRotation.getPitch() * 57.295776F) * 0.017453292F) : Float.NaN;
/*     */             float roll = this.rollArg.provided(context) ? (((RelativeFloat)this.rollArg.get(context)).resolve(previousHeadRotation.getRoll() * 57.295776F) * 0.017453292F) : Float.NaN;
/*     */             Teleport teleport = (new Teleport(new Vector3d(x, y, z), new Vector3f(previousBodyRotation.getPitch(), yaw, previousBodyRotation.getRoll()))).withHeadRotation(new Vector3f(pitch, yaw, roll));
/*     */             store.addComponent(ref, Teleport.getComponentType(), (Component)teleport);
/*     */             Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */             if (playerComponent == null)
/*     */               continue; 
/*     */             PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/*     */             assert playerRefComponent != null;
/*     */             TeleportHistory teleportHistoryComponent = (TeleportHistory)store.ensureAndGetComponent(ref, TeleportHistory.getComponentType());
/*     */             teleportHistoryComponent.append(targetWorld, previousPos, previousHeadRotation, String.format("Teleport to (%s, %s, %s) by %s", new Object[] { Double.valueOf(x), Double.valueOf(y), Double.valueOf(z), context.sender().getDisplayName() }));
/*     */             if (hasRotation) {
/*     */               float displayYaw = Float.isNaN(yaw) ? (previousHeadRotation.getYaw() * 57.295776F) : (yaw * 57.295776F);
/*     */               float displayPitch = Float.isNaN(pitch) ? (previousHeadRotation.getPitch() * 57.295776F) : (pitch * 57.295776F);
/*     */               float displayRoll = Float.isNaN(roll) ? (previousHeadRotation.getRoll() * 57.295776F) : (roll * 57.295776F);
/*     */               NotificationUtil.sendNotification(playerRefComponent.getPacketHandler(), MESSAGE_COMMANDS_TELEPORT_TELEPORTED_WITH_LOOK_NOTIFICATION.param("x", x).param("y", y).param("z", z).param("yaw", displayYaw).param("pitch", displayPitch).param("roll", displayRoll).param("sender", context.sender().getDisplayName()), null, "teleportation");
/*     */               continue;
/*     */             } 
/*     */             NotificationUtil.sendNotification(playerRefComponent.getPacketHandler(), MESSAGE_COMMANDS_TELEPORT_TELEPORTED_TO_COORDINATES_NOTIFICATION.param("x", x).param("y", y).param("z", z).param("sender", context.sender().getDisplayName()), null, "teleportation");
/*     */           } 
/*     */           if (hasRotation) {
/*     */             float displayYaw = this.yawArg.provided(context) ? ((RelativeFloat)this.yawArg.get(context)).getRawValue() : 0.0F;
/*     */             float displayPitch = this.pitchArg.provided(context) ? ((RelativeFloat)this.pitchArg.get(context)).getRawValue() : 0.0F;
/*     */             float displayRoll = this.rollArg.provided(context) ? ((RelativeFloat)this.rollArg.get(context)).getRawValue() : 0.0F;
/*     */             context.sendMessage(MESSAGE_COMMANDS_TELEPORT_TELEPORT_EVERYONE_WITH_LOOK.param("world", targetWorld.getName()).param("x", x).param("y", y).param("z", z).param("yaw", displayYaw).param("pitch", displayPitch).param("roll", displayRoll));
/*     */           } else {
/*     */             context.sendMessage(MESSAGE_COMMANDS_TELEPORT_TELEPORT_EVERYONE.param("world", targetWorld.getName()).param("x", x).param("y", y).param("z", z));
/*     */           } 
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\teleport\TeleportAllCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */