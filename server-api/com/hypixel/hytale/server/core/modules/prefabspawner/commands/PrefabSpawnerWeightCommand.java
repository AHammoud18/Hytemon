/*    */ package com.hypixel.hytale.server.core.modules.prefabspawner.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.modules.prefabspawner.PrefabSpawnerState;
/*    */ import com.hypixel.hytale.server.core.prefab.PrefabWeights;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabSpawnerWeightCommand
/*    */   extends TargetPrefabSpawnerCommand
/*    */ {
/*    */   @Nonnull
/* 24 */   private final RequiredArg<String> prefabArg = withRequiredArg("prefab", "server.commands.prefabspawner.weight.prefab.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 30 */   private final RequiredArg<Float> weightArg = withRequiredArg("weight", "server.commands.prefabspawner.weight.weight.desc", (ArgumentType)ArgTypes.FLOAT);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrefabSpawnerWeightCommand() {
/* 36 */     super("weight", "server.commands.prefabspawner.weight.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull WorldChunk chunk, @Nonnull PrefabSpawnerState prefabSpawner) {
/* 41 */     String prefab = (String)this.prefabArg.get(context);
/* 42 */     Float weight = (Float)this.weightArg.get(context);
/*    */     
/* 44 */     PrefabWeights prefabWeights = prefabSpawner.getPrefabWeights();
/* 45 */     if (prefabWeights == PrefabWeights.NONE) prefabWeights = new PrefabWeights();
/*    */     
/* 47 */     if (weight.floatValue() < 0.0F) {
/* 48 */       prefabWeights.removeWeight(prefab);
/* 49 */       context.sendMessage(Message.translation("server.commands.prefabspawner.weight.remove")
/* 50 */           .param("prefab", prefab));
/*    */     } else {
/* 52 */       prefabWeights.setWeight(prefab, weight.floatValue());
/* 53 */       context.sendMessage(Message.translation("server.commands.prefabspawner.weight.set")
/* 54 */           .param("prefab", prefab)
/* 55 */           .param("weight", weight.floatValue()));
/*    */     } 
/*    */     
/* 58 */     prefabSpawner.setPrefabWeights(prefabWeights);
/* 59 */     chunk.markNeedsSaving();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\prefabspawner\commands\PrefabSpawnerWeightCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */