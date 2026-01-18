/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ 
/*    */ 
/*    */ public class NewSpawnComponent
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   private float newSpawnWindow;
/*    */   
/*    */   public static ComponentType<EntityStore, NewSpawnComponent> getComponentType() {
/* 15 */     return EntityModule.get().getNewSpawnComponentType();
/*    */   }
/*    */   
/*    */   public NewSpawnComponent(float newSpawnWindow) {
/* 19 */     this.newSpawnWindow = newSpawnWindow;
/*    */   }
/*    */   
/*    */   public boolean newSpawnWindowPassed(float dt) {
/* 23 */     return ((this.newSpawnWindow -= dt) <= 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public Component<EntityStore> clone() {
/* 28 */     return new NewSpawnComponent(this.newSpawnWindow);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\NewSpawnComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */