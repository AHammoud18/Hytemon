package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;

@CheckReturnValue
public interface Message extends MessageLite, MessageOrBuilder {
  Parser<? extends Message> getParserForType();
  
  boolean equals(Object paramObject);
  
  int hashCode();
  
  String toString();
  
  Builder newBuilderForType();
  
  Builder toBuilder();
  
  public static interface Builder extends MessageLite.Builder, MessageOrBuilder {
    @CanIgnoreReturnValue
    Builder clear();
    
    @CanIgnoreReturnValue
    Builder mergeFrom(Message param1Message);
    
    @CanIgnoreReturnValue
    Builder mergeFrom(CodedInputStream param1CodedInputStream) throws IOException;
    
    @CanIgnoreReturnValue
    Builder mergeFrom(CodedInputStream param1CodedInputStream, ExtensionRegistryLite param1ExtensionRegistryLite) throws IOException;
    
    @CanIgnoreReturnValue
    Builder mergeFrom(ByteString param1ByteString) throws InvalidProtocolBufferException;
    
    @CanIgnoreReturnValue
    Builder mergeFrom(ByteString param1ByteString, ExtensionRegistryLite param1ExtensionRegistryLite) throws InvalidProtocolBufferException;
    
    @CanIgnoreReturnValue
    Builder mergeFrom(byte[] param1ArrayOfbyte) throws InvalidProtocolBufferException;
    
    @CanIgnoreReturnValue
    Builder mergeFrom(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws InvalidProtocolBufferException;
    
    @CanIgnoreReturnValue
    Builder mergeFrom(byte[] param1ArrayOfbyte, ExtensionRegistryLite param1ExtensionRegistryLite) throws InvalidProtocolBufferException;
    
    @CanIgnoreReturnValue
    Builder mergeFrom(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2, ExtensionRegistryLite param1ExtensionRegistryLite) throws InvalidProtocolBufferException;
    
    @CanIgnoreReturnValue
    Builder mergeFrom(InputStream param1InputStream) throws IOException;
    
    @CanIgnoreReturnValue
    Builder mergeFrom(InputStream param1InputStream, ExtensionRegistryLite param1ExtensionRegistryLite) throws IOException;
    
    Message build();
    
    Message buildPartial();
    
    Builder clone();
    
    Descriptors.Descriptor getDescriptorForType();
    
    Builder newBuilderForField(Descriptors.FieldDescriptor param1FieldDescriptor);
    
    Builder getFieldBuilder(Descriptors.FieldDescriptor param1FieldDescriptor);
    
    Builder getRepeatedFieldBuilder(Descriptors.FieldDescriptor param1FieldDescriptor, int param1Int);
    
    @CanIgnoreReturnValue
    Builder setField(Descriptors.FieldDescriptor param1FieldDescriptor, Object param1Object);
    
    @CanIgnoreReturnValue
    Builder clearField(Descriptors.FieldDescriptor param1FieldDescriptor);
    
    @CanIgnoreReturnValue
    Builder clearOneof(Descriptors.OneofDescriptor param1OneofDescriptor);
    
    @CanIgnoreReturnValue
    Builder setRepeatedField(Descriptors.FieldDescriptor param1FieldDescriptor, int param1Int, Object param1Object);
    
    @CanIgnoreReturnValue
    Builder addRepeatedField(Descriptors.FieldDescriptor param1FieldDescriptor, Object param1Object);
    
    @CanIgnoreReturnValue
    Builder setUnknownFields(UnknownFieldSet param1UnknownFieldSet);
    
    @CanIgnoreReturnValue
    Builder mergeUnknownFields(UnknownFieldSet param1UnknownFieldSet);
    
    boolean mergeDelimitedFrom(InputStream param1InputStream) throws IOException;
    
    boolean mergeDelimitedFrom(InputStream param1InputStream, ExtensionRegistryLite param1ExtensionRegistryLite) throws IOException;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Message.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */