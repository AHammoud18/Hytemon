/*    */ package com.hypixel.hytale.server.core.asset.type.gameplay;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
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
/*    */ 
/*    */ 
/*    */ public class ItemDurabilityConfig
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<ItemDurabilityConfig> CODEC;
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ItemDurabilityConfig.class, ItemDurabilityConfig::new).appendInherited(new KeyedCodec("BrokenPenalties", (Codec)BrokenPenalties.CODEC), (itemDurabilityConfig, itemTypeDoubleMap) -> itemDurabilityConfig.brokenPenalties = itemTypeDoubleMap, itemDurabilityConfig -> itemDurabilityConfig.brokenPenalties, (o, p) -> o.brokenPenalties = p.brokenPenalties).addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/* 32 */   protected BrokenPenalties brokenPenalties = BrokenPenalties.DEFAULT;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public BrokenPenalties getBrokenPenalties() {
/* 37 */     return this.brokenPenalties;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\ItemDurabilityConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */