/*    */ package com.hypixel.hytale.codec.schema.metadata;
/*    */ 
/*    */ import com.hypixel.hytale.codec.schema.config.Schema;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VirtualPath
/*    */   implements Metadata
/*    */ {
/*    */   private final String path;
/*    */   
/*    */   public VirtualPath(String path) {
/* 16 */     this.path = path;
/*    */   }
/*    */ 
/*    */   
/*    */   public void modify(@Nonnull Schema schema) {
/* 21 */     schema.getHytale().setVirtualPath(this.path);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\metadata\VirtualPath.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */