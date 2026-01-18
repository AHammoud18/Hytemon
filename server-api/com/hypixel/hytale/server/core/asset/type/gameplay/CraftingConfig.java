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
/*    */ public class CraftingConfig
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<CraftingConfig> CODEC;
/*    */   
/*    */   static {
/* 47 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CraftingConfig.class, CraftingConfig::new).appendInherited(new KeyedCodec("BenchMaterialChestHorizontalSearchRadius", (Codec)Codec.INTEGER), (gameplayConfig, o) -> gameplayConfig.benchMaterialHorizontalChestSearchRadius = o.intValue(), gameplayConfig -> Integer.valueOf(gameplayConfig.benchMaterialHorizontalChestSearchRadius), (gameplayConfig, parent) -> gameplayConfig.benchMaterialHorizontalChestSearchRadius = parent.benchMaterialHorizontalChestSearchRadius).addValidator(Validators.range(Integer.valueOf(0), Integer.valueOf(7))).documentation("The horizontal radius of search around a bench to use materials from the chests").add()).appendInherited(new KeyedCodec("BenchMaterialChestVerticalSearchRadius", (Codec)Codec.INTEGER), (gameplayConfig, o) -> gameplayConfig.benchMaterialVerticalChestSearchRadius = o.intValue(), gameplayConfig -> Integer.valueOf(gameplayConfig.benchMaterialVerticalChestSearchRadius), (gameplayConfig, parent) -> gameplayConfig.benchMaterialVerticalChestSearchRadius = parent.benchMaterialVerticalChestSearchRadius).addValidator(Validators.range(Integer.valueOf(0), Integer.valueOf(7))).documentation("The vertical radius of search around a bench to use materials from the chests").add()).appendInherited(new KeyedCodec("BenchMaterialChestLimit", (Codec)Codec.INTEGER), (gameplayConfig, o) -> gameplayConfig.benchMaterialChestLimit = o.intValue(), gameplayConfig -> Integer.valueOf(gameplayConfig.benchMaterialChestLimit), (gameplayConfig, parent) -> gameplayConfig.benchMaterialChestLimit = parent.benchMaterialChestLimit).addValidator(Validators.range(Integer.valueOf(0), Integer.valueOf(200))).documentation("The maximum number of chests a crafting bench will draw materials from").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 52 */   protected int benchMaterialHorizontalChestSearchRadius = 7;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 57 */   protected int benchMaterialVerticalChestSearchRadius = 3;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 62 */   protected int benchMaterialChestLimit = 100;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getBenchMaterialHorizontalChestSearchRadius() {
/* 68 */     return this.benchMaterialHorizontalChestSearchRadius;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getBenchMaterialVerticalChestSearchRadius() {
/* 75 */     return this.benchMaterialVerticalChestSearchRadius;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getBenchMaterialChestLimit() {
/* 82 */     return this.benchMaterialChestLimit;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\CraftingConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */