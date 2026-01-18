/*    */ package com.hypixel.hytale.codec.schema.metadata.ui;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UIButton
/*    */ {
/*    */   public static final BuilderCodec<UIButton> CODEC;
/*    */   private String buttonId;
/*    */   private String textId;
/*    */   
/*    */   static {
/* 19 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(UIButton.class, UIButton::new).append(new KeyedCodec("textId", (Codec)Codec.STRING, false, true), (o, i) -> o.textId = i, o -> o.textId).add()).append(new KeyedCodec("buttonId", (Codec)Codec.STRING, false, true), (o, i) -> o.buttonId = i, o -> o.buttonId).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public UIButton(String textId, String buttonId) {
/* 25 */     this.textId = textId;
/* 26 */     this.buttonId = buttonId;
/*    */   }
/*    */   
/*    */   protected UIButton() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\metadat\\ui\UIButton.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */