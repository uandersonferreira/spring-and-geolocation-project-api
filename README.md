# 📍 API de Geolocalização com Spring Boot

Este projeto é uma API que calcula informações de distância, endereço e valores associados a viagens, utilizando coordenadas extraídas de URLs do Google Maps. Foi desenvolvido com **Spring Boot**, integrando serviços de geolocalização e utilizando a API OSRM para processamento de rotas.

---

## 🛠️ Tecnologias Utilizadas

- **Java 21** ☕
- **Spring Boot 3.4.0** 🚀
- **Maven** 📦
- **Docker** 🐳
- **OSRM Backend** 🗺️
- **Lombok** ✨
- **OpenFeign** 🔗

---

## ⚙️ Funcionalidades da API

- **📍 Extração de Coordenadas**: A partir de URLs do Google Maps, a API extrai as coordenadas de latitude e longitude dos pontos inicial e final.
- **🏠 Identificação de Endereços**: Converte as coordenadas extraídas em endereços compreensíveis.
- **📏 Cálculo de Distância**: Retorna a distância entre dois pontos em **metros** e **quilômetros**.
- **💰 Detalhamento de Valores de Viagem**: Calcula os custos de viagem, incluindo:
  - Valor pago pelo passageiro.
  - Valor recebido pelo motorista.
  - Valor recebido pela empresa intermediária.
  - Taxa percentual aplicada pelo intermediário.

---

## 📋 Pré-requisitos

Certifique-se de ter os seguintes itens instalados:

- [Docker](https://docs.docker.com/get-docker/)
- [Java 21](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)
- [Maven](https://maven.apache.org/install.html)

---

## 🗺️ Passo a Passo para Configuração

### 1️⃣ Obtenha os Dados de Mapa
Baixe os dados da região desejada no [Geofabrik](https://download.geofabrik.de/). Por exemplo, para o norte da Alemanha, utilize o seguinte comando:

```bash
wget http://download.geofabrik.de/europe/germany/norte-latest.osm.pbf
```

> **Nota**: O arquivo `norte-latest.osm.pbf` deve ser substituído pelo arquivo correspondente à região escolhida.

---

### 2️⃣ Prepare o Backend OSRM com Docker
Baixe e configure a API OSRM usando os comandos abaixo no terminal:

#### **Passo 1: Extração dos Dados**
```bash
docker run -t -v "${PWD}:/data" ghcr.io/project-osrm/osrm-backend osrm-extract -p /opt/car.lua /data/norte-latest.osm.pbf || echo "osrm-extract failed"
```

#### **Passo 2: Partição e Personalização**
```bash
docker run -t -v "${PWD}:/data" ghcr.io/project-osrm/osrm-backend osrm-partition /data/norte-latest.osrm || echo "osrm-partition failed"

docker run -t -v "${PWD}:/data" ghcr.io/project-osrm/osrm-backend osrm-customize /data/norte-latest.osrm || echo "osrm-customize failed"
```

#### **Passo 3: Inicialização do Servidor**
```bash
docker run -t -i -p 5000:5000 -v "${PWD}:/data" ghcr.io/project-osrm/osrm-backend osrm-routed --algorithm mld /data/norte-latest.osrm
```

---

### 3️⃣ Criação do Projeto Spring Boot
1. Acesse o site [Spring Initializr](https://start.spring.io/).
2. Configure o projeto com as seguintes dependências:
   - **Spring Web** 🌐
   - **OpenFeign** 🔗
   - **Lombok** ✨
3. Baixe o projeto, importe-o na sua IDE e inicie o desenvolvimento!

---

## 🚀 Como Executar a API

1. **Clone o repositório** e navegue até a pasta do projeto:
   ```bash
   git clone https://github.com/uandersonferreira/spring-and-geolocation-project-api.git
   cd spring-and-geolocation-project-api/spring-simple-driver
   ```
2. **Compile e execute o projeto:**
   ```bash
   mvn spring-boot:run
   ```

### Endpoint Disponível

> ATENÇÃO: LEMBRE-SE DE TER O SERVIDOR OSRM RODANDO!

```bash
docker run -t -i -p 5000:5000 -v "${PWD}:/data" ghcr.io/project-osrm/osrm-backend osrm-routed --algorithm mld /data/norte-latest.osrm
```

- **URL**: `http://127.0.0.1:8080/api/v1/simple`
- **Método**: `POST`
- **Headers**:
  - `Content-Type`: `application/json`

### Exemplo de Requisição

Envie uma requisição `POST` para o endpoint com o seguinte corpo JSON no Postman ou via `curl`:

```json
{
    "urlStart": "https://www.google.com/maps/place/Instituto+Federal+do+Tocantins+-+Campus+Palmas/@-10.1992599,-48.3149674,17z/data=!3m1!4b1!4m6!3m5!1s0x933b331a66317527:0x360a15d22d68d0e0!8m2!3d-10.1992652!4d-48.3123871!16s%2Fg%2F121k85xm?entry=ttu&g_ep=EgoyMDI0MTEyNC4xIKXMDSoASAFQAw%3D%3D",

    "urlEnd": "https://www.google.com/maps/place/Praia+Graciosa/@-10.1895097,-48.3672295,17z/data=!3m1!4b1!4m6!3m5!1s0x9324cade0006717d:0x7e23624b2377f4e8!8m2!3d-10.1895871!4d-48.3644269!16s%2Fg%2F1ymx4r98l?entry=ttu&g_ep=EgoyMDI0MTEyNC4xIKXMDSoASAFQAw%3D%3D"
}
```

### Exemplo de Resposta

O servidor retornará um JSON no seguinte formato:

```json
{
    "streetInitial": "Alameda 10",
    "streetFinal": "Avenida Orla / Avenida Orla / Alameda 08",
    "distance_in_meters": 6679.5,
    "distance_in_km": 7.0,
    "details_travel_values": {
        "valuePaidPassenger": 8.41,
        "valueReceivedDriver": 7.35,
        "valueReceivedIntermediate": 1.05,
        "taxOfIntermediate": 12.5
    }
}
```

### Explicação dos Campos Retornados

- **`streetInitial`**: Nome da rua ou localização inicial.
- **`streetFinal`**: Nome da rua ou localização final.
- **`distance_in_meters`**: Distância total em metros entre os dois pontos.
- **`distance_in_km`**: Distância total em quilômetros.
- **`details_travel_values`**:
  - **`valuePaidPassenger`**: Valor total pago pelo passageiro.
  - **`valueReceivedDriver`**: Valor recebido pelo motorista.
  - **`valueReceivedIntermediate`**: Valor recebido pelo intermediário (empresa).
  - **`taxOfIntermediate`**: Taxa cobrada pelo intermediário (em percentual).


---

## 📑 Créditos

- Este projeto foi desenvolvido com base no tutorial de **Madson Silva**, disponível no canal do YouTube [Maddytec](https://www.youtube.com/@maddytec). 🎥
- Tutorial utilizado: [Trabalhando com Geolocalização no Spring Boot](https://www.youtube.com/live/1cJIzCnTWqw?si=Xpjvvj-l2Psq_0PC). 🗺️

--- 

✨ **Divirta-se criando e compartilhando!** 😊