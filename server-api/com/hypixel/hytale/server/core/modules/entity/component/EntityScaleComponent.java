/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityScaleComponent
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static final BuilderCodec<EntityScaleComponent> CODEC;
/*    */   
/*    */   static {
/* 23 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(EntityScaleComponent.class, EntityScaleComponent::new).addField(new KeyedCodec("Scale", (Codec)Codec.FLOAT), (o, scale) -> o.scale = scale.floatValue(), o -> Float.valueOf(o.scale))).build();
/*    */   }
/*    */   public static ComponentType<EntityStore, EntityScaleComponent> getComponentType() {
/* 26 */     return EntityModule.get().getEntityScaleComponentType();
/*    */   }
/*    */   
/* 29 */   private float scale = 1.0F;
/*    */ 
/*    */   
/*    */   private boolean isNetworkOutdated = true;
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityScaleComponent(float scale) {
/* 37 */     this.scale = scale;
/*    */   }
/*    */   
/*    */   public float getScale() {
/* 41 */     return this.scale;
/*    */   }
/*    */   
/*    */   public void setScale(float scale) {
/* 45 */     this.scale = scale;
/* 46 */     this.isNetworkOutdated = true;
/*    */   }
/*    */   
/*    */   public boolean consumeNetworkOutdated() {
/* 50 */     boolean temp = this.isNetworkOutdated;
/* 51 */     this.isNetworkOutdated = false;
/* 52 */     return temp;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 58 */     return new EntityScaleComponent(this.scale);
/*    */   }
/*    */   
/*    */   private EntityScaleComponent() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\EntityScaleComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */