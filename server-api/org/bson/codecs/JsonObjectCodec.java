/*    */ package org.bson.codecs;
/*    */ 
/*    */ import java.io.StringWriter;
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.json.JsonObject;
/*    */ import org.bson.json.JsonReader;
/*    */ import org.bson.json.JsonWriter;
/*    */ import org.bson.json.JsonWriterSettings;
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
/*    */ public class JsonObjectCodec
/*    */   implements Codec<JsonObject>
/*    */ {
/*    */   private final JsonWriterSettings writerSettings;
/*    */   
/*    */   public JsonObjectCodec() {
/* 40 */     this(JsonWriterSettings.builder().build());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonObjectCodec(JsonWriterSettings writerSettings) {
/* 49 */     this.writerSettings = writerSettings;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, JsonObject value, EncoderContext encoderContext) {
/* 54 */     writer.pipe((BsonReader)new JsonReader(value.getJson()));
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonObject decode(BsonReader reader, DecoderContext decoderContext) {
/* 59 */     StringWriter stringWriter = new StringWriter();
/* 60 */     (new JsonWriter(stringWriter, this.writerSettings)).pipe(reader);
/* 61 */     return new JsonObject(stringWriter.toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<JsonObject> getEncoderClass() {
/* 66 */     return JsonObject.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\JsonObjectCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */