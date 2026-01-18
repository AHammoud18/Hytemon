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
/*    */ 
/*    */ public class PrefabPathUpdateObservationAngleCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 30 */   private final EntityWrappedArg entityIdArg = (EntityWrappedArg)
/* 31 */     withOptionalArg("entityId", "server.commands.npcpath.update.observationAngle.entityId.desc", ArgTypes.ENTITY_ID);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 37 */   private final RequiredArg<Float> angleArg = withRequiredArg("angle", "server.commands.npcpath.update.observationAngle.angle.desc", (ArgumentType)ArgTypes.FLOAT);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrefabPathUpdateObservationAngleCommand() {
/* 43 */     super("observationAngle", "server.commands.npcpath.update.observationAngle.desc");
/*    */   }
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*    */     Ref<EntityStore> ref;
/* 48 */     Float angleDegrees = (Float)this.angleArg.get(context);
/*    */ 
/*    */     
/* 51 */     if (this.entityIdArg.provided(context)) {
/* 52 */       ref = this.entityIdArg.get((ComponentAccessor)store, context);
/*    */     } else {
/* 54 */       Ref<EntityStore> playerRef = context.senderAsPlayerRef();
/*    */ 
/*    */       
/* 57 */       if (playerRef == null) {
/* 58 */         throw new GeneralCommandException(Message.translation("server.commands.errors.playerOrArg")
/* 59 */             .param("option", "entity"));
/*    */       }
/*    */ 
/*    */       
/* 63 */       if (!playerRef.isValid()) {
/* 64 */         throw new GeneralCommandException(Message.translation("server.commands.errors.playerNotInWorld")
/* 65 */             .param("option", "entity"));
/*    */       }
/*    */       
/* 68 */       Ref<EntityStore> entityRef = TargetUtil.getTargetEntity(playerRef, (ComponentAccessor)store);
/* 69 */       if (entityRef == null) {
/* 70 */         throw new GeneralCommandException(Message.translation("server.commands.errors.no_entity_in_view")
/* 71 */             .param("option", "entity"));
/*    */       }
/* 73 */       ref = entityRef;
/*    */     } 
/*    */     
/* 76 */     PatrolPathMarkerEntity marker = (PatrolPathMarkerEntity)store.getComponent(ref, PatrolPathMarkerEntity.getComponentType());
/* 77 */     if (marker == null) {
/* 78 */       context.sendMessage(Message.translation("server.general.entityNotFound")
/* 79 */           .param("id", ref.getIndex()));
/*    */       
/*    */       return;
/*    */     } 
/* 83 */     marker.setObservationAngle(angleDegrees.floatValue() * 0.017453292F);
/* 84 */     marker.markNeedsSave();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\PrefabPathUpdateObservationAngleCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */