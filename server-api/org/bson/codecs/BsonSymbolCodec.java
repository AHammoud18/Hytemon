/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonSymbol;
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
/*    */ public class BsonSymbolCodec
/*    */   implements Codec<BsonSymbol>
/*    */ {
/*    */   public BsonSymbol decode(BsonReader reader, DecoderContext decoderContext) {
/* 31 */     return new BsonSymbol(reader.readSymbol());
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, BsonSymbol value, EncoderContext encoderContext) {
/* 36 */     writer.writeSymbol(value.getSymbol());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonSymbol> getEncoderClass() {
/* 41 */     return BsonSymbol.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonSymbolCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */