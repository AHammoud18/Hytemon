/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.math.shape.Box;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.DetailBox;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BoundingBox
/*    */   implements Component<EntityStore> {
/*    */   public static ComponentType<EntityStore, BoundingBox> getComponentType() {
/* 15 */     return EntityModule.get().getBoundingBoxComponentType();
/*    */   }
/*    */   
/* 18 */   private final Box boundingBox = new Box();
/*    */ 
/*    */   
/*    */   protected Map<String, DetailBox[]> detailBoxes;
/*    */ 
/*    */   
/*    */   public BoundingBox(@Nonnull Box boundingBox) {
/* 25 */     setBoundingBox(boundingBox);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Box getBoundingBox() {
/* 30 */     return this.boundingBox;
/*    */   }
/*    */   
/*    */   public void setBoundingBox(@Nonnull Box boundingBox) {
/* 34 */     this.boundingBox.assign(boundingBox);
/*    */   }
/*    */   
/*    */   public Map<String, DetailBox[]> getDetailBoxes() {
/* 38 */     return this.detailBoxes;
/*    */   }
/*    */   
/*    */   public void setDetailBoxes(Map<String, DetailBox[]> detailBoxes) {
/* 42 */     this.detailBoxes = detailBoxes;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 49 */     return new BoundingBox(this.boundingBox);
/*    */   }
/*    */   
/*    */   public BoundingBox() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\BoundingBox.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */