/*    */ package com.hypixel.hytale.server.core.universe.world.commands.worldconfig;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldConfigSetPvpCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 24 */   private final RequiredArg<Boolean> stateArg = withRequiredArg("enabled", "server.commands.world.config.setpvp.stateArg.desc", (ArgumentType)ArgTypes.BOOLEAN);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldConfigSetPvpCommand() {
/* 30 */     super("pvp", "server.commands.setpvp.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 35 */     boolean isPvpEnabled = this.stateArg.provided(context) ? ((Boolean)this.stateArg.get(context)).booleanValue() : (!world.getWorldConfig().isPvpEnabled());
/* 36 */     WorldConfig worldConfig = world.getWorldConfig();
/* 37 */     worldConfig.setPvpEnabled(isPvpEnabled);
/* 38 */     worldConfig.markChanged();
/*    */     
/* 40 */     context.sendMessage(
/* 41 */         Message.translation("server.universe.setpvp.info")
/* 42 */         .param("status", MessageFormat.enabled(isPvpEnabled))
/* 43 */         .param("worldName", world.getName()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\worldconfig\WorldConfigSetPvpCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */