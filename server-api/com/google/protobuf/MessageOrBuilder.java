package com.google.protobuf;

import java.util.List;
import java.util.Map;

@CheckReturnValue
public interface MessageOrBuilder extends MessageLiteOrBuilder {
  Message getDefaultInstanceForType();
  
  List<String> findInitializationErrors();
  
  String getInitializationErrorString();
  
  Descriptors.Descriptor getDescriptorForType();
  
  Map<Descriptors.FieldDescriptor, Object> getAllFields();
  
  boolean hasOneof(Descriptors.OneofDescriptor paramOneofDescriptor);
  
  Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor paramOneofDescriptor);
  
  boolean hasField(Descriptors.FieldDescriptor paramFieldDescriptor);
  
  Object getField(Descriptors.FieldDescriptor paramFieldDescriptor);
  
  int getRepeatedFieldCount(Descriptors.FieldDescriptor paramFieldDescriptor);
  
  Object getRepeatedField(Descriptors.FieldDescriptor paramFieldDescriptor, int paramInt);
  
  UnknownFieldSet getUnknownFields();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\MessageOrBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */