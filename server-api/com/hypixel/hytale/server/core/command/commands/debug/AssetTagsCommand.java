/*    */ package com.hypixel.hytale.server.core.command.commands.debug;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetMap;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.map.AssetMapWithIndexes;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class AssetTagsCommand
/*    */   extends CommandBase {
/*    */   @Nonnull
/* 23 */   private static final Message MESSAGE_COMMANDS_ASSETS_TAGS_TAG_NOT_FOUND = Message.translation("server.commands.assets.tags.tagNotFound");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 29 */   private final RequiredArg<String> classArg = withRequiredArg("class", "server.commands.assets.tags.class.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 35 */   private final RequiredArg<String> tagArg = withRequiredArg("tag", "server.commands.assets.tags.tag.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AssetTagsCommand() {
/* 41 */     super("tags", "server.commands.assets.tags.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 46 */     String assetClass = (String)this.classArg.get(context);
/* 47 */     String tag = (String)this.tagArg.get(context);
/* 48 */     int tagIndex = AssetRegistry.getTagIndex(tag);
/*    */     
/* 50 */     if (tagIndex == Integer.MIN_VALUE) {
/* 51 */       context.sendMessage(MESSAGE_COMMANDS_ASSETS_TAGS_TAG_NOT_FOUND
/* 52 */           .param("tag", tag));
/*    */       
/*    */       return;
/*    */     } 
/* 56 */     for (Map.Entry<Class<? extends JsonAssetWithMap>, AssetStore<?, ?, ?>> entry : (Iterable<Map.Entry<Class<? extends JsonAssetWithMap>, AssetStore<?, ?, ?>>>)AssetRegistry.getStoreMap().entrySet()) {
/* 57 */       String simpleName = ((Class)entry.getKey()).getSimpleName();
/* 58 */       if (!simpleName.equalsIgnoreCase(assetClass))
/*    */         continue; 
/* 60 */       context.sendMessage(Message.translation("server.commands.assets.tags.assetsOfTypeWithTag")
/* 61 */           .param("type", simpleName)
/* 62 */           .param("tag", tag));
/*    */       
/* 64 */       AssetMap<?, ?> assetMap = ((AssetStore)entry.getValue()).getAssetMap();
/*    */ 
/*    */ 
/*    */       
/* 68 */       Set<Message> keysForTag = (Set<Message>)assetMap.getKeysForTag(tagIndex).stream().map(Object::toString).map(Message::raw).collect(Collectors.toSet());
/* 69 */       context.sendMessage(MessageFormat.list(Message.translation("server.commands.assets.tags.assetKeys"), keysForTag));
/*    */       
/* 71 */       if (assetMap instanceof AssetMapWithIndexes) { AssetMapWithIndexes<?, ?> assetMapWithIndexes = (AssetMapWithIndexes<?, ?>)assetMap;
/*    */ 
/*    */ 
/*    */         
/* 75 */         Set<Message> indexesForTag = (Set<Message>)assetMapWithIndexes.getIndexesForTag(tagIndex).intStream().mapToObj(Integer::toString).map(Message::raw).collect(Collectors.toSet());
/* 76 */         context.sendMessage(MessageFormat.list(Message.translation("server.commands.assets.tags.assetIndexes"), indexesForTag)); }
/*    */     
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\AssetTagsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */