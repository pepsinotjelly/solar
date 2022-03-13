# -*- coding: UTF-8 -*-
"""
@Project ：certificate_authority
@File    ：test_cryption.py
@Author  ：Sonia.Suen
@Date    ：2022/2/22 1:46 PM 
"""
import numpy as np
from phe import paillier
import ctypes


public_key, private_key = paillier.generate_paillier_keypair()  # 获取公钥私钥
keyring = paillier.PaillierPrivateKeyring()  # 获取一个钥匙圈
keyring.add(private_key)  # 加入钥匙圈中

public_key1, private_key1 = paillier.generate_paillier_keypair(keyring)
print(public_key1.nsquare)
print("=========================")
print(private_key1)
print("=======================")

public_key2, private_key2 = paillier.generate_paillier_keypair(keyring)

secret_number_list = [3.141592653, 1300, -4.6e-12, 1111]  # 待加密的数据

encrypted_number_list = [public_key.encrypt(x) for x in secret_number_list]  # 逐个加密
a, b, c, d = encrypted_number_list
print(encrypted_number_list, "encrypted_number_list")  # 加密结果
print(a + 1)
for y in encrypted_number_list:
    print(y._EncryptedNumber__is_obfuscated, "     y")  # 是否被混淆
    print(y._EncryptedNumber__ciphertext)  # 密文内容
# decription
print([private_key.decrypt(x) for x in encrypted_number_list])
print([keyring.decrypt(x) for x in encrypted_number_list])
print(private_key.decrypt(d + 1))  # 运算后解密
enc_mean = np.mean(a)
print(enc_mean)
print(private_key.decrypt(enc_mean))

player_1 = [[0, 1],
            [2, 3],
            [0, 5],
            [0, 7]]
player_2 = [[0, 1, 2, 3], [2, 2, 3, 4]]
print(np.dot(player_1, player_2))

