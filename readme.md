# KURULUMLAR

## MongoDB İşlemler

    Not: MongoDB ile işlem yaparken admin kullanısı ve admin şifresi kullanılmamalıdır. Bu nedenle
    oluşturulacak her DB için yeni kullanıcı ve şifre tanımlanır.
    1- Öncelikle DB yi yanımlayın
    2- Üzerinde çalışma yapabilmek için mongoDB Compass üzerinden MONGOSH ı açın (sol en alt)
    3- "use DB_ADI" şeklinde komut girilir
    4- bu DB'yi yönetecek olan bir kullanıcı tanımlıyorsunuz
    db.createUser({
        user: "defaultUser",
        pwd: "bilge!*123", //şifre
        roles: ["readWrite","dbAdmin"] //kullanıcını rollerini giriyoruz
    })
    ->db.createUser({user: "defaultUser",pwd: "bilge!*123",roles: ["readWrite", "dbAdmin"]})




## DOCKER ÜZERİNDE MONGODB KURULUMU

    docker run --name mongodb -e "MONGO_INITDB_ROOT_USERNAME:admin" -e "MONGO_INITDB_ROOT_PASSWORD:root" -p 27017:27017 mongo:7.0-rc-j

## DOCKER'a image gonderme
    docker build -t [hub.docker repository name]/[project name]:[version] .


### DOCKER REDIS KURULUMU
    docker run --name lokalredis -d -p 6379:6379 redis:7.2.3-alpine3.18

### DOCKER ELASTICSEARCH KURULUMU
    docker run -d -p 9200:9200 -p 9300:9300 -e "ES_JAVA_OPTS=-Xms512m -Xmx1024m" -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.5.2

### DOCKER DESKTOP UZERINDE UYGULAMALARI YUKLEME
Sırası ile localde bulunması gerekenler;
1-  docker run --name mongodb -e "MONGO_INITDB_ROOT_USERNAME:admin" -e "MONGO_INITDB_ROOT_PASSWORD:root" -p 27017:27017 mongo:7.0-rc-j
1.1 kurulumun ardından mongo compass ile bağlanıp mongoSH içinde yeni bir yetkili kullanıcı oluştur.
1.1.1 db.createUser({user: "defaultUser",pwd: "bilge!*123",roles: ["readWrite", "dbAdmin"]})
2- postgresql
3- docker run -d --name some-rabbit -p -e RABBITMQ_DEFAULT_USER="BilgeAdmin" -e RABBITMQ_DEFAULT_PASS="BilgeAdam!!**" rabbitmq:3-management


