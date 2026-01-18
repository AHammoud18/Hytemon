/*    */ package com.hypixel.hytale.server.core.command.commands.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandUtil;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*    */ import com.hypixel.hytale.server.core.permissions.PermissionHolder;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReferCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 30 */   private final RequiredArg<String> hostArg = withRequiredArg("host", "Target server hostname or IP", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 36 */   private final RequiredArg<Integer> portArg = withRequiredArg("port", "Target server port", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReferCommand() {
/* 42 */     super("refer", "Refer a player to another server for testing");
/* 43 */     addAliases(new String[] { "transfer" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 48 */     String host = (String)this.hostArg.get(context);
/* 49 */     int port = ((Integer)this.portArg.get(context)).intValue();
/* 50 */     boolean isTargetingOther = !ref.equals(sourceRef);
/*    */ 
/*    */     
/* 53 */     if (isTargetingOther) {
/* 54 */       CommandUtil.requirePermission((PermissionHolder)context.sender(), HytalePermissions.fromCommand("refer.other"));
/*    */     } else {
/* 56 */       CommandUtil.requirePermission((PermissionHolder)context.sender(), HytalePermissions.fromCommand("refer.self"));
/*    */     } 
/*    */ 
/*    */     
/* 60 */     if (port <= 0 || port > 65535) {
/* 61 */       context.sendMessage(Message.translation("server.commands.refer.invalidPort"));
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 66 */     byte[] testData = "Test referral".getBytes();
/*    */ 
/*    */     
/*    */     try {
/* 70 */       playerRef.referToServer(host, port, testData);
/*    */       
/* 72 */       if (isTargetingOther) {
/* 73 */         context.sendMessage(Message.translation("server.commands.refer.success.other")
/* 74 */             .param("username", playerRef.getUsername())
/* 75 */             .param("host", host)
/* 76 */             .param("port", port));
/*    */       } else {
/* 78 */         context.sendMessage(Message.translation("server.commands.refer.success.self")
/* 79 */             .param("host", host)
/* 80 */             .param("port", port));
/*    */       } 
/* 82 */     } catch (Exception e) {
/* 83 */       context.sendMessage(Message.translation("server.commands.refer.failed").param("error", e.getMessage()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\ReferCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */