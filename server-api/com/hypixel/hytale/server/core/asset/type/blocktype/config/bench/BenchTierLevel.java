/*    */ package com.hypixel.hytale.server.core.asset.type.blocktype.config.bench;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.BenchTierLevel;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.util.function.Supplier;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BenchTierLevel
/*    */   implements NetworkSerializable<BenchTierLevel>
/*    */ {
/*    */   public static final BuilderCodec<BenchTierLevel> CODEC;
/*    */   protected BenchUpgradeRequirement upgradeRequirement;
/*    */   protected float craftingTimeReductionModifier;
/*    */   protected int extraInputSlot;
/*    */   protected int extraOutputSlot;
/*    */   
/*    */   static {
/* 37 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BenchTierLevel.class, BenchTierLevel::new).append(new KeyedCodec("UpgradeRequirement", (Codec)BenchUpgradeRequirement.CODEC), (tier, d) -> tier.upgradeRequirement = d, tier -> tier.upgradeRequirement).add()).append(new KeyedCodec("CraftingTimeReductionModifier", (Codec)Codec.DOUBLE), (tier, d) -> tier.craftingTimeReductionModifier = d.floatValue(), tier -> Double.valueOf(tier.craftingTimeReductionModifier)).addValidator(Validators.range(Double.valueOf(0.0D), Double.valueOf(1.0D))).add()).append(new KeyedCodec("ExtraInputSlot", (Codec)Codec.INTEGER), (tier, d) -> tier.extraInputSlot = d.intValue(), tier -> Integer.valueOf(tier.extraInputSlot)).addValidator(Validators.min(Integer.valueOf(0))).add()).append(new KeyedCodec("ExtraOutputSlot", (Codec)Codec.INTEGER), (tier, d) -> tier.extraOutputSlot = d.intValue(), tier -> Integer.valueOf(tier.extraOutputSlot)).addValidator(Validators.min(Integer.valueOf(0))).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BenchTierLevel(BenchUpgradeRequirement upgradeRequirement, float craftingTimeReductionModifier, int extraInputSlot, int extraOutputSlot) {
/* 45 */     this.upgradeRequirement = upgradeRequirement;
/* 46 */     this.craftingTimeReductionModifier = craftingTimeReductionModifier;
/* 47 */     this.extraInputSlot = extraInputSlot;
/* 48 */     this.extraOutputSlot = extraOutputSlot;
/*    */   }
/*    */   
/*    */   protected BenchTierLevel() {}
/*    */   
/*    */   public float getCraftingTimeReductionModifier() {
/* 54 */     return this.craftingTimeReductionModifier;
/*    */   } public BenchUpgradeRequirement getUpgradeRequirement() {
/* 56 */     return this.upgradeRequirement;
/*    */   } public int getExtraInputSlot() {
/* 58 */     return this.extraInputSlot;
/*    */   } public int getExtraOutputSlot() {
/* 60 */     return this.extraOutputSlot;
/*    */   }
/*    */   
/*    */   public BenchTierLevel toPacket() {
/* 64 */     BenchTierLevel packet = new BenchTierLevel();
/*    */     
/* 66 */     if (this.upgradeRequirement != null) {
/* 67 */       packet.benchUpgradeRequirement = this.upgradeRequirement.toPacket();
/*    */     }
/* 69 */     packet.craftingTimeReductionModifier = this.craftingTimeReductionModifier;
/* 70 */     packet.extraInputSlot = this.extraInputSlot;
/* 71 */     packet.extraOutputSlot = this.extraOutputSlot;
/*    */     
/* 73 */     return packet;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\bench\BenchTierLevel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */