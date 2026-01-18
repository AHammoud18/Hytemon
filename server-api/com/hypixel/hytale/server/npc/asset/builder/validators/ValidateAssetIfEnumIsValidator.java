/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValidateAssetIfEnumIsValidator<E extends Enum<E> & Supplier<String>>
/*    */   extends Validator
/*    */ {
/*    */   private final String parameter1;
/*    */   private final transient AssetValidator validator;
/*    */   private final String parameter2;
/*    */   private final E enumValue;
/*    */   
/*    */   private ValidateAssetIfEnumIsValidator(String p1, AssetValidator validator, String p2, E value) {
/* 17 */     this.parameter1 = p1;
/* 18 */     this.validator = validator;
/* 19 */     this.parameter2 = p2;
/* 20 */     this.enumValue = value;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static <E extends Enum<E> & Supplier<String>> ValidateAssetIfEnumIsValidator<E> withAttributes(String p1, AssetValidator validator, String p2, E value) {
/* 25 */     return new ValidateAssetIfEnumIsValidator<>(p1, validator, p2, value);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\ValidateAssetIfEnumIsValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */