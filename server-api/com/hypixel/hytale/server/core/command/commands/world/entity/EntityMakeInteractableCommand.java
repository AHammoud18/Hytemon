/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetEntityCommand;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.Interactable;
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
/*    */ public class EntityMakeInteractableCommand
/*    */   extends AbstractTargetEntityCommand
/*    */ {
/*    */   @Nonnull
/* 24 */   private final FlagArg disableFlag = withFlagArg("disable", "server.commands.entity.interactable.disable.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityMakeInteractableCommand() {
/* 30 */     super("interactable", "server.commands.entity.interactable.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull ObjectList<Ref<EntityStore>> entities, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 35 */     boolean disable = this.disableFlag.provided(context);
/* 36 */     for (ObjectListIterator<Ref<EntityStore>> objectListIterator = entities.iterator(); objectListIterator.hasNext(); ) { Ref<EntityStore> entity = objectListIterator.next();
/* 37 */       if (disable) {
/* 38 */         store.tryRemoveComponent(entity, Interactable.getComponentType()); continue;
/*    */       } 
/* 40 */       store.ensureComponent(entity, Interactable.getComponentType()); }
/*    */ 
/*    */ 
/*    */     
/* 44 */     context.sendMessage(Message.translation("server.commands.entity.interactable.success." + (disable ? "unset" : "set")).param("amount", entities.size()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\EntityMakeInteractableCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */