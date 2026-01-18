/*     */ package com.hypixel.hytale.server.npc.asset.builder;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.hypixel.hytale.codec.schema.NamedSchema;
/*     */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*     */ import com.hypixel.hytale.codec.schema.SchemaConvertable;
/*     */ import com.hypixel.hytale.codec.schema.config.ObjectSchema;
/*     */ import com.hypixel.hytale.codec.schema.config.Schema;
/*     */ import com.hypixel.hytale.codec.schema.config.StringSchema;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BuilderFactory<T>
/*     */   implements SchemaConvertable<Void>, NamedSchema
/*     */ {
/*     */   public static final String DEFAULT_TYPE = "Type";
/*     */   public static final String COMPONENT_TYPE = "Component";
/*     */   private final String typeTag;
/*     */   private final Supplier<Builder<T>> defaultBuilder;
/*     */   private final Class<T> category;
/*  31 */   private final Map<String, Supplier<Builder<T>>> buildersSuppliers = new HashMap<>();
/*     */   
/*     */   public BuilderFactory(Class<T> category, String typeTag) {
/*  34 */     this(category, typeTag, null);
/*     */   }
/*     */   
/*     */   public BuilderFactory(Class<T> category, String typeTag, Supplier<Builder<T>> defaultBuilder) {
/*  38 */     this.category = category;
/*  39 */     this.typeTag = typeTag;
/*  40 */     this.defaultBuilder = defaultBuilder;
/*  41 */     add("Component", () -> new BuilderComponent(category));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BuilderFactory<T> add(String name, Supplier<Builder<T>> builder) {
/*  46 */     if (this.buildersSuppliers.containsKey(name)) throw new IllegalArgumentException(String.format("Builder with name %s already exists", new Object[] { name })); 
/*  47 */     if (this.typeTag.isEmpty()) throw new IllegalArgumentException("Can't add named builder to array builder factory"); 
/*  48 */     this.buildersSuppliers.put(name, builder);
/*  49 */     return this;
/*     */   }
/*     */   
/*     */   public Class<T> getCategory() {
/*  53 */     return this.category;
/*     */   }
/*     */   
/*     */   public Builder<T> createBuilder(@Nonnull JsonElement config) {
/*  57 */     if (!config.isJsonObject()) {
/*  58 */       if (this.defaultBuilder == null) {
/*  59 */         throw new IllegalArgumentException(String.format("Array builder must have default builder defined: %s", new Object[] { config }));
/*     */       }
/*  61 */       return this.defaultBuilder.get();
/*     */     } 
/*  63 */     return createBuilder(config.getAsJsonObject(), this.typeTag);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKeyName(@Nonnull JsonElement config) {
/*  68 */     if (!config.isJsonObject()) {
/*  69 */       return "-";
/*     */     }
/*  71 */     JsonElement element = config.getAsJsonObject().get(this.typeTag);
/*  72 */     return (element != null) ? element.getAsString() : "???";
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Builder<T> createBuilder(String name) {
/*  78 */     if (!this.buildersSuppliers.containsKey(name)) {
/*  79 */       throw new IllegalArgumentException(String.format("Builder %s does not exist", new Object[] { name }));
/*     */     }
/*     */     
/*  82 */     Builder<T> builder = ((Supplier<Builder<T>>)this.buildersSuppliers.get(name)).get();
/*  83 */     if (builder.category() != getCategory()) {
/*  84 */       throw new IllegalArgumentException(String.format("Builder %s has category %s which does not match %s", new Object[] { name, builder.category().getName(), getCategory().getName() }));
/*     */     }
/*     */     
/*  87 */     builder.setTypeName(name);
/*  88 */     return builder;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Builder<T> tryCreateDefaultBuilder() {
/*  93 */     return (this.defaultBuilder != null) ? this.defaultBuilder.get() : null;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<String> getBuilderNames() {
/*  98 */     return (List<String>)new ObjectArrayList(this.buildersSuppliers.keySet());
/*     */   }
/*     */   
/*     */   private Builder<T> createBuilder(@Nonnull JsonObject config, @Nonnull String tag) {
/* 102 */     if (config == null) throw new IllegalArgumentException("JSON config cannot be null when creating builder"); 
/* 103 */     if (tag == null || tag.trim().isEmpty()) {
/* 104 */       throw new IllegalArgumentException(String.format("Tag cannot be null or empty when creating builder with content %s", new Object[] { config }));
/*     */     }
/* 106 */     JsonElement element = config.get(tag);
/* 107 */     if (element == null && this.defaultBuilder != null) return this.defaultBuilder.get(); 
/* 108 */     if (element == null) {
/* 109 */       throw new IllegalArgumentException(String.format("Builder tag of type %s must be supplied if no default is defined in %s", new Object[] { tag, config }));
/*     */     }
/* 111 */     return createBuilder(element.getAsString());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getSchemaName() {
/* 117 */     return "NPCType:" + getCategory().getSimpleName();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Schema toSchema(@Nonnull SchemaContext context) {
/* 123 */     return toSchema(context, false);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Schema toSchema(@Nonnull SchemaContext context, boolean isRoot) {
/* 128 */     int index = 0;
/* 129 */     Schema[] schemas = new Schema[getBuilderNames().size()];
/*     */     
/* 131 */     ObjectSchema check = new ObjectSchema();
/* 132 */     check.setRequired(new String[] { this.typeTag });
/* 133 */     StringSchema keys = new StringSchema();
/* 134 */     keys.setEnum((String[])getBuilderNames().toArray(x$0 -> new String[x$0]));
/* 135 */     check.setProperties(Map.of(this.typeTag, keys));
/*     */     
/* 137 */     Schema root = new Schema();
/* 138 */     if (this.defaultBuilder != null || !getBuilderNames().isEmpty()) {
/* 139 */       root.setIf((Schema)check);
/* 140 */       root.setThen(Schema.anyOf(schemas));
/*     */     } else {
/* 142 */       root.setAnyOf(schemas);
/*     */     } 
/*     */     
/* 145 */     for (String builderName : getBuilderNames()) {
/* 146 */       Builder<T> builder = createBuilder(builderName);
/* 147 */       Schema schemaRef = context.refDefinition(builder);
/* 148 */       ObjectSchema schema = (ObjectSchema)context.getRawDefinition(builder);
/* 149 */       LinkedHashMap<String, Schema> newProps = new LinkedHashMap<>();
/* 150 */       Schema type = StringSchema.constant(builderName);
/* 151 */       if (builder instanceof BuilderBase) type.setDescription(((BuilderBase)builder).getLongDescription()); 
/* 152 */       newProps.put(this.typeTag, type);
/* 153 */       if (isRoot) {
/* 154 */         newProps.put("TestType", new StringSchema());
/* 155 */         newProps.put("FailReason", new StringSchema());
/* 156 */         newProps.put("Parameters", BuilderParameters.toSchema(context));
/*     */       } 
/* 158 */       newProps.putAll(schema.getProperties());
/* 159 */       schema.setProperties(newProps);
/*     */       
/* 161 */       Schema cond = new Schema();
/* 162 */       ObjectSchema checkType = new ObjectSchema();
/* 163 */       checkType.setProperties(Map.of(this.typeTag, StringSchema.constant(builderName)));
/* 164 */       checkType.setRequired(new String[] { this.typeTag });
/* 165 */       cond.setIf((Schema)checkType);
/* 166 */       cond.setThen(schemaRef);
/* 167 */       cond.setElse(false);
/*     */       
/* 169 */       schemas[index++] = cond;
/*     */     } 
/*     */     
/* 172 */     if (this.defaultBuilder != null) {
/* 173 */       Builder<T> builder = this.defaultBuilder.get();
/* 174 */       Schema schemaRef = context.refDefinition(builder);
/* 175 */       root.setElse(schemaRef);
/*     */     } else {
/* 177 */       root.setElse(false);
/*     */     } 
/*     */     
/* 180 */     root.setHytaleSchemaTypeField(new Schema.SchemaTypeField(this.typeTag, null, (String[])getBuilderNames().toArray(x$0 -> new String[x$0])));
/* 181 */     root.setTitle(getCategory().getSimpleName());
/* 182 */     return root;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderFactory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */