/*     */ package com.hypixel.hytale.server.core.command.commands.debug;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAsset;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetRegistry;
/*     */ import com.hypixel.hytale.server.core.asset.common.asset.FileCommonAsset;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*     */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class AssetsDuplicatesCommand
/*     */   extends AbstractAsyncCommand
/*     */ {
/*     */   @Nonnull
/*  31 */   private final FlagArg reverseFlag = withFlagArg("reverse", "server.commands.assets.duplicates.reverse.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetsDuplicatesCommand() {
/*  37 */     super("duplicates", "server.commands.assets.duplicates.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/*  43 */     boolean reverse = ((Boolean)this.reverseFlag.get(context)).booleanValue();
/*     */     
/*  45 */     ObjectArrayList<CompletableFuture<Void>> objectArrayList = new ObjectArrayList();
/*  46 */     ObjectArrayList<DuplicatedAssetInfo> objectArrayList1 = new ObjectArrayList();
/*  47 */     for (Map.Entry<String, List<CommonAssetRegistry.PackAsset>> entry : (Iterable<Map.Entry<String, List<CommonAssetRegistry.PackAsset>>>)CommonAssetRegistry.getDuplicatedAssets().entrySet()) {
/*  48 */       DuplicatedAssetInfo duplicateInfo = new DuplicatedAssetInfo(entry.getKey(), entry.getValue());
/*  49 */       objectArrayList1.add(duplicateInfo);
/*  50 */       objectArrayList.add(duplicateInfo.calculateTotalSize());
/*     */     } 
/*     */     
/*  53 */     return CompletableFuture.allOf((CompletableFuture<?>[])objectArrayList.toArray(x$0 -> new CompletableFuture[x$0])).thenAccept(aVoid -> {
/*     */           duplicates.sort(reverse ? DuplicatedAssetInfo.COMPARATOR_REVERSE : DuplicatedAssetInfo.COMPARATOR);
/*     */           long totalWastedSpace = 0L;
/*     */           for (DuplicatedAssetInfo duplicateInfo : duplicates) {
/*     */             Message header = Message.translation("server.commands.assets.duplicates.header").param("hash", duplicateInfo.hash).param("wastedBytes", FormatUtil.bytesToString(duplicateInfo.wastedSpace));
/*     */             Set<Message> duplicateAssets = (Set<Message>)duplicateInfo.assets.stream().map(()).map(Message::raw).collect(Collectors.toSet());
/*     */             context.sendMessage(MessageFormat.list(header, duplicateAssets));
/*     */             totalWastedSpace += duplicateInfo.wastedSpace;
/*     */           } 
/*     */           context.sendMessage(Message.translation("server.commands.assets.duplicates.total").param("wastedBytes", FormatUtil.bytesToString(totalWastedSpace)));
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DuplicatedAssetInfo
/*     */   {
/*     */     @Nonnull
/*     */     public static final Comparator<DuplicatedAssetInfo> COMPARATOR;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  82 */       COMPARATOR = Comparator.comparingLong(o -> o.wastedSpace);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*  88 */     public static final Comparator<DuplicatedAssetInfo> COMPARATOR_REVERSE = Collections.reverseOrder(COMPARATOR);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     final String hash;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     final List<CommonAssetRegistry.PackAsset> assets;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     long wastedSpace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DuplicatedAssetInfo(@Nonnull String hash, @Nonnull List<CommonAssetRegistry.PackAsset> assets) {
/* 114 */       this.hash = hash;
/* 115 */       this.assets = assets;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CompletableFuture<Void> calculateTotalSize() {
/* 125 */       CommonAsset commonAsset = ((CommonAssetRegistry.PackAsset)this.assets.getFirst()).asset();
/* 126 */       if (commonAsset instanceof FileCommonAsset) { FileCommonAsset fileCommonAsset = (FileCommonAsset)commonAsset;
/* 127 */         Path path = fileCommonAsset.getFile();
/* 128 */         return CompletableFuture.runAsync(SneakyThrow.sneakyRunnable(() -> this.wastedSpace = Files.size(path) * (this.assets.size() - 1))); }
/*     */       
/* 130 */       return commonAsset.getBlob().thenAccept(bytes -> this.wastedSpace = bytes.length * (this.assets.size() - 1));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\AssetsDuplicatesCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */