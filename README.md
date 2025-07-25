DE PREFERENCIA EJECUTAR EL COMPIRMIDO DEL PROYECTO QUE ESTA EN LA ACTIVDAD, POR ERROR EN GITHUB Y EL JDK 

INSTRUCCIONES PARA PROBAR LA APLICACIÓN 
1. Instalar la aplicación en un dispositivo Android con GPS habilitado. 
2. Conceder permisos de ubicación. 
3. Abrir la aplicación y presionar Iniciar Monitoreo. 
4. Acceder vía navegador o herramienta como Postman a: 
• http://<IP_LOCAL>:8080/api/device_status 
• http://<IP_LOCAL>:8080/api/sensor_data?start_time=YYYY-MM
DDTHH:MM:SS&end_time=YYYY-MM-DDTHH:MM:SS 
5. Incluir el header de autorización: Authorization: Bearer 123456TOKEN
