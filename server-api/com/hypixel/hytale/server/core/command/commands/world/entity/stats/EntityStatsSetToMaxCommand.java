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
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
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
/*    */ public class EntityStatsSetToMaxCommand
/*    */   extends AbstractTargetEntityCommand
/*    */ {
/*    */   @Nonnull
/* 33 */   private final RequiredArg<String> entityStatNameArg = withRequiredArg("statName", "server.commands.entity.stats.settomax.statName.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityStatsSetToMaxCommand() {
/* 39 */     super("settomax", "server.commands.entity.stats.settomax.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull ObjectList<Ref<EntityStore>> entities, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 44 */     String entityStatName = (String)this.entityStatNameArg.get(context);
/* 45 */     setEntityStatMax(context, (List<Ref<EntityStore>>)entities, entityStatName, store);
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
/*    */   public static void setEntityStatMax(@Nonnull CommandContext context, @Nonnull List<Ref<EntityStore>> entities, @Nonnull String entityStatName, @Nonnull Store<EntityStore> store) {
/* 57 */     int entityStatIndex = EntityStatType.getAssetMap().getIndex(entityStatName);
/* 58 */     if (entityStatIndex == Integer.MIN_VALUE) {
/* 59 */       context.sendMessage(Message.translation("server.commands.entityStats.entityStatNotFound").param("name", entityStatName));
/* 60 */       context.sendMessage(Message.translation("server.general.failed.didYouMean").param("choices", 
/* 61 */             StringUtil.sortByFuzzyDistance(entityStatName, EntityStatType.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 65 */     for (Ref<EntityStore> entity : entities) {
/* 66 */       EntityStatMap entityStatMap = (EntityStatMap)store.getComponent(entity, EntityStatsModule.get().getEntityStatMapComponentType());
/* 67 */       if (entityStatMap == null) {
/*    */         continue;
/*    */       }
/*    */       
/* 71 */       EntityStatValue entityStatValue = entityStatMap.get(entityStatIndex);
/* 72 */       if (entityStatValue == null) {
/* 73 */         context.sendMessage(Message.translation("server.commands.entityStats.entityStatNotFound").param("name", entityStatName));
/* 74 */         context.sendMessage(Message.translation("server.general.failed.didYouMean").param("choices", 
/* 75 */               StringUtil.sortByFuzzyDistance(entityStatName, EntityStatType.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*    */         
/*    */         continue;
/*    */       } 
/* 79 */       float newValueOfStat = entityStatMap.setStatValue(entityStatIndex, entityStatValue.getMax());
/* 80 */       context.sendMessage(Message.translation("server.commands.entityStats.success").param("name", entityStatName).param("value", newValueOfStat));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\stats\EntityStatsSetToMaxCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */