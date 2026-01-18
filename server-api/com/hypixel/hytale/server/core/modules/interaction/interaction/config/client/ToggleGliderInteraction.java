/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.protocol.Interaction;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ToggleGliderInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/* 15 */   public static final BuilderCodec<ToggleGliderInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ToggleGliderInteraction.class, ToggleGliderInteraction::new, SimpleInstantInteraction.CODEC)
/* 16 */     .documentation("Toggles Glider movement for the player."))
/* 17 */     .build();
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected Interaction generatePacket() {
/* 25 */     return (Interaction)new com.hypixel.hytale.protocol.ToggleGliderInteraction();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 31 */     return "ToggleGliderInteraction{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\ToggleGliderInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */