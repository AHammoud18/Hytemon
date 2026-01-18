/*    */ package com.hypixel.hytale.server.core.command.commands.debug.packs;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetPack;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.AssetModule;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PacksListCommand
/*    */   extends CommandBase {
/*    */   @Nonnull
/* 19 */   private static final Message MESSAGE_PACKS_NOT_INITIALIZED = Message.translation("server.commands.packs.notInitialized");
/*    */   @Nonnull
/* 21 */   private static final Message MESSAGE_PACKS_NONE_LOADED = Message.translation("server.commands.packs.noneLoaded");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PacksListCommand() {
/* 27 */     super("list", "server.commands.packs.list.desc");
/* 28 */     addAliases(new String[] { "ls" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 33 */     AssetModule assetModule = AssetModule.get();
/* 34 */     if (assetModule == null) {
/* 35 */       context.sendMessage(MESSAGE_PACKS_NOT_INITIALIZED);
/*    */       
/*    */       return;
/*    */     } 
/* 39 */     List<AssetPack> assetPacks = assetModule.getAssetPacks();
/* 40 */     if (assetPacks.isEmpty()) {
/* 41 */       context.sendMessage(MESSAGE_PACKS_NONE_LOADED);
/*    */       
/*    */       return;
/*    */     } 
/* 45 */     ObjectArrayList<Message> packs = new ObjectArrayList();
/*    */ 
/*    */ 
/*    */     
/* 49 */     Objects.requireNonNull(packs); assetPacks.stream().sorted(Comparator.comparing(AssetPack::getName, String.CASE_INSENSITIVE_ORDER)).map(PacksListCommand::formatPack).forEach(packs::add);
/*    */     
/* 51 */     context.sendMessage(MessageFormat.list(
/* 52 */           Message.translation("server.commands.packs.listHeader"), (Collection)packs));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   private static Message formatPack(@Nonnull AssetPack pack) {
/* 58 */     String name = pack.getName();
/* 59 */     String root = (pack.getRoot() != null) ? pack.getRoot().toString() : "<unknown>";
/* 60 */     return Message.translation("server.commands.packs.listEntry")
/* 61 */       .param("name", name)
/* 62 */       .param("root", root);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\packs\PacksListCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */