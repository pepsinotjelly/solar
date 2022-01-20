package com.bubble.utils;

import com.bubble.thrift.test.SayHelloService;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/20 8:02 PM
 * @Desc:
 */
public class ThriftServer {
    public static void start(Class[] classes, String localhost, int port){
        try {
            System.out.println("============================THRIFT-SERVER START============================");
            Pattern pattern = Pattern.compile("^(.+)\\$Iface$");
            Matcher matcher = null;
            TServerSocket serverTransport = new TServerSocket(port);
            TBinaryProtocol.Factory protocolFactory = new TBinaryProtocol.Factory();
            TMultiplexedProcessor processors = new TMultiplexedProcessor();
            for(int i = 0; i < classes.length; i ++) {
                System.out.println("======================="+i);
                Class<?> classT = classes[i];
                Class<?>[] interfaces = classT.getInterfaces();
                for(Class<?> interfacez : interfaces) {
                    String interfacezName = interfacez.getName();
                    matcher = pattern.matcher(interfacezName);
                    if(matcher.find()){
                        String classTName = matcher.group(1);
                        System.out.println(classTName);
                        Object classTObject = Class.forName(classTName).newInstance();
                        Class iface = Class.forName(classTName + "$Iface");
                        Object object = classT.newInstance();
                        TProcessor processor = (TProcessor) Class.forName(classTName + "$Processor")
                                .getDeclaredConstructor(iface)
                                .newInstance(object);
                        processors.registerProcessor(classTObject.getClass().getSimpleName(), processor);
                     }
                }
            }
//            TServer.Args tArgs = new TServer.Args(serverTransport);
//            tArgs.processor(processors);
//            tArgs.protocolFactory(new TBinaryProtocol.Factory());
//            TServer server = new TSimpleServer(tArgs);
//            server.serve();

            TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport);
            args.protocolFactory(protocolFactory);
            args.processor(processors);
            args.minWorkerThreads(10);
            args.maxWorkerThreads(10);
            TServer server = new TThreadPoolServer(args);
            System.out.println("Thrift Server on port 7090");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
