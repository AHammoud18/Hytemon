/*    */ package com.hypixel.hytale.server.core.asset.type.tagpattern.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.TagPattern;
/*    */ import com.hypixel.hytale.protocol.TagPatternType;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import java.lang.ref.SoftReference;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class NotPatternOp extends TagPattern {
/*    */   @Nonnull
/*    */   public static BuilderCodec<NotPatternOp> CODEC;
/*    */   protected TagPattern pattern;
/*    */   
/*    */   static {
/* 21 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(NotPatternOp.class, NotPatternOp::new, TagPattern.BASE_CODEC).append(new KeyedCodec("Pattern", (Codec)TagPattern.CODEC), (singleTagOp, s) -> singleTagOp.pattern = s, singleTagOp -> singleTagOp.pattern).addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean test(Int2ObjectMap<IntSet> tags) {
/* 27 */     return !this.pattern.test(tags);
/*    */   }
/*    */ 
/*    */   
/*    */   public TagPattern toPacket() {
/* 32 */     TagPattern cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 33 */     if (cached != null) return cached;
/*    */     
/* 35 */     TagPattern packet = new TagPattern();
/* 36 */     packet.type = TagPatternType.Not;
/* 37 */     packet.not = (TagPattern)this.pattern.toPacket();
/*    */     
/* 39 */     this.cachedPacket = new SoftReference<>(packet);
/* 40 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 46 */     return "NotPatternOp{pattern=" + String.valueOf(this.pattern) + "} " + super
/*    */       
/* 48 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\tagpattern\config\NotPatternOp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */