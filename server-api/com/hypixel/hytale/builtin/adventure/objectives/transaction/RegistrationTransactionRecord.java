/*    */ package com.hypixel.hytale.builtin.adventure.objectives.transaction;
/*    */ 
/*    */ import com.hypixel.hytale.event.EventRegistry;
/*    */ import com.hypixel.hytale.function.consumer.BooleanConsumer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class RegistrationTransactionRecord
/*    */   extends TransactionRecord {
/*    */   protected BooleanConsumer registration;
/*    */   
/*    */   public RegistrationTransactionRecord(BooleanConsumer registration) {
/* 12 */     this.registration = registration;
/*    */   }
/*    */ 
/*    */   
/*    */   public void revert() {
/* 17 */     this.registration.accept(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void complete() {
/* 22 */     this.registration.accept(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void unload() {
/* 27 */     this.registration.accept(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldBeSerialized() {
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 38 */     return "RegistrationTransactionRecord{registration=" + String.valueOf(this.registration) + "} " + super
/*    */       
/* 40 */       .toString();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static TransactionRecord[] wrap(@Nonnull EventRegistry registry) {
/* 45 */     BooleanConsumer[] registrations = (BooleanConsumer[])registry.getRegistrations().toArray(x$0 -> new BooleanConsumer[x$0]);
/* 46 */     TransactionRecord[] records = new TransactionRecord[registrations.length];
/* 47 */     int i = 0;
/* 48 */     for (BooleanConsumer registration : registrations) records[i++] = new RegistrationTransactionRecord(registration); 
/* 49 */     return records;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static TransactionRecord[] append(@Nonnull TransactionRecord[] arr, @Nonnull EventRegistry registry) {
/* 54 */     BooleanConsumer[] registrations = (BooleanConsumer[])registry.getRegistrations().toArray(x$0 -> new BooleanConsumer[x$0]);
/* 55 */     TransactionRecord[] records = new TransactionRecord[arr.length + registrations.length];
/* 56 */     System.arraycopy(arr, 0, records, 0, arr.length);
/* 57 */     int i = registrations.length;
/* 58 */     for (BooleanConsumer registration : registrations) records[i++] = new RegistrationTransactionRecord(registration); 
/* 59 */     return records;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\transaction\RegistrationTransactionRecord.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */