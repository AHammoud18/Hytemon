/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.types.Symbol;
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
/*    */ public class SymbolCodec
/*    */   implements Codec<Symbol>
/*    */ {
/*    */   public Symbol decode(BsonReader reader, DecoderContext decoderContext) {
/* 31 */     return new Symbol(reader.readSymbol());
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, Symbol value, EncoderContext encoderContext) {
/* 36 */     writer.writeSymbol(value.getSymbol());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<Symbol> getEncoderClass() {
/* 41 */     return Symbol.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\SymbolCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */