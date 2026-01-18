/*    */ package com.hypixel.hytale.codec.schema.metadata;
/*    */ 
/*    */ import com.hypixel.hytale.codec.schema.config.Schema;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class AllowEmptyObject
/*    */   implements Metadata {
/*  8 */   public static final AllowEmptyObject INSTANCE = new AllowEmptyObject(true);
/*    */   
/*    */   private final boolean allowEmptyObject;
/*    */   
/*    */   public AllowEmptyObject(boolean allowEmptyObject) {
/* 13 */     this.allowEmptyObject = allowEmptyObject;
/*    */   }
/*    */ 
/*    */   
/*    */   public void modify(@Nonnull Schema schema) {
/* 18 */     schema.getHytale().setAllowEmptyObject(Boolean.valueOf(this.allowEmptyObject));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\metadata\AllowEmptyObject.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */