# 🚀 INSTRUCCIONES RÁPIDAS - DOCKER

## ✅ Archivos creados:
- ✅ Dockerfile (con soporte para Oracle Wallet)
- ✅ docker-compose.yml (con tus credenciales configuradas)
- ✅ Puerto actualizado a 8082

---

## 📦 PASO 1: Construir la imagen Docker

```powershell
# Asegúrate de estar en la carpeta del proyecto
cd "G:\Mi unidad\Proyectos\mascotas"

# Construir la imagen (esto tarda 3-5 minutos la primera vez)
docker-compose build
```

**Nota:** La primera vez descargará las imágenes base de Java y Maven, así que puede tardar un poco.

---

## 🏃 PASO 2: Ejecutar el contenedor

```powershell
# Iniciar el microservicio
docker-compose up -d

# Ver los logs en tiempo real
docker-compose logs -f
```

---

## ✅ PASO 3: Verificar que funciona

```powershell
# Opción 1: Verificar el health check
curl http://localhost:8082/actuator/health

# Opción 2: Probar un endpoint
curl http://localhost:8082/api/productos
```

O abre en tu navegador: http://localhost:8082/actuator/health

---

## 🛠️ Comandos útiles

```powershell
# Ver contenedores corriendo
docker ps

# Ver logs
docker-compose logs -f

# Detener el contenedor
docker-compose down

# Reconstruir la imagen (si haces cambios)
docker-compose build --no-cache

# Reiniciar el contenedor
docker-compose restart
```

---

## 🌐 SUBIR A DOCKER HUB (para Docker Lab)

```powershell
# 1. Login en Docker Hub
docker login

# 2. Etiquetar la imagen (reemplaza TU_USUARIO con tu usuario de Docker Hub)
docker tag microservicio-mascotas TU_USUARIO/tienda-mascotas:latest

# 3. Subir la imagen
docker push TU_USUARIO/tienda-mascotas:latest
```

---

## ⚠️ Si tienes problemas:

### Problema: "Error de conexión a Oracle"
**Solución:** Verifica que la carpeta `Wallet` esté en la raíz del proyecto

### Problema: "Puerto 8082 ya en uso"
**Solución:** 
```powershell
# Ver qué está usando el puerto
netstat -ano | findstr :8082

# Detener otros contenedores
docker-compose down
```

### Problema: "Maven build failed"
**Solución:**
```powershell
# Probar build localmente primero
mvn clean package -DskipTests

# Si funciona, entonces hacer el docker build
docker-compose build
```

---

## 📊 Estado actual:

✅ Dockerfile creado  
✅ docker-compose.yml creado  
✅ Puerto configurado (8082)  
✅ Credenciales Oracle configuradas  
✅ Wallet configurado  

**Siguiente paso:** Ejecutar `docker-compose build`

---

## 🎯 Para la entrega final:

1. ✅ Construir imagen: `docker-compose build`
2. ✅ Probar localmente: `docker-compose up`
3. ✅ Subir a Docker Hub: `docker push`
4. ✅ Desplegar en Docker Lab
5. ✅ Grabar video mostrando todo funcionando

---

**¡Listo para construir! Ejecuta:** `docker-compose build` 🚀
