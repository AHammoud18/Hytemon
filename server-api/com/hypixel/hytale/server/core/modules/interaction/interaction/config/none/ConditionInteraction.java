/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.none;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.MovementStates;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConditionInteraction
/*     */   extends SimpleInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ConditionInteraction> CODEC;
/*     */   @Nullable
/*     */   private GameMode requiredGameMode;
/*     */   @Nullable
/*     */   private Boolean jumping;
/*     */   @Nullable
/*     */   private Boolean swimming;
/*     */   @Nullable
/*     */   private Boolean crouching;
/*     */   @Nullable
/*     */   private Boolean running;
/*     */   @Nullable
/*     */   private Boolean flying;
/*     */   
/*     */   static {
/*  71 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ConditionInteraction.class, ConditionInteraction::new, SimpleInteraction.CODEC).documentation("An interaction that is successful if the given conditions are met.")).appendInherited(new KeyedCodec("RequiredGameMode", (Codec)ProtocolCodecs.GAMEMODE), (interaction, s) -> interaction.requiredGameMode = s, interaction -> interaction.requiredGameMode, (interaction, parent) -> interaction.requiredGameMode = parent.requiredGameMode).add()).appendInherited(new KeyedCodec("Jumping", (Codec)Codec.BOOLEAN), (interaction, s) -> interaction.jumping = s, interaction -> interaction.jumping, (interaction, parent) -> interaction.jumping = parent.jumping).add()).appendInherited(new KeyedCodec("Swimming", (Codec)Codec.BOOLEAN), (interaction, s) -> interaction.swimming = s, interaction -> interaction.swimming, (interaction, parent) -> interaction.swimming = parent.swimming).add()).appendInherited(new KeyedCodec("Crouching", (Codec)Codec.BOOLEAN), (interaction, s) -> interaction.crouching = s, interaction -> interaction.crouching, (interaction, parent) -> interaction.crouching = parent.crouching).add()).appendInherited(new KeyedCodec("Running", (Codec)Codec.BOOLEAN), (interaction, s) -> interaction.running = s, interaction -> interaction.running, (interaction, parent) -> interaction.running = parent.running).add()).appendInherited(new KeyedCodec("Flying", (Codec)Codec.BOOLEAN), (interaction, s) -> interaction.flying = s, interaction -> interaction.flying, (interaction, parent) -> interaction.flying = parent.flying).documentation("Whether the entity can be flying.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 111 */     boolean success = true;
/*     */     
/* 113 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 114 */     assert commandBuffer != null;
/*     */     
/* 116 */     Ref<EntityStore> ref = context.getEntity();
/* 117 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*     */ 
/*     */     
/* 120 */     if (this.requiredGameMode != null && playerComponent != null && this.requiredGameMode != playerComponent.getGameMode()) {
/* 121 */       success = false;
/*     */     }
/*     */     
/* 124 */     MovementStatesComponent movementStatesComponent = (MovementStatesComponent)commandBuffer.getComponent(ref, MovementStatesComponent.getComponentType());
/* 125 */     assert movementStatesComponent != null;
/*     */     
/* 127 */     MovementStates movementStates = movementStatesComponent.getMovementStates();
/*     */     
/* 129 */     if (this.jumping != null && this.jumping.booleanValue() != movementStates.jumping) {
/* 130 */       success = false;
/*     */     }
/*     */     
/* 133 */     if (this.swimming != null && this.swimming.booleanValue() != movementStates.swimming) {
/* 134 */       success = false;
/*     */     }
/*     */     
/* 137 */     if (this.crouching != null && this.crouching.booleanValue() != movementStates.crouching) {
/* 138 */       success = false;
/*     */     }
/*     */     
/* 141 */     if (this.running != null && this.running.booleanValue() != movementStates.running) {
/* 142 */       success = false;
/*     */     }
/*     */     
/* 145 */     if (this.flying != null && this.flying.booleanValue() != movementStates.flying) {
/* 146 */       success = false;
/*     */     }
/*     */     
/* 149 */     (context.getState()).state = success ? InteractionState.Finished : InteractionState.Failed;
/*     */     
/* 151 */     super.tick0(firstRun, time, type, context, cooldownHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 157 */     return (Interaction)new com.hypixel.hytale.protocol.ConditionInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 162 */     super.configurePacket(packet);
/* 163 */     com.hypixel.hytale.protocol.ConditionInteraction p = (com.hypixel.hytale.protocol.ConditionInteraction)packet;
/* 164 */     p.requiredGameMode = this.requiredGameMode;
/* 165 */     p.jumping = this.jumping;
/* 166 */     p.swimming = this.swimming;
/* 167 */     p.crouching = this.crouching;
/* 168 */     p.running = this.running;
/* 169 */     p.flying = this.flying;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 175 */     return "ConditionInteraction{requiredGameMode=" + String.valueOf(this.requiredGameMode) + ", jumping=" + this.jumping + ", swimming=" + this.swimming + ", crouching=" + this.crouching + ", running=" + this.running + ", flying=" + this.flying + "} " + super
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 182 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\none\ConditionInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */