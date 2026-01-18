/*    */ package com.hypixel.hytale.server.core.command.commands.debug;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.modules.debug.DebugUtils;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.teleport.PendingTeleport;
/*    */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*    */ 
/*    */ public class DebugPlayerPositionCommand extends AbstractPlayerCommand {
/*    */   public DebugPlayerPositionCommand() {
/* 22 */     super("debugplayerposition", "server.commands.debugplayerposition.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@NonNullDecl CommandContext context, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
/* 27 */     Transform transform = ((TransformComponent)store.getComponent(ref, TransformComponent.getComponentType())).getTransform();
/* 28 */     Vector3f headRotation = ((HeadRotation)store.getComponent(ref, HeadRotation.getComponentType())).getRotation();
/* 29 */     Teleport teleport = (Teleport)store.getComponent(ref, Teleport.getComponentType());
/* 30 */     PendingTeleport pendingTeleport = (PendingTeleport)store.getComponent(ref, PendingTeleport.getComponentType());
/*    */     
/* 32 */     String teleportFmt = (teleport == null) ? "none" : fmtPos(teleport.getPosition());
/* 33 */     String pendingTeleportFmt = (pendingTeleport == null) ? "none" : fmtPos(pendingTeleport.getPosition());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     Message message = Message.translation("server.commands.debugplayerposition.result").param("bodyPosition", fmtPos(transform.getPosition())).param("bodyRotation", fmtRot(transform.getRotation())).param("headRotation", fmtRot(headRotation)).param("teleport", teleportFmt).param("pendingTeleport", pendingTeleportFmt);
/*    */     
/* 42 */     playerRef.sendMessage(message);
/*    */     
/* 44 */     Vector3f blue = new Vector3f(0.137F, 0.867F, 0.882F);
/* 45 */     DebugUtils.addSphere(world, transform.getPosition(), blue, 0.5D, 30.0F);
/*    */     
/* 47 */     playerRef.sendMessage(Message.translation("server.commands.debugplayerposition.notify").color("#23DDE1"));
/*    */   }
/*    */   
/*    */   private static String fmtPos(Vector3d vector) {
/* 51 */     String fmt = "%.1f";
/* 52 */     return String.format("%.1f", new Object[] { Double.valueOf(vector.getX()) }) + ", " + String.format("%.1f", new Object[] { Double.valueOf(vector.getX()) }) + ", " + String.format("%.1f", new Object[] { Double.valueOf(vector.getY()) });
/*    */   }
/*    */   
/*    */   private static String fmtRot(Vector3f vector) {
/* 56 */     return "Pitch=" + fmtDegrees(vector.getPitch()) + ", Yaw=" + fmtDegrees(vector.getYaw()) + ", Roll=" + fmtDegrees(vector.getRoll());
/*    */   }
/*    */   
/*    */   private static String fmtDegrees(float radians) {
/* 60 */     return String.format("%.1f", new Object[] { Double.valueOf(Math.toDegrees(radians)) }) + "°";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\DebugPlayerPositionCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */