/*    */ package com.hypixel.hytale.server.core.asset.type.gameplay;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BrokenPenalties
/*    */ {
/* 17 */   public static final BrokenPenalties DEFAULT = new BrokenPenalties();
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static final BuilderCodec<BrokenPenalties> CODEC;
/*    */ 
/*    */ 
/*    */   
/*    */   private Double tool;
/*    */ 
/*    */ 
/*    */   
/*    */   private Double armor;
/*    */ 
/*    */ 
/*    */   
/*    */   private Double weapon;
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 39 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BrokenPenalties.class, BrokenPenalties::new).addField(new KeyedCodec("Tool", (Codec)Codec.DOUBLE), (o, i) -> o.tool = i, o -> o.tool)).addField(new KeyedCodec("Armor", (Codec)Codec.DOUBLE), (o, i) -> o.armor = i, o -> o.armor)).addField(new KeyedCodec("Weapon", (Codec)Codec.DOUBLE), (o, i) -> o.weapon = i, o -> o.weapon)).build();
/*    */   }
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getTool(double defaultValue) {
/* 63 */     return (this.tool == null) ? defaultValue : this.tool.doubleValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getArmor(double defaultValue) {
/* 73 */     return (this.armor == null) ? defaultValue : this.armor.doubleValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getWeapon(double defaultValue) {
/* 83 */     return (this.weapon == null) ? defaultValue : this.weapon.doubleValue();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\BrokenPenalties.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */