/*     */ package com.hypixel.hytale.builtin.adventure.memories.memories.npc;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.memories.MemoriesPlugin;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.memories.Memory;
/*     */ import com.hypixel.hytale.builtin.adventure.memories.memories.MemoryProvider;
/*     */ import com.hypixel.hytale.logger.sentry.SkipSentryException;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderInfo;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*     */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*     */ import com.hypixel.hytale.server.spawning.ISpawnableWithModel;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
/*     */ 
/*     */ public class NPCMemoryProvider
/*     */   extends MemoryProvider<NPCMemory> {
/*     */   public NPCMemoryProvider() {
/*  27 */     super("NPC", NPCMemory.CODEC, 10.0D);
/*     */   }
/*     */   public static final double DEFAULT_RADIUS = 10.0D;
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, Set<Memory>> getAllMemories() {
/*  33 */     Object2ObjectOpenHashMap<String, Set<NPCMemory>> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/*     */     
/*  35 */     Int2ObjectMap<BuilderInfo> allBuilders = NPCPlugin.get().getBuilderManager().getAllBuilders();
/*  36 */     for (ObjectIterator<BuilderInfo> objectIterator = allBuilders.values().iterator(); objectIterator.hasNext(); ) { BuilderInfo builderInfo = objectIterator.next(); try {
/*     */         NPCMemory memory;
/*  38 */         Builder<?> builder = builderInfo.getBuilder();
/*  39 */         if (!builder.isSpawnable() || 
/*  40 */           builder.isDeprecated() || 
/*  41 */           !builderInfo.isValid() || 
/*  42 */           !isMemory(builder))
/*     */           continue; 
/*  44 */         String category = getCategory(builder);
/*  45 */         if (category == null) {
/*     */           continue;
/*     */         }
/*     */         
/*  49 */         String memoriesNameOverride = getMemoriesNameOverride(builder);
/*  50 */         String translationKey = getNPCNameTranslationKey(builder);
/*  51 */         if (memoriesNameOverride != null && !memoriesNameOverride.isEmpty()) {
/*  52 */           memory = new NPCMemory(memoriesNameOverride, translationKey, true);
/*     */         } else {
/*  54 */           memory = new NPCMemory(builderInfo.getKeyName(), translationKey, false);
/*     */         } 
/*     */         
/*  57 */         ((Set<NPCMemory>)object2ObjectOpenHashMap.computeIfAbsent(category, s -> new HashSet())).add(memory);
/*  58 */       } catch (SkipSentryException e) {
/*  59 */         MemoriesPlugin.get().getLogger().at(Level.SEVERE).log(e.getMessage());
/*     */       }  }
/*     */     
/*  62 */     return (Map)object2ObjectOpenHashMap;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static String getCategory(@Nonnull Builder<?> builder) {
/*  67 */     if (builder instanceof ISpawnableWithModel) { ISpawnableWithModel spawnableWithModel = (ISpawnableWithModel)builder;
/*  68 */       ExecutionContext executionContext = new ExecutionContext();
/*  69 */       executionContext.setScope(spawnableWithModel.createExecutionScope());
/*  70 */       Scope modifierScope = spawnableWithModel.createModifierScope(executionContext);
/*  71 */       return spawnableWithModel.getMemoriesCategory(executionContext, modifierScope); }
/*     */ 
/*     */     
/*  74 */     return "Other";
/*     */   }
/*     */   
/*     */   private static boolean isMemory(@Nonnull Builder<?> builder) {
/*  78 */     if (builder instanceof ISpawnableWithModel) { ISpawnableWithModel spawnableWithModel = (ISpawnableWithModel)builder;
/*  79 */       ExecutionContext executionContext = new ExecutionContext();
/*  80 */       executionContext.setScope(spawnableWithModel.createExecutionScope());
/*  81 */       Scope modifierScope = spawnableWithModel.createModifierScope(executionContext);
/*  82 */       return spawnableWithModel.isMemory(executionContext, modifierScope); }
/*     */ 
/*     */     
/*  85 */     return false;
/*     */   }
/*     */   
/*     */   @NullableDecl
/*     */   private static String getMemoriesNameOverride(@Nonnull Builder<?> builder) {
/*  90 */     if (builder instanceof ISpawnableWithModel) { ISpawnableWithModel spawnableWithModel = (ISpawnableWithModel)builder;
/*  91 */       ExecutionContext executionContext = new ExecutionContext();
/*  92 */       executionContext.setScope(spawnableWithModel.createExecutionScope());
/*  93 */       Scope modifierScope = spawnableWithModel.createModifierScope(executionContext);
/*  94 */       return spawnableWithModel.getMemoriesNameOverride(executionContext, modifierScope); }
/*     */ 
/*     */     
/*  97 */     return null;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static String getNPCNameTranslationKey(@Nonnull Builder<?> builder) {
/* 102 */     if (builder instanceof ISpawnableWithModel) { ISpawnableWithModel spawnableWithModel = (ISpawnableWithModel)builder;
/* 103 */       ExecutionContext executionContext = new ExecutionContext();
/* 104 */       executionContext.setScope(spawnableWithModel.createExecutionScope());
/* 105 */       Scope modifierScope = spawnableWithModel.createModifierScope(executionContext);
/* 106 */       return spawnableWithModel.getNameTranslationKey(executionContext, modifierScope); }
/*     */ 
/*     */     
/* 109 */     throw new SkipSentryException(new IllegalStateException("Cannot get translation key for a non spawnable NPC role!"));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\memories\memories\npc\NPCMemoryProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */