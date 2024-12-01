
Abaixo está o passo a passo que eu fiz para criar o projeto:

BASE DE DADOS: https://download.geofabrik.de/
ou 
wget http://download.geofabrik.de/europe/germany/norte-latest.osm.pbf

OBS: o nome 'norte-latest.osm.pbf' é o nome do arquivo que eu baixei da base de dados do geofabrik.de ele muda de acordo com a região que você escolher.

API VIA DOCKER NO GITHUB: https://github.com/Project-OSRM/osrm-backend

criar um projeto spring boot pelo site: https://start.spring.io/
    - com java 21
    - maven
    - spring boot 3.4.0
    - spring web, openfeign e lombok


No terminal linux:

(Eu já tenho docker instalado)

1°:

docker run -t -v "${PWD}:/data" ghcr.io/project-osrm/osrm-backend osrm-extract -p /opt/car.lua /data/norte-latest.osm.pbf || echo "osrm-extract failed"

2°:

docker run -t -v "${PWD}:/data" ghcr.io/project-osrm/osrm-backend osrm-partition /data/norte-latest.osrm || echo "osrm-partition failed"

docker run -t -v "${PWD}:/data" ghcr.io/project-osrm/osrm-backend osrm-customize /data/norte-latest.osrm || echo "osrm-customize failed"

3°:

docker run -t -i -p 5000:5000 -v "${PWD}:/data" ghcr.io/project-osrm/osrm-backend osrm-routed --algorithm mld /data/norte-latest.osrm
