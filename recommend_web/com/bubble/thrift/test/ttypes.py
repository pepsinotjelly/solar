#
# Autogenerated by Thrift Compiler (0.9.3)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py
#

from thrift.Thrift import TType, TMessageType, TException, TApplicationException
import com.bubble.thrift.ttypes


from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol, TProtocol
try:
  from thrift.protocol import fastbinary
except:
  fastbinary = None



class HelloRequest:
  """
  Attributes:
   - sayWhat
   - times
   - Base
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRING, 'sayWhat', None, "", ), # 1
    (2, TType.I32, 'times', None, 0, ), # 2
    None, # 3
    None, # 4
    None, # 5
    None, # 6
    None, # 7
    None, # 8
    None, # 9
    None, # 10
    None, # 11
    None, # 12
    None, # 13
    None, # 14
    None, # 15
    None, # 16
    None, # 17
    None, # 18
    None, # 19
    None, # 20
    None, # 21
    None, # 22
    None, # 23
    None, # 24
    None, # 25
    None, # 26
    None, # 27
    None, # 28
    None, # 29
    None, # 30
    None, # 31
    None, # 32
    None, # 33
    None, # 34
    None, # 35
    None, # 36
    None, # 37
    None, # 38
    None, # 39
    None, # 40
    None, # 41
    None, # 42
    None, # 43
    None, # 44
    None, # 45
    None, # 46
    None, # 47
    None, # 48
    None, # 49
    None, # 50
    None, # 51
    None, # 52
    None, # 53
    None, # 54
    None, # 55
    None, # 56
    None, # 57
    None, # 58
    None, # 59
    None, # 60
    None, # 61
    None, # 62
    None, # 63
    None, # 64
    None, # 65
    None, # 66
    None, # 67
    None, # 68
    None, # 69
    None, # 70
    None, # 71
    None, # 72
    None, # 73
    None, # 74
    None, # 75
    None, # 76
    None, # 77
    None, # 78
    None, # 79
    None, # 80
    None, # 81
    None, # 82
    None, # 83
    None, # 84
    None, # 85
    None, # 86
    None, # 87
    None, # 88
    None, # 89
    None, # 90
    None, # 91
    None, # 92
    None, # 93
    None, # 94
    None, # 95
    None, # 96
    None, # 97
    None, # 98
    None, # 99
    None, # 100
    None, # 101
    None, # 102
    None, # 103
    None, # 104
    None, # 105
    None, # 106
    None, # 107
    None, # 108
    None, # 109
    None, # 110
    None, # 111
    None, # 112
    None, # 113
    None, # 114
    None, # 115
    None, # 116
    None, # 117
    None, # 118
    None, # 119
    None, # 120
    None, # 121
    None, # 122
    None, # 123
    None, # 124
    None, # 125
    None, # 126
    None, # 127
    None, # 128
    None, # 129
    None, # 130
    None, # 131
    None, # 132
    None, # 133
    None, # 134
    None, # 135
    None, # 136
    None, # 137
    None, # 138
    None, # 139
    None, # 140
    None, # 141
    None, # 142
    None, # 143
    None, # 144
    None, # 145
    None, # 146
    None, # 147
    None, # 148
    None, # 149
    None, # 150
    None, # 151
    None, # 152
    None, # 153
    None, # 154
    None, # 155
    None, # 156
    None, # 157
    None, # 158
    None, # 159
    None, # 160
    None, # 161
    None, # 162
    None, # 163
    None, # 164
    None, # 165
    None, # 166
    None, # 167
    None, # 168
    None, # 169
    None, # 170
    None, # 171
    None, # 172
    None, # 173
    None, # 174
    None, # 175
    None, # 176
    None, # 177
    None, # 178
    None, # 179
    None, # 180
    None, # 181
    None, # 182
    None, # 183
    None, # 184
    None, # 185
    None, # 186
    None, # 187
    None, # 188
    None, # 189
    None, # 190
    None, # 191
    None, # 192
    None, # 193
    None, # 194
    None, # 195
    None, # 196
    None, # 197
    None, # 198
    None, # 199
    None, # 200
    None, # 201
    None, # 202
    None, # 203
    None, # 204
    None, # 205
    None, # 206
    None, # 207
    None, # 208
    None, # 209
    None, # 210
    None, # 211
    None, # 212
    None, # 213
    None, # 214
    None, # 215
    None, # 216
    None, # 217
    None, # 218
    None, # 219
    None, # 220
    None, # 221
    None, # 222
    None, # 223
    None, # 224
    None, # 225
    None, # 226
    None, # 227
    None, # 228
    None, # 229
    None, # 230
    None, # 231
    None, # 232
    None, # 233
    None, # 234
    None, # 235
    None, # 236
    None, # 237
    None, # 238
    None, # 239
    None, # 240
    None, # 241
    None, # 242
    None, # 243
    None, # 244
    None, # 245
    None, # 246
    None, # 247
    None, # 248
    None, # 249
    None, # 250
    None, # 251
    None, # 252
    None, # 253
    None, # 254
    (255, TType.STRUCT, 'Base', (com.bubble.thrift.ttypes.Base, com.bubble.thrift.ttypes.Base.thrift_spec), None, ), # 255
  )

  def __init__(self, sayWhat=thrift_spec[1][4], times=thrift_spec[2][4], Base=None,):
    self.sayWhat = sayWhat
    self.times = times
    self.Base = Base

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.STRING:
          self.sayWhat = iprot.readString()
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.I32:
          self.times = iprot.readI32()
        else:
          iprot.skip(ftype)
      elif fid == 255:
        if ftype == TType.STRUCT:
          self.Base = com.bubble.thrift.ttypes.Base()
          self.Base.read(iprot)
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('HelloRequest')
    if self.sayWhat is not None:
      oprot.writeFieldBegin('sayWhat', TType.STRING, 1)
      oprot.writeString(self.sayWhat)
      oprot.writeFieldEnd()
    if self.times is not None:
      oprot.writeFieldBegin('times', TType.I32, 2)
      oprot.writeI32(self.times)
      oprot.writeFieldEnd()
    if self.Base is not None:
      oprot.writeFieldBegin('Base', TType.STRUCT, 255)
      self.Base.write(oprot)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __hash__(self):
    value = 17
    value = (value * 31) ^ hash(self.sayWhat)
    value = (value * 31) ^ hash(self.times)
    value = (value * 31) ^ hash(self.Base)
    return value

  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class HelloResponse:
  """
  Attributes:
   - answerIs
   - BaseResp
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRING, 'answerIs', None, "", ), # 1
    None, # 2
    None, # 3
    None, # 4
    None, # 5
    None, # 6
    None, # 7
    None, # 8
    None, # 9
    None, # 10
    None, # 11
    None, # 12
    None, # 13
    None, # 14
    None, # 15
    None, # 16
    None, # 17
    None, # 18
    None, # 19
    None, # 20
    None, # 21
    None, # 22
    None, # 23
    None, # 24
    None, # 25
    None, # 26
    None, # 27
    None, # 28
    None, # 29
    None, # 30
    None, # 31
    None, # 32
    None, # 33
    None, # 34
    None, # 35
    None, # 36
    None, # 37
    None, # 38
    None, # 39
    None, # 40
    None, # 41
    None, # 42
    None, # 43
    None, # 44
    None, # 45
    None, # 46
    None, # 47
    None, # 48
    None, # 49
    None, # 50
    None, # 51
    None, # 52
    None, # 53
    None, # 54
    None, # 55
    None, # 56
    None, # 57
    None, # 58
    None, # 59
    None, # 60
    None, # 61
    None, # 62
    None, # 63
    None, # 64
    None, # 65
    None, # 66
    None, # 67
    None, # 68
    None, # 69
    None, # 70
    None, # 71
    None, # 72
    None, # 73
    None, # 74
    None, # 75
    None, # 76
    None, # 77
    None, # 78
    None, # 79
    None, # 80
    None, # 81
    None, # 82
    None, # 83
    None, # 84
    None, # 85
    None, # 86
    None, # 87
    None, # 88
    None, # 89
    None, # 90
    None, # 91
    None, # 92
    None, # 93
    None, # 94
    None, # 95
    None, # 96
    None, # 97
    None, # 98
    None, # 99
    None, # 100
    None, # 101
    None, # 102
    None, # 103
    None, # 104
    None, # 105
    None, # 106
    None, # 107
    None, # 108
    None, # 109
    None, # 110
    None, # 111
    None, # 112
    None, # 113
    None, # 114
    None, # 115
    None, # 116
    None, # 117
    None, # 118
    None, # 119
    None, # 120
    None, # 121
    None, # 122
    None, # 123
    None, # 124
    None, # 125
    None, # 126
    None, # 127
    None, # 128
    None, # 129
    None, # 130
    None, # 131
    None, # 132
    None, # 133
    None, # 134
    None, # 135
    None, # 136
    None, # 137
    None, # 138
    None, # 139
    None, # 140
    None, # 141
    None, # 142
    None, # 143
    None, # 144
    None, # 145
    None, # 146
    None, # 147
    None, # 148
    None, # 149
    None, # 150
    None, # 151
    None, # 152
    None, # 153
    None, # 154
    None, # 155
    None, # 156
    None, # 157
    None, # 158
    None, # 159
    None, # 160
    None, # 161
    None, # 162
    None, # 163
    None, # 164
    None, # 165
    None, # 166
    None, # 167
    None, # 168
    None, # 169
    None, # 170
    None, # 171
    None, # 172
    None, # 173
    None, # 174
    None, # 175
    None, # 176
    None, # 177
    None, # 178
    None, # 179
    None, # 180
    None, # 181
    None, # 182
    None, # 183
    None, # 184
    None, # 185
    None, # 186
    None, # 187
    None, # 188
    None, # 189
    None, # 190
    None, # 191
    None, # 192
    None, # 193
    None, # 194
    None, # 195
    None, # 196
    None, # 197
    None, # 198
    None, # 199
    None, # 200
    None, # 201
    None, # 202
    None, # 203
    None, # 204
    None, # 205
    None, # 206
    None, # 207
    None, # 208
    None, # 209
    None, # 210
    None, # 211
    None, # 212
    None, # 213
    None, # 214
    None, # 215
    None, # 216
    None, # 217
    None, # 218
    None, # 219
    None, # 220
    None, # 221
    None, # 222
    None, # 223
    None, # 224
    None, # 225
    None, # 226
    None, # 227
    None, # 228
    None, # 229
    None, # 230
    None, # 231
    None, # 232
    None, # 233
    None, # 234
    None, # 235
    None, # 236
    None, # 237
    None, # 238
    None, # 239
    None, # 240
    None, # 241
    None, # 242
    None, # 243
    None, # 244
    None, # 245
    None, # 246
    None, # 247
    None, # 248
    None, # 249
    None, # 250
    None, # 251
    None, # 252
    None, # 253
    None, # 254
    (255, TType.STRUCT, 'BaseResp', (com.bubble.thrift.ttypes.BaseResp, com.bubble.thrift.ttypes.BaseResp.thrift_spec), None, ), # 255
  )

  def __init__(self, answerIs=thrift_spec[1][4], BaseResp=None,):
    self.answerIs = answerIs
    self.BaseResp = BaseResp

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.STRING:
          self.answerIs = iprot.readString()
        else:
          iprot.skip(ftype)
      elif fid == 255:
        if ftype == TType.STRUCT:
          self.BaseResp = com.bubble.thrift.ttypes.BaseResp()
          self.BaseResp.read(iprot)
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('HelloResponse')
    if self.answerIs is not None:
      oprot.writeFieldBegin('answerIs', TType.STRING, 1)
      oprot.writeString(self.answerIs)
      oprot.writeFieldEnd()
    if self.BaseResp is not None:
      oprot.writeFieldBegin('BaseResp', TType.STRUCT, 255)
      self.BaseResp.write(oprot)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __hash__(self):
    value = 17
    value = (value * 31) ^ hash(self.answerIs)
    value = (value * 31) ^ hash(self.BaseResp)
    return value

  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)
