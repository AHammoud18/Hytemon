/*    */ package com.hypixel.hytale.registry;
/*    */ 
/*    */ import java.util.function.BooleanSupplier;
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
/*    */ public class Registration
/*    */ {
/*    */   @Nonnull
/*    */   protected final BooleanSupplier isEnabled;
/*    */   @Nonnull
/*    */   protected final Runnable unregister;
/*    */   private boolean registered = true;
/*    */   
/*    */   public Registration(@Nonnull BooleanSupplier isEnabled, @Nonnull Runnable unregister) {
/* 35 */     this.isEnabled = isEnabled;
/* 36 */     this.unregister = unregister;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void unregister() {
/* 43 */     if (this.registered && this.isEnabled.getAsBoolean()) {
/* 44 */       this.unregister.run();
/*    */     }
/* 46 */     this.registered = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isRegistered() {
/* 55 */     return (this.registered && this.isEnabled.getAsBoolean());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 61 */     return "Registration{isEnabled=" + String.valueOf(this.isEnabled) + ", unregister=" + String.valueOf(this.unregister) + ", registered=" + this.registered + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\registry\Registration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */