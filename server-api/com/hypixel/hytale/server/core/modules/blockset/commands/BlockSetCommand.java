/*    */ package com.hypixel.hytale.server.core.modules.blockset.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.blockset.config.BlockSet;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.modules.blockset.BlockSetModule;
/*    */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockSetCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/*    */   private final BlockSetModule blockSetModule;
/*    */   @Nonnull
/* 37 */   private final OptionalArg<String> blockSetArg = withOptionalArg("blockset", "server.commands.blockset.blockset.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockSetCommand(@Nonnull BlockSetModule blockSetModule) {
/* 45 */     super("blockset", "server.commands.blockset.desc");
/* 46 */     this.blockSetModule = blockSetModule;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 51 */     if (!this.blockSetArg.provided(context)) {
/*    */ 
/*    */ 
/*    */       
/* 55 */       Set<Message> blockSetKeys = (Set<Message>)BlockSet.getAssetMap().getAssetMap().keySet().stream().map(Message::raw).collect(Collectors.toSet());
/* 56 */       context.sendMessage(MessageFormat.list(null, blockSetKeys));
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 61 */     String blockSetName = (String)this.blockSetArg.get(context);
/* 62 */     int index = BlockSet.getAssetMap().getIndex(blockSetName);
/* 63 */     if (index == Integer.MIN_VALUE) {
/* 64 */       context.sendMessage(Message.translation("server.modules.blockset.setNotFound")
/* 65 */           .param("name", blockSetName));
/*    */       
/*    */       return;
/*    */     } 
/* 69 */     IntSet set = (IntSet)this.blockSetModule.getBlockSets().get(index);
/* 70 */     if (set == null) {
/* 71 */       context.sendMessage(Message.translation("server.modules.blockset.setNotFound")
/* 72 */           .param("name", blockSetName));
/*    */       
/*    */       return;
/*    */     } 
/* 76 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/* 77 */     set.forEach(i -> names.add(Message.raw(((BlockType)BlockType.getAssetMap().getAsset(i)).getId().toString())));
/*    */     
/* 79 */     objectArrayList.sort(null);
/*    */     
/* 81 */     context.sendMessage(MessageFormat.list(null, (Collection)objectArrayList));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\blockset\commands\BlockSetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */