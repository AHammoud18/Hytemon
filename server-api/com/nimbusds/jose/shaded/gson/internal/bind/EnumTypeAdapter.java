/*     */ package com.nimbusds.jose.shaded.gson.internal.bind;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.gson.Gson;
/*     */ import com.nimbusds.jose.shaded.gson.TypeAdapter;
/*     */ import com.nimbusds.jose.shaded.gson.TypeAdapterFactory;
/*     */ import com.nimbusds.jose.shaded.gson.annotations.SerializedName;
/*     */ import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
/*     */ import com.nimbusds.jose.shaded.gson.stream.JsonReader;
/*     */ import com.nimbusds.jose.shaded.gson.stream.JsonToken;
/*     */ import com.nimbusds.jose.shaded.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ class EnumTypeAdapter<T extends Enum<T>>
/*     */   extends TypeAdapter<T>
/*     */ {
/*  36 */   static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
/*     */     {
/*     */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken)
/*     */       {
/*  40 */         Class<? super T> rawType = typeToken.getRawType();
/*  41 */         if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class) {
/*  42 */           return null;
/*     */         }
/*  44 */         if (!rawType.isEnum()) {
/*  45 */           rawType = rawType.getSuperclass();
/*     */         }
/*     */         
/*  48 */         TypeAdapter<T> adapter = new EnumTypeAdapter<>(rawType);
/*  49 */         return adapter;
/*     */       }
/*     */     };
/*     */   
/*  53 */   private final Map<String, T> nameToConstant = new HashMap<>();
/*  54 */   private final Map<String, T> stringToConstant = new HashMap<>();
/*  55 */   private final Map<T, String> constantToName = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private EnumTypeAdapter(Class<T> classOfT) {
/*     */     try {
/*  61 */       Field[] fields = classOfT.getDeclaredFields();
/*  62 */       int constantCount = 0;
/*  63 */       for (Field f : fields) {
/*     */         
/*  65 */         if (f.isEnumConstant()) {
/*  66 */           fields[constantCount++] = f;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  72 */       fields = Arrays.<Field>copyOf(fields, constantCount);
/*     */       
/*  74 */       AccessibleObject.setAccessible((AccessibleObject[])fields, true);
/*     */       
/*  76 */       for (Field constantField : fields) {
/*     */         
/*  78 */         Enum enum_ = (Enum)constantField.get(null);
/*  79 */         String name = enum_.name();
/*  80 */         String toStringVal = enum_.toString();
/*     */         
/*  82 */         SerializedName annotation = constantField.<SerializedName>getAnnotation(SerializedName.class);
/*  83 */         if (annotation != null) {
/*  84 */           name = annotation.value();
/*  85 */           for (String alternate : annotation.alternate()) {
/*  86 */             this.nameToConstant.put(alternate, (T)enum_);
/*     */           }
/*     */         } 
/*  89 */         this.nameToConstant.put(name, (T)enum_);
/*  90 */         this.stringToConstant.put(toStringVal, (T)enum_);
/*  91 */         this.constantToName.put((T)enum_, name);
/*     */       } 
/*  93 */     } catch (IllegalAccessException e) {
/*     */ 
/*     */       
/*  96 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public T read(JsonReader in) throws IOException {
/* 102 */     if (in.peek() == JsonToken.NULL) {
/* 103 */       in.nextNull();
/* 104 */       return null;
/*     */     } 
/* 106 */     String key = in.nextString();
/* 107 */     Enum enum_ = (Enum)this.nameToConstant.get(key);
/*     */     
/* 109 */     return (enum_ == null) ? this.stringToConstant.get(key) : (T)enum_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JsonWriter out, T value) throws IOException {
/* 114 */     out.value((value == null) ? null : this.constantToName.get(value));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\internal\bind\EnumTypeAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */