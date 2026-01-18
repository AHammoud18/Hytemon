/*    */ package com.hypixel.hytale.builtin.ambience.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.ambience.resources.AmbienceResource;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.ambiencefx.config.AmbienceFX;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class AmbienceSetMusicCommand extends AbstractWorldCommand {
/* 17 */   private final RequiredArg<AmbienceFX> ambienceFxIdArg = withRequiredArg("ambienceFxId", "server.commands.ambience.setmusic.arg.ambiencefxid.desc", (ArgumentType)ArgTypes.AMBIENCE_FX_ASSET);
/*    */   
/*    */   public AmbienceSetMusicCommand() {
/* 20 */     super("setmusic", "server.commands.ambience.setmusic.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 25 */     AmbienceFX ambienceFX = (AmbienceFX)this.ambienceFxIdArg.get(context);
/*    */     
/* 27 */     AmbienceResource resource = (AmbienceResource)store.getResource(AmbienceResource.getResourceType());
/* 28 */     resource.setForcedMusicAmbience(ambienceFX.getId());
/*    */     
/* 30 */     context.sendMessage(Message.translation("server.commands.ambience.setmusic.success")
/* 31 */         .param("ambience", ambienceFX.getId()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\ambience\commands\AmbienceSetMusicCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */