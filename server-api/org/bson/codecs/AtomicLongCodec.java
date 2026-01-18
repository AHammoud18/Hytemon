/*    */ package org.bson.codecs;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicLong;
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
/*    */ public class AtomicLongCodec
/*    */   implements Codec<AtomicLong>
/*    */ {
/*    */   public void encode(BsonWriter writer, AtomicLong value, EncoderContext encoderContext) {
/* 36 */     writer.writeInt64(value.longValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public AtomicLong decode(BsonReader reader, DecoderContext decoderContext) {
/* 41 */     return new AtomicLong(NumberCodecHelper.decodeLong(reader));
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<AtomicLong> getEncoderClass() {
/* 46 */     return AtomicLong.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\AtomicLongCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */