/*     */ package com.hypixel.hytale.server.core.modules.i18n.commands;
/*     */ import com.hypixel.hytale.assetstore.AssetPack;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.AssetModule;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*     */ import com.hypixel.hytale.server.core.modules.i18n.I18nModule;
/*     */ import com.hypixel.hytale.server.core.modules.i18n.event.GenerateDefaultLanguageEvent;
/*     */ import com.hypixel.hytale.server.core.modules.i18n.generator.TranslationMap;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileInputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class GenerateI18nCommand extends AbstractAsyncCommand {
/*  29 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*  31 */   protected final FlagArg cleanArg = withFlagArg("clean", "server.commands.i18n.gen.clean.desc");
/*     */   
/*     */   public GenerateI18nCommand() {
/*  34 */     super("gen", "server.commands.i18n.gen.desc");
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/*  39 */     CommandSender commandSender = context.sender();
/*     */     
/*  41 */     AssetPack baseAssetPack = AssetModule.get().getBaseAssetPack();
/*  42 */     if (baseAssetPack.isImmutable()) {
/*  43 */       commandSender.sendMessage(Message.translation("server.commands.i18n.gen.immutable"));
/*  44 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */     
/*  47 */     Path baseAssetPackRoot = baseAssetPack.getRoot();
/*     */     
/*  49 */     boolean cleanOldKeys = ((Boolean)this.cleanArg.get(context)).booleanValue();
/*     */     
/*  51 */     ConcurrentHashMap<String, TranslationMap> translationFiles = new ConcurrentHashMap<>();
/*     */     
/*  53 */     HytaleServer.get().getEventBus()
/*  54 */       .dispatchFor(GenerateDefaultLanguageEvent.class)
/*  55 */       .dispatch((IBaseEvent)new GenerateDefaultLanguageEvent(translationFiles));
/*     */     
/*  57 */     return CompletableFuture.runAsync(() -> {
/*     */           try {
/*     */             for (Map.Entry<String, TranslationMap> entry : (Iterable<Map.Entry<String, TranslationMap>>)translationFiles.entrySet()) {
/*     */               String filename = entry.getKey();
/*     */               
/*     */               TranslationMap generatedMap = entry.getValue();
/*     */               
/*     */               Path path = baseAssetPackRoot.resolve(I18nModule.DEFAULT_GENERATED_PATH).resolve(filename + ".lang");
/*     */               
/*     */               TranslationMap mergedMap = mergei18nWithOnDisk(path, generatedMap, cleanOldKeys);
/*     */               
/*     */               mergedMap.sortByKeyBeforeFirstDot();
/*     */               
/*     */               writeTranslationMap(path, mergedMap);
/*     */               
/*     */               LOGGER.at(Level.INFO).log("Wrote %s translation(s) to %s", mergedMap.size(), path.toAbsolutePath());
/*     */             } 
/*     */             
/*     */             LOGGER.at(Level.INFO).log("Wrote %s generated translation file(s)", translationFiles.size());
/*     */             commandSender.sendMessage(Message.translation(cleanOldKeys ? "server.commands.i18n.gen.cleaned" : "server.commands.i18n.gen.done"));
/*  77 */           } catch (Throwable t) {
/*     */             throw new RuntimeException("Error writing generated translation file(s)", t);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private TranslationMap mergei18nWithOnDisk(@Nonnull Path path, @Nonnull TranslationMap generated, boolean cleanOldKeys) throws Exception {
/*  86 */     TranslationMap mergedMap = new TranslationMap();
/*     */     
/*  88 */     if (Files.exists(path, new java.nio.file.LinkOption[0])) {
/*  89 */       Properties diskAsProperties = new Properties();
/*  90 */       diskAsProperties.load(new FileInputStream(path.toFile()));
/*  91 */       TranslationMap diskTranslationMap = new TranslationMap(diskAsProperties);
/*     */       
/*  93 */       if (cleanOldKeys) {
/*  94 */         Set<String> extraneousDiskKeys = difference(diskTranslationMap.asMap().keySet(), generated.asMap().keySet());
/*  95 */         diskTranslationMap.removeKeys(extraneousDiskKeys);
/*     */       } 
/*     */       
/*  98 */       mergedMap.putAbsentKeys(diskTranslationMap);
/*     */     } 
/*     */     
/* 101 */     mergedMap.putAbsentKeys(generated);
/*     */     
/* 103 */     return mergedMap;
/*     */   }
/*     */   
/*     */   private void writeTranslationMap(@Nonnull Path path, @Nonnull TranslationMap translationMap) throws Exception {
/* 107 */     Files.createDirectories(path.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/*     */     
/* 109 */     Map<String, String> map = translationMap.asMap();
/* 110 */     BufferedWriter writer = Files.newBufferedWriter(path, new java.nio.file.OpenOption[0]); 
/* 111 */     try { for (Map.Entry<String, String> e : map.entrySet()) {
/* 112 */         String k = e.getKey();
/* 113 */         String v = e.getValue();
/*     */         
/* 115 */         writer.write(k);
/* 116 */         writer.write(" = ");
/* 117 */         writer.write(v);
/* 118 */         writer.write(System.lineSeparator());
/*     */       } 
/* 120 */       if (writer != null) writer.close();  }
/*     */     catch (Throwable throwable) { if (writer != null)
/*     */         try { writer.close(); }
/*     */         catch (Throwable throwable1)
/*     */         { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 126 */      } @Nonnull private static <T> Set<T> difference(@Nonnull Set<T> a, @Nonnull Set<T> b) { Set<T> difference = new HashSet<>(a);
/* 127 */     difference.removeAll(b);
/* 128 */     return difference; }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\i18n\commands\GenerateI18nCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */