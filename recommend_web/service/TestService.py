"""
    demo client for test service
"""
import multiprocessing
import os
import sys

from com.bubble.thrift.test import SayHelloService
from com.bubble.thrift.test.ttypes import HelloRequest
from thrift.protocol import TMultiplexedProtocol

sys.path.append(os.path.abspath(os.path.join(os.path.dirname('__file__'), os.path.pardir)))

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol

__HOST = 'localhost'
__PORT = 7090
# __HOST = '10.71.167.104'  # 通过 ifconfig | grep "inet"命令获取host
# __HOST = '192.168.0.105'
try:
    t_socket = TSocket.TSocket(__HOST, __PORT)
    transport = TTransport.TBufferedTransport(t_socket)
    protocol = TBinaryProtocol.TBinaryProtocol(transport)
    multiplexProtocol = TMultiplexedProtocol.TMultiplexedProtocol(serviceName='SayHelloService', protocol=protocol)
    client = SayHelloService.Client(multiplexProtocol)

    test_word = HelloRequest(sayWhat='do you love me?', times=3, Base=None)
    transport.open()
    res = client.Visit(test_word)
    print(res.answerIs)
    transport.close()

except Thrift.TException as ex:
    print(ex)
