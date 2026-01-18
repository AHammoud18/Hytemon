/*    */ package com.hypixel.hytale.builtin.mounts.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.mounts.MountedComponent;
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
/*    */ public class MountCheckCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/* 20 */   private static final Message MESSAGE_COMMANDS_CHECK_NO_COMPONENT = Message.translation("server.commands.check.noComponent");
/* 21 */   private static final Message MESSAGE_COMMANDS_CHECK_MOUNTED_TO_ENTITY = Message.translation("server.commands.check.mountedToEntity");
/* 22 */   private static final Message MESSAGE_COMMANDS_CHECK_MOUNTED_TO_BLOCK = Message.translation("server.commands.check.mountedToBlock");
/* 23 */   private static final Message MESSAGE_COMMANDS_CHECK_UNKNOWN_STATUS = Message.translation("server.commands.check.unknownStatus");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MountCheckCommand() {
/* 29 */     super("check", "server.commands.check.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 34 */     MountedComponent mountedComponent = (MountedComponent)store.getComponent(ref, MountedComponent.getComponentType());
/* 35 */     if (mountedComponent == null) {
/* 36 */       playerRef.sendMessage(MESSAGE_COMMANDS_CHECK_NO_COMPONENT);
/*    */       
/*    */       return;
/*    */     } 
/* 40 */     if (mountedComponent.getMountedToEntity() != null) {
/* 41 */       playerRef.sendMessage(MESSAGE_COMMANDS_CHECK_MOUNTED_TO_ENTITY);
/* 42 */     } else if (mountedComponent.getMountedToBlock() != null) {
/* 43 */       playerRef.sendMessage(MESSAGE_COMMANDS_CHECK_MOUNTED_TO_BLOCK);
/*    */     } else {
/* 45 */       playerRef.sendMessage(MESSAGE_COMMANDS_CHECK_UNKNOWN_STATUS);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mounts\commands\MountCheckCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */