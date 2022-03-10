package com.bubble.thrift;

public class ThriftProxyFactory {

    public static Object newInstance(Class classT, String[] hostPorts) {
        ThriftProxy thriftProxy = new ThriftProxy();
        return thriftProxy.newInstance(classT, hostPorts);
    }

}