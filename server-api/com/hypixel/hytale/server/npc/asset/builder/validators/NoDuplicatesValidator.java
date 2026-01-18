/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class NoDuplicatesValidator<T>
/*    */   extends Validator
/*    */ {
/*    */   private final Iterable<T> iterable;
/*    */   private final String variableName;
/*    */   
/*    */   private NoDuplicatesValidator(Iterable<T> iterable, String variableName) {
/* 15 */     this.iterable = iterable;
/* 16 */     this.variableName = variableName;
/*    */   }
/*    */   
/*    */   public boolean test() {
/* 20 */     Set<T> set = new HashSet<>();
/* 21 */     for (T each : this.iterable) {
/* 22 */       if (!set.add(each)) return false; 
/*    */     } 
/* 24 */     return true;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage() {
/* 29 */     return "There are not allowed to be duplicate entries in the \"" + this.variableName + "\" list.";
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static <T> NoDuplicatesValidator<T> withAttributes(Iterable<T> iterable, String variableName) {
/* 34 */     return new NoDuplicatesValidator<>(iterable, variableName);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\NoDuplicatesValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */