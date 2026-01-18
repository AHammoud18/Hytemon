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
/*    */ public class WorldRemoveCommand
/*    */   extends CommandBase
/*    */ {
/* 18 */   public static final Message MESSAGE_UNIVERSE_REMOVE_WORLD_NOT_FOUND = Message.translation("server.universe.removeworld.notFound");
/* 19 */   public static final Message MESSAGE_UNIVERSE_REMOVE_WORLD_ONLY_ONE_WORLD_LOADED = Message.translation("server.universe.removeworld.onlyOneWorldLoaded");
/* 20 */   public static final Message MESSAGE_UNIVERSE_REMOVE_WORLD_CHANGE_DEFAULT_WORLD = Message.translation("server.universe.removeworld.changeDefaultWorld");
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 25 */   private final RequiredArg<String> nameArg = withRequiredArg("name", "server.commands.removeworld.arg.name.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldRemoveCommand() {
/* 31 */     super("remove", "server.commands.removeworld.desc");
/* 32 */     addAliases(new String[] { "rm" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 37 */     CommandSender sender = context.sender();
/*    */     
/* 39 */     String name = (String)context.get((Argument)this.nameArg);
/* 40 */     if (Universe.get().getWorld(name) == null) {
/* 41 */       sender.sendMessage(MESSAGE_UNIVERSE_REMOVE_WORLD_NOT_FOUND); return;
/*    */     } 
/* 43 */     if (Universe.get().getWorlds().size() == 1) {
/* 44 */       sender.sendMessage(MESSAGE_UNIVERSE_REMOVE_WORLD_ONLY_ONE_WORLD_LOADED); return;
/*    */     } 
/* 46 */     if (name.equalsIgnoreCase(HytaleServer.get().getConfig().getDefaults().getWorld())) {
/* 47 */       sender.sendMessage(MESSAGE_UNIVERSE_REMOVE_WORLD_CHANGE_DEFAULT_WORLD);
/*    */       
/*    */       return;
/*    */     } 
/* 51 */     Universe.get().removeWorld(name);
/* 52 */     sender.sendMessage(Message.translation("server.universe.removeworld.success")
/* 53 */         .param("worldName", name));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\world\WorldRemoveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */