docker run -d --name postgres -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=survey -p 5432:5432  postgres:latest

docker run -p 127.0.0.1:8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin keycloak/keycloak:latest start-dev

bu scriptleri cmd ye girdikten sonra localhost:8080'e admin admin ile giriyoruz.

Clients'e gelip keycloak clienti oluşturacağız. ClientId dfx-survey. Client authorization u açıp Authentication Flow kısmında hepsini işaretleyelim.

Sonrasında o client'e girip Credidentials kısmından Client Secret'i kopyalayıp application.yml da yerine yapıştıralım.

En son test kullanıcısı oluşturmak için Users'e girip kullanıcı oluşturuyoruz. Ardından User detayına girip Credidentials'dan şifre oluşturuyoruz. (Temp olmamalı)