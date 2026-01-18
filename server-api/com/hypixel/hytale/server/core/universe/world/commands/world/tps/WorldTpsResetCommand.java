/*    */ package com.hypixel.hytale.server.core.universe.world.commands.world.tps;
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
/*    */ 
/*    */ public class WorldTpsResetCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   public WorldTpsResetCommand() {
/* 22 */     super("reset", "server.commands.world.tps.reset.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 27 */     int defaultTps = 30;
/*    */     
/* 29 */     world.setTps(30);
/* 30 */     double defaultMs = 33.333333333333336D;
/* 31 */     context.sendMessage(Message.translation("server.commands.world.tps.reset.success")
/* 32 */         .param("worldName", world.getName())
/* 33 */         .param("tps", 30)
/* 34 */         .param("ms", String.format("%.2f", new Object[] { Double.valueOf(33.333333333333336D) })));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\world\tps\WorldTpsResetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */