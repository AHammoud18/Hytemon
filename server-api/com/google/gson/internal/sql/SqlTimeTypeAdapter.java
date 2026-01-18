/*    */ package com.google.gson.internal.sql;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.JsonSyntaxException;
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import com.google.gson.reflect.TypeToken;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonToken;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import java.sql.Time;
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
/*    */ 
/*    */ final class SqlTimeTypeAdapter
/*    */   extends TypeAdapter<Time>
/*    */ {
/* 42 */   static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
/*    */     {
/*    */       
/*    */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken)
/*    */       {
/* 47 */         return (typeToken.getRawType() == Time.class) ? 
/* 48 */           new SqlTimeTypeAdapter() : 
/* 49 */           null;
/*    */       }
/*    */     };
/*    */   
/* 53 */   private final DateFormat format = new SimpleDateFormat("hh:mm:ss a");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Time read(JsonReader in) throws IOException {
/* 59 */     if (in.peek() == JsonToken.NULL) {
/* 60 */       in.nextNull();
/* 61 */       return null;
/*    */     } 
/* 63 */     String s = in.nextString();
/* 64 */     synchronized (this) {
/* 65 */       TimeZone originalTimeZone = this.format.getTimeZone();
/*    */       try {
/* 67 */         Date date = this.format.parse(s);
/* 68 */         return new Time(date.getTime());
/* 69 */       } catch (ParseException e) {
/* 70 */         throw new JsonSyntaxException("Failed parsing '" + s + "' as SQL Time; at path " + in
/* 71 */             .getPreviousPath(), e);
/*    */       } finally {
/* 73 */         this.format.setTimeZone(originalTimeZone);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void write(JsonWriter out, Time value) throws IOException {
/*    */     String timeString;
/* 80 */     if (value == null) {
/* 81 */       out.nullValue();
/*    */       
/*    */       return;
/*    */     } 
/* 85 */     synchronized (this) {
/* 86 */       timeString = this.format.format(value);
/*    */     } 
/* 88 */     out.value(timeString);
/*    */   }
/*    */   
/*    */   private SqlTimeTypeAdapter() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\sql\SqlTimeTypeAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */