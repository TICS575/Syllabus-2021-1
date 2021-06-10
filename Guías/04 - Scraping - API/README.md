# Creando una API con Flask y MongoDB

En esta clase vamos a dar algunos conceptos que nos permitirán comprender uno de los flujos de trabajo que usan MongoDB. Vamos a presentar una App que sigue las siguientes acciones:

- Tenemos un script en Python que hace _scraping_ de los datos de EMOL.
- Tenemos una base de datos MongoDB en la que cargamos estos datos como JSON. Además, creamos un índice por texto.
- Creamos una app en Flask, un _framework_ de Python para hacer aplicaciones web. Desde ahí montamos una API.

Además, en caso de querer subir esto a la nube, pueden ver el siguiente tutorial  para [subir una aplicación de Flask a Heroku](https://dev.to/techparida/how-to-deploy-a-flask-app-on-heroku-heb) y para [conectar la aplicación en Flask a una base de datos en Atlas](https://developer.mongodb.com/how-to/use-atlas-on-heroku/).
