/*    */ package com.hypixel.hytale.server.core.asset.type.blocktype.config.bench;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.BenchUpgradeRequirement;
/*    */ import com.hypixel.hytale.server.core.inventory.MaterialQuantity;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.util.Arrays;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BenchUpgradeRequirement
/*    */   implements NetworkSerializable<BenchUpgradeRequirement>
/*    */ {
/*    */   public static final BuilderCodec<BenchUpgradeRequirement> CODEC;
/*    */   protected MaterialQuantity[] input;
/*    */   protected float timeSeconds;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BenchUpgradeRequirement.class, BenchUpgradeRequirement::new).append(new KeyedCodec("Material", (Codec)new ArrayCodec((Codec)MaterialQuantity.CODEC, x$0 -> new MaterialQuantity[x$0])), (benchUpgradeRequirement, objects) -> benchUpgradeRequirement.input = objects, benchUpgradeRequirement -> benchUpgradeRequirement.input).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("TimeSeconds", (Codec)Codec.DOUBLE), (benchUpgradeRequirement, d) -> benchUpgradeRequirement.timeSeconds = d.floatValue(), benchUpgradeRequirement -> Double.valueOf(benchUpgradeRequirement.timeSeconds)).addValidator(Validators.min(Double.valueOf(0.0D))).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BenchUpgradeRequirement(MaterialQuantity[] input, float timeSeconds) {
/* 32 */     this.input = input;
/* 33 */     this.timeSeconds = timeSeconds;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BenchUpgradeRequirement() {}
/*    */   
/*    */   public MaterialQuantity[] getInput() {
/* 40 */     return this.input;
/*    */   }
/*    */   
/*    */   public float getTimeSeconds() {
/* 44 */     return this.timeSeconds;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 50 */     return "BenchUpgradeRequirement{input=" + Arrays.toString((Object[])this.input) + ", timeSeconds=" + this.timeSeconds + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BenchUpgradeRequirement toPacket() {
/* 57 */     BenchUpgradeRequirement packet = new BenchUpgradeRequirement();
/* 58 */     if (this.input != null && this.input.length > 0) {
/* 59 */       packet.material = new com.hypixel.hytale.protocol.MaterialQuantity[this.input.length];
/* 60 */       for (int i = 0; i < this.input.length; i++) {
/* 61 */         packet.material[i] = this.input[i].toPacket();
/*    */       }
/* 63 */       packet.timeSeconds = this.timeSeconds;
/*    */     } 
/* 65 */     return packet;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\bench\BenchUpgradeRequirement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */