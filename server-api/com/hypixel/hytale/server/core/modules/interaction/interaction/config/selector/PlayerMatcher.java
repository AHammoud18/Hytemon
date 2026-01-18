/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.selector;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.EntityMatcher;
/*    */ import com.hypixel.hytale.protocol.EntityMatcherType;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.SelectInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PlayerMatcher extends SelectInteraction.EntityMatcher {
/* 15 */   public static final BuilderCodec<PlayerMatcher> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PlayerMatcher.class, PlayerMatcher::new, BASE_CODEC)
/* 16 */     .documentation("Matches only players"))
/* 17 */     .build();
/*    */ 
/*    */   
/*    */   public boolean test0(Ref<EntityStore> attacker, @Nonnull Ref<EntityStore> target, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 21 */     return commandBuffer.getArchetype(target).contains(Player.getComponentType());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public EntityMatcher toPacket() {
/* 27 */     EntityMatcher packet = super.toPacket();
/* 28 */     packet.type = EntityMatcherType.Player;
/* 29 */     return packet;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\selector\PlayerMatcher.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */