/*    */ package com.hypixel.hytale.server.core.asset.type.tagpattern.config;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
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
/*    */ 
/*    */ 
/*    */ public class EqualsTagOp
/*    */   extends TagPattern
/*    */ {
/*    */   @Nonnull
/*    */   public static BuilderCodec<EqualsTagOp> CODEC;
/*    */   protected String tag;
/*    */   protected int tagIndex;
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(EqualsTagOp.class, EqualsTagOp::new, TagPattern.BASE_CODEC).append(new KeyedCodec("Tag", (Codec)Codec.STRING), (singleTagOp, s) -> singleTagOp.tag = s, singleTagOp -> singleTagOp.tag).addValidator(Validators.nonNull()).add()).afterDecode(singleTagOp -> { if (singleTagOp.tag == null) return;  singleTagOp.tagIndex = AssetRegistry.getOrCreateTagIndex(singleTagOp.tag); })).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EqualsTagOp(String tag) {
/* 33 */     this.tag = tag;
/*    */   }
/*    */ 
/*    */   
/*    */   protected EqualsTagOp() {}
/*    */ 
/*    */   
/*    */   public boolean test(@Nonnull Int2ObjectMap<IntSet> tags) {
/* 41 */     return tags.containsKey(this.tagIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   public TagPattern toPacket() {
/* 46 */     TagPattern cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 47 */     if (cached != null) return cached;
/*    */     
/* 49 */     TagPattern packet = new TagPattern();
/* 50 */     packet.type = TagPatternType.Equals;
/* 51 */     packet.tagIndex = this.tagIndex;
/*    */     
/* 53 */     this.cachedPacket = new SoftReference<>(packet);
/* 54 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 60 */     return "EqualsTagOp{tag='" + this.tag + "'} " + super
/*    */       
/* 62 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\tagpattern\config\EqualsTagOp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */