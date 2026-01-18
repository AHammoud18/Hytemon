/*    */ package com.hypixel.hytale.server.core.ui;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class PatchStyle
/*    */ {
/*    */   public static final BuilderCodec<PatchStyle> CODEC;
/*    */   private Value<String> texturePath;
/*    */   private Value<Integer> border;
/*    */   private Value<Integer> horizontalBorder;
/*    */   private Value<Integer> verticalBorder;
/*    */   private Value<String> color;
/*    */   private Value<Area> area;
/*    */   
/*    */   static {
/* 40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PatchStyle.class, PatchStyle::new).addField(new KeyedCodec("TexturePath", ValueCodec.STRING), (p, t) -> p.texturePath = t, p -> p.texturePath)).addField(new KeyedCodec("Border", ValueCodec.INTEGER), (p, t) -> p.border = t, p -> p.border)).addField(new KeyedCodec("HorizonzalBorder", ValueCodec.INTEGER), (p, t) -> p.horizontalBorder = t, p -> p.horizontalBorder)).addField(new KeyedCodec("VerticalBorder", ValueCodec.INTEGER), (p, t) -> p.verticalBorder = t, p -> p.verticalBorder)).addField(new KeyedCodec("Color", ValueCodec.STRING), (p, t) -> p.color = t, p -> p.color)).addField(new KeyedCodec("Area", new ValueCodec<>((Codec<Area>)Area.CODEC)), (p, t) -> p.area = t, p -> p.area)).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PatchStyle() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PatchStyle(Value<String> texturePath) {
/* 53 */     this.texturePath = texturePath;
/*    */   }
/*    */   
/*    */   public PatchStyle(Value<String> texturePath, Value<Integer> border) {
/* 57 */     this.texturePath = texturePath;
/* 58 */     this.border = border;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public PatchStyle setTexturePath(Value<String> texturePath) {
/* 63 */     this.texturePath = texturePath;
/* 64 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public PatchStyle setBorder(Value<Integer> border) {
/* 69 */     this.border = border;
/* 70 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public PatchStyle setHorizontalBorder(Value<Integer> horizontalBorder) {
/* 75 */     this.horizontalBorder = horizontalBorder;
/* 76 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public PatchStyle setVerticalBorder(Value<Integer> verticalBorder) {
/* 81 */     this.verticalBorder = verticalBorder;
/* 82 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public PatchStyle setColor(Value<String> color) {
/* 87 */     this.color = color;
/* 88 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public PatchStyle setArea(Value<Area> area) {
/* 93 */     this.area = area;
/* 94 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\ui\PatchStyle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */