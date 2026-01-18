/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.packets.interface_.Page;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class OpenPageInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/*    */   public static final BuilderCodec<OpenPageInteraction> CODEC;
/*    */   
/*    */   static {
/* 39 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(OpenPageInteraction.class, OpenPageInteraction::new, SimpleInstantInteraction.CODEC).documentation("Opens a predefined page.")).appendInherited(new KeyedCodec("Page", (Codec)new EnumCodec(Page.class)), (o, v) -> o.page = v, o -> o.page, (o, p) -> o.page = p.page).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("CanCloseThroughInteraction", (Codec)Codec.BOOLEAN), (o, v) -> o.canCloseThroughInteraction = v.booleanValue(), o -> Boolean.valueOf(o.canCloseThroughInteraction), (o, p) -> o.canCloseThroughInteraction = p.canCloseThroughInteraction).add()).build();
/*    */   }
/* 41 */   private static final Map<Page, PageUsageValidator> USAGE_VALIDATOR_MAP = new ConcurrentHashMap<>();
/*    */   
/*    */   protected Page page;
/*    */   
/*    */   protected boolean canCloseThroughInteraction;
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 48 */     Ref<EntityStore> ref = context.getEntity();
/* 49 */     Store<EntityStore> store = ref.getStore();
/* 50 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*    */     
/* 52 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/* 53 */     if (playerComponent == null)
/*    */       return; 
/* 55 */     PageUsageValidator validator = USAGE_VALIDATOR_MAP.get(this.page);
/* 56 */     if (validator != null && !validator.canUse(ref, playerComponent, context, (ComponentAccessor<EntityStore>)context.getCommandBuffer())) {
/*    */       return;
/*    */     }
/*    */     
/* 60 */     playerComponent.getPageManager().setPage(ref, store, this.page, this.canCloseThroughInteraction);
/*    */   }
/*    */   
/*    */   public static void registerUsageValidator(Page page, PageUsageValidator validator) {
/* 64 */     USAGE_VALIDATOR_MAP.put(page, validator);
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface PageUsageValidator {
/*    */     boolean canUse(Ref<EntityStore> param1Ref, Player param1Player, InteractionContext param1InteractionContext, ComponentAccessor<EntityStore> param1ComponentAccessor);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\OpenPageInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */