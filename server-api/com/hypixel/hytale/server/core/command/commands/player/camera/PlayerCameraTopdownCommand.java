/*    */ package com.hypixel.hytale.server.core.command.commands.player.camera;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.ClientCameraView;
/*    */ import com.hypixel.hytale.protocol.MouseInputType;
/*    */ import com.hypixel.hytale.protocol.MovementForceRotationType;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.PositionDistanceOffsetType;
/*    */ import com.hypixel.hytale.protocol.RotationType;
/*    */ import com.hypixel.hytale.protocol.ServerCameraSettings;
/*    */ import com.hypixel.hytale.protocol.Vector3f;
/*    */ import com.hypixel.hytale.protocol.packets.camera.SetServerCamera;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class PlayerCameraTopdownCommand extends AbstractTargetPlayerCommand {
/*    */   @Nonnull
/* 23 */   private static final Message MESSAGE_COMMANDS_CAMERA_TOPDOWN_SUCCESS = Message.translation("server.commands.camera.topdown.success");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerCameraTopdownCommand() {
/* 29 */     super("topdown", "server.commands.camera.topdown.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 34 */     ServerCameraSettings cameraSettings = new ServerCameraSettings();
/* 35 */     cameraSettings.positionLerpSpeed = 0.2F;
/* 36 */     cameraSettings.rotationLerpSpeed = 0.2F;
/* 37 */     cameraSettings.distance = 20.0F;
/* 38 */     cameraSettings.displayCursor = true;
/* 39 */     cameraSettings.isFirstPerson = false;
/* 40 */     cameraSettings.movementForceRotationType = MovementForceRotationType.Custom;
/* 41 */     cameraSettings.eyeOffset = true;
/* 42 */     cameraSettings.positionDistanceOffsetType = PositionDistanceOffsetType.DistanceOffset;
/* 43 */     cameraSettings.rotationType = RotationType.Custom;
/* 44 */     cameraSettings.rotation = new Direction(0.0F, -1.5707964F, 0.0F);
/* 45 */     cameraSettings.mouseInputType = MouseInputType.LookAtPlane;
/* 46 */     cameraSettings.planeNormal = new Vector3f(0.0F, 1.0F, 0.0F);
/* 47 */     playerRef.getPacketHandler().writeNoCache((Packet)new SetServerCamera(ClientCameraView.Custom, true, cameraSettings));
/* 48 */     context.sendMessage(MESSAGE_COMMANDS_CAMERA_TOPDOWN_SUCCESS);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\camera\PlayerCameraTopdownCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */