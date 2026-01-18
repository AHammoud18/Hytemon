/*    */ package com.hypixel.hytale.builtin.path.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.path.WorldPath;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Comparator;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldPathListCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   public WorldPathListCommand() {
/* 23 */     super("list", "server.commands.worldpath.list.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 32 */     Objects.requireNonNull(context); world.getWorldPathConfig().getPaths().values().stream().sorted(Comparator.comparing(WorldPath::getName)).map(WorldPath::getName).map(Message::raw).forEach(context::sendMessage);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\WorldPathListCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */