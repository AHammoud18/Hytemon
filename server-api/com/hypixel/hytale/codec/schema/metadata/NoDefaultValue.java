/*    */ package com.hypixel.hytale.codec.schema.metadata;
/*    */ import com.hypixel.hytale.codec.schema.config.Schema;
/*    */ import com.hypixel.hytale.codec.schema.config.StringSchema;
/*    */ 
/*    */ public class NoDefaultValue implements Metadata {
/*  6 */   public static final NoDefaultValue INSTANCE = new NoDefaultValue();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void modify(Schema schema) {
/* 14 */     if (schema instanceof StringSchema) {
/* 15 */       ((StringSchema)schema).setDefault(null);
/* 16 */     } else if (schema instanceof IntegerSchema) {
/* 17 */       ((IntegerSchema)schema).setDefault(null);
/* 18 */     } else if (schema instanceof NumberSchema) {
/* 19 */       ((NumberSchema)schema).setDefault(null);
/* 20 */     } else if (schema instanceof BooleanSchema) {
/* 21 */       ((BooleanSchema)schema).setDefault(null);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\metadata\NoDefaultValue.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */