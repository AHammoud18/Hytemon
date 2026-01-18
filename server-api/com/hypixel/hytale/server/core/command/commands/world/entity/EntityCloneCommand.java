/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity;
/*    */ 
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.EntityWrappedArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class EntityCloneCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 29 */   private static final Message MESSAGE_GENERAL_NO_ENTITY_IN_VIEW = Message.translation("server.general.noEntityInView");
/*    */   @Nonnull
/* 31 */   private static final Message MESSAGE_COMMANDS_ENTITY_CLONE_CLONED = Message.translation("server.commands.entity.clone.cloned");
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 36 */   private final EntityWrappedArg entityArg = (EntityWrappedArg)
/* 37 */     withOptionalArg("entity", "server.commands.entity.clone.entity.desc", ArgTypes.ENTITY_ID);
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 42 */   private final DefaultArg<Integer> countArg = (DefaultArg<Integer>)
/* 43 */     withDefaultArg("count", "server.commands.entity.clone.count.desc", (ArgumentType)ArgTypes.INTEGER, Integer.valueOf(1), "server.commands.entity.clone.count.default")
/* 44 */     .addValidator(Validators.greaterThan(Integer.valueOf(0)));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityCloneCommand() {
/* 50 */     super("clone", "server.commands.entity.clone.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 55 */     Ref<EntityStore> entityRef = this.entityArg.get((ComponentAccessor)store, context);
/* 56 */     if (entityRef == null || !entityRef.isValid()) {
/* 57 */       context.sendMessage(MESSAGE_GENERAL_NO_ENTITY_IN_VIEW);
/*    */       
/*    */       return;
/*    */     } 
/* 61 */     CommandSender sender = context.sender();
/* 62 */     int count = ((Integer)this.countArg.get(context)).intValue();
/*    */     
/* 64 */     for (int i = 0; i < count; i++) {
/* 65 */       Holder<EntityStore> copy = store.copyEntity(entityRef);
/* 66 */       copy.replaceComponent(UUIDComponent.getComponentType(), (Component)new UUIDComponent(UUID.randomUUID()));
/* 67 */       store.addEntity(copy, AddReason.SPAWN);
/*    */     } 
/*    */     
/* 70 */     if (count == 1) {
/* 71 */       sender.sendMessage(MESSAGE_COMMANDS_ENTITY_CLONE_CLONED);
/*    */     } else {
/* 73 */       sender.sendMessage(Message.translation("server.commands.entity.clone.cloned.multiple")
/* 74 */           .param("count", count));
/*    */     } 
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
/*    */   public static void cloneEntity(@Nonnull CommandSender sender, @Nonnull Ref<EntityStore> entityReference, @Nonnull Store<EntityStore> store) {
/* 88 */     Holder<EntityStore> copy = store.copyEntity(entityReference);
/* 89 */     if (copy.getComponent(UUIDComponent.getComponentType()) != null) {
/* 90 */       copy.replaceComponent(UUIDComponent.getComponentType(), (Component)new UUIDComponent(UUID.randomUUID()));
/*    */     }
/* 92 */     store.addEntity(copy, AddReason.SPAWN);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\EntityCloneCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */