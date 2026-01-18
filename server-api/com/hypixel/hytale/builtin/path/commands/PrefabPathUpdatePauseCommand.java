/*    */ package com.hypixel.hytale.builtin.path.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.path.entities.PatrolPathMarkerEntity;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.EntityWrappedArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabPathUpdatePauseCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 30 */   private final EntityWrappedArg entityIdArg = (EntityWrappedArg)
/* 31 */     withOptionalArg("entityId", "server.commands.npcpath.update.pause.entityId.desc", ArgTypes.ENTITY_ID);
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 36 */   private final RequiredArg<Double> pauseTimeArg = withRequiredArg("pauseTime", "server.commands.npcpath.update.pause.pauseTime.desc", (ArgumentType)ArgTypes.DOUBLE);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrefabPathUpdatePauseCommand() {
/* 42 */     super("pause", "server.commands.npcpath.update.pause.desc");
/*    */   }
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*    */     Ref<EntityStore> ref;
/* 47 */     Double pauseTime = (Double)this.pauseTimeArg.get(context);
/*    */ 
/*    */     
/* 50 */     if (this.entityIdArg.provided(context)) {
/* 51 */       ref = this.entityIdArg.get((ComponentAccessor)store, context);
/*    */     } else {
/* 53 */       Ref<EntityStore> playerRef = context.senderAsPlayerRef();
/*    */ 
/*    */       
/* 56 */       if (playerRef == null) {
/* 57 */         throw new GeneralCommandException(Message.translation("server.commands.errors.playerOrArg")
/* 58 */             .param("option", "entity"));
/*    */       }
/*    */ 
/*    */       
/* 62 */       if (!playerRef.isValid()) {
/* 63 */         throw new GeneralCommandException(Message.translation("server.commands.errors.playerNotInWorld")
/* 64 */             .param("option", "entity"));
/*    */       }
/*    */       
/* 67 */       Ref<EntityStore> entityRef = TargetUtil.getTargetEntity(playerRef, (ComponentAccessor)store);
/* 68 */       if (entityRef == null) {
/* 69 */         throw new GeneralCommandException(Message.translation("server.commands.errors.no_entity_in_view")
/* 70 */             .param("option", "entity"));
/*    */       }
/* 72 */       ref = entityRef;
/*    */     } 
/*    */     
/* 75 */     PatrolPathMarkerEntity patrolPathMarkerComponent = (PatrolPathMarkerEntity)store.getComponent(ref, PatrolPathMarkerEntity.getComponentType());
/* 76 */     if (patrolPathMarkerComponent == null) {
/* 77 */       context.sendMessage(Message.translation("server.general.entityNotFound").param("id", ref.getIndex()));
/*    */       return;
/*    */     } 
/* 80 */     patrolPathMarkerComponent.setPauseTime(pauseTime.doubleValue());
/*    */     
/* 82 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 83 */     assert transformComponent != null;
/* 84 */     transformComponent.markChunkDirty((ComponentAccessor)store);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\PrefabPathUpdatePauseCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */