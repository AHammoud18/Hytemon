/*    */ package com.hypixel.hytale.builtin.ambience.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.ambience.resources.AmbienceResource;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class AmbienceClearCommand
/*    */   extends AbstractWorldCommand {
/*    */   public AmbienceClearCommand() {
/* 15 */     super("clear", "server.commands.ambience.clear.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 20 */     AmbienceResource resource = (AmbienceResource)store.getResource(AmbienceResource.getResourceType());
/* 21 */     resource.setForcedMusicAmbience(null);
/*    */     
/* 23 */     context.sendMessage(Message.translation("server.commands.ambience.clear.success"));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\ambience\commands\AmbienceClearCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */