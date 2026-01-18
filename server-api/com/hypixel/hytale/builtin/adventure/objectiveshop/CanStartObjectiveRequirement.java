/*    */ package com.hypixel.hytale.builtin.adventure.objectiveshop;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceRequirement;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CanStartObjectiveRequirement
/*    */   extends ChoiceRequirement
/*    */ {
/*    */   public static final BuilderCodec<CanStartObjectiveRequirement> CODEC;
/*    */   protected String objectiveId;
/*    */   
/*    */   static {
/* 23 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(CanStartObjectiveRequirement.class, CanStartObjectiveRequirement::new, ChoiceRequirement.BASE_CODEC).append(new KeyedCodec("ObjectiveId", (Codec)Codec.STRING), (canStartObjectiveRequirement, s) -> canStartObjectiveRequirement.objectiveId = s, canStartObjectiveRequirement -> canStartObjectiveRequirement.objectiveId).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public CanStartObjectiveRequirement(String objectiveId) {
/* 28 */     this.objectiveId = objectiveId;
/*    */   }
/*    */ 
/*    */   
/*    */   protected CanStartObjectiveRequirement() {}
/*    */ 
/*    */   
/*    */   public boolean canFulfillRequirement(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef) {
/* 36 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 37 */     if (playerComponent == null) return false;
/*    */     
/* 39 */     return ObjectivePlugin.get().canPlayerDoObjective(playerComponent, this.objectiveId);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 45 */     return "CanStartObjectiveRequirement{objectiveId='" + this.objectiveId + "'} " + super
/*    */       
/* 47 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectiveshop\CanStartObjectiveRequirement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */