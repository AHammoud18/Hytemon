/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class AttributeRelationValidator
/*    */   extends Validator
/*    */ {
/*    */   private final String firstAttribute;
/*    */   private final RelationalOperator relation;
/*    */   private final String secondAttribute;
/*    */   
/*    */   private AttributeRelationValidator(String firstAttribute, RelationalOperator relation, String secondAttribute) {
/* 14 */     this.firstAttribute = firstAttribute;
/* 15 */     this.relation = relation;
/* 16 */     this.secondAttribute = secondAttribute;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AttributeRelationValidator withAttributes(String firstAttribute, RelationalOperator relation, String secondAttribute) {
/* 21 */     return new AttributeRelationValidator(firstAttribute, relation, secondAttribute);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\AttributeRelationValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */