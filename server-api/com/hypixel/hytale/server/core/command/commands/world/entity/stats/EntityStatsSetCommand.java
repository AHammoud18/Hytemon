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
/*    */ 
/*    */ 
/*    */ public class EntityStatsSetCommand
/*    */   extends AbstractTargetEntityCommand
/*    */ {
/*    */   @Nonnull
/* 33 */   private final RequiredArg<String> entityStatNameArg = withRequiredArg("statName", "server.commands.entity.stats.set.statName.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 39 */   private final RequiredArg<Integer> statValueArg = withRequiredArg("statValue", "server.commands.entity.stats.set.statValue.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityStatsSetCommand() {
/* 45 */     super("set", "server.commands.entity.stats.set.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull ObjectList<Ref<EntityStore>> entities, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 50 */     int newStatValue = ((Integer)this.statValueArg.get(context)).intValue();
/* 51 */     String entityStatName = (String)this.entityStatNameArg.get(context);
/* 52 */     setEntityStat(context, (List<Ref<EntityStore>>)entities, newStatValue, entityStatName, store);
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
/*    */   
/*    */   public static void setEntityStat(@Nonnull CommandContext context, @Nonnull List<Ref<EntityStore>> entities, int newStatValue, @Nonnull String entityStatName, @Nonnull Store<EntityStore> store) {
/* 65 */     int entityStatIndex = EntityStatType.getAssetMap().getIndex(entityStatName);
/* 66 */     if (entityStatIndex == Integer.MIN_VALUE) {
/* 67 */       context.sendMessage(Message.translation("server.commands.entityStats.entityStatNotFound").param("name", entityStatName));
/* 68 */       context.sendMessage(Message.translation("server.general.failed.didYouMean").param("choices", 
/* 69 */             StringUtil.sortByFuzzyDistance(entityStatName, EntityStatType.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 73 */     for (Ref<EntityStore> entity : entities) {
/* 74 */       EntityStatMap entityStatMap = (EntityStatMap)store.getComponent(entity, EntityStatsModule.get().getEntityStatMapComponentType());
/* 75 */       if (entityStatMap == null) {
/*    */         continue;
/*    */       }
/*    */       
/* 79 */       if (entityStatMap.get(entityStatIndex) == null) {
/* 80 */         context.sendMessage(Message.translation("server.commands.entityStats.entityStatNotFound").param("name", entityStatName));
/* 81 */         context.sendMessage(Message.translation("server.general.failed.didYouMean").param("choices", 
/* 82 */               StringUtil.sortByFuzzyDistance(entityStatName, EntityStatType.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*    */         
/*    */         continue;
/*    */       } 
/* 86 */       float newValueOfStat = entityStatMap.setStatValue(entityStatIndex, newStatValue);
/* 87 */       context.sendMessage(Message.translation("server.commands.entityStats.success").param("name", entityStatName).param("value", newValueOfStat));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\stats\EntityStatsSetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */