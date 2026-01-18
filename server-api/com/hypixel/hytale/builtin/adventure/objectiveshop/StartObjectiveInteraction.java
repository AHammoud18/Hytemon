/*    */ package com.hypixel.hytale.builtin.adventure.objectiveshop;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveAsset;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.HashSet;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class StartObjectiveInteraction
/*    */   extends ChoiceInteraction {
/*    */   public static final BuilderCodec<StartObjectiveInteraction> CODEC;
/*    */   protected String objectiveId;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(StartObjectiveInteraction.class, StartObjectiveInteraction::new, ChoiceInteraction.BASE_CODEC).append(new KeyedCodec("ObjectiveId", (Codec)Codec.STRING), (startObjectiveInteraction, s) -> startObjectiveInteraction.objectiveId = s, startObjectiveInteraction -> startObjectiveInteraction.objectiveId).addValidator(ObjectiveAsset.VALIDATOR_CACHE.getValidator()).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public StartObjectiveInteraction(String objectiveId) {
/* 30 */     this.objectiveId = objectiveId;
/*    */   }
/*    */ 
/*    */   
/*    */   protected StartObjectiveInteraction() {}
/*    */   
/*    */   public String getObjectiveId() {
/* 37 */     return this.objectiveId;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef) {
/* 42 */     HashSet<UUID> playerSet = new HashSet<>();
/* 43 */     playerSet.add(playerRef.getUuid());
/*    */     
/* 45 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 46 */     ObjectivePlugin.get().startObjective(this.objectiveId, playerSet, world.getWorldConfig().getUuid(), null, store);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 52 */     return "StartObjectiveInteraction{objectiveId='" + this.objectiveId + "'} " + super
/*    */       
/* 54 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectiveshop\StartObjectiveInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */