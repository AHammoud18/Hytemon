/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.selector;
/*    */ 
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.function.consumer.TriIntConsumer;
/*    */ import com.hypixel.hytale.math.vector.Vector4d;
/*    */ import com.hypixel.hytale.protocol.SelectedHitEntity;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class ClientSourcedSelector
/*    */   implements Selector
/*    */ {
/*    */   private final Selector parent;
/*    */   private final InteractionContext context;
/*    */   
/*    */   public ClientSourcedSelector(Selector parent, InteractionContext context) {
/* 24 */     this.parent = parent;
/* 25 */     this.context = context;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull Ref<EntityStore> ref, float time, float runTime) {
/* 30 */     this.parent.tick(commandBuffer, ref, time, runTime);
/*    */   }
/*    */ 
/*    */   
/*    */   public void selectTargetEntities(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull Ref<EntityStore> ref, @Nonnull BiConsumer<Ref<EntityStore>, Vector4d> consumer, Predicate<Ref<EntityStore>> filter) {
/* 35 */     SelectedHitEntity[] hits = (this.context.getClientState()).hitEntities;
/* 36 */     if (hits == null)
/* 37 */       return;  EntityStore store = (EntityStore)commandBuffer.getStore().getExternalData();
/* 38 */     for (SelectedHitEntity info : hits) {
/* 39 */       Ref<EntityStore> targetRef = store.getRefFromNetworkId(info.networkId);
/* 40 */       if (targetRef != null) {
/* 41 */         consumer.accept(targetRef, new Vector4d(info.hitLocation.x, info.hitLocation.y, info.hitLocation.z, 0.0D));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void selectTargetBlocks(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull Ref<EntityStore> ref, @Nonnull TriIntConsumer consumer) {
/* 47 */     this.parent.selectTargetBlocks(commandBuffer, ref, consumer);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\selector\ClientSourcedSelector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */