/*    */ package com.hypixel.hytale.server.core.entity.entities.player.pages.choices;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class ChoiceInteraction
/*    */ {
/* 13 */   public static final CodecMapCodec<ChoiceInteraction> CODEC = new CodecMapCodec("Type");
/* 14 */   public static final BuilderCodec<ChoiceInteraction> BASE_CODEC = BuilderCodec.abstractBuilder(ChoiceInteraction.class)
/* 15 */     .build();
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void run(@Nonnull Store<EntityStore> paramStore, @Nonnull Ref<EntityStore> paramRef, @Nonnull PlayerRef paramPlayerRef);
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 25 */     return "ChoiceInteraction{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\pages\choices\ChoiceInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */