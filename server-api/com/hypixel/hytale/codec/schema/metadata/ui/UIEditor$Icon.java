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
/*    */ public class Icon
/*    */   implements UIEditor.EditorComponent
/*    */ {
/*    */   public static final BuilderCodec<Icon> CODEC;
/*    */   private String defaultPathTemplate;
/*    */   private int width;
/*    */   private int height;
/*    */   
/*    */   static {
/* 64 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Icon.class, Icon::new).addField(new KeyedCodec("defaultPathTemplate", (Codec)Codec.STRING, true, true), (o, i) -> o.defaultPathTemplate = i, o -> o.defaultPathTemplate)).addField(new KeyedCodec("width", (Codec)Codec.INTEGER, true, true), (o, i) -> o.width = i.intValue(), o -> Integer.valueOf(o.width))).addField(new KeyedCodec("height", (Codec)Codec.INTEGER, true, true), (o, i) -> o.height = i.intValue(), o -> Integer.valueOf(o.height))).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Icon(String defaultPathTemplate, int width, int height) {
/* 75 */     this.defaultPathTemplate = defaultPathTemplate;
/* 76 */     this.width = width;
/* 77 */     this.height = height;
/*    */   }
/*    */   
/*    */   public Icon() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\metadat\\ui\UIEditor$Icon.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */