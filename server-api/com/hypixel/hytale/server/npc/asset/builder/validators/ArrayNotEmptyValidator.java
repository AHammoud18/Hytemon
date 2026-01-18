/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderObjectArrayHelper;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArrayNotEmptyValidator
/*    */   extends ArrayValidator
/*    */ {
/* 12 */   private static final ArrayNotEmptyValidator INSTANCE = new ArrayNotEmptyValidator();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean test(@Nonnull BuilderObjectArrayHelper<?, ?> builderObjectArrayHelper) {
/* 19 */     return builderObjectArrayHelper.isPresent();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String name, BuilderObjectArrayHelper<?, ?> builderObjectArrayHelper) {
/* 25 */     return errorMessage(name);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(BuilderObjectArrayHelper<?, ?> builderObjectArrayHelper) {
/* 31 */     return errorMessage((String)null);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(@Nullable String name) {
/* 36 */     if (name == null) {
/* 37 */       name = "Array";
/*    */     } else {
/* 39 */       name = "'" + name + "'";
/*    */     } 
/* 41 */     return name + " must not be empty";
/*    */   }
/*    */   
/*    */   public static ArrayNotEmptyValidator get() {
/* 45 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\ArrayNotEmptyValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */