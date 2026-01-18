/*    */ package com.hypixel.hytale.server.core.universe.world.commands.worldconfig;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
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
/*    */ public class WorldConfigSeedCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   public WorldConfigSeedCommand() {
/* 21 */     super("seed", "server.commands.seed.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 26 */     context.sendMessage(Message.translation("server.universe.seed.info")
/* 27 */         .param("seed", world.getWorldConfig().getSeed()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\worldconfig\WorldConfigSeedCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */