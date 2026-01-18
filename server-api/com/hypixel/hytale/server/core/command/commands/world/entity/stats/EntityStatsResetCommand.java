/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity.stats;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.StringUtil;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandUtil;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetEntityCommand;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsModule;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityStatsResetCommand
/*    */   extends AbstractTargetEntityCommand
/*    */ {
/*    */   @Nonnull
/* 31 */   private final RequiredArg<String> entityStatNameArg = withRequiredArg("statName", "server.commands.entity.stats.reset.statName.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityStatsResetCommand() {
/* 37 */     super("reset", "server.commands.entity.stats.reset.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull ObjectList<Ref<EntityStore>> entities, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 42 */     String entityStat = (String)this.entityStatNameArg.get(context);
/* 43 */     resetEntityStat(context, (List<Ref<EntityStore>>)entities, entityStat, store);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void resetEntityStat(@Nonnull CommandContext context, @Nonnull List<Ref<EntityStore>> entities, @Nonnull String entityStat, @Nonnull Store<EntityStore> store) {
/* 55 */     int entityStatIndex = EntityStatType.getAssetMap().getIndex(entityStat);
/* 56 */     if (entityStatIndex == Integer.MIN_VALUE) {
/* 57 */       context.sendMessage(Message.translation("server.commands.entityStats.entityStatNotFound").param("name", entityStat));
/* 58 */       context.sendMessage(Message.translation("server.general.failed.didYouMean").param("choices", 
/* 59 */             StringUtil.sortByFuzzyDistance(entityStat, EntityStatType.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 63 */     for (Ref<EntityStore> entity : entities) {
/* 64 */       EntityStatMap entityStatMap = (EntityStatMap)store.getComponent(entity, EntityStatsModule.get().getEntityStatMapComponentType());
/* 65 */       if (entityStatMap == null) {
/*    */         continue;
/*    */       }
/*    */       
/* 69 */       if (entityStatMap.get(entityStatIndex) == null) {
/* 70 */         context.sendMessage(Message.translation("server.commands.entityStats.entityStatNotFound").param("name", entityStat));
/* 71 */         context.sendMessage(Message.translation("server.general.failed.didYouMean").param("choices", 
/* 72 */               StringUtil.sortByFuzzyDistance(entityStat, EntityStatType.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*    */         
/*    */         continue;
/*    */       } 
/* 76 */       float valueResetTo = entityStatMap.resetStatValue(entityStatIndex);
/* 77 */       context.sendMessage(Message.translation("server.commands.entityStats.valueReset")
/* 78 */           .param("name", entityStat)
/* 79 */           .param("value", valueResetTo));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\stats\EntityStatsResetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */