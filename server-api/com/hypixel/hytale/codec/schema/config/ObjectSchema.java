/*    */ package com.hypixel.hytale.codec.schema.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectSchema
/*    */   extends Schema
/*    */ {
/*    */   public static final BuilderCodec<ObjectSchema> CODEC;
/*    */   private Map<String, Schema> properties;
/*    */   @Nullable
/*    */   private Object additionalProperties;
/*    */   private StringSchema propertyNames;
/*    */   private Schema unevaluatedProperties;
/*    */   
/*    */   static {
/* 29 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ObjectSchema.class, ObjectSchema::new, Schema.BASE_CODEC).addField(new KeyedCodec("properties", (Codec)new MapCodec((Codec)Schema.CODEC, java.util.LinkedHashMap::new), false, true), (o, i) -> o.properties = i, o -> o.properties)).addField(new KeyedCodec("additionalProperties", new Schema.BooleanOrSchema(), false, true), (o, i) -> o.additionalProperties = i, o -> o.additionalProperties)).addField(new KeyedCodec("propertyNames", (Codec)StringSchema.CODEC, false, true), (o, i) -> o.propertyNames = i, o -> o.propertyNames)).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, Schema> getProperties() {
/* 38 */     return this.properties;
/*    */   }
/*    */   
/*    */   public void setProperties(Map<String, Schema> properties) {
/* 42 */     this.properties = properties;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Object getAdditionalProperties() {
/* 47 */     return this.additionalProperties;
/*    */   }
/*    */   
/*    */   public void setAdditionalProperties(boolean additionalProperties) {
/* 51 */     this.additionalProperties = Boolean.valueOf(additionalProperties);
/*    */   }
/*    */   
/*    */   public void setAdditionalProperties(Schema additionalProperties) {
/* 55 */     this.additionalProperties = additionalProperties;
/*    */   }
/*    */   
/*    */   public StringSchema getPropertyNames() {
/* 59 */     return this.propertyNames;
/*    */   }
/*    */   
/*    */   public void setPropertyNames(StringSchema propertyNames) {
/* 63 */     this.propertyNames = propertyNames;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 68 */     if (this == o) return true; 
/* 69 */     if (o == null || getClass() != o.getClass()) return false; 
/* 70 */     if (!super.equals(o)) return false;
/*    */     
/* 72 */     ObjectSchema that = (ObjectSchema)o;
/*    */     
/* 74 */     if ((this.properties != null) ? !this.properties.equals(that.properties) : (that.properties != null)) return false; 
/* 75 */     if ((this.additionalProperties != null) ? !this.additionalProperties.equals(that.additionalProperties) : (that.additionalProperties != null)) return false; 
/* 76 */     if ((this.propertyNames != null) ? !this.propertyNames.equals(that.propertyNames) : (that.propertyNames != null)) return false; 
/* 77 */     return (this.unevaluatedProperties != null) ? this.unevaluatedProperties.equals(that.unevaluatedProperties) : ((that.unevaluatedProperties == null));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 82 */     int result = super.hashCode();
/* 83 */     result = 31 * result + ((this.properties != null) ? this.properties.hashCode() : 0);
/* 84 */     result = 31 * result + ((this.additionalProperties != null) ? this.additionalProperties.hashCode() : 0);
/* 85 */     result = 31 * result + ((this.propertyNames != null) ? this.propertyNames.hashCode() : 0);
/* 86 */     result = 31 * result + ((this.unevaluatedProperties != null) ? this.unevaluatedProperties.hashCode() : 0);
/* 87 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 93 */     return "ObjectSchema{properties=" + String.valueOf(this.properties) + ", additionalProperties=" + String.valueOf(this.additionalProperties) + "} " + super
/*    */ 
/*    */       
/* 96 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\config\ObjectSchema.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */