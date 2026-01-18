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
/*    */ public class RotateObjectComponent
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<RotateObjectComponent> CODEC;
/*    */   private float rotationSpeed;
/*    */   
/*    */   @Nonnull
/*    */   public static ComponentType<EntityStore, RotateObjectComponent> getComponentType() {
/* 23 */     return EntityModule.get().getRotateObjectComponentType();
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
/*    */ 
/*    */   
/*    */   static {
/* 37 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(RotateObjectComponent.class, RotateObjectComponent::new).append(new KeyedCodec("RotationSpeed", (Codec)Codec.FLOAT), (c, f) -> c.rotationSpeed = f.floatValue(), c -> Float.valueOf(c.rotationSpeed)).add()).build();
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
/*    */   public RotateObjectComponent() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RotateObjectComponent(float rotationSpeed) {
/* 58 */     this.rotationSpeed = rotationSpeed;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 64 */     return new RotateObjectComponent(this.rotationSpeed);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationSpeed(float rotationSpeed) {
/* 73 */     this.rotationSpeed = rotationSpeed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getRotationSpeed() {
/* 80 */     return this.rotationSpeed;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\RotateObjectComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */