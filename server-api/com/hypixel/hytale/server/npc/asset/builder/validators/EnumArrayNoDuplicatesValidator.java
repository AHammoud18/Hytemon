/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnumArrayNoDuplicatesValidator
/*    */   extends EnumArrayValidator
/*    */ {
/* 12 */   private static final EnumArrayNoDuplicatesValidator INSTANCE = new EnumArrayNoDuplicatesValidator();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <T extends Enum<T>> boolean test(@Nonnull T[] array, Class<T> clazz) {
/* 19 */     EnumSet<T> set = EnumSet.noneOf(clazz);
/* 20 */     for (T item : array) {
/* 21 */       if (!set.add(item)) {
/* 22 */         return false;
/*    */       }
/*    */     } 
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public <T extends Enum<T>> String errorMessage(String name, T[] array) {
/* 31 */     return String.format("%s must not contain duplicates: %s", new Object[] { name, Arrays.toString((Object[])array) });
/*    */   }
/*    */   
/*    */   public static EnumArrayNoDuplicatesValidator get() {
/* 35 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\EnumArrayNoDuplicatesValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */