/*    */ package com.hypixel.hytale.builtin.adventure.shop.barter;
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
/*    */ public class BarterItemStack
/*    */ {
/*    */   public static final BuilderCodec<BarterItemStack> CODEC;
/*    */   protected String itemId;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BarterItemStack.class, BarterItemStack::new).append(new KeyedCodec("ItemId", (Codec)Codec.STRING), (stack, s) -> stack.itemId = s, stack -> stack.itemId).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Quantity", (Codec)Codec.INTEGER), (stack, i) -> stack.quantity = i.intValue(), stack -> Integer.valueOf(stack.quantity)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(1))).add()).build();
/*    */   }
/*    */   
/* 28 */   protected int quantity = 1;
/*    */   
/*    */   public BarterItemStack(String itemId, int quantity) {
/* 31 */     this.itemId = itemId;
/* 32 */     this.quantity = quantity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getItemId() {
/* 39 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public int getQuantity() {
/* 43 */     return this.quantity;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 49 */     return "BarterItemStack{itemId='" + this.itemId + "', quantity=" + this.quantity + "}";
/*    */   }
/*    */   
/*    */   protected BarterItemStack() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shop\barter\BarterItemStack.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */