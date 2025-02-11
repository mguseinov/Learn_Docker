FROM ubuntu:18.04

RUN apt-get update
RUN apt-get install -y python3 python3-pip

COPY main.py /main.py

ENTRYPOINT [ "executable" ] 
CMD ["python3", "/main.py"]