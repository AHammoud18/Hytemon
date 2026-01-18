/*    */ package com.hypixel.hytale.server.core.command.commands.player.camera;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerCameraDemoActivateCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 20 */   private static final Message MESSAGE_COMMANDS_CAMERA_DEMO_ENABLED = Message.translation("server.commands.camera.demo.enabled");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerCameraDemoActivateCommand() {
/* 26 */     super("activate", "server.commands.camera.demo.activate.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 31 */     CameraDemo.INSTANCE.activate();
/* 32 */     context.sendMessage(MESSAGE_COMMANDS_CAMERA_DEMO_ENABLED);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\camera\PlayerCameraDemoActivateCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */