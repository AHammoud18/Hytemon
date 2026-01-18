/*    */ package com.hypixel.hytale.server.core.command.commands.debug;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.CompletableFuture;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssetLongestAssetNameCommand
/*    */   extends AbstractAsyncCommand
/*    */ {
/*    */   public AssetLongestAssetNameCommand() {
/* 30 */     super("longest", "");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 36 */     return CompletableFuture.runAsync(() -> {
/*    */           for (Map.Entry<Class<? extends JsonAssetWithMap>, AssetStore<?, ?, ?>> e : (Iterable<Map.Entry<Class<? extends JsonAssetWithMap>, AssetStore<?, ?, ?>>>)AssetRegistry.getStoreMap().entrySet()) {
/*    */             String longestName = "";
/*    */             for (Object asset : ((AssetStore)e.getValue()).getAssetMap().getAssetMap().keySet()) {
/*    */               String name = ((AssetStore)e.getValue()).transformKey(asset).toString();
/*    */               if (name.length() > longestName.length())
/*    */                 longestName = name; 
/*    */             } 
/*    */             context.sendMessage(Message.raw("Longest asset name for " + ((Class)e.getKey()).getSimpleName() + ": " + longestName + " (" + longestName.length() + " characters)"));
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\AssetsCommand$AssetLongestAssetNameCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */