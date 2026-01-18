/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.protocol.ColorLight;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PersistentDynamicLight
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static final BuilderCodec<PersistentDynamicLight> CODEC;
/*    */   private ColorLight colorLight;
/*    */   
/*    */   static {
/* 22 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PersistentDynamicLight.class, PersistentDynamicLight::new).addField(new KeyedCodec("Light", (Codec)ProtocolCodecs.COLOR_LIGHT), (o, light) -> o.colorLight = light, o -> o.colorLight)).build();
/*    */   }
/*    */   public static ComponentType<EntityStore, PersistentDynamicLight> getComponentType() {
/* 25 */     return EntityModule.get().getPersistentDynamicLightComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private PersistentDynamicLight() {}
/*    */ 
/*    */   
/*    */   public PersistentDynamicLight(ColorLight colorLight) {
/* 34 */     this.colorLight = colorLight;
/*    */   }
/*    */   
/*    */   public ColorLight getColorLight() {
/* 38 */     return this.colorLight;
/*    */   }
/*    */   
/*    */   public void setColorLight(ColorLight colorLight) {
/* 42 */     this.colorLight = colorLight;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 48 */     return new PersistentDynamicLight(new ColorLight(this.colorLight));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\PersistentDynamicLight.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */