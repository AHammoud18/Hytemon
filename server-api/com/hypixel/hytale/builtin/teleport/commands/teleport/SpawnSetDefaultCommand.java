/*    */ package com.hypixel.hytale.builtin.teleport.commands.teleport;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpawnSetDefaultCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 18 */   private static final Message MESSAGE_UNIVERSE_SET_SPAWN_DEFAULT = Message.translation("server.universe.setspawn.default");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SpawnSetDefaultCommand() {
/* 24 */     super("default", "server.commands.spawn.set.default.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 31 */     world.getWorldConfig().setSpawnProvider(null);
/*    */ 
/*    */     
/* 34 */     world.getLogger().at(Level.INFO).log("Set spawn provider to: %s", world.getWorldConfig().getSpawnProvider());
/* 35 */     context.sendMessage(MESSAGE_UNIVERSE_SET_SPAWN_DEFAULT);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\teleport\SpawnSetDefaultCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */