/*    */ package com.hypixel.hytale.server.core.asset.type.tagpattern.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.TagPattern;
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class MultiplePatternOp
/*    */   extends TagPattern {
/*    */   @Nonnull
/*    */   public static BuilderCodec<MultiplePatternOp> CODEC;
/*    */   protected TagPattern[] patterns;
/*    */   
/*    */   static {
/* 19 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.abstractBuilder(MultiplePatternOp.class, TagPattern.BASE_CODEC).append(new KeyedCodec("Patterns", (Codec)new ArrayCodec((Codec)TagPattern.CODEC, x$0 -> new TagPattern[x$0])), (tagPattern, tagPatterns) -> tagPattern.patterns = tagPatterns, tagPattern -> tagPattern.patterns).addValidator(Validators.nonEmptyArray()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TagPattern toPacket() {
/* 25 */     TagPattern packet = new TagPattern();
/* 26 */     packet.operands = new TagPattern[this.patterns.length];
/* 27 */     for (int i = 0; i < this.patterns.length; i++) {
/* 28 */       packet.operands[i] = (TagPattern)this.patterns[i].toPacket();
/*    */     }
/* 30 */     return packet;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 37 */     return "MultiplePatternOp{patterns=" + Arrays.toString((Object[])this.patterns) + "} " + super
/* 38 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\tagpattern\config\MultiplePatternOp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */