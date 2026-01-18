/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PositionDataComponent
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static ComponentType<EntityStore, PositionDataComponent> getComponentType() {
/* 16 */     return EntityModule.get().getPositionDataComponentType();
/*    */   }
/*    */   
/* 19 */   private int insideBlockTypeId = 0;
/* 20 */   private int standingOnBlockTypeId = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PositionDataComponent(int insideBlockTypeId, int standingOnBlockTypeId) {
/* 28 */     this.insideBlockTypeId = insideBlockTypeId;
/* 29 */     this.standingOnBlockTypeId = standingOnBlockTypeId;
/*    */   }
/*    */   
/*    */   public int getInsideBlockTypeId() {
/* 33 */     return this.insideBlockTypeId;
/*    */   }
/*    */   
/*    */   public void setInsideBlockTypeId(int insideBlockTypeId) {
/* 37 */     this.insideBlockTypeId = insideBlockTypeId;
/*    */   }
/*    */   
/*    */   public int getStandingOnBlockTypeId() {
/* 41 */     return this.standingOnBlockTypeId;
/*    */   }
/*    */   
/*    */   public void setStandingOnBlockTypeId(int standingOnBlockTypeId) {
/* 45 */     this.standingOnBlockTypeId = standingOnBlockTypeId;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 52 */     return new PositionDataComponent(this.insideBlockTypeId, this.standingOnBlockTypeId);
/*    */   }
/*    */   
/*    */   public PositionDataComponent() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\PositionDataComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */