/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.protocol.ColorLight;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DynamicLight
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static ComponentType<EntityStore, DynamicLight> getComponentType() {
/* 14 */     return EntityModule.get().getDynamicLightComponentType();
/*    */   }
/*    */   
/* 17 */   private ColorLight colorLight = new ColorLight();
/*    */ 
/*    */   
/*    */   private boolean isNetworkOutdated = true;
/*    */ 
/*    */   
/*    */   public DynamicLight(ColorLight colorLight) {
/* 24 */     this.colorLight = colorLight;
/*    */   }
/*    */   
/*    */   public ColorLight getColorLight() {
/* 28 */     return this.colorLight;
/*    */   }
/*    */   
/*    */   public void setColorLight(ColorLight colorLight) {
/* 32 */     this.colorLight = colorLight;
/* 33 */     this.isNetworkOutdated = true;
/*    */   }
/*    */   
/*    */   public boolean consumeNetworkOutdated() {
/* 37 */     boolean temp = this.isNetworkOutdated;
/* 38 */     this.isNetworkOutdated = false;
/* 39 */     return temp;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 45 */     return new DynamicLight(new ColorLight(this.colorLight));
/*    */   }
/*    */   
/*    */   public DynamicLight() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\DynamicLight.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */