/*    */ package com.hypixel.hytale.codec.schema.metadata.ui;
/*    */ 
/*    */ import com.hypixel.hytale.codec.schema.config.Schema;
/*    */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class UIEditorSectionStart
/*    */   implements Metadata {
/*    */   private final String title;
/*    */   
/*    */   public UIEditorSectionStart(String title) {
/* 12 */     this.title = title;
/*    */   }
/*    */ 
/*    */   
/*    */   public void modify(@Nonnull Schema schema) {
/* 17 */     schema.getHytale().setUiSectionStart(this.title);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\metadat\\ui\UIEditorSectionStart.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */