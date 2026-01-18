/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.selector;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.EntityMatcher;
/*    */ import com.hypixel.hytale.protocol.EntityMatcherType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.Invulnerable;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.SelectInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VulnerableMatcher
/*    */   extends SelectInteraction.EntityMatcher
/*    */ {
/*    */   @Nonnull
/* 23 */   public static final BuilderCodec<VulnerableMatcher> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(VulnerableMatcher.class, VulnerableMatcher::new, BASE_CODEC)
/* 24 */     .documentation("Used to match any entity that is attackable"))
/* 25 */     .build();
/*    */ 
/*    */   
/*    */   public boolean test0(Ref<EntityStore> attacker, @Nonnull Ref<EntityStore> target, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 29 */     boolean invulnerable = commandBuffer.getArchetype(target).contains(Invulnerable.getComponentType());
/* 30 */     return !invulnerable;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public EntityMatcher toPacket() {
/* 36 */     EntityMatcher packet = super.toPacket();
/* 37 */     packet.type = EntityMatcherType.VulnerableMatcher;
/* 38 */     return packet;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\selector\VulnerableMatcher.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */