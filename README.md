# ms-restaurant-0.0.1-SNAPSHOT.jar
___

# 🧩 Overview
Restaurant System — mikroservis əsaslı arxitektura ilə hazırlanmış bir backend sistemidir. Layihə restoranların, menyuların və bildirişlərin idarə olunmasını təmin edir.
Müxtəlif texnologiyaların (Kafka, Redis, Feign Client, Liquibase, Docker və s.) inteqrasiyası ilə real mikroservis kommunikasiya nümunəsi yaradır.

___

🏗️ Architecture

- Layihə 3 əsas mikroservisdən ibarətdir:
___

1️⃣ ms-restaurant

- Restoranların qeydiyyatı və idarə olunması
- CRUD əməliyyatları (Create, Read, Update, Delete)
- Kafka inteqrasiyası — restoran yaradıldıqda mesaj göndərilir
- Redis ilə caching
- Liquibase ilə database versiyalaşdırması
- Docker dəstəyi (PostgreSQL ilə birgə)
- Kafka Producer — restoran yaradıldıqda “restaurant-created” topikinə mesaj göndərir

___

2️⃣ ms-menu

- Restoranlara aid menyuların idarə olunması
- CRUD əməliyyatları
- Feign Client — ms-restaurant servisinə REST çağırışları edir (restoran ID-ni yoxlamaq üçün)
- Tətbiqin performansını daha da artırmaq məqsədilə keş (cache) mexanizmi üçün Redis istifadə olunur
- Liquibase inteqrasiyası
- External Payment Integration — ayrıca deploy olunmuş “Payment & Order” sistemindən ödəniş prosesi çağırılır
- Dockerized
___

3️⃣ ms-notification

- Bildirişlərin göndərilməsi servisi
- Kafka Consumer — restaurant-created topikindən mesajları qəbul edir
- Gmail SMTP vasitəsilə avtomatik e-poçt göndərir
- Mikroservis tam asinxron işləyir
