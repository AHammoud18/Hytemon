/*    */ package com.hypixel.hytale.builtin.ambience.components;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.ambience.AmbiencePlugin;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class AmbientEmitterComponent
/*    */   implements Component<EntityStore> {
/*    */   public static final BuilderCodec<AmbientEmitterComponent> CODEC;
/*    */   
/*    */   public static ComponentType<EntityStore, AmbientEmitterComponent> getComponentType() {
/* 19 */     return AmbiencePlugin.get().getAmbientEmitterComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   private String soundEventId;
/*    */   
/*    */   private Ref<EntityStore> spawnedEmitter;
/*    */   
/*    */   static {
/* 28 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(AmbientEmitterComponent.class, AmbientEmitterComponent::new).append(new KeyedCodec("SoundEventId", (Codec)Codec.STRING), (emitter, s) -> emitter.soundEventId = s, emitter -> emitter.soundEventId).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSoundEventId() {
/* 34 */     return this.soundEventId;
/*    */   }
/*    */   
/*    */   public void setSoundEventId(String soundEventId) {
/* 38 */     this.soundEventId = soundEventId;
/*    */   }
/*    */   
/*    */   public Ref<EntityStore> getSpawnedEmitter() {
/* 42 */     return this.spawnedEmitter;
/*    */   }
/*    */   
/*    */   public void setSpawnedEmitter(Ref<EntityStore> spawnedEmitter) {
/* 46 */     this.spawnedEmitter = spawnedEmitter;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Component<EntityStore> clone() {
/* 52 */     AmbientEmitterComponent clone = new AmbientEmitterComponent();
/* 53 */     clone.soundEventId = this.soundEventId;
/* 54 */     return clone;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\ambience\components\AmbientEmitterComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */