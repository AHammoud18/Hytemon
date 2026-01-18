/*    */ package com.hypixel.hytale.builtin.adventure.camera.interaction;
/*    */ 
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.server.core.asset.type.camera.CameraEffect;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class CameraShakeInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<CameraShakeInteraction> CODEC;
/*    */   @Nullable
/*    */   protected String effectId;
/*    */   
/*    */   static {
/* 39 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CameraShakeInteraction.class, CameraShakeInteraction::new, SimpleInstantInteraction.CODEC).documentation("Triggers a camera shake effect on use.")).appendInherited(new KeyedCodec("CameraEffect", CameraEffect.CHILD_ASSET_CODEC), (interaction, effect) -> interaction.effectId = effect, interaction -> interaction.effectId, (interaction, parent) -> interaction.effectId = parent.effectId).addValidator(Validators.nonNull()).addValidator(CameraEffect.VALIDATOR_CACHE.getValidator()).add()).afterDecode(cameraShakeInteraction -> { if (cameraShakeInteraction.effectId != null) cameraShakeInteraction.effectIndex = CameraEffect.getAssetMap().getIndex(cameraShakeInteraction.effectId);  })).build();
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
/* 50 */   protected int effectIndex = Integer.MIN_VALUE;
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 54 */     if (this.effectIndex == Integer.MIN_VALUE)
/*    */       return; 
/* 56 */     Ref<EntityStore> ref = context.getEntity();
/* 57 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 58 */     assert commandBuffer != null;
/*    */     
/* 60 */     PlayerRef playerRefComponent = (PlayerRef)commandBuffer.getComponent(ref, PlayerRef.getComponentType());
/* 61 */     if (playerRefComponent == null)
/*    */       return; 
/* 63 */     CameraEffect cameraShakeEffect = (CameraEffect)CameraEffect.getAssetMap().getAsset(this.effectIndex);
/* 64 */     if (cameraShakeEffect == null)
/*    */       return; 
/* 66 */     playerRefComponent.getPacketHandler().writeNoCache((Packet)cameraShakeEffect.createCameraShakePacket());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 72 */     return "CameraShakeInteraction{effectId='" + this.effectId + "', effectIndex=" + this.effectIndex + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\interaction\CameraShakeInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */