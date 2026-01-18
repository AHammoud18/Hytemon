/*    */ package com.hypixel.hytale.builtin.adventure.objectives.transaction;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.common.util.ArrayUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public abstract class TransactionRecord {
/* 13 */   public static final CodecMapCodec<TransactionRecord> CODEC = new CodecMapCodec("Type");
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<TransactionRecord> BASE_CODEC;
/*    */ 
/*    */   
/*    */   static {
/* 20 */     BASE_CODEC = ((BuilderCodec.Builder)BuilderCodec.abstractBuilder(TransactionRecord.class).append(new KeyedCodec("Status", (Codec)new EnumCodec(TransactionStatus.class, EnumCodec.EnumStyle.LEGACY)), (spawnEntityTransactionRecord, status) -> spawnEntityTransactionRecord.status = status, spawnEntityTransactionRecord -> spawnEntityTransactionRecord.status).add()).build();
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
/* 78 */     CODEC.register("SpawnEntity", SpawnEntityTransactionRecord.class, (Codec)SpawnEntityTransactionRecord.CODEC);
/* 79 */     CODEC.register("SpawnBlock", SpawnTreasureChestTransactionRecord.class, (Codec)SpawnTreasureChestTransactionRecord.CODEC);
/*    */   }
/*    */   
/*    */   protected TransactionStatus status = TransactionStatus.SUCCESS;
/*    */   private String reason;
/*    */   
/*    */   public TransactionStatus getStatus() {
/*    */     return this.status;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public TransactionRecord fail(String reason) {
/*    */     this.status = TransactionStatus.FAIL;
/*    */     this.reason = reason;
/*    */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/*    */     return "TransactionRecord{status=" + String.valueOf(this.status) + ", reason='" + this.reason + "'}";
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static <T extends TransactionRecord> TransactionRecord[] appendTransaction(@Nullable TransactionRecord[] transactions, @Nonnull T transaction) {
/*    */     if (transactions == null)
/*    */       return new TransactionRecord[] { (TransactionRecord)transaction }; 
/*    */     return (TransactionRecord[])ArrayUtil.append((Object[])transactions, transaction);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static <T extends TransactionRecord> TransactionRecord[] appendFailedTransaction(TransactionRecord[] transactions, @Nonnull T transaction, String reason) {
/*    */     return appendTransaction(transactions, transaction.fail(reason));
/*    */   }
/*    */   
/*    */   public abstract void revert();
/*    */   
/*    */   public abstract void complete();
/*    */   
/*    */   public abstract void unload();
/*    */   
/*    */   public abstract boolean shouldBeSerialized();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\transaction\TransactionRecord.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */