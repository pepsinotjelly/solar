package com.bubble.utils.client;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ThriftProxy implements InvocationHandler {

    //Client类
    private Class clientClass;
    //Service类
    private Class classs;
    private String[] hostPorts;

    public Object newInstance(Class classs, String[] hostPorts) {
        try {
            this.clientClass = Class.forName(classs.getName() + "$Client");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.classs = classs;
        this.hostPorts = hostPorts;
        return Proxy.newProxyInstance(clientClass.getClassLoader(), clientClass.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object obj, Method method, Object[] objs) throws Throwable {
        if (null != hostPorts && hostPorts.length > 0) {
            for (String hostPort : hostPorts) {
                if (null != hostPort && "" != hostPort.trim()) {
                    TSocket socket = null;
                    try {
                        int index = hostPort.indexOf(":");
                        String host = hostPort.substring(0, index);
                        String port = hostPort.substring(index+1, hostPort.length());
                        socket = new TSocket(host, Integer.valueOf(port));

                        TProtocol tProtocol = new TBinaryProtocol(socket);
                        TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(tProtocol, classs.getSimpleName());
                        Class[] classes = new Class[]{TProtocol.class};
                        socket.open();
                        return method.invoke(clientClass.getConstructor(classes).newInstance(multiplexedProtocol), objs);
                    } finally {
                        if (null != socket) {
                            socket.close();
                        }
                    }
                }
            }
        }

        return null;
    }

}