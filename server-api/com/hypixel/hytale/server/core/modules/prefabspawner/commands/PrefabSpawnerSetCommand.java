/*     */ package com.hypixel.hytale.server.core.modules.prefabspawner.commands;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.modules.prefabspawner.PrefabSpawnerState;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabWeights;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrefabSpawnerSetCommand
/*     */   extends TargetPrefabSpawnerCommand
/*     */ {
/*     */   @Nonnull
/*  22 */   private static final Message MESSAGE_COMMANDS_PREFAB_SPAWNER_SET = Message.translation("server.commands.prefabspawner.set");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  28 */   protected final RequiredArg<String> prefabPathArg = withRequiredArg("prefab", "server.commands.prefabspawner.set.prefab.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  34 */   protected final OptionalArg<Boolean> fitHeightmapArg = withOptionalArg("fitHeightmap", "server.commands.prefabspawner.set.fitHeightmap.desc", (ArgumentType)ArgTypes.BOOLEAN);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  40 */   protected final OptionalArg<Boolean> inheritSeedArg = withOptionalArg("inheritSeed", "server.commands.prefabspawner.set.inheritSeed.desc", (ArgumentType)ArgTypes.BOOLEAN);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  46 */   protected final OptionalArg<Boolean> inheritHeightCheckArg = withOptionalArg("inheritHeightCheck", "server.commands.prefabspawner.set.inheritHeightCheck.desc", (ArgumentType)ArgTypes.BOOLEAN);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  52 */   protected final OptionalArg<Double> defaultWeightArg = withOptionalArg("defaultWeight", "server.commands.prefabspawner.set.defaultWeight.desc", (ArgumentType)ArgTypes.DOUBLE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrefabSpawnerSetCommand() {
/*  58 */     super("set", "server.commands.prefabspawner.set.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull WorldChunk chunk, @Nonnull PrefabSpawnerState prefabSpawner) {
/*  63 */     String prefabPath = (String)this.prefabPathArg.get(context);
/*  64 */     prefabSpawner.setPrefabPath(prefabPath);
/*     */     
/*  66 */     if (this.fitHeightmapArg.provided(context)) {
/*  67 */       boolean fitHeightmap = getOrDefault(this.fitHeightmapArg, context, true);
/*  68 */       prefabSpawner.setFitHeightmap(fitHeightmap);
/*     */     } 
/*     */     
/*  71 */     if (this.inheritSeedArg.provided(context)) {
/*  72 */       boolean inheritSeed = getOrDefault(this.inheritSeedArg, context, true);
/*  73 */       prefabSpawner.setInheritSeed(inheritSeed);
/*     */     } 
/*     */     
/*  76 */     if (this.inheritHeightCheckArg.provided(context)) {
/*  77 */       boolean inheritHeightCheck = getOrDefault(this.inheritHeightCheckArg, context, true);
/*  78 */       prefabSpawner.setInheritHeightCondition(inheritHeightCheck);
/*     */     } 
/*     */     
/*  81 */     if (this.defaultWeightArg.provided(context)) {
/*  82 */       double weight = ((Double)this.defaultWeightArg.get(context)).doubleValue();
/*  83 */       PrefabWeights prefabWeights = prefabSpawner.getPrefabWeights();
/*  84 */       if (prefabWeights == PrefabWeights.NONE) prefabWeights = new PrefabWeights(); 
/*  85 */       prefabWeights.setDefaultWeight(weight);
/*  86 */       prefabSpawner.setPrefabWeights(prefabWeights);
/*     */     } 
/*     */     
/*  89 */     chunk.markNeedsSaving();
/*  90 */     context.sendMessage(MESSAGE_COMMANDS_PREFAB_SPAWNER_SET);
/*     */   }
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
/*     */   protected static boolean getOrDefault(@Nonnull OptionalArg<Boolean> arg, @Nonnull CommandContext context, boolean defaultValue) {
/* 103 */     if (!arg.provided(context)) {
/* 104 */       return defaultValue;
/*     */     }
/* 106 */     Boolean value = (Boolean)arg.get(context);
/* 107 */     return (value != null) ? value.booleanValue() : defaultValue;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\prefabspawner\commands\PrefabSpawnerSetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */