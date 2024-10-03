FROM acrptpfcdevitcentralindia001.azurecr.io/ocs-openjdk:17

ENV HTTP_PROXY "172.18.100.5:8080"
ENV HTTPS_PROXY "172.18.100.5:8080"
ENV NO_PROXY "172.19.15.65/27,172.19.15.96/27,localhost,127.0.0.1,::1,172.19.16.0/24,172.19.15.0/24,172.19.17.0/24,*.rbihub.io"

ADD sslcert/* /opt/app/sslcert/
ADD target/*.jar /opt/app
