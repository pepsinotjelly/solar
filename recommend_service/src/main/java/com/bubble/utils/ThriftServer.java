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
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/20 8:02 PM
 * @Desc:
 */
@Component
public class ThriftServer {
    public static void start(Class<?>[] serverImplClass, int PORT) {
        try {
            System.out.println("============================THRIFT-SERVER START============================");
            Pattern pattern = Pattern.compile("^(.+)\\$Iface$");
            Matcher matcher = null;
            TServerTransport serverTransport = new TServerSocket(PORT);
            TBinaryProtocol.Factory protocolFactory = new TBinaryProtocol.Factory();
            TMultiplexedProcessor processors = new TMultiplexedProcessor();
            for (Class<?> serverClass : serverImplClass) {
                Class<?>[] serverInterfaces = serverClass.getInterfaces();
                for (Class<?> serverInterface : serverInterfaces) {
                    String interfaceName = serverInterface.getName();
                    matcher = pattern.matcher(interfaceName);
                    if (matcher.find()) {
                        String classTName = matcher.group(1);
                        Object classTObject = Class.forName(classTName).newInstance();
                        Class iface = Class.forName(classTName + "$Iface");
                        Object object = serverClass.newInstance();
                        TProcessor processor = (TProcessor) Class.forName(classTName + "$Processor")
                                .getDeclaredConstructor(iface)
                                .newInstance(object);
                        processors.registerProcessor(classTObject.getClass().getSimpleName(), processor);
                    }
                }
            }
            TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport);
            args.protocolFactory(protocolFactory);
            args.processor(processors);
            args.minWorkerThreads(10);
            args.maxWorkerThreads(10);
            TServer server = new TThreadPoolServer(args);
            server.serve();
            System.out.println("Thrift Server on port 7090");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
