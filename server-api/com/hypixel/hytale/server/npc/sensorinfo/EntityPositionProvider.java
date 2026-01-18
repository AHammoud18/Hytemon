/*    */ package com.hypixel.hytale.server.npc.sensorinfo;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.ParameterProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityPositionProvider
/*    */   extends PositionProvider
/*    */ {
/*    */   @Nullable
/*    */   private Ref<EntityStore> target;
/*    */   
/*    */   public EntityPositionProvider() {}
/*    */   
/*    */   public EntityPositionProvider(ParameterProvider parameterProvider) {
/* 26 */     super(parameterProvider);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear() {
/* 37 */     super.clear();
/* 38 */     this.target = null;
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
/*    */   public Ref<EntityStore> setTarget(@Nullable Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 50 */     return this.target = super.setTarget(ref, componentAccessor);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Ref<EntityStore> getTarget() {
/* 60 */     if (this.target == null) return null;
/*    */     
/* 62 */     Store<EntityStore> store = this.target.getStore();
/* 63 */     if (!this.target.isValid() || store.getArchetype(this.target).contains(DeathComponent.getComponentType())) clear(); 
/* 64 */     return this.target;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasPosition() {
/* 69 */     return (getTarget() != null);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\sensorinfo\EntityPositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */