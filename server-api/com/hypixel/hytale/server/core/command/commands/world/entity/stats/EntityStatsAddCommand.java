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
/*    */ 
/*    */ 
/*    */ public class EntityStatsAddCommand
/*    */   extends AbstractTargetEntityCommand
/*    */ {
/*    */   @Nonnull
/* 35 */   private final RequiredArg<String> entityStatNameArg = withRequiredArg("statName", "server.commands.entity.stats.add.statName.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 41 */   private final RequiredArg<Integer> statAmountArg = withRequiredArg("statAmount", "server.commands.entity.stats.add.statAmount.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityStatsAddCommand() {
/* 47 */     super("add", "server.commands.entity.stats.add.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull ObjectList<Ref<EntityStore>> entities, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 52 */     int statAmount = ((Integer)this.statAmountArg.get(context)).intValue();
/* 53 */     String entityStatName = (String)this.entityStatNameArg.get(context);
/* 54 */     addEntityStat(context, (List<Ref<EntityStore>>)entities, statAmount, entityStatName, store);
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void addEntityStat(@Nonnull CommandContext context, @Nonnull List<Ref<EntityStore>> entityRefs, int statAmount, @Nonnull String entityStatName, @Nonnull Store<EntityStore> store) {
/* 71 */     int entityStatIndex = EntityStatType.getAssetMap().getIndex(entityStatName);
/* 72 */     if (entityStatIndex == Integer.MIN_VALUE) {
/* 73 */       context.sendMessage(Message.translation("server.commands.entityStats.entityStatNotFound")
/* 74 */           .param("name", entityStatName));
/* 75 */       context.sendMessage(Message.translation("server.general.failed.didYouMean")
/* 76 */           .param("choices", 
/* 77 */             StringUtil.sortByFuzzyDistance(entityStatName, EntityStatType.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 81 */     for (Ref<EntityStore> entity : entityRefs) {
/* 82 */       EntityStatMap entityStatMapComponent = (EntityStatMap)store.getComponent(entity, EntityStatsModule.get().getEntityStatMapComponentType());
/* 83 */       if (entityStatMapComponent == null) {
/*    */         continue;
/*    */       }
/*    */       
/* 87 */       if (entityStatMapComponent.get(entityStatIndex) == null) {
/* 88 */         context.sendMessage(Message.translation("server.commands.entityStats.entityStatNotFound")
/* 89 */             .param("name", entityStatName));
/* 90 */         context.sendMessage(Message.translation("server.general.failed.didYouMean")
/* 91 */             .param("choices", 
/* 92 */               StringUtil.sortByFuzzyDistance(entityStatName, EntityStatType.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*    */         
/*    */         continue;
/*    */       } 
/* 96 */       float newValueOfStat = entityStatMapComponent.addStatValue(entityStatIndex, statAmount);
/* 97 */       context.sendMessage(Message.translation("server.commands.entityStats.success")
/* 98 */           .param("name", entityStatName)
/* 99 */           .param("value", newValueOfStat));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\stats\EntityStatsAddCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */