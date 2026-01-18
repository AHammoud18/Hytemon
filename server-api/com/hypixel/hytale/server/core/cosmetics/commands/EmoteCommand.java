/*    */ package com.hypixel.hytale.server.core.cosmetics.commands;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.StringUtil;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.AnimationSlot;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandUtil;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.cosmetics.CosmeticRegistry;
/*    */ import com.hypixel.hytale.server.core.cosmetics.CosmeticsModule;
/*    */ import com.hypixel.hytale.server.core.cosmetics.Emote;
/*    */ import com.hypixel.hytale.server.core.entity.AnimationUtils;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EmoteCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 32 */   private final RequiredArg<String> emoteArg = withRequiredArg("emote", "server.commands.emote.emote.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EmoteCommand() {
/* 38 */     super("emote", "server.commands.emote.desc");
/* 39 */     setPermissionGroup(GameMode.Adventure);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 50 */     CosmeticRegistry cosmeticsRegistry = CosmeticsModule.get().getRegistry();
/* 51 */     Map<String, Emote> emotes = cosmeticsRegistry.getEmotes();
/*    */     
/* 53 */     String emoteId = (String)this.emoteArg.get(context);
/* 54 */     Emote emote = emotes.get(emoteId);
/* 55 */     if (emote == null) {
/* 56 */       context.sendMessage(Message.translation("server.commands.emote.emoteNotFound")
/* 57 */           .param("id", emoteId));
/* 58 */       context.sendMessage(Message.translation("server.general.failed.didYouMean")
/* 59 */           .param("choices", StringUtil.sortByFuzzyDistance(emoteId, emotes.keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 63 */     AnimationUtils.playAnimation(ref, AnimationSlot.Emote, null, emote.getId(), true, (ComponentAccessor)store);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\cosmetics\commands\EmoteCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */