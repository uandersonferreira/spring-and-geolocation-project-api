BASE DE DADOS: https://download.geofabrik.de/
ou 
wget http://download.geofabrik.de/europe/germany/norte-latest.osm.pbf

API VIA DOCKER NO GITHUB: https://github.com/Project-OSRM/osrm-backend


1°:

docker run -t -v "${PWD}:/data" ghcr.io/project-osrm/osrm-backend osrm-extract -p /opt/car.lua /data/norte-latest.osm.pbf || echo "osrm-extract failed"

2°:

docker run -t -v "${PWD}:/data" ghcr.io/project-osrm/osrm-backend osrm-partition /data/norte-latest.osrm || echo "osrm-partition failed"

docker run -t -v "${PWD}:/data" ghcr.io/project-osrm/osrm-backend osrm-customize /data/norte-latest.osrm || echo "osrm-customize failed"

3°:

docker run -t -i -p 5000:5000 -v "${PWD}:/data" ghcr.io/project-osrm/osrm-backend osrm-routed --algorithm mld /data/norte-latest.osrm
