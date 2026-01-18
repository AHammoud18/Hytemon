/*     */ package com.hypixel.hytale.codec.schema;
/*     */ 
/*     */ import com.hypixel.hytale.codec.EmptyExtraInfo;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.schema.config.NullSchema;
/*     */ import com.hypixel.hytale.codec.schema.config.Schema;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SchemaContext
/*     */ {
/*     */   static {
/*  25 */     Schema.init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  33 */   private final Map<String, Schema> definitions = (Map<String, Schema>)new Object2ObjectLinkedOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  41 */   private final Map<String, Schema> otherDefinitions = (Map<String, Schema>)new Object2ObjectLinkedOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  47 */   private final Map<Object, String> nameMap = (Map<Object, String>)new Object2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  53 */   private final Object2IntMap<String> nameCollisionCount = (Object2IntMap<String>)new Object2IntOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  59 */   private final Map<SchemaConvertable<?>, String> fileReferences = (Map<SchemaConvertable<?>, String>)new Object2ObjectOpenHashMap();
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
/*     */   public void addFileReference(@Nonnull String fileName, @Nonnull SchemaConvertable<?> codec) {
/*  78 */     this.fileReferences.put(codec, fileName + "#");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Schema getFileReference(@Nonnull SchemaConvertable<?> codec) {
/*  90 */     String file = this.fileReferences.get(codec);
/*  91 */     if (file != null) {
/*  92 */       return Schema.ref(file);
/*     */     }
/*  94 */     return null;
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
/*     */   @Nonnull
/*     */   public Schema refDefinition(@Nonnull SchemaConvertable<?> codec) {
/* 107 */     return refDefinition(codec, null);
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
/*     */   @Nonnull
/*     */   public <T> Schema refDefinition(@Nonnull SchemaConvertable<T> convertable, @Nullable T def) {
/* 122 */     Schema ref = getFileReference(convertable);
/* 123 */     if (ref != null) return ref;
/*     */     
/* 125 */     if (convertable instanceof BuilderCodec) { BuilderCodec<T> builderCodec = (BuilderCodec<T>)convertable;
/*     */       
/* 127 */       String name = resolveName(builderCodec);
/* 128 */       if (!this.definitions.containsKey(name)) {
/*     */ 
/*     */         
/* 131 */         this.definitions.put(name, NullSchema.INSTANCE);
/*     */         
/* 133 */         this.definitions.put(name, convertable.toSchema(this));
/*     */       } 
/*     */       
/* 136 */       Schema c = Schema.ref("common.json#/definitions/" + name);
/* 137 */       if (def != null) {
/* 138 */         c.setDefaultRaw(builderCodec.encode(def, (ExtraInfo)EmptyExtraInfo.EMPTY));
/*     */       }
/* 140 */       return c; }
/*     */ 
/*     */     
/* 143 */     if (convertable instanceof NamedSchema) { NamedSchema namedSchema = (NamedSchema)convertable;
/*     */       
/* 145 */       String name = resolveName(namedSchema);
/* 146 */       if (!this.otherDefinitions.containsKey(name)) {
/*     */ 
/*     */         
/* 149 */         this.otherDefinitions.put(name, NullSchema.INSTANCE);
/*     */         
/* 151 */         this.otherDefinitions.put(name, convertable.toSchema(this));
/*     */       } 
/* 153 */       return Schema.ref("other.json#/definitions/" + name); }
/*     */ 
/*     */     
/* 156 */     return convertable.toSchema(this, def);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Schema getRawDefinition(@Nonnull BuilderCodec<?> codec) {
/* 167 */     String name = resolveName(codec);
/* 168 */     return this.definitions.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Schema getRawDefinition(@Nonnull NamedSchema namedSchema) {
/* 179 */     return this.otherDefinitions.get(resolveName(namedSchema));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, Schema> getDefinitions() {
/* 187 */     return this.definitions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, Schema> getOtherDefinitions() {
/* 195 */     return this.otherDefinitions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String resolveName(@Nonnull NamedSchema namedSchema) {
/* 205 */     return this.nameMap.computeIfAbsent(namedSchema, key -> {
/*     */           String n = ((NamedSchema)key).getSchemaName();
/*     */           int count = this.nameCollisionCount.getInt(n);
/*     */           this.nameCollisionCount.put(n, count + 1);
/*     */           return (count > 0) ? (n + "@" + n) : n;
/*     */         });
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
/*     */   @Nonnull
/*     */   private String resolveName(@Nonnull BuilderCodec<?> codec) {
/* 224 */     return this.nameMap.computeIfAbsent(codec.getInnerClass(), key -> {
/*     */           String n = ((Class)key).getSimpleName();
/*     */           int count = this.nameCollisionCount.getInt(n);
/*     */           this.nameCollisionCount.put(n, count + 1);
/*     */           return (count > 0) ? (n + "@" + n) : n;
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\SchemaContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */