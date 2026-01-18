/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.completion;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemDropList;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GiveItemsCompletionAsset
/*    */   extends ObjectiveCompletionAsset
/*    */ {
/*    */   public static final BuilderCodec<GiveItemsCompletionAsset> CODEC;
/*    */   protected String dropListId;
/*    */   
/*    */   static {
/* 22 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(GiveItemsCompletionAsset.class, GiveItemsCompletionAsset::new, BASE_CODEC).append(new KeyedCodec("DropList", (Codec)new ContainedAssetCodec(ItemDropList.class, (AssetCodec)ItemDropList.CODEC)), (objective, dropListId) -> objective.dropListId = dropListId, objective -> objective.dropListId).addValidator(Validators.nonNull()).addValidator(ItemDropList.VALIDATOR_CACHE.getValidator()).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public GiveItemsCompletionAsset(String dropListId) {
/* 27 */     this.dropListId = dropListId;
/*    */   }
/*    */ 
/*    */   
/*    */   protected GiveItemsCompletionAsset() {}
/*    */   
/*    */   public String getDropListId() {
/* 34 */     return this.dropListId;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 40 */     return "GiveItemsCompletionAsset{dropListId='" + this.dropListId + "'} " + super
/*    */       
/* 42 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\completion\GiveItemsCompletionAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */