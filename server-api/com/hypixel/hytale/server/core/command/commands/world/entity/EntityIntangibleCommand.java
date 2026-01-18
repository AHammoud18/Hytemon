/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetEntityCommand;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.Intangible;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityIntangibleCommand
/*    */   extends AbstractTargetEntityCommand
/*    */ {
/*    */   @Nonnull
/* 24 */   private final FlagArg removeFlag = withFlagArg("remove", "server.commands.entity.intangible.remove.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityIntangibleCommand() {
/* 30 */     super("intangible", "server.commands.entity.intangible.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull ObjectList<Ref<EntityStore>> entities, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 35 */     boolean remove = this.removeFlag.provided(context);
/* 36 */     for (ObjectListIterator<Ref<EntityStore>> objectListIterator = entities.iterator(); objectListIterator.hasNext(); ) { Ref<EntityStore> entity = objectListIterator.next();
/* 37 */       if (remove) {
/* 38 */         store.tryRemoveComponent(entity, Intangible.getComponentType()); continue;
/*    */       } 
/* 40 */       store.ensureComponent(entity, Intangible.getComponentType()); }
/*    */ 
/*    */     
/* 43 */     context.sendMessage(Message.translation("server.commands.entity.intangible.success." + (remove ? "unset" : "set")).param("amount", entities.size()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\EntityIntangibleCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */