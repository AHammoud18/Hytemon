/*    */ package com.hypixel.hytale.builtin.mounts.interactions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.mounts.MountedByComponent;
/*    */ import com.hypixel.hytale.builtin.mounts.MountedComponent;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.protocol.InteractionState;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.MountController;
/*    */ import com.hypixel.hytale.protocol.Vector3f;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MountInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/*    */   public static final BuilderCodec<MountInteraction> CODEC;
/*    */   
/*    */   static {
/* 38 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(MountInteraction.class, MountInteraction::new, SimpleInstantInteraction.CODEC).appendInherited(new KeyedCodec("AttachmentOffset", (Codec)ProtocolCodecs.VECTOR3F), (o, v) -> o.attachmentOffset.assign(v.x, v.y, v.z), o -> new Vector3f(o.attachmentOffset.x, o.attachmentOffset.y, o.attachmentOffset.z), (o, p) -> o.attachmentOffset = p.attachmentOffset).add()).appendInherited(new KeyedCodec("Controller", (Codec)new EnumCodec(MountController.class)), (o, v) -> o.controller = v, o -> o.controller, (o, p) -> o.controller = p.controller).addValidator(Validators.nonNull()).add()).build();
/*    */   }
/* 40 */   private Vector3f attachmentOffset = new Vector3f(0.0F, 0.0F, 0.0F);
/*    */   
/*    */   private MountController controller;
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 45 */     Ref<EntityStore> target = context.getTargetEntity();
/* 46 */     if (target == null) {
/* 47 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 51 */     Ref<EntityStore> self = context.getEntity();
/* 52 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*    */     
/* 54 */     MountedComponent mounted = (MountedComponent)commandBuffer.getComponent(self, MountedComponent.getComponentType());
/* 55 */     if (mounted != null) {
/* 56 */       commandBuffer.removeComponent(self, MountedComponent.getComponentType());
/* 57 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 61 */     MountedByComponent mountedBy = (MountedByComponent)commandBuffer.getComponent(target, MountedByComponent.getComponentType());
/*    */     
/* 63 */     if (mountedBy != null && !mountedBy.getPassengers().isEmpty()) {
/*    */ 
/*    */       
/* 66 */       (context.getState()).state = InteractionState.Failed;
/*    */       
/*    */       return;
/*    */     } 
/* 70 */     commandBuffer.addComponent(self, MountedComponent.getComponentType(), (Component)new MountedComponent(target, this.attachmentOffset, this.controller));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mounts\interactions\MountInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */