package com.google.protobuf;

public interface BlockingRpcChannel {
  Message callBlockingMethod(Descriptors.MethodDescriptor paramMethodDescriptor, RpcController paramRpcController, Message paramMessage1, Message paramMessage2) throws ServiceException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\BlockingRpcChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */