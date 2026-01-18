/*    */ package com.hypixel.hytale.codec.schema.metadata.ui;
/*    */ 
/*    */ import com.hypixel.hytale.codec.schema.config.Schema;
/*    */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class UIDefaultCollapsedState
/*    */   implements Metadata {
/*  9 */   public static final UIDefaultCollapsedState UNCOLLAPSED = new UIDefaultCollapsedState(false);
/*    */   
/*    */   private final boolean collapsedByDefault;
/*    */   
/*    */   private UIDefaultCollapsedState(boolean collapsedByDefault) {
/* 14 */     this.collapsedByDefault = collapsedByDefault;
/*    */   }
/*    */ 
/*    */   
/*    */   public void modify(@Nonnull Schema schema) {
/* 19 */     schema.getHytale().setUiCollapsedByDefault(Boolean.valueOf(this.collapsedByDefault));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\metadat\\ui\UIDefaultCollapsedState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */