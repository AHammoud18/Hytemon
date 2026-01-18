/*    */ package org.bson.codecs;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
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
/*    */ public class AtomicIntegerCodec
/*    */   implements Codec<AtomicInteger>
/*    */ {
/*    */   public void encode(BsonWriter writer, AtomicInteger value, EncoderContext encoderContext) {
/* 36 */     writer.writeInt32(value.intValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public AtomicInteger decode(BsonReader reader, DecoderContext decoderContext) {
/* 41 */     return new AtomicInteger(NumberCodecHelper.decodeInt(reader));
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<AtomicInteger> getEncoderClass() {
/* 46 */     return AtomicInteger.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\AtomicIntegerCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */