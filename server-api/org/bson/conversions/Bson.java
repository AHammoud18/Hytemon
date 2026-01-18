/*    */ package org.bson.conversions;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import org.bson.BsonDocument;
/*    */ import org.bson.codecs.BsonCodecProvider;
/*    */ import org.bson.codecs.BsonValueCodecProvider;
/*    */ import org.bson.codecs.DocumentCodecProvider;
/*    */ import org.bson.codecs.IterableCodecProvider;
/*    */ import org.bson.codecs.JsonObjectCodecProvider;
/*    */ import org.bson.codecs.MapCodecProvider;
/*    */ import org.bson.codecs.ValueCodecProvider;
/*    */ import org.bson.codecs.configuration.CodecProvider;
/*    */ import org.bson.codecs.configuration.CodecRegistries;
/*    */ import org.bson.codecs.configuration.CodecRegistry;
/*    */ import org.bson.codecs.jsr310.Jsr310CodecProvider;
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
/*    */ public interface Bson
/*    */ {
/* 58 */   public static final CodecRegistry DEFAULT_CODEC_REGISTRY = CodecRegistries.fromProviders(Arrays.asList(new CodecProvider[] { (CodecProvider)new ValueCodecProvider(), (CodecProvider)new BsonValueCodecProvider(), (CodecProvider)new DocumentCodecProvider(), (CodecProvider)new IterableCodecProvider(), (CodecProvider)new MapCodecProvider(), (CodecProvider)new Jsr310CodecProvider(), (CodecProvider)new JsonObjectCodecProvider(), (CodecProvider)new BsonCodecProvider() }));
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
/*    */   <TDocument> BsonDocument toBsonDocument(Class<TDocument> paramClass, CodecRegistry paramCodecRegistry);
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
/*    */   default BsonDocument toBsonDocument() {
/* 91 */     return toBsonDocument(BsonDocument.class, DEFAULT_CODEC_REGISTRY);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\conversions\Bson.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */