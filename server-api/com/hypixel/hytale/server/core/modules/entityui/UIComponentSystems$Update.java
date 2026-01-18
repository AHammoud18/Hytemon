/*    */ package com.hypixel.hytale.server.core.modules.entityui;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.SystemGroup;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*    */ import com.hypixel.hytale.protocol.ComponentUpdateType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Update
/*    */   extends EntityTickingSystem<EntityStore>
/*    */ {
/*    */   private final ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType;
/*    */   private final ComponentType<EntityStore, UIComponentList> uiComponentListComponentType;
/*    */   @Nonnull
/*    */   private final Query<EntityStore> query;
/*    */   
/*    */   public Update(ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType, ComponentType<EntityStore, UIComponentList> uiComponentListComponentType) {
/* 53 */     this.visibleComponentType = visibleComponentType;
/* 54 */     this.uiComponentListComponentType = uiComponentListComponentType;
/* 55 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)visibleComponentType, (Query)uiComponentListComponentType });
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public SystemGroup<EntityStore> getGroup() {
/* 61 */     return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 67 */     return this.query;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 72 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 77 */     EntityTrackerSystems.Visible visible = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.visibleComponentType);
/* 78 */     UIComponentList uiComponentList = (UIComponentList)archetypeChunk.getComponent(index, this.uiComponentListComponentType);
/*    */     
/* 80 */     if (!visible.newlyVisibleTo.isEmpty()) {
/* 81 */       queueUpdatesFor(archetypeChunk.getReferenceTo(index), uiComponentList, visible.newlyVisibleTo);
/*    */     }
/*    */   }
/*    */   
/*    */   private static void queueUpdatesFor(Ref<EntityStore> ref, @Nonnull UIComponentList uiComponentList, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo) {
/* 86 */     ComponentUpdate update = new ComponentUpdate();
/* 87 */     update.type = ComponentUpdateType.UIComponents;
/* 88 */     update.entityUIComponents = uiComponentList.getComponentIds();
/*    */     
/* 90 */     for (EntityTrackerSystems.EntityViewer viewer : visibleTo.values())
/* 91 */       viewer.queueUpdate(ref, update); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entityui\UIComponentSystems$Update.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */