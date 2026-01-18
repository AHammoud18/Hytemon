/*    */ package com.hypixel.hytale.builtin.adventure.objectives.blockstates;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.events.TreasureChestOpeningEvent;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.event.IBaseEvent;
/*    */ import com.hypixel.hytale.event.IEventDispatcher;
/*    */ import com.hypixel.hytale.server.core.HytaleServer;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*    */ import com.hypixel.hytale.server.core.universe.world.meta.state.BreakValidatedBlockState;
/*    */ import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TreasureChestState
/*    */   extends ItemContainerState
/*    */   implements BreakValidatedBlockState
/*    */ {
/*    */   public static final BuilderCodec<TreasureChestState> CODEC;
/*    */   protected UUID objectiveUUID;
/*    */   protected UUID chestUUID;
/*    */   protected boolean opened;
/*    */   
/*    */   static {
/* 41 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(TreasureChestState.class, TreasureChestState::new, BlockState.BASE_CODEC).append(new KeyedCodec("ObjectiveUUID", (Codec)Codec.UUID_BINARY), (treasureChestState, uuid) -> treasureChestState.objectiveUUID = uuid, treasureChestState -> treasureChestState.objectiveUUID).add()).append(new KeyedCodec("ChestUUID", (Codec)Codec.UUID_BINARY), (treasureChestState, uuid) -> treasureChestState.chestUUID = uuid, treasureChestState -> treasureChestState.chestUUID).add()).append(new KeyedCodec("Opened", (Codec)Codec.BOOLEAN), (treasureChestState, aBoolean) -> treasureChestState.opened = aBoolean.booleanValue(), treasureChestState -> Boolean.valueOf(treasureChestState.opened)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canOpen(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 49 */     if (!this.opened) {
/* 50 */       UUIDComponent uuidComponent = (UUIDComponent)componentAccessor.getComponent(ref, UUIDComponent.getComponentType());
/* 51 */       assert uuidComponent != null;
/*    */       
/* 53 */       Objective objective = ObjectivePlugin.get().getObjectiveDataStore().getObjective(this.objectiveUUID);
/* 54 */       return (objective != null && objective.getActivePlayerUUIDs().contains(uuidComponent.getUuid()));
/*    */     } 
/* 56 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canDestroy(@Nonnull Ref<EntityStore> playerRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 61 */     return this.opened;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onOpen(@Nonnull Ref<EntityStore> ref, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 66 */     IEventDispatcher<TreasureChestOpeningEvent, TreasureChestOpeningEvent> dispatcher = HytaleServer.get().getEventBus().dispatchFor(TreasureChestOpeningEvent.class, world.getName());
/* 67 */     if (dispatcher.hasListener()) {
/* 68 */       dispatcher.dispatch((IBaseEvent)new TreasureChestOpeningEvent(this.objectiveUUID, this.chestUUID, ref, store));
/*    */     }
/*    */     
/* 71 */     setOpened(true);
/*    */   }
/*    */   
/*    */   public void setOpened(boolean opened) {
/* 75 */     this.opened = opened;
/*    */   }
/*    */   
/*    */   public void setObjectiveData(UUID objectiveUUID, UUID chestUUID, List<ItemStack> itemStacks) {
/* 79 */     this.objectiveUUID = objectiveUUID;
/* 80 */     this.chestUUID = chestUUID;
/* 81 */     this.itemContainer.addItemStacks(itemStacks);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\blockstates\TreasureChestState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */