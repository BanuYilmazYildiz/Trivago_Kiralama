# Öncelikle uygulamanızın çalışabileceği bir image kullanıyoruz. (Linux/JVM17) hub.docker.com dan eklenmiştir

FROM amazoncorretto:17
# Uygulamanızın jar dosyasını oluşturacağınız dıcker container içine
#COPY ile ekliyorsunuz. Dikkat ederseniz COPY [DOSYANIZ] [KOPYALANAN YER]
COPY build/libs/AutMicroService-v.0.1.jar app.jar
#Container ayağa kaldırıldığında çalışması için gerekli komutları iştecek kısmı yazıyoruz
ENTRYPOINT ["java","-jar","/app.jar"]
# Bu dosyayı kullanarak yeni bir docker imge oluşturmak için docker build kullanıyoruz. tam olarak komut şu şekildedir.
#docker build -t [hub.docker repository name]/[project name]:[version] .