/*    */ package com.hypixel.hytale.server.core.universe.world.commands.world;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.HytaleServer;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldSetDefaultCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 22 */   private final RequiredArg<String> nameArg = withRequiredArg("name", "server.commands.world.setdefault.arg.name.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldSetDefaultCommand() {
/* 28 */     super("setdefault", "server.commands.world.setdefault.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 33 */     CommandSender sender = context.sender();
/*    */     
/* 35 */     String worldName = (String)context.get((Argument)this.nameArg);
/* 36 */     if (Universe.get().getWorld(worldName) == null) {
/* 37 */       sender.sendMessage(Message.translation("server.world.notFound")
/* 38 */           .param("worldName", worldName));
/*    */       
/*    */       return;
/*    */     } 
/* 42 */     HytaleServer.get().getConfig().getDefaults().setWorld(worldName);
/* 43 */     sender.sendMessage(Message.translation("server.universe.defaultWorldSet")
/* 44 */         .param("worldName", worldName));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\world\WorldSetDefaultCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */