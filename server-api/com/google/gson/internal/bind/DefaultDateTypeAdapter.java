/*     */ package com.google.gson.internal.bind;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.TypeAdapterFactory;
/*     */ import com.google.gson.internal.JavaVersion;
/*     */ import com.google.gson.internal.PreJava9DateFormatProvider;
/*     */ import com.google.gson.internal.bind.util.ISO8601Utils;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.ParsePosition;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DefaultDateTypeAdapter<T extends Date>
/*     */   extends TypeAdapter<T>
/*     */ {
/*     */   private static final String SIMPLE_NAME = "DefaultDateTypeAdapter";
/*     */   
/*  59 */   public static final TypeAdapterFactory DEFAULT_STYLE_FACTORY = new TypeAdapterFactory()
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken)
/*     */       {
/*  69 */         return (typeToken.getRawType() == Date.class) ? 
/*  70 */           new DefaultDateTypeAdapter<>(DefaultDateTypeAdapter.DateType.DATE, 2, 2) : 
/*     */ 
/*     */           
/*  73 */           null;
/*     */       }
/*     */ 
/*     */       
/*     */       public String toString() {
/*  78 */         return "DefaultDateTypeAdapter#DEFAULT_STYLE_FACTORY";
/*     */       }
/*     */     };
/*     */   private final DateType<T> dateType;
/*     */   
/*  83 */   public static abstract class DateType<T extends Date> { public static final DateType<Date> DATE = new DateType<Date>(Date.class)
/*     */       {
/*     */         protected Date deserialize(Date date)
/*     */         {
/*  87 */           return date;
/*     */         }
/*     */       };
/*     */     
/*     */     private final Class<T> dateClass;
/*     */     
/*     */     protected DateType(Class<T> dateClass) {
/*  94 */       this.dateClass = dateClass;
/*     */     }
/*     */     
/*     */     protected abstract T deserialize(Date param1Date);
/*     */     
/*     */     private TypeAdapterFactory createFactory(DefaultDateTypeAdapter<T> adapter) {
/* 100 */       return TypeAdapters.newFactory(this.dateClass, adapter);
/*     */     }
/*     */     
/*     */     public final TypeAdapterFactory createAdapterFactory(String datePattern) {
/* 104 */       return createFactory(new DefaultDateTypeAdapter<>(this, datePattern));
/*     */     }
/*     */     
/*     */     public final TypeAdapterFactory createAdapterFactory(int dateStyle, int timeStyle) {
/* 108 */       return createFactory(new DefaultDateTypeAdapter<>(this, dateStyle, timeStyle));
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   private final List<DateFormat> dateFormats = new ArrayList<>();
/*     */   
/*     */   private DefaultDateTypeAdapter(DateType<T> dateType, String datePattern) {
/* 121 */     this.dateType = Objects.<DateType<T>>requireNonNull(dateType);
/* 122 */     this.dateFormats.add(new SimpleDateFormat(datePattern, Locale.US));
/* 123 */     if (!Locale.getDefault().equals(Locale.US)) {
/* 124 */       this.dateFormats.add(new SimpleDateFormat(datePattern));
/*     */     }
/*     */   }
/*     */   
/*     */   private DefaultDateTypeAdapter(DateType<T> dateType, int dateStyle, int timeStyle) {
/* 129 */     this.dateType = Objects.<DateType<T>>requireNonNull(dateType);
/* 130 */     this.dateFormats.add(DateFormat.getDateTimeInstance(dateStyle, timeStyle, Locale.US));
/* 131 */     if (!Locale.getDefault().equals(Locale.US)) {
/* 132 */       this.dateFormats.add(DateFormat.getDateTimeInstance(dateStyle, timeStyle));
/*     */     }
/* 134 */     if (JavaVersion.isJava9OrLater()) {
/* 135 */       this.dateFormats.add(PreJava9DateFormatProvider.getUsDateTimeFormat(dateStyle, timeStyle));
/*     */     }
/*     */   }
/*     */   
/*     */   public void write(JsonWriter out, Date value) throws IOException {
/*     */     String dateFormatAsString;
/* 141 */     if (value == null) {
/* 142 */       out.nullValue();
/*     */       
/*     */       return;
/*     */     } 
/* 146 */     DateFormat dateFormat = this.dateFormats.get(0);
/*     */ 
/*     */     
/* 149 */     synchronized (this.dateFormats) {
/* 150 */       dateFormatAsString = dateFormat.format(value);
/*     */     } 
/* 152 */     out.value(dateFormatAsString);
/*     */   }
/*     */ 
/*     */   
/*     */   public T read(JsonReader in) throws IOException {
/* 157 */     if (in.peek() == JsonToken.NULL) {
/* 158 */       in.nextNull();
/* 159 */       return null;
/*     */     } 
/* 161 */     Date date = deserializeToDate(in);
/* 162 */     return this.dateType.deserialize(date);
/*     */   }
/*     */   
/*     */   private Date deserializeToDate(JsonReader in) throws IOException {
/* 166 */     String s = in.nextString();
/*     */     
/* 168 */     synchronized (this.dateFormats) {
/* 169 */       for (DateFormat dateFormat : this.dateFormats) {
/* 170 */         TimeZone originalTimeZone = dateFormat.getTimeZone();
/*     */         try {
/* 172 */           return dateFormat.parse(s);
/* 173 */         } catch (ParseException parseException) {
/*     */         
/*     */         } finally {
/* 176 */           dateFormat.setTimeZone(originalTimeZone);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/* 182 */       return ISO8601Utils.parse(s, new ParsePosition(0));
/* 183 */     } catch (ParseException e) {
/* 184 */       throw new JsonSyntaxException("Failed parsing '" + s + "' as Date; at path " + in
/* 185 */           .getPreviousPath(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 191 */     DateFormat defaultFormat = this.dateFormats.get(0);
/* 192 */     if (defaultFormat instanceof SimpleDateFormat) {
/* 193 */       return "DefaultDateTypeAdapter(" + ((SimpleDateFormat)defaultFormat).toPattern() + ')';
/*     */     }
/* 195 */     return "DefaultDateTypeAdapter(" + defaultFormat.getClass().getSimpleName() + ')';
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\bind\DefaultDateTypeAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */