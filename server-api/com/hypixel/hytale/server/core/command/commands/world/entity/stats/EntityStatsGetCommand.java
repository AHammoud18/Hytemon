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
/*    */ public class EntityStatsGetCommand
/*    */   extends AbstractTargetEntityCommand
/*    */ {
/*    */   @Nonnull
/* 33 */   private final RequiredArg<String> entityStatNameArg = withRequiredArg("statName", "server.commands.entity.stats.get.statName.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityStatsGetCommand() {
/* 39 */     super("get", "server.commands.entity.stats.get.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull ObjectList<Ref<EntityStore>> entities, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 44 */     String entityStat = (String)this.entityStatNameArg.get(context);
/* 45 */     getEntityStat(context, (List<Ref<EntityStore>>)entities, entityStat, store);
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
/*    */   public static void getEntityStat(@Nonnull CommandContext context, @Nonnull List<Ref<EntityStore>> entities, @Nonnull String entityStat, @Nonnull Store<EntityStore> store) {
/* 57 */     int entityStatIndex = EntityStatType.getAssetMap().getIndex(entityStat);
/* 58 */     if (entityStatIndex == Integer.MIN_VALUE) {
/* 59 */       context.sendMessage(Message.translation("server.commands.entityStats.entityStatNotFound").param("name", entityStat));
/* 60 */       context.sendMessage(Message.translation("server.general.failed.didYouMean").param("choices", StringUtil.sortByFuzzyDistance(entityStat, 
/* 61 */               EntityStatType.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 65 */     for (Ref<EntityStore> entity : entities) {
/* 66 */       EntityStatMap entityStatMapComponent = (EntityStatMap)store.getComponent(entity, EntityStatsModule.get().getEntityStatMapComponentType());
/* 67 */       if (entityStatMapComponent == null) {
/*    */         continue;
/*    */       }
/*    */       
/* 71 */       EntityStatValue value = entityStatMapComponent.get(entityStatIndex);
/* 72 */       if (value == null) {
/* 73 */         context.sendMessage(Message.translation("server.commands.entityStats.entityStatNotFound").param("name", entityStat));
/* 74 */         context.sendMessage(Message.translation("server.general.failed.didYouMean").param("choices", StringUtil.sortByFuzzyDistance(entityStat, 
/* 75 */                 EntityStatType.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*    */         
/*    */         continue;
/*    */       } 
/* 79 */       context.sendMessage(Message.translation("server.commands.entityStats.value")
/* 80 */           .param("name", value.getId())
/* 81 */           .param("value", value.get()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\stats\EntityStatsGetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */