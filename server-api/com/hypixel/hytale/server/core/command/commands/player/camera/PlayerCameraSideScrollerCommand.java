/*    */ package com.hypixel.hytale.server.core.command.commands.player.camera;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.ClientCameraView;
/*    */ import com.hypixel.hytale.protocol.MouseInputType;
/*    */ import com.hypixel.hytale.protocol.MovementForceRotationType;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.PositionDistanceOffsetType;
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
/*    */ public class PlayerCameraSideScrollerCommand extends AbstractTargetPlayerCommand {
/*    */   @Nonnull
/* 22 */   private static final Message MESSAGE_COMMANDS_CAMERA_SIDESCROLLER_SUCCESS = Message.translation("server.commands.camera.sidescroller.success");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerCameraSideScrollerCommand() {
/* 28 */     super("sidescroller", "server.commands.camera.sidescroller.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 33 */     ServerCameraSettings cameraSettings = new ServerCameraSettings();
/* 34 */     cameraSettings.positionLerpSpeed = 0.2F;
/* 35 */     cameraSettings.rotationLerpSpeed = 0.2F;
/* 36 */     cameraSettings.distance = 15.0F;
/* 37 */     cameraSettings.displayCursor = true;
/* 38 */     cameraSettings.isFirstPerson = false;
/* 39 */     cameraSettings.movementForceRotationType = MovementForceRotationType.Custom;
/* 40 */     cameraSettings.movementMultiplier = new Vector3f(1.0F, 1.0F, 0.0F);
/* 41 */     cameraSettings.eyeOffset = true;
/* 42 */     cameraSettings.positionDistanceOffsetType = PositionDistanceOffsetType.DistanceOffset;
/* 43 */     cameraSettings.rotationType = RotationType.Custom;
/* 44 */     cameraSettings.mouseInputType = MouseInputType.LookAtPlane;
/* 45 */     cameraSettings.planeNormal = new Vector3f(0.0F, 0.0F, 1.0F);
/* 46 */     playerRef.getPacketHandler().writeNoCache((Packet)new SetServerCamera(ClientCameraView.Custom, true, cameraSettings));
/* 47 */     context.sendMessage(MESSAGE_COMMANDS_CAMERA_SIDESCROLLER_SUCCESS);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\camera\PlayerCameraSideScrollerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */