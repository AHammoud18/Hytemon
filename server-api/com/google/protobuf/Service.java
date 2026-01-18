package com.google.protobuf;

public interface Service {
  Descriptors.ServiceDescriptor getDescriptorForType();
  
  void callMethod(Descriptors.MethodDescriptor paramMethodDescriptor, RpcController paramRpcController, Message paramMessage, RpcCallback<Message> paramRpcCallback);
  
  Message getRequestPrototype(Descriptors.MethodDescriptor paramMethodDescriptor);
  
  Message getResponsePrototype(Descriptors.MethodDescriptor paramMethodDescriptor);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\Service.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */