/*     */ package com.google.protobuf;
/*     */ 
/*     */ 
/*     */ public final class ApiProto
/*     */   extends GeneratedFile
/*     */ {
/*     */   static final Descriptors.Descriptor internal_static_google_protobuf_Api_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_Api_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_protobuf_Method_descriptor;
/*     */   
/*     */   static {
/*  12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 33, 0, "", "ApiProto");
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
/*  52 */     String[] descriptorData = { "\n\031google/protobuf/api.proto\022\017google.protobuf\032$google/protobuf/source_context.proto\032\032google/protobuf/type.proto\"Û\002\n\003Api\022\022\n\004name\030\001 \001(\tR\004name\0221\n\007methods\030\002 \003(\0132\027.google.protobuf.MethodR\007methods\0221\n\007options\030\003 \003(\0132\027.google.protobuf.OptionR\007options\022\030\n\007version\030\004 \001(\tR\007version\022E\n\016source_context\030\005 \001(\0132\036.google.protobuf.SourceContextR\rsourceContext\022.\n\006mixins\030\006 \003(\0132\026.google.protobuf.MixinR\006mixins\022/\n\006syntax\030\007 \001(\0162\027.google.protobuf.SyntaxR\006syntax\022\030\n\007edition\030\b \001(\tR\007edition\"Ô\002\n\006Method\022\022\n\004name\030\001 \001(\tR\004name\022(\n\020request_type_url\030\002 \001(\tR\016requestTypeUrl\022+\n\021request_streaming\030\003 \001(\bR\020requestStreaming\022*\n\021response_type_url\030\004 \001(\tR\017responseTypeUrl\022-\n\022response_streaming\030\005 \001(\bR\021responseStreaming\0221\n\007options\030\006 \003(\0132\027.google.protobuf.OptionR\007options\0223\n\006syntax\030\007 \001(\0162\027.google.protobuf.SyntaxB\002\030\001R\006syntax\022\034\n\007edition\030\b \001(\tB\002\030\001R\007edition\"/\n\005Mixin\022\022\n\004name\030\001 \001(\tR\004name\022\022\n\004root\030\002 \001(\tR\004rootBv\n\023com.google.protobufB\bApiProtoP\001Z,google.golang.org/protobuf/types/known/apipb¢\002\003GPBª\002\036Google.Protobuf.WellKnownTypesb\006proto3" };
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
/*  80 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] {
/*     */           
/*  82 */           SourceContextProto.getDescriptor(), 
/*  83 */           TypeProto.getDescriptor()
/*     */         });
/*     */     
/*  86 */     internal_static_google_protobuf_Api_descriptor = getDescriptor().getMessageType(0);
/*  87 */     internal_static_google_protobuf_Api_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_protobuf_Api_descriptor, new String[] { "Name", "Methods", "Options", "Version", "SourceContext", "Mixins", "Syntax", "Edition" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     internal_static_google_protobuf_Method_descriptor = getDescriptor().getMessageType(1);
/*  93 */     internal_static_google_protobuf_Method_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_protobuf_Method_descriptor, new String[] { "Name", "RequestTypeUrl", "RequestStreaming", "ResponseTypeUrl", "ResponseStreaming", "Options", "Syntax", "Edition" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     internal_static_google_protobuf_Mixin_descriptor = getDescriptor().getMessageType(2);
/*  99 */     internal_static_google_protobuf_Mixin_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_google_protobuf_Mixin_descriptor, new String[] { "Name", "Root" });
/*     */ 
/*     */ 
/*     */     
/* 103 */     descriptor.resolveAllFeaturesImmutable();
/* 104 */     SourceContextProto.getDescriptor();
/* 105 */     TypeProto.getDescriptor();
/*     */   }
/*     */   
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_Method_fieldAccessorTable;
/*     */   static final Descriptors.Descriptor internal_static_google_protobuf_Mixin_descriptor;
/*     */   static final GeneratedMessage.FieldAccessorTable internal_static_google_protobuf_Mixin_fieldAccessorTable;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ApiProto.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */