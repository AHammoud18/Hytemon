/*    */ package com.hypixel.hytale.server.core.modules.entitystats.asset.modifier;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.asset.condition.Condition;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.time.Instant;
/*    */ import java.util.Arrays;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegeneratingModifier
/*    */ {
/*    */   public static final BuilderCodec<RegeneratingModifier> CODEC;
/*    */   protected Condition[] conditions;
/*    */   protected float amount;
/*    */   
/*    */   static {
/* 28 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RegeneratingModifier.class, RegeneratingModifier::new).append(new KeyedCodec("Conditions", (Codec)new ArrayCodec((Codec)Condition.CODEC, x$0 -> new Condition[x$0])), (condition, value) -> condition.conditions = value, condition -> condition.conditions).add()).append(new KeyedCodec("Amount", (Codec)Codec.FLOAT), (condition, value) -> condition.amount = value.floatValue(), condition -> Float.valueOf(condition.amount)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected RegeneratingModifier() {}
/*    */ 
/*    */   
/*    */   public RegeneratingModifier(Condition[] conditions, float amount) {
/* 37 */     this.conditions = conditions;
/* 38 */     this.amount = amount;
/*    */   }
/*    */   
/*    */   public float getModifier(ComponentAccessor<EntityStore> store, Ref<EntityStore> ref, Instant currentTime) {
/* 42 */     if (Condition.allConditionsMet(store, ref, currentTime, this.conditions)) {
/* 43 */       return this.amount;
/*    */     }
/* 45 */     return 1.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 52 */     return "RegeneratingModifier{conditions=" + Arrays.toString((Object[])this.conditions) + ", amount=" + this.amount + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\asset\modifier\RegeneratingModifier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */