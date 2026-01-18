/*    */ package com.hypixel.hytale.codec.schema;
/*    */ 
/*    */ import com.hypixel.hytale.codec.schema.config.Schema;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ public interface SchemaConvertable<T>
/*    */ {
/*    */   @Nonnull
/*    */   Schema toSchema(@Nonnull SchemaContext paramSchemaContext);
/*    */   
/*    */   @Nonnull
/*    */   default Schema toSchema(@Nonnull SchemaContext context, @Nullable T def) {
/* 35 */     return toSchema(context);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\SchemaConvertable.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */