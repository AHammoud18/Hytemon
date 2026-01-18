/*    */ package com.hypixel.hytale.server.core.asset.type.tagpattern.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.protocol.TagPattern;
/*    */ import com.hypixel.hytale.protocol.TagPatternType;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import java.lang.ref.SoftReference;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class AndPatternOp extends MultiplePatternOp {
/*    */   @Nonnull
/* 14 */   public static BuilderCodec<AndPatternOp> CODEC = BuilderCodec.builder(AndPatternOp.class, AndPatternOp::new, MultiplePatternOp.CODEC)
/* 15 */     .build();
/*    */ 
/*    */   
/*    */   public boolean test(Int2ObjectMap<IntSet> tags) {
/* 19 */     for (int i = 0; i < this.patterns.length; i++) {
/* 20 */       if (!this.patterns[i].test(tags)) return false;
/*    */     
/*    */     } 
/* 23 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public TagPattern toPacket() {
/* 28 */     TagPattern cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 29 */     if (cached != null) return cached;
/*    */     
/* 31 */     TagPattern packet = super.toPacket();
/* 32 */     packet.type = TagPatternType.And;
/*    */     
/* 34 */     this.cachedPacket = new SoftReference<>(packet);
/* 35 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 41 */     return "AndPatternOp{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\tagpattern\config\AndPatternOp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */