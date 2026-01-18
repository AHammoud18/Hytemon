/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelComponent
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   private final Model model;
/*    */   
/*    */   public static ComponentType<EntityStore, ModelComponent> getComponentType() {
/* 18 */     return EntityModule.get().getModelComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean isNetworkOutdated = true;
/*    */ 
/*    */   
/*    */   public ModelComponent(Model model) {
/* 26 */     this.model = model;
/*    */   }
/*    */   
/*    */   public Model getModel() {
/* 30 */     return this.model;
/*    */   }
/*    */   
/*    */   public boolean consumeNetworkOutdated() {
/* 34 */     boolean temp = this.isNetworkOutdated;
/* 35 */     this.isNetworkOutdated = false;
/* 36 */     return temp;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 43 */     return new ModelComponent(this.model);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\ModelComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */