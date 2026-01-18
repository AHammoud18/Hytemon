/*    */ package com.hypixel.hytale.server.core.modules.prefabspawner.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.modules.prefabspawner.PrefabSpawnerState;
/*    */ import com.hypixel.hytale.server.core.prefab.PrefabWeights;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabSpawnerGetCommand
/*    */   extends TargetPrefabSpawnerCommand
/*    */ {
/*    */   public PrefabSpawnerGetCommand() {
/* 20 */     super("get", "server.commands.prefabspawner.get.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull WorldChunk chunk, @Nonnull PrefabSpawnerState prefabSpawner) {
/* 25 */     String prefab = Objects.<String>requireNonNullElse(prefabSpawner.getPrefabPath(), "<undefined>");
/* 26 */     context.sendMessage(Message.translation("server.commands.prefabspawner.get.path")
/* 27 */         .param("prefab", prefab));
/* 28 */     context.sendMessage(Message.translation("server.commands.prefabspawner.get.fitsHeightmap")
/* 29 */         .param("fitHeightmap", prefabSpawner.isFitHeightmap()));
/* 30 */     context.sendMessage(Message.translation("server.commands.prefabspawner.get.inheritsSeed")
/* 31 */         .param("inheritSeed", prefabSpawner.isInheritSeed()));
/* 32 */     context.sendMessage(Message.translation("server.commands.prefabspawner.get.inheritsHeightCheck")
/* 33 */         .param("inheritHeightCheck", prefabSpawner.isInheritHeightCondition()));
/*    */     
/* 35 */     PrefabWeights weights = prefabSpawner.getPrefabWeights();
/* 36 */     if (weights.size() == 0)
/*    */       return; 
/* 38 */     context.sendMessage(Message.translation("server.commands.prefabspawner.get.defaultWeight")
/* 39 */         .param("weight", weights.getDefaultWeight()));
/* 40 */     context.sendMessage(Message.translation("server.commands.prefabspawner.get.weights")
/* 41 */         .param("weights", weights.getMappingString()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\prefabspawner\commands\PrefabSpawnerGetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */