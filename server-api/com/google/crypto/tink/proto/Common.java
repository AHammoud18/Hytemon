/*    */ package com.google.crypto.tink.proto;
/*    */ 
/*    */ import com.google.protobuf.Descriptors;
/*    */ import com.google.protobuf.ExtensionRegistry;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import com.google.protobuf.GeneratedFile;
/*    */ import com.google.protobuf.RuntimeVersion;
/*    */ 
/*    */ public final class Common
/*    */   extends GeneratedFile {
/*    */   static {
/* 12 */     RuntimeVersion.validateProtobufGencodeVersion(RuntimeVersion.RuntimeDomain.PUBLIC, 4, 32, 1, "", Common.class
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 18 */         .getName());
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
/* 37 */     String[] descriptorData = { "\n\022proto/common.proto\022\022google.crypto.tink*c\n\021EllipticCurveType\022\021\n\rUNKNOWN_CURVE\020\000\022\r\n\tNIST_P256\020\002\022\r\n\tNIST_P384\020\003\022\r\n\tNIST_P521\020\004\022\016\n\nCURVE25519\020\005*j\n\rEcPointFormat\022\022\n\016UNKNOWN_FORMAT\020\000\022\020\n\fUNCOMPRESSED\020\001\022\016\n\nCOMPRESSED\020\002\022#\n\037DO_NOT_USE_CRUNCHY_UNCOMPRESSED\020\003*V\n\bHashType\022\020\n\fUNKNOWN_HASH\020\000\022\b\n\004SHA1\020\001\022\n\n\006SHA384\020\002\022\n\n\006SHA256\020\003\022\n\n\006SHA512\020\004\022\n\n\006SHA224\020\005BY\n\034com.google.crypto.tink.protoP\001Z7github.com/tink-crypto/tink-go/v2/proto/common_go_protob\006proto3" };
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
/* 51 */     descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
/*    */ 
/*    */     
/* 54 */     descriptor.resolveAllFeaturesImmutable();
/*    */   }
/*    */   
/*    */   private static Descriptors.FileDescriptor descriptor;
/*    */   
/*    */   public static void registerAllExtensions(ExtensionRegistryLite registry) {}
/*    */   
/*    */   public static void registerAllExtensions(ExtensionRegistry registry) {
/*    */     registerAllExtensions((ExtensionRegistryLite)registry);
/*    */   }
/*    */   
/*    */   public static Descriptors.FileDescriptor getDescriptor() {
/*    */     return descriptor;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\proto\Common.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */