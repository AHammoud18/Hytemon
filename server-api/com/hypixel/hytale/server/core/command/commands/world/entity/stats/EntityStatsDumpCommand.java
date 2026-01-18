/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity.stats;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetEntityCommand;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityStatsDumpCommand
/*    */   extends AbstractTargetEntityCommand
/*    */ {
/*    */   public EntityStatsDumpCommand() {
/* 27 */     super("dump", "server.commands.entity.stats.dump.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull ObjectList<Ref<EntityStore>> entities, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 34 */     dumpEntityStatsData(context, (List<Ref<EntityStore>>)entities, store);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void dumpEntityStatsData(@Nonnull CommandContext context, @Nonnull List<Ref<EntityStore>> entities, @Nonnull Store<EntityStore> store) {
/* 45 */     for (Ref<EntityStore> entity : entities) {
/* 46 */       ComponentType<EntityStore, EntityStatMap> component = EntityStatsModule.get().getEntityStatMapComponentType();
/* 47 */       EntityStatMap statMap = (EntityStatMap)store.getComponent(entity, component);
/* 48 */       if (statMap == null)
/*    */         continue; 
/* 50 */       ObjectArrayList<Message> values = new ObjectArrayList(statMap.size());
/* 51 */       for (int i = 0; i < statMap.size(); i++) {
/* 52 */         EntityStatValue entityStat = statMap.get(i);
/* 53 */         values.add(Message.translation("server.commands.entityStats.value")
/* 54 */             .param("name", entityStat.getId())
/* 55 */             .param("value", entityStat.get()));
/*    */       } 
/* 57 */       context.sendMessage(MessageFormat.list(null, (Collection)values));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\stats\EntityStatsDumpCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */