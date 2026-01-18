/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class ExistsIfParameterSetValidator
/*    */   extends Validator
/*    */ {
/*    */   private final String parameter;
/*    */   private final String attribute;
/*    */   
/*    */   private ExistsIfParameterSetValidator(String parameter, String attribute) {
/* 13 */     this.parameter = parameter;
/* 14 */     this.attribute = attribute;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String errorMessage(String parameter, String attribute) {
/* 19 */     return String.format("If %s is set, %s must be present", new Object[] { parameter, attribute });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ExistsIfParameterSetValidator withAttributes(String parameter, String attribute) {
/* 24 */     return new ExistsIfParameterSetValidator(parameter, attribute);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\ExistsIfParameterSetValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */