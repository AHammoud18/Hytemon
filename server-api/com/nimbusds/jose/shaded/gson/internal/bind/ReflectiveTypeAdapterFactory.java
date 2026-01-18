/*     */ package com.nimbusds.jose.shaded.gson.internal.bind;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.gson.FieldNamingStrategy;
/*     */ import com.nimbusds.jose.shaded.gson.Gson;
/*     */ import com.nimbusds.jose.shaded.gson.JsonIOException;
/*     */ import com.nimbusds.jose.shaded.gson.JsonParseException;
/*     */ import com.nimbusds.jose.shaded.gson.JsonSyntaxException;
/*     */ import com.nimbusds.jose.shaded.gson.ReflectionAccessFilter;
/*     */ import com.nimbusds.jose.shaded.gson.TypeAdapter;
/*     */ import com.nimbusds.jose.shaded.gson.TypeAdapterFactory;
/*     */ import com.nimbusds.jose.shaded.gson.annotations.JsonAdapter;
/*     */ import com.nimbusds.jose.shaded.gson.annotations.SerializedName;
/*     */ import com.nimbusds.jose.shaded.gson.internal.ConstructorConstructor;
/*     */ import com.nimbusds.jose.shaded.gson.internal.Excluder;
/*     */ import com.nimbusds.jose.shaded.gson.internal.GsonTypes;
/*     */ import com.nimbusds.jose.shaded.gson.internal.ObjectConstructor;
/*     */ import com.nimbusds.jose.shaded.gson.internal.Primitives;
/*     */ import com.nimbusds.jose.shaded.gson.internal.ReflectionAccessFilterHelper;
/*     */ import com.nimbusds.jose.shaded.gson.internal.TroubleshootingGuide;
/*     */ import com.nimbusds.jose.shaded.gson.internal.reflect.ReflectionHelper;
/*     */ import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
/*     */ import com.nimbusds.jose.shaded.gson.stream.JsonReader;
/*     */ import com.nimbusds.jose.shaded.gson.stream.JsonToken;
/*     */ import com.nimbusds.jose.shaded.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ReflectiveTypeAdapterFactory
/*     */   implements TypeAdapterFactory
/*     */ {
/*     */   private final ConstructorConstructor constructorConstructor;
/*     */   private final FieldNamingStrategy fieldNamingPolicy;
/*     */   private final Excluder excluder;
/*     */   private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
/*     */   private final List<ReflectionAccessFilter> reflectionFilters;
/*     */   
/*     */   public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingPolicy, Excluder excluder, JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory, List<ReflectionAccessFilter> reflectionFilters) {
/*  73 */     this.constructorConstructor = constructorConstructor;
/*  74 */     this.fieldNamingPolicy = fieldNamingPolicy;
/*  75 */     this.excluder = excluder;
/*  76 */     this.jsonAdapterFactory = jsonAdapterFactory;
/*  77 */     this.reflectionFilters = reflectionFilters;
/*     */   }
/*     */   
/*     */   private boolean includeField(Field f, boolean serialize) {
/*  81 */     return !this.excluder.excludeField(f, serialize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> getFieldNames(Field f) {
/*     */     String fieldName;
/*     */     List<String> alternates;
/*  90 */     SerializedName annotation = f.<SerializedName>getAnnotation(SerializedName.class);
/*  91 */     if (annotation == null) {
/*  92 */       fieldName = this.fieldNamingPolicy.translateName(f);
/*  93 */       alternates = this.fieldNamingPolicy.alternateNames(f);
/*     */     } else {
/*  95 */       fieldName = annotation.value();
/*  96 */       alternates = Arrays.asList(annotation.alternate());
/*     */     } 
/*     */     
/*  99 */     if (alternates.isEmpty()) {
/* 100 */       return Collections.singletonList(fieldName);
/*     */     }
/*     */     
/* 103 */     List<String> fieldNames = new ArrayList<>(alternates.size() + 1);
/* 104 */     fieldNames.add(fieldName);
/* 105 */     fieldNames.addAll(alternates);
/* 106 */     return fieldNames;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
/* 111 */     Class<? super T> raw = type.getRawType();
/*     */     
/* 113 */     if (!Object.class.isAssignableFrom(raw)) {
/* 114 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 119 */     if (ReflectionHelper.isAnonymousOrNonStaticLocal(raw))
/*     */     {
/*     */ 
/*     */       
/* 123 */       return new TypeAdapter<T>()
/*     */         {
/*     */           public T read(JsonReader in) throws IOException {
/* 126 */             in.skipValue();
/* 127 */             return null;
/*     */           }
/*     */ 
/*     */           
/*     */           public void write(JsonWriter out, T value) throws IOException {
/* 132 */             out.nullValue();
/*     */           }
/*     */ 
/*     */           
/*     */           public String toString() {
/* 137 */             return "AnonymousOrNonStaticLocalClassAdapter";
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/* 143 */     ReflectionAccessFilter.FilterResult filterResult = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, raw);
/* 144 */     if (filterResult == ReflectionAccessFilter.FilterResult.BLOCK_ALL) {
/* 145 */       throw new JsonIOException("ReflectionAccessFilter does not permit using reflection for " + raw + ". Register a TypeAdapter for this type or adjust the access filter.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 150 */     boolean blockInaccessible = (filterResult == ReflectionAccessFilter.FilterResult.BLOCK_INACCESSIBLE);
/*     */ 
/*     */ 
/*     */     
/* 154 */     if (ReflectionHelper.isRecord(raw)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 159 */       TypeAdapter<T> adapter = new RecordAdapter<>((Class)raw, getBoundFields(gson, type, raw, blockInaccessible, true), blockInaccessible);
/* 160 */       return adapter;
/*     */     } 
/*     */     
/* 163 */     ObjectConstructor<T> constructor = this.constructorConstructor.get(type, true);
/* 164 */     return new FieldReflectionAdapter<>(constructor, 
/* 165 */         getBoundFields(gson, type, raw, blockInaccessible, false));
/*     */   }
/*     */ 
/*     */   
/*     */   private static <M extends AccessibleObject & Member> void checkAccessible(Object object, M member) {
/* 170 */     if (!ReflectionAccessFilterHelper.canAccess((AccessibleObject)member, 
/* 171 */         Modifier.isStatic(((Member)member).getModifiers()) ? null : object)) {
/* 172 */       String memberDescription = ReflectionHelper.getAccessibleObjectDescription((AccessibleObject)member, true);
/* 173 */       throw new JsonIOException(memberDescription + " is not accessible and ReflectionAccessFilter does not permit making it accessible. Register a TypeAdapter for the declaring type, adjust the access filter or increase the visibility of the element and its declaring type.");
/*     */     } 
/*     */   }
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
/*     */   private BoundField createBoundField(Gson context, Field field, final Method accessor, String serializedName, TypeToken<?> fieldType, boolean serialize, final boolean blockInaccessible) {
/*     */     final TypeAdapter<Object> writeTypeAdapter;
/* 190 */     final boolean isPrimitive = Primitives.isPrimitive(fieldType.getRawType());
/*     */     
/* 192 */     int modifiers = field.getModifiers();
/* 193 */     final boolean isStaticFinalField = (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers));
/*     */     
/* 195 */     JsonAdapter annotation = field.<JsonAdapter>getAnnotation(JsonAdapter.class);
/* 196 */     TypeAdapter<?> mapped = null;
/* 197 */     if (annotation != null)
/*     */     {
/*     */       
/* 200 */       mapped = this.jsonAdapterFactory.getTypeAdapter(this.constructorConstructor, context, fieldType, annotation, false);
/*     */     }
/*     */     
/* 203 */     boolean jsonAdapterPresent = (mapped != null);
/* 204 */     if (mapped == null) {
/* 205 */       mapped = context.getAdapter(fieldType);
/*     */     }
/*     */ 
/*     */     
/* 209 */     final TypeAdapter<Object> typeAdapter = (TypeAdapter)mapped;
/*     */     
/* 211 */     if (serialize) {
/*     */ 
/*     */ 
/*     */       
/* 215 */       writeTypeAdapter = jsonAdapterPresent ? typeAdapter : new TypeAdapterRuntimeTypeWrapper(context, typeAdapter, fieldType.getType());
/*     */     } else {
/*     */       
/* 218 */       writeTypeAdapter = typeAdapter;
/*     */     } 
/* 220 */     return new BoundField(serializedName, field) {
/*     */         void write(JsonWriter writer, Object source) throws IOException, IllegalAccessException {
/*     */           Object fieldValue;
/* 223 */           if (blockInaccessible) {
/* 224 */             if (accessor == null) {
/* 225 */               ReflectiveTypeAdapterFactory.checkAccessible(source, (M)this.field);
/*     */             }
/*     */             else {
/*     */               
/* 229 */               ReflectiveTypeAdapterFactory.checkAccessible(source, (M)accessor);
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/* 234 */           if (accessor != null) {
/*     */             try {
/* 236 */               fieldValue = accessor.invoke(source, new Object[0]);
/* 237 */             } catch (InvocationTargetException e) {
/*     */               
/* 239 */               String accessorDescription = ReflectionHelper.getAccessibleObjectDescription(accessor, false);
/* 240 */               throw new JsonIOException("Accessor " + accessorDescription + " threw exception", e
/* 241 */                   .getCause());
/*     */             } 
/*     */           } else {
/* 244 */             fieldValue = this.field.get(source);
/*     */           } 
/* 246 */           if (fieldValue == source) {
/*     */             return;
/*     */           }
/*     */           
/* 250 */           writer.name(this.serializedName);
/* 251 */           writeTypeAdapter.write(writer, fieldValue);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         void readIntoArray(JsonReader reader, int index, Object[] target) throws IOException, JsonParseException {
/* 257 */           Object fieldValue = typeAdapter.read(reader);
/* 258 */           if (fieldValue == null && isPrimitive) {
/* 259 */             throw new JsonParseException("null is not allowed as value for record component '" + this.fieldName + "' of primitive type; at path " + reader
/*     */ 
/*     */ 
/*     */                 
/* 263 */                 .getPath());
/*     */           }
/* 265 */           target[index] = fieldValue;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         void readIntoField(JsonReader reader, Object target) throws IOException, IllegalAccessException {
/* 271 */           Object fieldValue = typeAdapter.read(reader);
/* 272 */           if (fieldValue != null || !isPrimitive) {
/* 273 */             if (blockInaccessible) {
/* 274 */               ReflectiveTypeAdapterFactory.checkAccessible(target, (M)this.field);
/* 275 */             } else if (isStaticFinalField) {
/*     */ 
/*     */ 
/*     */               
/* 279 */               String fieldDescription = ReflectionHelper.getAccessibleObjectDescription(this.field, false);
/* 280 */               throw new JsonIOException("Cannot set value of 'static final' " + fieldDescription);
/*     */             } 
/* 282 */             this.field.set(target, fieldValue);
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private static class FieldsData {
/* 289 */     public static final FieldsData EMPTY = new FieldsData(
/* 290 */         Collections.emptyMap(), Collections.emptyList());
/*     */ 
/*     */     
/*     */     public final Map<String, ReflectiveTypeAdapterFactory.BoundField> deserializedFields;
/*     */     
/*     */     public final List<ReflectiveTypeAdapterFactory.BoundField> serializedFields;
/*     */ 
/*     */     
/*     */     public FieldsData(Map<String, ReflectiveTypeAdapterFactory.BoundField> deserializedFields, List<ReflectiveTypeAdapterFactory.BoundField> serializedFields) {
/* 299 */       this.deserializedFields = deserializedFields;
/* 300 */       this.serializedFields = serializedFields;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static IllegalArgumentException createDuplicateFieldException(Class<?> declaringType, String duplicateName, Field field1, Field field2) {
/* 306 */     throw new IllegalArgumentException("Class " + declaringType
/*     */         
/* 308 */         .getName() + " declares multiple JSON fields named '" + duplicateName + "'; conflict is caused by fields " + 
/*     */ 
/*     */ 
/*     */         
/* 312 */         ReflectionHelper.fieldToString(field1) + " and " + 
/*     */         
/* 314 */         ReflectionHelper.fieldToString(field2) + "\nSee " + 
/*     */         
/* 316 */         TroubleshootingGuide.createUrl("duplicate-fields"));
/*     */   }
/*     */ 
/*     */   
/*     */   private FieldsData getBoundFields(Gson context, TypeToken<?> type, Class<?> raw, boolean blockInaccessible, boolean isRecord) {
/* 321 */     if (raw.isInterface()) {
/* 322 */       return FieldsData.EMPTY;
/*     */     }
/*     */     
/* 325 */     Map<String, BoundField> deserializedFields = new LinkedHashMap<>();
/*     */ 
/*     */     
/* 328 */     Map<String, BoundField> serializedFields = new LinkedHashMap<>();
/*     */     
/* 330 */     Class<?> originalRaw = raw;
/* 331 */     while (raw != Object.class) {
/* 332 */       Field[] fields = raw.getDeclaredFields();
/*     */ 
/*     */       
/* 335 */       if (raw != originalRaw && fields.length > 0) {
/*     */         
/* 337 */         ReflectionAccessFilter.FilterResult filterResult = ReflectionAccessFilterHelper.getFilterResult(this.reflectionFilters, raw);
/* 338 */         if (filterResult == ReflectionAccessFilter.FilterResult.BLOCK_ALL) {
/* 339 */           throw new JsonIOException("ReflectionAccessFilter does not permit using reflection for " + raw + " (supertype of " + originalRaw + "). Register a TypeAdapter for this type or adjust the access filter.");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 346 */         blockInaccessible = (filterResult == ReflectionAccessFilter.FilterResult.BLOCK_INACCESSIBLE);
/*     */       } 
/*     */       
/* 349 */       for (Field field : fields) {
/* 350 */         boolean serialize = includeField(field, true);
/* 351 */         boolean deserialize = includeField(field, false);
/* 352 */         if (serialize || deserialize) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 358 */           Method accessor = null;
/* 359 */           if (isRecord)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 365 */             if (Modifier.isStatic(field.getModifiers())) {
/* 366 */               deserialize = false;
/*     */             } else {
/* 368 */               accessor = ReflectionHelper.getAccessor(raw, field);
/*     */               
/* 370 */               if (!blockInaccessible) {
/* 371 */                 ReflectionHelper.makeAccessible(accessor);
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 378 */               if (accessor.getAnnotation(SerializedName.class) != null && field
/* 379 */                 .getAnnotation(SerializedName.class) == null) {
/*     */                 
/* 381 */                 String methodDescription = ReflectionHelper.getAccessibleObjectDescription(accessor, false);
/* 382 */                 throw new JsonIOException("@SerializedName on " + methodDescription + " is not supported");
/*     */               } 
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 390 */           if (!blockInaccessible && accessor == null) {
/* 391 */             ReflectionHelper.makeAccessible(field);
/*     */           }
/*     */           
/* 394 */           Type fieldType = GsonTypes.resolve(type.getType(), raw, field.getGenericType());
/* 395 */           List<String> fieldNames = getFieldNames(field);
/* 396 */           String serializedName = fieldNames.get(0);
/*     */           
/* 398 */           BoundField boundField = createBoundField(context, field, accessor, serializedName, 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 403 */               TypeToken.get(fieldType), serialize, blockInaccessible);
/*     */ 
/*     */ 
/*     */           
/* 407 */           if (deserialize) {
/* 408 */             for (String name : fieldNames) {
/* 409 */               BoundField replaced = deserializedFields.put(name, boundField);
/*     */               
/* 411 */               if (replaced != null) {
/* 412 */                 throw createDuplicateFieldException(originalRaw, name, replaced.field, field);
/*     */               }
/*     */             } 
/*     */           }
/*     */           
/* 417 */           if (serialize) {
/* 418 */             BoundField replaced = serializedFields.put(serializedName, boundField);
/* 419 */             if (replaced != null)
/* 420 */               throw createDuplicateFieldException(originalRaw, serializedName, replaced.field, field); 
/*     */           } 
/*     */         } 
/*     */       } 
/* 424 */       type = TypeToken.get(GsonTypes.resolve(type.getType(), raw, raw.getGenericSuperclass()));
/* 425 */       raw = type.getRawType();
/*     */     } 
/* 427 */     return new FieldsData(deserializedFields, new ArrayList<>(serializedFields.values()));
/*     */   }
/*     */ 
/*     */   
/*     */   static abstract class BoundField
/*     */   {
/*     */     final String serializedName;
/*     */     
/*     */     final Field field;
/*     */     
/*     */     final String fieldName;
/*     */     
/*     */     protected BoundField(String serializedName, Field field) {
/* 440 */       this.serializedName = serializedName;
/* 441 */       this.field = field;
/* 442 */       this.fieldName = field.getName();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract void write(JsonWriter param1JsonWriter, Object param1Object) throws IOException, IllegalAccessException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract void readIntoArray(JsonReader param1JsonReader, int param1Int, Object[] param1ArrayOfObject) throws IOException, JsonParseException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract void readIntoField(JsonReader param1JsonReader, Object param1Object) throws IOException, IllegalAccessException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class Adapter<T, A>
/*     */     extends TypeAdapter<T>
/*     */   {
/*     */     private final ReflectiveTypeAdapterFactory.FieldsData fieldsData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Adapter(ReflectiveTypeAdapterFactory.FieldsData fieldsData) {
/* 480 */       this.fieldsData = fieldsData;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(JsonWriter out, T value) throws IOException {
/* 485 */       if (value == null) {
/* 486 */         out.nullValue();
/*     */         
/*     */         return;
/*     */       } 
/* 490 */       out.beginObject();
/*     */       try {
/* 492 */         for (ReflectiveTypeAdapterFactory.BoundField boundField : this.fieldsData.serializedFields) {
/* 493 */           boundField.write(out, value);
/*     */         }
/* 495 */       } catch (IllegalAccessException e) {
/* 496 */         throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e);
/*     */       } 
/* 498 */       out.endObject();
/*     */     }
/*     */ 
/*     */     
/*     */     public T read(JsonReader in) throws IOException {
/* 503 */       if (in.peek() == JsonToken.NULL) {
/* 504 */         in.nextNull();
/* 505 */         return null;
/*     */       } 
/*     */       
/* 508 */       A accumulator = createAccumulator();
/* 509 */       Map<String, ReflectiveTypeAdapterFactory.BoundField> deserializedFields = this.fieldsData.deserializedFields;
/*     */       
/*     */       try {
/* 512 */         in.beginObject();
/* 513 */         while (in.hasNext()) {
/* 514 */           String name = in.nextName();
/* 515 */           ReflectiveTypeAdapterFactory.BoundField field = deserializedFields.get(name);
/* 516 */           if (field == null) {
/* 517 */             in.skipValue(); continue;
/*     */           } 
/* 519 */           readField(accumulator, in, field);
/*     */         }
/*     */       
/* 522 */       } catch (IllegalStateException e) {
/* 523 */         throw new JsonSyntaxException(e);
/* 524 */       } catch (IllegalAccessException e) {
/* 525 */         throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e);
/*     */       } 
/* 527 */       in.endObject();
/* 528 */       return finalize(accumulator);
/*     */     }
/*     */ 
/*     */     
/*     */     abstract A createAccumulator();
/*     */ 
/*     */     
/*     */     abstract void readField(A param1A, JsonReader param1JsonReader, ReflectiveTypeAdapterFactory.BoundField param1BoundField) throws IllegalAccessException, IOException;
/*     */ 
/*     */     
/*     */     abstract T finalize(A param1A);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class FieldReflectionAdapter<T>
/*     */     extends Adapter<T, T>
/*     */   {
/*     */     private final ObjectConstructor<T> constructor;
/*     */ 
/*     */     
/*     */     FieldReflectionAdapter(ObjectConstructor<T> constructor, ReflectiveTypeAdapterFactory.FieldsData fieldsData) {
/* 549 */       super(fieldsData);
/* 550 */       this.constructor = constructor;
/*     */     }
/*     */ 
/*     */     
/*     */     T createAccumulator() {
/* 555 */       return (T)this.constructor.construct();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void readField(T accumulator, JsonReader in, ReflectiveTypeAdapterFactory.BoundField field) throws IllegalAccessException, IOException {
/* 561 */       field.readIntoField(in, accumulator);
/*     */     }
/*     */ 
/*     */     
/*     */     T finalize(T accumulator) {
/* 566 */       return accumulator;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class RecordAdapter<T> extends Adapter<T, Object[]> {
/* 571 */     static final Map<Class<?>, Object> PRIMITIVE_DEFAULTS = primitiveDefaults();
/*     */ 
/*     */     
/*     */     private final Constructor<T> constructor;
/*     */     
/*     */     private final Object[] constructorArgsDefaults;
/*     */     
/* 578 */     private final Map<String, Integer> componentIndices = new HashMap<>();
/*     */     
/*     */     RecordAdapter(Class<T> raw, ReflectiveTypeAdapterFactory.FieldsData fieldsData, boolean blockInaccessible) {
/* 581 */       super(fieldsData);
/* 582 */       this.constructor = ReflectionHelper.getCanonicalRecordConstructor(raw);
/*     */       
/* 584 */       if (blockInaccessible) {
/* 585 */         ReflectiveTypeAdapterFactory.checkAccessible(null, (M)this.constructor);
/*     */       } else {
/*     */         
/* 588 */         ReflectionHelper.makeAccessible(this.constructor);
/*     */       } 
/*     */       
/* 591 */       String[] componentNames = ReflectionHelper.getRecordComponentNames(raw);
/* 592 */       for (int i = 0; i < componentNames.length; i++) {
/* 593 */         this.componentIndices.put(componentNames[i], Integer.valueOf(i));
/*     */       }
/* 595 */       Class<?>[] parameterTypes = this.constructor.getParameterTypes();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 600 */       this.constructorArgsDefaults = new Object[parameterTypes.length];
/* 601 */       for (int j = 0; j < parameterTypes.length; j++)
/*     */       {
/* 603 */         this.constructorArgsDefaults[j] = PRIMITIVE_DEFAULTS.get(parameterTypes[j]);
/*     */       }
/*     */     }
/*     */     
/*     */     private static Map<Class<?>, Object> primitiveDefaults() {
/* 608 */       Map<Class<?>, Object> zeroes = new HashMap<>();
/* 609 */       zeroes.put(byte.class, Byte.valueOf((byte)0));
/* 610 */       zeroes.put(short.class, Short.valueOf((short)0));
/* 611 */       zeroes.put(int.class, Integer.valueOf(0));
/* 612 */       zeroes.put(long.class, Long.valueOf(0L));
/* 613 */       zeroes.put(float.class, Float.valueOf(0.0F));
/* 614 */       zeroes.put(double.class, Double.valueOf(0.0D));
/* 615 */       zeroes.put(char.class, Character.valueOf(false));
/* 616 */       zeroes.put(boolean.class, Boolean.valueOf(false));
/* 617 */       return zeroes;
/*     */     }
/*     */ 
/*     */     
/*     */     Object[] createAccumulator() {
/* 622 */       return (Object[])this.constructorArgsDefaults.clone();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void readField(Object[] accumulator, JsonReader in, ReflectiveTypeAdapterFactory.BoundField field) throws IOException {
/* 628 */       Integer componentIndex = this.componentIndices.get(field.fieldName);
/* 629 */       if (componentIndex == null) {
/* 630 */         throw new IllegalStateException("Could not find the index in the constructor '" + 
/*     */             
/* 632 */             ReflectionHelper.constructorToString(this.constructor) + "' for field with name '" + field.fieldName + "', unable to determine which argument in the constructor the field corresponds to. This is unexpected behavior, as we expect the RecordComponents to have the same names as the fields in the Java class, and that the order of the RecordComponents is the same as the order of the canonical constructor parameters.");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 641 */       field.readIntoArray(in, componentIndex.intValue(), accumulator);
/*     */     }
/*     */ 
/*     */     
/*     */     T finalize(Object[] accumulator) {
/*     */       try {
/* 647 */         return this.constructor.newInstance(accumulator);
/* 648 */       } catch (IllegalAccessException e) {
/* 649 */         throw ReflectionHelper.createExceptionForUnexpectedIllegalAccess(e);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 654 */       catch (InstantiationException|IllegalArgumentException e) {
/* 655 */         throw new RuntimeException("Failed to invoke constructor '" + 
/*     */             
/* 657 */             ReflectionHelper.constructorToString(this.constructor) + "' with args " + 
/*     */             
/* 659 */             Arrays.toString(accumulator), e);
/*     */       }
/* 661 */       catch (InvocationTargetException e) {
/*     */         
/* 663 */         throw new RuntimeException("Failed to invoke constructor '" + 
/*     */             
/* 665 */             ReflectionHelper.constructorToString(this.constructor) + "' with args " + 
/*     */             
/* 667 */             Arrays.toString(accumulator), e
/* 668 */             .getCause());
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\internal\bind\ReflectiveTypeAdapterFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */