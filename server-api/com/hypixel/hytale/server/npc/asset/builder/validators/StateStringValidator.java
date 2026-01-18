/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class StateStringValidator extends StringValidator {
/*    */   private String[] stateParts;
/*    */   private final boolean allowEmptyMain;
/*    */   private final boolean mainStateOnly;
/*    */   private final boolean allowNull;
/*    */   
/*    */   private StateStringValidator(boolean allowEmptyMain, boolean mainStateOnly, boolean allowNull) {
/* 13 */     this.allowEmptyMain = allowEmptyMain;
/* 14 */     this.mainStateOnly = mainStateOnly;
/* 15 */     this.allowNull = allowNull;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(@Nullable String value) {
/* 20 */     if (value == null) return this.allowNull; 
/* 21 */     if (value.isEmpty()) return false;
/*    */     
/* 23 */     this.stateParts = value.split("\\.");
/* 24 */     if (this.stateParts.length > 2) return false;
/*    */     
/* 26 */     if (this.stateParts.length > 1 && this.mainStateOnly) return false;
/*    */ 
/*    */     
/* 29 */     if (this.stateParts.length > 1 && this.allowEmptyMain) {
/* 30 */       String statePart = this.stateParts[1];
/* 31 */       return (statePart != null && !statePart.isEmpty());
/*    */     } 
/*    */     
/* 34 */     if (this.stateParts.length == 0) return false;
/*    */     
/* 36 */     return (this.stateParts[0] != null && !this.stateParts[0].isEmpty());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String value) {
/* 42 */     return String.format("%s is not a valid format for a state string. May only contain one . separator and must not be empty.%s%s", new Object[] { value, 
/* 43 */           this.allowEmptyMain ? "" : " Main state must not be empty.", this.mainStateOnly ? " Sub state must not be set." : "" });
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String value, String name) {
/* 49 */     return String.format("Parameter %s, %s is not a valid format for a state string. May only contain one . separator and must not be empty.%s%s", new Object[] { name, value, 
/* 50 */           this.allowEmptyMain ? "" : " Main state must not be empty.", this.mainStateOnly ? " Sub state must not be set." : "" });
/*    */   }
/*    */   
/*    */   public boolean hasMainState() {
/* 54 */     if (this.stateParts.length <= 0) return false; 
/* 55 */     String statePart = this.stateParts[0];
/* 56 */     return (statePart != null && !statePart.isEmpty());
/*    */   }
/*    */   
/*    */   public boolean hasSubState() {
/* 60 */     return (this.stateParts.length > 1 && this.stateParts[1] != null && !this.stateParts[1].isEmpty());
/*    */   }
/*    */   
/*    */   public String getMainState() {
/* 64 */     return this.stateParts[0];
/*    */   }
/*    */   
/*    */   public String getSubState() {
/* 68 */     return this.stateParts[1];
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static StateStringValidator get() {
/* 73 */     return new StateStringValidator(true, false, false);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static StateStringValidator mainStateOnly() {
/* 78 */     return new StateStringValidator(false, true, false);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static StateStringValidator requireMainState() {
/* 83 */     return new StateStringValidator(false, false, false);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static StateStringValidator requireMainStateOrNull() {
/* 88 */     return new StateStringValidator(false, false, true);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\StateStringValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */