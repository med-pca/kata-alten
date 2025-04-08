# Utilisation d'une image officielle OpenJDK pour exécuter l'application
FROM openjdk:21-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR généré depuis le build Maven/Gradle
COPY target/*.jar app.jar

# Exposer le port sur lequel l'application tourne
EXPOSE 8086

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
