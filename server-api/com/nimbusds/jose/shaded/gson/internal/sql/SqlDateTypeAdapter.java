/*    */ package com.nimbusds.jose.shaded.gson.internal.sql;
/*    */ 
/*    */ import com.nimbusds.jose.shaded.gson.Gson;
/*    */ import com.nimbusds.jose.shaded.gson.JsonSyntaxException;
/*    */ import com.nimbusds.jose.shaded.gson.TypeAdapter;
/*    */ import com.nimbusds.jose.shaded.gson.TypeAdapterFactory;
/*    */ import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
/*    */ import com.nimbusds.jose.shaded.gson.stream.JsonReader;
/*    */ import com.nimbusds.jose.shaded.gson.stream.JsonToken;
/*    */ import com.nimbusds.jose.shaded.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import java.sql.Date;
/*    */ import java.text.DateFormat;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.TimeZone;
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
/*    */ final class SqlDateTypeAdapter
/*    */   extends TypeAdapter<Date>
/*    */ {
/* 41 */   static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
/*    */     {
/*    */       
/*    */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken)
/*    */       {
/* 46 */         return (typeToken.getRawType() == Date.class) ? 
/* 47 */           new SqlDateTypeAdapter() : 
/* 48 */           null;
/*    */       }
/*    */     };
/*    */   
/* 52 */   private final DateFormat format = new SimpleDateFormat("MMM d, yyyy");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Date read(JsonReader in) throws IOException {
/* 58 */     if (in.peek() == JsonToken.NULL) {
/* 59 */       in.nextNull();
/* 60 */       return null;
/*    */     } 
/* 62 */     String s = in.nextString();
/* 63 */     synchronized (this) {
/* 64 */       TimeZone originalTimeZone = this.format.getTimeZone();
/*    */       try {
/* 66 */         Date utilDate = this.format.parse(s);
/* 67 */         return new Date(utilDate.getTime());
/* 68 */       } catch (ParseException e) {
/* 69 */         throw new JsonSyntaxException("Failed parsing '" + s + "' as SQL Date; at path " + in
/* 70 */             .getPreviousPath(), e);
/*    */       } finally {
/* 72 */         this.format.setTimeZone(originalTimeZone);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void write(JsonWriter out, Date value) throws IOException {
/*    */     String dateString;
/* 79 */     if (value == null) {
/* 80 */       out.nullValue();
/*    */       
/*    */       return;
/*    */     } 
/* 84 */     synchronized (this) {
/* 85 */       dateString = this.format.format(value);
/*    */     } 
/* 87 */     out.value(dateString);
/*    */   }
/*    */   
/*    */   private SqlDateTypeAdapter() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\internal\sql\SqlDateTypeAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */