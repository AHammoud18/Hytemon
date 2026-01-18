/*    */ package com.hypixel.hytale.builtin.path.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldPathRemoveCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 24 */   private final RequiredArg<String> nameArg = withRequiredArg("name", "server.commands.worldpath.remove.name.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldPathRemoveCommand() {
/* 30 */     super("remove", "server.commands.worldpath.remove.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 35 */     String name = (String)this.nameArg.get(context);
/*    */     
/* 37 */     if (world.getWorldPathConfig().removePath(name) == null) {
/* 38 */       context.sendMessage(Message.translation("server.universe.worldpath.noPathFound")
/* 39 */           .param("path", name));
/*    */     } else {
/* 41 */       context.sendMessage(Message.translation("server.universe.worldpath.removed")
/* 42 */           .param("path", name));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\WorldPathRemoveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */