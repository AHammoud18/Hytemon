/*     */ package com.google.protobuf;
/*     */ 
/*     */ public final class StructProto
/*     */   extends GeneratedFile
/*     */ {
/*     */   static final Descriptors.Descriptor internal_static_google_protobuf_Struct_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_Struct_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_protobuf_Struct_FieldsEntry_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_Struct_FieldsEntry_fieldAccessorTable;
/*     */   
/*     */   static {
/*  12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "StructProto");
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
/*  57 */     String[] descriptorData = { "\n\034google/protobuf/struct.proto\022\017google.protobuf\"\001\n\006Struct\022;\n\006fields\030\001 \003(\0132#.google.protobuf.Struct.FieldsEntryR\006fields\032Q\n\013FieldsEntry\022\020\n\003key\030\001 \001(\tR\003key\022,\n\005value\030\002 \001(\0132\026.google.protobuf.ValueR\005value:\0028\001\"²\002\n\005Value\022;\n\nnull_value\030\001 \001(\0162\032.google.protobuf.NullValueH\000R\tnullValue\022#\n\fnumber_value\030\002 \001(\001H\000R\013numberValue\022#\n\fstring_value\030\003 \001(\tH\000R\013stringValue\022\037\n\nbool_value\030\004 \001(\bH\000R\tboolValue\022<\n\fstruct_value\030\005 \001(\0132\027.google.protobuf.StructH\000R\013structValue\022;\n\nlist_value\030\006 \001(\0132\032.google.protobuf.ListValueH\000R\tlistValueB\006\n\004kind\";\n\tListValue\022.\n\006values\030\001 \003(\0132\026.google.protobuf.ValueR\006values*\033\n\tNullValue\022\016\n\nNULL_VALUE\020\000B\n\023com.google.protobufB\013StructProtoP\001Z/google.golang.org/protobuf/types/known/structpbø\001\001¢\002\003GPBª\002\036Google.Protobuf.WellKnownTypesb\006proto3" };
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
/*  79 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
/*     */ 
/*     */ 
/*     */     
/*  83 */     internal_static_google_protobuf_Struct_descriptor = getDescriptor().getMessageType(0);
/*  84 */     internal_static_google_protobuf_Struct_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_protobuf_Struct_descriptor, new String[] { "Fields" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     internal_static_google_protobuf_Struct_FieldsEntry_descriptor = internal_static_google_protobuf_Struct_descriptor.getNestedType(0);
/*  90 */     internal_static_google_protobuf_Struct_FieldsEntry_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_protobuf_Struct_FieldsEntry_descriptor, new String[] { "Key", "Value" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     internal_static_google_protobuf_Value_descriptor = getDescriptor().getMessageType(1);
/*  96 */     internal_static_google_protobuf_Value_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_protobuf_Value_descriptor, new String[] { "NullValue", "NumberValue", "StringValue", "BoolValue", "StructValue", "ListValue", "Kind" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     internal_static_google_protobuf_ListValue_descriptor = getDescriptor().getMessageType(2);
/* 102 */     internal_static_google_protobuf_ListValue_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_protobuf_ListValue_descriptor, new String[] { "Values" });
/*     */ 
/*     */ 
/*     */     
/* 106 */     descriptor.resolveAllFeaturesImmutable();
/*     */   }
/*     */   
/*     */   static final Descriptors.Descriptor internal_static_google_protobuf_Value_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_Value_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_protobuf_ListValue_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_ListValue_fieldAccessorTable;
/*     */   private static Descriptors.FileDescriptor descriptor;
/*     */   
/*     */   public static void registerAllExtensions(ExtensionRegistryLite registry) {}
/*     */   
/*     */   public static void registerAllExtensions(ExtensionRegistry registry) {
/*     */     registerAllExtensions(registry);
/*     */   }
/*     */   
/*     */   public static Descriptors.FileDescriptor getDescriptor() {
/*     */     return descriptor;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\StructProto.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */