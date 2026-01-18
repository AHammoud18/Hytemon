/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.EntityWrappedArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EntityNameplateCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/* 22 */   private static final Message MESSAGE_GENERAL_NO_ENTITY_IN_VIEW = Message.translation("server.general.noEntityInView");
/* 23 */   private static final Message MESSAGE_COMMANDS_ENTITY_NAMEPLATE_UPDATED = Message.translation("server.commands.entity.nameplate.updated");
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 28 */   private final EntityWrappedArg entityArg = (EntityWrappedArg)
/* 29 */     withOptionalArg("entity", "server.commands.entity.nameplate.entity.desc", ArgTypes.ENTITY_ID);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 35 */   private final RequiredArg<String> textArg = withRequiredArg("text", "server.commands.entity.nameplate.text.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityNameplateCommand() {
/* 41 */     super("nameplate", "server.commands.entity.nameplate.desc");
/* 42 */     addUsageVariant((AbstractCommand)new Remove());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 47 */     Ref<EntityStore> entityRef = this.entityArg.get((ComponentAccessor)store, context);
/* 48 */     if (entityRef == null || !entityRef.isValid()) {
/* 49 */       context.sendMessage(MESSAGE_GENERAL_NO_ENTITY_IN_VIEW);
/*    */       
/*    */       return;
/*    */     } 
/* 53 */     String text = (String)this.textArg.get(context);
/* 54 */     ((Nameplate)store.ensureAndGetComponent(entityRef, Nameplate.getComponentType())).setText(text);
/* 55 */     context.sendMessage(MESSAGE_COMMANDS_ENTITY_NAMEPLATE_UPDATED);
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Remove
/*    */     extends AbstractWorldCommand
/*    */   {
/*    */     @Nonnull
/* 63 */     private static final Message MESSAGE_GENERAL_NO_ENTITY_IN_VIEW = Message.translation("server.general.noEntityInView");
/*    */     @Nonnull
/* 65 */     private static final Message MESSAGE_COMMANDS_ENTITY_NAMEPLATE_REMOVED = Message.translation("server.commands.entity.nameplate.removed");
/*    */ 
/*    */ 
/*    */     
/*    */     @Nonnull
/* 70 */     private final EntityWrappedArg entityArg = (EntityWrappedArg)
/* 71 */       withOptionalArg("entity", "server.commands.entity.nameplate.entity.desc", ArgTypes.ENTITY_ID);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public Remove() {
/* 77 */       super("server.commands.entity.nameplate.remove.desc");
/*    */     }
/*    */ 
/*    */     
/*    */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 82 */       Ref<EntityStore> entityRef = this.entityArg.get((ComponentAccessor)store, context);
/* 83 */       if (entityRef == null || !entityRef.isValid()) {
/* 84 */         context.sendMessage(MESSAGE_GENERAL_NO_ENTITY_IN_VIEW);
/*    */         
/*    */         return;
/*    */       } 
/* 88 */       store.tryRemoveComponent(entityRef, Nameplate.getComponentType());
/* 89 */       context.sendMessage(MESSAGE_COMMANDS_ENTITY_NAMEPLATE_REMOVED);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\EntityNameplateCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */