/*    */ package com.hypixel.hytale.codec.schema.metadata.ui;
/*    */ 
/*    */ import com.hypixel.hytale.codec.schema.config.Schema;
/*    */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class UISidebarButtons
/*    */   implements Metadata {
/*    */   private final UIButton[] buttons;
/*    */   
/*    */   public UISidebarButtons(UIButton... buttons) {
/* 12 */     this.buttons = buttons;
/*    */   }
/*    */ 
/*    */   
/*    */   public void modify(@Nonnull Schema schema) {
/* 17 */     schema.getHytale().setUiSidebarButtons(this.buttons);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\metadat\\ui\UISidebarButtons.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */