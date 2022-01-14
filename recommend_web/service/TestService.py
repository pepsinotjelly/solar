"""
    demo client for test service
"""

import os
import sys

from com.bubble.thrift.test.SayHelloService import Client
from com.bubble.thrift.test.ttypes import HelloRequest

sys.path.append(os.path.abspath(os.path.join(os.path.dirname('__file__'), os.path.pardir)))

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol

__HOST = 'localhost'
__PORT = 7090
try:
    t_socket = TSocket.TSocket(__HOST, __PORT)
    transport = TTransport.TBufferedTransport(t_socket)
    protocal = TBinaryProtocol.TBinaryProtocol(transport)
    client = Client(protocal)

    test_word = HelloRequest(sayWhat='do you love me?', times=3, Base=None)
    transport.open()
    res = client.Visit(test_word)
    print(res.answerIs)
    print()
    print('ok')
    transport.close()

except Thrift.TException as ex:
    print(ex)
