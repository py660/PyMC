import argparse
import socket
import mcrcon
import os
from dotenv import load_dotenv

load_dotenv()

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect(("127.0.0.1", 25575))
try:
    #login
    result = mcrcon.login(sock, os.getenv('PW'))

    ##generate request
    #request = f"whitelist list"
    #print(request)
    #print(mcrcon.command(sock, request))
    # Start looping
    while True:
        request = input("")
        response = mcrcon.command(sock, request)
        print(response)
finally:
    sock.close()