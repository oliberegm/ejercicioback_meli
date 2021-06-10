# Challenge para MercadoLibre

Ejercico practico de back

### Tecnologias
Codigo fuente en java 11 utilizando el framework Spring Boot.
Base de datos princial mongo y redis para almanceje de la cache
### Documentación
Para acceder la documentacion se configuro un swagger donde se puede ver los endpoint y 
la informacion necesaria para su conusmo
- ruta: host/swagger-ui.html

### Configuración
Para poder inidicar la app debe de indicar los siguientes datos en el 
archivo de properties ubicado en src/main/java/appication.propertiers.

#### INFORMACION DE LA DB DE MONGO
- spring.data.mongodb.host=localhost
- spring.data.mongodb.port=27017
- spring.data.mongodb.database=melidb
#### INFORMACION PARA REDIS
- spring.redis.host=localhost
- spring.redis.port=6397
#### DATOS PARA PODER CONECTARSE AL SERCICIO EXTERNO
Indicamos el codigo para obtner informacion de exchange
- api.fixer.code=37ce7e9545ef3c90f4b29040c0de45a8

## Ejecución con  Docker
Realizar los siguientes pasos:
- git clone https://github.com/oliberegm/ejercicioback_meli.git
- cd ejercicioback_meli
- docker-compose up --build
- ingresar en el navegador http://localhost/swagger-ui.html

###EXITOS!!!!!