/*    */ package com.hypixel.hytale.codec.schema.metadata;
/*    */ 
/*    */ import com.hypixel.hytale.codec.schema.config.Schema;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class HytaleType
/*    */   implements Metadata {
/*    */   private final String type;
/*    */   
/*    */   public HytaleType(String type) {
/* 11 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public void modify(@Nonnull Schema schema) {
/* 16 */     schema.getHytale().setType(this.type);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\metadata\HytaleType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */