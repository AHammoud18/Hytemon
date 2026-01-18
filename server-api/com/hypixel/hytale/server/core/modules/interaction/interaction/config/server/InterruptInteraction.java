/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.util.InteractionTarget;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InterruptInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/*    */   public static final BuilderCodec<InterruptInteraction> CODEC;
/*    */   
/*    */   static {
/* 61 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(InterruptInteraction.class, InterruptInteraction::new, SimpleInstantInteraction.CODEC).documentation("Interrupts interactions on the target entity.")).appendInherited(new KeyedCodec("Entity", (Codec)InteractionTarget.CODEC), (o, i) -> o.entityTarget = i, o -> o.entityTarget, (o, p) -> o.entityTarget = p.entityTarget).documentation("The entity to target for this interaction.").addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("InterruptTypes", (Codec)InteractionModule.INTERACTION_TYPE_SET_CODEC), (o, i) -> o.interruptTypes = i, o -> o.interruptTypes, (o, p) -> o.interruptTypes = p.interruptTypes).documentation("A set of interaction types that this interrupt will cancel").add()).appendInherited(new KeyedCodec("RequiredTag", (Codec)Codec.STRING), (o, i) -> o.requiredTag = i, o -> o.requiredTag, (o, p) -> o.requiredTag = p.requiredTag).documentation("The tag that the root interaction of an active interaction chain must have to be interrupted.\nIf not set then no tag is required.").add()).appendInherited(new KeyedCodec("ExcludedTag", (Codec)Codec.STRING), (o, i) -> o.excludedTag = i, o -> o.excludedTag, (o, p) -> o.excludedTag = p.excludedTag).documentation("The tag that if the root interaction of an active interaction chain has then it will not be interrupted.").add()).afterDecode(o -> { if (o.requiredTag != null) o.requiredTagIndex = AssetRegistry.getOrCreateTagIndex(o.requiredTag);  if (o.excludedTag != null) o.excludedTagIndex = AssetRegistry.getOrCreateTagIndex(o.excludedTag);  })).build();
/*    */   }
/* 63 */   private InteractionTarget entityTarget = InteractionTarget.USER;
/*    */   @Nullable
/*    */   private Set<InteractionType> interruptTypes;
/*    */   @Nullable
/*    */   private String requiredTag;
/* 68 */   private int requiredTagIndex = Integer.MIN_VALUE;
/*    */   @Nullable
/*    */   private String excludedTag;
/* 71 */   private int excludedTagIndex = Integer.MIN_VALUE;
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 75 */     Ref<EntityStore> ref = context.getEntity();
/* 76 */     Ref<EntityStore> targetRef = this.entityTarget.getEntity(context, ref);
/* 77 */     if (targetRef == null)
/*    */       return; 
/* 79 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 80 */     InteractionManager interactionManagerComponent = (InteractionManager)commandBuffer.getComponent(targetRef, InteractionModule.get().getInteractionManagerComponent());
/* 81 */     if (interactionManagerComponent == null)
/*    */       return; 
/* 83 */     for (ObjectIterator<InteractionChain> objectIterator = interactionManagerComponent.getChains().values().iterator(); objectIterator.hasNext(); ) { InteractionChain interactionChain = objectIterator.next();
/* 84 */       if (this.interruptTypes != null && !this.interruptTypes.contains(interactionChain.getType()))
/* 85 */         continue;  IntSet tags = interactionChain.getInitialRootInteraction().getData().getExpandedTagIndexes();
/* 86 */       if ((this.requiredTag != null && !tags.contains(this.requiredTagIndex)) || (
/* 87 */         this.excludedTag != null && tags.contains(this.excludedTagIndex)))
/*    */         continue; 
/* 89 */       interactionManagerComponent.cancelChains(interactionChain); }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\InterruptInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */