/*     */ package com.hypixel.hytale.server.core.command.commands.utility;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetPack;
/*     */ import com.hypixel.hytale.common.util.PathUtil;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.AssetModule;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.BsonPrefabBufferDeserializer;
/*     */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*     */ import com.hypixel.hytale.server.core.util.io.FileUtil;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.bson.BsonDocument;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ValidateCPBCommand
/*     */   extends AbstractAsyncCommand
/*     */ {
/*     */   private static final String UNABLE_TO_LOAD_MODEL = "Unable to load entity with model ";
/*     */   private static final String FAILED_TO_FIND_BLOCK = "Failed to find block ";
/*     */   @Nonnull
/*  36 */   private final OptionalArg<String> pathArg = withOptionalArg("path", "server.commands.validatecpb.path.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValidateCPBCommand() {
/*  42 */     super("validatecpb", "server.commands.validatecpb.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/*  48 */     if (this.pathArg.provided(context)) {
/*  49 */       String path = (String)this.pathArg.get(context);
/*  50 */       return CompletableFuture.runAsync(() -> convertPrefabs(context, PathUtil.get(path)));
/*     */     } 
/*  52 */     return CompletableFuture.runAsync(() -> {
/*     */           for (AssetPack pack : AssetModule.get().getAssetPacks()) {
/*     */             convertPrefabs(context, pack.getRoot());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static void convertPrefabs(@Nonnull CommandContext context, @Nonnull Path assetPath) {
/*  61 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/*     */     
/*  63 */     try { Stream<Path> stream = Files.walk(assetPath, FileUtil.DEFAULT_WALK_TREE_OPTIONS_ARRAY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  88 */       try { CompletableFuture[] futures = (CompletableFuture[])stream.filter(path -> (Files.isRegularFile(path, new java.nio.file.LinkOption[0]) && path.toString().endsWith(".prefab.json"))).map(path -> BsonUtil.readDocument(path, false).thenAccept(()).exceptionally(())).toArray(x$0 -> new CompletableFuture[x$0]);
/*     */ 
/*     */         
/*  91 */         CompletableFuture.allOf((CompletableFuture<?>[])futures).join();
/*  92 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/*  93 */     { throw SneakyThrow.sneakyThrow(e); }
/*     */ 
/*     */     
/*  96 */     if (!objectArrayList.isEmpty()) {
/*  97 */       context.sendMessage(Message.translation("server.commands.validatecpb.failed")
/*  98 */           .param("failed", objectArrayList.toString()));
/*     */     }
/*     */     
/* 101 */     context.sendMessage(Message.translation("server.commands.prefabConvertionDone")
/* 102 */         .param("path", assetPath.toString()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\ValidateCPBCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */